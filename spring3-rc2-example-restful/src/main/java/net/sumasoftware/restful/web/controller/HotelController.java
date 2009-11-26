package net.sumasoftware.restful.web.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author Renan Huanca
 * @since Nov 22, 2009  10:54:31 AM
 */
@Controller
public class HotelController {
    private static Logger logger = Logger.getLogger(HotelController.class);

    @RequestMapping(value="/hotels/{hotelId}", method= RequestMethod.GET)
    public void getHotel(@PathVariable String hotelId, HttpServletResponse response) throws IOException, SQLException {
        System.out.println(">>> hotelId = " + hotelId);
//        List<Hotel> hotels = hotelService.getHotels();
//        model.addAttribute("hotels", hotels);
//        return "hotels";
        ConnectionManager instance = ConnectionManager.getInstance();
        for (Iterator i = instance.getConnections().iterator(); i.hasNext();) {
            DBConnection dbConnection = (DBConnection) i.next();
            logger.info(">>> dbConnection.getDriverClassName() = " + dbConnection.getDriverClassName());
            DataSource dataSource = dbConnection.getDataSource();
            Connection connection = dataSource.getConnection();
            logger.info("connection.getCatalog() = " + connection.getCatalog());
            List list = dbConnection.getTableNames();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                String tableName = (String) iterator.next();
                logger.info(">>> tableName = " + tableName);
            }
        }


        response.getWriter().print("hotelId = " + hotelId);
    }
}
