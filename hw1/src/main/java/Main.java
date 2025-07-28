public class Main {
    public static void main(String[] args) {
        Thread producerThread = new Thread(() -> {
            new WeatherProducer().run();
        });
        producerThread.start();

        Thread consumerThread = new Thread(() -> {
            new WeatherConsumer().run();
        });
        consumerThread.start();
    }
}