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