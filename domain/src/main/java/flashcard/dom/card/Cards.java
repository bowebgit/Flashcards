package flashcard.dom.card;

import java.util.List;
import java.util.Set;

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
import org.apache.isis.applib.services.homepage.HomePageProviderService;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(nature = NatureOfService.VIEW_MENU_ONLY, objectType = "simple.CardsMenu", repositoryFor = Card.class)
@DomainObjectLayout
public class Cards {

	@Inject RepositoryService repositoryService;
	@Inject CardRepository cardRepository;
	@Inject HomePageProviderService homePageService;
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "1")
	public List<Card> listAllCards(){
		return repositoryService.allInstances(Card.class);
	}
	
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "2")
	public Set<Card> findCardsByName(@ParameterLayout(named = "Card") String card) {
		return cardRepository.findCardsByName(card);
	}
	
	
	
}
