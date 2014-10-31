$(document).ready(function() {
	
	// ENABLE: Highlight all Values for all Text Fields when focused
    $("input:text").focus(function() { $(this).select(); } );
	
    // ENABLE: Highlight all Values for all Password Fields when focused
    $("input:password").focus(function() { $(this).select(); } );
    
    // ENABLE: Submit on Enter pressed For all Text Fields
    $('input:text').keypress(function (e) {
  	  if (e.which == 13) {
  		submitForm();
  	    return false;
  	  }
  	});
  	
    // ENABLE: Submit on Enter pressed For all Password Fields
    $('input:password').keypress(function (e) {
  	  if (e.which == 13) {
  		submitForm();
  	    return false;
  	  }
  	});
    
});