package com.kan.hro.service.inf.biz.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.util.ExcelImportHandler;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.poi.bean.CellData;
import com.kan.base.util.poi.bean.CellDataDTO;
import com.kan.base.util.poi.bean.JDBCDataType;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;

public class TimeSheetAllowanceImportHandlerImpl extends ContextService implements ExcelImportHandler< List< CellDataDTO >>
{

   private TimesheetHeaderDao timesheetHeaderDao;

   private TimesheetAllowanceDao timesheetAllowanceDao;

   public TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public TimesheetAllowanceDao getTimesheetAllowanceDao()
   {
      return timesheetAllowanceDao;
   }

   public void setTimesheetAllowanceDao( TimesheetAllowanceDao timesheetAllowanceDao )
   {
      this.timesheetAllowanceDao = timesheetAllowanceDao;
   }

   /**
    * 更换数据库表为临时表。不需要临时表的不需要这步操作。
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
    * 同步到数据库之前校验数据是否合法
    */
   @Override
   public boolean excuteBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      boolean result = true;

      //query condition
      TimesheetHeaderVO condition = new TimesheetHeaderVO();
      TimesheetAllowanceVO allowanceCondition = new TimesheetAllowanceVO();

      List< Object > timeSheetHeaderList = null;
      List< Object > allowanceList = null;

      List< String > itemNames = new ArrayList< String >();

