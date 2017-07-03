package com.kan.base.web.actions.management;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.PositionGradeCurrencyVO;
import com.kan.base.domain.management.PositionGradeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.PositionGradeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionGradeAction extends BaseAction
{
   /**
    * AccessAction
    */
   public static final String accessAction = "HRO_EMPLOYEE_POSITIONGRADES";

   @Override
   // Code reviewed by Siuvan Xia at 2014-07-17
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // 获得Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
         // 如果子Action是删除用户列表
         if ( positionGradeVO.getSubAction() != null && positionGradeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( positionGradeVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListGradeHolder = new PagedListHolder();
         // 设置当前页
         pagedListGradeHolder.setPage( page );
         // 添加查找用positionGradeVO
         pagedListGradeHolder.setObject( positionGradeVO );
         // 设置每页显示条数
         pagedListGradeHolder.setPageSize( listPageSize );
         // 查询
         positionGradeService.getPositionGradeVOByCondition( pagedListGradeHolder, true );
         // 刷新对象，初始化对象列表及国际化
         refreshHolder( pagedListGradeHolder, request );
         // 添加pageHolder
         request.setAttribute( "positionGradeHolder", pagedListGradeHolder );

         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPositionGradeTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPositionGrade" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      this.saveToken( request );
      ( ( PositionGradeVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( PositionGradeVO ) form ).setStatus( BaseVO.TRUE );

      // 跳转到新建界面
      return mapping.findForward( "managePositionGrade" );
   }

   @Override
   // Code Reviewed by Siuvan Xia at 2014-7-17
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // 初始化PositionGradeCurrencyVO
      final PositionGradeCurrencyVO positionGradeCurrencyVO = new PositionGradeCurrencyVO();

      try
      {
         // 防止页面重复提交
         if ( isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
            // 获得ActionForm
            final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
            positionGradeVO.setCreateBy( getUserId( request, response ) );
            positionGradeVO.setAccountId( getAccountId( request, response ) );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            positionGradeService.insertPositionGrade( positionGradeVO );
            positionGradeCurrencyVO.setPositionGradeId( positionGradeVO.getPositionGradeId() );
            // 重新加载常量中的EmployeePositionGrade
            constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            // 清空Form条件
            ( ( PositionGradeVO ) form ).reset();
         }
         else
         {
            // 清空form
            ( ( PositionGradeVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转页面
      return new PositionGradeCurrencyAction().list_object( mapping, positionGradeCurrencyVO, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   /**
    * Modify positionGrade
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );

            // 获取的positionGrade的主键ID
            final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
            // 根据主键ID获取对应的PositionGradeVO
            final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
            // 更新VO数据
            positionGradeVO.update( ( PositionGradeVO ) form );
            positionGradeVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            positionGradeService.updatePositionGrade( positionGradeVO );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            // 重新加载常量中的EmployeePositionGrade
            constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
         }

         // 清空Form条件
         ( ( PositionGradeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到list currency Action
      return new PositionGradeCurrencyAction().list_object( mapping, new PositionGradeCurrencyVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // 获得当前主键
         final String positionGradeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionGradeId" ), "UTF-8" ) );
         // 根据主键获取对应的positionGradeVO
         final PositionGradeVO positionGradeVO = positionGradeService.getPositionGradeVOByPositionGradeId( positionGradeId );
         // 删除主键对应对象
         positionGradeVO.setModifyBy( getUserId( request, response ) );
         positionGradeVO.setModifyDate( new Date() );
         // 标记删除指定的positionGradeVO
         positionGradeService.deletePositionGrade( positionGradeVO );
         // 重新加载常量中的EmployeePositionGrade
         constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PositionGradeService positionGradeService = ( PositionGradeService ) getService( "mgtPositionGradeService" );
         // 获得Action Form
         final PositionGradeVO positionGradeVO = ( PositionGradeVO ) form;
         // 存在选中的ID
         if ( positionGradeVO.getSelectedIds() != null && !positionGradeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : positionGradeVO.getSelectedIds().split( "," ) )
            {
               // 根据主键获取对应的positionGradeVO
               final PositionGradeVO positionGradeVOForDel = positionGradeService.getPositionGradeVOByPositionGradeId( selectedId );
               // 删除主键对应对象
               positionGradeVOForDel.setModifyBy( getUserId( request, response ) );
               positionGradeVOForDel.setModifyDate( new Date() );
               // 标记删除指定的positionGradeVOForDel
               positionGradeService.deletePositionGrade( positionGradeVOForDel );
            }
         }
         // 重新加载常量中的EmployeePositionGrade
         constantsInit( "initEmployeePositionGrade", getAccountId( request, response ) );
         // 清空Form条件
         ( ( PositionGradeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}