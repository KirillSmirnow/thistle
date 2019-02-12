package thistle.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Component;
import thistle.domain.User;
import thistle.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class BearerAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getPrincipal().toString();
        User user = userRepository.findByAccessToken(token)
                .orElseThrow(() -> new InvalidTokenException(":-("));
        return new BearerAuthentication(user);
    }
}
