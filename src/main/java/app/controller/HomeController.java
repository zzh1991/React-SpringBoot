package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhihao zhang
 * @since 2019-03-18
 */

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

}
