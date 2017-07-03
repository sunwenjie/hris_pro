package com.kan.hro.web.actions.biz.attendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.RecordVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.RecordService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class RecordAction extends BaseAction
{

   // accessAction
   public final static String ACCESS_ACTION = "HRO_BIZ_ATTENDANCE_RECORD";

   public final static String[] RECORD_COLUMN = new String[] { "人员编号", "登记号码", "刷卡日期", "刷卡时间", "签到方式", "设备编号", "备注" };

   public final static String[] RECORD_COLUMN_NUM = new String[] { "0", "1", "4", "5", "6", "7", "11" };

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final RecordService recordService = ( RecordService ) getService( "recordService" );
         // 获得Action Form
         final RecordVO recordVO = ( RecordVO ) form;

         // 处理subAction
         dealSubAction( recordVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder recordHolder = new PagedListHolder();
         // 传入当前页
         recordHolder.setPage( page );
         // 传入当前值对象
         recordHolder.setObject( recordVO );
         // 设置页面记录条数
         recordHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         recordService.getRecordVOsByCondition( recordHolder, true );

         // 刷新Holder，国际化传值
         refreshHolder( recordHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "recordHolder", recordHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "role", getRole( request, null ) );
            return mapping.findForward( "listRecordTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listRecord" );
   }

   // 下载模板
   public void downloadTemplate( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取板路径
         final String path = KANUtil.basePath + "/signRecord.xlsx";

         // 创建File
         final File file = new File( path );

         // 存在模板路径
         if ( file.exists() )
         {
            // 初始化工作薄
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );

            // 初始化OutputStream
            final OutputStream os = response.getOutputStream();

            // 设置返回文件下载
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "打卡记录登记表.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel文件写入OutputStream
            workbook.write( os );

            // 输出OutputStream
            os.flush();
            //关闭流  
            os.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 上传文件
   public void uploadFile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service
         final RecordService recordService = ( RecordService ) getService( "recordService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // 获取板路径
         final String path = KANUtil.basePath + "/signRecord.xlsx";

         // 创建File
         final File file = new File( path );

         final List< Object > records = new ArrayList< Object >();

         // 存在模板路径
         if ( file.exists() )
         {
            // 初始化工作薄
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );

            final XSSFSheet sheet = workbook.getSheetAt( 0 );

            // 遍历行
            for ( int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               final XSSFRow row = sheet.getRow( i );

               final RecordVO recordVO = new RecordVO();

               // 行不为空
               if ( row != null )
               {
                  // 获取到Excel文件中的所有的列
                  int cells = row.getPhysicalNumberOfCells();

                  // 遍历列
                  for ( int j = 0; j < cells; j++ )
                  {
                     // 获取到列的值
                     final XSSFCell cell = row.getCell( j );
                     final String cellValue = cell.getStringCellValue();
                     // { "0", "1", "4", "5", "6", "7", "11" };
                     if ( j == 0 )
                     {
                        final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( cellValue );
                        if ( employeeVO != null )
                        {
                           recordVO.setEmployeeNameZH( employeeVO.getNameZH() );
                           recordVO.setEmployeeNameEN( employeeVO.getNameEN() );
                        }
                        recordVO.setEmployeeId( cellValue );
                     }
                     else if ( j == 1 )
                     {
                        recordVO.setEmployeeNo( cellValue );
                     }
                     else if ( j == 4 )
                     {
                        recordVO.setSignDate( cellValue );
                     }
                     else if ( j == 5 )
                     {
                        recordVO.setSignTime( cellValue );
                     }
                     else if ( j == 6 )
                     {
                        recordVO.setSignType( cellValue );
                     }
                     else if ( j == 7 )
                     {
                        recordVO.setMachineNo( cellValue );
                     }
                     else if ( j == 11 )
                     {
                        recordVO.setDescription( cellValue );
                     }
                  }

                  recordVO.setStatus( "1" );
                  recordVO.setCreateBy( getUserId( request, null ) );
                  recordVO.setCreateDate( new Date() );
                  recordVO.setModifyBy( getUserId( request, null ) );
                  recordVO.setModifyDate( new Date() );
               }

               records.add( recordVO );
            }
         }

         recordService.insertRecordVOs( records );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
