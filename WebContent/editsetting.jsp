<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
 <h3>Edit setting</h3>
 <c:if test="${bean.id == 0}">
 <c:url var="saveUrl" value="/settings/add" />
 </c:if>
 <c:if test="${bean.id != 0}">
 <c:url var="saveUrl" value="/editsetting"/>
 </c:if>
<form:form modelAttribute="bean" method="POST" action="${saveUrl}">
 <table>
  <tr>
   <td><form:label path="name">Name</form:label></td>
   <td><form:input path="name"/></td>
   <td><form:label path="id">ID</form:label><b><c:out value="${bean.id}" /></b><form:hidden path="id"/></td>
   
   <td><form:label path="TheLastEntry">Last update date</form:label><form:hidden path="TheLastEntry"/></td>
   <td><b><c:out value="${bean.theLastEntry}" /></b></td>
  </tr>
   <tr>
   <form:hidden path="user"/>
   <form:hidden path="SettingsObject"/>
   </tr>
 </table>
  
  <p>Transponders table
  <br>
  <input type="submit" value="Up"/>
  <input type="submit" value="Down"/>
  <input type="submit" value="Select transponders..." name="selectTransponders" />
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
 <td><form:input path="dataSettingsConversion[${x.index}].transponder"/></td>
 <td><c:out value="${DataSC.polarization}" /></td>
 <td><c:out value="${DataSC.carrier}" /></td>
 <td><c:out value="${DataSC.speed}" /></td>
 <td><c:out value="${DataSC.satellite}" /></td>
 <td><c:out value="${DataSC.FEC}" /></td>
 <td><c:out value="${DataSC.satindex}" /><form:hidden path="dataSettingsConversion[${x.index}].satindex" /></td>
 <td><c:out value="${DataSC.tpindex}" /><form:hidden path="dataSettingsConversion[${x.index}].tpindex" /></td>
 <td><c:out value="${DataSC.theLineOfIntersection}" /></td>
 <td><form:input path="dataSettingsConversion[${x.index}].note"/></td>
 <td><c:out value="${DataSC.theLineOfIntersection}" /></td>
 <td><form:checkbox path="dataSettingsConversion[${x.index}].checked" /></td>
 <form:hidden path="dataSettingsConversion[${x.index}].parent_id"/>
 <form:hidden path="dataSettingsConversion[${x.index}].transponder.id"/>
 </tr>
 </c:forEach> 
  </table>
  <c:if test="${setting.id == 0}">
  <input type="submit" value="OK" name="add" />
  </c:if>
  <c:if test="${setting.id != 0}">
 <input type="submit" value="OK" name="save"/>
 </c:if>
 <input type="submit" value="Cancel" name="cancel"/>
</form:form>

<a href="settings">Settings</a>
 <form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
</body>
</html>