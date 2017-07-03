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
    * Ĭ��ÿ�η��͵��ʼ����� 100��
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
          * 1.��ѯ�����͵��ʼ�
          */
         notSendEmailList = this.sqlSessionTemplate.selectList( "getNotSendMessageMailVO", this.sendCount );
         //         List< String > list = new ArrayList< String >();

         //         for ( MessageMailVO mail : notSendEmailList )
         //         {
         //            mail.setStatus( "2" );
         //            list.add( mail.getMailId() );
         //         }
         //         /**
         //          * 2.�������͵��ʼ����Ϊ������״̬��׼������
         //          */
         //                  if ( list.size() > 0 )
         //                  {
         //                     logger.debug( "2.�������͵��ʼ����Ϊ������״̬��׼������" );
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
    * �����ʼ�
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
         logger.debug( "#######�ʼ�#" + mail.getMailId() + "#����" + ( sendSuccess == true ? "�ɹ���" : "ʧ�ܣ�" ) );
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
      logger.debug( "ִ�������񡣡�������" );
      List< MessageMailVO > notSendList = getNotSendEmail();

      logger.debug( "δ�����ʼ�����=" + notSendList.size() );
      sendEmail( notSendList );

      // ��ȡ�ݸ�����ʼ�
      List< MessageMailVO > draftBoxEmailList = getDraftBoxEmail();
      // ����ݸ��ʼ�
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
          * 1.��ѯ�ݸ��ʼ�
          */
         draftBoxEmailList = this.sqlSessionTemplate.selectList( "getDraftBoxMessageMail", this.sendCount );
         List< String > list = new ArrayList< String >();

         for ( MessageMailVO mail : draftBoxEmailList )
         {
            // �ݸ��ʼ���Ϊ������
            mail.setStatus( "1" );
            list.add( mail.getMailId() );
         }
         /**
          * 2.���ݸ��ʼ���Ϊ������״̬
          */
         //         if ( list.size() > 0 )
         //         {
         //            logger.debug( "���ݸ��ʼ���Ϊ������״̬" );
         //            this.sqlSessionTemplate.update( "updateBatchMail2ToSend", list );
         //         }

      }
      catch ( final Exception e )
      {

      }
      return draftBoxEmailList;
   }

   // �жϵ�ǰ���ʼ��Ƿ��з��͹������������������ݸ��ʼ�
   private void dealDraftBoxEmails( List< MessageMailVO > draftBoxEmailList ) throws Exception
   {
      for ( MessageMailVO mailVO : draftBoxEmailList )
      {
         // �жϷ�������  1�������� 2��ʱ���� 3 ѭ������  ͬʱ�����Ƿ�����Ч����
         if ( "3".equals( mailVO.getSendType() ) && availRegular( mailVO ) )
         {
            // ��ȡѭ������  1 ����  2 ���� 3 ����
            final String repeatType = mailVO.getRepeatType();
            // �´δ�����ʱ��
            Date nextToSendTime = null;
            if ( "1".equals( repeatType ) )
            {
               //  ��ȡ��ǰ�ʼ��Ĵ�����ʱ��
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // ��ȡ��ǰ�ʼ����´η�������
               final String period = mailVO.getPeriod();
               // ������ʼ����´δ�����ʱ��
               nextToSendTime = KANUtil.getDateAfter( toSendTime, Integer.parseInt( period ) );

            }
            else if ( "2".equals( repeatType ) )
            {
               //  ��ȡ��ǰ�ʼ��Ĵ�����ʱ��
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // ��ȡ��ǰ�ʼ����´η�������
               final int period = Integer.parseInt( mailVO.getPeriod() );
               // ��ȡ��ǰ�ʼ��´η�����������
               final String weekPeriod = mailVO.getWeekPeriod();
               final char[] weekPeriodChar = weekPeriod.toCharArray();
               // ��ȡ�´η���ʱ������ܼ�111100�����������壬�����Ϊ����һ����������3�����Ϊ������
               final int nextAvailWeekDay = nextAvailWeekDay( weekPeriodChar );
               final int curActualDayOfWeek = KANUtil.getActualDayOfWeek( new Date() );
               // ����´��ʼ����ڴ��ڵ�ǰ���ڡ���Ϊ�����ڼ�������Ϊ�¼������ڼ�
               if ( nextAvailWeekDay > curActualDayOfWeek )
               {
                  nextToSendTime = KANUtil.getLastestWeekDay( toSendTime, nextAvailWeekDay );
               }
               else
               {
                  // ������ʼ����´δ�����ʱ��
                  for ( int i = 0; i < period; i++ )
                  {
                     // һ��ѭ��Ϊ��һ������
                     nextToSendTime = KANUtil.getNextWeekDate( i == 0 ? toSendTime : nextToSendTime, nextAvailWeekDay );
                  }
               }

            }
            else if ( "3".equals( repeatType ) )
            {

               //  ��ȡ��ǰ�ʼ��Ĵ�����ʱ��
               final Date toSendTime = KANUtil.createDate( mailVO.getToSendTime() );
               // ��ȡ��ǰ�ʼ����´η�������  ���xx����
               final int period = Integer.parseInt( mailVO.getPeriod() );
               // ��ȡ��ǰ�ʼ��ĵڼ���
               final int additionalPeriod = Integer.parseInt( mailVO.getAdditionalPeriod() );
               // ������ʼ����´δ�����ʱ��
               // ��������º��ʱ��
               Date tempNextToSendTime = KANUtil.getDateAfterMonth( toSendTime, period );
               // �������µĵڼ���
               nextToSendTime = KANUtil.getFirstDateTimeOfMonth( tempNextToSendTime, additionalPeriod );

            }
            // ����õ��´η���ʱ�� �����´η���ʱ��δ���� ������´η����ʼ�;
            if ( nextToSendTime != null && nextToSendTime.getTime() <= KANUtil.createDate( mailVO.getEndDateTime() ).getTime() )
            {
               // �����´η��͵��ʼ�,
               final MessageMailVO nextMessageMailVO = mailVO;
               // �����ʼ��Ĵ�����ʱ��Ϊ�´εĴ�����ʱ��
               nextMessageMailVO.setToSendTime( KANUtil.formatDate( nextToSendTime, "yyyy-MM-dd HH:mm:ss" ) );
               // ��Ӳݸ��ʼ������ݿ�
               this.sqlSessionTemplate.insert( "insertDraftBoxMessageMail", nextMessageMailVO );
            }
            else
            {
               logger.debug( "ѭ���ʼ���" + mailVO.getTitle() + "�����һ�η���ʱ��Ϊ��" + mailVO.getToSendTime() );
            }

         }
         // �������ʧЧ���������ʼ�ʧЧ����,�ϴη�����Ϊ���һ�η���
         else
         {
            logger.debug( "ѭ���ʼ���" + mailVO.getTitle() + "   ѭ������" );
         }
      }

   }

   /**
    * �жϵ�ǰ���ʼ������Ƿ��� ��Ч����
    * @param messageMailVO
    * @return
    */
   private boolean availRegular( MessageMailVO messageMailVO )
   {
      // ��Ϊֻ����ʷ�ݸ��ʼ����ܱ����������ֻ��Ҫ�жϴ�����ʱ���Ƿ�С�ڹ����ʧЧʱ��
      // ��ȡ������ʱ��
      final Date toSendTime = KANUtil.createDate( messageMailVO.getToSendTime() );
      // ��ȡʧЧʱ��
      final Date endDateTime = KANUtil.createDate( messageMailVO.getEndDateTime() );
      return toSendTime.getTime() <= endDateTime.getTime();
   }

   /**����ʵ�����ڼ�
    * @param weekPeriodChar   1111100
    * @return
    */
   private static int nextAvailWeekDay( char[] weekPeriodChar )
   {
      int[] weekNums = { 7, 1, 2, 3, 4, 5, 6 };
      Calendar cal = Calendar.getInstance();
      cal.setTime( new Date() );
      // ��ȡ��ǰ�����ڼ���
      int weekOfDay = cal.get( Calendar.DAY_OF_WEEK ) - 1;
      if ( weekOfDay < 0 )
      {
         weekOfDay = 0;
      }
      // ת��ZH ��������
      weekOfDay = weekNums[ weekOfDay ];
      int resultWeekOfDay = 0;
      for ( int i = weekOfDay; i < weekPeriodChar.length; i++ )
      {
         // �ж��Ƿ���
         if ( weekPeriodChar[ i ] == '1' )
         {
            resultWeekOfDay = i;
            break;
         }
      }
      // ���û�ҵ���Ϊ��һ�����ڵ����ڼ�
      if ( resultWeekOfDay == 0 )
      {
         for ( int i = 0; i < weekPeriodChar.length; i++ )
         {
            // �ж��Ƿ���
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
