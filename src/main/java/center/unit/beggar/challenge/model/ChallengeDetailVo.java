package center.unit.beggar.challenge.model;

import center.unit.beggar.member.model.MemberDetailVo;
import lombok.Value;

import java.util.List;

@Value
public class ChallengeDetailVo {
    List<MemberDetailVo> memberDetailVoList;
}
