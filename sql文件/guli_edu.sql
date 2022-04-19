/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.5.58 : Database - guli_edu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`guli_edu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `guli_edu`;

/*Table structure for table `crm_banner` */

DROP TABLE IF EXISTS `crm_banner`;

CREATE TABLE `crm_banner` (
  `id` char(19) NOT NULL DEFAULT '' COMMENT 'ID',
  `title` varchar(20) DEFAULT '' COMMENT '标题',
  `image_url` varchar(500) NOT NULL DEFAULT '' COMMENT '图片地址',
  `link_url` varchar(500) DEFAULT '' COMMENT '链接地址',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首页banner表';

/*Data for the table `crm_banner` */

insert  into `crm_banner`(`id`,`title`,`image_url`,`link_url`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1194556896025845762','test1','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg','/course',4,0,'2019-11-13 18:05:32','2019-11-18 10:28:22'),('1194607458461216123','微信支付实战123','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/banner/1513575e3e7e291857.jpg','/course',3,0,'2022-03-02 15:37:54','2022-03-03 14:44:12'),('1499272486004162561','test','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg','',5,0,'2022-03-03 14:36:30','2022-03-03 14:36:30'),('1499273683691864065','test4','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/03/669007bb0bc649ec9eb13dd2c3998637banner-2-blue.jpg','/teacher',5,0,'2022-03-03 14:41:16','2022-03-03 14:41:16'),('1499275836833628162','课程','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/03/5e891238ee934dcd8b3352a8cbed98debanner-1-green.jpg','\\course',6,0,'2022-03-03 14:49:49','2022-03-03 14:50:14'),('1499276177616633858','teacher','https://online-teach-file.oss-cn-beijing.aliyuncs.com/cms/2019/11/14/297acd3b-b592-4cfb-a446-a28310369675.jpg','\\teacher1',7,0,'2022-03-03 14:51:10','2022-03-03 16:18:37'),('1499278036662190082','testt5','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/03/df64936d819d4c3f95b7b4749e7605501513575e3e7e291857.jpg','\\test',4,0,'2022-03-03 14:58:33','2022-03-03 14:58:33');

/*Table structure for table `edu_chapter` */

DROP TABLE IF EXISTS `edu_chapter`;

CREATE TABLE `edu_chapter` (
  `id` char(19) NOT NULL COMMENT '章节ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `title` varchar(50) NOT NULL COMMENT '章节名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_chapter` */

insert  into `edu_chapter`(`id`,`course_id`,`title`,`sort`,`gmt_create`,`gmt_modified`) values ('1497374141157416961','1497374105296117761','第一章',1,'2022-02-26 08:53:09','2022-02-26 08:53:09'),('1497374165870256130','1497374105296117761','第二章',2,'2022-02-26 08:53:15','2022-02-26 08:53:15'),('1497374200158691329','1497374105296117761','第三章',3,'2022-02-26 08:53:23','2022-02-26 08:53:23'),('1497374229598511106','1497374105296117761','第四章',4,'2022-02-26 08:53:30','2022-02-26 08:53:30'),('1497374250209320962','1497374105296117761','第五章',0,'2022-02-26 08:53:35','2022-02-26 08:53:35'),('1497374280093736962','1497374105296117761','第六章',6,'2022-02-26 08:53:42','2022-02-26 08:53:42'),('1497827661446836225','1497827625069637634','第一章',1,'2022-02-27 14:55:17','2022-02-27 14:55:17'),('1498939097426370562','1498939042573262849','第一章',1,'2022-03-02 16:31:44','2022-03-02 16:31:44'),('1515611536801595393','1515611502332805122','第一章',1,'2022-04-17 16:42:03','2022-04-17 16:42:03'),('1515611558985269249','1515611502332805122','第二章',1,'2022-04-17 16:42:09','2022-04-17 16:42:09'),('1515611582607589378','1515611502332805122','第三章',3,'2022-04-17 16:42:14','2022-04-17 16:42:14');

/*Table structure for table `edu_comment` */

DROP TABLE IF EXISTS `edu_comment`;

