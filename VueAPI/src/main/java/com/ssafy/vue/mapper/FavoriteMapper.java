package com.ssafy.vue.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ssafy.vue.dto.FavoriteAptDto;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Favorite;
import com.ssafy.vue.dto.HouseInfoDto;
@Mapper
public interface FavoriteMapper {
	public List<FavoriteAptDto> getFavoriteList(String userid);
	public int insertFavorite(Favorite favorite);
	public int deleteFavorite(Favorite favorite);
	public List<HouseInfoDto> getDealInfoList(String aptCode);
}