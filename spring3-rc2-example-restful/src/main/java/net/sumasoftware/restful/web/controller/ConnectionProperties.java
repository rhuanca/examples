package net.sumasoftware.restful.web.controller;

/**
 * @author Renan Huanca
 * @since Nov 25, 2009 4:07:01 PM
 */
public class ConnectionProperties {
    String driverClassName;
    String url;
    String username;
    String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
