package flashcard.application.fixture.scenarios;

import javax.inject.Inject;

import org.apache.isis.applib.AppManifest2;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.metamodel.MetaModelService4;

import flashcard.dom.fixture.SimpleObject_persona;

public class DomainAppDemo extends FixtureScript {

    public DomainAppDemo() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {
        AppManifest2 appManifest2 = metaModelService4.getAppManifest2();
        ec.executeChild(this, appManifest2.getTeardownFixture());
        ec.executeChild(this, appManifest2.getRefDataSetupFixture());
        ec.executeChild(this, new SimpleObject_persona.PersistAll());
    }

    @Inject
    MetaModelService4 metaModelService4;
}
