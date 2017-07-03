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

   // 获得数据库连接实例
   Connection connection = null;

   private void getConnection() throws SQLException
   {
      // 获取连接池的连接
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
            // 开启事务
            accountId = accountVO.getAccountId();
            flag = flag + createClient( accountVO );
            flag = flag + updateClient();
            flag = flag + createStaff();
            flag = flag + createOptions();
            flag = flag + createEmailConfiguration();
            flag = flag + createShareFolderConfiguration();

            flag = flag + createSearch();
            // 设置初始化状态
            flag = flag + updateAccount();
            if ( flag == 0 )
            {
               // 提交事务
               connection.commit();

               closeConnection();
               return true;
            }
            else
            {
               // 回滚事务
               connection.rollback();
               closeConnection();
               return false;
            }
         }
         else
         {
            // 未找到需要初始化的对象或对象已被初始化
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
            //创建结算规则
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
               //添加hro_sec_group_module_right_relation
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
         //添加hro_sec_group_module_rule_relation
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

   // 初始化选项设置
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

   //创建staff--> user
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
         // 获取主键
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
         // 获取主键
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

   // 创建user
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
         // 获取主键
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

   //创建Position
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
         sb.append( "(" + accountId + ",null, 0, 0, 0, '系统管理员 HS', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null," + userId + ",'"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }

         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append( "INSERT INTO HRO_SEC_Position" );
         stringBuffer.append( "(accountId,corpId, locationId, branchId, positionGradeId, titleZH, titleEN, description, skill, note, attachment, parentPositionId, isVacant,isIndependentDisplay ,needPublish, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         stringBuffer.append( "VALUES " );
         stringBuffer.append( "(" + accountId + ",'"+corpId+"', 0, 0, 0, '系统管理员 IH', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null,"+ userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeadpreparedStatement = connection.prepareStatement( stringBuffer.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeadpreparedStatement.executeUpdate();
         final ResultSet resultSet = HeadpreparedStatement.getGeneratedKeys();
         if ( resultSet.next() )
         {
            Administrator_primaryKey = resultSet.getString( 1 );
         }

         //创建HRO_SEC_Position_Staff_Relation
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
      sqlRoport_A.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '69', '在职员工报表', 'Employee Report', '52', '1', '15', '0', '2', null, null, null, '1', '2', '9', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6954', '69', '员工ID', 'Employee Id', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6901', '69', '员工姓名', 'Staff Name', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6902', '69', '称呼', 'Salutation', '5', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6904', '69', '出生年月', 'Birthday', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_A.addSubSQLScript( sqlDetail+ " '6911', '69', '最高学历', 'Highest Education', '5', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6933', '69', '邮箱', 'Email', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6903', '69', '员工状态', 'Employee Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + "'6965', '69', '服务部门', 'Branchs', '', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_A.addSubSQLScript( sqlDetail + " '6923', '69', '员工工号', 'Employee No', '', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_A );

      SQLScriptDTO sqlRoport_B = new SQLScriptDTO();
      sqlRoport_B.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '71', '劳动合同报表', 'Labor Contract Report', '25', '1', '15', '0', '2', null, null, null, '1', '2', '9', '2', '{" + positionId+ "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail+ "'7102', '71', '劳动合同名称', 'Contract Name (ZH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7100', '71', '员工ID', 'Staff ID', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7107', '71', '签约主体', 'Entity', '10', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7113', '71', '开始日期', 'Start Date', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + "'7114', '71', '结束日期', 'End Date', '10', '1', '5', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7120', '71', '排班情况', 'Shift', '15', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7105', '71', '合同状态', 'Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7104', '71', '劳动合同编号', 'Contract No', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_B.addSubSQLScript( sqlDetail + " '7121', '71', '劳动合同ID', 'Labor Contract ID', '', '1', '0', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', '0', '1', '', '1', '1'," + after );
      insertDB( sqlRoport_B );

      SQLScriptDTO sqlRoport_C = new SQLScriptDTO();
      sqlRoport_C.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '19', '请假统计表', 'Leave Report', '16', '1', '15', '0', '2', null, null, null, '1', '2', '10', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1900', '19', '员工ID', 'Staff ID', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail + "'1902', '19', '请假类别', 'Leave Type', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_C.addSubSQLScript( sqlDetail+ "'1903', '19', '开始时间', 'Estimate Start Date', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail+ "'1904', '19', '结束时间', 'Estimate End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1907', '19', '法定休假小时数', 'Estimate Legal Hours', '5', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1908', '19', '休福利假小时数', 'Estimate Benefit Hours', '5', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_C.addSubSQLScript( sqlDetail + " '1912', '19', '状态', 'Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_C );

      SQLScriptDTO sqlRoport_D = new SQLScriptDTO();
      sqlRoport_D.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '20', '加班统计表', 'OT Report', '11', '1', '15', '0', '2', null, null, null, '1', '2', '10', '2', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2000', '20', '员工ID', 'Stafff ID', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail+ "'2003', '20', '开始时间', 'Estimate Start Date', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + " '2004', '20', '结束时间', 'Estimate End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2002', '20', '科目', 'Item', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + " '2007', '20', '加班小时数', 'Estimate Hours', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_D.addSubSQLScript( sqlDetail + "'2010', '20', '状态', 'Status', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_D );

      SQLScriptDTO sqlRoport_E = new SQLScriptDTO();
      sqlRoport_E.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '3', '供应商报表', 'Vendor Report', '24', '1', '15', '0', '2', null, null, null, '1', '2', '7', '2', '{"+ positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'300', '3', '供应商名称', 'Vendor Name(ZH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'302', '3', '供应商类型', 'Vendor Type', '10', '1', '2', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_E.addSubSQLScript( sqlDetail+ " '304', '3', '合同开始日期', 'Contract Start Date', '10', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail+ "'305', '3', '合同结束日期', 'Contract End Date', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + " '303', '3', '城市', 'City', '10', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_E.addSubSQLScript( sqlDetail + "'312', '3', '签约主体', 'Legal Entity', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_E.addSubSQLScript( sqlDetail + " '315', '3', '状态', 'Vendor Status', '10', '1', '7', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_E );

      SQLScriptDTO sqlRoport_F = new SQLScriptDTO();
      sqlRoport_F.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '76', '员工信息', 'Staff Information', '54', '1', '15', '0', '2', null, null, null, '1', '2', '9', '1', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7654', '76', '员工ID', 'Employee Id', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7601', '76', '员工姓名', 'Staff Name', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7602', '76', '称呼', 'Salutation', '10', '1', '3', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail+ "'7663', '76', '劳动合同ID', 'Labor Contract ID', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail+ " '7667', '76', '合同状态', 'Contract Status', '10', '1', '5', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + " '7668', '76', '薪资', 'Salary', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7669', '76', '津贴', 'Allowance', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_F.addSubSQLScript( sqlDetail + "'7670', '76', '奖金', 'Subsidy', '10', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_F );

      SQLScriptDTO sqlRoport_G = new SQLScriptDTO();
      sqlRoport_G.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '79', '员工 - 劳动合同信息', 'Staff - Labor Contract Info', '12', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId+ "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ " '7911', '79', '劳动合同ID', 'Labor Contract ID', '5', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7901', '79', '劳动合同名称（中文）', 'Contract Name (ZH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7907', '79', '合同开始时间', 'Start Date', '10', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7908', '79', '合同结束时间', 'End Date', '10', '1', '4', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ " '7916', '79', '员工姓名（中文）', 'Employee Name( CH)', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7923', '79', '银行', 'Bank', '10', '1', '6', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7924', '79', '银行账户', 'Bank Account', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail+ "'7934', '79', '社保方案', 'Social Benefit Solution', '10', '1', '8', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7935', '79', '社保基数', 'Base', '10', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7941', '79', '年假', 'Annual leave', '5', '1', '10', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_G.addSubSQLScript( sqlDetail + " '7948', '79', '加班1.0', 'OT 1.0', '5', '1', '11', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_G.addSubSQLScript( sqlDetail + "'7931', '79', '薪资', 'Salary', '5', '1', '12', '13', '0', '0', '', '0', '0', '0', '0', '3', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_G );

      SQLScriptDTO sqlRoport_H = new SQLScriptDTO();
      sqlRoport_H.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '80', '社保 - 社保报表', 'SB - SB Report', '25', '1', '15', '0', '2', null, '{8013}', '{\"8013\":\"AVG\"}', '3', '2', '11', '1', '{" + positionId + "}', '', '', null, null, '', '1', '2'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8004', '80', '员工姓名（中文）', 'Employee Name(CH)', '10', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8000', '80', '劳动合同ID', 'Contract ID', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ " '8010', '80', '供应商名称（中文）', 'Vendor Name(CH)', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8012', '80', '账单月份', 'Monthly', '5', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + "'8013', '80', '养老保险-公司', 'Endowment - Company', '5', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8014', '80', '养老保险-个人', 'Endowment - Personal', '5', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8015', '80', '医疗保险-公司', 'Medical - Company', '5', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8016', '80', '医疗保险-个人', 'Medical - Personal', '5', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1', " + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8017', '80', '失业保险-公司', 'Unemployment - Company', '5', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ " '8018', '80', '失业保险-个人', 'Unemployment - Personal', '5', '1', '10', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8019', '80', '工伤保险-公司', 'Injury - Company', '5', '1', '11', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8020', '80', '工伤保险-个人', 'Injury - Personal', '5', '1', '12', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + "'8021', '80', '生育保险-公司', 'Maternity - Company', '5', '1', '13', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ "'8022', '80', '生育保险-个人', 'Maternity - Personal', '5', '1', '14', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail+ "'8025', '80', '住房公积金-公司', 'Housing - Company', '5', '1', '15', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_H.addSubSQLScript( sqlDetail + " '8026', '80', '住房公积金-个人', 'Housing - Personal', '5', '1', '16', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_H );

      SQLScriptDTO sqlRoport_I = new SQLScriptDTO();
      sqlRoport_I.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '81', '商保 - 商保报表', 'CB - CB Report', '11', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId + "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8100', '81', '员工ID', 'Staff ID', '8', '1', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8102', '81', '员工姓名（中文）', 'Staff Name(CH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8113', '81', '商保中文名', 'CB Solution Name(CH)', '10', '1', '3', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8118', '81', '第三者责任险', 'Disanzhe', '10', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_I.addSubSQLScript( sqlDetail+ " '8119', '81', '门诊医疗保险', 'Menzhen Yiliao', '10', '1', '5', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail+ " '8120', '81', '基本医疗保险', 'Jiben Yiliao', '10', '1', '6', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8121', '81', '综合医疗保险', 'Zonghe Yiliao', '10', '1', '7', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8122', '81', '补充工伤保险', 'Buchong Gongshang', '10', '1', '8', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_I.addSubSQLScript( sqlDetail + "'8123', '81', '特殊补充', 'Teshu Buchong', '10', '1', '9', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_I.addSubSQLScript( sqlDetail + " '8117', '81', '商保状态', 'CB Status', '12', '1', '10', '13', '1', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      insertDB( sqlRoport_I );

      SQLScriptDTO sqlRoport_J = new SQLScriptDTO();
      sqlRoport_J.setSqlScript( sqlHeader + "" + accountId + ", " + corpId+ ", '71', '员工 - 劳动合同', '合同到期续签名单', '17', '1', '15', '0', '2', '{\"7115\":\"ASC（升）\"}', null, null, '1', '2', null, null,  '{" + positionId+ "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_J.addSubSQLScript( sqlDetail + "'7100', '71', '员工ID', 'Employee ID', '3', '2', '1', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail+ "'7102', '71', '劳动合同名称（中文）', 'Contract Name (ZH)', '10', '1', '2', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '7114', '71', '合同结束时间', 'End Date', '11', '1', '3', '13', '0', '0', '', '0', '1', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '7115', '71', '所属部门', 'Branch', '30', '1', '4', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1',"+ after );
      sqlRoport_J.addSubSQLScript( sqlDetail + " '8137', null, '续签次数', 'count', '3', '1', '5', '13', '0', '0', '', '0', '0', '1', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_J );

      SQLScriptDTO sqlRoport_K = new SQLScriptDTO();
      sqlRoport_K.setSqlScript( sqlHeader + "" + accountId + ", " + corpId + ", '3', '供应商 - 供应商TS', '', '12', '1', '15', '0', '2', null, null, null, '3', '2', null, null, '{" + positionId + "}', null, null, null, null, '', '1', '1'," + after );
      sqlRoport_K.addSubSQLScript( sqlDetail + "'300', '3', '供应商名称（中文）', 'Vendor Name(ZH)', '10', '1', '0', '13', '0', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      sqlRoport_K.addSubSQLScript( sqlDetail+ " '304', '3', '合同开始日期', 'Contract Start Date', '10', '1', '0', '13', '2', '0', '', '0', '0', '0', '0', '1', '1', null, '1', '', '1', '1'," + after );
      insertDB( sqlRoport_K );

      return 0;
   }

   private int createImport( String positionId )
   {
      String sqlHeader = "INSERT INTO Hro_Def_Import_Header (parentId,accountId,corpId,tableId,nameZH,nameEN,positionIds,positionGradeIds,positionGroupIds,needBatchId,matchConfig,handlerBeanId,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ";
      String sqlDetail = "INSERT INTO Hro_Def_Import_Detail (importHeaderId,columnId,nameZH,nameEN,isPrimaryKey,isForeignKey,columnWidth,columnIndex,fontSize,specialField,tempValue,isDecoded,isIgnoreDefaultValidate,datetimeFormat,accuracy,round,align,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ?,";
      String after = "null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";

      //员工导入
      SQLScriptDTO sqlImport_A = new SQLScriptDTO();
      sqlImport_A.setSqlScript( sqlHeader + "  '0', '" + accountId + "', " + corpId + ", '69', '员工导入', 'Employee Import', '{" + positionId + "}', '', '', '2', '0', 'employeeImportHandler', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6954', '员工系统编号', 'Employee ID', '1', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6901', '姓名', 'Employee Name (ZH)', '0', '0', '14', '2', '13', '0', '张三', '2', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6900', '员工英文名', 'Employee Name (EN)', '0', '0', '14', '0', '13', '0', 'Zhangsan', '2', '0', '0', '0', '0', '1', '', '1', '2', "+ after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6902', '称呼 ', 'Salutation', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6905', '婚姻状况', 'Marital Status', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6904', '出生日期', 'Birthday', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6907', '出生地', 'Place of Birth', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6914', '户口性质', 'Residency Type', '0', '0', '14', '9', '13', '0', '', '1', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6908', '户籍地址', 'Residency Address', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6911', '最高学历', 'Highest Education', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6933', '邮箱', 'Email', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6947', '所属部门', 'Branch', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2',    " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6948', '所属人', 'Owner', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6903', '员工状态', 'Status', '0', '0', '14', '18', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6931', '电话', 'Phone', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6932', '手机号码', 'Mobile', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6926', '证件类型', 'Certificate Type', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6927', '证件号码', 'Certificate Number', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '7671', '职位名称', 'Position Name', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2', " + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6953', '籍贯', '', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6935', '联系电话', 'Phone (2nd)', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6941', '即时通讯 - 2', 'IM Type - 2', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6942', '即时通讯号码 - 2', 'IM - 2', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6943', '即时通讯 - 3', 'IM Type - 3', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6944', '即时通讯号码 - 3', 'IM - 3', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6945', '即时通讯 - 4', 'IM Type - 4', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6946', '即时通讯号码 - 4', 'IM - 4', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '2'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + " '6913', '户籍地址', 'Record Address', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6909', '联系地址', 'Personal Address', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6923', '员工工号', 'Employee No', '0', '0', '14', '11', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_A.addSubSQLScript( sqlDetail + "'6923', '员工工号', 'Employee No', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_A );

      //工资导入
      SQLScriptDTO sqlImport_B = new SQLScriptDTO();
      sqlImport_B.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '74', '工资导入', 'Salary Import', '{" + positionId + "}', '', '', '1', '0', 'salaryExcueHandler', '', '1', '2'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7408', '帐套Id', 'orderId', '0', '0', '14', '8', '13', '0', '500000001', '2', '0', '0', '0', '0', '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7409', '劳动合同Id', 'contractId', '0', '0', '14', '9', '13', '0', '200000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7410', '员工Id', 'Employee ID', '0', '0', '14', '10', '13', '0', '100000001', '2', '0', '0', '0', '0', '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7411', '员工姓名（中文）', 'employeeNameZH', null, null, '14', '11', '13', null, '张三', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7412', '员工姓名（英文）', 'employeeNameEN', null, null, '14', '12', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7413', '薪酬开始日期', 'startDate', null, null, '14', '13', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7414', '薪酬结束日期', 'endDate', null, null, '14', '14', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+ after );
      sqlImport_B.addSubSQLScript( sqlDetail + "'7415', '证件类型', 'certificateType', null, null, '14', '15', '13', null, '', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail+ " '7416', '证件号码', 'certificateNumber', null, null, '14', '16', '13', null, '2.11E+17', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail+ " '7418', '银行名称（中文）', 'bankNameZH', null, null, '14', '18', '13', null, '中国银行上海市分行', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7419', '银行名称（英文）', 'bankNameEN', null, null, '14', '19', '13', null, '', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7420', '银行账户', 'bankAccount', null, null, '14', '20', '13', null, '6.23E+18', '2', null, '', null, null, '1', null, '1', '1'," + after );
      sqlImport_B.addSubSQLScript( sqlDetail + " '7427', '薪酬月份', 'monthly', null, null, '14', '27', '13', null, '2014/01', '2', null, '', null, null, '1', null, '1', '1'," + after );
      String primaryKey_B = insertDB( sqlImport_B );

      //劳动合同
      SQLScriptDTO sqlImport_C = new SQLScriptDTO();
      sqlImport_C.setSqlScript( sqlHeader + " '" + primaryKey_B + "', '" + accountId + "', " + corpId + ", '75', '劳动合同', 'Salary Import - Detail', '{" + positionId+ "}', '', '', '0', '0', 'salaryExcueHandler', '', '1', '2'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '基本工资', 'Base Salary', '0', '0', '14', '0', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '请假扣款', 'Leave Deduct', '0', '0', '14', '1', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '其它扣款', 'Other Withhold', '0', '0', '14', '2', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '岗位津贴', 'Position Allowance', '0', '0', '14', '3', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '高温津贴', 'High Temperature Allowance', '0', '0', '14', '4', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '驻外津贴', 'Travel Allowance', '0', '0', '14', '5', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '其他津贴', 'Other Allowance', '0', '0', '14', '6', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '夜班补贴', 'Night Shift Subsidy', '0', '0', '14', '7', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '交通补贴', 'Transportation Subsidy', '0', '0', '14', '8', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '用餐补贴', 'Meat Subsidy', '0', '0', '14', '9', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '其他补贴', 'Other Subsidy', '0', '0', '14', '10', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '福利费', 'Welfare', '0', '0', '14', '11', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '绩效', 'Performance', '0', '0', '14', '12', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '奖金', 'Bonus', '0', '0', '14', '13', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '佣金', 'Commission', '0', '0', '14', '14', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '年终奖', 'Annual Bonus', '0', '0', '14', '15', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7518', '加班 1.0', 'OT 1.0', '0', '0', '14', '16', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '加班 1.5', 'OT 1.5', '0', '0', '14', '17', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '加班 2.0', 'OT 2.0', '0', '0', '14', '18', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '加班 3.0', 'OT 3.0', '0', '0', '14', '19', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '加班换休', 'OT Shift', '0', '0', '14', '20', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '报销', 'Reimbursement', '0', '0', '14', '21', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '报销 - 交际费', 'Reimbursement - Entertainment', '0', '0', '14', '22', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7518', '报销 - 加班', 'Reimbursement - OT', '0', '0', '14', '23', '13', '0', '0.00', '2', null, '9', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '住房公积金-公司', 'Housing(Company)', '0', '0', '14', '24', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '住房公积金-个人', 'Housing(Personal)', '0', '0', '14', '25', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '养老保险-公司', 'Endowment(Company)', '0', '0', '14', '26', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '养老保险-个人', 'Endowment(Personal)', '0', '0', '14', '27', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '医疗保险-公司', 'Medical(Company)', '0', '0', '14', '28', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '失业保险-个人', 'Unemployment(Personal)', '0', '0', '14', '29', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '工伤保险-公司', 'Injury(Company)', '0', '0', '14', '30', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '工伤保险-个人', 'Injury(Personal)', '0', '0', '14', '31', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '生育保险-公司', 'Maternity(Company)', '0', '0', '14', '32', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '生育保险-个人', 'Maternity(Personal)', '0', '0', '14', '33', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '残疾人保障金-公司', 'Disable(Company)', '0', '0', '14', '34', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '残疾人保障金-个人', 'Disable(Personal)', '0', '0', '14', '35', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '取暖费-公司', 'Warming(Company)', '0', '0', '14', '36', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '取暖费-个人', 'Warming(Personal)', '0', '0', '14', '37', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '调配费-公司', 'Allocation(Company)', '0', '0', '14', '38', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '调配费-个人', 'Allocation(Personal)', '0', '0', '14', '39', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '档案管理费-公司', 'Archive(Company)', '0', '0', '14', '40', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '档案管理费-个人', 'Archive(Personal)', '0', '0', '14', '41', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', "+ after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7519', '住院医疗保险-公司', 'Zhuyuan Yiliao(Company)', '0', '0', '14', '42', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7520', '住院医疗保险-个人', 'Zhuyuan Yiliao(Personal)', '0', '0', '14', '43', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7519', '补充医疗保险-公司', 'Buchong Yiliao(Company)', '0', '0', '14', '44', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '补充医疗保险-个人', 'Buchong Yiliao(Personal)', '0', '0', '14', '45', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519', '大病医疗保险-公司', 'Dabing Yiliao(Company)', '0', '0', '14', '46', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '大病医疗保险-个人', 'Dabing Yiliao(Personal)', '0', '0', '14', '47', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7519', '异地农村医保-公司', 'Yidi Nongcun(Company)', '0', '0', '14', '48', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ " '7520', '异地农村医保-个人', 'Yidi Nongcun(Personal)', '0', '0', '14', '49', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7519', '医疗共济金-公司', 'Medical Aid Funding(Company)', '0', '0', '14', '50', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail+ "'7520', '医疗共济金-个人', 'Medical Aid Funding(Personal)', '0', '0', '14', '51', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7520', '医疗保险-个人', 'Medical(Personal)', '0', '0', '14', '28', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_C.addSubSQLScript( sqlDetail + " '7519', '失业保险-公司', 'Unemployment(Company)', '0', '0', '14', '29', '13', '0', '0.00', '2', null, '0', '3', '0', '3', '', '1', '1',"+ after );
      insertDB( sqlImport_C );

      //劳动合同
      SQLScriptDTO sqlImport_D = new SQLScriptDTO();
      sqlImport_D.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '71', '劳动合同', 'Employee Contract', '{" + positionId + "}', '', '', '1', '0', 'employeeContractImportHandler', '', '1', '2'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7100', '员工ID', 'Employee ID', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7122', '员工姓名（中文）', 'Employee Name (ZH)', null, null, '14', '3', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7106', '账套ID', 'Order ID', null, null, '14', '5', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7121', '劳动合同系统编号', 'Labor Contract ID', '1', '0', '14', '10', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "  '7102', '劳动合同名称（中文）', 'Contract Name (ZH)', null, null, '14', '12', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7103', '劳动合同名称（英文）', 'Contract Name (EN)', null, null, '14', '13', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7104', '劳动合同编号', 'Contract No', null, null, '14', '14', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7109', '劳动合同模板', 'Template ', null, null, '14', '16', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7113', '合同开始时间', 'Start Date', null, null, '14', '17', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7114', '合同结束时间', 'End Date', null, null, '14', '18', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7105', '合同状态', 'Status', null, null, '14', '27', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7107', '法务实体', 'Entity ID', null, null, '14', '32', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7108', '业务类型', 'Business Type ', null, null, '14', '33', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7119', '日历', 'Calendar', null, null, '14', '34', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + " '7120', '排班', 'Shift', null, null, '14', '35', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'7124', '考勤方式', 'Attendance Check Type', null, null, '14', '36', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_D.addSubSQLScript( sqlDetail + "'8579', '身份证号码', 'certificateNumber', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1', " + after );
      String primaryKey_D = insertDB( sqlImport_D );
       
      //劳动合同从表 - 社保方案
      SQLScriptDTO sqlImport_K = new SQLScriptDTO();
      sqlImport_K.setSqlScript( sqlHeader + "" + primaryKey_D + ", '" + accountId + "', " + corpId + ", '37', '劳动合同从表 - 社保方案', '劳动合同从表 - 社保方案', '{" + positionId+ "}', '', '', '1', '0', '0', '', '1', '2'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '社保方案A', 'Solution Benefit A', '0', '0', '14', '1', '13', '0', '', '1', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '加保日期A', 'Start Date A', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '社保基数A', 'SB Base A', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '社保方案B', 'Solution Benefit B', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '加保日期B', 'Start Date B', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '社保基数B', 'SB Base B', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3701', '社保方案C', 'Solution Benefit C', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3706', '加保日期C', 'Start Date C', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'8585', '社保基数C', 'SB Action C', '0', '0', '14', '9', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '社保方案D', 'Solution Benefit D', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3706', '加保日期D', 'Start Date D', '0', '0', '14', '11', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '社保基数D', 'SB Base D', '0', '0', '14', '12', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + "'3701', '社保方案E', 'Solution Benefit E', '0', '0', '14', '13', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '3706', '加保日期E', 'Start Date E', '0', '0', '14', '14', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_K.addSubSQLScript( sqlDetail + " '8585', '社保基数E', 'SB Base E', '0', '0', '14', '15', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_K );
      
      //劳动合同从表 - 薪酬方案
      SQLScriptDTO sqlImport_H = new SQLScriptDTO();
      sqlImport_H.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '40', '劳动合同从表 - 薪酬方案', '劳动合同从表 - 薪酬方案', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_H.addSubSQLScript( sqlDetail + "'4004', '基本工资', '基本工资', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_H );

      //劳动合同从表 - 商保方案
      SQLScriptDTO sqlImport_I = new SQLScriptDTO();
      sqlImport_I.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '39', '劳动合同从表 - 商保方案', '劳动合同从表 - 商保方案', '{" + positionId+ "}', null, null, '0', '0', '0', '', '1', '1'," + after );
      sqlImport_I.addSubSQLScript( sqlDetail + "'3901', '商保方案', 'Commercial Benefit ID', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_I.addSubSQLScript( sqlDetail + "'3905', '商保方案 - 加保日期', 'Start Date', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_I );

      //劳动合同从表 - 休假方案
      SQLScriptDTO sqlImport_J = new SQLScriptDTO();
      sqlImport_J.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '42', '劳动合同从表 - 休假方案', '劳动合同从表 - 休假方案', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_J.addSubSQLScript( sqlDetail + " '4205', '年假', '年假', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_J.addSubSQLScript( sqlDetail + " '4205', '病假 - 全薪', 'Base', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_J );

     

      //劳动合同从表 - 加班方案
      SQLScriptDTO sqlImport_L = new SQLScriptDTO();
      sqlImport_L.setSqlScript( sqlHeader + " " + primaryKey_D + ", '" + accountId + "', " + corpId + ", '41', '劳动合同从表 - 加班方案', '劳动合同从表 - 加班方案', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + " '4104', '加班 1.0', 'Base', '0', '0', '14', '0', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + "'4104', '加班 2.0', 'Base', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_L.addSubSQLScript( sqlDetail + "'4104', '加班换休', 'Base', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_L );

      //劳动合同社保方案
      SQLScriptDTO sqlImport_E = new SQLScriptDTO();
      sqlImport_E.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '37', '劳动合同社保方案', '', '{" + positionId + "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3700', '劳动合同ID', 'Contract ID', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3701', '社保方案', 'Solution Benefit', null, null, '14', '2', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3705', '供应商', 'Vendor', null, null, '14', '3', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3702', '供应商服务', 'Vendor Service', null, null, '14', '4', '13', null, null, '2', null, null, null, null, '1', null, '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3706', '加保日期', 'Start Date', null, null, '14', '5', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "'3707', '退保日期', 'End Date', null, null, '14', '6', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3709', '需要办理医保卡', 'Need Medical Card', null, null, '14', '7', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3712', '医保卡帐号', 'Medical Number', null, null, '14', '8', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail+ " '3711', '需要办理社保卡', 'Need Social Benefit Card', null, null, '14', '9', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail+ "'3713', '社保卡帐号', 'Social Benefit Number', null, null, '14', '10', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',  " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + " '3714', '公积金帐号', 'fundNumber', null, null, '14', '11', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "  '3704', '描述', 'Description', null, null, '14', '12', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_E.addSubSQLScript( sqlDetail + "  '3703', '状态', 'Status', null, null, '14', '13', '13', null, null, '2', null, null, null, null, '1', '', '1', '1', " + after );
      insertDB( sqlImport_E );

      //供应商社保方案 - 导入
      SQLScriptDTO sqlImport_F = new SQLScriptDTO();
      sqlImport_F.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '53', '供应商社保方案 - 导入', ' Vendor SB Important ', '{" + positionId + "}', '', '', '1', '0', 'vendorSBImportHandlerImpl', '', '1', '2'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5309', '订单ID', 'Order ID', null, null, '14', '17', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5310', '合同Id', 'Contract ID', '0', '0', '14', '19', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5311', '员工ID', 'Employee ID', '0', '0', '14', '21', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5313', '员工姓名（中文）', 'employeeName(ZH)', '0', '0', '14', '25', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5314', '员工姓名（英文）', 'Employee Name(EN)', '0', '0', '14', '27', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5318', '劳动合同开始时间', 'Contract Start Date', '0', '0', '14', '35', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5319', '劳动合同结束时间', 'Contract End Date', '0', '0', '14', '37', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + " '5334', '医保卡帐号', 'Medical Number', null, null, '14', '67', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',  "+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5335', '社保卡帐号', 'SB Number', null, null, '14', '69', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5336', '公积金帐号', 'Fund Number', null, null, '14', '71', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5342', '员工状态', 'Employ Status', '0', '0', '14', '83', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5344', '加保日期（起缴年月）', 'Start Date', null, null, '14', '87', '13', null, null, '2', null, null, null, null, '1', '', '1', '1',"+ after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5345', '退保日期', 'End Date', null, null, '14', '89', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5348', '账单月份', 'Monthly', null, null, '14', '95', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5303', '法务实体ID', 'Entity ID', '0', '0', '14', '5', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5304', '业务类型ID', 'BusinessType ID', '0', '0', '14', '6', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_F.addSubSQLScript( sqlDetail + "'5315', '雇员社保方案ID', 'EmployeeSB ID', '0', '0', '14', '8', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      String primaryKey_F = insertDB( sqlImport_F );

      //供应商社保方案 - 导入从表明细
      SQLScriptDTO sqlImport_G = new SQLScriptDTO();
      sqlImport_G.setSqlScript( sqlHeader + "  " + primaryKey_F + ", '" + accountId + "', " + corpId + ", '54', '供应商社保方案 - 导入从表明细', 'Vendor SB Detail Important', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '1'," + after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', '养老保险-个人', 'Endowment - Company', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '养老保险-公司', 'Endowment - Personal', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '医疗保险-公司', 'Medical - Company', '0', '0', '14', '3', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5413', '医疗保险-个人', 'Medical - Personal', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', '失业保险-个人', 'Unemployment - Personal', '0', '0', '14', '6', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',  "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5412', '失业保险-公司', 'Unemployment - Company', '0', '0', '14', '5', '13', '0', '', '2', null, '0', '0', '0', '1', '', '2', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '工伤保险-公司', ' Injury - Company ', '0', '0', '14', '7', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "  '5413', '工伤保险-个人', 'Injury - Personal', '0', '0', '14', '8', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5414', '生育保险-公司', 'Maternity - Company', '0', '0', '14', '9', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + " '5413', '生育保险-个人', 'Maternity - Personal', '0', '0', '14', '10', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5414', '住房公积金-公司', 'Housing - Company', '0', '0', '14', '11', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', "+ after );
      sqlImport_G.addSubSQLScript( sqlDetail + "'5413', '住房公积金-个人', 'Housing - Personal', '0', '0', '14', '12', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1', " + after );
      insertDB( sqlImport_G );

      //考勤请假导入
      SQLScriptDTO sqlImport_M = new SQLScriptDTO();
      sqlImport_M.setSqlScript( sqlHeader + " '0', '" + accountId + "', " + corpId + ", '19', '考勤请假导入', '', '{" + positionId+ "}', '', '', '1', '0', 'leaveExcueHandler', '', '1', '2'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1900', '员工ID', 'Employee ID', '0', '0', '14', '1', '13', '0', '100000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1918', '员工姓名（中文）', 'Employee Name(ZH)', '0', '0', '14', '2', '13', '0', '张三', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_M.addSubSQLScript( sqlDetail + " '1924', '劳动合同', 'Contract', '0', '0', '14', '7', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + "'1902', '请假类别', 'Item', null, null, '14', '8', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail + " '1903', '开始时间', 'Estimate Start Date', '0', '0', '14', '9', '13', '0', '2014-01-01  12:12:12', '2', null, '1', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_M.addSubSQLScript( sqlDetail+ "'1904', '结束时间', 'Estimate End Date', '0', '0', '14', '10', '13', '0', '2014-01-01  12:12:12', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_M );

      //考勤加班导入
      SQLScriptDTO sqlImport_N = new SQLScriptDTO();
      sqlImport_N.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '20', '考勤加班导入', '', '{" + positionId+ "}', '', '', '1', '0', 'otExcueHandler', '', '1', '2'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + " '2000', '员工ID', 'Employee', null, null, '14', '1', '13', null, null, '2', null, null, null, null, '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + " '2002', '科目', 'Item', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2011', '员工姓名', 'Employee Name', '0', '0', '14', '2', '13', '0', '张三', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2022', '劳动合同', 'Contract', '0', '0', '14', '3', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail + "'2003', '开始时间', 'Estimate Start Date', '0', '0', '14', '8', '13', '0', '2014-01-01  12:12:12', '2', null, '1', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_N.addSubSQLScript( sqlDetail+ " '2004', '结束时间', 'Estimate End Date', '0', '0', '14', '9', '13', '0', '2014-01-01  12:12:12', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      insertDB( sqlImport_N );

      //考勤津贴导入
      SQLScriptDTO sqlImport_O = new SQLScriptDTO();
      sqlImport_O.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '44', '考勤津贴导入', 'Timesheet Import', '{" + positionId+ "}', '', '', '1', '0', 'timesheetAllowance', '', '1', '2'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + " '4420', '员工名（中文）', 'Employee NameZH', '0', '0', '14', '2', '13', '0', '张三', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + " '4402', '劳动合同ID', 'Contract ID', '0', '0', '14', '4', '13', '0', '200000001', '2', null, '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_O.addSubSQLScript( sqlDetail + "'4405', '月份', 'Time Sheet Month', '0', '0', '14', '3', '13', '0', '', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_O.addSubSQLScript( sqlDetail + "'4401', '员工ID', 'Employee ID', '0', '0', '14', '1', '13', '0', '100000001', '2', null, '0', '0', '0', '1', '', '1', '1'," + after );
      String primaryKey_O = insertDB( sqlImport_O );

      //考勤导入-津贴
      SQLScriptDTO sqlImport_P = new SQLScriptDTO();
      sqlImport_P.setSqlScript( sqlHeader + " " + primaryKey_O + ", '" + accountId + "', " + corpId + ", '85', '考勤导入-津贴', 'TimeSheet Import Allowance', '{" + positionId+ "}', '', '', '1', '0', '0', '', '1', '2'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '驻外津贴', 'Travel Allowance', '0', '0', '14', '10', '13', '0', '0.00', '2', '1', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail+ " '8503', '高温津贴', 'High Temperature Allowance', '0', '0', '14', '11', '13', '0', '0.00', '2', '2', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '岗位津贴', 'Position Allowance', '0', '0', '14', '12', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '夜班补贴', 'Night Shift Subsidy', '0', '0', '14', '13', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '其他津贴', 'Other Allowance', '0', '0', '14', '14', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '交通补贴', 'Transportation Subsidy', '0', '0', '14', '15', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '用餐补贴', 'Meat Subsidy', '0', '0', '14', '16', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '其他补贴', 'Other Subsidy', '0', '0', '14', '17', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '福利费', 'Welfare', '0', '0', '14', '18', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + "'8503', '年终奖', 'Annual Bonus', '0', '0', '14', '19', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '绩效', 'Performance', '0', '0', '14', '20', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '奖金', 'Bonus', '0', '0', '14', '21', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '佣金', 'Commission', '0', '0', '14', '21', '13', '0', '0.00', '2', '0', '0', '0', '0', '3', '', '1', '1', " + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '报销', 'Reimbursement', '0', '0', '14', '22', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail+ "'8503', '报销 - 交际费', 'Reimbursement - Entertainment', '0', '0', '14', '23', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1'," + after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '报销 - 加班', 'Reimbursement - OT', '0', '0', '14', '24', '13', '0', '0.00', '2', null, '0', '0', '0', '3', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503', '调整', 'Adjust', '0', '0', '14', '25', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503',  '全勤奖', 'Full Bonus', '0', '0', '14', '26', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_P.addSubSQLScript( sqlDetail + " '8503',  '绩效工资', '', '0', '0', '14', '27', '13', '0', '', '2', '2', '0', '0', '0', '1', '', '1', '1',"+ after );
      insertDB( sqlImport_P );
      
      //考勤汇总导入
      SQLScriptDTO sqlImport_Q = new SQLScriptDTO();
      sqlImport_Q.setSqlScript( sqlHeader + "'0', '" + accountId + "', " + corpId + ", '113', '考勤汇总导入', '', '{" + positionId+ "}', '', '', '1', '0', 'attendanceHeaderImportHandler', '', '1', '2'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13305', '劳动合同ID', 'Contract ID', '0', '0', '14', '5', '13', '0', '', '2', '1', '0', '0', '0', '1', '*黄色单元格为必填项', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13307', '考勤表月份', 'Monthly', '0', '0', '14', '7', '13', '0', '2015/01', '2', '1', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13309', '工作小时数（考勤周期合计）', 'Total Work Hours', '0', '0', '14', '9', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13311', '工作天数（考勤周期合计）', 'Total Work Days', '0', '0', '14', '11', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13313', '全勤小时数（考勤周期合计）', 'Total Full Hours', '0', '0', '14', '13', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1',"+ after );
      sqlImport_Q.addSubSQLScript( sqlDetail + "'13315', '全勤天数（考勤周期合计）', 'Total Full Days', '0', '0', '14', '17', '13', '0', '', '2', '1', '0', '0', '0', '1', '', '1', '1'," + after );
      sqlImport_Q.addSubSQLScript( sqlDetail + " '13320', '员工姓名', 'Employee Name', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', '×会验证员工姓名和合同对应的员工姓名是否匹配', '1', '1'," + after );
      String primaryKey_Q = insertDB( sqlImport_Q );
      
      
      //考勤汇总从表导入
      SQLScriptDTO sqlImport_U = new SQLScriptDTO();
      sqlImport_U.setSqlScript( sqlHeader + " " + primaryKey_Q + ", '" + accountId + "', " + corpId + ", '115', '考勤汇总从表导入', '', '{" + positionId+ "}', '', '', '0', '0', '0', '', '1', '2'," + after );
      sqlImport_U.addSubSQLScript( sqlDetail + " '11505', '科目ID', 'Item ID', NULL, NULL, '14', '5', '13', NULL, NULL, '2', NULL, NULL, NULL, NULL, '1', '', '1', '1'," + after );
      sqlImport_U.addSubSQLScript( sqlDetail + " '11507', '考勤小时数', 'Hours', NULL, NULL, '14', '9', '13', NULL, NULL, '2', NULL, NULL, NULL, NULL, '1', '', '1', '1', " + after );
      insertDB( sqlImport_U );
      return 0;
   }

   private int createGroup( final String positionId, final String Administrator_positionId )
   {
      try
      {
         //创建Group
         String primaryKey = "";
         StringBuffer sb = new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Group" );
         sb.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "(" + accountId + ",null, '系统管理组','System Management Team', 2,'Easy Staffing',1, 1, null, null, null, null, null," + userId + ",'" + getDate() + "', " + userId+ ", '" + getDate() + "')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }

         //创建Group
         String administrator_primaryKey = "";
         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append( "INSERT INTO HRO_SEC_Group" );
         stringBuffer.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         stringBuffer.append( "VALUES " );
         stringBuffer.append( "(" + accountId + "," + corpId + ", '系统管理组','System Management Team', 1,'Easy Staffing',1, 1, null, null, null, null, null," + userId + ",'"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement HeadpreparedStatement = connection.prepareStatement( stringBuffer.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeadpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet resultSet = HeadpreparedStatement.getGeneratedKeys();
         if ( resultSet.next() )
         {
            administrator_primaryKey = resultSet.getString( 1 );
         }
         //创建PositionGroupRelation
         StringBuffer sr = new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         sr.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "(" + positionId + "," + primaryKey + ",1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();

         //创建PositionGroupRelation
         StringBuffer stringBuff = new StringBuffer();
         stringBuff.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         stringBuff.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         stringBuff.append( "VALUES " );
         stringBuff.append( "(" + Administrator_positionId + "," + administrator_primaryKey + ",1,1,null,null,null,null,null," + userId + ",'" + getDate() + "', " + userId + ", '"+ getDate() + "')" );
         final PreparedStatement statement = connection.prepareStatement( stringBuff.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         statement.executeUpdate();
         //group 规则
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

   // 初始化选项设置
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

   // 初始化邮件设置
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

   //初始化Share Folder设置
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

   //初始化Search设置
   public int createSearch()
   {
      // 初始化SQL集
      //      List< SQLScriptDTO > sqlScripts = new ArrayList< SQLScriptDTO >();
      //list_Header
      try
      {
         String listHeaderBefore = "insert into Hro_Def_List_Header( accountId, corpId,  parentId, useJavaObject, javaObjectName,tableId, searchId, nameZH, nameEN, pageSize, loadPages, usePagination, isSearchFirst, exportExcel, description, deleted, status, remark1,  remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate) values ( ";
         String listDetailBefore = "insert into Hro_Def_List_Detail(listHeaderId, columnId, propertyName, nameZH ,nameEN, columnWidth, columnWidthType, columnIndex, fontSize, isDecoded, isLinked, linkedAction, linkedTarget, appendContent, properties, datetimeFormat, accuracy, align, round, sort, display, description, deleted, status, remark1, remark2, remark3, remark4,  remark5, createBy, createDate, modifyBy, modifyDate ) values( ";
         String after = "null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";

         String searchHeaderBefore = "INSERT INTO hro_def_search_header (accountId,corpId,tableId,useJavaObject,javaObjectName,nameZH,nameEN,isSearchFirst,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate)  values ( ";
         String searchDetailBefore = "INSERT INTO hro_def_search_detail (searchHeaderId,columnId,propertyName,nameZH,nameEN,columnIndex,fontSize,useThinking,thinkingAction,contentType,content,`range`,display,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ?,";
         //用户
         SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
         sqlScriptDTO.setSqlScript( searchHeaderBefore+""+ accountId + ", "+ corpId + ", '1', '2', '', '用户', 'Staff', null, '', '1', '1', "+after );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '105', null, '员工编号', 'Staff Code', '1', '13', '2', '', '0', '', '100_200', '1', ' ', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '101', null, '员工姓名（中文）', 'Staff Name (Chinese)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '102', null, '员工姓名（英文）', 'Staff Name (English)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '104', null, '员工状态', 'Staff Status', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         //用户搜索主键
         String userPrimarykey = insertDB( sqlScriptDTO );
         //用户列表
         SQLScriptDTO sqlScript = new SQLScriptDTO();
         sqlScript.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", 0, 2, '', 1, " + userPrimarykey + ", '用户列表', 'Staff List', 10, 0, 1, 2, 1, '', 1, 1,"+ after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '105', null, '员工编号', 'Staff Code','8', '1', '2', '13', '2', '2', '', '1', '', '', '0', '0', '1', '0', '1', '0', '1', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '101', null, '员工姓名（中文）', 'Staff Name (Chiness)','10', 1, 3, 13, 2,2, '',2, '', '', '', null, 1, null,1, 1, '', 1, 1,"+ after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '118', null, '户籍地址（籍贯）', 'Hometown','18', 1, 7, 13, 2,2, '',0, '', '', '', null, '1', null,1, 1, 1, 1, 1," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '104', null, '状态', 'Status','5', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '116', null, '证件号码', 'ID NO', '', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '7525', null, '工作年限', 'working period', '', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '124', null, '修改人', 'Modify By','8', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '125', null, '修改时间', 'Decode Modify Date','12', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore+ "?, '126', null, '员工ID', 'staffId','3', '1', '1', '13', '2', '1', 'staffAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript );

         //供应商
         SQLScriptDTO sqlScriptDTO_A = new SQLScriptDTO();
         sqlScriptDTO_A.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '3', '2', '', '供应商', 'Vendor', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '300', null, '供应商名称（中文）', 'Vendor Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '301', null, '供应商名称（英文）', 'Vendor Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '302', null, '供应商类型', 'Vendor Type', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '316', null, '省份 - 城市', 'Province', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '312', null, '法务实体', 'Legal Entity', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,   '313', null, '所属部门', 'Branch', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '314', null, '所属人', 'Owner', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '315', null, '状态', 'Stauts', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String vendorPrimarykey = insertDB( sqlScriptDTO_A );
         //供应商列表
         SQLScriptDTO sqlScript_A = new SQLScriptDTO();
         sqlScript_A.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ",  0, 2,'',3, " + vendorPrimarykey + ", '供应商列表', 'Vendor List',10, 0, 1, 2, 1, '', 1, 1,"+ after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'317', null, '供应商ID', 'Vendor ID', '5', '1', '1', '13', '2', '1', 'vendorAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'300', null, '供应商名称（中文）', 'Vendor Name (ZH)', '15', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'304', null, '合同开始日期', 'contract start date', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '305', null, '合同结束日期', 'Contract end date', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '303', null, '省份 - 城市', 'City', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'302', null, '类型', 'Vendor Type', '5', '1', '6', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '324', null, '联系人', 'Contacts', '6', '1', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '323', null, '联系人电话', 'Phone', '8', '1', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '326', null, '联系人邮箱', 'Email', '8', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '312', null, '法务实体', 'Entity', '10', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'315', null, '状态', 'Status', '8', '1', '11', '13', '1', '2', null, '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?,'318', null, '修改人', 'Modify By', '6', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_A.addSubSQLScript( listDetailBefore+ "?, '319', null, '修改日期', 'Modify Date', '8', '1', '13', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         insertDB( sqlScript_A );

         //供应商联系人
         SQLScriptDTO sqlScriptDTO_B = new SQLScriptDTO();
         sqlScriptDTO_B.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '4', '2', '', '供应商联系人', 'Vendor Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '402', null, '联系人姓名（中文）', 'Contact Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'403', null, '联系人姓名（英文）', 'Contact Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'404', null, '称呼', 'Salutation', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'423', null, '状态', 'Status', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'407', null, '省份 - 城市', 'CIty', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'424', null, '所属部门', 'Branch', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'425', null, '所属人', 'Owner', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String VendorContactKey = insertDB( sqlScriptDTO_B );
         //供应商联系人列表
         SQLScriptDTO sqlScript_B = new SQLScriptDTO();
         sqlScript_B.setSqlScript( listHeaderBefore + "" + accountId + ",  " + corpId + ", '0', '2', '', '4', " + VendorContactKey+ ", '供应商联系人列表', 'Vendor Contact List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '400', null, '联系人ID', 'Contract ID', '5', '1', '1', '13', '2', '1', 'vendorContactAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'402', null, '联系人姓名', 'Contact Name', '10', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '408', null, '公司电话', 'Company Number', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'414', null, '邮箱', 'Email', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '404', null, '称呼', 'Salutation', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'406', null, '部门', 'Title', '8', '1', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '405', null, '职位', 'Department', '8', '1', '7', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'401', null, '供应商ID', 'Vendor ID', '8', '1', '8', '13', '2', '1', 'vendorAction.do?proc=to_objectModify&id=${0}', '1', null, 'encodedVendorId', '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'426', null, '供应商名称', 'Vendor Name', '15', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?, '423', null, '状态', 'Status', '8', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'430', null, '修改人', 'Modify By', '8', '1', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         sqlScript_B.addSubSQLScript( listDetailBefore+ "?,'431', null, '修改日期', 'Modify Date', '9', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1'," + after );
         insertDB( sqlScript_B );

         //集团
         SQLScriptDTO sqlScriptDTO_C = new SQLScriptDTO();
         sqlScriptDTO_C.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '5', '2', null, '集团', 'Client Group', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '500', null, '集团编号', 'Number', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'501', null, '集团名称（中文）', 'Client Group NameZH', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '502', null, '集团名称（英文）', 'Client Group NameEN', '13', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '504', null, '状态', 'Status', '15', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientGroupKey = insertDB( sqlScriptDTO_C );
         SQLScriptDTO sqlScript_C = new SQLScriptDTO();
         //集团列表
         sqlScript_C.setSqlScript( listHeaderBefore + "" + accountId + ",  " + corpId + ", '0', '2', '', '5', " + clientGroupKey+ ", '集团列表', 'Client Group List', '10', '0', '1', '2', '1', '客户管理 = > 集团列表', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'500', '', '集团编号', 'number', '10', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'501', '', '集团名称（中文）', 'Client Group NameZH', '28', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'502', '', '集团名称（英文）', 'Client Group NameEN', '28', '1', '12', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?,'504', '', '状态', 'Status', '6', '1', '17', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '505', '', '修改人', 'Modify By', '8', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '506', '', '修改时间', '修改时间', '12', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_C.addSubSQLScript( listDetailBefore+ "?, '507', '', '集团ID', 'Client Group ID', '8', '1', '9', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_C );

         //客户
         SQLScriptDTO sqlScriptDTO_D = new SQLScriptDTO();
         sqlScriptDTO_D.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '6', '2', null, '客户', 'Client', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'609', null, '财务编码', 'number', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'610', null, '客户名称（中文）', 'ClientNameZH', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '611', null, '客户名称（英文）', 'ClientNameZH', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '641', null, '集团名称', 'GroupName', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'632', null, '客户ID', 'Client ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'605', null, '行业', 'Industry', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '606', null, '规模', 'Size', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '600', null, '法务实体', 'Entity', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '602', null, '所属部门', 'Branch', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '601', null, '所属人', 'Owner', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'612', null, '状态', 'Status', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'633', null, '集团编号', 'Group Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientKey = insertDB( sqlScriptDTO_D );
         //客户列表
         SQLScriptDTO sqlScript_D = new SQLScriptDTO();
         sqlScript_D.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '6', " + ClientKey+ ", '客户列表', 'Client List', '10', '0', '1', '2', '1', '客户管理 = > 客户列表', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'609', null, '财务编码', 'number', '9', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'610', '', '客户名称（中文）', 'nameCN', '25', '1', '5', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1', "+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'611', '', '客户名称（英文）', 'nameEN', '25', '1', '7', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '612', '', '状态', 'status', '7', '1', '28', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '639', '', '修改人', 'Modify By', '7', '1', '26', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '640', '', '修改时间', 'Modify Date', '11', '1', '27', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?, '633', '', '集团编号', 'Group Number', '9', '1', '8', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedGroupId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_D.addSubSQLScript( listDetailBefore+ "?,'632', '', '客户ID', 'Client ID', '7', '1', '1', '13', '2', '1', 'clientAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_D );

         //客户联系人
         SQLScriptDTO sqlScriptDTO_E = new SQLScriptDTO();
         sqlScriptDTO_E.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", "+ corpId+ ", '13', '2', null, '客户联系人', 'Client Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1305', null, '联系人姓名（中文）', 'nameZH', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1306', null, '联系人姓名（英文）', 'nameEN', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1323', null, '状态', 'status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1302', null, '客户ID', 'Client ID', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1308', null, '部门', 'Department', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1307', null, '职位', 'Title', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1333', null, '客户编号', 'Client Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1334', null, '客户名称（中文）', 'Client Name(ZH)', '10', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1335', null, '客户名称（英文）', 'Client Name(EN)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientContactKey = insertDB( sqlScriptDTO_E );
         //客户联系人列表
         SQLScriptDTO sqlScript_E = new SQLScriptDTO();
         sqlScript_E.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '13', " + clientContactKey+ ", '客户联系人列表', 'Client Contact List', '10', '0', '1', '2', '1', '客户管理 = > 客户联系人列表', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1305', '', '联系人姓名（中文）', 'nameZH', '11', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1306', '', '联系人姓名（英文）', 'nameEN', '11', '1', '3', '13', '2', '2', 'clientContactAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1304', '', '称呼', 'Salutation', '4', '1', '4', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1323', '', '状态', 'status', '4', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1307', '', '职位', 'title', '11', '1', '8', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1336', '', '修改人', 'Modify By', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1337', '', '修改时间', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1338', '', '客户名称', 'Client Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '6428', '', '联系人ID', 'Client Contact ID', '6', '1', '1', '13', '2', '1', 'clientContactAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?, '1302', '', '客户ID', 'Client ID', '7', '1', '5', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_E.addSubSQLScript( listDetailBefore+ "?,'1308', '', '部门', 'Department', '10', '1', '7', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_E );

         //账单地址
         SQLScriptDTO sqlScriptDTO_F = new SQLScriptDTO();
         sqlScriptDTO_F.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '14', '2', null, '账单地址', 'Client Invoice', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1401', null, '发票台头', 'Title', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1402', null, '中文名（收件人）', 'NameZH', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1403', null, '英文名（收件人）', 'NameEN', '5', '0', '2', '', '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1417', null, '状态', 'Status', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1430', null, '客户ID', 'Client ID', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1433', null, '客户编号', 'Client Number', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1407', null, '地址', 'Address', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1434', null, '客户名称（中文）', 'Client Name(ZH)', '13', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1435', null, '客户名称（英文）', 'Client Name(EN)', '14', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientInvoicekey = insertDB( sqlScriptDTO_F );
         //账单地址列表
         SQLScriptDTO sqlScript_F = new SQLScriptDTO();
         sqlScript_F.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '14', " + ClientInvoicekey+ ", '账单地址列表', 'Client Invoice List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1401', '', '发票台头', 'Title', '18', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1402', '', '收件人姓名（中文）', 'Name (ZH)', '11', '1', '3', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1403', '', '收件人姓名（英文）', 'Name (EN)', '11', '1', '5', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1417', '', '状态', 'status', '6', '1', '16', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1436', '', '修改人', 'Modify By', '7', '1', '14', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1437', '', '修改时间', 'Modify Date', '11', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1438', '', '账单地址ID', 'Client Inovice ID', '7', '1', '1', '13', '2', '1', 'clientInvoiceAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?, '1439', '', '客户名称', 'Client Name', '22', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_F.addSubSQLScript( listDetailBefore+ "?,'1430', '', '客户ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_F );

         //雇员 
         SQLScriptDTO sqlScriptDTO_G = new SQLScriptDTO();
         sqlScriptDTO_G.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '17', '2', null, '雇员', 'Employee', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1760', null, '雇员ID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1725', null, '雇员编号', 'Employee NO', '2', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1701', null, '雇员姓名（中文）', 'Employee Name (ZH)', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1700', null, '雇员姓名（英文）', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1714', null, '档案编号', 'Record No', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1728', null, '证件类型', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1729', null, '证件号码', 'Certificate Number', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1705', null, '婚姻状况', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1773', null, '出生年月（开始）', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1772', null, '出生年月（结束）', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1771', null, '首次参加工作日期（开始）', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1770', null, '首次参加工作日期（结束）', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1706', null, '国籍', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1716', null, '户口性质', 'Residency Type', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1713', null, '最高学历', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1703', null, '状态', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String Employeekey = insertDB( sqlScriptDTO_G );
         //雇员列表
         SQLScriptDTO sqlScript_G = new SQLScriptDTO();
         sqlScript_G.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '17', " + Employeekey+ ", '雇员列表', 'Employee List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1725', '', '雇员编号', 'Employee No', '8', '1', '2', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1701', '', '姓名（中文）', 'Employee Name (ZH)', '12', '1', '3', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1700', '', '姓名（英文）', 'Employee Name (EN)', '12', '1', '4', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1728', '', '证件类型', 'Certificate Type', '7', '1', '5', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1729', '', '证件号码', 'Certificate Number', '15', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1703', '', '状态', 'Status', '6', '1', '12', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1780', '', '修改人', 'Modify By', '8', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1781', '', '修改时间', 'Modify Date', '11', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?,'1760', '', '雇员ID', 'Employee ID', '7', '1', '1', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1', '', '劳动合同', 'Labor Contract', '7', '1', '8', '13', '2', '2', '', '0', '${0}', 'numberOfLaborContract', '', null, '1', null, '2', '1', '', '1', '1',"+ after );
         sqlScript_G.addSubSQLScript( listDetailBefore+ "?, '1', '', '派送协议', 'Dispatch Contract', '7', '1', '9', '13', '2', '2', '', '0', '${0}', 'numberOfServiceContract', '', null, '1', null, '2', '1', '', '1', '1',"+ after );
         insertDB( sqlScript_G );

         //商务合同
         SQLScriptDTO sqlScriptDTO_H = new SQLScriptDTO();
         sqlScriptDTO_H.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '18', '2', null, '商务合同', 'Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1800', null, '客户ID', 'Client ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1802', null, '法务实体', 'entityId', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1805', null, '合同编号', 'Contract Number', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1810', null, '合同名称（中文）', 'Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1811', null, '合同名称（英文）', 'Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1818', null, '状态', 'Status', '11', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1820', null, '合同ID', 'Client Contact ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1821', null, '财务编码', 'Client Number', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1822', null, '客户名称（中文）', 'Client Name(ZH)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1823', null, '客户名称（英文）', 'Client Name(EN)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1803', null, '业务类型', 'Business Type', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String contractKey = insertDB( sqlScriptDTO_H );
         //商务合同列表
         SQLScriptDTO sqlScript_H = new SQLScriptDTO();
         sqlScript_H.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '18', " + contractKey+ ", '商务合同列表', 'Contract List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1824', '', '修改人', 'Modify By', '6', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1825', '', '修改时间', 'Modify Date', '12', '1', '17', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1804', '', '合同模板', 'Template', '12', '1', '15', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1805', '', '合同编号', 'Contract Number', '10', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1809', '', '合同名称', 'Contract Name', '18', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?,'1818', '', '状态', 'Status', '6', '1', '19', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1820', '', '合同ID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1800', '', '客户ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '0', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_H.addSubSQLScript( listDetailBefore+ "?, '1801', '', '客户名称', 'Client Name', '22', '1', '13', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         insertDB( sqlScript_H );

         //劳动合同
         SQLScriptDTO sqlScriptDTO_I = new SQLScriptDTO();
         sqlScriptDTO_I.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '24', '2', null, '劳动合同', 'Labor Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2403', null, '劳动合同名称（英文）', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2402', null, '劳动合同名称（中文）', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2405', null, '合同状态', 'Employee Status', '12', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2400', null, '雇员ID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2442', null, '劳动合同ID', 'Labor Contract ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2443', null, '雇员姓名（中文）', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2444', null, '雇员姓名（英文）', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2451', null, '开始日期（起始）', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2452', null, '开始日期（截止）', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2453', null, '结束日期（起始）', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2454', null, '结束日期（截止）', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String primaryKey_I = insertDB( sqlScriptDTO_I );
         //劳动合同列表
         SQLScriptDTO sqlScript_I = new SQLScriptDTO();
         sqlScript_I.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', null, '24', " + primaryKey_I+ ", '劳动合同列表', 'Labor Contract List', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2401', null, '雇员姓名', 'Employee Name', '14', '1', '4', '13', '0', '2', '', '0', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2402', null, '合同名称（中文）', 'Name ZH ', '15', '1', '5', '13', '2', '2', '', '1', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2403', null, '合同名称（英文）', 'Name EN ', '15', '1', '6', '13', '0', '2', '', '1', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2414', null, '合同类型名称', 'contractTypeName ', '10', '1', '7', '13', '1', '0', '', '0', '', '', null, null, '0', null, null, '0', '', '2', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2404', null, '合同编号', 'Contract No', '10', '1', '6', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2405', null, '合同状态', 'Status', '3', '1', '12', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2442', null, '劳动合同ID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedContractId', null, null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2400', null, '雇员ID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', null, null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2443', null, '姓名（中文）', 'Employee Name (ZH)', '8', '1', '3', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ " ?,'2444', null, '姓名（英文）', 'Employee Name (EN)', '8', '1', '4', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2445', null, '修改人', 'Modify By', '7', '1', '10', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2446', null, '修改时间', 'Modify Time', '11', '1', '11', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2450', null, '合同名称', 'Contract Name', '22', '1', '7', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2418', null, '开始日期', 'Start Date', '7', '1', '8', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?,'2419', null, '结束日期', 'End Date', '7', '1', '9', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '1', null, '操作', 'Handle', '3', '1', '13', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_I.addSubSQLScript( listDetailBefore+ "?, '2440', null, '实际结束日期', '', '5', '1', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_I );

         //订单
         SQLScriptDTO sqlScriptDTO_J = new SQLScriptDTO();
         sqlScriptDTO_J.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '28', '2', null, '订单', 'Order', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2828', null, '状态', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2806', null, '法务实体', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2808', null, '业务类型', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2836', null, '订单ID', 'Order ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2802', null, '客户ID', 'Client ID', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2804', null, '合同ID', 'Contract ID', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2812', null, '订单开始日期', 'Order Start Date', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2813', null, '订单结束日期', 'Order End Date', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2826', null, '所属部门', 'Branch', '31', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2827', null, '所属人', 'Owner', '33', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2844', null, '客户名称（中文）', 'Client Name(ZH)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2845', null, '客户名称（英文）', 'Client Name(EN)', '12', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2853', null, '财务编码', 'Client Number', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String orderKey = insertDB( sqlScriptDTO_J );
         //订单列表
         SQLScriptDTO sqlScript_J = new SQLScriptDTO();
         sqlScript_J.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', null, '28', " + orderKey+ ", '订单列表', 'Order List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2843', '', '客户名称', 'Client Name', '20', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2848', '', '修改人', 'Modify By', '6', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2849', '', '修改时间', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2828', '', '状态', 'Status', '6', '1', '11', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2802', '', '客户ID', 'Client ID', '7', '1', '2', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2836', '', '订单ID', 'Order ID', '7', '1', '1', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2804', '', '商务合同ID', 'Contract ID', '7', '1', '4', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedContractId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2812', '', '开始日期', 'Start Date', '7', '1', '5', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2813', '', '结束日期', 'End Date', '7', '1', '6', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2826', '', '所属部门', 'Branch', '10', '1', '7', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_J.addSubSQLScript( listDetailBefore+ "?,'2827', '', '所属人', 'owner', '12', '1', '8', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_J );

         //派送协议
         SQLScriptDTO sqlScriptDTO_K = new SQLScriptDTO();
         sqlScriptDTO_K.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '43', '2', null, '派送协议', 'Service Contract', null, '', '1', '1', '', '', '', '', '', "+ userId+ ", '"+ getDate()+ "', "+ userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4305', null, '协议状态', 'Contract Service Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4311', null, '客户ID', 'Client ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4303', null, '派送协议名称（英文）', 'Service Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4302', null, '派送协议名称（中文）', 'Service Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4300', null, '雇员ID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4340', null, '派送协议ID', 'Service Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4341', null, '雇员姓名（中文）', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4342', null, '雇员姓名（英文）', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4356', null, '开始日期（起始）', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4357', null, '开始日期（截止）', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4358', null, '结束日期（起始）', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4359', null, '结束日期（截止）', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4361', null, '财务编码', 'Client Number', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String serviceContractKey = insertDB( sqlScriptDTO_K );
         //派送协议列表
         SQLScriptDTO sqlScript_K = new SQLScriptDTO();
         sqlScript_K.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', null, '43', " + serviceContractKey+ ", '派送协议列表', 'Service Contract List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4304', '', '编号', 'Contract No', '8', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4305', '', '协议状态', 'Contract Status', '3', '1', '12', '13', '1', '2', '', '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4340', '', '派送协议ID', 'Contract Service ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&comeFrom=2', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4300', '', '雇员ID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4341', '', '姓名（中文）', 'Employee Name ZH', '8', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4342', '', '姓名（英文）', 'Employee Name EN', '8', '1', '4', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'1', '', '操作', 'Handle', '3', '1', '13', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4351', '', '修改人', 'Modify BY', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4352', '', '修改时间', 'Modify By', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4355', '', '派送协议名称', 'Contract Service Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4311', '', '客户ID', 'Client ID', '7', '1', '8', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1', "+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4306', '', '订单ID', 'Order ID', '7', '1', '7', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedOrderId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4346', '', '雇佣状态', 'EmployStatus', '6', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '8583', null, '身份证号码', 'ID', '', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4318', null, '开始时间', 'Start date', '', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1', " + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?,'4319', null, '结束日期', 'End Date', '', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         sqlScript_K.addSubSQLScript( listDetailBefore+ "?, '4322', null, '所属人', 'be', '', '1', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1'," + after );
         insertDB( sqlScript_K );

         //工资单
         SQLScriptDTO sqlScriptDTO_L = new SQLScriptDTO();
         sqlScriptDTO_L.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '工资单', 'Payslip', null, '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String payslip = insertDB( sqlScriptDTO_L );
         //工资单列表
         SQLScriptDTO sqlScript_L = new SQLScriptDTO();
         sqlScript_L.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', " + payslip+ ", '工资单列表', 'Payslip List', '10', '0', '1', '2', '1', 'payslip', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'employeeId', '员工ID', 'Staff Id', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore + "?, null, 'employeeNameZH', '员工姓名（中文）', 'Staff Name( CH )', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'employeeNameEN', '员工姓名（英文）', 'Staff Name( EN )', '100', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'certificateNumber', '证件号码', 'ID Number', '150', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'bankName', '银行名称', 'Bank Name', '150', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'bankAccount', '银行账户', 'Bank Account', '150', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'monthly', '薪酬月份', 'Pay Monthly', '60', '2', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'orderId', '订单ID', 'Order ID', '80', '2', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '劳动合同ID', 'Contract ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore + "?,null, 'beforeTaxSalary', '应发工资', 'Accrued Wages', '150', '2', '95', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'taxAmountPersonal', '个税', 'Personal Income Tax', '150', '2', '97', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'afterTaxSalary', '实发工资', 'Real Wages', '100', '2', '98', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?,null, 'status', '状态', 'Status', '60', '2', '99', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'entity', '法务实体', 'Entity', '100', '2', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_L.addSubSQLScript( listDetailBefore+ "?, null, 'taxAgentAmountPersonal', '代扣税工资', 'Tax Agent Amount Personal', '150', '2', '96', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         //工资单列表主键
         String payslipKey = insertDB( sqlScript_L );
         //工资单列表 - 明细
         SQLScriptDTO sqlScript_LD = new SQLScriptDTO();
         sqlScript_LD.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", " + payslipKey+ ", '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', '0', '工资单列表 - 明细', 'Payslip List - Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_1', '基本工资', 'Base Salary', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_2', '工资调整', 'Salary Adjustment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_3', '请假扣款', 'Leave Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_4', '其它扣款', 'Other Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_5', '个税', 'Income Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_6', '岗位津贴', 'Position Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_7', '高温津贴', 'High Temperature Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_8', '驻外津贴', 'Travel Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_9', '其他津贴', 'Other Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_10', '夜班补贴', 'Night Shift Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_11', '交通补贴', 'Transportation Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_12', '用餐补贴', 'Meat Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_13', '其他补贴', 'Other Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_14', '福利费', 'Welfare', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_15', '绩效', 'Performance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_16', '奖金', 'Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_17', '佣金', 'Commission', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_18', '年终奖', 'Annual Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_21', '加班 1.0', 'OT 1.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_22', '加班 1.5', 'OT 1.5', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_23', '加班 2.0', 'OT 2.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_24', '加班 3.0', 'T 3.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', " + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_25', '加班换休', 'OT Shift', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_26', '其他加班', 'OT Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_31', '报销', 'Reimbursement', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_32', '报销 - 交际费', 'Reimbursement - Entertainment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_33', '报销 - 加班', 'Reimbursement - OT', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_41', '年假', 'Annual Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_42', '病假 - 全薪', 'Sick Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_43', '病假 - 非全薪（70%）', 'Sick Leave - 70%', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_44', '事假', 'Absence', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_45', '产假', 'Maternity Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_46', '护理假', 'Nursing Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_47', '丧假', 'Bereft Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_c', '养老保险-公司', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_62_c', '医疗保险-公司', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_c', '失业保险-公司', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_c', '工伤保险-公司', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_65_c', '生育保险-公司', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_66_c', '残疾人保障金-公司', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_c', '住房公积金-公司', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_68_c', '取暖费-公司', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_69_c', '调配费-公司', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_c', '档案管理费-公司', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_71_c', '住院医疗保险-公司', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_c', '补充医疗保险-公司', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_73_c', '大病医疗保险-公司', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_74_c', '异地农村医保-公司', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_c', '医疗共济金-公司', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_p', '养老保险-个人', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_62_p', '医疗保险-个人', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_p', '失业保险-个人', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_p', '工伤保险-个人', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_65_p', '生育保险-个人', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_66_p', '残疾人保障金-个人', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_p', '住房公积金-个人', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_68_p', '取暖费-个人', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_69_p', '调配费-个人', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_p', '档案管理费-个人', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_71_p', '住院医疗保险-个人', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_p', '补充医疗保险-个人', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_73_p', '大病医疗保险-个人', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_p', '异地农村医保-个人', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_p', '医疗共济金-个人', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_130', '服务费（含税）', 'Service Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_131', '服务费（含税） - 订金', 'Service Fee (I-Tax) - Prepay', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_132', '服务费（含税） - 违约金', 'Service Fee (I-Tax) - Cancelation', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_133', '服务费（含税） - 其他', 'Service Fee (I-Tax) - Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_134', '培训费（含税）', 'Training Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_135', '招聘费（含税）', 'Recruitment Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_136', '代理费（含税）', 'Agent Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_141', '营业税', 'Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_142', '入职体检', 'Onboard Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_143', '年度体检', 'Annual Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_144', '社保卡办卡费', 'SB Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_145', '医保卡办卡费', 'Medical Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_146', '工资卡办卡费', 'Salary Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_147', '工会费', 'Labour Union Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_148', '滞纳金', 'Overdue Fine', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_149', '手续费', 'Poundage', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_150', '工本费', 'Paper Work Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_151', '互助金', 'Each Other Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_161', '社保管理费', 'Social Benefit Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+ after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_162', '第三方管理费', '3rd Part Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_181', '工会基金', 'Labour Union Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_182', '工伤基金', 'Injury Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?,null, 'item_183', '生育基金', 'Maternity Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_184', '残障基金', 'Disable Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_185', '替补基金', 'Replace Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore+ "?, null, 'item_186', '风险基金', 'VC Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_187', '全责基金', 'Full Liability Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_188', '其他基金', 'Other Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_197', '绩效津贴', 'Performance allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_LD.addSubSQLScript( listDetailBefore + "?, null, 'item_19', '加班调整', 'OT Adjustment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_LD );

         //个税
         SQLScriptDTO sqlScriptDTO_M = new SQLScriptDTO();
         sqlScriptDTO_M.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '个税月度报表', 'Income Tax ', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String incomeTax = insertDB( sqlScriptDTO_M );
         //个税申报列表
         SQLScriptDTO sqlScript_M = new SQLScriptDTO();
         sqlScript_M.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '0', " + incomeTax+ ", '个税申报列表', 'Income Tax List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'employeeId', '雇员ID', 'EmployeeId', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '15', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '15', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'orderId', '订单ID', 'Order', '10', '1', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?,null, 'certificateNumber', '证件号码', 'Certificate Number', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'monthly', '个税所属月份', 'Tax Monthy', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'taxAmountPersonal', '个税金额', 'Tax Amount', '10', '1', '7', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'cityId', '申报城市', 'City', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_M.addSubSQLScript( listDetailBefore+ "?, null, 'status', '状态', 'Status', '10', '1', '9', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_M );

         //结算规则
         SQLScriptDTO sqlScriptDTO_N = new SQLScriptDTO();
         sqlScriptDTO_N.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '78', '2', '', '结算规则', 'Order', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7852', '', '状态', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'7806', '', '法务实体', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
       //  sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7808', '', '业务类型', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7836', '', '结算规则ID', 'Order ID', '1', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7812', '', '规则开始日期', 'Order Start Date', '17', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7813', '', '规则结束日期', 'Order End Date', '19', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7826', '', '所属部门', 'Branch', '31', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7827', '', '所属人', 'Owner', '33', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String primaryKey_N = insertDB( sqlScriptDTO_N );
         SQLScriptDTO sqlScript_N = new SQLScriptDTO();
         sqlScript_N.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '2', '', '78', " + primaryKey_N+ ", '结算规则列表', 'Order List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7852', '', '状态', 'Status', '6', '1', '11', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7836', null, '结算规则ID', 'Order ID', '9', '1', '1', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7812', null, '开始日期', 'Start Date', '12', '1', '5', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7813', null, '结束日期', 'End Date', '12', '1', '6', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7826', null, '所属部门', 'Branch', '12', '1', '7', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '2'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7827', '', '所属人', 'owner', '14', '1', '8', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '2', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7848', '', '修改人', 'Modify By', '8', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7849', '', '修改时间', 'Modify Date', '13', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7831', null, '结算规则名称', 'Associated Information', '12', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?,'7806', null, '适用法务实体', 'Entity Name', '16', '1', '3', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_N.addSubSQLScript( listDetailBefore+ "?, '7808', null, '适用业务类型', 'Business Type', '12', '1', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', " + after );
         insertDB( sqlScript_N );

         //社保单
         SQLScriptDTO sqlScriptDTO_O = new SQLScriptDTO();
         sqlScriptDTO_O.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", "+ corpId+ ", '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '社保单', 'SB Bill', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate() + "', " + userId + ", '" + getDate() + "')" );         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '员工姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '员工姓名（英文）', 'Employee Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '劳动合同ID', 'Labor Contract ID', '4', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '客户ID', 'Client ID', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  null, 'employeeId', '员工ID', 'Staff ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '客户名称（中文）', 'Client Name(ZH)', '7', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '客户名称（英文）', 'Client Name(EN)', '8', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'vendorNameZH', '供应商名称', 'Vendor Name', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String sbKey = insertDB( sqlScriptDTO_O );
         //社保单列表
         SQLScriptDTO sqlScript_O = new SQLScriptDTO();
         sqlScript_O.setSqlScript( listHeaderBefore + " " + accountId + ", " + corpId + ", '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', " + sbKey+ ", '社保单', 'Social Benefit ', '10', '0', '1', '2', '1', '', '1', '1', " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'clientId', '客户ID', 'Client ID', '100', '2', '1', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'clientNo', '财务编码', 'Client Number', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'clientNameZH', '客户名称', 'Client Name', '100', '2', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1', " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, null, 'employeeId', '雇员ID', 'Staff ID', '100', '2', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,null, 'employeeNameZH', '雇员姓名', 'Employee Name', '100', '2', '5', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '派送协议ID', 'Labor Contract ID', '100', '2', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'contractStatus', '派送协议状态', 'Staff Status', '100', '2', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'monthly', '申报月份', 'Monthly', '100', '2', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'employeeSBNameZH', '社保方案名称', 'Social Benefit Name', '100', '2', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, null, 'sbStatus', '社保状态', 'Social Benefit Status', '100', '2', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'vendorNameZH', '供应商名称', 'Vendor Name', '100', '2', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'amountCompany', '合计（公司）', 'Amount Company', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?,null, 'amountPersonal', '合计（个人）', 'Amount Personal', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1',"+ after );
         sqlScript_O.addSubSQLScript( listDetailBefore+ "?, null, 'status', '状态', 'Status', '100', '2', '99', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,null, 'entityId', '法务实体', 'Entity Name', '100', '2', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'accountMonthly', '所属月份', 'AccountMonthly', 100, 2, 5, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'description', '备注', 'Remark', 100, 2, 99, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'certificateNumber', '证件号码', 'Certificate Number', 100, 2, 2, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, " + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'sbNumber', '社保卡帐号', 'Social Benefit Card', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'fundNumber', '公积金帐号', 'Accumulation Fund Card', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'basePersonal', '基数', 'Base Personal', 100, 2, 6, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, " + after );
         //社保单主键
         String sbDetailKey = insertDB( sqlScript_O );
         //社保单 - 明细
         SQLScriptDTO sqlScript_OD = new SQLScriptDTO();
         sqlScript_OD.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", " + sbDetailKey+ ", '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', '0', '社保单 - 明细', 'Social Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_61_c', '养老保险-公司', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_62_c', '医疗保险-公司', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_c', '失业保险-公司', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_64_c', '工伤保险-公司', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_65_c', '生育保险-公司', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_66_c', '残疾人保障金-公司', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_67_c', '住房公积金-公司', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_68_c', '取暖费-公司', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_69_c', '调配费-公司', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_70_c', '档案管理费-公司', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_71_c', '住院医疗保险-公司', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_72_c', '补充医疗保险-公司', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_73_c', '大病医疗保险-公司', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_c', '异地农村医保-公司', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_c', '医疗共济金-公司', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_61_p', '养老保险-个人', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_62_p', '医疗保险-个人', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_63_p', '失业保险-个人', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_64_p', '工伤保险-个人', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_65_p', '生育保险-个人', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_66_p', '残疾人保障金-个人', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_67_p', '住房公积金-个人', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_68_p', '取暖费-个人', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_69_p', '调配费-个人', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?,null, 'item_70_p', '档案管理费-个人', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_71_p', '住院医疗保险-个人', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_72_p', '补充医疗保险-个人', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_73_p', '大病医疗保险-个人', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_74_p', '异地农村医保-个人', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+ "?, null, 'item_75_p', '医疗共济金-个人', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+ after );
         insertDB( sqlScript_OD );

         //员工 - 基本信息
         SQLScriptDTO sqlScriptDTO_P = new SQLScriptDTO();
         sqlScriptDTO_P.setSqlScript( searchHeaderBefore + "" + accountId + ", " + corpId + ", '69', '2', null, '员工 - 基本信息', 'Employee', null, 'In House', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6954', null, '员工ID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6923', null, '员工编号', 'Employee NO', '2', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6901', null, '员工姓名（中文）', 'Employee Name (ZH)', '1', '0', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6900', null, '员工姓名（英文）', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6912', null, '档案编号', 'Record No', '5', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6926', null, '证件类型', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6927', null, '证件号码', 'Certificate Number', '7', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6905', null, '婚姻状况', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6904', null, '出生年月（开始）', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6904', null, '出生年月（结束）', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '2', '1', " + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6916', null, '首次参加工作日期（开始）', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6916', null, '首次参加工作日期（结束）', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6906', null, '国籍', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6914', null, '户口性质', 'Residency Type', '23', '0', '2', '', '0', '', '', '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6914', null, '最高学历', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6903', null, '状态', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6903', null, '员工状态', 'Employee Status', '25', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6911', null, '最高学历', 'Highest Education', '24', '13', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + "'6962', null, '出生年月（开始）', 'Birthday Start', '18', '13', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6961', null, '出生年月（结束）', 'Birthday End', '19', '0', '2', '', '0', '', null, '1', '', '2', '1'," + after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6960', null, '首次参加工作日期（开始）', 'Start Work Date Start', '20', '13', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         sqlScriptDTO_P.addSubSQLScript( searchDetailBefore + " '6959', null, '首次参加工作日期（结束）', 'start Work Date End', '21', '13', '2', '', '0', '', null, '1', '', '2', '1',"+ after );
         String sbDetailKey_P = insertDB( sqlScriptDTO_P );
         //员工列表
         SQLScriptDTO sqlScript_P = new SQLScriptDTO();
         sqlScript_P.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', '', '69', " + sbDetailKey_P+ ", '员工列表', 'Employee List', '10', '0', '1', '2', '1', 'In House', '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6923', null, '档案编号', 'Filing  No', '6', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6901', null, '员工中文名', 'Staff Name (ZH)', '8', '1', '3', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '2', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6900', null, '员工英文名', 'Staff Name (EN)', '9', '1', '4', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6926', null, '证件类型', 'Certificate Type', '7', '1', '5', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6927', null, '证件号码', 'Certificate Number', '15', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6903', null, '员工状态', 'Staff Status', '7', '1', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6954', null, '员工系统编号', 'Employee ID', '8', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '2', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ " ?,'6955', null, '修改人', 'Modify By', '8', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6956', null, '修改时间', 'Modify Date', '11', '1', '21', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6905', null, '婚姻状况', 'Marital status', '6', '1', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6904', null, '出生年月', 'Birthday', '7', '1', '11', '13', '2', '2', '', '0', '', '', '1', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6902', null, '称呼', 'Salutation', '4', '1', '9', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6907', null, '出生地', 'Birthday Place', '10', '1', '12', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6914', null, '户口性质', 'Residency Type', '6', '1', '13', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6908', null, '户籍地址', 'Residency Address', '20', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6911', null, '最高学历', null, '50', '2', '12', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6933', null, '邮箱', 'Email', '16', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6947', null, '所属部门', 'Branch', '5', '1', '18', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6948', null, '所属人', 'Owner', '5', '1', '19', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'6911', null, '最高学历', 'Highest Education', '6', '1', '15', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6931', null, '电话', 'Telephone number', '9', '1', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6932', null, '手机号码', 'Cellphone  number', '8', '1', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?,'7671', null, '职位名称', '', '12', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '2', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6954', null, '员工系统编号', '', '80', '2', '1', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6923', null, '员工工号', '', '80', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6901', null, '员工中文名', null, '100', '2', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6900', null, '员工英文名', '', '100', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6902', null, '称呼', null, '50', '2', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + " ?,'6931', null, '电话', '', '80', '2', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6932', null, '手机号码', null, '80', '2', '7', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6933', null, '邮箱', null, '80', '2', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6904', null, '出生年月', null, '50', '2', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6905', null, '婚姻状况', null, '50', '2', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6914', null, '户口性质', null, '80', '2', '11', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6916', null, '首次参加工作日期', null, '50', '2', '13', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?, '6963', null, '入司日期', null, '50', '2', '14', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6927', null, '身份证号码', null, '100', '2', '15', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6903', null, '员工状态', null, '80', '2', '16', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6965', null, '服务部门', null, '100', '2', '17', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore+ "?,'6967', null, '职位名', null, '100', '2', '18', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6966', null, '用户名', null, '100', '2', '19', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1'," + after );
         sqlScript_P.addSubSQLScript( listDetailBefore + "?, '6912', null, '档案编号', '', '', '1', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_P );

         //劳动合同 - 内部
         SQLScriptDTO sqlScriptDTO_Q = new SQLScriptDTO();
         sqlScriptDTO_Q.setSqlScript( searchHeaderBefore + "" + accountId + ", " + corpId+ ", '71', '2', null, '劳动合同 - 内部', 'Labor Contract - In House', null, 'In House', '1', '1', " + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7103', null, '劳动合同名称（英文）', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7102', null, '劳动合同名称（中文）', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1',"+ after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7105', null, '合同状态', 'Employee Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7100', null, '员工ID', 'Employee ID', '9', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7106', null, '帐套ID', 'Order ID', '4', '13', '1', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7121', null, '劳动合同ID', 'Labor Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', " + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7122', null, '员工姓名（中文）', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + " '7123', null, '员工姓名（英文）', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7131', null, '开始日期（起始）', 'Start Date (From)', '5', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7132', null, '开始日期（截止）', 'Start Date (To)', '6', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7133', null, '结束日期（起始）', 'End Date (From)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7134', null, '结束日期（截止）', 'End Date (To)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1'," + after );
         sqlScriptDTO_Q.addSubSQLScript( searchDetailBefore + "'7126',   NULL, '雇佣状态',    'Employee Status', '11',  '13',' 2', NULL, '0', NULL, NULL, '1', NULL, '1', '1', " + after );
         String sbDetailKey_Q = insertDB( sqlScriptDTO_Q );
         //劳动合同列表 - 内部
         SQLScriptDTO sqlScript_Q = new SQLScriptDTO();
         sqlScript_Q.setSqlScript( listHeaderBefore + "" + accountId + ", " + corpId + ", '0', '2', null, '71', " + sbDetailKey_Q + ", '劳动合同列表 - 内部', 'Labor Contract - In House', '10', '0', '1', '2', '1', 'In House', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7104', null, '合同编号', 'contractNo', '10', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', null, '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7105', null, '合同状态', 'Status', '4', '1', '13', '13', '1', '2', '', '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7121', null, '劳动合同ID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&comeFrom=2', '1', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7100', null, '员工ID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', '', null, '1', null, '1', '1', '', '1', '1',"+ after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7122', null, '姓名（中文）', 'Employee Name ZH', '8', '1', '3', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?, '7123', null, '姓名（英文）', 'Employee Name EN', '8', '1', '4', '13', '2', '2', '', '0', '', '', null, null, '1', null, '1', '1', '', '2', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7128', null, '修改人', 'Modify BY', '8', '1', '14', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7129', null, '修改时间', 'Modify By', '7', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?,'7130', null, '劳动合同名称', 'Contract Name', '18', '1', '7', '13', '2', '2', '', '0', '', '', null, null, '1', null, '2', '1', '', '2', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7106', null, '账套ID', 'Order ID', '7', '1', '16', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedOrderId', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7126', null, '雇佣状态', 'Employee Status', '4', '1', '11', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7113', null, '开始日期', 'Start Date', '7', '1', '8', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7114', null, '结束日期', 'End Date', '7', '1', '9', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '1', null, '操作', 'Handle', '4', '1', '17', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore + "?, '7107', null, '签约主体', 'Legal entity', '14', '1', '5', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?, '7120', null, '排班情况', 'Shift', '8', '1', '10', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+ "?,'7125', null, '离职日期', 'Resign Date', '8', '1', '12', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB( sqlScript_Q );

         //商保单
         SQLScriptDTO sqlScript_S = new SQLScriptDTO();
         sqlScript_S.setSqlScript( listHeaderBefore + "" + accountId+ ", "+corpId+", '0', '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '商保单', 'Commercial Benefit List', '10', '0', '1', '2', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'employeeId', '员工ID', 'Staff Id', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?, null, 'employeeNameZH', '姓名', 'Name', '10', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?, null, 'contractId', '劳动合同ID', 'Labor Contract ID', '10', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'employStatus', '员工状态', 'Staff Status', '10', '1', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?, null, 'monthly', '费用月份', 'Cost Month', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?, null, 'employeeCBNameZH', '商保名称', 'Commercial Benefit Name', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+ after );
         sqlScript_S.addSubSQLScript( listDetailBefore+ "?,null, 'status', '状态', 'Status', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2'," + after );
         sqlScript_S.addSubSQLScript( listDetailBefore + "?,null, 'cbStatus', '商保状态', 'Commercial Benefit  Status', '10', '1', '99', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         //商保单主键
         String sbdkey = insertDB( sqlScript_S );
         //商保单明细
         SQLScriptDTO sqlScript_SD = new SQLScriptDTO();
         sqlScript_SD.setSqlScript( listHeaderBefore + "" + accountId + ", "+corpId+", " + sbdkey+ ", '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '商保单 - 明细', 'Commercial Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_83', '基本医疗保险', 'Jiben Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_84', '综合医疗保险', 'Zonghe Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_85', '补充工伤保险', 'Buchong Gongshang', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore + "?, null, 'item_81', '第三者责任险', 'Disanzhe', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         sqlScript_SD.addSubSQLScript( listDetailBefore+ "?, null, 'item_86', '特殊补充', 'Teshu Buchong', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1'," + after );
         insertDB( sqlScript_SD );
         
         //工资单报表
         SQLScriptDTO sqlScriptDTO_T = new SQLScriptDTO();
         sqlScriptDTO_T.setSqlScript(  searchHeaderBefore + "" + accountId + ", " + corpId+ ", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO.Report', '工资单报表', 'Payslip Report', null, null, '1', '1', " + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "  null, 'employeeId', '员工ID', 'Staff ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "   null, 'employeeNameZH', '员工姓名（中文）', 'Staff Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         sqlScriptDTO_T.addSubSQLScript( searchDetailBefore + "   null, 'employeeNameEN', '员工姓名（英文）', 'Staff Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1'," + after );
         String sbDetailKey_T = insertDB( sqlScriptDTO_T );
         SQLScriptDTO sqlScript_T = new SQLScriptDTO();
         //工资报表
         sqlScript_T.setSqlScript( listHeaderBefore+" "+accountId+", "+corpId+", '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO.Report', '0', '"+sbDetailKey_T+"', '工资报表', 'Payslip Report', '10', '0', '1', '2', '1', 'payslip', '1', '1',"+after );
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL, 'employeeId',  '员工ID',  'Staff Id', 80,   2, 1, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'employeeNameZH', '员工姓名（中文）', 'Staff Name( CH )',  100,  2, 2, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?,NULL,'employeeNameEN', '员工姓名（英文）', 'Staff Name( EN )',  100,  2, 3, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'certificateNumber', '证件号码',  'ID Number',   150,  2, 4, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?,NULL,'bankName', '银行名称',  'Bank Name',   150,  2, 5, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'bankAccount', '银行账户',  'Bank Account',   150,  2, 6, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'monthly',  '薪酬月份',  'Pay Monthly', 60,   2, 7, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'orderId',  '结算规则ID',   'Order ID', 80,   2, 8, 13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempBranchIds',  '工作部门',  'Work Branch', 100,  2, 9, 13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempPositionIds',   '职位名称',  'Position Name',  100,  2, 10,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'tempParentBranchIds',  '上级部门',  'Parent Branch',  100,  2, 11,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'contractId',  '劳动合同ID',   'Contract ID', 80,   2, 12,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'beforeTaxSalary',   '应发工资',  'Accrued Wages',  150,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'taxAmountPersonal', '个税', 'Personal Income Tax',  150,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'afterTaxSalary', '实发工资',  'Real Wages',  100,  2, 99,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL,'status',   '状态', 'Status',   60,   2, 99,   13,   1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1,"+after);
         sqlScript_T.addSubSQLScript( listDetailBefore +"?, NULL, 'entity', '法务实体', 'Entity', 100, 2, 8, 13, 1, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1, "+after);
         String list_T=insertDB( sqlScript_T );
         //工资报表 - 明细
         SQLScriptDTO sqlScript_U = new SQLScriptDTO();
         sqlScript_U.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  "+list_T+",   1, 'com.kan.hro.domain.biz.payment.PayslipDTO.Report',   0, 0, '工资报表 - 明细',   'Payslip Report - Detail', 10,   0, 1, 2, 0, NULL, 1, 1,"+after );
         sqlScript_U.addSubSQLScript( listDetailBefore +" ?,NULL, 'item_1',   '基本工资',  'Base Salary', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_2',   '工资调整',  'Salary Adjustment', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_4',   '其它扣款',  'Other Withhold', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_5',   '个税', 'Income Tax',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_6',   '岗位津贴',  'Position Allowance',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_7',   '高温津贴',  'High Temperature Allowance', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_8',   '驻外津贴',  'Travel Allowance',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_9',   '其他津贴',  'Other Allowance',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_10',  '夜班补贴',  'Night Shift Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_11',  '交通补贴',  'Transportation Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_12',  '用餐补贴',  'Meat Subsidy',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_13',  '其他补贴',  'Other Subsidy',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_14',  '福利费',   'Welfare',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_15',  '绩效', 'Performance', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_16',  '奖金', 'Bonus', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_17',  '佣金', 'Commission',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_18',  '年终奖',   'Annual Bonus',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_21',  '加班 1.0',   'OT 1.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_22',  '加班 1.5',   'OT 1.5',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_23',  '加班 2.0',   'OT 2.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_24',  '加班 3.0',   'OT 3.0',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_25',  '加班换休',  'OT Shift', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +" ?, NULL, 'item_26',  '其他加班',  'OT Other', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_31',  '报销', 'Reimbursement',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_32',  '报销 - 交际费', 'Reimbursement - Entertainment', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_33',  '报销 - 加班',  'Reimbursement - OT',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_41',  '年假', 'Annual Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_42',  '病假 - 全薪',  'Sick Leave',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_43',  '病假 - 非全薪（70%）',  'Sick Leave - 70%',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_44',  '事假', 'Absence',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_45',  '产假', 'Maternity Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_46',  '护理假',   'Nursing Leave',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_47',  '丧假', 'Bereft Leave',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_61_c',   '养老保险-公司',  'Endowment - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_62_c',   '医疗保险-公司',  'Medical - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_63_c',   '失业保险-公司',  'Unemployment - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_64_c',   '工伤保险-公司',  'Injury - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_65_c',   '生育保险-公司',  'Maternity - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_66_c',   '残疾人保障金-公司',   'Disable - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_67_c',   '住房公积金-公司', 'Housing - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_68_c',   '取暖费-公司',   'Warming - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_69_c',   '调配费-公司',   'Allocation - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_70_c',   '档案管理费-公司', 'Archive - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_71_c',   '住院医疗保险-公司',   'Zhuyuan Yiliao - Company',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_72_c',   '补充医疗保险-公司',   'Buchong Yiliao - Company',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_73_c',   '大病医疗保险-公司',   'Dabing Yiliao - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_74_c',   '异地农村医保-公司',   'Yidi Nongcun - Company',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_75_c',   '医疗共济金-公司', 'Medical Aid Funding - Company', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_61_p',   '养老保险-个人',  'Endowment - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_62_p',   '医疗保险-个人',  'Medical - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_63_p',   '失业保险-个人',  'Unemployment - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_64_p',   '工伤保险-个人',  'Injury - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_65_p',   '生育保险-个人',  'Maternity - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?, NULL, 'item_66_p',   '残疾人保障金-个人',   'Disable - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_67_p',   '住房公积金-个人', 'Housing - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_68_p',   '取暖费-',  'Warming - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_69_p',   '调配费-',  '  Allocation - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_70_p',   '档案管理费-个人', 'Archive - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_71_p',   '住院医疗保险-个人',   'Zhuyuan Yiliao - Personal',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_72_p',   '补充医疗保险-个人',   'Buchong Yiliao - Personal',  150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_73_p',   '大病医疗保险-个人',   'Dabing Yiliao - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_74_p',   '异地农村医保-个人',   'Yidi Nongcun - Personal', 150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_75_p',   '医疗共济金-个人', 'Medical Aid Funding - Personal',   150,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_130', '服务费（含税）',  'ervice Fee (I-Tax)',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_131', '服务费（含税） - 订金',   'Service Fee (I-Tax) - Prepay',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_132', '服务费（含税） - 违约金',  'Service Fee (I-Tax) - Cancelation',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,NULL, 'item_133', '服务费（含税）- 其他   ', 'Service Fee (I-Tax) - Other',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_134', '培训费（含税）',  'Training Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_135', '招聘费（含税）',  'Recruitment Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_136', '代理费（含税）',  'Agent Fee (I-Tax)', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_141', '营业税',   'Tax',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_142', '入职体检',  'Onboard Physical Examination',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_143', '年度体检',  'Annual Physical Examination',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_144', '社保卡办卡费',   'SB Card Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_145', '医保卡办卡费',   'Medical Card Fee',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_146', '工资卡办卡费',   'Salary Card Fee',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_147', '工会费',   'Labour Union Fee',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_148', '滞纳金',   'Overdue Fine',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_149', '手续费',   'Poundage', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_150', '工本费',   'Paper Work Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_151', '互助金',   'Each Other Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_161', '社保管理费', 'Social Benefit Management Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_162', '第三方管理费',   '3rd Part Management Fee', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_181', '工会基金',  'Labour Union Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_182', '工伤基金',  'Injury Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_183', '生育基金',  'Maternity Fund', 100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_184', '残障基金',  'Disable Fund',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,   NULL, 'item_185', '替补基金',  'Replace Fund',   100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_186', '风险基金',  'VC Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_187', '全责基金',  'Full Liability Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         sqlScript_U.addSubSQLScript( listDetailBefore +"?,  NULL, 'item_188', '其他基金',  'Other Fund',  100,  2, 50,   13,   2, 2, NULL, 0, NULL, NULL, 0, 0, 3, 0, 2, 1, NULL, 1, 1, "+after);
         insertDB( sqlScript_U );
         
         //社保花名册
         SQLScriptDTO sqlScript_SBSetting = new SQLScriptDTO();
         sqlScript_SBSetting.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  '0', '2', '', '37', '0', '社保花名册', 'SB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after );
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3700', null, '合同ID', 'Contract ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3719', null, '序号ID', 'Employee SB ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3723', null, '员工ID', 'Employee ID', '80', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3724', null, '员工姓名（中）', 'Employee Name(ZH)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',  "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3725', null, '员工姓名（英）', 'Employee Name(EN)', '120', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3701', null, '社保公积金方案', 'SB', '120', '2', '11', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3706', null, '加保日期', 'Start Date', '120', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3707', null, '退保日期', 'End Date', '120', '2', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3703', null, '社保公积金状态', 'SB Status', '80', '2', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3730', null, '结算规则名称', 'Order Description', '150', '2', '19', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3729', null, '合同状态', 'Contract Status', '80', '2', '21', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3726', null, '身份证号码', 'Certificate Number', '150', '2', '23', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
//         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3731', null, '客户ID', 'Client ID', '80', '2', '25', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_SBSetting );
         
         //商保花名册
         SQLScriptDTO sqlScript_CBSetting = new SQLScriptDTO();
         sqlScript_CBSetting.setSqlScript( listHeaderBefore+""+accountId+",   "+corpId+",  '0', '2', '', '39', '0', '商保花名册', 'CB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after );
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3912', null, '序号ID', 'CB ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3914', null, '员工ID', 'Employee ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3915', null, '员工姓名（中）', 'Employee Name(ZH)', '120', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3916', null, '员工姓名（英）', 'Employee Name(EN)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',  "+after);
//         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3921', null, '客户ID', 'Client ID', '80', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3913', null, '结算规则ID', 'Order ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3900', null, '劳动合同ID', 'Contract ID', '80', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3920', null, '劳动合同状态', 'Contract Status', '80', '2', '15', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
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
    * 数据持久化操作
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
            // 获取主键
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
    * 添加主从表 返回主表主键
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
            // 获取主键
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
