<form>

<%
// Get a Random Number From 1 to 10
int Min = 1;
int Max = 10;
int Range = (Max - Min) + 1;     
int RandomNumber = (int)(Math.random() * Range) + Min;
%>

<h1>Round <%= RandomNumber %></h1>
<script type = "text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script type = "text/javascript">
    $("#demo").live("click", function () {
        $.ajax({
            url: '/ThrowErrorServlet',
            type: 'POST',
            cache:      false,
            data:       $("form").serialize(),
            success: function (data, status) {
                    alert("Error Demo Success!");
            },
            error: function (xhr, textStatus, thrownError) {
            
            alert("xhr.responseText = " + xhr.responseText);
 
            }
        });
    });
</script>
<input type = "button" id = "demo" value = "Demo" />
</form>

<!-- 

Here Mudassar Ahmed Khan has provided an example to parse jQuery AJAX error responseText string received in the error handler to JSON object. 

The below code snippet explains how to parse the jQuery AJAX error responseText to JSON object so that its Attributes can be easily read.


Explanation:
Above on the click of the button with ID demo a jQuery AJAX call is made to URL. The error or exceptions occurring during this jQuery AJAX call are captured in the error event handler. jQuery stores the error information in the responseText object in a JSON string format. To convert it to a JSON object jQuery.parseJSON method is used. And later the object attributes are displayed via alert.

if (json && json.length) {
  // ...
}

 -->