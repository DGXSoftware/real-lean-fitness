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
function validateContactMeFormInput(){

// Validate the Contact Me Form Input. If any of the validateTextField methods return False, stop any further processing
if(validateTextField("SenderFirstName", "Please provide your First Name.") == false) return false;
if(validateTextField("SenderEMail", "Please provide your E-Mail.") == false) return false;
if(validateTextField("SenderSubject", "Please provide a Subject.") == false) return false;
if(validateTextField("SenderMessage", "Please provide a Message.") == false) return false;

}
