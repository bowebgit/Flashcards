package flashcard.dom.subject;

import java.util.SortedSet;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.collect.Sets;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Subject.class)
public class SubjectRepository {

	@javax.inject.Inject
	private RepositoryService repositoryService;

	public SortedSet<Subject> finalAll()
	{
		return Sets.newTreeSet(repositoryService.allInstances(Subject.class));
	}
}
