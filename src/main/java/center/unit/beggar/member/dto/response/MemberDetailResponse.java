package center.unit.beggar.member.dto.response;

import center.unit.beggar.member.model.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDetailResponse {
    private Long memberId;
    private MemberStatus status;
}
