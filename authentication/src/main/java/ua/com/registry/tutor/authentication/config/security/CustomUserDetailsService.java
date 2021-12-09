package ua.com.registry.tutor.authentication.config.security;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.registry.tutor.authentication.domain.entity.User;
import ua.com.registry.tutor.authentication.service.UserService;

@RequiredArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService.findByUserName(username)
        .filter(User::isEnabled)
        .map(u -> new org.springframework.security.core.userdetails.User(
            u.getUsername(),
            u.getPassword(),
            u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getValue()))
                .collect(Collectors.toSet())
        ))
        .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
  }
}
