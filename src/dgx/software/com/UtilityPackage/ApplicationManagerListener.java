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
        dgx.software.com.JavaBeanPackage.PayPalJavaBean.setServletContextProperties(properties);
        dgx.software.com.JavaBeanPackage.UserSQLOperationJavaBean.setServletContextProperties(properties);
    }

    public void contextDestroyed(ServletContextEvent sce) {
       
    }

}