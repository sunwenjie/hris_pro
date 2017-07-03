package com.kan.hro.service.impl.biz.sb;

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
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.client.ClientOrderHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderSBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSBDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportBatchDao;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;

public class SBAdjustmentHandlerImp extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   EmployeeContractDao employeeContractDao;

   EmployeeContractSBDao employeeContractSBDao;

   ClientOrderSBDao clientOrderSBDao;

   ClientOrderHeaderDao clientOrderHeaderDao;

   SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao;

   private List< MappingVO > itemList;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractSBDao getEmployeeContractSBDao()
   {
      return employeeContractSBDao;
   }

   public void setEmployeeContractSBDao( EmployeeContractSBDao employeeContractSBDao )
   {
      this.employeeContractSBDao = employeeContractSBDao;
   }

   public ClientOrderSBDao getClientOrderSBDao()
   {
      return clientOrderSBDao;
   }

   public void setClientOrderSBDao( ClientOrderSBDao clientOrderSBDao )
   {
      this.clientOrderSBDao = clientOrderSBDao;
   }

   public ClientOrderHeaderDao getClientOrderHeaderDao()
   {
      return clientOrderHeaderDao;
   }

   public void setClientOrderHeaderDao( ClientOrderHeaderDao clientOrderHeaderDao )
   {
      this.clientOrderHeaderDao = clientOrderHeaderDao;
   }



   public SBAdjustmentImportBatchDao getSbAdjustmentImportBatchDao()
   {
      return sbAdjustmentImportBatchDao;
   }

   public void setSbAdjustmentImportBatchDao( SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao )
   {
      this.sbAdjustmentImportBatchDao = sbAdjustmentImportBatchDao;
   }



   private Map< String, CellDataDTO > SBMap = null;

   @Override
   public void init( List< CellDataDTO > importData )
   {

      SBMap = new HashMap< String, CellDataDTO >();

      // TODO Auto-generated method stub
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
      final SBAdjustmentHeaderVO condition = new SBAdjustmentHeaderVO();
      // 补充excel中没有的列到数据库
      List< CellData > rowCellDatas = new ArrayList< CellData >();
      try
      {
         final String accountId = BaseAction.getAccountId( request, null );
         final String corpId = BaseAction.getCorpId( request, null );
         itemList = KANConstants.getKANAccountConstants( accountId ).getItemsByType( "7", "ZH", corpId );

         final Iterator< CellDataDTO > iterators = importData.iterator();

         while ( iterators.hasNext() )
         {

            // reset query condition
            rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;

            // itemID 验证加班项目
            String sbSolutionId = "0";
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
                     condition.setMonthly( cell.getDbValue().toString() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }
                  if ( cell.getColumnVO() != null && "employeeSBId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        sbSolutionId = cell.getValue().trim();
                        cell.getColumnVO().setIsDBColumn( "2" );

                     }

                  }

               }

               //格式化子表数据
               for ( CellDataDTO cellDataDTO : row.getSubCellDataDTOs() )
               {
                  for ( CellData cell : cellDataDTO.getCellDatas() )
                  {
                     if ( cell.getColumnVO() != null && "amountPersonal".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        condition.setAmountPersonal( ( String ) cell.getDbValue() );
                     }

                     if ( cell.getColumnVO() != null && "amountCompany".equals( cell.getColumnVO().getNameDB() ) )
                     {
                        condition.setAmountCompany( ( String ) cell.getDbValue() );
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

               final SocialBenefitSolutionDTO socialBenefitSolutionDTO = KANConstants.getKANAccountConstants( accountId ).getSocialBenefitSolutionDTOByName( sbSolutionId, corpId );

               if ( socialBenefitSolutionDTO == null || socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO() == null )
               {
                  row.setErrorMessange( rowNumber + "行,社保方案不存在,请检查后再上传。" );
                  result = false;
                  mainHasError = true;
                  continue;
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

               // 初始化EmployeeContractSBVO
               // 初始化社保个人部分公司承担
               String personalSBBurden = "0";

               final List< Object > employeeContractSBVOs = employeeContractSBDao.getEmployeeContractSBVOsByContractId( condition.getContractId() );

               if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
               {
                  // 遍历
                  for ( Object employeeContractSBVOObject : employeeContractSBVOs )
                  {
                     EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;

                     if ( socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getHeaderId().equals( employeeContractSBVO.getSbSolutionId() ) )
                     {
                        condition.setEmployeeSBId( employeeContractSBVO.getEmployeeSBId() );
                        condition.setSbSolutionId( employeeContractSBVO.getSbSolutionId() );
                        condition.setVendorId( employeeContractSBVO.getVendorId() );
                        condition.setVendorNameZH( employeeContractSBVO.getVendorNameZH() );
                        condition.setVendorNameEN( employeeContractSBVO.getVendorNameEN() );
                        // 初始化社保个人部分公司承担
                        personalSBBurden = employeeContractSBVO.getPersonalSBBurden();

                        itemError = true;
                        continue;
                     }
                  }

                  if ( !itemError )
                  {

                     row.setErrorMessange( rowNumber + "行,劳动合同/服务协议无社保信息,请检查后再上传。" );
                     result = false;
                     mainHasError = true;
                     continue;
                  }

               }
               else
               {
                  row.setErrorMessange( rowNumber + "行,劳动合同/服务协议无社保信息,请检查后再上传。" );
                  result = false;
                  mainHasError = true;
                  continue;

               }

               // 尝试从订单社保方案中获取
               if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
               {
                  // 获取ClientOrderSBVO列表
                  final List< Object > clientOrderSBVOs = clientOrderSBDao.getClientOrderSBVOsByClientOrderHeaderId( employeeContractVO.getOrderId() );

                  if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
                  {
                     for ( Object clientOrderSBVOObject : clientOrderSBVOs )
                     {
                        // 获取ClientOrderSBVO
                        final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOObject;

                        if ( clientOrderSBVO != null && clientOrderSBVO.getSbSolutionId().equals( condition.getSbSolutionId() ) )
                        {
                           personalSBBurden = clientOrderSBVO.getPersonalSBBurden();
                        }
                     }
                  }
               }

               // 尝试从订单中获取

               if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
               {
                  final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderDao.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
                  personalSBBurden = clientOrderHeaderVO.getPersonalSBBurden();
               }

               // 尝试从社保方案中获取
               if ( KANUtil.filterEmpty( personalSBBurden, "0" ) == null )
               {
                  personalSBBurden = socialBenefitSolutionDTO.getSocialBenefitSolutionHeaderVO().getPersonalSBBurden();
               }

               if ( !mainHasError )
               {
                  row.setErrorMessange( null );
                  if ( SBMap.containsKey( condition.getContractId() + monthly + condition.getSbSolutionId() ) )
                  {
                     SBMap.get( condition.getContractId() + monthly + condition.getSbSolutionId() ).getSubCellDataDTOs().addAll( row.getSubCellDataDTOs() );
                     iterators.remove();
                  }
                  else
                  {
                     // 补全要保存的数据
                     addMainTableColumn( rowCellDatas, employeeContractVO, condition, personalSBBurden );
                     row.getCellDatas().addAll( rowCellDatas );
                     SBMap.put( condition.getContractId() + monthly + condition.getSbSolutionId(), row );
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
      // TODO Auto-generated method stub
      sbAdjustmentImportBatchDao.updateSBAdjustmentImportHeaderBybatchId( batchId );
      return false;
   }

   /**
    * 补全主表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addMainTableColumn( List< CellData > rowCellDatas, EmployeeContractVO employeeContractVO, SBAdjustmentHeaderVO condition, String personalSBBurden )
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

     /* CellData businessTypeIdCell = new CellData();
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

      CellData vendorIdCell = new CellData();
      ColumnVO vendorIdColumn = new ColumnVO();
      vendorIdColumn.setAccountId( "1" );
      vendorIdColumn.setIsDBColumn( "1" );
      vendorIdColumn.setNameDB( "vendorId" );
      vendorIdCell.setColumnVO( vendorIdColumn );
      vendorIdCell.setJdbcDataType( JDBCDataType.INT );
      vendorIdCell.setDbValue( condition.getVendorId() );
      rowCellDatas.add( vendorIdCell );

      CellData vendorNameZHCell = new CellData();
      ColumnVO vendorNameZHColumn = new ColumnVO();
      vendorNameZHColumn.setAccountId( "1" );
      vendorNameZHColumn.setIsDBColumn( "1" );
      vendorNameZHColumn.setNameDB( "vendorNameZH" );
      vendorNameZHCell.setColumnVO( vendorNameZHColumn );
      vendorNameZHCell.setJdbcDataType( JDBCDataType.VARCHAR );
      vendorNameZHCell.setDbValue( condition.getVendorNameZH() );
      rowCellDatas.add( vendorNameZHCell );

      CellData vendorNameENCell = new CellData();
      ColumnVO vendorNameENColumn = new ColumnVO();
      vendorNameENColumn.setAccountId( "1" );
      vendorNameENColumn.setIsDBColumn( "1" );
      vendorNameENColumn.setNameDB( "vendorNameEN" );
      vendorNameENCell.setColumnVO( vendorNameENColumn );
      vendorNameENCell.setJdbcDataType( JDBCDataType.VARCHAR );
      vendorNameENCell.setDbValue( condition.getVendorNameEN() );
      rowCellDatas.add( vendorNameENCell );

      CellData orderIdCell = new CellData();
      ColumnVO orderIdColumn = new ColumnVO();
      orderIdColumn.setAccountId( "1" );
      orderIdColumn.setIsDBColumn( "1" );
      orderIdColumn.setNameDB( "orderId" );
      orderIdCell.setColumnVO( orderIdColumn );
      orderIdCell.setJdbcDataType( JDBCDataType.INT );
      orderIdCell.setDbValue( employeeContractVO.getOrderId() );
      rowCellDatas.add( orderIdCell );

      CellData clientNameZHCell = new CellData();
      ColumnVO clientNameZHColumn = new ColumnVO();
      clientNameZHColumn.setAccountId( "1" );
      clientNameZHColumn.setIsDBColumn( "1" );
      clientNameZHColumn.setNameDB( "clientNameZH" );
      clientNameZHCell.setColumnVO( clientNameZHColumn );
      clientNameZHCell.setJdbcDataType( JDBCDataType.VARCHAR );
      clientNameZHCell.setDbValue( employeeContractVO.getClientNameZH() );
      rowCellDatas.add( clientNameZHCell );

      CellData clientNameENCell = new CellData();
      ColumnVO clientNameENColumn = new ColumnVO();
      clientNameENColumn.setAccountId( "1" );
      clientNameENColumn.setIsDBColumn( "1" );
      clientNameENColumn.setNameDB( "clientNameEN" );
      clientNameENCell.setColumnVO( clientNameENColumn );
      clientNameENCell.setJdbcDataType( JDBCDataType.VARCHAR );
      clientNameENCell.setDbValue( employeeContractVO.getClientNameEN() );
      rowCellDatas.add( clientNameENCell );

      CellData amountPersonalCell = new CellData();
      ColumnVO amountPersonalColumn = new ColumnVO();
      amountPersonalColumn.setAccountId( "1" );
      amountPersonalColumn.setIsDBColumn( "1" );
      amountPersonalColumn.setNameDB( "amountPersonal" );
      amountPersonalCell.setColumnVO( amountPersonalColumn );
      amountPersonalCell.setJdbcDataType( JDBCDataType.VARCHAR );
      amountPersonalCell.setDbValue( condition.getAmountPersonal() );
      rowCellDatas.add( amountPersonalCell );

      CellData amountCompanyCell = new CellData();
      ColumnVO amountCompanyColumn = new ColumnVO();
      amountCompanyColumn.setAccountId( "1" );
      amountCompanyColumn.setIsDBColumn( "1" );
      amountCompanyColumn.setNameDB( "amountCompany" );
      amountCompanyCell.setColumnVO( amountCompanyColumn );
      amountCompanyCell.setJdbcDataType( JDBCDataType.INT );
      amountCompanyCell.setDbValue( condition.getAmountCompany() );
      rowCellDatas.add( amountCompanyCell );

      CellData personalSBBurdenCell = new CellData();
      ColumnVO personalSBBurdenColumn = new ColumnVO();
      personalSBBurdenColumn.setAccountId( "1" );
      personalSBBurdenColumn.setIsDBColumn( "1" );
      personalSBBurdenColumn.setNameDB( "personalSBBurden" );
      personalSBBurdenCell.setColumnVO( personalSBBurdenColumn );
      personalSBBurdenCell.setJdbcDataType( JDBCDataType.VARCHAR );
      personalSBBurdenCell.setDbValue( personalSBBurden );
      rowCellDatas.add( personalSBBurdenCell );

      CellData employeeSBIdCell = new CellData();
      ColumnVO employeeSBIdColumn = new ColumnVO();
      employeeSBIdColumn.setAccountId( "1" );
      employeeSBIdColumn.setIsDBColumn( "1" );
      employeeSBIdColumn.setNameDB( "employeeSBId" );
      employeeSBIdCell.setColumnVO( employeeSBIdColumn );
      employeeSBIdCell.setJdbcDataType( JDBCDataType.VARCHAR );
      employeeSBIdCell.setDbValue( condition.getEmployeeSBId() );
      rowCellDatas.add( employeeSBIdCell );

   }

   /**
    * 补全从表中的字段
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addSubTableColumn( List< CellData > cellDatas, SBAdjustmentHeaderVO condition, String itemNameZH, String itemNameEN )
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

      CellData mothlyCell = new CellData();
      ColumnVO mothlyColumn = new ColumnVO();
      mothlyColumn.setNameDB( "monthly" );
      mothlyColumn.setAccountId( "1" );
      mothlyColumn.setIsDBColumn( "1" );
      mothlyCell.setColumnVO( mothlyColumn );
      mothlyCell.setDbValue( condition.getMonthly() );
      mothlyCell.setJdbcDataType( JDBCDataType.VARCHAR );
      cellDatas.add( mothlyCell );

   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

}
