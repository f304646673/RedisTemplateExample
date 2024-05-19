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

