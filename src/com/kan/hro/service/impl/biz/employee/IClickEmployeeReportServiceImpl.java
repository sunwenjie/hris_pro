package com.kan.hro.service.impl.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.POIUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractSalaryDao;
import com.kan.hro.dao.inf.biz.employee.IClickEmployeeReportDao;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;
import com.kan.hro.domain.biz.employee.IClickEmployeeReportView;
import com.kan.hro.service.inf.biz.employee.IClickEmployeeReportService;

public class IClickEmployeeReportServiceImpl extends ContextService implements IClickEmployeeReportService
{
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
   public PagedListHolder getFullEmployeeReportViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final IClickEmployeeReportDao iClickEmployeeReportDao = ( IClickEmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( iClickEmployeeReportDao.countFullEmployeeReportViewsByCondition( ( IClickEmployeeReportView ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( iClickEmployeeReportDao.getFullEmployeeReportViewsByCondition( ( IClickEmployeeReportView ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( iClickEmployeeReportDao.getFullEmployeeReportViewsByCondition( ( IClickEmployeeReportView ) pagedListHolder.getObject() ) );
      }

      fetchSalaryItems( pagedListHolder, ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalarys() );
      return pagedListHolder;
   }

   @Override
   public PagedListHolder getFullEmployeeReportViewsByCondition_r4( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final IClickEmployeeReportDao iClickEmployeeReportDao = ( IClickEmployeeReportDao ) getDao();
      pagedListHolder.setHolderSize( iClickEmployeeReportDao.countFullEmployeeReportViewsByCondition_r4( ( IClickEmployeeReportView ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( iClickEmployeeReportDao.getFullEmployeeReportViewsByCondition_r4( ( IClickEmployeeReportView ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( iClickEmployeeReportDao.getFullEmployeeReportViewsByCondition_r4( ( IClickEmployeeReportView ) pagedListHolder.getObject() ) );
      }

      fetchSalaryItems( pagedListHolder, ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalarys() );
      return pagedListHolder;
   }

   public static final int FIRST_ROW_INDEX = 13;

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
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            final XSSFSheet sheet = workbook.getSheetAt( 0 );
            final XSSFRow baseRow = sheet.getRow( FIRST_ROW_INDEX );

            if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( int i = 0; i <= pagedListHolder.getSource().size(); i++ )
               {
                  final EmployeeReportVO tempEmployeeReportVO = ( EmployeeReportVO ) pagedListHolder.getSource().get( i );

                  final XSSFRow tempRow = sheet.createRow( FIRST_ROW_INDEX + i );

                  int columnIndex = 0;

                  final XSSFCell fullNameCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), fullNameCell, workbook );
                  fullNameCell.setCellValue( tempEmployeeReportVO.getNameEN() );

                  columnIndex++;
                  final XSSFCell shortNameCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), shortNameCell, workbook );
                  shortNameCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jiancheng" ) ) );

                  columnIndex++;
                  final XSSFCell chineseNameCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), chineseNameCell, workbook );
                  chineseNameCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getNameZH() ) );

                  columnIndex++;
                  final XSSFCell entityNameENCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), entityNameENCell, workbook );
                  entityNameENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityNameEN() ) );

                  columnIndex++;
                  final XSSFCell entityNameZHCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), entityNameZHCell, workbook );
                  entityNameZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityNameZH() ) );

                  columnIndex++;
                  final XSSFCell entityTitleCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), entityTitleCell, workbook );
                  entityTitleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getEntityTitle() ) );

                  columnIndex++;
                  final XSSFCell bUFunctionENCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), bUFunctionENCell, workbook );
                  bUFunctionENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getParentBranchNameEN() ) );

                  columnIndex++;
                  final XSSFCell bUFunctionZHCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), bUFunctionZHCell, workbook );
                  bUFunctionZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getParentBranchNameZH() ) );

                  columnIndex++;
                  final XSSFCell branchNameENCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), branchNameENCell, workbook );
                  branchNameENCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getBranchNameEN() ) );

                  columnIndex++;
                  final XSSFCell branchNameZHCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), branchNameZHCell, workbook );
                  branchNameZHCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getBranchNameZH() ) );

                  columnIndex++;
                  final XSSFCell costCenterCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), costCenterCell, workbook );
                  costCenterCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecodeSettlementBranch() ) );

                  columnIndex++;
                  final XSSFCell functionCodeCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), functionCodeCell, workbook );
                  functionCodeCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecodeBusinessTypeId() ) );

                  columnIndex++;
                  final XSSFCell bangongdidianCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), bangongdidianCell, workbook );
                  bangongdidianCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "bangongdidian" ) ) );

                  columnIndex++;
                  final XSSFCell jobroleCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), jobroleCell, workbook );
                  jobroleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jobrole" ) ) );

                  columnIndex++;
                  final XSSFCell zhiweimingchengyingwenCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), zhiweimingchengyingwenCell, workbook );
                  zhiweimingchengyingwenCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "zhiweimingchengyingwen" ) ) );

                  columnIndex++;
                  final XSSFCell neibuchengweiCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), neibuchengweiCell, workbook );
                  neibuchengweiCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );

                  columnIndex++;
                  final XSSFCell jobGradeCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), jobGradeCell, workbook );
                  jobGradeCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempPositionGradeIds() ) );

                  columnIndex++;
                  final XSSFCell innerTitleCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), innerTitleCell, workbook );
                  innerTitleCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "neibuchengwei" ) ) );

                  columnIndex++;
                  final XSSFCell yewuhuibaoxianjingliCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), yewuhuibaoxianjingliCell, workbook );
                  yewuhuibaoxianjingliCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "yewuhuibaoxianjingli" ) ) );

                  columnIndex++;
                  final XSSFCell lineManagerCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), lineManagerCell, workbook );
                  lineManagerCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDecode_tempParentPositionOwners() ) );

                  columnIndex++;
                  final XSSFCell startWorkDateCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), startWorkDateCell, workbook );
                  startWorkDateCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getStartWorkDate() ) );

                  columnIndex++;
                  final XSSFCell contractStartDateCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), contractStartDateCell, workbook );
                  contractStartDateCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getContractStartDate() ) );

                  columnIndex++;
                  final XSSFCell gupiaoshuliangCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), gupiaoshuliangCell, workbook );
                  //gupiaoshuliangCell.setCellValue

                  columnIndex++;
                  final XSSFCell performanceRating2013Cell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), performanceRating2013Cell, workbook );
                  performanceRating2013Cell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getDynaColumns().get( "jixiaodengji" ) ) );

                  columnIndex++;
                  final XSSFCell promotionYERR2013Cell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), promotionYERR2013Cell, workbook );
                  //promotionYERR2013Cell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell promotionReviewMidYear2014Cell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), promotionReviewMidYear2014Cell, workbook );
                  //promotionReviewMidYear2014Cell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell salaryIncreaseReviewMidYear2014 = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), salaryIncreaseReviewMidYear2014, workbook );
                  //salaryIncreaseReviewMidYear2014.setCellValue(  );

                  columnIndex++;
                  final XSSFCell bizhongCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), bizhongCell, workbook );
                  //bizhongCell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell baseMonthlySalaryLocalCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), baseMonthlySalaryLocalCell, workbook );
                  baseMonthlySalaryLocalCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getMontnlySalary() ) );

                  columnIndex++;
                  final XSSFCell baseMonthlySalaryUSDCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), baseMonthlySalaryUSDCell, workbook );
                  //baseMonthlySalaryUSDCell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell annualSalaryLocalCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), annualSalaryLocalCell, workbook );
                  //annualSalaryLocalCell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell annualSalaryUSDCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), annualSalaryUSDCell, workbook );
                  //annualSalaryUSDCell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell housingAllowanceCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), housingAllowanceCell, workbook );
                  housingAllowanceCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getHousingAllowance() ) );

                  columnIndex++;
                  final XSSFCell childenEducationAllowanceCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), childenEducationAllowanceCell, workbook );
                  childenEducationAllowanceCell.setCellValue( KANUtil.filterEmpty( tempEmployeeReportVO.getChildenEducationAllowance() ) );

                  columnIndex++;
                  final XSSFCell annualGuaranteedCashLocalCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), annualGuaranteedCashLocalCell, workbook );
                  //annualGuaranteedCashLocalCell.setCellValue(  );

                  columnIndex++;
                  final XSSFCell annualGuaranteedCashUSDCell = tempRow.createCell( columnIndex );
                  POIUtil.copyCellStyle( baseRow.getCell( columnIndex ), annualGuaranteedCashUSDCell, workbook );
                  //annualGuaranteedCashUSDCell.setCellValue(  );

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

   private void fetchSalaryItems( final PagedListHolder pagedListHolder, final List< MappingVO > itemVOs ) throws KANException
   {
      // 收集contractIds 
      final List< String > contractIds = new ArrayList< String >();

      if ( pagedListHolder != null && pagedListHolder.getHolderSize() > 0 )
      {
         final List< Object > iClickEmployeeReportViews = pagedListHolder.getSource();

         if ( iClickEmployeeReportViews != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object iClickEmployeeReportView : iClickEmployeeReportViews )
            {
               final IClickEmployeeReportView tempEmployeeReport = ( IClickEmployeeReportView ) iClickEmployeeReportView;
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
               employeeContractSalaryVO.setAccountId( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getAccountId() );
               employeeContractSalaryVO.setCorpId( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getCorpId() );
               employeeContractSalaryVO.setSalaryStartDateStart( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalaryStartDateStart() );
               employeeContractSalaryVO.setSalaryStartDateEnd( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalaryStartDateEnd() );
               employeeContractSalaryVO.setSalaryEndDateStart( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalaryEndDateStart() );
               employeeContractSalaryVO.setSalaryEndDateEnd( ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getSalaryEndDateEnd() );

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
                     for ( Object iClickEmployeeReportView : iClickEmployeeReportViews )
                     {
                        final IClickEmployeeReportView tempEmployeeReport = ( IClickEmployeeReportView ) iClickEmployeeReportView;

                        if ( tempEmployeeReport.getContractId() != null && employeeContractSalary.getContractId() != null
                              && tempEmployeeReport.getContractId().equals( employeeContractSalary.getContractId() ) )
                        {
                           final MappingVO salary = new MappingVO();
                           salary.setMappingId( employeeContractSalary.getItemId() );
                           salary.setMappingValue( KANUtil.formatNumber( employeeContractSalary.getBase(), ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).getAccountId() ) );
                           tempEmployeeReport.getSalarys().add( salary );
                           continue;
                        }
                     }
                  }
                  ( ( IClickEmployeeReportView ) pagedListHolder.getObject() ).setSalarys( items );
               }
            }
         }
      }
   }

}
