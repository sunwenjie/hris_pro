package com.kan.hro.service.impl.biz.employee;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractLeaveDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveReportVO;
import com.kan.hro.domain.biz.employee.EmployeeContractLeaveVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.attendance.LeaveHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveService;

public class EmployeeContractLeaveServiceImpl extends ContextService implements EmployeeContractLeaveService
{
   private ItemDao itemDao;

   private EmployeeContractDao employeeContractDao;

   private ClientOrderLeaveDao clientOrderLeaveDao;

   private LeaveHeaderDao leaveHeaderDao;

   private LeaveDetailDao leaveDetailDao;

   private LeaveHeaderService leaveHeaderService;

   public LeaveDetailDao getLeaveDetailDao()
   {
      return leaveDetailDao;
   }

   public void setLeaveDetailDao( LeaveDetailDao leaveDetailDao )
   {
      this.leaveDetailDao = leaveDetailDao;
   }

   public LeaveHeaderDao getLeaveHeaderDao()
   {
      return leaveHeaderDao;
   }

   public void setLeaveHeaderDao( LeaveHeaderDao leaveHeaderDao )
   {
      this.leaveHeaderDao = leaveHeaderDao;
   }

   public ClientOrderLeaveDao getClientOrderLeaveDao()
   {
      return clientOrderLeaveDao;
   }

   public void setClientOrderLeaveDao( ClientOrderLeaveDao clientOrderLeaveDao )
   {
      this.clientOrderLeaveDao = clientOrderLeaveDao;
   }

   public ItemDao getItemDao()
   {
      return itemDao;
   }

   public void setItemDao( ItemDao itemDao )
   {
      this.itemDao = itemDao;
   }

   public EmployeeContractDao getEmployeeContractDao()
   {
      return employeeContractDao;
   }

   public void setEmployeeContractDao( EmployeeContractDao employeeContractDao )
   {
      this.employeeContractDao = employeeContractDao;
   }

   public LeaveHeaderService getLeaveHeaderService()
   {
      return leaveHeaderService;
   }

   public void setLeaveHeaderService( LeaveHeaderService leaveHeaderService )
   {
      this.leaveHeaderService = leaveHeaderService;
   }

