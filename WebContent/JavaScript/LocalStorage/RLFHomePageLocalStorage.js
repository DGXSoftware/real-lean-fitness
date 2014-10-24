/* ======================================================================================================================================= */
/* REQUIRED USER INPUT */
/* ======================================================================================================================================= */

// Declare the appropriate Local Storage Field ID Lists
var UserFieldRememberListIDs  = ["LoginUsername", "LoginPassword"];
var UserRememberMeCheckBoxID = document.getElementById("LoginRememberMe");
var UserSubmitButtonName = "LoginButton";

/* ======================================================================================================================================= */
/* SETUP CODE: Requires user Input and jQUERY (<script src="/JavaScript/jquery.js</script>) */
/* ======================================================================================================================================= */

      // Use JQuery to execute JavaScript code as soon as the page loads
      $(document).ready(function(){
    	  
    	  // If the Last Submit Set  the Checkbox To Remember Check the Checkbox by default
    	  isLocalStorageFound();
    	  
    	  // Retrieve the Appropriate Credentials from Local Storage 
    	  getLocalStorageCredentials(UserFieldRememberListIDs);
    	  
    	  // On Click Manage the Local Storage (Remember or Forget Credentials)
    	  $("#"+ UserSubmitButtonName).click(function(){
    		  
    			// Manage Local Storage to Remember or Forget Credentials
    		    manageLocalStorageSubmit();
    	    }); 

    	  
      });

/* ======================================================================================================================================= */
/* Local Storage Managing Methods */
/* ======================================================================================================================================= */

/* Method that Manage Local Storage (Setting and Clearing) */
function manageLocalStorageSubmit(){

    // if the User wishes to have his credentials Remembered then store them in Local Storage 
    if (UserRememberMeCheckBoxID.checked) {
    
    // Handle Submits With a Checked Remember Me
    localStorage.setItem('RLF.LastSubmitSetToRemember', 'Remember These Credentials');
    setLocalStorageCredentials(UserFieldRememberListIDs);
    
    } else {
    	
    // Handle Submits With an Unchecked Remember Me
    localStorage.removeItem('RLF.LastSubmitSetToRemember');
    clearLocalStorageCredentials(UserFieldRememberListIDs);
    }


}

/* Save the appropriate Credentials to the Local Storage */
function setLocalStorageCredentials(){
	
	// Loop through all the UserFieldRememberListIDs Entries and Save all approproiate field values to Local Storage
	for (var i = 0; i < UserFieldRememberListIDs.length; i++) { 
		var FieldTempValue = $('#' + UserFieldRememberListIDs[i]).val();
		// If FieldTempValue is NOT Empty (Empty String or NULL)
		if(FieldTempValue){
			localStorage.setItem('RLF.' + UserFieldRememberListIDs[i], $('#' + UserFieldRememberListIDs[i]).val());
	    }
	 }
	
}

/* Retrieve the appropriate Credentials from the Local Storage */
function getLocalStorageCredentials(){
	
	// Loop through all the UserFieldRememberListIDs Entries and Retrieve all approproiate Local Storage values and set them to the proper fields.
	for (var i = 0; i < UserFieldRememberListIDs.length; i++) {
		var LocalStorageTempValue = localStorage.getItem('RLF.' + UserFieldRememberListIDs[i]);
		// If LocalStorageTempValue is NOT Empty (Empty String or NULL)
		if(LocalStorageTempValue){
	    	document.getElementById(UserFieldRememberListIDs[i]).value = LocalStorageTempValue; 
	    }
	 }
	
}

/* Remove the appropriate Credentials from the Local Storage */
function clearLocalStorageCredentials(){
	
	
	// Loop through all the UserFieldRememberListIDs Entries and Remove all approproiate Local Storage values
	for (var i = 0; i < UserFieldRememberListIDs.length; i++) {
		var LocalStorageTempValue = localStorage.getItem('RLF.' + UserFieldRememberListIDs[i]);
		// If LocalStorageTempValue is NOT Empty (Empty String or NULL)
		if(LocalStorageTempValue){
			localStorage.removeItem('RLF.' + UserFieldRememberListIDs[i]);
	    }
	 }
	
}

/* If the Last Submit Set  the Checkbox To Remember Check the Checkbox by default */
function isLocalStorageFound(){

	var LocalStorageTempValue = localStorage.getItem('RLF.LastSubmitSetToRemember');

	if(LocalStorageTempValue){
		// Check the Checkbox
		UserRememberMeCheckBoxID.checked = true;
	}else{
		// Uncheck the Checkbox
		UserRememberMeCheckBoxID.checked = false;
	}

}

/* ======================================================================================================================================= */
/* ======================================================================================================================================= */
