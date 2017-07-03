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

         // �ӳ����л�ȡ�����е�BusinessContractVOs
         final List< BusinessContractTemplateVO > AllBusinessContractTemplateVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).BUSINESS_CONTRACT_TEMPLATE_VO;

         // ����������
         if ( AllBusinessContractTemplateVOs != null && AllBusinessContractTemplateVOs.size() > 0 )
         {
            // ������޸�ҳ��������ʾ
            if ( entityId != null && businessTypeId != null )
            {
               for ( BusinessContractTemplateVO businessContractTemplateVOTemp : AllBusinessContractTemplateVOs )
               {

                  // ���entityIds �� businessTypeIds
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
                  // ��������Ļ���
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
                     mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() );
                     mappingVOs.add( mappingVO );
                  }
                  // ����Ƿ����Ļ���
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
         // ˢ�±���
         constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // ���Action Form
         final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         businessContractTemplateVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( businessContractTemplateVO.getSubAction() != null && businessContractTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( businessContractTemplateVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder businessContractTemplateHolder = new PagedListHolder();
         // ���뵱ǰҳ
         businessContractTemplateHolder.setPage( page );
         // ���뵱ǰֵ����
         businessContractTemplateHolder.setObject( businessContractTemplateVO );
         // ����ҳ���¼����
         businessContractTemplateHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         businessContractTemplateService.getBusinessContractTemplateVOsByCondition( businessContractTemplateHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( businessContractTemplateHolder, request );
         // Holder��д��Request����
         request.setAttribute( "businessContractTemplateHolder", businessContractTemplateHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listBusinessContractTemplateTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ��ʼ������Tab Number
      request.setAttribute( "entityCount", 0 );
      request.setAttribute( "businessTypeCount", 0 );

      // ��ʼ������
      ( ( BusinessContractTemplateVO ) form ).setContentType( "2" );
      ( ( BusinessContractTemplateVO ) form ).setStatus( BusinessContractTemplateVO.TRUE );
      // ����Sub Action
      ( ( BusinessContractTemplateVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // ��õ�ǰFORM
            final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;
            // ��ȡEntityId Array��Business TypeId Array
            final String[] entityIdArray = businessContractTemplateVO.getEntityIdArray();
            final String[] businessTypeIdArray = businessContractTemplateVO.getBusinessTypeIdArray();

            // ���EntityId Array ��Ϊ����ת����Jason������businessContractTemplateVO��
            if ( entityIdArray != null && entityIdArray.length > 0 )
            {
               businessContractTemplateVO.setEntityIds( KANUtil.toJasonArray( entityIdArray ) );
            }
            // ���Business TypeId Array ��Ϊ����ת����Jason������businessContractTemplateVO��
            if ( businessTypeIdArray != null && businessTypeIdArray.length > 0 )
            {
               businessContractTemplateVO.setBusinessTypeIds( KANUtil.toJasonArray( businessTypeIdArray ) );
            }

            businessContractTemplateVO.setCreateBy( getUserId( request, response ) );
            businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
            businessContractTemplateVO.setAccountId( getAccountId( request, response ) );
            businessContractTemplateService.insertBusinessContractTemplate( businessContractTemplateVO );

            constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ�� Service�ӿ�
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // ������ȡ�����
         String businessContractTemplateId = KANUtil.decodeString( request.getParameter( "templateId" ) );

         if ( businessContractTemplateId == null || businessContractTemplateId.trim().isEmpty() )
         {
            businessContractTemplateId = ( ( BusinessContractTemplateVO ) form ).getTemplateId();
         }

         // ���BusinessContractTemplateVO����                                                                                          
         final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

         // ����Add��Update
         businessContractTemplateVO.setSubAction( VIEW_OBJECT );
         businessContractTemplateVO.reset( null, request );

         // ���Entity Count����Tab Number��ʾ
         final int entityCount = businessContractTemplateVO.getEntityIdArray().length;

         // ���Entity Count����Tab Number��ʾ
         final int businessTypeCount = businessContractTemplateVO.getBusinessTypeIdArray().length;

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "businessContractTemplateId", businessContractTemplateId );

         // ��BusinessContractTemplateVO����request����
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // ������ȡ�����
            final String businessContractTemplateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );
            // ��ȡBusinessContractTemplateVO����
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

            // װ�ؽ��洫ֵ
            businessContractTemplateVO.update( ( BusinessContractTemplateVO ) form );
            // ��ȡ��¼�û�
            businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            businessContractTemplateService.updateBusinessContractTemplate( businessContractTemplateVO );

            // ��ʼ������
            constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form
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
    *	Ajax�޸ģ�Tab����
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

         // ���BusinessContractTemplateId
         final String businessContractTemplateId = request.getParameter( "businessContractTemplateId" );

         if ( businessContractTemplateId != null && !businessContractTemplateId.trim().isEmpty() )
         {
            // ��ʼ�� Service�ӿ�
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

            final String entityId = request.getParameter( "entityId" );
            final String businessTypeId = request.getParameter( "businessTypeId" );
            final String actionFlag = request.getParameter( "actionFlag" );

            // �����ɾ��
            if ( actionFlag != null && actionFlag.trim().equals( "delObject" ) )
            {
               // �޸�EntityIds
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

                     // ���ɾ�����EntityIds
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

               // �޸�BusinessTypeIds
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

                     // ���ɾ�����BusinessTypeIds
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
            // ��������
            else if ( actionFlag != null && actionFlag.trim().equals( "addObject" ) )
            {
               // �޸�EntityIds
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

                  // �����Ӻ��EntityIds
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

               // �޸�BusinessTypeIds
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
                  // �����Ӻ��BusinessTypeIds
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
         // ��ʼ��Service�ӿ�
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // ���Action Form
         final BusinessContractTemplateVO businessContractTemplateVO = ( BusinessContractTemplateVO ) form;

         // ����ѡ�е�ID
         if ( businessContractTemplateVO.getSelectedIds() != null && !businessContractTemplateVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : businessContractTemplateVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               businessContractTemplateVO.setTemplateId( selectedId );
               businessContractTemplateVO.setModifyBy( getUserId( request, response ) );
               businessContractTemplateVO.setModifyDate( new Date() );
               businessContractTemplateService.deleteBusinessContractTemplate( businessContractTemplateVO );
            }

         }

         // ��ʼ������
         constantsInit( "initBusinessContractTemplate", getAccountId( request, response ) );

         // ���Selected IDs����Action
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
         // ��ʼ�� Service�ӿ�
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         // ��ȡ�����ͬ����
         String businessContractTemplateId = KANUtil.decodeString( request.getParameter( "id" ) );
         
         if ( businessContractTemplateId == null || businessContractTemplateId.trim().isEmpty() )
         {
            businessContractTemplateId = ( ( BusinessContractTemplateVO ) form ).getTemplateId();
         }

         // ���BusinessContractTemplateVO����                                                                                          
         final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( businessContractTemplateId );

         // ����Add��Update
         businessContractTemplateVO.setSubAction( VIEW_OBJECT );
         businessContractTemplateVO.reset( null, request );

         // ���Entity Count����Tab Number��ʾ
         final int entityCount = businessContractTemplateVO.getEntityIdArray().length;

         // ���Entity Count����Tab Number��ʾ
         final int businessTypeCount = businessContractTemplateVO.getBusinessTypeIdArray().length;

         request.setAttribute( "entityCount", entityCount );
         request.setAttribute( "businessTypeCount", businessTypeCount );
         request.setAttribute( "businessContractTemplateId", businessContractTemplateId );

         // ��BusinessContractTemplateVO����request����
         request.setAttribute( "businessContractTemplateForm", businessContractTemplateVO );
         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = businessContractTemplateVO.getNameZH() + fileName;
         }
         else
         {
            fileName = businessContractTemplateVO.getNameEN() + fileName;
         }

         // ���������ͬPDF�汾
         String content = businessContractTemplateVO.getContent();
         String logo = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE;
         ByteArrayOutputStream bos = HTMLParseUtil.htmlParsePDF( this.generateContent( content ) , businessContractTemplateVO.getTemplateId(), logo );
         new DownloadFileAction().download( response,bos , fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
            return mapping.findForward( "manageBusinessContractTemplate" );
         }
         else
         {
            throw new KANException( e );
         }
      }
      // Ajax����
      return mapping.findForward( "" );
   }

   private void setContractTemplateSelectOptions( HttpServletRequest request, BusinessContractTemplateVO businessContractTemplateVOTemp, List< MappingVO > mappingVOs )
   {

      // ��������Ļ���
      if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
      {
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( businessContractTemplateVOTemp.getTemplateId() );
         mappingVO.setMappingValue( businessContractTemplateVOTemp.getNameZH() );
         mappingVOs.add( mappingVO );
      }
      // ����Ƿ����Ļ���
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
