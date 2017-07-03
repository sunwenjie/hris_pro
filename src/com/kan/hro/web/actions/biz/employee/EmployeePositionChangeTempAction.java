package com.kan.hro.web.actions.biz.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeePositionChangeTempVO;
import com.kan.hro.service.inf.biz.employee.EmployeePositionChangeTempService;

public class EmployeePositionChangeTempAction extends BaseAction
{

   // 员工 - 批量异动
   public static final String ACCESS_ACTION = "HRO_EMPLOYEE_BATCH_POSITION_CHANGE";

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
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // 获得Action Form
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         employeePositionChangeTempVO.setBatchId( request.getParameter( "batchId" ) );
         // 处理subAction
         dealSubAction( employeePositionChangeTempVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( employeePositionChangeTempVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeePositionChangeTempService.getEmployeePositionChangeTempVOsByCondition( pagedListHolder, true );
         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "employeePositionChangeTempHolder", pagedListHolder );

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeePositionChangeTempTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEmployeePositionChangeTemp" );
   }

   /***
    * 提交 submit_temp
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         // 初始化Service接口
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( employeePositionChangeTempVO.getSelectedIds() ) != null )
         {
            // 分割选择项
            final String[] positionChangeIds = employeePositionChangeTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( int i = 0; i < positionChangeIds.length; i++ )
            {
               positionChangeIds[ i ] = KANUtil.decodeStringFromAjax( positionChangeIds[ i ] );
            }

            rows = employeePositionChangeTempService.submitEmployeePositionChangeTempVOByPositionChangeIds( positionChangeIds, getUserId( request, null ), getIPAddress( request ), request.getLocale() );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeePositionChangeTempVO, Operate.SUBMIT, null, "submit_temp:" + KANUtil.decodeSelectedIds( employeePositionChangeTempVO.getSelectedIds() ) );
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
    * 退回 rollback_temp
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final EmployeePositionChangeTempVO employeePositionChangeTempVO = ( EmployeePositionChangeTempVO ) form;
         // 初始化Service接口
         final EmployeePositionChangeTempService employeePositionChangeTempService = ( EmployeePositionChangeTempService ) getService( "employeePositionChangeTempService" );
         // 存在勾选ID
         if ( KANUtil.filterEmpty( employeePositionChangeTempVO.getSelectedIds() ) != null )
         {
            // 分割选择项
            final String[] positionChangeIds = employeePositionChangeTempVO.getSelectedIds().split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( int i = 0; i < positionChangeIds.length; i++ )
            {
               positionChangeIds[ i ] = KANUtil.decodeStringFromAjax( positionChangeIds[ i ] );
            }

            rows = employeePositionChangeTempService.rollbackEmployeePositionChangeTempVOByPositionChangeIds( positionChangeIds, getUserId( request, null ) );

            if ( rows > 0 )
            {
               success( request, MESSAGE_TYPE_ROLLBACK );
               insertlog( request, employeePositionChangeTempVO, Operate.ROllBACK, null, "rollback_temp:"
                     + KANUtil.decodeSelectedIds( employeePositionChangeTempVO.getSelectedIds() ) );
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

}
