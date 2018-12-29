package flashcard.dom.subject;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;

import flashcard.dom.simpleobj.SimpleObject;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.SubjectsMenu", repositoryFor = Subject.class)
@DomainObjectLayout
public class Subjects {
	
	@Inject RepositoryService repositoryService;
	
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Subject> listAll() {
        return repositoryService.allInstances(Subject.class);
    }

    
    @Action
    @MemberOrder(sequence="2")
    public Subject create(@ParameterLayout(named = "name") final String name) {
    	return repositoryService.persist(new Subject(name));
    }
    

}
