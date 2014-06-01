<?xml version = '1.0'?>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>

<head>

<!-- Set the Title for the Website Page -->
<title>User Profile Servlet</title>

<!-- Set the Favicon for the Website page -->
<link rel='Shortcut Icon' type='image/ico' href='/Images/favicon.ico'/>

<!-- Set the Character Encoding for the Website page -->
<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />

<!-- Include the Stylesheet Files -->
<link rel='stylesheet' type='text/css' href='/CSS/RLFStyle.css' />

<!-- Include the JavaScript Files -->
<script language='javascript' type='text/javascript' src='/JavaScript/Validation/User Information Page Validation.js' > </script>

</head>

<body>
<div id='container'>
<div id='main'>
<div id='header'></div>
<div id='nav'>
<ul>
<!-- START DYNAMIC HTML -->
<li><a href='/UserProfileServlet'>1</a></li>
<!-- END DYNAMIC HTML -->
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='/UserInformationServlet'>Edit Profile</a></li>
<li><a href='/LogOutServlet'>Log Out</a></li>
</ul>
</div>
<div id='content'>
<div id='left'>
<div class='post'>
<h1>Profile Information</h1>

<p align='left'>&#160;</p>

<form action='' method='post' id='UserInformationForm' name='UserInformationForm'>

<!-- START DYNAMIC HTML -->
<p> First Name: </p> <input type='text' id='First_Name' name='First_Name' title='First Name' size='32' value='' />
<p> Middle Name: </p> <input type='text' id='Middle_Name' name='Middle_Name' title='Middle Name' size='32' value='' />
<p> Last Name: </p> <input type='text' id='Last_Name' name='Last_Name' title='Last Name' size='32' value='' />
<p> Location Address: </p> <input type='text' id='Location_Address' name='Location_Address' title='Location Address' size='32' value='' />
<p> Location City: </p> <input type='text' id='Location_City' name='Location_City' title='Location City' size='32' value='' />
<p> Location State: </p> <input type='text' id='Location_State' name='Location_State' title='Location State' size='32' value='' />
<p> Location ZipCode: </p> <input type='text' id='Location_ZipCode' name='Location_ZipCode' title='Location ZipCode' size='32' value='' />
<p> Location Country: </p> <input type='text' id='Location_Country' name='Location_Country' title='Location Country' size='32' value='' />
<!-- END DYNAMIC HTML -->

<br />
<br />
<br />
 
<input type='submit' id='SaveInformationButton' name='SaveInformationButton' value = 'Save Information' />
 
</form>

</div>

</div>


<div class='clear'></div>
</div>
</div>

<div id='footer'>
<ul>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
<li><a href='#'></a></li>
</ul>
<span>Copyright © 2012</span>
</div>
</div>
</body>
</html>
