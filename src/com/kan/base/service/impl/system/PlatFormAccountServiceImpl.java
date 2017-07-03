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
   
   // ������ݿ�����ʵ��
   Connection connection = null;
  
   private void getConnection() throws SQLException{
         // ��ȡ���ӳص�����
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
            // ��������
            accountId = accountVO.getAccountId();
            flag = flag+createStaff();
            flag = flag+createOptions();
            flag = flag+createEmailConfiguration();
            flag = flag+createShareFolderConfiguration();
            
            flag = flag+createSearch();
            // ���ó�ʼ��״̬
            flag = flag+updateAccount();
            createRuleAndRight("1");
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
             //���hro_sec_group_module_right_relation
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
             //���hro_sec_group_module_rule_relation
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
   
   // ��ʼ��ѡ������
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
   
   //����staff--> user
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
         // ��ȡ����
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
   
  
   // ����user
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
         // ��ȡ����
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

   //����Position
   private int createPosition(final String staffId){
      try
      {
         String primaryKey="";
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Position" );
         sb.append( "(accountId,corpId, locationId, branchId, positionGradeId, titleZH, titleEN, description, skill, note, attachment, parentPositionId, isVacant,isIndependentDisplay ,needPublish, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",null, 0, 0, 0, 'ϵͳ����Ա', 'System Administrator', null,null, null, null, 0, 2, null,2,1, 1, null, null, null, null, null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         //����HRO_SEC_Position_Staff_Relation
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
         //����Group
         String primaryKey="";
         StringBuffer sb=new StringBuffer();
         sb.append( "INSERT INTO HRO_SEC_Group" );
         sb.append( "(accountId, corpId, nameZH, nameEN, hrFunction, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
         sb.append( "VALUES " );
         sb.append( "("+accountId+",null, 'ϵͳ������','System Management Team', 2,'Easy Staffing',1, 1, null, null, null, null, null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         // ��ȡ����
         final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
         if ( rs.next() )
         {
            primaryKey = rs.getString( 1 );
         }
         //����PositionGroupRelation
         StringBuffer sr=new StringBuffer();
         sr.append( "INSERT INTO HRO_SEC_Position_Group_Relation" );
         sr.append( "(positionId, groupId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate )" );
         sr.append( "VALUES " );
         sr.append( "("+positionId+","+primaryKey+",1,1,null,null,null,null,null,"+userId+",'"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement preparedStatement = connection.prepareStatement( sr.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         preparedStatement.executeUpdate();
         //group ����
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
   
   // ��ʼ��ѡ������
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

   // ��ʼ���ʼ�����
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

   //��ʼ��Share Folder����
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

    //��ʼ��Search����
   public int createSearch()
   {
      // ��ʼ��SQL��
      List< SQLScriptDTO > sqlScripts = new ArrayList< SQLScriptDTO >();

      //�û�
      SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
      sqlScriptDTO.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '1', '2', '', '�û�', 'Staff', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '105', null, 'Ա�����', 'Staff Code', '1', '13', '2', '', '0', '', '100_200', '1', ' ', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '101', null, 'Ա�����������ģ�', 'Staff Name (Chinese)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '102', null, 'Ա��������Ӣ�ģ�', 'Staff Name (English)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '104', null, 'Ա��״̬', 'Staff Status', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO );
      //��Ӧ��
      SQLScriptDTO sqlScriptDTO_A = new SQLScriptDTO();
      sqlScriptDTO_A.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '3', '2', '', '��Ӧ��', 'Vendor', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '300', null, '��Ӧ�����ƣ����ģ�', 'Vendor Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '301', null, '��Ӧ�����ƣ�Ӣ�ģ�', 'Vendor Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '302', null, '��Ӧ������', 'Vendor Type', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '316', null, 'ʡ�� - ����', 'Province', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '312', null, '����ʵ��', 'Legal Entity', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,   '313', null, '��������', 'Branch', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '314', null, '������', 'Owner', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '315', null, '״̬', 'Stauts', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_A );
      //��Ӧ����ϵ��
      SQLScriptDTO sqlScriptDTO_B = new SQLScriptDTO();
      sqlScriptDTO_B.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '4', '2', '', '��Ӧ����ϵ��', 'Vendor Contact', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '402', null, '��ϵ�����������ģ�', 'Contact Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'403', null, '��ϵ��������Ӣ�ģ�', 'Contact Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'404', null, '�ƺ�', 'Salutation', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'423', null, '״̬', 'Status', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'407', null, 'ʡ�� - ����', 'CIty', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'424', null, '��������', 'Branch', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'425', null, '������', 'Owner', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_B );
      //����
      SQLScriptDTO sqlScriptDTO_C = new SQLScriptDTO();
      sqlScriptDTO_C.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '5', '2', null, '����', 'Client Group', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '500', null, '���ű��', 'Number', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'501', null, '�������ƣ����ģ�', 'Client Group NameZH', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '502', null, '�������ƣ�Ӣ�ģ�', 'Client Group NameEN', '13', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '504', null, '״̬', 'Status', '15', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_C );
      //�ͻ�
      SQLScriptDTO sqlScriptDTO_D = new SQLScriptDTO();
      sqlScriptDTO_D.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '6', '2', null, '�ͻ�', 'Client', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'609', null, '�������', 'number', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'610', null, '�ͻ����ƣ����ģ�', 'ClientNameZH', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '611', null, '�ͻ����ƣ�Ӣ�ģ�', 'ClientNameZH', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '641', null, '��������', 'GroupName', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'632', null, '�ͻ�ID', 'Client ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'605', null, '��ҵ', 'Industry', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '606', null, '��ģ', 'Size', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '600', null, '����ʵ��', 'Entity', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '602', null, '��������', 'Branch', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '601', null, '������', 'Owner', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'612', null, '״̬', 'Status', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'633', null, '���ű��', 'Group Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_D );
      //�ͻ���ϵ��
      SQLScriptDTO sqlScriptDTO_E = new SQLScriptDTO();
      sqlScriptDTO_E.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("
            + accountId
            + ", null, '13', '2', null, '�ͻ���ϵ��', 'Client Contact', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1305', null, '��ϵ�����������ģ�', 'nameZH', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1306', null, '��ϵ��������Ӣ�ģ�', 'nameEN', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1323', null, '״̬', 'status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1302', null, '�ͻ�ID', 'Client ID', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1308', null, '����', 'Department', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1307', null, 'ְλ', 'Title', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1333', null, '�ͻ����', 'Client Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1334', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '10', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1335', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_E );
      //�˵���ַ
      SQLScriptDTO sqlScriptDTO_F = new SQLScriptDTO();
      sqlScriptDTO_F.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '14', '2', null, '�˵���ַ', 'Client Invoice', null, '', '1', '1', null, null, null, null, null,"
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1401', null, '��Ʊ̨ͷ', 'Title', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1402', null, '���������ռ��ˣ�', 'NameZH', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1403', null, 'Ӣ�������ռ��ˣ�', 'NameEN', '5', '0', '2', '', '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1417', null, '״̬', 'Status', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1430', null, '�ͻ�ID', 'Client ID', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1433', null, '�ͻ����', 'Client Number', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1407', null, '��ַ', 'Address', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1434', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '13', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1435', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '14', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_F );
      //��Ա - ������Ϣ
      SQLScriptDTO sqlScriptDTO_G = new SQLScriptDTO();
      sqlScriptDTO_G.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '17', '2', null, '��Ա - ������Ϣ', 'Employee', null, '', '1', '1', null, null, null, null, null,"
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1760', null, '��ԱID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1725', null, '��Ա���', 'Employee NO', '2', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1701', null, '��Ա���������ģ�', 'Employee Name (ZH)', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1700', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN)', '4', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1714', null, '�������', 'Record No', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1728', null, '֤������', 'Certificate Type', '6', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1729', null, '֤������', 'Certificate Number', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1705', null, '����״��', 'Marital Status', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1773', null, '�������£���ʼ��', 'Birthday (Start)', '18', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1772', null, '�������£�������', 'Birthday (End)', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1771', null, '�״βμӹ������ڣ���ʼ��', 'start Work Date (Start)', '20', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1770', null, '�״βμӹ������ڣ�������', 'start Work Date (End)', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1706', null, '����', 'nationNality', '22', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1716', null, '��������', 'Residency Type', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1713', null, '���ѧ��', 'Highes tEducation', '24', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1703', null, '״̬', 'Status', '25', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_G );
      //�����ͬ
      SQLScriptDTO sqlScriptDTO_H = new SQLScriptDTO();
      sqlScriptDTO_H.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '18', '2', null, '�����ͬ', 'Contract', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1800', null, '�ͻ�ID', 'Client ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1802', null, '����ʵ��', 'entityId', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1805', null, '��ͬ���', 'Contract Number', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1810', null, '��ͬ���ƣ����ģ�', 'Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1811', null, '��ͬ���ƣ�Ӣ�ģ�', 'Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_H.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1818', null, '״̬', 'Status', '11', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1820', null, '��ͬID', 'Client Contact ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1821', null, '�������', 'Client Number', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1822', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1823', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1803', null, 'ҵ������', 'Business Type', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_H );
      //�Ͷ���ͬ
      SQLScriptDTO sqlScriptDTO_I = new SQLScriptDTO();
      sqlScriptDTO_I.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '24', '2', null, '�Ͷ���ͬ', 'Labor Contract', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2403', null, '�Ͷ���ͬ���ƣ�Ӣ�ģ�', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2402', null, '�Ͷ���ͬ���ƣ����ģ�', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2405', null, '��ͬ״̬', 'Employee Status', '12', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2400', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2442', null, '�Ͷ���ͬID', 'Labor Contract ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2443', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2444', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2451', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2452', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2453', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2454', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_I );

      //����
      SQLScriptDTO sqlScriptDTO_J = new SQLScriptDTO();
      sqlScriptDTO_J.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '28', '2', null, '����', 'Order', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2828', null, '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2806', null, '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2808', null, 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2836', null, '����ID', 'Order ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2802', null, '�ͻ�ID', 'Client ID', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2804', null, '��ͬID', 'Contract ID', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2812', null, '������ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2813', null, '������������', 'Order End Date', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2826', null, '��������', 'Branch', '31', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2827', null, '������', 'Owner', '33', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2844', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2845', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '12', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2853', null, '�������', 'Client Number', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_J );
      //����Э��
      SQLScriptDTO sqlScriptDTO_K = new SQLScriptDTO();
      sqlScriptDTO_K.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '43', '2', null, '����Э��', 'Service Contract', null, '', '1', '1', '', '', '', '', '', "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4305', null, 'Э��״̬', 'Contract Service Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4311', null, '�ͻ�ID', 'Client ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4303', null, '����Э�����ƣ�Ӣ�ģ�', 'Service Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4302', null, '����Э�����ƣ����ģ�', 'Service Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4300', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4340', null, '����Э��ID', 'Service Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4341', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4342', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4356', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4357', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4358', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4359', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4361', null, '�������', 'Client Number', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_K );
      //���ʵ�
      SQLScriptDTO sqlScriptDTO_L = new SQLScriptDTO();
      sqlScriptDTO_L.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '���ʵ�', 'Payslip', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_L );
      //��˰
      SQLScriptDTO sqlScriptDTO_M = new SQLScriptDTO();
      sqlScriptDTO_M.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '��˰', 'Income Tax ', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_M );
      //����
      SQLScriptDTO sqlScriptDTO_N = new SQLScriptDTO();
      sqlScriptDTO_N.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '78', '2', '', '����', 'Order', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', "
            + userId
            + ", '"
            + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7852', '', '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'7806', '', '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7808', '', 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7836', '', '����ID', 'Order ID', '1', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7812', '', '������ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7813', '', '������������', 'Order End Date', '19', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7826', '', '��������', 'Branch', '31', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7827', '', '������', 'Owner', '33', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_N );
      //�籣��
      SQLScriptDTO sqlScriptDTO_O = new SQLScriptDTO();
      sqlScriptDTO_O.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '�籣��', 'SB Bill', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '"
            + getDate()
            + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '����Э��ID', 'Labor Contract ID', '4', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '�ͻ�ID', 'Client ID', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNo', '�������', 'Client No', '6', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '7', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '8', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'vendorNameZH', '��Ӧ������', 'Vendor Name', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_O );
      //���㵥
      SQLScriptDTO sqlScriptDTO_P = new SQLScriptDTO();
      sqlScriptDTO_P.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.settlement.OrderDTO', '���㵥', 'Settlement', null, '', '1', '1', null, null, null, null, null, "
            + userId
            + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '�ͻ�ID', 'Client ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'orderId', '����ID', 'Order ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNO', '�������', 'clientNO', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_P );
      //������ϸ
      SQLScriptDTO sqlScriptDTO_Q = new SQLScriptDTO();
      sqlScriptDTO_Q.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("
            + accountId
            + ", null, '0', '1', 'com.kan.hro.domain.biz.settlement.SettlementDTO', '������ϸ', 'Order Bill Detail', null, '', '1', '1', null, null, null, null, null, "
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '��ͬID', 'Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"
            + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
      sqlScripts.add( sqlScriptDTO_Q );
      //�������ݿ�
      if(insertDB(sqlScripts))
         return 0;
      else
         return 1;
   }
   /**
    * ���ݳ־û�����
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
            // ��ȡ����
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