CREATE TABLE `edu_comment` (
  `id` char(19) NOT NULL COMMENT '评论ID',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论';

/*Data for the table `edu_comment` */

insert  into `edu_comment`(`id`,`course_id`,`member_id`,`content`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1503676375537205249','1498939281543733250','1503574953088962562','12356',0,'2022-03-15 18:15:59','2022-03-15 18:15:59'),('1503677431541317633','1498939281543733250','1503574953088962562','1265',0,'2022-03-15 18:20:11','2022-03-15 18:20:11'),('1503691054837911553','1497374105296117761','1503574953088962562','12646546',0,'2022-03-15 19:14:19','2022-03-15 19:14:19'),('1503691054854688769','1497374105296117761','1503574953088962562','12646546',0,'2022-03-15 19:14:19','2022-03-15 19:14:19'),('1503691056121368577','1497374105296117761','1503574953088962562','',0,'2022-03-15 19:14:19','2022-03-15 19:14:19'),('1503691222362607618','1498939042573262849','1503574953088962562','123456',0,'2022-03-15 19:14:59','2022-03-15 19:14:59'),('1503691254654554114','1498939042573262849','1503574953088962562','156151123',0,'2022-03-15 19:15:07','2022-03-15 19:15:07'),('1503691287391096834','1498939042573262849','1503574953088962562','kkkkkkkkkkkkkkk',0,'2022-03-15 19:15:14','2022-03-15 19:15:14'),('1503736870717870081','1497374105296117761','1503574953088962562','111111111111\n',0,'2022-03-15 22:16:22','2022-03-15 22:16:22'),('1503736894281469954','1497374105296117761','1503574953088962562','124567987',0,'2022-03-15 22:16:28','2022-03-15 22:16:28'),('1503737398873018370','1498939042573262849','1503574953088962562','jadflkjja;fkd',0,'2022-03-15 22:18:28','2022-03-15 22:18:28'),('1503737516409999361','1497374105296117761','1503574953088962562','123456151\n',0,'2022-03-15 22:18:56','2022-03-15 22:18:56'),('1503737545099038721','1497374105296117761','1503574953088962562','1231512',0,'2022-03-15 22:19:03','2022-03-15 22:19:03'),('1503737564187316226','1497374105296117761','1503574953088962562','5616515161',0,'2022-03-15 22:19:08','2022-03-15 22:19:08'),('1503738278817026050','1497374105296117761','1503574953088962562','123456',0,'2022-03-15 22:21:58','2022-03-15 22:21:58'),('1503738486284079106','1497374105296117761','1502234599249129474','123456',0,'2022-03-15 22:22:47','2022-03-15 22:22:47'),('1503738543339196418','1497374105296117761','1502234599249129474','哈哈哈哈,价格太贵了',0,'2022-03-15 22:23:01','2022-03-15 22:23:01'),('1503738621533605889','1498939042573262849','1502234599249129474','嗯...狗仔,你咋还在这里呢',0,'2022-03-15 22:23:20','2022-03-15 22:23:20'),('1503741398510981122','1498939042573262849','1501865429156208642','呵呵\n',0,'2022-03-15 22:34:22','2022-03-15 22:34:22'),('1503979507903238146','1498939281543733250','1501865429156208642','123456',0,'2022-03-16 14:20:31','2022-03-16 14:20:31'),('1503979507911626754','1498939281543733250','1501865429156208642','123456',0,'2022-03-16 14:20:31','2022-03-16 14:20:31'),('1504039873815461889','1497374105296117761','1501865429156208642','',0,'2022-03-16 18:20:24','2022-03-16 18:20:24'),('1504397948585934849','1497827625069637634','1501865429156208642','',0,'2022-03-17 18:03:15','2022-03-17 18:03:15'),('1504397948585934850','1497827625069637634','1501865429156208642','',0,'2022-03-17 18:03:15','2022-03-17 18:03:15'),('1504398439957037057','1497827625069637634','1501865429156208642','',0,'2022-03-17 18:05:13','2022-03-17 18:05:13'),('1504398590473830401','1498939042573262849','1501865429156208642','afdfda',0,'2022-03-17 18:05:49','2022-03-17 18:05:49'),('1504398614146482177','1498939042573262849','1501865429156208642',' ',0,'2022-03-17 18:05:54','2022-03-17 18:05:54'),('1504398643691159553','1498939042573262849','1501865429156208642','a  ',0,'2022-03-17 18:06:01','2022-03-17 18:06:01');

/*Table structure for table `edu_course` */

DROP TABLE IF EXISTS `edu_course`;

CREATE TABLE `edu_course` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) DEFAULT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) DEFAULT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) DEFAULT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) unsigned DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) unsigned DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint(10) unsigned DEFAULT '0' COMMENT '浏览数量',
  `version` bigint(20) unsigned DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint(3) DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程';

