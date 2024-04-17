package org.example.redistemplateexample.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.util.Pair;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

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

            pubSubOperation.Publish(channel, message);
        });
    }

    @Test
    public void testSubscribe() {
        assertDoesNotThrow(() -> {
            Pair<String, String> pair = KeyGenerator.generateString("testSubscribe");
            final String channel = pair.getFirst();
            final String message = pair.getSecond();

            final MessageListener listener = mock(MessageListener.class);
            // MessageListener listener = (message1, pattern) -> {
            //     System.out.println("Message received: " + message1.toString());
            // };

            pubSubOperation.Subscribe(channel, listener);
            sleep(1000);
            pubSubOperation.Publish(channel, message);
            sleep(1000);
            verify(listener, only()).onMessage(any(), any());

        });
    }
}
