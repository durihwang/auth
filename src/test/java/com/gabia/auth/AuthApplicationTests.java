package com.gabia.auth;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApplicationTests {

    /*@Autowired
    private MockMvc mockMvc;

    @Test
    void 토큰_발급() throws Exception {

		mockMvc.perform(
				post("/oauth/token")
						.header("Authorization", "Basic Nzg4NjFmODQtNmEyMy00ZWRjLWE3ZTktMzdlNzg2YmExYjQ5OjRlNTUxMjk3LWIyODAtNGY5Zi05OWJmLTEyOTFhMmMxNjA1Mg==")
						.param("grant_type", "client_credentials"))
				.andDo(print())
				.andExpect(status().isOk());
    }

    @Test
    void 토큰_검증() throws Exception{

		MvcResult mvcResult =
				mockMvc.perform(
						post("/oauth/token")
								.header("Authorization", "Basic Nzg4NjFmODQtNmEyMy00ZWRjLWE3ZTktMzdlNzg2YmExYjQ5OjRlNTUxMjk3LWIyODAtNGY5Zi05OWJmLTEyOTFhMmMxNjA1Mg==")
								.param("grant_type", "client_credentials"))
						.andDo(print())
						.andExpect(status().isOk()).andReturn();
		System.out.println("mvcResult = " + mvcResult.getResponse().getContentAsString());
    }*/
}
