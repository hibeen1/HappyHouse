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
}