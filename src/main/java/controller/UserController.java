package controller;

import model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create_user", method = RequestMethod.GET)
    public ModelAndView loadCreateForm(){
        logger.trace("Go into loadCreateForm()");
        ModelAndView createForm = null;
        User user;
        try {
            user = new User();
            createForm = new ModelAndView("create");
            createForm.addObject("user", user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.trace("Exit loadCreateForm()");
        return createForm;
    }

    @RequestMapping(value = "/create_user", method = RequestMethod.POST)
    public ModelAndView createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        logger.trace("Go into createUser()");
        ModelAndView created = null;
        try {
            user.validate(user, bindingResult);
            if (bindingResult.hasFieldErrors()) {
                created = new ModelAndView("create");
            } else {
                userService.save(user);
                logger.info(user.toString());
                created = new ModelAndView("result");
                created.addObject("user", user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.trace("Exit createUser()");
        return created;
    }
}