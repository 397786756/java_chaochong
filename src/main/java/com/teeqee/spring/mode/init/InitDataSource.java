package com.teeqee.spring.mode.init;


import com.teeqee.spring.mode.annotation.DataSourceType;
import com.teeqee.spring.mode.annotation.Dispather;
import com.teeqee.spring.mode.strategy.CacheCollection;
import com.teeqee.utils.ClassScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

@Component
public class InitDataSource {

    @Value("${chaoChong.package}")
    private String packageVo;

    private static HashMap<String, Method> methodMap = new HashMap<>(1024);




    public static Object invoke(String cmd, Object... objects) throws Exception {
        Method method = methodMap.get(cmd);
        try {
            Object obj = declaringClassMap.get(cmd);
            return method.invoke(obj, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 服务器启动时候加载
     */
    @PostConstruct
    public void init() {

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
                                    String cmd = dataSourceType.value();
                                    //进行判断
                                    CacheCollection.putClass(cmd, clazz);
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


    private static HashMap<String,Object> declaringClassMap = new  HashMap<>(1024);

    public void initCmd() {
        Set<Class<?>> classes = ClassScanner.listClassesWithAnnotation(packageVo, Service.class);
        if (classes != null && classes.size() > 0) {
            classes.forEach(c -> {
                Method[] methods = c.getMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        String name = method.getName();
                        Method method1 = methodMap.get(name);
                        if (method1 != null) {
                            throw new RuntimeException("this method " + name + "exist");
                        } else {
                            try {
                                methodMap.put(name, method);
                                declaringClassMap.put(name, c.newInstance());
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }
}

