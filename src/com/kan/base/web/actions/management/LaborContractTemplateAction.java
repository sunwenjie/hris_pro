package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ContractTemplateClientVo;
import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.LaborContractTemplateService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.util.pdf.PDFTool;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.service.inf.biz.client.ClientService;

public class LaborContractTemplateAction extends BaseAction
{
   public static String accessAction = "HRO_MGT_LABOR_CONTRACT_TEMPLATE";

   public static final String STRING_BLANK = "____________";

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
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // 获得Action Form
         final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
         // 需要设置当前用户AccountId
         laborContractTemplateVO.setAccountId( getAccountId( request, response ) );
         // 如果是客户登录
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            laborContractTemplateVO.setCorpId( getCorpId( request, response ) );
         }
         // 调用删除方法
         if ( laborContractTemplateVO.getSubAction() != null && laborContractTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( laborContractTemplateVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder laborContractTemplateHolder = new PagedListHolder();
         // 传入当前页
         laborContractTemplateHolder.setPage( page );
         // 传入当前值对象
         laborContractTemplateHolder.setObject( laborContractTemplateVO );
         // 设置页面记录条数
         laborContractTemplateHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         laborContractTemplateService.getLaborContractTemplateVOsByCondition( laborContractTemplateHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( laborContractTemplateHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "laborContractTemplateHolder", laborContractTemplateHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               return mapping.findForward( "listLaborContractTemplateTableInHouse" );
            }
            else
            {
               return mapping.findForward( "listLaborContractTemplateTable" );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return mapping.findForward( "listLaborContractTemplateInHouse" );
      }
      else
      {
         return mapping.findForward( "listLaborContractTemplate" );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 初始化设置Tab Number
      request.setAttribute( "entityCount", 0 );
      request.setAttribute( "businessTypeCount", 0 );
      request.setAttribute( "clientIdsCount", 0 );
      request.setAttribute( "clientIdsList", new ArrayList< ContractTemplateClientVo >() );

      // 设置Sub Action
      ( ( LaborContractTemplateVO ) form ).setStatus( LaborContractTemplateVO.TRUE );
      ( ( LaborContractTemplateVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( LaborContractTemplateVO ) form ).setContractTypeId( "1" );
      // 跳转到新建界面  
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return mapping.findForward( "manageLaborContractTemplateInHouse" );
      }
      else
      {
         return mapping.findForward( "manageLaborContractTemplate" );
      }
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
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            // 获得当前FORM
            final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
            // 获取EntityId Array和Labor TypeId Array
            final String[] entityIdArray = laborContractTemplateVO.getEntityIdArray();
            final String[] businessTypeIdArray = laborContractTemplateVO.getBusinessTypeIdArray();
            final String[] clientIdsArray = laborContractTemplateVO.getClientIdsArray();

            // 如果EntityId Array 不为空则转换成Jason并存入laborContractTemplateVO中
            if ( entityIdArray != null && entityIdArray.length > 0 )
            {
               laborContractTemplateVO.setEntityIds( KANUtil.toJasonArray( entityIdArray ) );
            }
            // 如果Labor TypeId Array 不为空则转换成Jason并存入laborContractTemplateVO中
            if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
            {
               laborContractTemplateVO.setBusinessTypeIds( KANUtil.toJasonArray( businessTypeIdArray ) );
            }
            // 如果Labor TypeId Array 不为空则转换成Jason并存入laborContractTemplateVO中
            if ( clientIdsArray != null && clientIdsArray.length > 0 )
            {
               laborContractTemplateVO.setClientIds( KANUtil.toJasonArray( clientIdsArray ) );
            }

            laborContractTemplateVO.setCreateBy( getUserId( request, response ) );
            laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
            laborContractTemplateVO.setAccountId( getAccountId( request, response ) );
            laborContractTemplateVO.setClientId( getClientId( request, response ) );
            laborContractTemplateVO.setCorpId( getCorpId( request, response ) );
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               laborContractTemplateVO.setContractTypeId( "2" );
            }
            laborContractTemplateService.insertLaborContractTemplate( laborContractTemplateVO );

            // 初始化常量
            constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, laborContractTemplateVO, Operate.ADD, laborContractTemplateVO.getTemplateId(), null );
         }

         // 清空Form
         ( ( LaborContractTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   /**  
    * Modify Object Ajax Tab
    *	
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

         // 获得LaborContractTemplateId
         final String laborContractTemplateId = request.getParameter( "laborContractTemplateId" );

         if ( laborContractTemplateId != null && !laborContractTemplateId.trim().isEmpty() )
         {
            // 初始化 Service接口
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

            final String entityId = request.getParameter( "entityId" );
            final String businessTypeId = request.getParameter( "businessTypeId" );
            final String actionFlag = request.getParameter( "actionFlag" );

            // 如果是删除
            if ( actionFlag != null && actionFlag.trim().equals( "delObject" ) )
            {
               // 修改EntityIds
               if ( entityId != null && !entityId.trim().isEmpty() )
               {
                  String[] entityIdArray = laborContractTemplateVO.getEntityIdArray();

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
                     laborContractTemplateVO.setEntityIds( entityIds );
                     laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
                     laborContractTemplateVO.setModifyDate( new Date() );

                     if ( laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO ) > 0 )
                     {
                        flag = true;
                     }
                  }

               }

               // 修改BusinessTypeIds
               if ( businessTypeId != null && !businessTypeId.trim().isEmpty() )
               {
                  String[] businessTypeIdArray = laborContractTemplateVO.getBusinessTypeIdArray();

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
                     laborContractTemplateVO.setBusinessTypeIds( businessTypeIds );
                     laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
                     laborContractTemplateVO.setModifyDate( new Date() );

                     if ( laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO ) > 0 )
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
            else if ( actionFlag != null && actionFlag.trim().equals( "addObject" ) )
            {
               // 修改EntityIds
               if ( entityId != null && !entityId.trim().isEmpty() )
               {
                  String[] entityIdArray = laborContractTemplateVO.getEntityIdArray();
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
                  laborContractTemplateVO.setEntityIds( entityIds );
                  laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
                  laborContractTemplateVO.setModifyDate( new Date() );

                  if ( laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO ) > 0 )
                  {
                     flag = true;
                  }

               }

               // 修改BusinessTypeIds
               if ( businessTypeId != null && !businessTypeId.trim().isEmpty() )
               {
                  List< String > laborTypeIdList = new ArrayList< String >();
                  String[] laborTypeIdArray = laborContractTemplateVO.getBusinessTypeIdArray();

                  if ( laborTypeIdArray != null && laborTypeIdArray.length > 0 )
                  {
                     for ( String tempBusinessTypeId : laborTypeIdArray )
                     {
                        laborTypeIdList.add( tempBusinessTypeId );
                     }
                  }
                  laborTypeIdList.add( businessTypeId );
                  // 获得添加后的BusinessTypeIds
                  laborTypeIdArray = laborTypeIdList.toArray( new String[ laborTypeIdList.size() ] );
                  final String laborTypeIds = KANUtil.toJasonArray( laborTypeIdArray );
                  laborContractTemplateVO.setBusinessTypeIds( laborTypeIds );
                  laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
                  laborContractTemplateVO.setModifyDate( new Date() );

                  if ( laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO ) > 0 )
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
            else
            {
               //final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
               final String[] clientIdsArray = request.getParameterValues( "clientIdsArray" );
               laborContractTemplateVO.setClientIds( KANUtil.toJasonArray( clientIdsArray ) );
               laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
               laborContractTemplateVO.setModifyDate( new Date() );
               if ( laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO ) > 0 )
               {
                  flag = true;
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
         constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );
         out.flush();
         out.close();
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
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // 主键获取需解码
         String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( laborContractTemplateId ) == null )
         {
            laborContractTemplateId = ( ( LaborContractTemplateVO ) form ).getTemplateId();
         }
         // 获得LaborContractTemplateVO对象                                                                                          
         final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );
         // 区分Add和Update
         laborContractTemplateVO.setSubAction( VIEW_OBJECT );
         laborContractTemplateVO.reset( null, request );

         // 添加Entity Count用于Tab Number显示
         final int entityCount = laborContractTemplateVO.getEntityIdArray() == null ? 0 : laborContractTemplateVO.getEntityIdArray().length;

         // 添加Entity Count用于Tab Number显示
         final int businessTypeCount = laborContractTemplateVO.getBusinessTypeIdArray() == null ? 0 : laborContractTemplateVO.getBusinessTypeIdArray().length;

         //添加Tab显示客户list
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         List< ContractTemplateClientVo > clientIdsList = clientService.clientIdsForTab( laborContractTemplateVO.getTemplateId(), laborContractTemplateVO.getClientIds() );

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "clientIdsCount", clientIdsList.size() );
         request.setAttribute( "clientIdsList", clientIdsList );
         request.setAttribute( "laborContractTemplateId", laborContractTemplateId );

         // 将LaborContractTemplateVO传入request对象
         request.setAttribute( "laborContractTemplateForm", laborContractTemplateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return mapping.findForward( "manageLaborContractTemplateInHouse" );
      }
      else
      {
         return mapping.findForward( "manageLaborContractTemplate" );
      }
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
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            // 主键获取需解码
            final String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
            // 获取LaborContractTemplateVO对象
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

            // 装载界面传值
            laborContractTemplateVO.update( ( LaborContractTemplateVO ) form );
            final String[] clientIdsArray = laborContractTemplateVO.getClientIdsArray();
            // 如果Labor TypeId Array 不为空则转换成Jason并存入laborContractTemplateVO中
            if ( clientIdsArray != null && clientIdsArray.length > 0 )
            {
               laborContractTemplateVO.setClientIds( KANUtil.toJasonArray( clientIdsArray ) );
            }
            // 如果是InHouse
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               laborContractTemplateVO.setContractTypeId( "2" );
            }
            // 获取登录用户
            laborContractTemplateVO.setModifyBy( getAccountId( request, response ) );
            // 调用修改方法
            laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO );
            // 初始化常量
            constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, laborContractTemplateVO, Operate.MODIFY, laborContractTemplateVO.getTemplateId(), null );
         }
         // 清空Form
         ( ( LaborContractTemplateVO ) form ).reset();
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
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // 获得Action Form
         final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
         // 存在选中的ID
         if ( laborContractTemplateVO.getSelectedIds() != null && !laborContractTemplateVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : laborContractTemplateVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               laborContractTemplateVO.setTemplateId( selectedId );
               laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
               laborContractTemplateVO.setModifyDate( new Date() );
               laborContractTemplateService.deleteLaborContractTemplate( laborContractTemplateVO );
            }

            insertlog( request, laborContractTemplateVO, Operate.DELETE, null, laborContractTemplateVO.getSelectedIds() );
         }

         // 初始化常量
         constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         laborContractTemplateVO.setSelectedIds( "" );
         laborContractTemplateVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /***
    * @author Jixiang Date:2013-08-14 10:49
    */

   public ActionForward getContractTemplatesByContractTypeId_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得合同类型ID
         final String contractTypeId = request.getParameter( "contractTypeId" );
         // 初始化Service接口
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // 获得合同类型ID对应的合同模板Id
         final List< Object > laborContractTemplateVOs = laborContractTemplateService.getLaborContractTemplateVOsByContractTypeId( contractTypeId );
         // 初始化
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         if ( laborContractTemplateVOs != null && laborContractTemplateVOs.size() > 0 )
         {
            for ( Object employeeContractBaseViewObject : laborContractTemplateVOs )
            {
               final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) employeeContractBaseViewObject;
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( laborContractTemplateVO.getTemplateId() );
               if ( getLocale( request ).getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( laborContractTemplateVO.getNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( laborContractTemplateVO.getNameEN() );
               }
               mappingVOs.add( mappingVO );
            }
         }
         mappingVOs.add( 0, ( ( LaborContractTemplateVO ) form ).getEmptyMappingVO() );
         if ( mappingVOs != null && mappingVOs.size() > 0 )
         {
            String resultHTML = KANUtil.getSelectHTML( mappingVOs, "templateId", "templateId", null, null, null );
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // 初始化PrintWrite对象
            final PrintWriter out = response.getWriter();
            // Send to client
            out.println( resultHTML );
            out.flush();
            out.close();
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

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
         final String contractTypeId = request.getParameter( "contractTypeId" );
         final String clientIdPage = request.getParameter( "clientIdPage" );

         // 从常量中获取的所有的LaborContractVOs
         final List< LaborContractTemplateVO > allLaborContractTemplateVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).LABOR_CONTRACT_TEMPLATE_VO;

         final String corpId = getCorpId( request, response );
         // 生成下拉框
         if ( allLaborContractTemplateVOs != null && allLaborContractTemplateVOs.size() > 0 )
         {
            for ( LaborContractTemplateVO businessContractTemplateVOTemp : allLaborContractTemplateVOs )
            {
               if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  // InHouse 过滤CorpId
                  if ( !corpId.equals( businessContractTemplateVOTemp.getCorpId() ) )
                  {
                     continue;
                  }
               }
               else
               {
                  // HR SERVICE 去掉客户的
                  if ( KANUtil.filterEmpty( businessContractTemplateVOTemp.getCorpId() ) != null )
                  {
                     continue;
                  }
               }
               // 获得entityIds 和 businessTypeIds
               final String entityIds = businessContractTemplateVOTemp.getEntityIds();
               final String businessTypeIds = businessContractTemplateVOTemp.getBusinessTypeIds();
               final String clientIds = businessContractTemplateVOTemp.getClientIds();

               final String[] entityIdArray = KANUtil.jasonArrayToStringArray( entityIds );
               final String[] businessTypeIdArray = KANUtil.jasonArrayToStringArray( businessTypeIds );
               final String[] clientIdArray = KANUtil.jasonArrayToStringArray( clientIds );

               String contractTypeIdMemory = businessContractTemplateVOTemp.getContractTypeId();
               // 如果  entityId 和 businessTypeId 能够匹配
               //jzy add 2014/04/10 合同模板下拉框值设定
               if ( StringUtils.equals( contractTypeId, contractTypeIdMemory ) )
               {

                  if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
                  {
                     if ( entityIdArray.length == 0 )
                     {

                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }

                     if ( entityIdArray.length > 0 )
                     {
                        if ( ArrayUtils.contains( entityIdArray, entityId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }
                  }
                  else
                  {
                     //jzy add 2014/04/10 合同模板法务实体，业务类型，客户没有设定所有合同都可选
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length == 0 && clientIdArray.length == 0 )
                     {

                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }

                     //jzy add 2014/04/10 合同模板法务实体设定，，业务类型，客户没有设定，符合法务实体的合同可选
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length == 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板业务类型设定，法务实体，客户没有设定，符合业务类型的合同可选
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length > 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( businessTypeIdArray, businessTypeId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板客户设定，法务实体，业务类型没有设定，符合客户的合同可选
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length == 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板法务实体，业务类型设定，客户没有设定,符合法务实体，业务类型合同都可选
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length > 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) && ArrayUtils.contains( businessTypeIdArray, businessTypeId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板法务实体，客户设定，业务类型没有设定,符合法务实体，客户合同都可选
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length == 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) && ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板业务类型，客户设定，法务实体没有设定,符合业务类型，客户合同都可选
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length > 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( businessTypeIdArray, businessTypeId ) && ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 合同模板法务实体，业务类型，客户都设定,符合法务实体,业务类型，客户合同可选
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length > 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) && ArrayUtils.contains( businessTypeIdArray, businessTypeId )
                              && ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }
                  }
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "templateId", templateId ) );
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

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      try
      {
         String accountId = getAccountId( request, response );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化Account Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         //	初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientService.getClientByAccountId( accountId ) );
         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // 主键获取需解码
         String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
         // 获得LaborContractTemplateVO对象                                                                                          
         final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

         // 区分Add和Update
         laborContractTemplateVO.setSubAction( VIEW_OBJECT );
         laborContractTemplateVO.reset( null, request );

         // 添加Entity Count用于Tab Number显示
         final int entityCount = laborContractTemplateVO.getEntityIdArray() == null ? 0 : laborContractTemplateVO.getEntityIdArray().length;
         // 添加Entity Count用于Tab Number显示
         final int businessTypeCount = laborContractTemplateVO.getBusinessTypeIdArray() == null ? 0 : laborContractTemplateVO.getBusinessTypeIdArray().length;

         //添加Tab显示客户list
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         List< ContractTemplateClientVo > clientIdsList = clientService.clientIdsForTab( laborContractTemplateVO.getTemplateId(), laborContractTemplateVO.getClientIds() );

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "clientIdsCount", clientIdsList.size() );
         request.setAttribute( "clientIdsList", clientIdsList );
         request.setAttribute( "laborContractTemplateId", laborContractTemplateId );

         // 将LaborContractTemplateVO传入request对象
         request.setAttribute( "laborContractTemplateForm", laborContractTemplateVO );
         // 初始化文件名
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = laborContractTemplateVO.getNameZH() + fileName;
         }
         else
         {
            fileName = laborContractTemplateVO.getNameEN() + fileName;
         }

         // 下载商务合同PDF版本
         String content = laborContractTemplateVO.getContent();
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );
         String logo = KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) ? accountConstants.getClientLogoFileByCorpId( BaseAction.getCorpId( request, response ) )
               : accountConstants.OPTIONS_LOGO_FILE;

         new DownloadFileAction().download( response, PDFTool.generationPdfDzOrder( this.generateContent( content, logo ) ), fileName );
         //ByteArrayOutputStream bos = HTMLParseUtil.htmlParsePDF( this.generateContent( content ), laborContractTemplateVO.getTemplateId(), logo );
         //new DownloadFileAction().download( response, bos, fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "行号" ) )
         {
            error( request, null, e.getMessage() );
            return mapping.findForward( "manageLaborContractTemplate" );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax调用
      return mapping.findForward( "" );
   }

   private void setContractTemplateSelectOptions( HttpServletRequest request, LaborContractTemplateVO businessContractTemplateVOTemp, List< MappingVO > mappingVOs )
   {

      // 如果是中文环境
      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() + ( businessContractTemplateVOTemp.getStatus().equals( "2" ) ? "（停用）" : "" ) );
         mappingVOs.add( mappingVO );
      }
      // 如果是中文环境
      else
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameEN() + ( businessContractTemplateVOTemp.getStatus().equals( "2" ) ? "（Stop）" : "" ) );
         mappingVOs.add( mappingVO );
      }
   }

   private String generateContent( final String replaceContent, final String logo ) throws KANException
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
      String logoString = "";
      if ( StringUtils.isNotBlank( logo ) )
      {
         logoString = "<img width='160px' src=\"" + KANUtil.basePath + "/" + logo + "\" border=\"0\"/>";
      }
      content = "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=" + KANConstants.WKHTMLTOPDF_HTML_CHARSET + "'></head><body>"
            + logoString + "<div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'>" + content;
      content += "</div></div>";
      return content;
   }
}
