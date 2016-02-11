<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">    
<html>
<head>
<title>Openbox SF-5 settings editor - Users list</title>
<sec:csrfMetaTags/>
<style>
table, th, td {

     border-collapse: collapse;
}
th, td {
    padding: 5px;
}
</style>
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
<table border="0">
<tbody>
  <tr>
  <td> <h3>Users</h3></td>
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
<table style="text-align:center" border="1">
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
   <c:url var="deleteUrl" value="/deleteuser?id=${user.id}" />
   <c:url var="changeStateUrl" value="/changeuser?id=${user.id}&selectionmode=false" />
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


<!-- <a href="settings">Settings</a>
<a href="transponders">Transponders</a> -->	

<c:url var="transpondersUrl" value="/transponders" />
<c:url var="settingsUrl" value="/settings" />
<c:url var="usersUrl" value="/users" />
<a href="${settingsUrl}">Settings</a>
<a href="${transpondersUrl}">Transponders</a>
<a href="${usersUrl}">Users</a>


</body>
</html>