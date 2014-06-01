<!-- SOLUTION !!!!!!!!!!!!!!!!!!!!

(THIS PAGE MIGHT DO THIS INSTEAD)
Place a middle man JSP "PayPalForwardMessage" (Similar to PayPalCancel, Cancel may call this as well instead of doing the countdown) between this JSP and HomePage.jsp
which informs the user that they will be forwarded to the PayPal site to securely activate their account 

Once they're HERE

Have "PayPalJavaBean" do all the work of "PayPalRegistrationSubmitServlet". (Also delete this Servlet once It's useless.)

PayPalJavaBean will have a method called "getUserAccountIDAndEMailUserActivation(String Username)" 

With the obtained Account ID, plug it into the hidden input

<input type="hidden" name="return" value="http://www.RealLeanFitness.com/PayPalSuccess.jsp?Account_ID=88273882717A72734">

and submit to PayPal

Make the account activator = PayPalSuccess.jsp 

Then automated account creations should be done.

 -->

<!-- 
TO DO
Have this JSP do the work of dgx.software.com.ServletPackage.PayPalRegistrationSubmitServlet
As in 3 way forward
1. Already activated
2. Account not Created


3. Account NOT Activated (Then submit automatically to PayPal)
Provide it the Account_ID gathered from querying the database for the success URL

ALSO, have the "PayPalSuccess.jsp" activate accounts.

-->

<%
// BEFORE ANYTHING
// CHECK IF USER IS ACTIVATED
// IF HE/SHE IS THEN FORWAD BACK TO THE HOMEPAGE
%>

<%
// TO DO IF USER NOT ACTIVATED
// E-Mail the user the URL to get to this page with the "RegistrationUsername" as a Query string parameter (If Username exists in the Database)
// This Page will get the RegistrationUsername's Account ID
// It will place the Account ID in the PayPal Button Form
// It will auto Submit the PayPal Button Form with succesful payment return value of "PayPalSuccess.jsp" with the Account_ID in the Query String
// Once "PayPalSuccess.jsp" is called by payPal the Account ID obtained will be activated
// 
%>

<%
// NOTES
// E-MAIL URL SAMPLE
// http://localhost:8080/JSP/PayPal/PayPalRegistrationSubmit.jsp?RegistrationUsername=DGXFAN
// request.setAttribute("myStation", value);
// String value = (String)request.getAttribute("myStation");
%>



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
<jsp:useBean id="MyPayPalJavaBean" scope="page" class="dgx.software.com.JavaBeanPackage.PayPalJavaBean" />

<%

// Get the Current CurrentRegistrationUsername
String CurrentRegistrationUsername = "";

//If the Query String Variable RegistrationUsername exists, get the value
if (request.getParameter("RegistrationUsername") != null){
	CurrentRegistrationUsername = request.getParameter("RegistrationUsername");
}

// Only continue to attempt user activation if a RegistrationUsername was provided in the query string
if(!CurrentRegistrationUsername.equals("")){

//Get the AccountIDToActivate
String AccountIDToActivate = MyPayPalJavaBean.getUserAccountIDAndEMailUserActivation(request, response, CurrentRegistrationUsername);

if(AccountIDToActivate.equals("")){
	String CancelMessage = "User " + request.getParameter("RegistrationUsername") + " was not found in the database.";
	response.sendRedirect("/JSP/PayPal/PayPalForwardMessage.jsp?CancelMessage="+CancelMessage+"");
}else{

	// Set the Cancel Message
	String CancelMessage = "Transaction Canceled. Returning to homepage.";
	
	// Define the PayPalSuccessURL and PayPalCancelURL
	String PayPalSuccessURL = "http://www.RealLeanFitness.com/PayPalSuccess.jsp?Account_ID=" + AccountIDToActivate;
	String PayPalCancelURL = "http://www.RealLeanFitness.com/PayPalForwardMessage.jsp?CancelMessage=" + CancelMessage;
	
	// Set Test Versions (DELETE IN PRODUCTION)
	PayPalSuccessURL = "/JSP/PayPal/PayPalSuccess.jsp?Account_ID=" + AccountIDToActivate;
	PayPalCancelURL = "/JSP/PayPal/PayPalForwardMessage.jsp?CancelMessage=" + CancelMessage;
	
%>

<!-- Begin PayPal Logo -->
<!-- TEST = action="/JSP/PayPal/PayPalSimulator.jsp" -->
<!-- PRODUCTION = action="https://www.paypal.com/cgi-bin/webscr" -->
<form id="PayPalForm" action="/JSP/PayPal/PayPalSimulator.jsp" method="post" target="_top">
<!-- <table><tr><td><b>Real Lean Fitness Lifetime Membership</b></td></tr></table> -->
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="hosted_button_id" value="XECU2BVEDTZ5Y">
<!-- <input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!"> -->
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
<input type="hidden" name="return" value="<%= PayPalSuccessURL %>">
<input type="hidden" name="cancel_return" value="<%= PayPalCancelURL %>">
<input type="hidden" name="no_shipping" value="1">
</form>
<!-- End PayPal Logo -->

<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	// Submit the PayPalForm
    	document.getElementById("PayPalForm").submit();
      });
</script>

<%
}

}else{
	// Return the user to the Homepage because the "RegistrationUsername" was not provided in the query string
	String CancelMessage = "Invalid activation URL.";
	response.sendRedirect("/JSP/PayPal/PayPalForwardMessage.jsp?CancelMessage="+CancelMessage+"");
}

%>

</body>

</html>