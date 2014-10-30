<!DOCTYPE html>

<% response.addHeader("Cache-Control", "no-cache"); %>

<html>
	<head>
	
	<meta charset='utf-8'>
	<title>Submit Form</title>
	<script type='text/javascript' src='/JavaScript/Validation/GlobalFieldValidation.js?<%= Math.random() %>' > </script>
	<script type='text/javascript' src="/JavaScript/JQuery/jquery.js"></script>

<style>
body { 
	background-color: #dcdcdc ;
}
</style>

<b>DGX</b>	
<br/>
<b><%= Math.random() %></b>	
	
	<script>
	
	function SubmitForm(f){

        //If the First Name is not valid Alert so, and stop further processing
        if(!isValidNameFieldAlert('FirstName','FirstNameIcon','',false)){ return false; }
        
        //If the Last Name is not valid Alert so, and stop further processing
        if(!isValidNameFieldAlert('LastName','LastNameIcon','',false)){ return false; }
        
        //If the Username is not valid Alert so, and stop further processing
        if(!isValidUsernameAlert('Username','UsernameIcon','',false)){ return false; }
        
        //If the Password is not valid Alert so, and stop further processing
        if(!isValidPasswordAlert('Password','PasswordIcon','',false)){ return false; }
        
        //If the EMail is not valid Alert so, and stop further processing
        if(!isValidEMailAlert('EMail','EMailIcon','',false)){ return false; }
       
        //If the Role is not valid Alert so, and stop further processing
        if(!isValidDropDownFieldAlert('Role','RoleIcon','',false)){ return false; }
        
	}
	
	</script>
	
	</head>

<body>
  
  <form name="MyForm" action="">
  
  
<label for="FirstName">
<input type="text" id="FirstName" name="FirstName" onKeyUp="isValidNameField('FirstName','FirstNameIcon','',false);" />
<img id="FirstNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>

<br/>
<label for="LastName">
<input type="text" id="LastName" name="LastName" onKeyUp="isValidNameField('LastName','LastNameIcon','',false);" />
<img id="LastNameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>

<br/>
<label for="Username">
<input type="text" id="Username" name="Username" onKeyUp="isValidUsername('Username','UsernameIcon','',false);" />
<img id="UsernameIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>

<br/>
<label for="Password">
<input type="password" id="Password" name="Password" onKeyUp="isValidPassword('Password','PasswordIcon','',false);" />
<img id="PasswordIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>

<br/>
<label for="EMail">
<input type="text" id="EMail" name="EMail" onKeyUp="isValidEMail('EMail','EMailIcon','',false);" />
<img id="EMailIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>

<br/>
<label for="Role">
<select id="Role" name="Role" onChange="isValidDropDownField('Role','RoleIcon','',false);" >
<option value="">I am...</option>
<option value="Programmer">Programmer</option>
<option value="Manager">Manager</option>
<option value="Director">Director</option>
</select>
<img id="RoleIcon" src="/Images/Icons/Valid/Valid(16x16).png" style="visibility:hidden;" />
</label>
              
        <input type="button" name="MyButton" Value="Submit" onClick="SubmitForm(document.MyForm);" />
        
  </form>
</body>
                    
</html>

<!-- TESTING CREDENTIALS  -->
<script>
      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
        // TEST STUFF
        document.getElementById("FirstName").value = "Dalvis";
        document.getElementById("LastName").value = "Gomez";
        document.getElementById("Username").value = "DGXFAN";
        document.getElementById("Password").value = "DGXFAN";
        document.getElementById("EMail").value = "DGX@VZW.com";
        document.getElementById("Role").selectedIndex = "1";
        
      });
</script>