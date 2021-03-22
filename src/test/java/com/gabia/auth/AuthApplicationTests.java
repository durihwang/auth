package com.gabia.auth;

import com.gabia.auth.entity.TokenEntity;
import com.gabia.auth.repositories.TokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@Profile("local")
@SpringBootTest(
        args = {"--spring.profiles.active=local"
                , "--hiworks.kms.role-id=a2d67753-929c-14b9-ccac-5b5086141e68"
                , "--hiworks.kms.secret-id=42a39ca7-fa56-5ec0-f085-71ce90e5d6bd"
                , "--hiworks.kms.engine-name=gabia"}
)
@AutoConfigureMockMvc
class AuthApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
	private TokenRepository tokenRepository;

    @Autowired
    ApplicationContext ctx;

    @Test
    void 토큰_검증() throws Exception{

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
}
