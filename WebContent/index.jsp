<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Real Lean Fitness</title>

<%
// Get the "do" Parameter
String IndexAction = (String) request.getParameter("do");

// If the "do" parameter is not null perform the appropriate action
if(IndexAction != null){
	if(IndexAction.equals("ContactUs")){response.sendRedirect(GlobalTools.GTV_ContactUs);}
	if(IndexAction.equals("Google")){response.sendRedirect("https://www.google.com/");}
}

%>

<meta http-equiv="REFRESH" content="0;url=/HomePage.jsp"/>

</head>
<body>
<h1></h1>
</body>
</html>
