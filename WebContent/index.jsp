<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- JSP Imports --%>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Real Lean Fitness</title>

<%
// Get the "do" Parameter
String Action = (String) request.getParameter("do");

// If the "do" parameter is not null perform the appropriate action
if(Action != null){
	if(Action.equals("editprofile")){response.sendRedirect(GlobalTools.GTV_UserSettings);}
	if(Action.equals("contactsupport")){response.sendRedirect(GlobalTools.GTV_ContactUs);}
	if(Action.equals("google")){response.sendRedirect("https://www.google.com/");}
	
	if(Action.equals("fpwdc")){
		String User = (String) request.getParameter("usr");
		String Key = (String) request.getParameter("key");
		if(User != null && Key != null){
		String EncodedUser = URLEncoder.encode(User, "UTF-8");
		String EncodedKey = URLEncoder.encode(Key, "UTF-8");
		response.sendRedirect(GlobalTools.GTV_Settings_ForgotPasswordChange + "?do="+Action+"&usr="+EncodedUser+"&key="+EncodedKey);
		}
	}
	
	if(Action.equals("vfy")){
		String User = (String) request.getParameter("usr");
		String EMail = (String) request.getParameter("key");
		if(User != null && EMail != null){
		String EncodedUser = URLEncoder.encode(User, "UTF-8");
		String EncodedEMail = URLEncoder.encode(EMail, "UTF-8");
		response.sendRedirect(GlobalTools.GTV_Settings_EMailVerification + "?do="+Action+"&usr="+EncodedUser+"&key="+EncodedEMail);
        
		}
	}
	
}

%>

<meta http-equiv="REFRESH" content="0;url=/HomePage.jsp"/>

</head>
<body>
<h1></h1>
</body>
</html>
