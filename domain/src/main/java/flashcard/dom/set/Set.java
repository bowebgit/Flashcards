package flashcard.dom.set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
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

import flashcard.dom.subject.Subject;
import lombok.RequiredArgsConstructor;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(objectType = "simple.Set")
@DomainObjectLayout()
@RequiredArgsConstructor
public class Set implements Comparable<Set>{

	private Subject subject;
	private String setName;
	
	
	@Title
	@Column(sqlType = "VARCHAR", length = 80, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Set")
	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	@Column
	@Property(editing = Editing.DISABLED)
	@PropertyLayout(hidden = Where.NOWHERE)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public int compareTo(Set otherSet) {
		return this.getSetName().compareTo(otherSet.getSetName());
	}

}
