package com.hello.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Component
public class ActiveRecordConfig {

	
	// 由于spring中已经注入了DruidDataSource这里直接拿
    @Autowired private ComboPooledDataSource comboPooledDataSource;
     
    @Bean(initMethod="start", destroyMethod="stop")
    public ActiveRecordPlugin init() {
        ActiveRecordPlugin arp = new ActiveRecordPlugin(comboPooledDataSource);
        arp.addMapping("blog", Blog.class);
        return arp;
    }
}
