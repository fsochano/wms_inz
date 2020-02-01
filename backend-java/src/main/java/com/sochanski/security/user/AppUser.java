package com.sochanski.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @Column(length = 50, unique = true, updatable = false, nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<AppAuthority> authorities;

    public AppUser(UserDetails user) {
        username = user.getUsername();
        password = user.getPassword();
        isAccountNonExpired = user.isAccountNonExpired();
        isAccountNonLocked = user.isAccountNonLocked();
        isCredentialsNonExpired = user.isCredentialsNonExpired();
        isEnabled = user.isEnabled();
        authorities = user.getAuthorities().stream()
                .map(a -> new AppAuthority(a.getAuthority(), this))
                .collect(Collectors.toList());
    }
}
