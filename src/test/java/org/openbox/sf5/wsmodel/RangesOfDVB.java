
package org.openbox.sf5.wsmodel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rangesOfDVB.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="rangesOfDVB">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="KU"/>
 *     &lt;enumeration value="C"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "rangesOfDVB")
@XmlEnum
public enum RangesOfDVB {

    KU,
    C;

    public String value() {
        return name();
    }

    public static RangesOfDVB fromValue(String v) {
        return valueOf(v);
    }

}
