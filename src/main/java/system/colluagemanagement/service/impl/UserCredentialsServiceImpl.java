package system.colluagemanagement.service.impl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import system.colluagemanagement.model.User;
import system.colluagemanagement.model.UserRole;
import system.colluagemanagement.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCredentialsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;
    public UserCredentialsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username); // username is email
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(),mapRolesToAuthorities(Collections.singleton(user.get().getUserRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

}
