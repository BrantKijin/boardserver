package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.reqeust.PostSearchRequest;

import com.fastcampus.boardserver.mapper.PostSearchMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {


	private final PostSearchMapper productSearchMapper;

	@Async
	@Cacheable(value = "getProducts", key = "'getProducts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
	@Override
	public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
		List<PostDTO> postDTOList = null;
		try {
			postDTOList = productSearchMapper.selectPosts(postSearchRequest);
		} catch (RuntimeException e) {
			log.error("selectPosts 실패", e.getMessage());
		}
		return postDTOList;
	}

	public List<PostDTO> getPostByTag(String tagName) {
		List<PostDTO> postDTOList = null;
		try {
			postDTOList = productSearchMapper.getPostByTag(tagName);
		} catch (RuntimeException e) {
			log.error("getPostByTag 실패", e.getMessage());
		}
		return postDTOList;
	}
}