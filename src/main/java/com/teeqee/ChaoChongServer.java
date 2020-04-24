package com.teeqee;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//开启定时器
@EnableScheduling
@EnableTransactionManagement //开启事务
//开启异步调用
@EnableAsync
//这种方式，是springBoot自身的一种异步方式，使用注解实现，非常方便，我们在想要异步执行的方法上加上@Async注解，
/**
 * 功能描述:springboot 启动类
 * @Author : zhengsongjie
 * @Date : 2019-09-02 上午 05:23
 */
public class ChaoChongServer  extends SpringBootServletInitializer implements CommandLineRunner  {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ChaoChongServer.class, args);
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}[]|");
    }

    @Override
    public void run(String... args) throws Exception {
        //1加载配置文件
        //2启动websocket服务器
        logger.info("gameserver start");
    }

    @Configuration
    public class TomcatConfig {
        /**
         * @param redisConnectionFactory redis序列化
         * @return
         */
        @Bean
        public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
            redisTemplate.setConnectionFactory(redisConnectionFactory);
            // 使用Jackson2JsonRedisSerialize 替换默认序列化
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
            // 设置value的序列化规则和 key的序列化规则
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            //Hash的序列化
            redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
            //开启redis事务(true)
            // redisTemplate.setEnableTransactionSupport(true);
            redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
            redisTemplate.afterPropertiesSet();
            return redisTemplate;
        }

    }
}
