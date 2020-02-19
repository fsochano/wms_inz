package com.sochanski.security;

import com.sochanski.ApiUtils;
import com.sochanski.security.user.AppUser;
import com.sochanski.security.user.AppUserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@PreAuthorize(value = "hasAuthority('SETTINGS')")
@RestController
@RequestMapping(path = ApiUtils.BASE_API_PATH + "/users")
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
        params.setPassword(passwordEncoder.encode(params.getPassword()));
        userDetailsManager.createUser(new AppUser(params));
        var userDetails = userDetailsManager.loadUserByUsername(params.getUsername());
        return new UserDTO(userDetails);
    }

    @DeleteMapping(path = "/{username}")
    public void removeUser(@PathVariable String username) {
        var userDetails = userDetailsManager.loadUserByUsername(username);
        userDetailsManager.deleteUser(userDetails.getUsername());
    }

    @PostMapping(path = "/{username}/password")
    public UserDTO changePassword(@PathVariable String username, @RequestBody String password) {
        var userDetails = userDetailsManager.loadUserByUsername(username);
        userDetailsManager.updatePassword(userDetails, password);
        return new UserDTO(userDetails);
    }

    @PostMapping(path = "/{username}/authorities/{authority}")
    public UserDTO addAuthority(@PathVariable String username, @PathVariable String authority) {
        var userDetails = userDetailsManager.loadUserByUsername(username);
        var user = userDetailsManager.addAuthority(userDetails, authority);
        return new UserDTO(user);
    }

    @DeleteMapping(path = "/{username}/authorities/{authority}")
    public UserDTO removeAuthority(@PathVariable String username, @PathVariable String authority) {
        var userDetails = userDetailsManager.loadUserByUsername(username);
        var user = userDetailsManager.removeAuthority(userDetails, authority);
        return new UserDTO(user);
    }

}
