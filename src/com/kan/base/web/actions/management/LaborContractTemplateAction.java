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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // ���Action Form
         final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         laborContractTemplateVO.setAccountId( getAccountId( request, response ) );
         // ����ǿͻ���¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            laborContractTemplateVO.setCorpId( getCorpId( request, response ) );
         }
         // ����ɾ������
         if ( laborContractTemplateVO.getSubAction() != null && laborContractTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( laborContractTemplateVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder laborContractTemplateHolder = new PagedListHolder();
         // ���뵱ǰҳ
         laborContractTemplateHolder.setPage( page );
         // ���뵱ǰֵ����
         laborContractTemplateHolder.setObject( laborContractTemplateVO );
         // ����ҳ���¼����
         laborContractTemplateHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         laborContractTemplateService.getLaborContractTemplateVOsByCondition( laborContractTemplateHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( laborContractTemplateHolder, request );
         // Holder��д��Request����
         request.setAttribute( "laborContractTemplateHolder", laborContractTemplateHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
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
      // ��תJSPҳ��
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
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ��ʼ������Tab Number
      request.setAttribute( "entityCount", 0 );
      request.setAttribute( "businessTypeCount", 0 );
      request.setAttribute( "clientIdsCount", 0 );
      request.setAttribute( "clientIdsList", new ArrayList< ContractTemplateClientVo >() );

      // ����Sub Action
      ( ( LaborContractTemplateVO ) form ).setStatus( LaborContractTemplateVO.TRUE );
      ( ( LaborContractTemplateVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( LaborContractTemplateVO ) form ).setContractTypeId( "1" );
      // ��ת���½�����  
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            // ��õ�ǰFORM
            final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
            // ��ȡEntityId Array��Labor TypeId Array
            final String[] entityIdArray = laborContractTemplateVO.getEntityIdArray();
            final String[] businessTypeIdArray = laborContractTemplateVO.getBusinessTypeIdArray();
            final String[] clientIdsArray = laborContractTemplateVO.getClientIdsArray();

            // ���EntityId Array ��Ϊ����ת����Jason������laborContractTemplateVO��
            if ( entityIdArray != null && entityIdArray.length > 0 )
            {
               laborContractTemplateVO.setEntityIds( KANUtil.toJasonArray( entityIdArray ) );
            }
            // ���Labor TypeId Array ��Ϊ����ת����Jason������laborContractTemplateVO��
            if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
            {
               laborContractTemplateVO.setBusinessTypeIds( KANUtil.toJasonArray( businessTypeIdArray ) );
            }
            // ���Labor TypeId Array ��Ϊ����ת����Jason������laborContractTemplateVO��
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

            // ��ʼ������
            constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, laborContractTemplateVO, Operate.ADD, laborContractTemplateVO.getTemplateId(), null );
         }

         // ���Form
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         boolean flag = false;

         // ���LaborContractTemplateId
         final String laborContractTemplateId = request.getParameter( "laborContractTemplateId" );

         if ( laborContractTemplateId != null && !laborContractTemplateId.trim().isEmpty() )
         {
            // ��ʼ�� Service�ӿ�
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

            final String entityId = request.getParameter( "entityId" );
            final String businessTypeId = request.getParameter( "businessTypeId" );
            final String actionFlag = request.getParameter( "actionFlag" );

            // �����ɾ��
            if ( actionFlag != null && actionFlag.trim().equals( "delObject" ) )
            {
               // �޸�EntityIds
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

                     // ���ɾ�����EntityIds
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

               // �޸�BusinessTypeIds
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

                     // ���ɾ�����BusinessTypeIds
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

               // ����״̬��Ajax
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
               // �޸�EntityIds
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

                  // �����Ӻ��EntityIds
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

               // �޸�BusinessTypeIds
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
                  // �����Ӻ��BusinessTypeIds
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

               // ����״̬��Ajax
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
               // ����״̬��Ajax
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // ������ȡ�����
         String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( laborContractTemplateId ) == null )
         {
            laborContractTemplateId = ( ( LaborContractTemplateVO ) form ).getTemplateId();
         }
         // ���LaborContractTemplateVO����                                                                                          
         final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );
         // ����Add��Update
         laborContractTemplateVO.setSubAction( VIEW_OBJECT );
         laborContractTemplateVO.reset( null, request );

         // ���Entity Count����Tab Number��ʾ
         final int entityCount = laborContractTemplateVO.getEntityIdArray() == null ? 0 : laborContractTemplateVO.getEntityIdArray().length;

         // ���Entity Count����Tab Number��ʾ
         final int businessTypeCount = laborContractTemplateVO.getBusinessTypeIdArray() == null ? 0 : laborContractTemplateVO.getBusinessTypeIdArray().length;

         //���Tab��ʾ�ͻ�list
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         List< ContractTemplateClientVo > clientIdsList = clientService.clientIdsForTab( laborContractTemplateVO.getTemplateId(), laborContractTemplateVO.getClientIds() );

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "clientIdsCount", clientIdsList.size() );
         request.setAttribute( "clientIdsList", clientIdsList );
         request.setAttribute( "laborContractTemplateId", laborContractTemplateId );

         // ��LaborContractTemplateVO����request����
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
            // ������ȡ�����
            final String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
            // ��ȡLaborContractTemplateVO����
            final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

            // װ�ؽ��洫ֵ
            laborContractTemplateVO.update( ( LaborContractTemplateVO ) form );
            final String[] clientIdsArray = laborContractTemplateVO.getClientIdsArray();
            // ���Labor TypeId Array ��Ϊ����ת����Jason������laborContractTemplateVO��
            if ( clientIdsArray != null && clientIdsArray.length > 0 )
            {
               laborContractTemplateVO.setClientIds( KANUtil.toJasonArray( clientIdsArray ) );
            }
            // �����InHouse
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               laborContractTemplateVO.setContractTypeId( "2" );
            }
            // ��ȡ��¼�û�
            laborContractTemplateVO.setModifyBy( getAccountId( request, response ) );
            // �����޸ķ���
            laborContractTemplateService.updateLaborContractTemplate( laborContractTemplateVO );
            // ��ʼ������
            constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, laborContractTemplateVO, Operate.MODIFY, laborContractTemplateVO.getTemplateId(), null );
         }
         // ���Form
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
         // ��ʼ��Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // ���Action Form
         final LaborContractTemplateVO laborContractTemplateVO = ( LaborContractTemplateVO ) form;
         // ����ѡ�е�ID
         if ( laborContractTemplateVO.getSelectedIds() != null && !laborContractTemplateVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : laborContractTemplateVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               laborContractTemplateVO.setTemplateId( selectedId );
               laborContractTemplateVO.setModifyBy( getUserId( request, response ) );
               laborContractTemplateVO.setModifyDate( new Date() );
               laborContractTemplateService.deleteLaborContractTemplate( laborContractTemplateVO );
            }

            insertlog( request, laborContractTemplateVO, Operate.DELETE, null, laborContractTemplateVO.getSelectedIds() );
         }

         // ��ʼ������
         constantsInit( "initLaborContractTemplate", getAccountId( request, response ) );

         // ���Selected IDs����Action
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
         // ��ú�ͬ����ID
         final String contractTypeId = request.getParameter( "contractTypeId" );
         // ��ʼ��Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // ��ú�ͬ����ID��Ӧ�ĺ�ͬģ��Id
         final List< Object > laborContractTemplateVOs = laborContractTemplateService.getLaborContractTemplateVOsByContractTypeId( contractTypeId );
         // ��ʼ��
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
            // ��ʼ��PrintWrite����
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ������ѡ��
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // ��ȡtemplateId
         final String templateId = request.getParameter( "templateId" );

         // ��� entityId �� businessTypeId
         final String entityId = request.getParameter( "entityId" );
         final String businessTypeId = request.getParameter( "businessTypeId" );
         final String contractTypeId = request.getParameter( "contractTypeId" );
         final String clientIdPage = request.getParameter( "clientIdPage" );

         // �ӳ����л�ȡ�����е�LaborContractVOs
         final List< LaborContractTemplateVO > allLaborContractTemplateVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).LABOR_CONTRACT_TEMPLATE_VO;

         final String corpId = getCorpId( request, response );
         // ����������
         if ( allLaborContractTemplateVOs != null && allLaborContractTemplateVOs.size() > 0 )
         {
            for ( LaborContractTemplateVO businessContractTemplateVOTemp : allLaborContractTemplateVOs )
            {
               if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
               {
                  // InHouse ����CorpId
                  if ( !corpId.equals( businessContractTemplateVOTemp.getCorpId() ) )
                  {
                     continue;
                  }
               }
               else
               {
                  // HR SERVICE ȥ���ͻ���
                  if ( KANUtil.filterEmpty( businessContractTemplateVOTemp.getCorpId() ) != null )
                  {
                     continue;
                  }
               }
               // ���entityIds �� businessTypeIds
               final String entityIds = businessContractTemplateVOTemp.getEntityIds();
               final String businessTypeIds = businessContractTemplateVOTemp.getBusinessTypeIds();
               final String clientIds = businessContractTemplateVOTemp.getClientIds();

               final String[] entityIdArray = KANUtil.jasonArrayToStringArray( entityIds );
               final String[] businessTypeIdArray = KANUtil.jasonArrayToStringArray( businessTypeIds );
               final String[] clientIdArray = KANUtil.jasonArrayToStringArray( clientIds );

               String contractTypeIdMemory = businessContractTemplateVOTemp.getContractTypeId();
               // ���  entityId �� businessTypeId �ܹ�ƥ��
               //jzy add 2014/04/10 ��ͬģ��������ֵ�趨
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
                     //jzy add 2014/04/10 ��ͬģ�巨��ʵ�壬ҵ�����ͣ��ͻ�û���趨���к�ͬ����ѡ
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length == 0 && clientIdArray.length == 0 )
                     {

                        setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                     }

                     //jzy add 2014/04/10 ��ͬģ�巨��ʵ���趨����ҵ�����ͣ��ͻ�û���趨�����Ϸ���ʵ��ĺ�ͬ��ѡ
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length == 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ��ҵ�������趨������ʵ�壬�ͻ�û���趨������ҵ�����͵ĺ�ͬ��ѡ
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length > 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( businessTypeIdArray, businessTypeId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ��ͻ��趨������ʵ�壬ҵ������û���趨�����Ͽͻ��ĺ�ͬ��ѡ
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length == 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ�巨��ʵ�壬ҵ�������趨���ͻ�û���趨,���Ϸ���ʵ�壬ҵ�����ͺ�ͬ����ѡ
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length > 0 && clientIdArray.length == 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) && ArrayUtils.contains( businessTypeIdArray, businessTypeId ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ�巨��ʵ�壬�ͻ��趨��ҵ������û���趨,���Ϸ���ʵ�壬�ͻ���ͬ����ѡ
                     if ( entityIdArray.length > 0 && businessTypeIdArray.length == 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( entityIdArray, entityId ) && ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ��ҵ�����ͣ��ͻ��趨������ʵ��û���趨,����ҵ�����ͣ��ͻ���ͬ����ѡ
                     if ( entityIdArray.length == 0 && businessTypeIdArray.length > 0 && clientIdArray.length > 0 )
                     {

                        if ( ArrayUtils.contains( businessTypeIdArray, businessTypeId ) && ArrayUtils.contains( clientIdArray, clientIdPage ) )
                        {
                           setContractTemplateSelectOptions( request, businessContractTemplateVOTemp, mappingVOs );
                        }
                     }

                     //jzy add 2014/04/10 ��ͬģ�巨��ʵ�壬ҵ�����ͣ��ͻ����趨,���Ϸ���ʵ��,ҵ�����ͣ��ͻ���ͬ��ѡ
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
      // ��תJSPҳ��
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ��Account Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         //	��ʼ�� JSONArray
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
         // ��ʼ�� Service�ӿ�
         final LaborContractTemplateService laborContractTemplateService = ( LaborContractTemplateService ) getService( "laborContractTemplateService" );
         // ������ȡ�����
         String laborContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
         // ���LaborContractTemplateVO����                                                                                          
         final LaborContractTemplateVO laborContractTemplateVO = laborContractTemplateService.getLaborContractTemplateVOByLaborContractTemplateId( laborContractTemplateId );

         // ����Add��Update
         laborContractTemplateVO.setSubAction( VIEW_OBJECT );
         laborContractTemplateVO.reset( null, request );

         // ���Entity Count����Tab Number��ʾ
         final int entityCount = laborContractTemplateVO.getEntityIdArray() == null ? 0 : laborContractTemplateVO.getEntityIdArray().length;
         // ���Entity Count����Tab Number��ʾ
         final int businessTypeCount = laborContractTemplateVO.getBusinessTypeIdArray() == null ? 0 : laborContractTemplateVO.getBusinessTypeIdArray().length;

         //���Tab��ʾ�ͻ�list
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         List< ContractTemplateClientVo > clientIdsList = clientService.clientIdsForTab( laborContractTemplateVO.getTemplateId(), laborContractTemplateVO.getClientIds() );

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "clientIdsCount", clientIdsList.size() );
         request.setAttribute( "clientIdsList", clientIdsList );
         request.setAttribute( "laborContractTemplateId", laborContractTemplateId );

         // ��LaborContractTemplateVO����request����
         request.setAttribute( "laborContractTemplateForm", laborContractTemplateVO );
         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = laborContractTemplateVO.getNameZH() + fileName;
         }
         else
         {
            fileName = laborContractTemplateVO.getNameEN() + fileName;
         }

         // ���������ͬPDF�汾
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
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
            return mapping.findForward( "manageLaborContractTemplate" );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax����
      return mapping.findForward( "" );
   }

   private void setContractTemplateSelectOptions( HttpServletRequest request, LaborContractTemplateVO businessContractTemplateVOTemp, List< MappingVO > mappingVOs )
   {

      // ��������Ļ���
      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() + ( businessContractTemplateVOTemp.getStatus().equals( "2" ) ? "��ͣ�ã�" : "" ) );
         mappingVOs.add( mappingVO );
      }
      // ��������Ļ���
      else
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameEN() + ( businessContractTemplateVOTemp.getStatus().equals( "2" ) ? "��Stop��" : "" ) );
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
