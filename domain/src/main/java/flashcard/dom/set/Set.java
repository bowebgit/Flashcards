package flashcard.dom.set;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flashcard.dom.Breadcrumb;
import flashcard.dom.card.Card;
import lombok.RequiredArgsConstructor;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(objectType = "simple.Set")
@DomainObjectLayout()
@RequiredArgsConstructor
public class Set extends Breadcrumb implements Comparable<Set> {

	@Inject RepositoryService repositoryService;
	
	private String name;
	private String description;
	private SortedSet<Card> cards = new TreeSet<Card>();

	public Set(String name) {
		this.name = name;
	}
	
	public Set(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@Title
	@Column(sqlType = "VARCHAR", length = 40, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Set Name")
	@MemberOrder(sequence="1")
	public String getName() {
		return name;
	}
	
	public void setName(String setName) {
		this.name = setName;
	}

	@Persistent(mappedBy = "set", dependentElement = "false")
	@Collection
	@CollectionLayout(defaultView = "table", hidden=Where.ALL_TABLES, named = "Cards", paged = 30)
	public SortedSet<Card> getCards() {
		return cards;
	}

	public void setCards(SortedSet<Card> cards) {
		this.cards = cards;
	}

	@Column(sqlType = "VARCHAR", length = 4000, allowsNull = "true")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Description", multiLine = 3)
	@MemberOrder(sequence="2")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Action 
	public Set addSet(@ParameterLayout(named = "Set Name") String name) {
		Set set = repositoryService.instantiate(Set.class);
		set.setName(name);
		repositoryService.persist(set);
		return set;
	}

	@Action
	public Set addCard(
			@Parameter(maxLength=40) @ParameterLayout(named = "Card") String name,
			@Parameter(optionality = Optionality.OPTIONAL, maxLength=40) @ParameterLayout(named = "Function") String function,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Definition", multiLine = 5) String definition 
			) {
		Card newCard = new Card(name, function, definition);
		newCard.setSet(this);
		repositoryService.persistAndFlush(newCard);
		return this;
	}

	
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void deleteSet() {
		SortedSet<Card> cardsToDelete = this.getCards();
		for(Card card : cardsToDelete) {
			repositoryService.remove(card);
		}
		repositoryService.removeAndFlush(this);
	}

	@NotPersistent
	@Property(hidden = Where.ALL_TABLES)
	@JsonIgnore
	//@Override
	public Object getLevelN() {
		return this;
	}

	@Override
	public int compareTo(Set otherSet) {
		return this.getName().compareTo(otherSet.getName());
	}
	
	
}
