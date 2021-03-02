package com.infotech.adb.model.entity;

import javax.persistence.*;


@Entity
@Table(name = "USER_ROLE")
public class UserRole {
    /*
     * Key Fields
     */
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, updatable = false)
    private Long id;

    /*
     * Entity Specific Fields
     */
    @Column(name = "USERNAME")
    private String username;

    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "USERROLE_USER_ID_FK"))
    @ManyToOne (fetch = FetchType.LAZY)
    private User user;

    @Column(name = "ROLE")
    private String role;

    /*
     * Getters/Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
