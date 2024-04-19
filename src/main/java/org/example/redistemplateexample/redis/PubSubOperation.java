package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;

import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PubSubOperation {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    
    @Resource
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    
    public Long Publish(String channel, String message) {
        return redisTemplate.convertAndSend(channel, message);
    }

    public void Subscribe(String channel, Consumer<ReactiveSubscription.Message<String,String>> consumer) {
        // Consumer<ReactiveSubscription.Message<String,String>> consumer = new Consumer<ReactiveSubscription.Message<String,String>>() {
        //     @Override
        //     public void accept(ReactiveSubscription.Message<String, String> message) {
        //         System.out.println("Message received: " + message.getMessage());
        //     }
        // };

        reactiveRedisTemplate.listenToChannel(channel).doOnNext(consumer).subscribe();
    }
}
