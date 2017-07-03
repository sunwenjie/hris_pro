package com.kan.hro.web.actions.biz.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.poi.POIUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentBatchService;

public class EmployeeSalaryAdjustmentBatchAction extends BaseAction
{
   // 员工 - 批量调薪
   public static final String ACCESS_ACTION = "HRO_EMPLOYEE_BATCH_SALARY_ADJUSTMENT";

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
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // 获得Action Form
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;
         employeeSalaryAdjustmentBatchVO.setAccessAction( ACCESS_ACTION );
         // 如果没有指定排序则默认按 batchId排序
         if ( employeeSalaryAdjustmentBatchVO.getSortColumn() == null || employeeSalaryAdjustmentBatchVO.getSortColumn().isEmpty() )
         {
            employeeSalaryAdjustmentBatchVO.setSortOrder( "desc" );
            employeeSalaryAdjustmentBatchVO.setSortColumn( "batchId" );
         }

         //setDataAuth( request, response, employeeSalaryAdjustmentBatchVO );
         Set< String > rulePositionIds = new HashSet< String >();
         rulePositionIds.add( getPositionId( request, null ) );
         employeeSalaryAdjustmentBatchVO.setRulePositionIds( rulePositionIds );
         employeeSalaryAdjustmentBatchVO.setRulePublic( AuthConstants.RULE_UN_PUBLIC );
         
         // 处理subAction
         dealSubAction( employeeSalaryAdjustmentBatchVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeSalaryAdjustmentBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeSalaryAdjustmentBatchHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeSalaryAdjustmentBatchTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeeSalaryAdjustmentBatch" );
   }

   /***
    * 提交 员工 - 批量调薪
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;

         // 初始化Service接口
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) != null )
         {
            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : employeeSalaryAdjustmentBatchVO.getSelectedIds().split( "," ) )
            {
               final EmployeeSalaryAdjustmentBatchVO submitObject = employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setIp( getIPAddress( request ) );
               submitObject.setLocale( request.getLocale() );
               rows = rows + employeeSalaryAdjustmentBatchService.submitEmployeeSalaryAdjustmentBatch( submitObject );
            }

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeSalaryAdjustmentBatchVO, Operate.SUBMIT, null, KANUtil.decodeSelectedIds( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /***
    * 退回 员工 - 批量调薪
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException
    */
   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeeSalaryAdjustmentBatchVO employeeSalaryAdjustmentBatchVO = ( EmployeeSalaryAdjustmentBatchVO ) form;

         // 初始化Service接口
         final EmployeeSalaryAdjustmentBatchService employeeSalaryAdjustmentBatchService = ( EmployeeSalaryAdjustmentBatchService ) getService( "employeeSalaryAdjustmentBatchService" );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) != null )
         {
            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : employeeSalaryAdjustmentBatchVO.getSelectedIds().split( "," ) )
            {
               final EmployeeSalaryAdjustmentBatchVO rollbackObject = employeeSalaryAdjustmentBatchService.getEmployeeSalaryAdjustmentBatchVOByBatchId( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               rows = rows + employeeSalaryAdjustmentBatchService.rollbackEmployeeSalaryAdjustmentBatch( rollbackObject );
            }

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, employeeSalaryAdjustmentBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( employeeSalaryAdjustmentBatchVO.getSelectedIds() ) );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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

   /***
    * 批量调薪 - 导出初始模板
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward export_template( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      boolean lang_zh = request.getLocale().getLanguage().equalsIgnoreCase( "zh" );
      try
      {
         String templateName = lang_zh ? "批量调薪模板.xlsx" : "Batch Adjustment salary Template(EN).xlsx";
         final String path = KANUtil.basePath + "/" + templateName;
         // 创建File
         final File file = new File( path );
         // 存在模板路径
         if ( file.exists() )
         {
            // 初始化工作薄
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );
            final List< MappingVO > salaryItemMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getSalaryItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
            final String[] itemArray = KANUtil.mappingListToArray( salaryItemMappingVOs );
            POIUtil.SetDataValidation( workbook, itemArray, 1, 3, 1000, 3, ( lang_zh ? "薪资科目" : "Salary Item" ) );
            final List< MappingVO > changeReasonMappingVOs = KANUtil.getMappings( request.getLocale(), "business.employee.change.report.changeReason" );
            final String[] changeReasonArray = KANUtil.mappingListToArray( changeReasonMappingVOs );
            POIUtil.SetDataValidation( workbook, changeReasonArray, 1, 7, 1000, 7, ( lang_zh ? "异动原因" : "Movement Category" ) );

            // 初始化OutputStream
            final OutputStream os = response.getOutputStream();
            // 设置返回文件下载
            response.setContentType( "application/x-msdownload" );
            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=\"" + new String( URLDecoder.decode( templateName, "UTF-8" ).getBytes(), "iso-8859-1" ) + "\"" );
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

}
