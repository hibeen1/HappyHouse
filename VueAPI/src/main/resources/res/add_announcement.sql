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