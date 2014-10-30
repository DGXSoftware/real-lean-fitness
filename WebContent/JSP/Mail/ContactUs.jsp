<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.GlobalTools" %>

<?xml version = "1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<!-- Set the Title for the Website Page -->
	<title>Contact Us</title>
		
    <!-- Set the Favicon for the Website page -->
	<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
	<!-- Set the Character Encoding for the Website page -->
	<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
	<!-- Include the Stylesheet Files -->
	<link rel='stylesheet' type='text/css' href='/CSS/ContactUs.css' />
	
	<!-- Include the jQuery Files -->
	<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>
	
	<!-- Include the Contact Me JavaScript File -->
	<script type='text/javascript' src='/JavaScript/Validation/GlobalFieldValidation.js?<%= Math.random() %>' > </script>
	<script type='text/javascript' src='/JavaScript/Captcha.js' > </script>
		
	<script>
	
	function SubmitContactUs(f){
		
        // Validate the User Input before Submitting. Set it so it Alerts about the specific User
        // Input that is invalid. IF this method returns false, stop further execution and don't submit.
        
        //If the Sender First Name is not valid Alert so, and stop further processing
        if(!isValidNameFieldAlert('SenderFirstName','SenderFirstNameIcon','',false)){ return false; }
        
        //If the Sender Last Name is not valid Alert so, and stop further processing
        if(!isValidNameFieldAlert('SenderLastName','SenderLastNameIcon','',false)){ return false; }
        
        //If the Sender EMail is not valid Alert so, and stop further processing
        if(!isValidEMailAlert('SenderEMail','SenderEMailIcon','',false)){ return false; }

        //If the Sender Subjectis not valid Alert so, and stop further processing
        if(!isValidRandomFieldAlert('SenderSubject','SenderSubjectIcon','',false)){ return false; }

        //If the Sender Message is not valid Alert so, and stop further processing
        if(!isValidRandomFieldAlert('SenderMessage','SenderMessageIcon','',false)){ return false; }

            var jqxhr = $.ajax({
                type:       "POST",
                url:        "/SendEMailServlet",
                cache:      false,
                data:       $("form").serialize(),
                    
                // Before load, notify the user that the request
                // may take awhile 
                beforeSend: function() {

                // Use AJAX to Inject the Sending Now HTML to the appropriate DIV
                $('#ContactUsFormFeedbackDiv').html("<div id='BeforeSendResults'><font color='blue' size='+2'><b> Sending Now! </b></font></div>");
                
                            },
                                
                // If user remains on page for the results,
                // show alert with results
                success:    function(data, status) {

                <%
                // Since the Password Change was successful
                // Return the user Home via a Successful Countdown Forward Message
                String SuccessMessage = "We received your E-Mail. We will respond as soon as possible. ~ Thank You!";
                String SuccessURL = GlobalTools.GTV_CountdownForwardMessage + "?SuccessMessage="+SuccessMessage+"";
                %>
                    
                // Forward to the Success URL
                window.location = "<%= SuccessURL %>";
                
                         },
                         
                // If there is an error and the user hasn't yet closed the
                // browser, display the message. Otherwise it will come in
                // the email
                error:      function(xhr, textStatus, thrownError) {

                // Use AJAX to Inject the Error HTML to the appropriate DIV (DETAILED VERSION)
                // $('#ContactUsFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='+1'><b>" + thrownError + " - </b> Error " + xhr.status + ":" + xhr.responseText + "</font></div>");

                // Use AJAX to Inject the Error HTML to the appropriate DIV (SHORT VERSION)
                $('#ContactUsFormFeedbackDiv').html("<div id='ErrorResults'><font color='red' size='1'><b>" + xhr.responseText + "</b></font></div>");

                // Stop further processing
                return false;
                            }
                    });
        
	}
	
	</script>
		
</head>

<body>
<div id="container">
	<div id="main">
		<div id="header"></div>
		<div id="nav">
			<ul>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="#"></a></li>
				<li><a href="<%= GlobalTools.GTV_Homepage %>">Home</a></li>
			</ul>
		</div>
		<div id="content">
			<div id="left">
				<div class="post">
					<h1>Contact Us</h1>

	<p align="left">&#160;</p>
					

<div id="input">

 <!-- Create the Contact Us Form -->
<form id="ContactUsForm" name="ContactUsForm" method="post" >

<!-- Login For Feedback Div -->
<div id="ContactUsFormFeedbackDiv" name="ContactUsFormFeedbackDiv"></div>

<b>We would love to hear from you!&nbsp; Fill out the form below and we'll get back to you as soon possible.</b>

