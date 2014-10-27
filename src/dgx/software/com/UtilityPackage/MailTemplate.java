package dgx.software.com.UtilityPackage;

public class MailTemplate {

	// Generates a Complete HTML E-Mail
	public static String getCompleteHTMLEMail(String CustomHTMLBody, String EMailToUnsubscribe){
		
		String CompleteHTMLEMail = "";
		
		// Call the Opening and CLosing Methods to Generate a Complete HTML E-Mail
	    CompleteHTMLEMail = CompleteHTMLEMail.concat(getHTMLEMailOpening());
	    CompleteHTMLEMail = CompleteHTMLEMail.concat(CustomHTMLBody);
	    CompleteHTMLEMail = CompleteHTMLEMail.concat(getHTMLEMailClosing(EMailToUnsubscribe));
		
		return CompleteHTMLEMail;
	}
	
	// Returns the Fixed HTML Opening of the E-Mail HTML (Fixed until the Custom part starts)
	private static String getHTMLEMailOpening(){
		
		String EMailStart = "";
		EMailStart =
				"<!-- ************************************** START FIXED JAVA HTML OPENING ************************************** -->" +
						"<body marginheight='0' background='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/MegamanHelmetBG.png' topmargin='0' marginwidth='0' bgcolor='#518ae0' offset='0' leftmargin='0'>" +
						"" +
						"<!-- START Wrapper Table-->" +
						"<table cellspacing='0' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;'  width='100%' cellpadding='0'><tr><td valign='top'>" +
						"" +
						"<br>" +
						"<table rules='none' cellspacing='0' border='1' bordercolor='#d6d6d6' frame='border' align='center' style='font-size: 14px; border-color: #d6d6d6 #d6d6d6 #d6d6d6 #d6d6d6; border-collapse: collapse; font-family: Arial, sans-serif; line-height: 20px; color: #666666; border-spacing: 0px; border-width: 1px 1px 1px 1px; border-style: solid solid solid solid; background: #ffffff;' bgcolor='#ffffff' width='600' cellpadding='0'><tr><td>" +
						"<!-- START MAIN CONTENT. ADD MODULES BELOW -->" +
						"" +
						"<!-- START Module #1 -->" +
						"<br>" +
						"<table cellspacing='0' align='center' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
						"<tr>" +
						"" +
						"" +
						"<td align='center' valign='top' bgcolor='#333'>" +
						"" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/MegamanXAttack.gif' alt='' width='540' height='200' vspace='10' />" +
						"" +
						"</td>" +
						"" +
						"</tr>" +
						"</table>" +
						"<!-- END Module #1 -->" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Module-Divider.gif' height='61' alt='' width='600' />" +
						"" +
						"<!-- START Module #2 -->" +
						"<table cellspacing='0' align='center' style='font-size: 14px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
						"<tr>" +
						"<td valign='top'>" +
						"<!-- ************************************** END FIXED JAVA HTML OPENING ************************************** -->" +
						"<!-- ************************************** START CUSTOM JAVA BODY ************************************** -->";

		
		return EMailStart;
		
	}
	
	
	// Returns the Fixed HTML Closing of the E-Mail HTML (Fixed until the Custom part ends)
	private static String getHTMLEMailClosing(String EMailToUnsubscribe){
		
		boolean ShowUnsubscribeURL = false;
		if(EMailToUnsubscribe == null){
			EMailToUnsubscribe = "";
			}else{
				ShowUnsubscribeURL = true;
			}
		String NewsletterUnsubscribeURL = GlobalTools.GTV_Settings_NewsletterSubscription + "?" + "EMail=" + EMailToUnsubscribe;
		
		String EMailEnd = "";
		EMailEnd = 
				"<!-- ****************************************** END CUSTOM JAVA BODY ****************************************** -->" +
				"<!-- ************************************** START FIXED JAVA HTML CLOSING ************************************** -->" +
				"</td>" +
				"</tr>" +
				"</table>" +
				"" +
				"<!-- END Module #2 -->" +
				"" +
				"<!-- END MAIN CONTENT -->" +
				"" +
				"<!-- START SOCIAL + COMPANY INFO + SUBSCRIPTION -->" +
				"<br>" +
				"<br>" +
				"<table cellspacing='0' style='font-size: 12px; border-bottom: 1px solid #D0D0D0; font-family: Arial, sans-serif; line-height: 20px; color: #666666; border-top: 1px solid #D0D0D0;' bgcolor='#efefef' cellpadding='0'>" +
				"<tr>" +
				"" +
				"<td height='45' valign='middle' width='30'>&nbsp;</td>" +
				"" +
				"<td height='45' valign='middle' width='115'>" +
				"<b style='color: #333333;'>Follow us on:</b>" +
				"</td>" +
				"" +
				"<!-- START Facebook -->" +
				"<td height='45' valign='middle' width='50'>" +
				"<a href='https://www.facebook.com/RealLeanFitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
				"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Facebook-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
				"</td>" +
				"<!-- END Facebook -->" +
				"" +
				"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
				"" +
				"<!-- START Twitter -->" +
				"<td height='45' valign='middle' width='50'>" +
				"<a href='https://Twitter.com/RealLeanFitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
				"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Twitter-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
				"</td>" +
				"<!-- END Twitter -->" +
				"" +
				"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
				"" +
				"<!-- START Instagram -->" +
				"<td height='45' valign='middle' width='50'>" +
				"<a href='http://Instagram.com/realleanfitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
				"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Instagram-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
				"</td>" +
				"<!-- END Instagram -->" +
				"" +
				"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
				"" +
				"<!-- START Google Plus -->" +
				"<td height='45' valign='middle' width='50'>" +
				"<a href='https://plus.google.com/106427449538506931543/posts' title='' style='color: #3a3a3a; text-decoration: none;'>" +
				"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Google-Plus-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
				"</td>" +
				"<!-- END Google Plus -->" +
				"" +
				"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
				"" +
				"<!-- START Reddit -->" +
				"<td height='45' valign='middle' width='50'>" +
				"<a href='https://www.reddit.com' title='' style='color: #3a3a3a; text-decoration: none;'>" +
				"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Reddit-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
				"</td>" +
				"<!-- END Reddit -->" +
				"" +
				"<td height='45' valign='middle' width='200'>&nbsp;</td>" +
				"";
		
				// If we received a valid E-Mail address, Generate the unsubscribe Section
		        if(ShowUnsubscribeURL == true){
		        System.out.println();
			    EMailEnd = EMailEnd +
				"<table cellspacing='0' align='center' style='font-size: 14px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
				"<tr>" +
				"<td valign='top'>" +
				"<p style='font-size: 9; font-family: Arial, sans-serif; color: #888888;'>This message was sent to dmastagx@hotmail.com. If you don't want to receive these E-Mails from Real Lean Fitness in the future, please <a href='"+NewsletterUnsubscribeURL+"'>unsubscribe</a>.</p>" +
				"</td>" +
				"</tr>" +
				"</table>";
		        }
		        
		EMailEnd = EMailEnd +
				"" +
				"</tr>" +
				"</table>" +
				"<br>" +
				"</td>" +
				"</tr>" +
				"</table>" +
				"" +
				"" +
				"<table cellspacing='0' align='center' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='560' cellpadding='0'>" +
				"" +
				"<tr><td align='center' valign='top'>" +
				"" +
				"<!-- START FOOTER CONTENT -->" +
				"<p>&copy; 2014 <b style='color: #333333;'>www.RealLeanFitness.com</b>. All rights reserved.</p>" +
				"<!-- END FOOTER CONTENT -->" +
				"" +
				"</td></tr></table>" +
				"" +
				"<br />" +
				"" +
				"</td>" +
				"</tr>" +
				"</table>" +
				"<!-- END Wrapper Table-->" +
				"</body>" +
				"</html>" +
				"<!-- ************************************** END FIXED JAVA HTML CLOSING ************************************** -->";
		
		return EMailEnd;
		
	}
	
/*	
	public static String FullSampleEMail(){
		
		String FullSampleEMail = "";
		
		FullSampleEMail =
				"<!-- ************************************** START FIXED JAVA HTML OPENING ************************************** -->" +
						"<body marginheight='0' background='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/MegamanHelmetBG.png' topmargin='0' marginwidth='0' bgcolor='#518ae0' offset='0' leftmargin='0'>" +
						"" +
						"<!-- START Wrapper Table-->" +
						"<table cellspacing='0' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;'  width='100%' cellpadding='0'><tr><td valign='top'>" +
						"" +
						"<br>" +
						"<table rules='none' cellspacing='0' border='1' bordercolor='#d6d6d6' frame='border' align='center' style='font-size: 14px; border-color: #d6d6d6 #d6d6d6 #d6d6d6 #d6d6d6; border-collapse: collapse; font-family: Arial, sans-serif; line-height: 20px; color: #666666; border-spacing: 0px; border-width: 1px 1px 1px 1px; border-style: solid solid solid solid; background: #ffffff;' bgcolor='#ffffff' width='600' cellpadding='0'><tr><td>" +
						"<!-- START MAIN CONTENT. ADD MODULES BELOW -->" +
						"" +
						"<!-- START Module #1 -->" +
						"<br>" +
						"<table cellspacing='0' align='center' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
						"<tr>" +
						"" +
						"" +
						"<td align='center' valign='top' bgcolor='#333'>" +
						"" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/MegamanXAttack.gif' alt='' width='540' height='200' vspace='10' />" +
						"" +
						"</td>" +
						"" +
						"</tr>" +
						"</table>" +
						"<!-- END Module #1 -->" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Module-Divider.gif' height='61' alt='' width='600' />" +
						"" +
						"<!-- START Module #2 -->" +
						"<table cellspacing='0' align='center' style='font-size: 14px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
						"<tr>" +
						"<td valign='top'>" +
						"<!-- ************************************** END FIXED JAVA HTML OPENING ************************************** -->" +
						"<!-- ************************************** START CUSTOM JAVA BODY ************************************** -->" +
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
						"<a href='www.RealLeanFitness.com?do=contactsupport'>www.RealLeanFitness.com?do=contactsupport</a></p>" +
						"<!-- ****************************************** END CUSTOM JAVA BODY ****************************************** -->" +
						"<!-- ************************************** START FIXED JAVA HTML CLOSING ************************************** -->" +
						"</td>" +
						"</tr>" +
						"</table>" +
						"" +
						"<!-- END Module #2 -->" +
						"" +
						"<!-- END MAIN CONTENT -->" +
						"" +
						"<!-- START SOCIAL + COMPANY INFO + SUBSCRIPTION -->" +
						"<br>" +
						"<br>" +
						"<table cellspacing='0' style='font-size: 12px; border-bottom: 1px solid #D0D0D0; font-family: Arial, sans-serif; line-height: 20px; color: #666666; border-top: 1px solid #D0D0D0;' bgcolor='#efefef' cellpadding='0'>" +
						"<tr>" +
						"" +
						"<td height='45' valign='middle' width='30'>&nbsp;</td>" +
						"" +
						"<td height='45' valign='middle' width='115'>" +
						"<b style='color: #333333;'>Follow us on:</b>" +
						"</td>" +
						"" +
						"<!-- START Facebook -->" +
						"<td height='45' valign='middle' width='50'>" +
						"<a href='https://www.facebook.com/RealLeanFitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Facebook-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
						"</td>" +
						"<!-- END Facebook -->" +
						"" +
						"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
						"" +
						"<!-- START Twitter -->" +
						"<td height='45' valign='middle' width='50'>" +
						"<a href='https://Twitter.com/RealLeanFitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Twitter-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
						"</td>" +
						"<!-- END Twitter -->" +
						"" +
						"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
						"" +
						"<!-- START Instagram -->" +
						"<td height='45' valign='middle' width='50'>" +
						"<a href='http://Instagram.com/realleanfitness' title='' style='color: #3a3a3a; text-decoration: none;'>" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Instagram-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
						"</td>" +
						"<!-- END Instagram -->" +
						"" +
						"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
						"" +
						"<!-- START Google Plus -->" +
						"<td height='45' valign='middle' width='50'>" +
						"<a href='https://plus.google.com/106427449538506931543/posts' title='' style='color: #3a3a3a; text-decoration: none;'>" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Google-Plus-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
						"</td>" +
						"<!-- END Google Plus -->" +
						"" +
						"<td height='45' valign='middle' width='25'>&nbsp;</td>" +
						"" +
						"<!-- START Reddit -->" +
						"<td height='45' valign='middle' width='50'>" +
						"<a href='https://www.reddit.com' title='' style='color: #3a3a3a; text-decoration: none;'>" +
						"<img src='http://DGXSoftware.com/RLF/JSP/Mail/Pictures/Reddit-Logo.png' border='0' height='24' alt='' width='24' /></a>" +
						"</td>" +
						"<!-- END Reddit -->" +
						"" +
						"<td height='45' valign='middle' width='200'>&nbsp;</td>" +
						"" +
						"<table cellspacing='0' align='center' style='font-size: 14px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='540' cellpadding='0'>" +
						"<tr>" +
						"<td valign='top'>" +
						"<p style='font-size: 10; font-family: Arial, sans-serif; color: #888888;'>This message was sent to dmastagx@hotmail.com. If you don't want to receive these E-Mails from Real Lean Fitness in the future, please <a href='www.Google.com'>unsubscribe</a>.</p>" +
						"</td>" +
						"</tr>" +
						"</table>" +
						"" +
						"</tr>" +
						"</table>" +
						"<br>" +
						"</td>" +
						"</tr>" +
						"</table>" +
						"" +
						"" +
						"<table cellspacing='0' align='center' style='font-size: 12px; line-height: 20px; font-family: Arial, sans-serif; color: #666666;' width='560' cellpadding='0'>" +
						"" +
						"<tr><td align='center' valign='top'>" +
						"" +
						"<!-- START FOOTER CONTENT -->" +
						"<p>&copy; 2014 <b style='color: #333333;'>www.RealLeanFitness.com</b>. All rights reserved.</p>" +
						"<!-- END FOOTER CONTENT -->" +
						"" +
						"</td></tr></table>" +
						"" +
						"<br />" +
						"" +
						"</td>" +
						"</tr>" +
						"</table>" +
						"<!-- END Wrapper Table-->" +
						"</body>" +
						"</html>" +
						"<!-- ************************************** END FIXED JAVA HTML CLOSING ************************************** -->";
		
		
		return FullSampleEMail;
	}
*/	
	
}
