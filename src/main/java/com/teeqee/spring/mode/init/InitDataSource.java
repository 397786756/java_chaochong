package com.teeqee.spring.mode.init;


import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.mode.strategy.CacheCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;

@Component
public class InitDataSource {

    @Value("${chaoChong.package}")
    private String packageVo;

    /**
     * 服务器启动时候加载
     */
    @PostConstruct
    public void init() {
        initAllDataSourceType(packageVo);
    }

    private void initAllDataSourceType(String packageVo) {
        URL url = this.getClass().getClassLoader().getResource(packageVo.replace(".", "/"));
        if (url != null) {
            File dir = new File(url.getFile());
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        continue;
                    } else {
                        String className = packageVo + "." + file.getName().replace(".class", "");
                        try {
                            // class cn.haoxy.ref.inter.impl.faceRecognition
                            Class<?> clazz = Class.forName(className);
                            //判断是否含有spring的service注解
                            if (clazz.isAnnotationPresent(Service.class)) {
                                //判断这个类上是否存在指定的注解
                                if (clazz.isAnnotationPresent(DataSourceType.class)) {
                                    //如果存在,得到此注解的value值
                                    DataSourceType dataSourceType = clazz.getAnnotation(DataSourceType.class);
                                    //放入容器中提供使用
                                    CacheCollection.putClass(dataSourceType.value(), clazz);
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        }
    }
}

