package thistle.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import thistle.domain.User;

import java.util.Collection;

@RequiredArgsConstructor
public class BearerAuthentication implements Authentication {

    private final User user;

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
    }
}
