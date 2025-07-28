import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import java.util.Properties;
import java.util.Random;

public class WeatherProducer {
    private static final String TOPIC = "weathertopic";
    private KafkaProducer<String, String> producer;
    private Random random;

    public WeatherProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        random = new Random();
    }

    private String generateWeather() {
        int temperature = random.nextInt(36); // 0-35
        String[] conditions = {"sunny", "cloudy", "rain"};
        String condition = conditions[random.nextInt(conditions.length)];
        return String.format("Temperature: %d°C, condition: %s", temperature, condition);
    }

    public void sendWeather() {
        String weatherData = generateWeather();
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, weatherData);
        producer.send(record);
        System.out.println("Sent: " + weatherData);
    }

    public void close() {
        producer.close();
    }

    public void run() {
        WeatherProducer producer = new WeatherProducer();

        while (true) {
            try {
                producer.sendWeather();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                break;
            }
        }
    }
}