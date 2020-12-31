package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="authorities")
public class Authorities {
    @Id
    @GeneratedValue
    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("authority")
    private String authority;
    private String username;
    //@OneToMany(mappedBy = "role", cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    //private List<User> users;
    public Authorities(){}
    public Authorities(String authority, String username) {
        this.username = username;
        this.authority = authority;
    }


    public int getRole_id() {
        return roleId;
    }

    public void setRole_id(int role_id) {
        this.roleId = role_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authorities that = (Authorities) o;
        return roleId == that.roleId &&
                Objects.equals(authority, that.authority) &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, authority, username);
    }
}
