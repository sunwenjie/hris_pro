package com.kan.base.web.actions.management;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BusinessContractTemplateService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.HTMLParseUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;

public class BusinessContractTemplateAction extends BaseAction
{
   public static final String accessAction = "HRO_MGT_BUSINESS_CONTRACT_TEMPLATE";

   public static final String STRING_BLANK = "____________";

   /**  
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // 获取templateId
         final String templateId = request.getParameter( "templateId" );

         // 获得 entityId 和 businessTypeId
         final String entityId = request.getParameter( "entityId" );
         final String businessTypeId = request.getParameter( "businessTypeId" );

         // 从常量中获取的所有的BusinessContractVOs
         final List< BusinessContractTemplateVO > AllBusinessContractTemplateVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).BUSINESS_CONTRACT_TEMPLATE_VO;

         // 生成下拉框
         if ( AllBusinessContractTemplateVOs != null && AllBusinessContractTemplateVOs.size() > 0 )
         {
            // 如果是修改页面条件显示
            if ( entityId != null && businessTypeId != null )
            {
               for ( BusinessContractTemplateVO businessContractTemplateVOTemp : AllBusinessContractTemplateVOs )
               {

                  // 获得entityIds 和 businessTypeIds
                  final String entityIds = businessContractTemplateVOTemp.getEntityIds();
                  final String businessTypeIds = businessContractTemplateVOTemp.getBusinessTypeIds();

                  final String[] entityIdArray = KANUtil.jasonArrayToStringArray( entityIds );
                  final String[] businessTypeIdArray = KANUtil.jasonArrayToStringArray( businessTypeIds );

                  List< String > entityIdList = Arrays.asList( entityIdArray );
                  List< String > businessTypeIdList = Arrays.asList( businessTypeIdArray );

                  //jzy add 2014/4/11
                  if ( entityIdList.size() == 0 && businessTypeIdList.size() == 0 )
                  {
                     setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                  }
                  if ( entityIdList.size() == 0 && businessTypeIdList.size() > 0 )
                  {
                     if ( businessTypeIdList.contains( businessTypeId ) )
                     {
                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }
                  }
                  if ( entityIdList.size() > 0 && businessTypeIdList.size() == 0 )
                  {
                     if ( entityIdList.contains( entityId ) )
                     {
                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }
                  }
                  if ( entityIdList.size() > 0 && businessTypeIdList.size() > 0 )
                  {
                     if ( entityIdList.contains( entityId ) && businessTypeIdList.contains( businessTypeId ) )
                     {
                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }
                  }
               }

            }
            else
            {
               for ( BusinessContractTemplateVO businessContractTemplateVOTemp : AllBusinessContractTemplateVOs )
               {
                  // 如果是中文环境
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
                     mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() );
                     mappingVOs.add( mappingVO );
                  }
                  // 如果是非中文环境
                  else
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
                     mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameEN() );
                     mappingVOs.add( mappingVO );
                  }
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "templateId", templateId ) );
         out.flush();
         out.close();
         // 刷新变量
         constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
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
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // 获得Action Form
         final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;
         // 需要设置当前用户AccountId
         businessContractTemplateVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( businessContractTemplateVO.getSubAction() != null && businessContractTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( businessContractTemplateVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder businessContractTemplateHolder = new PagedListHolder();
         // 传入当前页
         businessContractTemplateHolder.setPage( page );
         // 传入当前值对象
         businessContractTemplateHolder.setObject( businessContractTemplateVO );
         // 设置页面记录条数
         businessContractTemplateHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         businessContractTemplateService.getBusinessContractTemplateVOsByCondition( businessContractTemplateHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( businessContractTemplateHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "businessContractTemplateHolder", businessContractTemplateHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listBusinessContractTemplateTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listBusinessContractTemplate" );
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

      // 初始化设置Tab Number
      request.setAttribute( "entityCount", 0 );
      request.setAttribute( "businessTypeCount", 0 );

      // 初始化设置
      ( ( BusinessContractTemplateVO ) form ).setContentType( "2" );
      ( ( BusinessContractTemplateVO ) form ).setStatus( BusinessContractTemplateVO.TRUE );
      // 设置Sub Action
      ( ( BusinessContractTemplateVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageBusinessContractTemplate" );
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
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // 获得当前FORM
            final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;
            // 获取EntityId Array和Business TypeId Array
            final String[] entityIdArray = businessContractTemplateVO.getEntityIdArray();
            final String[] businessTypeIdArray = businessContractTemplateVO.getBusinessTypeIdArray();

            // 如果EntityId Array 不为空则转换成Jason并存入businessContractTemplateVO中
            if ( entityIdArray != null && entityIdArray.length > 0 )
            {
               businessContractTemplateVO.setEntityIds( KANUtil.toJasonArray( entityIdArray ) );
            }
            // 如果Business TypeId Array 不为空则转换成Jason并存入businessContractTemplateVO中
            if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
            {
               businessContractTemplateVO.setBusinessTypeIds( KANUtil.toJasonArray( businessTypeIdArray ) );
            }

            businessContractTemplateVO.setCreateBy( getUserId( request, response ) );
            businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
            businessContractTemplateVO.setAccountId( getAccountId( request, response ) );
            businessContractTemplateService.insertBusinessContractTemplate( businessContractTemplateVO );

            constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form
         ( ( BusinessContractTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // 主键获取需解码
         String businessContractTemplateId = KANUtil.decodeString( request.getParameter( "templateId" ) );

         if ( businessContractTemplateId == null || businessContractTemplateId.trim().isEmpty() )
         {
            businessContractTemplateId = ( ( BusinessContractTemplateVO ) form ).getTemplateId();
         }

         // 获得BusinessContractTemplateVO对象                                                                                          
         final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

         // 区分Add和Update
         businessContractTemplateVO.setSubAction( VIEW_OBJECT );
         businessContractTemplateVO.reset( null, request );

         // 添加Entity Count用于Tab Number显示
         final int entityCount = businessContractTemplateVO.getEntityIdArray().length;

         // 添加Entity Count用于Tab Number显示
         final int businessTypeCount = businessContractTemplateVO.getBusinessTypeIdArray().length;

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "businessContractTemplateId", businessContractTemplateId );

         // 将BusinessContractTemplateVO传入request对象
         request.setAttribute( "businessContractTemplateForm", businessContractTemplateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageBusinessContractTemplate" );
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
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // 主键获取需解码
            final String businessContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
            // 获取BusinessContractTemplateVO对象
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

            // 装载界面传值
            businessContractTemplateVO.update( ( BusinessContractTemplateVO ) form );
            // 获取登录用户
            businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO );

            // 初始化常量
            constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form
         ( ( BusinessContractTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Ajax Tab
    *	Ajax修改（Tab区域）
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   public void modify_object_ajax_tab( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         boolean flag = false;

         // 获得BusinessContractTemplateId
         final String businessContractTemplateId = request.getParameter( "businessContractTemplateId" );

         if ( businessContractTemplateId != null && !businessContractTemplateId.trim().isEmpty() )
         {
            // 初始化 Service接口
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

            final String entityId = request.getParameter( "entityId" );
            final String businessTypeId = request.getParameter( "businessTypeId" );
            final String actionFlag = request.getParameter( "actionFlag" );

            // 如果是删除
            if ( actionFlag != null && actionFlag.trim().equals( "delObject" ) )
            {
               // 修改EntityIds
               if ( entityId != null && !entityId.trim().isEmpty() )
               {
                  String[] entityIdArray = businessContractTemplateVO.getEntityIdArray();

                  if ( entityIdArray != null && entityIdArray.length > 0 )
                  {
                     List< String > entityIdList = new ArrayList< String >();

                     for ( String tempEntityId : entityIdArray )
                     {
                        if ( !entityId.equals( tempEntityId ) )
                        {
                           entityIdList.add( tempEntityId );
                        }
                     }

                     // 获得删除后的EntityIds
                     entityIdArray = entityIdList.toArray( new String[ entityIdList.size() ] );
                     final String entityIds = KANUtil.toJasonArray( entityIdArray );
                     businessContractTemplateVO.setEntityIds( entityIds );
                     businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
                     businessContractTemplateVO.setModifyDate( new Date() );

                     if ( businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO ) > 0 )
                     {
                        flag = true;
                     }
                  }

               }

               // 修改BusinessTypeIds
               if ( businessTypeId != null && !businessTypeId.trim().isEmpty() )
               {
                  String[] businessTypeIdArray = businessContractTemplateVO.getBusinessTypeIdArray();

                  if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
                  {
                     List< String > businessTypeIdList = new ArrayList< String >();

                     for ( String tempBusinessTypeId : businessTypeIdArray )
                     {
                        if ( !businessTypeId.equals( tempBusinessTypeId ) )
                        {
                           businessTypeIdList.add( tempBusinessTypeId );
                        }
                     }

                     // 获得删除后的BusinessTypeIds
                     businessTypeIdArray = businessTypeIdList.toArray( new String[ businessTypeIdList.size() ] );
                     final String businessTypeIds = KANUtil.toJasonArray( businessTypeIdArray );
                     businessContractTemplateVO.setBusinessTypeIds( businessTypeIds );
                     businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
                     businessContractTemplateVO.setModifyDate( new Date() );

                     if ( businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO ) > 0 )
                     {
                        flag = true;
                     }

                  }

               }

               // 返回状态至Ajax
               if ( flag )
               {
                  deleteSuccessAjax( out, null );
               }
               else
               {
                  deleteFailedAjax( out, null );
               }

            }
            // 如果是添加
            else if ( actionFlag != null && actionFlag.trim().equals( "addObject" ) )
            {
               // 修改EntityIds
               if ( entityId != null && !entityId.trim().isEmpty() )
               {
                  String[] entityIdArray = businessContractTemplateVO.getEntityIdArray();
                  List< String > entityIdList = new ArrayList< String >();

                  if ( entityIdArray != null && entityIdArray.length > 0 )
                  {

                     for ( String tempEntityId : entityIdArray )
                     {
                        entityIdList.add( tempEntityId );
                     }
                  }
                  entityIdList.add( entityId );

                  // 获得添加后的EntityIds
                  entityIdArray = entityIdList.toArray( new String[ entityIdList.size() ] );
                  final String entityIds = KANUtil.toJasonArray( entityIdArray );
                  businessContractTemplateVO.setEntityIds( entityIds );
                  businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
                  businessContractTemplateVO.setModifyDate( new Date() );

                  if ( businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO ) > 0 )
                  {
                     flag = true;
                  }

               }

               // 修改BusinessTypeIds
               if ( businessTypeId != null && !businessTypeId.trim().isEmpty() )
               {
                  List< String > businessTypeIdList = new ArrayList< String >();
                  String[] businessTypeIdArray = businessContractTemplateVO.getBusinessTypeIdArray();

                  if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
                  {
                     for ( String tempBusinessTypeId : businessTypeIdArray )
                     {
                        businessTypeIdList.add( tempBusinessTypeId );
                     }
                  }
                  businessTypeIdList.add( businessTypeId );
                  // 获得添加后的BusinessTypeIds
                  businessTypeIdArray = businessTypeIdList.toArray( new String[ businessTypeIdList.size() ] );
                  final String businessTypeIds = KANUtil.toJasonArray( businessTypeIdArray );
                  businessContractTemplateVO.setBusinessTypeIds( businessTypeIds );
                  businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
                  businessContractTemplateVO.setModifyDate( new Date() );

                  if ( businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO ) > 0 )
                  {
                     flag = true;
                  }

               }

               // 返回状态至Ajax
               if ( flag )
               {
                  addSuccessAjax( out, null );
               }
               else
               {
                  addFailedAjax( out, null );
               }

            }
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // 获得Action Form
         final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;

         // 存在选中的ID
         if ( businessContractTemplateVO.getSelectedIds() != null && !businessContractTemplateVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : businessContractTemplateVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               businessContractTemplateVO.setTemplateId( selectedId );
               businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
               businessContractTemplateVO.setModifyDate( new Date() );
               businessContractTemplateService.deleteBusinessContractTemplate( businessContractTemplateVO );
            }

         }

         // 初始化常量
         constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         businessContractTemplateVO.setSelectedIds( "" );
         businessContractTemplateVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // 获取商务合同主键
         String businessContractTemplateId = KANUtil.decodeString( request.getParameter( "id" ) );
         
         if ( businessContractTemplateId == null || businessContractTemplateId.trim().isEmpty() )
         {
            businessContractTemplateId = ( ( BusinessContractTemplateVO ) form ).getTemplateId();
         }

         // 获得BusinessContractTemplateVO对象                                                                                          
         final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

         // 区分Add和Update
         businessContractTemplateVO.setSubAction( VIEW_OBJECT );
         businessContractTemplateVO.reset( null, request );

         // 添加Entity Count用于Tab Number显示
         final int entityCount = businessContractTemplateVO.getEntityIdArray().length;

         // 添加Entity Count用于Tab Number显示
         final int businessTypeCount = businessContractTemplateVO.getBusinessTypeIdArray().length;

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "businessContractTemplateId", businessContractTemplateId );

         // 将BusinessContractTemplateVO传入request对象
         request.setAttribute( "businessContractTemplateForm", businessContractTemplateVO );
         // 初始化文件名
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = businessContractTemplateVO.getNameZH() + fileName;
         }
         else
         {
            fileName = businessContractTemplateVO.getNameEN() + fileName;
         }

         // 下载商务合同PDF版本
         String content = businessContractTemplateVO.getContent();
         String logo = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE;
         ByteArrayOutputStream bos = HTMLParseUtil.htmlParsePDF( this.generateContent( content ) , businessContractTemplateVO.getTemplateId(), logo );
         new DownloadFileAction().download( response,bos , fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "行号" ) )
         {
            error( request, null, e.getMessage() );
            return mapping.findForward( "manageBusinessContractTemplate" );
         }
         else
         {
            throw new KANException( e );
         }
      }
      // Ajax调用
      return mapping.findForward( "" );
   }

   private void setContractTemplateSelectOptions( HttpServletRequest request, BusinessContractTemplateVO businessContractTemplateVOTemp, List< MappingVO > mappingVOs )
   {

      // 如果是中文环境
      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() );
         mappingVOs.add( mappingVO );
      }
      // 如果是非中文环境
      else
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameEN() );
         mappingVOs.add( mappingVO );
      }
   }

   private String generateContent( final String replaceContent ) throws KANException
   {
      String content = replaceContent;
      if ( StringUtils.isNotEmpty( content ) )
      {
         if ( content.contains( "&ldquo;" ) )
         {
            content = content.replaceAll( "&ldquo;", "\"" );
         }
         if ( content.contains( "&rdquo;" ) )
         {
            content = content.replaceAll( "&rdquo;", "\"" );
         }
         if ( content.contains( "&quot;" ) )
         {
            content = content.replaceAll( "&quot;", "\"" );
         }
         if ( content.contains( "&#39;" ) )
         {
            content = content.replaceAll( "&#39;", "\'" );
         }
         if ( content.contains( "&lt;" ) )
         {
            content = content.replaceAll( "&lt;", "<" );
         }
         if ( content.contains( "&gt;" ) )
         {
            content = content.replaceAll( "&gt;", ">" );
         }
         while ( content.contains( "${" ) )
         {
            int begin = content.indexOf( "${" );
            int end = content.indexOf( "}", begin );
            String replaceString = content.substring( begin, end + 1 );
            content = content.replace( replaceString, STRING_BLANK );
         }
      }
      return content;
   }
}
