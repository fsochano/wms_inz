package com.sochanski.security;

import com.sochanski.security.user.AppAuthority;
import com.sochanski.security.user.AppAuthorityRepository;
import com.sochanski.security.user.AppUser;
import com.sochanski.security.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class AppUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    private final AppUserRepository userRepository;
    private final AppAuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserDetailsManager(AppUserRepository userRepository, AppAuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void createUser(UserDetails user) {
        AppUser appUser = new AppUser(user);
        List<AppAuthority> authorities = appUser.getAuthorities();
        appUser.setAuthorities(emptyList());
        userRepository.save(appUser);
        authorityRepository.saveAll(authorities);
    }

    @Override
    public void updateUser(UserDetails user) {
        AppUser appUser = new AppUser(user);
        List<AppAuthority> authorities = appUser.getAuthorities();
        appUser.setAuthorities(emptyList());
        userRepository.save(appUser);
        authorityRepository.saveAll(authorities);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext()
                .getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException(
                    "Can't change password as no Authentication object found in context "
                            + "for current user.");
        }

        String username = currentUser.getName();
        UserDetails userDetails = loadUserByUsername(username);

        if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect old password");
        }

        updatePassword(userDetails, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public AppUser updatePassword(UserDetails user, String newPassword) {
        AppUser appUser = new AppUser(user);
        appUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(appUser);
    }

}
