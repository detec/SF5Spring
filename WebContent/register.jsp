<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register new user</title>
<sec:csrfMetaTags/>
</head>
<body>
    <H1>
        Register new user
    </H1>
 <c:if test="${not empty viewErrMsg}" >
 <div style="color:Red;"> ${viewErrMsg}</div>
 </c:if>
 
    <form:form modelAttribute="user" method="POST" enctype="utf8">
        <br>
        <table>
        <tr>
        <td><label>Username
            </label>
        </td>
        <td><form:input path="username" value="" /></td>
        <form:errors path="username" element="div" cssStyle="color:Red"/>
    </tr>
    <tr>
        <td><label>Password
            </label>
        </td>
        <td><form:input path="password" value="" type="password" /></td>
        <form:errors path="password" element="div" cssStyle="color:Red"/>
    </tr>
    <tr>
        <td><label>Confirm password
            </label>
        </td>
        <td><form:input path="matchingPassword" value="" type="password" /></td>
        <form:errors element="div" cssStyle="color:Red"/>
    </tr>
    </table>
        <button type="submit">Submit</button>
        
        	<input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
    </form:form>
    <br>
    <a href="login">Login </a>
</body>
</html>
