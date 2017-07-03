package com.kan.base.task;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

public class EmailSendTask extends TimerTask
{

   /**
    * 默认每次发送的邮件个数 100个
    */
   private int sendCount = 100;

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

   public List< MessageMailVO > getNotSendEmail()
   {
      List< MessageMailVO > notSendEmailList = null;
      try
      {

         /**
          * 1.查询待发送的邮件
          */
         notSendEmailList = this.sqlSessionTemplate.selectList( "getNotSendMessageMailVO", this.sendCount );
         //         List< String > list = new ArrayList< String >();

         //         for ( MessageMailVO mail : notSendEmailList )
         //         {
         //            mail.setStatus( "2" );
         //            list.add( mail.getMailId() );
         //         }
         //         /**
         //          * 2.将待发送的邮件标记为发送中状态，准备发送
         //          */
         //                  if ( list.size() > 0 )
         //                  {
         //                     logger.debug( "2.将待发送的邮件标记为发送中状态，准备发送" );
         //                     this.sqlSessionTemplate.update( "updateBatchMailToSending", list );
         //                  }

      }
      catch ( final Exception e )
      {
         e.printStackTrace();

      }
      return notSendEmailList;
   }

   /**
    * 发送邮件
    * @param notSendList
    * @return
    */
   public int sendEmail( List< MessageMailVO > notSendList )
   {
      int successCount = 0;
      for ( MessageMailVO mail : notSendList )
      {
         boolean sendSuccess = false;
         try
         {
            logger.info( "###getAccountId=" + mail.getAccountId() + "#    getReception=" + mail.getReception() + "#    mail.getTitle()=" + mail.getTitle() );
            if ( "2".equals( mail.getContentType() ) )
            {
               sendSuccess = new Mail( mail.getAccountId(), mail.getReception(), mail.getTitle(), mail.getContent(), mail.getRemark1() ).send( true );
            }
            else
            {
               sendSuccess = new Mail( mail.getAccountId(), mail.getReception(), mail.getTitle(), mail.getContent(), mail.getRemark1() ).send( false );
            }
            successCount++;
         }
         catch ( KANException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e);
         }
         logger.error("=============sendSuccess:"+sendSuccess);
         if ( sendSuccess )
         {
            mail.setStatus( "3" );
            int sendTimes = mail.getSendingTimes();
//            mail.setSendingTimes( sendTimes++ );
            mail.setSendingTimes( sendTimes+1 );
            mail.setSentTime( Calendar.getInstance().getTime() );
         }
         else
         {
            int sendTimes = mail.getSendingTimes();
//            mail.setSendingTimes( sendTimes++ );
            mail.setSendingTimes( sendTimes+1 );
            mail.setLastSendingTime( Calendar.getInstance().getTime() );
            mail.setStatus( "-1" );
         }
         this.sqlSessionTemplate.update( "updateMessageMail", mail );
         logger.debug( "#######邮件#" + mail.getMailId() + "#发送" + ( sendSuccess == true ? "成功！" : "失败！" ) );
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
      List< MessageMailVO > notSendList = getNotSendEmail();

      logger.debug( "未发送邮件个数=" + notSendList.size() );
      sendEmail( notSendList );

      // 获取草稿箱的邮件
      List< MessageMailVO > draftBoxEmailList = getDraftBoxEmail();
      // 处理草稿邮件
      try
      {
         dealDraftBoxEmails( draftBoxEmailList );
      }
      catch ( Exception e )
      {
         System.err.println( e );
      }

   }

   private List< MessageMailVO > getDraftBoxEmail()
   {
      List< MessageMailVO > draftBoxEmailList = null;
      try
      {
         /**
          * 1.查询草稿邮件
          */
         draftBoxEmailList = this.sqlSessionTemplate.selectList( "getDraftBoxMessageMail", this.sendCount );
         List< String > list = new ArrayList< String >();

         for ( MessageMailVO mail : draftBoxEmailList )
         {
            // 草稿邮件设为待发送
            mail.setStatus( "1" );
            list.add( mail.getMailId() );
         }
         /**
          * 2.将草稿邮件设为待发送状态
          */
         //         if ( list.size() > 0 )
         //         {
         //            logger.debug( "将草稿邮件设为待发送状态" );
         //            this.sqlSessionTemplate.update( "updateBatchMail2ToSend", list );
         //         }

      }
      catch ( final Exception e )
      {

      }
      return draftBoxEmailList;
   }

