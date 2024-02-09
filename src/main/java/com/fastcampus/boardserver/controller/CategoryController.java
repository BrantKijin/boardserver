package com.fastcampus.boardserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.service.CategoryService;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	private CategoryService categoryService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@LoginCheck(type = LoginCheck.UserType.ADMIN)
	public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
		categoryService.register(accountId, categoryDTO);
	}

	@PatchMapping("{categoryId}")
	@LoginCheck(type = LoginCheck.UserType.ADMIN)
	public void updateCategories(String accountId,
		@PathVariable(name = "categoryId") int categoryId,
		@RequestBody CategoryRequest categoryRequest) {
		CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), CategoryDTO.SortStatus.NEWEST,10,1);
		categoryService.update(categoryDTO);
	}

	@DeleteMapping("{categoryId}")
	@LoginCheck(type = LoginCheck.UserType.ADMIN)
	public void updateCategories(String accountId,
		@PathVariable(name = "categoryId") int categoryId) {
		categoryService.delete(categoryId);
	}

	// -------------- request 객체 --------------

	@Setter
	@Getter
	private static class CategoryRequest {
		private int id;
		private String name;
	}
}
