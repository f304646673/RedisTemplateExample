在工作中，我们经常可以见到值是字符串类型的RedisTemplate<String, String>。而有些场景下，比如我们希望保存某个对象，而又不想使用外部的一些序列化方法，则可以考虑将数据保存而为二进制，然后保存到Redis中。这个时候，我们就可以使用Value类型是byte[]类型的RedisTemplate<String, byte[]>。
为了区分一般项目中常用的RedisTemplate<String, String>类型，我们给新的Bean取一个名字bytesRedisTemplate。

```java
package org.example.redistemplateexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisTemplateBeansConfig {

    @Bean(name = "bytesRedisTemplate")
    public RedisTemplate<String, byte[]> bytesRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setHashValueSerializer(RedisSerializer.byteArray());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
上述类会重用connectionFactory，这样就会和项目中其他默认的RedisTemplate使用的是相同的连接属性，包括地址、用户名、密码、连接池等。
然后定义一个封装了Redis操作的类

```java
package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
@Component
public class BytesOperation {
    @Resource(name = "bytesRedisTemplate")
    private RedisTemplate<String, byte[]> redisTemplate;

    public void Set(String key, byte[] value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public byte[] Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
```
BytesOperation 类中的redisTemplate通过Resource的name指定为之前创建的Bean

在测试代码中，我们使用[《使用java.io库序列化Java对象》](https://blog.csdn.net/breaksoftware/article/details/138852948)中的序列化方法，将测试对象转换成二进制数组，然后保存到Redis中。

```java
package org.example.redistemplateexample.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.example.redistemplateexample.pojo.BaseTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BytesOperationTest {

    @Autowired
    private BytesOperation bytesOperation;

    @Test
    public void testSet() {
        BaseTypes baseTypesFrom = new BaseTypes();
        baseTypesFrom.setByteValue((byte) 1);
        baseTypesFrom.setByteValue((byte) 1);
        baseTypesFrom.setShortValue((short) 2);
        baseTypesFrom.setIntValue(3);
        baseTypesFrom.setLongValue(4L);
        baseTypesFrom.setFloatValue(5.0f);
        baseTypesFrom.setDoubleValue(6.0);
        baseTypesFrom.setCharValue('7');
        baseTypesFrom.setBooleanValue(true);

        String key = "baseTypes";

        try {
            bytesOperation.Set(key, serialize(baseTypesFrom));
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        byte[] get = bytesOperation.Get(key);

        BaseTypes baseTypesTo = null;
        try {
            baseTypesTo = deserialize(get);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
        assertEquals(baseTypesFrom, baseTypesTo);
    }

    public static <T> byte[] serialize(T obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }

    public static <T> T deserialize(byte[] data) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try( ObjectInputStream ois = new ObjectInputStream(bis)) {
            @SuppressWarnings("unchecked")
            T obj = (T) ois.readObject();  
            return obj;
        }
    }
}
```
