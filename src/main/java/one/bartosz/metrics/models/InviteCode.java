package one.bartosz.metrics.models;

import jakarta.persistence.*;
import one.bartosz.metrics.StringIDGenerator;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "invite_codes")
public class InviteCode {

    @Id
    @GeneratedValue(generator = StringIDGenerator.generatorName)
    @GenericGenerator(name = StringIDGenerator.generatorName, type = StringIDGenerator.class)
    private String id;
    private long expiresOn;
    @ManyToOne
    private User user;

    public String getId() {
        return id;
    }

    public InviteCode setId(String id) {
        this.id = id;
        return this;
    }

    public long getExpiresOn() {
        return expiresOn;
    }

    public InviteCode setExpiresOn(long expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public User getUser() {
        return user;
    }

    public InviteCode setUser(User user) {
        this.user = user;
        return this;
    }
}