/*Data for the table `edu_course` */

insert  into `edu_course`(`id`,`teacher_id`,`subject_id`,`subject_parent_id`,`title`,`price`,`lesson_num`,`cover`,`buy_count`,`view_count`,`version`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1497374105296117761','1497371035963170817','1495631214316060673','1495631214211203073','Java入门基础','0.01',200,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',200,1000,1,'Normal',NULL,'2022-02-26 08:53:01','2022-03-13 18:14:34'),('1497827625069637634','1497370752482746370','1495631706735738882','1495631706735738881','世上最牛的Linux教程','10.00',200,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/317a045332754835845d2af9b8c5c703shujujiegou.png',1500,3500,1,'Normal',NULL,'2022-02-27 14:55:08','2022-03-13 18:13:10'),('1498939042573262849','1497371035963170817','1495631706907705345','1495631706869956610','Mysql从入门到进阶','10.00',100,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/02/91eb0ed84a9443c8ae87a89a5c6d785emysql.jpg',85000,98555,1,'Normal',NULL,'2022-03-02 16:31:31','2022-03-02 16:31:47'),('1498939281543733250','1497373205726961665','1495665977391243266','1495665977324134401','Html从入门到进阶','0.00',360,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/3040385d03c64ec5a6e5e82846c08ef4vue.jpg',3000,124567,1,'Normal',NULL,'2022-03-02 16:32:28','2022-03-13 18:13:40'),('1498939463886905345','1497372669002850305','1495631214316060673','1495631214211203073','Java高级教程','0.00',100,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/02/28837fed0e3e4f62b2c646184593a6b9mysql.jpg',2600,15656,1,'Normal',NULL,'2022-03-02 16:33:11','2022-03-02 17:13:27'),('1498939631638093826','1497370752482746370','1495631706668630018','1495631214211203073','谷粒学院项目','30.00',150,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/e00f686c3a7e40a38888070a8b34339fJavaEE.png',90000,89251,1,'Normal',NULL,'2022-03-02 16:33:51','2022-03-13 18:11:39'),('1498939753990135809','1497371035963170817','1495631214437695490','1495631214211203073','Python高级教程','0.00',150,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/02/b91d868706014173832bc698e1ca84eaJavaWeb.png',15121,14562,1,'Normal',NULL,'2022-03-02 16:34:20','2022-03-02 17:12:46'),('1498939903856812034','1497372419596951553','1495631214504804354','1495631214211203073','传智健康教程','20.00',150,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/38c176acbcc24e5fb44b1deb1ab2e7a6JavaWeb.png',80000,94512,1,'Normal',NULL,'2022-03-02 16:34:56','2022-04-17 16:38:26'),('1515611502332805122','1497372669002850305','1495631706538606593','1495631214211203073','Python人工智能','5.00',300,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/course.jpg',0,0,1,'Normal',NULL,'2022-04-17 16:41:55','2022-04-17 16:43:38');

/*Table structure for table `edu_course_collect` */

DROP TABLE IF EXISTS `edu_course_collect`;

