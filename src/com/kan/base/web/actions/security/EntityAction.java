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
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.domain.security.EntityBaseView;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.EntityService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientService;

public class EntityAction extends BaseAction
{

   public static String accessAction = "HRO_SYS_ENTITY";

   /**  
    * list_object_options_ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_options_ajax_byClientId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获取clientId
         final String clientId = ( request.getParameter( "clientId" ).equals( "" ) ) ? "0" : request.getParameter( "clientId" );

         // 根据主键查找对应的ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // 国际化ClientVO
         if ( clientVO != null )
         {
            clientVO.reset( null, request );
         }

         final String accountId = clientVO.getAccountId();

         // 生成下拉框
         if ( clientVO != null && clientVO.getLegalEntity() != null && !clientVO.getLegalEntity().trim().isEmpty() && !clientVO.getLegalEntity().trim().equals( "0" ) )
         {
            // 初始化Service接口
            final EntityService entityService = ( EntityService ) getService( "entityService" );
            // 根据clientContractVO获得生成下拉框需要的clientContractVOs
            final List< Object > legalEntities = entityService.getEntityVOsByAccountId( accountId );

            if ( legalEntities != null && legalEntities.size() > 0 )
            {
               for ( Object object : legalEntities )
               {
                  final EntityVO entityVO = ( EntityVO ) object;
                  // 如果是中文环境
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( entityVO.getEntityId() );
                     mappingVO.setMappingValue( entityVO.getNameZH() );
                     mappingVOs.add( mappingVO );
                  }
                  else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( entityVO.getEntityId() );
                     mappingVO.setMappingValue( entityVO.getNameEN() );
                     mappingVOs.add( mappingVO );
                  }

               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "entityId", ( clientVO == null ) ? "0" : clientVO.getLegalEntity() ) );
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

         // 获取EntityId
         final String entityId = request.getParameter( "entityId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 获取EntityVO
         final EntityVO entityVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( entityId );
         if ( entityVO != null )
         {
            entityVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( entityVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
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
         // 常量中获得所有 EntityVOs 
         final List< EntityVO > account_entityVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ENTITY_VO;
         EntityBaseView entityBaseView = null;
         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
         {
            for ( EntityVO entityVO : account_entityVOs )
            {
               if ( getClientId( request, response ).equals( entityVO.getClientId() ) )
               {
                  entityBaseView = new EntityBaseView();
                  entityBaseView.setAccountId( entityVO.getAccountId() );
                  entityBaseView.setId( entityVO.getEntityId() );
                  entityBaseView.setName( entityVO.getNameZH() + " - " + entityVO.getNameEN() );
                  array.add( entityBaseView );
               }
            }
         }
         else
         {
            for ( EntityVO entityVO : account_entityVOs )
            {
               if ( entityVO.getCorpId() == null || "".equals( entityVO.getCorpId() ) )
               {
                  entityBaseView = new EntityBaseView();
                  entityBaseView.setAccountId( entityVO.getAccountId() );
                  entityBaseView.setId( entityVO.getEntityId() );
                  entityBaseView.setName( entityVO.getNameZH() + " - " + entityVO.getNameEN() );
                  array.add( entityBaseView );
               }
            }
         }

         // Send to entityGroup
         out.println( array.toString() );
         out.flush();
         out.close();
         entityBaseView = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

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
         final EntityService entityService = ( EntityService ) getService( "entityService" );
         // 获得Action Form
         final EntityVO entityVO = ( EntityVO ) form;
         // 需要设置当前用户AccountId
         entityVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( entityVO.getSubAction() != null && entityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( entityVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder entityHolder = new PagedListHolder();
         // 传入当前页
         entityHolder.setPage( page );
         // 传入当前值对象
         entityHolder.setObject( entityVO );
         // 设置页面记录条数
         entityHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         entityService.getEntityVOsByCondition( entityHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( entityHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "entityHolder", entityHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Entity JSP
            return mapping.findForward( "listEntityTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listEntity" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( EntityVO ) form ).setStatus( TableVO.TRUE );
      // 设置默认的独立报税为本地化独立报税
      final String independenceTax = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).OPTIONS_INDEPENDENCE_TAX;

      ( ( EntityVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( EntityVO ) form ).setIndependenceTax( independenceTax );
      // 跳转到新建界面  
      return mapping.findForward( "manageEntity" );
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
            final EntityService entityService = ( EntityService ) getService( "entityService" );

            // 获得当前FORM
            final EntityVO entityVO = ( EntityVO ) form;
            entityVO.setCreateBy( getUserId( request, response ) );
            entityVO.setModifyBy( getUserId( request, response ) );
            entityVO.setAccountId( getAccountId( request, response ) );

            entityService.insertEntity( entityVO );

            // 重新加载常量中的Entity
            constantsInit( "initEntity", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, entityVO, Operate.ADD, entityVO.getEntityId(), null );
         }
         else
         {
            // 清空Action Form
            ( ( EntityVO ) form ).reset();

            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
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
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final EntityService entityService = ( EntityService ) getService( "entityService" );

         // 主键获取需解码
         String entityId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( entityId ) == null )
         {
            entityId = ( ( EntityVO ) form ).getEntityId();
         }
         else
         {
            entityId = Cryptogram.decodeString( URLDecoder.decode( entityId, "UTF-8" ) );
         }

         // 获得EntityVO对象
         final EntityVO entityVO = entityService.getEntityVOByEntityId( entityId );
         // 区分Add和Update
         entityVO.setSubAction( VIEW_OBJECT );
         entityVO.reset( null, request );

         // 将EntityVO传入request对象
         request.setAttribute( "entityForm", entityVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageEntity" );
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
            final EntityService entityService = ( EntityService ) getService( "entityService" );

            // 主键获取需解码
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获取EntityVO对象
            final EntityVO entityVO = entityService.getEntityVOByEntityId( entityId );
            // 装载界面传值
            entityVO.update( ( EntityVO ) form );
            // 照片
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            if ( imageFileArray == null )
            {
               entityVO.setLogoFile( null );
            }
            else
            {
               String imageFileString = "";
               for ( String s : imageFileArray )
               {
                  imageFileString += s;
                  imageFileString += "##";
               }
               entityVO.setLogoFile( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }
            // 获取登录用户
            entityVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            entityService.updateEntity( entityVO );

            // 重新加载常量中的Entity
            constantsInit( "initEntity", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, entityVO, Operate.MODIFY, entityVO.getEntityId(), null );
         }

         // 清空Action Form
         ( ( EntityVO ) form ).reset();
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
         final EntityService entityService = ( EntityService ) getService( "entityService" );

         // 获得Action Form
         final EntityVO entityVO = ( EntityVO ) form;
         // 存在选中的ID
         if ( entityVO.getSelectedIds() != null && !entityVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : entityVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               entityVO.setEntityId( selectedId );
               entityVO.setAccountId( getAccountId( request, response ) );
               entityVO.setModifyBy( getUserId( request, response ) );
               entityService.deleteEntity( entityVO );
            }

            insertlog( request, entityVO, Operate.DELETE, null, entityVO.getSelectedIds() );
         }

         // 重新加载常量中的Entity
         constantsInit( "initEntity", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         entityVO.setSelectedIds( "" );
         entityVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Inhouse 根据获取客户下的法务实体
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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

         // 根据clientContractVO获得生成下拉框需要的clientContractVOs
         List< EntityVO > legalEntities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ENTITY_VO;
         List< EntityVO > legalEntitiesInHouse = new ArrayList< EntityVO >();
         final String corpId = getCorpId( request, response );

         for ( EntityVO entityVO : legalEntities )
         {
            if ( corpId.equals( entityVO.getCorpId() ) )
            {
               legalEntitiesInHouse.add( entityVO );
            }
         }

         if ( legalEntitiesInHouse != null && legalEntitiesInHouse.size() > 0 )
         {
            for ( Object object : legalEntitiesInHouse )
            {
               final EntityVO entityVO = ( EntityVO ) object;
               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( entityVO.getEntityId() );
                  mappingVO.setMappingValue( entityVO.getNameZH() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( entityVO.getEntityId() );
                  mappingVO.setMappingValue( entityVO.getNameEN() );
                  mappingVOs.add( mappingVO );
               }

            }
         }
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "entityId", null ) );
         out.flush();
         out.close();
         legalEntitiesInHouse = null;
         legalEntities = null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   public void getEntityList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );

      List< EntityVO > entityVOList = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).ENTITY_VO;
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( EntityVO entityVO : entityVOList )
      {
         if ( StringUtils.equals( entityVO.getCorpId(), BaseAction.getCorpId( request, response ) ) )
         {
            Map< String, String > map = new HashMap< String, String >();
            map.put( "id", entityVO.getEntityId() );
            map.put( "name", request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) ? entityVO.getNameZH() : entityVO.getNameEN() );
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
