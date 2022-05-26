<<<<<<< HEAD
package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Favorite;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.MemberDto;
import com.ssafy.vue.dto.FavoriteAptDto;
import com.ssafy.vue.service.JwtServiceImpl;
import com.ssafy.vue.service.FavoriteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("FavoriteController V1")
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

	public static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private FavoriteService favoriteService;

	@ApiOperation(value = "관심매물 리스트", notes = "로그인한 유저의 모든 관심매물을 반환한다.", response = List.class)
	@GetMapping("")
	public ResponseEntity<List<FavoriteAptDto>> listFavorite(@RequestParam String userid) throws Exception {
		logger.debug("listFavorite - 호출");
		return new ResponseEntity<List<FavoriteAptDto>>(favoriteService.listFavorite(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심매물의 매매정보 리스트", notes = "해당 아파트의 매매정보 리스트를 반환한다.", response = List.class)
	@GetMapping("{aptCode}")
	public ResponseEntity<List<HouseInfoDto>> listDealInfo(@PathVariable String aptCode) throws Exception {
		logger.debug("listFavorite - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(favoriteService.listDealInfo(aptCode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심매물 추가", notes = "관심목록에 새로운 매물을 추가한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("")
	public ResponseEntity<String> addFavorite(@RequestBody Favorite favorite) throws Exception {
		logger.debug("addFavorite - 호출");
		if (favoriteService.addFavorite(favorite)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "관심매물 제거", notes = "관심목록에서 매물을 제거한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("")
	public ResponseEntity<String> deleteFavorite(@RequestBody Favorite favorite) throws Exception {
		logger.debug("deleteFavorite - 호출");
		if (favoriteService.deleteFavorite(favorite)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
=======
package com.ssafy.vue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Favorite;
import com.ssafy.vue.dto.HouseInfoDto;
import com.ssafy.vue.dto.MemberDto;
import com.ssafy.vue.dto.FavoriteAptDto;
import com.ssafy.vue.service.JwtServiceImpl;
import com.ssafy.vue.service.FavoriteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("FavoriteController V1")
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

	public static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private FavoriteService favoriteService;

	@ApiOperation(value = "관심매물 리스트", notes = "로그인한 유저의 모든 관심매물을 반환한다.", response = List.class)
	@GetMapping("")
	public ResponseEntity<List<FavoriteAptDto>> listFavorite(@RequestParam String userid) throws Exception {
		logger.debug("listFavorite - 호출");
		return new ResponseEntity<List<FavoriteAptDto>>(favoriteService.listFavorite(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심매물의 매매정보 리스트", notes = "해당 아파트의 매매정보 리스트를 반환한다.", response = List.class)
	@GetMapping("{aptCode}")
	public ResponseEntity<List<HouseInfoDto>> listDealInfo(@PathVariable String aptCode) throws Exception {
		logger.debug("listFavorite - 호출");
		return new ResponseEntity<List<HouseInfoDto>>(favoriteService.listDealInfo(aptCode), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심매물 추가", notes = "관심목록에 새로운 매물을 추가한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("")
	public ResponseEntity<String> addFavorite(@RequestBody Favorite favorite) throws Exception {
		logger.debug("addFavorite - 호출");
		if (favoriteService.addFavorite(favorite)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "관심매물 제거", notes = "관심목록에서 매물을 제거한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("")
	public ResponseEntity<String> deleteFavorite(@RequestBody Favorite favorite) throws Exception {
		logger.debug("deleteFavorite - 호출");
		if (favoriteService.deleteFavorite(favorite)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
