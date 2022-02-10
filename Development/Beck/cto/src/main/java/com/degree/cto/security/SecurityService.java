package com.degree.cto.security;

import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.logic.AutoCreateCollections;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UsersRepository userRepository;


    private AutoCreateCollections autoCreateCollections;

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

        System.out.println("Створено новий профіль");
    }
}
