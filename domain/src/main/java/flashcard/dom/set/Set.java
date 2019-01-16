package flashcard.dom.set;

import java.util.Arrays;
import java.util.List;
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
import flashcard.dom.card.Rank;
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
	
	@Persistent(mappedBy = "set", dependentElement = "false")
	@Collection
	@CollectionLayout(defaultView = "table", hidden=Where.ALL_TABLES, named = "Cards", paged = 30)
	public SortedSet<Card> getCards() {
		return cards;
	}

	public void setCards(SortedSet<Card> cards) {
		this.cards = cards;
	}
	
	@NotPersistent
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Size")
	@MemberOrder(sequence = "3")
	public String getNumberOfCards() {
		return String.valueOf(getCards().size());
	}

	
	@Action 
	public Set addSet(
			@Parameter(maxLength=40) @ParameterLayout(named = "Set Name") String name,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Description", multiLine = 3) String description
			) {
		Set set = repositoryService.instantiate(Set.class);
		set.setName(name);
		set.setDescription(description);
		return repositoryService.persistAndFlush(set);
		
	}
	
	@Action
	public Set editSet(
			@Parameter(maxLength=40) @ParameterLayout(named = "Set Name") String name,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Description", multiLine = 3) String description
			) {
		this.setName(name);
		this.setDescription(description);
		return repositoryService.persist(this);
	}
	
	public String default0EditSet() {
		return this.name;
		}
	
	public String default1EditSet() {
		return this.description;
		}
	
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public void deleteSet() {
		SortedSet<Card> cardsToDelete = this.getCards();
		for(Card card : cardsToDelete) {
			repositoryService.remove(card);
		}
		repositoryService.removeAndFlush(this);
	}
	
	@Action
	public Set addCard(
			@Parameter(maxLength=400) @ParameterLayout(named = "Card") String name,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Rank") Rank rank,
			//@Parameter(optionality = Optionality.OPTIONAL, maxLength=40) @ParameterLayout(named = "Function") String function,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Definition", multiLine = 5) String definition 
			) {
		Card newCard = new Card(name, definition);
		newCard.setSet(this);
		newCard.setRank(rank);
		repositoryService.persistAndFlush(newCard);
		return this;
	}

	public List<Rank> choices1AddCard() {
		return Arrays.asList(Rank.values());
	}
	
	// Move Card from this Set to another Set, return this Set. Can multi select?
	@Action
	public Set moveCard(
			@ParameterLayout(named = "Card") Card card,
			@ParameterLayout(named = "Set") Set set) {
		card.setSet(set);
		return this;
	}
	
	public SortedSet<Card> choices0MoveCard() {
		return this.getCards();
	}
	
	public List<Set> choices1MoveCard(){
		return repositoryService.allInstances(Set.class);
	}
	
	@Action
	public Set deleteCard(Card card) {
		repositoryService.remove(card);
		return this;
	}
	
	public SortedSet<Card> choices0DeleteCard(){
		return this.getCards();
	}
	
	@NotPersistent
	@Property(hidden = Where.ALL_TABLES)
	@JsonIgnore
	public Object getLevelN() {
		return this;
	}

	@Override
	public int compareTo(Set otherSet) {
		return this.getName().compareTo(otherSet.getName());
	}
	
	
}
