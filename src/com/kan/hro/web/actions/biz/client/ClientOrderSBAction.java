package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientOrderSBAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientOrderSBAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_SB";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_SB_IN_HOUSE";

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
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         // ���Action Form
         final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;
         clientOrderSBVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientOrderSBVO.getSubAction() != null && clientOrderSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientOrderSBVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientOrderSBVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientOrderSBVO.getSubAction() != null )
         {
            subAction = clientOrderSBVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientOrderSBService.getClientOrderSBVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
      return mapping.findForward( "listClientOrderSB" );
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

      // TODO�ȴ��籣����ȡ

      // ��ʼ��Service
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // ��ȡ�Ƿ�˾�е��籣
      if ( KANUtil.filterEmpty( orderHeaderId ) != null )
      {
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
         if ( clientOrderHeaderVO != null )
         {
            ( ( ClientOrderSBVO ) form ).setPersonalSBBurden( clientOrderHeaderVO.getPersonalSBBurden() );
         }
      }

      ( ( ClientOrderSBVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderSBVO ) form ).setStatus( ClientOrderSBVO.TRUE );
      ( ( ClientOrderSBVO ) form ).setSubAction( CREATE_OBJECT );

      // ���ʻ�
      ( ( ClientOrderSBVO ) form ).reset( null, request );

      // ��ת���½�����
      return mapping.findForward( "manageClientOrderSB" );
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
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // ��õ�ǰ����
         String orderSBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderSBId == null || orderSBId.trim().isEmpty() )
         {
            orderSBId = ( ( ClientOrderSBVO ) form ).getOrderSbId();
         }

         // ���ClientOrderSBVO
         final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( orderSBId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientOrderSBVO.reset( null, request );
         // ����Sub Action
         clientOrderSBVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderSBForm", clientOrderSBVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientOrderSB" );
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
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            // ���ActionForm
            final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;
            // ��ȡ��¼�û�
            clientOrderSBVO.setCreateBy( getUserId( request, response ) );
            clientOrderSBVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientOrderSBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientOrderSBService.insertClientOrderSB( clientOrderSBVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderSBVO, Operate.ADD, clientOrderSBVO.getOrderSbId(), null );
         }

         // ���Form����
         ( ( ClientOrderSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
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
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
            // ��õ�ǰ����
            final String orderSBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( orderSBId );
            // ��ȡ��¼�û�
            clientOrderSBVO.update( ( ClientOrderSBVO ) form );
            // �����Զ���Column
            clientOrderSBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderSBVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            clientOrderSBService.updateClientOrderSB( clientOrderSBVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderSBVO, Operate.MODIFY, clientOrderSBVO.getOrderSbId(), null );
         }

         // ���Form����
         ( ( ClientOrderSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
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
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // ��ȡ����
         final String clientOrderSBId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderSBId" ) );

         // ����������ö�ӦVO
         final ClientOrderSBVO clientOrderSBVO = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( clientOrderSBId );
         clientOrderSBVO.setModifyBy( getUserId( request, response ) );
         clientOrderSBVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = clientOrderSBService.deleteClientOrderSB( clientOrderSBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderSBVO, Operate.DELETE, clientOrderSBVO.getOrderSbId(), "ajax delete" );
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
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         // ���Action Form
         ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) form;

         // ����ѡ�е�ID
         if ( clientOrderSBVO.getSelectedIds() != null && !clientOrderSBVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientOrderSBVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientOrderSBId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientOrderSBVO clientOrderSBVOForDel = clientOrderSBService.getClientOrderSBVOByClientOrderSBId( clientOrderSBId );
                  clientOrderSBVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderSBVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientOrderSBService.deleteClientOrderSB( clientOrderSBVOForDel );
               }
            }

            insertlog( request, clientOrderSBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderSBVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         clientOrderSBVO.setSelectedIds( "" );
         clientOrderSBVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Object Json
    * ������������
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
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // ��ʼ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // �����In House��¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // �����Hr Service��¼
         else
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // ���super��
         mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );

         if ( mappingVOs != null && mappingVOs.size() > 0 )
         {
            // �Ƴ���ͬ���������籣
            if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
            {
               final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

               // ���ContractId�����籣
               final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
               final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

               if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
               {
                  // ��ʼ�������籣
                  for ( Object object : employeeContractSBVOs )
                  {
                     EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;

                     // ����籣�Ѵ����Ҳ�Ϊ��ǰ�޸������Ƴ�
                     for ( Iterator< MappingVO > iterator = mappingVOs.iterator(); iterator.hasNext(); )
                     {
                        MappingVO mappingVO = ( MappingVO ) iterator.next();

                        // ���
                        if ( employeeContractSBVO.getSbSolutionId().equals( mappingVO.getMappingId() ) && !employeeContractSBVO.getSbSolutionId().equals( sbSolutionId ) )
                        {
                           iterator.remove();
                        }
                     }
                  }
               }
            }

         }

         mappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( mappingVOs, "sbSolutionId", sbSolutionId ) );

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

   // ȥ�����е��籣
   public ActionForward list_object_options_manage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // ��ÿͻ�����ID
         String orderHeaderId = request.getParameter( "orderHeaderId" );

         if ( KANUtil.filterEmpty( orderHeaderId ) != null )
         {
            orderHeaderId = KANUtil.decodeStringFromAjax( orderHeaderId );
         }

         // ��������籣
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );

         // ��������籣
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         // �����In House��¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // �����Hr Service��¼
         else
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }

         // ��ȡ�ظ��ġ�
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         ClientOrderSBVO clientOrderSBVO = null;
         for ( int i = 0; i < clientOrderSBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOs.get( i );
               if ( clientOrderSBVO.getSbSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {
                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( allMappingVOs, "sbSolutionId", null ) );

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

   // ���ݽ������ID����ȡ�籣��
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

         // ��ʼ��Service�ӿ�
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // ��÷���Э��ID
         String orderHeaderId = request.getParameter( "orderHeaderId" );

         // ��������籣
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );
         // �����籣
         final List< MappingVO > allMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) );
         // ���Ŀ���籣
         final List< MappingVO > targetMappingVOs = new ArrayList< MappingVO >();

         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            // ���������������籣�����ս�������������û��������˻������е��籣
            for ( int i = 0; i < clientOrderSBVOs.size(); i++ )
            {
               for ( MappingVO mappingVO : allMappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( ( ( ClientOrderSBVO ) clientOrderSBVOs.get( i ) ).getSbSolutionId() ) )
                  {
                     targetMappingVOs.add( mappingVO );
                  }
               }

            }
         }
         else
         {
            // ��������籣
            targetMappingVOs.addAll( allMappingVOs );

         }

         targetMappingVOs.add( 0, ( ( ClientOrderSBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( targetMappingVOs, "sbSolutionId", null ) );

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
