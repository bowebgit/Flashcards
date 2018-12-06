package flashcard.dom;

import org.apache.isis.applib.AppManifestAbstract2;

/**
 * Used by <code>isis-maven-plugin</code> (build-time validation of the module) and also by module-level integration tests.
 */
public class SimpleModuleManifest extends AppManifestAbstract2 {

    public static final Builder BUILDER = Builder.forModule(new SimpleModule())
            .withConfigurationProperty("isis.persistor.datanucleus.impl.datanucleus.schema.autoCreateAll","true")
            .withConfigurationProperty("isis.persistor.datanucleus.impl.datanucleus.identifier.case","MixedCase");

    public SimpleModuleManifest() {
        super(BUILDER);
    }


}
