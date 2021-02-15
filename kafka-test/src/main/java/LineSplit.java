import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.state.KeyValueStore;

public class LineSplit {

	final CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();

		// 스트림 애플리케이션 아이디 지정
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-linesplit");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		final StreamsBuilder builder = new StreamsBuilder();

		builder.<String, String>stream("streams-plaintext-input")
				.flatMapValues(new ValueMapper<String, Iterable<String>>() {
					@Override
					public Iterable<String> apply(String value) {
						return Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+"));
					}
				})
				// 각 문자열 별로 모으는 새로운 스트림을 만들기 위해 groupBy 메소드 사용
				// groupBy를 사용해서 같은 키를 가진 데이터를 모은다
				// 기존에 split으로 분리된 스트림 데이터에는 키 값이 없으므로
				// 새로운 키/값 쌍을 만들기 위해 KeyValueMapper를 사용한다.
				.groupBy(new KeyValueMapper<String, String, String>() {
					@Override
					public String apply(String key, String value) {
						return value;
					}
				})
				//count는 스트림을 테이블로 변환할 때 사용하는 메서드이다. count의 입력값은
				//groupBy 메소드의 결과값인 String, Long인데 이것을 Bytes와 byte[]로 변환해서 counts-store에 테이블 형태로 저장한다.
				//여기에 저장된 값은 실시간으로 쿼리가 가능하다(이것을 발전시킨 것이 KSQL)
				.count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store")).toStream()
				//count의 결과로 생성된 counts-store 테이블 데이터를 스트림으로 변환한하여 streas-wordcount-output 토픽에 저장한다.
				//저장할때 데이터 값은 Bytes, byte[]에서 Serdes.String()과 Serder.Long() 타입으로 변환한다.
				.to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));

		final Topology topology = builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, props);
		final CountDownLatch latch = new CountDownLatch(1);

		// Ctrl+C를 처리하기 위한 핸들러 추가
		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				System.out.println("topology started");
				latch.countDown();
				System.out.println("topology terminated");
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (Throwable e) {
			System.exit(1);
		}
		System.exit(0);
	}
}
