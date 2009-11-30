package net.sumasoftware.wsui;

import org.springframework.web.context.support.XmlWebApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

import net.sumasoftware.wsui.ApplicationConfiguration;
import net.sumasoftware.ws.ConnectionManager;

/**
 * @author Renan Huanca
 * @since Nov 23, 2009 3:10:27 PM
 */
public class ApplicationContext extends XmlWebApplicationContext {

    private static Logger logger = Logger.getLogger(ApplicationContext.class);

    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
        ApplicationConfiguration.loadConfiguration(servletContext,
                servletContext.getInitParameter("contextConfigLocation"));
        ConnectionManager.loadConnectionProperties(ApplicationConfiguration.getInstance());
        
    }
}
