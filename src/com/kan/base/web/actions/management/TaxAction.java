package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.TaxService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class TaxAction extends BaseAction
{

   public static String accessAction = "HRO_MGT_TAX";

   /**  
    * List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 获取TaxId
         final String taxId = request.getParameter( "taxId" );

         // 初始化Service接口
         final TaxService taxService = ( TaxService ) getService( "taxService" );

         // 获取EntityId和businessTypeId
         final String entityId = request.getParameter( "entityId" );
         final String businessTypeId = request.getParameter( "businessTypeId" );

         // 初始化TaxVO
         final TaxVO taxVO = new TaxVO();
         taxVO.setAccountId( getAccountId( request, response ) );
         taxVO.setEntityId( entityId );
         taxVO.setBusinessTypeId( businessTypeId );

         // 获取TaxVO列表
         final List< Object > taxVOs = taxService.getTaxVOsByTaxVO( taxVO );

         // 生成下拉框
         if ( taxVOs != null && taxVOs.size() > 0 )
         {
            for ( Object taxVOObject : taxVOs )
            {
               final TaxVO tempTaxVO = ( TaxVO ) taxVOObject;

               // 初始化MappingVO
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( tempTaxVO.getTaxId() );
               // 中文
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( tempTaxVO.getNameZH() );
               }
               // 非中文
               else
               {
                  mappingVO.setMappingValue( tempTaxVO.getNameEN() );
               }
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "taxId", taxId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**
    * Get Object JSON
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取TaxId
         final String taxId = request.getParameter( "taxId" );

         // 获取EntityId
         final String entityId = request.getParameter( "entityId" );

         // 获取BusinessTypeId
         final String businessTypeId = request.getParameter( "businessTypeId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( taxId != null && !taxId.trim().isEmpty() )
         {
            // 获取TaxVO
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( taxId );

            if ( taxVO != null )
            {
               taxVO.reset( mapping, request );
               jsonObject = JSONObject.fromObject( taxVO );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }
         else if ( entityId != null && !entityId.trim().isEmpty() && businessTypeId != null && !businessTypeId.trim().isEmpty() )
         {
            // 获取TaxVO列表
            final List< TaxVO > taxVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOs( entityId, businessTypeId );

            if ( taxVOs != null && taxVOs.size() == 1 )
            {
               taxVOs.get( 0 ).reset( mapping, request );
               jsonObject = JSONObject.fromObject( taxVOs.get( 0 ) );
               jsonObject.put( "success", "true" );
            }
            else
            {
               jsonObject.put( "success", "false" );
            }
         }

         // Send to client
         out.println( jsonObject != null ? jsonObject.toString() : "" );
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
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // 获得Action Form
         final TaxVO taxVO = ( TaxVO ) form;
         // 需要设置当前用户AccountId
         taxVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( taxVO.getSubAction() != null && taxVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( taxVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder taxHolder = new PagedListHolder();
         // 传入当前页
         taxHolder.setPage( page );
         // 传入当前值对象
         taxHolder.setObject( taxVO );
         // 设置页面记录条数
         taxHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         taxService.getTaxVOsByCondition( taxHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( taxHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "taxHolder", taxHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listTaxTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTax" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( TaxVO ) form ).setStatus( TaxVO.TRUE );
      ( ( TaxVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageTax" );
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
            final TaxService taxService = ( TaxService ) getService( "taxService" );
            // 获得当前FORM
            final TaxVO taxVO = ( TaxVO ) form;
            taxVO.setCreateBy( getUserId( request, response ) );
            taxVO.setModifyBy( getUserId( request, response ) );
            taxVO.setAccountId( getAccountId( request, response ) );
            taxService.insertTax( taxVO );

            // 初始化常量持久对象
            constantsInit( "initTax", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form
         ( ( TaxVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // 主键获取需解码
         final String taxId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "taxId" ), "UTF-8" ) );
         // 获得TaxVO对象                                                                                          
         final TaxVO taxVO = taxService.getTaxVOByTaxId( taxId );
         // 区分Add和Update
         taxVO.setSubAction( VIEW_OBJECT );
         taxVO.reset( null, request );
         // 将TaxVO传入request对象
         request.setAttribute( "taxForm", taxVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageTax" );
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
            final TaxService taxService = ( TaxService ) getService( "taxService" );
            // 主键获取需解码
            final String taxId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "taxId" ), "UTF-8" ) );
            // 获取TaxVO对象
            final TaxVO taxVO = taxService.getTaxVOByTaxId( taxId );
            // 装载界面传值
            taxVO.update( ( TaxVO ) form );
            // 获取登录用户
            taxVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            taxService.updateTax( taxVO );

            // 初始化常量持久对象
            constantsInit( "initTax", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form
         ( ( TaxVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
         final TaxService taxService = ( TaxService ) getService( "taxService" );
         // 获得Action Form
         final TaxVO taxVO = ( TaxVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( taxVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : taxVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               taxVO.setTaxId( selectedId );
               taxVO.setAccountId( getAccountId( request, response ) );
               taxVO.setModifyBy( getUserId( request, response ) );
               taxService.deleteTax( taxVO );
            }

            // 初始化常量持久对象
            constantsInit( "initTax", getAccountId( request, response ) );
         }
         // 清除Selected IDs和子Action
         taxVO.setSelectedIds( "" );
         taxVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
