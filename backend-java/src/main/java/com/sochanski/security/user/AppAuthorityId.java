package com.sochanski.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AppAuthorityId implements Serializable {

    @JoinColumn(table = "app_user", name ="username", nullable = false, updatable = false, insertable = false)
    private String username;

    @Column(nullable = false, updatable = false)
    private String authority;

}
