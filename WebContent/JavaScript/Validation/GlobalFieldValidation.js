
// NAME: isEmptyField
// DESCRIPTION: Tests if the value of a field is empty.
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
// RETURN: true = Field is Empty
//         false = Field is NOT Empty
function isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) {

	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}
	
	// If the Current Field is Empty, set it back to Its default state, and let the caller know if It's empty
	// Act upon Empty validation Results
    // true = Field is Empty
    // false = Field is NOT Empty
    if (CurrentTextFieldObject.value.length == 0) {
    	//CurrentTextFieldObject.style.background = "white";
    	CurrentTextFieldObject.style.backgroundColor="";
    	CurrentTextFieldIconObject.style.visibility = "hidden";
    	if (AlertOnBadInput == true){
    		$('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>You didn't enter a "+CurrentTextFieldName+".</div>");
    		CurrentTextFieldObject.style.background = "yellow"; 
        }
    	return true;
    }else{return false;}
}

//NAME: isValidNameField
//DESCRIPTION: Tests if the value of a field is a Valid Name Field (Examples; First Name and Last Name fields)
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//            IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//      false = Field is NOT Valid
function isValidNameField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, IsEmptyPermitted){

	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}

	// Define Illegal Characters
	// Only Allow Letters and Spaces
	// NOTE: Adding quotes to the Regex below will mess things up!
	var IllegalCharacters = new RegExp("^[A-Za-z]+$");
    
	// Validate the Current Field
	if(IllegalCharacters.test(CurrentTextFieldObject.value)){
	// Return Valid
	CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
	CurrentTextFieldIconObject.style.visibility = "visible";
	if (AlertOnBadInput == true){
	$('#'+CurrentFeedbackDivID+'').empty();
	CurrentTextFieldObject.style.background = "white";
	}
	return true;
	//Return Valid
	}else {
	//Return Invalid
	 CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
	 CurrentTextFieldIconObject.style.visibility = "visible";
	 if (AlertOnBadInput == true){
	 $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>Invalid "+CurrentTextFieldName+".</div>");
     CurrentTextFieldObject.style.background = "yellow"; 
	 }
	 return false;
	//Return Invalid
	}
	

}

//NAME: isValidUsername
//DESCRIPTION: Tests if the value of a field is a Valid Username
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//            IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//    false = Field is NOT Valid
function isValidUsername(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, IsEmptyPermitted) {

	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}

    // Define Illegal Characters
	// Only Allow Letters, Numbers, and Underscores
    var IllegalCharacters = new RegExp("^[a-zA-Z0-9_]+(,[a-zA-Z0-9_]+)*$");
    var MinUsernameLength = 4;
    var MaxUsernameLength = 32;
    
    if ((CurrentTextFieldObject.value.length < MinUsernameLength) || (CurrentTextFieldObject.value.length > MaxUsernameLength)) {
      //Return Invalid
        CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
        CurrentTextFieldIconObject.style.visibility = "visible";
        if (AlertOnBadInput == true){
        $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>The "+CurrentTextFieldName+" has an invalid length.</div>");
        CurrentTextFieldObject.style.background = "yellow"; 
        }
        return false;
      //Return Invalid
    } else if(!IllegalCharacters.test(CurrentTextFieldObject.value)){
      //Return Invalid
        CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
        CurrentTextFieldIconObject.style.visibility = "visible";
        if (AlertOnBadInput == true){
        $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>The "+CurrentTextFieldName+" contains illegal characters.</div>");
        CurrentTextFieldObject.style.background = "yellow"; 
        }
        return false;
      //Return Invalid
    } else {
      // Return Valid
    	CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
    	CurrentTextFieldIconObject.style.visibility = "visible";
    	if (AlertOnBadInput == true){
    	$('#'+CurrentFeedbackDivID+'').empty();
    	CurrentTextFieldObject.style.background = "white";
    	}
    	return true;
     //Return Valid	
    }
}

