在现实项目中，我们往往会遇到需要使用多个Redis数据源的场景。本文介绍的是一种高度定制化的方案。每个独立的数据源都会使用自己的配置，其中包括针对该数据源的连接池配置。
要完成这个功能，需要解决几个基础能力：

 - 在配置文件中加载Redis数据源和连接池数组配置
 - 构建连接池（本例使用默认的Lettuce）
 - 多种Redis部署模式的适配

# 配置
这个配置的设计也是在构建整个项目中不停积累起来的。
首先我们需要确定有哪几种Redis模式：

 - standalone：单机
 - cluster：集群
 - sentinel：哨兵

本例我们将支持所有的模式，但是仅在单机模式下完成了测试。其他模式因为没有环境，没有测试。
## 配置文件结构

```java
package org.example.redistemplateexample.config;

import lombok.Data;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis-pool")
@Component
public class RedisPoolConfig {

    private List<Config> configs;

    @Data
    public static class Config {
        private String hostAndPort;
        private int type;
        private String username;
        private String password;
        private String database;
        private String sentinelMasterHostAndPort; // for Sentine
        private String masterName; // for Sentine
        private String clusterMaxRedirects; // for Cluster
        private String timeout;
        private String maxActive;
        private String maxWait;
        private String maxIdle;
        private String minIdle;
    }
}
```
hostAndPort用于保存连接地址和端口。
type表示Redis的部署模式：1 单机；2 集群；3 哨兵。
username和password表示用户名和密码。
database表示分片号。注意Cluster(集群)模式是没有分片号的。
sentinelMasterHostAndPort表示Sentinel(哨兵)模式下的主节点信息。
masterName是master节点的命名。
clusterMaxRedirects表示Cluster(集群)模式下重定向的最大数量。在 Redis Cluster 环境中，客户端可以向任何一个节点发出请求，如果所请求的数据不在该节点上，该节点会指引客户端到正确节点。maxredirects 参数定义了在一系列重定向后，客户端在放弃并返回错误之前，可以跟随的重定向次数。这是为了防止在网络问题或配置错误时，客户端在 Cluster 中无限循环。
timeout、maxActive、maxWait、maxIdle和minIdle都是连接池需要的参数。当然完整的参数不止这些，我们只是列出了常用的参数。
@ConfigurationProperties注解表示我们需要Spingboot加载的配置文件中，对应于本数据结构的字段特征。本例中prefix = "redis-pool"，即表示文本中的内容是以redis-pool开头的。
## 配置文件
以下是application.properties文件中的内容。它们都是以RedisPoolConfig的@ConfigurationProperties注解中的prefix = "redis-pool"开头，然后configs对应于RedisPoolConfig.configs，之后跟随的是数组编号。本例我们将测试下标为0的配置，它的最大连接数maxActive是30，最大空闲连接数maxIdle也是30，最小空闲连接数minIdle是10，最长等待时间是10000毫秒。
```yaml
redis-pool.configs[0].hostAndPort=127.0.0.1:6379
redis-pool.configs[0].type=1
redis-pool.configs[0].maxActive=30
redis-pool.configs[0].maxIdle=30
redis-pool.configs[0].minIdle=10
redis-pool.configs[0].maxWait=10000

redis-pool.configs[1].hostAndPort=127.0.0.1:6379
redis-pool.configs[1].type=2

redis-pool.configs[2].hostAndPort=127.0.0.1:6379
redis-pool.configs[2].type=3
```
# 连接池和数据源定制
之前我们将配置以及配置对应的数据结构准备好了。
现在我们需要Spingboot将配置文件加载好以便我们使用。这就要求我们需要告知Springboot相关代码的构建和运行顺序，即要先把配置文件加载好，然后再调用使用配置文件的代码。

