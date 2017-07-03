package com.kan.base.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.service.inf.message.MessageMailService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANUtil;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class ContractExpireNoticeTask extends TimerTask
{

   /**
    * 默认每次发送的邮件个数 10个
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

   // email service
   private MessageMailService messageMailService;

   // 过期 
   public static int CONTRACT_EXPIRE = 1;
   // 试用期 
   public static int CONTRACT_PROBATION = 2;
   // 香港社保缴纳
   public static int CONTRACT_HKSB = 3;

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

   public int getSendCount()
   {
      return sendCount;
   }

   public void setSendCount( int sendCount )
   {
      this.sendCount = sendCount;
   }

   public MessageMailService getMessageMailService()
   {
      return messageMailService;
   }

   public void setMessageMailService( MessageMailService messageMailService )
   {
      this.messageMailService = messageMailService;
   }

   @Override
   public void run()
   {
      logger.debug( "执行了任务。。。。。" );
      // i =  1 2 3 4 5;
      // 1 为 3个月发送; 2 1个月发送; 3 15天发送; 4 一个星期每天发送;5 4 个月发送
      for ( int i = 1; i <= 5; i++ )
      {
         List< EmployeeContractVO > toNoticeContractList = getToNoticeContractList( i );
         List< EmployeeContractVO > toNoticeProbationContractList = getToNoticeProbationContractList( i );
         List< EmployeeContractVO > toNoticeHKSBContractList = getToNoticeHKSBContractList( i );
         List< EmployeeVO > toNoticeRetireEmployeeList = getToNoticeRetireEmployee( i );
         // 发送给同一个收件人，同一个账套，同一个提醒时间作为一封邮件。
         final List< List< EmployeeContractVO > > sendNoticeList = noticeList( toNoticeContractList );
         final List< List< EmployeeContractVO > > sendNoticeProbationList = noticeList( toNoticeProbationContractList );
         final List< List< EmployeeContractVO > > sendNoticeHKSBList = noticeList( toNoticeHKSBContractList );
         dealContractList( sendNoticeList, i, CONTRACT_EXPIRE );
         dealContractList( sendNoticeProbationList, i, CONTRACT_PROBATION );
         dealContractList( sendNoticeHKSBList, i, CONTRACT_HKSB );
         // 发送给同一个owner的作为同一份邮件
         dealRetireEmployeeList( toNoticeRetireEmployeeList, i );
      }

      // 获取近3天过期的合同。如果该员工没有其他未过期的合同。则修改员工状态为正常结束
      List< Object > employeeContracts = this.sqlSessionTemplate.selectList( "getRecent3DaysExpiredContracts" );
      for ( Object obj : employeeContracts )
      {
         // 判断
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) obj;
         final int count = this.sqlSessionTemplate.selectOne( "countExpiredContractsAfterNow", employeeContractVO.getEmployeeId() );
         if ( count == 0 )
         {
            // 修改雇员为离职
            final EmployeeVO employeeVO = this.sqlSessionTemplate.selectOne( "getEmployeeVOByEmployeeId", employeeContractVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               employeeVO.setStatus( "3" );
            }
            this.sqlSessionTemplate.update( "updateEmployee", employeeVO );
         }
      }

      this.sqlSessionTemplate.update( "updateEmployeePosition" );

      // 健康证到期的
      final List< Object > employeeVOs = this.sqlSessionTemplate.selectList( "getEmployeeVOsByNoticeHealthCardExpire" );
      noticeHealthCardExpire( employeeVOs );
   }

   private void dealRetireEmployeeList( List< EmployeeVO > toNoticeRetireEmployeeList, int i )
   {
      final List< String > owns = new ArrayList< String >();
      for ( EmployeeVO employeeVO : toNoticeRetireEmployeeList )
      {
         owns.add( employeeVO.getOwner() );
      }
      final List< String > distinctOwners = KANUtil.getDistinctList( owns );
      for ( String owner : distinctOwners )
      {
         StringBuffer sb = new StringBuffer();
         sb.append( "身份证号码为:" );
         EmployeeVO tempEmployeeVO = null;
         for ( EmployeeVO employeeVO : toNoticeRetireEmployeeList )
         {
            if ( owner.equals( employeeVO.getOwner() ) )
            {
               tempEmployeeVO = employeeVO;
               sb.append( employeeVO.getCertificateNumber() ).append( "（" + employeeVO.getNameZH() + "）" ).append( "；" );
            }
         }
         sb.append( "的雇员/员工，将在" );
         switch ( i )
         {
            case 1:
               sb.append( "3个月后退休" );
               break;
            case 2:
               sb.append( "1个月后退休" );
               break;
            case 3:
               sb.append( "15天后退休" );
               break;
            case 4:
               sb.append( "7天内退休" );
               break;
            default:
               break;
         }

         if ( KANUtil.filterEmpty( tempEmployeeVO.getOwnerBizEmail() ) != null )
         {
            sendEmailRetire( sb.toString(), tempEmployeeVO.getOwnerBizEmail(), tempEmployeeVO.getAccountId() );
         }
         else if ( KANUtil.filterEmpty( tempEmployeeVO.getOwnerPersonalEmail() ) != null )
         {
            sendEmailRetire( sb.toString(), tempEmployeeVO.getOwnerPersonalEmail(), tempEmployeeVO.getAccountId() );
         }
      }

   }

   private void sendEmailRetire( final String content, final String ownerBizEmail, final String accountId )
   {
      try
      {
         final MessageMailVO messageMailVO = new MessageMailVO();
         // 设置systemId
         messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
         messageMailVO.setAccountId( accountId );
         messageMailVO.setTitle( "退休通知" );
         messageMailVO.setContent( content );
         messageMailVO.setContentType( "2" );
         messageMailVO.setTemplateId( "0" );
         messageMailVO.setStatus( "1" );
         messageMailVO.setReception( ownerBizEmail );
         messageMailService.insertMessageMail( messageMailVO, accountId );
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

   }

   /**
    * i 为 1 2 3 4 所以time从[1] 开始有值
    * @param sendNoticeList
    * @param i
    */
   private void dealContractList( List< List< EmployeeContractVO > > sendNoticeList, int i, int type )
   {
      final String[] time = new String[] { "", "三个月", "一个月", "15天", "7天" };
      //      final String keyWord = type == 1 ? "过期" : "试用期结束";
      String keyWord = "";

      switch ( type )
      {
         case 1:
            keyWord = "过期";
            break;
         case 2:
            keyWord = "试用期结束";
            break;
         case 3:
            keyWord = "香港强积金需缴纳";
            break;
      }

      // 同账套，同提醒时间，同收件人
      for ( List< EmployeeContractVO > sameTypeContractVOs : sendNoticeList )
      {
         if ( sameTypeContractVOs != null && sameTypeContractVOs.size() > 0 )
         {
            final StringBuffer sb = new StringBuffer();
            sb.append( "<p>账套为：" + sameTypeContractVOs.get( 0 ).getOrderId() + "（" + sameTypeContractVOs.get( 0 ).getOrderName() + "）" + "的以下员工劳动合同将在" + time[ i ]
                  + ( i == 4 ? "内" : "后" ) + keyWord + "（合计：" + sameTypeContractVOs.size() + "个）：</p>" );
            sb.append( "<table border='1'>" );
            sb.append( "<tr>" );
            sb.append( "<td>员工ID</td><td>员工编号</td><td>员工姓名</td><td>合同ID</td><td>合同编号</td><td>合同名称</td><td>合同开始时间</td><td>合同结束时间</td><td>试用期结束日期</td>" );
            sb.append( "</tr>" );
            for ( EmployeeContractVO tempVO : sameTypeContractVOs )
            {
               sb.append( getEmailContentModule( tempVO ) );
            }
            sb.append( "</table>" );
            // 发送给owner
            sendEmail( sameTypeContractVOs.get( 0 ), sb.toString(), type, sameTypeContractVOs.get( 0 ).getAccountId() );
         }
      }
   }

   private void sendEmail( final EmployeeContractVO employeeContractVO, final String content, final int type, final String accountId )
   {
      try
      {
         final String positionId = employeeContractVO.getOwner();
         final List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( accountId ).getStaffDTOsByPositionId( positionId );
         final List< String > distinctEmails = getDistinctEmailsByStaffDTOs( staffDTOs );
         for ( String email : distinctEmails )
         {

            if ( KANUtil.filterEmpty( email ) != null )
            {
               final MessageMailVO messageMailVO = new MessageMailVO();
               // 设置systemId
               messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
               messageMailVO.setAccountId( employeeContractVO.getAccountId() );
               //               messageMailVO.setTitle( type == 1 ? "合同到期通知" : "合同试用期结束通知" );

               switch ( type )
               {
                  case 1:
                     messageMailVO.setTitle( "合同到期通知" );
                     break;
                  case 2:
                     messageMailVO.setTitle( "合同试用期结束通知" );
                     break;
                  case 3:
                     messageMailVO.setTitle( "香港强积金缴纳通知" );
                     break;
               }

               messageMailVO.setContent( content );
               messageMailVO.setContentType( "2" );
               messageMailVO.setTemplateId( "0" );
               messageMailVO.setStatus( "1" );
               messageMailVO.setReception( email );
               messageMailService.insertMessageMail( messageMailVO, messageMailVO.getAccountId() );
            }
         }
      }
      catch ( Exception e )
      {
         System.err.println( e );
      }

   }

   /**
    * 获取不重复邮箱
    * @param staffDTOs
    * @return
    */
   private List< String > getDistinctEmailsByStaffDTOs( List< StaffDTO > staffDTOs )
   {
      final List< String > distinctEmails = new ArrayList< String >();
      for ( StaffDTO staffDTO : staffDTOs )
      {
         if ( KANUtil.filterEmpty( staffDTO.getStaffVO().getBizEmail() ) != null )
         {
            distinctEmails.add( staffDTO.getStaffVO().getBizEmail() );
         }
         else if ( KANUtil.filterEmpty( staffDTO.getStaffVO().getPersonalEmail() ) != null )
         {
            distinctEmails.add( staffDTO.getStaffVO().getPersonalEmail() );
         }
      }
      for ( int i = 0; i < distinctEmails.size(); i++ )
      {
         for ( int j = distinctEmails.size() - 1; j > i; j-- )
         {
            if ( distinctEmails.get( i ).equals( distinctEmails.get( j ) ) )
            {
               distinctEmails.remove( j );
            }
         }
      }
      return distinctEmails;
   }

   private List< List< EmployeeContractVO > > noticeList( List< EmployeeContractVO > toNoticeContractList )
   {
      // 如果劳动合同已经续签或不需要续签，合同结束提醒就不需要出现了
      if ( toNoticeContractList != null && toNoticeContractList.size() > 0 )
      {
         for ( Iterator iterator = toNoticeContractList.iterator(); iterator.hasNext(); )
         {
            EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) iterator.next();

            /** 如果合同已为离职(不为“在职”)不续签 */
            if ( !"1".equals( employeeContractVO.getEmployStatus() ) && !"0".equals( employeeContractVO.getEmployStatus() ) )
            {
               iterator.remove();
               continue;
            }

            try
            {
               /** 如果劳动合同已经续签 */
               // 初始化查询条件
               EmployeeContractVO tempEmployeeContractVO = new EmployeeContractVO();
               tempEmployeeContractVO.setAccountId( employeeContractVO.getAccountId() );
               tempEmployeeContractVO.setCorpId( employeeContractVO.getCorpId() );
               tempEmployeeContractVO.setEmployeeId( employeeContractVO.getEmployeeId() );
               tempEmployeeContractVO.setStartDate( KANUtil.getStrDate( employeeContractVO.getEndDate(), 1 ) );
               tempEmployeeContractVO.setTemplateId( employeeContractVO.getTemplateId() );
               tempEmployeeContractVO.setEntityId( employeeContractVO.getEntityId() );
               final List< Object > employeeContractVOs = this.sqlSessionTemplate.selectList( "getEmployeeContractVOsByCondition", tempEmployeeContractVO );

               if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
               {
                  iterator.remove();
                  continue;
               }

            }
            catch ( ParseException e )
            {
               e.printStackTrace();
            }

         }

      }

      final List< List< EmployeeContractVO > > contractGroup = new ArrayList< List< EmployeeContractVO > >();
      List< String > distinctOrderIds = getContractsDistinctOrderList( toNoticeContractList );
      List< String > distinctOwners = getContractsDistinctOwnerList( toNoticeContractList );
      for ( String orderId : distinctOrderIds )
      {
         for ( String owner : distinctOwners )
         {
            final List< EmployeeContractVO > tempGroup = new ArrayList< EmployeeContractVO >();
            // 同 order 同 owner 3个月 或其他时间段
            for ( EmployeeContractVO vo : toNoticeContractList )
            {
               if ( orderId.equals( vo.getOrderId() ) && owner.equals( vo.getOwner() ) )
               {
                  tempGroup.add( vo );
               }
            }
            contractGroup.add( tempGroup );
         }

      }
      return contractGroup;

   }

   private List< EmployeeContractVO > getToNoticeContractList( int noticeExpireType )
   {
      return this.sqlSessionTemplate.selectList( "getToNoticeContractList" + noticeExpireType, noticeExpireType );
   }

   private List< EmployeeContractVO > getToNoticeProbationContractList( int noticeExpireType )
   {
      return this.sqlSessionTemplate.selectList( "getToNoticeProbationContractList" + noticeExpireType, noticeExpireType );
   }

   private List< EmployeeContractVO > getToNoticeHKSBContractList( int noticeExpireType )
   {
      if ( noticeExpireType == 1 )
      {
         return new ArrayList< EmployeeContractVO >();
      }
      else
      {
         return this.sqlSessionTemplate.selectList( "getToNoticeHKSBContractList" + noticeExpireType, noticeExpireType );
      }
   }

   private List< EmployeeVO > getToNoticeRetireEmployee( int noticeRetireType )
   {
      return this.sqlSessionTemplate.selectList( "getToNoticeRetireList" + noticeRetireType, noticeRetireType );
   }

   // 发送内容
   private String getEmailContentModule( EmployeeContractVO employeeContractVO )
   {
      StringBuffer sb = new StringBuffer();
      sb.append( "<tr>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeId() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeNo() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeNameZH() );
      if ( KANUtil.filterEmpty( employeeContractVO.getEmployeeNameEN() ) != null )
      {
         sb.append( "（" + employeeContractVO.getEmployeeNameEN() + "）" );
      }
      sb.append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractId() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractNo() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractNameZH() );
      if ( KANUtil.filterEmpty( employeeContractVO.getContractNameEN() ) != null )
      {
         sb.append( "（" + employeeContractVO.getContractNameEN() + "）" );
      }
      sb.append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getStartDate() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEndDate() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getProbationEndDate() ).append( "</td>" );
      sb.append( "</tr>" );
      return sb.toString();
   }

   // 获取合同里面所有的账套
   private List< String > getContractsDistinctOrderList( List< EmployeeContractVO > toNoticeContractList )
   {
      List< String > distinctOrderIds = new ArrayList< String >();
      if ( toNoticeContractList != null && toNoticeContractList.size() > 0 )
      {
         for ( EmployeeContractVO employeeContractVO : toNoticeContractList )
         {
            boolean flag = false;
            for ( String distinctOrderId : distinctOrderIds )
            {
               if ( distinctOrderId.equals( employeeContractVO.getOrderId() ) )
               {
                  flag = true;
                  break;
               }
            }
            if ( !flag )
            {
               distinctOrderIds.add( employeeContractVO.getOrderId() );
            }
         }
      }

      return distinctOrderIds;
   }

   // 获取所有的所属人
   private List< String > getContractsDistinctOwnerList( List< EmployeeContractVO > toNoticeContractList )
   {
      List< String > distinctOwners = new ArrayList< String >();
      for ( EmployeeContractVO employeeContractVO : toNoticeContractList )
      {
         boolean flag = false;
         for ( String distinctOwner : distinctOwners )
         {
            if ( distinctOwner.equals( employeeContractVO.getOwner() ) )
            {
               flag = true;
               break;
            }
         }
         if ( !flag )
         {
            distinctOwners.add( employeeContractVO.getOwner() );
         }
      }

      return distinctOwners;
   }

   /**
    * 健康证到期提醒
    * @param employeeVOs
    */
   private void noticeHealthCardExpire( List< Object > employeeVOs )
   {
      final List< EmployeeVO > targetEmployeeVOs = new ArrayList< EmployeeVO >();
      for ( int i = 0; i < employeeVOs.size(); i++ )
      {
         final EmployeeVO employeeVO = ( EmployeeVO ) employeeVOs.get( i );
         final List< Object > employeeContractVOs = this.sqlSessionTemplate.selectList( "getEmployeeContractVOsByEmployeeId", employeeVO.getEmployeeId() );
         if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
         {
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) employeeContractVOs.get( i );
            final String orderId = employeeContractVO.getOrderId();
            final ClientOrderHeaderVO clientOrderHeaderVO = this.sqlSessionTemplate.selectOne( "getClientOrderHeaderVOByOrderHeaderId", orderId );
            final String[] jsonArray = KANUtil.jasonArrayToStringArray( clientOrderHeaderVO.getNoticeExpire() );
            for ( int j = 0; j < jsonArray.length; j++ )
            {
               if ( employeeVO.getNoticeFlag().equals( jsonArray[ j ] ) )
               {
                  targetEmployeeVOs.add( employeeVO );
                  break;
               }
            }

         }
         else
         {
            // 如果没有合同，则全部提醒
            targetEmployeeVOs.add( employeeVO );
         }
      }
      // 目标通知员工
      for ( EmployeeVO employeeVO : targetEmployeeVOs )
      {
         String email = "";
         if ( KANUtil.filterEmpty( employeeVO.getEmail1() ) != null )
         {
            email = employeeVO.getEmail1();
         }
         if ( KANUtil.filterEmpty( email ) == null )
         {
            email = employeeVO.getEmail2();
         }
         if ( KANUtil.filterEmpty( email ) != null )
         {
            try
            {
               final MessageMailVO messageMailVO = new MessageMailVO();
               // 设置systemId
               messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
               messageMailVO.setAccountId( employeeVO.getAccountId() );
               messageMailVO.setCorpId( employeeVO.getCorpId() );
               messageMailVO.setTitle( "健康证过期提醒" );
               messageMailVO.setContent( getHealthCardNoticeContent( employeeVO ) );
               messageMailVO.setContentType( "2" );
               messageMailVO.setTemplateId( "0" );
               messageMailVO.setStatus( "1" );
               messageMailVO.setReception( email );
               messageMailService.insertMessageMail( messageMailVO, messageMailVO.getAccountId() );
            }
            catch ( Exception e )
            {
               e.printStackTrace();
               System.err.println( "employeeId:" + employeeVO.getEmployeeId() + " 健康证过期提醒出错" );
            }
         }
      }

   }

   private String getHealthCardNoticeContent( final EmployeeVO employeeVO )
   {
      final String[] strs = new String[] { "", "三个月后", "一个月后", "半个月后", "7天内" };
      final StringBuffer sb = new StringBuffer();
      sb.append( employeeVO.getName() ).append( "你好!" );
      sb.append( "你的健康证:" ).append( employeeVO.getHealthCardNo() ).append( "， 生效时间:" );
      sb.append( employeeVO.getHealthCardStartDate() ).append( "，有效期一年。将于" );
      sb.append( strs[ Integer.parseInt( employeeVO.getNoticeFlag() ) ] ).append( "过期" );
      return sb.toString();
   }

}
