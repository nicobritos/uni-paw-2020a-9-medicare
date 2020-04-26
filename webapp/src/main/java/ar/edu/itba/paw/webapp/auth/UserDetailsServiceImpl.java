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
            throw new UsernameNotFoundException("No existe ningun usuario con el mail: " + username);
        }

        Collection<? extends GrantedAuthority> authorities;
        if (!user.get().getStaffs().isEmpty()) {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.STAFF.name()));
        } else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(UserRole.PATIENT.name()));
        }

        return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), authorities);
    }
}
