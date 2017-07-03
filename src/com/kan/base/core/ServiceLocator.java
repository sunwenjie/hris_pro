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
    * 自动实例化
    * @return
    */
   public static ServiceLocator getInstance()
   {
      if ( serviceLocator == null )
         serviceLocator = ( ServiceLocator ) beanFactory.getBean( "serviceLocator" );
      return serviceLocator;
   }

   /**
    * 根据提供的Bean名称得到相应的服务类
    * 
    * @param serviceName
    *            Bean名称
    */
   public static Object getService( final String serviceName )
   {
      return beanFactory.getBean( serviceName );
   }

}
