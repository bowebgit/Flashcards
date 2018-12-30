package flashcard.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.services.i18n.TranslatableString;

import flashcard.dom.simpleobj.SimpleObject;
import flashcard.dom.simpleobj.SimpleObjects;
import flashcard.dom.subject.Subject;
import flashcard.dom.subject.Subjects;

@DomainObject(nature = Nature.VIEW_MODEL, objectType = "flashcard.application.services.homepage.HomePageViewModel")
public class HomePageViewModel {

    @Inject Subjects subjects;
    //@Inject SimpleObjects simpleObjects;
	
    public String title() {
    	return "Flashcards";
       // return TranslatableString.tr("{num} objects", "num", getObjects().size());
    }

//    public List<SimpleObject> getObjects() {
//        return simpleObjects.listAll();
//    }
    
    public List<Subject> getSubjects() {
    	return subjects.listAll();
    }
    
}
