package flashcard.dom.set;

import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.collect.Sets;

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
