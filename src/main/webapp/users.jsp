<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Openbox SF-5 settings editor - Users list</title>
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
<h3>Users list</h3>
 <c:if test="${not empty viewMsg}" >
	<div style="color:#408080;"> ${viewMsg}</div>
</c:if>
 <c:if test="${not empty viewErrMsg}" >
 <div style="color:Red;"> ${viewErrMsg}</div>
 </c:if>
<table style="text-align:center">
 <thead >

  <tr>
  <th>Action</th>
   <th>ID</th>
   <th>Username</th>
   <th>Enabled</th>
   <th>List of roles</th>
   
   </tr>
   </thead>
   <tbody>
   <c:forEach items="${users}" var="user" >
   <c:url var="deleteUrl" value="/users/delete?id=${user.id}" />
   <c:url var="changeStateUrl" value="/users/change?id=${user.id}&selectionmode=false" />
    <tr>
   <td><a href="${deleteUrl}">Delete</a><a href="${changeStateUrl}">Enable/disable</a></td>
   <td><c:out value="${user.id}" /></td>
   <td><c:out value="${user.username}" /></td>
   <td><c:out value="${user.enabled}" /></td>
   <c:forEach items="${user.authorities}" var="role" varStatus="x">
   <td><c:out value="${role.authority}" />
   <c:if test="${ (x.index + 1) < user.authorities.size()}">
   <br>
   </c:if>
    </td>
   </c:forEach>
   </tr> 
   </c:forEach>
   </tbody>
   </table>
   
<c:if test="${empty users}">
 	<i>There are currently no users in the list.</i> 
</c:if>


<a href="settings">Settings</a>
<a href="transponders">Transponders</a>	
 
 <form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>
</body>
</html>