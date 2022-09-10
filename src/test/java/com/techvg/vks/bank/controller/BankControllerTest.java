package com.techvg.vks.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techvg.vks.security.jwt.JwtAuthEntryPoint;
import com.techvg.vks.security.jwt.JwtProvider;
import com.techvg.vks.society.controller.SocietyBankController;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.service.SocietyBankService;
import com.techvg.vks.user.service.serviceimpl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SocietyBankController.class)
//@WebMvcTest(controllers = {BankController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
//@ContextConfiguration(classes={VksApplicationTests.class})
//@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
//@ActiveProfiles("test")
//@WithMockUser(username="admin",authorities={"ADMIN"})
//@WithMockUser(username="spring")
//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthEntryPoint unauthorizedHandler;

    @MockBean
    JwtProvider jwtProvider;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SocietyBankService bankService;

/*    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }*/

    @Test
    void testSaveNewBank() throws Exception {
        String token = JwtProvider.generateJwtToken("clerk");

        assertNotNull(token);

        SocietyBankDto bankDto = getValidBankDto();
        String bankDtoToJson = objectMapper.writeValueAsString(bankDto);

        given(bankService.addNewBank(any())).willReturn(getValidBankDto());

        mockMvc.perform(post("/api/bank")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(bankDtoToJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBank() throws Exception {
        String token = JwtProvider.generateJwtToken("clerk");

        assertNotNull(token);

        given(bankService.getBankById(anyLong())).willReturn(getValidBankDto());
        mockMvc.perform(get("/api/bank/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        }

    SocietyBankDto getValidBankDto(){
        return SocietyBankDto.builder()
                .bankName("Citi")
                .branchName("Aundh")
                .ifsccode("IFSCCODE0101")
                .status("A")
                .build();
    }
}