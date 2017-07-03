package com.kan.hro.web.actions.biz.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BusinessContractTemplateService;
import com.kan.base.util.HTMLParseUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContractPropertyService;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientContractAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-8  
 */
public class ClientContractAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_CONTRACT";

   /**  
    * Get Object Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward get_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         // ��ȡClientContractVO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         if ( clientContractVO != null && clientContractVO.getAccountId() != null && clientContractVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientContractVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientContractVO );
            String finalEndDate = getFinalEndDate( clientContractVO );
            jsonObject.put( "finalEndDate", finalEndDate );
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

      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ������������ڣ�Order/Contract��
         final String flag = request.getParameter( "flag" );

         // ��ȡClientId
         final String clientId = request.getParameter( "clientId" );
         // ��ȡClientContractVO�б�
         final List< Object > clientContractVOs = clientContractService.getClientContractVOsByClientId( clientId );

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );

         if ( flag != null && !flag.trim().isEmpty() && clientContractVOs != null && clientContractVOs.size() > 0 )
         {
            // ����
            for ( Object object : clientContractVOs )
            {
               final ClientContractVO clientContractVO = ( ClientContractVO ) object;
               boolean target = false;

               if ( flag.trim().equalsIgnoreCase( "order" ) )
               {
                  if ( clientContractVO.getStatus() != null
                        && ( clientContractVO.getStatus().trim().equals( "3" ) || clientContractVO.getStatus().trim().equals( "5" ) || clientContractVO.getStatus().trim().equals( "6" ) ) )
                  {
                     target = true;
                  }
               }
               else if ( flag.trim().equalsIgnoreCase( "contract" ) )
               {
                  if ( clientContractVO.getStatus() != null && clientContractVO.getStatus().trim().equals( "6" ) )
                  {
                     target = true;
                  }
               }

               if ( target )
               {
                  // ��ʼ��MappingVO
                  final MappingVO mappingVO = new MappingVO();
                  // ��ʼ��MappingValue
                  String mappingValue = clientContractVO.getContractId();
                  if ( KANUtil.filterEmpty( clientContractVO.getContractNo() ) != null )
                  {
                     mappingValue = mappingValue + "/" + clientContractVO.getContractNo();
                  }

                  mappingVO.setMappingId( clientContractVO.getContractId() );
                  // ����
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingValue = mappingValue + " - " + clientContractVO.getNameZH();
                  }
                  // ������
                  else
                  {
                     if ( KANUtil.filterEmpty( clientContractVO.getNameEN() ) != null )
                     {
                        mappingValue = mappingValue + " - " + clientContractVO.getNameEN();
                     }
                  }
                  mappingVO.setMappingValue( mappingValue );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", contractId ) );
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
    * List Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-06
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

         // ��ʼ�� Service
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ��ȡ��ǰ��¼�û���accountId
         final String accountId = getAccountId( request, response );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientContractService.getClientContractBaseViews( accountId ) );

         // Send to clientContractGroup
         out.println( array.toString() );
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
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // ���Action Form
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;

         //��������Ȩ��
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientContractVO );

         // ���SubAction
         final String subAction = getSubAction( form );

         // ����Զ�����������
         clientContractVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // ���û��ָ��������Ĭ�ϰ� ContractId����
         if ( clientContractVO.getSortColumn() == null || clientContractVO.getSortColumn().isEmpty() )
         {
            clientContractVO.setSortColumn( "contractId" );
            clientContractVO.setSortOrder( "desc" );
         }

         // ����SubAction
         dealSubAction( clientContractVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder clientContractHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            clientContractHolder.setPage( page );
            // ���뵱ǰֵ����
            clientContractHolder.setObject( clientContractVO );
            // ����ҳ���¼����
            clientContractHolder.setPageSize( getPageSize( request, accessAction ) );

            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            clientContractService.getClientContractVOsByCondition( clientContractHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( clientContractHolder, request );
         }

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", clientContractHolder );

         // ����Return
         return dealReturn( accessAction, "listClientContract", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ��Entity Mapping�б�
         final List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntities( request.getLocale().getLanguage() );

         // ��ʼ��Business Type Mapping�б�
         final List< MappingVO > businessTypes = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBusinessTypes( request.getLocale().getLanguage() );

         // ��ʼ��PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         // ���ActionForm
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;
         // Ĭ������
         clientContractVO.setSubAction( CREATE_OBJECT );

         if ( entities != null && entities.size() == 1 )
         {
            clientContractVO.setEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );

            // ��ʼ��
            final EntityVO entityVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );
            clientContractVO.setBusinessTypeId( entityVO.getBizType() );
         }

         if ( businessTypes != null && businessTypes.size() == 1 )
         {
            clientContractVO.setBusinessTypeId( ( ( MappingVO ) businessTypes.get( 0 ) ).getMappingId() );
         }

         // �����ͬ��ʼ����Ĭ�ϵ���
         clientContractVO.setStartDate( KANUtil.formatDate( KANUtil.createDate( null ), "yyyy-MM-dd" ) );
         // �����ͬ��������Ĭ������
         clientContractVO.setEndDate( KANUtil.formatDate( KANUtil.getDate( KANUtil.createDate( null ), 3, 0, -1 ), "yyyy-MM-dd" ) );
         clientContractVO.setStatus( ClientContractVO.TRUE );

         if ( positionVO != null )
         {
            clientContractVO.setBranch( positionVO.getBranchId() );
            clientContractVO.setOwner( positionVO.getPositionId() );
         }

         // ���Client Id��Ϊ��
         if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
         {
            // ��ȡClient Id
            final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
            // ����Client Id
            ( ( ClientContractVO ) form ).setClientId( clientId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���½�����   
      return mapping.findForward( "manageClientContract" );
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
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ�� Service�ӿ�
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

            // ��õ�ǰFORM
            final ClientContractVO clientContractVO = ( ClientContractVO ) form;
            clientContractVO.setCreateBy( getUserId( request, response ) );
            clientContractVO.setModifyBy( getUserId( request, response ) );

            if ( clientContractVO.getTemplateId() != null && !"0".equals( clientContractVO.getTemplateId() ) )
            {
               BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
               clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            }

            // ���ò��뷽��
            clientContractService.insertClientContract( clientContractVO );

            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( ClientContractVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // ������ȡ�����
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( KANUtil.filterEmpty( contractId ) == null )
         {
            contractId = ( ( ClientContractVO ) form ).getContractId();
         }

         // ���ClientContractVO����                                                                                          
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         clientContractVO.reset( null, request );

         // ����Add��Update
         clientContractVO.setSubAction( VIEW_OBJECT );

         // ��ClientContractVO����request����
         request.setAttribute( "clientContractForm", clientContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageClientContract" );
   }

   /**
    * Modify Object
    * ��һ�����桰���桱��ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {

            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );
            // ���ҳ������ֵ
            checkClientId( mapping, form, request, response );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ�� Service�ӿ�
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡClientContractVO����
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            // װ�ؽ��洫ֵ
            clientContractVO.update( ( ClientContractVO ) form );
            // ��ȡ��¼�û�
            clientContractVO.setModifyBy( getUserId( request, response ) );

            if ( clientContractVO.getTemplateId() != null && !"0".equals( clientContractVO.getTemplateId() ) )
            {
               BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
               clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            }

            // �����޸ķ���
            clientContractService.updateClientContract( clientContractVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Object Step 2
    * �ڶ������桰���桱�����ύ����ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_step2( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ������ֵ������
            request.setAttribute( "errorCount", 0 );

            // ҳ����ת����
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // ��ʼ�� Service�ӿ�
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ��ȡClientContractVO����
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            // ��ȡ��ͬģ����Ϣ
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
            final String content = businessContractTemplateVO.getContent();

            // װ�ؽ��洫ֵ
            clientContractVO.setContent( content );
            // ���µ�¼�û����޸�ʱ��
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( content, KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), request, null, MatchUtil.FLAG_GET_PROPERTIES );

            // ��ʼ��Rows
            int rows = 0;

            // �����޸ķ���
            rows = clientContractService.updateClientContract( clientContractVO, constantVOs );

            // ����Ǻ�ͬ�ύ - Ĭ��״̬Ϊ��������
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               clientContractVO.reset( null, request );
               rows = clientContractService.submitClientContract( clientContractVO );
            }

            // ������ʾ��Ϣ
            if ( rows == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         // ���Form
         ( ( ClientContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Submit Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by siuvan.xia 2014-06-23
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ��õ�ǰ����
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ���������Ӧ����
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

         List< Object > objects = getClientContractPDFVos( clientContractVO, request );
         final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( clientContractVO.getContent(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );

         // ��������
         clientContractVO.setModifyBy( getUserId( request, response ) );
         clientContractVO.setModifyDate( new Date() );
         clientContractVO.reset( null, request );
         clientContractVO.setConstantVOs( constantVOs );
         clientContractService.submitClientContract( clientContractVO );
         success( request, MESSAGE_TYPE_SUBMIT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Chop Object
    * �ڶ������桰���¡���ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-11
   public ActionForward chop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡClientContractVO����
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            clientContractVO.setStatus( "5" );
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            // �����޸ķ���
            clientContractService.chopClientContract( clientContractVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Archive Object
    * �ڶ������桰�鵵����ť����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-11
   public ActionForward archive_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            // ������ȡ�����
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡClientContractVO����
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            clientContractVO.setStatus( "6" );
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            // �����޸ķ���
            clientContractService.updateClientContract( clientContractVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
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
         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // ���Action Form
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;

         // ����ѡ�е�ID
         if ( clientContractVO.getSelectedIds() != null && !clientContractVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientContractVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String contractId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientContractVO clientContractVOForDel = clientContractService.getClientContractVOByContractId( contractId );
                  clientContractVOForDel.setModifyBy( getUserId( request, response ) );
                  clientContractVOForDel.setModifyDate( new Date() );
                  clientContractService.deleteClientContract( clientContractVOForDel );
               }
            }
         }

         // ���Selected IDs����Action
         clientContractVO.setSelectedIds( "" );
         clientContractVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tabɾ�������ͬ
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Row
         long rows = 0;

         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ��ȡ����
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // ����������ö�ӦVO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         clientContractVO.setModifyBy( getUserId( request, response ) );
         clientContractVO.setModifyDate( new Date() );

         // ����״̬����ɾ��
         if ( clientContractVO.getStatus() != null
               && ( clientContractVO.getStatus().trim().equals( "3" ) || clientContractVO.getStatus().trim().equals( "5" ) || clientContractVO.getStatus().trim().equals( "6" ) ) )
         {
            rows = 0;
         }
         else
         {
            // ����ɾ���ӿ�
            rows = clientContractService.deleteClientContract( clientContractVO );
         }

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
         }
         else
         {
            deleteFailedAjax( out, null );
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
   * List Special Info HTML
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ÿͻ���ͬ���
         final String contractId = KANUtil.decodeString( request.getParameter( "clientContractId" ) );

         // ��ʼ��Tab Number
         int attachmentCount = 0;
         int clientOrderHeaderCount = 0;

         if ( contractId != null && !contractId.trim().isEmpty() )
         {
            // ���ClientContractVO
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            clientContractVO.reset( null, request );
            clientContractVO.setSubAction( VIEW_OBJECT );

            request.setAttribute( "clientContractForm", clientContractVO );

            // ��ȡTab��ǩNumber
            if ( clientContractVO.getAttachmentArray() != null )
            {
               attachmentCount = clientContractVO.getAttachmentArray().length;
            }

            // ��ʼ��ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setContractId( contractId );
            clientOrderHeaderVO.setClientId( clientContractVO.getClientId() );
            clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
            // ���ClientOrderHeaderVO�б�
            final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

            // ��ȡTab��ǩNumber
            if ( clientOrderHeaderVOs != null )
            {
               clientOrderHeaderCount = clientOrderHeaderVOs.size();
            }

            request.setAttribute( "clientOrderHeaderVOs", clientOrderHeaderVOs );
         }

         request.setAttribute( "clientOrderHeaderCount", clientOrderHeaderCount );
         request.setAttribute( "attachmentCount", attachmentCount );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "manageClientContractSpecialInfo" );
   }

   /**  
    * Generate Contract
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward generate_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );

         // ��ʼ�� Service�ӿ�
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // ������޸�,��ȡ�����ͬ����Id
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ��ý��洫ֵ
         ClientContractVO clientContractVO = ( ClientContractVO ) form;
         BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );

         if ( contractId == null || contractId.trim().isEmpty() )
         {
            // ���������
            clientContractVO.setStatus( "1" );
            clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            clientContractService.insertClientContract( clientContractVO );
         }
         else
         {
            // ������޸ģ����������Ӧ����
            final ClientContractVO clientContractVOFromDB = clientContractService.getClientContractVOByContractId( contractId );

            // װ�ؽ��洫ֵ
            clientContractVOFromDB.update( clientContractVO );
            // ��ȡ��¼�û�
            clientContractVOFromDB.setModifyBy( getUserId( request, response ) );
            clientContractVOFromDB.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            // �����޸ķ���
            clientContractService.updateClientContract( clientContractVOFromDB );
         }

         if ( clientContractVO.getTemplateId() != null && !clientContractVO.getTemplateId().trim().equals( "0" ) )
         {
            // ��ȡ��ͬģ����Ϣ
            final String content = businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent();

            // ��ʼ��Object List
            //            final List< Object > objects = new ArrayList< Object >();
            //            ClientVO clientVo = clientService.getClientVOByClientIdForPdf( clientContractVO.getClientId() );
            //            clientVo.setContactAddress( clientVo.getAddress() );
            //            if ( clientVo.getRemark1() != null && clientVo.getRemark1().contains( "registeredAddress" ) )
            //            {
            //               String[] remarks = clientVo.getRemark1().replaceAll( "\"", "" ).replace( "{", "" ).replace( "}", "" ).split( "," );
            //               for ( String remark : remarks )
            //               {
            //                  if ( remark.contains( "registeredAddress" ) )
            //                  {
            //                     clientVo.setRegisteredAddress( remark.replace( "registeredAddress:", "" ) );
            //                     break;
            //                  }
            //               }
            //            }
            //
            //            if ( StringUtils.isEmpty( clientVo.getContactWay() ) )
            //            {
            //               clientVo.setContactWay( clientVo.getPhone() );
            //            }
            //            objects.add( clientVo );
            //            objects.add( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( clientContractVO.getEntityId() ) );
            //            objects.add( clientContractVO );

            List< Object > objects = getClientContractPDFVos( clientContractVO, request );

            // ����ClientContractVO��Content
            clientContractVO.setContent( MatchUtil.generateContent( content, KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), objects, request ) );
         }

         request.setAttribute( "clientContractForm", clientContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax����
      return mapping.findForward( "generateContract" );
   }

   /**  
    * Export Contract PDF
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-09
   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ�� Service�ӿ�
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // ��ȡ�����ͬ����
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ��ȡ�����ͬ����
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

         // ��ʼ���ļ���
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = clientContractVO.getNameZH() + fileName;
         }
         else
         {
            fileName = clientContractVO.getNameEN() + fileName;
         }

         // ���������ͬPDF�汾
         String htmlContent = MatchUtil.generateContent( clientContractVO.getContent(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), clientContractPropertyService.getClientContractPropertyVOsByContractId( clientContractVO.getContractId() ), request, MatchUtil.FLAG_GET_CONTENT,null );
         //new DownloadFileAction().download( response, HTMLParseUtil.htmlToPDF( htmlContent, clientContractVO.getContractId(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE ), fileName );
         new DownloadFileAction().download( response, HTMLParseUtil.htmlParsePDF( htmlContent, clientContractVO.getContractId(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE ), fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "�к�" ) )
         {
            error( request, null, e.getMessage() );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax����
      return mapping.findForward( "" );
   }

   /**  
    * To PrePage
    *	ҳ��������󷵻��ύҳ��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_prePage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת��Manage����   
      return mapping.findForward( "manageClientContract" );
   }

   /**  
    * �������ClientId�Ƿ���Ч
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // ��ȡForm
      final ClientContractVO clientContractVO = ( ClientContractVO ) form;
      // ���ClientId
      final String clientId = KANUtil.filterEmpty( clientContractVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "�ͻ�ID������Ч��" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   private String getFinalEndDate( final ClientContractVO clientContractVO ) throws Exception
   {
      String finalEndDate = clientContractVO.getEndDate();
      final String startDate = clientContractVO.getStartDate() + " 00:00:00";
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
      final SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
      final Date sDate = sdf.parse( startDate );
      Calendar calendar = Calendar.getInstance();
      if ( KANUtil.filterEmpty( finalEndDate ) != null )
      {
         final Date eDate = sdf.parse( clientContractVO.getEndDate() + " 23:59:59" );
         calendar.setTime( sDate );
         calendar.add( Calendar.YEAR, 3 );
         //�������ʱ�����3���ڣ����ն�����
         if ( calendar.getTime().getTime() > eDate.getTime() )
         {
            finalEndDate = clientContractVO.getEndDate();
         }
         else
         {
            // ������3��
            calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
            calendar.add( Calendar.DAY_OF_MONTH, -1 );
            finalEndDate = sdf2.format( calendar.getTime() );
         }
      }
      else
      {
         // ��ʱ�������
         calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
         calendar.add( Calendar.DAY_OF_MONTH, -1 );
         finalEndDate = sdf2.format( calendar.getTime() );
      }
      return finalEndDate;
   }

   public void setInputValueForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String clientContractId = request.getParameter( "contractId" );
      final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
      List< Object > list = clientContractPropertyService.getClientContractPropertyVOsByContractId( KANUtil.decodeString( clientContractId ) );
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( Object object : list )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "id", ( ( ClientContractPropertyVO ) object ).getPropertyName() );
         map.put( "value", ( ( ClientContractPropertyVO ) object ).getPropertyValue() );
         listReturn.add( map );
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

   public void getArchiveClientContractCount( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ�� Service
      final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
      int count = clientContractService.getArchiveClientContractCount( request.getParameter( "clientId" ) );
      try
      {
         final PrintWriter out = response.getWriter();
         out.print( count + "" );
         out.flush();
         out.close();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }

   public List< Object > getClientContractPDFVos( final ClientContractVO clientContractVO, final HttpServletRequest request ) throws KANException
   {
      // ��ʼ��Object List
      final List< Object > objects = new ArrayList< Object >();
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      ClientVO clientVo = clientService.getClientVOByClientIdForPdf( clientContractVO.getClientId() );
      clientVo.setContactAddress( clientVo.getAddress() );

      if ( clientVo.getRemark1() != null && clientVo.getRemark1().contains( "registeredAddress" ) )
      {
         String[] remarks = clientVo.getRemark1().replaceAll( "\"", "" ).replace( "{", "" ).replace( "}", "" ).split( "," );
         for ( String remark : remarks )
         {
            if ( remark.contains( "registeredAddress" ) )
            {
               clientVo.setRegisteredAddress( remark.replace( "registeredAddress:", "" ) );
               break;
            }
         }
      }

      if ( StringUtils.isEmpty( clientVo.getContactWay() ) )
      {
         clientVo.setContactWay( clientVo.getPhone() );
      }

      objects.add( clientVo );
      objects.add( KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getEntityVOByEntityId( clientContractVO.getEntityId() ) );
      objects.add( clientContractVO );

      return objects;
   }

}