      //补充excel中没有的列到数据库
      List< CellData > rowCellDatas = new ArrayList< CellData >();
      List< CellData > cellDatas = new ArrayList< CellData >();
      List<MappingVO> itemMapVOs = new ArrayList<MappingVO>();
    /*  itemMapVOs.addAll(KANConstants.getItemsByType("2",request.getLocale().getLanguage() ));
      itemMapVOs.addAll(KANConstants.getItemsByType("3",request.getLocale().getLanguage() ));
      itemMapVOs.addAll(KANConstants.getItemsByType("5",request.getLocale().getLanguage() ));*/
      KANAccountConstants c ;
      try
      {
          c = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
          itemMapVOs.addAll( c.getItemsByType( "2", request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) ));
          itemMapVOs.addAll( c.getItemsByType( "3", request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) ));
          itemMapVOs.addAll( c.getItemsByType( "5", request.getLocale().getLanguage(), BaseAction.getCorpId( request, null ) ));
      }
      catch ( KANException e1 )
      {
         e1.printStackTrace();
      }
      
      try
      {
         for ( CellDataDTO row : importData )
         {
            //reset query condition
            condition.reset();
            condition.setCorpId( BaseAction.getCorpId( request, null ) );
            allowanceCondition.reset();

            //reset item names 
            itemNames.clear();

            rowCellDatas.clear();

            //main table has error
            boolean mainHasError = false;

            if ( row.getCellDatas() != null && row.getCellDatas().size() > 0 )
            {
               row.setErrorMessange( row.getErrorMessange() + "单元格[" );
               for ( CellData cell : row.getCellDatas() )
               {

                  if ( cell.getColumnVO() != null && "accountId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setAccountId( ( String ) cell.getDbValue() );
                  }

                  if ( StringUtils.isBlank( cell.getCellRef() ) )
                  {
                     continue;
                  }
                  //不保存employeeNameZH字段
                  if ( cell.getColumnVO() != null && "employeeNameZH".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     cell.getColumnVO().setIsDBColumn( "2" );
                  }

                  //查询条件赋值
                  if ( cell.getColumnVO() != null && "employeeId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setEmployeeId( cell.getValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }
                  if ( cell.getColumnVO() != null && "monthly".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setMonthly( cell.getValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }
                  if ( cell.getColumnVO() != null && "contractId".equals( cell.getColumnVO().getNameDB() ) )
                  {
                     condition.setContractId( cell.getValue() );
                     row.setErrorMessange( row.getErrorMessange() + cell.getCellRef() + "," );
                  }
               }

               timeSheetHeaderList = timesheetHeaderDao.getTimesheetHeaderVOsByCondition( condition );

               if ( timeSheetHeaderList != null && timeSheetHeaderList.size() == 1 )
               {
                  allowanceCondition.setHeaderId( ( ( TimesheetHeaderVO ) timeSheetHeaderList.get( 0 ) ).getHeaderId() );
                  allowanceList = timesheetAllowanceDao.getTimesheetAllowanceVOsByCondition( allowanceCondition );
//                  if ( allowanceList != null )
//                  {
//                     for ( Object allowance : allowanceList )
//                     {
//                        itemNames.add( ( ( TimesheetAllowanceVO ) allowance ).getItemNameZH() );
//                     }
//                  }
                  //查询条件没有错误，清空错误信息
                  row.setErrorMessange( "" );

                  //补全要保存的数据
                  addMainTableColumn( rowCellDatas, ( TimesheetHeaderVO ) timeSheetHeaderList.get( 0 ) );

               }
               //修改
               else
               {
                  String rowMessange = row.getErrorMessange();
                  row.setErrorMessange( rowMessange.substring( 0, rowMessange.length() - 1 ) + "]数据有误，请检查后再上传。" );
                  mainHasError = true;
               }
            }

            if ( mainHasError )
            {
               result = false;
               continue;//if main table has error, break check
            }
            else
            {
               row.getCellDatas().addAll( rowCellDatas );
            }

            boolean slaveHasError = false;

//            if ( row.getSubCellDataDTOs() != null )
//            {
//               for ( CellDataDTO subCellDataDTO : row.getSubCellDataDTOs() )
//               {
//                  if ( subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
//                  {
//
//                     for ( CellData subCell : subCellDataDTO.getCellDatas() )
//                     {
//                        if ( StringUtils.isBlank( subCell.getCellRef() ) )
//                        {
//                           continue;
//                        }
//                        //修改
//                        if ( StringUtils.isNotBlank( subCell.getValue() ) && subCell.getColumnVO() != null && !itemNames.contains( subCell.getColumnVO().getNameZH() ) )
//                        {
//                           subCell.setErrorMessange( "单元格[" + subCell.getCellRef() + "]错误，该雇员没有此项考勤津贴，请删除后再上传。" );
//                           slaveHasError = true;
//                        }
//                     }
//                  }
//               }
//            }

            if ( !slaveHasError )
            {
               if ( row.getSubCellDataDTOs() != null )
               {

                  Iterator< CellDataDTO > iter = row.getSubCellDataDTOs().iterator();
                  while ( iter.hasNext() )
                  {
                     CellDataDTO subCellDataDTO = iter.next();
                     cellDatas.clear();
                     if ( subCellDataDTO.getCellDatas() != null && subCellDataDTO.getCellDatas().size() > 0 )
                     {
                        for ( CellData subCell : subCellDataDTO.getCellDatas() )
                        {
                           if ( StringUtils.isBlank( subCell.getCellRef() ) )
                           {
                              continue;
                           }
                           if ( subCell.getColumnVO() != null && "base".equals( subCell.getColumnVO().getNameDB() ) )
                           {
                              subCell.getColumnVO().setIsDBColumn( "1" );
                           }
                           if(StringUtils.isNotBlank( subCell.getValue())){
	                           String allowanceId = null;
	                           for ( Object allowance : allowanceList )
	                           {
	                              TimesheetAllowanceVO vo = ( TimesheetAllowanceVO ) allowance;
	                              if ( vo.getItemNameZH().equals( subCell.getColumnVO().getNameZH() ) )
	                              {
	                            	  //final String headerId,final String allowanceId ,final String columnId
	                            	  allowanceId = vo.getAllowanceId();
	                                 break;
	                              }
	                           }
	                           //修改时过滤掉0 值 
	                           if((this.isNumeric( subCell.getValue() )&&(StringUtils.isNotBlank( allowanceId )||Double.compare( new Double(0), ( Double.parseDouble( subCell.getValue() )))!=0))){
	                             addSlaveTableColumn( cellDatas, allowanceCondition.getHeaderId(),allowanceId,subCell.getColumnVO().getNameZH() ,itemMapVOs);
	                           }
                           }

                        }
                     }

                     if ( cellDatas != null && cellDatas.size() == 0 )
                     {
                        iter.remove();//从表中空数据remove掉，不进数据库
                     }
                     else
                     {
                        subCellDataDTO.getCellDatas().addAll( cellDatas );
                     }
                  }
               }
            }
            else
            {
               result = false;
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

   /**
    * 补全主表中的字段 
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addMainTableColumn( List< CellData > rowCellDatas, TimesheetHeaderVO timesheetHeaderVO )
   {

      /*
      CellData id = new CellData();
        ColumnVO idColumn = new ColumnVO();
        idColumn.setNameDB( "id" );
        idColumn.setAccountId( "1" );
        idColumn.setIsDBColumn( "1" );
        idColumn.setIsPrimaryKey("1");
        id.setColumnVO( idColumn );
        id.setJdbcDataType( JDBCDataType.INT );*/

      CellData headerId = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "headerId" );
      headerIdColumn.setAccountId( "1" );
      headerIdColumn.setIsDBColumn( "1" );
      headerIdColumn.setIsPrimaryKey( "1" );
      headerId.setColumnVO( headerIdColumn );
      headerId.setJdbcDataType( JDBCDataType.INT );

      //原表主键放到remark4中
      CellData remark4 = new CellData();
      ColumnVO remark4Column = new ColumnVO();
      remark4Column.setNameDB( "remark4" );
      remark4Column.setAccountId( "1" );
      remark4Column.setIsDBColumn( "1" );
      remark4.setColumnVO( remark4Column );
      remark4.setJdbcDataType( JDBCDataType.VARCHAR );
      remark4.setDbValue( timesheetHeaderVO.getHeaderId() );

      CellData orderId = new CellData();
      ColumnVO orderIdColumn = new ColumnVO();
      orderIdColumn.setNameDB( "orderId" );
      orderIdColumn.setAccountId( "1" );
      orderIdColumn.setIsDBColumn( "1" );
      orderId.setColumnVO( orderIdColumn );
      orderId.setJdbcDataType( JDBCDataType.INT );
      orderId.setDbValue( timesheetHeaderVO.getOrderId() );

      CellData startDate = new CellData();
      ColumnVO startDateColumn = new ColumnVO();
      startDateColumn.setNameDB( "startDate" );
      startDateColumn.setAccountId( "1" );
      startDateColumn.setIsDBColumn( "1" );
      startDate.setColumnVO( startDateColumn );
      startDate.setJdbcDataType( JDBCDataType.DATE );
      startDate.setDbValue( timesheetHeaderVO.getStartDate() );

      CellData endDate = new CellData();
      ColumnVO endDateColumn = new ColumnVO();
      endDateColumn.setNameDB( "endDate" );
      endDateColumn.setAccountId( "1" );
      endDateColumn.setIsDBColumn( "1" );
      endDate.setColumnVO( endDateColumn );
      endDate.setJdbcDataType( JDBCDataType.DATE );
      endDate.setDbValue( timesheetHeaderVO.getEndDate() );

      CellData totalWorkHours = new CellData();
      ColumnVO totalWorkColumn = new ColumnVO();
      totalWorkColumn.setNameDB( "totalWorkHours" );
      totalWorkColumn.setAccountId( "1" );
      totalWorkColumn.setIsDBColumn( "1" );
      totalWorkHours.setColumnVO( totalWorkColumn );
      totalWorkHours.setJdbcDataType( JDBCDataType.DOUBLE );
      totalWorkHours.setDbValue( timesheetHeaderVO.getTotalWorkHours() );

      CellData totalWorkDays = new CellData();
      ColumnVO totalWorkDaysColumn = new ColumnVO();
      totalWorkDaysColumn.setNameDB( "totalWorkDays" );
      totalWorkDaysColumn.setAccountId( "1" );
      totalWorkDaysColumn.setIsDBColumn( "1" );
      totalWorkDays.setColumnVO( totalWorkDaysColumn );
      totalWorkDays.setJdbcDataType( JDBCDataType.DOUBLE );
      totalWorkDays.setDbValue( timesheetHeaderVO.getTotalWorkDays() );

      ColumnVO totalFullHoursColumn = new ColumnVO();
      totalFullHoursColumn.setNameDB( "totalFullHours" );
      totalFullHoursColumn.setAccountId( "1" );
      totalFullHoursColumn.setIsDBColumn( "1" );
      CellData totalFullHours = new CellData();
      totalFullHours.setColumnVO( totalFullHoursColumn );
      totalFullHours.setJdbcDataType( JDBCDataType.DOUBLE );
      totalFullHours.setDbValue( timesheetHeaderVO.getTotalFullHours() );

      ColumnVO totalFullDaysColumn = new ColumnVO();
      totalFullDaysColumn.setNameDB( "totalFullDays" );
      totalFullDaysColumn.setAccountId( "1" );
      totalFullDaysColumn.setIsDBColumn( "1" );
      CellData totalFullDays = new CellData();
      totalFullDays.setColumnVO( totalFullDaysColumn );
      totalFullDays.setJdbcDataType( JDBCDataType.DOUBLE );
      totalFullDays.setDbValue( timesheetHeaderVO.getTotalFullDays() );

      ColumnVO needAuditColumn = new ColumnVO();
      needAuditColumn.setNameDB( "needAudit" );
      needAuditColumn.setAccountId( "1" );
      needAuditColumn.setIsDBColumn( "1" );
      CellData needAudit = new CellData();
      needAudit.setColumnVO( needAuditColumn );
      needAudit.setJdbcDataType( JDBCDataType.INT );
      needAudit.setDbValue( timesheetHeaderVO.getNeedAudit() );

      ColumnVO isNormalColumn = new ColumnVO();
      isNormalColumn.setNameDB( "isNormal" );
      isNormalColumn.setAccountId( "1" );
      isNormalColumn.setIsDBColumn( "1" );
      CellData isNormal = new CellData();
      isNormal.setColumnVO( isNormalColumn );
      isNormal.setJdbcDataType( JDBCDataType.INT );
      isNormal.setDbValue( timesheetHeaderVO.getIsNormal() );

      ColumnVO clientColumn = new ColumnVO();
      clientColumn.setNameDB( "clientId" );
      clientColumn.setAccountId( "1" );
      clientColumn.setIsDBColumn( "1" );
      CellData client = new CellData();
      client.setColumnVO( clientColumn );
      client.setJdbcDataType( JDBCDataType.INT );
      client.setDbValue( timesheetHeaderVO.getClientId() );

      rowCellDatas.add( headerId );
      rowCellDatas.add( remark4 );
      rowCellDatas.add( orderId );
      rowCellDatas.add( startDate );
      rowCellDatas.add( endDate );
      rowCellDatas.add( totalWorkHours );
      rowCellDatas.add( totalWorkDays );
      rowCellDatas.add( totalFullHours );
      rowCellDatas.add( totalFullDays );
      rowCellDatas.add( needAudit );
      rowCellDatas.add( isNormal );
      rowCellDatas.add( client );

   }

   /**补全从表中的字段
    * @param rowCellDatas
    * @param timesheetHeaderVO
    */
   public void addSlaveTableColumn( final List< CellData > rowCellDatas, final String headerId,final String allowanceId ,final String columnNameZH,final List<MappingVO> itemMapVOs)
   {

      /*CellData id = new CellData();
        ColumnVO idColumn = new ColumnVO();
        idColumn.setNameDB( "id" );
        idColumn.setAccountId( "1" );
        idColumn.setIsDBColumn( "1" );
        idColumn.setIsPrimaryKey("1");
        id.setColumnVO( idColumn );
        id.setJdbcDataType( JDBCDataType.INT );*/

      CellData allowanceIdCell = new CellData();
      ColumnVO allowanceColumn = new ColumnVO();
      allowanceColumn.setNameDB( "allowanceId" );
      allowanceColumn.setAccountId( "1" );
      allowanceColumn.setIsDBColumn( "1" );
      allowanceColumn.setIsPrimaryKey( "1" );
      allowanceIdCell.setColumnVO( allowanceColumn );
      allowanceIdCell.setJdbcDataType( JDBCDataType.INT );

      //原表主键放到remark4中
      CellData remark4Cell = new CellData();
      ColumnVO remark4Column = new ColumnVO();
      remark4Column.setNameDB( "remark4" );
      remark4Column.setAccountId( "1" );
      remark4Column.setIsDBColumn( "1" );
      remark4Cell.setColumnVO( remark4Column );
      remark4Cell.setJdbcDataType( JDBCDataType.VARCHAR );
      if(StringUtils.isNotBlank(allowanceId)){
    	  remark4Cell.setDbValue( allowanceId );
      }
      CellData headerIdCell = new CellData();
      ColumnVO headerIdColumn = new ColumnVO();
      headerIdColumn.setNameDB( "headerId" );
      headerIdColumn.setAccountId( "1" );
      headerIdColumn.setIsDBColumn( "1" );
      headerIdColumn.setIsForeignKey( "1" );
      headerIdCell.setColumnVO( headerIdColumn );
      headerIdCell.setJdbcDataType( JDBCDataType.INT );
      if(StringUtils.isNotBlank(headerId)){
    	  headerIdCell.setDbValue( headerId );
      }

      CellData itemIdCell = new CellData();
      ColumnVO itemIdColumn = new ColumnVO();
      itemIdColumn.setNameDB( "itemId" );
      itemIdColumn.setAccountId( "1" );
      itemIdColumn.setIsDBColumn( "1" );
      itemIdCell.setColumnVO( itemIdColumn );
      itemIdCell.setJdbcDataType( JDBCDataType.INT );
      if(StringUtils.isNotBlank(columnNameZH)){
    	  for(MappingVO itemMapVO:itemMapVOs){
    		  if(columnNameZH.equals(itemMapVO.getMappingValue())){
    			  itemIdCell.setDbValue( itemMapVO.getMappingId() );
    			  break;
    		  }
    	  }
      }
  

      rowCellDatas.add( remark4Cell );
      rowCellDatas.add( allowanceIdCell );
      rowCellDatas.add( itemIdCell );
      rowCellDatas.add( headerIdCell );

   }

   /**
    * 同步到数据库后操作
    */
   @Override
   public boolean excueEndInsert( List< CellDataDTO > importData, String batchId )
   {

      return false;
   }
   
   private boolean isNumeric(String nuber){
      try{
         Double.parseDouble(nuber);
         return true;
        }catch(Exception e){
         return false;
        }
   }

   @Override
   public boolean excuteRegroupmentBeforInsert( List< CellDataDTO > importData, HttpServletRequest request )
   {
      // TODO Auto-generated method stub
      return false;
   }

}
