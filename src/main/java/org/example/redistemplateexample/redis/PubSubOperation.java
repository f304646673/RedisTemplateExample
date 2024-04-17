package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PubSubOperation {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void Publish(String channel, Object message) throws Exception {
        redisTemplate.convertAndSend(channel, message);
    }

    public void Subscribe(String channel, MessageListener listener) throws Exception {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.subscribe(listener, channel.getBytes());
                return null;
            }
        });
    }

//    public void Unsubscribe(String channel, MessageListener listener) throws Exception {
//        redisTemplate.execute(new RedisCallback<Void>() {
//            @Override
//            public Void doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.unsubscribe(listener, channel.getBytes());
//                return null;
//            }
//        });
//    }

    
}
