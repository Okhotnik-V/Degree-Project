package com.degree.cto.logic.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public void addLog(String type, String name, String title, String message) {
        LogDTO log = new LogDTO();
        log.setType(type);
        log.setName(name);
        log.setTitle(title);
        log.setMessage(message);
        log.setDate(timestamp.toLocalDateTime().toString());
        log.setLogicDateDays(createLogicDate());
        System.out.println("----------log----------\n" + title +  "\n" + message + "\n" + log.getDate() + "\n-----------------------");
        logRepository.save(log);
    }

    private String createLogicDate() {
        String date = timestamp.toLocalDateTime().toString();
        return date.substring(0,10);
    }

    public List<LogDTO> findFilterList(String date) {
        if (date != null) {
            return findByLogicDateDays(date);
        } else {
            return findAllLogs();
        }
    }

    private List<LogDTO> findAllLogs() {
        return logRepository.findAll();
    }

    private List<LogDTO> findByLogicDateDays(String date) {
        return logRepository.findByLogicDateDays(date);
    }
}
