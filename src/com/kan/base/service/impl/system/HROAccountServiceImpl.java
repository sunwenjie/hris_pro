package com.kan.base.service.impl.system;

import com.kan.base.core.ContextService;
import com.kan.base.core.ServiceLocator;
import com.kan.base.dao.inf.system.AccountModuleRelationDao;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.system.AccountModuleRelationVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.system.HROAccountService;
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

public class HROAccountServiceImpl extends ContextService implements HROAccountService
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
        List<Object> list=accountModuleRelationDao.getAccountModuleRelationVOsByAccountId( accountId );
        if(list!=null&&list.size()>0){
           for ( Object object : list )
           {
             AccountModuleRelationVO accountModuleRelationVO=(AccountModuleRelationVO)object;
             ModuleVO moduleVO= moduleDao.getModuleVOByModuleId( accountModuleRelationVO.getModuleId() );
             //���hro_sec_group_module_right_relation
             if(moduleVO.getRightIds()!=null&&moduleVO.getRightIds().length()>0){
                List<String>  rightList=KANUtil.jasonArrayToStringList( moduleVO.getRightIds() );
                for ( String rightId : rightList )
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
//             if(moduleVO.getRuleIds()!=null&&moduleVO.getRuleIds().length()>0){
//                List<String>  ruleList=KANUtil.jasonArrayToStringList( moduleVO.getRuleIds() );
//                for ( String  ruleId : ruleList )
//               {
//                   StringBuffer sb=new StringBuffer();
//                   sb.append( "insert into HRO_SEC_Group_Module_Rule_Relation" );
//                   sb.append( "(groupId, moduleId, ruleId, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate)" );
//                   sb.append( "VALUES" );
//                   sb.append( "("+groupId+", "+moduleVO.getModuleId()+", "+ruleId+", 1, 1, null, null, null, null, null, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
//                   final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
//                   HeaderpreparedStatement.executeUpdate();
//               }
//             }
           }
        }
         return  0;
      }
      catch ( KANException e )
      {
         System.err.println("create createRuleAndRight is error!");
         e.printStackTrace();
         return 1;
      }
      catch ( SQLException e )
      {
         System.err.println("create createRuleAndRight is error!");
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
         System.err.println("create HRO_SYS_Account is error!");
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
         System.err.println("create staff is error!");
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
         sb.append( "("+accountId+",null,"+staffId+",'Admin', '" + Cryptogram.encodeString( "Kangroup" ) + "','', '"+getDate()+"','',     null,    1,1,null, null, null, null, null,  'System','"+getDate()+"', 'System', '"+getDate()+"')" );
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
         System.err.println("create user is error!");
         e.printStackTrace();
         return 1;
      }
      catch ( KANException e )
      {
         System.err.println("create user is error!");
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
         int flag = createImport( primaryKey );
         flag = flag + createReport(primaryKey);
         flag = flag + createGroup( primaryKey );
         return  flag;
      }
      catch ( SQLException e )
      {
         System.err.println("create Position or Position_Staff_Relation is error!");
         e.printStackTrace();
         return 1;
      }
   }
   
   private int createReport(final String positionId){
      String sqlHeader="INSERT INTO Hro_Def_Report_Header (accountId,corpId,tableId,nameZH,nameEN,clicks,usePagination,pageSize,loadPages,isSearchFirst,sortColumns,groupColumns,statisticsColumns,exportExcelType,isExportPDF,moduleType,isPublic,positionIds,positionGradeIds,positionGroupIds,branch,owner,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ";
      String sqlDetail="INSERT INTO Hro_Def_Report_Detail (reportHeaderId,columnId,tableId,nameZH,nameEN,columnWidth,columnWidthType,columnIndex,fontSize,isDecoded,isLinked,linkedAction,linkedTarget,datetimeFormat,accuracy,round,align,sort,statisticsFun,display,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values (?, ";
      String after="null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";
      SQLScriptDTO sqlRoport_A=new SQLScriptDTO();
      sqlRoport_A.setSqlScript( sqlHeader+""+accountId+", null, 5, '�ͻ� - ����', 'customer info', '0', '1', '15', '0', '2', null, null, null, '1', '2', null, null, '{"+positionId+"}', null, null, null, null, '', '1', '1',"+after );
      sqlRoport_A.addSubSQLScript( sqlDetail +"507, null, '����ID', 'Client Group ID', '80', '1', '0', '12', '1', '2', '', '0', '0', '0', '0', '2', '1', null, '1', '', '1', '1',"+after);
      insertDB( sqlRoport_A );
      return 0;
   }
   
   
   private int createImport(String positionId){
      String sqlHeader="INSERT INTO Hro_Def_Import_Header (parentId,accountId,corpId,tableId,nameZH,nameEN,positionIds,positionGradeIds,positionGroupIds,needBatchId,matchConfig,handlerBeanId,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ";
      String sqlDetail="INSERT INTO Hro_Def_Import_Detail (importHeaderId,columnId,nameZH,nameEN,isPrimaryKey,isForeignKey,columnWidth,columnIndex,fontSize,specialField,tempValue,isDecoded,isIgnoreDefaultValidate,datetimeFormat,accuracy,round,align,description,deleted,status,remark1,remark2,remark3,remark4,remark5,createBy,createDate,modifyBy,modifyDate) values ( ?,";
      String after="null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";
      SQLScriptDTO sqlImport_A=new SQLScriptDTO();
      sqlImport_A.setSqlScript( sqlHeader+" '0', '"+accountId+"', null, '17', '��Ա����', 'Employee Import', '{"+positionId+"}', '', '', '2', '0', '0', '', '1', '2',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail+" '1701', '���������ģ�', 'Name', '0', '0', '14', '2', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1'," +after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1725', '��Ա���', 'Employee No', '0', '0', '14', '1', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +"'1728', '֤������', 'Certificate Type', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1729', '֤������', 'Certificate Number', '0', '0', '14', '5', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +"'1782', '����', 'Bank', '0', '0', '14', '9', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1783', '�����˺�', 'Bank Account', '0', '0', '14', '10', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1703', '״̬', 'Employee Status', '0', '0', '14', '11', '13', '0', '', '1', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1760', '��ԱID', 'Employee Id', '1', '0', '14', '0', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_A.addSubSQLScript( sqlDetail +" '1700', '������Ӣ�ģ�', 'Staff Name (EN)', '0', '0', '14', '3', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      insertDB( sqlImport_A );
      
      SQLScriptDTO sqlImport_B=new SQLScriptDTO();
      sqlImport_B.setSqlScript( sqlHeader+"'0', '"+accountId+"', null, '74', '���ʵ���', 'Salary Import', '{"+positionId+"}', '', '', '1', '0', 'salaryExcueHandler', '', '1', '2',"+after );
      sqlImport_B.addSubSQLScript( sqlDetail +" '7408', '����ID', 'Order ID', '0', '0', '14', '8', '13', '0', '500000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7409', '����Э��ID', 'Contract ID', '0', '0', '14', '9', '13', '0', '200000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +"'7410', '��ԱID', 'Employee ID', '0', '0', '14', '10', '13', '0', '100000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7411', '��Ա���������ģ�', 'employeeNameZH', null, null, '14', '11', '13', null, '����', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7412', '��Ա������Ӣ�ģ�', 'employeeNameEN', null, null, '14', '12', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +"'7413', 'н�꿪ʼ����', 'startDate', null, null, '14', '13', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7414', 'н���������', 'endDate', null, null, '14', '14', '13', null, '2014-01-01', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +"'7415', '֤������', 'certificateType', null, null, '14', '15', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7416', '֤������', 'certificateNumber', null, null, '14', '16', '13', null, '2.11E+17', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7418', '�������ƣ����ģ�', 'bankNameZH', null, null, '14', '18', '13', null, '�й������Ϻ��з���', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7419', '�������ƣ�Ӣ�ģ�', 'bankNameEN', null, null, '14', '19', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7420', '�����˻�', 'bankAccount', null, null, '14', '20', '13', null, '6.23E+18', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7427', 'н���·�', 'monthly', null, null, '14', '27', '13', null, '2014/06', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7405', '�ͻ�ID', 'Client ID', '0', '0', '14', '1', '13', '0', '300000001', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +"'7406', '�ͻ����ƣ����ģ�', 'clientNameZH', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_B.addSubSQLScript( sqlDetail +" '7407', '�ͻ����ƣ�Ӣ�ģ�', 'clientNameEN', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      String primaryKey_B=insertDB( sqlImport_B );
      
      SQLScriptDTO sqlImport_C=new SQLScriptDTO();
      sqlImport_C.setSqlScript( sqlHeader+" '"+primaryKey_B+"', '"+accountId+"', null, '75', '���ʵ�����ϸ', 'Salary Import - Detail', '{"+positionId+"}', '', '', '0', '0', 'salaryExcueHandler', '', '1', '2',"+after );
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '��������', 'Base Salary', '0', '0', '14', '2', '13', '0', '0', '2', '0', '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '��ٿۿ�', 'Leave Deduct', '0', '0', '14', '3', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�����ۿ�', 'Other Withhold', '0', '0', '14', '4', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '��λ����', 'Position Allowance', '0', '0', '14', '5', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '���½���', 'High Temperature Allowance', '0', '0', '14', '6', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', 'פ�����', 'Travel Allowance', '0', '0', '14', '7', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '��������', 'Other Allowance', '0', '0', '14', '8', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'ҹ�ಹ��', 'Night Shift Subsidy', '0', '0', '14', '9', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '��ͨ����', 'Transportation Subsidy', '0', '0', '14', '10', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�òͲ���', 'Meat Subsidy', '0', '0', '14', '11', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '��������', 'Other Subsidy', '0', '0', '14', '12', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '������', 'Welfare', '0', '0', '14', '13', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '��Ч', 'Performance', '0', '0', '14', '14', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '����', 'Bonus', '0', '0', '14', '15', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'Ӷ��', 'Commission', '0', '0', '14', '16', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '���ս�', 'Annual Bonus', '0', '0', '14', '17', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '�Ӱ� 1.0', 'OT 1.0', '0', '0', '14', '18', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�Ӱ� 1.5', 'OT 1.5', '0', '0', '14', '19', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�Ӱ� 2.0', 'OT 2.0', '0', '0', '14', '20', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�Ӱ� 3.0', 'OT 3.0', '0', '0', '14', '21', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�Ӱ໻��', 'OT Shift', '0', '0', '14', '22', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '����', 'Reimbursement', '0', '0', '14', '23', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '���� - ���ʷ�', 'Reimbursement - Entertainment', '0', '0', '14', '24', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '���� - �Ӱ�', 'Reimbursement - OT', '0', '0', '14', '25', '13', '0', '0', '2', null, '9', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'ס��������-��˾', 'Housing(Company)', '0', '0', '14', '26', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', 'ס��������-����', 'Housing(Personal)', '0', '0', '14', '27', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '���ϱ���-��˾', 'Endowment(Company)', '0', '0', '14', '28', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '���ϱ���-����', 'Endowment(Personal)', '0', '0', '14', '29', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'ҽ�Ʊ���-��˾', 'Medical(Company)', '0', '0', '14', '30', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7520', 'ҽ�Ʊ���-����', 'Medical(Personal)', '0', '0', '14', '31', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'ʧҵ����-��˾', 'Unemployment(Company)', '0', '0', '14', '32', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', 'ʧҵ����-����', 'Unemployment(Personal)', '0', '0', '14', '33', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519',  '���˱���-��˾', 'Injury(Company)', '0', '0', '14', '34', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '���˱���-����', 'Injury(Personal)', '0', '0', '14', '35', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '��������-��˾', 'Maternity(Company)', '0', '0', '14', '36', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '��������-����', 'Maternity(Personal)', '0', '0', '14', '37', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�м��˱��Ͻ�-��˾', 'Disable(Company)', '0', '0', '14', '38', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '�м��˱��Ͻ�-����', 'Disable(Personal)', '0', '0', '14', '39', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', 'ȡů��-��˾', 'Warming(Company)', '0', '0', '14', '40', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7520',  'ȡů��-����', 'Warming(Personal)', '0', '0', '14', '41', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '�����-��˾', 'Allocation(Company)', '0', '0', '14', '42', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7520',  '�����-����', 'Allocation(Personal)', '0', '0', '14', '43', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519',  '���������-��˾', 'Archive(Company)', '0', '0', '14', '44', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '���������-����', 'Archive(Personal)', '0', '0', '14', '45', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao(Company)', '0', '0', '14', '46', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao(Personal)', '0', '0', '14', '47', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after );
      sqlImport_C.addSubSQLScript( sqlDetail + "'7519',  '����ҽ�Ʊ���-��˾', 'Buchong Yiliao(Company)', '0', '0', '14', '48', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after );
      sqlImport_C.addSubSQLScript( sqlDetail +"'7520', '����ҽ�Ʊ���-����', 'Buchong Yiliao(Personal)', '0', '0', '14', '49', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7519', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao(Company)', '0', '0', '14', '50', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +"'7520', '��ҽ�Ʊ���-����', 'Dabing Yiliao(Personal)', '0', '0', '14', '51', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', '���ũ��ҽ��-��˾', 'Yidi Nongcun(Company)', '0', '0', '14', '52', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', '���ũ��ҽ��-����', 'Yidi Nongcun(Personal)', '0', '0', '14', '53', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7519', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding(Company)', '0', '0', '14', '54', '13', '0', '0', '2', null, '0', '3', '0', '3', null, '1', '1',"+after);
      sqlImport_C.addSubSQLScript( sqlDetail +" '7520', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding(Personal)', '0', '0', '14', '55', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      insertDB( sqlImport_C );
      
      SQLScriptDTO sqlImport_D=new SQLScriptDTO();
      sqlImport_D.setSqlScript( sqlHeader +" '0', '"+accountId+"', null, '44', '��������', 'Timesheet Import', '{"+positionId+"}', '', '', '1', '0', 'timesheetAllowance', '', '1', '2',"+after); 
      sqlImport_D.addSubSQLScript( sqlDetail+" '4420', '��Ա�������ģ�', 'Employee NameZH', '0', '0', '14', '2', '13', null, '����', '2', null, '', null, null, '1', null, '1', '1',"+after );
      sqlImport_D.addSubSQLScript( sqlDetail+"'4402', '����Э��ID', 'Contract ID', '0', '0', '14', '4', '13', null, '200000001', '2', null, '', null, null, '1', null, '1', '1',"+after );
      sqlImport_D.addSubSQLScript( sqlDetail+" '4405', '�·�', 'Time Sheet Month', '0', '0', '14', '3', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after );
      sqlImport_D.addSubSQLScript( sqlDetail+"'4401', '��ԱID', 'Employee ID', '0', '0', '14', '1', '13', '0', '100000001', '2', null, '0', '0', '0', '1', null, '1', '1',"+after );
      String primaryKey_D= insertDB( sqlImport_D );
      
      SQLScriptDTO sqlImport_E=new SQLScriptDTO();
      sqlImport_E.setSqlScript( sqlHeader+"'"+primaryKey_D+"', '"+accountId+"', null, '85', '���ڵ��� - ����', 'Timesheet Import - Allowance', '{"+positionId+"}', '', '', '0', '0', '0', '', '1', '2',"+after );
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', 'פ�����', 'Travel Allowance', '0', '0', '14', '10', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '���½���', 'High Temperature Allowance', '0', '0', '14', '11', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '��λ����', 'Position Allowance', '0', '0', '14', '12', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', 'ҹ�ಹ��', 'Night Shift Subsidy', '0', '0', '14', '13', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '��������', 'Other Allowance', '0', '0', '14', '14', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '��ͨ����', 'Transportation Subsidy', '0', '0', '14', '15', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '�òͲ���', 'Meat Subsidy', '0', '0', '14', '16', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '��������', 'Other Subsidy', '0', '0', '14', '17', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '������', 'Welfare', '0', '0', '14', '18', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '���ս�', 'Annual Bonus', '0', '0', '14', '19', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '��Ч', 'Performance', '0', '0', '14', '20', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '����', 'Bonus', '0', '0', '14', '21', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', 'Ӷ��', 'Commission', '0', '0', '14', '21', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '����', 'Reimbursement', '0', '0', '14', '22', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +" '8503', '���� - ���ʷ�', 'Reimbursement - Entertainment', '0', '0', '14', '23', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      sqlImport_E.addSubSQLScript( sqlDetail +"'8503', '���� - �Ӱ�', 'Reimbursement - OT', '0', '0', '14', '24', '13', '0', '0', '2', null, '0', '0', '0', '3', null, '1', '1',"+after);
      insertDB( sqlImport_E );
      
      SQLScriptDTO sqlImport_F=new SQLScriptDTO();
      sqlImport_F.setSqlScript( sqlHeader+" '0', '"+accountId+"',null, '19', '��ٵ���', 'Leave Import', '{"+positionId+"}', '', '', '1', '0', 'leaveExcueHandler', '', '1', '2',"+after );
      sqlImport_F.addSubSQLScript( sqlDetail +"'1900', '��ԱID', 'Employee ID', null, null, '14', '1', '13', null, '100000001', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_F.addSubSQLScript( sqlDetail +" '1918', '��Ա���������ģ�', 'Employee Name(ZH)', null, null, '14', '2', '13', null, '����', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_F.addSubSQLScript( sqlDetail +"'1924', '������Ϣ', 'Contract', null, null, '14', '7', '13', null, '200000001', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_F.addSubSQLScript( sqlDetail +"'1902', '������', 'Item', null, null, '14', '8', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_F.addSubSQLScript( sqlDetail +" '1903', '��ʼʱ��', 'Estimate Start Date', null, null, '14', '9', '13', null, '2014-01-01 12:12', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_F.addSubSQLScript( sqlDetail +" '1904', '����ʱ��', 'Estimate End Date', null, null, '14', '10', '13', null, '2014-01-01 12:12', '2', null, '', null, null, '1', null, '1', '1',"+after);
      insertDB( sqlImport_F );
      
      SQLScriptDTO sqlImport_G=new SQLScriptDTO();
      sqlImport_G.setSqlScript( sqlHeader +" '0', '"+accountId+"', null, '20', '�Ӱർ��', 'OT Import', '{"+positionId+"}', '', '', '1', '0', 'otExcueHandler', '', '1', '2',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +" '2000', '��ԱID', 'Employee', null, null, '14', '1', '13', null, '', '2', null, '', null, null, '1', null, '1', '1',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +" '2002', '��Ŀ', 'Item', '0', '0', '14', '4', '13', '0', '', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +"'2011', '��Ա����', 'Employee Name', '0', '0', '14', '2', '13', '0', '����', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +"'2022', '����Э��', 'Contract', '0', '0', '14', '3', '13', '0', '200000001', '2', null, '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +" '2003', '��ʼʱ��', 'Estimate Start Date', '0', '0', '14', '8', '13', '0', '2014-01-01 12:12', '2', null, '4', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_G.addSubSQLScript( sqlDetail +" '2004', '����ʱ��', 'Estimate End Date', '0', '0', '14', '9', '13', '0', '2014-01-01 12:12', '2', null, '4', '0', '0', '1', null, '1', '1',"+after);
      insertDB( sqlImport_G );
      
      SQLScriptDTO sqlImport_H=new SQLScriptDTO();
      sqlImport_H.setSqlScript( sqlHeader +" '0', '"+accountId+"',  null, '43', '����Э�鵼��', 'Service Contract Import', '{"+positionId+"}', '', '', '1', '0', 'employeeContractImportHandler', '', '1', '2',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4300', '��ԱID', 'Employee ID', '0', '0', '14', '1', '13', '0', '', '2', '1', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'8583', '���֤����', 'certificateNumber', '0', '0', '14', '3', '13', '0', '', '2', '1', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4341', '��Ա����', 'Employee Name (ZH)', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1', "+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4306', '����ID', 'Order ID', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4340', '����Э��ID', 'Service Contract ID', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4302', 'Э�����ƣ����ģ�', 'Name (ZH)', '0', '0', '14', '9', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4303', 'Э�����ƣ�Ӣ�ģ�', 'Name (EN)', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4304', 'Э����', 'Service Contract No.', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4310', 'Э��ģ��', 'Template ', '0', '0', '14', '11', '13', '0', '', '2', '1', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4318', '��ʼʱ��', 'Start Date', '0', '0', '14', '12', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4319', '����ʱ��', 'End Date', '0', '0', '14', '13', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4305', 'Э��״̬', 'Status', '0', '0', '14', '14', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4308', 'ǩԼ����', 'Entity ID', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4309', 'ҵ������', 'Business Type ', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4326', '����', 'Calendar', '0', '0', '14', '15', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +"'4327', '�Ű�', 'Shift', '0', '0', '14', '16', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4321', '��������', 'Branch', '0', '0', '14', '17', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_H.addSubSQLScript( sqlDetail +" '4322', '������', 'Owner', '0', '0', '14', '18', '13', '2', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      String primaryKey_H=insertDB(sqlImport_H);
      
      SQLScriptDTO sqlImport_I=new SQLScriptDTO();
      sqlImport_I.setSqlScript( sqlHeader+" '"+primaryKey_H+"', '"+accountId+"', null, '37', '����Э�鵼�� - �籣����', 'Service Contract Import - SB', '{"+positionId+"}', '', '', '1', '0', '0', '', '1', '2',"+after );
      sqlImport_I.addSubSQLScript( sqlDetail +"'3701', '�籣����A', 'Solution Benefit A', '0', '0', '14', '1', '13', '0', '', '1', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +"'3706', '�ӱ�����A', 'Start Date A', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '8585', '�籣����A', 'SB Base A', '0', '0', '14', '3', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3701', '�籣����B', 'Solution Benefit B', '0', '0', '14', '4', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3706', '�ӱ�����B', 'Start Date B', '0', '0', '14', '5', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +"'8585', '�籣����B', 'SB Base B', '0', '0', '14', '6', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3701', '�籣����C', 'Solution Benefit C', '0', '0', '14', '7', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +"'3706', '�ӱ�����C', 'Start Date C', '0', '0', '14', '8', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '8585', '�籣����C', 'SB Action C', '0', '0', '14', '9', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3701', '�籣����D', 'Solution Benefit D', '0', '0', '14', '10', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +"'3706', '�ӱ�����D', 'Start Date D', '0', '0', '14', '11', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '8585', '�籣����D', 'SB Base D', '0', '0', '14', '12', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3701', '�籣����E', 'Solution Benefit E', '0', '0', '14', '13', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '3706', '�ӱ�����E', 'Start Date E', '0', '0', '14', '14', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_I.addSubSQLScript( sqlDetail +" '8585', '�籣����E', 'SB Base E', '0', '0', '14', '15', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      insertDB( sqlImport_I );
      
      SQLScriptDTO sqlImport_J=new SQLScriptDTO();
      sqlImport_J.setSqlScript( sqlHeader+"'"+primaryKey_H+"', '"+accountId+"', null, '40', '����Э�鵼�� - н�귽��', 'Service Contract Import - Salary', '{"+positionId+"}', '', '', '0', '0', '0', '', '1', '2',"+after );
      sqlImport_J.addSubSQLScript( sqlDetail +" '4004', '��������', '��������', '0', '0', '14', '1', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      sqlImport_J.addSubSQLScript( sqlDetail +"'4004', '��λ����', '��λ����', '0', '0', '14', '2', '13', '0', '', '2', '0', '0', '0', '0', '1', null, '1', '1',"+after);
      insertDB( sqlImport_J );
      return 0;
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
         int flag=createRuleAndRight(primaryKey);
         return  flag;
      }
      catch ( SQLException e )
      {
         System.err.println("create Group or Position_Group_Relation is error!");
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
         System.err.println("create HRO_MGT_Options is error!");
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
         sb.append( "("+accountId+", 'KANgroup Notice', 2, 'system@kangroup.com.cn', null,'smtp.kangroup.com.cn', '25', 'system@kangroup.com.cn', 'Kangroup2014',1,  0,'pop3.kangroup.com.cn','110', 1, 1, null, null, "+userId+", '"+getDate()+"', "+userId+", '"+getDate()+"')" );
         final PreparedStatement HeaderpreparedStatement = connection.prepareStatement( sb.toString(), PreparedStatement.RETURN_GENERATED_KEYS );
         HeaderpreparedStatement.executeUpdate();
         return  0;
      }
      catch ( SQLException e )
      {
         System.err.println("create HRO_MGT_Email_Configuration is error!");
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
         String listHeaderBefore="insert into Hro_Def_List_Header( accountId, corpId,  parentId, useJavaObject, javaObjectName,tableId, searchId, nameZH, nameEN, pageSize, loadPages, usePagination, isSearchFirst, exportExcel, description, deleted, status, remark1,  remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate) values ( ";
         String listDetailBefore="insert into Hro_Def_List_Detail(listHeaderId, columnId, propertyName, nameZH ,nameEN, columnWidth, columnWidthType, columnIndex, fontSize, isDecoded, isLinked, linkedAction, linkedTarget, appendContent, properties, datetimeFormat, accuracy, align, round, sort, display, description, deleted, status, remark1, remark2, remark3, remark4,  remark5, createBy, createDate, modifyBy, modifyDate ) values( ";
         String after="null,null,null,null,null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')";
         //�û�
         SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
         sqlScriptDTO.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '1', '2', '', '�û�', 'Staff', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '105', null, 'Ա�����', 'Staff Code', '1', '13', '2', '', '0', '', '100_200', '1', ' ', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '101', null, 'Ա�����������ģ�', 'Staff Name (Chinese)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '102', null, 'Ա��������Ӣ�ģ�', 'Staff Name (English)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '104', null, 'Ա��״̬', 'Staff Status', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         //�û���������
         String userPrimarykey = insertDB( sqlScriptDTO );
         //�û��б�
         SQLScriptDTO sqlScript = new SQLScriptDTO();
         sqlScript.setSqlScript(listHeaderBefore+""+accountId+", null, 0, 2, '', 1, "+userPrimarykey+", '�û��б�', 'Staff List', 10, 0, 1, 2, 1, '', 1, 1,"+after);
         sqlScript.addSubSQLScript( listDetailBefore + "?, '105', null, 'Ա�����', 'Staff Code','8', '1', '2', '13', '2', '2', '', '1', '', '', '0', '0', '1', '0', '1', '0', '1', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '101', null, 'Ա�����������ģ�', 'Staff Name (Chiness)','10', 1, 3, 13, 2,2, '',2, '', '', '', null, 1, null,1, 1, '', 1, 1," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '118', null, '������ַ�����ᣩ', 'Hometown','18', 1, 7, 13, 2,2, '',0, '', '', '', null, '1', null,1, 1, 1, 1, 1," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '104', null, '״̬', 'Status','5', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '116', null, '֤������', 'ID NO', '', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '7525', null, '��������', 'working period', '', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '124', null, '�޸���', 'Modify By','8', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '125', null, '�޸�ʱ��', 'Decode Modify Date','12', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1'," + after );
         sqlScript.addSubSQLScript( listDetailBefore + "?, '126', null, 'Ա��ID', 'staffId','3', '1', '1', '13', '2', '1', 'staffAction.do?proc=to_objectModify', '1', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1'," + after );
         insertDB(sqlScript);
            
         
         //��Ӧ��
         SQLScriptDTO sqlScriptDTO_A = new SQLScriptDTO();
         sqlScriptDTO_A.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '3', '2', '', '��Ӧ��', 'Vendor', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '300', null, '��Ӧ�����ƣ����ģ�', 'Vendor Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '301', null, '��Ӧ�����ƣ�Ӣ�ģ�', 'Vendor Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '302', null, '��Ӧ������', 'Vendor Type', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '316', null, 'ʡ�� - ����', 'Province', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '312', null, '����ʵ��', 'Legal Entity', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,   '313', null, '��������', 'Branch', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '314', null, '������', 'Owner', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_A.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '315', null, '״̬', 'Stauts', '8', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String vendorPrimarykey=insertDB( sqlScriptDTO_A );
         //��Ӧ���б�
         SQLScriptDTO sqlScript_A = new SQLScriptDTO();
         sqlScript_A.setSqlScript( listHeaderBefore+""+accountId+", null,  0, 2,'',3, "+vendorPrimarykey+", '��Ӧ���б�', 'Vendor List',10, 0, 1, 2, 1, '', 1, 1,"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'317', null, '��Ӧ��ID', 'Vendor ID', '5', '1', '1', '13', '2', '1', 'vendorAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'300', null, '��Ӧ�����ƣ����ģ�', 'Vendor Name (ZH)', '15', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'304', null, '��ͬ��ʼ����', 'contract start date', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '305', null, '��ͬ��������', 'Contract end date', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '303', null, 'ʡ�� - ����', 'City', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'302', null, '����', 'Vendor Type', '5', '1', '6', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '324', null, '��ϵ��', 'Contacts', '6', '1', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '323', null, '��ϵ�˵绰', 'Phone', '8', '1', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '326', null, '��ϵ������', 'Email', '8', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '312', null, '����ʵ��', 'Entity', '10', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'315', null, '״̬', 'Status', '8', '1', '11', '13', '1', '2', null, '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?,'318', null, '�޸���', 'Modify By', '6', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_A.addSubSQLScript( listDetailBefore+"?, '319', null, '�޸�����', 'Modify Date', '8', '1', '13', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1',"+after );
         insertDB( sqlScript_A );
         
         
         
         //��Ӧ����ϵ��
         SQLScriptDTO sqlScriptDTO_B = new SQLScriptDTO();
         sqlScriptDTO_B.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '4', '2', '', '��Ӧ����ϵ��', 'Vendor Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '402', null, '��ϵ�����������ģ�', 'Contact Name(ZH)', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'403', null, '��ϵ��������Ӣ�ģ�', 'Contact Name(EN)', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'404', null, '�ƺ�', 'Salutation', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'423', null, '״̬', 'Status', '7', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'407', null, 'ʡ�� - ����', 'CIty', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'424', null, '��������', 'Branch', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_B.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'425', null, '������', 'Owner', '6', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String VendorContactKey=insertDB( sqlScriptDTO_B );
         //��ϵ���б�
         SQLScriptDTO sqlScript_B = new SQLScriptDTO();
         sqlScript_B.setSqlScript( listHeaderBefore+""+accountId+",  null, '0', '2', '', '4', "+VendorContactKey+", '��Ӧ����ϵ���б�', 'Vendor Contact List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?, '400', null, '��ϵ��ID', 'Contract ID', '5', '1', '1', '13', '2', '1', 'vendorContactAction.do?proc=to_objectModify', '1', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'402', null, '��ϵ������', 'Contact Name', '10', '1', '2', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?, '408', null, '��˾�绰', 'Company Number', '8', '1', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'414', null, '����', 'Email', '8', '1', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?, '404', null, '�ƺ�', 'Salutation', '5', '1', '5', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'406', null, '����', 'Title', '8', '1', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?, '405', null, 'ְλ', 'Department', '8', '1', '7', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'401', null, '��Ӧ��ID', 'Vendor ID', '8', '1', '8', '13', '2', '1', 'vendorAction.do?proc=to_objectModify&id=${0}', '1', null, 'encodedVendorId', '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'426', null, '��Ӧ������', 'Vendor Name', '15', '1', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?, '423', null, '״̬', 'Status', '8', '1', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'430', null, '�޸���', 'Modify By', '8', '1', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_B.addSubSQLScript( listDetailBefore+"?,'431', null, '�޸�����', 'Modify Date', '9', '1', '12', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '2', '1', null, '1', '1',"+after );
         insertDB( sqlScript_B );
         
         
         
         //����
         SQLScriptDTO sqlScriptDTO_C = new SQLScriptDTO();
         sqlScriptDTO_C.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", null, '5', '2', null, '����', 'Client Group', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,  '500', null, '���ű��', 'Number', '10', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'501', null, '�������ƣ����ģ�', 'Client Group NameZH', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '502', null, '�������ƣ�Ӣ�ģ�', 'Client Group NameEN', '13', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_C.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '504', null, '״̬', 'Status', '15', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientGroupKey=insertDB( sqlScriptDTO_C );
         SQLScriptDTO sqlScript_C = new SQLScriptDTO();
         //�����б�
         sqlScript_C.setSqlScript( listHeaderBefore+""+accountId+",  null, '0', '2', '', '5', "+clientGroupKey+", '�����б�', 'Client Group List', '10', '0', '1', '2', '1', '�ͻ����� = > �����б�', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?,'500', '', '���ű��', 'number', '10', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?,'501', '', '�������ƣ����ģ�', 'Client Group NameZH', '28', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?,'502', '', '�������ƣ�Ӣ�ģ�', 'Client Group NameEN', '28', '1', '12', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?,'504', '', '״̬', 'Status', '6', '1', '17', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?, '505', '', '�޸���', 'Modify By', '8', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?, '506', '', '�޸�ʱ��', '�޸�ʱ��', '12', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after );
         sqlScript_C.addSubSQLScript( listDetailBefore+"?, '507', '', '����ID', 'Client Group ID', '8', '1', '9', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after );
         insertDB( sqlScript_C );
         
         
         
         //�ͻ�
         SQLScriptDTO sqlScriptDTO_D = new SQLScriptDTO();
         sqlScriptDTO_D.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", null, '6', '2', null, '�ͻ�', 'Client', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'609', null, '�������', 'number', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'610', null, '�ͻ����ƣ����ģ�', 'ClientNameZH', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '611', null, '�ͻ����ƣ�Ӣ�ģ�', 'ClientNameZH', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '641', null, '��������', 'GroupName', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'632', null, '�ͻ�ID', 'Client ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'605', null, '��ҵ', 'Industry', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '606', null, '��ģ', 'Size', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '600', null, '����ʵ��', 'Entity', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '602', null, '��������', 'Branch', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '601', null, '������', 'Owner', '21', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'612', null, '״̬', 'Status', '23', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_D.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'633', null, '���ű��', 'Group Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientKey=insertDB( sqlScriptDTO_D );
         //�ͻ��б�
         SQLScriptDTO sqlScript_D = new SQLScriptDTO();
         sqlScript_D.setSqlScript( listHeaderBefore +""+accountId+", null, '0', '2', '', '6', "+ClientKey+", '�ͻ��б�', 'Client List', '10', '0', '1', '2', '1', '�ͻ����� = > �ͻ��б�', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?,'609', null, '�������', 'number', '9', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?,'610', '', '�ͻ����ƣ����ģ�', 'nameCN', '25', '1', '5', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1', "+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?,'611', '', '�ͻ����ƣ�Ӣ�ģ�', 'nameEN', '25', '1', '7', '13', '2', '2', 'clientAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?, '612', '', '״̬', 'status', '7', '1', '28', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?, '639', '', '�޸���', 'Modify By', '7', '1', '26', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?, '640', '', '�޸�ʱ��', 'Modify Date', '11', '1', '27', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?, '633', '', '���ű��', 'Group Number', '9', '1', '8', '13', '2', '1', 'clientGroupAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedGroupId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_D.addSubSQLScript( listDetailBefore +"?,'632', '', '�ͻ�ID', 'Client ID', '7', '1', '1', '13', '2', '1', 'clientAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_D );

         
         
         //�ͻ���ϵ��
         SQLScriptDTO sqlScriptDTO_E = new SQLScriptDTO();
         sqlScriptDTO_E.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate)  VALUES ("+ accountId+ ", null, '13', '2', null, '�ͻ���ϵ��', 'Client Contact', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1305', null, '��ϵ�����������ģ�', 'nameZH', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1306', null, '��ϵ��������Ӣ�ģ�', 'nameEN', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1323', null, '״̬', 'status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1302', null, '�ͻ�ID', 'Client ID', '8', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1308', null, '����', 'Department', '5', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1307', null, 'ְλ', 'Title', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1333', null, '�ͻ����', 'Client Number', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1334', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '10', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_E.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1335', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String clientContactKey=insertDB( sqlScriptDTO_E );
         //�ͻ���ϵ���б�
         SQLScriptDTO sqlScript_E = new SQLScriptDTO();
         sqlScript_E.setSqlScript( listHeaderBefore +""+accountId+", null, '0', '2', '', '13', "+clientContactKey+", '�ͻ���ϵ���б�', 'Client Contact List', '10', '0', '1', '2', '1', '�ͻ����� = > �ͻ���ϵ���б�', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?, '1305', '', '��ϵ�����������ģ�', 'nameZH', '11', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1306', '', '��ϵ��������Ӣ�ģ�', 'nameEN', '11', '1', '3', '13', '2', '2', 'clientContactAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1304', '', '�ƺ�', 'Salutation', '4', '1', '4', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1323', '', '״̬', 'status', '4', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?, '1307', '', 'ְλ', 'title', '11', '1', '8', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?, '1336', '', '�޸���', 'Modify By', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1337', '', '�޸�ʱ��', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1338', '', '�ͻ�����', 'Client Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?, '6428', '', '��ϵ��ID', 'Client Contact ID', '6', '1', '1', '13', '2', '1', 'clientContactAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?, '1302', '', '�ͻ�ID', 'Client ID', '7', '1', '5', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_E.addSubSQLScript( listDetailBefore +"?,'1308', '', '����', 'Department', '10', '1', '7', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_E );

         
         
         //�˵���ַ
         SQLScriptDTO sqlScriptDTO_F = new SQLScriptDTO();
         sqlScriptDTO_F.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '14', '2', null, '�˵���ַ', 'Client Invoice', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1401', null, '��Ʊ̨ͷ', 'Title', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1402', null, '���������ռ��ˣ�', 'NameZH', '3', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1403', null, 'Ӣ�������ռ��ˣ�', 'NameEN', '5', '0', '2', '', '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1417', null, '״̬', 'Status', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1430', null, '�ͻ�ID', 'Client ID', '9', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1433', null, '�ͻ����', 'Client Number', '11', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1407', null, '��ַ', 'Address', '15', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null, " + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1434', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '13', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_F.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '1435', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '14', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String ClientInvoicekey=insertDB( sqlScriptDTO_F );
         //�˵���ַ�б�
         SQLScriptDTO sqlScript_F = new SQLScriptDTO();
         sqlScript_F.setSqlScript( listHeaderBefore+""+accountId+", null, '0', '2', '', '14', "+ClientInvoicekey+", '�˵���ַ�б�', 'Client Invoice List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_F.addSubSQLScript( listDetailBefore +"?, '1401', '', '��Ʊ̨ͷ', 'Title', '18', '1', '2', '13', '2', '2', '', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?, '1402', '', '�ռ������������ģ�', 'Name (ZH)', '11', '1', '3', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1403', '', '�ռ���������Ӣ�ģ�', 'Name (EN)', '11', '1', '5', '13', '2', '2', 'clientInvoiceAction.do?proc=to_objectModify', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1417', '', '״̬', 'status', '6', '1', '16', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1436', '', '�޸���', 'Modify By', '7', '1', '14', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1437', '', '�޸�ʱ��', 'Modify Date', '11', '1', '15', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1438', '', '�˵���ַID', 'Client Inovice ID', '7', '1', '1', '13', '2', '1', 'clientInvoiceAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?, '1439', '', '�ͻ�����', 'Client Name', '22', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_F.addSubSQLScript( listDetailBefore +"?,'1430', '', '�ͻ�ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_F );

         
         
         //��Ա - ������Ϣ
         SQLScriptDTO sqlScriptDTO_G = new SQLScriptDTO();
         sqlScriptDTO_G.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '17', '2', null, '��Ա - ������Ϣ', 'Employee', null, '', '1', '1', null, null, null, null, null,"+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_G.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'1760', null, '��ԱID', 'Employee ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
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
         String Employeekey=insertDB( sqlScriptDTO_G );
         //��Ա�б�
         SQLScriptDTO sqlScript_G = new SQLScriptDTO();
         sqlScript_G.setSqlScript( listHeaderBefore+""+accountId+", null, '0', '2', '', '17', "+Employeekey+", '��Ա�б�', 'Employee List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1725', '', '��Ա���', 'Employee No', '8', '1', '2', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?,'1701', '', '���������ģ�', 'Employee Name (ZH)', '12', '1', '3', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?,'1700', '', '������Ӣ�ģ�', 'Employee Name (EN)', '12', '1', '4', '13', '2', '2', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?,'1728', '', '֤������', 'Certificate Type', '7', '1', '5', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1729', '', '֤������', 'Certificate Number', '15', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1703', '', '״̬', 'Status', '6', '1', '12', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?,'1780', '', '�޸���', 'Modify By', '8', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1781', '', '�޸�ʱ��', 'Modify Date', '11', '1', '11', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?,'1760', '', '��ԱID', 'Employee ID', '7', '1', '1', '13', '2', '1', 'employeeAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1', '', '�Ͷ���ͬ', 'Labor Contract', '7', '1', '8', '13', '2', '2', '', '0', '${0}', 'numberOfLaborContract', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_G.addSubSQLScript( listDetailBefore +"?, '1', '', '����Э��', 'Dispatch Contract', '7', '1', '9', '13', '2', '2', '', '0', '${0}', 'numberOfServiceContract', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         insertDB( sqlScript_G );

         
         
         //�����ͬ
         SQLScriptDTO sqlScriptDTO_H = new SQLScriptDTO();
         sqlScriptDTO_H.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '18', '2', null, '�����ͬ', 'Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
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
         String contractKey=insertDB( sqlScriptDTO_H );
         SQLScriptDTO sqlScript_H = new SQLScriptDTO();
         sqlScript_H.setSqlScript( listHeaderBefore +""+accountId+", null, '0', '2', '', '18', "+contractKey+", '�����ͬ�б�', 'Contract List', '10', '0', '1', '2', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1824', '', '�޸���', 'Modify By', '6', '1', '16', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1825', '', '�޸�ʱ��', 'Modify Date', '12', '1', '17', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1804', '', '��ͬģ��', 'Template', '12', '1', '15', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1805', '', '��ͬ���', 'Contract Number', '10', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1809', '', '��ͬ����', 'Contract Name', '18', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?,'1818', '', '״̬', 'Status', '6', '1', '19', '13', '1', '2', '', '0', '${0}', 'isLink', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?, '1820', '', '��ͬID', 'Contract ID', '7', '1', '1', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?, '1800', '', '�ͻ�ID', 'Client ID', '7', '1', '9', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '0', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_H.addSubSQLScript( listDetailBefore +"?, '1801', '', '�ͻ�����', 'Client Name', '22', '1', '13', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         insertDB( sqlScript_H );
         
         
         
         //�Ͷ���ͬ
         SQLScriptDTO sqlScriptDTO_I = new SQLScriptDTO();
         sqlScriptDTO_I.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '24', '2', null, '�Ͷ���ͬ', 'Labor Contract', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2403', null, '�Ͷ���ͬ���ƣ�Ӣ�ģ�', 'Labor Contract Name (EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2402', null, '�Ͷ���ͬ���ƣ����ģ�', 'Labor Contract Name (ZH) ', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2405', null, '��ͬ״̬', 'Employee Status', '12', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2400', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2442', null, '�Ͷ���ͬID', 'Labor Contract ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2443', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2444', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2451', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2452', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2453', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_I.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2454', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         insertDB( sqlScriptDTO_I );

         
         
         
         //����
         SQLScriptDTO sqlScriptDTO_J = new SQLScriptDTO();
         sqlScriptDTO_J.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId + ", null, '28', '2', null, '����', 'Order', null, '', '1', '1', null, null, null, null, null, " + userId+ ", '"+ getDate() + "', " + userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2828', null, '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2806', null, '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2808', null, 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2836', null, '����ID', 'Order ID', '1', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2802', null, '�ͻ�ID', 'Client ID', '7', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2804', null, '��ͬID', 'Contract ID', '13', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2812', null, '������ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2813', null, '������������', 'Order End Date', '19', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2826', null, '��������', 'Branch', '31', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2827', null, '������', 'Owner', '33', '0', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2844', null, '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '11', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '2845', null, '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '12', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_J.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'2853', null, '�������', 'Client Number', '9', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String orderKey=insertDB( sqlScriptDTO_J );
         SQLScriptDTO sqlScript_J = new SQLScriptDTO();
         sqlScript_J.setSqlScript( listHeaderBefore+" "+accountId+", null, '0', '2', null, '28', "+orderKey+", '�����б�', 'Order List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2843', '', '�ͻ�����', 'Client Name', '20', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2848', '', '�޸���', 'Modify By', '6', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2849', '', '�޸�ʱ��', 'Modify Date', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2828', '', '״̬', 'Status', '6', '1', '11', '13', '1', '2', '', '0', '${0}', 'isLink', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2802', '', '�ͻ�ID', 'Client ID', '7', '1', '2', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2836', '', '����ID', 'Order ID', '7', '1', '1', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2804', '', '�����ͬID', 'Contract ID', '7', '1', '4', '13', '2', '1', 'clientContractAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedContractId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2812', '', '��ʼ����', 'Start Date', '7', '1', '5', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2813', '', '��������', 'End Date', '7', '1', '6', '13', '2', '2', '', '0', '', '', '1', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2826', '', '��������', 'Branch', '10', '1', '7', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_J.addSubSQLScript( listDetailBefore +"?,'2827', '', '������', 'owner', '12', '1', '8', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_J );

         
         
         //����Э��
         SQLScriptDTO sqlScriptDTO_K = new SQLScriptDTO();
         sqlScriptDTO_K.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '43', '2', null, '����Э��', 'Service Contract', null, '', '1', '1', '', '', '', '', '', "+ userId+ ", '" + getDate() + "', " + userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4305', null, 'Э��״̬', 'Contract Service Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4311', null, '�ͻ�ID', 'Client ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4303', null, '����Э�����ƣ�Ӣ�ģ�', 'Service Contract Name (EN)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4302', null, '����Э�����ƣ����ģ�', 'Service Contract Name (ZH) ', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4300', null, '��ԱID', 'Employee ID', '9', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4340', null, '����Э��ID', 'Service Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4341', null, '��Ա���������ģ�', 'Employee Name (ZH)', '10', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4342', null, '��Ա������Ӣ�ģ�', 'Employee Name (EN) ', '11', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4356', null, '��ʼ���ڣ���ʼ��', 'Start Date (From)', '5', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4357', null, '��ʼ���ڣ���ֹ��', 'Start Date (To)', '6', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4358', null, '�������ڣ���ʼ��', 'End Date (From)', '7', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4359', null, '�������ڣ���ֹ��', 'End Date (To)', '8', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_K.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4361', null, '�������', 'Client Number', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String serviceContractKey=insertDB( sqlScriptDTO_K );
         SQLScriptDTO sqlScript_K = new SQLScriptDTO();
         sqlScript_K.setSqlScript( listHeaderBefore+""+accountId+", null, '0', '2', null, '43', "+serviceContractKey+", '����Э���б�', 'Service Contract List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4304', '', '���', 'Contract No', '8', '1', '5', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4305', '', 'Э��״̬', 'Contract Status', '3', '1', '12', '13', '1', '2', '', '0', '${0}${1}', 'isLink,otherLink', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4340', '', '����Э��ID', 'Contract Service ID', '7', '1', '1', '13', '2', '1', 'employeeContractAction.do?proc=to_objectModify&comeFrom=2', '1', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4300', '', '��ԱID', 'Employee ID', '7', '1', '2', '13', '2', '1', 'employeeAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedEmployeeId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4341', '', '���������ģ�', 'Employee Name ZH', '8', '1', '3', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4342', '', '������Ӣ�ģ�', 'Employee Name EN', '8', '1', '4', '13', '2', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'1', '', '����', 'Handle', '3', '1', '13', '13', '2', '2', '', '0', '${0}', 'isShowHandle', '0', '0', '1', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4351', '', '�޸���', 'Modify BY', '7', '1', '9', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4352', '', '�޸�ʱ��', 'Modify By', '11', '1', '10', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4355', '', '����Э������', 'Contract Service Name', '18', '1', '6', '13', '2', '2', '', '0', '', '', '', null, '1', null, '2', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4311', '', '�ͻ�ID', 'Client ID', '7', '1', '8', '13', '2', '1', 'clientAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedClientId', '', null, '1', null, '1', '1', '', '1', '1', "+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4306', '', '����ID', 'Order ID', '7', '1', '7', '13', '2', '1', 'clientOrderHeaderAction.do?proc=to_objectModify&id=${0}', '1', '', 'encodedOrderId', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4346', '', '��Ӷ״̬', 'EmployStatus', '6', '1', '11', '13', '1', '2', '', '0', '', '', '', null, '1', null, '1', '1', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '8583', null, '���֤����', 'ID', '', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4318', null, '��ʼʱ��', 'Start date', '', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1', "+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?,'4319', null, '��������', 'End Date', '', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1',"+after);
         sqlScript_K.addSubSQLScript( listDetailBefore +"?, '4322', null, '������', 'be', '', '1', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '2', '', '1', '1',"+after);
         insertDB( sqlScript_K );

         
         //���ʵ�
         SQLScriptDTO sqlScriptDTO_L = new SQLScriptDTO();
         sqlScriptDTO_L.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '���ʵ�', 'Payslip', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null,'clientId', '�ͻ�ID', 'Client Id', 4, 13, 2, NULL, 0, NULL, NULL, 1, NULL, 1, 1, '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', 5, 13, 2, NULL, 0, NULL, NULL, 1, NULL, 1, 1, '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', 6, 13, 2, NULL, 0, NULL, NULL, 1, NULL, 1, 1, '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'orderId', '����ID', 'Order Id', 7, 13, 2, NULL, 0, NULL, NULL, 1, NULL, 1, 1, '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null,'clientNO', '�������', 'Client Number', 4, 13, 2, NULL, 0, NULL, NULL, 1, NULL, 1, 1, '', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'4306', null, '����ID', 'Order ID', '4', '13', '2', '', '0', '', null, '1', '', '1', '1','', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_L.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '4346', null, '��Ӷ״̬', 'Employee Status', '12', '13', '2', '', '0', '', null, '1', '', '1', '1','', '', '', '', ''," + userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         
         
         String payslip=insertDB( sqlScriptDTO_L );
         SQLScriptDTO sqlScript_L = new SQLScriptDTO();
         sqlScript_L.setSqlScript( listHeaderBefore +""+accountId+", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', "+payslip+", '���ʵ��б�', 'Payslip List', '10', '0', '1', '2', '1', 'payslip', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'employeeId', '��ԱID', 'Staff Id', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'employeeNameZH', '��Ա���������ģ�', 'Staff Name( CH )', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Staff Name( EN )', '100', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'certificateNumber', '֤������', 'ID Number', '150', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'bankName', '��������', 'Bank Name', '150', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'bankAccount', '�����˻�', 'Bank Account', '150', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?,null, 'monthly', 'н���·�', 'Pay Monthly', '60', '2', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?,null, 'orderId', '����ID', 'Order ID', '80', '2', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'contractId', '�Ͷ���ͬID', 'Contract ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?,null, 'beforeTaxSalary', 'Ӧ������', 'Accrued Wages', '150', '2', '99', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'taxAmountPersonal', '��˰', 'Personal Income Tax', '150', '2', '99', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?,null, 'afterTaxSalary', 'ʵ������', 'Real Wages', '100', '2', '99', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_L.addSubSQLScript( listDetailBefore +"?, null, 'status', '״̬', 'Status', '60', '2', '99', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         //���ʵ��б�����
         String  payslipKey=insertDB( sqlScript_L );
         //���ʵ��б� - ��ϸ
         SQLScriptDTO sqlScript_LD = new SQLScriptDTO();
         sqlScript_LD.setSqlScript( listHeaderBefore +""+accountId+", null, "+payslipKey+", '1', 'com.kan.hro.domain.biz.payment.PayslipDTO', '0', '0', '���ʵ��б� - ��ϸ', 'Payslip List - Detail', '10', '0', '1', '2', '0', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_1', '��������', 'Base Salary', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_2', '���ʵ���', 'Salary Adjustment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_3', '��ٿۿ�', 'Leave Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_4', '�����ۿ�', 'Other Withhold', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_5', '��˰', 'Income Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_6', '��λ����', 'Position Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_7', '���½���', 'High Temperature Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_8', 'פ�����', 'Travel Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_9', '��������', 'Other Allowance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_10', 'ҹ�ಹ��', 'Night Shift Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_11', '��ͨ����', 'Transportation Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_12', '�òͲ���', 'Meat Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_13', '��������', 'Other Subsidy', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_14', '������', 'Welfare', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_15', '��Ч', 'Performance', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_16', '����', 'Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_17', 'Ӷ��', 'Commission', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_18', '���ս�', 'Annual Bonus', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_21', '�Ӱ� 1.0', 'OT 1.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_22', '�Ӱ� 1.5', 'OT 1.5', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_23', '�Ӱ� 2.0', 'OT 2.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_24', '�Ӱ� 3.0', 'T 3.0', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_25', '�Ӱ໻��', 'OT Shift', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_26', '�����Ӱ�', 'OT Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_31', '����', 'Reimbursement', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_32', '���� - ���ʷ�', 'Reimbursement - Entertainment', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_33', '���� - �Ӱ�', 'Reimbursement - OT', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_41', '���', 'Annual Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_42', '���� - ȫн', 'Sick Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_43', '���� - ��ȫн��70%��', 'Sick Leave - 70%', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_44', '�¼�', 'Absence', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_45', '����', 'Maternity Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_46', '�����', 'Nursing Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_47', 'ɥ��', 'Bereft Leave', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_61_c', '���ϱ���-��˾', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_62_c', 'ҽ�Ʊ���-��˾', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_63_c', 'ʧҵ����-��˾', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_64_c', '���˱���-��˾', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_65_c', '��������-��˾', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_66_c', '�м��˱��Ͻ�-��˾', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_67_c', 'ס��������-��˾', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_68_c', 'ȡů��-��˾', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_69_c', '�����-��˾', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_70_c', '���������-��˾', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_71_c', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_72_c', '����ҽ�Ʊ���-��˾', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_73_c', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_74_c', '���ũ��ҽ��-��˾', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_75_c', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_61_p', '���ϱ���-����', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_62_p', 'ҽ�Ʊ���-����', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_63_p', 'ʧҵ����-����', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_64_p', '���˱���-����', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_65_p', '��������-����', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_66_p', '�м��˱��Ͻ�-����', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_67_p', 'ס��������-����', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_68_p', 'ȡů��-����', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_69_p', '�����-����', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_70_p', '���������-����', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_71_p', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_72_p', '����ҽ�Ʊ���-����', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_73_p', '��ҽ�Ʊ���-����', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_74_p', '���ũ��ҽ��-����', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_75_p', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_130', '����ѣ���˰��', 'Service Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_131', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Prepay', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_132', '����ѣ���˰�� - ΥԼ��', 'Service Fee (I-Tax) - Cancelation', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_133', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Other', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_134', '��ѵ�ѣ���˰��', 'Training Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_135', '��Ƹ�ѣ���˰��', 'Recruitment Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_136', '����ѣ���˰��', 'Agent Fee (I-Tax)', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_141', 'Ӫҵ˰', 'Tax', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_142', '��ְ���', 'Onboard Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_143', '������', 'Annual Physical Examination', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_144', '�籣���쿨��', 'SB Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_145', 'ҽ�����쿨��', 'Medical Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_146', '���ʿ��쿨��', 'Salary Card Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_147', '�����', 'Labour Union Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_148', '���ɽ�', 'Overdue Fine', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_149', '������', 'Poundage', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_150', '������', 'Paper Work Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_151', '������', 'Each Other Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_161', '�籣�����', 'Social Benefit Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_162', '�����������', '3rd Part Management Fee', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_181', '�������', 'Labour Union Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_182', '���˻���', 'Injury Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?,null, 'item_183', '��������', 'Maternity Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_184', '���ϻ���', 'Disable Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_185', '�油����', 'Replace Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_186', '���ջ���', 'VC Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_187', 'ȫ�����', 'Full Liability Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_LD.addSubSQLScript( listDetailBefore +"?, null, 'item_188', '��������', 'Other Fund', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         insertDB( sqlScript_LD );

         
         //��˰
         SQLScriptDTO sqlScriptDTO_M = new SQLScriptDTO();
         sqlScriptDTO_M.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '��˰', 'Income Tax ', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_M.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String incomeTax=insertDB( sqlScriptDTO_M );
         SQLScriptDTO sqlScript_M = new SQLScriptDTO();
         sqlScript_M.setSqlScript( listHeaderBefore +" "+accountId+", null, '0', '1', 'com.kan.hro.domain.biz.payment.PayslipTaxDTO', '0', "+incomeTax+", '��˰�걨�б�', 'Income Tax List', '10', '0', '1', '2', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?,null, 'employeeId', '��ԱID', 'EmployeeId', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '15', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?,null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '15', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?,null, 'orderId', '����ID', 'Order', '10', '1', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?,null, 'certificateNumber', '֤������', 'Certificate Number', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?, null, 'monthly', '��˰�����·�', 'Tax Monthy', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?, null, 'taxAmountPersonal', '��˰���', 'Tax Amount', '10', '1', '7', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?, null, 'cityId', '�걨����', 'City', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_M.addSubSQLScript( listDetailBefore +"?, null, 'status', '״̬', 'Status', '10', '1', '9', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_M );

         
         
         //����
         SQLScriptDTO sqlScriptDTO_N = new SQLScriptDTO();
         sqlScriptDTO_N.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '78', '2', '', '����', 'Order', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', "+ userId+ ", '"+ getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7852', '', '״̬', 'status', '35', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,'7806', '', '����ʵ��', 'Entity', '20', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7808', '', 'ҵ������', 'Business Type', '21', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7836', '', '����ID', 'Order ID', '1', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7812', '', '������ʼ����', 'Order Start Date', '17', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7813', '', '������������', 'Order End Date', '19', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7826', '', '��������', 'Branch', '31', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '', "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_N.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, '7827', '', '������', 'Owner', '33', '0', '2', '', '0', '', '', '1', '', '1', '1', '', '', '', '', '',"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         insertDB( sqlScriptDTO_N );

         
         //�籣��
         SQLScriptDTO sqlScriptDTO_O = new SQLScriptDTO();
         sqlScriptDTO_O.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '�籣��', 'SB Bill', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '"+ getDate()+ "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeId', '��ԱID', 'Employee ID', '1', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameZH', '��Ա���������ģ�', 'Employee Name(ZH)', '2', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name(EN)', '3', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '����Э��ID', 'Labor Contract ID', '4', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '�ͻ�ID', 'Client ID', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNo', '�������', 'Client No', '6', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '7', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '8', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'vendorNameZH', '��Ӧ������', 'Vendor Name', '5', '13', '2', null, '0', null, null, '1', null, '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_O.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, NULL, 'batchId',      '����ID', 'Batch ID',       1,   13,   2, NULL,   0, NULL, NULL,  1,  NULL, 1, 1, null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String sbKey=insertDB( sqlScriptDTO_O );
         //�籣���б�
         SQLScriptDTO sqlScript_O = new SQLScriptDTO();
         sqlScript_O.setSqlScript( listHeaderBefore +" "+accountId+", null, '0', '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', "+sbKey+", '�籣��', 'Social Benefit ', '10', '0', '1', '2', '1', '', '1', '1', "+after);
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'clientId', '�ͻ�ID', 'Client ID', '100', '2', '1', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'clientNo', '�������', 'Client Number', '100', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'clientNameZH', '�ͻ�����', 'Client Name', '100', '2', '3', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1', "+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'employeeId', '��ԱID', 'Staff ID', '100', '2', '4', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'employeeNameZH', '��Ա����', 'Employee Name', '100', '2', '5', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'contractId', '����Э��ID', 'Labor Contract ID', '100', '2', '6', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'contractStatus', '����Э��״̬', 'Staff Status', '100', '2', '7', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'monthly', '�걨�·�', 'Monthly', '100', '2', '8', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'employeeSBNameZH', '�籣��������', 'Social Benefit Name', '100', '2', '9', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'sbStatus', '�籣״̬', 'Social Benefit Status', '100', '2', '10', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'vendorNameZH', '��Ӧ������', 'Vendor Name', '100', '2', '11', '13', '2', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'amountCompany', '�ϼƣ���˾��', 'Amount Company', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'amountPersonal', '�ϼƣ����ˣ�', 'Amount Personal', '100', '2', '99', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?, null, 'status', '״̬', 'Status', '100', '2', '99', '13', '1', '2', null, '0', null, null, '0', '0', '1', '0', '1', '1', null, '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore+"?,null, 'entityId', '����ʵ��', 'Entity Name', '100', '2', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'certificateNumber', '֤������', 'Certificate Number', 100, 2, 5, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'sbNumber', '�籣���ʺ�', 'Social Benefit Card', 100, 2, 10, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?,NULL, 'fundNumber', '�������ʺ�', 'Accumulation Fund Card', 100, 2, 10, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         sqlScript_O.addSubSQLScript( listDetailBefore + "?, NULL, 'basePersonal', '����', 'Base Personal', 100, 2, 9, 13, 2, 2, NULL, 0, NULL, NULL, 0, 0, 1, 0, 1, 1, NULL, 1, 1," + after );
         //�籣������
         String sbDetailKey=insertDB( sqlScript_O );
         //�籣�� - ��ϸ
         SQLScriptDTO sqlScript_OD = new SQLScriptDTO();
         sqlScript_OD.setSqlScript( listHeaderBefore +""+accountId+", null, "+sbDetailKey+", '1', 'com.kan.hro.domain.biz.sb.SBDTO', '0', '0', '�籣�� - ��ϸ', 'Social Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1',"+after);
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_61_c', '���ϱ���-��˾', 'Endowment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_62_c', 'ҽ�Ʊ���-��˾', 'Medical - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_63_c', 'ʧҵ����-��˾', 'Unemployment - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_64_c', '���˱���-��˾', 'Injury - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_65_c', '��������-��˾', 'Maternity - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_66_c', '�м��˱��Ͻ�-��˾', 'Disable - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_67_c', 'ס��������-��˾', 'Housing - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_68_c', 'ȡů��-��˾', 'Warming - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_69_c', '�����-��˾', 'Allocation - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_70_c', '���������-��˾', 'Archive - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_71_c', 'סԺҽ�Ʊ���-��˾', 'Zhuyuan Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_72_c', '����ҽ�Ʊ���-��˾', 'Buchong Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_73_c', '��ҽ�Ʊ���-��˾', 'Dabing Yiliao - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_74_c', '���ũ��ҽ��-��˾', 'Yidi Nongcun - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_75_c', 'ҽ�ƹ��ý�-��˾', 'Medical Aid Funding - Company', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_61_p', '���ϱ���-����', 'Endowment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_62_p', 'ҽ�Ʊ���-����', 'Medical - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_63_p', 'ʧҵ����-����', 'Unemployment - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_64_p', '���˱���-����', 'Injury - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_65_p', '��������-����', 'Maternity - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_66_p', '�м��˱��Ͻ�-����', 'Disable - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_67_p', 'ס��������-����', 'Housing - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_68_p', 'ȡů��-����', 'Warming - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_69_p', '�����-����', 'Allocation - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?,null, 'item_70_p', '���������-����', 'Archive - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1', "+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_71_p', 'סԺҽ�Ʊ���-����', 'Zhuyuan Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_72_p', '����ҽ�Ʊ���-����', 'Buchong Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_73_p', '��ҽ�Ʊ���-����', 'Dabing Yiliao - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_74_p', '���ũ��ҽ��-����', 'Yidi Nongcun - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         sqlScript_OD.addSubSQLScript( listDetailBefore+"?, null, 'item_75_p', 'ҽ�ƹ��ý�-����', 'Medical Aid Funding - Personal', '150', '2', '50', '13', '2', '2', null, '0', null, null, '0', '0', '3', '0', '2', '1', null, '1', '1',"+after );
         insertDB( sqlScript_OD );

         
         
         //���㵥
         SQLScriptDTO sqlScriptDTO_P = new SQLScriptDTO();
         sqlScriptDTO_P.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '0', '1', 'com.kan.hro.domain.biz.settlement.OrderDTO', '���㵥', 'Settlement', null, '', '1', '1', null, null, null, null, null, "+ userId+ ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientId', '�ͻ�ID', 'Client ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'orderId', '����ID', 'Order ID', '5', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_P.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNO', '�������', 'clientNO', '2', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String settlement=insertDB( sqlScriptDTO_P );
         SQLScriptDTO sqlScript_P = new SQLScriptDTO();
         sqlScript_P.setSqlScript( listHeaderBefore +""+accountId+", null, '0', 1, 'com.kan.hro.domain.biz.settlement.OrderDTO', '0', "+settlement+", '���㵥', 'Settlement', '10', '0', '1', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?,null, 'clientId', '�ͻ�ID', 'Client ID', '16', '1', '1', '13', '2', '1', 'orderBillHeaderViewAction.do?proc=to_object_detail&clientId=${0}&monthly=${1}&status=${2}&orderId=${3}', '0', '', 'encodedClientId,encodedMonthly,status,encodedOrderId', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'monthly', '�����·�', 'Monthly', '16', '1', '30', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'decodeOrderDTOStatus', '����״̬', 'Status', '15', '1', '70', '13', '2', '2', '', '0', '${0}', 'decodeOrderDTOStatus4Page', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'billAmountCompany', '�ϼƣ���˾Ӫ�գ�', 'Bill Amount Company', '16', '1', '40', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'costAmountCompany', '�ϼƣ���˾�ɱ���', 'Cost Amount Company', '16', '1', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'orderId', '����ID', 'Order ID', '16', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?,null, 'itemServiceFree', '�����', 'Service Free', '10', '1', '60', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '16', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '16', '1', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?,null, 'desInfo', '��ע', 'Description', '200', '2', '65', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'entityId', '����ʵ��', 'Entity ID', '16', '1', '21', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_P.addSubSQLScript( listDetailBefore +"?, null, 'businessTypeId', 'ҵ������', 'Business Type ID', '16', '1', '22', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_P );

         
         
         //������ϸ
         SQLScriptDTO sqlScriptDTO_Q = new SQLScriptDTO();
         sqlScriptDTO_Q.setSqlScript( "INSERT INTO `hro_def_search_header` (accountId,corpId, tableId, useJavaObject, javaObjectName, nameZH, nameEN, isSearchFirst, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy,createDate, modifyBy, modifyDate) VALUES ("+ accountId+ ", null, '0', 1, 'com.kan.hro.domain.biz.settlement.SettlementDTO', '������ϸ', 'Order Bill Detail', null, '', '1', '1', null, null, null, null, null, "+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'contractId', '��ͬID', 'Contract ID', '1', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name(ZH)', '3', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         sqlScriptDTO_Q.addSubSQLScript( "INSERT INTO `hro_def_search_detail` (searchHeaderId, columnId, propertyName, nameZH, nameEN, columnIndex, fontSize, useThinking, thinkingAction, contentType, content, `range`, display, description, deleted, status, remark1, remark2, remark3, remark4, remark5, createBy, createDate, modifyBy, modifyDate ) VALUES (?,null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name(EN)', '4', '13', '2', '', '0', '', null, '1', '', '1', '1', null, null, null, null, null,"+ userId + ", '" + getDate() + "', " + userId + ", '" + getDate() + "')" );
         String orderBill=insertDB( sqlScriptDTO_Q );
         //��ϸ�굥
         SQLScriptDTO sqlScript_Q = new SQLScriptDTO();
         sqlScript_Q.setSqlScript( listHeaderBefore +""+accountId+", null, '0', '1', 'com.kan.hro.domain.biz.settlement.SettlementDTO', '0', "+orderBill+", '���㵥-��ϸ����', 'Order Bill Detail(Main)', '10', '0', '1', '2', '1', '', '1', '1',"+after);
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'contractId', 'Э��ID', 'Service Contract ID', '16', '1', '8', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?,null, 'employeeNameZH', '��Ա���������ģ�', 'EmployeeName ZH', '10', '1', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?,null, 'employeeNameEN', '��Ա������Ӣ�ģ�', 'Employee Name EN', '10', '1', '10', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?,null, 'clientId', '�ͻ�ID', 'Client ID', '180', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'orderId', '����ID', 'OrderId', '200', '2', '4', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'monthly', '�����·�', 'Monthly', '200', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'clientNameZH', '�ͻ����ƣ����ģ�', 'Client Name (ZH)', '300', '2', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?,null, 'clientNameEN', '�ͻ����ƣ�Ӣ�ģ�', 'Client Name (EN)', '300', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'entityId', '����ʵ��', 'Entity ID', '16', '1', '5', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_Q.addSubSQLScript( listDetailBefore+"?, null, 'businessTypeId', 'ҵ������', 'Business Type ID', '16', '1', '6', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         //��ϸ�굥����
         String QKey=insertDB( sqlScript_Q );
         SQLScriptDTO sqlScript_QD = new SQLScriptDTO();
         sqlScript_QD.setSqlScript( listHeaderBefore +""+accountId+", null, "+QKey+", '1', 'com.kan.hro.domain.biz.settlement.SettlementDTO', '0', '0', '���㵥-��ϸ�ӱ�', 'Order Bill Detail(Sub)', '10', '0', '1', '2', '0', '', '1', '1',"+after);
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_122', '�����', 'Service Fee', '10', '1', '10', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_1', '��������', 'Base Salary', '10', '1', '11', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_2', '���ʵ���', 'Salary Adjustment', '10', '1', '12', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_3', '��ٿۿ�', 'Leave Withhold', '10', '1', '13', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_4', '�����ۿ�', 'Other Withhold', '10', '1', '14', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_5', '��˰', 'Income Tax', '10', '1', '15', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_6', '��λ����', 'Position Allowance', '10', '1', '16', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_7', '���½���', 'High Temperature Allowance', '10', '1', '17', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_8', 'פ�����', 'Travel Allowance', '10', '1', '18', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_9', '��������', 'Other Allowance', '10', '1', '19', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_10', 'ҹ�ಹ��', 'Night Shift Subsidy', '10', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_11', '��ͨ����', 'Transportation Subsidy', '10', '1', '21', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_12', '�òͲ���', 'Meat Subsidy', '10', '1', '22', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_13', '��������', 'Other Subsidy', '10', '1', '23', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_14', '������', 'Welfare', '10', '1', '24', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_15', '��Ч', 'Performance', '10', '1', '25', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_16', '����', 'Bonus', '10', '1', '26', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_17', 'Ӷ��', 'Commission', '10', '1', '27', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_18', '���ս�', 'Annual Bonus', '10', '1', '28', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_21', '�Ӱ� 1.0', 'OT 1.0', '10', '1', '29', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_22', '�Ӱ� 1.5', 'OT 1.5', '10', '1', '30', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_23', '�Ӱ� 2.0', 'OT 2.0', '10', '1', '31', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_24', '�Ӱ� 3.0', 'OT 3.0', '10', '1', '32', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_25', '�Ӱ໻��', 'OT Shift', '10', '1', '33', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_26', '�����Ӱ�', 'OT Other', '10', '1', '34', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_31', '����', 'Reimbursement', '10', '1', '35', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_32', '���� - ���ʷ�', 'Reimbursement - Entertainment', '10', '1', '36', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_33', '���� - �Ӱ�', 'Reimbursement - OT', '10', '1', '37', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_41', '���', 'Annual Leave', '10', '1', '38', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_42', '���� - ȫн', 'Sick Leave', '10', '1', '39', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_43', '���� - ��ȫн', 'Sick Leave - Discount', '10', '1', '40', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_44', '�¼�', 'Absence', '10', '1', '41', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_45', '����', 'Maternity Leave', '10', '1', '42', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_46', '�����', 'Nursing Leave', '10', '1', '43', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_47', 'ɥ��', 'Bereft Leave', '10', '1', '44', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_60', '����', 'Back Leave', '10', '1', '45', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_61', '���ϱ���', 'Endowment', '10', '1', '46', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_62', 'ҽ�Ʊ���', 'Medical', '10', '1', '47', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_63', 'ʧҵ����', 'Unemployment', '10', '1', '48', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_64', '���˱���', 'Injury ', '10', '1', '49', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_65', '��������', 'Maternity', '10', '1', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_66', '�м��˱��Ͻ�', 'Disable', '10', '1', '51', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_67', 'ס��������', 'Housing', '10', '1', '52', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_68', 'ȡů��', 'Warming', '10', '1', '53', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_69', '�����', 'Allocation', '10', '1', '54', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_70', '���������', 'Archive', '10', '1', '55', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_71', 'סԺҽ�Ʊ���', 'Zhuyuan Yiliao', '10', '1', '56', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_72', '����ҽ�Ʊ���', 'Buchong Yiliao', '10', '1', '57', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_73', '��ҽ�Ʊ���', 'Dabing Yiliao', '10', '1', '58', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_74', '���ũ��ҽ��', 'Yidi Nongcun', '10', '1', '59', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_75', 'ҽ�ƹ��ý�', 'Medical Aid Funding', '10', '1', '60', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_81', '������������', 'Disan Zheren', '10', '1', '61', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_82', '����ҽ�Ʊ���', 'Buchong Yiliao', '10', '1', '62', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_83', '����ҽ�Ʊ���', 'Menzhen Yiliao', '10', '1', '63', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_84', 'סԺҽ�Ʊ���', 'Zhuyuan Yiliao', '10', '1', '64', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_85', '�����˺�����', 'Yiwai Shanghai', '10', '1', '65', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_86', '�������ﱣ��', 'Yiwai Menzhen', '10', '1', '66', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_87', '����סԺ����', 'Yiwai Zhuyuan', '10', '1', '67', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_88', 'Ů����������', 'Nvxing Shengyu', '10', '1', '68', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_89', '�������ٱ���', 'Dingqi Renshou', '10', '1', '69', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_90', '�ۺ�ҽ�Ʊ���', 'Zonghe Yiliao', '10', '1', '70', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_91', '���乤�˱���', 'Buchong Gongshang', '10', '1', '71', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_92', '��������', 'Gonggong Baoxian', '10', '1', '72', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_93', '���ⲹ��', 'Teshu Buchong', '10', '1', '73', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_121', '�����', 'Management Fee', '10', '1', '74', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_123', '����� - ����', 'Service Fee - Prepay', '10', '1', '76', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_124', '����� - ΥԼ��', 'Service Fee - Cancelation', '10', '1', '77', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_125', '����� - ����', 'Service Fee - Other', '10', '1', '78', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_126', '��ѵ��', 'Training Fee', '10', '1', '79', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_127', '��Ƹ��', 'Recruitment Fee', '10', '1', '80', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_128', '�����', 'Agent Fee', '10', '1', '81', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_129', '����ѣ���˰��', 'Management Fee (I-Tax)', '10', '1', '82', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_130', '����ѣ���˰��', 'Service Fee (I-Tax)', '10', '1', '83', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_131', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Prepay', '10', '1', '84', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_132', '����ѣ���˰�� - ΥԼ��', 'Service Fee (I-Tax) - Cancelation', '10', '1', '85', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_133', '����ѣ���˰�� - ����', 'Service Fee (I-Tax) - Other', '10', '1', '86', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_134', '��ѵ�ѣ���˰��', 'Training Fee (I-Tax)', '10', '1', '87', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_135', '��Ƹ�ѣ���˰��', 'Recruitment Fee (I-Tax)', '10', '1', '88', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_136', '����ѣ���˰��', 'Agent Fee (I-Tax)', '10', '1', '89', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_141', 'Ӫҵ˰', 'Tax', '10', '1', '90', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_142', '��ְ���', 'Onboard Physical Examination', '10', '1', '91', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_143', '������', 'Annual Physical Examination', '10', '1', '92', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_144', '�籣���쿨��', 'SB Card Fee', '10', '1', '93', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_145', 'ҽ�����쿨��', 'Medical Card Fee', '10', '1', '94', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_146', '���ʿ��쿨��', 'Salary Card Fee', '10', '1', '95', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_147', '�����', 'Labour Union Fee', '10', '1', '96', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_148', '���ɽ�', 'Overdue Fine', '10', '1', '97', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_149', '������', 'Poundage', '10', '1', '98', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_150', '������', 'Paper Work Fee', '10', '1', '99', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_151', '������', 'Each Other Fee', '10', '1', '100', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_161', '�籣�����', 'Social Benefit Management Fee', '10', '1', '91', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_162', '�����������', '3rd Part Management Fee', '10', '1', '101', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_181', '�������', 'Labour Union Fund', '10', '1', '102', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_182', '���˻���', 'Injury Fund', '10', '1', '103', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1', "+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_183', '��������', 'Maternity Fund', '10', '1', '104', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_184', '���ϻ���', 'Disable Fund', '10', '1', '105', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_185', '�油����', 'Replace Fund', '10', '1', '106', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_186', '���ջ���', 'VC Fund', '10', '1', '107', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?, null, 'item_187', 'ȫ�����', 'Full Liability Fund', '10', '1', '108', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'item_188', '��������', 'Other Fund', '10', '1', '109', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after );
         sqlScript_QD.addSubSQLScript( listDetailBefore+"?,null, 'orderId', '����ID', 'Order ID', '16', '1', '20', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         insertDB( sqlScript_QD );

         
         //�̱���
         SQLScriptDTO sqlScript_S = new SQLScriptDTO();
         sqlScript_S.setSqlScript( listHeaderBefore+""+accountId+", null, '0', '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '�̱���', 'Commercial Benefit List', '10', '0', '1', '2', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?,null, 'employeeId', 'Ա��ID', 'Staff Id', '10', '1', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?, null, 'employeeNameZH', '����', 'Name', '10', '1', '2', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?, null, 'contractId', '�Ͷ���ͬID', 'Labor Contract ID', '10', '1', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?,null, 'employStatus', 'Ա��״̬', 'Staff Status', '10', '1', '4', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?, null, 'monthly', '�����·�', 'Cost Month', '10', '1', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?, null, 'employeeCBNameZH', '�̱�����', 'Commercial Benefit Name', '10', '1', '6', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?,null, 'status', '״̬', 'Status', '10', '1', '8', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '2',"+after );
         sqlScript_S.addSubSQLScript( listDetailBefore+"?,null, 'cbStatus', '�̱�״̬', 'Commercial Benefit  Status', '10', '1', '99', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after );
         //�̱�������
         String sbdkey=insertDB( sqlScript_S );
         //�̱�����ϸ
         SQLScriptDTO sqlScript_SD = new SQLScriptDTO();
         sqlScript_SD.setSqlScript( listHeaderBefore +""+accountId+", null, "+sbdkey+", '1', 'com.kan.hro.domain.biz.cb.CBDTO', '0', '0', '�̱��� - ��ϸ', 'Commercial Benefit Detail', '10', '0', '1', '2', '0', '', '1', '1',"+after);
         sqlScript_SD.addSubSQLScript( listDetailBefore +"?, null, 'item_83', '����ҽ�Ʊ���', 'Jiben Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_SD.addSubSQLScript( listDetailBefore +"?, null, 'item_84', '�ۺ�ҽ�Ʊ���', 'Zonghe Yiliao', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_SD.addSubSQLScript( listDetailBefore +"?, null, 'item_85', '���乤�˱���', 'Buchong Gongshang', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_SD.addSubSQLScript( listDetailBefore +"?, null, 'item_81', '������������', 'Disanzhe', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         sqlScript_SD.addSubSQLScript( listDetailBefore +"?, null, 'item_86', '���ⲹ��', 'Teshu Buchong', '100', '2', '50', '13', '2', '2', '', '0', '', '', '0', '0', '3', '0', '2', '1', '', '1', '1',"+after);
         insertDB( sqlScript_SD );
         
         //�̱�������
         SQLScriptDTO sqlScript_CBSetting = new SQLScriptDTO();
         sqlScript_CBSetting.setSqlScript(listHeaderBefore +""+accountId+",   null, '0', '2', '', '39', '0', '�̱�������', 'CB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3912', null, '���ID', 'CB ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3914', null, '��ԱID', 'Employee ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3915', null, '��Ա�������У�', 'Employee Name(ZH)', '120', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3916', null, '��Ա������Ӣ��', 'Employee Name(EN)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3921', null, '�ͻ�ID', 'Client ID', '80', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3913', null, '����ID', 'Order ID', '80', '2', '11', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3900', null, '����Э��ID', 'Contract ID', '80', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_CBSetting.addSubSQLScript(listDetailBefore +"?, '3920', null, 'Э��״̬', 'Contract Status', '80', '2', '15', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_CBSetting );
         
         //�籣������
         SQLScriptDTO sqlScript_SBSetting = new SQLScriptDTO();
         sqlScript_SBSetting.setSqlScript( listHeaderBefore+""+accountId+",   null,  '0', '2', '', '37', '0', '�籣������', 'SB Setting', '10', '0', '1', '2', '0', '', '1', '1',"+after );
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3700', null, '����Э��ID', 'Contract ID', '80', '2', '1', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3719', null, '���ID', 'Employee SB ID', '80', '2', '3', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3723', null, '��ԱID', 'Employee ID', '80', '2', '5', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3724', null, '��Ա�������У�', 'Employee Name(ZH)', '120', '2', '7', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',  "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3725', null, '��Ա������Ӣ��', 'Employee Name(EN)', '120', '2', '9', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3701', null, '�籣�����𷽰�', 'SB', '120', '2', '11', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3706', null, '�ӱ�����', 'Start Date', '120', '2', '13', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3707', null, '�˱�����', 'End Date', '120', '2', '15', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3703', null, '�籣������״̬', 'SB Status', '80', '2', '17', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3730', null, '�����������', 'Order Description', '150', '2', '19', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3729', null, '��ͬ״̬', 'Contract Status', '80', '2', '21', '13', '1', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1', "+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3726', null, '���֤����', 'Certificate Number', '150', '2', '23', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         sqlScript_SBSetting.addSubSQLScript(listDetailBefore +"?,  '3731', null, '�ͻ�ID', 'Client ID', '80', '2', '25', '13', '2', '2', '', '0', '', '', '0', '0', '1', '0', '1', '1', '', '1', '1',"+after);
         insertDB( sqlScript_SBSetting );
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
   /**
    * ������ӱ� ������������
    * 
    * @author : Ian.huang
    * @date   : 2014-6-4
    * @param  : @param sqlScripts
    * @return : void
    */
   private String insertDB(final SQLScriptDTO sqlScripts){
      String primaryKey="";
      String error="";
      try
      {
        if(sqlScripts!=null){
           final PreparedStatement HeaderpreparedStatement = connection.prepareStatement(sqlScripts.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS);
           HeaderpreparedStatement.executeUpdate();
           // ��ȡ����
           final ResultSet rs = HeaderpreparedStatement.getGeneratedKeys();
           if ( rs.next() )
           {
              primaryKey = rs.getString( 1 );
           }
           if(sqlScripts.getSubSQLScriptDTOs()!=null&&sqlScripts.getSubSQLScriptDTOs().size()>0){
              for ( SQLScriptDTO sqlScriptDTO : sqlScripts.getSubSQLScriptDTOs() )
              {
                 final PreparedStatement DetailpreparedStatement = connection.prepareStatement(sqlScriptDTO.getSqlScript(), PreparedStatement.RETURN_GENERATED_KEYS);
                 error=sqlScriptDTO.getSqlScript();
                 if(primaryKey!=""&&sqlScriptDTO.getSqlScript().contains( "?" )){
                    DetailpreparedStatement.setObject( 1, primaryKey );
                 }
                 DetailpreparedStatement.executeUpdate();
              }
           }
        }
      }
      catch ( SQLException e )
      {
        System.err.println("Error:"+error);
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
}
