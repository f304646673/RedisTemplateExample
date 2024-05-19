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
