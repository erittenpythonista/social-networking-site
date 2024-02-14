package com.eritten.backend.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    // Relationships
    @OneToMany(mappedBy = "user")
    private List<Blacklist> blacklist;

    @ManyToMany
    @JoinTable(name = "user_contacts", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<User> contacts;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return authorities/roles assigned to the user (if needed)
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Add logic if account expiration is needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Add logic if account locking is needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Add logic if credentials expiration is needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Add logic if user enabling/disabling is needed
    }
}
