package snttgr.alkemy.challenge.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping()
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping()
    public String index(){
        //TODO: for now...
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("login/success")
    public String success(Authentication authentication){

        log.debug("--------- Log in -----------------");

        if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")))
            return "redirect:/admin";

        if(authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")))
            return "redirect:/user/";

        return "redirect:/login";
    }

}
