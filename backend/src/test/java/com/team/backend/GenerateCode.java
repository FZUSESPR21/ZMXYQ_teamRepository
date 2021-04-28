package com.team.backend;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.ArrayList;

/**
 * @author : yangyu
 * @ClassName : com.team.backend.GenerateCode
 * @Description : 代码生成器
 * @date : 2021-04-22 23:46:56 Copyright  2021 user. All rights reserved.
 */

public class GenerateCode {

  public static void main(String[] args) {

    // 需要构建一个 代码自动生成器对象
    AutoGenerator mpg = new AutoGenerator();

    // 配置策略
    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/src/main/java");
    gc.setAuthor("yangyu");
    gc.setOpen(false);
    gc.setFileOverride(false); // 是否覆盖
    gc.setServiceName("%sService"); // 去Service的I前缀
    gc.setIdType(IdType.AUTO);
    gc.setDateType(DateType.ONLY_DATE);
    gc.setSwagger2(true);
    mpg.setGlobalConfig(gc);

    // 设置数据源
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/teamproject?" +
        "useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("cnz123456");
    dsc.setDbType(DbType.MYSQL);
    mpg.setDataSource(dsc);

    // 包的配置
    PackageConfig pc = new PackageConfig();
//        pc.setModuleName("backend");// 模块名
    pc.setParent("com.team.backend");
    pc.setEntity("model");
    pc.setMapper("mapper");
    pc.setService("service");
    pc.setController("controller");
    mpg.setPackageInfo(pc);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setInclude("admin", "black_list", "notification", "party", "party_comment",
        "party_participants", "party_type", "post", "post_comment", "post_eye_on", "post_like",
        "post_reward", "post_type", "private_chat", "report", "tree_hole", "user"); // 设置要映射的表名
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true); // 自动lombok；
    strategy.setLogicDeleteFieldName("deleted");// 逻辑删除

    // 自动填充配置
    TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
    TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
    ArrayList<TableFill> tableFills = new ArrayList<>();
    tableFills.add(gmtCreate);
    tableFills.add(gmtModified);
    strategy.setTableFillList(tableFills);

//        // 乐观锁
//        strategy.setVersionFieldName("version");

    strategy.setRestControllerStyle(true);
    strategy.setControllerMappingHyphenStyle(true);
    mpg.setStrategy(strategy);
    mpg.execute(); //执行
  }
}

