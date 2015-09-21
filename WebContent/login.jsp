<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<sec:csrfMetaTags/>
</head>
<body>
   <h1>Login</h1>
   <form name='f' action="perform_login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='j_username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='j_password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="Submit" /></td>
         </tr>
      </table>
      
  	<input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />    
  </form>
 No account? <a href="register">Register</a>
</body>
</html>
