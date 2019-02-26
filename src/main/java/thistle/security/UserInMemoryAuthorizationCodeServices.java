package thistle.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserInMemoryAuthorizationCodeServices extends InMemoryAuthorizationCodeServices {

    public String createAuthorizationCode(String user) {
        OAuth2Request request = new OAuth2Request(null, "thistle", null, true,
                null, null, null, null, null);
        return createAuthorizationCode(new OAuth2Authentication(request, new UserAuthentication(user)));
    }

    @RequiredArgsConstructor
    private static class UserAuthentication implements Authentication {

        private final String user;

        @Override
        public String getName() {
            return user;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
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
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        }
    }
}
