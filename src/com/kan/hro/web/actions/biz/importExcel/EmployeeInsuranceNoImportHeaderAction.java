package com.kan.hro.web.actions.biz.importExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeInsuranceNoImportHeaderVO;
import com.kan.hro.service.inf.biz.employee.EmployeeInsuranceNoImportHeaderService;

public class EmployeeInsuranceNoImportHeaderAction extends BaseAction
{

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
         final EmployeeInsuranceNoImportHeaderService employeeInsuranceNoImportHeaderService = ( EmployeeInsuranceNoImportHeaderService ) getService( "employeeInsuranceNoImportHeaderService" );

         // 获得Action Form
         final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO = ( EmployeeInsuranceNoImportHeaderVO ) form;

         // 如果没有指定排序则默认按 batchId排序
         if ( employeeInsuranceNoImportHeaderVO.getSortColumn() == null || employeeInsuranceNoImportHeaderVO.getSortColumn().isEmpty() )
         {
            employeeInsuranceNoImportHeaderVO.setSortOrder( "desc" );
            employeeInsuranceNoImportHeaderVO.setSortColumn( "a.cardnoId" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeeInsuranceNoImportHeaderVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeInsuranceNoImportHeaderService.getEmployeeInsuranceNoImportHeaderVOsByCondition( pagedListHolder, true );

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeeInsuranceNoHeaderHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeInsuranceNoImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listEmployeeInsuranceNoImportHeader" );
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
      final EmployeeInsuranceNoImportHeaderService employeeInsuranceNoImportHeaderService = ( EmployeeInsuranceNoImportHeaderService ) getService( "employeeInsuranceNoImportHeaderService" );
      String ids = request.getParameter( "selectedIds" );
      if ( StringUtils.isNotEmpty( ids ) )
      {
         String batchId = request.getParameter( "batchId" );
         int count = employeeInsuranceNoImportHeaderService.backUpRecord( ids.split( "," ), batchId );
         if ( count == 0 )
         {
            EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = new EmployeeInsuranceNoImportBatchVO();
            employeeInsuranceNoImportBatchVO.reset( mapping, request );
            return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, employeeInsuranceNoImportBatchVO, request, response );
         }
         else
         {
            final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO = ( EmployeeInsuranceNoImportHeaderVO ) form;
            employeeInsuranceNoImportHeaderVO.setBatchId( batchId );
            employeeInsuranceNoImportHeaderVO.setSelectedIds( "" );
            return list_object( mapping, employeeInsuranceNoImportHeaderVO, request, response );
         }
      }
      else
      {
         EmployeeInsuranceNoImportBatchVO employeeInsuranceNoImportBatchVO = new EmployeeInsuranceNoImportBatchVO();
         employeeInsuranceNoImportBatchVO.reset( mapping, request );
         return new EmployeeInsuranceNoImportBatchAction().list_object( mapping, employeeInsuranceNoImportBatchVO, request, response );
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
