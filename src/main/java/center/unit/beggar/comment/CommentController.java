package center.unit.beggar.comment;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import center.unit.beggar.comment.dto.request.CommentDeleteDto;
import center.unit.beggar.comment.dto.request.CommentPostDto;
import center.unit.beggar.comment.dto.response.CommentResponseStatus;
import center.unit.beggar.comment.model.Comment;
import center.unit.beggar.comment.service.CommentService;
import center.unit.beggar.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ApiResponse<CommentResponseStatus> addComment(
		@RequestHeader("X-BEGGAR-MEMBER-ID") Long memberId,
		@RequestBody CommentPostDto requestDto
	) {
		Comment saveComment = commentService.saveComment(memberId, requestDto);
		CommentResponseStatus response = CommentResponseStatus.builder()
			.comment(saveComment)
			.build();
		return ApiResponse.success(response);
	}

	@DeleteMapping
	public ApiResponse<CommentResponseStatus> deleteComment(
		@RequestHeader("X-BEGGAR-MEMBER-ID") Long memberId,
		@RequestBody CommentDeleteDto requestDto
	) {
		commentService.deleteComment(memberId, requestDto);
		return ApiResponse.success();
	}

}
