package flashcard.application.manifest;

import org.apache.isis.applib.AppManifestAbstract2;

import flashcard.application.DomainAppApplicationModule;

/**
 * Bootstrap the application.
 */
public class DomainAppAppManifest extends AppManifestAbstract2 {

    public static final Builder BUILDER = Builder
            .forModule(new DomainAppApplicationModule())
            .withConfigurationPropertiesFile(DomainAppAppManifest.class,
                    "isis-non-changing.properties",
                    "authentication_shiro.properties",
                    "persistor_datanucleus.properties",
                    "viewer_restfulobjects.properties",
                    "viewer_wicket.properties")
            .withAuthMechanism("shiro");

    public DomainAppAppManifest() {
        super(BUILDER);
    }

}
