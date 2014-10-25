
// Re-Validate all the fields and stop further processing if any of them fail validation (Return False)
function validateFormOnSubmit(CurrentFeedbackDivID) {

    // Assume that all validations succeeded	
	var ValidationSucceeded = true;
    var RegistrationErrorFeedbackDivName = "RegistrationErrorFeedbackDiv";
    var SubRegistrationErrorFeedbackDiv = "SubRegistrationErrorFeedbackDiv";
    var ArrayOfValidationFailures = [];
    
	// If a Form Feedback Div Name is provided set it so that this method alerts about Invalid input in the Feedback Div.
	var AlertOnBadInput = null;
	if (CurrentFeedbackDivID === ''){
	AlertOnBadInput = false;
	}else {
	AlertOnBadInput = true;
	}
	
	// Re-Validate all the fields and stop further processing if any of them fail validation (Return False)
	// NOTE: We're providing the CurrentFeedbackDivID to report what failed to validate
	if(isValidNameField('RegistrationFirstName','RegistrationFirstNameIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidNameField('RegistrationLastName','RegistrationLastNameIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidUsername('RegistrationUsername','RegistrationUsernameIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidPassword('RegistrationPassword','RegistrationPasswordIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidEMail('RegistrationEMail','RegistrationEMailIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidConfirmation('ConfirmationRegistrationEMail','ConfirmationRegistrationEMailIcon',CurrentFeedbackDivID,'RegistrationEMail',false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidDropDownField('RegistrationGender','RegistrationGenderIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidDropDownField('RegistrationBirthDay','RegistrationBirthDayIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidDropDownField('RegistrationBirthMonth','RegistrationBirthMonthIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	if(isValidDropDownField('RegistrationBirthYear','RegistrationBirthYearIcon',CurrentFeedbackDivID,false) === false){ ValidationSucceeded = false; ArrayOfValidationFailures.push(document.getElementById(RegistrationErrorFeedbackDivName).innerHTML); }
	
	if(ValidationSucceeded === false){
    if (AlertOnBadInput == true){
    //Return Invalid
    
    // Print to the Main Error Div
    $('#'+CurrentFeedbackDivID+'').html('<div id='+RegistrationErrorFeedbackDivName+'>Please fix your input for the fields below before submitting.</div>');

    // Print to the Sub Error Div
    for (var i = 0; i < ArrayOfValidationFailures.length; i++) {
    $('#'+RegistrationErrorFeedbackDivName+'').append('<div id='+ SubRegistrationErrorFeedbackDiv +'> - '+ArrayOfValidationFailures[i]+'</div>');
    }
    
    }
    return false;
    //Return Invalid
	}else{
	//Return Valid
	// If nothing failed to Validate then return True
	return true;
	//Return Valid
	}
}
