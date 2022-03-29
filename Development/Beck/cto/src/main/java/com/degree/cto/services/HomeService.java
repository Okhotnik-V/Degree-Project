package com.degree.cto.services;

import com.degree.cto.dtos.ReviewsDTO;
import com.degree.cto.dtos.UsersDTO;
import com.degree.cto.repositorys.ReviewsRepository;
import com.degree.cto.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UsersRepository usersRepository;

    int[] logicId = new int[9];

    public List<UsersDTO> managementList() {
        List<UsersDTO> list = usersRepository.findByRole("Директор");
        list.addAll(usersRepository.findByRole("Менеджер"));
        list.addAll(usersRepository.findByRole("Бухгалтер"));
        list.addAll(usersRepository.findByRole("Рекрутер"));
        return list;
    }

    public List<ReviewsDTO> reviewsGetList() {
        List<ReviewsDTO> reviewsList = reviewsRepository.findAll();
        reviewsGetLogicId(reviewsList);
        reviewsList.clear();
        for (int i = 0; i < 9; i++) {
            if (logicId[i] != 0) {
                reviewsList.add(reviewsRepository.findByLogicId(logicId[i]));
            }
        }
        return reviewsList;
    }

    private int[] reviewsGetLogicId(List<ReviewsDTO> list) {
        long sizeList = list.size();
        for (int i = 0; i < 9; i++) {
            int randomReviews = 1 + (int) (Math.random() * sizeList);
            if (randomReviews != logicId[0] && randomReviews != logicId[1] && randomReviews != logicId[2] && randomReviews != logicId[3] &&
                    randomReviews != logicId[4] && randomReviews != logicId[5] && randomReviews != logicId[6] && randomReviews != logicId[7] &&
                    randomReviews != logicId[8]) {
                logicId[i] = randomReviews;
            } else {
                if (sizeList >= 9) {
                    i--;
                } else {
                    logicId[i] = 0;
                }
            }
        }
        return logicId;
    }
}
