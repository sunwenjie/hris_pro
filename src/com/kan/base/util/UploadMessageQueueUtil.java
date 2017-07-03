package com.kan.base.util;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.web.action.BaseAction;

public class UploadMessageQueueUtil
{

   // д�����
   public static void offer( final HttpServletRequest request, final String key, final Object value ) throws KANException
   {
      // ��ȡ������Ϣ��ջ
      Queue<Object> queue = ( Queue<Object> ) BaseAction.getObjectFromSession( request,  key );

      if(queue==null){
         queue = new LinkedList<Object>();
      }
      queue.offer( value );
      BaseAction.setObjectToSession( request, key, queue );
   }
   
   // ȡ����
   public static Object poll( final HttpServletRequest request, final String key) throws KANException
   {
      // ��ȡ������Ϣ��ջ
      Queue<Object> queue = ( Queue<Object> ) BaseAction.getObjectFromSession( request,  key );
      Object object = null;
      if(queue!=null&&queue.size()>0){
         object = queue.poll();
         BaseAction.setObjectToSession( request, key, queue );
      }

      return object;
   }
  
}
