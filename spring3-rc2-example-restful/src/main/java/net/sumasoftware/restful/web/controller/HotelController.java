package net.sumasoftware.restful.web.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.io.IOException;

/**
 * @author Renan Huanca
 * @since Nov 22, 2009  10:54:31 AM
 */
@Controller
public class HotelController {

    @RequestMapping(value="/hotels/{hotelId}", method= RequestMethod.GET)
    public void getHotel(@PathVariable String hotelId, HttpServletResponse response) throws IOException {
        System.out.println(">>> hotelId = " + hotelId);
//        List<Hotel> hotels = hotelService.getHotels();
//        model.addAttribute("hotels", hotels);
//        return "hotels";
        response.getWriter().print("hotelId = " + hotelId);
    }
}
