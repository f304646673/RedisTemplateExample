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
