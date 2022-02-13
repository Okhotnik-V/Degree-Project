package com.degree.cto.security;

import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.logic.OrderStatusDAO;
import com.degree.cto.logic.Log.LogService;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private LogService logService;


    private OrderStatusDAO autoCreateCollections;

    public String profileCheck(String url, String pageName, String personal_indent) {
        if (userRepository.findByPersonalIndent(personal_indent) == null) {
            return "redirect:/authorize/confirm?url=" + url;
        } else {
            return pageName;
        }
    }

    public void createProfile(String firstName, String lastName, String email, String roles, String username) {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setName(firstName + " " + lastName);
        usersDTO.setEmail(email);
        usersDTO.setPersonalIndent(username);
        usersDTO.setStatus("Клієнт");
        usersDTO.setPhoto_url("/assets/img/not-image.jpg");
        userRepository.save(usersDTO);
        logService.addLog("log", "Користувачі", "Новий користува", "Користувач:@"+ username + " приєднався до сервісу");
    }
}
