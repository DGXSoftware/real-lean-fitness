/* This method is used to test the JavaScript File  */
function testJS(){

alert("The JavaScript File is Working!");

}

/* Generate and return a Random Captcha Code */
function getRandomCaptcha(){

// Create an Empty String Variable to hold the Random Captcha Code
var RandomCaptchaCode = '';

// Create the list of possible characters for the Captcha
var CharacterList = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890';

// Set the Minimum Length of the Captcha Code
var MinimumLength = 6;

// Set the Maximum Length of the Captcha Code
var MaximumLength = 6;

// Choose a Random Length between the Minimum Length and Maximum Length
var RandomLength = Math.round(Math.random() * (MaximumLength - MinimumLength) + MinimumLength);

// Populate the Random Captcha Code using the Character List and Random Length
for(i=0; i < RandomLength; i++){
RandomCaptchaCode += CharacterList.charAt(Math.floor(Math.random() * CharacterList.length));
}

// Set the RandomCaptchaCode for Display
document.getElementById("CaptchaDisplay").innerHTML = RandomCaptchaCode;

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

/* This method validates and compares the User Captcha Input by Matching it to the correct value */
function validateSenderCaptchaInput(CurrentElement, ElementPrompt, ElementToMatch, ElementMatchErrorMessage){

// The If Statements makes sure that the user Inputs some Text in the Current Element
if (document.getElementById(CurrentElement).value == ""){

// If the Element is Empty display an alert box with the prompt provided
alert(ElementPrompt);

// Place the cursor on the Element for revision
document.getElementById(CurrentElement).focus();

// return false to stop further processing
return (false);

}

// Check if the User Input was correct by comparing it to the ElementToMatch
if(document.getElementById(CurrentElement).value != document.getElementById(ElementToMatch).innerHTML ){

// If the user entered the incorrect Captcha Code send an alert using the ElementMatchErrorMessage provided
alert(ElementMatchErrorMessage);

// Place the cursor on the Element for revision
document.getElementById(CurrentElement).focus();

// return false to stop further processing
return (false);

}

}

/* This method calls all the methods to validate the Input entered by the user */
function validateContactMeFormInput(){

// Validate the Contact Me Form Input. If any of the validateTextField methods return False, stop any further processing
if(validateTextField("SenderName", "Please provide your Name.") == false) return false;
if(validateTextField("SenderEMail", "Please provide your E-Mail.") == false) return false;
if(validateTextField("SenderMessage", "Please provide a Message.") == false) return false;

// If the validateSenderCaptchaInput method returns False, stop further processing
if(validateSenderCaptchaInput("CaptchaCodeUserInput", "Please enter the Captcha Code.", "CaptchaDisplay", "Incorrect Captcha Code. Please try again.") == false) return false;

}
