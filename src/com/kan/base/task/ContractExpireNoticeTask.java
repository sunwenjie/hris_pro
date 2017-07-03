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
    * Ĭ��ÿ�η��͵��ʼ����� 10��
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

   // ���� 
   public static int CONTRACT_EXPIRE = 1;
   // ������ 
   public static int CONTRACT_PROBATION = 2;
   // ����籣����
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
      logger.debug( "ִ�������񡣡�������" );
      // i =  1 2 3 4 5;
      // 1 Ϊ 3���·���; 2 1���·���; 3 15�췢��; 4 һ������ÿ�췢��;5 4 ���·���
      for ( int i = 1; i <= 5; i++ )
      {
         List< EmployeeContractVO > toNoticeContractList = getToNoticeContractList( i );
         List< EmployeeContractVO > toNoticeProbationContractList = getToNoticeProbationContractList( i );
         List< EmployeeContractVO > toNoticeHKSBContractList = getToNoticeHKSBContractList( i );
         List< EmployeeVO > toNoticeRetireEmployeeList = getToNoticeRetireEmployee( i );
         // ���͸�ͬһ���ռ��ˣ�ͬһ�����ף�ͬһ������ʱ����Ϊһ���ʼ���
         final List< List< EmployeeContractVO > > sendNoticeList = noticeList( toNoticeContractList );
         final List< List< EmployeeContractVO > > sendNoticeProbationList = noticeList( toNoticeProbationContractList );
         final List< List< EmployeeContractVO > > sendNoticeHKSBList = noticeList( toNoticeHKSBContractList );
         dealContractList( sendNoticeList, i, CONTRACT_EXPIRE );
         dealContractList( sendNoticeProbationList, i, CONTRACT_PROBATION );
         dealContractList( sendNoticeHKSBList, i, CONTRACT_HKSB );
         // ���͸�ͬһ��owner����Ϊͬһ���ʼ�
         dealRetireEmployeeList( toNoticeRetireEmployeeList, i );
      }

      // ��ȡ��3����ڵĺ�ͬ�������Ա��û������δ���ڵĺ�ͬ�����޸�Ա��״̬Ϊ��������
      List< Object > employeeContracts = this.sqlSessionTemplate.selectList( "getRecent3DaysExpiredContracts" );
      for ( Object obj : employeeContracts )
      {
         // �ж�
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) obj;
         final int count = this.sqlSessionTemplate.selectOne( "countExpiredContractsAfterNow", employeeContractVO.getEmployeeId() );
         if ( count == 0 )
         {
            // �޸Ĺ�ԱΪ��ְ
            final EmployeeVO employeeVO = this.sqlSessionTemplate.selectOne( "getEmployeeVOByEmployeeId", employeeContractVO.getEmployeeId() );
            if ( employeeVO != null )
            {
               employeeVO.setStatus( "3" );
            }
            this.sqlSessionTemplate.update( "updateEmployee", employeeVO );
         }
      }

      this.sqlSessionTemplate.update( "updateEmployeePosition" );

      // ����֤���ڵ�
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
         sb.append( "���֤����Ϊ:" );
         EmployeeVO tempEmployeeVO = null;
         for ( EmployeeVO employeeVO : toNoticeRetireEmployeeList )
         {
            if ( owner.equals( employeeVO.getOwner() ) )
            {
               tempEmployeeVO = employeeVO;
               sb.append( employeeVO.getCertificateNumber() ).append( "��" + employeeVO.getNameZH() + "��" ).append( "��" );
            }
         }
         sb.append( "�Ĺ�Ա/Ա��������" );
         switch ( i )
         {
            case 1:
               sb.append( "3���º�����" );
               break;
            case 2:
               sb.append( "1���º�����" );
               break;
            case 3:
               sb.append( "15�������" );
               break;
            case 4:
               sb.append( "7��������" );
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
         // ����systemId
         messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
         messageMailVO.setAccountId( accountId );
         messageMailVO.setTitle( "����֪ͨ" );
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
    * i Ϊ 1 2 3 4 ����time��[1] ��ʼ��ֵ
    * @param sendNoticeList
    * @param i
    */
   private void dealContractList( List< List< EmployeeContractVO > > sendNoticeList, int i, int type )
   {
      final String[] time = new String[] { "", "������", "һ����", "15��", "7��" };
      //      final String keyWord = type == 1 ? "����" : "�����ڽ���";
      String keyWord = "";

      switch ( type )
      {
         case 1:
            keyWord = "����";
            break;
         case 2:
            keyWord = "�����ڽ���";
            break;
         case 3:
            keyWord = "���ǿ���������";
            break;
      }

      // ͬ���ף�ͬ����ʱ�䣬ͬ�ռ���
      for ( List< EmployeeContractVO > sameTypeContractVOs : sendNoticeList )
      {
         if ( sameTypeContractVOs != null && sameTypeContractVOs.size() > 0 )
         {
            final StringBuffer sb = new StringBuffer();
            sb.append( "<p>����Ϊ��" + sameTypeContractVOs.get( 0 ).getOrderId() + "��" + sameTypeContractVOs.get( 0 ).getOrderName() + "��" + "������Ա���Ͷ���ͬ����" + time[ i ]
                  + ( i == 4 ? "��" : "��" ) + keyWord + "���ϼƣ�" + sameTypeContractVOs.size() + "������</p>" );
            sb.append( "<table border='1'>" );
            sb.append( "<tr>" );
            sb.append( "<td>Ա��ID</td><td>Ա�����</td><td>Ա������</td><td>��ͬID</td><td>��ͬ���</td><td>��ͬ����</td><td>��ͬ��ʼʱ��</td><td>��ͬ����ʱ��</td><td>�����ڽ�������</td>" );
            sb.append( "</tr>" );
            for ( EmployeeContractVO tempVO : sameTypeContractVOs )
            {
               sb.append( getEmailContentModule( tempVO ) );
            }
            sb.append( "</table>" );
            // ���͸�owner
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
               // ����systemId
               messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
               messageMailVO.setAccountId( employeeContractVO.getAccountId() );
               //               messageMailVO.setTitle( type == 1 ? "��ͬ����֪ͨ" : "��ͬ�����ڽ���֪ͨ" );

               switch ( type )
               {
                  case 1:
                     messageMailVO.setTitle( "��ͬ����֪ͨ" );
                     break;
                  case 2:
                     messageMailVO.setTitle( "��ͬ�����ڽ���֪ͨ" );
                     break;
                  case 3:
                     messageMailVO.setTitle( "���ǿ�������֪ͨ" );
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
    * ��ȡ���ظ�����
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
      // ����Ͷ���ͬ�Ѿ���ǩ����Ҫ��ǩ����ͬ�������ѾͲ���Ҫ������
      if ( toNoticeContractList != null && toNoticeContractList.size() > 0 )
      {
         for ( Iterator iterator = toNoticeContractList.iterator(); iterator.hasNext(); )
         {
            EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) iterator.next();

            /** �����ͬ��Ϊ��ְ(��Ϊ����ְ��)����ǩ */
            if ( !"1".equals( employeeContractVO.getEmployStatus() ) && !"0".equals( employeeContractVO.getEmployStatus() ) )
            {
               iterator.remove();
               continue;
            }

            try
            {
               /** ����Ͷ���ͬ�Ѿ���ǩ */
               // ��ʼ����ѯ����
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
            // ͬ order ͬ owner 3���� ������ʱ���
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

   // ��������
   private String getEmailContentModule( EmployeeContractVO employeeContractVO )
   {
      StringBuffer sb = new StringBuffer();
      sb.append( "<tr>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeId() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeNo() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEmployeeNameZH() );
      if ( KANUtil.filterEmpty( employeeContractVO.getEmployeeNameEN() ) != null )
      {
         sb.append( "��" + employeeContractVO.getEmployeeNameEN() + "��" );
      }
      sb.append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractId() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractNo() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getContractNameZH() );
      if ( KANUtil.filterEmpty( employeeContractVO.getContractNameEN() ) != null )
      {
         sb.append( "��" + employeeContractVO.getContractNameEN() + "��" );
      }
      sb.append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getStartDate() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getEndDate() ).append( "</td>" );
      sb.append( "<td>" ).append( employeeContractVO.getProbationEndDate() ).append( "</td>" );
      sb.append( "</tr>" );
      return sb.toString();
   }

   // ��ȡ��ͬ�������е�����
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

   // ��ȡ���е�������
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
    * ����֤��������
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
            // ���û�к�ͬ����ȫ������
            targetEmployeeVOs.add( employeeVO );
         }
      }
      // Ŀ��֪ͨԱ��
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
               // ����systemId
               messageMailVO.setSystemId( KANConstants.SYSTEM_ID );
               messageMailVO.setAccountId( employeeVO.getAccountId() );
               messageMailVO.setCorpId( employeeVO.getCorpId() );
               messageMailVO.setTitle( "����֤��������" );
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
               System.err.println( "employeeId:" + employeeVO.getEmployeeId() + " ����֤�������ѳ���" );
            }
         }
      }

   }

   private String getHealthCardNoticeContent( final EmployeeVO employeeVO )
   {
      final String[] strs = new String[] { "", "�����º�", "һ���º�", "����º�", "7����" };
      final StringBuffer sb = new StringBuffer();
      sb.append( employeeVO.getName() ).append( "���!" );
      sb.append( "��Ľ���֤:" ).append( employeeVO.getHealthCardNo() ).append( "�� ��Чʱ��:" );
      sb.append( employeeVO.getHealthCardStartDate() ).append( "����Ч��һ�ꡣ����" );
      sb.append( strs[ Integer.parseInt( employeeVO.getNoticeFlag() ) ] ).append( "����" );
      return sb.toString();
   }

}
