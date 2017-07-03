package com.kan.hro.service.impl.biz.employee;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.PositionDao;
import com.kan.base.dao.inf.security.PositionStaffRelationDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.dao.inf.security.UserDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.domain.security.PositionStaffRelationVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffDTO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.util.ContentUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.IDCard;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class EmployeeImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO > >
{
   private StaffDao staffDao;
   private EmployeeDao employeeDao;
   private UserDao userDao;
   private PositionStaffRelationDao positionStaffRelationDao;
   private PositionDao positionDao;

   private String role;

   @Override
   public void init( List< CellDataDTO > importData )
   {
      // No Use
   }

   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importDatas, HttpServletRequest request )
   {

      boolean flag = true;
      try
      {
         role = BaseAction.getRole( request, null );
         if ( importDatas != null && importDatas.size() > 0 )
         {
            // 自动填充性别和生日
            if ( importDatas.size() > 0 )
            {
               for ( CellDataDTO cellDataDTO : importDatas )
               {
                  // 初始化雇员ID
                  CellData employeeIdCellData = cellDataDTO.getCellDataByColumnNameDB( "employeeId" );

                  // 初始化性别CellData
                  CellData salutationCellData = cellDataDTO.getCellDataByColumnNameDB( "salutation" );

                  // 初始化生日CellData
                  CellData birthdayCellData = cellDataDTO.getCellDataByColumnNameDB( "birthday" );

                  /**
                   * 证件号码处理
                   */
                  // 初始化证件类型
                  CellData certificateTypeCellData = cellDataDTO.getCellDataByColumnNameDB( "certificateType" );
                  String certificateTypeValue = "";

                  if ( certificateTypeCellData != null && certificateTypeCellData.getDbValue() != null )
                  {
                     certificateTypeValue = ( String ) certificateTypeCellData.getDbValue();
                  }

                  // 初始化证件号码
                  CellData certificateNumberCellData = cellDataDTO.getCellDataByColumnNameDB( "certificateNumber" );

                  // 查询身份证号码并自动更新性别
                  if ( certificateNumberCellData != null && KANUtil.filterEmpty( certificateNumberCellData.getValue() ) != null )
                  {
                     // 如果 证件类型 不为空
                     if ( "1".equals( certificateTypeValue ) )
                     {
                        final String certificateNumber = certificateNumberCellData.getValue();

                        if ( !com.kan.base.util.IDCard.validateCard( certificateNumber ) )
                        {
                           certificateNumberCellData.setErrorMessange( "单元格[" + certificateNumberCellData.getCellRef() + "]身份证号码不正确" );
                           flag = false;
                        }
                        else
                        {
                           // 查找身份证对应的雇员
                           final EmployeeVO tempEmployeeVO = new EmployeeVO();
                           tempEmployeeVO.setAccountId( BaseAction.getAccountId( request, null ) );
                           tempEmployeeVO.setCorpId( BaseAction.getCorpId( request, null ) );
                           tempEmployeeVO.setCertificateType( "1" );
                           tempEmployeeVO.setCertificateNumber( ( String ) certificateNumberCellData.getDbValue() );
                           final List< Object > employeeVOs = this.employeeDao.getEmployeeVOsByCondition( tempEmployeeVO );

                           if ( employeeVOs != null && employeeVOs.size() > 0 )
                           {
                              // 不需要insert
                              cellDataDTO.setNeedInsert( false );

                              // 如果EmployeeId不为空不操作、提示不能新建
                              if ( employeeIdCellData != null && KANUtil.filterEmpty( employeeIdCellData.getValue() ) != null )
                              {
                                 flag = false;
                                 employeeIdCellData.setErrorMessange( "第 " + ( employeeIdCellData.getRow() + 1 ) + " 行 身份证号码[" + certificateNumberCellData.getValue()
                                       + "]已存在对应员工，如需修改对应员工的数据，请移除单元格[" + employeeIdCellData.getCellRef() + "]中 " + employeeIdCellData.getColumnVO().getNameZH() + "的值。" );
                              }
                              else
                              {
                                 final EmployeeVO employeeVO = ( EmployeeVO ) employeeVOs.get( 0 );

                                 // 更新 cellDataDTO 的 CellData 集合
                                 if ( !updataCellDatas( employeeVO, cellDataDTO, request ) )
                                 {
                                    flag = false;
                                 }

                                 // 更新性别
                                 employeeVO.setSalutation( getSalutationByCertificateNumber( certificateNumber ) );

                                 // 更新生日
                                 employeeVO.setBirthday( getBirthdayByCertificateNumber( certificateNumber ) );

                                 // 更新 EmployeeVO
                                 this.employeeDao.updateEmployee( employeeVO );
                              }

                           }
                           // 如果是新插入雇员
                           else
                           {
                              // Update Salutation CellData
                              if ( salutationCellData != null )
                              {
                                 // 新建性别CellData
                                 salutationCellData.setDbValue( getSalutationByCertificateNumber( certificateNumber ) );
                              }
                              else
                              {
                                 salutationCellData = new CellData();
                                 final ColumnVO salutationColumn = new ColumnVO();
                                 salutationColumn.setNameDB( "salutation" );
                                 salutationColumn.setIsForeignKey( "2" );
                                 salutationColumn.setAccountId( "1" );
                                 salutationColumn.setIsDBColumn( "1" );
                                 salutationCellData.setColumnVO( salutationColumn );
                                 salutationCellData.setJdbcDataType( JDBCDataType.INT );
                                 salutationCellData.setDbValue( getSalutationByCertificateNumber( certificateNumber ) );
                                 cellDataDTO.getCellDatas().add( salutationCellData );
                              }

                              // Update Birthday CellData
                              if ( birthdayCellData != null )
                              {
                                 birthdayCellData.setDbValue( getBirthdayByCertificateNumber( certificateNumber ) );
                              }
                              // 新建生日CellData
                              else
                              {
                                 birthdayCellData = new CellData();
                                 final ColumnVO birthdayColumn = new ColumnVO();
                                 birthdayColumn.setNameDB( "birthday" );
                                 birthdayColumn.setIsForeignKey( "2" );
                                 birthdayColumn.setAccountId( "1" );
                                 birthdayColumn.setIsDBColumn( "1" );
                                 birthdayCellData.setColumnVO( birthdayColumn );
                                 birthdayCellData.setJdbcDataType( JDBCDataType.DATE );
                                 birthdayCellData.setDbValue( getBirthdayByCertificateNumber( certificateNumber ) );
                                 cellDataDTO.getCellDatas().add( birthdayCellData );
                              }

                              try
                              {
                                 flag = fetchOwnerAndBranchInfo( cellDataDTO, request, null, null );
                              }
                              catch ( Exception e )
                              {
                                 e.printStackTrace();
                              }

                           }
                        }
                     }
                     // 如果是非身份證
                     else if ( !"1".equals( certificateTypeValue ) && !"0".equals( certificateTypeValue ) )
                     {
                        final String certificateNumber = certificateNumberCellData.getValue();
                        // 查找身份证对应的雇员
                        final EmployeeVO tempEmployeeVO = new EmployeeVO();
                        tempEmployeeVO.setAccountId( BaseAction.getAccountId( request, null ) );
                        tempEmployeeVO.setCorpId( BaseAction.getCorpId( request, null ) );
                        tempEmployeeVO.setCertificateType( certificateTypeValue );
                        tempEmployeeVO.setCertificateNumber( certificateNumber );

                        final List< Object > employeeVOs = this.employeeDao.getEmployeeVOsByCondition( tempEmployeeVO );

                        // 存在对应证件类型及号码的员工
                        if ( employeeVOs != null && employeeVOs.size() > 0 )
                        {
                           // 不需要insert
                           cellDataDTO.setNeedInsert( false );

                           // 如果EmployeeId不为空不操作、提示不能新建
                           if ( employeeIdCellData != null && KANUtil.filterEmpty( employeeIdCellData.getValue() ) != null )
                           {
                              flag = false;
                              employeeIdCellData.setErrorMessange( "第 " + ( employeeIdCellData.getRow() + 1 ) + " 行 " + certificateTypeCellData.getValue() + "号码["
                                    + certificateNumberCellData.getValue() + "]已存在对应员工，如需修改对应员工的数据，请移除单元格[" + employeeIdCellData.getCellRef() + "]中 "
                                    + employeeIdCellData.getColumnVO().getNameZH() + "的值。" );
                              continue;
                           }

                           final EmployeeVO employeeVO = ( EmployeeVO ) employeeVOs.get( 0 );

                           // 更新 cellDataDTO 的 CellData 集合
                           if ( !updataCellDatas( employeeVO, cellDataDTO, request ) )
                           {
                              flag = false;
                           }

                           // 更新 EmployeeVO
                           this.employeeDao.updateEmployee( employeeVO );
                        }

                     }

                  }
               }
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return flag;
   }

   // 获得所属人和所属人所在部门
   private boolean fetchOwnerAndBranchInfo( final CellDataDTO cellDataDTO, final HttpServletRequest request, final String branchId, final String owner ) throws KANException
   {
      boolean flag = true;

      // 初始化是否更新对接人、对接部门
      boolean updataFlag = true;

      // 初始化对接部门，对接人信息
      String branchId_import = null;
      String owner_import = null;

      final CellData branchCellData = cellDataDTO.getCellDataByColumnNameDB( "branch" );
      final CellData ownerCellData = cellDataDTO.getCellDataByColumnNameDB( "owner" );

      if ( branchCellData != null )
      {
         branchId_import = KANUtil.filterEmpty( branchCellData.getDbValue(), "0" );
      }
      if ( ownerCellData != null )
      {
         owner_import = KANUtil.filterEmpty( ownerCellData.getDbValue(), "0" );
      }

      // 验证部门是否有效
      if ( branchCellData != null && KANUtil.filterEmpty( branchCellData.getValue() ) != null && branchId_import == null )
      {
         flag = false;
         branchCellData.setErrorMessange( "单元格[ " + branchCellData.getCellRef() + "]'" + branchCellData.getValue() + "'对应的" + branchCellData.getColumnVO().getNameZH() + "无效，请核实。" );
      }

      // 对接人已配置
      if ( ownerCellData != null )
      {
         // 对接人已配置且数据不为空
         if ( owner_import != null )
         {
            final PositionVO positionVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( owner_import );

            if ( positionVO != null )
            {

               if ( branchCellData != null )
               {
                  final String branchId_position = KANUtil.filterEmpty( positionVO.getBranchId() );

                  // 验证对接人职位与部门是否匹配
                  if ( branchId_position != null )
                  {

                     if ( !branchId_position.equals( branchCellData.getDbValue() ) )
                     {
                        flag = false;

                        branchCellData.setErrorMessange( "第 " + ( ownerCellData.getRow() + 1 ) + " 行[" + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'对应的部门"
                              + branchCellData.getValue() + "[" + branchCellData.getCellRef() + "]'" + branchCellData.getValue() + "'不匹配，请核实。" );
                     }

                  }
                  else
                  {
                     branchCellData.setDbValue( null );
                  }
               }
               else
               {
                  final CellData branchCellData_new = new CellData();
                  final ColumnVO branchColumn = new ColumnVO();
                  branchColumn.setNameDB( "branch" );
                  branchColumn.setIsForeignKey( "2" );
                  branchColumn.setAccountId( "1" );
                  branchColumn.setIsDBColumn( "1" );
                  branchCellData_new.setColumnVO( branchColumn );
                  branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
                  branchCellData_new.setDbValue( positionVO.getBranchId() );
                  cellDataDTO.getCellDatas().add( branchCellData_new );
               }

            }
            // 对接人输入值无效
            else
            {
               ownerCellData.setErrorMessange( "单元格[ " + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'在系统中不存在有效职位，请核实。" );
               flag = false;
            }
         }
         else if ( KANUtil.filterEmpty( ownerCellData.getValue() ) != null )
         {
            flag = false;
            ownerCellData.setErrorMessange( "单元格[ " + ownerCellData.getCellRef() + "]'" + ownerCellData.getValue() + "'对应的" + ownerCellData.getColumnVO().getNameZH()
                  + "无效或者导入的对接部门不匹配，请核实。" );
         }
         // 已配置对接人且数据为空
         else
         {
            updataFlag = false;

            ownerCellData.setValue( BaseAction.getPositionId( request, null ) );
            ownerCellData.setDbValue( BaseAction.getPositionId( request, null ) );

            // 添加当前操作人所在部门为对接部门
            final PositionVO positionVO_temp = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );
            if ( branchCellData == null )
            {
               final CellData branchCellData_new = new CellData();
               final ColumnVO branchColumn = new ColumnVO();
               branchColumn.setNameDB( "branch" );
               branchColumn.setIsForeignKey( "2" );
               branchColumn.setAccountId( "1" );
               branchColumn.setIsDBColumn( "1" );
               branchCellData_new.setColumnVO( branchColumn );
               branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
               branchCellData_new.setDbValue( positionVO_temp.getBranchId() );
               cellDataDTO.getCellDatas().add( branchCellData_new );
            }
            else
            {
               branchCellData.setDbValue( positionVO_temp.getBranchId() );
            }
         }
      }
      else
      {
         updataFlag = false;

         // 添加当前操作人为对接人
         final CellData ownerData_new = new CellData();
         final ColumnVO ownerColumn = new ColumnVO();
         ownerColumn.setNameDB( "owner" );
         ownerColumn.setIsForeignKey( "2" );
         ownerColumn.setAccountId( "1" );
         ownerColumn.setIsDBColumn( "1" );
         ownerData_new.setColumnVO( ownerColumn );
         ownerData_new.setJdbcDataType( JDBCDataType.VARCHAR );
         ownerData_new.setDbValue( BaseAction.getPositionId( request, null ) );
         cellDataDTO.getCellDatas().add( ownerData_new );

         // 添加当前操作人所在部门为对接部门
         final PositionVO positionVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );
         if ( branchCellData == null )
         {
            final CellData branchCellData_new = new CellData();
            final ColumnVO branchColumn = new ColumnVO();
            branchColumn.setNameDB( "branch" );
            branchColumn.setIsForeignKey( "2" );
            branchColumn.setAccountId( "1" );
            branchColumn.setIsDBColumn( "1" );
            branchCellData_new.setColumnVO( branchColumn );
            branchCellData_new.setJdbcDataType( JDBCDataType.VARCHAR );
            branchCellData_new.setDbValue( positionVO.getBranchId() );
            cellDataDTO.getCellDatas().add( branchCellData_new );
         }
         else
         {
            branchCellData.setDbValue( positionVO.getBranchId() );
         }

      }

      // 数据库已有对接人（且导入表未配置对接人或者对接人为空）对接人不修改
      if ( KANUtil.filterEmpty( owner ) != null && !updataFlag )
      {
         cellDataDTO.getCellDataByColumnNameDB( "owner" ).setDbValue( owner );
         cellDataDTO.getCellDataByColumnNameDB( "branch" ).setDbValue( branchId );
      }

      return flag;
   }

   private boolean updataCellDatas( EmployeeVO employeeVO, CellDataDTO cellDataDTO, HttpServletRequest request )
   {
      boolean flag = true;
      /**
       * 对接部门、对接人处理
       */
      try
      {
         flag = fetchOwnerAndBranchInfo( cellDataDTO, request, employeeVO.getBranch(), employeeVO.getOwner() );
      }
      catch ( KANException e1 )
      {
         e1.printStackTrace();
      }

      // 初始化Map对象，用于装载自定义Column Value
      Map< String, String > defineColumnValues = new HashMap< String, String >();

      // 如果表内数据不为空则更新 EmployeeVO 对应值
      for ( CellData cellData : cellDataDTO.getCellDatas() )
      {

         if ( cellData.getDbValue() != null )
         {
            if ( cellData != null && cellData.getColumnVO() != null && "1".equals( cellData.getColumnVO().getIsDBColumn() )
                  && !"1".equals( cellData.getColumnVO().getIsPrimaryKey() ) )

            {
               String dbValue = null;

               if ( cellData.getJdbcDataType() != null && cellData.getJdbcDataType().equals( JDBCDataType.DATE ) )
               {
                  cellData.setDbValue( KANUtil.formatDate( cellData.getDbValue(), "yyyy-MM-dd HH:mm:ss" ) );
                  dbValue = KANUtil.formatDate( cellData.getDbValue(), "yyyy-MM-dd HH:mm:ss" );
               }
               else if ( cellData.getJdbcDataType() != null && cellData.getJdbcDataType().equals( JDBCDataType.NUMBER ) )
               {
                  cellData.setDbValue( cellData.getDbValue() );
                  dbValue = cellData.getDbValue().toString();
               }
               else
               {
                  dbValue = cellData.getDbValue().toString();
               }

               // 非自定义字段
               if ( "1".equals( cellData.getColumnVO().getAccountId() ) )
               {
                  try
                  {
                     BeanUtils.copyProperty( employeeVO, cellData.getColumnVO().getNameDB(), cellData.getDbValue() );
                  }
                  catch ( IllegalAccessException e )
                  {
                     e.printStackTrace();
                  }
                  catch ( InvocationTargetException e )
                  {
                     e.printStackTrace();
                  }
               }
               // 自定义字段处理
               else
               {
                  defineColumnValues.put( cellData.getColumnVO().getNameDB(), dbValue );
               }

            }
         }
      }

      // 查询原有 remark1
      final String remark1 = employeeVO.getRemark1();
      Map< String, String > defineColumnValues_inDB = new HashMap< String, String >();

      if ( KANUtil.filterEmpty( remark1 ) != null )
      {
         defineColumnValues_inDB = JSONObject.fromObject( remark1 );
         // 处理自定义字段信息
         defineColumnValues = handleDefineColumnValues( defineColumnValues_inDB, defineColumnValues );
      }

      final JSONObject jsonObject = new JSONObject();
      jsonObject.putAll( defineColumnValues );
      employeeVO.setRemark1( jsonObject.toString() );

      return flag;
   }

   /**  
    * handleDefineColumnValues
    * 处理自定义导入字段
    * @param defineColumnValues_inDB
    * @param defineColumnValues_inExcel
    * Add By：Jack  
    * Add Date：2015-2-27  
    */
   private static Map< String, String > handleDefineColumnValues( final Map< String, String > defineColumnValues_inDB, final Map< String, String > defineColumnValues_inExcel )
   {
      // 初始化返回自定义字段
      Map< String, String > defineColumnValues_return = new HashMap< String, String >();

      if ( defineColumnValues_inDB != null && defineColumnValues_inDB.size() > 0 )
      {
         defineColumnValues_return.putAll( defineColumnValues_inDB );
      }

      defineColumnValues_return.putAll( defineColumnValues_inExcel );

      return defineColumnValues_return;
   }

   private String getBirthdayByCertificateNumber( String certificateNumber )
   {
      if ( certificateNumber != null )
      {

         // 15位转18位
         if ( certificateNumber.length() == 15 )
         {
            certificateNumber = IDCard.conver15CardTo18( certificateNumber ).trim();
         }

         final String birth = IDCard.getBirthByIdCard( certificateNumber );

         return birth.substring( 0, 4 ) + "-" + birth.substring( 4, 6 ) + "-" + birth.substring( 6, 8 );
      }

      return null;
   }

   private String getSalutationByCertificateNumber( final String certificateNumber )
   {
      // 15位身份证
      if ( certificateNumber.length() == 15 )
      {
         if ( ( Integer.parseInt( String.valueOf( certificateNumber.charAt( 14 ) ) ) ) % 2 == 0 )
         {
            return "2";
         }
         else
         {
            return "1";
         }
      }

      // 18位身份证
      if ( certificateNumber.length() == 18 )
      {
         if ( ( Integer.parseInt( String.valueOf( certificateNumber.charAt( 16 ) ) ) ) % 2 == 0 )
         {
            return "2";
         }
         else
         {
            return "1";
         }
      }

      return null;
   }

   @Override
   // Employee导入后，更新或者新增StaffVO
   public boolean excueEndInsert( List< CellDataDTO > importDatas, String batchId ) throws KANException
   {
      if ( !KANConstants.ROLE_IN_HOUSE.equals( role ) )
      {
         return true;
      }

      boolean flag = true;

      if ( importDatas != null && importDatas.size() > 0 )
      {
         // 保存受影响的StaffId
         List< String > staffIds = new ArrayList< String >();

         List< EmployeeVO > employeeVOs = transform( importDatas );

         String accountId = null;
         try
         {

            // 开启事务
            startTransaction();

            for ( EmployeeVO employeeVO : employeeVOs )
            {
               // 如果非InHouse，直接终止循环
               if ( KANUtil.filterEmpty( employeeVO.getCorpId() ) == null )
                  break;

               if ( KANUtil.filterEmpty( employeeVO.getEmployeeId() ) != null )
               {
                  // 尝试获取StaffVO
                  StaffVO staffVO = this.getStaffDao().getStaffVOByEmployeeId( employeeVO.getEmployeeId() );

                  // 为空则新增StaffVO
                  if ( staffVO == null || staffVO.getStaffId() == null )
                  {
                     final StaffVO staffVO_new = new StaffVO();
                     staffVO_new.setEmployeeId( employeeVO.getEmployeeId() );
                     staffVO_new.setAccountId( employeeVO.getAccountId() );
                     staffVO_new.setClientId( employeeVO.getClientId() );
                     staffVO_new.setCorpId( employeeVO.getCorpId() );
                     staffVO_new.setNameZH( employeeVO.getNameZH() );
                     staffVO_new.setNameEN( employeeVO.getNameEN() );
                     staffVO_new.setStaffNo( employeeVO.getEmployeeNo() );
                     staffVO_new.setSalutation( employeeVO.getSalutation() );
                     staffVO_new.setBirthday( employeeVO.getBirthday() );
                     staffVO_new.setMaritalStatus( employeeVO.getStatus() );
                     staffVO_new.setRegistrationHometown( employeeVO.getResidencyCityId() );
                     staffVO_new.setRegistrationAddress( employeeVO.getRecordAddress() );
                     staffVO_new.setPersonalAddress( employeeVO.getPersonalAddress() );
                     staffVO_new.setPersonalPostcode( employeeVO.getPersonalPostcode() );
                     staffVO_new.setCertificateType( employeeVO.getCertificateType() );
                     staffVO_new.setCertificateNumber( employeeVO.getCertificateNumber() );
                     staffVO_new.setDescription( employeeVO.getDescription() );
                     staffVO_new.setBizEmail( employeeVO.getEmail1() );
                     staffVO_new.setBizMobile( employeeVO.getMobile1() );
                     staffVO_new.setPersonalEmail( employeeVO.getEmail2() );
                     staffVO_new.setPersonalMobile( employeeVO.getMobile2() );
                     staffVO_new.setStatus( StaffVO.TRUE );
                     staffVO_new.setRemark1( employeeVO.getRemark1() );
                     staffVO_new.setRemark2( employeeVO.getRemark2() );
                     staffVO_new.setRemark3( employeeVO.getRemark3() );
                     staffVO_new.setRemark4( employeeVO.getRemark4() );
                     staffVO_new.setCreateBy( employeeVO.getCreateBy() );
                     staffVO_new.setPositionIdArray( employeeVO.getPositionIdArray() );
                     staffVO_new.setCreateDate( new Date() );

                     this.getStaffDao().insertStaff( staffVO_new );

                     staffVO = staffVO_new;
                  }
                  else
                  {
                     staffVO.setNameZH( employeeVO.getNameZH() );
                     staffVO.setNameEN( employeeVO.getNameEN() );
                     staffVO.setStaffNo( employeeVO.getEmployeeNo() );
                     staffVO.setSalutation( employeeVO.getSalutation() );
                     staffVO.setBirthday( employeeVO.getBirthday() );
                     staffVO.setMaritalStatus( employeeVO.getStatus() );
                     staffVO.setRegistrationHometown( employeeVO.getResidencyCityId() );
                     staffVO.setRegistrationAddress( employeeVO.getRecordAddress() );
                     staffVO.setPersonalAddress( employeeVO.getPersonalAddress() );
                     staffVO.setPersonalPostcode( employeeVO.getPersonalPostcode() );
                     staffVO.setCertificateType( employeeVO.getCertificateType() );
                     staffVO.setCertificateNumber( employeeVO.getCertificateNumber() );
                     staffVO.setDescription( employeeVO.getDescription() );
                     staffVO.setBizEmail( employeeVO.getEmail1() );
                     staffVO.setBizMobile( employeeVO.getMobile1() );
                     staffVO.setPersonalEmail( employeeVO.getEmail2() );
                     staffVO.setPersonalMobile( employeeVO.getMobile2() );
                     staffVO.setStatus( StaffVO.TRUE );
                     staffVO.setRemark1( employeeVO.getRemark1() );
                     staffVO.setRemark2( employeeVO.getRemark2() );
                     staffVO.setRemark3( employeeVO.getRemark3() );
                     staffVO.setRemark4( employeeVO.getRemark4() );
                     staffVO.setPositionIdArray( employeeVO.getPositionIdArray() );
                     staffVO.setModifyBy( employeeVO.getModifyBy() );
                     staffVO.setModifyDate( new Date() );

                     this.getStaffDao().updateStaff( staffVO );
                  }

                  // 保存staff和和position直接的关系
                  insertPositionStaffRelation( staffVO, employeeVO, importDatas );

                  // 如果Username不为空，则新增User对象
                  if ( KANUtil.filterEmpty( employeeVO.get_tempUsername() ) != null )
                  {
                     // 获取UserVO
                     UserVO userVO = this.getUserDao().getUserVOByStaffId( staffVO.getStaffId() );

                     boolean exists = false;
                     if ( userVO == null )
                     {
                        userVO = new UserVO();
                        userVO.setAccountId( staffVO.getAccountId() );
                        userVO.setStaffId( staffVO.getStaffId() );
                        userVO.setUsername( employeeVO.get_tempUsername() );
                        String pwd = PassWordUtil.randomPassWord();
                        userVO.setPassword( Cryptogram.encodeString( pwd ) );
                        userVO.setCorpId( employeeVO.getCorpId() );
                        userVO.setStatus( "1" );
                        SecurityAction.encryptPassword( userVO, pwd );

                        // Role不插入数据库
                        userVO.setRole( employeeVO.getRole() );

                        // 如果当前用户名不存在，则添加用户记录
                        if ( userDao.login_inHouse( userVO ) == null )
                        {
                           // 插入用户
                           this.getUserDao().insertUser( userVO );

                           // 发送邮件
                           if ( staffVO.getBizEmail() != null && !staffVO.getBizEmail().equals( "" ) )
                           {
                              userVO.setPassword( pwd );
                              new Mail( staffVO.getAccountId(), staffVO.getBizEmail(), ContentUtil.getMailContent_UserCreate( new Object[] { userVO, staffVO } ) ).send( true );
                           }
                        }
                        else
                        {
                           importDatas.get( 0 ).setExtraMessage( "用户名 " + employeeVO.get_tempUsername() + " 已经存在，请重新选择；" );
                           exists = true;
                           employeeVO.set_tempUsername( null );
                        }
                     }
                     else
                     {
                        importDatas.get( 0 ).setExtraMessage( "员工 " + employeeVO.getNameZH() + " 已经存在账户，请勿重复添加；" );
                        exists = true;
                        employeeVO.set_tempUsername( null );
                     }

                     if ( exists )
                        this.getEmployeeDao().updateEmployee( employeeVO );
                  }

                  accountId = staffVO.getAccountId();
                  // 保存staffId
                  staffIds.add( staffVO.getStaffId() );
               }
            }

            commitTransaction();

            if ( staffIds.size() > 0 )
            {
               try
               {
                  for ( String staffId : staffIds )
                  {
                     BaseAction.constantsInit( "initStaff", new String[] { accountId, staffId } );
                     BaseAction.constantsInit( "initStaffBaseView", new String[] { accountId, staffId } );
                  }
                  BaseAction.constantsInit( "initPosition", accountId );
               }
               catch ( MalformedURLException e )
               {
                  e.printStackTrace();
               }
               catch ( RemoteException e )
               {
                  e.printStackTrace();
               }
               catch ( NotBoundException e )
               {
                  e.printStackTrace();
               }
            }
         }
         catch ( KANException e )
         {
            rollbackTransaction();
            logger.error( "employeeImportExcel.excueEndInsert rollbackTransaction  message=" + e.toString() );
         }

         if ( employeeVOs != null && employeeVOs.size() > 0 )
         {
            for ( EmployeeVO employeeVO : employeeVOs )
            {
               BaseAction.syncWXContacts( employeeVO.getEmployeeId() );
            }
         }
      }

      return flag;
   }

   /***
    * 将数据库对象转换成 EmployeeVO
   *  transform
   *  @param importDatas
   *  @return EmployeeVOs
    * @throws KANException 
    */
   private List< EmployeeVO > transform( final List< CellDataDTO > importDatas ) throws KANException
   {
      final List< EmployeeVO > employeeVOs = new ArrayList< EmployeeVO >();

      if ( importDatas != null && importDatas.size() > 0 )
      {
         for ( CellDataDTO importData : importDatas )
         {
            if ( importData != null && importData.getCellDatas() != null )
            {
               final CellData pkCellData = importData.getCellDataByColumnNameDB( "pkCache" );
               if ( pkCellData != null && KANUtil.filterEmpty( pkCellData.getDbValue() ) != null )
               {
                  employeeVOs.add( ( ( EmployeeVO ) this.getEmployeeDao().getEmployeeVOByEmployeeId( pkCellData.getDbValue().toString() ) ) );
               }
            }
         }
      }

      return employeeVOs;
   }

   // 建立Position与Staff之间的关联
   public int insertPositionStaffRelation( final StaffVO staffVO, final EmployeeVO employeeVO, List< CellDataDTO > importDatas ) throws KANException
   {
      // 需要新增职位
      if ( staffVO != null && employeeVO != null && KANUtil.filterEmpty( employeeVO.getRemark4() ) != null )
      {
         PositionVO positionVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getPositionVOByPositionId( employeeVO.getRemark4() );
         if ( positionVO != null )
         {
            // 初始化查询对象
            final PositionVO search = new PositionVO();
            search.setPositionId( positionVO.getPositionId() );
            // 先判断当前职位对应的员工编制数量是否已达上线
            final int positionBalances = this.positionDao.getPositionVOBalancesByPositionVO( search );
            // 超过或者等于最大编制数不能继续添加
            if ( positionBalances <= 0 )
            {
               importDatas.get( 0 ).setExtraMessage( "职位（" + positionVO.getTitleZH() + "）对应的最大编制数已满，不能再添加对应员工（" + employeeVO.getNameZH() + "）。" );
            }
            else
            {
               // 解除之前的职位关系
               this.getPositionStaffRelationDao().deletePositionStaffRelationByStaffId( staffVO.getStaffId() );

               final PositionStaffRelationVO positionStaffRelationVO = new PositionStaffRelationVO();
               positionStaffRelationVO.setStaffId( staffVO.getStaffId() );
               positionStaffRelationVO.setPositionId( positionVO.getPositionId() );
               positionStaffRelationVO.setCreateBy( employeeVO.getModifyBy() );
               positionStaffRelationVO.setModifyBy( employeeVO.getModifyBy() );
               this.positionStaffRelationDao.insertPositionStaffRelation( positionStaffRelationVO );

               employeeVO.set_tempPositionIds( positionVO.getPositionId() );

               BranchVO branchVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getBranchVOByBranchId( positionVO.getBranchId() );
               if ( branchVO != null )
               {

                  employeeVO.set_tempBranchIds( positionVO.getBranchId() );
                  BranchVO parentBranchVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getBranchVOByBranchId( branchVO.getParentBranchId() );
                  if ( parentBranchVO != null )
                  {
                     employeeVO.set_tempParentBranchIds( parentBranchVO.getBranchId() );
                  }
               }

               PositionVO parentPositionVO = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getPositionVOByPositionId( positionVO.getPositionId() );
               if ( parentPositionVO != null )
               {
                  List< StaffDTO > staffDTOs = KANConstants.getKANAccountConstants( employeeVO.getAccountId() ).getStaffDTOsByPositionId( parentPositionVO.getPositionId() );
                  if ( staffDTOs != null && staffDTOs.size() > 0 )
                  {
                     final StringBuilder rs = new StringBuilder();
                     for ( StaffDTO dto : staffDTOs )
                     {
                        if ( KANUtil.filterEmpty( rs.toString() ) == null )
                        {
                           rs.append( dto.getStaffVO() == null ? "" : dto.getStaffVO().getStaffId() );
                        }
                        else
                        {
                           rs.append( ( dto.getStaffVO() == null ? "" : ( "," + dto.getStaffVO().getStaffId() ) ) );
                        }
                     }

                     employeeVO.set_tempParentPositionOwners( rs.toString() );
                  }

                  employeeVO.set_tempParentPositionIds( parentPositionVO.getPositionId() );
                  employeeVO.set_tempParentPositionBranchIds( parentPositionVO.getBranchId() );
               }

               employeeVO.set_tempPositionLocationIds( positionVO.getLocationId() );
               employeeVO.set_tempPositionGradeIds( positionVO.getPositionGradeId() );

               this.getEmployeeDao().updateEmployee( employeeVO );
            }
         }
      }

      return 0;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }

   public UserDao getUserDao()
   {
      return userDao;
   }

   public void setUserDao( UserDao userDao )
   {
      this.userDao = userDao;
   }

   public PositionStaffRelationDao getPositionStaffRelationDao()
   {
      return positionStaffRelationDao;
   }

   public void setPositionStaffRelationDao( PositionStaffRelationDao positionStaffRelationDao )
   {
      this.positionStaffRelationDao = positionStaffRelationDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // No Use
      return false;
   }

}
