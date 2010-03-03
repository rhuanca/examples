package net.sumasoftware.ws;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.*;

/**
 * @author Renan Huanca
 * @since Feb 28, 2010 3:16:57 PM
 */
public class MyWs extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        //InputStream stream = this.getClass().getClassLoader().getResourceAsStream("myresponse.xml");
        InputStream stream = new FileInputStream("c:\\todelete\\myresponse.xml");
        PrintWriter writer = response.getWriter();
        int i = stream.read();
        while(i>0){
            System.out.print((char)i);
            writer.print((char)i);
            i = stream.read();
        }
    }


    public static String readInputStreamAsString(InputStream in)
            throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
          byte b = (byte)result;
          buf.write(b);
          result = bis.read();
        }
        return buf.toString();
    }
}
