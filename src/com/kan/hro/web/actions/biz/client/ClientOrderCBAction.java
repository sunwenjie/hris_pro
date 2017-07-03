package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderCBAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderCBAction extends BaseAction
{

   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_CB";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_CB_IN_HOUSE";

   /**
    * List client Order Header
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         // ���Action Form
         final ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;
         clientOrderCBVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderCBVO.getSubAction() != null && clientOrderCBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderCBVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderCBVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderCBVO.getSubAction() != null )
         {
            subAction = clientOrderCBVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderCBService.getClientOrderCBVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pageListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pageListHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // ��ʼ��PrintWrite����
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_SB" ) );
               out.flush();
               out.close();
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listClientOrderCB" );
   }

   /**
    * To object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ���OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      ( ( ClientOrderCBVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderCBVO ) form ).setFreeShortOfMonth( "2" );
      ( ( ClientOrderCBVO ) form ).setChargeFullMonth( "2" );
      ( ( ClientOrderCBVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderCBVO ) form ).setStatus( "1" );

      // ���ʻ�
      ( ( ClientOrderCBVO ) form ).reset( null, request );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderCB" );
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
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // ��õ�ǰ����
         String orderCBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderCBId == null || orderCBId.trim().isEmpty() )
         {
            orderCBId = ( ( ClientOrderCBVO ) form ).getOrderCbId();
         }

         //  ���ClientOrderCBVO
         final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( orderCBId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderCBVO.reset( null, request );
         // ����Sub Action
         clientOrderCBVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderCBForm", clientOrderCBVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderCB" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

            // ���ActionForm
            final ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;
            // ��ȡ��¼�û�
            clientOrderCBVO.setCreateBy( getUserId( request, response ) );
            clientOrderCBVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderCBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderCBService.insertClientOrderCB( clientOrderCBVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderCBVO, Operate.ADD, clientOrderCBVO.getOrderCbId(), null );
         }

         // ���Form����
         ( ( ClientOrderCBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
      return to_objectModify( mapping, form, request, response );
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
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
            // ��õ�ǰ����
            final String clientOrderCBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
            // ��ȡ��¼�û�
            clientOrderCBVO.update( ( ClientOrderCBVO ) form );
            // �����Զ���Column
            clientOrderCBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderCBVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            clientOrderCBService.updateClientOrderCB( clientOrderCBVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderCBVO, Operate.MODIFY, clientOrderCBVO.getOrderCbId(), null );
         }

         // ���Form����
         ( ( ClientOrderCBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
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
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * Delete Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // ��ȡ����
         final String clientOrderCBId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderCBId" ) );

         // ����������ö�ӦVO
         final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
         clientOrderCBVO.setModifyBy( getUserId( request, response ) );
         clientOrderCBVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderCBService.deleteClientOrderCB( clientOrderCBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderCBVO, Operate.DELETE, clientOrderCBVO.getOrderCbId(), "ajax delete" );
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
    * Delete Object List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         // ���Action Form
         ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderCBVO.getSelectedIds() != null && !clientOrderCBVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderCBVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderCBId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderCBVO clientOrderCBVOForDel = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
                  clientOrderCBVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderCBVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderCBService.deleteClientOrderCB( clientOrderCBVOForDel );
               }
            }

            insertlog( request, clientOrderCBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderCBVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         clientOrderCBVO.setSelectedIds( "" );
         clientOrderCBVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 
    * 	List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String cbSolutionId = request.getParameter( "solutionId" );

         // ��ʼ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // �����In House��¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // �����Hr Service��¼
         else
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // ���super 
         mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );

         mappingVOs.add( 0, ( ( ClientOrderCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( mappingVOs, "cbSolutionId", cbSolutionId ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

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

         // ��ȡ�̱�����
         final String solutionId = request.getParameter( "solutionId" );
         // ��ȡ����ID
         final String orderId = request.getParameter( "orderId" );

         // ��ʼ��Service
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderId );
         ClientOrderCBVO clientOrderCBVO = null;
         for ( Object cbvoObject : clientOrderCBVOs )
         {
            ClientOrderCBVO cbvo = ( ClientOrderCBVO ) cbvoObject;
            if ( KANUtil.filterEmpty( solutionId ) != null )
            {
               if ( solutionId.equals( cbvo.getCbSolutionId() ) )
               {
                  clientOrderCBVO = cbvo;
                  break;
               }
            }
         }

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();
         if ( clientOrderCBVO == null )
         {
            jsonObject.put( "success", "false" );
         }
         else
         {
            jsonObject.put( "freeShortOfMonth", KANUtil.filterEmpty( clientOrderCBVO.getFreeShortOfMonth() ) == null ? "0" : clientOrderCBVO.getFreeShortOfMonth() );
            jsonObject.put( "chargeFullMonth", KANUtil.filterEmpty( clientOrderCBVO.getChargeFullMonth() ) == null ? "0" : clientOrderCBVO.getChargeFullMonth() );
            jsonObject.put( "success", "true" );
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

      // ��ת���б����
      return mapping.findForward( "" );
   }

   // ���ٴ���Ա�������ݽ�������ȡ�̱�.super���̱���û���籣
   public ActionForward list_object_options_byOrderHeaderId_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // ��ʼ��
         final List< MappingVO > targetMappingVOs = new ArrayList< MappingVO >();
         final List< MappingVO > allMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) );

         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderHeaderId );

         if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
         {
            for ( Object clientOrderCBObject : clientOrderCBVOs )
            {
               // ��ȡ��������̱�
               for ( MappingVO mappingVO : allMappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( ( ( ClientOrderCBVO ) clientOrderCBObject ).getCbSolutionId() ) )
                  {
                     targetMappingVOs.add( mappingVO );
                  }
               }
            }
         }
         else
         {
            // ��������������û���̱���������˺ŵ������̱�
            targetMappingVOs.addAll( allMappingVOs );
         }
         // ���super 
         targetMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         targetMappingVOs.add( 0, ( ( ClientOrderCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( targetMappingVOs, "cbSolutionId", null ) );

         // Send to client
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
