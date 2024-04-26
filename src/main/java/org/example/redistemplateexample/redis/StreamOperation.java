package org.example.redistemplateexample.redis;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.time.Duration;

import lombok.val;

import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.Limit;

@Component
public class StreamOperation {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("null")
    public String add(String streamKey, String key, String value) {
        Map<String, String> stores = Map.of(key, value);
        RecordId recordId = redisTemplate.opsForStream().add(streamKey, stores);
        return recordId.getValue();
    }


    // @SuppressWarnings("unchecked")
    // public void read(String streamKey) {
    //     redisTemplate.opsForStream().read(StreamOffset.create(streamKey, ReadOffset.lastConsumed()));
    // }

    public String createConsumerGroup(String streamKey, String consumerGroup) {
        return redisTemplate.opsForStream().createGroup(streamKey, consumerGroup);
    }

    public String read(String streamKey, String consumerGroup, String consumerName) {
        Consumer consumer = Consumer.from(consumerGroup, consumerName);
        StreamReadOptions options = StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1000));
        StreamOffset<String> streamOffset = StreamOffset.create(streamKey, ReadOffset.lastConsumed());
        @SuppressWarnings("unchecked")
        List<MapRecord<String,Object,Object>> list = redisTemplate.opsForStream().read(consumer, options, streamOffset);
        if (list == null || list.isEmpty()) {
            return null;
        }
        val record = list.getFirst();
        return record.toString();
    }

    public Long size(String streamKey) {
        return redisTemplate.opsForStream().size(streamKey);
    }

    public PendingMessages pendingMessages(String streamKey, String consumerGroup, String consumerName) {
        Consumer consumer = Consumer.from(consumerGroup, consumerName);
        return redisTemplate.opsForStream().pending(streamKey, consumer);
    }

    public PendingMessagesSummary pendingMessagesSummary(String streamKey, String consumerGroup) {
        return redisTemplate.opsForStream().pending(streamKey, consumerGroup);
    }   

    public Long ack(String streamKey, String consumerGroup, String recordId) {
        return redisTemplate.opsForStream().acknowledge(streamKey, consumerGroup, recordId);
    }

    public MapRecord<String,Object,Object> readFirstMessage(String streamKey, String recordIdStart) {
        ReadOffset fromOffset = ReadOffset.from(recordIdStart);
        StreamOffset<String> streamOffset = StreamOffset.create(streamKey, fromOffset);
        
        @SuppressWarnings("unchecked")
        List<MapRecord<String,Object,Object>> list = redisTemplate.opsForStream().read(streamOffset);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getFirst();
    }

    public List<MapRecord<String,Object,Object>> rangeAllMessages(String streamKey) {
        Range<String> range = Range.unbounded();
        return redisTemplate.opsForStream().range(streamKey, range, Limit.unlimited());
    }

    public List<MapRecord<String,Object,Object>> rangeMessageBiggerThan(String streamKey, String recordId) {
        Range<String> range = Range.rightUnbounded(Bound.exclusive(recordId));
        return redisTemplate.opsForStream().range(streamKey, range, Limit.unlimited());
    }

    public Long delete(String streamKey, String recordId) {
        return redisTemplate.opsForStream().delete(streamKey, recordId);
    }

    public Long deleteEqualLessThan(String streamKey, String recordId) {
        Range<String> range = Range.leftUnbounded(Bound.inclusive(recordId));
        List<String> recordIds = new java.util.ArrayList<>();
        List<MapRecord<String,Object,Object>> records = redisTemplate.opsForStream().range(streamKey, range, Limit.unlimited());
        if (null == records || records.isEmpty()) {
            return 0L;
        }
        records.forEach(record -> {
            recordIds.add(record.getId().getValue());
        });
        return redisTemplate.opsForStream().delete(streamKey, recordIds.toArray(new String[0]));
    }
}
