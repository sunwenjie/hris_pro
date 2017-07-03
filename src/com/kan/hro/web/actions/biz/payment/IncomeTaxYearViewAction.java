package com.kan.hro.web.actions.biz.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MoneyToChinese;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;
import com.kan.hro.service.inf.biz.payment.IncomeTaxYearViewService;

public class IncomeTaxYearViewAction extends BaseAction
{
   public static String ACCESSACTION = "JAVA_OBJECT_INCOME_TAX_YEAR";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final IncomeTaxYearViewService incomeTaxYearViewService = ( IncomeTaxYearViewService ) getService( "incomeTaxYearViewService" );
         // 获得Action Form
         final IncomeTaxYearView incomeTaxYearView = ( IncomeTaxYearView ) form;

         //处理数据权限
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESSACTION, incomeTaxYearView );
            setDataAuth( request, response, incomeTaxYearView );
         }

         if ( incomeTaxYearView.getTaxAmountPersonalMin() != null && incomeTaxYearView.getTaxAmountPersonalMin().equals( "" ) )
         {
            incomeTaxYearView.setTaxAmountPersonalMin( "0" );
         }
         
         // 解码form
         decodedObject( incomeTaxYearView );

         // 默认查询当前年份
         if ( KANUtil.filterEmpty( incomeTaxYearView.getYear() ) == null )
         {
            incomeTaxYearView.setYear( KANUtil.getYear( new Date() ) );
         }

         String frontTaxAmountPersonalMin = incomeTaxYearView.getTaxAmountPersonalMin();
         String frontTaxAmountPersonalMax = incomeTaxYearView.getTaxAmountPersonalMax();

         // 个税不低于
         if ( KANUtil.filterEmpty( incomeTaxYearView.getTaxAmountPersonalMin() ) == null )
         {
            frontTaxAmountPersonalMin = "120000";
            incomeTaxYearView.setTaxAmountPersonalMin( frontTaxAmountPersonalMin );
         }

         // 个税不高于
         if ( KANUtil.filterEmpty( incomeTaxYearView.getTaxAmountPersonalMax() ) == null )
         {
            incomeTaxYearView.setTaxAmountPersonalMax( String.valueOf( Integer.MAX_VALUE ) );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder incomeTaxYearViewHolder = new PagedListHolder();
         // 传入当前页
         incomeTaxYearViewHolder.setPage( page );
         // 传入当前值对象
         incomeTaxYearViewHolder.setObject( incomeTaxYearView );
         // 设置页面记录条数
         incomeTaxYearViewHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         incomeTaxYearViewService.getIncomeTaxYearViewsByCondition( incomeTaxYearViewHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( incomeTaxYearViewHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "incomeTaxYearViewHolder", incomeTaxYearViewHolder );

         incomeTaxYearView.setTaxAmountPersonalMin( frontTaxAmountPersonalMin.equals( "0" ) ? "" : frontTaxAmountPersonalMin );
         incomeTaxYearView.setTaxAmountPersonalMax( frontTaxAmountPersonalMax );

         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", BaseAction.getRole( request, null ) );
            return mapping.findForward( "listIncomeTaxYearViewTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listIncomeTaxYearView" );
   }

   // 生成excel
   public ActionForward genreate_excel( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取contractId
         final String contractId = request.getParameter( "contractId" );

         // 获取year
         final String year = request.getParameter( "year" );

         // 初始化Service
         final IncomeTaxYearViewService incomeTaxYearViewService = ( IncomeTaxYearViewService ) getService( "incomeTaxYearViewService" );

         // 获取ActionForm
         IncomeTaxYearView incomeTaxYearView = ( IncomeTaxYearView ) form;
         incomeTaxYearView.setYear( year );
         incomeTaxYearView.setContractId( contractId );

         // 获取IncomeTaxYearView
         incomeTaxYearView = incomeTaxYearViewService.getIncomeTaxYearViewByCondition( incomeTaxYearView );
         incomeTaxYearView.reset( null, request );

         // 初始化excel名
         String excelName = incomeTaxYearView.getEmployeeNameZH() + "的个税申报（年度）.xls";

         // 获取个税年报申报的模板路径
         final String path = KANUtil.basePath + "/12w.xls";

         // 创建File
         final File file = new File( path );

         // 存在模板路径
         if ( file.exists() )
         {
            // 初始化工作薄
            final HSSFWorkbook workbook = new HSSFWorkbook( new FileInputStream( file ) );

            // 获取工作表
            final HSSFSheet sheet = workbook.getSheetAt( 0 );

            // 遍历行
            for ( int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               // 读取左上端单元格
               final HSSFRow row = sheet.getRow( i );

               // 行不为空
               if ( row != null )
               {
                  // 获取到Excel文件中的所有的列
                  int cells = row.getPhysicalNumberOfCells();

                  // 遍历列
                  for ( int j = 0; j < cells; j++ )
                  {
                     // 获取到列的值
                     HSSFCell cell = row.getCell( j );
                     if ( cell != null )
                     {
                        String field = "";
                        String value = "";
                        String cellValue = "";
                        if ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC )
                        {
                           cellValue = String.valueOf( cell.getNumericCellValue() );
                        }
                        else if ( cell.getCellType() == Cell.CELL_TYPE_STRING )
                        {
                           cellValue = cell.getStringCellValue();
                        }

                        // 身份证照号码
                        if ( i == 4 && j == 7 )
                        {
                           cellValue = "${certificateNumber}";
                        }

                        // 联系地址邮编
                        if ( i == 6 && j == 7 )
                        {
                           cellValue = "${personalPostcode}";
                        }

                        if ( !cellValue.equals( "" ) && cellValue.contains( "$" ) )
                        {
                           field = KANUtil.getSubString( cellValue, "{", "}" );

                           // 如果需要转换中文金额${chineseMoney}        
                           if ( field.equals( "chineseMoney" ) )
                           {
                              value = ( String ) KANUtil.getValue( incomeTaxYearView, "beforeTaxSalary" );
                              if ( KANUtil.filterEmpty( value ) != null )
                              {
                                 value = MoneyToChinese.getMoneyString( Double.valueOf( value ) );
                              }
                           }
                           else
                           {
                              value = ( String ) KANUtil.getValue( incomeTaxYearView, field );
                           }

                           // 如果是身份证号||邮编号码
                           if ( field.equals( "certificateNumber" ) || field.equals( "personalPostcode" ) )
                           {
                              if ( KANUtil.filterEmpty( value ) != null )
                              {
                                 for ( int k = 0; k < value.length(); k++ )
                                 {
                                    j++;
                                    row.getCell( j ).setCellValue( String.valueOf( value.charAt( k ) ) );
                                 }
                              }

                              continue;
                           }

                           cell.setCellValue( cellValue.replace( "${" + field + "}", value == null ? "" : value ) );
                        }
                     }
                  }
               }
            }

            // 往客户端传送文件流
            // 初始化OutputStream
            final OutputStream os = response.getOutputStream();

            // 设置返回文件下载
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( excelName, "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel文件写入OutputStream
            workbook.write( os );

            // 输出OutputStream
            os.flush();
            //关闭流  
            os.close();
         }

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   public static void main( String[] args )
   {
      String s = "320382198808175519";

      for ( int i = 0; i < s.length(); i++ )
      {
         System.out.println( String.valueOf( s.charAt( i ) ) );
      }
   }
}