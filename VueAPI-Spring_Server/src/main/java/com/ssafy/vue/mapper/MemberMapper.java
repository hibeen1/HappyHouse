package com.ssafy.vue.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.dto.MemberDto;


public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	void registerMember(MemberDto memberDto) throws Exception;
	int idCheck(String checkId) throws Exception;
	
//	List<MemberDto> listMember() throws Exception;
	int updateMember(MemberDto memberDto) throws Exception;
	int deleteMember(String userid) throws Exception;
}