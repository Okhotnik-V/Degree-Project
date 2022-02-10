package com.degree.cto.services;

import com.degree.cto.dtos.ReviewsDTO;
import com.degree.cto.repositorys.ReviewsRepository;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class PersonalPageService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ReviewsRepository reviewsRepository;

    public String pageNotFond(String username) {
        if (usersRepository.findByPersonalIndent(username) == null) {
            return "redirect:/personal-page";
        } else {
            return "personal-page";
        }
    }

    public void createReviews(ReviewsDTO reviewsDTO, String username) {
        reviewsDTO.setTimestamp(new Timestamp(System.currentTimeMillis()));
        reviewsDTO.setUsername(username);
        reviewsRepository.save(reviewsDTO);
    }

    public String editAccess(String username, String urlPage) {
        if (username.equals(urlPage)){
            return "true";
        } else {
            return "false";
        }
    }

}
