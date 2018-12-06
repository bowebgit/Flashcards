package flashcard.application.manifest;

import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import flashcard.dom.fixture.SimpleObject_persona;

/**
 * Run the app but setting up any fixtures.
 */
public class DomainAppAppManifestWithFixtures extends DomainAppAppManifest {

    @Override
    protected void overrideFixtures(final List<Class<? extends FixtureScript>> fixtureScripts) {
        fixtureScripts.add(SimpleObject_persona.PersistAll.class);
    }

}
