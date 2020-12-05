package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enabled")
    private boolean enabled;
//    private String role;
    //@ManyToOne(cascade = CascadeType.PERSIST)
    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    public User(){}
    public User(String email, boolean enabled, Role role) {
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }
    public User(String email, boolean enabled, String roleName) {
        this.email = email;
        this.enabled = enabled;
        this.role = new Role("roleName");
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(String roleName) {
        this.role = new Role();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                enabled == user.enabled &&
                email.equals(user.email) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, enabled, role);
    }
}
