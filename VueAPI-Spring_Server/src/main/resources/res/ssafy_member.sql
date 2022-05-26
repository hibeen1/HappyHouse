use ssafyweb;

CREATE TABLE IF NOT EXISTS `ssafyweb`.`announcement` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`),
  INDEX `announcement_to_member_fk` (`userid` ASC) VISIBLE,
  CONSTRAINT `announcement_to_member_fk`
    FOREIGN KEY (`userid`)
    REFERENCES `ssafyweb`.`ssafy_member` (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


insert into announcement(userid, subject, content) 
values('admin', '아아. 싸피 사무국에서 알립니다', '힝 속았지~?'),
      ('admin', '여기는 공지사항 게시판입니다', '태정태세문단세 예성연중인명선 광인효연숙경영 정순헌철고순');
   

commit;

select * from ssafy_member;
select count(*) from housedeal;
delete from housedeal where aptCode not in (select aptCode from houseinfo);
alter table `housedeal` add CONSTRAINT `housedeal_aptCode_houseinfo_aptCode_fk` FOREIGN KEY (`aptCode`) REFERENCES `houseinfo` (`aptCode`);

select * from houseinfo;
select * from favorite_apt;
insert into favorite_apt(userid, aptCode)
value('ssafy', 11140000000111);
select * from favorite_apt;
select * from ssafy_member;
select * from houseinfo;


CREATE TABLE `favorite_apt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) DEFAULT NULL,
  `aptCode` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_idx` (`userid`),
  KEY `aptCode` (`aptCode`),
  CONSTRAINT `aptCode` FOREIGN KEY (`aptCode`) REFERENCES `houseinfo` (`aptCode`),
  CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `ssafy_member` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

select * from ssafy_member;

select i.apartmentName, d.area, avg(d.dealAmount)
from houseinfo i join housedeal d on i.aptCode = d.aptCode
where i.aptCode = '11110000000001'
group by d.area;

-- 전체 가격 거래금액 정보 가져오기 
select d.dealAmount
from houseinfo i join housedeal d on i.aptCode = d.aptCode;

-- 해당 아파트 전체 거래내역 가져오기
select d.*
from houseinfo i join housedeal d on i.aptCode = d.aptCode
where i.aptCode = '11110000000001';

select * from sidocode;
select * from dongcode;
select * from houseinfo;

select h.aptCode,
	h.apartmentName,
	(select dealAmount 
    from housedeal 
    where aptCode = h.aptCode and no = (
		select max(no) 
        from housedeal 
        where aptCode = h.aptCode and dealYear = 2022 and dealMonth = 4)) recentPrice
from houseinfo h
left join sidocode si
on left(h.dongcode,2) = left(si.sidocode,2)
left join guguncode gu
on left(h.dongcode,5) = left(gu.guguncode,5)
where apartmentName like CONCAT('광화문')
having recentPrice is not null;
