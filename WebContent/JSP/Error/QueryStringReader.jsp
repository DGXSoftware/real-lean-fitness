<!--
GOAL: Reads the query String Field and Name value pairs and displays them for troubleshooting.
-->

<!DOCTYPE html>

<%@ page import = "java.util.Arrays" %>
<%@ page import = "java.util.Map" %>

<%
System.out.println("");
System.out.println("START Get Form Parameter List");
System.out.println("");

    Map map = request.getParameterMap();
    
    for (Object Key: map.keySet()){
    
            String KeyString = (String)Key;
            String[] Value = (String[])map.get(KeyString);
            System.out.println((String)Key + " = " + Arrays.toString(Value));
    }

System.out.println("");
System.out.println("END Get Form Parameter List\n");
System.out.println("");
%>

<p>
<font style="color:RED;" size="72">Get Form Parameter List Display!</font>
</p>

<%

    for (Object Key: map.keySet()){
    
            String KeyString = (String)Key;
            String[] Value = (String[])map.get(KeyString);
            
            String CurrentKey = (String)Key;
            String CurrentValue = Arrays.toString(Value);
%>            
<div>
<font color="BLUE" size="+2"><B> <%= CurrentKey %> = </b></font>
<font color="RED" size="+2"><B> <%= CurrentValue %> </b></font>
</div>
<%            
    }

%>

<div>
<br/>
<font color="GREEN" size="+2"><B> <a href="javascript:history.back()">Back</a> </b></font>
</div>
