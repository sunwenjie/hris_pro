package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.ManagerDetailVO;
import com.kan.base.domain.define.ManagerHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ManagerHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ManagerHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_PAGE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 获得Action Form
         final ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;

         // 如果子Action是删除
         if ( managerHeaderVO.getSubAction() != null && managerHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( managerHeaderVO );
         }

         // 初始化Service接口
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder managerHeaderHolder = new PagedListHolder();
         // 传入当前页
         managerHeaderHolder.setPage( page );
         // 传入当前值对象
         managerHeaderHolder.setObject( managerHeaderVO );
         // 设置页面记录条数
         managerHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         managerHeaderService.getManagerHeaderVOsByCondition( managerHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( managerHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "managerHeaderHolder", managerHeaderHolder );

         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listManagerHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listManagerHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 找到当前AccountId的所有ManagerDTO
      final List< ManagerDTO > managerDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).MANAGER_DTO;

      // 如果tableId已建立过Manager，则移除。
      if ( ( ( ManagerHeaderVO ) form ).getTables() != null && ( ( ManagerHeaderVO ) form ).getTables().size() > 0 && managerDTOs != null && managerDTOs.size() > 0 )
      {
         for ( int i = ( ( ManagerHeaderVO ) form ).getTables().size() - 1; i > 0; i-- )
         {
            for ( ManagerDTO managerDTO : managerDTOs )
            {
               if ( managerDTO.getManagerHeaderVO() != null && managerDTO.getManagerHeaderVO().getTableId() != null
                     && ( ( ManagerHeaderVO ) form ).getTables().get( i ).getMappingId().equals( managerDTO.getManagerHeaderVO().getTableId() ) )
               {
                  ( ( ManagerHeaderVO ) form ).getTables().remove( i );
                  break;
               }
            }
         }
      }

      // 设置初始化字段
      ( ( ManagerHeaderVO ) form ).setStatus( ManagerHeaderVO.TRUE );
      ( ( ManagerHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageManagerHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化ManagerDetailVO
         final ManagerDetailVO managerDetailVO = new ManagerDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

            // 获得当前FORM
            final ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;
            // 获取登录用户及账户
            managerHeaderVO.setCreateBy( getUserId( request, response ) );
            managerHeaderVO.setModifyBy( getUserId( request, response ) );
            managerHeaderVO.setAccountId( getAccountId( request, response ) );

            // 添加managerHeaderVO
            managerHeaderService.insertManagerHeader( managerHeaderVO );

            managerDetailVO.setManagerHeaderId( managerHeaderVO.getManagerHeaderId() );

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, managerHeaderVO, Operate.ADD, managerHeaderVO.getManagerHeaderId(), null );
         }
         else
         {
            // 清空Form
            ( ( ManagerHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 跳转List ManagerDetail界面
         return new ManagerDetailAction().list_object( mapping, managerDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

            // 获得主键
            final String managerHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得ManagerHeaderVO对象
            final ManagerHeaderVO managerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( managerHeaderId );

            // 装载界面传值
            managerHeaderVO.update( ( ManagerHeaderVO ) form );
            // 获取登录用户
            managerHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            managerHeaderService.updateManagerHeader( managerHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, managerHeaderVO, Operate.MODIFY, managerHeaderVO.getManagerHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转List ManagerDetail界面
      return new ManagerDetailAction().list_object( mapping, new ManagerDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ManagerHeaderService managerHeaderService = ( ManagerHeaderService ) getService( "managerHeaderService" );

         // 获得Action Form
         ManagerHeaderVO managerHeaderVO = ( ManagerHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( managerHeaderVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : managerHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               final ManagerHeaderVO tempManagerHeaderVO = managerHeaderService.getManagerHeaderVOByManagerHeaderId( selectedId );
               tempManagerHeaderVO.setModifyBy( getUserId( request, response ) );
               tempManagerHeaderVO.setModifyDate( new Date() );
               managerHeaderService.deleteManagerHeader( tempManagerHeaderVO );
            }

            insertlog( request, managerHeaderVO, Operate.DELETE, null, managerHeaderVO.getSelectedIds() );

            // 初始化常量持久对象
            constantsInit( "initManagerHeader", getAccountId( request, response ) );
            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         ( ( ManagerHeaderVO ) form ).setSelectedIds( "" );
         ( ( ManagerHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
