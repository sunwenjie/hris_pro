package com.kan.hro.web.actions.biz.importExcel;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportBatchService;

public class EmployeeInsuranceNoImportBatchAction extends BaseAction
{

   // 当前Action对应的Access Action
   public static final String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CARDNO_TEMP";

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
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // 获得Action Form
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;

         // 如果没有指定排序则默认按 batchId排序
         if ( employeeInsuranceNoImportBatchVO.getSortColumn() == null || employeeInsuranceNoImportBatchVO.getSortColumn().isEmpty() )
         {
            employeeInsuranceNoImportBatchVO.setSortOrder( "desc" );
            employeeInsuranceNoImportBatchVO.setSortColumn( "batchId" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeInsuranceNoImportBatchVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeInsuranceNoImportBatchHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeInsuranceNoImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listEmployeeInsuranceNoImportBatch" );
   }

   /**
    * 更新，直接update正式表里的金额
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;
         employeeInsuranceNoImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // 获得勾选ID
         final String batchIds = employeeInsuranceNoImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeInsuranceNoImportBatchVO submitObject = employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );
               rows = rows + employeeInsuranceNoImportBatchService.updateEmployeeInsuranceNoImportBatch( submitObject );
            }

            if ( rows == 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }

         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 退回,物理删除temp表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = ( EmployeeInsuranceNoImportBatchVO ) form;
         employeeInsuranceNoImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final EmployeeInsuranceNoImportBatchService employeeInsuranceNoImportBatchService = ( EmployeeInsuranceNoImportBatchService ) getService( "employeeInsuranceNoImportBatchService" );

         // 获得勾选ID
         final String batchIds = employeeInsuranceNoImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final EmployeeInsuranceNoImportBatchVO submitObject = employeeInsuranceNoImportBatchService.getEmployeeInsuranceNoImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + employeeInsuranceNoImportBatchService.backBatch( submitObject );
            }

            success( request, MESSAGE_TYPE_SUBMIT, "退回" );
         }
         return list_object( mapping, form, request, response );
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
