package net.sumasoftware.restful.web.controller;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationMap;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;

/**
 * @author Renan Huanca
 * @since Nov 23, 2009 2:45:51 PM
 */
public class ApplicationConfiguration extends ConfigurationMap {
    private static Logger logger = Logger.getLogger(ApplicationConfiguration.class);

    private static ApplicationConfiguration instance = null;
    public ApplicationConfiguration (Configuration configuration) {
        super(configuration);
        instance = this;
    }

    public static void loadConfiguration(ServletContext servletContext, String confFilePath){
        logger.info("Agenda Application: Loading configuration file. ("+confFilePath+")");
        try {
            // loading configuration
            PropertiesConfiguration configuration = new PropertiesConfiguration();
            configuration.load(servletContext.getRealPath(confFilePath));
            ApplicationConfiguration agendaConfiguration = new ApplicationConfiguration(configuration);
            servletContext.setAttribute("configuration", agendaConfiguration);

        } catch (ConfigurationException e) {
            logger.error("Unable to load agenda configuration file");
            logger.error("Due to this exception:" + e);
        }
    }

    public static Configuration getInstance(){
        return instance.getConfiguration();
    }
}