<br />
<br />
<br />

 <!-- Get the user's First Name -->
        <!-- First_Name VARCHAR(32) -->
		<label for="SenderFirstName">
		<p> First Name: </p>
        <input type="text" id="SenderFirstName" name="SenderFirstName" onKeyUp="isValidNameField('SenderFirstName','SenderFirstNameIcon','',false);" 
        title='Sender First Name' size='32' maxlength='32' />
        <img id="SenderFirstNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
<br />
<br />

 <!-- Get the user's Last Name -->
        <!-- Last_Name VARCHAR(32) -->
		<label for="SenderLastName">
		<p> Last Name: </p>
        <input type="text" id="SenderLastName" name="SenderLastName" onKeyUp="isValidNameField('SenderLastName','SenderLastNameIcon','',false);" 
        title='Sender Last Name' size='32' maxlength='32' />
        <img id="SenderLastNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
 
<br />
<br />
 
 <!-- Get the user's E-Mail Address -->
		<!-- EMail VARCHAR(255) -->
        <label for="SenderEMail">
        <p> E-Mail: </p>
        <input type="text" id="SenderEMail" name="SenderEMail" onKeyUp="isValidEMail('SenderEMail','SenderEMailIcon','',false);" 
        title='Sender E-Mail' size='48' maxlength='255' />
        <img id="SenderEMailIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>
        
<br />
<br />

 <!-- Get the user's Subject -->
		<label for="SenderSubject">
		<p> Subject: </p>
        <input type="text" id="SenderSubject" name="SenderSubject" onKeyUp="isValidRandomField('SenderSubject','SenderSubjectIcon','',false);" 
        title='Subject' size='32' maxlength='100' />
        <img id="SenderSubjectIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>

<br />
<br />

 <!-- Get the user's Message --> 
		<label for="SenderMessage">
		<p> Message: </p>
        <textarea id="SenderMessage" name="SenderMessage" onKeyUp="isValidRandomField('SenderMessage','SenderMessageIcon','',false);" 
        title='Message' rows="10" cols="50" maxlength="50"></textarea>
        <img id="SenderMessageIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
        </label>


<br />
<br />
<br />

<!-- START DISABLED Captcha and Attachment  -->
<!-- 
 <-- Get the user's File Attachment --
<span style="font-weight: bold;">Attachment (optional)</span>
<br />
<input type="file" id="SenderFileAttachment" name="SenderFileAttachment" size="30" />
<-- Set the E-Mail SenderFileAttachment --
<-- <input type="hidden" id="SenderFileAttachment" name="SenderFileAttachment" value="C:/Images/image.jpg" /> --

<br />
<br />
<br />

 <!-- Warn the user about the case sensitivity of the Captcha --
<span style="font-weight: bold;">Case sensitive Captcha</span>
<br />
<br />

 <!-- Display the Captcha Code --
<table id="CaptchaTable" name="CaptchaTable" >
<tr>
<td style = "background-color: black;" >
<b id="CaptchaDisplay" name="CaptchaDisplay" value="" style = "color: cyan;"></b>
</td>
</tr>
</table>

<!-- This Button Re-Calculates the Captcha --
<input type="button" value="Refresh Captcha" onClick="getRandomCaptcha();">


<br />
<br />



 <!-- Get the user's Captcha Input --
<span style="font-weight: bold;">Enter the Captcha Code</span>
<br />
<input type ="text" id="CaptchaCodeUserInput" name="CaptchaCodeUserInput" size="10" />
-->

<script>
/* DISABLED
// On Load Generate the Captcha
$( document ).ready(function() {
	getRandomCaptcha();
});
*/
</script>
<!-- END DISABLED Captcha and Attachment -->

 <!-- Set the E-Mail Recipient -->
<input type="hidden" id="Recipient" name="Recipient" value="RealLeanFitness@GMail.com" />

<center>
<input type="button" name="MyContactMeSubmitButton" Value="Submit Form" onClick="SubmitContactUs(document.ContactUsForm);" />
</center>
 
 </form>
 
<script>
// TEMPORARY TEST VALUES
$( document ).ready(function() {
	//document.getElementById("SenderFirstName").value='TESTFIRSTNAME';
	//document.getElementById("SenderLastName").value='TESTLASTNAME';
	//document.getElementById("SenderEMail").value='RealLeanFitness@GMail.com';
	//document.getElementById("SenderSubject").value='TEST SUBJECT';
	//document.getElementById("SenderMessage").value='TEST SENDER MESSAGE';
});
</script>
 
 </div>
					
					
				</div>
				<div class="post">
				<h1></h1>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div id="footer">
		<ul>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		<li><a href='#'></a></li>
		</ul>
		<span>Copyright © 2014</span>
	</div>
</div>
</body>
</html>
