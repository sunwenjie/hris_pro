package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.poi.POIUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeAddSubtractDao;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;
import com.kan.hro.service.inf.biz.employee.EmployeeAddSubtractService;

public class EmployeeAddSubtractServiceImpl extends ContextService implements EmployeeAddSubtractService
{

   @Override
   public PagedListHolder getEmployeeAddSubtractsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeAddSubtractDao employeeAddSubtractDao = ( EmployeeAddSubtractDao ) getDao();
      pagedListHolder.setHolderSize( employeeAddSubtractDao.countEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeAddSubtractDao.getEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeAddSubtractDao.getEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SXSSFWorkbook employeeAddSubtractReport( PagedListHolder employeeAddSubtractHolder )
   {
      // 初始化工作薄
      final SXSSFWorkbook workbook = new SXSSFWorkbook( POIUtil.EXPORT_BATCH_SIZE );
      workbook.setCompressTempFiles( true );
      try
      {

         // 创建字体
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // 创建单元格样式
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

         // 创建单元格样式
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

         // 创建单元格样式
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

         // 创建单元格样式(红色)
         final Font fontRed = workbook.createFont();
         fontRed.setFontName( "Calibri" );
         fontRed.setFontHeightInPoints( ( short ) 11 );
         fontRed.setColor( Font.COLOR_RED );
         final CellStyle cellStyleCenterRed = workbook.createCellStyle();
         cellStyleCenterRed.setFont( fontRed );
         cellStyleCenterRed.setAlignment( CellStyle.ALIGN_CENTER );

         // 创建表格
         final Sheet sheet = workbook.createSheet( "增减员报表" );
         // 设置表格默认列宽度为15个字节
         sheet.setDefaultColumnWidth( 15 );

         // 生成Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // 用以标识Header列序号
         int headerColumnIndex = 0;
         String[] headerColumnName = {  "派遣协议编号", "合同开始时间", "合同结束时间", "离职日期", "方案名称", "方案起缴日期", "方案停缴纳日期",
               "基数","员工姓名", "性别", "身份证号", "劳动保障卡号", "户籍性质", "户籍地址", "籍贯", "学历", "职位", "财务编码", "客户名称", "备注" };
         List< String > headerColumnNames = new ArrayList< String >();
         for ( int i = 0; i < headerColumnName.length; i++ )
         {

            final Cell cell = rowHeader.createCell( headerColumnIndex );
            cell.setCellValue( headerColumnName[ i ] );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
            headerColumnNames.add( headerColumnName[ i ] );

         }

         // 遍历生成Excel Body
         if ( employeeAddSubtractHolder.getSource() != null && employeeAddSubtractHolder.getSource().size() > 0 )
         {
            // 用以标识Body行序号
            int bodyRowIndex = 1;

            EmployeeAddSubtract employeeAddSubtractObject = ( EmployeeAddSubtract ) employeeAddSubtractHolder.getObject();

            // 遍历行
            for ( Object object : employeeAddSubtractHolder.getSource() )
            {
               EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) object;
               // 生成Excel Body Row
               final Row rowBody = sheet.createRow( bodyRowIndex );

               // 用以标识Body列序号
               int bodyColumnIndex = 0;
               
               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getContractId() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getStartDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getEndDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getResignDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getSbName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getPlanStartDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getPlanEndDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getBase() );
               bodyColumnIndex++;


               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getEmployeeName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtractObject.decodeField( employeeAddSubtract.getSalutation(), employeeAddSubtractObject.getSalutations() ) );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getCertificateNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getSbNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getDecodeResidencyType() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getResidencyAddress() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getCityNameZH() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtractObject.decodeField( employeeAddSubtract.getHighestEducation(), employeeAddSubtractObject.getHighestEducations() ) );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getAdditionalPosition() );
               bodyColumnIndex++;

              
               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getClientName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getDescription() );
               bodyColumnIndex++;

               bodyRowIndex++;
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return workbook;
   }

}
