package one.bartosz.metrics.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfiguration {

    private final String influxToken;
    private final String influxURL;

    public InfluxDBConfiguration(@Value("${metrics.influxdb.token}") String influxToken, @Value("${metrics.influxdb.url}") String influxURL) {
        this.influxToken = influxToken;
        this.influxURL = influxURL;
    }

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(influxURL, influxToken.toCharArray());
    }
}
