/* This method is used to test the JavaScript File  */
function testJS(){
 
alert("The JavaScript File is Working!");
 
}

 
/* This method validates the TextField when provided an Element ID */
function validateTextField(CurrentElement, ElementPrompt){
 
// Check if the value of the element provided is Empty
if (document.getElementById(CurrentElement).value == ""){
 
// If the Element is Empty display an alert box with the prompt provided
alert(ElementPrompt);
 
// Place the cursor on the Element for revision
document.getElementById(CurrentElement).focus();
 
// return false to stop further processing
return (false);
 
}
 
}

/* This method calls all the methods to validate the Input entered by the user */
function validateLoginFormInput(){
 
// Validate the Login Form Input. If any of the validateTextField methods return False, stop any further processing
if(validateTextField("LoginUsername", "Please enter your Username.") == false) return false;
if(validateTextField("LoginPassword", "Please enter your Password.") == false) return false;

 
}

 
