//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11-b140731.1112
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.12.30 at 05:14:45 PM EET
//

package org.openbox.sf5.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="satid" maxOccurs="8" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tp" maxOccurs="4" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="LnbFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Freq" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Symbol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Polar" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "satid" })
@XmlRootElement(name = "sat")
public class Sat {

	protected List<Sat.Satid> satid;

	/**
	 * Gets the value of the satid property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the satid property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getSatid().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Sat.Satid
	 * }
	 *
	 *
	 */
	public List<Sat.Satid> getSatid() {
		if (satid == null) {
			satid = new ArrayList<Sat.Satid>();
		}
		return satid;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 *
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="tp" maxOccurs="4" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="LnbFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                   &lt;element name="Freq" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                   &lt;element name="Symbol" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                   &lt;element name="Polar" type="{http://www.w3.org/2001/XMLSchema}int"/>
	 *                 &lt;/sequence>
	 *                 &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 *
	 *
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "tp" })
	public static class Satid {

		protected List<Sat.Satid.Tp> tp;

		@XmlValue
		protected String value;

		@XmlAttribute(name = "index", required = true)
		protected String index;

		/**
		 * Gets the value of the value property.
		 *
		 * @return possible object is {@link String }
		 *
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the value property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 *
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Gets the value of the tp property.
		 *
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the tp property.
		 *
		 * <p>
		 * For example, to add a new item, do as follows:
		 *
		 * <pre>
		 * getTp().add(newItem);
		 * </pre>
		 *
		 *
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Sat.Satid.Tp }
		 *
		 *
		 */
		public List<Sat.Satid.Tp> getTp() {
			if (tp == null) {
				tp = new ArrayList<Sat.Satid.Tp>();
			}
			return tp;
		}

		/**
		 * Gets the value of the index property.
		 *
		 * @return possible object is {@link String }
		 *
		 */
		public String getIndex() {
			return index;
		}

		/**
		 * Sets the value of the index property.
		 *
		 * @param value
		 *            allowed object is {@link String }
		 *
		 */
		public void setIndex(String value) {
			index = value;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 *
		 * <p>
		 * The following schema fragment specifies the expected content
		 * contained within this class.
		 *
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="LnbFreq" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *         &lt;element name="Freq" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *         &lt;element name="Symbol" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *         &lt;element name="Polar" type="{http://www.w3.org/2001/XMLSchema}int"/>
		 *       &lt;/sequence>
		 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 *
		 *
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "lnbFreq", "freq", "symbol", "polar" })
		public static class Tp {

			@XmlElement(name = "LnbFreq")
			protected String lnbFreq;
			@XmlElement(name = "Freq")
			protected int freq;
			@XmlElement(name = "Symbol")
			protected int symbol;
			@XmlElement(name = "Polar")
			protected int polar;
			@XmlAttribute(name = "index", required = true)
			protected String index;

			/**
			 * Gets the value of the lnbFreq property.
			 *
			 */
			public String getLnbFreq() {
				return lnbFreq;
			}

			/**
			 * Sets the value of the lnbFreq property.
			 *
			 */
			public void setLnbFreq(String value) {
				lnbFreq = value;
			}

			/**
			 * Gets the value of the freq property.
			 *
			 */
			public int getFreq() {
				return freq;
			}

			/**
			 * Sets the value of the freq property.
			 *
			 */
			public void setFreq(int value) {
				freq = value;
			}

			/**
			 * Gets the value of the symbol property.
			 *
			 */
			public int getSymbol() {
				return symbol;
			}

			/**
			 * Sets the value of the symbol property.
			 *
			 */
			public void setSymbol(int value) {
				symbol = value;
			}

			/**
			 * Gets the value of the polar property.
			 *
			 */
			public int getPolar() {
				return polar;
			}

			/**
			 * Sets the value of the polar property.
			 *
			 */
			public void setPolar(int value) {
				polar = value;
			}

			/**
			 * Gets the value of the index property.
			 *
			 * @return possible object is {@link String }
			 *
			 */
			public String getIndex() {
				return index;
			}

			/**
			 * Sets the value of the index property.
			 *
			 * @param value
			 *            allowed object is {@link String }
			 *
			 */
			public void setIndex(String value) {
				index = value;
			}

		}

	}

}
