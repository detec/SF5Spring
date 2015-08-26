<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Openbox SF-5 settings editor</title>
<sec:csrfMetaTags/>
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
 <h3>Settings</h3>
<c:url var="addUrl" value="/settings/add" />
<table style="border: 1px solid; width: 500px; text-align:center">
 <thead style="background:#fcf">
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
   <c:url var="editUrl" value="/editsetting?id=${setting.id}" />
   <c:url var="deleteUrl" value="/deletesetting?id=${setting.id}" />
  <tr>
   <td><c:out value="${setting.id}" /></td>
   </tr>
  <tr>
  <td><c:out value="${setting.name}" /></td>
  </tr>
   <tr>
   <td><c:out value="${setting.lastupdatedate}" /></td>
   </tr>
   <tr>
    <td><a href="${editUrl}">Edit</a></td>
   <td><a href="${deleteUrl}">Delete</a></td>

  </tr>
 </c:forEach>
 </tbody>
</table>
 
<c:if test="${empty settings}">
 There are currently no settings in the list. <a href="${addUrl}">Add</a> setting.
</c:if>
 
</body>

</html>

