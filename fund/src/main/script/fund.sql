
# fund 数据库
CREATE DATABASE IF NOT EXISTS fund DEFAULT CHARACTER SET utf8;

USE fund;


# 基金基本信息
CREATE TABLE fund_basic_info(
  `id` BIGINT AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL COMMENT '基金代码',
  `found_date` DATE COMMENT '成立日期',
  `short_name` VARCHAR(50) COMMENT '基金简称',
  `full_name` VARCHAR(100) COMMENT '基金全称',
  `mmf` BIT COMMENT '是否货币基金',
  PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `code_idx` ON fund_basic_info(`code`);


# 非货币基金净值
CREATE TABLE IF NOT EXISTS nav_nmf(
  `id` BIGINT AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL COMMENT '基金代码',
  `date` DATE NOT NULL COMMENT '净值日期',
  `source` VARCHAR(50) NOT NULL COMMENT '数据来源',
  `unit_nav` DECIMAL(10,4) COMMENT '单位净值',
  `accum_nav` DECIMAL(10,4) COMMENT '累计净值',
  PRIMARY KEY (`id`)
) COMMENT '非货币基金净值';

# 创建唯一索引
CREATE UNIQUE INDEX `code_date_source_idx` ON nav_nmf (`code`,`date`,`source`);

# 货币基金净值
CREATE TABLE IF NOT EXISTS nav_mmf(
  `id` BIGINT AUTO_INCREMENT,
  `code` VARCHAR(10) NOT NULL COMMENT '基金代码',
  `date` DATE NOT NULL COMMENT '净值日期',
  `source` VARCHAR(50) NOT NULL COMMENT '数据来源',
  `yield_7days` DECIMAL(10,4) COMMENT '7日年化收益率',
  `yield_10k` DECIMAL(10,5) COMMENT '每万分收益',
  PRIMARY KEY (`id`)
) COMMENT '货币基金净值';

# 创建唯一索引
CREATE UNIQUE INDEX `code_date_source_idx` on nav_mmf (`code`,`date`,`source`);



# 定时任务
CREATE TABLE IF NOT EXISTS cron_task(
  `id` BIGINT AUTO_INCREMENT,
  `name` VARCHAR(30) COMMENT '任务名称',
  `bean_name` VARCHAR(100) COMMENT 'Bean的名称',
  `method_name` VARCHAR(100) COMMENT '调用的方法名称',
  `cron` VARCHAR(100) COMMENT 'cron表达式',
  `comment` VARCHAR(200) COMMENT '注释',
  PRIMARY KEY (`id`)
);

INSERT INTO cron_task(name,bean_name,method_name,cron) VALUES ('网易财经净值','navTask','crawlNavNTES','0 0 18 * * *');