//NAME: isValidPassword
//DESCRIPTION: Tests if the value of a field is a Valid Password
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//            IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//  false = Field is NOT Valid
function isValidPassword(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, IsEmptyPermitted) {

	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}
	
	/* DISABLED; Password will allow any characters
    // Define Illegal Characters
	// Only Allow Letters, Numbers, and Underscores
	// NOTE: Adding quotes to the Regex below will mess things up!
    var IllegalCharacters = new RegExp("^[a-zA-Z0-9_]+(,[a-zA-Z0-9_]+)*$");
	*/
    var MinPasswordLength = 6;
    var MaxPasswordLength = 32;

    
    if ((CurrentTextFieldObject.value.length < MinPasswordLength) || (CurrentTextFieldObject.value.length > MaxPasswordLength)) {
       //Return Invalid
        CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
        CurrentTextFieldIconObject.style.visibility = "visible";
        if (AlertOnBadInput == true){
        $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>The "+CurrentTextFieldName+" chosen contains an invalid length.</div>");
        CurrentTextFieldObject.style.background = "yellow"; 
        }
        return false;
      //Return Invalid
    /* DISABLED; Password will allow any characters
    } else if(!IllegalCharacters.test(CurrentTextFieldObject.value)){
        //Return Invalid
        CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
        CurrentTextFieldIconObject.style.visibility = "visible";
        if (AlertOnBadInput == true){
        $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>The "+CurrentTextFieldName+" chosen contains illegal characters.</div>");
        CurrentTextFieldObject.style.background = "yellow"; 
        }
        return false;
      //Return Invalid
    */  
    } else if (!((CurrentTextFieldObject.value.search("/(a-z)+/")) && (CurrentTextFieldObject.value.search("/(0-9)+/")))) {
        //Return Invalid
        CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
        CurrentTextFieldIconObject.style.visibility = "visible";
        if (AlertOnBadInput == true){
        $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>The "+CurrentTextFieldName+" chosen must contain at least one numeral.</div>");
        CurrentTextFieldObject.style.background = "yellow"; 
        }
        return false;
      //Return Invalid
    } else {
       // Return Valid
    	CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
    	CurrentTextFieldIconObject.style.visibility = "visible";
    	if (AlertOnBadInput == true){
    	$('#'+CurrentFeedbackDivID+'').empty();
    	CurrentTextFieldObject.style.background = "white";
    	}
    	return true;
      //Return Valid	
    }

}

//NAME: isValidEmail
//DESCRIPTION: Tests if the value of a field is a Valid EMail (Validates using the "RFC822" E-Mail rules)
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//          CurrentTextFieldIconID = ID attribute value for the field icon
//          CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//          IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//false = Field is NOT Valid
function isValidEmail(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, IsEmptyPermitted) {
	
	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}

//Get the E-Mail
var sEmail = CurrentTextFieldObject.value;

// START E-Mail Validation
var sQtext = '[^\\x0d\\x22\\x5c\\x80-\\xff]';
var sDtext = '[^\\x0d\\x5b-\\x5d\\x80-\\xff]';
var sAtom = '[^\\x00-\\x20\\x22\\x28\\x29\\x2c\\x2e\\x3a-\\x3c\\x3e\\x40\\x5b-\\x5d\\x7f-\\xff]+';
var sQuotedPair = '\\x5c[\\x00-\\x7f]';
var sDomainLiteral = '\\x5b(' + sDtext + '|' + sQuotedPair + ')*\\x5d';
var sQuotedString = '\\x22(' + sQtext + '|' + sQuotedPair + ')*\\x22';
var sDomain_ref = sAtom;
var sSubDomain = '(' + sDomain_ref + '|' + sDomainLiteral + ')';
var sWord = '(' + sAtom + '|' + sQuotedString + ')';
var sDomain = sSubDomain + '(\\x2e' + sSubDomain + ')*';
var sLocalPart = sWord + '(\\x2e' + sWord + ')*';
var sAddrSpec = sLocalPart + '\\x40' + sDomain; // complete RFC822 email address spec
var sValidEmail = '^' + sAddrSpec + '$'; // as whole string

var reValidEmail = new RegExp(sValidEmail);
//END E-Mail Validation

