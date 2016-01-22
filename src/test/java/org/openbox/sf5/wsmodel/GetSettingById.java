
package org.openbox.sf5.wsmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSettingById complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSettingById">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inputSettingId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSettingById", propOrder = {
    "inputSettingId"
})
public class GetSettingById {

    protected long inputSettingId;

    /**
     * Gets the value of the inputSettingId property.
     * 
     */
    public long getInputSettingId() {
        return inputSettingId;
    }

    /**
     * Sets the value of the inputSettingId property.
     * 
     */
    public void setInputSettingId(long value) {
        this.inputSettingId = value;
    }

}
