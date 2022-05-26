<<<<<<< HEAD
package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.Announcement;
import com.ssafy.vue.mapper.AnnouncementMapper;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
	
    @Autowired
	private AnnouncementMapper announcementMapper;

    @Override
	public List<Announcement> retrieveAnnouncement() {
		return announcementMapper.selectAnnouncement();
	}
    
  	@Override
	public boolean writeAnnouncement(Announcement announcement) {
		return announcementMapper.insertAnnouncement(announcement) == 1;
	}

	@Override
	public Announcement detailAnnouncement(int articleno) {
		return announcementMapper.selectAnnouncementByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateAnnouncement(Announcement announcement) {
		return announcementMapper.updateAnnouncement(announcement) == 1;
	}

	@Override
	@Transactional
	public boolean deleteAnnouncement(int articleno) {
		return announcementMapper.deleteAnnouncement(articleno) == 1;
	}
=======
package com.ssafy.vue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.vue.dto.Announcement;
import com.ssafy.vue.mapper.AnnouncementMapper;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
	
    @Autowired
	private AnnouncementMapper announcementMapper;

    @Override
	public List<Announcement> retrieveAnnouncement() {
		return announcementMapper.selectAnnouncement();
	}
    
  	@Override
	public boolean writeAnnouncement(Announcement announcement) {
		return announcementMapper.insertAnnouncement(announcement) == 1;
	}

	@Override
	public Announcement detailAnnouncement(int articleno) {
		return announcementMapper.selectAnnouncementByNo(articleno);
	}

	@Override
	@Transactional
	public boolean updateAnnouncement(Announcement announcement) {
		return announcementMapper.updateAnnouncement(announcement) == 1;
	}

	@Override
	@Transactional
	public boolean deleteAnnouncement(int articleno) {
		return announcementMapper.deleteAnnouncement(articleno) == 1;
	}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
}