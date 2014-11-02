<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<!-- EXTERNAL jQuery Import -->
<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<script>

$(function() {
    $("#playlist li").on("click", function() {
        $("#videoarea").attr({
            "src": $(this).attr("movieurl"),
            "poster": "",
            "autoplay": "autoplay"
        })
    })
    $("#videoarea").attr({
        "src": $("#playlist li").eq(0).attr("movieurl"),
        "poster": $("#playlist li").eq(0).attr("moviesposter")
    })
})

</script>

<style>
#playlist {
    display:table;
}
#playlist li{
    cursor:pointer;
    padding:8px;
}

#playlist li:hover{
    color:blue;                        
}
#videoarea {
    float:left;
    width:640px;
    height:480px;
    margin:10px;    
    border:1px solid silver;
}
</style>

</head>
<body>

<video id="videoarea" controls="controls" poster="" src=""></video>

<ul id="playlist">
    <li movieurl="http://www.dgxsoftware.com/RLF/Videos/MP4/happyfit2.mp4" moviesposter="http://html5videoformatconverter.com/data/images/screen.jpg">Happy Feet</li>
    <li movieurl="http://www.dgxsoftware.com/RLF/Videos/MP4/sintel_trailer-480.mp4">Sintel</li>
    <li movieurl="http://www.dgxsoftware.com/RLF/Videos/MP4/big_buck_bunny.mp4">Big Buck Bunny</li>
	
<!-- 
<a href="http://yoursite.com/downloadFile?id=1234">Click here to Download Hello.mp4</a>
  <source src="/JSP/Video/Clips/big_buck_bunny.mp4" type='video/mp4'>
 <source src="/JSP/Video/Clips/big_buck_bunny.mp4" type='video/webm'>
-->

	
</ul>

</body>
</html>