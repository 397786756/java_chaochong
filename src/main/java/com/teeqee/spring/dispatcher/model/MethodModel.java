package com.teeqee.spring.dispatcher.model;

import com.alibaba.fastjson.JSONObject;
import com.teeqee.net.handler.AbstractSession;
import lombok.Data;

/**
 * 标记着一个方法体
 */
@Data
public class MethodModel {
   /**会话session*/
   private AbstractSession session;
   /**方法cmd*/
   private String cmd;
   /**数据源*/
   private JSONObject data;

   public MethodModel( String cmd, JSONObject data,AbstractSession session) {
      this.session = session;
      this.cmd = cmd;
      this.data = data;
   }
}
