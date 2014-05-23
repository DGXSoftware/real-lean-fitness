<%--
To make a JSP page an exception handler ( i.e. errorPage ), you have to specify the "isErrorPage" 
attribute in the page directive at the top and set it's value to true.
--%>

<%@ page isErrorPage="true" %>

<%--
1. SAMPLE PUTPUTS
exception.toString() = java.lang.RuntimeException: THIS IS MY CUSTOM ERROR !!!!!!!!!!!!!!!!!!!!!!!!!
exception.getLocalizedMessage() = THIS IS MY CUSTOM ERROR !!!!!!!!!!!!!!!!!!!!!!!!!
exception.getMessage() = THIS IS MY CUSTOM ERROR !!!!!!!!!!!!!!!!!!!!!!!!!

2. Stack trace as a string
import java.io.PrintWriter;
import java.io.StringWriter;

StringWriter MyStringWriter = new StringWriter();
PrintWriter MyPrintWriter = new PrintWriter(MyStringWriter);
exception.printStackTrace(MyPrintWriter);
System.out.println(MyStringWriter.toString()); // Stack trace as a string
--%>

<%-- Print only the Error Message --%>
<%= exception.getMessage() %>
