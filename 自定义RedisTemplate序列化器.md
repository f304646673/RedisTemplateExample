在[《RedisTemplate保存二进制数据的方法》](https://fangliang.blog.csdn.net/article/details/139046319)一文中，我们将Java对象通过[《使用java.io库序列化Java对象》](https://blog.csdn.net/breaksoftware/article/details/138852948)中介绍的方法转换为二进制数组，然后保存到Redis中。实际可以通过定制RedisTemplate序列化器来避开手工序列化和反序列化的工作。本文我们将介绍3种常见的序列化器。
# RedisSerializer
```java
package org.example.redistemplateexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisTemplateBeansConfig {
    @Bean(name = "nomaljsonRedisTemplate")
    public <T> RedisTemplate<String, T> nomaljsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
主要设置ValueSerializer和HashValueSerializer。RedisSerializer.json()会将模板类型T的对象序列化为Json，然后保存到Redis中。
然后定义一个操作类，并使用上面创建的名字为nomaljsonRedisTemplate的redisTemplate。
```java
package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class NomalJsonOperation<T> {
    @Resource(name = "nomaljsonRedisTemplate")
    public RedisTemplate<String, T> redisTemplate;

    public void Set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public T Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
```
测试代码如下

```java
package org.example.redistemplateexample.redis;

import org.example.redistemplateexample.pojo.BaseTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NomalJsonOperationTest {

    @Autowired
    private NomalJsonOperation<BaseTypes> nomalJsonOperation;
    
    @Test
    public void testSetGet() {
        String key = "NomalJsonOperationTest";
        BaseTypes value = generate(2);

        nomalJsonOperation.Set(key, value);
        BaseTypes result = nomalJsonOperation.Get(key);

        assertEquals(value, result);
    }

    private BaseTypes generate(int mark) {
        BaseTypes baseTypes = new BaseTypes();
        baseTypes.setByteValue((byte) mark);
        baseTypes.setShortValue((short) mark);
        baseTypes.setIntValue(mark);
        baseTypes.setLongValue((long) mark);
        baseTypes.setFloatValue((float) mark);
        baseTypes.setDoubleValue((double) mark);
        baseTypes.setCharValue((char) mark);
        baseTypes.setBooleanValue(mark % 2 == 0);
        return baseTypes;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/1ac2858ae9af48759142ac165889ac96.png)

# FastJsonRedisSerializer
fastjson是阿里巴巴公司推出的json序列化库，在现实生产中广泛应用。
但是在我们的场景下，使用fastjson需要做一些特殊处理，模式也和其他两者不一样。

```java
package org.example.redistemplateexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

@Configuration
public class RedisTemplateBeansConfig {
    @Bean(name = "fastjsonRedisTemplate")
    public RedisTemplate<String, Object> fastjsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
可以看到，我们需要使用RedisTemplate<String, Object>，即模板类的第二类名需要使用Object。这是因为FastJsonRedisSerializer的构造需要指定Class\<T\>，而我们又不能通过函数的返回值类型T推导出Class\<T\>，所以只能使用Object.class这种通用的类型。当然，如果不需要使用模板类型，即让RedisTemplate只支持某个特定类型的Value，则可以直接指定确定的类型，而不用使用Object。
相对应的操作类，Get方法也只能返回Object，而不是模板类型T。

```java
package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class FastJsonOperation<T>  {

    @Resource(name = "fastjsonRedisTemplate")
    public RedisTemplate<String, T> redisTemplate;

    public void Set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
```
测试的代码如下

```java
package org.example.redistemplateexample.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.example.redistemplateexample.pojo.BaseTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FastJsonOperationTest {

    @Autowired
    private FastJsonOperation<BaseTypes> fastJsonOperation;
    
    @Test
    public void testSetGet() {
        String key = "FastJsonOperationTest";
        BaseTypes value = generate(2);

        fastJsonOperation.Set(key, value);
        BaseTypes result = JSONObject.parseObject(JSON.toJSONString(fastJsonOperation.Get(key)), BaseTypes.class);

        assertEquals(value, result);
    }

    private BaseTypes generate(int mark) {
        BaseTypes baseTypes = new BaseTypes();
        baseTypes.setByteValue((byte) mark);
        baseTypes.setShortValue((short) mark);
        baseTypes.setIntValue(mark);
        baseTypes.setLongValue((long) mark);
        baseTypes.setFloatValue((float) mark);
        baseTypes.setDoubleValue((double) mark);
        baseTypes.setCharValue((char) mark);
        baseTypes.setBooleanValue(mark % 2 == 0);
        return baseTypes;
    }
}
```
我们需要使用JSON.toJSONString将Fastjson序列化后的object转换成Json字符串，然后再使用JSONObject.parseObject将其转化成明确的类型BaseTypes。
这种转换让Fastjson方案黯然失色。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/be115f6afeff48ff8896183f1771a2e6.png)
# 自定义二进制序列化器
最后我们介绍结合了[《使用java.io库序列化Java对象》](https://blog.csdn.net/breaksoftware/article/details/138852948)和[《RedisTemplate保存二进制数据的方法》](https://fangliang.blog.csdn.net/article/details/139046319)的方式。
首先定义序列化器IoSerializer，它继承于RedisSerializer。中间的序列化和反序列化步骤已经在[《使用java.io库序列化Java对象》](https://blog.csdn.net/breaksoftware/article/details/138852948)中有过介绍。

```java
package org.example.redistemplateexample.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IoSerializer<T> implements RedisSerializer<T> {

    @Override
    public byte[] serialize(T obj) throws SerializationException {
        if (obj == null) {
            return new byte[0];
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new SerializationException("Failed to serialize", e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try( ObjectInputStream ois = new ObjectInputStream(bis)) {
            T obj = (T) ois.readObject();
            return obj;
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize", e);
        }
    }
}
```
然后设置序列化器

```java
package org.example.redistemplateexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisTemplateBeansConfig {

    @Bean(name = "iomemoryRedisTemplate")
    public <T> RedisTemplate<String, T> iomemoryRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new IoSerializer<T>());
        redisTemplate.setHashValueSerializer(new IoSerializer<T>());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```
再定义个操作类

```java
package org.example.redistemplateexample.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class IoMemoryOperation<T> {
    @Resource(name = "iomemoryRedisTemplate")
    public RedisTemplate<String, T> redisTemplate;

    public void Set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public T Get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
```
测试代码如下

```java
package org.example.redistemplateexample.redis;

import org.example.redistemplateexample.pojo.BaseTypes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IoMemoryOperationTest {
    @Autowired
    private IoMemoryOperation<BaseTypes> ioMemoryOperation;

    @Test
    public void testSetGet() {
        String key = "IoMemoryOperationTest";
        BaseTypes value = generate(2);

        ioMemoryOperation.Set(key, value);
        BaseTypes result = ioMemoryOperation.Get(key);

        assertEquals(value, result);
    }

    private BaseTypes generate(int mark) {
        BaseTypes baseTypes = new BaseTypes();
        baseTypes.setByteValue((byte) mark);
        baseTypes.setShortValue((short) mark);
        baseTypes.setIntValue(mark);
        baseTypes.setLongValue((long) mark);
        baseTypes.setFloatValue((float) mark);
        baseTypes.setDoubleValue((double) mark);
        baseTypes.setCharValue((char) mark);
        baseTypes.setBooleanValue(mark % 2 == 0);
        return baseTypes;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/9d480944bfb84d87a5c7714328da662f.png)

# 总结
- 广泛使用的Fastjson在使用模板类时，类型转换比较丑陋。
- iomemoryRedisTemplate和nomaljsonRedisTemplate被限制在Java工程中。特别是iomemoryRedisTemplate方案，因为它保存的是Java对象的二进制值。
# 代码
[https://github.com/f304646673/RedisTemplateExample](https://github.com/f304646673/RedisTemplateExample)
