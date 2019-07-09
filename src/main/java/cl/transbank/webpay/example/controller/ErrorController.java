package cl.transbank.webpay.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    private static Logger log = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView error() {
        log.info("Error controller");
        return new ModelAndView("error");
    }
}
