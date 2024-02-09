package com.fastcampus.boardserver.service;

import java.util.List;

import com.fastcampus.boardserver.dto.PostDTO;

public interface PostService {

	void register(String id, PostDTO postDTO);

	List<PostDTO> getMyProducts(int accountId);

	void updateProducts(PostDTO postDTO);

	void deleteProduct(int userId, int productId);
}