package com.sochanski.security.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_authority")
@EqualsAndHashCode(of = { "id" })
public class AppAuthority implements GrantedAuthority {

    @EmbeddedId
    private AppAuthorityId id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false, updatable = false, insertable = false)
    private AppUser user;

    public AppAuthority(String authority, AppUser user) {
        this.id = new AppAuthorityId(user.getUsername(), authority);
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return getId().getAuthority();
    }
}
