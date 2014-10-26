
package dgx.software.com.JavaBeanPackage;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/* (ATTACHMENTS DISABLED)
import java.io.File;
import javax.activation.*;
*/
public class MailJavaBean {

	// Sender GMail Account
	String SenderEMail = "RLFMailService@GMail.com";
	String SenderPassword = "moc.liaMG@ecivreSliaMFLR";

	// GMail Host
	String SenderHost = "smtp.gmail.com";

	// Gmail SMTP port as: 465 or 587
	String SenderPort = "465";

	// E-Mail Receiver (Default value overwritten by the setEMailReceiver() method)
	// NOTE: Can be sent to Multiple Recipients via a Semicolon ";" delimiter
	// EX: MyMailJavaBean.setEMailReceiver("RealLeanFitness@GMail.com;dmastagx@hotmail.com;rosariojairo@gmail.com");
	String EMailReceiver = "RLFMailService@GMail.com";
	
	// E-Mail Subject
	String EMailSubject = "Real Lean Fitness Contact Us E-Mail";

	// E-Mail Body
	String EMailBody = "No Message attached by sender.";
	
	// E-Mail Attachment Path (ATTACHMENTS DISABLED)
	//File FileAttachmentPath = new File("");
	
	// Set the E-Mail Recipient
	public void setEMailReceiver(String EMailRecipient){
		EMailReceiver = EMailRecipient;
	}
	
	// Set the E-Mail Subject
	public void setEMailSubject(String SenderSubject){
		EMailSubject = SenderSubject;
	}
	
	// Set the E-Mail Message
	public void setEMailBody(String SenderMessage){
		EMailBody = SenderMessage;
	}
	
	// Set E-Mail File Attachment (ATTACHMENTS DISABLED)
	/*
	public void setAttachment(String FileAttachment){
		FileAttachmentPath = new File(FileAttachment);
	}
	*/
	
	public void sendEMail(HttpServletResponse response) throws IOException {
		
		// Set the E-Mail Properties
		Properties EMailProperties = new Properties();
		EMailProperties.put("mail.smtp.user", SenderEMail);
		EMailProperties.put("mail.smtp.host", SenderHost);
		EMailProperties.put("mail.smtp.port", SenderPort);
		EMailProperties.put("mail.smtp.starttls.enable", "true");
		EMailProperties.put("mail.smtp.auth", "true");
		EMailProperties.put("mail.smtp.socketFactory.port", SenderPort);
		EMailProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		EMailProperties.put("mail.smtp.socketFactory.fallback", "false");
		
		// Try to form and send the E-Mail
		try {

			// Create a MimeMultipart Object to hold together MimeBody Part Objects
			MimeMultipart MyMimeMultipart = new MimeMultipart("related");
			
			// Create, Configure and add a Message BodyPart to the MimeMultipart Object
			MimeBodyPart MessageBodyPart = new MimeBodyPart();
			//MessageBodyPart.setContent(EMailBody, "text/html");
			MessageBodyPart.setContent(EMailBody, "text/html; charset=utf-8");
			MyMimeMultipart.addBodyPart(MessageBodyPart);
			
			// If the file FileAttachment exists Add it to the E-Mail as an Attachment (ATTACHMENTS DISABLED)
			/*
			if(FileAttachmentPath.exists()){

			// Create, Configure and add an EMail Attachment BodyPart to the MimeMultipart Object
			MimeBodyPart EMailAttachmentBodyPart = new MimeBodyPart();
			DataSource EMailAttachment = new FileDataSource(FileAttachmentPath);
			EMailAttachmentBodyPart.setDataHandler(new DataHandler(EMailAttachment));
			EMailAttachmentBodyPart.setFileName(EMailAttachment.getName());
			MyMimeMultipart.addBodyPart(EMailAttachmentBodyPart);

		}
		*/
			
			// Create a SMTPAuthenticator Object to Validate the Sender Account Information
			Authenticator MySMTPAuthenticator = new SMTPAuthenticator();
			
			// Create and Configure a Session Object with the E-Mail Properties and Authentication
			Session MySession = Session.getInstance(EMailProperties, MySMTPAuthenticator);
			
			// Create and configure a MimeMessage with the Complete Sender Message (Body,Attachment,HTML,Etc...)
			MimeMessage CompleteSenderMessage = new MimeMessage(MySession);
			CompleteSenderMessage.setContent(MyMimeMultipart);
			CompleteSenderMessage.setSubject(EMailSubject);
			CompleteSenderMessage.setFrom(new InternetAddress(SenderEMail));
			
			// Add all the EMailReceiver Recipients (Delimited by a Semicolon ";")
			String [] EMailReceiverArray = EMailReceiver.split(";");
			for(int i = 0 ; i < EMailReceiverArray.length; i++){
				System.out.println("Send To = " + EMailReceiverArray[i]);
				CompleteSenderMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(EMailReceiverArray[i]));
			}

			// Send the E-Mail with the Complete Sender Message
			Transport.send(CompleteSenderMessage);
			
			// Send a successful HTML Forward response
			sendSuccessForward(response);
			
		} catch (Exception EX) {
			
			// Print the Stack Trace
			EX.printStackTrace();
			
			// Send an Error HTML Forward response
			sendErrorForward(response);
		}
	}
	
	// Send a successful HTML Forward response
	private void sendSuccessForward(HttpServletResponse response) throws IOException {
		
		// Send a successful HTML Forward response
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<!-- Alert the User that the E-Mail was submitted. -->");
		out.println("<script language='javascript' type='text/javascript'> alert('Your E-Mail was submitted successfully.\\n\\n ~ Thank You.'); </script>");
		out.println("<!-- Redirect the user after sending the E-Mail -->");
		out.println("<meta http-equiv='REFRESH' content='0; url=/' />");
		out.println("</body>");
		out.println("</html>");
		
	}
	
	// Send an Error HTML Forward response
	private void sendErrorForward(HttpServletResponse response) throws IOException {
		
		// Send a successful HTML Forward response
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<!-- Alert the User that the E-Mail was submitted. -->");
		out.println("<script language='javascript' type='text/javascript'> alert('Your E-Mail failed to send.\\n\\n ~ Please try again!'); </script>");
		out.println("<!-- Redirect the user after sending the E-Mail -->");
		out.println("<meta http-equiv='REFRESH' content='0; url=/' />");
		out.println("</body>");
		out.println("</html>");
		
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SenderEMail, SenderPassword);
		}
	}
}