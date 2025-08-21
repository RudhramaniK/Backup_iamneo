package com.examly.springapp.security;

import com.examly.springapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String name; // Add this to match the User model's name field
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String email, String password, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getName(), // Use the name field from User
                Collections.singletonList(authority)
        );
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { 
        return authorities.iterator().next().getAuthority().replace("ROLE_", "");
    }
    public String getName() { return name; } // Add getter for name

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override
    public String getPassword() { return password; }
    @Override
    public String getUsername() { return email; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}