package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.service.inf.biz.client.ClientContractPropertyService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientContractPropertyAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-19  
 */
public class ClientContractPropertyAction extends BaseAction
{

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
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // ���Action Form
         final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;
         clientContractPropertyVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientContractPropertyVO.getSubAction() != null && clientContractPropertyVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientContractPropertyVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientContractPropertyVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";

         // �����SubAction��Ϊ��
         if ( clientContractPropertyVO.getSubAction() != null )
         {
            subAction = clientContractPropertyVO.getSubAction().trim();
         }

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientContractPropertyService.getClientContractPropertyVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_CONTRACT_PROPERTY" ) );
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
      return mapping.findForward( "listClientContractProperty" );
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
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );
      // ����Sub Action
      ( ( ClientContractPropertyVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageClientContractProperty" );
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
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // ��õ�ǰ����
         String clientContractPropertyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // �����������Ҷ�Ӧ��clientContractPropertyVO
         final ClientContractPropertyVO clientContractPropertyVO = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientContractPropertyVO.reset( null, request );
         // ����Sub Action
         clientContractPropertyVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientContractPropertyForm", clientContractPropertyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientContractProperty" );
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
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
            // ���ActionForm
            final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;
            // ��ȡ��¼�û�
            clientContractPropertyVO.setAccountId( getAccountId( request, response ) );
            clientContractPropertyVO.setCreateBy( getUserId( request, response ) );
            clientContractPropertyVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            clientContractPropertyService.insertClientContractProperty( clientContractPropertyVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
         // ���Form����
         ( ( ClientContractPropertyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
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
            final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );

            // ��õ�ǰ����
            final String clientContractPropertyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���������Ӧ����
            final ClientContractPropertyVO clientContractPropertyVO = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );

            // ��ȡ��¼�û�
            clientContractPropertyVO.update( ( ClientContractPropertyVO ) form );
            clientContractPropertyVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            clientContractPropertyService.updateClientContractProperty( clientContractPropertyVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form����
         ( ( ClientContractPropertyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
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
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // ���Action Form
         ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) form;

         // ����ѡ�е�ID
         if ( clientContractPropertyVO.getSelectedIds() != null && !clientContractPropertyVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientContractPropertyVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientContractPropertyId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientContractPropertyVO clientContractPropertyVOForDel = clientContractPropertyService.getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );
                  clientContractPropertyVOForDel.setModifyBy( getUserId( request, response ) );
                  clientContractPropertyVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientContractPropertyService.deleteClientContractProperty( clientContractPropertyVOForDel );
               }
            }
         }

         // ���Selected IDs����Action
         clientContractPropertyVO.setSelectedIds( "" );
         clientContractPropertyVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
