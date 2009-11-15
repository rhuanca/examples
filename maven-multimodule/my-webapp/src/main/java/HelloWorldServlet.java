import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author rhuanca
 * @since Nov 1, 2009  11:54:11 AM
 */
public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HelloWorldService helloWorldService = new HelloWorldService();
        response.getWriter().println(helloWorldService.sayHello());
    }
}
