package dgx.software.com.UtilityPackage;

public class MailTemplate {

	public static String EMailStart(){
		
		String EMailStart = "";
		
		EMailStart =
		"<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1//EN' 'http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd'>" +
		"<html xmlns='http://www.w3.org/1999/xhtml'>" +
		"<head>" +
		"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
		"" +
		"<!-- DISABLED: Local CSS -->" +
		"<!-- <link rel='stylesheet' type='text/css' href='/JSP/Mail/CSS/MailStyle.css' /> -->" +
		"" +
		"<!-- DISABLED: Web Hosted CSS -->" +
		"<!-- <link rel='stylesheet' type='text/css' href='http://www.DGXSoftware.com/RLF/JSP/Mail/CSS/MailStyle.css' /> -->" +
		"" +
		"<title>Real Lean Fitness</title>" +
		"</head>" +
		"" +
		MailTemplate.EMailCSS() +
		"" +
		"<body>" +
		"    <div id='page'>" +
		"    	        <div id='header'>" +
		"				    <!-- Enable Either an Image Logo or a Text Logo for this Page -->" +
		"                	<!-- <img src='Images/logo.png' alt='' /> -->" +
		"					<h1 style='padding-top:50px; text-align:center; color:#FFFFFF;'>Real Lean Fitness</h1>" +
		"                </div>" +
		"    " +
		"                </div>" +
		"" +
		"                <div id='content'>" +
		"                	<div id='container'>" +
		"" +
		"                        <div id='main'>" +
		"                        <div id='menu'>" +
		"                            <ul>" +
		"							<!--" +
		"	                            <li><a href='#'>Contact</a></li>" +
		"                                <li><a href='#'>About</a></li>" +
		"                                <li><a href='#'>Services</a></li>" +
		"                                <li><a href='#'>Gallery</a></li>" +
		"                                <li><a href='#'>Portfolio</a></li>" +
		"                                <li><a href='#'>Home</a></li>" +
		"							-->	" +
		"                            </ul>" +
		"                        </div>" +
		"                        <div id='text'>" +
		"						";

		
		return EMailStart;
		
	}
	
	
	public static String EMailCSS(){
		
		
		String EMailCSS = "";
		
		EMailCSS =
				"<style>" +
						"" +
						"html, body {" +
						"text-align: center;" +
						"}" +
						"p {text-align: left;}" +
						"" +
						"body {" +
						"	margin: 0;" +
						"	padding: 0;" +
						"	background: #FFFFFF;" +
						"	background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/background.png);" +
						"	text-align: left;" +
						"	font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif;" +
						"	font-size: 13px;" +
						"	color: #000000;" +
						"}" +
						"*" +
						"{" +
						"  margin: 0 auto 0 auto;" +
						" text-align:left;}" +
						"" +
						"p {text-align: left;}" +
						"" +
						"#page" +
						"{" +
						"  width:581px;" +
						"}" +
						"" +
						"#header" +
						"{" +
						"width:581px;" +
						"height:90px;" +
						"background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/header.png);" +
						"clear:both;" +
						"}" +
						"" +
						"#header img" +
						"{" +
						"display:block;" +
						"margin:0 auto 0 auto;" +
						"padding-top:40px;" +
						"}" +
						"" +
						"#content" +
						"{" +
						"background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/content_back_shadow.png);" +
						"background-repeat:repeat-y;" +
						"width:581px;" +
						"}" +
						"" +
						"#content #container" +
						"{" +
						"float:left;" +
						"background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/gradient_rectangle_back.png);" +
						"background-repeat:repeat-x;" +
						"background-color:#4586F1;" +
						"width:547px;" +
						"margin-left:14px;" +
						"clear:both;" +
						"}" +
						"" +
						"#menu" +
						"{" +
						"background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/menu_background.png);" +
						"height:54px;" +
						"width:527px;" +
						"}" +
						"" +
						"#menu ul {" +
						"	padding: 0px;" +
						"	list-style-type: none;" +
						"	text-align:center;" +
						"	height:40px;" +
						"" +
						"}" +
						"#menu ul li , #menu ul li a, #menu ul li a:visited{" +
						"	display:block;" +
						"	float:right;" +
						"	margin: 0px;" +
						"	text-align:left;" +
						"	line-height:40px;" +
						"	color:#FFFFFF;" +
						"	padding-left:6px;" +
						"	padding-right:2px;" +
						"	font-size:14px;" +
						"	font-weight:bold;" +
						"	text-decoration:none;" +
						"	width:77px;" +
						"}" +
						"" +
						"" +
						"#menu ul li a:hover{" +
						"color:#65A9ED;" +
						"}" +
						"" +
						"#content #main" +
						"{" +
						"display:block;" +
						"width:527px;" +
						"clear:both" +
						"}" +
						"" +
						"#content #text" +
						"{" +
						"margin:0;" +
						"padding:0;" +
						"width:497px;" +
						"display:block;" +
						"background-color:#FFFFFF;" +
						"float:left;" +
						"min-height:400px;" +
						"clear:both;" +
						"padding-left:15px;" +
						"padding-right:15px;" +
						"}" +
						"" +
						".clear {" +
						"    clear:both;" +
						"  }" +
						"" +
						"" +
						"#content #text h1, #content #text h1 a, #content #text h1 a:visited" +
						"{" +
						"margin:0;" +
						"padding:0;" +
						"color:#093175;" +
						"font-size:24px;" +
						"margin-bottom:7px;" +
						"margin-top:7px;" +
						"}" +
						"" +
						"#content #text p" +
						"{" +
						"margin-top:5px;" +
						"margin-bottom:5px;" +
						"padding:0;" +
						"}" +
						"" +
						"" +
						"#footer" +
						"{" +
						"background-image:url(http://www.DGXSoftware.com/RLF/JSP/Mail/Images/footer.png);" +
						"background-repeat:no-repeat;" +
						"width:581px;" +
						"height:60px;" +
						"font-size:11px;" +
						"color:#C5DDFA;" +
						"text-align:right;" +
						"}" +
						"" +
						"#footer p" +
						"{" +
						"text-align:center;" +
						"padding-top:20px;" +
						"}" +
						"" +
						"#footer a, #footer a:visited" +
						"{" +
						"font-size:11px;" +
						"color:#C5DDFA;" +
						"text-decoration:none;" +
						"}" +
						"" +
						"</style>";
		
		return EMailCSS;
		
	}
	
