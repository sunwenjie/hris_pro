package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CommercialBenefitSolutionHeaderAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CB_SOLUTION";

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
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // 获得Action Form
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;

         // 是否为INHOUSE
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            commercialBenefitSolutionHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // 调用删除方法
         if ( commercialBenefitSolutionHeaderVO.getSubAction() != null && commercialBenefitSolutionHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( commercialBenefitSolutionHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder commercialBenefitSolutionHeaderHolder = new PagedListHolder();
         // 传入当前页
         commercialBenefitSolutionHeaderHolder.setPage( page );
         // 传入当前值对象
         commercialBenefitSolutionHeaderHolder.setObject( commercialBenefitSolutionHeaderVO );
         // 设置页面记录条数
         commercialBenefitSolutionHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOsByCondition( commercialBenefitSolutionHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( commercialBenefitSolutionHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "commercialBenefitSolutionHeaderHolder", commercialBenefitSolutionHeaderHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table调用，直接传回CommercialBenefitSolutionHeader JSP
            return mapping.findForward( "listCommercialBenefitSolutionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listCommercialBenefitSolutionHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setCalculateType( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setAccuracy( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setRound( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setStatus( CommercialBenefitSolutionHeaderVO.TRUE );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "listAttachmentCount", "0" );

      // 跳转到新建界面  
      return mapping.findForward( "manageCommercialBenefitSolutionHeader" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化CommercialBenefitSolutionDetailVO
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = new CommercialBenefitSolutionDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

            // 获得当下FORM
            final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;
            commercialBenefitSolutionHeaderVO.setCreateBy( getUserId( request, response ) );
            commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
            commercialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );

            // 调用添加方法
            commercialBenefitSolutionHeaderService.insertCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
            commercialBenefitSolutionDetailVO.setHeaderId( commercialBenefitSolutionHeaderVO.getHeaderId() );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            // 重新加载账户常量中的商保方案
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.ADD, commercialBenefitSolutionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空Form
            ( ( CommercialBenefitSolutionHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new CommercialBenefitSolutionDetailAction().list_object( mapping, commercialBenefitSolutionDetailVO, request, response );
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
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取主键对象
            final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( headerId );
            // 装载界面传值
            commercialBenefitSolutionHeaderVO.update( ( CommercialBenefitSolutionHeaderVO ) form );
            commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );

            // 调用修改方法
            commercialBenefitSolutionHeaderService.updateCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );

            // 重新加载账户常量中的商保方案
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.MODIFY, commercialBenefitSolutionHeaderVO.getHeaderId(), null );
         }

         // 清空Form
         ( ( CommercialBenefitSolutionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转List界面
      return new CommercialBenefitSolutionDetailAction().list_object( mapping, new CommercialBenefitSolutionDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // 获得当前form
         CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;
         // 存在选中的ID
         if ( commercialBenefitSolutionHeaderVO.getSelectedIds() != null && !commercialBenefitSolutionHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.DELETE, null, commercialBenefitSolutionHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : commercialBenefitSolutionHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( selectedId );
               commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
               commercialBenefitSolutionHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               commercialBenefitSolutionHeaderService.deleteCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
            }

         }

         // 重新加载账户常量中的商保方案
         constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         ( ( CommercialBenefitSolutionHeaderVO ) form ).setSelectedIds( "" );
         ( ( CommercialBenefitSolutionHeaderVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service
         final CommercialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( socialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderViewsByAccountId( getAccountId( request, response ) ) );

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

}
