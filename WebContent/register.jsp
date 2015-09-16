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
<title><spring:message code="label.form.title"></spring:message></title>
<sec:csrfMetaTags/>
</head>
<body>
    <H1>
        <spring:message code="label.form.title"></spring:message>
    </H1>
    <form:form modelAttribute="user" method="POST" enctype="utf8">
        <br>
        <tr>
        <td><label><spring:message code="label.user.username"></spring:message>
            </label>
        </td>
        <td><form:input path="username" value="" /></td>
        <form:errors path="username" element="div"/>
    </tr>
    <tr>
        <td><label><spring:message code="label.user.password"></spring:message>
            </label>
        </td>
        <td><form:input path="password" value="" type="password" /></td>
        <form:errors path="password" element="div" />
    </tr>
    <tr>
        <td><label><spring:message code="label.user.confirmPass"></spring:message>
            </label>
        </td>
        <td><form:input path="matchingPassword" value="" type="password" /></td>
        <form:errors element="div" />
    </tr>
        <button type="submit"><spring:message code="label.form.submit"></spring:message>
        </button>
    </form:form>
    <br>
    <a href="<c:url value="login.html" />">
        <spring:message code="label.form.loginLink"></spring:message>
    </a>
</body>
</html>
