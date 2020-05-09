package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userService.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        Collection<? extends GrantedAuthority> authorities;
        if (!user.get().getVerified()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + UserRole.UNVERIFIED.name()));
        } else {
            if (this.userService.isStaff(user.get())) {
                authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + UserRole.STAFF.name()));
            } else {
                authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + UserRole.PATIENT.name()));
            }
        }

        return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), authorities);
    }
}
