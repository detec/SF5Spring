
package org.openbox.sf5.wsmodel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for carrierFrequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="carrierFrequency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LOWER"/>
 *     &lt;enumeration value="TOP"/>
 *     &lt;enumeration value="C_RANGE"/>
 *     &lt;enumeration value="TOP_PIE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "carrierFrequency")
@XmlEnum
public enum CarrierFrequency {

    LOWER,
    TOP,
    C_RANGE,
    TOP_PIE;

    public String value() {
        return name();
    }

    public static CarrierFrequency fromValue(String v) {
        return valueOf(v);
    }

}
