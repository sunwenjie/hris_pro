package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.EducationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.EducationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class EducationAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_EDUCATION";

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
         final EducationService educationService = ( EducationService ) getService( "educationService" );
         // 获得Action Form
         final EducationVO educationVO = ( EducationVO ) form;
         // 需要设置当前用户AccountId
         educationVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( educationVO.getSubAction() != null && educationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( educationVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder educationHolder = new PagedListHolder();
         // 传入当前页
         educationHolder.setPage( page );
         // 传入当前值对象
         educationHolder.setObject( educationVO );
         // 设置页面记录条数
         educationHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         educationService.getEducationVOsByCondition( educationHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( educationHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "educationHolder", educationHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listEducationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listEducation" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( EducationVO ) form ).setStatus( EducationVO.TRUE );
      ( ( EducationVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageEducation" );
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
            final EducationService educationService = ( EducationService ) getService( "educationService" );

            // 获得当前FORM
            final EducationVO educationVO = ( EducationVO ) form;
            educationVO.setCreateBy( getUserId( request, response ) );
            educationVO.setModifyBy( getUserId( request, response ) );
            educationVO.setAccountId( getAccountId( request, response ) );
            educationService.insertEducation( educationVO );

            // 初始化常量持久对象
            constantsInit( "initEducation", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, educationVO, Operate.ADD, educationVO.getEducationId(), null );
         }
         else
         {
            // 清空FORM
            ( ( EducationVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         // 清空Action Form
         ( ( EducationVO ) form ).reset();
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
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final EducationService educationService = ( EducationService ) getService( "educationService" );
         // 主键获取需解码
         String educationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( educationId ) == null )
         {
            educationId = ( ( EducationVO ) form ).getEducationId();
         }
         // 获得EducationVO对象
         final EducationVO educationVO = educationService.getEducationVOByEducationId( educationId );
         educationVO.reset( null, request );
         // 区分Add和Update
         educationVO.setSubAction( VIEW_OBJECT );
         // 将EducationVO传入request对象
         request.setAttribute( "educationForm", educationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageEducation" );
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
            final EducationService educationService = ( EducationService ) getService( "educationService" );

            // 主键获取需解码
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获得EducationVO对象
            final EducationVO educationVO = educationService.getEducationVOByEducationId( entityId );
            // 装载界面传值
            educationVO.update( ( EducationVO ) form );
            // 获取登录用户
            educationVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            educationService.updateEducation( educationVO );

            // 初始化常量持久对象
            constantsInit( "initEducation", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, educationVO, Operate.MODIFY, educationVO.getEducationId(), null );
         }

         // 清空Action Form
         ( ( EducationVO ) form ).reset();
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
         final EducationService educationService = ( EducationService ) getService( "educationService" );

         // 获得Action Form
         final EducationVO educationVO = ( EducationVO ) form;
         // 存在选中的ID
         if ( educationVO.getSelectedIds() != null && !educationVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : educationVO.getSelectedIds().split( "," ) )
            {
               educationVO.setEducationId( selectedId );
               educationVO.setModifyBy( getUserId( request, response ) );
               // 调用删除接口
               educationService.deleteEducation( educationVO );
            }

            // 初始化常量持久对象
            constantsInit( "initEducation", getAccountId( request, response ) );

            insertlog( request, educationVO, Operate.DELETE, null, educationVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         educationVO.setSelectedIds( "" );
         educationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
