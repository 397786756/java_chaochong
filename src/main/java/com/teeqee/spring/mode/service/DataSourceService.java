package com.teeqee.spring.mode.service;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.Session;
import com.teeqee.spring.mode.context.DataSourceContextAware;
import com.teeqee.spring.mode.init.InitDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class DataSourceService {

    @Resource
    private  DataSourceContextAware dataSourceContextAware;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DataSourceService(DataSourceContextAware dataSourceContextAware) {
        this.dataSourceContextAware = dataSourceContextAware;
    }

   // public Object connect(String dsType, JSONObject data, Session channel)  {
   //     //加入泛型
   //     Class<?> aClass = dataSourceContextAware.getStrategyInstance(dsType);
   //     //判断一下会话的状态
   //     logger.info("cmd={},class={}", dsType,aClass);
   //     if (aClass != null&&channel.getChannel().isOpen()) {
   //         try {
   //             //获取接口
   //             data=new JSONObject();
   //             Method method = aClass.getMethod(dsType, String.class, JSONObject.class, Session.class);
   //             //返回一个实例化对象
   //             Object obj = aClass.newInstance();
   //             return method.invoke(obj, dsType, data, channel);
   //         } catch (Exception e) {
   //             e.printStackTrace();
   //         }
   //     }
   //     //这边可以返回自定义的Result结果集
   //     return new Result("error","undefined cmd");
   // }


    public Object connect(String dsType, JSONObject data, Session channel) throws Exception{
        return InitDataSource.invoke(dsType,dsType,data,channel);
    }
}
