package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.domain.common.CommonBatchVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.BankVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;

public class SalaryImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{
   private EmployeeDao employeeDao;

   private EmployeeContractDao employeeContractDao;

   private ClientDao clientDao;

   private CommonBatchDao commonBatchDao;

   private SalaryHeaderDao salaryHeaderDao;

   private SalaryDetailDao salaryDetailDao;

   // 劳动合同 ID
   private String contractId;;

   @Override
   public boolean excuteBeforInsert( final List< CellDataDTO > cellDataDTOs, final HttpServletRequest request )
   {
      boolean flag = true;

      String erroMessange = "";

      // 遍历 importData 查找子表数据
      for ( CellDataDTO cellDataDTO : cellDataDTOs )
      {
         //日期格式支持“.-/”,但写入数据库用“/”
         CellData monthlyCell = cellDataDTO.getCellDataByColumnNameDB( "monthly" );

         if ( monthlyCell != null && monthlyCell.getDbValue() != null )
         {
            monthlyCell.setDbValue( KANUtil.formatDate( monthlyCell.getDbValue(), "yyyy/MM" ) );
         }

         if ( cellDataDTO != null )
         {
            // 初始化主表总计
            double billAmountCompany = 0;
            double costAmountCompany = 0;
            double billAmountPersonal = 0;
            double costAmountPersonal = 0;

            //存储需要拆分的子表数据
            Map< String, CellDataDTO > cellDatasMaps = new HashMap< String, CellDataDTO >();

            // 遍历 子表数据查找数据对应的CellDatas
            for ( int i = 0; i < cellDataDTO.getSubCellDataDTOs().size(); i++ )
            {
               // 获得子CellDataDTO
               final CellDataDTO subCellDataDTO = cellDataDTO.getSubCellDataDTOs().get( i );
               // 缓存CellData列表
               final List< CellData > tempCellData = new ArrayList< CellData >();
               // 初始化ItemVO
               ItemVO itemVO = null;

               if ( subCellDataDTO != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
               {
                  // 初始化当前子对象是否是空的
                  boolean isEmpty = false;

                  String firstItemName = "";

                  final Iterator< CellData > iterators = subCellDataDTO.getCellDatas().iterator();

                  while ( iterators.hasNext() )
                  {
                     final CellData cellData = iterators.next();
                     final CellDataDTO tempSubCellDataDTO = new CellDataDTO();

                     if ( cellData != null && cellData.getColumnVO() != null && KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ) != null )
                     {
                        // 初始化Item Name
                        //                        final String itemName = KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ) != null
                        //                              && KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).contains( "-" ) ? cellData.getColumnVO().getNameZH().split( "-" )[ 0 ].trim()
                        //                              : cellData.getColumnVO().getNameZH();
                        String itemName = cellData.getColumnVO().getNameZH();

                        // 初始化Item Sub Name
                        String itemSubName = "";

                        if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).contains( "-" )
                              && KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).split( "-" ).length > 1 )
                        {
                           itemSubName = KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).split( "-" )[ 1 ].trim();
                        }

                        try
                        {
                           itemVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemVOByItemName( itemName, BaseAction.getCorpId( request, null ) );

                           if ( itemVO == null )
                           {
                              itemName = KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ) != null
                                    && KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).contains( "-" ) ? cellData.getColumnVO().getNameZH().split( "-" )[ 0 ].trim()
                                    : cellData.getColumnVO().getNameZH();

                              itemVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemVOByItemName( itemName, BaseAction.getCorpId( request, null ) );
                              if ( KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).contains( "-" )
                                    && KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).split( "-" ).length > 1 )
                              {
                                 itemSubName = KANUtil.filterEmpty( cellData.getColumnVO().getNameZH() ).split( "-" )[ 1 ].trim();

                              }
                           }

