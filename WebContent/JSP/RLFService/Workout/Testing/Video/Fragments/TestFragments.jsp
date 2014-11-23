<!DOCTYPE html>

<html>

<head>
	<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<script>

function submitForm(){
	
	//$('body').load('/JSP/Video/Fragments/RLFResultsFragment.jsp');
	//$("#RLFPlayerBody").show();
	//$("#RLFPlayerBody").hide();
	
	if ($('#RLFPlayerBody').is(':visible')){
		alert("VISIBLE!!!!!!!!");
	}else{
		alert("NOT VISIBLE!");
	}
}

</script>

</head>

<div id="RLFPlayerBody" >
<body>
<h1>TEST</h1>
</body>
</div>

<form>
<input type="button" value="Submit Results" onClick="submitForm();"/>
</form>

</html>