package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.Favorite;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.FavoriteAptDto;
import com.ssafy.vue.mapper.FavoriteMapper;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Override
	public List<FavoriteAptDto> listFavorite(String userid) throws Exception {
		return favoriteMapper.getFavoriteList(userid);
	}
	
	@Override
	public boolean addFavorite(Favorite favorite) throws Exception {
		return favoriteMapper.insertFavorite(favorite) == 1;
	}
	
	@Override
	public boolean deleteFavorite(Favorite favorite) throws Exception {
		return favoriteMapper.deleteFavorite(favorite) == 1;
	}

	@Override
	public List<HouseInfoDto> listDealInfo(String aptCode) {
		return favoriteMapper.getDealInfoList(aptCode);
	}
	
}
