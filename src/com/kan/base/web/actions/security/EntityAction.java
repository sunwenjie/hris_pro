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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // ��ȡclientId
         final String clientId = ( request.getParameter( "clientId" ).equals( "" ) ) ? "0" : request.getParameter( "clientId" );

         // �����������Ҷ�Ӧ��ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // ���ʻ�ClientVO
         if ( clientVO != null )
         {
            clientVO.reset( null, request );
         }

         final String accountId = clientVO.getAccountId();

         // ����������
         if ( clientVO != null && clientVO.getLegalEntity() != null && !clientVO.getLegalEntity().trim().isEmpty() && !clientVO.getLegalEntity().trim().equals( "0" ) )
         {
            // ��ʼ��Service�ӿ�
            final EntityService entityService = ( EntityService ) getService( "entityService" );
            // ����clientContractVO���������������Ҫ��clientContractVOs
            final List< Object > legalEntities = entityService.getEntityVOsByAccountId( accountId );

            if ( legalEntities != null && legalEntities.size() > 0 )
            {
               for ( Object object : legalEntities )
               {
                  final EntityVO entityVO = ( EntityVO ) object;
                  // ��������Ļ���
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
      // ��תJSPҳ��
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡEntityId
         final String entityId = request.getParameter( "entityId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ȡEntityVO
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

      // ��ת���б����
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         // �����л������ EntityVOs 
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

      // ��ת���б����
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final EntityService entityService = ( EntityService ) getService( "entityService" );
         // ���Action Form
         final EntityVO entityVO = ( EntityVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         entityVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( entityVO.getSubAction() != null && entityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( entityVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder entityHolder = new PagedListHolder();
         // ���뵱ǰҳ
         entityHolder.setPage( page );
         // ���뵱ǰֵ����
         entityHolder.setObject( entityVO );
         // ����ҳ���¼����
         entityHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         entityService.getEntityVOsByCondition( entityHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( entityHolder, request );

         // Holder��д��Request����
         request.setAttribute( "entityHolder", entityHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Entity JSP
            return mapping.findForward( "listEntityTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEntity" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( EntityVO ) form ).setStatus( TableVO.TRUE );
      // ����Ĭ�ϵĶ�����˰Ϊ���ػ�������˰
      final String independenceTax = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).OPTIONS_INDEPENDENCE_TAX;

      ( ( EntityVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( EntityVO ) form ).setIndependenceTax( independenceTax );
      // ��ת���½�����  
      return mapping.findForward( "manageEntity" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final EntityService entityService = ( EntityService ) getService( "entityService" );

            // ��õ�ǰFORM
            final EntityVO entityVO = ( EntityVO ) form;
            entityVO.setCreateBy( getUserId( request, response ) );
            entityVO.setModifyBy( getUserId( request, response ) );
            entityVO.setAccountId( getAccountId( request, response ) );

            entityService.insertEntity( entityVO );

            // ���¼��س����е�Entity
            constantsInit( "initEntity", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, entityVO, Operate.ADD, entityVO.getEntityId(), null );
         }
         else
         {
            // ���Action Form
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final EntityService entityService = ( EntityService ) getService( "entityService" );

         // ������ȡ�����
         String entityId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( entityId ) == null )
         {
            entityId = ( ( EntityVO ) form ).getEntityId();
         }
         else
         {
            entityId = Cryptogram.decodeString( URLDecoder.decode( entityId, "UTF-8" ) );
         }

         // ���EntityVO����
         final EntityVO entityVO = entityService.getEntityVOByEntityId( entityId );
         // ����Add��Update
         entityVO.setSubAction( VIEW_OBJECT );
         entityVO.reset( null, request );

         // ��EntityVO����request����
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final EntityService entityService = ( EntityService ) getService( "entityService" );

            // ������ȡ�����
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡEntityVO����
            final EntityVO entityVO = entityService.getEntityVOByEntityId( entityId );
            // װ�ؽ��洫ֵ
            entityVO.update( ( EntityVO ) form );
            // ��Ƭ
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
            // ��ȡ��¼�û�
            entityVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            entityService.updateEntity( entityVO );

            // ���¼��س����е�Entity
            constantsInit( "initEntity", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, entityVO, Operate.MODIFY, entityVO.getEntityId(), null );
         }

         // ���Action Form
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
         // ��ʼ��Service�ӿ�
         final EntityService entityService = ( EntityService ) getService( "entityService" );

         // ���Action Form
         final EntityVO entityVO = ( EntityVO ) form;
         // ����ѡ�е�ID
         if ( entityVO.getSelectedIds() != null && !entityVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : entityVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               entityVO.setEntityId( selectedId );
               entityVO.setAccountId( getAccountId( request, response ) );
               entityVO.setModifyBy( getUserId( request, response ) );
               entityService.deleteEntity( entityVO );
            }

            insertlog( request, entityVO, Operate.DELETE, null, entityVO.getSelectedIds() );
         }

         // ���¼��س����е�Entity
         constantsInit( "initEntity", getAccountId( request, response ) );

         // ���Selected IDs����Action
         entityVO.setSelectedIds( "" );
         entityVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Inhouse ���ݻ�ȡ�ͻ��µķ���ʵ��
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ����clientContractVO���������������Ҫ��clientContractVOs
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
               // ��������Ļ���
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
      // ��תJSPҳ��
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
