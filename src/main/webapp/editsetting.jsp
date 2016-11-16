<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Openbox SF-5 settings editor - edit setting</title>
<sec:csrfMetaTags/>
</head>
<body>
 
<h2>Openbox SF5 settings editor</h2>
<table border="0">
<tbody>
  <tr>
  <td> <h3>Edit setting</h3></td>
  <td>
  <form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
  </td>
  </tr>
</tbody>
</table>
 
 <c:if test="${not empty viewMsg}" >
	<div style="color:#408080;"> ${viewMsg}</div>
</c:if>
 <c:if test="${not empty viewErrMsg}" >
 <div style="color:Red;"> ${viewErrMsg}</div>
 </c:if>

  <c:url var="saveUrl" value="/editsetting"/>
<form:form modelAttribute="bean" method="POST" action="${saveUrl}">
 <table>
  <tr>
   <td><form:label path="name">Name</form:label></td>
   <td><form:input path="name"/></td>
   <td><form:label path="id">ID</form:label><b><c:out value="${bean.id}" /></b><form:hidden path="id"/></td>
   
   <td><form:label path="lastEntry">Last update date</form:label><form:hidden path="lastEntry"/></td>
   <td><b><fmt:formatDate pattern="dd-MM-yyyy HH:mm" value="${bean.lastEntry}" /></b></td>
  </tr>
   <tr>
   <form:hidden path="user"/>
   <form:hidden path="SettingsObject"/>
   <form:hidden path="SelectionMode"/>
   </tr>
 </table>
  
  <p>Transponders table
  <br>
  <c:if test="${!bean.selectionMode}">
  <input type="submit" value="Move up" name="moveup" />
  <input type="submit" value="Move down" name="movedown" />
  <input type="submit" value="Select transponders..." name="selectTransponders" />
  <input type="submit" value="Remove selected" name="removeSCrows" />
  <input type="submit" value="Select from other setting..." name="selectfromother" />
  <input type="submit" value="Check intersection" name="checkIntersection" />
  
  <input type="submit" value="Export to XML" name="exportToXML" />
  <c:if test="${bean.id != 0}">
  <input type="submit" value="Print" name="print" />
  </c:if>
  </c:if>
   <c:if test="${bean.selectionMode}">
   <input type="submit" value="Select rows..." name="selectRows" />
   <input type="submit" value="Cancel select" name="cancelselectRows" />
   </c:if>
  <table>
   <tr>
   <th>Line no.</th>
   <th>Transponder</th>
   <th>Pol.</th>
   <th>Carrier</th>
   <th>Speed</th>
   <th>Satellite</th>
   <th>FEC</th>
   <th>SatIdx</th>
   <th>TpIdx</th>
   <th>Int-tion #</th>
   <th>Comment</th>
   <th>Actions</th>
   <th>Select</th>
   <th colspan="13"></th>
  </tr>
 <c:forEach items="${bean.dataSettingsConversion}" var="DataSC" varStatus="x">
 
 
 <tr>
 <td><c:out value="${DataSC.lineNumber}" /><form:hidden path="dataSettingsConversion[${x.index}].lineNumber" /></td>
 <td><c:out value="${DataSC.transponder}" /></td>
 <td><c:out value="${DataSC.transponder.polarization}" /></td>
 <td><c:out value="${DataSC.transponder.carrier}" /></td>
 <td><c:out value="${DataSC.transponder.speed}" /></td>
 <td><c:out value="${DataSC.transponder.satellite}" /></td>
 <td><c:out value="${DataSC.transponder.FEC}" /></td>
 <td><c:out value="${DataSC.satindex}" /><form:hidden path="dataSettingsConversion[${x.index}].satindex" /></td>
 <td><c:out value="${DataSC.tpindex}" /><form:hidden path="dataSettingsConversion[${x.index}].tpindex" /></td>
 <td><c:out value="${DataSC.lineOfIntersection}" /><form:hidden path="dataSettingsConversion[${x.index}].lineOfIntersection" /></td>
 <td><form:input path="dataSettingsConversion[${x.index}].note"/></td>
 <td></td>
 <td><form:checkbox path="dataSettingsConversion[${x.index}].checked" /></td>
 <form:hidden path="dataSettingsConversion[${x.index}].parent_id"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.id"/>
 <form:hidden path="dataSettingsConversion[${x.index}].id"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.polarization"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.carrier"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.speed"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.satellite"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.FEC"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.frequency"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.versionOfTheDVB"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.rangeOfDVB"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.satellite.id"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.satellite.name"/>
 </tr>
 </c:forEach> 
  </table>
 <c:if test="${!bean.selectionMode}">

<input type="submit" value="OK" name="save"/>
   
 <input type="submit" value="Cancel" name="cancel"/>
 </c:if>
</form:form>



<c:url var="transpondersUrl" value="/transponders" />
<c:url var="settingsUrl" value="/settings" />
<a href="${settingsUrl}">Settings</a>
<a href="${transpondersUrl}">Transponders</a>


</body>
</html>