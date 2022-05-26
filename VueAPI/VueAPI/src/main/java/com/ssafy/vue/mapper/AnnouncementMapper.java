<<<<<<< HEAD
package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Announcement;
@Mapper
public interface AnnouncementMapper {
	public List<Announcement> selectAnnouncement();
	public Announcement selectAnnouncementByNo(int articleno);
	public int insertAnnouncement(Announcement announcement);
	public int updateAnnouncement(Announcement announcement);
	public int deleteAnnouncement(int articleno);
=======
package com.ssafy.vue.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.dto.Announcement;
@Mapper
public interface AnnouncementMapper {
	public List<Announcement> selectAnnouncement();
	public Announcement selectAnnouncementByNo(int articleno);
	public int insertAnnouncement(Announcement announcement);
	public int updateAnnouncement(Announcement announcement);
	public int deleteAnnouncement(int articleno);
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
}