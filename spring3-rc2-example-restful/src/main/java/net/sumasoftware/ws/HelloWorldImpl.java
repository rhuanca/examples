package net.sumasoftware.ws;

import javax.jws.WebService;


/**
 * @author Renan Huanca
 * @since Dec 11, 2009 5:31:48 PM
 */
@WebService(endpointInterface = "net.sumasoftware.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }

}
