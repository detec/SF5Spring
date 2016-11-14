
package org.openbox.sf5.wsmodel;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "CheckVerifyFault", targetNamespace = "http://wsmodel.sf5.openbox.org/")
public class WSException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private CheckVerifyFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public WSException(String message, CheckVerifyFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public WSException(String message, CheckVerifyFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: org.openbox.sf5.wsmodel.CheckVerifyFault
     */
    public CheckVerifyFault getFaultInfo() {
        return faultInfo;
    }

}
