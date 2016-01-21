
package org.openbox.sf5.wsmodel;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OpenboxSF5Impl", targetNamespace = "http://wsmodel.sf5.openbox.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OpenboxSF5Impl {


    /**
     * 
     * @param inputUser
     * @return
     *     returns long
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createUser", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.CreateUser")
    @ResponseWrapper(localName = "createUserResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.CreateUserResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createUserRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createUserResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createUser/Fault/WSException")
    })
    public long createUser(
        @WebParam(name = "inputUser", targetNamespace = "")
        Users inputUser)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputSettingId
     * @param inputLogin
     * @return
     *     returns org.openbox.sf5.wsmodel.Settings
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSettingById", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingById")
    @ResponseWrapper(localName = "getSettingByIdResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingByIdResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingByIdRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingByIdResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingById/Fault/WSException")
    })
    public Settings getSettingById(
        @WebParam(name = "inputSettingId", targetNamespace = "")
        long inputSettingId,
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputLogin
     * @return
     *     returns org.openbox.sf5.wsmodel.Users
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserByLogin", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetUserByLogin")
    @ResponseWrapper(localName = "getUserByLoginResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetUserByLoginResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getUserByLoginRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getUserByLoginResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getUserByLogin/Fault/WSException")
    })
    public Users getUserByLogin(
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputSatelliteId
     * @return
     *     returns org.openbox.sf5.wsmodel.Satellites
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSatelliteById", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSatelliteById")
    @ResponseWrapper(localName = "getSatelliteByIdResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSatelliteByIdResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatelliteByIdRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatelliteByIdResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatelliteById/Fault/WSException")
    })
    public Satellites getSatelliteById(
        @WebParam(name = "inputSatelliteId", targetNamespace = "")
        long inputSatelliteId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param arg2
     * @param arg0
     * @param inputLogin
     * @return
     *     returns long
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createSetting", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.CreateSetting")
    @ResponseWrapper(localName = "createSettingResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.CreateSettingResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createSettingRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createSettingResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/createSetting/Fault/WSException")
    })
    public long createSetting(
        @WebParam(name = "arg0", targetNamespace = "")
        Settings arg0,
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin,
        @WebParam(name = "arg2", targetNamespace = "")
        UriComponentsBuilder arg2)
        throws WSException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Transponders>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTransponders", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTransponders")
    @ResponseWrapper(localName = "getTranspondersResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTranspondersResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTransponders/Fault/WSException")
    })
    public List<Transponders> getTransponders()
        throws WSException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Satellites>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAllSatellites", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetAllSatellites")
    @ResponseWrapper(localName = "getAllSatellitesResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetAllSatellitesResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getAllSatellitesRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getAllSatellitesResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getAllSatellites/Fault/WSException")
    })
    public List<Satellites> getAllSatellites()
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputLogin
     * @return
     *     returns boolean
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "ifSuchLoginExists", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.IfSuchLoginExists")
    @ResponseWrapper(localName = "ifSuchLoginExistsResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.IfSuchLoginExistsResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/ifSuchLoginExistsRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/ifSuchLoginExistsResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/ifSuchLoginExists/Fault/WSException")
    })
    public boolean ifSuchLoginExists(
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputFieldName
     * @param inputFieldValue
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Transponders>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTranspondersByArbitraryFilter", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTranspondersByArbitraryFilter")
    @ResponseWrapper(localName = "getTranspondersByArbitraryFilterResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTranspondersByArbitraryFilterResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersByArbitraryFilterRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersByArbitraryFilterResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersByArbitraryFilter/Fault/WSException")
    })
    public List<Transponders> getTranspondersByArbitraryFilter(
        @WebParam(name = "inputFieldName", targetNamespace = "")
        String inputFieldName,
        @WebParam(name = "inputFieldValue", targetNamespace = "")
        String inputFieldValue)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputTransponderId
     * @return
     *     returns org.openbox.sf5.wsmodel.Transponders
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTransponderById", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTransponderById")
    @ResponseWrapper(localName = "getTransponderByIdResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTransponderByIdResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTransponderByIdRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTransponderByIdResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTransponderById/Fault/WSException")
    })
    public Transponders getTransponderById(
        @WebParam(name = "inputTransponderId", targetNamespace = "")
        long inputTransponderId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputSatId
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Transponders>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTranspondersBySatelliteId", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTranspondersBySatelliteId")
    @ResponseWrapper(localName = "getTranspondersBySatelliteIdResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetTranspondersBySatelliteIdResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersBySatelliteIdRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersBySatelliteIdResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getTranspondersBySatelliteId/Fault/WSException")
    })
    public List<Transponders> getTranspondersBySatelliteId(
        @WebParam(name = "inputSatId", targetNamespace = "")
        long inputSatId)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputLogin
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Settings>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSettingsByUserLogin", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingsByUserLogin")
    @ResponseWrapper(localName = "getSettingsByUserLoginResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingsByUserLoginResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByUserLoginRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByUserLoginResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByUserLogin/Fault/WSException")
    })
    public List<Settings> getSettingsByUserLogin(
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputLogin
     * @param inputFieldName
     * @param inputFieldValue
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Settings>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSettingsByArbitraryFilter", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingsByArbitraryFilter")
    @ResponseWrapper(localName = "getSettingsByArbitraryFilterResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSettingsByArbitraryFilterResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByArbitraryFilterRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByArbitraryFilterResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSettingsByArbitraryFilter/Fault/WSException")
    })
    public List<Settings> getSettingsByArbitraryFilter(
        @WebParam(name = "inputFieldName", targetNamespace = "")
        String inputFieldName,
        @WebParam(name = "inputFieldValue", targetNamespace = "")
        String inputFieldValue,
        @WebParam(name = "inputLogin", targetNamespace = "")
        String inputLogin)
        throws WSException_Exception
    ;

    /**
     * 
     * @param inputFieldName
     * @param inputFieldValue
     * @return
     *     returns java.util.List<org.openbox.sf5.wsmodel.Satellites>
     * @throws WSException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getSatellitesByArbitraryFilter", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSatellitesByArbitraryFilter")
    @ResponseWrapper(localName = "getSatellitesByArbitraryFilterResponse", targetNamespace = "http://wsmodel.sf5.openbox.org/", className = "org.openbox.sf5.wsmodel.GetSatellitesByArbitraryFilterResponse")
    @Action(input = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatellitesByArbitraryFilterRequest", output = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatellitesByArbitraryFilterResponse", fault = {
        @FaultAction(className = WSException_Exception.class, value = "http://wsmodel.sf5.openbox.org/OpenboxSF5Impl/getSatellitesByArbitraryFilter/Fault/WSException")
    })
    public List<Satellites> getSatellitesByArbitraryFilter(
        @WebParam(name = "inputFieldName", targetNamespace = "")
        String inputFieldName,
        @WebParam(name = "inputFieldValue", targetNamespace = "")
        String inputFieldValue)
        throws WSException_Exception
    ;

}