// Act upon E-Mail validation Results
// true = Valid E-Mail
// false = Invalid E-Mail
if (reValidEmail.test(sEmail)) {
// Return Valid
CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
CurrentTextFieldIconObject.style.visibility = "visible";
if (AlertOnBadInput == true){
$('#'+CurrentFeedbackDivID+'').empty();
CurrentTextFieldObject.style.background = "white";
}
return true;
//Return Valid
}else{
//Return Invalid
CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
CurrentTextFieldIconObject.style.visibility = "visible";
if (AlertOnBadInput == true){
$('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>Invalid "+CurrentTextFieldName+".</div>");
CurrentTextFieldObject.style.background = "yellow"; 
}
return false;
//Return Invalid
}


}

//NAME: isValidEmail
//DESCRIPTION: Tests if the value of a field is a Valid EMail (Validates using the "RFC822" E-Mail rules)
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//            CurrentTextFieldToConfirmID = ID attribute value for the Field that has to be confirmed
//            IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//false = Field is NOT Valid
function isValidConfirmation(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, CurrentTextFieldToConfirmID, IsEmptyPermitted) {
	
	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
    var CurrentTextFieldToConfirmObject = document.getElementById(CurrentTextFieldToConfirmID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
    
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}
	
	// Validate the Current Field
	if(CurrentTextFieldObject.value === CurrentTextFieldToConfirmObject.value){
	// Return Valid
	CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
	CurrentTextFieldIconObject.style.visibility = "visible";
	if (AlertOnBadInput == true){
	$('#'+CurrentFeedbackDivID+'').empty();
	CurrentTextFieldObject.style.background = "white";
	}
	return true;
	//Return Valid
	}else {
	//Return Invalid
	 CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
	 CurrentTextFieldIconObject.style.visibility = "visible";
	 if (AlertOnBadInput == true){
	 $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>Invalid "+CurrentTextFieldName+".</div>");
     CurrentTextFieldObject.style.background = "yellow"; 
	 }
	 return false;
	//Return Invalid
	}
	
}

//NAME: isValidDropDownField
//DESCRIPTION: Tests if the value of a field is a Valid Drop-Down Selection
//PARAMETERS: CurrentTextFieldID = ID attribute value for the field
//            CurrentTextFieldIconID = ID attribute value for the field icon
//            CurrentFeedbackDivID = ID attribute value for the Summary Feedback DIV
//            IsEmptyPermitted = Decide if this is a required field (true = Optional Field, false = Required Field)
//RETURN: true = Field is Valid
//false = Field is NOT Valid
function isValidDropDownField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID, IsEmptyPermitted){

	// If the Current Field is Empty, set it back to Its default state, and stop further processing
	if(IsEmptyPermitted == false){
		if (isEmptyField(CurrentTextFieldID, CurrentTextFieldIconID, CurrentFeedbackDivID) === true){ return false;}
	}
	
	// Get the Field and Field Icon Objects
	var CurrentTextFieldObject = document.getElementById(CurrentTextFieldID);
	var CurrentTextFieldIconObject = document.getElementById(CurrentTextFieldIconID);
	
	// Get the Field Name
	// Also, find each occurrence of a lower case character followed by an upper case character, and insert a space between them.
	// Example; The text "HelloBeautifulWorld" will become "Hello Beautiful World" 
	var CurrentTextFieldName = CurrentTextFieldObject.name.replace(/([a-z])([A-Z])/g, "$1 $2");
	
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}
	
	// Validate the Current Field
	if(CurrentTextFieldObject.options[CurrentTextFieldObject.selectedIndex].value !== ""){
	// Return Valid
	CurrentTextFieldIconObject.src = "/Images/Icons/Valid/Valid(16x16).png";
	CurrentTextFieldIconObject.style.visibility = "visible";
	if (AlertOnBadInput == true){
	$('#'+CurrentFeedbackDivID+'').empty();
	CurrentTextFieldObject.style.background = "white";
	}
	return true;
	//Return Valid
	}else {
	//Return Invalid
	 CurrentTextFieldIconObject.src = "/Images/Icons/Invalid/Invalid(16x16).png";
	 CurrentTextFieldIconObject.style.visibility = "visible";
	 if (AlertOnBadInput == true){
	 $('#'+CurrentFeedbackDivID+'').html("<div id='RegistrationErrorFeedbackDiv'>Invalid "+CurrentTextFieldName+".</div>");
     CurrentTextFieldObject.style.background = "yellow"; 
	 }
	 return false;
	//Return Invalid
	}
	

}
