package org.example.redistemplateexample.controller;

import org.example.redistemplateexample.redis.StreamOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "StreamOperationController")
@RestController
@RequestMapping("/stream_operation")
public class StreamOperationController {

    @Autowired
    private StreamOperation streamOperation;

    @Operation(summary = "Add a new key-value pair to a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "key", description = "The key of the new key-value pair"),
        @Parameter(name = "value", description = "The value of the new key-value pair")
    })
    @PostMapping("/add")
    public String add(@RequestParam String streamKey, @RequestParam String key, @RequestParam String value) {
        return streamOperation.add(streamKey, key, value);
    }

    @Operation(summary = "Create a new consumer group")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the new consumer group")
    })
    @PostMapping("/create_consumer_group")
    public String createConsumerGroup(@RequestParam String streamKey, @RequestParam String consumerGroup) {
        return streamOperation.createConsumerGroup(streamKey, consumerGroup);
    }

    @Operation(summary = "Read a message from a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group"),
        @Parameter(name = "consumerName", description = "The name of the consumer")
    })
    @GetMapping("/read")
    public DeferredResult<String> read(@RequestParam String streamKey, @RequestParam String consumerGroup, @RequestParam String consumerName) {
        DeferredResult<String> result = new DeferredResult<>();
        result.onTimeout(() -> result.setErrorResult("Request timeout"));
        result.onCompletion(() -> System.out.println("Request completed"));
        result.onError((Throwable t) -> result.setErrorResult(t.getMessage()));
        
        new Thread(() -> {
            String value = streamOperation.read(streamKey, consumerGroup, consumerName);
            result.setResult(value);
        }).start();
        return result;
    }

    @Operation(summary = "Get the size of a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream")
    })
    @GetMapping("/size")
    public Long size(@RequestParam String streamKey) {
        return streamOperation.size(streamKey);
    }

    @Operation(summary = "Get the pending messages of a consumer")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group"),
        @Parameter(name = "consumerName", description = "The name of the consumer")
    })
    @GetMapping("/pending_messages")
    public String pendingMessages(@RequestParam String streamKey, @RequestParam String consumerGroup, @RequestParam String consumerName) {
        return streamOperation.pendingMessages(streamKey, consumerGroup, consumerName).toString();
    }
    
    @Operation(summary = "Get the pending messages summary of a consumer group")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group")
    })
    @GetMapping("/pending_messages_summary")
    public String pendingMessagesSummary(@RequestParam String streamKey, @RequestParam String consumerGroup) {
        return streamOperation.pendingMessagesSummary(streamKey, consumerGroup).toString();
    }

    @Operation(summary = "Acknowledge a message")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group"),
        @Parameter(name = "recordId", description = "The ID of the message")
    })
    @PutMapping("/acknowledge")
    public Long acknowledge(@RequestParam String streamKey, @RequestParam String consumerGroup, @RequestParam String recordId) {
        return streamOperation.ack(streamKey, consumerGroup, recordId);
    }

    @Operation(summary = "Read the first message from a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "recordId", description = "The ID of the message")
    })
    @GetMapping("/get_first_message")
    public String getMessage(@RequestParam String streamKey, @RequestParam String recordId) {
        return streamOperation.readFirstMessage(streamKey, recordId).toString();
    }

    @Operation(summary = "Read all messages from a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream")
    })
    @GetMapping("/read_all")
    public DeferredResult<String> readAll(@RequestParam String streamKey) {
        DeferredResult<String> result = new DeferredResult<>();
        result.onTimeout(() -> result.setErrorResult("Request timeout"));
        result.onCompletion(() -> System.out.println("Request completed"));
        result.onError((Throwable t) -> result.setErrorResult(t.getMessage()));
        
        new Thread(() -> {
            result.setResult(streamOperation.rangeAllMessages(streamKey).toString());
        }).start();
        return result;
    }

    @Operation(summary = "Read messages bigger than a given ID")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "recordId", description = "The ID of the message")
    })
    @GetMapping("/read_message_bigger_than")
    public DeferredResult<String> readMessageBiggerThan(@RequestParam String streamKey, @RequestParam String recordId) {
        DeferredResult<String> result = new DeferredResult<>();
        result.onTimeout(() -> result.setErrorResult("Request timeout"));
        result.onCompletion(() -> System.out.println("Request completed"));
        result.onError((Throwable t) -> result.setErrorResult(t.getMessage()));
        
        new Thread(() -> {
            result.setResult(streamOperation.rangeMessageBiggerThan(streamKey, recordId).toString());
        }).start();
        return result;
    }

    @Operation(summary = "Delete a message")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "recordId", description = "The ID of the message")
    })
    @DeleteMapping("/delete")
    public Long delete(@RequestParam String streamKey, @RequestParam String recordId) {
        return streamOperation.delete(streamKey, recordId);
    }

    @Operation(summary = "Delete messages equal or less than a given ID")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "recordId", description = "The ID of the message")
    })
    @DeleteMapping("/delete_equal_less_than")
    public Long deleteEqualLessThan(@RequestParam String streamKey, @RequestParam String recordId) {
        return streamOperation.deleteEqualLessThan(streamKey, recordId);
    }

    @Operation(summary = "Get the consumers of a stream")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group")
    })
    @GetMapping
    public String consumers(@RequestParam String streamKey, @RequestParam String consumerGroup) {
        return streamOperation.consumers(streamKey, consumerGroup).toString();
    }

    @Operation(summary = "Delete a consumer group")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group"),
        @Parameter(name = "consumerName", description = "The name of the consumer")
    })
    @DeleteMapping("/delete_consumer_group")
    public Boolean deleteConsumer(@RequestParam String streamKey, @RequestParam String consumerGroup, @RequestParam String consumerName) {
        return streamOperation.deleteConsumer(streamKey, consumerGroup, consumerName);
    }

    @Operation(summary = "Read and pass a message")
    @Parameters({
        @Parameter(name = "streamKey", description = "The key of the stream"),
        @Parameter(name = "consumerGroup", description = "The name of the consumer group")
    })
    @GetMapping("/read_and_pass")
    public String readAndPass(@RequestParam String streamKey, @RequestParam String consumerGroup) {
        return streamOperation.readAndPass(streamKey, consumerGroup);
    }
}
