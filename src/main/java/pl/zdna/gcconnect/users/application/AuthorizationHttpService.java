package pl.zdna.gcconnect.users.application;

public interface AuthorizationHttpService {
    void createNewUser(String username, String password, String inviterUsername);
    void deleteUser(String username);
    void resetPassword(String username);
}
