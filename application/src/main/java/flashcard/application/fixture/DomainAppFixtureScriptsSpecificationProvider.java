package flashcard.application.fixture;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import flashcard.application.fixture.scenarios.DomainAppDemo;

/**
 * Specifies where to find fixtures, and other settings.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class DomainAppFixtureScriptsSpecificationProvider implements FixtureScriptsSpecificationProvider {
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification
                .builder(DomainAppFixtureScriptsSpecificationProvider.class)
                .with(FixtureScripts.MultipleExecutionStrategy.EXECUTE)
                .withRunScriptDefault(DomainAppDemo.class)
                .withRunScriptDropDown(FixtureScriptsSpecification.DropDownPolicy.CHOICES)
                .withRecreate(DomainAppDemo.class)
                .build();
    }
}
