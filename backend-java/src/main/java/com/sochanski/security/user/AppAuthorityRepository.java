package com.sochanski.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppAuthorityRepository extends JpaRepository<AppAuthority, AppAuthorityId> {
}
