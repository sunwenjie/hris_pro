package com.kan.hro.service.impl.biz.settlement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.settlement.AdjustmentHeaderTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class AdjustmentImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   private EmployeeContractDao employeeContractDao;
   
   private AdjustmentHeaderTempDao adjustmentHeaderTempDao;

   private List< ItemVO > itemList = null;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public AdjustmentHeaderTempDao getAdjustmentHeaderTempDao()
   {
      return adjustmentHeaderTempDao;
   }

   public void setAdjustmentHeaderTempDao( AdjustmentHeaderTempDao adjustmentHeaderTempDao )
   {
      this.adjustmentHeaderTempDao = adjustmentHeaderTempDao;
   }

   /**
    * �������ݿ��Ϊ��ʱ������Ҫ��ʱ��Ĳ���Ҫ�ⲽ������
    */
   @Override
   public void init( List< CellDataDTO > importData )
   {

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

   /**
    * ͬ�������ݿ�֮ǰУ�������Ƿ�Ϸ�
    */
   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      boolean result = true;

      // query condition
      EmployeeContractOTVO condition = new EmployeeContractOTVO();

      try
      {
         // ��ȡItemVO List
         String type = "1";
         itemList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemVOsByType( type );
         for ( CellDataDTO row : importData )
         {
            // reset query condition

          //  rowCellDatas.clear();

            // main table has error
            boolean mainHasError = false;
            
            String monthly = null;

            boolean itemError = false;

            // rowNumber
            int rowNumber = 1;

            if ( !mainHasError && row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               row.setErrorMessange( row.getErrorMessange() + "��Ԫ��[" );
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
                     cell.setDbValue( cell.getFormateValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }

                  if ( cell.getColumnVO() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     //���˶���ո��ʽ������
                     Pattern p = Pattern.compile( "[' ']+" );
                     Matcher m = p.matcher( cell.getValue() );
                     monthly = KANUtil.formatDate( m.replaceAll( " " ).trim(), "yyyy/MM" );
                     cell.setDbValue( monthly );
                  }
               }

               // ��ȡEmployeeContractVO
               EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );

               if ( !mainHasError&&employeeContractVO == null )
               {
                  row.setErrorMessange( rowNumber + "��,�Ͷ���ͬ/����Э�鲻��ȷ,��������ϴ���" );
                  mainHasError = true;
               }
               //��׼�����¡��鵵������������ʱ�����ְʱ��û����Ҳ�У�
               String[] status = { "3", "5", "6", "7" };
               if ( !mainHasError&&!Arrays.asList( status ).contains( employeeContractVO.getStatus() ) )
               {

                  row.setErrorMessange( rowNumber + "��,�Ͷ���ͬ/����Э��δ��Ч,��������ϴ���" );
                  mainHasError = true;
               }

            }
            //detail��
            // ���� �ӱ����ݲ������ݶ�Ӧ��CellDatas
            if ( !mainHasError && row.getSubCellDataDTOs() != null && row.getSubCellDataDTOs().size() > 0 )
            {
               for ( int i = 0; i < row.getSubCellDataDTOs().size(); i++ )
               {
                  // �����CellDataDTO
                  final CellDataDTO subCellDataDTO = row.getSubCellDataDTOs().get( i );

                  if ( subCellDataDTO != null && subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
                  {
                     // ��ʼ����ǰ�Ӷ����Ƿ��ǿյ�

                     final Iterator< CellData > iterators = subCellDataDTO.getCellDatas().iterator();

                     while ( iterators.hasNext() )
                     {
                        CellData cellData = iterators.next();

                        cellData.getValue();
                        if ( cellData.getColumnVO() != null && "nameZH".equals( cellData.getColumnVO().getNameDB() ) )
                        {
                           // ��ʼ��Item Name

                           final String itemName = cellData.getValue();
                           final String firstItemName = StringUtils.isNotBlank( itemName ) && itemName.contains( "-" ) ? itemName.split( "-" )[ 0 ].trim() : itemName.trim();

                           if ( StringUtils.isNotBlank( cellData.getValue() ) )
                              for ( ItemVO item : itemList )
                              {
                                 if ( item.getNameZH() != null && item.getNameZH().equals( firstItemName ) )
                                 {
                                    cellData.setValue( firstItemName );
                                    cellData.setFormateValue( firstItemName );
                                    cellData.setDbValue( firstItemName );
                                    itemError = true;
                                    break;
                                 }
                              }
                        }

                        if ( !itemError )
                        {
                           row.setErrorMessange( rowNumber + "��,��Ŀ����ȷ,��������ϴ���" );
                           mainHasError = true;
                        }
                     }

                  }

               }
            }
            if ( mainHasError )
            {
               result = result&&false;
               continue;// if main table has error, break check
            }
            else
            {
               row.setErrorMessange( null );
            }

         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
         return false;
      }
      return result;
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      //��ŷ��������
      Map< String, CellDataDTO > importDataMap = new HashMap< String, CellDataDTO >();
      //��ʱ���
      List< CellDataDTO > importDataTemp = new ArrayList< CellDataDTO >();
      for ( CellDataDTO row : importData )
      {
         //         //�����ֶ�
         //         String orderId = null;
         //         String entityId = null;
         //         String businessTypeId = null;
         //         String clientId = null;
         //         String employeeId = null;
         //         String employeeNameZH = null;
         //         String employeeNameEN = null;
         String contractId = null;
         String key = null;
         String monthly = null;

         if ( StringUtils.isBlank( row.getErrorMessange() ) && row.getCellDatas().size() > 0 )
         {
            for ( int i = 0; i < row.getCellDatas().size(); i++ )
            {

               CellData cell = row.getCellDatas().get( i );

               if ( StringUtils.isBlank( cell.getCellRef() ) )
               {
                  continue;
               }

               if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) && StringUtils.isNotBlank( cell.getFormateValue() ) )
               {
                  contractId = ( String ) cell.getDbValue();
               }

               if ( cell.getColumnVO() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) && StringUtils.isNotBlank( cell.getFormateValue() ) )
               {
                  monthly = ( String ) cell.getDbValue();
               }

               
            }
            
            if ( StringUtils.isNotBlank( contractId ) && StringUtils.isNotBlank( contractId ) )
            {
               key = contractId + "_" + monthly;
            }
            //����
            if ( importDataMap.containsKey( key ) )
            {
               importDataMap.get( key ).getSubCellDataDTOs().addAll( row.getSubCellDataDTOs() );
            }
            else
            {
               importDataMap.put( key, row );
            }

         }
      }

      //������� �����ֶ�
      importData.clear();
      CellDataDTO cellDataDTOTemp = null;
      if ( importDataMap.size() > 0 )
      {
         for ( String key : importDataMap.keySet() )
         {
            cellDataDTOTemp = importDataMap.get( key );
            cellDataDTOTemp.getCellDatas();
            addTableColumn( cellDataDTOTemp );
            importDataTemp.add( cellDataDTOTemp );
         }
      }
      importData.clear();
      importData.addAll( importDataTemp );
      return false;
   }

   /**
    * ��ȫ�ֶ�
    * 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addTableColumn( final CellDataDTO cellDataDTO )
   {
      String adjustmentDateStr = KANUtil.formatDate( new Date(), "yyyy-MM-dd" );
      if ( cellDataDTO.getCellDatas() != null && cellDataDTO.getCellDatas().size() > 0 )
      {

         //      String orderIdStr = null;
         //      String entityIdStr = null;
         //      String businessTypeIdStr = null;
         //      String clientIdStr = null;
         //      String employeeIdStr = null;
         //      String employeeNameZHStr = null;
         //      String employeeNameENStr = null;
         //      String corpIdStr = null;

         //      double billAmountCompanyDou = 0;
         //      double billAmountPersonal = 0;
         //      double costAmountCompany = 0;
         //      double costAmountPersonal = 0;

         //adjustmentHeaderId
         CellData adjustmentHeaderId = new CellData();
         ColumnVO adjustmentHeaderIdColumn = new ColumnVO();
         adjustmentHeaderIdColumn.setNameDB( "adjustmentHeaderId" );
         adjustmentHeaderIdColumn.setAccountId( "1" );
         adjustmentHeaderIdColumn.setIsDBColumn( "1" );
         adjustmentHeaderIdColumn.setIsPrimaryKey( "1" );
         adjustmentHeaderId.setColumnVO( adjustmentHeaderIdColumn );
         adjustmentHeaderId.setJdbcDataType( JDBCDataType.INT );

         CellData status = new CellData();
         ColumnVO statusColumn = new ColumnVO();
         statusColumn.setNameDB( "status" );
         statusColumn.setAccountId( "1" );
         statusColumn.setIsDBColumn( "1" );
         status.setColumnVO( statusColumn );
         status.setDbValue( "1" );
         status.setJdbcDataType( JDBCDataType.INT );

         CellData adjustmentDate = new CellData();
         ColumnVO adjustmentDateColumn = new ColumnVO();
         adjustmentDateColumn.setAccountId( "1" );
         adjustmentDateColumn.setIsDBColumn( "1" );
         adjustmentDateColumn.setNameDB( "adjustmentDate" );
         adjustmentDate.setColumnVO( adjustmentDateColumn );
         adjustmentDate.setJdbcDataType( JDBCDataType.DATE );
         adjustmentDate.setDbValue( adjustmentDateStr );

         cellDataDTO.getCellDatas().add( adjustmentHeaderId );
         cellDataDTO.getCellDatas().add( status );
         cellDataDTO.getCellDatas().add( adjustmentDate );
      }

      //      CellData orderId = new CellData();
      //      ColumnVO orderIdColumn = new ColumnVO();
      //      orderIdColumn.setAccountId( "1" );
      //      orderIdColumn.setIsDBColumn( "1" );
      //      orderIdColumn.setNameDB( "clientId" );
      //      orderId.setColumnVO( orderIdColumn );
      //      orderId.setJdbcDataType( JDBCDataType.INT );
      //      orderId.setDbValue( orderIdStr);
      //
      //      CellData entityId = new CellData();
      //      ColumnVO entityIdColumn = new ColumnVO();
      //      entityIdColumn.setAccountId( "1" );
      //      entityIdColumn.setIsDBColumn( "1" );
      //      entityIdColumn.setNameDB( "entityId" );
      //      entityId.setColumnVO( entityIdColumn );
      //      entityId.setJdbcDataType( JDBCDataType.DOUBLE );
      //      entityId.setDbValue( entityIdStr );
      //      
      //      CellData businessTypeId = new CellData();
      //      ColumnVO businessTypeIdColumn = new ColumnVO();
      //      businessTypeIdColumn.setAccountId( "1" );
      //      businessTypeIdColumn.setIsDBColumn( "1" );
      //      businessTypeIdColumn.setNameDB( "businessTypeId" );
      //      businessTypeId.setColumnVO( businessTypeIdColumn );
      //      businessTypeId.setJdbcDataType( JDBCDataType.INT );
      //      businessTypeId.setDbValue( businessTypeIdStr );
      //      
      //      CellData clientId = new CellData();
      //      ColumnVO clientIdColumn = new ColumnVO();
      //      clientIdColumn.setAccountId( "1" );
      //      clientIdColumn.setIsDBColumn( "1" );
      //      clientIdColumn.setNameDB( "clientId" );
      //      clientId.setColumnVO( clientIdColumn );
      //      clientId.setJdbcDataType( JDBCDataType.INT );
      //      clientId.setDbValue( clientIdStr );
      //      
      //      
      //      CellData corpId = new CellData();
      //      ColumnVO corpIdColumn = new ColumnVO();
      //      corpIdColumn.setAccountId( "1" );
      //      corpIdColumn.setIsDBColumn( "1" );
      //      corpIdColumn.setNameDB( "corpId" );
      //      corpId.setColumnVO( corpIdColumn );
      //      corpId.setJdbcDataType( JDBCDataType.INT );
      //      corpId.setDbValue( corpIdStr );
      //      
      //      CellData employeeId = new CellData();
      //      ColumnVO employeeIdColumn = new ColumnVO();
      //      employeeIdColumn.setAccountId( "1" );
      //      employeeIdColumn.setIsDBColumn( "1" );
      //      employeeIdColumn.setNameDB( "employeeId" );
      //      employeeId.setColumnVO( employeeIdColumn );
      //      employeeId.setJdbcDataType( JDBCDataType.INT );
      //      employeeId.setDbValue( employeeIdStr );
      //      
      //      CellData employeeNameZH = new CellData();
      //      ColumnVO employeeNameZHColumn = new ColumnVO();
      //      employeeNameZHColumn.setAccountId( "1" );
      //      employeeNameZHColumn.setIsDBColumn( "1" );
      //      employeeNameZHColumn.setNameDB( "employeeNameZH" );
      //      employeeNameZH.setColumnVO( employeeNameZHColumn );
      //      employeeNameZH.setJdbcDataType( JDBCDataType.VARCHAR );
      //      employeeNameZH.setDbValue( employeeNameZHStr );
      //
      //      CellData employeeNameEN = new CellData();
      //      ColumnVO employeeNameENColumn = new ColumnVO();
      //      employeeNameENColumn.setAccountId( "1" );
      //      employeeNameENColumn.setIsDBColumn( "1" );
      //      employeeNameENColumn.setNameDB( "employeeNameEN" );
      //      employeeNameEN.setColumnVO( employeeNameENColumn );
      //      employeeNameEN.setJdbcDataType( JDBCDataType.VARCHAR );
      //      employeeNameEN.setDbValue( employeeNameENStr );
      //
      //      CellData adjustmentDate = new CellData();
      //      ColumnVO adjustmentDateColumn = new ColumnVO();
      //      adjustmentDateColumn.setAccountId( "1" );
      //      adjustmentDateColumn.setIsDBColumn( "1" );
      //      adjustmentDateColumn.setNameDB( "employeeNameEN" );
      //      adjustmentDate.setColumnVO( adjustmentDateColumn );
      //      adjustmentDate.setJdbcDataType( JDBCDataType.DATE );
      //      adjustmentDate.setDbValue( adjustmentDateStr );
      //      
      //        for(CellDataDTO subCellDataDTO:cellDataDTO.getSubCellDataDTOs()){

      if ( cellDataDTO != null && cellDataDTO.getSubCellDataDTOs() != null && cellDataDTO.getSubCellDataDTOs().size() > 0 )
      {
               
         for ( CellDataDTO subCellDataDTO : cellDataDTO.getSubCellDataDTOs() )
         {

            CellData adjustmentHeaderId = new CellData();
            ColumnVO adjustmentHeaderIdColumn = new ColumnVO();
            adjustmentHeaderIdColumn.setNameDB( "adjustmentHeaderId" );
            adjustmentHeaderIdColumn.setAccountId( "1" );
            adjustmentHeaderIdColumn.setIsDBColumn( "1" );
            adjustmentHeaderIdColumn.setIsForeignKey( "1" );
            adjustmentHeaderId.setColumnVO( adjustmentHeaderIdColumn );
            adjustmentHeaderId.setJdbcDataType( JDBCDataType.INT );
//
//            CellData adjustmentDetailId = new CellData();
//            ColumnVO adjustmentDetailIdColumn = new ColumnVO();
//            adjustmentDetailIdColumn.setNameDB( "adjustmentDetailId" );
//            adjustmentDetailIdColumn.setAccountId( "1" );
//            adjustmentDetailIdColumn.setIsDBColumn( "1" );
//            adjustmentDetailIdColumn.setIsPrimaryKey( "1" );
//            adjustmentHeaderId.setColumnVO( adjustmentDetailIdColumn );
//            adjustmentHeaderId.setJdbcDataType( JDBCDataType.INT );

            CellData status = new CellData();
            ColumnVO statusColumn = new ColumnVO();
            statusColumn.setNameDB( "status" );
            statusColumn.setAccountId( "1" );
            statusColumn.setIsDBColumn( "1" );
            status.setColumnVO( statusColumn );
            status.setDbValue( "1" );
            status.setJdbcDataType( JDBCDataType.INT );
            final Iterator< CellData > iterators = subCellDataDTO.getCellDatas().iterator();
            CellData itemId = null;
            CellData itemEN = null;
            while ( iterators.hasNext() )
            {
               CellData cellData = iterators.next();

               cellData.getValue();
               if ( cellData.getColumnVO() != null && "nameZH".equals( cellData.getColumnVO().getNameDB() ) )
               {
                  // ��ʼ��Item Name

                  final String itemName = cellData.getValue();
                  final String firstItemName = StringUtils.isNotBlank( itemName ) && itemName.contains( "-" ) ? itemName.split( "-" )[ 0 ].trim() : itemName.trim();

                  if ( StringUtils.isNotBlank( cellData.getValue() ) )
                     for ( ItemVO item : itemList )
                     {
                        if ( item.getNameZH() != null && item.getNameZH().equals( firstItemName ) )
                        {
                           itemId = new CellData();
                           ColumnVO itemIdColumn = new ColumnVO();
                           itemIdColumn.setNameDB( "itemId" );
                           itemIdColumn.setAccountId( "1" );
                           itemIdColumn.setIsDBColumn( "1" );
                           itemId.setColumnVO( itemIdColumn );
                           itemId.setDbValue( item.getItemId() );
                           itemId.setJdbcDataType( JDBCDataType.INT );
                           itemEN = new CellData();
                           ColumnVO itemENColumn = new ColumnVO();
                           itemENColumn.setNameDB( "nameEN" );
                           itemENColumn.setAccountId( "1" );
                           itemENColumn.setIsDBColumn( "1" );
                           itemEN.setColumnVO( itemENColumn );
                           itemEN.setDbValue( item.getNameEN() );
                           itemEN.setJdbcDataType( JDBCDataType.VARCHAR );
                           break;
                        }
                     }
               }
            }

            subCellDataDTO.getCellDatas().add( adjustmentHeaderId );
            subCellDataDTO.getCellDatas().add( status );
//            subCellDataDTO.getCellDatas().add( adjustmentDetailId );
            if(itemId!=null){
               subCellDataDTO.getCellDatas().add( itemId);
            }
            if(itemEN!=null){
               subCellDataDTO.getCellDatas().add( itemEN);
            }

         }
      }

   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      
      //����header
      try
      {
         adjustmentHeaderTempDao.updateAdjustmentHeaderTempReplenish( batchId );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
      //����deltail
      return false;
   }

}
