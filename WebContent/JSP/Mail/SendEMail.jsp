<?xml version = "1.0" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!-- Create a MailJavaBean Object -->
<jsp:useBean id="MyMailJavaBean" scope="page" class="dgx.software.com.JavaBeanPackage.MailJavaBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<head></head>

<body>

<%

// Configure MyMailJavaBean

// Set the E-Mail Receiver
MyMailJavaBean.setEMailReceiver(request.getParameter( "Recipient" ));

// Set the E-Mail Body
String ContactMeFormUserInput =
"<h3>" +
"<p>" +
"<b>Sender First Name: </b>" +
request.getParameter( "SenderFirstName" ) +
"</p>" +
"<p>" +
"<b>Sender Last Name: </b>" +
request.getParameter( "SenderLastName" ) +
"</p>" +
"<p>" +
"<b>Sender E-Mail: </b>" +
request.getParameter( "SenderEMail" ) +
"</p>" +
"<p>" +
"<b>SenderMessage: </b>" +
request.getParameter( "SenderMessage" ) +
"</p>" +
"<br/><br/>" +
"<p> ~ Real Lean Fitness Mailing System</p>" +
"</h3>";

//Set the E-Mail Header Subject
MyMailJavaBean.setEMailSubject(request.getParameter( "SenderSubject" ));

//Set the E-Mail Body Message
MyMailJavaBean.setEMailBody(ContactMeFormUserInput);

// Set the File Attachment
//MyMailJavaBean.setAttachment(request.getParameter( "SenderFileAttachment" ));

%>


<%
// Send the E-Mail
MyMailJavaBean.sendEMail();
%>

<!-- Alert the User that the E-Mail was submitted. -->
<script language="javascript" type="text/javascript"> alert("Your E-Mail was submitted.\n\n ~ Thank You."); </script>

<!-- Redirect the user after sending the E-Mail -->
<meta http-equiv="REFRESH" content="0; url=/" />


</body>

</html>