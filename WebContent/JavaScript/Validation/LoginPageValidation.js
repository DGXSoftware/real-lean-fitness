
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

/* This method validates that the Element given has a numeric value */
function validateNumericValue(CurrentElement, ElementPrompt, NonNumericErrorMessage){
 
// Check if the value of the element provided is Empty
if (document.getElementById(CurrentElement).value === ""){
 
// If the Element is Empty display an alert box with the prompt provided
alert(ElementPrompt);
 
// Place the cursor on the Element for revision
document.getElementById(CurrentElement).focus();
 
// return false to stop further processing
return (false);
 
}

// Check if the value of the element provided is a Not a Numeric Value
if (isNaN(Number(document.getElementById(CurrentElement).value))){
 
// If the Element is Empty display an alert box with the prompt provided
alert(NonNumericErrorMessage);
 
// Place the cursor on the Element for revision
document.getElementById(CurrentElement).focus();
 
// return false to stop further processing
return (false);
 
}
 
}

/* This method validates the Radio Button when provided an Element ID */
function validateRadioButton(CurrentElement, ElementForm ,ElementPrompt){

// Get a reference to the Radio Element
//var radios = document.getElementById(CurrentElement);
var radios = document[ElementForm].elements[CurrentElement];
 
// Loop through all the Options on the Radio Element
for (var i=0; i <radios.length; i++) {

// Check if any of the Radio Options are Checked
if (radios[i].checked) {
 
// return true to let the caller know that the Radio Element has a Checked Option
return (true);
 
}

}

}
