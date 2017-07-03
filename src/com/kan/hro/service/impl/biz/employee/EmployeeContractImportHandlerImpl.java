package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.kan.base.dao.inf.management.PositionDao;
import com.kan.base.dao.inf.security.StaffDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

public class EmployeeContractImportHandlerImpl implements ExcelImportHandler< List< CellDataDTO > >
{
   // 初始化EmployeeDao
   private EmployeeDao employeeDao;

   // 初始化ClientOrderHeaderDao
   private ClientOrderHeaderDao clientOrderHeaderDao;

   // 初始化EmployeeContractTempDao
   private EmployeeContractTempDao employeeContractTempDao;

   private EmployeeContractDao employeeContractDao;

   private PositionDao positionDao;

   private StaffDao staffDao;

   // 初始化AccountId
   private String ACCOUNT_ID;

   // 初始化CorpId
   private String CORP_ID;

   private EmployeeContractService employeeContractService;

   @Override
   // Reviewed by Kevin Jin at 2014-05-07
   public void init( List< CellDataDTO > cellDataDTOs )
   {
      // 将导入数据表改为临时表
      if ( cellDataDTOs != null )
      {
         for ( CellDataDTO cellDataDTO : cellDataDTOs )
         {
            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );

            if ( cellDataDTO.getSubCellDataDTOs() == null )
            {
               continue;
            }

            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
            }
         }
      }
   }

   @Override
   // Reviewed by Kevin Jin at 2014-05-07
   public boolean excuteBeforInsert( final List< CellDataDTO > cellDataDTOs, final HttpServletRequest request )
   {
      boolean flag = true;

      if ( cellDataDTOs != null && cellDataDTOs.size() > 0 )
      {
         // 主从表tableName
         String sbTableName = "hro_biz_employee_contract_sb_temp";
         String salaryTableName = "hro_biz_employee_contract_salary_temp";
         String cbTableName = "hro_biz_employee_contract_cb_temp";
         String leaveTableName = "hro_biz_employee_contract_leave_temp";
         String otTableName = "hro_biz_employee_contract_ot_temp";
         String otherTableName = "hro_biz_employee_contract_other_temp";

         try
         {
            // 初始化AccountId
            ACCOUNT_ID = BaseAction.getAccountId( request, null );
            // 初始化CorpId
            CORP_ID = BaseAction.getCorpId( request, null );
         }
         catch ( final KANException e )
         {
            e.printStackTrace();
         }

         // 初始化KANAccountConstants
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( ACCOUNT_ID );
         // 初始化ItemVO列表
         final List< ItemVO > itemVOs = accountConstants.getItemVOsByCorpId( CORP_ID );

         for ( CellDataDTO cellDataDTO : cellDataDTOs )
         {
            // 初始化是否包含 ContractId
            boolean contractIdExist = false;

            final CellData contractIdCellData = cellDataDTO.getCellDataByColumnNameDB( "contractId" );

            if ( contractIdCellData != null && contractIdCellData.getDbValue() != null )
            {
               contractIdExist = true;

               // 验证contractId 有效性
               try
               {
                  final EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( ( String ) contractIdCellData.getDbValue() );

                  if ( employeeContractVO == null )
                  {
                     flag = false;
                     contractIdCellData.setErrorMessange( "单元格[" + contractIdCellData.getCellRef() + "]" + contractIdCellData.getColumnVO().getNameZH() + "的值["
                           + contractIdCellData.getDbValue() + "]无效！" );
                  }
                  else
                  {
                     // 处理对接人和对接部门
                     if ( !fetchOwnerAndBranchInfo( cellDataDTO, request, employeeContractVO.getBranch(), employeeContractVO.getOwner() ) )
                     {
                        flag = false;
                     }
                  }
               }
               catch ( KANException e )
               {
                  e.printStackTrace();
               }

            }
            else
            {
               // 处理对接人和对接部门
               try
               {
                  if ( !fetchOwnerAndBranchInfo( cellDataDTO, request, null, null ) )
                  {
                     flag = false;
                  }
               }
               catch ( KANException e )
               {
                  e.printStackTrace();
               }
            }

            // 判断
            try
            {
               if ( flag )
               {
                  flag = arrangeCellData( cellDataDTO, request );
               }
               else
               {
                  arrangeCellData( cellDataDTO, request );
               }

               // 如果存在合同编号不用验证时间段内存在其他合同
               if ( !contractIdExist && flag )
               {
                  // 检测时间段内是否有其他有效合同
                  EmployeeContractVO employeeContractVO = new EmployeeContractVO();

                  employeeContractVO.setAccountId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "accountId" ) );
                  employeeContractVO.setCorpId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "corpId" ) );
                  employeeContractVO.setClientId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "clientId" ) );
                  employeeContractVO.setEmployeeId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "employeeId" ) );
                  employeeContractVO.setEntityId( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "entityId" ) );
                  employeeContractVO.setFlag( ( String ) cellDataDTO.getCellDataDbValueByColumnNameDB( "flag" ) );
                  employeeContractVO.setStartDate( KANUtil.formatDate( cellDataDTO.getCellDataDbValueByColumnNameDB( "startDate" ), "yyyy-MM-dd" ) );
                  employeeContractVO.setEndDate( KANUtil.formatDate( cellDataDTO.getCellDataDbValueByColumnNameDB( "endDate" ), "yyyy-MM-dd" ) );

                  if ( employeeContractService.checkContractConflict( employeeContractVO ) )
                  {
                     flag = false;
                     if ( ( KANUtil.filterEmpty( employeeContractVO.getFlag() ) != null && KANUtil.filterEmpty( employeeContractVO.getFlag() ).equals( "1" ) )
                           || ( KANUtil.filterEmpty( BaseAction.getRole( request, null ) ) != null && KANUtil.filterEmpty( BaseAction.getRole( request, null ) ).equals( KANConstants.ROLE_IN_HOUSE ) ) )
                     {
                        cellDataDTO.getCellDatas().get( 0 ).setErrorMessange( "第" + ( cellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "行合同时间段内已存在劳动合同" );
                     }
                     else
                     {
                        cellDataDTO.getCellDatas().get( 0 ).setErrorMessange( "第" + ( cellDataDTO.getCellDatas().get( 0 ).getRow() + 1 ) + "行合同时间段内已存在派送信息" );
                     }
                  }

               }
            }
            catch ( final KANException e )
            {
               e.printStackTrace();
            }

            // 初始化开始时间，结束时间
            final String startDate = getStartDate( cellDataDTO );
            final String endDate = getEndDate( cellDataDTO );
            final String orderId = getOrderId( cellDataDTO );

            //            // 验证导入合同的开始时间是否在结束时间之前
            //            if ( KANUtil.getDays( KANUtil.createDate( employeeContractVO.getStartDate() ) ) >= KANUtil.getDays( KANUtil.createDate( tempContractEndDate ) )
            //                  || KANUtil.getDays( KANUtil.createDate( employeeContractVO.getEndDate() ) ) <= KANUtil.getDays( KANUtil.createDate( tempContractStartDate ) ) )
            //            {
            //
            //            }

            if ( flag && cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
            {
               final ColumnVO foreignKeyCellColumnVO = new ColumnVO();
               foreignKeyCellColumnVO.setNameDB( "contractId" );
               foreignKeyCellColumnVO.setIsForeignKey( "1" );
               foreignKeyCellColumnVO.setAccountId( "1" );
               foreignKeyCellColumnVO.setIsDBColumn( "1" );

               final CellData foreignKeyCellData = new CellData();
               foreignKeyCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               foreignKeyCellData.setColumnVO( foreignKeyCellColumnVO );

               final Iterator< CellDataDTO > iterators = cellDataDTO.getSubCellDataDTOs().iterator();

               List< CellDataDTO > removeCellDataDTOs = new ArrayList< CellDataDTO >();
               while ( iterators.hasNext() )
               {
                  CellDataDTO subCellDataDTO = iterators.next();
                  if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ) != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
                  {
                     // 社保方案从表
                     if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( sbTableName ) )
                     {
                        boolean havesb = fetchSbCellDataDTO( subCellDataDTO, cellDataDTO.getSubCellDataDTOs() );

                        // 避免添加字段重复
                        if ( havesb && subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }

                        if ( !havesb && KANUtil.filterEmpty( subCellDataDTO.getErrorMessange() ) != null )
                        {
                           flag = false;
                        }

                     }
                     // 薪酬方案从表
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( salaryTableName ) )
                     {
                        CellData baseCellData = subCellDataDTO.getCellDataByColumnNameDB( "base" );
                        if ( baseCellData != null && ( baseCellData.getValue() == null || "".equals( baseCellData.getValue() ) ) )
                        {
                           removeCellDataDTOs.add( subCellDataDTO );
                        }
                        else
                        {
                           fetchSalaryCellDataDTO( subCellDataDTO, itemVOs, startDate, endDate, orderId );
                           // 避免添加字段重复
                           if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                           {
                              subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                           }
                        }
                     }
                     // 商保方案 
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( cbTableName ) )
                     {
                        fetchCbCellDataDTO( subCellDataDTO );

                        // 避免添加字段重复
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // 请假方案
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( leaveTableName ) )
                     {
                        addLeaveTableColumns( subCellDataDTO, itemVOs );

                        // 避免添加字段重复
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // 加班方案
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( otTableName ) )
                     {
                        addOtTableColumns( subCellDataDTO, itemVOs );

                        // 避免添加字段重复
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                     // 其他方案
                     else if ( KANUtil.filterEmpty( subCellDataDTO.getTableName() ).equalsIgnoreCase( otherTableName ) )
                     {
                        addOtherTableColumns( subCellDataDTO, itemVOs );

                        // 避免添加字段重复
                        if ( subCellDataDTO.getCellDataByColumnNameDB( foreignKeyCellData.getColumnVO().getNameDB() ) == null )
                        {
                           subCellDataDTO.getCellDatas().add( foreignKeyCellData );
                        }
                     }
                  }
               }

               if ( removeCellDataDTOs != null && removeCellDataDTOs.size() > 0 )
                  cellDataDTO.getSubCellDataDTOs().removeAll( removeCellDataDTOs );
            }
         }
      }

      return flag;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      try
      {
         // 根据OrderId更新劳动合同临时表中的客户Id
         this.getEmployeeContractTempDao().updateEmployeeContractClientIdFromOrderIdByBatchId( batchId );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }

      return false;
   }

   // Reviewed by Kevin Jin at 2014-05-07
   private boolean arrangeCellData( final CellDataDTO cellDataDTO, final HttpServletRequest request ) throws KANException
   {
      // 初始化CellData列表
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();
      // 初始化KANAccountConstants
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( ACCOUNT_ID );
      // 初始化PositionVO
      final PositionVO positionVO = accountConstants.getPositionVOByPositionId( BaseAction.getPositionId( request, null ) );

      final com.kan.base.domain.management.PositionVO mgtposition = new com.kan.base.domain.management.PositionVO();
      mgtposition.setAccountId( ACCOUNT_ID );
      final List< Object > positionList = positionDao.getPositionVOsByCondition( mgtposition );
      // 标识
      boolean flag = true;
      // Row
      //      int row = 0;

      if ( cellDatas != null )
      {
         // 主键对应单元格
         CellData primaryKeyCellData = null;
         CellData employeeIdCellData = null;
         String nameOfEmployeeId = "员工系统编号";
         String nameOfCertificateNumber = "身份证号";
         String employeeId = null;
         // 身份证号码
         String employeeCertificateNumber = null;

         // 雇员中文姓名
         String employeeNameZH = null;

         // 雇员英文姓名
         String employeeNameEN = null;

         // 雇员户籍性质
         String residencyType = null;

         // 雇员联系电话
         String phone1 = null;

         // 雇员户籍地址
         String residencyAddress = null;

         // 业务类型中文名
         String businessTypeNameZH = null;

         // 业务类型英文名
         String businessTypeNameEN = null;

         // 扩充Client Id
         final CellData clientIdCellData = new CellData();

         // 扩充Entity Id
         final CellData entityIdCellData = new CellData();

         // 扩充业务类型
         CellData businessTypeIdCellData = new CellData();
         // 临时业务类型
         final CellData businessTypeIdCellData_Temp = new CellData();

         for ( CellData cellData : cellDatas )
         {
            if ( cellData != null && cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null )
            {
               // 主键处理
               if ( KANUtil.filterEmpty( cellData.getColumnVO().getIsPrimaryKey() ) != null && KANUtil.filterEmpty( cellData.getColumnVO().getIsPrimaryKey() ).equals( "1" ) )
               {
                  primaryKeyCellData = cellData;
               }
               // OrderId处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "orderId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // 初始化OrderId
                     String orderId = ( String ) cellData.getValue();

                     if ( KANUtil.filterEmpty( orderId ) != null && KANUtil.filterEmpty( orderId ).matches( "[0-9]+" ) )
                     {
                        cellData.setDbValue( orderId );

                        try
                        {
                           final ClientOrderHeaderVO clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( orderId );

                           if ( clientOrderHeaderVO != null )
                           {
                              // 扩充ClientId
                              final ColumnVO clientIdColumnVO = new ColumnVO();
                              clientIdColumnVO.setNameDB( "clientId" );
                              clientIdColumnVO.setIsForeignKey( "2" );
                              clientIdColumnVO.setAccountId( "1" );
                              clientIdColumnVO.setIsDBColumn( "1" );

                              clientIdCellData.setColumnVO( clientIdColumnVO );
                              clientIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                              clientIdCellData.setDbValue( clientOrderHeaderVO.getClientId() );

                              // 扩充EntityId
                              final ColumnVO entityIdColumnVO = new ColumnVO();
                              entityIdColumnVO.setNameDB( "entityId" );
                              entityIdColumnVO.setIsForeignKey( "2" );
                              entityIdColumnVO.setAccountId( "1" );
                              entityIdColumnVO.setIsDBColumn( "1" );

                              entityIdCellData.setColumnVO( entityIdColumnVO );
                              entityIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                              entityIdCellData.setDbValue( clientOrderHeaderVO.getEntityId() );

                              // 扩充BusinessTypeId
                              final ColumnVO businessTypeIdColumnVO = new ColumnVO();
                              businessTypeIdColumnVO.setNameDB( "businessTypeId" );
                              businessTypeIdColumnVO.setIsForeignKey( "2" );
                              businessTypeIdColumnVO.setAccountId( "1" );
                              businessTypeIdColumnVO.setIsDBColumn( "1" );

                              businessTypeIdCellData_Temp.setColumnVO( businessTypeIdColumnVO );
                              businessTypeIdCellData_Temp.setJdbcDataType( JDBCDataType.VARCHAR );
                              businessTypeIdCellData_Temp.setDbValue( clientOrderHeaderVO.getBusinessTypeId() );
                           }
                           //订单号不存在
                           else
                           {
                              cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效，不存在该订单" );
                              flag = false;
                           }
                        }
                        catch ( final Exception e )
                        {
                           e.printStackTrace();
                        }
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值不能为空" );
                     flag = false;
                  }
               }
               // EmployeeId处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeId" ) )
               {
                  employeeIdCellData = cellData;
                  nameOfEmployeeId = cellData.getColumnVO().getNameZH();

                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeId = cellData.getValue().trim();
                  }
               }
               // Certificate Number处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "certificateNumber" ) )
               {
                  nameOfCertificateNumber = cellData.getColumnVO().getNameZH();

                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     if ( "身份证号码".equals( nameOfCertificateNumber ) && !com.kan.base.util.IDCard.validateCard( cellData.getValue().trim() ) )
                     {
                        flag = false;
                        cellData.setErrorMessange( "第" + String.valueOf( cellData.getRow() + 1 ) + "行[身份证号码]输入有误。" );
                     }
                     else
                     {
                        employeeCertificateNumber = cellData.getValue();
                     }
                  }
               }
               // Employee Name ZH处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeNameZH" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeNameZH = cellData.getValue();
                  }
               }
               // Employee Name EN处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "employeeNameEN" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     employeeNameEN = cellData.getValue();
                  }
               }
               // Employee residency Type 处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "residencyType" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     residencyType = cellData.getDbValue().toString();
                  }
               }
               // Employee phone1处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "phone1" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     phone1 = cellData.getValue();
                  }
               }
               // Employee residency Address处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "residencyAddress" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     residencyAddress = cellData.getValue();
                  }
               }
               // Entity Id处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "entityId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // 初始化EntityVO
                     final EntityVO entityVO = accountConstants.getEntityVOByEntityName( cellData.getValue().trim(), CORP_ID );

                     if ( entityVO != null )
                     {
                        cellData.setDbValue( entityVO.getEntityId() );
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效，不存在该法务实体" );
                        flag = false;
                     }
                  }
               }
               // Business Type Id
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "businessTypeId" ) )
               {
                  businessTypeIdCellData = cellData;
                  // 验证业务类型的有效性
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // 初始化BusinessTypeVO
                     final BusinessTypeVO businessTypeVO = accountConstants.getBusinessTypeByName( cellData.getValue().trim(), CORP_ID );

                     if ( businessTypeVO != null )
                     {
                        cellData.setDbValue( businessTypeVO.getBusinessTypeId() );
                        businessTypeNameZH = businessTypeVO.getNameZH();
                        businessTypeNameEN = businessTypeVO.getNameEN();
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效，不存在该业务类型" );
                        flag = false;
                     }
                  }
               }
               // Template Id
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "templateId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // 初始化LaborContractTemplateVO
                     final LaborContractTemplateVO laborContractTemplateVO = accountConstants.getLaborContractTemplatesByName( cellData.getValue().trim(), CORP_ID );

                     if ( laborContractTemplateVO != null )
                     {
                        cellData.setDbValue( laborContractTemplateVO.getTemplateId() );
                     }
                     else
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效，不存在该劳动合同模板" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值不能为空" );
                     flag = false;
                  }
               }
               // 成本部门
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "settlementBranch" ) )
               {
                  // 如果自己输入的部门名称
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     if ( KANUtil.filterEmpty( cellData.getDbValue(), "0" ) == null )
                     {
                        cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值[" + cellData.getValue() + "]无效，不存在对应的部门" );
                        flag = false;
                     }
                  }
                  else
                  {
                     cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值不能为空" );
                     flag = false;
                  }
               }
               // positionName
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "positionName" )
                     || KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "positionId" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) != null )
                  {
                     // 初始化LaborContractTemplateVO

                     if ( positionList != null && positionList.size() > 0 )
                     {
                        boolean haspositionId = false;
                        for ( Object obj : positionList )
                        {
                           com.kan.base.domain.management.PositionVO mgtpositionVO = ( com.kan.base.domain.management.PositionVO ) obj;
                           if ( mgtpositionVO != null && ( cellData.getValue().equals( mgtpositionVO.getTitleZH() ) || cellData.getValue().equals( mgtpositionVO.getTitleEN() ) ) )
                           {

                              // 扩充positionId
                              final ColumnVO positionIdColumnVO = cellData.getColumnVO();
                              positionIdColumnVO.setNameDB( "positionId" );
                              positionIdColumnVO.setIsForeignKey( "2" );
                              positionIdColumnVO.setAccountId( "1" );
                              positionIdColumnVO.setIsDBColumn( "1" );

                              cellData.setJdbcDataType( JDBCDataType.INT );
                              cellData.setColumnVO( positionIdColumnVO );
                              cellData.setDbValue( mgtpositionVO.getPositionId() );
                              haspositionId = true;
                              break;
                           }
                        }

                        if ( !haspositionId )
                        {
                           cellData.setErrorMessange( "[" + cellData.getCellRef() + "]的值" + cellData.getValue() + "无效，不存在该职位信息" );
                           flag = false;
                        }
                     }
                     else
                     {
                        cellData.setErrorMessange( "系统没有设定职位信息，请检查相关设定。" );
                     }
                  }
               }
               // Contract Name ZH处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "nameZH" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
                  {
                     cellData.setDbValue( ( KANUtil.filterEmpty( businessTypeNameZH ) != null ? businessTypeNameZH : "" ) + ( KANUtil.filterEmpty( CORP_ID ) != null ? "合同" : "协议" )
                           + ( KANUtil.filterEmpty( employeeNameZH ) != null ? ( "（" + employeeNameZH ) + "）" : "" ) + " - " + KANUtil.getMonthly( new Date(), "" ) );
                  }
               }
               // Contract Name EN处理
               else if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).equals( "nameEN" ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
                  {
                     cellData.setDbValue( ( KANUtil.filterEmpty( businessTypeNameEN ) != null ? businessTypeNameEN + " " : "" )
                           + ( KANUtil.filterEmpty( CORP_ID ) != null ? "Contract" : "Agreement" )
                           + ( KANUtil.filterEmpty( employeeNameEN ) != null ? ( " (" + businessTypeNameEN ) + ")" : "" ) + " - " + KANUtil.getMonthly( new Date(), "" ) );
                  }
               }
            }
         }

         // 扩充Client Id，避免添加字段重复
         if ( clientIdCellData.getColumnVO() != null && cellDataDTO.getCellDataByColumnNameDB( clientIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( clientIdCellData );
         }

         // 扩充Entity Id，避免添加字段重复
         if ( entityIdCellData.getColumnVO() != null && cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( entityIdCellData );
         }
         else if ( entityIdCellData.getColumnVO() != null
               && KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ).getDbValue() ) == null )
         {
            cellDataDTO.getCellDataByColumnNameDB( entityIdCellData.getColumnVO().getNameDB() ).setDbValue( entityIdCellData.getDbValue() );
         }

         // 扩充Business Type Id，避免添加字段重复
         if ( businessTypeIdCellData.getColumnVO() == null )
         {
            cellDatas.add( businessTypeIdCellData_Temp );
         }
         else
         {
            if ( KANUtil.filterEmpty( businessTypeIdCellData.getDbValue() ) == null && flag )
            {
               businessTypeIdCellData.setDbValue( businessTypeIdCellData_Temp.getDbValue() );
            }
         }

         if ( employeeId == null && employeeCertificateNumber == null )
         {
            cellDataDTO.setErrorMessange( "第" + ( employeeIdCellData.getRow() + 1 ) + "行[" + nameOfEmployeeId + "，" + nameOfCertificateNumber + "]至少一个不能为空。" );
            return false;
         }

         try
         {
            EmployeeVO employeeVO = null;

            if ( employeeId != null )
            {
               employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeId );

               if ( employeeVO == null && employeeCertificateNumber == null )
               {
                  cellDataDTO.setErrorMessange( "第" + ( employeeIdCellData.getRow() + 1 ) + "行[" + nameOfEmployeeId + "：" + employeeId + "]对应员工不存在，[" + nameOfCertificateNumber
                        + "]的值不能为空" );
                  return false;
               }
            }

            if ( employeeVO == null && employeeCertificateNumber != null )
            {
               // 初始化EmployeeVO搜索对象
               EmployeeVO tempEmployeeVO = new EmployeeVO();
               tempEmployeeVO.setAccountId( ACCOUNT_ID );
               tempEmployeeVO.setCorpId( CORP_ID );
               tempEmployeeVO.setCertificateNumber( employeeCertificateNumber );
               // 按身份证号码获取员工
               final List< Object > employeeVOs = this.getEmployeeDao().getEmployeeVOsByCondition( tempEmployeeVO );

               if ( employeeVOs != null && employeeVOs.size() > 0 )
               {
                  employeeVO = ( EmployeeVO ) employeeVOs.get( 0 );
               }
            }

            if ( employeeVO == null )
            {
               employeeVO = new EmployeeVO();
               employeeVO.setAccountId( ACCOUNT_ID );
               employeeVO.setCorpId( CORP_ID );
               // 身份证
               employeeVO.setCertificateType( "1" );
               employeeVO.setCertificateNumber( employeeCertificateNumber );
               employeeVO.setNameZH( employeeNameZH );
               employeeVO.setResidencyAddress( residencyAddress );
               employeeVO.setResidencyType( residencyType );
               employeeVO.setPhone1( phone1 );
               this.getEmployeeDao().insertEmployee( employeeVO );

               // 先判断是否存在一个和雇员关联的Staff
               StaffVO staffVO = new StaffVO();
               staffVO = staffDao.getStaffVOByEmployeeId( employeeVO.getEmployeeId() );
               if ( staffVO == null || staffVO.getStaffId() == null )
               {
                  // 新增一个staff
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
                  staffVO_new.setStatus( "1" );
                  staffVO_new.setRemark1( employeeVO.getRemark1() );
                  staffVO_new.setRemark2( employeeVO.getRemark2() );
                  staffVO_new.setRemark3( employeeVO.getRemark3() );
                  staffVO_new.setCreateBy( employeeVO.getCreateBy() );
                  staffVO_new.setPositionIdArray( employeeVO.getPositionIdArray() );
                  staffVO_new.setCreateDate( new Date() );
                  staffDao.insertStaff( staffVO_new );
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
                  staffVO.setStatus( "1" );
                  staffVO.setRemark1( employeeVO.getRemark1() );
                  staffVO.setRemark2( employeeVO.getRemark2() );
                  staffVO.setRemark3( employeeVO.getRemark3() );
                  staffVO.setCreateBy( employeeVO.getCreateBy() );
                  staffVO.setPositionIdArray( employeeVO.getPositionIdArray() );
                  staffVO.setCreateDate( new Date() );
                  staffDao.updateStaff( staffVO );
               }
            }

            if ( employeeIdCellData == null )
            {
               // 初始化ColumnVO
               final ColumnVO columnVO = new ColumnVO();
               columnVO.setNameDB( "employeeId" );
               columnVO.setIsForeignKey( "2" );
               columnVO.setAccountId( "1" );
               columnVO.setIsDBColumn( "1" );

               employeeIdCellData = new CellData();
               employeeIdCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               employeeIdCellData.setColumnVO( columnVO );
            }

            employeeIdCellData.setDbValue( employeeVO.getEmployeeId() );
         }
         catch ( final KANException e )
         {
            e.printStackTrace();
         }

         if ( primaryKeyCellData != null )
         {
            // 去掉主键，让其自增长
            cellDatas.remove( primaryKeyCellData );

            // remak4 存放原主键Id
            final ColumnVO remark4ColumnVO = new ColumnVO();
            remark4ColumnVO.setNameDB( "remark4" );
            remark4ColumnVO.setIsForeignKey( "2" );
            remark4ColumnVO.setAccountId( "1" );
            remark4ColumnVO.setIsDBColumn( "1" );

            final CellData remark4CellData = new CellData();
            remark4CellData.setColumnVO( remark4ColumnVO );
            remark4CellData.setJdbcDataType( JDBCDataType.VARCHAR );
            remark4CellData.setDbValue( primaryKeyCellData.getDbValue() );

            // 避免重复添加
            if ( cellDataDTO.getCellDataByColumnNameDB( remark4CellData.getColumnVO().getNameDB() ) == null )
            {
               cellDatas.add( remark4CellData );
            }
         }

         // 扩充Flag
         final ColumnVO flagColumnVO = new ColumnVO();
         flagColumnVO.setNameDB( "flag" );
         flagColumnVO.setIsForeignKey( "2" );
         flagColumnVO.setAccountId( "1" );
         flagColumnVO.setIsDBColumn( "1" );

         final CellData flagCellData = new CellData();
         flagCellData.setColumnVO( flagColumnVO );
         flagCellData.setJdbcDataType( JDBCDataType.INT );
         // 默认Flag = 2，派送协议
         flagCellData.setDbValue( "2" );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( flagCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( flagCellData );
         }

         // 扩充Temp Status
         final ColumnVO tempStatusColumnVO = new ColumnVO();
         tempStatusColumnVO.setNameDB( "tempStatus" );
         tempStatusColumnVO.setIsForeignKey( "2" );
         tempStatusColumnVO.setAccountId( "1" );
         tempStatusColumnVO.setIsDBColumn( "1" );

         final CellData tempStatusCellData = new CellData();
         tempStatusCellData.setJdbcDataType( JDBCDataType.INT );
         tempStatusCellData.setColumnVO( tempStatusColumnVO );
         // 默认TempStatus = 1，新建
         tempStatusCellData.setDbValue( "1" );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( tempStatusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( tempStatusCellData );
         }

         // 扩充Line Manager Id
         final ColumnVO lineManagerColumnVO = new ColumnVO();
         lineManagerColumnVO.setNameDB( "lineManagerId" );
         lineManagerColumnVO.setIsForeignKey( "2" );
         lineManagerColumnVO.setAccountId( "1" );
         lineManagerColumnVO.setIsDBColumn( "1" );

         final CellData lineManagerIdCellData = new CellData();
         lineManagerIdCellData.setColumnVO( lineManagerColumnVO );
         lineManagerIdCellData.setJdbcDataType( JDBCDataType.INT );
         lineManagerIdCellData.setDbValue( "0" );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( lineManagerIdCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( lineManagerIdCellData );
         }

         // 扩充Employ Status
         final ColumnVO employStatusColumnVO = new ColumnVO();
         employStatusColumnVO.setNameDB( "employStatus" );
         employStatusColumnVO.setIsForeignKey( "2" );
         employStatusColumnVO.setAccountId( "1" );
         employStatusColumnVO.setIsDBColumn( "1" );

         final CellData employStatusCellData = new CellData();
         employStatusCellData.setColumnVO( employStatusColumnVO );
         employStatusCellData.setJdbcDataType( JDBCDataType.INT );
         employStatusCellData.setDbValue( "1" );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( employStatusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( employStatusCellData );
         }

         // 扩充Locked
         final ColumnVO lockedColumnVO = new ColumnVO();
         lockedColumnVO.setNameDB( "locked" );
         lockedColumnVO.setIsForeignKey( "2" );
         lockedColumnVO.setAccountId( "1" );
         lockedColumnVO.setIsDBColumn( "1" );

         final CellData lockedCellData = new CellData();
         lockedCellData.setColumnVO( lockedColumnVO );
         lockedCellData.setJdbcDataType( JDBCDataType.INT );
         lockedCellData.setDbValue( "2" );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( lockedCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( lockedCellData );
         }

         // 扩充默认Brach
         final ColumnVO branchColumnVO = new ColumnVO();
         branchColumnVO.setNameDB( "branch" );
         branchColumnVO.setIsForeignKey( "2" );
         branchColumnVO.setAccountId( "1" );
         branchColumnVO.setIsDBColumn( "1" );

         final CellData branchCellData = new CellData();
         branchCellData.setColumnVO( branchColumnVO );
         branchCellData.setJdbcDataType( JDBCDataType.INT );
         branchCellData.setDbValue( positionVO.getBranchId() );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( branchCellData );
         }
         else
         {
            if ( KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ).getDbValue(), "0" ) == null )
            {
               cellDataDTO.getCellDataByColumnNameDB( branchCellData.getColumnVO().getNameDB() ).setDbValue( positionVO.getBranchId() );
            }
         }

         // 扩充默认Owner
         final ColumnVO ownerColumnVO = new ColumnVO();
         ownerColumnVO.setNameDB( "owner" );
         ownerColumnVO.setIsForeignKey( "2" );
         ownerColumnVO.setAccountId( "1" );
         ownerColumnVO.setIsDBColumn( "1" );

         final CellData ownerCellData = new CellData();
         ownerCellData.setColumnVO( ownerColumnVO );
         ownerCellData.setJdbcDataType( JDBCDataType.INT );
         ownerCellData.setDbValue( positionVO.getPositionId() );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( ownerCellData );
         }
         else
         {
            if ( KANUtil.filterEmpty( cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ).getDbValue(), "0" ) == null )
            {
               cellDataDTO.getCellDataByColumnNameDB( ownerCellData.getColumnVO().getNameDB() ).setDbValue( positionVO.getPositionId() );
            }
         }
      }

      return flag;
   }

   // Reviewed by Kevin Jin at 2014-05-08
   private boolean fetchSbCellDataDTO( final CellDataDTO cellDataDTO, List< CellDataDTO > cellDataDTOs )
   {
      // 初始化CellData列表
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();
      boolean flag = false;
      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {

            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "sbSolutionId" ) )
            {
               if ( KANUtil.filterEmpty( cellData.getValue() ) == null )
               {
                  cellDataDTOs.remove( cellDataDTO );
                  return false;
               }
               // 初始化SocialBenefitSolutionDTO
               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getSocialBenefitSolutionDTOByName( cellData.getValue().trim(), CORP_ID );

               if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() != null )
               {
                  cellData.setDbValue( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId() );

                  flag = true;
                  continue;
               }
               else
               {
                  cellDataDTO.setErrorMessange( "单元格[" + cellData.getCellRef() + "]社保方案不存在。" );
                  cellData.setErrorMessange( "单元格[" + cellData.getCellRef() + "]社保方案不存在。" );
                  flag = false;
               }
            }
            // SB startDate 处理
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "startDate" ) )
            {
               cellData.setDbValue( KANUtil.formatDate( cellData.getValue(), "yyyy-MM-dd" ) );
               continue;
            }
         }
         if ( flag )
         {
            // 扩充状态字段
            final ColumnVO statusColumnVO = new ColumnVO();
            statusColumnVO.setNameDB( "status" );
            statusColumnVO.setIsForeignKey( "2" );
            statusColumnVO.setAccountId( "1" );
            statusColumnVO.setIsDBColumn( "1" );

            final CellData statusCellData = new CellData();
            statusCellData.setColumnVO( statusColumnVO );
            statusCellData.setDbValue( "2" );
            statusCellData.setJdbcDataType( JDBCDataType.INT );

            // 避免重复添加
            if ( cellDataDTO.getCellDataByColumnNameDB( statusCellData.getColumnVO().getNameDB() ) == null )
            {
               cellDataDTO.getCellDatas().add( statusCellData );
            }
         }

      }
      return flag;
   }

   // Reviewed by Kevin Jin at 2014-05-08
   private void fetchSalaryCellDataDTO( final CellDataDTO cellDataDTO, final List< ItemVO > itemVOs, final String startDate, final String endDate, final String orderId )
   {
      // 初始化CellData列表
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();

      if ( cellDatas != null )
      {
         // 初始化ClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = null;
         // 初始化Salary Type
         String salaryType = "";
         // 初始化Divide Type
         String divideType = "";
         // 初始化Exclude Divide Item Ids
         String excludeDivideItemIds = "";

         try
         {
            clientOrderHeaderVO = this.getClientOrderHeaderDao().getClientOrderHeaderVOByOrderHeaderId( orderId );
            salaryType = clientOrderHeaderVO.getSalaryType();
            divideType = clientOrderHeaderVO.getDivideType();
            excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
         }
         catch ( KANException e )
         {
            e.printStackTrace();
         }

         // 扩充Salary Type
         final ColumnVO salaryTypeColumnVO = new ColumnVO();
         salaryTypeColumnVO.setNameDB( "salaryType" );
         salaryTypeColumnVO.setIsForeignKey( "2" );
         salaryTypeColumnVO.setAccountId( "1" );
         salaryTypeColumnVO.setIsDBColumn( "1" );

         final CellData salaryTypeCellData = new CellData();
         salaryTypeCellData.setDbValue( KANUtil.filterEmpty( salaryType ) != null ? salaryType : "1" );
         salaryTypeCellData.setJdbcDataType( JDBCDataType.INT );
         salaryTypeCellData.setColumnVO( salaryTypeColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( salaryTypeCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( salaryTypeCellData );
         }

         // 扩充Divide Type
         final ColumnVO divideTypeColumnVO = new ColumnVO();
         divideTypeColumnVO.setNameDB( "divideType" );
         divideTypeColumnVO.setIsForeignKey( "2" );
         divideTypeColumnVO.setAccountId( "1" );
         divideTypeColumnVO.setIsDBColumn( "1" );

         final CellData divideTypeCellData = new CellData();
         divideTypeCellData.setDbValue( KANUtil.filterEmpty( divideType ) != null ? divideType : "1" );
         divideTypeCellData.setJdbcDataType( JDBCDataType.INT );
         divideTypeCellData.setColumnVO( divideTypeColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( divideTypeCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( divideTypeCellData );
         }

         // 扩充Exclude Divide Item Ids
         final ColumnVO excludeDivideItemIdsColumnVO = new ColumnVO();
         excludeDivideItemIdsColumnVO.setNameDB( "excludeDivideItemIds" );
         excludeDivideItemIdsColumnVO.setIsForeignKey( "2" );
         excludeDivideItemIdsColumnVO.setAccountId( "1" );
         excludeDivideItemIdsColumnVO.setIsDBColumn( "1" );

         final CellData excludeDivideItemIdsCellData = new CellData();
         excludeDivideItemIdsCellData.setDbValue( KANUtil.filterEmpty( excludeDivideItemIds ) != null ? excludeDivideItemIds : "1" );
         excludeDivideItemIdsCellData.setJdbcDataType( JDBCDataType.INT );
         excludeDivideItemIdsCellData.setColumnVO( excludeDivideItemIdsColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( excludeDivideItemIdsCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( excludeDivideItemIdsCellData );
         }

         // 扩充Base From
         final ColumnVO baseFromColumnVO = new ColumnVO();
         baseFromColumnVO.setNameDB( "baseFrom" );
         baseFromColumnVO.setIsForeignKey( "2" );
         baseFromColumnVO.setAccountId( "1" );
         baseFromColumnVO.setIsDBColumn( "1" );

         final CellData baseFromCellData = new CellData();
         baseFromCellData.setDbValue( "0" );
         baseFromCellData.setJdbcDataType( JDBCDataType.INT );
         baseFromCellData.setColumnVO( baseFromColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( baseFromCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( baseFromCellData );
         }

         // 扩充Cycle
         final ColumnVO cycleColumnVO = new ColumnVO();
         cycleColumnVO.setNameDB( "cycle" );
         cycleColumnVO.setIsForeignKey( "2" );
         cycleColumnVO.setAccountId( "1" );
         cycleColumnVO.setIsDBColumn( "1" );

         final CellData cycleCellData = new CellData();
         cycleCellData.setDbValue( "1" );
         cycleCellData.setJdbcDataType( JDBCDataType.INT );
         cycleCellData.setColumnVO( cycleColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( cycleCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( cycleCellData );
         }

         // 扩充Start Date
         final ColumnVO startDateColumnVO = new ColumnVO();
         startDateColumnVO.setNameDB( "startDate" );
         startDateColumnVO.setIsForeignKey( "2" );
         startDateColumnVO.setAccountId( "1" );
         startDateColumnVO.setIsDBColumn( "1" );

         final CellData startDateCellData = new CellData();
         startDateCellData.setDbValue( startDate );
         startDateCellData.setJdbcDataType( JDBCDataType.INT );
         startDateCellData.setColumnVO( startDateColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( startDateCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( startDateCellData );
         }

         // 扩充End Date
         final ColumnVO endDateColumnVO = new ColumnVO();
         endDateColumnVO.setNameDB( "endDate" );
         endDateColumnVO.setIsForeignKey( "2" );
         endDateColumnVO.setAccountId( "1" );
         endDateColumnVO.setIsDBColumn( "1" );

         final CellData endDateCellData = new CellData();
         endDateCellData.setDbValue( endDate );
         endDateCellData.setJdbcDataType( JDBCDataType.INT );
         endDateCellData.setColumnVO( endDateColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( endDateCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( endDateCellData );
         }

         // 扩充Result Cap
         final ColumnVO resultCapColumnVO = new ColumnVO();
         resultCapColumnVO.setNameDB( "resultCap" );
         resultCapColumnVO.setIsForeignKey( "2" );
         resultCapColumnVO.setAccountId( "1" );
         resultCapColumnVO.setIsDBColumn( "1" );

         final CellData resultCapCellData = new CellData();
         resultCapCellData.setDbValue( "0" );
         resultCapCellData.setJdbcDataType( JDBCDataType.INT );
         resultCapCellData.setColumnVO( resultCapColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( resultCapCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( resultCapCellData );
         }

         // 扩充Result Floor
         final ColumnVO resultFloorColumnVO = new ColumnVO();
         resultFloorColumnVO.setNameDB( "resultFloor" );
         resultFloorColumnVO.setIsForeignKey( "2" );
         resultFloorColumnVO.setAccountId( "1" );
         resultFloorColumnVO.setIsDBColumn( "1" );

         final CellData resultFloorCellData = new CellData();
         resultFloorCellData.setDbValue( "0" );
         resultFloorCellData.setJdbcDataType( JDBCDataType.INT );
         resultFloorCellData.setColumnVO( resultFloorColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( resultFloorCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( resultFloorCellData );
         }

         // 扩充Show to TS
         final ColumnVO showToTSColumnVO = new ColumnVO();
         showToTSColumnVO.setNameDB( "showToTS" );
         showToTSColumnVO.setIsForeignKey( "2" );
         showToTSColumnVO.setAccountId( "1" );
         showToTSColumnVO.setIsDBColumn( "1" );

         final CellData showToTSCellData = new CellData();
         showToTSCellData.setDbValue( "2" );
         showToTSCellData.setJdbcDataType( JDBCDataType.INT );
         showToTSCellData.setColumnVO( showToTSColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( showToTSCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( showToTSCellData );
         }

         // 扩充Probation Using
         final ColumnVO probationUsingColumnVO = new ColumnVO();
         probationUsingColumnVO.setNameDB( "probationUsing" );
         probationUsingColumnVO.setIsForeignKey( "2" );
         probationUsingColumnVO.setAccountId( "1" );
         probationUsingColumnVO.setIsDBColumn( "1" );

         final CellData probationUsingCellData = new CellData();
         probationUsingCellData.setDbValue( "2" );
         probationUsingCellData.setJdbcDataType( JDBCDataType.INT );
         probationUsingCellData.setColumnVO( probationUsingColumnVO );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( probationUsingCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDatas.add( probationUsingCellData );
         }

         String[][] copyPropertys = { { "itemId", "INT" } };

         addTableColumnsForItem( cellDataDTO, itemVOs, copyPropertys );
      }
   }

   private void fetchCbCellDataDTO( CellDataDTO cellDataDTO )
   {

      // 初始化CellData列表
      final List< CellData > cellDatas = cellDataDTO.getCellDatas();

      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {
            // SB Solution Id 处理
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equalsIgnoreCase( "solutionId" ) )
            {
               // 初始化SocialBenefitSolutionDTO
               final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( ACCOUNT_ID ).getCommercialBenefitSolutionDTOByName( cellData.getValue().trim(), CORP_ID );

               if ( commercialBenefitSolutionDTO != null && commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO() != null )
               {
                  cellData.setDbValue( commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO().getHeaderId() );
                  break;
               }
            }
         }

         // 扩充状态字段
         final ColumnVO statusColumnVO = new ColumnVO();
         statusColumnVO.setNameDB( "status" );
         statusColumnVO.setIsForeignKey( "2" );
         statusColumnVO.setAccountId( "1" );
         statusColumnVO.setIsDBColumn( "1" );

         final CellData statusCellData = new CellData();
         statusCellData.setColumnVO( statusColumnVO );
         statusCellData.setDbValue( "2" );
         statusCellData.setJdbcDataType( JDBCDataType.INT );

         // 避免重复添加
         if ( cellDataDTO.getCellDataByColumnNameDB( statusCellData.getColumnVO().getNameDB() ) == null )
         {
            cellDataDTO.getCellDatas().add( statusCellData );
         }
      }

   }

   private boolean addTableColumnsForItem_leave( final CellDataDTO subImportDataDTO, final List< ItemVO > leaveItemVOs, final String[] copyPropertys )
   {
      boolean isAnnualLeave = false;
      // 初始化CellDataDTO
      final CellDataDTO tempCellDataDTO = new CellDataDTO();

      String tempItemId = "";
      // 遍历CellData，匹配找到itemId
      if ( subImportDataDTO.getCellDatas() != null )
      {
         for ( CellData cellData : subImportDataDTO.getCellDatas() )
         {
            String headeCellValue = cellData.getColumnVO().getNameZH();
            if ( KANUtil.filterEmpty( headeCellValue ) != null )
            {
               if ( headeCellValue.contains( "年假" ) && ( headeCellValue.contains( "法定" ) || headeCellValue.contains( "福利" ) ) )
               {
                  headeCellValue = "年假";
                  isAnnualLeave = true;
               }

               for ( ItemVO tempItemVO : leaveItemVOs )
               {
                  if ( tempItemVO.getNameZH().equalsIgnoreCase( headeCellValue ) )
                  {
                     tempItemId = tempItemVO.getItemId();

                     final CellData itemIdCellData = tempCellDataDTO.getCellDataByColumnNameDB( "itemId" );

                     if ( itemIdCellData == null )
                     {
                        final CellData tempItemIdCellData = new CellData();
                        final ColumnVO tempItemIdColumnVO = new ColumnVO();
                        tempItemIdCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( copyPropertys[ 1 ] ) );
                        tempItemIdCellData.setDbValue( tempItemId );
                        tempItemIdColumnVO.setNameDB( copyPropertys[ 0 ] );
                        tempItemIdColumnVO.setIsForeignKey( "2" );
                        tempItemIdColumnVO.setAccountId( "1" );
                        tempItemIdColumnVO.setIsDBColumn( "1" );

                        tempItemIdCellData.setColumnVO( tempItemIdColumnVO );

                        tempCellDataDTO.getCellDatas().add( tempItemIdCellData );
                     }

                     break;
                  }
               }
            }
         }
      }

      if ( tempCellDataDTO.getCellDatas().size() > 0 )
      {
         subImportDataDTO.getCellDatas().addAll( tempCellDataDTO.getCellDatas() );
      }

      return isAnnualLeave;
   }

   private void addLeaveTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[] copyPropertys = { "itemId", "INT" };
      boolean isAnnualLeave = addTableColumnsForItem_leave( subImportDataDTO, allItemVOs, copyPropertys );

      // 如果是年假，需扩充字段
      if ( isAnnualLeave )
      {
         // 使用周期
         final CellData cycleCellData = new CellData();
         final ColumnVO cycleColumnVO = new ColumnVO();
         cycleCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
         cycleCellData.setDbValue( "5" );
         cycleColumnVO.setNameDB( "cycle" );
         cycleColumnVO.setIsForeignKey( "2" );
         cycleColumnVO.setAccountId( "1" );
         cycleColumnVO.setIsDBColumn( "1" );
         cycleCellData.setColumnVO( cycleColumnVO );
         subImportDataDTO.getCellDatas().add( cycleCellData );

         // 年份
         final CellData yearCellData = new CellData();
         final ColumnVO yearColumnVO = new ColumnVO();
         yearCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( "INT" ) );
         yearCellData.setDbValue( KANUtil.formatDate( new Date(), "yyyy" ) );
         yearColumnVO.setNameDB( "year" );
         yearColumnVO.setIsForeignKey( "2" );
         yearColumnVO.setAccountId( "1" );
         yearColumnVO.setIsDBColumn( "1" );
         yearCellData.setColumnVO( yearColumnVO );
         subImportDataDTO.getCellDatas().add( yearCellData );
      }
   }

   private void addOtTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[][] copyPropertys = { { "itemId", "INT" } };
      addTableColumnsForItem( subImportDataDTO, allItemVOs, copyPropertys );
   }

   private void addOtherTableColumns( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs )
   {
      String[][] copyPropertys = { { "itemId", "INT" } };
      addTableColumnsForItem( subImportDataDTO, allItemVOs, copyPropertys );
   }

   private void addTableColumnsForItem( CellDataDTO subImportDataDTO, List< ItemVO > allItemVOs, final String[][] copyPropertys )
   {

      List< CellData > cellDatas = subImportDataDTO.getCellDatas();
      List< CellData > tempCellDatas = new ArrayList< CellData >();

      if ( cellDatas != null )
      {
         for ( CellData cellData : cellDatas )
         {
            // 表头字符串
            String headeCellValue = cellData.getColumnVO().getNameZH();
            // 过滤掉自动添加的createBy，modifyBy对应的单元格
            if ( headeCellValue != null )
            {
               ItemVO matchItemVO = null;
               for ( ItemVO itemVO : allItemVOs )
               {
                  if ( itemVO.getNameZH().equalsIgnoreCase( headeCellValue ) )
                  {
                     matchItemVO = itemVO;
                     break;
                  }
               }

               if ( matchItemVO != null )
               {
                  for ( String[] property : copyPropertys )
                  {

                     CellData addCellData = new CellData();
                     ColumnVO addCellColumnVO = new ColumnVO();

                     addCellColumnVO.setNameDB( property[ 0 ] );
                     addCellColumnVO.setIsForeignKey( "2" );
                     addCellData.setJdbcDataType( JDBCDataType.INT.getValueByName( property[ 1 ] ) );
                     addCellColumnVO.setAccountId( "1" );
                     addCellColumnVO.setIsDBColumn( "1" );
                     addCellData.setColumnVO( addCellColumnVO );
                     try
                     {
                        addCellData.setDbValue( BeanUtils.getProperty( matchItemVO, property[ 0 ] ) );
                     }
                     catch ( Exception e )
                     {
                        e.printStackTrace();
                     }

                     // 如果是销售奖金(季度)
                     if ( "10153".equals( matchItemVO.getItemId() ) )
                     {
                        CellData cycleCellData = subImportDataDTO.getCellDataByColumnNameDB( "cycle" );
                        if ( cycleCellData != null )
                        {
                           cycleCellData.setDbValue( "3" );
                           cycleCellData.setValue( "3" );
                        }
                     }

                     tempCellDatas.add( addCellData );
                  }
               }
            }
         }

         cellDatas.addAll( tempCellDatas );
      }
   }

   // Added by Kevin Jin at 2014-05-08
   private String getStartDate( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "startDate" ) )
            {
               if ( cellData.getFormateValue() == null )
               {
                  return null;
               }
               else
               {
                  String date = KANUtil.formatDate( cellData.getFormateValue(), "yyyy-MM-dd" );
                  cellData.setDbValue( date );
                  return date;
               }
            }
         }
      }

      return null;
   }

   // Added by Kevin Jin at 2014-05-08
   private String getEndDate( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "endDate" ) )
            {
               if ( cellData.getFormateValue() == null )
               {
                  return null;
               }
               else
               {
                  String date = KANUtil.formatDate( cellData.getFormateValue(), "yyyy-MM-dd" );
                  cellData.setDbValue( date );
                  return date;
               }

            }
         }
      }

      return null;
   }

   // Added by Kevin Jin at 2014-06-09
   private String getOrderId( final CellDataDTO cellDataDTO )
   {
      if ( cellDataDTO != null && cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {
         for ( CellData cellData : cellDataDTO.getCellDatas() )
         {
            if ( cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ) != null
                  && KANUtil.filterEmpty( cellData.getColumnVO().getNameDB() ).trim().equals( "orderId" ) )
            {
               return ( String ) cellData.getDbValue();
            }
         }
      }

      return null;
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

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public final ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public final void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public EmployeeContractTempDao getEmployeeContractTempDao()
   {
      return employeeContractTempDao;
   }

   public void setEmployeeContractTempDao( EmployeeContractTempDao employeeContractTempDao )
   {
      this.employeeContractTempDao = employeeContractTempDao;
   }

   public PositionDao getPositionDao()
   {
      return positionDao;
   }

   public void setPositionDao( PositionDao positionDao )
   {
      this.positionDao = positionDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractService getEmployeeContractService()
   {
      return employeeContractService;
   }

   public void setEmployeeContractService( EmployeeContractService employeeContractService )
   {
      this.employeeContractService = employeeContractService;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      return false;
   }

   public StaffDao getStaffDao()
   {
      return staffDao;
   }

   public void setStaffDao( StaffDao staffDao )
   {
      this.staffDao = staffDao;
   }
}
