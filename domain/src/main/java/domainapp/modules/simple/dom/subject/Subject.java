package domainapp.modules.simple.dom.subject;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

@PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "subject")
@DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(objectType = "Subject")
public class Subject implements Comparable<Subject> {

	
	private String subjectName;
	//private Set<String> strings = new TreeSet<>();
	
	@Column(sqlType = "VARCHAR", length = 8000, allowsNull = "false")
	@Property(hidden = Where.NOWHERE)
	@PropertyLayout(named = "Subject Name")
	public String getSubjectName() {
		return subjectName;
	}
	
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	
	@Override
	public int compareTo(Subject otherSubject) {
		return this.getSubjectName().compareTo(otherSubject.getSubjectName());
	}
	
	
}
