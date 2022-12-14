package center.unit.beggar.comment.dto.response;

import center.unit.beggar.comment.model.BeggarType;
import center.unit.beggar.comment.model.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseStatus {
	private BeggarType beggarType;
	private Integer beggarPoint;

	@Builder
	public CommentResponseStatus(Comment comment) {
		this.beggarType = comment.getBeggarType();
		this.beggarPoint = comment.getBeggarPoint();
	}
}
