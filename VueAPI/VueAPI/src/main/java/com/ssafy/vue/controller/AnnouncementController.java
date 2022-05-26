<<<<<<< HEAD
package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Announcement;
import com.ssafy.vue.service.AnnouncementService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

	private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private AnnouncementService announcementService;

    @ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Announcement>> retrieveAnnouncement() throws Exception {
		logger.debug("retrieveAnnouncement - 호출");
		return new ResponseEntity<List<Announcement>>(announcementService.retrieveAnnouncement(), HttpStatus.OK);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = Announcement.class)    
	@GetMapping("{articleno}")
	public ResponseEntity<Announcement> detailAnnouncement(@PathVariable int articleno) {
		logger.debug("detailAnnouncement - 호출");
		return new ResponseEntity<Announcement>(announcementService.detailAnnouncement(articleno), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeAnnouncement(@RequestBody Announcement announcement) {
		logger.debug("writeAnnouncement - 호출");
		if (announcementService.writeAnnouncement(announcement)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{articleno}")
	public ResponseEntity<String> updateAnnouncement(@RequestBody Announcement announcement) {
		logger.debug("updateAnnouncement - 호출");
		logger.debug("" + announcement);
		
		if (announcementService.updateAnnouncement(announcement)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteAnnouncement(@PathVariable int articleno) {
		logger.debug("deleteAnnouncement - 호출");
		if (announcementService.deleteAnnouncement(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
=======
package com.ssafy.vue.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.dto.Announcement;
import com.ssafy.vue.service.AnnouncementService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

	private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private AnnouncementService announcementService;

    @ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Announcement>> retrieveAnnouncement() throws Exception {
		logger.debug("retrieveAnnouncement - 호출");
		return new ResponseEntity<List<Announcement>>(announcementService.retrieveAnnouncement(), HttpStatus.OK);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = Announcement.class)    
	@GetMapping("{articleno}")
	public ResponseEntity<Announcement> detailAnnouncement(@PathVariable int articleno) {
		logger.debug("detailAnnouncement - 호출");
		return new ResponseEntity<Announcement>(announcementService.detailAnnouncement(articleno), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeAnnouncement(@RequestBody Announcement announcement) {
		logger.debug("writeAnnouncement - 호출");
		if (announcementService.writeAnnouncement(announcement)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{articleno}")
	public ResponseEntity<String> updateAnnouncement(@RequestBody Announcement announcement) {
		logger.debug("updateAnnouncement - 호출");
		logger.debug("" + announcement);
		
		if (announcementService.updateAnnouncement(announcement)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteAnnouncement(@PathVariable int articleno) {
		logger.debug("deleteAnnouncement - 호출");
		if (announcementService.deleteAnnouncement(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
}