package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.kan.hro.domain.biz.client.ClientGroupDTO;
import com.kan.hro.domain.biz.client.ClientGroupVO;
import com.kan.hro.service.inf.biz.client.ClientGroupService;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�ClientGroupAction  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-8-8  
 */
public class ClientGroupAction extends BaseAction
{

	public final static String accessAction = "HRO_BIZ_CLIENT_GROUP";
   /**
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

         // ��ʼ��Service
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // ��ȡ��ǰ��¼�û���accountId
         final String accountId = getAccountId( request, response );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientGroupService.getClientGroupBaseViews( accountId ) );

         // Send to clientGroup
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
    * List clientGroup
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
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
         // ���Action Form
         final ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
         clientGroupVO.setAccountId( getAccountId( request, response ) );

         // �����Action��ɾ���û��б�
         if ( clientGroupVO.getSubAction() != null && clientGroupVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( clientGroupVO );
         }

         // ���û��ָ��������Ĭ�ϰ�ClientGroupId����
         if ( clientGroupVO.getSortColumn() == null || clientGroupVO.getSortColumn().isEmpty() )
         {
            clientGroupVO.setSortColumn( "clientGroupId" );
            clientGroupVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pageListHolder.setPage( page );
         // ���뵱ǰֵ����
         pageListHolder.setObject( clientGroupVO );
         // ����ҳ���¼����
         pageListHolder.setPageSize( listPageSize );
         // ���SubAction
         String subAction = "";
         // �����SubAction��Ϊ��
         if ( clientGroupVO.getSubAction() != null )
         {
            subAction = clientGroupVO.getSubAction().trim();
         }
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         clientGroupService.getClientGroupVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_GROUP" ) );
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
      return mapping.findForward( "listClientGroup" );
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
   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( ClientGroupVO ) form ).setSubAction( CREATE_OBJECT );
      // ����״̬Ĭ��ֵ
      ( ( ClientGroupVO ) form ).setStatus( ClientGroupVO.TRUE );

      // ��ת���½�����
      return mapping.findForward( "manageClientGroup" );
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
   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // ��õ�ǰ����
         String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( clientGroupId == null || clientGroupId.trim().isEmpty() )
         {
            clientGroupId = ( ( ClientGroupVO ) form ).getClientGroupId();
         }

         // ���ClientGroupVO
         final ClientGroupVO clientGroupVO = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );

         // ˢ��VO���󣬳�ʼ�������б����ʻ�
         clientGroupVO.reset( null, request );
         clientGroupVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientGroupForm", clientGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageClientGroup" );
   }

   // Reviewed by Kevin Jin at 2013-11-07
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
            // ���ActionForm
            final ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
            // ��ȡ��¼�û�
            clientGroupVO.setCreateBy( getUserId( request, response ) );
            clientGroupVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            clientGroupVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // �½�����
            clientGroupService.insertClientGroup( clientGroupVO );
            
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
            
            // �ж��Ƿ���Ҫת��
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // ����ת���ַ
               forwardURL = forwardURL + ( ( ClientGroupVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );
               
               return null;
            }

         }

         // ���Form����
         ( ( ClientGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify clientGroup
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
            final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
            // ��õ�ǰ����
            final String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final ClientGroupVO clientGroupVO = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );
            // ��ȡ��¼�û�
            clientGroupVO.update( ( ClientGroupVO ) form );
            // �����Զ���Column
            clientGroupVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientGroupVO.setModifyBy( getUserId( request, response ) );
            // �޸Ķ���
            clientGroupService.updateClientGroup( clientGroupVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // ���Form����
         ( ( ClientGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete clientGroup
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
    * Delete clientGroup list
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
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );
         // ���Action Form
         ClientGroupVO clientGroupVO = ( ClientGroupVO ) form;
         // ����ѡ�е�ID
         if ( clientGroupVO.getSelectedIds() != null && !clientGroupVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : clientGroupVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // ����ת��
                  final String clientGroupId = KANUtil.decodeStringFromAjax( selectedId );
                  // ����������ö�ӦVO
                  final ClientGroupVO clientGroupVOForDel = clientGroupService.getClientGroupVOByClientGroupId( clientGroupId );
                  clientGroupVOForDel.setModifyBy( getUserId( request, response ) );
                  clientGroupVOForDel.setModifyDate( new Date() );
                  // ����ɾ���ӿ�
                  clientGroupService.deleteClientGroup( clientGroupVOForDel );
               }
            }
         }
         // ���Selected IDs����Action
         clientGroupVO.setSelectedIds( "" );
         clientGroupVO.setSubAction( "" );
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
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ClientGroupService clientGroupService = ( ClientGroupService ) getService( "clientGroupService" );

         // ��õ�ǰ����
         final String clientGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientGroupId" ), "UTF-8" ) );

         // ��ʼ��ClientGroupDTO
         ClientGroupDTO clientGroupDTO = new ClientGroupDTO();

         // �������clientGroupId
         if ( !clientGroupId.equals( "" ) )
         {
            // �½�һ��ClientGroupVO���ڲ�ѯDTO
            final ClientGroupVO clientGroupVO = new ClientGroupVO();
            clientGroupVO.setAccountId( getAccountId( request, response ) );
            clientGroupVO.setClientGroupId( clientGroupId );
            // ���ClientGroupDTO
            final Object cbjectClientGroupDTO = clientGroupService.getClientGroupDTOsByClientGroupVO( clientGroupVO );
            clientGroupDTO = ( ClientGroupDTO ) cbjectClientGroupDTO;

            // ˢ��VO���󣬳�ʼ�������б����ʻ�
            clientGroupDTO.getClientGroupVO().reset( null, request );
            clientGroupDTO.getClientGroupVO().setSubAction( VIEW_OBJECT );

         }

         // ���Client Count����Tab Number��ʾ
         final int clientCount = clientGroupDTO.getClientBaseViews().size();

         request.setAttribute( "clientCount", clientCount );
         request.setAttribute( "clientGroupDTO", clientGroupDTO );
         request.setAttribute( "clientGroupForm", ( clientGroupDTO.getClientGroupVO() == null ) ? request.getAttribute( "clientGroupForm" ) : clientGroupDTO.getClientGroupVO() );

         // Ajax����
         return mapping.findForward( "listSpecialInfo" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
