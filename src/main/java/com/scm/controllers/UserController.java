package com.scm.controllers;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.helpers.ImageSaveHandler;
import com.scm.repsitories.UserRepo;
import com.scm.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    // user dashbaord page
    @Value("${spring.mail.properties.saveImageInCloudanary}")
    private boolean saveImageInCloudanary;

    @Autowired
    private ImageSaveHandler imageSaveHandler;

    @Autowired
    private ImageService imageService;


    @RequestMapping(value = "/dashboard")
    public String userDashboard(Model model) {
        System.out.println("User dashboard");
        model.addAttribute("username", "Piyush"); // logged in user
        model.addAttribute("totalUsers", 151);
        model.addAttribute("totalOrders", 320);
        model.addAttribute("totalRevenue", "$25,000");
        model.addAttribute("newMessages", 42);
        return "user/dashboard";
    }

    // user profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }

    @PostMapping(value = "/updateprofileimage")
    public String updateprofileimage(@RequestParam("photo") MultipartFile file, Model model, Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userRepo.findByEmail(username).orElse(null);

        if(saveImageInCloudanary){
            if (file != null && !file.isEmpty()) {
                String filename = UUID.randomUUID().toString();
                String fileURL = imageService.uploadImage(file, filename);
                user.setProfilePic(fileURL);
            }
        }else{
            String filename = UUID.randomUUID().toString();
            String fileURL = imageSaveHandler.saveFile(file,user.getEmail(), user.getUserId(),filename);
            user.setProfilePic(fileURL);
        }
        userRepo.save(user);
        return "user/profile";
    }


    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact

}
