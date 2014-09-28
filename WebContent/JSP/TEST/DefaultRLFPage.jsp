
		<?xml version = '1.0'?>
		<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
		
		<!-- The xmlns attribute is required in XHTML but it is invalid in HTML. -->
		<!-- the namespace 'xmlns=http://www.w3.org/1999/xhtml' is default, and will be added to the <html> tag in XHTML even if you do not include it. -->
		<html xmlns='http://www.w3.org/1999/xhtml'>
		
		<head>
		
		<!-- Set the Title for the Website Page -->
		<title>RLF Default JSP></title>
		
		<!-- Set the Favicon for the Website page -->
		<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>
		
		<!-- Set the Character Encoding for the Website page -->
		<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />
		
		<!-- Include the Stylesheet Files -->
		<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />
		
		<style>
		#online_title { font-size: 20px; color: green; text-align:center; }
		#offline_title { font-size: 20px; color: red; text-align:center; }
		#BackButton { font-size: 20px; color: red; text-align:center; }
		</style>
		
		</head>

<body>
<div id="container">
	<div id="main">
		<div id="header"></div>

		<div id="content">

				<div class="post">
					<p align="left"></p>

<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
<div id="online_title">RLF Default JSP</div>
<p>&#160;</p>


<!-- Create a MailJavaBean Object -->
<jsp:useBean id="MyMailJavaBean" scope="page" class="dgx.software.com.JavaBeanPackage.PayPalJavaBean" />

<%

//Set the E-Mail Body Message
MyMailJavaBean.printContextParameters();

%>




<p>&#160;</p>
<p>&#160;</p>
<p>&#160;</p>
			
				</div>

			<div class="clear"></div>
		</div>
	</div>
	<div id="footer"><span>Copyright © 2014</span></div>
</div>
</body>
</html>