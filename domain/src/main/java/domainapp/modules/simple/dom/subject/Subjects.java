package domainapp.modules.simple.dom.subject;

import java.util.SortedSet;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Subject.class)
@DomainObjectLayout
public class Subjects {
	
	@Inject
	private SubjectRepository subjectRepository;

	
//	@CollectionLayout(defaultView = "table", paged = 25)
//	public SortedSet<Subject> getSubjects()	{
//		
//		return subjectRepository.finalAll();
//	}
	
	

}
