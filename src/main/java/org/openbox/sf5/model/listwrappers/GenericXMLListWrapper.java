package org.openbox.sf5.model.listwrappers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.openbox.sf5.model.Satellites;

// http://stackoverflow.com/questions/14268981/modify-a-class-definitions-annotation-string-parameter-at-runtime
// Changing annotation value at runtime


// http://stackoverflow.com/questions/14057932/javax-xml-bind-jaxbexception-class-nor-any-of-its-super-class-is-known-to-t
@XmlRootElement(name = "class stub")
@XmlSeeAlso(value = {Satellites.class})
public class GenericXMLListWrapper<T> {

	public void setWrappedList(List<T> wrappedList) {
		this.wrappedList = wrappedList;
	}

	private List<T> wrappedList;

	// http://stackoverflow.com/questions/16545868/exception-converting-object-to-xml-using-jaxb
	//@XmlElementWrapper(name = "stub")
	@XmlElement
    public List<T> getWrappedList() {
        if (wrappedList == null) {
        	wrappedList = new ArrayList<>();
        }
        return wrappedList;
    }
}
