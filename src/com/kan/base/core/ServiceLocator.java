package com.kan.base.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ServiceLocator implements BeanFactoryAware
{

   private static BeanFactory beanFactory = null;

   private static ServiceLocator serviceLocator = null;

   public void setBeanFactory( BeanFactory factory ) throws BeansException
   {
      beanFactory = factory;
   }

   public BeanFactory getBeanFactory()
   {
      return beanFactory;
   }

   /**
    * �Զ�ʵ����
    * @return
    */
   public static ServiceLocator getInstance()
   {
      if ( serviceLocator == null )
         serviceLocator = ( ServiceLocator ) beanFactory.getBean( "serviceLocator" );
      return serviceLocator;
   }

   /**
    * �����ṩ��Bean���Ƶõ���Ӧ�ķ�����
    * 
    * @param serviceName
    *            Bean����
    */
   public static Object getService( final String serviceName )
   {
      return beanFactory.getBean( serviceName );
   }

}
