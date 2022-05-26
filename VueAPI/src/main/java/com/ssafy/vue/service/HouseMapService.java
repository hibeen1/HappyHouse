package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.SchoolDto;
import com.ssafy.vue.dto.SidoGugunCodeDto;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong, String dealYear, String dealMonth) throws Exception;
	List<HouseInfoDto> getAptInName(String aptName, String dealYear, String dealMonth) throws SQLException;
	List<SchoolDto> getSchools(HouseInfoDto houseInfoDto) throws SQLException;
	
}
