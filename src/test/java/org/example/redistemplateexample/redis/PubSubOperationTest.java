package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.function.Consumer;
import org.springframework.data.redis.connection.ReactiveSubscription;

@SpringBootTest
public class PubSubOperationTest {
    @Autowired
    private PubSubOperation pubSubOperation;

    @Test
    public void testPublish() {
        assertDoesNotThrow(() -> {
            Pair<String, String> pair = KeyGenerator.generateString("testPublish");
            final String channel = pair.getFirst();
            final String message = pair.getSecond();

            assertEquals(0, pubSubOperation.Publish(channel, message));
        });
    }

    @Test
    public void testSubscribe() {
        assertDoesNotThrow(() -> {
            Pair<String, String> pair = KeyGenerator.generateString("testSubscribe");
            final String channel = pair.getFirst();
            final String message = pair.getSecond();

            Consumer<ReactiveSubscription.Message<String, String>> consumer = new Consumer<ReactiveSubscription.Message<String,String>>() {
                @Override
                public void accept(ReactiveSubscription.Message<String, String> message) {
                    assertEquals(channel, message.getChannel());
                    assertEquals(message, message.getMessage());
                }
            };
            pubSubOperation.Subscribe(channel, consumer);

            sleep(1000);
            assertEquals(1, pubSubOperation.Publish(channel, message));
            sleep(1000);
        });
    }
}
