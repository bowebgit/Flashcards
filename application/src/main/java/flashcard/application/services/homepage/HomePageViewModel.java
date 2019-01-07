package flashcard.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import flashcard.dom.set.Set;
import flashcard.dom.set.Sets;
import flashcard.dom.simpleobj.SimpleObject;
import flashcard.dom.simpleobj.SimpleObjects;

@DomainObject(nature = Nature.VIEW_MODEL, objectType = "flashcard.application.services.homepage.HomePageViewModel")
public class HomePageViewModel {

    @Inject Sets sets;
    @Inject RepositoryService repositoryService;
    //@Inject SimpleObjects simpleObjects;
	
    public String title() {
    	return "Flashcards";
       // return TranslatableString.tr("{num} objects", "num", getObjects().size());
    }

//    public List<SimpleObject> getObjects() {
//        return simpleObjects.listAll();
//    }
    
    @Collection
    @CollectionLayout(defaultView = "table", named = "Sets")
    public List<Set> getSets() {
    	return repositoryService.allInstances(Set.class);
    }
    
    @Action
    @ActionLayout(named = "New Set")
    public HomePageViewModel addSet(
    		@Parameter(maxLength=40) @ParameterLayout(named = "Set Name") String name,
    		@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Description", multiLine = 2) String description
    		) {
    	repositoryService.persistAndFlush(new Set(name, description));
    	return this;
    }
    
}