	public static String EMailEnd(){
		
		String EMailEnd = "";
		
		EMailEnd = 
						"						" +
						"                        </div>" +
						"" +
						"                        </div>" +
						"                </div>" +
						"                <div class='clear'></div>" +
						"                <div id='footer'>" +
						"                	<p><a href='www.RealLeanFitness.com'>Real Lean Fitness</a> by <a href='www.DGXSoftware.com'>DGXSoftware</a></p>" +
						"                </div>" +
						"      " +
						"     </div>" +
						"  " +
						"</body>" +
						"</html>";
		
		return EMailEnd;
		
	}
	
	
	public static String GoogleEMail(){
		
		String GoogleEMail = "";
		
		GoogleEMail =
				"<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1//EN' 'http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd'>" +
						"<html xmlns='http://www.w3.org/1999/xhtml' xmlns='http://www.w3.org/1999/xhtml' style='text-align: center; margin: 0 auto;'>" +
						"  <head>" +
						"    <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
						"    <link rel='stylesheet' type='text/css' href='http://www.DGXSoftware.com/RLF/JSP/Mail/CSS/MailStyle.css' />" +
						"    <title>Real Lean Fitness</title>" +
						"  </head>" +
						"  <body style='text-align: left; font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/background.png'); background-color: #FFFFFF; margin: 0; padding: 0;' bgcolor='#FFFFFF'>&#13;" +
						"    <div style='text-align: left; width: 581px; margin: 0 auto;' align='left'>&#13;" +
						"    	        <div style='text-align: left; width: 581px; height: 90px; clear: both; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/header.png'); margin: 0 auto;' align='left'>&#13;" +
						"				    &#13;" +
						"                	&#13;" +
						"					<h1 style='padding-top: 50px; text-align: center; color: #FFFFFF; margin: 0 auto;' align='center'>Real Lean Fitness</h1>&#13;" +
						"                </div>&#13;" +
						"    &#13;" +
						"                </div>&#13;" +
						"&#13;" +
						"                <div style='text-align: left; width: 581px; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/content_back_shadow.png'); background-repeat: repeat-y; margin: 0 auto;' align='left'>&#13;" +
						"                	<div style='text-align: left; float: left; width: 547px; clear: both; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/gradient_rectangle_back.png'); background-repeat: repeat-x; background-color: #4586F1; margin: 0 auto 0 14px;' align='left'>&#13;" +
						"&#13;" +
						"                        <div style='text-align: left; display: block; width: 527px; clear: both; margin: 0 auto;' align='left'>&#13;" +
						"                        <div style='text-align: left; height: 54px; width: 527px; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/menu_background.png'); margin: 0 auto;' align='left'>&#13;" +
						"                            <ul style='text-align: center; height: 40px; list-style-type: none; margin: 0 auto; padding: 0px;'></ul></div>&#13;" +
						"                        <div style='text-align: left; width: 497px; display: block; float: left; min-height: 400px; clear: both; background-color: #FFFFFF; margin: 0; padding: 0 15px;' align='left'>&#13;" +
						"						&#13;" +
						"                       		<h1 style='text-align: left; color: #093175; font-size: 24px; margin: 7px 0; padding: 0;' align='left'>Password Change:</h1>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>To reset your password, please visit the following page:</p>&#13;" +
						"							<a href='www.RealLeanFitness.com?do=fpwdc&amp;usr=1000000000&amp;key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db' style='text-align: left; margin: 0 auto;'>www.RealLeanFitness.com?do=fpwdc&amp;usr=1000000000&amp;key=965c21ae0dde31bc1c488b49ef08e93fbd1ab3db</a>&#13;" +
						"							 &#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'> </p>&#13;" +
						"							<p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>When you visit that page, you will be able to change your password. Upon a successful password change a confirmation E-Mail will be sent to you.</p>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'> </p>&#13;" +
						"							<p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>Your username is: MegamanX</p>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'> </p>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>To edit your profile, go to this page:</p>&#13;" +
						"							<a href='www.RealLeanFitness.com?do=editprofile' style='text-align: left; margin: 0 auto;'>www.RealLeanFitness.com?do=editprofile</a>&#13;" +
						"						    <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'> </p>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>Thanks for helping us maintain the security of your account.</p>&#13;" +
						"							<p style='text-align: left; margin: 5px auto; padding: 0;' align='left'> </p>&#13;" +
						"                            <p style='text-align: left; margin: 5px auto; padding: 0;' align='left'>~ The RealLeanFitness Support Team</p>&#13;" +
						"							<a href='www.RealLeanFitness.com?do=contactsupport' style='text-align: left; margin: 0 auto;'>www.RealLeanFitness.com?do=contactsupport</a>&#13;" +
						"						&#13;" +
						"                        </div>&#13;" +
						"&#13;" +
						"                        </div>&#13;" +
						"                </div>&#13;" +
						"                <div style='text-align: left; clear: both; margin: 0 auto;' align='left'></div>&#13;" +
						"                <div style='text-align: right; width: 581px; height: 60px; font-size: 11px; color: #C5DDFA; background-image: url('http://www.dgxsoftware.com/RLF/JSP/Mail/Images/footer.png'); background-repeat: no-repeat; margin: 0 auto;' align='right'>&#13;" +
						"                	<p style='text-align: center; padding-top: 20px; margin: 0 auto;' align='center'><a href='www.RealLeanFitness.com' style='text-align: left; font-size: 11px; color: #C5DDFA; text-decoration: none; margin: 0 auto;'>Real Lean Fitness</a> by <a href='www.DGXSoftware.com' style='text-align: left; font-size: 11px; color: #C5DDFA; text-decoration: none; margin: 0 auto;'>DGXSoftware</a></p>&#13;" +
						"                </div>&#13;" +
						"      &#13;" +
						"     </div>&#13;" +
						"  &#13;" +
						"</body>" +
						"</html>";
		
		
		return GoogleEMail;
	}
	
	
}
