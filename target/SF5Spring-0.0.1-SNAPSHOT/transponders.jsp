<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Openbox SF-5 settings editor - Browse, select transponders</title>
<sec:csrfMetaTags/>
<style>
table, th, td {
    border: 1px solid black;
     border-collapse: collapse;
}
th, td {
    padding: 5px;
}
</style>
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
 <h3>Transponders</h3>
 <form:form method="POST" action="transponders" modelAttribute="bean" id="formFilterSatellite" >
 	<form:select path="filterSatelliteId" >
		<form:option value="NONE" label="--- Select ---"/>
 		<form:options items="${bean.satellites}" />
	</form:select>
	<input type="submit" value="Apply filter" name="filter"/>
	<form:input type="hidden" path="SelectionMode"/>
</form:form>

	
<c:set var="colsnumber" value="${bean.selectionMode ? 9 : 8}" />
	
<form:form modelAttribute="wrapper" method="POST" action="transponders" items="${wrapper.tclist}" >
  	<c:if test="${bean.selectionMode}">
	<input type="submit" value="Select" name="select"/>
	</c:if>
	 	
 	<table style="border: 1px solid">
  	<thead>
  	<tr>
   	<c:if test="${bean.selectionMode}">
   	<th>Select</th>
    </c:if>
   	<th>Frequency</th>
   	<th>Speed</th>
   	<th>Pol.</th>
    <th>FEC</th>
    <th>Carrier</th>
    <th>DVB ver.</th>
    <th>DVB range</th>
    <th>Satellite</th>
  	</tr>
 	</thead>
 	<c:forEach items="${wrapper.tclist}" var="transponder" varStatus="x">
  	<tr>
  	<c:if test="${bean.selectionMode}">
	<td><form:checkbox path="tclist[${x.index}].checked" /></td>
  	</c:if>
	<form:input type="hidden" path="tclist[${x.index}].id"/>

	
  	<td><c:out value="${transponder.frequency}" /></td>
  	<td><c:out value="${transponder.speed}" /></td>
  	<td><c:out value="${transponder.polarization}" /></td>
  	<td><c:out value="${transponder.FEC}" /></td>
  	<td><c:out value="${transponder.carrier}" /></td>
  	<td><c:out value="${transponder.versionOfTheDVB}" /></td>
  	<td><c:out value="${transponder.rangeOfDVB}" /></td>
  	<td><c:out value="${transponder.satellite}" /></td>
  	</tr>
   	</c:forEach>
 	</table>
 </form:form>
 
 <c:if test="${empty wrapper.tclist}">
 <i>There are currently no transponders in the list.</i> 
</c:if>
<br>
<a href="settings">Settings</a>
<a href="upload">Import transponders from file</a>
<form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
</body>
</html>