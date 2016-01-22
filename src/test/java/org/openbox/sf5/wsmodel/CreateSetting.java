
package org.openbox.sf5.wsmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createSetting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createSetting">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inputSetting" type="{http://wsmodel.sf5.openbox.org/}settings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createSetting", propOrder = {
    "inputSetting"
})
public class CreateSetting {

    protected Settings inputSetting;

    /**
     * Gets the value of the inputSetting property.
     * 
     * @return
     *     possible object is
     *     {@link Settings }
     *     
     */
    public Settings getInputSetting() {
        return inputSetting;
    }

    /**
     * Sets the value of the inputSetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Settings }
     *     
     */
    public void setInputSetting(Settings value) {
        this.inputSetting = value;
    }

}
