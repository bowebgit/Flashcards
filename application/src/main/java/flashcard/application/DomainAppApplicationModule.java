package flashcard.application;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Sets;

import flashcard.dom.SimpleModule;

import org.apache.isis.applib.Module;
import org.apache.isis.applib.ModuleAbstract;

@XmlRootElement(name = "module")
public class DomainAppApplicationModule extends ModuleAbstract {

    @Override
    public Set<Module> getDependencies() {
        return Sets.<Module>newHashSet(new SimpleModule());
    }

}
