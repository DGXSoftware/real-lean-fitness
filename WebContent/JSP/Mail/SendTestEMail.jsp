<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!-- Create a MailJavaBean Object -->
<jsp:useBean id="MyMailJavaBean" scope="page" class="dgx.software.com.JavaBeanPackage.MailJavaBean" />

<%-- JSP Imports --%>
<%@ page import = "dgx.software.com.UtilityPackage.MailTemplate" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Real Lean Fitness</title>
</head>

<body>

<%

// Configure MyMailJavaBean

// Set the E-Mail Receiver
// NOTE: Can be sent to Multiple Recipients via a Semicolon ";" delimiter
//MyMailJavaBean.setEMailReceiver(request.getParameter( "Recipient" ));
//MyMailJavaBean.setEMailReceiver("RealLeanFitness@GMail.com");
//MyMailJavaBean.setEMailReceiver("RealLeanFitness@GMail.com;dmastagx@hotmail.com;rosariojairo@gmail.com");
MyMailJavaBean.setEMailReceiver("dmastagx@hotmail.com");
//MyMailJavaBean.setEMailReceiver("RealLeanFitness@GMail.com");

//Set the E-Mail Header Subject
//MyMailJavaBean.setEMailSubject(request.getParameter( "SenderSubject" ));
MyMailJavaBean.setEMailSubject("Testing Subject <333");

// Set the E-Mail Body
String CustomHTMLBody =
"<h2 style='font-size: 18px; font-family: Arial, sans-serif; color: #000;'>Password Change:</h2>" +
"" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>To reset your password, please visit the following page:</p>" +
"<a href='www.RealLeanFitness.com?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db'>www.RealLeanFitness.com?do=fpwdc&usr=1000000000&key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db</a>" +
"<p>&nbsp;</p>" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>When you visit that page, you will be able to change your password. Upon a successful password change an E-Mail confirmation will be sent to you.</p>" +
"<p>&nbsp;</p>" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>Your username is: <b>MegamanX</b></p>" +
"<p>&nbsp;</p>" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>To edit your profile, go to this page:</p>" +
"<a href='www.RealLeanFitness.com?do=editprofile'>www.RealLeanFitness.com?do=editprofile</a>" +
"<p>&nbsp;</p>" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>Thanks for helping us maintain the security of your account.</p>" +
"<p>&nbsp;</p>" +
"<p style='font-size: 14; font-family: Arial, sans-serif; color: #000;'>~ The RealLeanFitness Support Team</p>" +
"<a href='www.RealLeanFitness.com?do=contactsupport'>www.RealLeanFitness.com?do=contactsupport</a></p>";

// Generate the Complete HTML E-Mail
String RLFHTMLEMail = "";
RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(CustomHTMLBody, null);
//RLFHTMLEMail = MailTemplate.getCompleteHTMLEMail(CustomHTMLBody, "DGX123@RLF.com");

System.out.println(RLFHTMLEMail);

//Set the E-Mail Body Message
MyMailJavaBean.setEMailBody(RLFHTMLEMail);

// Set the File Attachment
//MyMailJavaBean.setAttachment(request.getParameter( "SenderFileAttachment" ));

%>


<%
// Send the E-Mail
MyMailJavaBean.sendEMail(response);
%>

</body>
</html>