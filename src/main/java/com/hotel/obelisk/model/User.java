package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    @JsonProperty("userId")
    private long userId;
    @NotNull
    @NotEmpty
    @JsonProperty("username")
    private String username;

    @JsonProperty("enabled")
    private int enabled;
    @NotNull
    @NotEmpty
    private String password;
//    private String role;
    //@ManyToOne(cascade = CascadeType.PERSIST)
    //@ManyToOne
    //@JoinColumn(name="roleId")
   // private Authorities role;
    public User(){}
    public User(String username, String password, int enabled){
        this.password = password;
        this.username = username;
        this.enabled = enabled;

    }
    public User(String username, String password, int enabled, Authorities authorities) {
        this.username = username;
        this.enabled = enabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }



    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                enabled == user.enabled &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, enabled, password);
    }
}
