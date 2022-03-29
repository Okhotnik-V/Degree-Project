package com.degree.cto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CtoApplicationTests {

    int[] statusCode = new int[]{200, 302, 403};

    @Autowired
    private MockMvc mockMvc;

    private void basicIntegrationsGetTest(String url, int statusPage) throws Exception {
        mockMvc.perform(get(url)).andExpect(status().is(statusPage));
    }

    private void basicIntegrationsPostTest(String url, int statusPage) throws Exception {
        mockMvc.perform(post(url)).andExpect(status().is(statusPage));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void homePage() throws Exception {
        basicIntegrationsGetTest("/", statusCode[0]);
    }

    @Test
    void logout() throws Exception {
        basicIntegrationsGetTest("/logout", statusCode[1]);
    }

    @Test
    void accountantPage() throws Exception {
        basicIntegrationsGetTest("/accountant", statusCode[1]);
        basicIntegrationsPostTest("/accountant", statusCode[2]);
        basicIntegrationsPostTest("/accountant/addCoast", statusCode[2]);
    }

    @Test
    void directorPage() throws Exception {
        basicIntegrationsGetTest("/director", statusCode[1]);
        basicIntegrationsPostTest("/director", statusCode[2]);
    }

    @Test
    void orderPages() throws Exception {
        basicIntegrationsGetTest("/order/1", statusCode[1]);
        basicIntegrationsGetTest("/order/1/edit", statusCode[1]);
        basicIntegrationsPostTest("/order/1/edit", statusCode[2]);
        basicIntegrationsPostTest("/order/1/edit/team/add", statusCode[2]);
        basicIntegrationsPostTest("/order/1/edit/team/dell", statusCode[2]);
        basicIntegrationsPostTest("/order/1/edit/check/add", statusCode[2]);
        basicIntegrationsPostTest("/order/1/edit/check/dell", statusCode[2]);
        basicIntegrationsGetTest("/order/create", statusCode[1]);
        basicIntegrationsPostTest("/order/create", statusCode[2]);
        basicIntegrationsGetTest("/order/management", statusCode[1]);
        basicIntegrationsPostTest("/order/management/find", statusCode[2]);
        basicIntegrationsGetTest("/order/management/archive/add", statusCode[1]);
        basicIntegrationsGetTest("/order/management/archive/dell", statusCode[1]);
        basicIntegrationsGetTest("/order/management/order/dell", statusCode[1]);
        basicIntegrationsGetTest("/order/management/order/status/new", statusCode[1]);
        basicIntegrationsGetTest("/order/management/order/status/confirmed", statusCode[1]);
        basicIntegrationsGetTest("/order/management/order/status/work", statusCode[1]);
        basicIntegrationsGetTest("/order/management/order/status/complete", statusCode[1]);
        basicIntegrationsGetTest("/order/management/archive", statusCode[1]);
        basicIntegrationsGetTest("/order/management/archive", statusCode[1]);
        basicIntegrationsPostTest("/order/management/archive/find", statusCode[2]);
    }

    @Test
    void personalPage() throws Exception {
        basicIntegrationsGetTest("/@_okhotnik_", statusCode[1]);
        basicIntegrationsPostTest("/@_okhotnik_/edit", statusCode[2]);
    }

    @Test
    void teamManagementPages() throws Exception {
        basicIntegrationsGetTest("/team/management", statusCode[1]);
        basicIntegrationsPostTest("/team/management/find", statusCode[2]);
        basicIntegrationsPostTest("/team/management/add", statusCode[2]);
        basicIntegrationsGetTest("/team/management/dell", statusCode[1]);
    }

    @Test
    void transactionsPage() throws Exception {
        basicIntegrationsGetTest("/transactions/1", statusCode[1]);
        basicIntegrationsGetTest("/transactions/1/dell", statusCode[1]);
        basicIntegrationsPostTest("/transactions/1/edit", statusCode[2]);
    }
}
