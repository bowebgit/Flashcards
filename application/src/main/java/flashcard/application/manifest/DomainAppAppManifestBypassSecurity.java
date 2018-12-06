package flashcard.application.manifest;

/**
 * Bypasses security, meaning any user/password combination can be used to login.
 */
public class DomainAppAppManifestBypassSecurity extends DomainAppAppManifest {

    @Override protected String overrideAuthMechanism() {
        return "bypass";
    }
}