```java
@Component
@DependsOn("redisPoolConfig")
@Data
public class RedisPool {
    @Resource
    private RedisPoolConfig redisPoolConfig;
```
@DependsOn("redisPoolConfig")表示RedisPool的构建需要在redisPoolConfig构建好之后，即要等到配置文件都转换成Java类RedisPoolConfig的对象之后。
## 连接池
我们使用线程安全的Lettuce客户端。核心的一些配置如下：
```java
    private LettuceClientConfiguration getClientConfiguration(RedisPoolConfig.Config config) {
        GenericObjectPoolConfig<LettuceConnection> poolConfig = new GenericObjectPoolConfig<LettuceConnection>();
        if (null != config.getMaxActive() && !config.getMaxActive().isEmpty()) {
            poolConfig.setMaxTotal(Integer.parseInt(config.getMaxActive()));
        }
        if (null != config.getMaxWait() && !config.getMaxWait().isEmpty()) {
            poolConfig.setMaxWait(Duration.ofMillis(Integer.parseInt(config.getMaxWait())));
        }
        if (null != config.getMaxIdle() && !config.getMaxIdle().isEmpty()) {
            poolConfig.setMaxIdle(Integer.parseInt(config.getMaxIdle()));
        }
        if (null != config.getMinIdle() && !config.getMinIdle().isEmpty()) {
            poolConfig.setMinIdle(Integer.parseInt(config.getMinIdle()));
        }
        int timeout = -1;
        if(null != config.getTimeout() && !config.getTimeout().isEmpty()) {
            timeout = Integer.parseInt(config.getTimeout());
        }

        return LettucePoolingClientConfiguration.builder()
                .shutdownTimeout(Duration.ofMillis(timeout)).poolConfig(poolConfig).build();
    }
```
## Sentinel（哨兵）模式
主要是设置MASTER和REPLICA节点信息。
```java
    private RedisSentinelConfiguration createRedisSentinelConfiguration(RedisPoolConfig.Config config) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        if (null == config.getSentinelMasterHostAndPort() || config.getSentinelMasterHostAndPort().isEmpty()) {
            return null;
        }

        List<Pair<String, Integer>> sentinels = parseClusterHostAndPort(config.getSentinelMasterHostAndPort());
        if (sentinels.size() != 1) {
            return null;
        }
        for (Pair<String, Integer> sentinel : sentinels) {
            RedisNodeBuilder builder = RedisNode.newRedisNode().withName(config.getMasterName()).promotedAs(NodeType.MASTER).listeningAt(sentinel.getFirst(), sentinel.getSecond());
            RedisNode node = builder.build();
            redisSentinelConfiguration.setMaster(node);
        }

        List<Pair<String, Integer>> hostAndPorts = parseClusterHostAndPort(config.getHostAndPort());
        if (hostAndPorts.isEmpty()) {
            return null;
        }
        for (Pair<String, Integer> hostAndPort : hostAndPorts) {
            RedisNodeBuilder builder = RedisNode.newRedisNode().promotedAs(NodeType.REPLICA).listeningAt(hostAndPort.getFirst(), hostAndPort.getSecond()); 
            RedisNode node = builder.build();
            redisSentinelConfiguration.addSentinel(node);
        }

		setUsername(config, redisSentinelConfiguration);
        setPassword(config, redisSentinelConfiguration);
        setDatabase(config, redisSentinelConfiguration);
        
        return redisSentinelConfiguration;
    }
```
## Cluster（集群）模式

```java
    private RedisClusterConfiguration createRedisClusterConfiguration(RedisPoolConfig.Config config) {
        List<Pair<String, Integer>> hostAndPorts = parseClusterHostAndPort(config.getHostAndPort());
        if (hostAndPorts.isEmpty()) {
            return null;
        }
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        for (Pair<String, Integer> hostAndPort : hostAndPorts) {
            RedisNode node = new RedisNode(hostAndPort.getFirst(), hostAndPort.getSecond());
            redisClusterConfiguration.addClusterNode(node);
        }

        setUsername(config, redisClusterConfiguration);
        setPassword(config, redisClusterConfiguration);
        setClusterConf(config, redisClusterConfiguration);

        return redisClusterConfiguration;
    }

    private void setClusterConf(RedisPoolConfig.Config config, RedisClusterConfiguration redisClusterConfiguration) {
        if (null != config.getClusterMaxRedirects() && !config.getClusterMaxRedirects().isEmpty()) {
            int maxRedirects = Integer.parseInt(config.getClusterMaxRedirects());
            redisClusterConfiguration.setMaxRedirects(maxRedirects);
        }
    }
```
## Standalone（单机）模式

