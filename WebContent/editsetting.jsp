<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Openbox SF-5 settings editor</title>
<sec:csrfMetaTags/>
</head>
<body>
 
<h2>Openbox SF5 settings editor</h2>
 <h3>Edit setting</h3>
 <c:if test="${setting.id == 0}">
 <c:url var="saveUrl" value="/settings/add" />
 </c:if>
 <c:if test="${setting.id != 0}">
 <c:url var="saveUrl" value="/settings/edit?id=${setting.id}"/>
 </c:if>
<form:form modelAttribute="setting" method="POST" action="${saveUrl}">
 <table>
  <tr>
   <td><form:label path="name">Name</form:label></td>
   <td><form:input path="name"/></td>
   <td><form:label path="id">ID</form:label></td>
   <td><form:label path="id" /></td>
   <td><form:label path="TheLastEntry">Last update date</form:label></td>
   <td><form:label path="TheLastEntry"/></td>
  </tr>
 </table>
  
  <p>Transponders table
  <br>
  <c:if test="${setting.id == 0}">
  <input type="submit" value="Add"/>
  </c:if>
  <c:if test="${setting.id != 0}">
 <input type="submit" value="Save" />
 </c:if>
</form:form>
<a href="index">Cancel</a>
 
</body>
</html>