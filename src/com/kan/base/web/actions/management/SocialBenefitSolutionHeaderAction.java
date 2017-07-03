package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SocialBenefitSolutionHeaderAction extends BaseAction

{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SB_SOLUTION";

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
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // ���Action Form
         final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;

         // ����ɾ������
         if ( socialBenefitSolutionHeaderVO.getSubAction() != null && socialBenefitSolutionHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( socialBenefitSolutionHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder socialBenefitSolutionHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         socialBenefitSolutionHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         socialBenefitSolutionHeaderHolder.setObject( socialBenefitSolutionHeaderVO );
         // ����ҳ���¼����
         socialBenefitSolutionHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsByCondition( socialBenefitSolutionHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( socialBenefitSolutionHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "socialBenefitSolutionHeaderHolder", socialBenefitSolutionHeaderHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���SocialBenefitSolutionHeader JSP
            return mapping.findForward( "listSocialBenefitSolutionHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listSocialBenefitSolutionHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( SocialBenefitSolutionHeaderVO ) form ).setStatus( SocialBenefitSolutionHeaderVO.TRUE );
      ( ( SocialBenefitSolutionHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( SocialBenefitSolutionHeaderVO ) form ).setPersonalSBBurden( "2" );

      final PagedListHolder socialBenefitSolutionDetailHolder = new PagedListHolder();
      socialBenefitSolutionDetailHolder.setHolderSize( 0 );
      request.setAttribute( "socialBenefitSolutionDetailHolder", socialBenefitSolutionDetailHolder );

      // ��ת���½�����  
      return mapping.findForward( "manageSocialBenefitSolutionHeader" );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ��ʼ��form
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

            // ��õ���FORM
            final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;
            socialBenefitSolutionHeaderVO.setCreateBy( getUserId( request, response ) );
            socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
            socialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );

            // ������ӷ���
            socialBenefitSolutionHeaderService.insertSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
            socialBenefitSolutionDetailVO.setHeaderId( socialBenefitSolutionHeaderVO.getHeaderId() );

            // ��ʼ�������־ö���
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, socialBenefitSolutionHeaderVO, Operate.ADD, socialBenefitSolutionHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���Form
            ( ( SocialBenefitSolutionHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תdetail����
      return new SocialBenefitSolutionDetailAction().list_object( mapping, socialBenefitSolutionDetailVO, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ��ʼ��form
      final SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO = new SocialBenefitSolutionDetailVO();
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "headerId" ), "UTF-8" ) );
            socialBenefitSolutionDetailVO.setHeaderId( headerId );

            // ��ȡSocialBenefitSolutionHeaderVO����
            final SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            socialBenefitSolutionHeaderVO.update( ( SocialBenefitSolutionHeaderVO ) form );
            socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );

            // �����޸ķ���
            socialBenefitSolutionHeaderService.updateSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );

            // ��ʼ�������־ö���
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, socialBenefitSolutionHeaderVO, Operate.MODIFY, socialBenefitSolutionHeaderVO.getHeaderId(), null );
         }

         // ���Form
         ( ( SocialBenefitSolutionHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תdetail����
      return new SocialBenefitSolutionDetailAction().list_object( mapping, socialBenefitSolutionDetailVO, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use

   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-22
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // ���Action Form
         SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) form;
         // ����ѡ�е�ID
         if ( socialBenefitSolutionHeaderVO.getSelectedIds() != null && !socialBenefitSolutionHeaderVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, socialBenefitSolutionHeaderVO, Operate.DELETE, null, socialBenefitSolutionHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : socialBenefitSolutionHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOByHeaderId( selectedId );
               socialBenefitSolutionHeaderVO.setHeaderId( selectedId );
               socialBenefitSolutionHeaderVO.setAccountId( getAccountId( request, response ) );
               socialBenefitSolutionHeaderVO.setModifyBy( getUserId( request, response ) );
               socialBenefitSolutionHeaderService.deleteSocialBenefitSolutionHeader( socialBenefitSolutionHeaderVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initSocialBenefitSolution", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         socialBenefitSolutionHeaderVO.setSelectedIds( "" );
         socialBenefitSolutionHeaderVO.setSubAction( "" );
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
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // ��ʼ�� JSONArray
         final JSONArray array = new JSONArray();

         // ��ȡSocialBenefitSolutionHeaderVO�б�
         final List< SocialBenefitSolutionHeaderVO > socialBenefitSolutionHeaderVOs = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsByAccountId( getAccountId( request, response ) );

         // ������Reset
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO : socialBenefitSolutionHeaderVOs )
            {
               socialBenefitSolutionHeaderVO.reset( mapping, request );
            }
         }

         array.addAll( socialBenefitSolutionHeaderVOs );

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
