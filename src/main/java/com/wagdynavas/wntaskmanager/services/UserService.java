package com.wagdynavas.wntaskmanager.services;

import com.wagdynavas.wntaskmanager.models.User;
import com.wagdynavas.wntaskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public ModelAndView createUser(User user, BindingResult result) {
        ModelAndView view = new ModelAndView();
        User userExist = getByEmail(user.getEmail());
        if(userExist != null) {
            result.rejectValue("email", "", "This email has already been used.");
        }
        if(result.hasErrors()) {
            view.setViewName("account/register");
            view.addObject(user);
        }else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            view.setViewName("redirect:/login");
        }
        return view;



    }
}
