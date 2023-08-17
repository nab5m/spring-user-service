package com.nab5m.userservice.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void rootPageAllowsAnonymousUser() throws Exception {
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    @WithAnonymousUser
    public void privatePageBanAnonymousUser() throws Exception {
        mockMvc.perform(get("/private")).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void privatePageAllowsAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/private")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("authenticated")));
    }
}
