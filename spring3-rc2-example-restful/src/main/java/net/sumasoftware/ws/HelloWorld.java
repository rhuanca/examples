package net.sumasoftware.ws;

import javax.jws.WebService;

/**
 * @author Renan Huanca
 * @since Dec 8, 2009 8:50:56 PM
 */
@WebService
public interface HelloWorld {
    String sayHi(String text);
}
