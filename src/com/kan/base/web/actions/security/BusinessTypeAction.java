package com.kan.base.web.actions.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BusinessTypeBaseView;
import com.kan.base.domain.security.BusinessTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.BusinessTypeService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BusinessTypeAction extends BaseAction
{
   public static String accessAction = "HRO_SYS_BUSINESSTYPE";

   /**
    * list object json
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

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         // 常量中获得所有 BusinessTypeVOs 
         final List< BusinessTypeVO > account_businessTypeVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).BUSINESS_TYPE_VO;
         BusinessTypeBaseView businessTypeBaseView = null;
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            for ( BusinessTypeVO vo : account_businessTypeVOs )
            {
               if ( getCorpId( request, response ).equals( vo.getCorpId() ) )
               {
                  businessTypeBaseView = new BusinessTypeBaseView();
                  businessTypeBaseView.setAccountId( vo.getAccountId() );
                  businessTypeBaseView.setId( vo.getBusinessTypeId() );
                  businessTypeBaseView.setName( vo.getNameZH() + " - " + vo.getNameEN() );
                  array.add( businessTypeBaseView );
               }
            }
         }
         else
         {
            for ( BusinessTypeVO vo : account_businessTypeVOs )
            {
               if ( vo.getCorpId() == null || "".equals( vo.getCorpId() ) )
               {
                  businessTypeBaseView = new BusinessTypeBaseView();
                  businessTypeBaseView.setAccountId( vo.getAccountId() );
                  businessTypeBaseView.setId( vo.getBusinessTypeId() );
                  businessTypeBaseView.setName( vo.getNameZH() + " - " + vo.getNameEN() );
                  array.add( businessTypeBaseView );
               }
            }
         }

         // Send to businessTypeGroup
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

   /**
    * List Object
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
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // 获得Action Form
         final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
         // 调用删除方法
         if ( buesinessTypeVO.getSubAction() != null && buesinessTypeVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( buesinessTypeVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder businessTypeHolder = new PagedListHolder();
         // 传入当前页
         businessTypeHolder.setPage( page );
         // 传入当前值对象
         businessTypeHolder.setObject( buesinessTypeVO );
         // 设置页面记录条数
         businessTypeHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         businessTypeService.getBusinessTypeVOsByCondition( businessTypeHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( businessTypeHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "businessTypeHolder", businessTypeHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listBusinessTypeTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listBusinessType" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( BusinessTypeVO ) form ).setStatus( BusinessTypeVO.TRUE );
      ( ( BusinessTypeVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageBusinessType" );
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

            // 获得当前FORM
            final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
            buesinessTypeVO.setCreateBy( getUserId( request, response ) );
            buesinessTypeVO.setModifyBy( getUserId( request, response ) );
            buesinessTypeVO.setAccountId( getAccountId( request, response ) );
            businessTypeService.insertBusinessType( buesinessTypeVO );

            // 初始化常量持久对象
            constantsInit( "initBusinessType", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, buesinessTypeVO, Operate.ADD, buesinessTypeVO.getBusinessTypeId(), null );
         }
         else
         {
            // 清空Form
            ( ( BusinessTypeVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 清空Form
         ( ( BusinessTypeVO ) form ).reset();
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // 主键获取需解码
         String businessTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( businessTypeId ) == null )
         {
            businessTypeId = ( ( BusinessTypeVO ) form ).getBusinessTypeId();
         }
         // 获得BusinessTypeVO对象                                                                                          
         final BusinessTypeVO buesinessTypeVO = businessTypeService.getBusinessTypeVOByBusinessTypeId( businessTypeId );
         // 区分Add和Update
         buesinessTypeVO.setSubAction( VIEW_OBJECT );
         buesinessTypeVO.reset( null, request );
         // 将BusinessTypeVO传入request对象
         request.setAttribute( "businessTypeForm", buesinessTypeVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageBusinessType" );
   }

   /**
    * Modify Object
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
            // 初始化 Service接口
            final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

            // 主键获取需解码
            final String businessTypeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取BusinessTypeVO对象
            final BusinessTypeVO buesinessTypeVO = businessTypeService.getBusinessTypeVOByBusinessTypeId( businessTypeId );
            // 装载界面传值
            buesinessTypeVO.update( ( BusinessTypeVO ) form );
            // 获取登录用户
            buesinessTypeVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            businessTypeService.updateBusinessType( buesinessTypeVO );

            // 初始化常量持久对象
            constantsInit( "initBusinessType", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, buesinessTypeVO, Operate.MODIFY, buesinessTypeVO.getBusinessTypeId(), null );
         }
         // 清空Form
         ( ( BusinessTypeVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Object List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );

         // 获得Action Form
         final BusinessTypeVO buesinessTypeVO = ( BusinessTypeVO ) form;
         // 存在选中的ID
         if ( buesinessTypeVO.getSelectedIds() != null && !buesinessTypeVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : buesinessTypeVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               buesinessTypeVO.setBusinessTypeId( selectedId );
               buesinessTypeVO.setAccountId( getAccountId( request, response ) );
               buesinessTypeVO.setModifyBy( getUserId( request, response ) );
               businessTypeService.deleteBusinessType( buesinessTypeVO );
            }

            insertlog( request, buesinessTypeVO, Operate.DELETE, null, buesinessTypeVO.getSelectedIds() );
         }

         // 初始化常量持久对象
         constantsInit( "initBusinessType", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         buesinessTypeVO.setSelectedIds( "" );
         buesinessTypeVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_object_options_ajax_byClientIdForInHouse( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化Service接口
         final BusinessTypeService businessTypeService = ( BusinessTypeService ) getService( "businessTypeService" );
         // 根据clientContractVO获得生成下拉框需要的clientContractVOs
         List< Object > businessTypes = businessTypeService.getBusinessTypeVOsByAccountId( getAccountId( request, response ) );
         List< Object > businessTypesInHouse = new ArrayList< Object >();

         final String corpId = getCorpId( request, response );

         for ( Object o : businessTypes )
         {
            if ( corpId.equals( ( ( BusinessTypeVO ) o ).getCorpId() ) )
            {
               businessTypesInHouse.add( o );
            }
         }

         if ( businessTypesInHouse != null && businessTypesInHouse.size() > 0 )
         {
            for ( Object object : businessTypesInHouse )
            {
               final BusinessTypeVO businessTypeVO = ( BusinessTypeVO ) object;
               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( businessTypeVO.getBusinessTypeId() );
                  mappingVO.setMappingValue( businessTypeVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( businessTypeVO.getBusinessTypeId() );
                  mappingVO.setMappingValue( businessTypeVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }

            }
         }
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "businessTypeId", null ) );
         out.flush();
         out.close();
         businessTypes = null;
         businessTypesInHouse = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   public void getBusinessTypeList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );

      List< BusinessTypeVO > businessTypeVOList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).BUSINESS_TYPE_VO;
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( BusinessTypeVO businessTypeVO : businessTypeVOList )
      {
         if ( StringUtils.equals( businessTypeVO.getCorpId(), BaseAction.getCorpId( request, response ) ) )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", businessTypeVO.getBusinessTypeId() );
            map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? businessTypeVO.getNameZH() : businessTypeVO.getNameEN() );
            listReturn.add( map );
         }
      }

      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }
}
