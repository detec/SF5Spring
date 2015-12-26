package org.openbox.sf5.model.listwrappers;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.openbox.sf5.model.Satellites;

@XmlRootElement(name = "satellites")
public class SatellitesListWrapper {

    private List<Satellites> satellites;

    @XmlElement
    public List<Satellites> getVetList() {
        if (satellites == null) {
        	satellites = new ArrayList<>();
        }
        return satellites;
    }

}
