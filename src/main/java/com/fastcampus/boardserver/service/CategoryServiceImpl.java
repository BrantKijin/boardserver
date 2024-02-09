package com.fastcampus.boardserver.service;

import org.springframework.stereotype.Service;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

	private CategoryMapper categoryMapper;
	@Override
	public void register(String accountId, CategoryDTO categoryDTO) {
		if(accountId != null){
			categoryMapper.register(categoryDTO);

		}else{
			log.error("register Error!! {} ", categoryDTO);
			throw new RuntimeException("register Error! 상품 카테고리 등록 메서드를 확인해주세요\n" + "Params:" + categoryDTO);
		}
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		if (categoryDTO != null) {
			categoryMapper.updateCategory(categoryDTO);
		} else {
			log.error("update ERROR! {}", categoryDTO);
			throw new RuntimeException("update ERROR! 물품 카테고리 변경 메서드를 확인해주세요\n" + "Params : " + categoryDTO);
		}

	}

	@Override
	public void delete(int categoryId) {
		if (categoryId != 0) {
			categoryMapper.deleteCategory(categoryId);
		} else {
			log.error("deleteCategory ERROR! {}", categoryId);
			throw new RuntimeException("deleteCategory ERROR! 물품 카테고리 삭제 메서드를 확인해주세요\n" + "Params : " + categoryId);
		}
	}
}
