package com.kan.base.service.impl.system;

import com.kan.base.core.ContextService;
import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.system.PlatFromAccountService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlatFormAccountServiceImpl extends ContextService implements PlatFromAccountService
{

   private ModuleDao moduleDao;

   private AccountModuleRelationDao accountModuleRelationDao;

   private static String accountId = null;

   private static String userId = null;
   
   // 获得数据库连接实例
   Connection connection = null;
  
   private void getConnection() throws SQLException{
         // 获取连接池的连接
         final DataSource dataSource = ( DataSource ) ServiceLocator.getService( "dataSource" );
         connection=dataSource.getConnection();
   }
   
   private void closeConnection() throws SQLException{
      if(!connection.isClosed()){
         connection.close();
      }
   }
   
   public String getDate(){
      return KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" );
   }

   @Override
   public boolean initAccount( final AccountVO accountVO ) throws KANException
   {
      try
      {
         getConnection();
         connection.setAutoCommit( false );
         if ( accountVO != null && accountVO.getInitialized().trim().equalsIgnoreCase( accountVO.FALSE ) )
         {
            int flag=0;
            // 开启事务
            accountId = accountVO.getAccountId();
            flag = flag+createStaff();
            flag = flag+createOptions();
            flag = flag+createEmailConfiguration();
            flag = flag+createShareFolderConfiguration();
            
            flag = flag+createSearch();
            // 设置初始化状态
            flag = flag+updateAccount();
            createRuleAndRight("1");
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
   
   
   private int createRuleAndRight(final String groupId){
      try
      {
         String primaryKey="";
         List<Object> list=accountModuleRelationDao.getAccountModuleRelationVOsByAccountId( accountId );
        if(list!=null&&list.size()>0){
           for ( Object object : list )
           {
             AccountModuleRelationVO accountModuleRelationVO=(AccountModuleRelationVO)object;
             ModuleVO moduleVO= moduleDao.getModuleVOByModuleId( accountModuleRelationVO.getModuleId() );
             //添加hro_sec_group_module_right_relation
             if(moduleVO.getRightIdArray()!=null&&moduleVO.getRightIdArray().length>0){
                for ( String rightId : moduleVO.getRightIdArray() )
               {
                   StringBuffer sb=new StringBuffer();
                   sb.append( "insert into HRO_SEC_Group_Module_Right_Relation" );
                   sb.append( "(groupId, moduleId, rightId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
                   sb.append( "VALUES" );
                   sb.append( "("+groupId+", "+moduleVO.getModuleId()+", "+rightId+", 1, 1, '', '', '', '','', "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
                   final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
                   HeaderpreparedStatement.executeUpdate();
               }
             }
             //添加hro_sec_group_module_rule_relation
             if(moduleVO.getRuleIdArray()!=null&&moduleVO.getRuleIdArray().length>0){
                for ( String  ruleId : moduleVO.getRuleIdArray() )
               {
                   StringBuffer sb=new StringBuffer();
                   sb.append( "insert into HRO_SEC_Group_Module_Rule_Relation" );
                   sb.append( "(groupId, moduleId, ruleId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
                   sb.append( "VALUES" );
                   sb.append( "("+groupId+", "+moduleVO.getModuleId()+", "+ruleId+", 1, 1, null, null, null, null, null, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
                   final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
                   HeaderpreparedStatement.executeUpdate();
               }
             }
           }
        }
         
         int flag=createUser(primaryKey);
         flag=flag+createPosition(primaryKey);
         return  flag;
      }
      catch ( KANException e )
      {
         System.out.println("create createRuleAndRight is error!");
         e.printStackTrace();
         return 1;
      }
      catch ( SQLException e )
      {
         System.out.println("create createRuleAndRight is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
   // 初始化选项设置
   private int updateAccount()
   {
      try
      {
         StringBuffer sb=new StringBuffer();
         sb.append( "update HRO_SYS_Account " );
         sb.append( "set initialized=1,modifyBy = "+userId+", modifyDate = '"+getDate()+"'" );
         sb.append( "where  " );
         sb.append( "accountId = "+accountId );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString() );
         HeaderpreparedStatement.executeUpdate();
         return  0;
      }
      catch ( SQLException e )
      {
         System.out.println("create HRO_MGT_Options is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
   //创建staff--> user
   private int createStaff(){
      try
      {
         String primaryKey="";
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO hro_sec_staff" );
         sb.append( "(accountId, corpId, staffNo, employeeId, salutation, nameZH, nameEN, birthday, bizPhone, bizExt, personalPhone, bizMobile, personalMobile, otherPhone, fax, bizEmail, personalEmail, certificateType, certificateNumber, maritalStatus, registrationHometown, registrationAddress, personalAddress, personalPostcode, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES" );
         sb.append( "("+accountId+",null, null,null, null, 'Admin', 'Admin', '"+getDate()+"',null, null, null, null,  null, null, null,'kevin.jin@kangroup.com.cn', null, 1,null, 1, null, null, null, null, null,1,1,null, null, null, null, null,  "+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         int flag=createUser(primaryKey);
         flag=flag+createPosition(primaryKey);
         return  flag;
      }
      catch ( SQLException e )
      {
         System.out.println("create staff is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
  
   // 创建user
   private int createUser(final String staffId)
   {
      try
      {
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_User" );
         sb.append( "(accountId,corpId, staffId,   username, password, bindIP, lastLogin,   lastLoginIP,userIds, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",null,"+staffId+",'Admin', 'taWQWR4=','', '"+getDate()+"','',     null,    1,1,null, null, null, null, null,  'System','"+getDate()+"', 'System', '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            userId = rs.getString( 1 );
         }
         return  0;
      }
      catch ( SQLException e )
      {
         System.out.println("create user is error!");
         e.printStackTrace();
         return 1;
      }
   }

   //创建Position
   private int createPosition(final String staffId){
      try
      {
         String primaryKey="";
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Position" );
         sb.append( "(accountId,corpId, locationId, branchId, positionGradeId, titleZH, titleEN, description, skill, note, attachment, parentPositionId, isVacant,isIndependentDisplay ,needPublish, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",null, 0, 0, 0, '系统管理员', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         //创建HRO_SEC_Position_Staff_Relation
         StringBuffer sr=new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Staff_Relation" );
         sr.append( "(positionId, staffId, staffType, agentStart, agentEnd, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,  createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "("+primaryKey+","+staffId+",1,null,null,null,1,1,null,null,null,null,null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();
            
         int flag=createGroup(primaryKey);
         
         return  flag;
      }
      catch ( SQLException e )
      {
         System.out.println("create Position or Position_Staff_Relation is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
   
   
   private int createGroup(final String positionId){
      try
      {  
         //创建Group
         String primaryKey="";
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Group" );
         sb.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",null, '系统管理组','System Management Team', 2,'Easy Staffing',1, 1, null, null, null, null, null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // 获取主键
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         //创建PositionGroupRelation
         StringBuffer sr=new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         sr.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "("+positionId+","+primaryKey+",1,1,null,null,null,null,null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();
         //group 规则
         createRuleAndRight(primaryKey);
         return  0;
      }
      catch ( SQLException e )
      {
         System.out.println("create Group or Position_Group_Relation is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
   // 初始化选项设置
   private int createOptions()
   {
      try
      {
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_MGT_Options" );
         sb.append( "(accountId, branchPrefer,positionPrefer,language, useBrowserLanguage, dateFormat, timeFormat, accuracy, round, pageStyle, smsGetway,logoFile,logoFileSize,mobileModuleRightIds, orderBindContract, sbGenerateCondition, cbGenerateCondition, settlementCondition, sbGenerateConditionSC, cbGenerateConditionSC, settlementConditionSC, independenceTax,createBy, createDate, modifyBy,  modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",0,0,1, 1, 1, 1,3, 1, 2,1, null, null, null, 2, 1, 1, 1,1, 1,  1,1, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return  0;
      }
      catch ( SQLException e )
      {
         System.out.println("create HRO_MGT_Options is error!");
         e.printStackTrace();
         return 1;
      }
   }

   // 初始化邮件设置
   private int createEmailConfiguration()
   {
      try
      {
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_MGT_Email_Configuration" );
         sb.append( "(accountId, showName, mailType, sentAs, accountName, smtpHost, smtpPort, username, password, smtpAuthType, smtpSecurityType, pop3Host, pop3Port, deleted, status, remark1, remark2, createBy, createDate, modifyBy, modifyDate )" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+", 'KANgroup Notice', 2, 'system@kangroup.com.cn', null,'smtp.kangroup.com.cn', '25', 'system@kangroup.com.c', 'Kangroup2014',1,  0,'pop3.kangroup.com.cn','110', 1, 1, null, null, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return  0;
      }
      catch ( SQLException e )
      {
         System.out.println("create HRO_MGT_Email_Configuration is error!");
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
         sb.append( "(" + accountId + ", '10.11.70.152', null, null, null, '/Samba/KANHRO/', 1, 1,null, null, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return 0;
      }
      catch ( SQLException e )
      {
         System.out.println( "create HRO_MGT_Share_Folder_Configuration is error!" );
         e.printStackTrace();
         return 1;
      }
   }

    //初始化Search设置
   public int createSearch()
   {
      // 初始化SQL集
      List< SQLScriptDTO > sqlScripts = new ArrayList< SQLScriptDTO >();

      //用户
      SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
      sqlScriptDTO.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '1', '2', '', '用户', 'Staff', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '105', null, '员工编号', 'Staff Code', '1', '13', '2', '', '0', '', '100_200', '1', ' ', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '101', null, '员工姓名（中文）', 'Staff Name (Chinese)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '102', null, '员工姓名（英文）', 'Staff Name (English)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '104', null, '员工状态', 'Staff Status', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO );
      //供应商
      SQLScriptDTO sqlScriptDTO_A = new SQLScriptDTO();
      sqlScriptDTO_A.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '3', '2', '', '供应商', 'Vendor', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '300', null, '供应商名称（中文）', 'Vendor Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '301', null, '供应商名称（英文）', 'Vendor Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '302', null, '供应商类型', 'Vendor Type', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '316', null, '省份 - 城市', 'Province', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '312', null, '法务实体', 'Legal Entity', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,   '313', null, '所属部门', 'Branch', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '314', null, '所属人', 'Owner', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '315', null, '状态', 'Stauts', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_A );
      //供应商联系人
      SQLScriptDTO sqlScriptDTO_B = new SQLScriptDTO();
      sqlScriptDTO_B.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '4', '2', '', '供应商联系人', 'Vendor Contact', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '402', null, '联系人姓名（中文）', 'Contact Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'403', null, '联系人姓名（英文）', 'Contact Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'404', null, '称呼', 'Salutation', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'423', null, '状态', 'Status', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'407', null, '省份 - 城市', 'CIty', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'424', null, '所属部门', 'Branch', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'425', null, '所属人', 'Owner', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_B );
      //集团
      SQLScriptDTO sqlScriptDTO_C = new SQLScriptDTO();
      sqlScriptDTO_C.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '5', '2', null, '集团', 'Client Group', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '500', null, '集团编号', 'Number', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'501', null, '集团名称（中文）', 'Client Group NameZH', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '502', null, '集团名称（英文）', 'Client Group NameEN', '13', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '504', null, '状态', 'Status', '15', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_C );
      //客户
      SQLScriptDTO sqlScriptDTO_D = new SQLScriptDTO();
      sqlScriptDTO_D.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '6', '2', null, '客户', 'Client', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'609', null, '财务编码', 'number', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'610', null, '客户名称（中文）', 'ClientNameZH', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '611', null, '客户名称（英文）', 'ClientNameZH', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '641', null, '集团名称', 'GroupName', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'632', null, '客户ID', 'Client ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'605', null, '行业', 'Industry', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '606', null, '规模', 'Size', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '600', null, '法务实体', 'Entity', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '602', null, '所属部门', 'Branch', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '601', null, '所属人', 'Owner', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'612', null, '状态', 'Status', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'633', null, '集团编号', 'Group Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_D );
      //客户联系人
      SQLScriptDTO sqlScriptDTO_E = new SQLScriptDTO();
      sqlScriptDTO_E.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '13', '2', null, '客户联系人', 'Client Contact', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1305', null, '联系人姓名（中文）', 'nameZH', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1306', null, '联系人姓名（英文）', 'nameEN', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1323', null, '状态', 'status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1302', null, '客户ID', 'Client ID', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1308', null, '部门', 'Department', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1307', null, '职位', 'Title', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1333', null, '客户编号', 'Client Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1334', null, '客户名称（中文）', 'Client Name(ZH)', '10', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1335', null, '客户名称（英文）', 'Client Name(EN)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_E );
      //账单地址
      SQLScriptDTO sqlScriptDTO_F = new SQLScriptDTO();
      sqlScriptDTO_F.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '14', '2', null, '账单地址', 'Client Invoice', null, '', '1', '1', null, null, null, null, null,"
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1401', null, '发票台头', 'Title', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1402', null, '中文名（收件人）', 'NameZH', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1403', null, '英文名（收件人）', 'NameEN', '5', '0', '2', '', '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1417', null, '状态', 'Status', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1430', null, '客户ID', 'Client ID', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1433', null, '客户编号', 'Client Number', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1407', null, '地址', 'Address', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1434', null, '客户名称（中文）', 'Client Name(ZH)', '13', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1435', null, '客户名称（英文）', 'Client Name(EN)', '14', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_F );
      //雇员 - 基本信息
      SQLScriptDTO sqlScriptDTO_G = new SQLScriptDTO();
      sqlScriptDTO_G.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '17', '2', null, '雇员 - 基本信息', 'Employee', null, '', '1', '1', null, null, null, null, null,"
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1760', null, '雇员ID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1725', null, '雇员编号', 'Employee NO', '2', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1701', null, '雇员姓名（中文）', 'Employee Name (ZH)', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1700', null, '雇员姓名（英文）', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1714', null, '档案编号', 'Record No', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1728', null, '证件类型', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1729', null, '证件号码', 'Certificate Number', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1705', null, '婚姻状况', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1773', null, '出生年月（开始）', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1772', null, '出生年月（结束）', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1771', null, '首次参加工作日期（开始）', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1770', null, '首次参加工作日期（结束）', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1706', null, '国籍', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1716', null, '户口性质', 'Residency Type', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1713', null, '最高学历', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1703', null, '状态', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_G );
      //商务合同
      SQLScriptDTO sqlScriptDTO_H = new SQLScriptDTO();
      sqlScriptDTO_H.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '18', '2', null, '商务合同', 'Contract', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1800', null, '客户ID', 'Client ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1802', null, '法务实体', 'entityId', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1805', null, '合同编号', 'Contract Number', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1810', null, '合同名称（中文）', 'Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1811', null, '合同名称（英文）', 'Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1818', null, '状态', 'Status', '11', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1820', null, '合同ID', 'Client Contact ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1821', null, '财务编码', 'Client Number', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1822', null, '客户名称（中文）', 'Client Name(ZH)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1823', null, '客户名称（英文）', 'Client Name(EN)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1803', null, '业务类型', 'Business Type', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_H );
      //劳动合同
      SQLScriptDTO sqlScriptDTO_I = new SQLScriptDTO();
      sqlScriptDTO_I.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '24', '2', null, '劳动合同', 'Labor Contract', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2403', null, '劳动合同名称（英文）', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2402', null, '劳动合同名称（中文）', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2405', null, '合同状态', 'Employee Status', '12', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2400', null, '雇员ID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2442', null, '劳动合同ID', 'Labor Contract ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2443', null, '雇员姓名（中文）', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2444', null, '雇员姓名（英文）', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2451', null, '开始日期（起始）', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2452', null, '开始日期（截止）', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2453', null, '结束日期（起始）', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2454', null, '结束日期（截止）', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_I );

      //订单
      SQLScriptDTO sqlScriptDTO_J = new SQLScriptDTO();
      sqlScriptDTO_J.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '28', '2', null, '订单', 'Order', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2828', null, '状态', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2806', null, '法务实体', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2808', null, '业务类型', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2836', null, '订单ID', 'Order ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2802', null, '客户ID', 'Client ID', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2804', null, '合同ID', 'Contract ID', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2812', null, '订单开始日期', 'Order Start Date', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2813', null, '订单结束日期', 'Order End Date', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2826', null, '所属部门', 'Branch', '31', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2827', null, '所属人', 'Owner', '33', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2844', null, '客户名称（中文）', 'Client Name(ZH)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2845', null, '客户名称（英文）', 'Client Name(EN)', '12', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2853', null, '财务编码', 'Client Number', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_J );
      //派送协议
      SQLScriptDTO sqlScriptDTO_K = new SQLScriptDTO();
      sqlScriptDTO_K.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '43', '2', null, '派送协议', 'Service Contract', null, '', '1', '1', '', '', '', '', '', "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4305', null, '协议状态', 'Contract Service Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4311', null, '客户ID', 'Client ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4303', null, '派送协议名称（英文）', 'Service Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4302', null, '派送协议名称（中文）', 'Service Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4300', null, '雇员ID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4340', null, '派送协议ID', 'Service Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4341', null, '雇员姓名（中文）', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4342', null, '雇员姓名（英文）', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4356', null, '开始日期（起始）', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4357', null, '开始日期（截止）', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4358', null, '结束日期（起始）', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4359', null, '结束日期（截止）', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4361', null, '财务编码', 'Client Number', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_K );
      //工资单
      SQLScriptDTO sqlScriptDTO_L = new SQLScriptDTO();
      sqlScriptDTO_L.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '工资单', 'Payslip', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_L );
      //个税
      SQLScriptDTO sqlScriptDTO_M = new SQLScriptDTO();
      sqlScriptDTO_M.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '个税', 'Income Tax ', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_M );
      //帐套
      SQLScriptDTO sqlScriptDTO_N = new SQLScriptDTO();
      sqlScriptDTO_N.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '78', '2', '', '帐套', 'Order', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7852', '', '状态', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'7806', '', '法务实体', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7808', '', '业务类型', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7836', '', '帐套ID', 'Order ID', '1', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7812', '', '订单开始日期', 'Order Start Date', '17', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7813', '', '订单结束日期', 'Order End Date', '19', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7826', '', '所属部门', 'Branch', '31', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7827', '', '所属人', 'Owner', '33', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_N );
      //社保单
      SQLScriptDTO sqlScriptDTO_O = new SQLScriptDTO();
      sqlScriptDTO_O.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '社保单', 'SB Bill', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '雇员ID', 'Employee ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '雇员姓名（中文）', 'Employee Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '雇员姓名（英文）', 'Employee Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '派送协议ID', 'Labor Contract ID', '4', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '客户ID', 'Client ID', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNo', '财务编码', 'Client No', '6', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '客户名称（中文）', 'Client Name(ZH)', '7', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '客户名称（英文）', 'Client Name(EN)', '8', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'vendorNameZH', '供应商名称', 'Vendor Name', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_O );
      //结算单
      SQLScriptDTO sqlScriptDTO_P = new SQLScriptDTO();
      sqlScriptDTO_P.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.settlement.OrderDTO', '结算单', 'Settlement', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '客户ID', 'Client ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameZH', '客户名称（中文）', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '客户名称（英文）', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'orderId', '订单ID', 'Order ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNO', '财务编码', 'clientNO', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_P );
      //结算明细
      SQLScriptDTO sqlScriptDTO_Q = new SQLScriptDTO();
      sqlScriptDTO_Q.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.settlement.SettlementDTO', '结算明细', 'Order Bill Detail', null, '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '合同ID', 'Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '客户名称（中文）', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '客户名称（英文）', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_Q );
      //插入数据库
      if(insertDB(sqlScripts))
         return 0;
      else
         return 1;
   }
   /**
    * 数据持久化操作
    * 
    * @author : Ian.huang
    * @date   : 2014-6-4
    * @param  : @param sqlScripts
    * @return : void
    */
   private boolean insertDB(final List< SQLScriptDTO > sqlScripts){
      try
      {
         for ( SQLScriptDTO sqlScriptDTO2 : sqlScripts )
         {  
            String primaryKey="";
            final PreparedStatement HeaderpreparedStatement = connection.prepareStatement(sqlScriptDTO2.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS);
            HeaderpreparedStatement.executeUpdate();
            // 获取主键
            final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
            if ( rs.next() )
            {
               primaryKey = rs.getString( 1 );
            }
            if(sqlScriptDTO2.getSubSQLScriptDTOs()!=null&&sqlScriptDTO2.getSubSQLScriptDTOs().size()>0){
               for ( SQLScriptDTO sqlScriptDTO : sqlScriptDTO2.getSubSQLScriptDTOs() )
               {
                  final PreparedStatement DetailpreparedStatement = connection.prepareStatement(sqlScriptDTO.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS);
                  if(primaryKey!=""&&sqlScriptDTO.getSqlScript().contains( "?" )){
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
}
