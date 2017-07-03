package com.kan.hro.web.actions.biz.settlement;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentHeaderService;

public class AdjustmentHeaderAction extends BaseAction
{
	public final static String accessAction = "HRO_SETTLE_ADJUSTMENT_HEADER";
   /**
    * 账单调整
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         // 获得Action Form
         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         
         String accessAction ="HRO_SETTLE_ADJUSTMENT_HEADER";
         //处理数据权限
         setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction,adjustmentHeaderVO);
         
         adjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId(), "0" ) );

         // 调用删除方法
         if ( adjustmentHeaderVO.getSubAction() != null && adjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( adjustmentHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder adjustmentHeaderHolder = new PagedListHolder();
         // 传入当前页
         adjustmentHeaderHolder.setPage( page );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( adjustmentHeaderVO.getSortColumn() == null || adjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            adjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            adjustmentHeaderVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         adjustmentHeaderHolder.setObject( adjustmentHeaderVO );
         // 设置页面记录条数
         adjustmentHeaderHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         adjustmentHeaderService.getAdjustmentHeaderVOsByCondition( adjustmentHeaderHolder, true );
         refreshHolder( adjustmentHeaderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "adjustmentHeaderHolder", adjustmentHeaderHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAdjustmentHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAdjustmentHeader" );
   }

   /**
    * 调整确认
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_confirm( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         // 获得Action Form
         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         
         String accessAction ="HRO_SETTLE_ADJUSTMENT_HEADER_CONFIRM";
         //处理数据权限
         setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction,adjustmentHeaderVO);
         
         adjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId(), "0" ) );

         // 调整确认，只查询“待审核”状态的数据
         adjustmentHeaderVO.setStatus( "2" );

         // 调用删除方法
         if ( adjustmentHeaderVO.getSubAction() != null && adjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( adjustmentHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder adjustmentHeaderHolder = new PagedListHolder();
         // 传入当前页
         adjustmentHeaderHolder.setPage( page );
         // 传入当前值对象
         adjustmentHeaderHolder.setObject( adjustmentHeaderVO );
         // 设置页面记录条数
         adjustmentHeaderHolder.setPageSize( listPageSize );

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         adjustmentHeaderService.getAdjustmentHeaderVOsByCondition( adjustmentHeaderHolder, true );
         refreshHolder( adjustmentHeaderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "adjustmentHeaderHolder", adjustmentHeaderHolder );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAdjustmentHeaderConfirmTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listAdjustmentHeaderConfirm" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( AdjustmentHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( AdjustmentHeaderVO ) form ).setAdjustmentDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
      ( ( AdjustmentHeaderVO ) form ).setBillAmountPersonal( "0" );
      ( ( AdjustmentHeaderVO ) form ).setBillAmountCompany( "0" );
      ( ( AdjustmentHeaderVO ) form ).setCostAmountPersonal( "0" );
      ( ( AdjustmentHeaderVO ) form ).setCostAmountCompany( "0" );
      ( ( AdjustmentHeaderVO ) form ).setStatus( "1" );

      request.setAttribute( "adjustmentDetailHolder", new PagedListHolder() );

      // 如果是In House登录，设置帐套数据
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageAdjustmentHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化form参数
         final AdjustmentDetailVO adjustmentDetailVO = new AdjustmentDetailVO();
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
            // 获得当前FORM
            final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
            // 设定当前用户
            adjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
            adjustmentHeaderVO.setCreateBy( getUserId( request, response ) );
            adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用添加方法
            int result = adjustmentHeaderService.insertAdjustmentHeader( adjustmentHeaderVO );
            adjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            }
         }
         else
         {
            // 清空FORM
            ( ( AdjustmentHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new AdjustmentDetailAction().list_object( mapping, adjustmentDetailVO, request, response );
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
         final String flag = request.getParameter( "flag" );
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取SBAdjustmentHeaderVO对象
            final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );
            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );
            // 装载界面传值
            adjustmentHeaderVO.update( ( AdjustmentHeaderVO ) form );
            // 获取登录用户
            adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );

            // 如果是提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( adjustmentHeaderService.submitAdjustmentHeader( adjustmentHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
               }
               else
               {
                  // 返回保存成功的标记
                  success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
                  ;
               }
            }
            else
            {
               adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );
               // 返回保存成功的标记
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            }
         }
         // 清空Action Form
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setClientNameZH( "" );
         ( ( AdjustmentHeaderVO ) form ).setClientNameEN( "" );
         if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "confirm" ) )
         {
            return list_object_confirm( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new AdjustmentDetailAction().list_object( mapping, new AdjustmentDetailVO(), request, response );
   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // 获得主键需解码
         final String adjustmentHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得LeaveVO对象
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
         // 获取登录用户
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderVO.setLocale( request.getLocale() );

         if ( adjustmentHeaderService.submitAdjustmentHeader( adjustmentHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward approve_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         adjustmentHeaderVO.setStatus( "3" );
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );

         // 清空FORM
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   public ActionForward rollback_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         adjustmentHeaderVO.setStatus( "4" );
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );

         // 清空FORM
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
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
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // 获得Action Form
         AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         // 存在选中的ID
         if ( adjustmentHeaderVO.getSelectedIds() != null && !adjustmentHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : adjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               adjustmentHeaderVO.setModifyDate( new Date() );
               adjustmentHeaderService.deleteAdjustmentHeader( adjustmentHeaderVO );
            }
         }

         // 清除Selected IDs和子Action
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( AdjustmentHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
