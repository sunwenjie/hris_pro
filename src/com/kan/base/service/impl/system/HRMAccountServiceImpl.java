package com.kan.base.service.impl.system;

import com.kan.base.core.ContextService;
import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.system.HRMAccountService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class HRMAccountServiceImpl extends ContextService implements HRMAccountService
{
   private ModuleDao moduleDao;

   private UserDao userDao;

   private static final String HRM = "2";

   private AccountModuleRelationDao accountModuleRelationDao;

   private static String accountId = null;

   private static String userId = null;

   private static String corpId = null;

   // ������ݿ�����ʵ��
   Connection connection = null;

   private void getConnection() throws SQLException
   {
      // ��ȡ���ӳص�����
      final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
      connection = dataSource.getConnection();
   }

   private void closeConnection() throws SQLException
   {
      if ( !connection.isClosed() )
      {
         connection.close();
      }
   }

   public String getDate()
   {
      return KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" );
   }

   @Override
   public boolean initAccount( final AccountVO accountVO ) throws KANException
   {
      try
      {
         getConnection();
         connection.setAutoCommit( false );
         if ( accountVO != null && accountVO.getInitialized().trim().equalsIgnoreCase( AccountVO.FALSE ) )
         {
            int flag = 0;
            // ��������
            accountId = accountVO.getAccountId();
            flag = flag + createClient( accountVO );
            flag = flag + updateClient();
            flag = flag + createStaff();
            flag = flag + createOptions();
            flag = flag + createEmailConfiguration();
            flag = flag + createShareFolderConfiguration();

            flag = flag + createSearch();
            // ���ó�ʼ��״̬
            flag = flag + updateAccount();
            if ( flag == 0 )
            {
               // �ύ����
               connection.commit();

               closeConnection();
               return true;
            }
            else
            {
               // �ع�����
               connection.rollback();
               closeConnection();
               return false;
            }
         }
         else
         {
            // δ�ҵ���Ҫ��ʼ���Ķ��������ѱ���ʼ��
            return false;
         }

      }
      catch ( final Exception e )
      {
         try
         {
            connection.rollback();
            closeConnection();
         }
         catch ( SQLException e1 )
         {
            e1.printStackTrace();
         }
         throw new KANException( e );
      }
   }

   private int createClient( AccountVO accountVO )
   {
      try
      {
         if ( accountVO != null && accountVO.getAccountType().equals( HRM ) )
         {
            String primaryKey = "";
            StringBuffer sb = new StringBuffer();
            sb.append( "INSERT INTO hro_biz_client " );
            sb.append( "(groupId, accountId,corpId, number, nameZH, nameEN, titleZH,titleEN,cityId, address, postcode, mainContact, phone, mobile, fax, email, im1Type, im1, im2Type, im2, website, invoiceDate, paymentTerms, industry, type, size, description, recommendPerson, recommendBranch, recommendPosition, legalEntity, branch, owner, orderBindContract,logoFile,logoFileSize,imageFile, mobileModuleRightIds,sbGenerateCondition, cbGenerateCondition, settlementCondition, sbGenerateConditionSC, cbGenerateConditionSC, settlementConditionSC, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
            sb.append( " values " );
            sb.append( "(null, " + accountVO.getAccountId() + "," + corpId + " ,'','" + accountVO.getEntityName() + "' , '" + accountVO.getNameEN() + "', '"+ accountVO.getEntityName() + "',null," + accountVO.getCityId() + ", '" + accountVO.getAddress() + "','" + accountVO.getPostcode() + "','0', '"+ accountVO.getBizPhone() + "','" + accountVO.getBizMobile() + "','" + accountVO.getFax() + "','" + accountVO.getBizEmail()+ "' , 0, '', 0, '', '', '0', null,0, 0, 0, null, null, null, null, null,'0', '0', null,'',null," + accountVO.getImageFile()+ ",'{1,2,3,4,5,6}', null,   null, null, null, null, null, 1,3,null, null, null, null, null,  'System','" + getDate() + "', 'System', '" + getDate() + "')" );
            final PreparedStatement preparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
            preparedStatement.executeUpdate();
            final ResultSet rs = preparedStatement.getGeneratedKeys();
            if ( rs.next() )
            {
            primaryKey = rs.getString( 1 );
            corpId=primaryKey.substring( 0,1 )+primaryKey.substring(4);
            }
            //�����������
            StringBuffer sr = new StringBuffer();
            sr.append( "INSERT INTO hro_biz_client_order_header" );
            sr.append( "(clientId,accountId,corpId,contractId,entityId,businessTypeId,invoiceAddressId,nameZH,nameEN,startDate,endDate,circleStartDay,circleEndDay,salaryVendorId,payrollDay,salaryMonth,sbMonth,cbMonth,salesType,invoiceType,settlementType,probationMonth,serviceScope,personalSBBurden,applyOTFirst,otLimitByDay,otLimitByMonth,workdayOTItemId,weekendOTItemId,holidayOTItemId,attendanceCheckType,attendanceGenerate,approveType,calendarId,shiftId,sickLeaveSalaryId,taxId,incomeTaxBaseId,incomeTaxRangeHeaderId,attachment,branch,owner,locked,noticeExpire,noticeProbationExpire,noticeRetire,salaryType,divideType,divideTypeIncomplete,excludeDivideItemIds,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate)" );
            sr.append( " values " );
            sr.append( " ('"+primaryKey+"', '"+accountId+"', '"+corpId+"', null, '0', '0', '0', null, null, '" + getDate() + "', '2099-12-30 00:00:00', '1', '31', '0', '5', '1', '1', '1', null, '2', null, '3', '3', '2', '2', '0', '0', '22', '23', '24', '2', '2', '0', '0', '0', '0', '0', '1', '16', null, '0', '0', '2', '{1,2,3,4}', '{1,2,3,4}', '{1,2,3,4}', '1', '3', '2', '{41,42,43,44,45,46,47,48,60}', '', '1', '3', '{}', '', '', '', '',  'System','" + getDate() + "', 'System', '" + getDate() + "') " );
            final PreparedStatement statement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
            statement.executeUpdate();
         }
      }
      catch ( SQLException e )
      {
         System.err.println( "create client is error!" );
         e.printStackTrace();
         return 1;
      }
      return 0;
   }

   private int updateClient()
   {
      try
      {
         StringBuffer sr = new StringBuffer();
         sr.append( "update hro_biz_client " );
         sr.append( "set corpId=" + corpId );
         sr.append( " where  " );
         sr.append( "accountId = " + accountId );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sr.toString() );
         HeaderpreparedStatement.executeUpdate();
      }
      catch ( SQLException e )
      {
         System.err.println( "update client is error!" );
         e.printStackTrace();
         return 1;
      }
      return 0;
   }

   private int createRuleAndRight( final String groupId )
   {
      try
      {
         List< Object > list = accountModuleRelationDao.getAccountModuleRelationVOsByAccountId( accountId );
         if ( list != null && list.size() > 0 )
         {
            for ( Object object : list )
            {
               AccountModuleRelationVO accountModuleRelationVO = ( AccountModuleRelationVO ) object;
               ModuleVO moduleVO = moduleDao.getModuleVOByModuleId( accountModuleRelationVO.getModuleId() );
               //���hro_sec_group_module_right_relation
               if ( moduleVO.getRightIds() != null && moduleVO.getRightIds().length() > 0 )
               {
                  List< String > rightList = KANUtil.jasonArrayToStringList( moduleVO.getRightIds() );
                  for ( String rightId : rightList )
                  {
                     StringBuffer sb = new StringBuffer();
                     sb.append( "insert into HRO_SEC_Group_Module_Right_Relation" );
                     sb.append( "(groupId, moduleId, rightId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
                     sb.append( "VALUES" );
                     sb.append( "(" + groupId + ", " + moduleVO.getModuleId() + ", " + rightId + ", 1, 1, '', '', '', '','', " + userId + ", '" + getDate() + "', " + userId
                           + ", '" + getDate() + "')" );
                     final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
                     HeaderpreparedStatement.executeUpdate();
                  }
               }
         //���hro_sec_group_module_rule_relation
//               if ( moduleVO.getRuleIds() != null && moduleVO.getRuleIds().length() > 0 )
//               {
//                  List< String > ruleList = KANUtil.jasonArrayToStringList( moduleVO.getRuleIds() );
//                  for ( String ruleId : ruleList )
//                  {
//                     StringBuffer sb = new StringBuffer();
//                     sb.append( "insert into HRO_SEC_Group_Module_Rule_Relation" );
//                     sb.append( "(groupId, moduleId, ruleId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
//                     sb.append( "VALUES" );
//                     sb.append( "(" + groupId + ", " + moduleVO.getModuleId() + ", " + ruleId + ", 1, 1, null, null, null, null, null, " + userId + ", '" + getDate() + "', "+ userId + ", '" + getDate() + "')" );
//                     final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
//                     HeaderpreparedStatement.executeUpdate();
//                  }
//               }
            }
         }
         return 0;
      }
      catch ( KANException e )
      {
         System.err.println( "create createRuleAndRight is error!" );
         e.printStackTrace();
         return 1;
      }
      catch ( SQLException e )
      {
         System.err.println( "create createRuleAndRight is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   // ��ʼ��ѡ������
   private int updateAccount()
   {
      try
      {
         StringBuffer sb = new StringBuffer();
         sb.append( "update HRO_SYS_Account " );
         sb.append( "set initialized=1,modifyBy = " + userId + ", modifyDate = '" + getDate() + "'" );
         sb.append( "where  " );
         sb.append( "accountId = " + accountId );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString() );
         HeaderpreparedStatement.executeUpdate();
         return 0;
      }
      catch ( SQLException e )
      {
         System.err.println( "update HRO_SYS_Account is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   //����staff--> user
   private int createStaff()
   {
      try
      {
         //staff Admin
         String primaryKey = "";
         String Administrator_primaryKey = "";
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO hro_sec_staff" );
         sb.append( "(accountId, corpId, staffNo, employeeId, salutation, nameZH, nameEN, birthday, bizPhone, bizExt, personalPhone, bizMobile, personalMobile, otherPhone, fax, bizEmail, personalEmail, certificateType, certificateNumber, maritalStatus, registrationHometown, registrationAddress, personalAddress, personalPostcode, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES" );
         sb.append( "(" + accountId + ",null, null,null, null, 'Admin', 'Admin', '" + getDate()+ "',null, null, null, null,  null, null, null,'kevin.jin@kangroup.com.cn', null, 1,null, 1, null, null, null, null, null,1,1,null, null, null, null, null,  "+ userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         //staff Administrator
         StringBuffer sr = new StringBuffer();
         sr.append( "INSERT INTO hro_sec_staff" );
         sr.append( "(accountId, corpId, staffNo, employeeId, salutation, nameZH, nameEN, birthday, bizPhone, bizExt, personalPhone, bizMobile, personalMobile, otherPhone, fax, bizEmail, personalEmail, certificateType, certificateNumber, maritalStatus, registrationHometown, registrationAddress, personalAddress, personalPostcode, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sr.append( "VALUES" );
         sr.append( "(" + accountId + "," + corpId + ", null,null, null, 'Administrator', 'Administrator', '" + getDate()+ "',null, null, null, null,  null, null, null,'kevin.jin@kangroup.com.cn', null, 1,null, 1, null, null, null, null, null,1,1,null, null, null, null, null,  "+ userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs_A = preparedStatement.getGeneratedKeys();
         if ( rs_A.next() )
         {
            Administrator_primaryKey = rs_A.getString( 1 );
         }
         int flag = createUser( primaryKey, Administrator_primaryKey );
         flag = flag + createPosition( primaryKey, Administrator_primaryKey );
         return flag;
      }
      catch ( SQLException e )
      {
         System.err.println( "create staff is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   // ����user
   private int createUser( final String staffId, final String Administrator_staffId )
   {
      try
      {

         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_User" );
         sb.append( "(accountId,corpId, staffId,   username, password, bindIP, lastLogin,   lastLoginIP,userIds, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ",null," + staffId + ",'Admin', '" + Cryptogram.encodeString( "Kangroup" ) + "','', '" + getDate()+ "','',     null,    1,1,null, null, null, null, null,  'System','" + getDate() + "', 'System', '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();

         StringBuffer sr = new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_User" );
         sr.append( "(accountId,corpId, staffId,   username, password, bindIP, lastLogin,   lastLoginIP,userIds, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "(" + accountId + "," + corpId + "," + Administrator_staffId + ",'Administrator', '" + Cryptogram.encodeString( "Kangroup" ) + "','', '" + getDate()+ "','',     null,    1,1,null, null, null, null, null,  'System','" + getDate() + "', 'System', '" + getDate() + "')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet resultSet = preparedStatement.getGeneratedKeys();
         if ( resultSet.next() )
         {
            userId = resultSet.getString( 1 );
         }
         return 0;
      }
      catch ( SQLException e )
      {
         System.err.println( "create user is error!" );
         e.printStackTrace();
         return 1;
      }
      catch ( KANException e )
      {
         System.err.println( "create user is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   //����Position
   private int createPosition( final String staffId, final String Administrator_staffId )
   {
      try
      {
         String primaryKey = "";
         String Administrator_primaryKey = "";
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Position" );
         sb.append( "(accountId,corpId, locationId, branchId, positionGradeId, titleZH, titleEN, description, skill, note, attachment, parentPositionId, isVacant,isIndependentDisplay ,needPublish, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ",null, 0, 0, 0, 'ϵͳ����Ա HS', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null," + userId + ",'"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }

         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append( "INSERT INTO HRO_SEC_Position" );
         stringBuffer.append( "(accountId,corpId, locationId, branchId, positionGradeId, titleZH, titleEN, description, skill, note, attachment, parentPositionId, isVacant,isIndependentDisplay ,needPublish, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         stringBuffer.append( "VALUES " );
         stringBuffer.append( "(" + accountId + ",'"+corpId+"', 0, 0, 0, 'ϵͳ����Ա IH', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null,"+ userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeadpreparedStatement = connection.prepareStatement( stringBuffer.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeadpreparedStatement.executeUpdate();
         final ResultSet resultSet = HeadpreparedStatement.getGeneratedKeys();
         if ( resultSet.next() )
         {
            Administrator_primaryKey = resultSet.getString( 1 );
         }

         //����HRO_SEC_Position_Staff_Relation
         StringBuffer sr = new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Staff_Relation" );
         sr.append( "(positionId, staffId, staffType, agentStart, agentEnd, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "(" + primaryKey + "," + staffId + ",1,null,null,null,1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();

         StringBuffer ser = new StringBuffer();
         ser.append( "INSERT INTO HRO_SEC_Position_Staff_Relation" );
         ser.append( "(positionId, staffId, staffType, agentStart, agentEnd, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         ser.append( "VALUES " );
         ser.append( "(" + Administrator_primaryKey + "," + Administrator_staffId + ",1,null,null,null,1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId+ ", '" + getDate() + "')" );
         final PreparedStatement statement = connection.prepareStatement( ser.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         statement.executeUpdate();
         int flag = createImport( Administrator_primaryKey );
         flag = flag + createReport( Administrator_primaryKey );
         flag = flag + createGroup( primaryKey, Administrator_primaryKey );
         return flag;
      }
      catch ( SQLException e )
      {
         System.err.println( "create Position or Position_Staff_Relation is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   private int createReport( final String positionId )
   {
      String sqlHeader = "INSERT INTO Hro_Def_Report_Header (accountId,corpId,tableId,nameZH,nameEN,clicks,usePagination,pageSize,loadPages,isSearchFirst,sortColumns,groupColumns,statisticsColumns,exportExcelType,isExportPDF,moduleType,isPublic,positionIds,positionGradeIds,positionGroupIds,branch,owner,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ";
      String sqlDetail = "INSERT INTO Hro_Def_Report_Detail (reportHeaderId,columnId,tableId,nameZH,nameEN,columnWidth,columnWidthType,columnIndex,fontSize,isDecoded,isLinked,linkedAction,linkedTarget,datetimeFormat,accuracy,round,align,sort,statisticsFun,display,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values (?, ";
      String after = "null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";

      SQLScriptDTO sqlRoport_A = new SQLScriptDTO();
      sqlRoport_A.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '69', '��ְԱ������', 'Employee Report', '52', '1', '15', '0', '2', null, null, null, '1', '2', '9', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6954', '69', 'Ա��ID', 'Employee Id', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6901', '69', 'Ա������', 'Staff Name', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6902', '69', '�ƺ�', 'Salutation', '5', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6904', '69', '��������', 'Birthday', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail+ " '6911', '69', '���ѧ��', 'Highest Education', '5', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6933', '69', '����', 'Email', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6903', '69', 'Ա��״̬', 'Employee Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6965', '69', '������', 'Branchs', '', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6923', '69', 'Ա������', 'Employee No', '', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_A );

      SQLScriptDTO sqlRoport_B = new SQLScriptDTO();
      sqlRoport_B.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '71', '�Ͷ���ͬ����', 'Labor Contract Report', '25', '1', '15', '0', '2', null, null, null, '1', '2', '9', '2', '{" + positionId+ "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail+ "'7102', '71', '�Ͷ���ͬ����', 'Contract Name (ZH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7100', '71', 'Ա��ID', 'Staff ID', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7107', '71', 'ǩԼ����', 'Entity', '10', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7113', '71', '��ʼ����', 'Start Date', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + "'7114', '71', '��������', 'End Date', '10', '1', '5', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7120', '71', '�Ű����', 'Shift', '15', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7105', '71', '��ͬ״̬', 'Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7104', '71', '�Ͷ���ͬ���', 'Contract No', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7121', '71', '�Ͷ���ͬID', 'Labor Contract ID', '', '1', '0', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', '0', '1', '', '1', '1'," + after );
      insertDB( sqlRoport_B );

      SQLScriptDTO sqlRoport_C = new SQLScriptDTO();
      sqlRoport_C.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '19', '���ͳ�Ʊ�', 'Leave Report', '16', '1', '15', '0', '2', null, null, null, '1', '2', '10', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1900', '19', 'Ա��ID', 'Staff ID', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail + "'1902', '19', '������', 'Leave Type', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail+ "'1903', '19', '��ʼʱ��', 'Estimate Start Date', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail+ "'1904', '19', '����ʱ��', 'Estimate End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1907', '19', '�����ݼ�Сʱ��', 'Estimate Legal Hours', '5', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1908', '19', '�ݸ�����Сʱ��', 'Estimate Benefit Hours', '5', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1912', '19', '״̬', 'Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_C );

      SQLScriptDTO sqlRoport_D = new SQLScriptDTO();
      sqlRoport_D.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '20', '�Ӱ�ͳ�Ʊ�', 'OT Report', '11', '1', '15', '0', '2', null, null, null, '1', '2', '10', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2000', '20', 'Ա��ID', 'Stafff ID', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail+ "'2003', '20', '��ʼʱ��', 'Estimate Start Date', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + " '2004', '20', '����ʱ��', 'Estimate End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2002', '20', '��Ŀ', 'Item', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + " '2007', '20', '�Ӱ�Сʱ��', 'Estimate Hours', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2010', '20', '״̬', 'Status', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_D );

      SQLScriptDTO sqlRoport_E = new SQLScriptDTO();
      sqlRoport_E.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '3', '��Ӧ�̱���', 'Vendor Report', '24', '1', '15', '0', '2', null, null, null, '1', '2', '7', '2', '{"+ positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'300', '3', '��Ӧ������', 'Vendor Name(ZH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'302', '3', '��Ӧ������', 'Vendor Type', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_E.addSubSQLScript( sqlDetail+ " '304', '3', '��ͬ��ʼ����', 'Contract Start Date', '10', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail+ "'305', '3', '��ͬ��������', 'Contract End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + " '303', '3', '����', 'City', '10', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'312', '3', 'ǩԼ����', 'Legal Entity', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_E.addSubSQLScript( sqlDetail + " '315', '3', '״̬', 'Vendor Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_E );

      SQLScriptDTO sqlRoport_F = new SQLScriptDTO();
      sqlRoport_F.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '76', 'Ա����Ϣ', 'Staff Information', '54', '1', '15', '0', '2', null, null, null, '1', '2', '9', '1', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7654', '76', 'Ա��ID', 'Employee Id', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7601', '76', 'Ա������', 'Staff Name', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7602', '76', '�ƺ�', 'Salutation', '10', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail+ "'7663', '76', '�Ͷ���ͬID', 'Labor Contract ID', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail+ " '7667', '76', '��ͬ״̬', 'Contract Status', '10', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + " '7668', '76', 'н��', 'Salary', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7669', '76', '����', 'Allowance', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7670', '76', '����', 'Subsidy', '10', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_F );

      SQLScriptDTO sqlRoport_G = new SQLScriptDTO();
      sqlRoport_G.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '79', 'Ա�� - �Ͷ���ͬ��Ϣ', 'Staff - Labor Contract Info', '12', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId+ "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ " '7911', '79', '�Ͷ���ͬID', 'Labor Contract ID', '5', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7901', '79', '�Ͷ���ͬ���ƣ����ģ�', 'Contract Name (ZH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7907', '79', '��ͬ��ʼʱ��', 'Start Date', '10', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7908', '79', '��ͬ����ʱ��', 'End Date', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ " '7916', '79', 'Ա�����������ģ�', 'Employee Name( CH)', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7923', '79', '����', 'Bank', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7924', '79', '�����˻�', 'Bank Account', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ "'7934', '79', '�籣����', 'Social Benefit Solution', '10', '1', '8', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7935', '79', '�籣����', 'Base', '10', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7941', '79', '���', 'Annual leave', '5', '1', '10', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7948', '79', '�Ӱ�1.0', 'OT 1.0', '5', '1', '11', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7931', '79', 'н��', 'Salary', '5', '1', '12', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_G );

      SQLScriptDTO sqlRoport_H = new SQLScriptDTO();
      sqlRoport_H.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '80', '�籣 - �籣����', 'SB - SB Report', '25', '1', '15', '0', '2', null, '{8013}', '{\"8013\":\"AVG\"}', '3', '2', '11', '1', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8004', '80', 'Ա�����������ģ�', 'Employee Name(CH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8000', '80', '�Ͷ���ͬID', 'Contract ID', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ " '8010', '80', '��Ӧ�����ƣ����ģ�', 'Vendor Name(CH)', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8012', '80', '�˵��·�', 'Monthly', '5', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + "'8013', '80', '���ϱ���-��˾', 'Endowment - Company', '5', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8014', '80', '���ϱ���-����', 'Endowment - Personal', '5', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8015', '80', 'ҽ�Ʊ���-��˾', 'Medical - Company', '5', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8016', '80', 'ҽ�Ʊ���-����', 'Medical - Personal', '5', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8017', '80', 'ʧҵ����-��˾', 'Unemployment - Company', '5', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ " '8018', '80', 'ʧҵ����-����', 'Unemployment - Personal', '5', '1', '10', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8019', '80', '���˱���-��˾', 'Injury - Company', '5', '1', '11', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8020', '80', '���˱���-����', 'Injury - Personal', '5', '1', '12', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + "'8021', '80', '��������-��˾', 'Maternity - Company', '5', '1', '13', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ "'8022', '80', '��������-����', 'Maternity - Personal', '5', '1', '14', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ "'8025', '80', 'ס��������-��˾', 'Housing - Company', '5', '1', '15', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8026', '80', 'ס��������-����', 'Housing - Personal', '5', '1', '16', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_H );

      SQLScriptDTO sqlRoport_I = new SQLScriptDTO();
      sqlRoport_I.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '81', '�̱� - �̱�����', 'CB - CB Report', '11', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId + "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8100', '81', 'Ա��ID', 'Staff ID', '8', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8102', '81', 'Ա�����������ģ�', 'Staff Name(CH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8113', '81', '�̱�������', 'CB Solution Name(CH)', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8118', '81', '������������', 'Disanzhe', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_I.addSubSQLScript( sqlDetail+ " '8119', '81', '����ҽ�Ʊ���', 'Menzhen Yiliao', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail+ " '8120', '81', '����ҽ�Ʊ���', 'Jiben Yiliao', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8121', '81', '�ۺ�ҽ�Ʊ���', 'Zonghe Yiliao', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8122', '81', '���乤�˱���', 'Buchong Gongshang', '10', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8123', '81', '���ⲹ��', 'Teshu Buchong', '10', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8117', '81', '�̱�״̬', 'CB Status', '12', '1', '10', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_I );

      SQLScriptDTO sqlRoport_J = new SQLScriptDTO();
      sqlRoport_J.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '71', 'Ա�� - �Ͷ���ͬ', '��ͬ������ǩ����', '17', '1', '15', '0', '2', '{\"7115\":\"ASC������\"}', null, null, '1', '2', null, null,  '{" + positionId+ "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_J.addSubSQLScript( sqlDetail + "'7100', '71', 'Ա��ID', 'Employee ID', '3', '2', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail+ "'7102', '71', '�Ͷ���ͬ���ƣ����ģ�', 'Contract Name (ZH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '7114', '71', '��ͬ����ʱ��', 'End Date', '11', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '7115', '71', '��������', 'Branch', '30', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '8137', null, '��ǩ����', 'count', '3', '1', '5', '13', '0', '0', '', '0', '0', '1', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_J );

      SQLScriptDTO sqlRoport_K = new SQLScriptDTO();
      sqlRoport_K.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '3', '��Ӧ�� - ��Ӧ��TS', '', '12', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId + "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_K.addSubSQLScript( sqlDetail + "'300', '3', '��Ӧ�����ƣ����ģ�', 'Vendor Name(ZH)', '10', '1', '0', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_K.addSubSQLScript( sqlDetail+ " '304', '3', '��ͬ��ʼ����', 'Contract Start Date', '10', '1', '0', '13', '2', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_K );

      return 0;
   }

   private int createImport( String positionId )
   {
      String sqlHeader = "INSERT INTO Hro_Def_Import_Header (parentId,accountId,corpId,tableId,nameZH,nameEN,positionIds,positionGradeIds,positionGroupIds,needBatchId,matchConfig,handlerBeanId,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ";
      String sqlDetail = "INSERT INTO Hro_Def_Import_Detail (importHeaderId,columnId,nameZH,nameEN,isPrimaryKey,isForeignKey,columnWidth,columnIndex,fontSize,specialField,tempValue,isDecoded,isIgnoreDefaultValidate,datetimeFormat,accuracy,round,align,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ?,";
      String after = "null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";

      //Ա������
      SQLScriptDTO sqlImport_A = new SQLScriptDTO();
      sqlImport_A.setSqlScript( sqlHeader + "  '0', '" + accountId + "', " + corpId + ", '69', 'Ա������', 'Employee Import', '{" + positionId + "}', '', '', '2', '0', 'employeeImportHandler', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6954', 'Ա��ϵͳ���', 'Employee ID', '1', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6901', '����', 'Employee Name (ZH)', '0', '0', '14', '2', '13', '0', '����', '2', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6900', 'Ա��Ӣ����', 'Employee Name (EN)', '0', '0', '14', '0', '13', '0', 'Zhangsan', '2', '0', '0', '0', '0', '1', '', '1', '2', "+ after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6902', '�ƺ� ', 'Salutation', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6905', '����״��', 'Marital Status', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6904', '��������', 'Birthday', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6907', '������', 'Place of Birth', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6914', '��������', 'Residency Type', '0', '0', '14', '9', '13', '0', '', '1', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6908', '������ַ', 'Residency Address', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6911', '���ѧ��', 'Highest Education', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6933', '����', 'Email', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6947', '��������', 'Branch', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2',    " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6948', '������', 'Owner', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6903', 'Ա��״̬', 'Status', '0', '0', '14', '18', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6931', '�绰', 'Phone', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6932', '�ֻ�����', 'Mobile', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6926', '֤������', 'Certificate Type', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6927', '֤������', 'Certificate Number', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '7671', 'ְλ����', 'Position Name', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6953', '����', '', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6935', '��ϵ�绰', 'Phone (2nd)', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6941', '��ʱͨѶ - 2', 'IM Type - 2', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6942', '��ʱͨѶ���� - 2', 'IM - 2', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6943', '��ʱͨѶ - 3', 'IM Type - 3', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6944', '��ʱͨѶ���� - 3', 'IM - 3', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6945', '��ʱͨѶ - 4', 'IM Type - 4', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6946', '��ʱͨѶ���� - 4', 'IM - 4', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6913', '������ַ', 'Record Address', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6909', '��ϵ��ַ', 'Personal Address', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6923', 'Ա������', 'Employee No', '0', '0', '14', '11', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6923', 'Ա������', 'Employee No', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_A );

      //���ʵ���
      SQLScriptDTO sqlImport_B = new SQLScriptDTO();
      sqlImport_B.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '74', '���ʵ���', 'Salary Import', '{" + positionId + "}', '', '', '1', '0', 'salaryExcueHandler', '', '1', '2'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7408', '����Id', 'orderId', '0', '0', '14', '8', '13', '0', '500000001', '2', '0', '0', '0', '0', '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7409', '�Ͷ���ͬId', 'contractId', '0', '0', '14', '9', '13', '0', '200000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7410', 'Ա��Id', 'Employee ID', '0', '0', '14', '10', '13', '0', '100000001', '2', '0', '0', '0', '0', '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7411', 'Ա�����������ģ�', 'employeeNameZH', null, null, '14', '11', '13', null, '����', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7412', 'Ա��������Ӣ�ģ�', 'employeeNameEN', null, null, '14', '12', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7413', 'н�꿪ʼ����', 'startDate', null, null, '14', '13', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7414', 'н���������', 'endDate', null, null, '14', '14', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7415', '֤������', 'certificateType', null, null, '14', '15', '13', null, '', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail+ " '7416', '֤������', 'certificateNumber', null, null, '14', '16', '13', null, '2.11E+17', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail+ " '7418', '�������ƣ����ģ�', 'bankNameZH', null, null, '14', '18', '13', null, '�й������Ϻ��з���', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7419', '�������ƣ�Ӣ�ģ�', 'bankNameEN', null, null, '14', '19', '13', null, '', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7420', '�����˻�', 'bankAccount', null, null, '14', '20', '13', null, '6.23E+18', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7427', 'н���·�', 'monthly', null, null, '14', '27', '13', null, '2014/01', '2', null, '', null, null, '1', null, '1', '1'," + after );
      String primaryKey_B = insertDB( sqlImport_B );

      //�Ͷ���ͬ
      SQLScriptDTO sqlImport_C = new SQLScriptDTO();
      sqlImport_C.setSqlScript( sqlHeader + " '" + primaryKey_B + "', '" + accountId + "', " + corpId + ", '75', '�Ͷ���ͬ', 'Salary Import - Detail', '{" + positionId+ "}', '', '', '0', '0', 'salaryExcueHandler', '', '1', '2'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '��������', 'Base Salary', '0', '0', '14', '0', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '��ٿۿ�', 'Leave Deduct', '0', '0', '14', '1', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '�����ۿ�', 'Other Withhold', '0', '0', '14', '2', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '��λ����', 'Position Allowance', '0', '0', '14', '3', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '���½���', 'High Temperature Allowance', '0', '0', '14', '4', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', 'פ�����', 'Travel Allowance', '0', '0', '14', '5', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '��������', 'Other Allowance', '0', '0', '14', '6', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', 'ҹ�ಹ��', 'Night Shift Subsidy', '0', '0', '14', '7', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '��ͨ����', 'Transportation Subsidy', '0', '0', '14', '8', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '�òͲ���', 'Meat Subsidy', '0', '0', '14', '9', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '��������', 'Other Subsidy', '0', '0', '14', '10', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '������', 'Welfare', '0', '0', '14', '11', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '��Ч', 'Performance', '0', '0', '14', '12', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '����', 'Bonus', '0', '0', '14', '13', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', 'Ӷ��', 'Commission', '0', '0', '14', '14', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '���ս�', 'Annual Bonus', '0', '0', '14', '15', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '�Ӱ� 1.0', 'OT 1.0', '0', '0', '14', '16', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '�Ӱ� 1.5', 'OT 1.5', '0', '0', '14', '17', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '�Ӱ� 2.0', 'OT 2.0', '0', '0', '14', '18', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '�Ӱ� 3.0', 'OT 3.0', '0', '0', '14', '19', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '�Ӱ໻��', 'OT Shift', '0', '0', '14', '20', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '����', 'Reimbursement', '0', '0', '14', '21', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '���� - ���ʷ�', 'Reimbursement - Entertainment', '0', '0', '14', '22', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '���� - �Ӱ�', 'Reimbursement - OT', '0', '0', '14', '23', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', 'ס��������-��˾', 'Housing(Company)', '0', '0', '14', '24', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', 'ס��������-����', 'Housing(Personal)', '0', '0', '14', '25', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '���ϱ���-��˾', 'Endowment(Company)', '0', '0', '14', '26', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '���ϱ���-����', 'Endowment(Personal)', '0', '0', '14', '27', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', 'ҽ�Ʊ���-��˾', 'Medical(Company)', '0', '0', '14', '28', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', 'ʧҵ����-����', 'Unemployment(Personal)', '0', '0', '14', '29', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '���˱���-��˾', 'Injury(Company)', '0', '0', '14', '30', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '���˱���-����', 'Injury(Personal)', '0', '0', '14', '31', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '��������-��˾', 'Maternity(Company)', '0', '0', '14', '32', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '��������-����', 'Maternity(Personal)', '0', '0', '14', '33', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '�м��˱��Ͻ�-��˾', 'Disable(Company)', '0', '0', '14', '34', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '�м��˱��Ͻ�-����', 'Disable(Personal)', '0', '0', '14', '35', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', 'ȡů��-��˾', 'Warming(Company)', '0', '0', '14', '36', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', 'ȡů��-����', 'Warming(Personal)', '0', '0', '14', '37', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '�����-��˾', 'Allocation(Company)', '0', '0', '14', '38', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '�����-����', 'Allocation(Personal)', '0', '0', '14', '39', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '���������-��˾', 'Archive(Company)', '0', '0', '14', '40', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '���������-����', 'Archive(Personal)', '0', '0', '14', '41', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7519', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao(Company)', '0', '0', '14', '42', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao(Personal)', '0', '0', '14', '43', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7519', '����ҽ�Ʊ���-��˾', 'Buchong Yiliao(Company)', '0', '0', '14', '44', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '����ҽ�Ʊ���-����', 'Buchong Yiliao(Personal)', '0', '0', '14', '45', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao(Company)', '0', '0', '14', '46', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '��ҽ�Ʊ���-����', 'Dabing Yiliao(Personal)', '0', '0', '14', '47', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7519', '���ũ��ҽ��-��˾', 'Yidi Nongcun(Company)', '0', '0', '14', '48', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '���ũ��ҽ��-����', 'Yidi Nongcun(Personal)', '0', '0', '14', '49', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7519', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding(Company)', '0', '0', '14', '50', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7520', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding(Personal)', '0', '0', '14', '51', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', 'ҽ�Ʊ���-����', 'Medical(Personal)', '0', '0', '14', '28', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', 'ʧҵ����-��˾', 'Unemployment(Company)', '0', '0', '14', '29', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      insertDB( sqlImport_C );

      //�Ͷ���ͬ
      SQLScriptDTO sqlImport_D = new SQLScriptDTO();
      sqlImport_D.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '71', '�Ͷ���ͬ', 'Employee Contract', '{" + positionId + "}', '', '', '1', '0', 'employeeContractImportHandler', '', '1', '2'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7100', 'Ա��ID', 'Employee ID', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7122', 'Ա�����������ģ�', 'Employee Name (ZH)', null, null, '14', '3', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7106', '����ID', 'Order ID', null, null, '14', '5', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7121', '�Ͷ���ͬϵͳ���', 'Labor Contract ID', '1', '0', '14', '10', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "  '7102', '�Ͷ���ͬ���ƣ����ģ�', 'Contract Name (ZH)', null, null, '14', '12', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7103', '�Ͷ���ͬ���ƣ�Ӣ�ģ�', 'Contract Name (EN)', null, null, '14', '13', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7104', '�Ͷ���ͬ���', 'Contract No', null, null, '14', '14', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7109', '�Ͷ���ͬģ��', 'Template ', null, null, '14', '16', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7113', '��ͬ��ʼʱ��', 'Start Date', null, null, '14', '17', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7114', '��ͬ����ʱ��', 'End Date', null, null, '14', '18', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7105', '��ͬ״̬', 'Status', null, null, '14', '27', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7107', '����ʵ��', 'Entity ID', null, null, '14', '32', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7108', 'ҵ������', 'Business Type ', null, null, '14', '33', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7119', '����', 'Calendar', null, null, '14', '34', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7120', '�Ű�', 'Shift', null, null, '14', '35', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7124', '���ڷ�ʽ', 'Attendance Check Type', null, null, '14', '36', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'8579', '���֤����', 'certificateNumber', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      String primaryKey_D = insertDB( sqlImport_D );
       
      //�Ͷ���ͬ�ӱ� - �籣����
      SQLScriptDTO sqlImport_K = new SQLScriptDTO();
      sqlImport_K.setSqlScript( sqlHeader + "" + primaryKey_D + ", '" + accountId + "', " + corpId + ", '37', '�Ͷ���ͬ�ӱ� - �籣����', '�Ͷ���ͬ�ӱ� - �籣����', '{" + positionId+ "}', '', '', '1', '0', '0', '', '1', '2'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '�籣����A', 'Solution Benefit A', '0', '0', '14', '1', '13', '0', '', '1', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '�ӱ�����A', 'Start Date A', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '�籣����A', 'SB Base A', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '�籣����B', 'Solution Benefit B', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '�ӱ�����B', 'Start Date B', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '�籣����B', 'SB Base B', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3701', '�籣����C', 'Solution Benefit C', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3706', '�ӱ�����C', 'Start Date C', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'8585', '�籣����C', 'SB Action C', '0', '0', '14', '9', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '�籣����D', 'Solution Benefit D', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3706', '�ӱ�����D', 'Start Date D', '0', '0', '14', '11', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '�籣����D', 'SB Base D', '0', '0', '14', '12', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '�籣����E', 'Solution Benefit E', '0', '0', '14', '13', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '�ӱ�����E', 'Start Date E', '0', '0', '14', '14', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '�籣����E', 'SB Base E', '0', '0', '14', '15', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_K );
      
      //�Ͷ���ͬ�ӱ� - н�귽��
      SQLScriptDTO sqlImport_H = new SQLScriptDTO();
      sqlImport_H.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '40', '�Ͷ���ͬ�ӱ� - н�귽��', '�Ͷ���ͬ�ӱ� - н�귽��', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_H.addSubSQLScript( sqlDetail + "'4004', '��������', '��������', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_H );

      //�Ͷ���ͬ�ӱ� - �̱�����
      SQLScriptDTO sqlImport_I = new SQLScriptDTO();
      sqlImport_I.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '39', '�Ͷ���ͬ�ӱ� - �̱�����', '�Ͷ���ͬ�ӱ� - �̱�����', '{" + positionId+ "}', null, null, '0', '0', '0', '', '1', '1'," + after );
      sqlImport_I.addSubSQLScript( sqlDetail + "'3901', '�̱�����', 'Commercial Benefit ID', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_I.addSubSQLScript( sqlDetail + "'3905', '�̱����� - �ӱ�����', 'Start Date', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_I );

      //�Ͷ���ͬ�ӱ� - �ݼٷ���
      SQLScriptDTO sqlImport_J = new SQLScriptDTO();
      sqlImport_J.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '42', '�Ͷ���ͬ�ӱ� - �ݼٷ���', '�Ͷ���ͬ�ӱ� - �ݼٷ���', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_J.addSubSQLScript( sqlDetail + " '4205', '���', '���', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_J.addSubSQLScript( sqlDetail + " '4205', '���� - ȫн', 'Base', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_J );

     

      //�Ͷ���ͬ�ӱ� - �Ӱ෽��
      SQLScriptDTO sqlImport_L = new SQLScriptDTO();
      sqlImport_L.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '41', '�Ͷ���ͬ�ӱ� - �Ӱ෽��', '�Ͷ���ͬ�ӱ� - �Ӱ෽��', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + " '4104', '�Ӱ� 1.0', 'Base', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + "'4104', '�Ӱ� 2.0', 'Base', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + "'4104', '�Ӱ໻��', 'Base', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_L );

      //�Ͷ���ͬ�籣����
      SQLScriptDTO sqlImport_E = new SQLScriptDTO();
      sqlImport_E.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '37', '�Ͷ���ͬ�籣����', '', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3700', '�Ͷ���ͬID', 'Contract ID', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3701', '�籣����', 'Solution Benefit', null, null, '14', '2', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3705', '��Ӧ��', 'Vendor', null, null, '14', '3', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3702', '��Ӧ�̷���', 'Vendor Service', null, null, '14', '4', '13', null, null, '2', null, null, null, null, '1', null, '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3706', '�ӱ�����', 'Start Date', null, null, '14', '5', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3707', '�˱�����', 'End Date', null, null, '14', '6', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3709', '��Ҫ����ҽ����', 'Need Medical Card', null, null, '14', '7', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3712', 'ҽ�����ʺ�', 'Medical Number', null, null, '14', '8', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail+ " '3711', '��Ҫ�����籣��', 'Need Social Benefit Card', null, null, '14', '9', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail+ "'3713', '�籣���ʺ�', 'Social Benefit Number', null, null, '14', '10', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',  " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3714', '�������ʺ�', 'fundNumber', null, null, '14', '11', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "  '3704', '����', 'Description', null, null, '14', '12', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "  '3703', '״̬', 'Status', null, null, '14', '13', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      insertDB( sqlImport_E );

      //��Ӧ���籣���� - ����
      SQLScriptDTO sqlImport_F = new SQLScriptDTO();
      sqlImport_F.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '53', '��Ӧ���籣���� - ����', ' Vendor SB Important ', '{" + positionId + "}', '', '', '1', '0', 'vendorSBImportHandlerImpl', '', '1', '2'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5309', '����ID', 'Order ID', null, null, '14', '17', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5310', '��ͬId', 'Contract ID', '0', '0', '14', '19', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5311', 'Ա��ID', 'Employee ID', '0', '0', '14', '21', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5313', 'Ա�����������ģ�', 'employeeName(ZH)', '0', '0', '14', '25', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5314', 'Ա��������Ӣ�ģ�', 'Employee Name(EN)', '0', '0', '14', '27', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5318', '�Ͷ���ͬ��ʼʱ��', 'Contract Start Date', '0', '0', '14', '35', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5319', '�Ͷ���ͬ����ʱ��', 'Contract End Date', '0', '0', '14', '37', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5334', 'ҽ�����ʺ�', 'Medical Number', null, null, '14', '67', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',  "+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5335', '�籣���ʺ�', 'SB Number', null, null, '14', '69', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5336', '�������ʺ�', 'Fund Number', null, null, '14', '71', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5342', 'Ա��״̬', 'Employ Status', '0', '0', '14', '83', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5344', '�ӱ����ڣ�������£�', 'Start Date', null, null, '14', '87', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5345', '�˱�����', 'End Date', null, null, '14', '89', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5348', '�˵��·�', 'Monthly', null, null, '14', '95', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5303', '����ʵ��ID', 'Entity ID', '0', '0', '14', '5', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5304', 'ҵ������ID', 'BusinessType ID', '0', '0', '14', '6', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5315', '��Ա�籣����ID', 'EmployeeSB ID', '0', '0', '14', '8', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      String primaryKey_F = insertDB( sqlImport_F );

      //��Ӧ���籣���� - ����ӱ���ϸ
      SQLScriptDTO sqlImport_G = new SQLScriptDTO();
      sqlImport_G.setSqlScript( sqlHeader + "  " + primaryKey_F + ", '" + accountId + "', " + corpId + ", '54', '��Ӧ���籣���� - ����ӱ���ϸ', 'Vendor SB Detail Important', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', '���ϱ���-����', 'Endowment - Company', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '���ϱ���-��˾', 'Endowment - Personal', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', 'ҽ�Ʊ���-��˾', 'Medical - Company', '0', '0', '14', '3', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5413', 'ҽ�Ʊ���-����', 'Medical - Personal', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', 'ʧҵ����-����', 'Unemployment - Personal', '0', '0', '14', '6', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5412', 'ʧҵ����-��˾', 'Unemployment - Company', '0', '0', '14', '5', '13', '0', '', '2', null, '0', '0', '0', '1', '', '2', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '���˱���-��˾', ' Injury - Company ', '0', '0', '14', '7', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "  '5413', '���˱���-����', 'Injury - Personal', '0', '0', '14', '8', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '��������-��˾', 'Maternity - Company', '0', '0', '14', '9', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5413', '��������-����', 'Maternity - Personal', '0', '0', '14', '10', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5414', 'ס��������-��˾', 'Housing - Company', '0', '0', '14', '11', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', 'ס��������-����', 'Housing - Personal', '0', '0', '14', '12', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      insertDB( sqlImport_G );

      //������ٵ���
      SQLScriptDTO sqlImport_M = new SQLScriptDTO();
      sqlImport_M.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '19', '������ٵ���', '', '{" + positionId+ "}', '', '', '1', '0', 'leaveExcueHandler', '', '1', '2'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1900', 'Ա��ID', 'Employee ID', '0', '0', '14', '1', '13', '0', '100000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1918', 'Ա�����������ģ�', 'Employee Name(ZH)', '0', '0', '14', '2', '13', '0', '����', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_M.addSubSQLScript( sqlDetail + " '1924', '�Ͷ���ͬ', 'Contract', '0', '0', '14', '7', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1902', '������', 'Item', null, null, '14', '8', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + " '1903', '��ʼʱ��', 'Estimate Start Date', '0', '0', '14', '9', '13', '0', '2014-01-01  12:12:12', '2', null, '1', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail+ "'1904', '����ʱ��', 'Estimate End Date', '0', '0', '14', '10', '13', '0', '2014-01-01  12:12:12', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_M );

      //���ڼӰർ��
      SQLScriptDTO sqlImport_N = new SQLScriptDTO();
      sqlImport_N.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '20', '���ڼӰർ��', '', '{" + positionId+ "}', '', '', '1', '0', 'otExcueHandler', '', '1', '2'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + " '2000', 'Ա��ID', 'Employee', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + " '2002', '��Ŀ', 'Item', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2011', 'Ա������', 'Employee Name', '0', '0', '14', '2', '13', '0', '����', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2022', '�Ͷ���ͬ', 'Contract', '0', '0', '14', '3', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2003', '��ʼʱ��', 'Estimate Start Date', '0', '0', '14', '8', '13', '0', '2014-01-01  12:12:12', '2', null, '1', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail+ " '2004', '����ʱ��', 'Estimate End Date', '0', '0', '14', '9', '13', '0', '2014-01-01  12:12:12', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_N );

      //���ڽ�������
      SQLScriptDTO sqlImport_O = new SQLScriptDTO();
      sqlImport_O.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '44', '���ڽ�������', 'Timesheet Import', '{" + positionId+ "}', '', '', '1', '0', 'timesheetAllowance', '', '1', '2'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + " '4420', 'Ա���������ģ�', 'Employee NameZH', '0', '0', '14', '2', '13', '0', '����', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + " '4402', '�Ͷ���ͬID', 'Contract ID', '0', '0', '14', '4', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_O.addSubSQLScript( sqlDetail + "'4405', '�·�', 'Time Sheet Month', '0', '0', '14', '3', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + "'4401', 'Ա��ID', 'Employee ID', '0', '0', '14', '1', '13', '0', '100000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      String primaryKey_O = insertDB( sqlImport_O );

      //���ڵ���-����
      SQLScriptDTO sqlImport_P = new SQLScriptDTO();
      sqlImport_P.setSqlScript( sqlHeader + " " + primaryKey_O + ", '" + accountId + "', " + corpId + ", '85', '���ڵ���-����', 'TimeSheet Import Allowance', '{" + positionId+ "}', '', '', '1', '0', '0', '', '1', '2'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', 'פ�����', 'Travel Allowance', '0', '0', '14', '10', '13', '0', '0.00', '2', '1', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail+ " '8503', '���½���', 'High Temperature Allowance', '0', '0', '14', '11', '13', '0', '0.00', '2', '2', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '��λ����', 'Position Allowance', '0', '0', '14', '12', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', 'ҹ�ಹ��', 'Night Shift Subsidy', '0', '0', '14', '13', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '��������', 'Other Allowance', '0', '0', '14', '14', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '��ͨ����', 'Transportation Subsidy', '0', '0', '14', '15', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '�òͲ���', 'Meat Subsidy', '0', '0', '14', '16', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '��������', 'Other Subsidy', '0', '0', '14', '17', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '������', 'Welfare', '0', '0', '14', '18', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '���ս�', 'Annual Bonus', '0', '0', '14', '19', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '��Ч', 'Performance', '0', '0', '14', '20', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '����', 'Bonus', '0', '0', '14', '21', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', 'Ӷ��', 'Commission', '0', '0', '14', '21', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '����', 'Reimbursement', '0', '0', '14', '22', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail+ "'8503', '���� - ���ʷ�', 'Reimbursement - Entertainment', '0', '0', '14', '23', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '���� - �Ӱ�', 'Reimbursement - OT', '0', '0', '14', '24', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '����', 'Adjust', '0', '0', '14', '25', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503',  'ȫ�ڽ�', 'Full Bonus', '0', '0', '14', '26', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503',  '��Ч����', '', '0', '0', '14', '27', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      insertDB( sqlImport_P );
      
      //���ڻ��ܵ���
      SQLScriptDTO sqlImport_Q = new SQLScriptDTO();
      sqlImport_Q.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '113', '���ڻ��ܵ���', '', '{" + positionId+ "}', '', '', '1', '0', 'attendanceHeaderImportHandler', '', '1', '2'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13305', '�Ͷ���ͬID', 'Contract ID', '0', '0', '14', '5', '13', '0', '', '2', '1', '0', '0', '0', '1', '*��ɫ��Ԫ��Ϊ������', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13307', '���ڱ��·�', 'Monthly', '0', '0', '14', '7', '13', '0', '2015/01', '2', '1', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13309', '����Сʱ�����������ںϼƣ�', 'Total Work Hours', '0', '0', '14', '9', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13311', '�����������������ںϼƣ�', 'Total Work Days', '0', '0', '14', '11', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13313', 'ȫ��Сʱ�����������ںϼƣ�', 'Total Full Hours', '0', '0', '14', '13', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13315', 'ȫ���������������ںϼƣ�', 'Total Full Days', '0', '0', '14', '17', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13320', 'Ա������', 'Employee Name', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '������֤Ա�������ͺ�ͬ��Ӧ��Ա�������Ƿ�ƥ��', '1', '1'," + after );
      String primaryKey_Q = insertDB( sqlImport_Q );
      
      
      //���ڻ��ܴӱ���
      SQLScriptDTO sqlImport_U = new SQLScriptDTO();
      sqlImport_U.setSqlScript( sqlHeader + " " + primaryKey_Q + ", '" + accountId + "', " + corpId + ", '115', '���ڻ��ܴӱ���', '', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_U.addSubSQLScript( sqlDetail + " '11505', '��ĿID', 'Item ID', NULL, NULL, '14', '5', '13', NULL, NULL, '2', NULL, NULL, NULL, NULL, '1', '', '1', '1'," + after );
      sqlImport_U.addSubSQLScript( sqlDetail + " '11507', '����Сʱ��', 'Hours', NULL, NULL, '14', '9', '13', NULL, NULL, '2', NULL, NULL, NULL, NULL, '1', '', '1', '1', " + after );
      insertDB( sqlImport_U );
      return 0;
   }

   private int createGroup( final String positionId, final String Administrator_positionId )
   {
      try
      {
         //����Group
         String primaryKey = "";
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Group" );
         sb.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ",null, 'ϵͳ������','System Management Team', 2,'Easy Staffing',1, 1, null, null, null, null, null," + userId + ",'" + getDate() + "', " + userId+ ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }

         //����Group
         String administrator_primaryKey = "";
         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append( "INSERT INTO HRO_SEC_Group" );
         stringBuffer.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         stringBuffer.append( "VALUES " );
         stringBuffer.append( "(" + accountId + "," + corpId + ", 'ϵͳ������','System Management Team', 1,'Easy Staffing',1, 1, null, null, null, null, null," + userId + ",'"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeadpreparedStatement = connection.prepareStatement( stringBuffer.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeadpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet resultSet = HeadpreparedStatement.getGeneratedKeys();
         if ( resultSet.next() )
         {
            administrator_primaryKey = resultSet.getString( 1 );
         }
         //����PositionGroupRelation
         StringBuffer sr = new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         sr.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "(" + positionId + "," + primaryKey + ",1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();

         //����PositionGroupRelation
         StringBuffer stringBuff = new StringBuffer();
         stringBuff.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         stringBuff.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         stringBuff.append( "VALUES " );
         stringBuff.append( "(" + Administrator_positionId + "," + administrator_primaryKey + ",1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId + ", '"+ getDate() + "')" );
         final PreparedStatement statement = connection.prepareStatement( stringBuff.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         statement.executeUpdate();
         //group ����
         int flag = createRuleAndRight( primaryKey );
         flag = flag + createRuleAndRight( administrator_primaryKey );
         return flag;
      }
      catch ( SQLException e )
      {
         System.err.println( "create Group or Position_Group_Relation is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   // ��ʼ��ѡ������
   private int createOptions()
   {
      try
      {
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_MGT_Options" );
         sb.append( "(accountId, branchPrefer,positionPrefer,language, useBrowserLanguage, dateFormat, timeFormat, accuracy, round, pageStyle, smsGetway,logoFile,logoFileSize,mobileModuleRightIds, orderBindContract, sbGenerateCondition, cbGenerateCondition, settlementCondition, sbGenerateConditionSC, cbGenerateConditionSC, settlementConditionSC, independenceTax,createBy, createDate, modifyBy,  modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ",0,0,1, 1, 1, 1,3, 1, 2,1, null, null, '{1,2,3,4,5,6}', 2, 1, 1, 1,1, 1,  1,1, " + userId + ", '" + getDate() + "', " + userId + ", '"+ getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return 0;
      }
      catch ( SQLException e )
      {
         System.err.println( "create HRO_MGT_Options is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   // ��ʼ���ʼ�����
   private int createEmailConfiguration()
   {
      try
      {
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_MGT_Email_Configuration" );
         sb.append( "(accountId, showName, mailType, sentAs, accountName, smtpHost, smtpPort, username, password, smtpAuthType, smtpSecurityType, pop3Host, pop3Port, deleted, status, remark1, remark2, createBy, createDate, modifyBy, modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "("+ accountId+ ", 'HRIS Notice', 2, 'hris@i-click.com', null,'smtp.office365.com', '587', 'hris@i-click.com', 'E@sY6@y2015',1,  0,'outlook.office365.com','993', 1, 1, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return 0;
      }
      catch ( SQLException e )
      {
         System.err.println( "create HRO_MGT_Email_Configuration is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   //��ʼ��Share Folder����
   private int createShareFolderConfiguration()
   {
      try
      {
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_MGT_Share_Folder_Configuration" );
         sb.append( "(accountId, host, port, username, password, directory, deleted, status, remark1, remark2, createBy, createDate, modifyBy, modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ", '10.11.70.152', null, null, null, '/Samba/KANHRO/', 1, 1,null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate()+ "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return 0;
      }
      catch ( SQLException e )
      {
         System.err.println( "create HRO_MGT_Share_Folder_Configuration is error!" );
         e.printStackTrace();
         return 1;
      }
   }

   //��ʼ��Search����
   public int createSearch()
   {
      // ��ʼ��SQL��
      //      List< SQLScriptDTO > sqlScripts = new ArrayList< SQLScriptDTO >();
      //list_Header
      try
      {
         String listHeaderBefore = "insert into Hro_Def_List_Header( accountId, corpId,  parentId, useJavaObject, javaObjectName,tableId, searchId, nameZH, nameEN, pageSize, loadPages, usePagination, isSearchFirst, exportExcel, description, deleted, status, remark1,  remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate) values ( ";
         String listDetailBefore = "insert into Hro_Def_List_Detail(listHeaderId, columnId, propertyName, nameZH ,nameEN, columnWidth, columnWidthType, columnIndex, fontSize, isDecoded, isLinked, linkedAction, linkedTarget, appendContent, properties, datetimeFormat, accuracy, align, round, sort, display, description, deleted, status, remark1, remark2, remark3, remark4,  remark5, createBy, createDate, modifyBy, modifyDate ) values( ";
         String after = "null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";

         String searchHeaderBefore = "INSERT INTO hro_def_search_header (accountId,corpId,tableId,useJavaObject,javaObjectName,nameZH,nameEN,isSearchFirst,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate)  values ( ";
         String searchDetailBefore = "INSERT INTO hro_def_search_detail (searchHeaderId,columnId,propertyName,nameZH,nameEN,columnIndex,fontSize,useThinking,thinkingAction,contentType,content,`range`,display,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ?,";
         //�û�
         SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
         sqlScriptDTO.setSqlScript( searchHeaderBefore+""+ accountId + ", "+ corpId + ", '1', '2', '', '�û�', 'Staff', null, '', '1', '1', "+after );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '105', null, 'Ա�����', 'Staff Code', '1', '13', '2', '', '0', '', '100_200', '1', ' ', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '101', null, 'Ա�����������ģ�', 'Staff Name (Chinese)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '102', null, 'Ա��������Ӣ�ģ�', 'Staff Name (English)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '104', null, 'Ա��״̬', 'Staff Status', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         //�û���������
         String userPrimarykey = insertDB( sqlScriptDTO );
         //�û��б�
         SQLScriptDTO sqlScript = new SQLScriptDTO();
         sqlScript.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", 0, 2, '', 1, " + userPrimarykey + ", '�û��б�', 'Staff List', 10, 0, 1, 2, 1, '', 1, 1,"+ after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '105', null, 'Ա�����', 'Staff Code','8', '1', '2', '13', '2', '2', '', '1', '', '', '0', '0', '1', '0', '1', '0', '1', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '101', null, 'Ա�����������ģ�', 'Staff Name (Chiness)','10', 1, 3, 13, 2,2, '',2, '', '', '', null, 1, null,1, 1, '', 1, 1,"+ after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '118', null, '������ַ�����ᣩ', 'Hometown','18', 1, 7, 13, 2,2, '',0, '', '', '', null, '1', null,1, 1, 1, 1, 1," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '104', null, '״̬', 'Status','5', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '116', null, '֤������', 'ID NO', '', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '7525', null, '��������', 'working period', '', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '124', null, '�޸���', 'Modify By','8', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '125', null, '�޸�ʱ��', 'Decode Modify Date','12', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '126', null, 'Ա��ID', 'staffId','3', '1', '1', '13', '2', '1', 'staffAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript );

         //��Ӧ��
         SQLScriptDTO sqlScriptDTO_A = new SQLScriptDTO();
         sqlScriptDTO_A.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '3', '2', '', '��Ӧ��', 'Vendor', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '300', null, '��Ӧ�����ƣ����ģ�', 'Vendor Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '301', null, '��Ӧ�����ƣ�Ӣ�ģ�', 'Vendor Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '302', null, '��Ӧ������', 'Vendor Type', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '316', null, 'ʡ�� - ����', 'Province', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '312', null, '����ʵ��', 'Legal Entity', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,   '313', null, '��������', 'Branch', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '314', null, '������', 'Owner', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '315', null, '״̬', 'Stauts', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String vendorPrimarykey = insertDB( sqlScriptDTO_A );
         //��Ӧ���б�
         SQLScriptDTO sqlScript_A = new SQLScriptDTO();
         sqlScript_A.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ",  0, 2,'',3, " + vendorPrimarykey + ", '��Ӧ���б�', 'Vendor List',10, 0, 1, 2, 1, '', 1, 1,"+ after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'317', null, '��Ӧ��ID', 'Vendor ID', '5', '1', '1', '13', '2', '1', 'vendorAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'300', null, '��Ӧ�����ƣ����ģ�', 'Vendor Name (ZH)', '15', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'304', null, '��ͬ��ʼ����', 'contract start date', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '305', null, '��ͬ��������', 'Contract end date', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '303', null, 'ʡ�� - ����', 'City', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'302', null, '����', 'Vendor Type', '5', '1', '6', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '324', null, '��ϵ��', 'Contacts', '6', '1', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '323', null, '��ϵ�˵绰', 'Phone', '8', '1', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '326', null, '��ϵ������', 'Email', '8', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '312', null, '����ʵ��', 'Entity', '10', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'315', null, '״̬', 'Status', '8', '1', '11', '13', '1', '2', null, '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'318', null, '�޸���', 'Modify By', '6', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '319', null, '�޸�����', 'Modify Date', '8', '1', '13', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         insertDB( sqlScript_A );

         //��Ӧ����ϵ��
         SQLScriptDTO sqlScriptDTO_B = new SQLScriptDTO();
         sqlScriptDTO_B.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '4', '2', '', '��Ӧ����ϵ��', 'Vendor Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '402', null, '��ϵ�����������ģ�', 'Contact Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'403', null, '��ϵ��������Ӣ�ģ�', 'Contact Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'404', null, '�ƺ�', 'Salutation', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'423', null, '״̬', 'Status', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'407', null, 'ʡ�� - ����', 'CIty', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'424', null, '��������', 'Branch', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'425', null, '������', 'Owner', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String VendorContactKey = insertDB( sqlScriptDTO_B );
         //��Ӧ����ϵ���б�
         SQLScriptDTO sqlScript_B = new SQLScriptDTO();
         sqlScript_B.setSqlScript( listHeaderBefore + "" + accountId + ",  " + corpId + ", '0', '2', '', '4', " + VendorContactKey+ ", '��Ӧ����ϵ���б�', 'Vendor Contact List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '400', null, '��ϵ��ID', 'Contract ID', '5', '1', '1', '13', '2', '1', 'vendorContactAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'402', null, '��ϵ������', 'Contact Name', '10', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '408', null, '��˾�绰', 'Company Number', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'414', null, '����', 'Email', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '404', null, '�ƺ�', 'Salutation', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'406', null, '����', 'Title', '8', '1', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '405', null, 'ְλ', 'Department', '8', '1', '7', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'401', null, '��Ӧ��ID', 'Vendor ID', '8', '1', '8', '13', '2', '1', 'vendorAction.do?proc=to_objectModify&id=${0}', '1', null, 'encodedVendorId', '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'426', null, '��Ӧ������', 'Vendor Name', '15', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '423', null, '״̬', 'Status', '8', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'430', null, '�޸���', 'Modify By', '8', '1', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'431', null, '�޸�����', 'Modify Date', '9', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         insertDB( sqlScript_B );

         //����
         SQLScriptDTO sqlScriptDTO_C = new SQLScriptDTO();
         sqlScriptDTO_C.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '5', '2', null, '����', 'Client Group', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '500', null, '���ű��', 'Number', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'501', null, '�������ƣ����ģ�', 'Client Group NameZH', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '502', null, '�������ƣ�Ӣ�ģ�', 'Client Group NameEN', '13', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '504', null, '״̬', 'Status', '15', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientGroupKey = insertDB( sqlScriptDTO_C );
         SQLScriptDTO sqlScript_C = new SQLScriptDTO();
         //�����б�
         sqlScript_C.setSqlScript( listHeaderBefore + "" + accountId + ",  " + corpId + ", '0', '2', '', '5', " + clientGroupKey+ ", '�����б�', 'Client Group List', '10', '0', '1', '2', '1', '�ͻ����� = > �����б�', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'500', '', '���ű��', 'number', '10', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'501', '', '�������ƣ����ģ�', 'Client Group NameZH', '28', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'502', '', '�������ƣ�Ӣ�ģ�', 'Client Group NameEN', '28', '1', '12', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'504', '', '״̬', 'Status', '6', '1', '17', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '505', '', '�޸���', 'Modify By', '8', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '506', '', '�޸�ʱ��', '�޸�ʱ��', '12', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '507', '', '����ID', 'Client Group ID', '8', '1', '9', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_C );

         //�ͻ�
         SQLScriptDTO sqlScriptDTO_D = new SQLScriptDTO();
         sqlScriptDTO_D.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '6', '2', null, '�ͻ�', 'Client', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'609', null, '�������', 'number', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'610', null, '�ͻ����ƣ����ģ�', 'ClientNameZH', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '611', null, '�ͻ����ƣ�Ӣ�ģ�', 'ClientNameZH', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '641', null, '��������', 'GroupName', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'632', null, '�ͻ�ID', 'Client ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'605', null, '��ҵ', 'Industry', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '606', null, '��ģ', 'Size', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '600', null, '����ʵ��', 'Entity', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '602', null, '��������', 'Branch', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '601', null, '������', 'Owner', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'612', null, '״̬', 'Status', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'633', null, '���ű��', 'Group Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientKey = insertDB( sqlScriptDTO_D );
         //�ͻ��б�
         SQLScriptDTO sqlScript_D = new SQLScriptDTO();
         sqlScript_D.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '6', " + ClientKey+ ", '�ͻ��б�', 'Client List', '10', '0', '1', '2', '1', '�ͻ����� = > �ͻ��б�', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'609', null, '�������', 'number', '9', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'610', '', '�ͻ����ƣ����ģ�', 'nameCN', '25', '1', '5', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1', "+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'611', '', '�ͻ����ƣ�Ӣ�ģ�', 'nameEN', '25', '1', '7', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '612', '', '״̬', 'status', '7', '1', '28', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '639', '', '�޸���', 'Modify By', '7', '1', '26', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '640', '', '�޸�ʱ��', 'Modify Date', '11', '1', '27', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '633', '', '���ű��', 'Group Number', '9', '1', '8', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedGroupId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'632', '', '�ͻ�ID', 'Client ID', '7', '1', '1', '13', '2', '1', 'clientAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_D );

         //�ͻ���ϵ��
         SQLScriptDTO sqlScriptDTO_E = new SQLScriptDTO();
         sqlScriptDTO_E.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '13', '2', null, '�ͻ���ϵ��', 'Client Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1305', null, '��ϵ�����������ģ�', 'nameZH', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1306', null, '��ϵ��������Ӣ�ģ�', 'nameEN', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1323', null, '״̬', 'status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1302', null, '�ͻ�ID', 'Client ID', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1308', null, '����', 'Department', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1307', null, 'ְλ', 'Title', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1333', null, '�ͻ����', 'Client Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1334', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '10', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1335', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientContactKey = insertDB( sqlScriptDTO_E );
         //�ͻ���ϵ���б�
         SQLScriptDTO sqlScript_E = new SQLScriptDTO();
         sqlScript_E.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '13', " + clientContactKey+ ", '�ͻ���ϵ���б�', 'Client Contact List', '10', '0', '1', '2', '1', '�ͻ����� = > �ͻ���ϵ���б�', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1305', '', '��ϵ�����������ģ�', 'nameZH', '11', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1306', '', '��ϵ��������Ӣ�ģ�', 'nameEN', '11', '1', '3', '13', '2', '2', 'clientContactAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1304', '', '�ƺ�', 'Salutation', '4', '1', '4', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1323', '', '״̬', 'status', '4', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1307', '', 'ְλ', 'title', '11', '1', '8', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1336', '', '�޸���', 'Modify By', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1337', '', '�޸�ʱ��', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1338', '', '�ͻ�����', 'Client Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '6428', '', '��ϵ��ID', 'Client Contact ID', '6', '1', '1', '13', '2', '1', 'clientContactAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1302', '', '�ͻ�ID', 'Client ID', '7', '1', '5', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1308', '', '����', 'Department', '10', '1', '7', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_E );

         //�˵���ַ
         SQLScriptDTO sqlScriptDTO_F = new SQLScriptDTO();
         sqlScriptDTO_F.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '14', '2', null, '�˵���ַ', 'Client Invoice', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1401', null, '��Ʊ̨ͷ', 'Title', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1402', null, '���������ռ��ˣ�', 'NameZH', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1403', null, 'Ӣ�������ռ��ˣ�', 'NameEN', '5', '0', '2', '', '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1417', null, '״̬', 'Status', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1430', null, '�ͻ�ID', 'Client ID', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1433', null, '�ͻ����', 'Client Number', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1407', null, '��ַ', 'Address', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1434', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '13', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1435', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '14', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientInvoicekey = insertDB( sqlScriptDTO_F );
         //�˵���ַ�б�
         SQLScriptDTO sqlScript_F = new SQLScriptDTO();
         sqlScript_F.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '14', " + ClientInvoicekey+ ", '�˵���ַ�б�', 'Client Invoice List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1401', '', '��Ʊ̨ͷ', 'Title', '18', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1402', '', '�ռ������������ģ�', 'Name (ZH)', '11', '1', '3', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1403', '', '�ռ���������Ӣ�ģ�', 'Name (EN)', '11', '1', '5', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1417', '', '״̬', 'status', '6', '1', '16', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1436', '', '�޸���', 'Modify By', '7', '1', '14', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1437', '', '�޸�ʱ��', 'Modify Date', '11', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1438', '', '�˵���ַID', 'Client Inovice ID', '7', '1', '1', '13', '2', '1', 'clientInvoiceAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1439', '', '�ͻ�����', 'Client Name', '22', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1430', '', '�ͻ�ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_F );

         //��Ա 
         SQLScriptDTO sqlScriptDTO_G = new SQLScriptDTO();
         sqlScriptDTO_G.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '17', '2', null, '��Ա', 'Employee', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1760', null, '��ԱID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1725', null, '��Ա���', 'Employee NO', '2', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1701', null, '��Ա���������ģ�', 'Employee Name (ZH)', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1700', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1714', null, '�������', 'Record No', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1728', null, '֤������', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1729', null, '֤������', 'Certificate Number', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1705', null, '����״��', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1773', null, '�������£���ʼ��', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1772', null, '�������£�������', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1771', null, '�״βμӹ������ڣ���ʼ��', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1770', null, '�״βμӹ������ڣ�������', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1706', null, '����', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1716', null, '��������', 'Residency Type', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1713', null, '���ѧ��', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1703', null, '״̬', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String Employeekey = insertDB( sqlScriptDTO_G );
         //��Ա�б�
         SQLScriptDTO sqlScript_G = new SQLScriptDTO();
         sqlScript_G.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '17', " + Employeekey+ ", '��Ա�б�', 'Employee List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1725', '', '��Ա���', 'Employee No', '8', '1', '2', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1701', '', '���������ģ�', 'Employee Name (ZH)', '12', '1', '3', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1700', '', '������Ӣ�ģ�', 'Employee Name (EN)', '12', '1', '4', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1728', '', '֤������', 'Certificate Type', '7', '1', '5', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1729', '', '֤������', 'Certificate Number', '15', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1703', '', '״̬', 'Status', '6', '1', '12', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1780', '', '�޸���', 'Modify By', '8', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1781', '', '�޸�ʱ��', 'Modify Date', '11', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1760', '', '��ԱID', 'Employee ID', '7', '1', '1', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1', '', '�Ͷ���ͬ', 'Labor Contract', '7', '1', '8', '13', '2', '2', '', '0', '${0}', 'numberOfLaborContract', '', null, '1', null, '2', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1', '', '����Э��', 'Dispatch Contract', '7', '1', '9', '13', '2', '2', '', '0', '${0}', 'numberOfServiceContract', '', null, '1', null, '2', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_G );

         //�����ͬ
         SQLScriptDTO sqlScriptDTO_H = new SQLScriptDTO();
         sqlScriptDTO_H.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '18', '2', null, '�����ͬ', 'Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1800', null, '�ͻ�ID', 'Client ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1802', null, '����ʵ��', 'entityId', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1805', null, '��ͬ���', 'Contract Number', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1810', null, '��ͬ���ƣ����ģ�', 'Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1811', null, '��ͬ���ƣ�Ӣ�ģ�', 'Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1818', null, '״̬', 'Status', '11', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1820', null, '��ͬID', 'Client Contact ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1821', null, '�������', 'Client Number', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1822', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1823', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1803', null, 'ҵ������', 'Business Type', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String contractKey = insertDB( sqlScriptDTO_H );
         //�����ͬ�б�
         SQLScriptDTO sqlScript_H = new SQLScriptDTO();
         sqlScript_H.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '18', " + contractKey+ ", '�����ͬ�б�', 'Contract List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1824', '', '�޸���', 'Modify By', '6', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1825', '', '�޸�ʱ��', 'Modify Date', '12', '1', '17', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1804', '', '��ͬģ��', 'Template', '12', '1', '15', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1805', '', '��ͬ���', 'Contract Number', '10', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1809', '', '��ͬ����', 'Contract Name', '18', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1818', '', '״̬', 'Status', '6', '1', '19', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1820', '', '��ͬID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1800', '', '�ͻ�ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '0', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1801', '', '�ͻ�����', 'Client Name', '22', '1', '13', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         insertDB( sqlScript_H );

         //�Ͷ���ͬ
         SQLScriptDTO sqlScriptDTO_I = new SQLScriptDTO();
         sqlScriptDTO_I.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '24', '2', null, '�Ͷ���ͬ', 'Labor Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2403', null, '�Ͷ���ͬ���ƣ�Ӣ�ģ�', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2402', null, '�Ͷ���ͬ���ƣ����ģ�', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2405', null, '��ͬ״̬', 'Employee Status', '12', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2400', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2442', null, '�Ͷ���ͬID', 'Labor Contract ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2443', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2444', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2451', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2452', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2453', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2454', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String primaryKey_I = insertDB( sqlScriptDTO_I );
         //�Ͷ���ͬ�б�
         SQLScriptDTO sqlScript_I = new SQLScriptDTO();
         sqlScript_I.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', null, '24', " + primaryKey_I+ ", '�Ͷ���ͬ�б�', 'Labor Contract List', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2401', null, '��Ա����', 'Employee Name', '14', '1', '4', '13', '0', '2', '', '0', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2402', null, '��ͬ���ƣ����ģ�', 'Name ZH ', '15', '1', '5', '13', '2', '2', '', '1', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2403', null, '��ͬ���ƣ�Ӣ�ģ�', 'Name EN ', '15', '1', '6', '13', '0', '2', '', '1', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2414', null, '��ͬ��������', 'contractTypeName ', '10', '1', '7', '13', '1', '0', '', '0', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2404', null, '��ͬ���', 'Contract No', '10', '1', '6', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2405', null, '��ͬ״̬', 'Status', '3', '1', '12', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2442', null, '�Ͷ���ͬID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedContractId', null, null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2400', null, '��ԱID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', null, null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2443', null, '���������ģ�', 'Employee Name (ZH)', '8', '1', '3', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ " ?,'2444', null, '������Ӣ�ģ�', 'Employee Name (EN)', '8', '1', '4', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2445', null, '�޸���', 'Modify By', '7', '1', '10', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2446', null, '�޸�ʱ��', 'Modify Time', '11', '1', '11', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2450', null, '��ͬ����', 'Contract Name', '22', '1', '7', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2418', null, '��ʼ����', 'Start Date', '7', '1', '8', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2419', null, '��������', 'End Date', '7', '1', '9', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '1', null, '����', 'Handle', '3', '1', '13', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2440', null, 'ʵ�ʽ�������', '', '5', '1', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_I );

         //����
         SQLScriptDTO sqlScriptDTO_J = new SQLScriptDTO();
         sqlScriptDTO_J.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '28', '2', null, '����', 'Order', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2828', null, '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2806', null, '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2808', null, 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2836', null, '����ID', 'Order ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2802', null, '�ͻ�ID', 'Client ID', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2804', null, '��ͬID', 'Contract ID', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2812', null, '������ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2813', null, '������������', 'Order End Date', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2826', null, '��������', 'Branch', '31', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2827', null, '������', 'Owner', '33', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2844', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2845', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '12', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2853', null, '�������', 'Client Number', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String orderKey = insertDB( sqlScriptDTO_J );
         //�����б�
         SQLScriptDTO sqlScript_J = new SQLScriptDTO();
         sqlScript_J.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', null, '28', " + orderKey+ ", '�����б�', 'Order List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2843', '', '�ͻ�����', 'Client Name', '20', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2848', '', '�޸���', 'Modify By', '6', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2849', '', '�޸�ʱ��', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2828', '', '״̬', 'Status', '6', '1', '11', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2802', '', '�ͻ�ID', 'Client ID', '7', '1', '2', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2836', '', '����ID', 'Order ID', '7', '1', '1', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2804', '', '�����ͬID', 'Contract ID', '7', '1', '4', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedContractId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2812', '', '��ʼ����', 'Start Date', '7', '1', '5', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2813', '', '��������', 'End Date', '7', '1', '6', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2826', '', '��������', 'Branch', '10', '1', '7', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2827', '', '������', 'owner', '12', '1', '8', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_J );

         //����Э��
         SQLScriptDTO sqlScriptDTO_K = new SQLScriptDTO();
         sqlScriptDTO_K.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '43', '2', null, '����Э��', 'Service Contract', null, '', '1', '1', '', '', '', '', '', "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4305', null, 'Э��״̬', 'Contract Service Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4311', null, '�ͻ�ID', 'Client ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4303', null, '����Э�����ƣ�Ӣ�ģ�', 'Service Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4302', null, '����Э�����ƣ����ģ�', 'Service Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4300', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4340', null, '����Э��ID', 'Service Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4341', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4342', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4356', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4357', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4358', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4359', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4361', null, '�������', 'Client Number', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String serviceContractKey = insertDB( sqlScriptDTO_K );
         //����Э���б�
         SQLScriptDTO sqlScript_K = new SQLScriptDTO();
         sqlScript_K.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', null, '43', " + serviceContractKey+ ", '����Э���б�', 'Service Contract List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4304', '', '���', 'Contract No', '8', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4305', '', 'Э��״̬', 'Contract Status', '3', '1', '12', '13', '1', '2', '', '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4340', '', '����Э��ID', 'Contract Service ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&comeFrom=2', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4300', '', '��ԱID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4341', '', '���������ģ�', 'Employee Name ZH', '8', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4342', '', '������Ӣ�ģ�', 'Employee Name EN', '8', '1', '4', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'1', '', '����', 'Handle', '3', '1', '13', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4351', '', '�޸���', 'Modify BY', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4352', '', '�޸�ʱ��', 'Modify By', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4355', '', '����Э������', 'Contract Service Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4311', '', '�ͻ�ID', 'Client ID', '7', '1', '8', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1', "+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4306', '', '����ID', 'Order ID', '7', '1', '7', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedOrderId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4346', '', '��Ӷ״̬', 'EmployStatus', '6', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '8583', null, '���֤����', 'ID', '', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4318', null, '��ʼʱ��', 'Start date', '', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1', " + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4319', null, '��������', 'End Date', '', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4322', null, '������', 'be', '', '1', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         insertDB( sqlScript_K );

         //���ʵ�
         SQLScriptDTO sqlScriptDTO_L = new SQLScriptDTO();
         sqlScriptDTO_L.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '���ʵ�', 'Payslip', null, '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String payslip = insertDB( sqlScriptDTO_L );
         //���ʵ��б�
         SQLScriptDTO sqlScript_L = new SQLScriptDTO();
         sqlScript_L.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', " + payslip+ ", '���ʵ��б�', 'Payslip List', '10', '0', '1', '2', '1', 'payslip', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'employeeId', 'Ա��ID', 'Staff Id', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore + "?, null, 'employeeNameZH', 'Ա�����������ģ�', 'Staff Name( CH )', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'employeeNameEN', 'Ա��������Ӣ�ģ�', 'Staff Name( EN )', '100', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'certificateNumber', '֤������', 'ID Number', '150', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'bankName', '��������', 'Bank Name', '150', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'bankAccount', '�����˻�', 'Bank Account', '150', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'monthly', 'н���·�', 'Pay Monthly', '60', '2', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'orderId', '����ID', 'Order ID', '80', '2', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '�Ͷ���ͬID', 'Contract ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore + "?,null, 'beforeTaxSalary', 'Ӧ������', 'Accrued Wages', '150', '2', '95', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'taxAmountPersonal', '��˰', 'Personal Income Tax', '150', '2', '97', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'afterTaxSalary', 'ʵ������', 'Real Wages', '100', '2', '98', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'status', '״̬', 'Status', '60', '2', '99', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'entity', '����ʵ��', 'Entity', '100', '2', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'taxAgentAmountPersonal', '����˰����', 'Tax Agent Amount Personal', '150', '2', '96', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         //���ʵ��б�����
         String payslipKey = insertDB( sqlScript_L );
         //���ʵ��б� - ��ϸ
         SQLScriptDTO sqlScript_LD = new SQLScriptDTO();
         sqlScript_LD.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", " + payslipKey+ ", '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', '0', '���ʵ��б� - ��ϸ', 'Payslip List - Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_1', '��������', 'Base Salary', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_2', '���ʵ���', 'Salary Adjustment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_3', '��ٿۿ�', 'Leave Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_4', '�����ۿ�', 'Other Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_5', '��˰', 'Income Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_6', '��λ����', 'Position Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_7', '���½���', 'High Temperature Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_8', 'פ�����', 'Travel Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_9', '��������', 'Other Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_10', 'ҹ�ಹ��', 'Night Shift Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_11', '��ͨ����', 'Transportation Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_12', '�òͲ���', 'Meat Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_13', '��������', 'Other Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_14', '������', 'Welfare', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_15', '��Ч', 'Performance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_16', '����', 'Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_17', 'Ӷ��', 'Commission', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_18', '���ս�', 'Annual Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_21', '�Ӱ� 1.0', 'OT 1.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_22', '�Ӱ� 1.5', 'OT 1.5', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_23', '�Ӱ� 2.0', 'OT 2.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_24', '�Ӱ� 3.0', 'T 3.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', " + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_25', '�Ӱ໻��', 'OT Shift', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_26', '�����Ӱ�', 'OT Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_31', '����', 'Reimbursement', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_32', '���� - ���ʷ�', 'Reimbursement - Entertainment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_33', '���� - �Ӱ�', 'Reimbursement - OT', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_41', '���', 'Annual Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_42', '���� - ȫн', 'Sick Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_43', '���� - ��ȫн��70%��', 'Sick Leave - 70%', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_44', '�¼�', 'Absence', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_45', '����', 'Maternity Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_46', '�����', 'Nursing Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_47', 'ɥ��', 'Bereft Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_c', '���ϱ���-��˾', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_62_c', 'ҽ�Ʊ���-��˾', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_c', 'ʧҵ����-��˾', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_c', '���˱���-��˾', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_65_c', '��������-��˾', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_66_c', '�м��˱��Ͻ�-��˾', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_c', 'ס��������-��˾', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_68_c', 'ȡů��-��˾', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_69_c', '�����-��˾', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_c', '���������-��˾', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_71_c', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_c', '����ҽ�Ʊ���-��˾', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_73_c', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_74_c', '���ũ��ҽ��-��˾', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_c', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_p', '���ϱ���-����', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_62_p', 'ҽ�Ʊ���-����', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_p', 'ʧҵ����-����', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_p', '���˱���-����', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_65_p', '��������-����', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_66_p', '�м��˱��Ͻ�-����', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_p', 'ס��������-����', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_68_p', 'ȡů��-����', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_69_p', '�����-����', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_p', '���������-����', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_71_p', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_p', '����ҽ�Ʊ���-����', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_73_p', '��ҽ�Ʊ���-����', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_p', '���ũ��ҽ��-����', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_p', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_130', '����ѣ���˰��', 'Service Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_131', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Prepay', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_132', '����ѣ���˰�� - ΥԼ��', 'Service Fee (I-Tax) - Cancelation', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_133', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_134', '��ѵ�ѣ���˰��', 'Training Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_135', '��Ƹ�ѣ���˰��', 'Recruitment Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_136', '����ѣ���˰��', 'Agent Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_141', 'Ӫҵ˰', 'Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_142', '��ְ���', 'Onboard Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_143', '������', 'Annual Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_144', '�籣���쿨��', 'SB Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_145', 'ҽ�����쿨��', 'Medical Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_146', '���ʿ��쿨��', 'Salary Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_147', '�����', 'Labour Union Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_148', '���ɽ�', 'Overdue Fine', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_149', '������', 'Poundage', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_150', '������', 'Paper Work Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_151', '������', 'Each Other Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_161', '�籣�����', 'Social Benefit Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_162', '�����������', '3rd Part Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_181', '�������', 'Labour Union Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_182', '���˻���', 'Injury Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_183', '��������', 'Maternity Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_184', '���ϻ���', 'Disable Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_185', '�油����', 'Replace Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_186', '���ջ���', 'VC Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_187', 'ȫ�����', 'Full Liability Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_188', '��������', 'Other Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_197', '��Ч����', 'Performance allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_19', '�Ӱ����', 'OT Adjustment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_LD );

         //��˰
         SQLScriptDTO sqlScriptDTO_M = new SQLScriptDTO();
         sqlScriptDTO_M.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '��˰�¶ȱ���', 'Income Tax ', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String incomeTax = insertDB( sqlScriptDTO_M );
         //��˰�걨�б�
         SQLScriptDTO sqlScript_M = new SQLScriptDTO();
         sqlScript_M.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '0', " + incomeTax+ ", '��˰�걨�б�', 'Income Tax List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'employeeId', '��ԱID', 'EmployeeId', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '15', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '15', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'orderId', '����ID', 'Order', '10', '1', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'certificateNumber', '֤������', 'Certificate Number', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'monthly', '��˰�����·�', 'Tax Monthy', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'taxAmountPersonal', '��˰���', 'Tax Amount', '10', '1', '7', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'cityId', '�걨����', 'City', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'status', '״̬', 'Status', '10', '1', '9', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_M );

         //�������
         SQLScriptDTO sqlScriptDTO_N = new SQLScriptDTO();
         sqlScriptDTO_N.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '78', '2', '', '�������', 'Order', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7852', '', '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'7806', '', '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
       //  sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7808', '', 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7836', '', '�������ID', 'Order ID', '1', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7812', '', '����ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7813', '', '�����������', 'Order End Date', '19', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7826', '', '��������', 'Branch', '31', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7827', '', '������', 'Owner', '33', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String primaryKey_N = insertDB( sqlScriptDTO_N );
         SQLScriptDTO sqlScript_N = new SQLScriptDTO();
         sqlScript_N.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', '', '78', " + primaryKey_N+ ", '��������б�', 'Order List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7852', '', '״̬', 'Status', '6', '1', '11', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7836', null, '�������ID', 'Order ID', '9', '1', '1', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7812', null, '��ʼ����', 'Start Date', '12', '1', '5', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7813', null, '��������', 'End Date', '12', '1', '6', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7826', null, '��������', 'Branch', '12', '1', '7', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '2'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7827', '', '������', 'owner', '14', '1', '8', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '2', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7848', '', '�޸���', 'Modify By', '8', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7849', '', '�޸�ʱ��', 'Modify Date', '13', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7831', null, '�����������', 'Associated Information', '12', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7806', null, '���÷���ʵ��', 'Entity Name', '16', '1', '3', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7808', null, '����ҵ������', 'Business Type', '12', '1', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', " + after );
         insertDB( sqlScript_N );

         //�籣��
         SQLScriptDTO sqlScriptDTO_O = new SQLScriptDTO();
         sqlScriptDTO_O.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '�籣��', 'SB Bill', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate() + "', " + userId + ", '" + getDate() + "')" );         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', 'Ա�����������ģ�', 'Employee Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', 'Ա��������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '�Ͷ���ͬID', 'Labor Contract ID', '4', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '�ͻ�ID', 'Client ID', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  null, 'employeeId', 'Ա��ID', 'Staff ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '7', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '8', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'vendorNameZH', '��Ӧ������', 'Vendor Name', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String sbKey = insertDB( sqlScriptDTO_O );
         //�籣���б�
         SQLScriptDTO sqlScript_O = new SQLScriptDTO();
         sqlScript_O.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', " + sbKey+ ", '�籣��', 'Social Benefit ', '10', '0', '1', '2', '1', '', '1', '1', " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'clientId', '�ͻ�ID', 'Client ID', '100', '2', '1', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'clientNo', '�������', 'Client Number', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'clientNameZH', '�ͻ�����', 'Client Name', '100', '2', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1', " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, null, 'employeeId', '��ԱID', 'Staff ID', '100', '2', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,null, 'employeeNameZH', '��Ա����', 'Employee Name', '100', '2', '5', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '����Э��ID', 'Labor Contract ID', '100', '2', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'contractStatus', '����Э��״̬', 'Staff Status', '100', '2', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'monthly', '�걨�·�', 'Monthly', '100', '2', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'employeeSBNameZH', '�籣��������', 'Social Benefit Name', '100', '2', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, null, 'sbStatus', '�籣״̬', 'Social Benefit Status', '100', '2', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'vendorNameZH', '��Ӧ������', 'Vendor Name', '100', '2', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'amountCompany', '�ϼƣ���˾��', 'Amount Company', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'amountPersonal', '�ϼƣ����ˣ�', 'Amount Personal', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'status', '״̬', 'Status', '100', '2', '99', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,null, 'entityId', '����ʵ��', 'Entity Name', '100', '2', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'accountMonthly', '�����·�', 'AccountMonthly', 100, 2, 5, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'description', '��ע', 'Remark', 100, 2, 99, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'certificateNumber', '֤������', 'Certificate Number', 100, 2, 2, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'sbNumber', '�籣���ʺ�', 'Social Benefit Card', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'fundNumber', '�������ʺ�', 'Accumulation Fund Card', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'basePersonal', '����', 'Base Personal', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, " + after );
         //�籣������
         String sbDetailKey = insertDB( sqlScript_O );
         //�籣�� - ��ϸ
         SQLScriptDTO sqlScript_OD = new SQLScriptDTO();
         sqlScript_OD.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", " + sbDetailKey+ ", '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', '0', '�籣�� - ��ϸ', 'Social Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_c', '���ϱ���-��˾', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_62_c', 'ҽ�Ʊ���-��˾', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_c', 'ʧҵ����-��˾', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_c', '���˱���-��˾', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_65_c', '��������-��˾', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_66_c', '�м��˱��Ͻ�-��˾', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_67_c', 'ס��������-��˾', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_68_c', 'ȡů��-��˾', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_69_c', '�����-��˾', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_70_c', '���������-��˾', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_71_c', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_72_c', '����ҽ�Ʊ���-��˾', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_73_c', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_c', '���ũ��ҽ��-��˾', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_c', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_61_p', '���ϱ���-����', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_62_p', 'ҽ�Ʊ���-����', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_p', 'ʧҵ����-����', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_64_p', '���˱���-����', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_65_p', '��������-����', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_66_p', '�м��˱��Ͻ�-����', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_p', 'ס��������-����', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_68_p', 'ȡů��-����', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_69_p', '�����-����', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_p', '���������-����', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_71_p', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_p', '����ҽ�Ʊ���-����', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_73_p', '��ҽ�Ʊ���-����', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_p', '���ũ��ҽ��-����', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_p', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         insertDB( sqlScript_OD );

         //Ա�� - ������Ϣ
         SQLScriptDTO sqlScriptDTO_P = new SQLScriptDTO();
         sqlScriptDTO_P.setSqlScript( searchHeaderBefore + "" + accountId + ", " + corpId + ", '69', '2', null, 'Ա�� - ������Ϣ', 'Employee', null, 'In House', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6954', null, 'Ա��ID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6923', null, 'Ա�����', 'Employee NO', '2', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6901', null, 'Ա�����������ģ�', 'Employee Name (ZH)', '1', '0', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6900', null, 'Ա��������Ӣ�ģ�', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6912', null, '�������', 'Record No', '5', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6926', null, '֤������', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6927', null, '֤������', 'Certificate Number', '7', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6905', null, '����״��', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6904', null, '�������£���ʼ��', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6904', null, '�������£�������', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '2', '1', " + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6916', null, '�״βμӹ������ڣ���ʼ��', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6916', null, '�״βμӹ������ڣ�������', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6906', null, '����', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6914', null, '��������', 'Residency Type', '23', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6914', null, '���ѧ��', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6903', null, '״̬', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6903', null, 'Ա��״̬', 'Employee Status', '25', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6911', null, '���ѧ��', 'Highest Education', '24', '13', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6962', null, '�������£���ʼ��', 'Birthday Start', '18', '13', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6961', null, '�������£�������', 'Birthday End', '19', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6960', null, '�״βμӹ������ڣ���ʼ��', 'Start Work Date Start', '20', '13', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6959', null, '�״βμӹ������ڣ�������', 'start Work Date End', '21', '13', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         String sbDetailKey_P = insertDB( sqlScriptDTO_P );
         //Ա���б�
         SQLScriptDTO sqlScript_P = new SQLScriptDTO();
         sqlScript_P.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '69', " + sbDetailKey_P+ ", 'Ա���б�', 'Employee List', '10', '0', '1', '2', '1', 'In House', '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6923', null, '�������', 'Filing  No', '6', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6901', null, 'Ա��������', 'Staff Name (ZH)', '8', '1', '3', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '2', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6900', null, 'Ա��Ӣ����', 'Staff Name (EN)', '9', '1', '4', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6926', null, '֤������', 'Certificate Type', '7', '1', '5', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6927', null, '֤������', 'Certificate Number', '15', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6903', null, 'Ա��״̬', 'Staff Status', '7', '1', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6954', null, 'Ա��ϵͳ���', 'Employee ID', '8', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '2', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ " ?,'6955', null, '�޸���', 'Modify By', '8', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6956', null, '�޸�ʱ��', 'Modify Date', '11', '1', '21', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6905', null, '����״��', 'Marital status', '6', '1', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6904', null, '��������', 'Birthday', '7', '1', '11', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6902', null, '�ƺ�', 'Salutation', '4', '1', '9', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6907', null, '������', 'Birthday Place', '10', '1', '12', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6914', null, '��������', 'Residency Type', '6', '1', '13', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6908', null, '������ַ', 'Residency Address', '20', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6911', null, '���ѧ��', null, '50', '2', '12', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6933', null, '����', 'Email', '16', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6947', null, '��������', 'Branch', '5', '1', '18', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6948', null, '������', 'Owner', '5', '1', '19', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6911', null, '���ѧ��', 'Highest Education', '6', '1', '15', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6931', null, '�绰', 'Telephone number', '9', '1', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6932', null, '�ֻ�����', 'Cellphone  number', '8', '1', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'7671', null, 'ְλ����', '', '12', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6954', null, 'Ա��ϵͳ���', '', '80', '2', '1', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6923', null, 'Ա������', '', '80', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6901', null, 'Ա��������', null, '100', '2', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6900', null, 'Ա��Ӣ����', '', '100', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6902', null, '�ƺ�', null, '50', '2', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + " ?,'6931', null, '�绰', '', '80', '2', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6932', null, '�ֻ�����', null, '80', '2', '7', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6933', null, '����', null, '80', '2', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6904', null, '��������', null, '50', '2', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6905', null, '����״��', null, '50', '2', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6914', null, '��������', null, '80', '2', '11', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6916', null, '�״βμӹ�������', null, '50', '2', '13', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6963', null, '��˾����', null, '50', '2', '14', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6927', null, '���֤����', null, '100', '2', '15', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6903', null, 'Ա��״̬', null, '80', '2', '16', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6965', null, '������', null, '100', '2', '17', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6967', null, 'ְλ��', null, '100', '2', '18', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6966', null, '�û���', null, '100', '2', '19', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6912', null, '�������', '', '', '1', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_P );

         //�Ͷ���ͬ - �ڲ�
         SQLScriptDTO sqlScriptDTO_Q = new SQLScriptDTO();
         sqlScriptDTO_Q.setSqlScript( searchHeaderBefore + "" + accountId + ", " + corpId+ ", '71', '2', null, '�Ͷ���ͬ - �ڲ�', 'Labor Contract - In House', null, 'In House', '1', '1', " + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7103', null, '�Ͷ���ͬ���ƣ�Ӣ�ģ�', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7102', null, '�Ͷ���ͬ���ƣ����ģ�', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1',"+ after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7105', null, '��ͬ״̬', 'Employee Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7100', null, 'Ա��ID', 'Employee ID', '9', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7106', null, '����ID', 'Order ID', '4', '13', '1', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7121', null, '�Ͷ���ͬID', 'Labor Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', " + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7122', null, 'Ա�����������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7123', null, 'Ա��������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7131', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7132', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7133', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7134', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7126',   NULL, '��Ӷ״̬',    'Employee Status', '11',  '13',' 2', NULL, '0', NULL, NULL, '1', NULL, '1', '1', " + after );
         String sbDetailKey_Q = insertDB( sqlScriptDTO_Q );
         //�Ͷ���ͬ�б� - �ڲ�
         SQLScriptDTO sqlScript_Q = new SQLScriptDTO();
         sqlScript_Q.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', null, '71', " + sbDetailKey_Q + ", '�Ͷ���ͬ�б� - �ڲ�', 'Labor Contract - In House', '10', '0', '1', '2', '1', 'In House', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7104', null, '��ͬ���', 'contractNo', '10', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', null, '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7105', null, '��ͬ״̬', 'Status', '4', '1', '13', '13', '1', '2', '', '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7121', null, '�Ͷ���ͬID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&comeFrom=2', '1', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7100', null, 'Ա��ID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7122', null, '���������ģ�', 'Employee Name ZH', '8', '1', '3', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?, '7123', null, '������Ӣ�ģ�', 'Employee Name EN', '8', '1', '4', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '2', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7128', null, '�޸���', 'Modify BY', '8', '1', '14', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7129', null, '�޸�ʱ��', 'Modify By', '7', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?,'7130', null, '�Ͷ���ͬ����', 'Contract Name', '18', '1', '7', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '2', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7106', null, '����ID', 'Order ID', '7', '1', '16', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedOrderId', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7126', null, '��Ӷ״̬', 'Employee Status', '4', '1', '11', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7113', null, '��ʼ����', 'Start Date', '7', '1', '8', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7114', null, '��������', 'End Date', '7', '1', '9', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '1', null, '����', 'Handle', '4', '1', '17', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?, '7107', null, 'ǩԼ����', 'Legal entity', '14', '1', '5', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7120', null, '�Ű����', 'Shift', '8', '1', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7125', null, '��ְ����', 'Resign Date', '8', '1', '12', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_Q );

         //�̱���
         SQLScriptDTO sqlScript_S = new SQLScriptDTO();
         sqlScript_S.setSqlScript( listHeaderBefore + "" + accountId+ ", "+corpId+", '0', '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '�̱���', 'Commercial Benefit List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'employeeId', 'Ա��ID', 'Staff Id', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?, null, 'employeeNameZH', '����', 'Name', '10', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '�Ͷ���ͬID', 'Labor Contract ID', '10', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'employStatus', 'Ա��״̬', 'Staff Status', '10', '1', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?, null, 'monthly', '�����·�', 'Cost Month', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?, null, 'employeeCBNameZH', '�̱�����', 'Commercial Benefit Name', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'status', '״̬', 'Status', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?,null, 'cbStatus', '�̱�״̬', 'Commercial Benefit  Status', '10', '1', '99', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         //�̱�������
         String sbdkey = insertDB( sqlScript_S );
         //�̱�����ϸ
         SQLScriptDTO sqlScript_SD = new SQLScriptDTO();
         sqlScript_SD.setSqlScript( listHeaderBefore + "" + accountId + ", "+corpId+", " + sbdkey+ ", '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '�̱��� - ��ϸ', 'Commercial Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_83', '����ҽ�Ʊ���', 'Jiben Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_84', '�ۺ�ҽ�Ʊ���', 'Zonghe Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_85', '���乤�˱���', 'Buchong Gongshang', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore + "?, null, 'item_81', '������������', 'Disanzhe', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_86', '���ⲹ��', 'Teshu Buchong', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         insertDB( sqlScript_SD );
         
         //���ʵ�����
         SQLScriptDTO sqlScriptDTO_T = new SQLScriptDTO();
         sqlScriptDTO_T.setSqlScript(  searchHeaderBefore + "" + accountId + ", " + corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO.Report', '���ʵ�����', 'Payslip Report', null, null, '1', '1', " + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "  null, 'employeeId', 'Ա��ID', 'Staff ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "   null, 'employeeNameZH', 'Ա�����������ģ�', 'Staff Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "   null, 'employeeNameEN', 'Ա��������Ӣ�ģ�', 'Staff Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         String sbDetailKey_T = insertDB( sqlScriptDTO_T );
         SQLScriptDTO sqlScript_T = new SQLScriptDTO();
         //���ʱ���
         sqlScript_T.setSqlScript( listHeaderBefore+" "+accountId+", "+corpId+", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO.Report', '0', '"+sbDetailKey_T+"', '���ʱ���', 'Payslip Report', '10', '0', '1', '2', '1', 'payslip', '1', '1',"+after );
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL, 'employeeId',  'Ա��ID',  'Staff Id', 80,   2, 1, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'employeeNameZH', 'Ա�����������ģ�', 'Staff Name( CH )',  100,  2, 2, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?,NULL,'employeeNameEN', 'Ա��������Ӣ�ģ�', 'Staff Name( EN )',  100,  2, 3, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'certificateNumber', '֤������',  'ID Number',   150,  2, 4, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?,NULL,'bankName', '��������',  'Bank Name',   150,  2, 5, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'bankAccount', '�����˻�',  'Bank Account',   150,  2, 6, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'monthly',  'н���·�',  'Pay Monthly', 60,   2, 7, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'orderId',  '�������ID',   'Order ID', 80,   2, 8, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempBranchIds',  '��������',  'Work Branch', 100,  2, 9, 13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempPositionIds',   'ְλ����',  'Position Name',  100,  2, 10,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempParentBranchIds',  '�ϼ�����',  'Parent Branch',  100,  2, 11,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'contractId',  '�Ͷ���ͬID',   'Contract ID', 80,   2, 12,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'beforeTaxSalary',   'Ӧ������',  'Accrued Wages',  150,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'taxAmountPersonal', '��˰', 'Personal Income Tax',  150,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'afterTaxSalary', 'ʵ������',  'Real Wages',  100,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'status',   '״̬', 'Status',   60,   2, 99,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL, 'entity', '����ʵ��', 'Entity', 100, 2, 8, 13, 1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, "+after);
         String list_T=insertDB( sqlScript_T );
         //���ʱ��� - ��ϸ
         SQLScriptDTO sqlScript_U = new SQLScriptDTO();
         sqlScript_U.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  "+list_T+",   1, 'com.kan.hro.domain.biz.payment.PayslipDTO.Report',   0, 0, '���ʱ��� - ��ϸ',   'Payslip Report - Detail', 10,   0, 1, 2, 0, NULL, 1, 1,"+after );
         sqlScript_U.addSubSQLScript( listDetailBefore +" ?,NULL, 'item_1',   '��������',  'Base Salary', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_2',   '���ʵ���',  'Salary Adjustment', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_4',   '�����ۿ�',  'Other Withhold', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_5',   '��˰', 'Income Tax',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_6',   '��λ����',  'Position Allowance',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_7',   '���½���',  'High Temperature Allowance', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_8',   'פ�����',  'Travel Allowance',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_9',   '��������',  'Other Allowance',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_10',  'ҹ�ಹ��',  'Night Shift Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_11',  '��ͨ����',  'Transportation Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_12',  '�òͲ���',  'Meat Subsidy',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_13',  '��������',  'Other Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_14',  '������',   'Welfare',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_15',  '��Ч', 'Performance', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_16',  '����', 'Bonus', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_17',  'Ӷ��', 'Commission',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_18',  '���ս�',   'Annual Bonus',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_21',  '�Ӱ� 1.0',   'OT 1.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_22',  '�Ӱ� 1.5',   'OT 1.5',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_23',  '�Ӱ� 2.0',   'OT 2.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_24',  '�Ӱ� 3.0',   'OT 3.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_25',  '�Ӱ໻��',  'OT Shift', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +" ?, NULL, 'item_26',  '�����Ӱ�',  'OT Other', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_31',  '����', 'Reimbursement',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_32',  '���� - ���ʷ�', 'Reimbursement - Entertainment', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_33',  '���� - �Ӱ�',  'Reimbursement - OT',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_41',  '���', 'Annual Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_42',  '���� - ȫн',  'Sick Leave',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_43',  '���� - ��ȫн��70%��',  'Sick Leave - 70%',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_44',  '�¼�', 'Absence',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_45',  '����', 'Maternity Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_46',  '�����',   'Nursing Leave',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_47',  'ɥ��', 'Bereft Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_61_c',   '���ϱ���-��˾',  'Endowment - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_62_c',   'ҽ�Ʊ���-��˾',  'Medical - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_63_c',   'ʧҵ����-��˾',  'Unemployment - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_64_c',   '���˱���-��˾',  'Injury - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_65_c',   '��������-��˾',  'Maternity - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_66_c',   '�м��˱��Ͻ�-��˾',   'Disable - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_67_c',   'ס��������-��˾', 'Housing - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_68_c',   'ȡů��-��˾',   'Warming - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_69_c',   '�����-��˾',   'Allocation - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_70_c',   '���������-��˾', 'Archive - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_71_c',   'סԺҽ�Ʊ���-��˾',   'Zhuyuan Yiliao - Company',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_72_c',   '����ҽ�Ʊ���-��˾',   'Buchong Yiliao - Company',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_73_c',   '��ҽ�Ʊ���-��˾',   'Dabing Yiliao - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_74_c',   '���ũ��ҽ��-��˾',   'Yidi Nongcun - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_75_c',   'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_61_p',   '���ϱ���-����',  'Endowment - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_62_p',   'ҽ�Ʊ���-����',  'Medical - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_63_p',   'ʧҵ����-����',  'Unemployment - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_64_p',   '���˱���-����',  'Injury - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_65_p',   '��������-����',  'Maternity - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_66_p',   '�м��˱��Ͻ�-����',   'Disable - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_67_p',   'ס��������-����', 'Housing - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_68_p',   'ȡů��-',  'Warming - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_69_p',   '�����-',  '  Allocation - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_70_p',   '���������-����', 'Archive - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_71_p',   'סԺҽ�Ʊ���-����',   'Zhuyuan Yiliao - Personal',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_72_p',   '����ҽ�Ʊ���-����',   'Buchong Yiliao - Personal',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_73_p',   '��ҽ�Ʊ���-����',   'Dabing Yiliao - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_74_p',   '���ũ��ҽ��-����',   'Yidi Nongcun - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_75_p',   'ҽ�ƹ��ý�-����', 'Medical Aid Funding - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_130', '����ѣ���˰��',  'ervice Fee (I-Tax)',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_131', '����ѣ���˰�� - ����',   'Service Fee (I-Tax) - Prepay',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_132', '����ѣ���˰�� - ΥԼ��',  'Service Fee (I-Tax) - Cancelation',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_133', '����ѣ���˰��- ����   ', 'Service Fee (I-Tax) - Other',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_134', '��ѵ�ѣ���˰��',  'Training Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_135', '��Ƹ�ѣ���˰��',  'Recruitment Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_136', '����ѣ���˰��',  'Agent Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_141', 'Ӫҵ˰',   'Tax',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_142', '��ְ���',  'Onboard Physical Examination',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_143', '������',  'Annual Physical Examination',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_144', '�籣���쿨��',   'SB Card Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_145', 'ҽ�����쿨��',   'Medical Card Fee',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_146', '���ʿ��쿨��',   'Salary Card Fee',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_147', '�����',   'Labour Union Fee',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_148', '���ɽ�',   'Overdue Fine',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_149', '������',   'Poundage', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_150', '������',   'Paper Work Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_151', '������',   'Each Other Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_161', '�籣�����', 'Social Benefit Management Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_162', '�����������',   '3rd Part Management Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_181', '�������',  'Labour Union Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_182', '���˻���',  'Injury Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_183', '��������',  'Maternity Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_184', '���ϻ���',  'Disable Fund',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_185', '�油����',  'Replace Fund',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_186', '���ջ���',  'VC Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_187', 'ȫ�����',  'Full Liability Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_188', '��������',  'Other Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         insertDB( sqlScript_U );
         
         //�籣������
         SQLScriptDTO sqlScript_SBSetting = new SQLScriptDTO();
         sqlScript_SBSetting.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  '0', '2', '', '37', '0', '�籣������', 'SB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after );
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3700', null, '��ͬID', 'Contract ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3719', null, '���ID', 'Employee SB ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3723', null, 'Ա��ID', 'Employee ID', '80', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3724', null, 'Ա���������У�', 'Employee Name(ZH)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',  "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3725', null, 'Ա��������Ӣ��', 'Employee Name(EN)', '120', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3701', null, '�籣�����𷽰�', 'SB', '120', '2', '11', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3706', null, '�ӱ�����', 'Start Date', '120', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3707', null, '�˱�����', 'End Date', '120', '2', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3703', null, '�籣������״̬', 'SB Status', '80', '2', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3730', null, '�����������', 'Order Description', '150', '2', '19', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3729', null, '��ͬ״̬', 'Contract Status', '80', '2', '21', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3726', null, '���֤����', 'Certificate Number', '150', '2', '23', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
//         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3731', null, '�ͻ�ID', 'Client ID', '80', '2', '25', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_SBSetting );
         
         //�̱�������
         SQLScriptDTO sqlScript_CBSetting = new SQLScriptDTO();
         sqlScript_CBSetting.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  '0', '2', '', '39', '0', '�̱�������', 'CB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after );
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3912', null, '���ID', 'CB ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3914', null, 'Ա��ID', 'Employee ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3915', null, 'Ա���������У�', 'Employee Name(ZH)', '120', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3916', null, 'Ա��������Ӣ��', 'Employee Name(EN)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',  "+after);
//         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3921', null, '�ͻ�ID', 'Client ID', '80', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3913', null, '�������ID', 'Order ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3900', null, '�Ͷ���ͬID', 'Contract ID', '80', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3920', null, '�Ͷ���ͬ״̬', 'Contract Status', '80', '2', '15', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         insertDB( sqlScript_CBSetting );
         
         return 0;
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return 1;
      }
   }

   /**
    * ���ݳ־û�����
    * 
    * @author : Ian.huang
    * @date   : 2014-6-4
    * @param  : @param sqlScripts
    * @return : void
    */
   private boolean insertDB( final List< SQLScriptDTO > sqlScripts )
   {
      try
      {
         for ( SQLScriptDTO sqlScriptDTO2 : sqlScripts )
         {
            String primaryKey = "";
            final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sqlScriptDTO2.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS );
            HeaderpreparedStatement.executeUpdate();
            // ��ȡ����
            final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
            if ( rs.next() )
            {
               primaryKey = rs.getString( 1 );
            }
            if ( sqlScriptDTO2.getSubSQLScriptDTOs() != null && sqlScriptDTO2.getSubSQLScriptDTOs().size() > 0 )
            {
               for ( SQLScriptDTO sqlScriptDTO : sqlScriptDTO2.getSubSQLScriptDTOs() )
               {
                  final PreparedStatement DetailpreparedStatement = connection.prepareStatement( sqlScriptDTO.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS );
                  if ( primaryKey != "" && sqlScriptDTO.getSqlScript().contains( "?" ) )
                  {
                     DetailpreparedStatement.setObject( 1, primaryKey );
                  }
                  DetailpreparedStatement.executeUpdate();
               }
            }
         }
         return true;
      }
      catch ( SQLException e )
      {
         return false;
      }
   }

   /**
    * ������ӱ� ������������
    * 
    * @author : Ian.huang
    * @date   : 2014-6-4
    * @param  : @param sqlScripts
    * @return : void
    */
   private String insertDB( final SQLScriptDTO sqlScripts )
   {
      String primaryKey = "";
      String error = "";
      try
      {
         if ( sqlScripts != null )
         {
            final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sqlScripts.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS );
            HeaderpreparedStatement.executeUpdate();
            // ��ȡ����
            final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
            if ( rs.next() )
            {
               primaryKey = rs.getString( 1 );
            }
            if ( sqlScripts.getSubSQLScriptDTOs() != null && sqlScripts.getSubSQLScriptDTOs().size() > 0 )
            {
               for ( SQLScriptDTO sqlScriptDTO : sqlScripts.getSubSQLScriptDTOs() )
               {
                  final PreparedStatement DetailpreparedStatement = connection.prepareStatement( sqlScriptDTO.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS );
                  error = sqlScriptDTO.getSqlScript();
                  if ( primaryKey != "" && sqlScriptDTO.getSqlScript().contains( "?" ) )
                  {
                     DetailpreparedStatement.setObject( 1, primaryKey );
                  }
                  DetailpreparedStatement.executeUpdate();
               }
            }
         }
      }
      catch ( SQLException e )
      {
         System.err.println( "Error:" + error );
      }
      return primaryKey;
   }

   public AccountModuleRelationDao getAccountModuleRelationDao()
   {
      return accountModuleRelationDao;
   }

   public void setAccountModuleRelationDao( AccountModuleRelationDao accountModuleRelationDao )
   {
      this.accountModuleRelationDao = accountModuleRelationDao;
   }

   public ModuleDao getModuleDao()
   {
      return moduleDao;
   }

   public void setModuleDao( ModuleDao moduleDao )
   {
      this.moduleDao = moduleDao;
   }

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }
}
