package one.bartosz.metrics.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    @JsonIgnore
    private String password;
    private boolean enabled;
    private long lastUpdated;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<InviteCode> inviteCodes;

    public UUID getId() {
        return id;
    }

    public User setId(UUID id) {
        this.id = id;
        return this;
    }
    @Override
    public String getUsername() {
        return username;
    }



    public User setUsername(String username) {
        this.username = username;
        return this;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    //Spring's bullshit I've never used
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //end of spring's stuff

    public long getLastUpdated() {
        return lastUpdated;
    }

    public User setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public Set<InviteCode> getInviteCodes() {
        return inviteCodes;
    }

    public User setInviteCodes(Set<InviteCode> inviteCodes) {
        this.inviteCodes = inviteCodes;
        return this;
    }
}
