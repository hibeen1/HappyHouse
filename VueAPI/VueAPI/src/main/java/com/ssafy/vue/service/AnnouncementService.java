<<<<<<< HEAD
package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Announcement;

public interface AnnouncementService {
	public List<Announcement> retrieveAnnouncement();
	public Announcement detailAnnouncement(int articleno);
	public boolean writeAnnouncement(Announcement announcement);
	public boolean updateAnnouncement(Announcement announcement);
	public boolean deleteAnnouncement(int articleno);
}
=======
package com.ssafy.vue.service;

import java.util.List;

import com.ssafy.vue.dto.Announcement;

public interface AnnouncementService {
	public List<Announcement> retrieveAnnouncement();
	public Announcement detailAnnouncement(int articleno);
	public boolean writeAnnouncement(Announcement announcement);
	public boolean updateAnnouncement(Announcement announcement);
	public boolean deleteAnnouncement(int articleno);
}
>>>>>>> 8dc7dd045e0223a381a2fd61cc44650e6dfb224f
