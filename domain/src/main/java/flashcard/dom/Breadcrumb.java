package flashcard.dom;

import javax.inject.Inject;
import javax.jdo.annotations.NotPersistent;

import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.homepage.HomePageProviderService;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Breadcrumb {

	@Inject HomePageProviderService homePageService;
	
	@Property(hidden = Where.ALL_TABLES)
	@NotPersistent
	@JsonIgnore
	public Object getLevel0() {
		return homePageService.homePage();
	}
	
//	@Property(hidden = Where.ALL_TABLES)
//	@NotPersistent
//	@JsonIgnore
//	public abstract Object getLevelN();
}
