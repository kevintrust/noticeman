package net.eadtrust.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller shows the index page.
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index")
    public ModelAndView indexPage() {
        ModelAndView mav = new ModelAndView("/WEB-INF/views/index.jsp");
        return mav;
    }
}
