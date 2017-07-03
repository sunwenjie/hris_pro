package com.kan.hro.service.impl.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.POIUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeReportDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.service.inf.biz.employee.EmployeeReportService;

public class EmployeeReportServiceImpl extends ContextService implements EmployeeReportService
{
   // 模板第14行开始写入数据
   private static final int FIRST_ROW_INDEX = 13;

   // 模板中对应的公式
   private static Map< String, String > FORMULA_MAP = new HashMap< String, String >();

   static
   {
      FORMULA_MAP.put( "AF", "ROUND(AE00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "AG", "AE00*12" );
      FORMULA_MAP.put( "AH", "ROUND(AG00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "AK", "AG00+AI00+AJ00" );
      FORMULA_MAP.put( "AL", "ROUND(AK00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "AR", "AK00+AP00" );
      FORMULA_MAP.put( "AS", "ROUND(AR00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "AV", "IFERROR(IF(AU00=\"P\",HLOOKUP(AD00,'YERR rule'!$G$18:$J$26,MATCH(AT00,'YERR rule'!$B$18:$B$26),0),HLOOKUP(AD00,'YERR rule'!$B$18:$F$26,MATCH(AT00,'YERR rule'!$B$18:$B$26),0)),\"\")" );
      FORMULA_MAP.put( "AX", "AR00*AW00" );
      FORMULA_MAP.put( "AY", "ROUND(AX00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "AZ", "BB00/12" );
      FORMULA_MAP.put( "BA", "ROUND(AZ00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "BB", "AW00*AG00" );
      FORMULA_MAP.put( "BC", "ROUND(BB00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "BD", "+AI00*AW00" );
      FORMULA_MAP.put( "BE", "+AJ00*AW00" );
      FORMULA_MAP.put( "BF", "BB00+BD00+BE00" );
      FORMULA_MAP.put( "BG", "ROUND(BF00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
      FORMULA_MAP.put( "BN", "IFERROR(VLOOKUP(BM00,'Job grade'!$A$2:$B$16,2,0),\"\")" );
      FORMULA_MAP.put( "BR", "IFERROR(VLOOKUP(AT00,'YERR rule'!$G$19:$K$26,5,0),\"\")" );
      FORMULA_MAP.put( "BT", "+AE00/365*BZ00*BS00" );
      FORMULA_MAP.put( "BU", "ROUND(BT00*VLOOKUP($AD00,'Exchange rate'!$A$3:$B$6,2,0),2)" );
   }

   private EmployeeContractSalaryDao employeeContractSalaryDao;

   public EmployeeContractSalaryDao getEmployeeContractSalaryDao()
   {
      return employeeContractSalaryDao;
   }

   public void setEmployeeContractSalaryDao( EmployeeContractSalaryDao employeeContractSalaryDao )
   {
      this.employeeContractSalaryDao = employeeContractSalaryDao;
   }

   @Override
   public PagedListHolder getEmployeeReportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeReportDao employeeReportDao = ( EmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( employeeReportDao.countEmployeeReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeeReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeeReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      }
      if ( "full".equals( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getRt() ) )
      {
         fetchSalaryItems( pagedListHolder, ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalarys() );
      }
      return pagedListHolder;
   }

   @Override
   public PagedListHolder getEmployeeSalaryReportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged, List< MappingVO > itemVOs ) throws KANException
   {

      final EmployeeReportDao employeeReportDao = ( EmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( employeeReportDao.countEmployeeSalaryReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeeSalaryReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeeSalaryReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      }

      fetchSalaryItems( pagedListHolder, itemVOs );
      return pagedListHolder;
   }

   private void fetchSalaryItems( final PagedListHolder pagedListHolder, final List< MappingVO > itemVOs ) throws KANException
   {
      // 收集contractIds 
      final List< String > contractIds = new ArrayList< String >();

      if ( pagedListHolder != null && pagedListHolder.getHolderSize() > 0 )
      {
         final List< Object > employeeReportList = pagedListHolder.getSource();

         if ( employeeReportList != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object employeeReport : employeeReportList )
            {
               final EmployeeReportVO tempEmployeeReport = ( EmployeeReportVO ) employeeReport;
               if ( !contractIds.contains( tempEmployeeReport.getContractId() ) )
               {
                  contractIds.add( tempEmployeeReport.getContractId() );
               }
            }

            final List< Object > listEmployeeContractSalary = new ArrayList< Object >();

            final List< MappingVO > items = new ArrayList< MappingVO >();
            if ( contractIds != null && contractIds.size() > 0 )
            {
               final EmployeeContractSalaryVO employeeContractSalaryVO = new EmployeeContractSalaryVO();

               employeeContractSalaryVO.getContractIds().addAll( contractIds );
               employeeContractSalaryVO.setAccountId( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getAccountId() );
               employeeContractSalaryVO.setCorpId( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getCorpId() );
               employeeContractSalaryVO.setSalaryStartDateStart( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalaryStartDateStart() );
               employeeContractSalaryVO.setSalaryStartDateEnd( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalaryStartDateEnd() );
               employeeContractSalaryVO.setSalaryEndDateStart( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalaryEndDateStart() );
               employeeContractSalaryVO.setSalaryEndDateEnd( ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalaryEndDateEnd() );

               listEmployeeContractSalary.addAll( employeeContractSalaryDao.getEmployeeContractSalaryVOsByContractIds( employeeContractSalaryVO ) );
               if ( listEmployeeContractSalary != null && listEmployeeContractSalary.size() > 0 )
               {
                  for ( Object object : listEmployeeContractSalary )
                  {
                     final EmployeeContractSalaryVO employeeContractSalary = ( EmployeeContractSalaryVO ) object;
                     //组成动态列
                     for ( MappingVO itemVO : itemVOs )
                     {
                        if ( itemVO != null && !items.contains( itemVO ) && employeeContractSalary.getItemId().equals( itemVO.getMappingId() ) )
                        {
                           items.add( itemVO );
                           continue;
                        }
                     }
                     //赋值相应的对象
                     for ( Object employeeReport : employeeReportList )
                     {
                        final EmployeeReportVO tempEmployeeReport = ( EmployeeReportVO ) employeeReport;

                        if ( tempEmployeeReport.getContractId() != null && employeeContractSalary.getContractId() != null
                              && tempEmployeeReport.getContractId().equals( employeeContractSalary.getContractId() ) )
                        {
                           final MappingVO salary = new MappingVO();
                           salary.setMappingId( employeeContractSalary.getItemId() );
                           final ItemVO tmpItemVO = KANConstants.getKANAccountConstants((( EmployeeReportVO ) pagedListHolder.getObject() ).getAccountId()).getItemVOByItemId(employeeContractSalary.getItemId());
                           if(tmpItemVO!=null && (tmpItemVO.getNameZH().contains("%") || tmpItemVO.getNameEN().contains("%"))){
                             salary.setMappingValue( KANUtil.formatNumberN(Double.parseDouble(employeeContractSalary.getBase()),3));
                           }else{
                             salary.setMappingValue( KANUtil.formatNumber( employeeContractSalary.getBase(), ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getAccountId() ) );
                           }
                           tempEmployeeReport.getSalarys().add( salary );
                           continue;
                        }
                     }
                  }
                  ( ( EmployeeReportVO ) pagedListHolder.getObject() ).setSalarys( items );
               }
            }
         }
      }
   }

   /**
    * 格式化excel
    * 
    */
   @Override
   public XSSFWorkbook EmployeeSalaryReport( final PagedListHolder employeeReportHolder, EmployeeReportVO employeeReportVO ) throws KANException
   {

      final List< MappingVO > items = ( ( EmployeeReportVO ) employeeReportHolder.getObject() ).getSalarys();

      // 初始化工作薄
      final XSSFWorkbook workbook = new XSSFWorkbook();

      // 创建字体
      final XSSFFont font = workbook.createFont();
      font.setFontName( "Calibri" );
      font.setFontHeightInPoints( ( short ) 11 );

      // 创建单元格样式
      final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
      cellStyleLeft.setFont( font );
      cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

      // 创建单元格样式
      final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
      cellStyleCenter.setFont( font );
      cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

      // 创建单元格样式
      final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
      cellStyleRight.setFont( font );
      cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

      // 创建表格
      final XSSFSheet sheet = workbook.createSheet();
      // 设置表格默认列宽度为15个字节
      sheet.setDefaultColumnWidth( 15 );

      // 生成Excel Header Row
      final XSSFRow rowHeaderOne = sheet.createRow( 0 );

      // 用以标识Header列序号
      int headerColumnIndex = 0;

      try
      {

         // 遍历Excel Header
         for ( String tabTitle : employeeReportVO.getTitleNameList().split( "," ) )
         {

            final XSSFCell cell = rowHeaderOne.createCell( headerColumnIndex );
            cell.setCellValue( tabTitle );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
         }

         for ( int i = 0; i < items.size(); i++ )
         {
            final MappingVO item = ( MappingVO ) items.get( i );
            final XSSFCell cellOne = rowHeaderOne.createCell( headerColumnIndex );
            cellOne.setCellValue( item.getMappingValue() );
            cellOne.setCellStyle( cellStyleCenter );
            headerColumnIndex++;
         }

         // 遍历生成Excel Body
         if ( employeeReportHolder.getSource() != null && employeeReportHolder.getSource().size() > 0 )
         {
            // 用以标识Body行序号
            int bodyRowIndex = 1;
            // 遍历行
            for ( Object object : employeeReportHolder.getSource() )
            {
               final EmployeeReportVO tempEmployeeReportVO = ( EmployeeReportVO ) object;
               // 生成Excel Body Row
               final XSSFRow rowBody = sheet.createRow( bodyRowIndex );
               // 用以标识Body列序号
               int bodyColumnIndex = 0;

               for ( String tabTitleId : employeeReportVO.getTitleIdList().split( "," ) )
               {
                  rowBody.createCell( bodyColumnIndex ).setCellValue( KANUtil.getFilterEmptyValue( tempEmployeeReportVO, tabTitleId ) );
                  bodyColumnIndex++;
               }

               for ( int i = 0; i < items.size(); i++ )
               {
                  final MappingVO item = ( MappingVO ) items.get( i );
                  boolean hasitem = false;
                  final XSSFCell cell = rowBody.createCell( bodyColumnIndex );
                  /*    for ( Object obj : ListEmployeeContractSalary )
                      {
                         EmployeeContractSalaryVO employeeContractSalary = ( EmployeeContractSalaryVO ) obj;
                         if ( employeeContractSalary != null && employeeContractSalary.getItemId() != null && employeeContractSalary.getItemId().equals( item.getMappingId() )
                               && employeeContractSalary.getContractId().equals( tempEmployeeReportVO.getContractId() ) )
                         {
                            cell.setCellValue( employeeContractSalary.getBase() );
                            hasitem = true;
                            continue;
                         }
                      }*/

                  for ( MappingVO salary : tempEmployeeReportVO.getSalarys() )
                  {
                     if ( salary.getMappingId().equals( item.getMappingId() ) )
                     {
                        cell.setCellValue( salary.getMappingValue() );
                        hasitem = true;
                        continue;
                     }
                  }

                  if ( !hasitem )
                  {
                     cell.setCellValue( 0 );
                  }
                  cell.setCellStyle( cellStyleCenter );
                  bodyColumnIndex++;
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

   @Override
   public PagedListHolder getEmployeePerformanceReportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeReportDao employeeReportDao = ( EmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( employeeReportDao.countEmployeePerformanceReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeePerformanceReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeReportDao.getEmployeePerformanceReportVOsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      }

      fetchSalaryItems( pagedListHolder, ( ( EmployeeReportVO ) pagedListHolder.getObject() ).getSalarys() );

      return pagedListHolder;
   }

   @Override
   public XSSFWorkbook generatePerformanceReport( final PagedListHolder pagedListHolder ) throws KANException
   {
      // 初始化工程目录的模板路径
      final String path = KANUtil.basePath + File.separator + "performanceReportTemplate.xlsx";
      // 创建File
      final File file = new File( path );

      try
      {
         // 存在模板路径
         if ( file.exists() )
         {
            final EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) pagedListHolder.getObject();
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            final XSSFSheet sheet = workbook.getSheetAt( 0 );

            // 将数据库中的汇率更新在模板上
            setExchangeRate( workbook, employeeReportVO.getAccountId(), employeeReportVO.getCorpId() );

            // 单元格样式靠左
            final XSSFCellStyle leftCellStyle = workbook.createCellStyle();
            leftCellStyle.setAlignment( HSSFCellStyle.VERTICAL_CENTER );
            leftCellStyle.setAlignment( HSSFCellStyle.ALIGN_LEFT );

            // 单元格样式居中
            final XSSFCellStyle rightCellStyle = workbook.createCellStyle();
            rightCellStyle.setAlignment( HSSFCellStyle.VERTICAL_CENTER );
            rightCellStyle.setAlignment( HSSFCellStyle.ALIGN_RIGHT );

            // 单元格样式靠右
            final XSSFCellStyle centerCellStyle = workbook.createCellStyle();
            centerCellStyle.setAlignment( HSSFCellStyle.VERTICAL_CENTER );
            centerCellStyle.setAlignment( HSSFCellStyle.ALIGN_CENTER );

            // 日期格式样式
            final XSSFCellStyle dateCellStyle = ( XSSFCellStyle ) leftCellStyle.clone();
            dateCellStyle.setDataFormat( workbook.createDataFormat().getFormat( "yyyy/MM/dd" ) );

            // 保留两位小数样式
            final XSSFCellStyle accuracyTwoCellStyle = ( XSSFCellStyle ) rightCellStyle.clone();
            accuracyTwoCellStyle.setDataFormat( HSSFDataFormat.getBuiltinFormat( "0.00" ) );

            // 保留三位小数样式
            final XSSFCellStyle accuracyThreeCellStyle = ( XSSFCellStyle ) rightCellStyle.clone();
            accuracyThreeCellStyle.setDataFormat( workbook.createDataFormat().getFormat( "0.000" ) );

            // 两位小数百分数样式
            final XSSFCellStyle percentCellStyle = workbook.createCellStyle();
            percentCellStyle.setDataFormat( HSSFDataFormat.getBuiltinFormat( "0.00%" ) );

            // 黄色背景样式
            final XSSFCellStyle yellowCellStyle = ( XSSFCellStyle ) centerCellStyle.clone();
            yellowCellStyle.setFillForegroundColor( IndexedColors.YELLOW.getIndex() );
            yellowCellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

            // 红色背景样式
            final XSSFCellStyle redCellStyle = ( XSSFCellStyle ) accuracyTwoCellStyle.clone();
            redCellStyle.setFillForegroundColor( IndexedColors.RED.getIndex() );
            redCellStyle.setFillPattern( CellStyle.SOLID_FOREGROUND );

            // 模板中的下拉框
            final String[] yesOrNoArray = new String[] { "Y", "N" };
            final String[] ratingArray = new String[] { "5", "4.5", "4", "3.5", "3", "2.5", "2", "1" };
            final String[] promotionTypeArray = new String[] { "P", "R", "N" };
            // 下拉框引用源
            final String jobGardeFormula = "Job_Grade";

            if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( int i = 0; i < pagedListHolder.getSource().size(); i++ )
               {
                  final EmployeeReportVO tempEmployeeReportVO = ( EmployeeReportVO ) pagedListHolder.getSource().get( i );

                  final XSSFRow tempRow = sheet.createRow( FIRST_ROW_INDEX + i );

                  int columnIndex = 0;

                  final XSSFCell employeeIdCell = tempRow.createCell( columnIndex );
                  employeeIdCell.setCellValue( tempEmployeeReportVO.getEmployeeId() );
                  employeeIdCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell yearlyCell = tempRow.createCell( columnIndex );
                  yearlyCell.setCellValue( "2015" );
                  yearlyCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell fullNameCell = tempRow.createCell( columnIndex );
                  fullNameCell.setCellValue( tempEmployeeReportVO.getNameEN() );
                  fullNameCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell shortNameCell = tempRow.createCell( columnIndex );
                  shortNameCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jiancheng" ) ) );
                  shortNameCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell chineseNameCell = tempRow.createCell( columnIndex );
                  chineseNameCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getNameZH() ) );
                  chineseNameCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell entityNameENCell = tempRow.createCell( columnIndex );
                  entityNameENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityNameEN() ) );
                  entityNameENCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell entityNameZHCell = tempRow.createCell( columnIndex );
                  entityNameZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityNameZH() ) );
                  entityNameZHCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell entityTitleCell = tempRow.createCell( columnIndex );
                  entityTitleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityTitle() ) );
                  entityTitleCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell bUFunctionENCell = tempRow.createCell( columnIndex );
                  bUFunctionENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getParentBranchNameEN() ) );
                  bUFunctionENCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell bUFunctionZHCell = tempRow.createCell( columnIndex );
                  bUFunctionZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getParentBranchNameZH() ) );
                  bUFunctionZHCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell branchNameENCell = tempRow.createCell( columnIndex );
                  branchNameENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getBranchNameEN() ) );
                  branchNameENCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell branchNameZHCell = tempRow.createCell( columnIndex );
                  branchNameZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getBranchNameZH() ) );
                  branchNameZHCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell costCenterCell = tempRow.createCell( columnIndex );
                  costCenterCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecodeSettlementBranch() ) );
                  costCenterCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell functionCodeCell = tempRow.createCell( columnIndex );
                  functionCodeCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecodeBusinessType() ) );
                  functionCodeCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell bangongdidianCell = tempRow.createCell( columnIndex );
                  bangongdidianCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "bangongdidian" ) ) );
                  bangongdidianCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell jobroleCell = tempRow.createCell( columnIndex );
                  jobroleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jobrole" ) ) );
                  jobroleCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell zhiweimingchengyingwenCell = tempRow.createCell( columnIndex );
                  zhiweimingchengyingwenCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "zhiweimingchengyingwen" ) ) );
                  zhiweimingchengyingwenCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell neibuchengweiCell = tempRow.createCell( columnIndex );
                  neibuchengweiCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );
                  neibuchengweiCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell jobGradeCell = tempRow.createCell( columnIndex );
                  jobGradeCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempPositionGradeIds() ) );
                  jobGradeCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell innerTitleCell = tempRow.createCell( columnIndex );
                  innerTitleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );
                  innerTitleCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell yewuhuibaoxianjingliCell = tempRow.createCell( columnIndex );
                  yewuhuibaoxianjingliCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "yewuhuibaoxianjingli" ) ) );
                  yewuhuibaoxianjingliCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell lineManagerCell = tempRow.createCell( columnIndex );
                  lineManagerCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempParentPositionOwners() ) );
                  lineManagerCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell startWorkDateCell = tempRow.createCell( columnIndex );
                  startWorkDateCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getStartWorkDate() ) );
                  startWorkDateCell.setCellStyle( dateCellStyle );

                  columnIndex++;
                  final XSSFCell contractStartDateCell = tempRow.createCell( columnIndex );
                  contractStartDateCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getContractStartDate() ) );
                  contractStartDateCell.setCellStyle( dateCellStyle );

                  columnIndex++;
                  final XSSFCell gupiaoshuliangCell = tempRow.createCell( columnIndex );
                  //gupiaoshuliangCell.setCellValue
                  gupiaoshuliangCell.setCellStyle( rightCellStyle );

                  columnIndex++;
                  final XSSFCell performanceRating2013Cell = tempRow.createCell( columnIndex );
                  performanceRating2013Cell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jixiaodengji" ) ) );
                  performanceRating2013Cell.setCellStyle( rightCellStyle );

                  columnIndex++;
                  final XSSFCell promotionYERR2013Cell = tempRow.createCell( columnIndex );
                  //promotionYERR2013Cell.setCellValue(  );
                  promotionYERR2013Cell.setCellStyle( rightCellStyle );
                  POIUtil.addValidationData( workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "" );

                  columnIndex++;
                  final XSSFCell promotionReviewMidYear2014Cell = tempRow.createCell( columnIndex );
                  //promotionReviewMidYear2014Cell.setCellValue(  );
                  promotionReviewMidYear2014Cell.setCellStyle( rightCellStyle );
                  POIUtil.addValidationData( workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "" );

                  columnIndex++;
                  final XSSFCell salaryIncreaseReviewMidYear2014 = tempRow.createCell( columnIndex );
                  //salaryIncreaseReviewMidYear2014.setCellValue(  );
                  salaryIncreaseReviewMidYear2014.setCellStyle( percentCellStyle );

                  columnIndex++;
                  final XSSFCell bizhongCell = tempRow.createCell( columnIndex );
                  bizhongCell.setCellValue( tempEmployeeReportVO.getCurrencyCode() );
                  bizhongCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell baseMonthlySalaryLocalCell = tempRow.createCell( columnIndex );
                  baseMonthlySalaryLocalCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getMontnlySalary() ) );
                  baseMonthlySalaryLocalCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell baseMonthlySalaryUSDCell = tempRow.createCell( columnIndex );
                  //baseMonthlySalaryUSDCell.setCellValue(  );
                  baseMonthlySalaryUSDCell.setCellStyle( accuracyTwoCellStyle );
                  baseMonthlySalaryUSDCell.setCellType( XSSFCell.CELL_TYPE_FORMULA );
                  baseMonthlySalaryUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell annualSalaryLocalCell = tempRow.createCell( columnIndex );
                  //annualSalaryLocalCell.setCellValue(  );
                  annualSalaryLocalCell.setCellStyle( accuracyTwoCellStyle );
                  annualSalaryLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell annualSalaryUSDCell = tempRow.createCell( columnIndex );
                  //annualSalaryUSDCell.setCellValue(  );
                  annualSalaryUSDCell.setCellStyle( accuracyTwoCellStyle );
                  annualSalaryUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell housingAllowanceCell = tempRow.createCell( columnIndex );
                  housingAllowanceCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getHousingAllowance() ) );
                  housingAllowanceCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell childenEducationAllowanceCell = tempRow.createCell( columnIndex );
                  childenEducationAllowanceCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getChildenEducationAllowance() ) );
                  childenEducationAllowanceCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell annualGuaranteedCashLocalCell = tempRow.createCell( columnIndex );
                  //annualGuaranteedCashLocalCell.setCellValue(  );
                  annualGuaranteedCashLocalCell.setCellStyle( accuracyTwoCellStyle );
                  annualGuaranteedCashLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell annualGuaranteedCashUSDCell = tempRow.createCell( columnIndex );
                  //annualGuaranteedCashUSDCell.setCellValue(  );
                  annualGuaranteedCashUSDCell.setCellStyle( accuracyTwoCellStyle );
                  annualGuaranteedCashUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  // Column Index AM(月度奖金)
                  columnIndex++;
                  final XSSFCell monthlyTargetCell = tempRow.createCell( columnIndex );
                  monthlyTargetCell.setCellValue( tempEmployeeReportVO.getMonthlyTarget() );
                  monthlyTargetCell.setCellStyle( accuracyTwoCellStyle );

                  // Column Index AN(季度奖金)
                  columnIndex++;
                  final XSSFCell quarterlyTargetCell = tempRow.createCell( columnIndex );
                  quarterlyTargetCell.setCellValue( tempEmployeeReportVO.getQuarterlyTarget() );
                  quarterlyTargetCell.setCellStyle( accuracyTwoCellStyle );

                  // Column Index AN(销售提成)
                  columnIndex++;
                  final XSSFCell gpTargetCell = tempRow.createCell( columnIndex );
                  gpTargetCell.setCellValue( tempEmployeeReportVO.getBonusRebate() );
                  gpTargetCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell targetValueLocalCell = tempRow.createCell( columnIndex );
                  //targetValueLocalCell.setCellValue(  ); 
                  targetValueLocalCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell targetValueUSDCell = tempRow.createCell( columnIndex );
                  //targetValueUSDCell.setCellValue(  ); 
                  targetValueUSDCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell ttcLocalCell = tempRow.createCell( columnIndex );
                  //ttcLocalCell.setCellValue(  ); 
                  ttcLocalCell.setCellStyle( accuracyTwoCellStyle );
                  ttcLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell ttcUSDCell = tempRow.createCell( columnIndex );
                  //ttcUSDCell.setCellValue(  ); 
                  ttcUSDCell.setCellStyle( accuracyTwoCellStyle );
                  ttcUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  // Column Index AT
                  columnIndex++;
                  final XSSFCell yearPerformanceRatingCell = tempRow.createCell( columnIndex );
                  //yearPerformanceRatingCell.setCellValue(  ); 
                  yearPerformanceRatingCell.setCellStyle( yellowCellStyle );
                  POIUtil.addValidationData( workbook, ratingArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "" );

                  // Column Index AU
                  columnIndex++;
                  final XSSFCell yearPerformancePromotionCell = tempRow.createCell( columnIndex );
                  //yearPerformancePromotionCell.setCellValue(  ); 
                  yearPerformancePromotionCell.setCellStyle( yellowCellStyle );
                  POIUtil.addValidationData( workbook, promotionTypeArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "" );

                  // Column Index AV
                  columnIndex++;
                  final XSSFCell recommendTTCIncreaseCell = tempRow.createCell( columnIndex );
                  //yearPerformancePromotionCell.setCellValue(  ); 
                  recommendTTCIncreaseCell.setCellStyle( accuracyThreeCellStyle );
                  recommendTTCIncreaseCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  // Column Index AW
                  columnIndex++;
                  final XSSFCell ttcIncreaseCell = tempRow.createCell( columnIndex );
                  //ttcIncreaseCell.setCellValue(  ); 
                  ttcIncreaseCell.setCellStyle( redCellStyle );

                  columnIndex++;
                  final XSSFCell newTTCLocalCell = tempRow.createCell( columnIndex );
                  //newTTCLocalCell.setCellValue(  ); 
                  newTTCLocalCell.setCellStyle( accuracyTwoCellStyle );
                  newTTCLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newTTCUSDCell = tempRow.createCell( columnIndex );
                  //newTTCUSDCell.setCellValue(  ); 
                  newTTCUSDCell.setCellStyle( accuracyTwoCellStyle );
                  newTTCUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newBaseSalaryLocalCell = tempRow.createCell( columnIndex );
                  //newBaseSalaryLocalCell.setCellValue(  ); 
                  newBaseSalaryLocalCell.setCellStyle( accuracyTwoCellStyle );
                  newBaseSalaryLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newBaseSalaryUSDCell = tempRow.createCell( columnIndex );
                  //newBaseSalaryUSDCell.setCellValue(  ); 
                  newBaseSalaryUSDCell.setCellStyle( accuracyTwoCellStyle );
                  newBaseSalaryUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualSalaryLocalCell = tempRow.createCell( columnIndex );
                  //newAnnualSalaryLocalCell.setCellValue(  ); 
                  newAnnualSalaryLocalCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualSalaryLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualSalaryUSDCell = tempRow.createCell( columnIndex );
                  //newAnnualSalaryUSDCell.setCellValue(  ); 
                  newAnnualSalaryUSDCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualSalaryUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualHousingAllowanceCell = tempRow.createCell( columnIndex );
                  //newAnnualHousingAllowanceCell.setCellValue(  ); 
                  newAnnualHousingAllowanceCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualHousingAllowanceCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualChildrenEduAllowanceCell = tempRow.createCell( columnIndex );
                  //newAnnualChildrenEduAllowanceCell.setCellValue(  ); 
                  newAnnualChildrenEduAllowanceCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualChildrenEduAllowanceCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualGuaranteedAllowanceLocalCell = tempRow.createCell( columnIndex );
                  //newAnnualGuaranteedAllowanceLocalCell.setCellValue(  ); 
                  newAnnualGuaranteedAllowanceLocalCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualGuaranteedAllowanceLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newAnnualGuatanteedAllowanceUSDCell = tempRow.createCell( columnIndex );
                  //newAnnualGuatanteedAllowanceUSDCell.setCellValue(  ); 
                  newAnnualGuatanteedAllowanceUSDCell.setCellStyle( accuracyTwoCellStyle );
                  newAnnualGuatanteedAllowanceUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newMonthlyTargetCell = tempRow.createCell( columnIndex );
                  //newMonthlyTargetCell.setCellValue(  ); 
                  newMonthlyTargetCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell newQuarterlyTargetCell = tempRow.createCell( columnIndex );
                  //newQuarterlyTargetCell.setCellValue(  ); 
                  newQuarterlyTargetCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell newGPTargetCell = tempRow.createCell( columnIndex );
                  //newGPTargetCell.setCellValue(  ); 
                  newGPTargetCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell newTargetValueLocalCell = tempRow.createCell( columnIndex );
                  //newTargetValueLocalCell.setCellValue(  ); 
                  newTargetValueLocalCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell newTargetValueUSDCell = tempRow.createCell( columnIndex );
                  //newTargetValueUSDCell.setCellValue(  ); 
                  newTargetValueUSDCell.setCellStyle( accuracyTwoCellStyle );

                  columnIndex++;
                  final XSSFCell newJobGradeCell = tempRow.createCell( columnIndex );
                  //newJobGradeCell.setCellValue(  ); 
                  newJobGradeCell.setCellStyle( yellowCellStyle );
                  POIUtil.addValidationData( workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, jobGardeFormula );

                  columnIndex++;
                  final XSSFCell newInternalTitleCell = tempRow.createCell( columnIndex );
                  //newInternalTitleCell.setCellValue(  ); 
                  newInternalTitleCell.setCellStyle( centerCellStyle );
                  newInternalTitleCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell newPositionENCell = tempRow.createCell( columnIndex );
                  //newPositionENCell.setCellValue(  ); 
                  newPositionENCell.setCellStyle( yellowCellStyle );

                  columnIndex++;
                  final XSSFCell newPositionZHCell = tempRow.createCell( columnIndex );
                  //newPositionZHCell.setCellValue(  ); 
                  newPositionZHCell.setCellStyle( yellowCellStyle );

                  columnIndex++;
                  final XSSFCell newShareOptionsCell = tempRow.createCell( columnIndex );
                  //newShareOptionsCell.setCellValue(  ); 
                  newShareOptionsCell.setCellStyle( yellowCellStyle );
                  POIUtil.addValidationData( workbook, yesOrNoArray, FIRST_ROW_INDEX + i, columnIndex, FIRST_ROW_INDEX + i, columnIndex, "" );

                  columnIndex++;
                  final XSSFCell targetBonusCell = tempRow.createCell( columnIndex );
                  //targetBonusCell.setCellValue(  ); 
                  targetBonusCell.setCellStyle( accuracyTwoCellStyle );
                  targetBonusCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell proposedBonusCell = tempRow.createCell( columnIndex );
                  //proposedBonusCell.setCellValue(  ); 
                  proposedBonusCell.setCellStyle( redCellStyle );

                  columnIndex++;
                  final XSSFCell proposedPayoutLocalCell = tempRow.createCell( columnIndex );
                  //proposedPayoutLocalCell.setCellValue(  ); 
                  proposedPayoutLocalCell.setCellStyle( accuracyTwoCellStyle );
                  proposedPayoutLocalCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell proposedPayoutUSDCell = tempRow.createCell( columnIndex );
                  //proposedPayoutUSDCell.setCellValue(  ); 
                  proposedPayoutUSDCell.setCellStyle( accuracyTwoCellStyle );
                  proposedPayoutUSDCell.setCellFormula( getFormula( columnIndex, FIRST_ROW_INDEX + i ) );

                  columnIndex++;
                  final XSSFCell descriptionCell = tempRow.createCell( columnIndex );
                  //descriptionCell.setCellValue(  ); 
                  descriptionCell.setCellStyle( yellowCellStyle );

                  columnIndex++;
                  final XSSFCell updateByCell = tempRow.createCell( columnIndex );
                  //updateByCell.setCellValue(  ); 
                  updateByCell.setCellStyle( leftCellStyle );

                  columnIndex++;
                  final XSSFCell updateDateCell = tempRow.createCell( columnIndex );
                  //updateDateCell.setCellValue(  ); 
                  updateDateCell.setCellValue( new Date() );
                  updateDateCell.setCellStyle( dateCellStyle );
               }
            }

            return workbook;
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // 模板设置汇率sheet
   private void setExchangeRate( final XSSFWorkbook workbook, final String accountId, final String cropId )
   {
      // 获取缓存中汇率
      final List< ExchangeRateVO > exchangeRateVOs = KANConstants.getKANAccountConstants( accountId ).EXCHANGE_RATE_VO;

      if ( exchangeRateVOs != null && exchangeRateVOs.size() > 0 )
      {
         // 普通字体
         final XSSFFont font = workbook.createFont();
         font.setFontName( "Arial Unicode MS" );
         font.setFontHeightInPoints( ( short ) 10 );

         // 蓝色字体
         final XSSFFont blueFont = workbook.createFont();
         blueFont.setFontName( "Arial Unicode MS" );
         blueFont.setFontHeightInPoints( ( short ) 10 );
         blueFont.setColor( HSSFColor.SKY_BLUE.index );

         // 普通样式
         final XSSFCellStyle cellStyle = workbook.createCellStyle();
         cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
         cellStyle.setBorderBottom( HSSFCellStyle.BORDER_THIN );
         cellStyle.setBorderLeft( HSSFCellStyle.BORDER_THIN );
         cellStyle.setBorderTop( HSSFCellStyle.BORDER_THIN );
         cellStyle.setBorderRight( HSSFCellStyle.BORDER_THIN );
         cellStyle.setFont( font );

         // 蓝色字体
         final XSSFCellStyle blueCellStyle = ( XSSFCellStyle ) cellStyle.clone();
         blueCellStyle.setFont( blueFont );

         // 保留四位小数
         final XSSFCellStyle fourCellStyle = ( XSSFCellStyle ) cellStyle.clone();
         fourCellStyle.setDataFormat( workbook.createDataFormat().getFormat( "0.0000" ) );

         // 获取模板sheet Exchange Rate
         final XSSFSheet exchangeRateSheet = workbook.getSheetAt( 4 );
         exchangeRateSheet.createRow( 0 );

         final XSSFRow titleRow = exchangeRateSheet.createRow( 1 );
         final XSSFCell titleCell1 = titleRow.createCell( 0 );
         titleCell1.setCellStyle( cellStyle );
         final XSSFCell titleCell2 = titleRow.createCell( 1 );
         titleCell2.setCellValue( "Local/USD" );
         titleCell2.setCellStyle( cellStyle );

         for ( int i = 0; i < exchangeRateVOs.size(); i++ )
         {
            final ExchangeRateVO tempExchangeRateVO = exchangeRateVOs.get( i );
            final XSSFRow tempRow = exchangeRateSheet.createRow( i + 2 );

            final XSSFCell cell1 = tempRow.createCell( 0 );
            cell1.setCellValue( tempExchangeRateVO.getCurrencyCode() );
            cell1.setCellStyle( blueCellStyle );

            final XSSFCell cell2 = tempRow.createCell( 1 );
            cell2.setCellFormula( tempExchangeRateVO.getFromUSD() + "/" + tempExchangeRateVO.getToLocal() );
            cell2.setCellStyle( fourCellStyle );
         }

         // 设置隐藏
         workbook.setSheetHidden( 4, true );
      }
   }

   private static String getFormula( final int columnIndex, final int rowIndex )
   {
      String rut = "";
      final String key = KANUtil.getExcelColumnName( columnIndex );
      final String formula = FORMULA_MAP.get( key );

      if ( KANUtil.filterEmpty( formula ) != null )
      {
         rut = formula.replaceAll( "00", String.valueOf( rowIndex + 1 ) );
      }

      return rut;
   }

   @Override
   public PagedListHolder getContactsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      // 如果是4. 注意微信同步也有这段, 写在xml.需要注意
      ( ( EmployeeReportVO ) pagedListHolder.getObject() ).setSearch4Contact( "1" );
      final EmployeeReportDao employeeReportDao = ( EmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( employeeReportDao.countContactsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeReportDao.getContactsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeReportDao.getContactsByCondition( ( EmployeeReportVO ) pagedListHolder.getObject() ) );
      }
      //fixPhone2( pagedListHolder.getSource() );
      return pagedListHolder;
   }

   // 修改公司电话,后面个个人部分不需要
   private void fixPhone2( List< Object > employeeReportObjects )
   {
      String targetPhone = "";
      for ( Object obj : employeeReportObjects )
      {
         EmployeeReportVO employeeReportVO = ( EmployeeReportVO ) obj;
         String phone2 = employeeReportVO.getPhone2();
         targetPhone = "";
         if ( !StringUtils.isBlank( phone2 ) )
         {
            String[] phone2s = null;
            if ( phone2.contains( "," ) )
            {
               phone2s = phone2.split( "," );
            }
            else if ( phone2.contains( "，" ) )
            {
               phone2s = phone2.split( "，" );
            }
            if ( phone2s != null && phone2s.length > 0 )
            {
               targetPhone = phone2s[ 0 ];
            }
         }
         employeeReportVO.setPhone2( targetPhone );
      }
   }
}
