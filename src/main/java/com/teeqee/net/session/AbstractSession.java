package com.teeqee.net.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 抽象类网关
 * @Software: IntelliJ IDEA
 */
public abstract class AbstractSession<T>  {


    protected T channel;
    private Map<String, Object> keyToAttrs = new HashMap<>();


    public AbstractSession(T channel) {
        this.channel = channel;
    }

    /**
     * 获取连接对象的ip
     *
     * @return 连接对象的ip
     */
    public abstract String getIp();

    /**
     * 是否已连接成功
     *
     * @return 是否已连接成功
     */
    public abstract boolean isConnect();

    /**
     * 关闭连接
     */
    public abstract void close();

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public abstract void sendMessage(Object message);

    public void clear() {
        keyToAttrs.clear();
    }
    public T getChannel() {
        return channel;
    }

    /**
     * @return 返回对象体
     */
    public Map<String, Object> getKeyToAttrs() {
        return keyToAttrs;
    }

    /**
     * @param key string key
     * @param object 对象
     * @return 返回是否加入成功
     */
    public boolean setKeyToAttrs(String key,Object object) {
        keyToAttrs.putIfAbsent(key, object);
        return true;
    }
}
