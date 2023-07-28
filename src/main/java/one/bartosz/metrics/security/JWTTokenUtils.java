package one.bartosz.metrics.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import one.bartosz.metrics.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JWTTokenUtils {
    //6 hours
    public static long VALIDITY = 6 * 60 * 60 * 1000;
    private final String issuer;
    private final Algorithm algo;
    private final JWTVerifier jwtVerifier;

    public JWTTokenUtils(@Value("${metrics.jwt.secret:random.value}") String secret, @Value("${metrics.jwt.issuer:metrics}") String issuer) {
        this.issuer = issuer;
        this.algo = Algorithm.HMAC512(secret);
        this.jwtVerifier = JWT.require(algo).withIssuer(issuer).build();
    }

    public String generateToken(User user) {
        return JWT.create()
                .withExpiresAt(Instant.ofEpochMilli(System.currentTimeMillis() + VALIDITY)).withIssuedAt(Instant.now()).withSubject(user.getUsername()).withIssuer(issuer)
                .sign(algo);
    }

    public boolean validateToken(String token, User user) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        String tokenSubject = jwt.getSubject();
        Date tokenExpiry = jwt.getExpiresAt();
        return (tokenSubject.equals(user.getUsername()) && !tokenExpiry.before(new Date()) && jwt.getIssuedAtAsInstant().toEpochMilli() > user.getLastUpdated());
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getSubject();
    }
}
