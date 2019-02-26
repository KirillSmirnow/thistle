package thistle.security;

import thistle.domain.User;

public class PseudoUser extends User {

    private PseudoUser(int id) {
        setId(id);
    }

    public static PseudoUser of(String user) {
        return new PseudoUser(Integer.valueOf(user));
    }

    @Override
    public void update(String firstName, String lastName) {
        throw new IllegalStateException();
    }

    @Override
    public String updateAccessToken() {
        throw new IllegalStateException();
    }

    @Override
    public String getAccessToken() {
        throw new IllegalStateException();
    }

    @Override
    public String getLastName() {
        throw new IllegalStateException();
    }

    @Override
    public String getFirstName() {
        throw new IllegalStateException();
    }
}
