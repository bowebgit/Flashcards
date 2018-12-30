package flashcard.dom.subject;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.repository.RepositoryService;

import flashcard.dom.set.Set;
import lombok.RequiredArgsConstructor;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(objectType = "simple.Subject")
@DomainObjectLayout()
@RequiredArgsConstructor
public class Subject implements Comparable<Subject> {

	@Inject RepositoryService repositoryService;
	
	private String subjectName;
	private SortedSet<Set> sets = new TreeSet<Set>();
	
	
	public Subject(String subjectName) {
		this.subjectName = subjectName;
	}
	
	@Title
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Subject Name")
	public String getSubjectName() {
		return subjectName;
	}
	
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Persistent(mappedBy = "subject", dependentElement = "false")
	@Collection
	@CollectionLayout(defaultView = "table", named = "Sets", paged = 10)
	public SortedSet<Set> getSets() {
		return sets;
	}

	public void setSets(SortedSet<Set> sets) {
		this.sets = sets;
	}

	
	@Action
	public Set addSet(@ParameterLayout(named = "Name") String name) {
		Set set = repositoryService.instantiate(Set.class);
		set.setSetName(name);
		set.setSubject(this);
		repositoryService.persist(set);
		return set;
	}
	
	
	
	
	
	
	@Override
	public int compareTo(Subject otherSubject) {
		return this.getSubjectName().compareTo(otherSubject.getSubjectName());
	}
	
	
}
