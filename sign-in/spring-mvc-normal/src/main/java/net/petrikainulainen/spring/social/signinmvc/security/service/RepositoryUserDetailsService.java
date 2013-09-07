package net.petrikainulainen.spring.social.signinmvc.security.service;

import net.petrikainulainen.spring.social.signinmvc.security.dto.ExampleUserDetails;
import net.petrikainulainen.spring.social.signinmvc.user.model.User;
import net.petrikainulainen.spring.social.signinmvc.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Petri Kainulainen
 */
public class RepositoryUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserDetailsService.class);

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by username: {}", username);

        User user = repository.findByEmail(username);
        LOGGER.debug("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        ExampleUserDetails principal = ExampleUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .username(user.getEmail())
                .build();

        LOGGER.debug("Returning user details: {}", principal);

        return principal;
    }
}