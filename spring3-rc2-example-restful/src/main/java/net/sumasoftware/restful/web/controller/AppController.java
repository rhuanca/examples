package net.sumasoftware.restful.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Renan Huanca
 * @since Nov 26, 2009 7:39:41 PM
 */
@Controller
public class AppController {
    
    @RequestMapping("/ui/services/index.do")
    public String init() {
        return "webServicesList";
    }

}
