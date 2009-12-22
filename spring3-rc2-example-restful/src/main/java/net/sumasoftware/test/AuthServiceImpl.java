package net.sumasoftware.test;

import javax.jws.WebService;

//import com.company.auth.bean.Employee;
//import com.company.auth.dao.EmployeeDAO;

/**
 * @author Renan Huanca
 * @since Dec 12, 2009 7:34:20 PM
 */

@WebService(endpointInterface = "net.sumasoftware.test.AuthService", serviceName = "corporateAuthService")
public class AuthServiceImpl implements AuthService  {

    public Employee getEmployee(String gid) {
        Employee employee = new Employee();
        employee.setFirstName("juan");
        employee.setLastName("perez");

        return employee;
    }
    
}
