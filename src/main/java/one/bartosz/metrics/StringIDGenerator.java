package one.bartosz.metrics;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class StringIDGenerator implements IdentifierGenerator {

    public static final String generatorName = "stringIDGenerator";
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