   // 判断当前的邮件是否有发送规则。如果有则产生下条草稿邮件
   private void dealDraftBoxEmails( List< MessageMailVO > draftBoxEmailList ) throws Exception
   {
      for ( MessageMailVO mailVO : draftBoxEmailList )
      {
         // 判断发送类型  1立即发送 2定时发送 3 循环发送  同时规则是否在有效期内
         if ( "3".equals( mailVO.getSendType() ) && availRegular( mailVO ) )
         {
            // 获取循环类型  1 按日  2 按月 3 按天
            final String repeatType = mailVO.getRepeatType();
            // 下次待发送时间
            Date nextToSendTime = null;
            if ( "1".equals( repeatType ) )
            {
               //  获取当前邮件的待发送时间
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // 获取当前邮件的下次发送周期
               final String period = mailVO.getPeriod();
               // 算出该邮件的下次待发送时间
               nextToSendTime = KANUtil.getDateAfter( toSendTime, Integer.parseInt( period ) );

            }
            else if ( "2".equals( repeatType ) )
            {
               //  获取当前邮件的待发送时间
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // 获取当前邮件的下次发送周期
               final int period = Integer.parseInt( mailVO.getPeriod() );
               // 获取当前邮件下次发送星期周数
               final String weekPeriod = mailVO.getWeekPeriod();
               final char[] weekPeriodChar = weekPeriod.toCharArray();
               // 获取下次发送时间最近周几111100，今天星期五，则最近为下周一。今天星期3则最近为本周四
               final int nextAvailWeekDay = nextAvailWeekDay( weekPeriodChar );
               final int curActualDayOfWeek = KANUtil.getActualDayOfWeek( new Date() );
               // 如果下次邮件星期大于当前星期。则为本星期几。否则为下几个星期几
               if ( nextAvailWeekDay > curActualDayOfWeek )
               {
                  nextToSendTime = KANUtil.getLastestWeekDay( toSendTime, nextAvailWeekDay );
               }
               else
               {
                  // 算出该邮件的下次待发送时间
                  for ( int i = 0; i < period; i++ )
                  {
                     // 一次循环为下一个星期
                     nextToSendTime = KANUtil.getNextWeekDate( i == 0 ? toSendTime : nextToSendTime, nextAvailWeekDay );
                  }
               }

            }
            else if ( "3".equals( repeatType ) )
            {

               //  获取当前邮件的待发送时间
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // 获取当前邮件的下次发送周期  间隔xx个月
               final int period = Integer.parseInt( mailVO.getPeriod() );
               // 获取当前邮件的第几天
               final int additionalPeriod = Integer.parseInt( mailVO.getAdditionalPeriod() );
               // 算出该邮件的下次待发送时间
               // 算出几个月后的时间
               Date tempNextToSendTime = KANUtil.getDateAfterMonth( toSendTime, period );
               // 算出这个月的第几天
               nextToSendTime = KANUtil.getFirstDateTimeOfMonth( tempNextToSendTime, additionalPeriod );

            }
            // 如果得到下次发送时间 并且下次发送时间未过期 则添加下次发送邮件;
            if ( nextToSendTime != null && nextToSendTime.getTime() <= KANUtil.createDate( mailVO.getEndDateTime() ).getTime() )
            {
               // 创建下次发送的邮件,
               final MessageMailVO nextMessageMailVO = mailVO;
               // 设置邮件的待发送时间为下次的待发送时间
               nextMessageMailVO.setToSendTime( KANUtil.formatDate( nextToSendTime, "yyyy-MM-dd HH:mm:ss" ) );
               // 添加草稿邮件到数据库
               this.sqlSessionTemplate.insert( "insertDraftBoxMessageMail", nextMessageMailVO );
            }
            else
            {
               logger.debug( "循环邮件《" + mailVO.getTitle() + "》最后一次发送时间为：" + mailVO.getToSendTime() );
            }

         }
         // 如果规则失效，即过了邮件失效日期,上次发送则为最后一次发送
         else
         {
            logger.debug( "循环邮件：" + mailVO.getTitle() + "   循环结束" );
         }
      }

   }

   /**
    * 判断当前的邮件规则是否在 有效期内
    * @param messageMailVO
    * @return
    */
   private boolean availRegular( MessageMailVO messageMailVO )
   {
      // 因为只有历史草稿邮件才能被查出。所以只需要判断待发送时间是否小于规则的失效时间
      // 获取待发送时间
      final Date toSendTime = KANUtil.createDate( messageMailVO.getToSendTime() );
      // 获取失效时间
      final Date endDateTime = KANUtil.createDate( messageMailVO.getEndDateTime() );
      return toSendTime.getTime() <= endDateTime.getTime();
   }

   /**返回实际星期几
    * @param weekPeriodChar   1111100
    * @return
    */
   private static int nextAvailWeekDay( char[] weekPeriodChar )
   {
      int[] weekNums = { 7, 1, 2, 3, 4, 5, 6 };
      Calendar cal = Calendar.getInstance();
      cal.setTime( new Date() );
      // 获取当前是星期几，
      int weekOfDay = cal.get( Calendar.DAY_OF_WEEK ) - 1;
      if ( weekOfDay < 0 )
      {
         weekOfDay = 0;
      }
      // 转成ZH 常用星期
      weekOfDay = weekNums[ weekOfDay ];
      int resultWeekOfDay = 0;
      for ( int i = weekOfDay; i < weekPeriodChar.length; i++ )
      {
         // 判断是否发送
         if ( weekPeriodChar[ i ] == '1' )
         {
            resultWeekOfDay = i;
            break;
         }
      }
      // 如果没找到则为下一个星期的星期几
      if ( resultWeekOfDay == 0 )
      {
         for ( int i = 0; i < weekPeriodChar.length; i++ )
         {
            // 判断是否发送
            if ( weekPeriodChar[ i ] == '1' )
            {
               resultWeekOfDay = i;
               break;
            }
         }
      }
      return resultWeekOfDay + 1;
   }
}