CREATE TABLE `edu_course_collect` (
  `id` char(19) NOT NULL COMMENT '收藏ID',
  `course_id` char(19) NOT NULL COMMENT '课程讲师ID',
  `member_id` char(19) NOT NULL DEFAULT '' COMMENT '课程专业ID',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程收藏';

/*Data for the table `edu_course_collect` */

insert  into `edu_course_collect`(`id`,`course_id`,`member_id`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1196269345666019330','1192252213659774977','1',1,'2019-11-18 11:30:12','2019-11-18 11:30:12');

/*Table structure for table `edu_course_description` */

DROP TABLE IF EXISTS `edu_course_description`;

CREATE TABLE `edu_course_description` (
  `id` char(19) NOT NULL COMMENT '课程ID',
  `description` text COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程简介';

/*Data for the table `edu_course_description` */

insert  into `edu_course_description`(`id`,`description`,`gmt_create`,`gmt_modified`) values ('1497374105296117761','<p><strong>你值得拥有!!!</strong></p>','2022-02-26 08:53:01','2022-03-13 18:14:34'),('1497827625069637634','<p>带你wanzhuanLinux</p>','2022-02-27 14:55:08','2022-03-13 18:13:10'),('1498939042573262849','<p>123456</p>','2022-03-02 16:31:31','2022-03-02 16:31:31'),('1498939281543733250','<p>123465</p>','2022-03-02 16:32:28','2022-03-13 18:13:40'),('1498939463886905345','<p>123456</p>','2022-03-02 16:33:11','2022-03-02 17:13:23'),('1498939631638093826','<p>123465</p>','2022-03-02 16:33:51','2022-03-13 18:11:39'),('1498939753990135809',NULL,'2022-03-02 16:34:20','2022-03-02 17:12:46'),('1498939903856812034','<p><strong>123465</strong></p>','2022-03-02 16:34:56','2022-04-17 16:38:22'),('1515611502332805122','<p>无</p>','2022-04-17 16:41:55','2022-04-17 16:41:55');

/*Table structure for table `edu_subject` */

DROP TABLE IF EXISTS `edu_subject`;

CREATE TABLE `edu_subject` (
  `id` char(19) NOT NULL COMMENT '课程类别ID',
  `title` varchar(50) NOT NULL COMMENT '类别名称',
  `parent_id` char(19) NOT NULL DEFAULT '0' COMMENT '父ID',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程科目';

/*Data for the table `edu_subject` */

insert  into `edu_subject`(`id`,`title`,`parent_id`,`sort`,`gmt_create`,`gmt_modified`) values ('1495631214211203073','Java后端','0',0,'2022-02-21 13:27:23','2022-02-21 13:27:23'),('1495631214316060673','JavaSE','1495631214211203073',0,'2022-02-21 13:27:23','2022-02-21 13:27:23'),('1495631214437695490','JavaWeb','1495631214211203073',0,'2022-02-21 13:27:23','2022-02-21 13:27:23'),('1495631214504804354','SSM','1495631214211203073',0,'2022-02-21 13:27:23','2022-02-21 13:27:23'),('1495631706538606593','Spring Boot2','1495631214211203073',0,'2022-02-21 13:29:20','2022-02-21 13:29:20'),('1495631706668630018','Spring Cloud','1495631214211203073',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706735738881','网络与运维','0',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706735738882','Linux','1495631706735738881',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706802847745','k8s','1495631706735738881',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706869956610','数据库','0',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706907705345','Mysql','1495631706869956610',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631706991591426','Redis','1495631706869956610',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495631707029340162','MongoDB','1495631706869956610',0,'2022-02-21 13:29:21','2022-02-21 13:29:21'),('1495665977055698945','DevOps','1495631706735738881',0,'2022-02-21 15:45:31','2022-02-21 15:45:31'),('1495665977324134401','前端开发','0',0,'2022-02-21 15:45:31','2022-02-21 15:45:31'),('1495665977391243266','Html/CSS','1495665977324134401',0,'2022-02-21 15:45:31','2022-02-21 15:45:31'),('1495665977458352129','Jquery','1495665977324134401',0,'2022-02-21 15:45:31','2022-02-21 15:45:31'),('1495665977525460994','Vue3','1495665977324134401',0,'2022-02-21 15:45:31','2022-02-21 15:45:31'),('1495665977592569857','JavaScript & ES6','1495665977324134401',0,'2022-02-21 15:45:31','2022-02-21 15:45:31');

/*Table structure for table `edu_teacher` */

DROP TABLE IF EXISTS `edu_teacher`;

CREATE TABLE `edu_teacher` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int(10) unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讲师';

/*Data for the table `edu_teacher` */

insert  into `edu_teacher`(`id`,`name`,`intro`,`career`,`level`,`avatar`,`sort`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1497370752482746370','李明','从业近二十年，曾任即时Linux研究院副院长、PHPChina技术总监等职，国内早期的UNIX/Linux从业者，中国UNIX用户协会成员，首批红旗Linux认证讲师。先后出版过《UNIX系统管理实用教程》、《Sun Solaris 8系统管理员指南》、《PHP5项目开发实战详解》、《完美应用Ubuntu》、《细说Linux系统管理》等多本计算机技术图书，国内多所大学特聘讲师，猎聘特邀讲师。近年来致力于Linux开源技术推广及职业教育，录制的Linux视频教程是无数从业者的入门宝典，出版的就业指导图书《明哥聊求职》广受好评，曾作客BTV《书香北京》栏目分享，在中国大学慕课网开设《年轻人的第一堂就业指导课》，2017年明哥聊求职获教育行业优秀自媒体奖，全网粉丝近百万。','风趣幽默，技术纵深与横向打通皆备，深受喜爱的教父级讲师风范',2,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/e6fee519ff1442f6b5979efbd2bb0982file.png',2,0,'2022-02-26 08:39:41','2022-02-26 08:39:41'),('1497371035963170817','宋红康','东北师大理学学士，北京航空航天大学硕士，曾于北航软件开发环境国家重点实验室研究多项课题，对智能交通—浮动车海量数据挖掘及在线社交网络信息传播和控制问题有深入研究，曾发表论文数篇。先后担任过高级软件开发工程师，系统架构师，高级讲师。具备丰富的软件开发经验和教学经验。精通C/C++、Java、Objective-C 等开发语言， 对JavaEE、Android开发有深入理解，对以Java语言为基础的各种框架有深入研究。亲自主持开发过多个大型项目，具备很强的项目管理能力和丰富的项目实施经验。','深入浅出、旁征博引，倡导责任教育和赏识教育，激发学生内在学习驱动力',2,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/ef3a19d0d32e4eaca456499bf98f8413file.png',3,0,'2022-02-26 08:40:49','2022-02-26 08:40:49'),('1497371416747253761','周阳','多年开发及管理经验，曾先后就职于神华和信、亚信联创、安润金融等大中型互联网公司，任技术经理、项目经理、架构师等职位。历经电信、互联网金融等热门行业的项目历练，对传统JavaEE技术体系、云计算、大数据及当下热门的互联网技术都具有深厚的技术功底。 能够将企业流行、实用的技术带回课堂，引导学生少走弯路。','教法凶悍，厚积薄发，激发式教学，让你睡觉都觉得奢侈',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/065c654125844463a630c31f1b32c4d1file.png',4,0,'2022-02-26 08:42:20','2022-02-26 08:51:46'),('1497371723359264770','缪传海','辽工大外聘大数据、区块链专家、辽工大保送硕士，曾就职于交大思诺、文思海辉、宅急送等知名企业，历任项目经理、架构师 。多年项目开发、管理经验。精通 Go、Hadoop、Spark、Android、Java、C/C++，对区块链、大数据、Linux 等有深入研究。技术功底深厚，热衷于新技术的研究。从业IT教育多年，学员多任职于腾讯、联想、京东、新浪、Oracle、IBM 等国内外互联网公司。人称大海哥，号称尚硅谷第一帅。','深入浅出，激情派授课，深受线上线下同学们喜爱',2,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/759329bb49494f7ba6e67e94732d0cbffile.png',5,0,'2022-02-26 08:43:33','2022-02-26 08:43:33'),('1497372212117315586','封杰','具多年软件开发经验，曾主持或参与开发《中国电力轨道交通线路数据交换平台》、《SG186工程宁夏电网营销 稽查管理项目》、《天津市广告监管系统》等项目。对JavaEE 技术体系结构、JavaWeb 原理有深刻理解，精通多种主流框架以及 Maven、Ant 等构建工具。 课堂教学循序渐进，深入浅出，主张快乐学习，学以致用。','善于使用生活中的例子讲解技术原理，引导学生认识所学知识的本质',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/58cc30f2f2894fb6bb12bc6a91fffaf7file.png',6,0,'2022-02-26 08:45:29','2022-02-26 08:45:29'),('1497372419596951553','李玉婷','具有多年项目开发和教育培训经验，曾先后任职于致远协创、用友科技等知名企业，历任项目经理、架构师等，精通JavaEE、Android、 C++、C#等开发语言，主持并参与Android SDK开发，熟练掌握JavaEE技术及架构体系、SpringMVC、Spring、 Mybatis、Redis、Struts2、Hibernate等框架','通俗易懂，婉约派，课堂语言极具魅力',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/84a9367491cb42d9b6b3bed7497faaa2file.png',7,0,'2022-02-26 08:46:19','2022-02-26 08:46:19'),('1497372669002850305','雷峰阳','具有多年软件开发经验，熟悉 Java、C/C++等多种开发语言，对主流框架Spring、Struts2、Hibernate、MyBatis、SpringMVC等具有丰富的开发经验。曾参与大型物联网系统（智能物流）、智慧城市系统的开发，并主持开发某社交软件的移动端（Android/iOS）、服务端（openfire）、桌面端（swing）、网页端（webIM），对即时通讯、图像处理、流媒体领域等有深入研究。','激情澎湃，大处着眼，生动有趣',2,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/1262fcd5159140ceb940eaea638ee0eafile.png',8,0,'2022-02-26 08:47:18','2022-02-26 08:47:18'),('1497372971366031361','张晨','中国人民大学软件工程硕士。精通java核心框架、大数据Hadoop、Spark技术，曾先后就职于南天软件、用友金融、中植集团、百合贷，任技术经理、项目经理、技术部负责人。主持开发了中信银行、国家电网、中植集团、东方资产等大型企业的金融类系统，也对时下流行的互联网金融有深入的研究。 十余年的项目经历，练就了纯厚的技术底蕴和丰富的职场经验。','授课过程中融入实战应用，实战应用中穿插职场经验，一以贯之，娓娓道来',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/34035875fdf84a13a62f83fcfede5a44file.png',9,0,'2022-02-26 08:48:30','2022-02-26 08:48:30'),('1497373205726961665','柴林燕','尚硅谷高级讲师，具有多年的教学和项目开发经验。曾参与国家电网内蒙古供电企业一体化管理信息系统的研发、国土资源局的国土资源非空间数据服务系统的研发、赛迪时代公司内部项目通用基础组件的设计与研发，并为新华南方等众多IT企业储备人才及广东、山东等多所高校大学生进行实训授课。精通Oracle、MySQL、SSH、MyBatis、JBPM等众多框架，现专注于JavaSE技术的研究、教学和推广。“细心，耐心，用心”是一贯坚持的工作态度，追求“润物细无声”的教育方式。','“细心，耐心，用心”是一贯坚持的教学态度，“润物细无声”的教育方式。',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/de4074f25d084d468d25fcba52b50f75file.png',10,0,'2022-02-26 08:49:26','2022-02-26 08:49:26'),('1497373482970456065','张宇','从事JavaEE技术多年，一直致力于Java技术培训，热爱教育。深入掌握Struts、Hibernate、Spring、SpringMVC、MyBatis、EJB等主流框架,通过分析源码，讲解框架设计原理。授课细致，深入浅出，深受学生喜爱。曾参与开发了首钢集团OA系统；CMMI项目管理系统；eGov电子政务系统等。对学生寄语：既然选择就别轻言放弃!','细致、认真、无微不至',1,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/02/26/5e8cdd3b33db45f7b4c2a0a3213a6125file.png',11,0,'2022-02-26 08:50:32','2022-03-03 16:26:08');

/*Table structure for table `edu_video` */

DROP TABLE IF EXISTS `edu_video`;

CREATE TABLE `edu_video` (
  `id` char(19) NOT NULL COMMENT '视频ID',
  `course_id` char(19) NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) NOT NULL COMMENT '章节ID',
  `title` varchar(50) NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) DEFAULT NULL COMMENT '原始文件名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='课程视频';

/*Data for the table `edu_video` */

insert  into `edu_video`(`id`,`course_id`,`chapter_id`,`title`,`video_source_id`,`video_original_name`,`sort`,`play_count`,`is_free`,`duration`,`status`,`size`,`version`,`gmt_create`,`gmt_modified`) values ('1497374325882953729','1497374105296117761','1497374141157416961','第一节','702a5be3c6c94088b5424758655daabc','6 - What If I Want to Move Faster.mp4',1,0,1,0,'Empty',0,1,'2022-02-26 08:53:53','2022-02-26 19:24:33'),('1497374358321700865','1497374105296117761','1497374141157416961','第二节','',NULL,2,0,1,0,'Empty',0,1,'2022-02-26 08:54:01','2022-02-26 17:16:31'),('1497374393629351937','1497374105296117761','1497374141157416961','第三节','',NULL,3,0,1,0,'Empty',0,1,'2022-02-26 08:54:09','2022-02-26 08:54:09'),('1497374420200267778','1497374105296117761','1497374141157416961','第四节','',NULL,0,0,0,0,'Empty',0,1,'2022-02-26 08:54:16','2022-02-26 08:54:16'),('1497374477691592705','1497374105296117761','1497374165870256130','第一节','857ab9b1a21e4663b9a2ae07e49eb562','6 - What If I Want to Move Faster.mp4',1,0,0,0,'Empty',0,1,'2022-02-26 08:54:30','2022-02-26 17:18:00'),('1497518702957666305','1497374105296117761','1497374141157416961','第五节','4fd76dbd49d04107bc25d873f998f873','6 - What If I Want to Move Faster.mp4',4,0,1,0,'Empty',0,1,'2022-02-26 18:27:35','2022-02-26 18:27:35'),('1497827839968997378','1497827625069637634','1497827661446836225','第一节','fb071adc0f434f7cbbf075cdae8e7ae1','6 - What If I Want to Move Faster.mp4',1,0,1,0,'Empty',0,1,'2022-02-27 14:56:00','2022-02-27 14:56:00'),('1515611739164180482','1515611502332805122','1515611536801595393','第一节 Python的由来','','',1,0,1,0,'Empty',0,1,'2022-04-17 16:42:51','2022-04-17 16:43:27'),('1515611811750805505','1515611502332805122','1515611536801595393','第二节','','',2,0,0,0,'Empty',0,1,'2022-04-17 16:43:09','2022-04-17 16:43:09');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `course_id` varchar(19) NOT NULL DEFAULT '' COMMENT '课程id',
  `course_title` varchar(100) DEFAULT NULL COMMENT '课程名称',
  `course_cover` varchar(255) DEFAULT NULL COMMENT '课程封面',
  `teacher_name` varchar(20) DEFAULT NULL COMMENT '讲师名称',
  `member_id` varchar(19) NOT NULL DEFAULT '' COMMENT '会员id',
  `nickname` varchar(50) DEFAULT NULL COMMENT '会员昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '会员手机',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '订单金额（分）',
  `pay_type` tinyint(3) DEFAULT NULL COMMENT '支付类型（1：微信 2：支付宝）',
  `status` tinyint(3) DEFAULT NULL COMMENT '订单状态（0：未支付 1：已支付）',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_order_no` (`order_no`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

/*Data for the table `t_order` */

insert  into `t_order`(`id`,`order_no`,`course_id`,`course_title`,`course_cover`,`teacher_name`,`member_id`,`nickname`,`mobile`,`total_fee`,`pay_type`,`status`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1504361589133455362','20220317153847680','1497374105296117761','Java入门基础','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',NULL,'1501865429156208642','傻狗','13525414492','5.00',1,0,0,'2022-03-17 15:38:47','2022-03-17 15:38:47'),('1504362763924451330','20220317154327513','1497374105296117761','Java入门基础','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',NULL,'1501865429156208642','傻狗','13525414492','0.01',1,0,0,'2022-03-17 15:43:27','2022-03-17 15:43:27'),('1504368229547159553','20220317160510274','1497374105296117761','Java入门基础','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',NULL,'1501865429156208642','傻狗','13525414492','0.01',1,1,0,'2022-03-17 16:05:10','2022-03-17 16:05:49'),('1504369077895475201','20220317160832218','1497374105296117761','Java入门基础','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',NULL,'1501865429156208642','傻狗','13525414492','0.01',1,1,0,'2022-03-17 16:08:32','2022-03-17 16:09:14'),('1515613105282560001','20220417164817699','1515611502332805122','Python人工智能','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/course.jpg',NULL,'1502234599249129474','Hello，world','','5.00',1,0,0,'2022-04-17 16:48:17','2022-04-17 16:48:17'),('1515613105282560002','20220417164817138','1515611502332805122','Python人工智能','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/course.jpg',NULL,'1502234599249129474','Hello，world','','5.00',1,0,0,'2022-04-17 16:48:17','2022-04-17 16:48:17'),('1515613412263669762','20220417164930618','1497374105296117761','Java入门基础','https://guli-photos.oss-cn-hangzhou.aliyuncs.com/2022/03/13/247dfe64e4d14e23ae701d7712ca19d0hello.jpg',NULL,'1502234599249129474','Hello，world','','0.01',1,1,0,'2022-04-17 16:49:30','2022-04-17 16:49:49');

/*Table structure for table `t_pay_log` */

DROP TABLE IF EXISTS `t_pay_log`;

CREATE TABLE `t_pay_log` (
  `id` char(19) NOT NULL DEFAULT '',
  `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
  `pay_time` datetime DEFAULT NULL COMMENT '支付完成时间',
  `total_fee` decimal(10,2) DEFAULT '0.01' COMMENT '支付金额（分）',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水号',
  `trade_state` char(20) DEFAULT NULL COMMENT '交易状态',
  `pay_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付类型（1：微信 2：支付宝）',
  `attr` text COMMENT '其他属性',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付日志表';

/*Data for the table `t_pay_log` */

insert  into `t_pay_log`(`id`,`order_no`,`pay_time`,`total_fee`,`transaction_id`,`trade_state`,`pay_type`,`attr`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1504368392000942082','20220317160510274','2022-03-17 16:05:49','0.01','4200001315202203171191091823','SUCCESS',1,'{\"transaction_id\":\"4200001315202203171191091823\",\"nonce_str\":\"YbGnF3dbdaWN4A9J\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuPSwXTa4xnpo5OLTgjQq-Jo\",\"sign\":\"3AB1CA563532EDDA8C8A04A37FFBDD1C\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"1\",\"out_trade_no\":\"20220317160510274\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20220317160546\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2022-03-17 16:05:49','2022-03-17 16:05:49'),('1504369254735720449','20220317160832218','2022-03-17 16:09:14','0.01','4200001312202203171878394593','SUCCESS',1,'{\"transaction_id\":\"4200001312202203171878394593\",\"nonce_str\":\"hmp6YLQrzOmHcWjs\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuPSwXTa4xnpo5OLTgjQq-Jo\",\"sign\":\"D2CC07BD5F83AA49C37B47B878E04E38\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"1\",\"out_trade_no\":\"20220317160832218\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20220317160913\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2022-03-17 16:09:14','2022-03-17 16:09:14'),('1515613489841516545','20220417164930618','2022-04-17 16:49:49','0.01','4200001459202204174508983595','SUCCESS',1,'{\"transaction_id\":\"4200001459202204174508983595\",\"nonce_str\":\"y9RVCkxB84tpsFlZ\",\"trade_state\":\"SUCCESS\",\"bank_type\":\"OTHERS\",\"openid\":\"oHwsHuPSwXTa4xnpo5OLTgjQq-Jo\",\"sign\":\"2CB5E040F28E41E1C80E89B86AA7AA6A\",\"return_msg\":\"OK\",\"fee_type\":\"CNY\",\"mch_id\":\"1558950191\",\"cash_fee\":\"1\",\"out_trade_no\":\"20220417164930618\",\"cash_fee_type\":\"CNY\",\"appid\":\"wx74862e0dfcf69954\",\"total_fee\":\"1\",\"trade_state_desc\":\"支付成功\",\"trade_type\":\"NATIVE\",\"result_code\":\"SUCCESS\",\"attach\":\"\",\"time_end\":\"20220417164947\",\"is_subscribe\":\"N\",\"return_code\":\"SUCCESS\"}',0,'2022-04-17 16:49:49','2022-04-17 16:49:49');

/*Table structure for table `ucenter_member` */

DROP TABLE IF EXISTS `ucenter_member`;

CREATE TABLE `ucenter_member` (
  `id` char(19) NOT NULL COMMENT '会员id',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(2) unsigned DEFAULT NULL COMMENT '性别 1 女，2 男',
  `age` tinyint(3) unsigned DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sign` varchar(100) DEFAULT NULL COMMENT '用户签名',
  `is_disabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否禁用 1（true）已禁用，  0（false）未禁用',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

/*Data for the table `ucenter_member` */

insert  into `ucenter_member`(`id`,`openid`,`mobile`,`password`,`nickname`,`sex`,`age`,`avatar`,`sign`,`is_disabled`,`is_deleted`,`gmt_create`,`gmt_modified`) values ('1086387099701100545',NULL,'13520191382','96e79218965eb72c92a549dd5a330112','用户LcAYbxLNdN',2,24,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099776598018',NULL,'13520191383','96e79218965eb72c92a549dd5a330112','用户dZdjcgltnk',2,25,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1086387099852095490',NULL,'13520191384','96e79218965eb72c92a549dd5a330112','用户wNHGHlxUwX',2,23,'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132',NULL,0,0,'2019-01-19 06:17:23','2019-01-19 06:17:23'),('1501865429156208642',NULL,'13525414492','e10adc3949ba59abbe56e057f20f883e','傻狗',NULL,NULL,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/FatDog.jpg',NULL,0,0,'2022-03-10 18:19:56','2022-03-10 18:19:56'),('1502234599249129474','o3_SC59qPDYhYL29MOWLBEuSzA6Q','',NULL,'Hello，world',0,NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/FooM9yDjUdjaYAqjV9SRm3rAuzs0KkzlvPRDxnBnz4rXK21bxIp8gy1xQ6N3X3wjG4OBItXuKRNvk3JEnFicCag/132',NULL,0,0,'2022-03-11 18:46:53','2022-03-11 18:46:53'),('1503574953088962562',NULL,'18625983574','e10adc3949ba59abbe56e057f20f883e','大李子',NULL,NULL,'https://guli-photos.oss-cn-hangzhou.aliyuncs.com/FatDog.jpg',NULL,0,0,'2022-03-15 11:32:58','2022-03-15 11:32:58');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
