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
