package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EmployeeStatusVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EmployeeStatusService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class EmployeeStatusAction extends BaseAction
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
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );
         // 获得Action Form
         final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;

         // 如果子Action是删除用户列表
         if ( employeeStatusVO.getSubAction() != null && employeeStatusVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder employeeStatusHolder = new PagedListHolder();
         // 传入当前页
         employeeStatusHolder.setPage( page );
         // 传入当前值对象
         employeeStatusHolder.setObject( employeeStatusVO );
         // 设置页面记录条数
         employeeStatusHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeStatusHolder = employeeStatusService.getEmployeeStatusVOsByCondition( employeeStatusHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( employeeStatusHolder, request );

         // employeeStatusHolder写入request
         request.setAttribute( "employeeStatusHolder", employeeStatusHolder );
         // Ajax
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEmployeeStatusTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到列表界面
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "listEmployeeStatus" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( EmployeeStatusVO ) form ).setStatus( EmployeeStatusVO.TRUE );
      ( ( EmployeeStatusVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "manageEmployeeStatus" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

            //获得ActionForm
            final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;
            //设置属性
            employeeStatusVO.setCreateBy( getUserId( request, response ) );
            employeeStatusVO.setModifyBy( getUserId( request, response ) );
            employeeStatusVO.setAccountId( getAccountId( request, response ) );
            //新建对象
            employeeStatusService.insertEmployeeStatus( employeeStatusVO );

            // 重新加载常量中的EmployeeStatus
            constantsInit( "initEmployeeStatus", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Action Form
         ( ( EmployeeStatusVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );
         // 主键获取需解码
         String employeeStatusId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( employeeStatusId ) == null )
         {
            employeeStatusId = ( ( EmployeeStatusVO ) form ).getEmployeeStatusId();
         }
         // 获得主键对象
         final EmployeeStatusVO employeeStatusVO = employeeStatusService.getEmployeeStatusVOByEmployeeStatusId( employeeStatusId );
         // 刷新对象，初始化对象列表及国际化
         employeeStatusVO.reset( null, request );
         // 区分Add和Update
         employeeStatusVO.setSubAction( VIEW_OBJECT );
         // 传回ActionForm对象到前端
         request.setAttribute( "employeeStatusForm", employeeStatusVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      request.setAttribute( "role", getRole( request, response ) );
      return mapping.findForward( "manageEmployeeStatus" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service
            final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

            // 主键获取需解码
            final String employeeStatusId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获得主键对象
            final EmployeeStatusVO employeeStatusVO = employeeStatusService.getEmployeeStatusVOByEmployeeStatusId( employeeStatusId );
            // 装载界面传值
            employeeStatusVO.update( ( EmployeeStatusVO ) form );
            // 获取登录用户
            employeeStatusVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            employeeStatusService.updateEmployeeStatus( employeeStatusVO );
            // 重新加载常量中的EmployeeStatus
            constantsInit( "initEmployeeStatus", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Action Form
         ( ( EmployeeStatusVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final EmployeeStatusService employeeStatusService = ( EmployeeStatusService ) getService( "employeeStatusService" );

         //获得ActionForm
         final EmployeeStatusVO employeeStatusVO = ( EmployeeStatusVO ) form;
         if ( employeeStatusVO.getSelectedIds() != null && !employeeStatusVO.getSelectedIds().equals( "" ) )
         {
            for ( String selectedId : employeeStatusVO.getSelectedIds().split( "," ) )
            {
               employeeStatusVO.setEmployeeStatusId( selectedId );
               employeeStatusVO.setModifyBy( getUserId( request, response ) );
               //调用删除接口
               employeeStatusService.deleteEmployeeStatus( employeeStatusVO );
            }
         }

         // 重新加载常量中的EmployeeStatus
         constantsInit( "initEmployeeStatus", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         employeeStatusVO.setSelectedIds( "" );
         employeeStatusVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}