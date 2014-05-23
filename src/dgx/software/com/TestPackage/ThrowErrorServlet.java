package dgx.software.com.TestPackage;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThrowErrorServlet extends HttpServlet {

private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	// Call the doPost() Method
	doPost(request, response);
	
  }

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	// THROW AN ERROR FOR THE AJAX CALL
	throw new RuntimeException("THIS IS MY CUSTOM ERROR !!!!!!!!!!!!!!!!!!!!!!!!!");

}

}
