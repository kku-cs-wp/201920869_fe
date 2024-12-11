package kr.ac.kku.cs.wp.wsd.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * UserController
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Controller
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    /**
     * Handles requests to "/hello" and returns a greeting message.
     *
     * @param name The name of the user (required).
     * @return ModelAndView with greeting message and view name.
     */

    @GetMapping("/hello")
    public ModelAndView hello(@RequestParam(name = "name", required = true) String name) {
        final String greeting = "Hello, %s!".formatted(name);

        logger.debug("Generated greeting: {}", greeting);

        ModelAndView mav = new ModelAndView();
        mav.addObject("hello", greeting);
        mav.setViewName("HelloView");

        return mav;
    }
}