   @Override
   public PagedListHolder getEmployeeContractLeaveVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {

      final EmployeeContractLeaveDao employeeContractLeaveDao = ( EmployeeContractLeaveDao ) getDao();
      pagedListHolder.setHolderSize( employeeContractLeaveDao.countEmployeeContractLeaveVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractLeaveDao.getEmployeeContractLeaveVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractLeaveDao.getEmployeeContractLeaveVOsByCondition( ( EmployeeContractLeaveVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public EmployeeContractLeaveVO getEmployeeContractLeaveVOByEmployeeLeaveId( final String employeeLeaveId ) throws KANException
   {
      return ( ( EmployeeContractLeaveDao ) getDao() ).getEmployeeContractLeaveVOByEmployeeLeaveId( employeeLeaveId );
   }

   @Override
   public int insertEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( ( EmployeeContractLeaveDao ) getDao() ).insertEmployeeContractLeave( employeeContractLeaveVO );
   }

   @Override
   public int updateEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      return ( ( EmployeeContractLeaveDao ) getDao() ).updateEmployeeContractLeave( employeeContractLeaveVO );
   }

   @Override
   public int deleteEmployeeContractLeave( final EmployeeContractLeaveVO employeeContractLeaveVO ) throws KANException
   {
      employeeContractLeaveVO.setDeleted( BaseVO.FALSE );
      return ( ( EmployeeContractLeaveDao ) getDao() ).updateEmployeeContractLeave( employeeContractLeaveVO );
   }

   @Override
   public List< Object > getEmployeeContractLeaveVOsByContractId( final String contractId ) throws KANException
   {
      return ( ( EmployeeContractLeaveDao ) getDao() ).getEmployeeContractLeaveVOsByContractId( contractId );
   }

   public PagedListHolder getEmployeeContractLeaveReportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) pagedListHolder.getObject();
      final EmployeeContractVO searchEmployeeContractVO = new EmployeeContractVO();
      searchEmployeeContractVO.setAccountId( employeeContractLeaveReportVO.getAccountId() );
      searchEmployeeContractVO.setCorpId( employeeContractLeaveReportVO.getCorpId() );
      searchEmployeeContractVO.setEmployeeId( employeeContractLeaveReportVO.getEmployeeId() );
      searchEmployeeContractVO.setEmployeeNameZH( employeeContractLeaveReportVO.getEmployeeNameZH() );
      searchEmployeeContractVO.setEmployeeNameEN( employeeContractLeaveReportVO.getEmployeeNameEN() );
      searchEmployeeContractVO.setRulePublic( employeeContractLeaveReportVO.getRulePublic() );
      searchEmployeeContractVO.setRulePrivateIds( employeeContractLeaveReportVO.getRulePrivateIds() );
      searchEmployeeContractVO.setRulePositionIds( employeeContractLeaveReportVO.getRulePositionIds() );
      searchEmployeeContractVO.setRuleBranchIds( employeeContractLeaveReportVO.getRuleBranchIds() );
      searchEmployeeContractVO.setRuleBusinessTypeIds( employeeContractLeaveReportVO.getRuleBusinessTypeIds() );
      searchEmployeeContractVO.setRuleEntityIds( employeeContractLeaveReportVO.getRuleEntityIds() );
      searchEmployeeContractVO.setEmployeeShortName( employeeContractLeaveReportVO.getRemark1() );
      searchEmployeeContractVO.setEmployStatus( employeeContractLeaveReportVO.getEmployStatus() );
      if ( KANUtil.filterEmpty( employeeContractLeaveReportVO.getLeaveStartDate() ) != null && KANUtil.filterEmpty( employeeContractLeaveReportVO.getLeaveEndDate() ) != null )
      {
         searchEmployeeContractVO.setRemark3( employeeContractLeaveReportVO.getLeaveStartDate() + " 00:00:00" );
         searchEmployeeContractVO.setRemark4( employeeContractLeaveReportVO.getLeaveEndDate() + " 23:59:59" );
      }
      pagedListHolder.setHolderSize( this.getEmployeeContractDao().countEmployeeContractVOsByCondition( searchEmployeeContractVO ) );

      List< Object > employeeContractVOs = null;
      if ( isPaged )
      {
         employeeContractVOs = this.getEmployeeContractDao().getEmployeeContractVOsByCondition( searchEmployeeContractVO, new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) );
      }
      else
      {
         employeeContractVOs = this.getEmployeeContractDao().getEmployeeContractVOsByCondition( searchEmployeeContractVO );
      }

