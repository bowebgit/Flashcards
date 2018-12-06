package flashcard.dom.subject;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainServiceLayout.MenuBar;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "subject.SubjectMenu",repositoryFor = Subject.class)
@DomainServiceLayout(named = "Subject", menuBar = MenuBar.PRIMARY, menuOrder = "2")
public class SubjectMenu {
	
	@Inject
	private Subjects subjects;

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(named = "List Subjects")
	@MemberOrder(sequence = "1")
	public Subjects allSubjects() {
		return subjects;
	}
}
