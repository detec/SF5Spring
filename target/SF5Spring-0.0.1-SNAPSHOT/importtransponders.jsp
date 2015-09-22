<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head>
<title>Openbox SF-5 settings editor - Import transponders from INI-file</title>
<sec:csrfMetaTags/>
</head>
<body>
<h2>Openbox SF5 settings editor</h2>
 <h3>Import transponders from  *.ini files</h3>
	<form method="POST" enctype="multipart/form-data" action="upload">
		File to upload: <input type="file" name="file"> <br /> <input type="submit"
			value="Upload">
		
	<input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />		
	</form>
<br>
<a href="settings">Settings</a>
<a href="transponders">Transponders</a>
<form:form method="POST" action="logout">
<input type="submit" value="Logout" />
</form:form>	
</body>
</html>