      final List< Object > leaveItemVOs = getLeaveItemVOsByAccountId( employeeContractLeaveReportVO.getAccountId(), employeeContractLeaveReportVO.getCorpId() );
      pagedListHolder.setSource( getEmployeeLeaveDetailInfo( employeeContractVOs, leaveItemVOs, employeeContractLeaveReportVO.getLeaveStartDate(), employeeContractLeaveReportVO.getLeaveEndDate() ) );
      return pagedListHolder;
   }

   private List< Object > getEmployeeLeaveDetailInfo( final List< Object > employeeContractVOs, final List< Object > leaveItemVOs, final String leaveStartDate,
         final String leaveEndDate ) throws KANException
   {
      // 初始化返回值
      final List< Object > employeeContractLeaveReportVOs = new ArrayList< Object >();

      String year = KANUtil.formatDate( new Date(), "yyyy" );
      String lastYear = String.valueOf( Integer.valueOf( KANUtil.formatDate( new Date(), "yyyy" ) ) - 1 );

      if ( employeeContractVOs != null && employeeContractVOs.size() > 0 )
      {
         final List< String > itemIds = new ArrayList< String >();
         for ( Object itemVOObject : leaveItemVOs )
         {
            itemIds.add( ( ( ItemVO ) itemVOObject ).getItemId() );
         }

         for ( Object o : employeeContractVOs )
         {
            final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) o;
            final EmployeeContractLeaveReportVO temp = new EmployeeContractLeaveReportVO();
            temp.setAccountId( employeeContractVO.getAccountId() );
            temp.setCorpId( employeeContractVO.getCorpId() );
            temp.setEmployeeId( employeeContractVO.getEmployeeId() );
            temp.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
            temp.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
            temp.setEmployeeShortName( employeeContractVO.getShortName() );
            temp.setContractId( employeeContractVO.getContractId() );
            temp.setOrderId( employeeContractVO.getOrderId() );

            final LeaveHeaderVO leaveHeaderVO = new LeaveHeaderVO();
            leaveHeaderVO.setContractId( employeeContractVO.getContractId() );
            leaveHeaderVO.setItemIds( itemIds );
            leaveHeaderVO.setYear( year );

            if ( KANUtil.filterEmpty( leaveStartDate ) != null && KANUtil.filterEmpty( leaveEndDate ) != null )
            {
               leaveHeaderVO.setEstimateStartDate( leaveStartDate + " 00:00:00" );
               leaveHeaderVO.setEstimateEndDate( leaveEndDate + " 23:59:59" );
            }

            String leaveDetailsMap = this.getLeaveHeaderDao().getMapLeaveDetailsByCondition( leaveHeaderVO );

            // 装载休假情况
            if ( leaveItemVOs != null && leaveItemVOs.size() > 0 )
            {
               final List< EmployeeContractLeaveVO > leaveDetails = new ArrayList< EmployeeContractLeaveVO >();
               for ( Object itemVOObject : leaveItemVOs )
               {
                  final ItemVO itemVO = ( ItemVO ) itemVOObject;
                  final EmployeeContractLeaveVO employeeContractLeaveVO = new EmployeeContractLeaveVO();
                  employeeContractLeaveVO.setItemId( itemVO.getItemId() );
                  employeeContractLeaveVO.setItemNameZH( itemVO.getNameZH() );
                  employeeContractLeaveVO.setItemNameEN( itemVO.getNameEN() );

                  // 已使用休假小时数
                  double usedHours = Double.valueOf( getUsedHoursByItemId( leaveDetailsMap, itemVO.getItemId() ) );
                  // 总设定休假小时数
                  double totalHours = 0.0;
                  // 如果是年假
                  if ( ArrayUtils.contains( new String[] { "41", "48", "49" }, itemVO.getItemId() ) )
                  {
                     final List< Object > employeeContractLeaveVOs = ( ( EmployeeContractLeaveDao ) getDao() ).getEmployeeContractLeaveVOsByContractId( employeeContractVO.getContractId() );
                     if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
                     {
                        for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
                        {
                           final EmployeeContractLeaveVO tempEmployeeContractLeaveVO = ( EmployeeContractLeaveVO ) employeeContractLeaveVOObject;
                           //                           if ( !"41".equals( tempEmployeeContractLeaveVO.getItemId() ) )
                           //                              continue;

                           if ( "41".equals( itemVO.getItemId() ) && year.equals( tempEmployeeContractLeaveVO.getYear() ) )
                           {
                              totalHours = Double.valueOf( tempEmployeeContractLeaveVO.getBenefitQuantity() ) + Double.valueOf( tempEmployeeContractLeaveVO.getLegalQuantity() );
                              break;
                           }
                           else if ( "48".equals( itemVO.getItemId() ) && lastYear.equals( tempEmployeeContractLeaveVO.getYear() ) )
                           {
                              totalHours = Double.valueOf( tempEmployeeContractLeaveVO.getLegalQuantity() );
                              break;
                           }
                           else if ( "49".equals( itemVO.getItemId() ) && lastYear.equals( tempEmployeeContractLeaveVO.getYear() ) )
                           {
                              totalHours = Double.valueOf( tempEmployeeContractLeaveVO.getBenefitQuantity() );
                              break;
                           }
                        }
                     }

                     employeeContractLeaveVO.setLeftBenefitQuantity( String.valueOf( totalHours - usedHours ) );
                  }

                  employeeContractLeaveVO.setBenefitQuantity( String.valueOf( totalHours ) );
                  employeeContractLeaveVO.setUsedBenefitQuantity( String.valueOf( usedHours ) );

                  leaveDetails.add( employeeContractLeaveVO );
               }

               temp.setLeaveDetails( leaveDetails );
            }

            employeeContractLeaveReportVOs.add( temp );
         }
      }

      return employeeContractLeaveReportVOs;
   }

   // 获取请假类型科目列表
   private List< Object > getLeaveItemVOsByAccountId( final String accountId, final String corpId ) throws KANException
   {
      final List< Object > results = new ArrayList< Object >();
      final ItemVO tempItemVO = new ItemVO();
      tempItemVO.setAccountId( accountId );
      tempItemVO.setCorpId( corpId );
      tempItemVO.setItemType( "6" );
      tempItemVO.setStatus( "1" );
      tempItemVO.setSortColumn( "itemId" );
      tempItemVO.setSortOrder( "ASC" );
      results.addAll( this.getItemDao().getItemVOsByCondition( tempItemVO ) );
      // 加班换休也是一种休假
      results.add( this.getItemDao().getItemVOByItemId( "25" ) );
      return results;
   }

   @Override
   // 导出雇员年假情况报表
   public XSSFWorkbook exportAnnualLeaveDetails( List< MappingVO > titleMappingVOs, PagedListHolder pagedListHolder, final Locale locale ) throws KANException
   {
      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      // 创建字体
      final XSSFFont font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont( font );
      cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

      // 创建单元格样式
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont( font );
      cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      // 创建单元格样式
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont( font );
      cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

      final XSSFCellStyle cellStyleCenterRed = workbook.createCellStyle();
      cellStyleCenterRed.setFillForegroundColor( new XSSFColor( Color.decode( "#FF0000" ) ) );
      cellStyleCenterRed.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
      cellStyleCenterRed.setFont( font );
      cellStyleCenterRed.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      // 创建表格
      final XSSFSheet sheet = workbook.createSheet();
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth( 15 );
      // 生成Excel Header Row
      final XSSFRow rowHeaderOne = sheet.createRow( 0 );
      final XSSFRow rowHeaderTwo = sheet.createRow( 1 );
      // 用以标识Header列序号
      int headerColumnIndex = 0;

      // 遍历表头
      for ( MappingVO tempMappingVO : titleMappingVOs )
      {
         if ( "lastLeaveDetails".equals( tempMappingVO.getMappingId() ) || "thisLeaveDetails".equals( tempMappingVO.getMappingId() ) )
         {
            final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
            cell.setCellValue( tempMappingVO.getMappingValue() );
            cell.setCellStyle( cellStyleCenter );
            sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + 2 ) );

            final XSSFCell cellOne = rowHeaderTwo.createCell( headerColumnIndex );
            cellOne.setCellValue( KANUtil.getProperty( locale, "annual.leave.report.totalHours" ) );
            cellOne.setCellStyle( cellStyleCenter );
            headerColumnIndex++;

            final XSSFCell cellTwo = rowHeaderTwo.createCell( headerColumnIndex );
            cellTwo.setCellValue( KANUtil.getProperty( locale, "annual.leave.report.probationUsing" ) );
            cellTwo.setCellStyle( cellStyleCenter );
            headerColumnIndex++;

            final XSSFCell cellThree = rowHeaderTwo.createCell( headerColumnIndex );
            cellThree.setCellValue( KANUtil.getProperty( locale, "annual.leave.report.delayMonth" ) );
            cellThree.setCellStyle( cellStyleCenter );
            headerColumnIndex++;
         }
         else
         {
            final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
            cell.setCellValue( tempMappingVO.getMappingValue() );
            cell.setCellStyle( cellStyleLeft );
            sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
            headerColumnIndex++;
         }

      }

      // 存在数据源
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         // 用以标识Body行序号
         int bodyRowIndex = 2;
         // 遍历行
         for ( Object object : pagedListHolder.getSource() )
         {
            final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) object;
            // 用以标识Body列序号
            int bodyColumnIndex = 0;
            // 生成Excel Body Row
            final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

            // 遍历表头
            for ( MappingVO tempMappingVO : titleMappingVOs )
            {
               if ( "lastLeaveDetails".equals( tempMappingVO.getMappingId() ) )
               {
                  final XSSFCell cell1 = rowBody.createCell( bodyColumnIndex );
                  cell1.setCellValue( employeeContractLeaveReportVO.getLastYearTotalHours() );
                  cell1.setCellStyle( cellStyleRight );
                  bodyColumnIndex++;
                  final XSSFCell cell2 = rowBody.createCell( bodyColumnIndex );
                  cell2.setCellValue( employeeContractLeaveReportVO.getDecodeLastYearProbationUsing() );
                  cell2.setCellStyle( cellStyleCenter );
                  bodyColumnIndex++;
                  final XSSFCell cell3 = rowBody.createCell( bodyColumnIndex );
                  cell3.setCellValue( employeeContractLeaveReportVO.getLastYearDelayMonth() );
                  cell3.setCellStyle( cellStyleRight );
                  bodyColumnIndex++;
               }
               else if ( "thisLeaveDetails".equals( tempMappingVO.getMappingId() ) )
               {
                  final XSSFCell cell1 = rowBody.createCell( bodyColumnIndex );
                  cell1.setCellValue( employeeContractLeaveReportVO.getThisYearTotalHours() );
                  cell1.setCellStyle( cellStyleRight );
                  bodyColumnIndex++;
                  final XSSFCell cell2 = rowBody.createCell( bodyColumnIndex );
                  cell2.setCellValue( employeeContractLeaveReportVO.getDecodeThisYearProbationUsing() );
                  cell2.setCellStyle( cellStyleCenter );
                  bodyColumnIndex++;
                  final XSSFCell cell3 = rowBody.createCell( bodyColumnIndex );
                  cell3.setCellValue( employeeContractLeaveReportVO.getThisYearDelayMonth() );
                  cell3.setCellStyle( cellStyleRight );
                  bodyColumnIndex++;
               }
               else
               {
                  final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                  cell.setCellValue( String.valueOf( KANUtil.getValue( employeeContractLeaveReportVO, tempMappingVO.getMappingId() ) ) );
                  cell.setCellStyle( cellStyleLeft );
                  bodyColumnIndex++;
               }
            }

            bodyRowIndex++;
         }
      }

      return workbook;
   }

   @Override
   // 导出雇员休假情况报表
   /** Added by siuvan @2014-10-31 */
   public XSSFWorkbook exportEmployeeContractLeaveReport( final List< MappingVO > titleMappingVOs, final PagedListHolder pagedListHolder, final String lang ) throws KANException
   {
      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      // 创建字体
      final XSSFFont font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont( font );
      cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );

      // 创建单元格样式
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont( font );
      cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      // 创建单元格样式
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont( font );
      cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );

      final XSSFCellStyle cellStyleCenterRed = workbook.createCellStyle();
      cellStyleCenterRed.setFillForegroundColor( new XSSFColor( Color.decode( "#FF0000" ) ) );
      cellStyleCenterRed.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
      cellStyleCenterRed.setFont( font );
      cellStyleCenterRed.setAlignment( XSSFCellStyle.ALIGN_CENTER );

      // 创建表格
      final XSSFSheet sheet = workbook.createSheet();
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth( 15 );
      // 生成Excel Header Row
      final XSSFRow rowHeaderOne = sheet.createRow( 0 );
      final XSSFRow rowHeaderTwo = sheet.createRow( 1 );
      // 用以标识Header列序号
      int headerColumnIndex = 0;

      final DecimalFormat hoursFormat = new DecimalFormat( "0.0" );
      final String[] split_cell_item_arr = new String[] { "41", "48", "49" };
      try
      {
         // 遍历表头
         for ( MappingVO tempMappingVO : titleMappingVOs )
         {
            final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
            cell.setCellValue( tempMappingVO.getMappingValue() );
            cell.setCellStyle( cellStyleLeft );
            sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
            headerColumnIndex++;
         }

         // 存在数据源
         if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) pagedListHolder.getSource().get( 0 );
            if ( employeeContractLeaveReportVO.getLeaveDetails() != null && employeeContractLeaveReportVO.getLeaveDetails().size() > 0 )
            {
               for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveReportVO.getLeaveDetails() )
               {
                  // 如果是年假，年假（去年）
                  if ( ArrayUtils.contains( split_cell_item_arr, employeeContractLeaveVO.getItemId() ) )
                  {
                     final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
                     final XSSFCell cellOne = rowHeaderTwo.createCell( headerColumnIndex );
                     final XSSFCell cellTwo = rowHeaderTwo.createCell( headerColumnIndex + 1 );

                     cell.setCellValue( lang.equalsIgnoreCase( "zh" ) ? employeeContractLeaveVO.getItemNameZH() : employeeContractLeaveVO.getItemNameEN() );
                     cellOne.setCellValue( lang.equalsIgnoreCase( "zh" ) ? "合计" : "Total" );
                     cellTwo.setCellValue( lang.equalsIgnoreCase( "zh" ) ? "剩余" : "Left" );
                     cell.setCellStyle( cellStyleCenter );
                     cellOne.setCellStyle( cellStyleCenter );
                     cellTwo.setCellStyle( cellStyleCenter );
                     sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + 1 ) );
                     sheet.setColumnWidth( headerColumnIndex, 2000 );
                     sheet.setColumnWidth( headerColumnIndex + 1, 2000 );
                     headerColumnIndex += 2;
                  }
                  else
                  {
                     final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
                     cell.setCellValue( lang.equalsIgnoreCase( "zh" ) ? employeeContractLeaveVO.getItemNameZH() : employeeContractLeaveVO.getItemNameEN() );
                     cell.setCellStyle( cellStyleCenter );
                     sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
                     headerColumnIndex++;
                  }
               }
            }
         }

         // 存在数据源
         if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // 用以标识Body行序号
            int bodyRowIndex = 2;
            // 遍历行
            for ( Object object : pagedListHolder.getSource() )
            {
               final EmployeeContractLeaveReportVO employeeContractLeaveReportVO = ( EmployeeContractLeaveReportVO ) object;
               // 用以标识Body列序号
               int bodyColumnIndex = 0;
               // 生成Excel Body Row
               final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

               // 遍历表头
               for ( MappingVO tempMappingVO : titleMappingVOs )
               {
                  final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                  cell.setCellValue( String.valueOf( KANUtil.getValue( employeeContractLeaveReportVO, tempMappingVO.getMappingId() ) ) );
                  cell.setCellStyle( cellStyleLeft );
                  bodyColumnIndex++;
               }

               // 遍历请假科目
               if ( employeeContractLeaveReportVO.getLeaveDetails() != null && employeeContractLeaveReportVO.getLeaveDetails().size() > 0 )
               {
                  for ( EmployeeContractLeaveVO employeeContractLeaveVO : employeeContractLeaveReportVO.getLeaveDetails() )
                  {
                     // 如果是年假，年假（去年）
                     if ( ArrayUtils.contains( split_cell_item_arr, employeeContractLeaveVO.getItemId() ) )
                     {
                        final XSSFCell cellOne = rowBody.createCell( bodyColumnIndex );
                        final XSSFCell cellTwo = rowBody.createCell( bodyColumnIndex + 1 );
                        cellOne.setCellValue( hoursFormat.format( Double.valueOf( employeeContractLeaveVO.getBenefitQuantity() ) ) );
                        cellTwo.setCellValue( hoursFormat.format( Double.valueOf( employeeContractLeaveVO.getLeftBenefitQuantity() ) ) );

                        // 年假小于"0"的标红
                        if ( cellTwo.getStringCellValue() != null && Double.valueOf( cellTwo.getStringCellValue() ) <= 0 )
                           cellTwo.setCellStyle( cellStyleCenterRed );
                        else
                           cellTwo.setCellStyle( cellStyleRight );
                        bodyColumnIndex += 2;
                     }
                     else
                     {
                        final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                        cell.setCellValue( hoursFormat.format( Double.valueOf( employeeContractLeaveVO.getUsedBenefitQuantity() ) ) );
                        bodyColumnIndex++;
                     }
                  }
               }

               bodyRowIndex++;
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return workbook;
   }

   // 根据itemId获取请假小时数
   private String getUsedHoursByItemId( final String leaveDetailsMap, final String itemId )
   {
      if ( KANUtil.filterEmpty( leaveDetailsMap ) == null || KANUtil.filterEmpty( itemId ) == null )
         return "0.0";

      for ( String leaveDetail : leaveDetailsMap.split( ":" ) )
      {
         String[] leaveDetailArray = leaveDetail.split( "_" );
         if ( leaveDetailArray.length == 2 && itemId.equals( leaveDetailArray[ 0 ] ) )
            return leaveDetailArray[ 1 ];
      }

      return "0.0";
   }

   @Override
   public PagedListHolder getAnnualLeaveDetailsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeContractLeaveDao employeeContractLeaveDao = ( EmployeeContractLeaveDao ) getDao();
      final EmployeeContractLeaveReportVO object = ( EmployeeContractLeaveReportVO ) pagedListHolder.getObject();
      pagedListHolder.setHolderSize( employeeContractLeaveDao.countAnnualLeaveDetailsByCondition( object ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeContractLeaveDao.getAnnualLeaveDetailsByCondition( object, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeContractLeaveDao.getAnnualLeaveDetailsByCondition( object ) );
      }

      return pagedListHolder;
   }
}
