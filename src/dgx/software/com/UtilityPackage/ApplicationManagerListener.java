package dgx.software.com.UtilityPackage;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/*
SOURCE: http://www.itry.it/codice/87/from_webxml_to_javabeans.aspx
Get web.xml values from JavaBeans
If you want to access web.xml properties from a Java Beans that not implements Servlets, you can place a ServletContextListener do the job for you
ServletContextListener will be executed once your web application is deployed in your application server (Tomcat or others). If you have any requirements that need to be executed before the application is started, ServletContextListener is the best place for you. ServletContextListener also detects when your web application is removed. For example, if you replace the WAR file in Tomcat, Tomcat will automatically re-deploy your web application based on the latest WAR. Re-deploying means that Tomcat first removes the web application and then deploy the new web application.
 */
public class ApplicationManagerListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        ServletContext servletContext = sce.getServletContext();
        Enumeration<?> keys = servletContext.getInitParameterNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = servletContext.getInitParameter(key);
            properties.setProperty(key, value);
        }
        
        try{
        // Initialize the following Methods for the following Beans
        dgx.software.com.JavaBeanPackage.PayPalJavaBean.setServletContextProperties(properties);
        dgx.software.com.JavaBeanPackage.UserSQLOperationJavaBean.setServletContextProperties(properties);
        dgx.software.com.UtilityPackage.GlobalTools.setServletContextProperties(properties);
        }catch(Exception EX){
        	System.out.println("================================== DGX CUSTOM ERROR ==================================");
        	System.out.println("ERROR DESCRIPTION: Exception sending context initialized event to listener instance of class dgx.software.com.UtilityPackage.ApplicationManagerListener");
        	System.out.println("ERROR MESSAGE: java.lang.RuntimeException: The Database is Unavailable. Please Try again later.");
        	System.out.println("ERROR SOLUTION: Check the \"web.xml\" file and make sure that all the Database Credentials are properly set for a successful Database Connection.");
        	System.out.println("================================== DGX CUSTOM ERROR ==================================");
        	EX.printStackTrace();
        }
        
    }

    public void contextDestroyed(ServletContextEvent sce) {
       
    }

}