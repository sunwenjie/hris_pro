package com.kan.base.util;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.web.action.BaseAction;

public class UploadMessageQueueUtil
{

   // 写入队列
   public static void offer( final HttpServletRequest request, final String key, final Object value ) throws KANException
   {
      // 获取缓存信息堆栈
      Queue<Object> queue = ( Queue<Object> ) BaseAction.getObjectFromSession( request,  key );

      if(queue==null){
         queue = new LinkedList<Object>();
      }
      queue.offer( value );
      BaseAction.setObjectToSession( request, key, queue );
   }
   
   // 取队列
   public static Object poll( final HttpServletRequest request, final String key) throws KANException
   {
      // 获取缓存信息堆栈
      Queue<Object> queue = ( Queue<Object> ) BaseAction.getObjectFromSession( request,  key );
      Object object = null;
      if(queue!=null&&queue.size()>0){
         object = queue.poll();
         BaseAction.setObjectToSession( request, key, queue );
      }

      return object;
   }
  
}
