package com.kan.base.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.util.KANException;
import com.kan.base.util.SMS;

public class SmsSendTask extends TimerTask
{

   /**
    * 默认每次发送的短信个数 10个
    */
   private int sendCount = 10;

   protected Log logger = LogFactory.getLog( getClass() );

   // Instance DataSource transaction manager
   private DataSourceTransactionManager transactionManager;

   // Default transaction definition
   private final DefaultTransactionDefinition definition = new DefaultTransactionDefinition( DefaultTransactionDefinition.PROPAGATION_NESTED );
   // Instance transaction status
   private TransactionStatus status = null;

   // Instance the SQL session template
   private SqlSessionTemplate sqlSessionTemplate;

   // Set DataSource transaction manager
   public void setTransactionManager( final DataSourceTransactionManager transactionManager )
   {
      this.transactionManager = transactionManager;
   }

   // Start transaction
   public void startTransaction()
   {
      status = transactionManager.getTransaction( definition );
   }

   // Commit transaction
   public void commitTransaction()
   {
      transactionManager.commit( status );
   }

   // Rollback transaction
   public void rollbackTransaction()
   {
      transactionManager.rollback( status );
   }

   // Set SQL session template
   public void setSqlSessionTemplate( final SqlSessionTemplate sqlSessionTemplate )
   {
      this.sqlSessionTemplate = sqlSessionTemplate;
   }

   public DataSourceTransactionManager getTransactionManager()
   {
      return transactionManager;
   }

   public SqlSessionTemplate getSqlSessionTemplate()
   {
      return sqlSessionTemplate;
   }

   public List< MessageSmsVO > getNotSendSms()
   {
      List< MessageSmsVO > notSendSmsList = null;
      try
      {
        
         /**
          * 1.查询待发送的短信
          */
         notSendSmsList = this.sqlSessionTemplate.selectList( "getNotSendMessageSmsVO", this.sendCount );
         List< String > list = new ArrayList< String >();

         for ( MessageSmsVO sms : notSendSmsList )
         {
            sms.setStatus( "2" );
            list.add( sms.getSmsId() );
         }
         logger.debug( "1.查询待发送的短信=" + list.size() );
         /**
          * 2.将待发送的短信标记为发送中状态，准备发送
          */
         if ( list.size() > 0 )
         {
            logger.debug( "2.将待发送的短信标记为发送中状态，准备发送" );
            this.sqlSessionTemplate.update( "updateBatchSmsToSending", list );
         }
      }
      catch ( final Exception e )
      {
         e.printStackTrace();
      }
      return notSendSmsList;
   }

   /**
    * 发送短信
    * @param notSendList
    * @return
    */
   public int sendSms( List< MessageSmsVO > notSendList )
   {
      int successCount = 0;
      for ( MessageSmsVO sms : notSendList )
      {
         boolean sendSuccess = false;
         try
         {
            logger.debug( "###getAccountId=" + sms.getAccountId() + "#    getReception=" + sms.getReception() + "#   getContent()=" + sms.getContent() );
            sendSuccess = new SMS( sms.getAccountId(), sms.getReception(), sms.getContent() ).send();
            successCount++;
         }
         catch ( KANException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         int sendTimes = sms.getSendingTimes();
         sms.setSendingTimes( ++sendTimes );
         if ( sendSuccess )
         {
            sms.setStatus( "3" );
            sms.setSentTime( Calendar.getInstance().getTime() );
         }
         else
         {
            sms.setLastSendingTime( Calendar.getInstance().getTime() );
         }
         this.sqlSessionTemplate.update( "updateMessageSms", sms );
         logger.debug( "#######短信#" + sms.getSmsId() + "#发送" + ( sendSuccess == true ? "成功！" : "失败！" ) );
      }
      return successCount;
   }

   public int getSendCount()
   {
      return sendCount;
   }

   public void setSendCount( int sendCount )
   {
      this.sendCount = sendCount;
   }

   @Override
   public void run()
   {
      logger.debug( "执行了任务。。。。。" );
      List< MessageSmsVO > notSendList = getNotSendSms();
      sendSms( notSendList );

   }

}
