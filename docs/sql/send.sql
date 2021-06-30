-- --------------------------------------------------------
-- 主机:                           581bef2c3550a.gz.cdb.myqcloud.com
-- 服务器版本:                        5.7.18-txsql-log - 20200701
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 s_send.auto_reply_sms 结构
CREATE TABLE IF NOT EXISTS `auto_reply_sms` (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Enterprise_No` varchar(128) DEFAULT NULL COMMENT '企业Id',
  `Enterprise_User_Id` int(11) DEFAULT NULL,
  `Key_Word` varchar(128) DEFAULT NULL COMMENT '上行关键字',
  `Content` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '短信内容',
  `Create_User` varchar(128) DEFAULT NULL COMMENT '创建人',
  `Create_User_Id` int(11) DEFAULT NULL COMMENT '创建人唯一标识',
  `Create_Date` datetime DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='根据上行短信自动回复';

-- 数据导出被取消选择。


-- 导出  表 s_send.inbox 结构
CREATE TABLE IF NOT EXISTS `inbox` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `SP_Number` varchar(128) DEFAULT NULL COMMENT 'SP号码',
  `Sub_Code` varchar(128) DEFAULT NULL COMMENT '扩展号',
  `Group_Code` varchar(128) DEFAULT NULL,
  `Input_Sub_Code` varchar(128) DEFAULT NULL,
  `Input_Msg_No` varchar(128) DEFAULT NULL,
  `Input_Content` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `Input_Create_Date` datetime DEFAULT NULL,
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '上行手机号',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家编号',
  `Content` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '上行内容',
  `Channel_No` varchar(128) DEFAULT NULL COMMENT '通道编号',
  `Agent_No` varchar(128) DEFAULT NULL COMMENT '代理商',
  `Enterprise_No` varchar(128) DEFAULT NULL COMMENT '代理商编号',
  `Enterprise_User_Id` int(11) DEFAULT NULL,
  `Protocol_Type_Code` varchar(128) DEFAULT NULL,
  `Pull_Total` int(11) DEFAULT '0' COMMENT '拉取次数',
  `Pull_Date` datetime DEFAULT NULL COMMENT '拉取时间',
  `Notify_Total` int(11) DEFAULT '0' COMMENT '通知次数',
  `Notify_Date` datetime DEFAULT NULL COMMENT '通知时间',
  `Notify_Status_Code` varchar(128) DEFAULT NULL COMMENT '通知状态',
  `Sender_Local_IP` varchar(128) DEFAULT NULL COMMENT '发送器IP',
  `Charset` varchar(11) DEFAULT NULL COMMENT '编码',
  `Create_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Create_Date` (`Create_Date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='上行短信';

-- 数据导出被取消选择。


-- 导出  表 s_send.input 结构
CREATE TABLE IF NOT EXISTS `input` (
  `Id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `UUID` varchar(128) DEFAULT NULL,
  `Msg_Batch_No` varchar(50) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(2000) DEFAULT NULL COMMENT '下游的msgId',
  `Enterprise_No` varchar(128) NOT NULL COMMENT '企业Id',
  `Agent_No` varchar(128) DEFAULT NULL,
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Enterprise_User_Id` int(11) NOT NULL COMMENT '用户标识',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Is_LMS` bit(1) NOT NULL COMMENT '是否长短信',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Message_Type_Code` varchar(128) DEFAULT NULL COMMENT '消息类型',
  `Content` varchar(2000) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Phone_Nos` varchar(8000) NOT NULL COMMENT '手机号码，多个用|分割  最后一位必须是|',
  `Phone_Nos_Count` int(11) DEFAULT NULL COMMENT '手机号码个数',
  `Protocol_Type_Code` varchar(128) NOT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家代码',
  `Source_IP` varchar(56) NOT NULL COMMENT '提交短信的IP',
  `Priority_Number` int(11) NOT NULL COMMENT '短信优先级',
  `Audit_Status_Code` varchar(128) DEFAULT NULL COMMENT '审核状态代码',
  `Audit_Admin_User_Id` int(11) DEFAULT NULL COMMENT '审核人',
  `Audit_Date` datetime DEFAULT NULL COMMENT '审核日期',
  `Sub_Code` varchar(128) DEFAULT NULL COMMENT '短号',
  `Send_Time` datetime DEFAULT NULL COMMENT '定时发送',
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Assist_Audit_Key` varchar(64) DEFAULT NULL COMMENT '辅助审核短信字段:md5',
  `Description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `Remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `Create_Date` datetime DEFAULT NULL COMMENT '创建时间',
  `Input_Date` datetime DEFAULT NULL COMMENT '用户提交短信时间',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `uuid` (`UUID`) USING BTREE,
  KEY `Assist_Audit_Key` (`Assist_Audit_Key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户提交的原始数据';

-- 数据导出被取消选择。


-- 导出  表 s_send.input_log 结构
CREATE TABLE IF NOT EXISTS `input_log` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `Msg_Batch_No` varchar(50) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(2000) DEFAULT NULL COMMENT '下游的msgId',
  `Enterprise_No` varchar(128) DEFAULT NULL COMMENT '企业Id',
  `Agent_No` varchar(128) DEFAULT NULL COMMENT '代理商Id',
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Enterprise_User_Id` int(11) DEFAULT NULL COMMENT '用户标识',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Is_LMS` bit(1) DEFAULT NULL COMMENT '是否长短信',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Is_Show` bit(1) DEFAULT NULL COMMENT '是否显示',
  `Message_Type_Code` varchar(128) DEFAULT NULL COMMENT '消息类型',
  `Content` varchar(2000) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Phone_Nos` varchar(8000) DEFAULT NULL COMMENT '手机号码，多个用|分割  最后一位必须是|',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT '接口类型  Web,Cmpp,HttpXml,HttpJson',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家代码',
  `Source_IP` varchar(56) DEFAULT NULL COMMENT '提交短信的IP',
  `Priority_Number` int(11) DEFAULT NULL COMMENT '短信优先级',
  `Audit_Status_Code` varchar(128) DEFAULT NULL,
  `Audit_Admin_User_Id` int(11) DEFAULT NULL COMMENT '审核人',
  `Audit_Date` datetime DEFAULT NULL COMMENT '审核日期',
  `Sub_Code` varchar(128) DEFAULT NULL,
  `Input_Sub_Code` varchar(128) DEFAULT NULL,
  `Input_Date` datetime DEFAULT NULL COMMENT '用户提交短信时间',
  `Send_Time` datetime DEFAULT NULL,
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Content_Length` int(11) DEFAULT NULL COMMENT '短信字数',
  `Phone_Nos_Count` int(11) DEFAULT NULL COMMENT '手机号码个数',
  `Fee_Count` int(11) DEFAULT NULL COMMENT '计费条数',
  `Faild_Count` int(11) DEFAULT NULL COMMENT '分拣失败条数',
  `Sale_Amount` decimal(20,4) DEFAULT NULL COMMENT '消费金额（元）',
  `Description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `Remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `Create_Date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Enterprise_Msg_No` (`Msg_Batch_No`) USING BTREE,
  KEY `Create_Date` (`Create_Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户提交的原始数据日志表';

-- 数据导出被取消选择。


-- 导出  表 s_send.report 结构
CREATE TABLE IF NOT EXISTS `report` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Msg_Batch_No` varchar(128) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(128) DEFAULT NULL COMMENT '下游的msgId',
  `Channel_Msg_Id` varchar(128) DEFAULT NULL COMMENT '通道msgid',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Product_Channel_Id` int(11) DEFAULT NULL COMMENT '产品通道id',
  `Group_Code` varchar(128) DEFAULT NULL COMMENT '分组编号',
  `Priority_Level` int(11) DEFAULT NULL COMMENT '优先级',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Country_Code` varchar(128) DEFAULT NULL,
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '接受短信手机号',
  `Channel_No` varchar(128) DEFAULT NULL,
  `Sub_Code` varchar(128) DEFAULT NULL,
  `Input_Sub_Code` varchar(128) DEFAULT NULL,
  `Enterprise_No` varchar(128) DEFAULT NULL,
  `Agent_No` varchar(128) DEFAULT NULL,
  `Enterprise_User_Id` int(11) DEFAULT NULL COMMENT '用户标识',
  `Enterprise_User_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '企业用户单价',
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Operator` varchar(50) DEFAULT NULL COMMENT '运营商：cmp：移动    sgi：联通    smg：电信      未知：Unkown',
  `Country_Number` varchar(128) DEFAULT NULL COMMENT '国家编号',
  `MCC` varchar(50) DEFAULT NULL COMMENT '移动国家码',
  `MNC` varchar(50) DEFAULT NULL COMMENT '移动网络码',
  `Area_Name` varchar(255) DEFAULT NULL COMMENT '地域',
  `Province_Code` varchar(21) DEFAULT NULL COMMENT '省份代码，0311,010省会的电话区号',
  `Message_Type_Code` varchar(128) DEFAULT NULL COMMENT '消息类型',
  `Content` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Is_Show` bit(1) DEFAULT NULL COMMENT '是否显示',
  `Is_Deduct` bit(1) DEFAULT NULL COMMENT '',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Signature` varchar(64) DEFAULT NULL COMMENT '签名',
  `Content_Length` int(11) DEFAULT NULL COMMENT '短信字数',
  `Sequence` int(11) DEFAULT NULL COMMENT '长短信的序列',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Status_Code` varchar(128) DEFAULT NULL COMMENT '状态，要自己定义:成功 Success，失败 Faild',
  `Sender_Local_IP` varchar(128) DEFAULT NULL COMMENT '发送器IP',
  `Sender_Local_Port` varchar(128) DEFAULT NULL COMMENT '发送器本地端口',
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Native_Status` varchar(128) DEFAULT NULL COMMENT '原生状态',
  `Status_Date` datetime DEFAULT NULL COMMENT '状态时间',
  `Submit_Status_Code` varchar(128) DEFAULT NULL COMMENT '提交状态:成功 Success，失败 Faild',
  `Submit_Description` varchar(128) DEFAULT NULL COMMENT '提交描述',
  `Submit_Date` datetime(3) NOT NULL COMMENT '网关提交时间',
  `Submit_Response_Date` datetime DEFAULT NULL COMMENT '提交响应时间',
  `Input_Log_Date` datetime(3) DEFAULT NULL COMMENT '在InputLog表中的创建时间',
  `Description` varchar(128) DEFAULT NULL,
  `Remark` varchar(128) DEFAULT NULL,
  `Create_Date` datetime NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE,
  KEY `Channel_Sub_Msg_No` (`Channel_Msg_Id`) USING BTREE,
  KEY `Enterprise_Msg_No` (`Msg_Batch_No`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='上行短信';

-- 数据导出被取消选择。


-- 导出  表 s_send.report_extra 结构
CREATE TABLE IF NOT EXISTS `report_extra` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Channel_Sub_Msg_No` varchar(128) DEFAULT NULL COMMENT '子消息id',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '接受短信手机号',
  `Channel_No` varchar(128) DEFAULT NULL,
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Native_Status` varchar(128) DEFAULT NULL COMMENT '原生状态',
  `Submit_Time` varchar(64) DEFAULT NULL COMMENT '提交时间',
  `Done_Time` varchar(64) DEFAULT NULL,
  `Description` varchar(128) DEFAULT NULL,
  `Remark` varchar(128) DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Channel_Sub_Msg_No` (`Channel_Sub_Msg_No`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='状态报告';

-- 数据导出被取消选择。


-- 导出  表 s_send.report_notify 结构
CREATE TABLE IF NOT EXISTS `report_notify` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Msg_Batch_No` varchar(128) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(128) DEFAULT NULL COMMENT '下游的msgId',
  `Channel_Msg_Id` varchar(128) DEFAULT NULL COMMENT '通道msgid',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家编码：中国86',
  `Country_Number` varchar(128) DEFAULT NULL COMMENT '国家编号：中国cn',
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '接受短信手机号',
  `Channel_No` varchar(128) DEFAULT NULL COMMENT '通道编号',
  `Sub_Code` varchar(128) DEFAULT NULL,
  `Enterprise_No` varchar(128) DEFAULT NULL COMMENT '企业编号',
  `Agent_No` varchar(128) DEFAULT NULL COMMENT '代理商编号',
  `Enterprise_User_Id` int(11) DEFAULT NULL COMMENT '企业用户ID',
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id（商务）',
  `Operator` varchar(50) DEFAULT NULL COMMENT '运营商：cmpp：移动sgip：联通smgp：电信 未知：Unkonw',
  `Area_Name` varchar(255) DEFAULT NULL COMMENT '地域',
  `Province_Code` varchar(21) DEFAULT NULL COMMENT '省份代码，0311,010省会的电话区号',
  `Message_Type_Code` varchar(128) DEFAULT NULL COMMENT '消息类型',
  `Content` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Content_Length` int(11) DEFAULT NULL,
  `Sequence` int(11) DEFAULT NULL COMMENT '长短信的序列',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Status_Code` varchar(128) DEFAULT NULL COMMENT '状态，成功 Success，失败 Faild',
  `Native_Status` varchar(128) DEFAULT NULL COMMENT '原生状态',
  `Status_Date` datetime DEFAULT NULL,
  `Notify_Status_Code` varchar(128) DEFAULT NULL COMMENT '通知状态:成功 Success，失败 Faild',
  `Submit_Status_Code` varchar(128) DEFAULT NULL,
  `Submit_Description` varchar(128) DEFAULT NULL,
  `Submit_Date` datetime(3) NOT NULL DEFAULT '0000-00-00 00:00:00.000' COMMENT '提交时间',
  `Notify_Response_Status` varchar(128) DEFAULT NULL COMMENT '通知响应状态',
  `Notify_Submit_Date` datetime(3) DEFAULT NULL COMMENT '通知提交时间',
  `Notify_Response_Date` datetime(3) DEFAULT NULL COMMENT '通知返回时间',
  `Input_Log_Date` datetime DEFAULT NULL COMMENT 'inputLog创建时间',
  `Description` varchar(128) DEFAULT NULL,
  `Remark` varchar(128) DEFAULT NULL,
  `Create_Date` datetime(3) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Channel_Sub_Msg_No` (`Channel_Msg_Id`) USING BTREE,
  KEY `Enterprise_Msg_No` (`Msg_Batch_No`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE,
  KEY `Submit_Date` (`Submit_Date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 数据导出被取消选择。


-- 导出  表 s_send.report_notify_await 结构
CREATE TABLE IF NOT EXISTS `report_notify_await` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Msg_Batch_No` varchar(128) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(128) DEFAULT NULL COMMENT '下游的msgId',
  `Channel_Msg_Id` varchar(128) DEFAULT NULL COMMENT '通道msgid',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Product_Channel_Id` int(11) DEFAULT NULL COMMENT '产品通道id',
  `Group_Code` varchar(128) DEFAULT NULL COMMENT '分组编号',
  `Priority_Level` int(11) DEFAULT NULL COMMENT '优先级',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Country_Code` varchar(128) DEFAULT NULL,
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '接受短信手机号',
  `Channel_No` varchar(128) DEFAULT NULL,
  `Sub_Code` varchar(128) DEFAULT NULL,
  `Input_Sub_Code` varchar(128) DEFAULT NULL,
  `Enterprise_No` varchar(128) DEFAULT NULL,
  `Agent_No` varchar(128) DEFAULT NULL,
  `Enterprise_User_Id` int(11) DEFAULT NULL COMMENT '用户标识',
  `Enterprise_User_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '企业用户单价',
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Operator` varchar(50) DEFAULT NULL COMMENT '运营商：cmp：移动    sgi：联通    smg：电信      未知：Unkown',
  `Country_Number` varchar(128) DEFAULT NULL COMMENT '国家编号',
  `MCC` varchar(50) DEFAULT NULL COMMENT '移动国家码',
  `MNC` varchar(50) DEFAULT NULL COMMENT '移动网络码',
  `Area_Name` varchar(255) DEFAULT NULL COMMENT '地域',
  `Province_Code` varchar(21) DEFAULT NULL COMMENT '省份代码，0311,010省会的电话区号',
  `Message_Type_Code` varchar(128) DEFAULT NULL COMMENT '消息类型',
  `Content` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Is_Show` bit(1) DEFAULT NULL COMMENT '是否显示',
  `Is_Deduct` bit(1) DEFAULT NULL COMMENT '',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Signature` varchar(64) DEFAULT NULL COMMENT '签名',
  `Content_Length` int(11) DEFAULT NULL COMMENT '短信字数',
  `Sequence` int(11) DEFAULT NULL COMMENT '长短信的序列',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Status_Code` varchar(128) DEFAULT NULL COMMENT '状态，要自己定义:成功 Success，失败 Faild',
  `Sender_Local_IP` varchar(128) DEFAULT NULL COMMENT '发送器IP',
  `Sender_Local_Port` varchar(128) DEFAULT NULL COMMENT '发送器本地端口',
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Native_Status` varchar(128) DEFAULT NULL COMMENT '原生状态',
  `Status_Date` datetime DEFAULT NULL COMMENT '状态时间',
  `Submit_Status_Code` varchar(128) DEFAULT NULL COMMENT '提交状态:成功 Success，失败 Faild',
  `Submit_Description` varchar(128) DEFAULT NULL COMMENT '提交描述',
  `Submit_Date` datetime(3) NOT NULL COMMENT '网关提交时间',
  `Submit_Response_Date` datetime DEFAULT NULL COMMENT '提交响应时间',
  `Input_Log_Date` datetime(3) DEFAULT NULL COMMENT '在InputLog表中的创建时间',
  `Description` varchar(128) DEFAULT NULL,
  `Remark` varchar(128) DEFAULT NULL,
  `Create_Date` datetime NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE,
  KEY `Channel_Sub_Msg_No` (`Channel_Msg_Id`) USING BTREE,
  KEY `Enterprise_Msg_No` (`Msg_Batch_No`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='待推送状态报告';

-- 数据导出被取消选择。


-- 导出  表 s_send.submit 结构
CREATE TABLE IF NOT EXISTS `submit` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `Channel_No` varchar(64) DEFAULT NULL COMMENT '通道标识',
  `Enterprise_No` varchar(64) DEFAULT NULL COMMENT '企业唯一标志',
  `Agent_No` varchar(128) DEFAULT NULL,
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Enterprise_User_Id` int(11) NOT NULL COMMENT '用户标识',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Product_Channel_Id` int(11) DEFAULT NULL COMMENT '产品通道id',
  `Priority_Level` int(11) DEFAULT NULL COMMENT '优先级',
  `Group_Code` varchar(128) DEFAULT NULL COMMENT '分组编码',
  `Input_Sub_Code` varchar(32) DEFAULT NULL,
  `Msg_Batch_No` varchar(128) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(128) DEFAULT NULL COMMENT '下游的msgId',
  `Channel_Msg_Id` varchar(128) DEFAULT NULL COMMENT '通道msgid',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Sequence` int(11) DEFAULT NULL COMMENT '长短信的序列',
  `Sub_Code` varchar(32) DEFAULT NULL,
  `Is_LMS` bit(1) NOT NULL COMMENT '是否长短信',
  `Is_Deduct` bit(1) DEFAULT NULL COMMENT '',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Is_Show` bit(1) DEFAULT NULL COMMENT '是否显示',
  `Message_Type_Code` varchar(128) NOT NULL COMMENT '消息类型',
  `Operator` varchar(50) DEFAULT NULL COMMENT '运营商：cmp：移动    sgi：联通    smg：电信      未知：Unkown',
  `Content` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Signature` varchar(64) DEFAULT NULL COMMENT '签名',
  `Content_Length` int(11) DEFAULT NULL COMMENT '短信字数',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家代码',
  `Country_Number` varchar(128) DEFAULT NULL COMMENT '国家编号',
  `MCC` varchar(128) DEFAULT NULL COMMENT '移动国家码',
  `MNC` varchar(128) DEFAULT NULL COMMENT '移动网络码',
  `Province_Code` varchar(21) DEFAULT NULL COMMENT '省份代码，0311,010省会的电话区号',
  `Area_Name` varchar(255) DEFAULT NULL COMMENT '地域',
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '手机号码',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Submit_Status_Code` varchar(128) DEFAULT NULL COMMENT '提交状态:成功 Success，失败 Faild',
  `Submit_Description` varchar(128) DEFAULT NULL COMMENT '提交描述',
  `Submit_Date` datetime(3) DEFAULT NULL COMMENT '网关提交时间',
  `Submit_Response_Date` datetime(3) DEFAULT NULL COMMENT '网关响应时间',
  `Sender_Local_IP` varchar(128) DEFAULT NULL COMMENT '发送器IP',
  `Sender_Local_Port` varchar(128) DEFAULT NULL COMMENT '发送器本地端口',
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Enterprise_User_Taxes` decimal(12,6) DEFAULT NULL COMMENT '企业税点',
  `Channel_Taxes` decimal(12,6) DEFAULT NULL COMMENT '通道的税点',
  `Channel_Unit_Price` decimal(12,6) DEFAULT '0.000000' COMMENT '通道单价',
  `Agent_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '代理商单价',
  `Agent_Taxes` decimal(12,6) DEFAULT NULL,
  `Enterprise_User_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '企业用户单价',
  `Agent_Profits` decimal(12,6) DEFAULT NULL COMMENT '代理商利润',
  `Profits` decimal(12,6) DEFAULT '0.000000' COMMENT '利润',
  `Input_Log_Date` datetime DEFAULT NULL COMMENT '再inputLog表中的创建时间',
  `Description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `Remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `Create_Date` datetime(3) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Index_Channel_Id` (`Channel_No`) USING BTREE,
  KEY `Index_Enterprise_Id` (`Enterprise_No`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通道已经发送短信表';

-- 数据导出被取消选择。


-- 导出  表 s_send.submit_await 结构
CREATE TABLE IF NOT EXISTS `submit_await` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `Channel_No` varchar(64) DEFAULT NULL COMMENT '通道标识',
  `Enterprise_No` varchar(64) DEFAULT NULL COMMENT '企业唯一标志',
  `Agent_No` varchar(128) DEFAULT NULL,
  `Business_User_Id` int(11) DEFAULT NULL COMMENT '业务员Id',
  `Enterprise_User_Id` int(11) NOT NULL COMMENT '用户标识',
  `Product_No` varchar(128) DEFAULT NULL COMMENT '产品编号',
  `Product_Channel_Id` int(11) DEFAULT NULL COMMENT '产品通道id',
  `Priority_Level` int(11) DEFAULT NULL COMMENT '优先级',
  `Group_Code` varchar(128) DEFAULT NULL COMMENT '分组编码',
  `Input_Sub_Code` varchar(32) DEFAULT NULL,
  `Msg_Batch_No` varchar(128) DEFAULT NULL COMMENT '唯一标识',
  `Enterprise_Msg_Id` varchar(128) DEFAULT NULL COMMENT '下游的msgId',
  `Channel_Msg_Id` varchar(128) DEFAULT NULL COMMENT '通道msgid',
  `SP_Number` varchar(128) DEFAULT NULL COMMENT '通道的SP号码',
  `Sequence` int(11) DEFAULT NULL COMMENT '长短信的序列',
  `Sub_Code` varchar(32) DEFAULT NULL,
  `Is_LMS` bit(1) NOT NULL COMMENT '是否长短信',
  `Is_Deduct` bit(1) DEFAULT NULL COMMENT '',
  `Data_Status_Code` varchar(11) DEFAULT '0' COMMENT '数据状态：0：正常数据 1：重发的数据 2：第一次发送失败的数据',
  `Is_Show` bit(1) DEFAULT NULL COMMENT '是否显示',
  `Message_Type_Code` varchar(128) NOT NULL COMMENT '消息类型',
  `Operator` varchar(50) DEFAULT NULL COMMENT '运营商：cmp：移动    sgi：联通    smg：电信      未知：Unkown',
  `Content` varchar(512) CHARACTER SET utf8mb4 NOT NULL COMMENT '短消息',
  `Charset` varchar(32) DEFAULT NULL COMMENT '编码',
  `Signature` varchar(64) DEFAULT NULL COMMENT '签名',
  `Content_Length` int(11) DEFAULT NULL COMMENT '短信字数',
  `Country_Code` varchar(128) DEFAULT NULL COMMENT '国家代码',
  `Country_Number` varchar(128) DEFAULT NULL COMMENT '国家编号',
  `MCC` varchar(128) DEFAULT NULL COMMENT '移动国家码',
  `MNC` varchar(128) DEFAULT NULL COMMENT '移动网络码',
  `Province_Code` varchar(21) DEFAULT NULL COMMENT '省份代码，0311,010省会的电话区号',
  `Area_Name` varchar(255) DEFAULT NULL COMMENT '地域',
  `Phone_No` varchar(128) DEFAULT NULL COMMENT '手机号码',
  `Protocol_Type_Code` varchar(128) DEFAULT NULL COMMENT 'Web,Cmpp,HttpXml,HttpJson',
  `Submit_Status_Code` varchar(128) DEFAULT NULL COMMENT '提交状态:成功 Success，失败 Faild',
  `Submit_Description` varchar(128) DEFAULT NULL COMMENT '提交描述',
  `Submit_Date` datetime(3) DEFAULT NULL COMMENT '网关提交时间',
  `Submit_Response_Date` datetime(3) DEFAULT NULL COMMENT '网关响应时间',
  `Sender_Local_IP` varchar(128) DEFAULT NULL COMMENT '发送器IP',
  `Sender_Local_Port` varchar(128) DEFAULT NULL COMMENT '发送器本地端口',
  `Gate_Ip` varchar(128) DEFAULT NULL COMMENT '网关IP',
  `Enterprise_User_Taxes` decimal(12,6) DEFAULT NULL COMMENT '企业税点',
  `Channel_Taxes` decimal(12,6) DEFAULT NULL COMMENT '通道的税点',
  `Channel_Unit_Price` decimal(12,6) DEFAULT '0.000000' COMMENT '通道单价',
  `Agent_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '代理商单价',
  `Agent_Taxes` decimal(12,6) DEFAULT NULL,
  `Enterprise_User_Unit_Price` decimal(12,6) DEFAULT NULL COMMENT '企业用户单价',
  `Agent_Profits` decimal(12,6) DEFAULT NULL COMMENT '代理商利润',
  `Profits` decimal(12,6) DEFAULT '0.000000' COMMENT '利润',
  `Input_Log_Date` datetime DEFAULT NULL COMMENT '再inputLog表中的创建时间',
  `Description` varchar(2048) DEFAULT NULL COMMENT '描述',
  `Remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `Create_Date` datetime(3) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Index_Channel_Id` (`Channel_No`) USING BTREE,
  KEY `Index_Enterprise_Id` (`Enterprise_No`) USING BTREE,
  KEY `Phone_No` (`Phone_No`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通道等待发送短信表';

-- 数据导出被取消选择。


-- 导出  表 s_send.submit_expired 结构
CREATE TABLE IF NOT EXISTS `submit_expired` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Submit_Id` bigint(20) NOT NULL COMMENT '关联的submit的Id',
  `Submit_Key` varchar(128) NOT NULL COMMENT '提交短信的key值，通道编号+连接编号+sequenceNuber',
  `Create_Date` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`) USING BTREE,
  KEY `Submit_Key` (`Submit_Key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信的提交超时缓存';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
