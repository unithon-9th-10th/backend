package center.unit.beggar.member;

import center.unit.beggar.dto.ApiResponse;
import center.unit.beggar.member.dto.response.MemberDetailResponse;
import center.unit.beggar.member.model.MemberStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup() throws Exception {
        mockMvc.perform(post("/api/v1/members/signup"))
                .andExpect(status().isOk());
    }

    @Test
    void getMemberDetail() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/members/signup"))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponse<MemberDetailResponse> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        Long memberId = response.getData().getMemberId();

        mockMvc.perform(get("/api/v1/members/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.status").value(MemberStatus.READY.name()));
    }

}