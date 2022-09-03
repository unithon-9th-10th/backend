package center.unit.beggar.challenge;

import center.unit.beggar.challenge.dto.ChallengeResponse;
import center.unit.beggar.challenge.dto.request.AddMemberRequest;
import center.unit.beggar.challenge.dto.request.ChallengePostDto;
import center.unit.beggar.dto.ApiResponse;
import center.unit.beggar.member.dto.response.MemberDetailResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ChallengeDetailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() throws Exception {
        // 가입
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/members/signup"))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponse<MemberDetailResponse> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        Long memberId = response.getData().getMemberId();

        // 챌린지 생성
        ChallengePostDto challengePostDto = new ChallengePostDto(
                "title",
                3,
                BigDecimal.valueOf(10000L)
        );
        MvcResult createChallengeResult = mockMvc.perform(post("/api/v1/challenges")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(challengePostDto)))
                .andExpect(status().isOk())
                .andReturn();
        ApiResponse<ChallengeResponse> challengeApiResponse = objectMapper.readValue(
                createChallengeResult.getResponse().getContentAsByteArray(),
                new TypeReference<>() {
                });
        Long challengeId = challengeApiResponse.getData().getChallengeId();

        // 멤버 추가
        AddMemberRequest addMemberRequest = new AddMemberRequest();
        addMemberRequest.setNickname("nickname");
        mockMvc.perform(put("/api/v1/challenges/{challengeId}/members/{memberId}", challengeId, memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(addMemberRequest)))
                .andExpect(status().isOk());

        // 챌린지 조회
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/challenge")
                .header("X-BEGGAR-MEMBER-ID", memberId))
                .andExpect(status().isOk());
    }
}