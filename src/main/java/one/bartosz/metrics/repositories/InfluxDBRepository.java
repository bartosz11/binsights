package one.bartosz.metrics.repositories;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.write.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

//A repository-ish class for InfluxDB interactions
@Repository
public class InfluxDBRepository {

    private final InfluxDBClient influxDBClient;
    private final String influxOrgID;

    public InfluxDBRepository(InfluxDBClient influxDBClient, @Value("${metrics.influxdb.organization-id}") String influxOrgID) {
        this.influxDBClient = influxDBClient;
        this.influxOrgID = influxOrgID;
    }

    public Bucket createBucket(String name, int retentionSeconds) {
        return influxDBClient.getBucketsApi().createBucket(name, new BucketRetentionRules().everySeconds(retentionSeconds), influxOrgID);
    }

    public void deleteBucket(String bucketID) {
        influxDBClient.getBucketsApi().deleteBucket(bucketID);
    }

    public void writePoint(Point point, String bucketId) {
        influxDBClient.getWriteApiBlocking().writePoint(bucketId, influxOrgID, point);
    }



}
