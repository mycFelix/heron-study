package heron.kafka;

import backtype.storm.tuple.Fields;
import backtype.storm.spout.MultiScheme;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 16/7/25.
 */
public class PrintScheme implements MultiScheme {
    @Override
    public Iterable<List<Object>> deserialize(byte[] bytes) {
        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("kafka=======>" + message);
        List<List<Object>> result = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        list.add(message);
        return result;
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("str");
    }
}
