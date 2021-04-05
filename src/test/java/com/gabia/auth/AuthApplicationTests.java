package com.gabia.auth;

import com.gabia.auth.entity.TokenEntity;
import com.gabia.auth.repositories.TokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Base64;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        args = {"--spring.profiles.active=dev"
                , "--hiworks.kms.role-id=a2d67753-929c-14b9-ccac-5b5086141e68"
                , "--hiworks.kms.secret-id=6ac38431-3dcf-8fef-75a8-b9e9f5d2ad13"
                , "--hiworks.kms.engine-name=dev-gabia"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private TokenRepository tokenRepository;

    @Test
    void 토큰_검증_성공() throws Exception{

        //given
        Base64.Encoder encoder = Base64.getEncoder();
        List<TokenEntity> allBy = tokenRepository.findAllBy();
        String auth = allBy.get(0).getClientId()+":"+allBy.get(0).getClientSecret().replace("{noop}","");
        byte[] authBytes = auth.getBytes("UTF-8");
        byte[] authBytesEncode = encoder.encode(authBytes);
        String Authorization = new String(authBytesEncode);

        //when
        MvcResult mvcResult =
                mockMvc.perform(
                        post("/oauth/token")
                                .header("Authorization", "Basic "+Authorization)
                                .param("grant_type", "client_credentials"))
                        .andDo(print())
                        .andExpect(status().isOk()).andReturn();
        //then
    }

    @Test
    void 토큰_검증_실패() throws Exception{

        MvcResult mvcResult =
                mockMvc.perform(
                        post("/oauth/token")
//                                .header("Authorization", "Basic "+Authorization)
                                .param("grant_type", "client_credentials"))
                        .andDo(print())
                        .andExpect(status().isUnauthorized()).andReturn();
    }
}
