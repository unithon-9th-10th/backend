package center.unit.beggar.comment.dto.request;

import center.unit.beggar.comment.model.BeggarType;
import lombok.Getter;

@Getter
public class CommentPostDto {
	private Long expenseId;
	private BeggarType beggarType;
}
