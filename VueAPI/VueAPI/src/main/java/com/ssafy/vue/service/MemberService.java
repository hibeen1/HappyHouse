<<<<<<< HEAD
package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	void registerMember(MemberDto memberDto) throws Exception;
	int idCheck(String checkId) throws Exception;
	
//	List<MemberDto> listMember() throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;
	boolean deleteMember(String userId) throws Exception;
}
=======
package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	void registerMember(MemberDto memberDto) throws Exception;
	int idCheck(String checkId) throws Exception;
	
//	List<MemberDto> listMember() throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;
	boolean deleteMember(String userId) throws Exception;
}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
