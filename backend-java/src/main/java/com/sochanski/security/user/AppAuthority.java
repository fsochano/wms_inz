package com.sochanski.security.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name="app_authority")
@IdClass(AppAuthorityId.class)
public class AppAuthority implements GrantedAuthority {

    @Id
    @Column(nullable = false, updatable = false)
    private String authority;
    @Id
    @JoinColumn(table = "app_user", name ="username", nullable = false, updatable = false, insertable = false)
    private String username;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false, updatable = false, insertable = false)
    private AppUser user;

    public AppAuthority(String authority, AppUser user) {
        this.authority = authority;
        this.username = user.getUsername();
        this.user = user;
    }

}
