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
        usersDTO.setRole("");
        int randomAvatar = 1 + (int) (Math.random() * 10);
        usersDTO.setPhoto_url("/assets/img/avatars/clients/" + randomAvatar + ".JPG");
        userRepository.save(usersDTO);
        logService.addLog("log", "Користувачі", "Новий користува", "Користувач:@"+ username + " приєднався до сервісу");
    }

    public String customAccess(String page, String username, String role_1, String role_2) {
        try {
            String userRole = userRepository.findByPersonalIndent(username).getRole();
            if (userRole.contentEquals(role_1) || userRole.contentEquals(role_2)) {
                return page;
            } else {
                return "redirect:/personal-page";
            }
        } catch (Exception e) {
            return "redirect:/personal-page";
        }
    }
}
