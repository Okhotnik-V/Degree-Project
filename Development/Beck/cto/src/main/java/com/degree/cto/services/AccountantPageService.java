package com.degree.cto.services;

import com.degree.cto.dtos.OrdersDTO;
import com.degree.cto.dtos.TransactionsInfoDTO;
import com.degree.cto.repositorys.OrdersRepository;
import com.degree.cto.repositorys.TransactionsInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccountantPageService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private TransactionsInfoRepository transactionsInfoRepository;

    public String defaultDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String month = null;
        if (timestamp.toLocalDateTime().getMonthValue() < 10) {
            month = "0" + timestamp.toLocalDateTime().getMonthValue();
        } else {
            month = String.valueOf(timestamp.toLocalDateTime().getMonthValue());
        }
        return timestamp.toLocalDateTime().getYear() + "-" + month;
    }

    public long[] ordersDTOListFindAndTransactionsMoney(String date) {
        String zeroI = null;
        long[] ordersInfo = new long[3];
        for (int i = 0; i <= 31; i++) {

            if (i < 10) {
                zeroI = "0" + i;
            } else {
                zeroI = String.valueOf(i);
            }

            if (ordersRepository.findByDateWorkAndStatusWork(date + "-" + zeroI, "Виконано").size() >= 1) {
                List<OrdersDTO> ordersDTO = ordersRepository.findByDateWorkAndStatusWork(date + "-" + zeroI, "Виконано");
                for (int listOrders = 0; listOrders <= ordersDTO.size() - 1; listOrders++) {
                    ordersInfo[0] = ordersInfo[0] + ordersDTO.get(listOrders).getNetProfit(); //дохід
                    ordersInfo[1]++; //кількість замовлень
                }
            }
            if (transactionsInfoRepository.findByLogicDate(date + "-" + zeroI).size() >= 1) {
                List<TransactionsInfoDTO> transactionsInfoDTOS = transactionsInfoRepository.findByLogicDate(date + "-" + zeroI);
                for (int listTransactions = 0; listTransactions <= transactionsInfoDTOS.size() - 1; listTransactions++) {
                    ordersInfo[2] = ordersInfo[2] + transactionsInfoDTOS.get(listTransactions).getMoney();
                }
            }
        }
        return ordersInfo;
    }

    public List<TransactionsInfoDTO> transactionsInfoDTOS(String date) {
        List<TransactionsInfoDTO> transactionsInfoDTO = null;
        String transL = null;
        int indexList = 0;

        for (int listener = 0; listener <= 31; listener++) {

            if (listener < 10) {
                transL = "0" + listener;
            } else {
                transL = String.valueOf(listener);
            }

            if (transactionsInfoRepository.findByLogicDate(date + "-" + transL).size() >= 1) {
                List<TransactionsInfoDTO> transactionsInfoDTOS = transactionsInfoRepository.findByLogicDate(date + "-" + transL);
                if (transactionsInfoDTO == null) {
                    transactionsInfoDTO = transactionsInfoDTOS;
                    indexList = indexList + transactionsInfoDTOS.size();
                } else {
                    for (int listTransactions = 0; listTransactions <= transactionsInfoDTOS.size() - 1; listTransactions++) {
                        transactionsInfoDTO.add(indexList, transactionsInfoDTOS.get(listTransactions));
                        indexList++;
                    }
                }
            }
        }
        return transactionsInfoDTO;
    }

    public List<OrdersDTO> ordersDTOList(String date) {
        List<OrdersDTO> dtoList = null;
        String zeroI = null;
        int indexList = 0;

        for (int i = 0; i <= 31; i++) {

            if (i < 10) {
                zeroI = "0" + i;
            } else {
                zeroI = String.valueOf(i);
            }

            if (ordersRepository.findByDateWorkAndStatusWork(date + "-" + zeroI, "Виконано").size() >= 1) {
                List<OrdersDTO> ordersDTO = ordersRepository.findByDateWorkAndStatusWork(date + "-" + zeroI, "Виконано");
                if (dtoList == null) {
                    dtoList = ordersDTO;
                    indexList = indexList + ordersDTO.size();
                } else {
                    for (int listOrders = 0; listOrders <= ordersDTO.size() - 1; listOrders++) {
                        dtoList.add(indexList, ordersDTO.get(listOrders));
                        indexList++;
                    }
                }
            }
        }
        return dtoList;
    }

    public boolean addCoast(TransactionsInfoDTO transactionsInfoDTO, String usernameCreator) {
        if (transactionsInfoDTO.getType().equals("Виплата зарплати") || transactionsInfoDTO.getType().equals("Виплата премії") || transactionsInfoDTO.getType().equals("Виплата авансу")  && transactionsInfoDTO.getUsername() != "" && transactionsInfoDTO.getDate() != "") {
            transactionsInfoDTO.setNumber(transactionsNumberCheck());
            transactionsInfoDTO.setInfo(transactionsInfoDTO.getType());
            transactionsInfoDTO.setFull_info(transactionsInfoDTO.getType() + " " + transactionsInfoDTO.getUsername());
            transactionsInfoDTO.setUsernameCreator(usernameCreator);
            transactionsInfoDTO.setLogicDate(transactionsInfoDTO.date.substring(0,10));
            transactionsInfoDTO.setLink("window.location='/transactions/" + transactionsInfoDTO.getNumber() + "';");
            transactionsInfoDTO.setType("Виплата");
            transactionsInfoRepository.save(transactionsInfoDTO);
            return true;
        } else if (transactionsInfoDTO.getType() != null && transactionsInfoDTO.getDate() != null) {
            transactionsInfoDTO.setNumber(transactionsNumberCheck());
            transactionsInfoDTO.setUsernameCreator(usernameCreator);
            transactionsInfoDTO.setLogicDate((transactionsInfoDTO.date.substring(0,10)));
            transactionsInfoDTO.setLink("window.location='/transactions/" + transactionsInfoDTO.getNumber() + "';");
            transactionsInfoRepository.save(transactionsInfoDTO);
            return true;
        } else { return false; }
    }

    private long transactionsNumberCheck() {
        long i;
        for (i = 1; transactionsInfoRepository.findByNumber(i) != null; i++) {
        }
        return i;
    }
}
