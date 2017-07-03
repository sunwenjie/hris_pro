package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.payment.PaymentAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;

public class PaymentAdjustmentHandlerImp extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   EmployeeContractDao employeeContractDao;

   EmployeeContractSalaryDao employeeContractSalaryDao;

   ClientOrderHeaderDao clientOrderHeaderDao;

   PaymentHeaderDao paymentHeaderDao;

   PaymentAdjustmentImportBatchDao paymentAdjustmentImportBatchDao;

   private List< MappingVO > itemList;

   private Map< String, CellDataDTO > payMap = null;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }

   public PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public PaymentAdjustmentImportBatchDao getPaymentAdjustmentImportBatchDao()
   {
      return paymentAdjustmentImportBatchDao;
   }

   public void setPaymentAdjustmentImportBatchDao( PaymentAdjustmentImportBatchDao paymentAdjustmentImportBatchDao )
   {
      this.paymentAdjustmentImportBatchDao = paymentAdjustmentImportBatchDao;
   }

   @Override
   public void init( List< CellDataDTO > importData )
   {
      payMap = new HashMap< String, CellDataDTO >();
      //更换数据库表为临时表。不需要临时表的不需要这步操作。
      if ( importData != null )
      {
         for ( CellDataDTO cellDataDTO : importData )
         {
            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );
            if ( cellDataDTO.getSubCellDataDTOs() == null )
               continue;
            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
            {
               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
            }
         }
      }
   }

   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {

      boolean result = true;

      // query condition
      final PaymentAdjustmentHeaderVO condition = new PaymentAdjustmentHeaderVO();
      // 补充excel中没有的列到数据库
      List< CellData > rowCellDatas = new ArrayList< CellData >();
      try
      {
         final String accountId = BaseAction.getAccountId( request, null );
         final String corpId = BaseAction.getCorpId( request, null );
         final String userId = BaseAction.getUserId( request, null );
         itemList = KANConstants.getKANAccountConstants( accountId ).getSalaryItems( "ZH" );

         final Iterator< CellDataDTO > iterators = importData.iterator();

         while ( iterators.hasNext() )
         {

            // reset query condition
            rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;

            // itemID 验证加班项目
            boolean itemError = false;
            String itemNameZH = "";
            String itemNameEN = "";

            // rowNumber
            int rowNumber = 1;

            //调整月
            String monthly = "";

            CellDataDTO row = iterators.next();

            if ( row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {

               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               row.setErrorMessange( row.getErrorMessange() + "单元格[" );
               for ( int i = 0; i < row.getCellDatas().size(); i++ )
               {
                  CellData cell = row.getCellDatas().get( i );

                  if ( cell.getColumnVO() != null && "accountId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setAccountId( ( String ) cell.getDbValue() );
                  }

                  if ( StringUtils.isBlank( cell.getCellRef() ) )
                  {
                     continue;
                  }

                  if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setContractId( cell.getValue() );
                     cell.setDbValue( cell.getValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }

                  if ( cell.getColumnVO() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     monthly = cell.getDbValue().toString();
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }

               }

               for ( CellDataDTO cellDataDTO : row.getSubCellDataDTOs() )
               {
                  for ( CellData cell : cellDataDTO.getCellDatas() )
                  {
                     if ( cell.getColumnVO() != null && "billAmountPersonal".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        condition.setBillAmountPersonal( ( String ) cell.getDbValue() );
                     }

                     if ( cell.getColumnVO() != null && "costAmountPersonal".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        condition.setCostAmountPersonal( ( String ) cell.getDbValue() );
                     }

                     if ( cell.getColumnVO() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        monthly = cell.getDbValue().toString();

                     }

                     if ( cell.getColumnVO() != null && "itemId".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        if ( StringUtils.isNotBlank( cell.getValue() ) )
                        {
                           for ( MappingVO item : itemList )
                           {
                              if ( item.getMappingValue().equals( cell.getValue().trim() ) )
                              {
                                 cell.setDbValue( item.getMappingId() );
                                 itemNameZH = item.getMappingValue();
                                 itemNameEN = item.getMappingTemp();
                                 itemError = true;
                                 break;

                              }
                           }
                        }
                     }
                  }

                  if ( !itemError )
                  {
                     row.setErrorMessange( rowNumber + "行,科目不存在,请检查后再上传。" );
                     result = false;
                     mainHasError = true;
                     continue;
                  }

                  addSubTableColumn( cellDataDTO.getCellDatas(), condition, itemNameZH, itemNameEN );
               }

               EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               if ( employeeContractVO == null )
               {
                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议不正确,请检查后再上传。" );
                  result = false;
                  mainHasError = true;
                  continue;
               }

               //批准、盖章、归档、结束（结束时间或离职时间没超过也行）
               String[] status = { "3", "5", "6", "7" };
               if ( !Arrays.asList( status ).contains( employeeContractVO.getStatus() ) )
               {

                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议未生效,请检查后再上传。" );
                  result = false;
                  mainHasError = true;
                  continue;
               }

               // 搜索工资结算数据
               final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
               paymentHeaderVO.setAccountId( accountId );
               paymentHeaderVO.setCorpId( corpId );
               paymentHeaderVO.setEmployeeId( employeeContractVO.getEmployeeId() );
               paymentHeaderVO.setMonthly( monthly );
               // 新建状态数据
               paymentHeaderVO.setStatus( "1" );
               final List< Object > paymentHeaderVOs = paymentHeaderDao.getPaymentHeaderVOsByCondition( paymentHeaderVO );

               if ( paymentHeaderVOs == null || paymentHeaderVOs.size() == 0 )
               {
                  // 获取EmployeeContractVO
                  if ( KANUtil.filterEmpty( employeeContractVO.getContractId() ) != null )
                  {

                     condition.setOrderId( employeeContractVO.getOrderId() );
//                     condition.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
                     condition.setEntityId( employeeContractVO.getEntityId() );
                     condition.setBranch( employeeContractVO.getBranch() );
                     condition.setOwner( employeeContractVO.getOwner() );
                  }

                  condition.setAccountId( accountId );
                  condition.setCreateBy( userId );
                  condition.setModifyBy( userId );

               }
               else
               {
                  row.setErrorMessange( rowNumber + "行, 工资调整不符合条件。请先提交" + employeeContractVO.getEmployeeNameZH() + "的工资结算数据！" );
                  result = false;
                  mainHasError = true;
                  continue;
               }

               if ( !mainHasError )
               {
                  row.setErrorMessange( null );
                  if ( payMap.containsKey( condition.getContractId() + monthly ) )
                  {
                     payMap.get( condition.getContractId() + monthly ).getSubCellDataDTOs().addAll( row.getSubCellDataDTOs() );
                     iterators.remove();
                  }
                  else
                  {
                     // 补全要保存的数据
                     addMainTableColumn( rowCellDatas, employeeContractVO, condition );
                     row.getCellDatas().addAll( rowCellDatas );
                     payMap.put( condition.getContractId() + monthly, row );
                  }

               }
               else
               {
                  result = false;
               }

            }

         }

      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return result;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      paymentAdjustmentImportBatchDao.updatePaymentAdjustmentImportHeaderBybatchId( batchId );
      return false;
   }

   /**
    * 补全主表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addMainTableColumn( List< CellData > rowCellDatas, EmployeeContractVO employeeContractVO, PaymentAdjustmentHeaderVO condition )
   {

      CellData headerId = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "adjustmentHeaderId" );
      headerIdColumn.setAccountId( "1" );
      headerIdColumn.setIsDBColumn( "1" );
      headerIdColumn.setIsPrimaryKey( "1" );
      headerId.setColumnVO( headerIdColumn );
      headerId.setJdbcDataType( JDBCDataType.INT );
      rowCellDatas.add( headerId );

      CellData status = new CellData();
      ColumnVO statusColumn = new ColumnVO();
      statusColumn.setNameDB( "status" );
      statusColumn.setAccountId( "1" );
      statusColumn.setIsDBColumn( "1" );
      status.setColumnVO( statusColumn );
      status.setDbValue( "1" );
      status.setJdbcDataType( JDBCDataType.INT );
      rowCellDatas.add( status );

      CellData clientIdCell = new CellData();
      ColumnVO clientIdColumn = new ColumnVO();
      clientIdColumn.setAccountId( "1" );
      clientIdColumn.setIsDBColumn( "1" );
      clientIdColumn.setNameDB( "clientId" );
      clientIdCell.setColumnVO( clientIdColumn );
      clientIdCell.setJdbcDataType( JDBCDataType.INT );
      clientIdCell.setDbValue( employeeContractVO.getClientId() );
      rowCellDatas.add( clientIdCell );

      CellData entityIdCell = new CellData();
      ColumnVO entityIdColumn = new ColumnVO();
      entityIdColumn.setAccountId( "1" );
      entityIdColumn.setIsDBColumn( "1" );
      entityIdColumn.setNameDB( "entityId" );
      entityIdCell.setColumnVO( entityIdColumn );
      entityIdCell.setJdbcDataType( JDBCDataType.INT );
      entityIdCell.setDbValue( employeeContractVO.getEntityId() );
      rowCellDatas.add( entityIdCell );

/*    CellData businessTypeIdCell = new CellData();
      ColumnVO businessTypeIdColumn = new ColumnVO();
      businessTypeIdColumn.setAccountId( "1" );
      businessTypeIdColumn.setIsDBColumn( "1" );
      businessTypeIdColumn.setNameDB( "businessTypeId" );
      businessTypeIdCell.setColumnVO( businessTypeIdColumn );
      businessTypeIdCell.setJdbcDataType( JDBCDataType.INT );
      businessTypeIdCell.setDbValue( employeeContractVO.getBusinessTypeId() );
      rowCellDatas.add( businessTypeIdCell );*/

      CellData employeeIdCell = new CellData();
      ColumnVO employeeIdColumn = new ColumnVO();
      employeeIdColumn.setAccountId( "1" );
      employeeIdColumn.setIsDBColumn( "1" );
      employeeIdColumn.setNameDB( "employeeId" );
      employeeIdCell.setColumnVO( employeeIdColumn );
      employeeIdCell.setJdbcDataType( JDBCDataType.INT );
      employeeIdCell.setDbValue( employeeContractVO.getEmployeeId() );
      rowCellDatas.add( employeeIdCell );

      CellData employeeNameZHCell = new CellData();
      ColumnVO employeeNameZHColumn = new ColumnVO();
      employeeNameZHColumn.setAccountId( "1" );
      employeeNameZHColumn.setIsDBColumn( "1" );
      employeeNameZHColumn.setNameDB( "employeeNameZH" );
      employeeNameZHCell.setColumnVO( employeeNameZHColumn );
      employeeNameZHCell.setJdbcDataType( JDBCDataType.VARCHAR );
      employeeNameZHCell.setDbValue( employeeContractVO.getEmployeeNameZH() );
      rowCellDatas.add( employeeNameZHCell );

      CellData employeeNameENCell = new CellData();
      ColumnVO employeeNameENColumn = new ColumnVO();
      employeeNameENColumn.setAccountId( "1" );
      employeeNameENColumn.setIsDBColumn( "1" );
      employeeNameENColumn.setNameDB( "employeeNameEN" );
      employeeNameENCell.setColumnVO( employeeNameENColumn );
      employeeNameENCell.setJdbcDataType( JDBCDataType.VARCHAR );
      employeeNameENCell.setDbValue( employeeContractVO.getEmployeeNameEN() );
      rowCellDatas.add( employeeNameENCell );

      CellData orderIdCell = new CellData();
      ColumnVO orderIdColumn = new ColumnVO();
      orderIdColumn.setAccountId( "1" );
      orderIdColumn.setIsDBColumn( "1" );
      orderIdColumn.setNameDB( "orderId" );
      orderIdCell.setColumnVO( orderIdColumn );
      orderIdCell.setJdbcDataType( JDBCDataType.INT );
      orderIdCell.setDbValue( employeeContractVO.getOrderId() );
      rowCellDatas.add( orderIdCell );

      CellData billAmountPersonalCell = new CellData();
      ColumnVO billAmountPersonalColumn = new ColumnVO();
      billAmountPersonalColumn.setAccountId( "1" );
      billAmountPersonalColumn.setIsDBColumn( "1" );
      billAmountPersonalColumn.setNameDB( "billAmountPersonal" );
      billAmountPersonalCell.setColumnVO( billAmountPersonalColumn );
      billAmountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      billAmountPersonalCell.setDbValue( condition.getBillAmountPersonal() );
      rowCellDatas.add( billAmountPersonalCell );

      CellData costAmountPersonalCell = new CellData();
      ColumnVO costAmountPersonalColumn = new ColumnVO();
      costAmountPersonalColumn.setAccountId( "1" );
      costAmountPersonalColumn.setIsDBColumn( "1" );
      costAmountPersonalColumn.setNameDB( "costAmountPersonal" );
      costAmountPersonalCell.setColumnVO( costAmountPersonalColumn );
      costAmountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      costAmountPersonalCell.setDbValue( condition.getCostAmountPersonal() );
      rowCellDatas.add( costAmountPersonalCell );

      CellData taxAmountPersonalCell = new CellData();
      ColumnVO taxAmountPersonalColumn = new ColumnVO();
      taxAmountPersonalColumn.setAccountId( "1" );
      taxAmountPersonalColumn.setIsDBColumn( "1" );
      taxAmountPersonalColumn.setNameDB( "taxAmountPersonal" );
      taxAmountPersonalCell.setColumnVO( taxAmountPersonalColumn );
      taxAmountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      taxAmountPersonalCell.setDbValue( "0" );
      rowCellDatas.add( taxAmountPersonalCell );

      CellData addtionalBillAmountPersonalCell = new CellData();
      ColumnVO addtionalBillAmountPersonalColumn = new ColumnVO();
      addtionalBillAmountPersonalColumn.setAccountId( "1" );
      addtionalBillAmountPersonalColumn.setIsDBColumn( "1" );
      addtionalBillAmountPersonalColumn.setNameDB( "addtionalBillAmountPersonal" );
      addtionalBillAmountPersonalCell.setColumnVO( addtionalBillAmountPersonalColumn );
      addtionalBillAmountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      addtionalBillAmountPersonalCell.setDbValue( "0" );
      rowCellDatas.add( addtionalBillAmountPersonalCell );

   }

   /**
    * 补全从表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addSubTableColumn( List< CellData > cellDatas, PaymentAdjustmentHeaderVO condition, String itemNameZH, String itemNameEN )
   {

      CellData headerId = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "adjustmentHeaderId" );
      headerIdColumn.setAccountId( "1" );
      headerIdColumn.setIsDBColumn( "1" );
      headerIdColumn.setIsForeignKey( "1" );
      headerId.setColumnVO( headerIdColumn );
      headerId.setJdbcDataType( JDBCDataType.INT );
      cellDatas.add( headerId );

      CellData status = new CellData();
      ColumnVO statusColumn = new ColumnVO();
      statusColumn.setNameDB( "status" );
      statusColumn.setAccountId( "1" );
      statusColumn.setIsDBColumn( "1" );
      status.setColumnVO( statusColumn );
      status.setDbValue( "1" );
      status.setJdbcDataType( JDBCDataType.INT );
      cellDatas.add( status );

      CellData itemNameZHCell = new CellData();
      ColumnVO itemNameZHColumn = new ColumnVO();
      itemNameZHColumn.setNameDB( "nameZH" );
      itemNameZHColumn.setAccountId( "1" );
      itemNameZHColumn.setIsDBColumn( "1" );
      itemNameZHCell.setColumnVO( itemNameZHColumn );
      itemNameZHCell.setDbValue( itemNameZH );
      itemNameZHCell.setJdbcDataType( JDBCDataType.VARCHAR );
      cellDatas.add( itemNameZHCell );

      CellData itemNameENCell = new CellData();
      ColumnVO itemNameENColumn = new ColumnVO();
      itemNameENColumn.setNameDB( "nameEN" );
      itemNameENColumn.setAccountId( "1" );
      itemNameENColumn.setIsDBColumn( "1" );
      itemNameENCell.setColumnVO( itemNameENColumn );
      itemNameENCell.setDbValue( itemNameEN );
      itemNameENCell.setJdbcDataType( JDBCDataType.VARCHAR );
      cellDatas.add( itemNameENCell );

      CellData taxAmountPersonalCell = new CellData();
      ColumnVO taxAmountPersonalColumn = new ColumnVO();
      taxAmountPersonalColumn.setNameDB( "taxAmountPersonal" );
      taxAmountPersonalColumn.setAccountId( "1" );
      taxAmountPersonalColumn.setIsDBColumn( "1" );
      taxAmountPersonalCell.setColumnVO( taxAmountPersonalColumn );
      taxAmountPersonalCell.setDbValue( condition.getTaxAmountPersonal() );
      taxAmountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      cellDatas.add( taxAmountPersonalCell );

   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

}
