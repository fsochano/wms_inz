package com.sochanski.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sochanski.security.CreateUserParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser implements UserDetails {

    @Id
    @Column(length = 50, unique = true, updatable = false, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 100)
    private String password;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<AppAuthority> authorities = emptySet();

    public AppUser(UserDetails user) {
        username = user.getUsername();
        password = user.getPassword();
        isAccountNonExpired = user.isAccountNonExpired();
        isAccountNonLocked = user.isAccountNonLocked();
        isCredentialsNonExpired = user.isCredentialsNonExpired();
        isEnabled = user.isEnabled();
        authorities = user.getAuthorities().stream()
                .map(a -> new AppAuthority(a.getAuthority(), this))
                .collect(Collectors.toSet());
    }

    public AppUser(CreateUserParams params) {
        username = params.getUsername();
        password = params.getPassword();
    }

}
