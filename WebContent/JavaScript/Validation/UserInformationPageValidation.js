/* This method is used to test the JavaScript File  */
function testJS(){
 
alert("The JavaScript File is Working!");
 
}

/* This method validates the TextField when provided an Element ID */
function validateTextField(CurrentElement, ElementPrompt){
 
// Check if the value of the element provided is Empty
if (document.getElementById(CurrentElement).value === ""){
 
// If the Element is Empty display an alert box with the prompt provided
alert(ElementPrompt);
 
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

// If the Element is NOT Empty display an alert box with the prompt provided
alert(ElementPrompt);

// Place the cursor on the Element for revision
//document.getElementById(CurrentElement).focus();
document[ElementForm].elements[CurrentElement][0].focus();


// return false to stop further processing
return (false);

}

/* This method validates the validate Drop-Down Menu when provided an Element ID */
function validateDropDownMenu(CurrentElement, ElementForm ,ElementPrompt){
 
// Check if Index 0 is selected for this Drop-Down Menu
if(document[ElementForm].elements[CurrentElement][0].selected){
 
// if Index 0 is selected for this Drop-Down Menu display an alert box with the prompt provided
alert(ElementPrompt);
 
// Place the cursor on the Element for revision
//document.getElementById(CurrentElement).focus();
document[ElementForm].elements[CurrentElement][0].focus();
 
// return false to stop further processing
return (false);
 
}
 
}

/* This method validates the Confirmation when provided two Element IDs */
function validateConfirmation(CurrentElement, ConfirmationElement, ElementPrompt, ConfirmationErrorMessage){
 
// Check if the value of the element provided is Empty
if (document.getElementById(ConfirmationElement).value === ""){
 
// If the Element is Empty display an alert box with the prompt provided
alert(ElementPrompt);
 
// Place the cursor on the Element for revision
document.getElementById(ConfirmationElement).focus();
 
// return false to stop further processing
return (false);
 
}

// If CurrentElement is not equal to ConfirmationElement display the ConfirmationErrorMessage
if(document.getElementById(CurrentElement).value !== document.getElementById(ConfirmationElement).value){

// If the Element is Empty display an alert box with the prompt provided
alert(ConfirmationErrorMessage);
 
// Place the cursor on the Element for revision
document.getElementById(ConfirmationElement).focus();
 
// return false to stop further processing
return (false);

}
 
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

/* This method calls all the methods to validate the Input entered by the user */
function validatePersonalInformationFormInput(){

if(validateTextField("RegistrationFirstName", "Please enter your First Name.") === false) { return false; }
if(validateTextField("RegistrationMiddleName", "Please enter your Middle Name.") === false) { return false; }
if(validateTextField("RegistrationLastName", "Please enter your Last Name.") === false) { return false; }
if(validateTextField("RegistrationAddress", "Please enter your Address.") === false) { return false; }
if(validateTextField("RegistrationCity", "Please enter your City.") === false) { return false; }
if(validateTextField("RegistrationState", "Please enter your State.") === false) { return false; }
if(validateNumericValue("RegistrationZipCode", "Please enter your Zip Code.", "Please enter a numeric Zip Code value.") === false) { return false; }
if(validateDropDownMenu("RegistrationCountry","PersonalInformationForm","Please enter your Country.") === false) { return false; }

}

