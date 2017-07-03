package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CommercialBenefitSolutionHeaderAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CB_SOLUTION";

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
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // ���Action Form
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;

         // �Ƿ�ΪINHOUSE
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            commercialBenefitSolutionHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // ����ɾ������
         if ( commercialBenefitSolutionHeaderVO.getSubAction() != null && commercialBenefitSolutionHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( commercialBenefitSolutionHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder commercialBenefitSolutionHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         commercialBenefitSolutionHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         commercialBenefitSolutionHeaderHolder.setObject( commercialBenefitSolutionHeaderVO );
         // ����ҳ���¼����
         commercialBenefitSolutionHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOsByCondition( commercialBenefitSolutionHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( commercialBenefitSolutionHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "commercialBenefitSolutionHeaderHolder", commercialBenefitSolutionHeaderHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table���ã�ֱ�Ӵ���CommercialBenefitSolutionHeader JSP
            return mapping.findForward( "listCommercialBenefitSolutionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listCommercialBenefitSolutionHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setCalculateType( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setAccuracy( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setRound( "1" );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setStatus( CommercialBenefitSolutionHeaderVO.TRUE );
      ( ( CommercialBenefitSolutionHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      request.setAttribute( "listAttachmentCount", "0" );

      // ��ת���½�����  
      return mapping.findForward( "manageCommercialBenefitSolutionHeader" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��CommercialBenefitSolutionDetailVO
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = new CommercialBenefitSolutionDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

            // ��õ���FORM
            final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;
            commercialBenefitSolutionHeaderVO.setCreateBy( getUserId( request, response ) );
            commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
            commercialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );

            // ������ӷ���
            commercialBenefitSolutionHeaderService.insertCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
            commercialBenefitSolutionDetailVO.setHeaderId( commercialBenefitSolutionHeaderVO.getHeaderId() );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            // ���¼����˻������е��̱�����
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.ADD, commercialBenefitSolutionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���Form
            ( ( CommercialBenefitSolutionHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new CommercialBenefitSolutionDetailAction().list_object( mapping, commercialBenefitSolutionDetailVO, request, response );
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
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡ��������
            final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            commercialBenefitSolutionHeaderVO.update( ( CommercialBenefitSolutionHeaderVO ) form );
            commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����޸ķ���
            commercialBenefitSolutionHeaderService.updateCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );

            // ���¼����˻������е��̱�����
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.MODIFY, commercialBenefitSolutionHeaderVO.getHeaderId(), null );
         }

         // ���Form
         ( ( CommercialBenefitSolutionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תList����
      return new CommercialBenefitSolutionDetailAction().list_object( mapping, new CommercialBenefitSolutionDetailVO(), request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // ��õ�ǰform
         CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) form;
         // ����ѡ�е�ID
         if ( commercialBenefitSolutionHeaderVO.getSelectedIds() != null && !commercialBenefitSolutionHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, commercialBenefitSolutionHeaderVO, Operate.DELETE, null, commercialBenefitSolutionHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : commercialBenefitSolutionHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( selectedId );
               commercialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
               commercialBenefitSolutionHeaderVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               commercialBenefitSolutionHeaderService.deleteCommercialBenefitSolutionHeader( commercialBenefitSolutionHeaderVO );
            }

         }

         // ���¼����˻������е��̱�����
         constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

         // ���Selected IDs����Action
         ( ( CommercialBenefitSolutionHeaderVO ) form ).setSelectedIds( "" );
         ( ( CommercialBenefitSolutionHeaderVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final CommercialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( socialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderViewsByAccountId( getAccountId( request, response ) ) );

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

}