                           if ( !firstItemName.equals( itemName ) )
                           {
                              tempSubCellDataDTO.setTableId( subCellDataDTO.getTableId() );
                              tempSubCellDataDTO.setTableName( subCellDataDTO.getTableName() );
                           }
                        }
                        catch ( KANException e )
                        {
                           e.printStackTrace();
                        }

                        if ( itemVO != null )
                        {
                           if ( cellData.getColumnVO().getNameDB().trim().equals( "billAmountCompany" ) || cellData.getColumnVO().getNameDB().trim().equals( "billAmountPersonal" )
                                 || cellData.getColumnVO().getNameDB().trim().equals( "costAmountCompany" )
                                 || cellData.getColumnVO().getNameDB().trim().equals( "costAmountPersonal" ) )
                           {
                              // 初始化Sub Amount
                              double subBillAmountCompany = 0;
                              double subBillAmountPersonal = 0;
                              double subCostAmountCompany = 0;
                              double subCostAmountPersonal = 0;

                              final String tempDBValue = KANUtil.filterEmpty( cellData.getDbValue() ) == null ? "0" : String.valueOf( cellData.getDbValue() );

                              if ( KANUtil.filterEmpty( itemSubName ) != null )
                              {
                                 if ( KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "公司" ) )
                                 {
                                    subBillAmountCompany = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getBillRateCompany() ) / 100;
                                    subCostAmountCompany = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getCostRateCompany() ) / 100;
                                 }
                                 else if ( KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "个人" ) )
                                 {
                                    subBillAmountPersonal = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getBillRatePersonal() ) / 100;
                                    subCostAmountPersonal = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getCostRatePersonal() ) / 100;
                                 }
                              }
                              else
                              {
                                 subBillAmountCompany = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getBillRateCompany() ) / 100;
                                 subBillAmountPersonal = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getBillRatePersonal() ) / 100;
                                 subCostAmountCompany = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getCostRateCompany() ) / 100;
                                 subCostAmountPersonal = Double.valueOf( tempDBValue ) * Double.valueOf( itemVO.getCostRatePersonal() ) / 100;
                              }

                              // 如果金额为“0”的情况，记录被忽略
                              if ( subBillAmountCompany == 0 && subBillAmountPersonal == 0 && subCostAmountCompany == 0 && subCostAmountPersonal == 0 )
                              {
                                 isEmpty = true;
                                 iterators.remove();
                                 continue;
                              }

                              // 累加Amount
                              billAmountCompany = billAmountCompany + subBillAmountCompany;
                              billAmountPersonal = billAmountPersonal + subBillAmountPersonal;
                              costAmountCompany = costAmountCompany + subCostAmountCompany;
                              costAmountPersonal = costAmountPersonal + subCostAmountPersonal;

                              // 添加Bill Amount Company
                              if ( KANUtil.filterEmpty( itemSubName ) == null || KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "公司" ) )
                              {
                                 if ( subCellDataDTO.getCellDataByColumnNameDB( "billAmountCompany", cellData.getColumnVO().getNameZH() ) == null )
                                 {
                                    final CellData billAmountCompanyCellData = new CellData();
                                    final ColumnVO billAmountCompanyColumn = new ColumnVO();
                                    billAmountCompanyColumn.setAccountId( "1" );
                                    billAmountCompanyColumn.setIsDBColumn( "1" );
                                    billAmountCompanyColumn.setNameDB( "billAmountCompany" );
                                    billAmountCompanyCellData.setColumnVO( billAmountCompanyColumn );
                                    billAmountCompanyCellData.setJdbcDataType( JDBCDataType.DOUBLE );
                                    billAmountCompanyCellData.setDbValue( subBillAmountCompany );

                                    //                                    tempCellData.add( billAmountCompanyCellData );
                                    if ( KANUtil.filterEmpty( firstItemName ) != null && !firstItemName.equals( itemName ) )
                                    {
                                       tempSubCellDataDTO.getCellDatas().add( billAmountCompanyCellData );
                                    }
                                    else
                                    {
                                       tempCellData.add( billAmountCompanyCellData );
                                    }
                                 }
                                 else
                                 {
                                    subCellDataDTO.getCellDataByColumnNameDB( "billAmountCompany" ).setDbValue( subBillAmountCompany );
                                 }
                              }

                              // 添加Bill Amount Personal
                              if ( KANUtil.filterEmpty( itemSubName ) == null || KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "个人" ) )
                              {
                                 if ( subCellDataDTO.getCellDataByColumnNameDB( "billAmountPersonal", cellData.getColumnVO().getNameZH() ) == null )
                                 {

                                    final CellData billAmountPersonalCellData = new CellData();
                                    final ColumnVO billAmountPersonalColumn = new ColumnVO();
                                    billAmountPersonalColumn.setAccountId( "1" );
                                    billAmountPersonalColumn.setIsDBColumn( "1" );
                                    billAmountPersonalColumn.setNameDB( "billAmountPersonal" );
                                    billAmountPersonalCellData.setColumnVO( billAmountPersonalColumn );
                                    billAmountPersonalCellData.setJdbcDataType( JDBCDataType.DOUBLE );
                                    billAmountPersonalCellData.setDbValue( subBillAmountPersonal );

                                    //                                    tempCellData.add( billAmountPersonalCellData );
                                    if ( KANUtil.filterEmpty( firstItemName ) != null && !firstItemName.equals( itemName ) )
                                    {
                                       tempSubCellDataDTO.getCellDatas().add( billAmountPersonalCellData );
                                    }
                                    else
                                    {
                                       tempCellData.add( billAmountPersonalCellData );
                                    }
                                 }
                                 else
                                 {
                                    subCellDataDTO.getCellDataByColumnNameDB( "billAmountPersonal" ).setDbValue( subBillAmountPersonal );
                                 }
                              }

                              // 添加Cost Amount Company
                              if ( KANUtil.filterEmpty( itemSubName ) == null || KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "公司" ) )
                              {
                                 if ( subCellDataDTO.getCellDataByColumnNameDB( "costAmountCompany", cellData.getColumnVO().getNameZH() ) == null )
                                 {

                                    final CellData costAmountCompanyCellData = new CellData();
                                    final ColumnVO costAmountCompanyColumn = new ColumnVO();
                                    costAmountCompanyColumn.setAccountId( "1" );
                                    costAmountCompanyColumn.setIsDBColumn( "1" );
                                    costAmountCompanyColumn.setNameDB( "costAmountCompany" );
                                    costAmountCompanyCellData.setColumnVO( costAmountCompanyColumn );
                                    costAmountCompanyCellData.setJdbcDataType( JDBCDataType.DOUBLE );
                                    costAmountCompanyCellData.setDbValue( subCostAmountCompany );

                                    //                                    tempCellData.add( costAmountCompanyCellData );
                                    if ( KANUtil.filterEmpty( firstItemName ) != null && !firstItemName.equals( itemName ) )
                                    {
                                       tempSubCellDataDTO.getCellDatas().add( costAmountCompanyCellData );
                                    }
                                    else
                                    {
                                       tempCellData.add( costAmountCompanyCellData );
                                    }
                                 }
                                 else
                                 {
                                    subCellDataDTO.getCellDataByColumnNameDB( "costAmountCompany" ).setDbValue( subCostAmountCompany );
                                 }
                              }

                              // 添加Cost Amount Personal
                              if ( KANUtil.filterEmpty( itemSubName ) == null || KANUtil.filterEmpty( itemSubName ).trim().equalsIgnoreCase( "个人" ) )
                              {
                                 if ( subCellDataDTO.getCellDataByColumnNameDB( "costAmountPersonal", cellData.getColumnVO().getNameZH() ) == null )
                                 {

                                    final CellData costAmountPersonalCellData = new CellData();
                                    final ColumnVO costAmountPersonalColumn = new ColumnVO();
                                    costAmountPersonalColumn.setAccountId( "1" );
                                    costAmountPersonalColumn.setIsDBColumn( "1" );
                                    costAmountPersonalColumn.setNameDB( "costAmountPersonal" );
                                    costAmountPersonalCellData.setColumnVO( costAmountPersonalColumn );
                                    costAmountPersonalCellData.setJdbcDataType( JDBCDataType.DOUBLE );
                                    costAmountPersonalCellData.setDbValue( subCostAmountPersonal );

                                    //                                    tempCellData.add( costAmountPersonalCellData );
                                    if ( KANUtil.filterEmpty( firstItemName ) != null && !firstItemName.equals( itemName ) )
                                    {
                                       tempSubCellDataDTO.getCellDatas().add( costAmountPersonalCellData );
                                    }
                                    else
                                    {
                                       tempCellData.add( costAmountPersonalCellData );
                                    }
                                 }
                                 else
                                 {
                                    subCellDataDTO.getCellDataByColumnNameDB( "costAmountPersonal" ).setDbValue( subCostAmountPersonal );
                                 }
                              }

                              //判断是否需要拆分 
                              if ( StringUtils.isBlank( firstItemName ) || firstItemName.equals( itemName ) )
                              {
                                 //赋值第一个itemName
                                 if ( cellDatasMaps.containsKey( itemName ) )
                                 {
                                    tempCellData.addAll( cellDatasMaps.get( itemName ).getCellDatas() );
                                    cellDatasMaps.remove( itemName );
                                 }
                                 else if ( StringUtils.isBlank( firstItemName ) )
                                 {
                                    extendItems( tempCellData, itemVO );
                                 }

                                 firstItemName = itemName;
                              }
                              else
                              {
                                 if ( cellDatasMaps.containsKey( itemName ) )
                                 {
                                    cellDatasMaps.get( itemName ).getCellDatas().add( cellData );
                                 }
                                 else
                                 {
                                    tempSubCellDataDTO.getCellDatas().add( cellData );
                                    extendItems( tempSubCellDataDTO.getCellDatas(), itemVO );
                                    cellDatasMaps.put( itemName, tempSubCellDataDTO );
                                 }
                                 iterators.remove();
                              }
                           }
                        }
                        else
                        {
                           flag = false;
                           cellData.setErrorMessange( "单元格[" + cellData.getCellRef() + "]错误，不存在该元素，请删除后上传！" );
                           break;
                        }
                     }
                  }

                  //                  if ( isEmpty && tempCellData.size() == 0 )
                  //                  {
                  //                     subCellDataDTO.getCellDatas().clear();
                  //                  }
                  //                  else
                  //                  {
                  //                     subCellDataDTO.getCellDatas().addAll( tempCellData );
                  //                     // 扩充Items
                  //                     extendItems( subCellDataDTO, itemVO );
                  //                  }

                  if ( isEmpty && tempCellData.size() == 0 )
                  {
                     subCellDataDTO.getCellDatas().clear();
                  }
                  else
                  {
                     subCellDataDTO.getCellDatas().addAll( tempCellData );
                  }
               }
            }

            cellDataDTO.getSubCellDataDTOs().addAll( cellDatasMaps.values() );
            cellDatasMaps.clear();

            // 扩充主键
            final CellData salaryHeaderIdCellData = new CellData();
            final ColumnVO salaryHeaderIdColumn = new ColumnVO();
            salaryHeaderIdColumn.setAccountId( "1" );
            salaryHeaderIdColumn.setIsDBColumn( "1" );
            salaryHeaderIdColumn.setNameDB( "salaryHeaderId" );
            salaryHeaderIdColumn.setIsPrimaryKey( "1" );
            salaryHeaderIdCellData.setColumnVO( salaryHeaderIdColumn );
            salaryHeaderIdCellData.setJdbcDataType( JDBCDataType.INT );

            if ( !cellDataDTO.containColumnByNameDB( salaryHeaderIdColumn ) )
            {
               cellDataDTO.getCellDatas().add( salaryHeaderIdCellData );
            }

            // 扩充状态
            final CellData statusCellData = new CellData();
            final ColumnVO statusColumn = new ColumnVO();
            statusColumn.setAccountId( "1" );
            statusColumn.setIsDBColumn( "1" );
            statusColumn.setNameDB( "status" );
            statusCellData.setColumnVO( statusColumn );
            statusCellData.setJdbcDataType( JDBCDataType.INT );
            statusCellData.setDbValue( "1" );

            // 添加状态 - 如果导入列表不含“状态”字段的情况
            if ( !cellDataDTO.containColumnByNameDB( statusColumn ) )
            {
               cellDataDTO.getCellDatas().add( statusCellData );
            }

            // 获取Contract ID的处理
            final CellData contractIdCellData = cellDataDTO.getCellDataByColumnNameDB( "contractId" );

            if ( contractIdCellData != null )
            {
               contractId = ( String ) contractIdCellData.getDbValue();
            }

            // 初始化ClientVO
            ClientVO clientVO = null;

            // 初始化EmployeeVO
            EmployeeVO employeeVO = null;

            // 初始化BankVO
            BankVO bankVO = null;

            // 初始化EmployeeContractVO
            EmployeeContractVO employeeContractVO = null;

            try
            {
               employeeContractVO = this.getEmployeeContractDao().getEmployeeContractVOByContractId( contractId );

               if ( employeeContractVO == null )
               {
                  erroMessange = erroMessange + "第" + contractIdCellData.getCellRow() + "行，劳动合同不存在，请检查后再上传！</br>";
                  flag = false;
               }
               else
               {
                  clientVO = this.getClientDao().getClientVOByClientId( employeeContractVO.getClientId() );
                  employeeVO = this.getEmployeeDao().getEmployeeVOByEmployeeId( employeeContractVO.getEmployeeId() );

                  if ( clientVO == null )
                  {
                     erroMessange = erroMessange + "第" + contractIdCellData.getCellRow() + "行，未找到对应企业，请检查后再上传！</br>";
                     flag = false;
                  }

                  if ( employeeVO == null )
                  {
                     erroMessange = erroMessange + "第" + contractIdCellData.getCellRow() + "行，未找到对应员工，请检查后再上传！</br>";
                     flag = false;
                  }
                  else
                  {
                     bankVO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getBankVOByBankId( employeeVO.getBankId() );
                  }
               }
            }
            catch ( final KANException e )
            {
               // Nothing to do
               e.printStackTrace();
            }

            // 获取Tax Amount Personal的处理
            final CellData taxAmountPersonalCellData = cellDataDTO.getCellDataByColumnNameDB( "taxAmountPersonal" );

            if ( taxAmountPersonalCellData != null )
            {
               taxAmountPersonalCellData.setDbValue( 0 );
            }

            // 获取Addtional Bill Amount Personal的处理
            final CellData addtionalBillAmountPersonalCellData = cellDataDTO.getCellDataByColumnNameDB( "addtionalBillAmountPersonal" );

            if ( addtionalBillAmountPersonalCellData != null )
            {
               addtionalBillAmountPersonalCellData.setDbValue( 0 );
            }

            // 未出错的情况
            if ( flag )
            {
               // 回填法务实体
               final CellData entityIdCellData = new CellData();
               final ColumnVO entityIdColumn = new ColumnVO();
               entityIdColumn.setAccountId( "1" );
               entityIdColumn.setIsDBColumn( "1" );
               entityIdColumn.setNameDB( "entityId" );
               entityIdCellData.setColumnVO( entityIdColumn );
               entityIdCellData.setJdbcDataType( JDBCDataType.INT );
               entityIdCellData.setDbValue( employeeContractVO.getEntityId() );
               cellDataDTO.addCellData( entityIdCellData );

               // 添加业务类型
               /*   final CellData businessTypeIdData = new CellData();
                  final ColumnVO businessTypeIdColumn = new ColumnVO();
                  businessTypeIdColumn.setAccountId( "1" );
                  businessTypeIdColumn.setIsDBColumn( "1" );
                  businessTypeIdColumn.setNameDB( "businessTypeId" );
                  businessTypeIdData.setColumnVO( businessTypeIdColumn );
                  businessTypeIdData.setJdbcDataType( JDBCDataType.INT );
                  businessTypeIdData.setDbValue( employeeContractVO.getBusinessTypeId() );
                  cellDataDTO.getCellDatas().add( businessTypeIdData );*/

               // 回填订单ID
               final CellData orderCellData = new CellData();
               final ColumnVO orderColumn = new ColumnVO();
               orderColumn.setAccountId( "1" );
               orderColumn.setIsDBColumn( "1" );
               orderColumn.setNameDB( "orderId" );
               orderCellData.setColumnVO( orderColumn );
               orderCellData.setJdbcDataType( JDBCDataType.INT );
               orderCellData.setDbValue( employeeContractVO.getOrderId() );
               cellDataDTO.addCellData( orderCellData );

               // 回填客户ID
               final CellData clientCellData = new CellData();
               final ColumnVO clientColumn = new ColumnVO();
               clientColumn.setAccountId( "1" );
               clientColumn.setIsDBColumn( "1" );
               clientColumn.setNameDB( "clientId" );
               clientCellData.setColumnVO( clientColumn );
               clientCellData.setJdbcDataType( JDBCDataType.INT );
               clientCellData.setDbValue( employeeContractVO.getClientId() );
               cellDataDTO.addCellData( clientCellData );

               // 回填客户中文名
               final CellData clientNameZHCellData = new CellData();
               final ColumnVO clientNameZHColumn = new ColumnVO();
               clientNameZHColumn.setAccountId( "1" );
               clientNameZHColumn.setIsDBColumn( "1" );
               clientNameZHColumn.setNameDB( "clientNameZH" );
               clientNameZHCellData.setColumnVO( clientNameZHColumn );
               clientNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               clientNameZHCellData.setDbValue( clientVO.getNameZH() );
               cellDataDTO.addCellData( clientNameZHCellData );

               // 回填客户英文名
               final CellData clientNameENCellData = new CellData();
               final ColumnVO clientNameENColumn = new ColumnVO();
               clientNameENColumn.setAccountId( "1" );
               clientNameENColumn.setIsDBColumn( "1" );
               clientNameENColumn.setNameDB( "clientNameEN" );
               clientNameENCellData.setColumnVO( clientNameENColumn );
               clientNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               clientNameENCellData.setDbValue( clientVO.getNameEN() );
               cellDataDTO.addCellData( clientNameENCellData );

               // 回填员工ID
               final CellData employeeCellData = new CellData();
               final ColumnVO employeeColumn = new ColumnVO();
               employeeColumn.setAccountId( "1" );
               employeeColumn.setIsDBColumn( "1" );
               employeeColumn.setNameDB( "employeeId" );
               employeeCellData.setColumnVO( employeeColumn );
               employeeCellData.setJdbcDataType( JDBCDataType.INT );
               employeeCellData.setDbValue( employeeContractVO.getEmployeeId() );
               cellDataDTO.addCellData( employeeCellData );

               // 回填员工中文名
               final CellData employeeNameZHCellDataVad = cellDataDTO.getCellDataByColumnNameDB( "employeeNameZH" );
               if ( employeeNameZHCellDataVad == null )
               {
                  final CellData employeeNameZHCellData = new CellData();
                  final ColumnVO employeeNameZHColumn = new ColumnVO();
                  employeeNameZHColumn.setAccountId( "1" );
                  employeeNameZHColumn.setIsDBColumn( "1" );
                  employeeNameZHColumn.setNameDB( "employeeNameZH" );
                  employeeNameZHCellData.setColumnVO( employeeNameZHColumn );
                  employeeNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                  employeeNameZHCellData.setDbValue( employeeVO.getNameZH() );
                  cellDataDTO.addCellData( employeeNameZHCellData );
               }

               // 回填员工英文名
               final CellData employeeNameENCellDataVad = cellDataDTO.getCellDataByColumnNameDB( "employeeNameEN" );
               if ( employeeNameENCellDataVad == null )
               {
                  final CellData employeeNameENCellData = new CellData();
                  final ColumnVO employeeNameENColumn = new ColumnVO();
                  employeeNameENColumn.setAccountId( "1" );
                  employeeNameENColumn.setIsDBColumn( "1" );
                  employeeNameENColumn.setNameDB( "employeeNameEN" );
                  employeeNameENCellData.setColumnVO( employeeNameENColumn );
                  employeeNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                  employeeNameENCellData.setDbValue( employeeVO.getNameEN() );
                  cellDataDTO.addCellData( employeeNameENCellData );
               }

               // 回填员工证件类型
               final CellData certificateTypeCellData = new CellData();
               final ColumnVO certificateTypeColumn = new ColumnVO();
               certificateTypeColumn.setAccountId( "1" );
               certificateTypeColumn.setIsDBColumn( "1" );
               certificateTypeColumn.setNameDB( "certificateType" );
               certificateTypeCellData.setColumnVO( certificateTypeColumn );
               certificateTypeCellData.setJdbcDataType( JDBCDataType.INT );
               certificateTypeCellData.setDbValue( employeeVO.getCertificateType() );
               cellDataDTO.addCellData( certificateTypeCellData );

               // 回填员工证件号码
               final CellData certificateNumberCellData = new CellData();
               final ColumnVO certificateNumberColumn = new ColumnVO();
               certificateNumberColumn.setAccountId( "1" );
               certificateNumberColumn.setIsDBColumn( "1" );
               certificateNumberColumn.setNameDB( "certificateNumber" );
               certificateNumberCellData.setColumnVO( certificateNumberColumn );
               certificateNumberCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               certificateNumberCellData.setDbValue( employeeVO.getCertificateNumber() );
               cellDataDTO.addCellData( certificateNumberCellData );

               // 回填银行编号
               final CellData bankIdCellData = new CellData();
               final ColumnVO bankIdColumn = new ColumnVO();
               bankIdColumn.setAccountId( "1" );
               bankIdColumn.setIsDBColumn( "1" );
               bankIdColumn.setNameDB( "bankId" );
               bankIdCellData.setColumnVO( bankIdColumn );
               bankIdCellData.setJdbcDataType( JDBCDataType.INT );
               bankIdCellData.setDbValue( employeeVO.getBankId() );
               cellDataDTO.addCellData( bankIdCellData );

               if ( bankVO != null )
               {
                  // 回填银行中文名
                  final CellData bankNameZHCellData = new CellData();
                  final ColumnVO bankNameZHColumn = new ColumnVO();
                  bankNameZHColumn.setAccountId( "1" );
                  bankNameZHColumn.setIsDBColumn( "1" );
                  bankNameZHColumn.setNameDB( "bankNameZH" );
                  bankNameZHCellData.setColumnVO( bankNameZHColumn );
                  bankNameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                  bankNameZHCellData.setDbValue( bankVO.getNameZH() );
                  cellDataDTO.addCellData( bankNameZHCellData );

                  // 回填银行英文名
                  final CellData bankNameENCellData = new CellData();
                  final ColumnVO bankNameENColumn = new ColumnVO();
                  bankNameENColumn.setAccountId( "1" );
                  bankNameENColumn.setIsDBColumn( "1" );
                  bankNameENColumn.setNameDB( "bankNameEN" );
                  bankNameENCellData.setColumnVO( bankNameENColumn );
                  bankNameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
                  bankNameENCellData.setDbValue( bankVO.getNameEN() );
                  cellDataDTO.addCellData( bankNameENCellData );
               }

               // 回填员工银行卡号
               final CellData bankAccountCellData = new CellData();
               final ColumnVO bankAccountColumn = new ColumnVO();
               bankAccountColumn.setAccountId( "1" );
               bankAccountColumn.setIsDBColumn( "1" );
               bankAccountColumn.setNameDB( "bankAccount" );
               bankAccountCellData.setColumnVO( bankAccountColumn );
               bankAccountCellData.setJdbcDataType( JDBCDataType.VARCHAR );
               bankAccountCellData.setDbValue( employeeVO.getBankAccount() );
               cellDataDTO.addCellData( bankAccountCellData );

               // 回填合计数
               final CellData billAmountCompanyCellData = new CellData();
               final ColumnVO billAmountCompanyColumn = new ColumnVO();
               billAmountCompanyColumn.setAccountId( "1" );
               billAmountCompanyColumn.setIsDBColumn( "1" );
               billAmountCompanyColumn.setNameDB( "billAmountCompany" );
               billAmountCompanyCellData.setColumnVO( billAmountCompanyColumn );
               billAmountCompanyCellData.setJdbcDataType( JDBCDataType.DOUBLE );
               billAmountCompanyCellData.setDbValue( billAmountCompany );
               cellDataDTO.addCellData( billAmountCompanyCellData );

               final CellData billAmountPersonalCellData = new CellData();
               final ColumnVO billAmountPersonalColumn = new ColumnVO();
               billAmountPersonalColumn.setAccountId( "1" );
               billAmountPersonalColumn.setIsDBColumn( "1" );
               billAmountPersonalColumn.setNameDB( "billAmountPersonal" );
               billAmountPersonalCellData.setColumnVO( billAmountPersonalColumn );
               billAmountPersonalCellData.setJdbcDataType( JDBCDataType.DOUBLE );
               billAmountPersonalCellData.setDbValue( billAmountPersonal );
               cellDataDTO.addCellData( billAmountPersonalCellData );

               final CellData costAmountCompanyCellData = new CellData();
               final ColumnVO costAmountCompanyColumn = new ColumnVO();
               costAmountCompanyColumn.setAccountId( "1" );
               costAmountCompanyColumn.setIsDBColumn( "1" );
               costAmountCompanyColumn.setNameDB( "costAmountCompany" );
               costAmountCompanyCellData.setColumnVO( costAmountCompanyColumn );
               costAmountCompanyCellData.setJdbcDataType( JDBCDataType.DOUBLE );
               costAmountCompanyCellData.setDbValue( costAmountCompany );
               cellDataDTO.addCellData( costAmountCompanyCellData );

               final CellData costAmountPersonalCellData = new CellData();
               final ColumnVO costAmountPersonalColumn = new ColumnVO();
               costAmountPersonalColumn.setAccountId( "1" );
               costAmountPersonalColumn.setIsDBColumn( "1" );
               costAmountPersonalColumn.setNameDB( "costAmountPersonal" );
               costAmountPersonalCellData.setColumnVO( costAmountPersonalColumn );
               costAmountPersonalCellData.setJdbcDataType( JDBCDataType.DOUBLE );
               costAmountPersonalCellData.setDbValue( costAmountPersonal );
               cellDataDTO.addCellData( costAmountPersonalCellData );
            }
         }
      }
      //放入 报错 信息 
      if ( !flag && KANUtil.filterEmpty( erroMessange ) != null )
      {
         cellDataDTOs.get( 0 ).getCellDatas().get( 0 ).setErrorMessange( erroMessange );
      }

      return flag;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importDatas, String batchId )
   {
      boolean flag = true;

      if ( KANUtil.filterEmpty( batchId ) != null )
      {
         // 初始化Amount
         //         double billAmountCompany = 0;
         //         double billAmountPersonal = 0;
         double costAmountCompany = 0;
         double costAmountPersonal = 0;

         // 遍历 importData 查找子表数据
         for ( CellDataDTO cellDataDTO : importDatas )
         {
            final List< CellDataDTO > subCellDataDTOs = cellDataDTO.getSubCellDataDTOs();

            if ( subCellDataDTOs != null && subCellDataDTOs.size() > 0 )
            {
               for ( CellDataDTO subCellDataDTO : subCellDataDTOs )
               {

                  // 合计叠加
                  //                  if ( subCellDataDTO.getCellDataByColumnNameDB( "billAmountPersonal" ) != null
                  //                        && subCellDataDTO.getCellDataByColumnNameDB( "billAmountPersonal" ).getDbValue() != null )
                  //                  {
                  //                     billAmountPersonal += Double.parseDouble( String.valueOf( subCellDataDTO.getCellDataByColumnNameDB( "billAmountPersonal" ).getDbValue() ) );
                  //                  }

                  if ( subCellDataDTO.getCellDataByColumnNameDB( "costAmountCompany" ) != null
                        && subCellDataDTO.getCellDataByColumnNameDB( "costAmountCompany" ).getDbValue() != null )
                  {
                     costAmountCompany += Double.parseDouble( String.valueOf( subCellDataDTO.getCellDataByColumnNameDB( "costAmountCompany" ).getDbValue() ) );
                  }

                  if ( subCellDataDTO.getCellDataByColumnNameDB( "costAmountPersonal" ) != null
                        && subCellDataDTO.getCellDataByColumnNameDB( "costAmountPersonal" ).getDbValue() != null )
                  {
                     costAmountPersonal += Double.parseDouble( String.valueOf( subCellDataDTO.getCellDataByColumnNameDB( "costAmountPersonal" ).getDbValue() ) );
                  }

               }
            }

         }

         if ( batchId != null )
         {
            try
            {
               final CommonBatchVO commonBatchVO = this.commonBatchDao.getCommonBatchVOByBatchId( batchId );

               if ( commonBatchVO != null )
               {
                  commonBatchVO.setRemark2( "合计：" + commonBatchVO.formatNumber( String.valueOf( costAmountCompany + costAmountPersonal ) ) );
               }

               this.commonBatchDao.updateCommonBatch( commonBatchVO );

               // 加密
               this.salaryHeaderDao.updateSalaryHeaderAfterImport( batchId );
               this.salaryDetailDao.updateSalaryDetailAfterImport( batchId );
            }
            catch ( KANException e )
            {
               e.printStackTrace();
            }
         }

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

   @Override
   public void init( List< CellDataDTO > importData )
   {
      //初始化
   }

   // Reviewed by Kevin Jin at 2014-05-14
   /*private void extendItems( final CellDataDTO subCellDataDTO, final ItemVO itemVO )
   {
      // 扩充外键
      final CellData salaryHeaderIdCellData = new CellData();
      final ColumnVO salaryHeaderIdColumn = new ColumnVO();
      salaryHeaderIdColumn.setAccountId( "1" );
      salaryHeaderIdColumn.setIsDBColumn( "1" );
      salaryHeaderIdColumn.setNameDB( "salaryHeaderId" );
      salaryHeaderIdColumn.setIsForeignKey( "1" );
      salaryHeaderIdCellData.setColumnVO( salaryHeaderIdColumn );
      salaryHeaderIdCellData.setJdbcDataType( JDBCDataType.INT );

      if ( !subCellDataDTO.containColumnByNameDB( salaryHeaderIdColumn ) )
      {
         subCellDataDTO.getCellDatas().add( salaryHeaderIdCellData );
      }

      // 扩充ItemId
      final CellData itemCellData = new CellData();
      final ColumnVO itemColumn = new ColumnVO();
      itemColumn.setAccountId( "1" );
      itemColumn.setIsDBColumn( "1" );
      itemColumn.setNameDB( "itemId" );
      itemCellData.setColumnVO( itemColumn );
      itemCellData.setJdbcDataType( JDBCDataType.INT );
      itemCellData.setDbValue( itemVO.getItemId() );

      if ( !subCellDataDTO.containColumnByNameDB( itemColumn ) )
      {
         subCellDataDTO.getCellDatas().add( itemCellData );
      }

      // 扩充ItemNo
      final CellData itemNoCellData = new CellData();
      final ColumnVO itemNoColumn = new ColumnVO();
      itemNoColumn.setAccountId( "1" );
      itemNoColumn.setIsDBColumn( "1" );
      itemNoColumn.setNameDB( "itemNo" );
      itemNoCellData.setColumnVO( itemNoColumn );
      itemNoCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      itemNoCellData.setDbValue( itemVO.getItemNo() );

      if ( !subCellDataDTO.containColumnByNameDB( itemNoColumn ) )
      {
         subCellDataDTO.getCellDatas().add( itemNoCellData );
      }

      // 扩充Item Name ZH
      final CellData nameZHCellData = new CellData();
      final ColumnVO nameZHColumn = new ColumnVO();
      nameZHColumn.setAccountId( "1" );
      nameZHColumn.setIsDBColumn( "1" );
      nameZHColumn.setNameDB( "nameZH" );
      nameZHCellData.setColumnVO( nameZHColumn );
      nameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      nameZHCellData.setDbValue( itemVO.getNameZH() );

      if ( !subCellDataDTO.containColumnByNameDB( nameZHColumn ) )
      {
         subCellDataDTO.getCellDatas().add( nameZHCellData );
      }

      // 扩充Item Name EN
      final CellData nameENCellData = new CellData();
      final ColumnVO nameENColumn = new ColumnVO();
      nameENColumn.setAccountId( "1" );
      nameENColumn.setIsDBColumn( "1" );
      nameENColumn.setNameDB( "nameEN" );
      nameENCellData.setColumnVO( nameENColumn );
      nameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      nameENCellData.setDbValue( itemVO.getNameEN() );

      if ( !subCellDataDTO.containColumnByNameDB( nameENColumn ) )
      {
         subCellDataDTO.getCellDatas().add( nameENCellData );
      }
   }*/

   private void extendItems( final List< CellData > cellDatas, final ItemVO itemVO )
   {
      // 扩充外键
      final CellData salaryHeaderIdCellData = new CellData();
      final ColumnVO salaryHeaderIdColumn = new ColumnVO();
      salaryHeaderIdColumn.setAccountId( "1" );
      salaryHeaderIdColumn.setIsDBColumn( "1" );
      salaryHeaderIdColumn.setNameDB( "salaryHeaderId" );
      salaryHeaderIdColumn.setIsForeignKey( "1" );
      salaryHeaderIdCellData.setColumnVO( salaryHeaderIdColumn );
      salaryHeaderIdCellData.setJdbcDataType( JDBCDataType.INT );

      cellDatas.add( salaryHeaderIdCellData );

      // 扩充ItemId
      final CellData itemCellData = new CellData();
      final ColumnVO itemColumn = new ColumnVO();
      itemColumn.setAccountId( "1" );
      itemColumn.setIsDBColumn( "1" );
      itemColumn.setNameDB( "itemId" );
      itemCellData.setColumnVO( itemColumn );
      itemCellData.setJdbcDataType( JDBCDataType.INT );
      itemCellData.setDbValue( itemVO.getItemId() );

      cellDatas.add( itemCellData );

      // 扩充ItemNo
      final CellData itemNoCellData = new CellData();
      final ColumnVO itemNoColumn = new ColumnVO();
      itemNoColumn.setAccountId( "1" );
      itemNoColumn.setIsDBColumn( "1" );
      itemNoColumn.setNameDB( "itemNo" );
      itemNoCellData.setColumnVO( itemNoColumn );
      itemNoCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      itemNoCellData.setDbValue( itemVO.getItemNo() );

      cellDatas.add( itemNoCellData );

      // 扩充Item Name ZH
      final CellData nameZHCellData = new CellData();
      final ColumnVO nameZHColumn = new ColumnVO();
      nameZHColumn.setAccountId( "1" );
      nameZHColumn.setIsDBColumn( "1" );
      nameZHColumn.setNameDB( "nameZH" );
      nameZHCellData.setColumnVO( nameZHColumn );
      nameZHCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      nameZHCellData.setDbValue( itemVO.getNameZH() );

      cellDatas.add( nameZHCellData );

      // 扩充Item Name EN
      final CellData nameENCellData = new CellData();
      final ColumnVO nameENColumn = new ColumnVO();
      nameENColumn.setAccountId( "1" );
      nameENColumn.setIsDBColumn( "1" );
      nameENColumn.setNameDB( "nameEN" );
      nameENCellData.setColumnVO( nameENColumn );
      nameENCellData.setJdbcDataType( JDBCDataType.VARCHAR );
      nameENCellData.setDbValue( itemVO.getNameEN() );
      cellDatas.add( nameENCellData );

   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public final ClientDao getClientDao()
   {
      return clientDao;
   }

   public final void setClientDao( ClientDao clientDao )
   {
      this.clientDao = clientDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }

   public SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   public SalaryDetailDao getSalaryDetailDao()
   {
      return salaryDetailDao;
   }

   public void setSalaryDetailDao( SalaryDetailDao salaryDetailDao )
   {
      this.salaryDetailDao = salaryDetailDao;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      if ( importData != null )
      {
         final Iterator< CellDataDTO > iterator = importData.iterator();

         while ( iterator.hasNext() )
         {
            final CellDataDTO cellDataDTO = iterator.next();
            final List< CellData > cells = cellDataDTO.getCellDatas();
            Boolean retain = false;

            for ( CellData cellData : cells )
            {
               if ( "billAmountCompany".equals( cellData.getColumnVO().getNameDB() ) || "billAmountPersonal".equals( cellData.getColumnVO().getNameDB() )
                     || "costAmountCompany".equals( cellData.getColumnVO().getNameDB() ) || "costAmountPersonal".equals( cellData.getColumnVO().getNameDB() )
                     || "taxAmountPersonal".equals( cellData.getColumnVO().getNameDB() ) || "addtionalBillAmountPersonal".equals( cellData.getColumnVO().getNameDB() )
                     || "actualSalary".equals( cellData.getColumnVO().getNameDB() ) || "estimateSalary".equals( cellData.getColumnVO().getNameDB() ) )
               {
                  if ( KANUtil.filterEmpty( cellData.getDbValue() ) != null && Double.valueOf( cellData.getDbValue().toString() ) != 0 )
                  {
                     retain = true;
                     break;
                  }
               }
            }

            if ( !retain )
            {
               iterator.remove();
            }
         }
      }

      return false;
   }

}
