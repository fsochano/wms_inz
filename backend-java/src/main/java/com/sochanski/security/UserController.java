package com.sochanski.security;

import com.sochanski.ApiUtils;
import com.sochanski.security.user.AppAuthority;
import com.sochanski.security.user.AppUser;
import com.sochanski.security.user.AppUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@PreAuthorize(value = "hasAuthority('SETTINGS')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH+"/users")
@CrossOrigin
public class UserController {

    private final AppUserRepository userRepository;
    private final AppUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserController(AppUserRepository userRepository, AppUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    @PostMapping
    public UserDTO createUser(@RequestBody @Valid CreateUserParams params) {
        userDetailsManager.createUser(
                User.withUsername(params.getUsername())
                .password(passwordEncoder.encode(params.getPassword()))
                .build());
        UserDetails userDetails = userDetailsManager.loadUserByUsername(params.getUsername());
        return new UserDTO(userDetails);
    }

    @PostMapping(path= "/{username}/authorities")
    public UserDTO addAuthority(@PathVariable String username, @RequestBody String authority) {
        AppUser userDetails = userDetailsManager.loadUserByUsername(username);
        List<AppAuthority> authorities = userDetails.getAuthorities();
        authorities.add(new AppAuthority(authority, userDetails));
        userDetailsManager.updateUser(userDetails);
        return new UserDTO(userDetails);
    }

}
