package com.kan.hro.service.impl.biz.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractCBDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class EmployeeContractCardNOImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   private EmployeeContractDao employeeContractDao;

   private EmployeeContractCBDao employeeContractCBDao;

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public EmployeeContractCBDao getEmployeeContractCBDao()
   {
      return employeeContractCBDao;
   }

   public void setEmployeeContractCBDao( EmployeeContractCBDao employeeContractCBDao )
   {
      this.employeeContractCBDao = employeeContractCBDao;
   }

   /**
    * 更换数据库表为临时表。不需要临时表的不需要这步操作。
    */
   @Override
   public void init( List< CellDataDTO > importData )
   {

      //      if ( importData != null )
      //      {
      //         for ( CellDataDTO cellDataDTO : importData )
      //         {
      //            cellDataDTO.setTableName( cellDataDTO.getTableName() + "_TEMP" );
      //            if ( cellDataDTO.getSubCellDataDTOs() == null )
      //               continue;
      //            for ( CellDataDTO subcellDataDTO : cellDataDTO.getSubCellDataDTOs() )
      //            {
      //               subcellDataDTO.setTableName( subcellDataDTO.getTableName() + "_TEMP" );
      //            }
      //         }
      //      }
   }

   /**
    * 同步到数据库之前校验数据是否合法
    */
   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      boolean result = true;

      // query condition
      EmployeeContractOTVO condition = new EmployeeContractOTVO();
      

      try
      {
         final String accountId = BaseAction.getAccountId( request, null );
         // 获取ItemVO List
         //         String type = "1";
         //         itemList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getItemVOsByType( type );
         for ( CellDataDTO row : importData )
         {

            // main table has error
            boolean mainHasError = false;

            EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
            // rowNumber
            int rowNumber = 1;
            boolean hasValue = false;
            boolean hasCbNumberValue = false;
            boolean hasSolutionNameZHValue = false;
            boolean hasSolutionNameZHValueB = false;
            boolean hasCbNumberValueB = false;
            boolean hasContractIdValue = false;

            if ( !mainHasError && row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               rowNumber = row.getCellDatas().get( 0 ).getRow() + 1;

               row.setErrorMessange( row.getErrorMessange() + "单元格[" );
               for ( int i = 0; i < row.getCellDatas().size(); i++ )
               {

                  CellData cell = row.getCellDatas().get( i );

                  if ( cell.getColumnVO() != null && "accountId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setAccountId( ( String ) cell.getDbValue() );
                     employeeContractCBVO.setAccountId( ( String ) cell.getDbValue() );
                  }

                  if ( StringUtils.isBlank( cell.getCellRef() ) )
                  {
                     continue;
                  }

                  if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getFormateValue() ) )
                     {
                        condition.setContractId( cell.getFormateValue().trim() );
                        employeeContractCBVO.setContractId( cell.getFormateValue().trim() );
                        cell.setDbValue( cell.getFormateValue().trim() );
                        hasContractIdValue = true;
                     }
                  }
                  
                  
                  if ( cell.getColumnVO() != null && "solutionIdB".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getFormateValue() ) )
                     {
                        employeeContractCBVO.setSolutionNameZH( cell.getValue() );
                        employeeContractCBVO.setAccountId( accountId );
                        
                        List< Object > objectList = employeeContractCBDao.getEmployeeContractCBVOsBySolutionNameZH( employeeContractCBVO );
                        if ( objectList != null && objectList.size() > 0 )
                        {
                           cell.setDbValue( ( ( EmployeeContractCBVO ) objectList.get( 0 ) ).getSolutionId() );
                        }
                        else
                        {
                           row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "],商保方案不正确,请检查后再上传。" );
                           mainHasError = true;
                        }
                        employeeContractCBVO.setSolutionNameZH( null );
                        hasSolutionNameZHValueB = true;
                     }
                  }

                  if ( cell.getColumnVO() != null && "solutionNameZH".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getFormateValue() ) )
                     {
                        employeeContractCBVO.setSolutionNameZH( cell.getValue() );
                        cell.setDbValue( cell.getFormateValue() );
                        cell.getColumnVO().setIsDBColumn( "2" );
                        hasSolutionNameZHValue = true;
                     }
                  }
             
                  
                  if ( !hasValue&&cell.getColumnVO() != null && "medicalNumber".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                        
                     }
                  }
                  
                  if ( !hasValue&&cell.getColumnVO() != null && "sbNumber".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                     }
                  }
                  
                  if ( !hasValue&&cell.getColumnVO() != null && "fundNumber".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                     }
                  }
                  
                  if ( !hasValue&&cell.getColumnVO() != null && "hsNumber".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                     }
                  }
                  
                  if (cell.getColumnVO() != null && "cbNumber".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                        hasCbNumberValue = true;
                     }
                  }
                  if (cell.getColumnVO() != null && "cbNumberB".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     if ( StringUtils.isNotBlank( cell.getValue() ) )
                     {
                        hasValue = true;
                        hasCbNumberValueB = true;
                     }
                  }
               }
               if ( !mainHasError && !hasContractIdValue )
               {
                  row.setErrorMessange( rowNumber + "行,派遣协议ID必须填写,请检查后再上传。" );
                  mainHasError = true;
               }
               
               if ( !mainHasError && !hasValue )
               {
                  row.setErrorMessange( rowNumber + "行,医保卡帐号、社保卡帐号、公积金帐号、房贴号、保单号 必须填写一项,请检查后再上传。" );
                  mainHasError = true;
               }
               if ( !mainHasError && ((hasCbNumberValue&&!hasSolutionNameZHValue)|| (hasCbNumberValueB&&!hasSolutionNameZHValueB)) )
               {
                  row.setErrorMessange( rowNumber + "行,存在保单号数据时，对应商保方案 必须填写,请检查后再上传。" );
                  mainHasError = true;
               }
               if ( !mainHasError )
               {
                  // 获取EmployeeContractVO
                  EmployeeContractVO employeeContractVO = employeeContractDao.getEmployeeContractVOByContractId( condition.getContractId() );
                  if(employeeContractVO == null ){
                     row.setErrorMessange( rowNumber + "行,劳动合同/服务协议不正确,请检查后再上传。" );
                     mainHasError = true;
                  }
               }

               if ( !mainHasError && StringUtils.isNotBlank( employeeContractCBVO.getSolutionNameZH() ) )
               {

                  List<Object> objectList = employeeContractCBDao.getEmployeeContractCBVOsBySolutionNameZH( employeeContractCBVO );

                  if ( objectList != null && objectList.size() > 0 )
                  {
                     //补齐solutionId
                     CellData solutionId = new CellData();
                     ColumnVO solutionIdColumn = new ColumnVO();

                     solutionIdColumn.setNameDB( "solutionId" );
                     solutionIdColumn.setAccountId( "1" );
                     solutionIdColumn.setIsDBColumn( "1" );
                     solutionId.setColumnVO( solutionIdColumn );
                     solutionId.setDbValue( ( ( EmployeeContractCBVO ) objectList.get( 0 ) ).getSolutionId() );
                     solutionId.setJdbcDataType( JDBCDataType.INT );
                     row.getCellDatas().add( solutionId );
                  }
                  else
                  {
                     row.setErrorMessange( rowNumber + "行,商保方案不正确,请检查后再上传。" );
                     mainHasError = true;
                  }
               }

            }
            if ( mainHasError )
            {
               result = result && false;
               continue;// if main table has error, break check
            }
            else
            {
               //补齐状态
               CellData status = new CellData();
               ColumnVO statusColumn = new ColumnVO();
               statusColumn.setNameDB( "status" );
               statusColumn.setAccountId( "1" );
               statusColumn.setIsDBColumn( "1" );
               status.setColumnVO( statusColumn );
               status.setDbValue( "1" );
               status.setJdbcDataType( JDBCDataType.INT );
               row.getCellDatas().add( status );
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
      return false;
   }

   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {
      return false;
   }

}
