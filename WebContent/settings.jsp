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
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
 <h3>Settings</h3>
<c:url var="addUrl" value="/settings/add" />
<table style="text-align:center">
 <thead >

  <tr>
   <th>ID</th>
   <th>Name</th>
   <th>Last update date</th>
   <th>Action</th>
   <th colspan="4"></th>
  </tr>
 </thead>
 <tbody>
 <c:forEach items="${settings}" var="setting">
   <c:url var="editUrl" value="/editsetting?id=${setting.id}&selectionmode=false" />
   <c:url var="deleteUrl" value="/settings/delete?id=${setting.id}" />
   <c:url var="selectUrl" value="/selectedsetting?id=${setting.id}" />
  <tr>
   <td><c:out value="${setting.id}" /></td>
   <td><c:out value="${setting.name}" /></td>
   <td><c:out value="${setting.theLastEntry}" /></td>
    <td>
    <c:if test="${!SelectionMode}">
    <a href="${editUrl}">Edit</a><a href="${deleteUrl}">Delete</a>
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
<a href="transponders">Transponders</a>
<a href="upload">Import transponders from file</a>

<form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
</body>

</html>

