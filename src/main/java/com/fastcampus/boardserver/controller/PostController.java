package com.fastcampus.boardserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.PostServiceImpl;
import com.fastcampus.boardserver.service.UserServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostServiceImpl postService;
	private final UserServiceImpl userService;



	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = LoginCheck.UserType.USER)
	public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
		postService.register(accountId, postDTO);
		CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);
		return ResponseEntity.ok(commonResponse);
	}

	@GetMapping("my-posts")
	@LoginCheck(type = LoginCheck.UserType.USER)
	public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
		UserDTO memberInfo = userService.getUserInfo(accountId);
		List<PostDTO> postDTOList = postService.getMyProducts(memberInfo.getId());
		CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);
		return ResponseEntity.ok(commonResponse);
	}

	@PatchMapping("{postId}")
	@LoginCheck(type = LoginCheck.UserType.USER)
	public ResponseEntity<CommonResponse<PostRequest>> updatePosts(String accountId,
		@PathVariable(name = "postId") int postId,
		@RequestBody PostRequest postRequest) {
		UserDTO memberInfo = userService.getUserInfo(accountId);
		PostDTO postDTO = PostDTO.builder()
			.id(postId)
			.name(postRequest.getName())
			.contents(postRequest.getContents())
			.views(postRequest.getViews())
			.categoryId(postRequest.getCategoryId())
			.userId(memberInfo.getId())
			.fileId(postRequest.getFileId())
			.updateTime(new Date())
			.build();
		postService.updateProducts(postDTO);
		CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
		return ResponseEntity.ok(commonResponse);
	}

	@DeleteMapping("{postId}")
	@LoginCheck(type = LoginCheck.UserType.USER)
	public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts(String accountId,
		@PathVariable(name = "postId") int postId,
		@RequestBody PostDeleteRequest postDeleteRequest) {
		UserDTO memberInfo = userService.getUserInfo(accountId);
		postService.deleteProduct(memberInfo.getId(), postId);
		CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteposts", postDeleteRequest);
		return ResponseEntity.ok(commonResponse);
	}

	// -------------- response 객체 --------------

	@Getter
	@AllArgsConstructor
	private static class PostResponse {
		private List<PostDTO> postDTO;
	}

	// -------------- request 객체 --------------

	@Setter
	@Getter
	private static class PostRequest {
		private String name;
		private String contents;
		private int views;
		private int categoryId;
		private int userId;
		private int fileId;
		private Date updateTime;
	}

	@Setter
	@Getter
	private static class PostDeleteRequest {
		private int id;
		private int accountId;
	}
}
