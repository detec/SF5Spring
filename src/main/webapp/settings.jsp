<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Openbox SF-5 settings editor - Settings list</title>
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

<!-- http://stackoverflow.com/questions/2521606/spring-mvc-absolute-url-problem 
This causes problem
-->
<%-- <base href="${pageContext.request.contextPath}"><base> --%>
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
<div style="font-style:italic;"> You are working under <c:out value="${username}" /></div>
 <c:if test="${not empty viewMsg}" >
	<div style="color:#408080;"> ${viewMsg}</div>
</c:if>
 <h3>Settings</h3>
<!--<c:url var="addUrl" value="/settings/add" /> -->
<c:url var="addUrl" value="/addsetting" />
<table style="text-align:center" id="SettingsTable">
 <thead >

  <tr>
   <th>ID</th>
   <th>Name</th>
   <th>Last update date</th>
   <th>Action</th>
   
  </tr>
 </thead>
 <tbody>
 <c:forEach items="${settings}" var="setting">
   <c:url var="editUrl" value="/editsetting?id=${setting.id}&selectionmode=false" />
   <c:url var="deleteUrl" value="/settings/delete?id=${setting.id}" />
   <c:url var="selectUrl" value="/selectedsetting?id=${setting.id}" />
   <c:url var="printUrl" value="/print?id=${setting.id}" />
  <tr>
   <td><c:out value="${setting.id}" /></td>
   <td><c:out value="${setting.name}" /></td>
   <td><c:out value="${setting.theLastEntry}" /></td>
    <td>
    <c:if test="${!SelectionMode}">
    <a href="${editUrl}">Edit</a><a href="${deleteUrl}">Delete</a><a href="${printUrl}">Print</a>
    </c:if>
    <c:if test="${SelectionMode}">
    <a href="${selectUrl}">Select</a>
    </c:if>
    </td>
  </tr>
 </c:forEach>
 </tbody>
</table>
 
<c:if test="${empty settings}">
 <i>There are currently no settings in the list.</i> 
</c:if>
<a href="${addUrl}">Add</a>

<c:url var="transpondersUrl" value="/transponders" />
<c:url var="uploadUrl" value="/upload" />
<a href="${transpondersUrl}">Transponders</a>
<a href="${uploadUrl}">Import transponders from file</a>

<c:if test="${hasAdminRole}">
<c:url var="usersUrl" value="/users/" />
<!-- <a href="/users/">User administration</a> -->
<a href="${usersUrl}">User administration</a>
</c:if>
<form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
</body>

</html>

