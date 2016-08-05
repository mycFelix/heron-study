package heron.kafka;


import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import com.twitter.heron.api.Config;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import java.util.Arrays;

/**
 * Created by Felix on 16/7/25.
 */
public class KafkaBaseTest {
    public static void main(String[] args) throws AlreadyAliveException, backtype.storm.generated.InvalidTopologyException {

        String zkConnString = "127.0.0.1:2181";
        String topicName = "test";
        String zkRoot = "/felix/brokers";

        BrokerHosts hosts = new ZkHosts(zkConnString, zkRoot);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, topicName, "/kafka/" + topicName, "felix");
        spoutConfig.scheme = new PrintScheme();

        spoutConfig.zkServers = Arrays.asList("127.0.0.1");
        spoutConfig.zkPort = 2181;

        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);


        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka", kafkaSpout);

        Config conf = new Config();
        conf.put("storm.zookeeper.session.timeout", 20000);
//        conf.put("storm.zookeeper.connection.timeout", 15000);
        conf.put("storm.zookeeper.retry.times", 5);
        conf.put("storm.zookeeper.retry.interval", 1000);

//        LocalCluster localCluster = new LocalCluster();
//        localCluster.submitTopology("Kafka-Base-Test", conf, builder.createTopology());
        StormSubmitter.submitTopology("Kafka-Base-Test", conf, builder.createTopology());

    }
}


