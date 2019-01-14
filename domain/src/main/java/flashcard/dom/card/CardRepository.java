package flashcard.dom.card;

import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.collect.Sets;

@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = Card.class)
public class CardRepository {

	@Inject RepositoryService repositoryService;
	
	public Set<Card> findCardsByName(String name){
		return Sets.newTreeSet(repositoryService.allMatches(
				new QueryDefault<>(Card.class, "findCardsByName", "name", name)));
	}
	
}
