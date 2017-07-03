package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TaxTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class TaxTemplateHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_INCOMETAXTEMPLATE";

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
         final TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;

         // 如果子Action是删除
         if ( taxTemplateHeaderVO.getSubAction() != null && taxTemplateHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( taxTemplateHeaderVO );
         }

         // 初始化Service接口
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder taxTemplateHeaderHolder = new PagedListHolder();
         // 传入当前页
         taxTemplateHeaderHolder.setPage( page );
         // 传入当前值对象
         taxTemplateHeaderHolder.setObject( taxTemplateHeaderVO );
         // 设置页面记录条数
         taxTemplateHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         taxTemplateHeaderService.getTaxTemplateHeaderVOsByCondition( taxTemplateHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( taxTemplateHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "taxTemplateHeaderHolder", taxTemplateHeaderHolder );

         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listTaxTemplateHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listTaxTemplateHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置初始化字段
      ( ( TaxTemplateHeaderVO ) form ).setStatus( TaxTemplateHeaderVO.TRUE );
      ( ( TaxTemplateHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageTaxTemplateHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化TaxTemplateDetailVO
         final TaxTemplateDetailVO taxTemplateDetailVO = new TaxTemplateDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

            // 获得当前FORM
            final TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;
            // 获取登录用户及账户
            taxTemplateHeaderVO.setCreateBy( getUserId( request, response ) );
            taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            taxTemplateHeaderVO.setAccountId( getAccountId( request, response ) );

            // 添加TaxTemplateHeaderVO
            taxTemplateHeaderService.insertTaxTemplateHeader( taxTemplateHeaderVO );

            taxTemplateDetailVO.setTemplateHeaderId( taxTemplateHeaderVO.getTemplateHeaderId() );

            // 重新加载常量中的TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, taxTemplateHeaderVO, Operate.ADD, taxTemplateHeaderVO.getTemplateHeaderId(), null );
         }
         else
         {
            // 清空form
            ( ( TaxTemplateHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 跳转List TaxTemplateDetail界面
         return new TaxTemplateDetailAction().list_object( mapping, taxTemplateDetailVO, request, response );
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
            final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

            // 获得主键
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得TaxTemplateHeaderVO对象
            final TaxTemplateHeaderVO taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( headerId );

            // 装载界面传值
            taxTemplateHeaderVO.update( ( TaxTemplateHeaderVO ) form );
            // 获取登录用户
            taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            taxTemplateHeaderService.updateTaxTemplateHeader( taxTemplateHeaderVO );

            // 重新加载常量中的TaxTemplateHeader
            constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, taxTemplateHeaderVO, Operate.MODIFY, taxTemplateHeaderVO.getTemplateHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转List TaxTemplateDetail界面
      return new TaxTemplateDetailAction().list_object( mapping, new TaxTemplateDetailVO(), request, response );
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
         final TaxTemplateHeaderService taxTemplateHeaderService = ( TaxTemplateHeaderService ) getService( "taxTemplateHeaderService" );

         // 获得Action Form
         TaxTemplateHeaderVO taxTemplateHeaderVO = ( TaxTemplateHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( taxTemplateHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, taxTemplateHeaderVO, Operate.DELETE, null, taxTemplateHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : taxTemplateHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获得删除对象
               taxTemplateHeaderVO = taxTemplateHeaderService.getTaxTemplateHeaderVOByTemplateHeaderId( selectedId );
               taxTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
               taxTemplateHeaderVO.setModifyDate( new Date() );
               taxTemplateHeaderService.deleteTaxTemplateHeader( taxTemplateHeaderVO );
            }

         }

         // 重新加载常量中的TaxTemplateHeader
         constantsInit( "initTaxTemplateHeader", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         ( ( TaxTemplateHeaderVO ) form ).setSelectedIds( "" );
         ( ( TaxTemplateHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 
   * cityId_change_ajax
   * 城市change事件调用
   * 判定当前选定城市是否存在于个税模板列表 
    */
   public ActionForward cityId_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取cityId
         final String cityId = request.getParameter( "cityId" );

         // 获取当前accountId - client下TaxTemplate Mapping形式
         final List< MappingVO > taxTemplateMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxTemplate( getLocale( request ).getLanguage(), KANUtil.filterEmpty( getCorpId( request, response ) ) );

         // 初始化JSONObject
         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "success", "true" );

         // 如果存在TaxTemplate Mapping List，遍历查找
         if ( taxTemplateMappingVOs != null && taxTemplateMappingVOs.size() > 0 )
         {
            for ( MappingVO taxTemplateMappingVO : taxTemplateMappingVOs )
            {
               // 获取TaxTemplateDTO
               final TaxTemplateDTO taxTemplateDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxTemplateDTOByTemplateHeaderId( taxTemplateMappingVO.getMappingId() );

               if ( taxTemplateDTO != null && taxTemplateDTO.getTaxTemplateHeaderVO() != null && taxTemplateDTO.getTaxTemplateHeaderVO().getCityId() != null
                     && taxTemplateDTO.getTaxTemplateHeaderVO().getCityId().equals( cityId ) )
               {
                  jsonObject.remove( "success" );
                  jsonObject.put( "success", "false" );
                  break;
               }
            }
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }
}
