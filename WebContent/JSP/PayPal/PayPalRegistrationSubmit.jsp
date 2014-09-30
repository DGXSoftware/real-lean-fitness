<!--
GOAL: Generates the RLF PayPal button along with the PayPalSuccessURL and PayPalCancelURL.
Then it automatically submits the form to forward the user to the PayPal payment site.

TO DO: E-Mail the user the link they would use if they'd like to activate their account
some other time.
-->

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
	
	// TEST: Set Test Versions (DELETE IN PRODUCTION)
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