package flashcard.dom.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
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
import flashcard.dom.set.Set;
import lombok.RequiredArgsConstructor;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@Queries({
	@Query(name="findCardsByName", language="JDOQL", 
			value = "SELECT FROM flashcard.dom.card.Card WHERE name.indexOf(:name) >= 0")
})
@DomainObject(objectType = "simple.Card")
@DomainObjectLayout()
@RequiredArgsConstructor
public class Card extends Breadcrumb implements Comparable<Card>{

	@Inject RepositoryService repositoryService;
	
	private Set set;
	private Rank rank;
	private String name;
	//private String function;
	private String definition;
	private String sentences;
	public static java.util.Set<Card> cards = new LinkedHashSet<Card>();
	
	
	public Card(String name, String definition) {
		this.name = name;
		//this.function = function;
		this.definition = definition;
	}

	@Column(name = "twiddle")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Rank")
	@MemberOrder(sequence = "2")
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
	@Title
	@Column(sqlType = "VARCHAR", length = 400, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Card", cssClass = "card-name-field")
	@MemberOrder(sequence = "1")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(hidden = Where.EVERYWHERE)
	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}
	
//	@Column(sqlType = "VARCHAR", length = 40, allowsNull = "true")
//	@Property(hidden = Where.NOWHERE)
//	@PropertyLayout(named = "Function")
//	@MemberOrder(sequence = "2")
//	public String getFunction() {
//		return function;
//	}
//
//	public void setFunction(String function) {
//		this.function = function;
//	}
	
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "true")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Definition", multiLine = 4)
	@MemberOrder(sequence = "3")
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "true")
	@Property(hidden = Where.ALL_TABLES, editing = Editing.ENABLED)
	@PropertyLayout(named = "Sentences", multiLine = 8)
	public String getSentences() {
		return sentences;
	}

	public void setSentences(String sentences) {
		this.sentences = sentences;
	}
	
	@Action
	public Card editCard(
			@Parameter(maxLength = 400) @ParameterLayout(named = "Card") String name,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Rank") Rank rank, 
			//@Parameter(optionality = Optionality.OPTIONAL, maxLength=40) @ParameterLayout(named = "Function") String function,
			@Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named = "Definition", multiLine = 5) String definition
			) {
		this.setName(name);
		this.setRank(rank);
		this.setDefinition(definition);
		return repositoryService.persist(this);
	}
	
	public String default0EditCard() {
		return this.getName();
	}
	
	public List<Rank> choices1EditCard(){
		return Arrays.asList(Rank.values());
	}
	
	public Rank default1EditCard() {
		return this.rank;
	}
	
	
	public String default2EditCard() {
		return this.getDefinition();
	}
	
	@Action
	public Set moveCard(@ParameterLayout(named = "Set") Set set) {
		Set currentParent = this.getSet();
		this.setSet(set);
		return currentParent;
	}
	
	public List<Set> choices0MoveCard(){
		return repositoryService.allInstances(Set.class);
	}
	
	@Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE)
	public Set deleteCard() {
		Set parentSet = this.getSet();
		repositoryService.remove(this);
		return parentSet;
	}
	
	@Action
	public Card randomCard() {
		java.util.Set<Card> all =  this.getSet().getCards();
		int item = new java.util.Random().nextInt(all.size());
		int i = 0;
		for(Card c : all) {
			if (i == item) {
				return c;
			} else {
				i++;
			}
		}
		return null;
		
	}
	
	
	@Override
	public int compareTo(Card otherCard) {
		return this.getName().compareTo(otherCard.getName());
	}

	@NotPersistent
	@Property(hidden = Where.ALL_TABLES)
	@JsonIgnore
	public Object getLevel1() {
		return getSet();
	}
	
	@NotPersistent
	@Property(hidden = Where.ALL_TABLES)
	@JsonIgnore
	//@Override
	public Object getLevelN() {
		return this;
	}

}
