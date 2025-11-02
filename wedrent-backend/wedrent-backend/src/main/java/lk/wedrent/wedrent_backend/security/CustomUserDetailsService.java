// java
package lk.wedrent.wedrent_backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

/**
 * Minimal UserDetailsService to satisfy autowiring for JwtAuthenticationFilter.
 * Replace with a real implementation that loads users from the database later.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Development stub: returns an in-memory admin user for username "admin"
        if ("admin".equals(username)) {
            return User.withUsername("admin")
                    .password("{noop}adminpass")
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
