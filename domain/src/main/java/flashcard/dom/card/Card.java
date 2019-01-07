package flashcard.dom.card;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flashcard.dom.Breadcrumb;
import flashcard.dom.set.Set;
import lombok.RequiredArgsConstructor;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(objectType = "simple.Card")
@DomainObjectLayout()
@RequiredArgsConstructor
public class Card extends Breadcrumb implements Comparable<Card>{

	private Set set;
	private String name;
	private String function;
	private String definition;
	private String sentences;
	
	public Card(String name, String function, String definition) {
		this.name = name;
		this.function = function;
		this.definition = definition;
	}
	
	
	
	@Title
	@Column(sqlType = "VARCHAR", length = 40, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Card")
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
	
	@Column(sqlType = "VARCHAR", length = 40, allowsNull = "true")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Function")
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "true")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Definition", multiLine = 3)
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "true")
	@Property(hidden = Where.ALL_TABLES)
	@PropertyLayout(named = "Sentences", multiLine = 10)
	public String getSentences() {
		return sentences;
	}

	public void setSentences(String sentences) {
		this.sentences = sentences;
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
