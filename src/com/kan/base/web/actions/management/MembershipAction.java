package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.MembershipVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.MembershipService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class MembershipAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_MEMBERSHIP";

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
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // 获得Action Form
         final MembershipVO membershipVO = ( MembershipVO ) form;
         // 需要设置当前用户AccountId
         membershipVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( membershipVO.getSubAction() != null && membershipVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( membershipVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder membershipHolder = new PagedListHolder();
         // 传入当前页
         membershipHolder.setPage( page );
         // 传入当前值对象
         membershipHolder.setObject( membershipVO );
         // 设置页面记录条数
         membershipHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         membershipService.getMembershipVOsByCondition( membershipHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( membershipHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "membershipHolder", membershipHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listMembershipTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listMembership" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( MembershipVO ) form ).setStatus( MembershipVO.TRUE );
      ( ( MembershipVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageMembership" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
            // 获得当前FORM
            final MembershipVO membershipVO = ( MembershipVO ) form;
            membershipVO.setCreateBy( getUserId( request, response ) );
            membershipVO.setModifyBy( getUserId( request, response ) );
            membershipVO.setAccountId( getAccountId( request, response ) );
            membershipService.insertMembership( membershipVO );

            // 初始化常量持久对象
            constantsInit( "initMembership", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, membershipVO, Operate.ADD, membershipVO.getMembershipId(), null );
         }
         // 清空Form
         ( ( MembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // 主键获取需解码
         String membershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "membershipId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( membershipId ) == null )
         {
            membershipId = ( ( MembershipVO ) form ).getMembershipId();
         }
         // 获得MembershipVO对象                                                                                          
         final MembershipVO membershipVO = membershipService.getMembershipVOByMembershipId( membershipId );
         // 区分Add和Update
         membershipVO.setSubAction( VIEW_OBJECT );
         membershipVO.reset( null, request );
         // 将MembershipVO传入request对象
         request.setAttribute( "membershipForm", membershipVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageMembership" );
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
            final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
            // 主键获取需解码
            final String membershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "membershipId" ), "UTF-8" ) );
            // 获取MembershipVO对象
            final MembershipVO membershipVO = membershipService.getMembershipVOByMembershipId( membershipId );
            // 装载界面传值
            membershipVO.update( ( MembershipVO ) form );
            // 获取登录用户
            membershipVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            membershipService.updateMembership( membershipVO );

            // 初始化常量持久对象
            constantsInit( "initMembership", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, membershipVO, Operate.MODIFY, membershipVO.getMembershipId(), null );
         }
         // 清空Form
         ( ( MembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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
         final MembershipService membershipService = ( MembershipService ) getService( "membershipService" );
         // 获得Action Form
         final MembershipVO membershipVO = ( MembershipVO ) form;
         // 存在选中的ID
         if ( membershipVO.getSelectedIds() != null && !membershipVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : membershipVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               membershipVO.setMembershipId( selectedId );
               membershipVO.setAccountId( getAccountId( request, response ) );
               membershipVO.setModifyBy( getUserId( request, response ) );
               membershipService.deleteMembership( membershipVO );
            }

            // 初始化常量持久对象
            constantsInit( "initMembership", getAccountId( request, response ) );
            insertlog( request, membershipVO, Operate.DELETE, null, membershipVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         membershipVO.setSelectedIds( "" );
         membershipVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
