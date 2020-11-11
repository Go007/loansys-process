package com.hong.loansys.process.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wanghong
 * @date 2020/04/06 18:06
 **/
public class MpGenerator {
    public static void main(String[] args) {
        // 1. 数据源配置
        String dbUrl = "jdbc:mysql://172.16.36.21:3306/loansys_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&maxReconnects=10&rewriteBatchedStatements=true&nullCatalogMeansCurrent=true";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("P@ssw0rd2018")
                .setDriverName("com.mysql.cj.jdbc.Driver");

        // 2. 全局配置
        //这里因为我是多模块项目，所以需要加上子模块的名称，以便直接生成到该目录下，如果是单模块项目，可以将后面的去掉
        //String projectPath = System.getProperty("user.dir") + "/viboot-mybatis-plus";
        Properties props = System.getProperties(); // 系统属性
        String projectPath = "D://Generator";
        GlobalConfig config = new GlobalConfig();
        //设置作者，输出路径，是否重写等属性
        config.setActiveRecord(false)   // 开启 activeRecord 模式
                .setEnableCache(false)  // XML 二级缓存
                .setBaseResultMap(true) // XML ResultMap
                .setBaseColumnList(true) // XML BaseColumnList
                .setAuthor(props.getProperty("user.name"))
                .setOutputDir(projectPath + "src/main/java")
                .setFileOverride(true)
                .setServiceName("%sService");
               // .setSwagger2(true);

        // 3. 策略配置
        // 设置需要生成的表名
        // 这里如果是MySql表,且没有设置不区分大小写,则表名在数据库中是小写就是小写,否则会报找不到表的错误
        String[] tableNames = new String[]{"act_ru_job"};
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setRestControllerStyle(true)
                //这里结合了Lombok，所以设置为true，如果没有集成Lombok，可以设置为false
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setInclude(tableNames); // 字段名生成策略 生成全部表的话把这段禁用

        // 4. 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" +
                        tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 5. 执行自动生成
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
               // .setTemplateEngine(new FreemarkerTemplateEngine())
                .setTemplateEngine(new VelocityTemplateEngine())
                .setCfg(cfg)
                //这里进行包名的设置
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.hong.loansys.process")
                                .setController("controller")
                                .setEntity("domain")
                                .setMapper("mapper")
                                .setService("service")
                                .setServiceImpl("service.impl")

                ).execute();
    }
}
