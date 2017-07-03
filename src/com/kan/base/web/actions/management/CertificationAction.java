package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.CertificationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CertificationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CertificationAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_LICENSES";

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
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // ���Action Form
         final CertificationVO certificationVO = ( CertificationVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         certificationVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( certificationVO.getSubAction() != null && certificationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( certificationVO );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder certificationHolder = new PagedListHolder();
         // ���뵱ǰҳ
         certificationHolder.setPage( page );
         // ���뵱ǰֵ����
         certificationHolder.setObject( certificationVO );
         // ����ҳ���¼����
         certificationHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         certificationService.getCertificationVOsByCondition( certificationHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( certificationHolder, request );
         // Holder��д��Request����
         request.setAttribute( "certificationHolder", certificationHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            return mapping.findForward( "listCertificationTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listCertification" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      // ����Sub Action
      ( ( CertificationVO ) form ).setStatus( CertificationVO.TRUE );
      ( ( CertificationVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageCertification" );
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
            final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
            // ��õ�ǰFORM
            final CertificationVO certificationVO = ( CertificationVO ) form;
            certificationVO.setCreateBy( getUserId( request, response ) );
            certificationVO.setModifyBy( getUserId( request, response ) );
            certificationVO.setAccountId( getAccountId( request, response ) );
            certificationService.insertCertification( certificationVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            // ��ʼ�������־ö���
            constantsInit( "initCertification", getAccountId( request, response ) );

            insertlog( request, certificationVO, Operate.ADD, certificationVO.getCertificationId(), null );
         }

         // ���Form
         ( ( CertificationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
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
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // ������ȡ�����
         String certificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "certificationId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( certificationId ) == null )
         {
            certificationId = ( ( CertificationVO ) form ).getCertificationId();
         }
         // ���CertificationVO����                                                                                          
         final CertificationVO certificationVO = certificationService.getCertificationVOByCertificationId( certificationId );
         // ����Add��Update
         certificationVO.setSubAction( VIEW_OBJECT );
         certificationVO.reset( null, request );
         // ��CertificationVO����request����
         request.setAttribute( "certificationForm", certificationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageCertification" );
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
            final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
            // ������ȡ�����
            final String certificationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "certificationId" ), "UTF-8" ) );
            // ��ȡCertificationVO����
            final CertificationVO certificationVO = certificationService.getCertificationVOByCertificationId( certificationId );
            // װ�ؽ��洫ֵ
            certificationVO.update( ( CertificationVO ) form );
            // ��ȡ��¼�û�
            certificationVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            certificationService.updateCertification( certificationVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            // ��ʼ�������־ö���
            constantsInit( "initCertification", getAccountId( request, response ) );

            insertlog( request, certificationVO, Operate.MODIFY, certificationVO.getCertificationId(), null );
         }
         // ���Form
         ( ( CertificationVO ) form ).reset();
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
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );
         // ���Action Form
         final CertificationVO certificationVO = ( CertificationVO ) form;
         // ����ѡ�е�ID
         if ( certificationVO.getSelectedIds() != null && !certificationVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : certificationVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               certificationVO.setCertificationId( selectedId );
               certificationVO.setAccountId( getAccountId( request, response ) );
               certificationVO.setModifyBy( getUserId( request, response ) );
               certificationService.deleteCertification( certificationVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initCertification", getAccountId( request, response ) );
            insertlog( request, certificationVO, Operate.DELETE, null, certificationVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         certificationVO.setSelectedIds( "" );
         certificationVO.setSubAction( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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

         // ��ʼ��CertificationService Service
         final CertificationService certificationService = ( CertificationService ) getService( "certificationService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( certificationService.getCertificationBaseViewsByAccountId( getAccountId( request, response ) ) );
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
