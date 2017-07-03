package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.YERRRuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class YERRRuleAction extends BaseAction
{
   public final static String accessAction = "HRO_PM_YERR_RULE";

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
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );
         // 获得Action Form
         final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
         // 需要设置当前用户AccountId
         yerrRuleVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( yerrRuleVO.getSubAction() != null && yerrRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder yerrRuleHolder = new PagedListHolder();
         // 传入当前页
         yerrRuleHolder.setPage( page );
         // 传入当前值对象
         yerrRuleHolder.setObject( yerrRuleVO );
         // 设置页面记录条数
         yerrRuleHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         yerrRuleService.getYERRRuleVOsByCondition( yerrRuleHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( yerrRuleHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "yerrRuleHolder", yerrRuleHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listYERRRuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listYERRRule" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      // 设置Sub Action
      ( ( YERRRuleVO ) form ).setDistribution( 2 );
      ( ( YERRRuleVO ) form ).setStatus( YERRRuleVO.TRUE );
      ( ( YERRRuleVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageYERRRule" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

            // 获得当前FORM
            final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
            yerrRuleVO.setDistribution( yerrRuleVO.getDistribution() );
            yerrRuleVO.setCreateBy( getUserId( request, response ) );
            yerrRuleVO.setModifyBy( getUserId( request, response ) );
            yerrRuleVO.setAccountId( getAccountId( request, response ) );
            yerrRuleService.insertYERRRule( yerrRuleVO );

            // 初始化常量持久对象
            constantsInit( "initYERRRule", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, yerrRuleVO, Operate.ADD, yerrRuleVO.getRuleId(), null );
         }
         else
         {
            // 清空FORM
            ( ( YERRRuleVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 清空Action Form
         ( ( YERRRuleVO ) form ).reset();
      }
      catch ( Exception e )
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
         // 初始化 Service接口
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );
         // 主键获取需解码
         String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( ruleId ) == null )
         {
            ruleId = ( ( YERRRuleVO ) form ).getRuleId();
         }
         // 获得YERRRuleVO对象
         YERRRuleVO yerrRuleVO = yerrRuleService.getYERRRuleVOByRuleId( ruleId );
         yerrRuleVO.reset( null, request );
         // 区分Add和Update
         yerrRuleVO.setSubAction( VIEW_OBJECT );
         // 将YERRRuleVO传入request对象
         request.setAttribute( "yerrRuleForm", yerrRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageYERRRule" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

            // 主键获取需解码
            final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获取YERRRuleVO对象
            final YERRRuleVO yerrRuleVO = yerrRuleService.getYERRRuleVOByRuleId( ruleId );
            // 装载界面传值
            yerrRuleVO.update( ( YERRRuleVO ) form );
            // 获取登录用户
            yerrRuleVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            yerrRuleService.updateYERRRule( yerrRuleVO );

            // 初始化常量持久对象
            constantsInit( "initYERRRule", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, yerrRuleVO, Operate.MODIFY, yerrRuleVO.getRuleId(), null );
         }

         // 清空Action Form
         ( ( YERRRuleVO ) form ).reset();
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
         final YERRRuleService yerrRuleService = ( YERRRuleService ) getService( "yerrRuleService" );

         // 获得Action Form
         final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) form;
         // 存在选中的ID
         if ( yerrRuleVO.getSelectedIds() != null && !yerrRuleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : yerrRuleVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               yerrRuleVO.setRuleId( selectedId );
               yerrRuleVO.setModifyBy( getUserId( request, response ) );
               yerrRuleVO.setAccountId( getAccountId( request, response ) );
               yerrRuleService.deleteYERRRule( yerrRuleVO );
            }

            // 初始化常量持久对象
            constantsInit( "initYERRRule", getAccountId( request, response ) );
            insertlog( request, yerrRuleVO, Operate.DELETE, null, yerrRuleVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         yerrRuleVO.setSelectedIds( "" );
         yerrRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
