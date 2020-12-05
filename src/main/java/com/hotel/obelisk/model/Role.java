package com.hotel.obelisk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hotel.obelisk.model.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue
    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("role")
    private String role;
    @OneToMany(mappedBy = "role", cascade={CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<User> users;
    public Role() {
    }
    public Role(String role) {
        this.role = role;
    }


    public int getRole_id() {
        return roleId;
    }

    public void setRole_id(int role_id) {
        this.roleId = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return roleId == role1.roleId &&
                role.equals(role1.role) &&
                Objects.equals(users, role1.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role, users);
    }
}
