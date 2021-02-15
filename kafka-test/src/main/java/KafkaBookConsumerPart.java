import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

public class KafkaBookConsumerPart {
  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092");
    props.put("group.id", "peter-partition");
    props.put("enable.auto.commit", "false");
    props.put("auto.offset.reset", "latest");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    String topic = "peter-topic";
    TopicPartition partition0 = new TopicPartition(topic, 0);
    TopicPartition partition1 = new TopicPartition(topic, 1);
    
    consumer.assign(Arrays.asList(partition0, partition1));
    //seek(파티션번호, 오프셋번호)를 지정해서 컨슈머가 다음 poll()하는 위치를 지정할 수 있다.
    //아래의 코드는 0,1번 파티션의 2번 오프셋부터 메시지를 가져오게 된다.
    consumer.seek(partition0, 2);
    consumer.seek(partition1, 2);
    
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      for (ConsumerRecord<String, String> record : records)
      {
        System.out.printf("Topic: %s, Partition: %s, Offset: %d, Key: %s, Value: %s\n", record.topic(), record.partition(), record.offset(), record.key(), record.value());
      }
      try {
        consumer.commitSync();
      } catch (CommitFailedException e) {
        System.out.printf("commit failed", e);
      }
    }
  }
}