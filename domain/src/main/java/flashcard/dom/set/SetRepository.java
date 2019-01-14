package flashcard.dom.set;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Set.class)
public class SetRepository {

//	@javax.inject.Inject
//	private RepositoryService repositoryService;
//
//	public SortedSet<Set> finalAll()
//	{
//		return Sets.newTreeSet(repositoryService.allInstances(Set.class));
//	}
}
