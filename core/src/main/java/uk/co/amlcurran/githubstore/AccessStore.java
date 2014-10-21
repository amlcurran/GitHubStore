package uk.co.amlcurran.githubstore;

public interface AccessStore {
    void authenticate(String clientId, String clientSecret, AuthenticationCallback authenticationCallback);
}
