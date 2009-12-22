package net.sumasoftware.test;

import javax.jws.WebService;
import javax.jws.WebParam;

/**
 * @author Renan Huanca
 * @since Dec 12, 2009 7:32:27 PM
 */
@WebService
public interface AuthService {
    Employee getEmployee(@WebParam(name="gid") String gid);
    
}
