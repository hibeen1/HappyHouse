package com.ssafy.vue.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.SchoolDto;
import com.ssafy.vue.dto.SidoGugunCodeDto;
import com.ssafy.vue.mapper.HouseMapMapper;

@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong, String dealYear, String dealMonth) throws Exception {
//		System.out.println(houseMapMapper.getAptInDong(dong, dealYear, dealMonth));
		return houseMapMapper.getAptInDong(dong, dealYear, dealMonth);
	}

	@Override
	public List<HouseInfoDto> getAptInName(String aptName, String dealYear, String dealMonth) throws SQLException {
		return houseMapMapper.getAptInName(aptName, dealYear, dealMonth);
	}
	
	@Override
	public List<SchoolDto> getSchools(HouseInfoDto houseInfoDto) throws SQLException {
		final int TARGET_RADIUS = 1000;
		double aptLat = Double.parseDouble(houseInfoDto.getLat());
		double aptLng = Double.parseDouble(houseInfoDto.getLng());
		List<SchoolDto> schooList = new ArrayList<>();
		List<SchoolDto> allSchooList = houseMapMapper.getSchools(houseInfoDto.getSidoName());
		for (SchoolDto schoolDto : allSchooList) {
			double schoolLat = Double.parseDouble(schoolDto.getLatitude());
			double schoolLng = Double.parseDouble(schoolDto.getLongitude());
			if (Logic.distance(aptLat, aptLng, schoolLat, schoolLng) <= TARGET_RADIUS) {
				schooList.add(schoolDto);
			}
		}
		
		return schooList; 
	}
	
}
