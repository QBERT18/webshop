package de.webshop.services.impl;

import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Service used for authentication of users.
 */
@Service("userAuthService")
@Transactional
public class UserAuthServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No User found with email" + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(),
                        enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                        getAuthorities());
    }

    public static Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));

        return authorityList;

    }

}