```java
    private RedisStandaloneConfiguration createRedisStandaloneConfiguration(RedisPoolConfig.Config config) {
        Pair<String, Integer> hostAndPort = parseHostAndPort(config.getHostAndPort());
        if (null == hostAndPort) {
            return null;
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostAndPort.getFirst());
        redisStandaloneConfiguration.setPort(hostAndPort.getSecond());

        setUsername(config, redisStandaloneConfiguration);
        setPassword(config, redisStandaloneConfiguration);
        setDatabase(config, redisStandaloneConfiguration);

        return redisStandaloneConfiguration;
    }
```

## 完整代码

```java
package org.example.redistemplateexample.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Data;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration.WithDatabaseIndex;
import org.springframework.data.redis.connection.RedisConfiguration.WithPassword;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisNode.NodeType;
import org.springframework.data.redis.connection.RedisNode.RedisNodeBuilder;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@DependsOn("redisPoolConfig")
@Data
public class RedisPool {
    @Resource
    private RedisPoolConfig redisPoolConfig;

    private List<LettuceConnectionFactory> redisConnectionFactorys = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (RedisPoolConfig.Config config : redisPoolConfig.getConfigs()) {
            LettuceConnectionFactory redisConnectionFactory = null;
            final int standalone = 1;
            final int cluster = 2;
            final int sentinel = 3; // to do
            LettuceClientConfiguration clientConfig = getClientConfiguration(config);
            switch (config.getType()) {
                case standalone:
                    RedisStandaloneConfiguration redisStandaloneConfiguration = createRedisStandaloneConfiguration(
                            config);
                    if (redisStandaloneConfiguration != null) {
                        redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
                    }
                    break;
                case cluster:
                    RedisClusterConfiguration redisClusterConfiguration = createRedisClusterConfiguration(config);
                    if (redisClusterConfiguration != null) {
                        redisConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
                    }
                    break;
                case sentinel:
                    RedisSentinelConfiguration redisSentinelConfiguration = createRedisSentinelConfiguration(config);
                    if (redisSentinelConfiguration != null) {
                        redisConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, clientConfig);
                    }
                    break;
                default:
                    System.out.printf("Unknown type: %d\n", config.getType());
                    break;
            }
            if (null != redisConnectionFactory) {
                redisConnectionFactory.start(); // start() for spring-data-redis-3.X; afterPropertiesSet() for spring-data-redis-2.X 
                redisConnectionFactorys.add(redisConnectionFactory);
            }
        }
    }

    private LettuceClientConfiguration getClientConfiguration(RedisPoolConfig.Config config) {
        GenericObjectPoolConfig<LettuceConnection> poolConfig = new GenericObjectPoolConfig<LettuceConnection>();
        if (null != config.getMaxActive() && !config.getMaxActive().isEmpty()) {
            poolConfig.setMaxTotal(Integer.parseInt(config.getMaxActive()));
        }
        if (null != config.getMaxWait() && !config.getMaxWait().isEmpty()) {
            poolConfig.setMaxWait(Duration.ofMillis(Integer.parseInt(config.getMaxWait())));
        }
        if (null != config.getMaxIdle() && !config.getMaxIdle().isEmpty()) {
            poolConfig.setMaxIdle(Integer.parseInt(config.getMaxIdle()));
        }
        if (null != config.getMinIdle() && !config.getMinIdle().isEmpty()) {
            poolConfig.setMinIdle(Integer.parseInt(config.getMinIdle()));
        }
        int timeout = -1;
        if(null != config.getTimeout() && !config.getTimeout().isEmpty()) {
            timeout = Integer.parseInt(config.getTimeout());
        }

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .shutdownTimeout(Duration.ofMillis(timeout)).poolConfig(poolConfig).build();
        return clientConfig;
    }

    private RedisSentinelConfiguration createRedisSentinelConfiguration(RedisPoolConfig.Config config) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        if (null == config.getSentinelMasterHostAndPort() || config.getSentinelMasterHostAndPort().isEmpty()) {
            return null;
        }

        List<Pair<String, Integer>> sentinels = parseClusterHostAndPort(config.getSentinelMasterHostAndPort());
        if (sentinels.size() != 1) {
            return null;
        }
        for (Pair<String, Integer> sentinel : sentinels) {
            RedisNodeBuilder builder = RedisNode.newRedisNode().withName(config.getMasterName()).promotedAs(NodeType.MASTER).listeningAt(sentinel.getFirst(), sentinel.getSecond());
            RedisNode node = builder.build();
            redisSentinelConfiguration.setMaster(node);
        }

        List<Pair<String, Integer>> hostAndPorts = parseClusterHostAndPort(config.getHostAndPort());
        if (hostAndPorts.isEmpty()) {
            return null;
        }
        for (Pair<String, Integer> hostAndPort : hostAndPorts) {
            RedisNodeBuilder builder = RedisNode.newRedisNode().promotedAs(NodeType.REPLICA).listeningAt(hostAndPort.getFirst(), hostAndPort.getSecond()); 
            RedisNode node = builder.build();
            redisSentinelConfiguration.addSentinel(node);
        }

        setUsername(config, redisSentinelConfiguration);
        setPassword(config, redisSentinelConfiguration);
        setDatabase(config, redisSentinelConfiguration);

        return redisSentinelConfiguration;
    }

    private RedisClusterConfiguration createRedisClusterConfiguration(RedisPoolConfig.Config config) {
        List<Pair<String, Integer>> hostAndPorts = parseClusterHostAndPort(config.getHostAndPort());
        if (hostAndPorts.isEmpty()) {
            return null;
        }
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        for (Pair<String, Integer> hostAndPort : hostAndPorts) {
            RedisNode node = new RedisNode(hostAndPort.getFirst(), hostAndPort.getSecond());
            redisClusterConfiguration.addClusterNode(node);
        }

        setUsername(config, redisClusterConfiguration);
        setPassword(config, redisClusterConfiguration);
        setClusterConf(config, redisClusterConfiguration);

        return redisClusterConfiguration;
    }

    private RedisStandaloneConfiguration createRedisStandaloneConfiguration(RedisPoolConfig.Config config) {
        Pair<String, Integer> hostAndPort = parseHostAndPort(config.getHostAndPort());
        if (null == hostAndPort) {
            return null;
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostAndPort.getFirst());
        redisStandaloneConfiguration.setPort(hostAndPort.getSecond());

        setUsername(config, redisStandaloneConfiguration);
        setPassword(config, redisStandaloneConfiguration);
        setDatabase(config, redisStandaloneConfiguration);

        return redisStandaloneConfiguration;
    }

    private void setUsername(RedisPoolConfig.Config config, WithPassword connectionFactory) {
        if (null != config.getUsername() && !config.getUsername().isEmpty()) {
            connectionFactory.setUsername(config.getUsername());
        }
    }

    private void setPassword(RedisPoolConfig.Config config, WithPassword connectionFactory) {
        if (null != config.getPassword() && !config.getPassword().isEmpty()) {
            connectionFactory.setPassword(config.getPassword());
        }
    }

    private void setDatabase(RedisPoolConfig.Config config, WithDatabaseIndex connectionFactory) {
        if (null != config.getDatabase() && !config.getDatabase().isEmpty()) {
            int database = Integer.parseInt(config.getDatabase());
            connectionFactory.setDatabase(database);
        }
    }

    private void setClusterConf(RedisPoolConfig.Config config, RedisClusterConfiguration redisClusterConfiguration) {
        if (null != config.getClusterMaxRedirects() && !config.getClusterMaxRedirects().isEmpty()) {
            int maxRedirects = Integer.parseInt(config.getClusterMaxRedirects());
            redisClusterConfiguration.setMaxRedirects(maxRedirects);
        }
    }

    private Pair<String, Integer> parseHostAndPort(String hostAndPortStr) {
        String[] hostAndPort = hostAndPortStr.split(":");
        if (hostAndPort.length != 2) {
            System.out.printf("Invalid host and port: %s\n", hostAndPortStr);
            return null;
        }
        String host = hostAndPort[0].trim();
        String port = hostAndPort[1].trim();
        return Pair.of(host, Integer.parseInt(port));
    }

    private List<Pair<String, Integer>> parseClusterHostAndPort(String hostAndPortStr) {
        String[] hosts = hostAndPortStr.split(",");
        List<Pair<String, Integer>> hostAndPorts = new ArrayList<>();
        for (String hostAndPort : hosts) {
            Pair<String, Integer> pair = parseHostAndPort(hostAndPort);
            if (null != pair) {
                hostAndPorts.add(pair);
            }
        }
        return hostAndPorts;
    }
}
```
# 工具类
工具类主要封装了RedisTemplate，并且通过下标确定该RedisTemplate使用哪个连接配置。
```java
package org.example.redistemplateexample.config;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
@DependsOn("redisPool")
public class RedisTemplateList {
    @Resource
    private RedisPool redisPool;

    public <T> RedisTemplate<String, T> getRedisTemplate(int index) {
        LettuceConnectionFactory lettuceConnectionFactory = redisPool.getRedisConnectionFactorys().get(index);
        if (lettuceConnectionFactory == null) {
            return null;
        }
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
```
# 测试代码
我们启动80个线程，并且保持一个长连接读取Redis。
在配置中，我们要求连接池最大连接数和最大空闲连接数是30。这样理论上上面代码会导致Redis连接数新增30个。
```java
package org.example.redistemplateexample.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;

@SpringBootTest
public class RedisTemplateListTest {
    @Autowired
    private RedisTemplateList redisTemplateList;

    @SuppressWarnings("null")
    @Test
    public void testGetRedisTemplate() {
        RedisTemplate<String, String> stringRedisTemplate = redisTemplateList.getRedisTemplate(0);
        assertNotNull(stringRedisTemplate);
        String key = "key";
        String value = "value";
        stringRedisTemplate.opsForValue().set(key, value);
        String result = stringRedisTemplate.opsForValue().get(key);
        assertNotNull(result);

        for (int i = 1; i < 80; i++) {
            new Thread(() -> {
                RedisTemplate<String, String> redisTemplate = redisTemplateList.getRedisTemplate(0);
                assertNotNull(redisTemplate);
                StreamReadOptions options = StreamReadOptions.empty().block(Duration.ofSeconds(100)).count(1);
                while (true) {
                    List<MapRecord<String, Object, Object>> records = redisTemplate.opsForStream().read(Consumer.from("group", "consumer"), options, StreamOffset.create("stream", ReadOffset.lastConsumed()));
                    if (!records.isEmpty()) {
                        records.forEach(record -> {
                            System.out.println("Thread " + Thread.currentThread().threadId() + " received: " + record.getValue());
                        });
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
我们在其他Redis连接客户端使用info clients命令查看连接数，可以看到前后变化如下
## 测试前

> \# Clients
connected_clients:2
cluster_connections:0
maxclients:10000
client_recent_max_input_buffer:20480
client_recent_max_output_buffer:0
blocked_clients:0
tracking_clients:0
clients_in_timeout_table:0
total_blocking_keys:0
total_blocking_keys_on_nokey:0

## 测试中

> \# Clients
connected_clients:32
cluster_connections:0
maxclients:10000
client_recent_max_input_buffer:20480
client_recent_max_output_buffer:0
blocked_clients:0
tracking_clients:0
clients_in_timeout_table:0
total_blocking_keys:0
total_blocking_keys_on_nokey:0

## 测试后
> \# Clients
connected_clients:2
cluster_connections:0
maxclients:10000
client_recent_max_input_buffer:20480
client_recent_max_output_buffer:0
blocked_clients:0
tracking_clients:0
clients_in_timeout_table:0
total_blocking_keys:0
total_blocking_keys_on_nokey:0

可以见到连接池配置生效。

# 代码
[https://github.com/f304646673/RedisTemplateExample](https://github.com/f304646673/RedisTemplateExample)