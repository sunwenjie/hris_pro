package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.PositionVO;
import com.kan.base.service.inf.management.PositionService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class PositionAction extends BaseAction
{
   public static String accessAction = "HRO_EMPLOYEE_POSITION";

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

         // ��ʼ��Position Service
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( positionService.getPositionBaseViewsByAccountId( getAccountId( request, response ) ) );
         // Send to client
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
    * List positions
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
         // ���Action Form
         final PositionVO positionVO = ( PositionVO ) form;

         // ��ȡ�ĵ�ǰ��¼��accountId
         final String accountId = getAccountId( request, response );
         // ��accountId����request��,�Ժ��ȡ����ְλ���õ�positionDTO��
         request.setAttribute( "accountId", accountId );

         positionVO.setAccountId( accountId );

         // �����Action��ɾ���û��б�
         if ( positionVO.getSubAction() != null && positionVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( positionVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      request.setAttribute( "role", getRole( request, response ) );
      // ��ת���б����
      return mapping.findForward( "listPosition" );
   }

   /**
    * To position modify page
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
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // ��õ�ǰ����
         String positionId = request.getParameter( "positionId" );
         if ( KANUtil.filterEmpty( positionId ) == null )
         {
            positionId = ( ( PositionVO ) form ).getPositionId();
         }
         else
         {
            positionId = Cryptogram.decodeString( URLDecoder.decode( positionId, "UTF-8" ) );
         }
         // ���������Ӧ����
         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         positionVO.reset( null, request );
         positionVO.setSubAction( VIEW_OBJECT );

         // ��õ�ǰPositionVO��Ӧ��SkillId �� Attachment ����
         final String[] skillIdArray = positionVO.getSkillIdArray();
         final String[] attachmentArray = positionVO.getAttachmentArray();
         // ����SkillIdArraySize �� AttachmentArraySize ��ǰ������Tab��ʾ
         request.setAttribute( "skillIdArraySize", ( skillIdArray == null ) ? "0" : skillIdArray.length );
         request.setAttribute( "attachmentArraySize", ( attachmentArray == null ) ? "0" : attachmentArray.length );

         // ����ActionForm����ǰ��
         request.setAttribute( "mgtPositionForm", positionVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "managePosition" );
   }

   /**
    * To position new page
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
      ( ( PositionVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( PositionVO ) form ).setStatus( BaseVO.TRUE );
      // �������Parent PositionId
      final String noEncodedParentPositionId = request.getParameter( "parentPositionId" );

      // ����SkillIdArray��AttachmentArray ����Ĭ��ֵ
      request.setAttribute( "skillIdArraySize", "0" );
      request.setAttribute( "attachmentArraySize", "0" );

      if ( noEncodedParentPositionId != null && !noEncodedParentPositionId.trim().equals( "" ) )
      {
         String parentPositionId;
         try
         {
            // ���Parent PositionVO������
            parentPositionId = Cryptogram.decodeString( URLDecoder.decode( noEncodedParentPositionId, "UTF-8" ) );
            // ��ʼ��Service�ӿ�
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // �����������Parent PositionVO
            final PositionVO positionVO = positionService.getPositionVOByPositionId( parentPositionId );
            // ���Parent PositionVO��Position Name
            final String parentPositionName = positionVO.getTitleZH() + " - " + positionVO.getTitleEN();
            ( ( PositionVO ) form ).setParentPositionId( parentPositionId );
            ( ( PositionVO ) form ).setParentPositionName( parentPositionName );

         }
         catch ( UnsupportedEncodingException e )
         {
            e.printStackTrace();
         }
      }
      // ��ת���½�����
      return mapping.findForward( "managePosition" );
   }

   /**
    * Add position
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // ���ActionForm
            final PositionVO positionVO = ( PositionVO ) form;

            positionVO.setAccountId( getAccountId( request, response ) );
            positionVO.setCreateBy( getUserId( request, response ) );
            positionVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            positionService.insertPosition( positionVO );
            // ������ӳɹ����
            success( request );
            // ���¼��س����е�mgtPosition
            constantsInit( "initEmployeePosition", getAccountId( request, response ) );
            // ���Form����
            ( ( PositionVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify position
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
            final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
            // ��õ�ǰ����
            final String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );
            // ���������Ӧδ�޸�ǰ����
            final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
            // �޸Ķ�������
            positionVO.update( ( PositionVO ) form );
            positionVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            positionService.updatePosition( positionVO );
            // ���¼��س����е�mgtPosition
            constantsInit( "initEmployeePosition", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
            // ���Form����
            ( ( PositionVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
   * Delete position
   *
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // ��õ�ǰ����
         final String positionId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "positionId" ), "UTF-8" ) );
         // ���������Ӧ��PositionVO
         final PositionVO positionVO = positionService.getPositionVOByPositionId( positionId );
         // ����PositionVO�����ֵ
         positionVO.setModifyBy( getUserId( request, response ) );
         positionVO.setModifyDate( new Date() );
         // ɾ��ѡ����positionVO
         positionService.deletePosition( positionVO );
         // ���¼��س����е�mgtPosition
         constantsInit( "initEmployeePosition", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete position list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PositionService positionService = ( PositionService ) getService( "mgtPositionService" );
         // ���Action Form
         final PositionVO positionVO = ( PositionVO ) form;
         // ����ѡ�е�ID
         if ( positionVO.getPositionIdArray() != null && positionVO.getPositionIdArray().length > 0 )
         {
            // �ָ�
            for ( String selectedId : positionVO.getPositionIdArray() )
            {
               // ͨ��id��ȡid��Ӧ�� positionVO
               final PositionVO positionVOForDel = positionService.getPositionVOByPositionId( selectedId );
               // ����PositionVO�����ֵ
               positionVOForDel.setModifyBy( getUserId( request, response ) );
               positionVOForDel.setModifyDate( new Date() );
               positionService.deletePosition( positionVOForDel );
            }
         }
         // ���¼��س����е�mgtPosition
         constantsInit( "initEmployeePosition", getAccountId( request, response ) );
         // ���Selected IDs����Action
         positionVO.setSelectedIds( "" );
         positionVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
