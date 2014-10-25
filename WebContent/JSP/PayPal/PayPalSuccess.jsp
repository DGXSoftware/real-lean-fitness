<!--
GOAL: When you press Register and are forwarded to the PayPal Payment site 
your account is created in a deactivated state. Upon a successful Payment confirmation
from PayPal this page will activate your account via the internal Account ID.
-->

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<!DOCTYPE html>

<html>

<head>

<!-- Set the Title for the Website Page -->
<title>PayPal Registration Submit</title>
		
<!-- Set the Favicon for the Website page -->
<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
<!-- Set the Character Encoding for the Website page -->
<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
<!-- Include the Stylesheet Files -->
<!-- <link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' /> -->
		
<!-- Include the jQuery Files -->
<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
<!--
EXTERNAL jQuery Import
<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
-->

</head>

<body>
<!-- Create a MailJavaBean Object -->
<jsp:useBean id="MyUserSQLDBJavaBean" scope="page" class="dgx.software.com.JavaBeanPackage.UserSQLOperationJavaBean" />

<%

// Get the Current CurrentRegistrationUsername
String Account_ID_To_Activate = "";

//If the Query String Variable RegistrationUsername exists, get the value
if (request.getParameter("Account_ID") != null){
	Account_ID_To_Activate = request.getParameter("Account_ID");
}

// Only continue to attempt user activation if a RegistrationUsername was provided in the query string
if(!Account_ID_To_Activate.equals("")){

// Activate the Account (True = Activated, False = NOT Activated)
boolean AccountWasActivated = MyUserSQLDBJavaBean.activateAccount(request, response, Account_ID_To_Activate);

// Proceed with successful activation
if(AccountWasActivated == true){
	String SuccessfulMessage = "Your account has been successfully activated!";
	response.sendRedirect(GlobalTools.GTV_CountdownForwardMessage + "?SuccessMessage="+SuccessfulMessage+"");
}
//Proceed with bad activation
else{
	String CancelMessage = "Your account failed to activate. Please contact us for a manual activation by <a href='"+ GlobalTools.GTV_ContactUs +"'>CLICKING HERE</a>";
	response.sendRedirect(GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"&SkipCountdown=true");
}


}else{
	// Return the user to the Homepage because the "RegistrationUsername" was not provided in the query string
	String CancelMessage = "Invalid activation URL.";
	response.sendRedirect(GlobalTools.GTV_CountdownForwardMessage + "?CancelMessage="+CancelMessage+"");
}

%>

</body>

</html>