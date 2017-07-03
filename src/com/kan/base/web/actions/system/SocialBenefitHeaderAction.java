package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.SocialBenefitHeaderRender;

public class SocialBenefitHeaderAction extends BaseAction
{

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
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // ���Action Form
         final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;

         // ����subAction
         dealSubAction( socialBenefitHeaderVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder socialBenefitHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         socialBenefitHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         socialBenefitHeaderHolder.setObject( socialBenefitHeaderVO );
         // ����ҳ���¼����
         socialBenefitHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         socialBenefitHeaderService.getSocialBenefitHeaderVOsByCondition( socialBenefitHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( socialBenefitHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "socialBenefitHeaderHolder", socialBenefitHeaderHolder );
         // ������籣������ readonly
         request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���SocialBenefitHeader JSP
            return mapping.findForward( "listSocialBenefitHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSocialBenefitHeader" );
   }

   public ActionForward list_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���CityId
         final String cityId = request.getParameter( "cityId" );
         // �籣����Id
         final String headerId = request.getParameter( "headerId" );
         // ��ʼ��Service
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // ��ó���ID��Ӧ���籣����
         final List< Object > socialBenefitHeaderVOs = socialBenefitHeaderService.getSocialBenefitHeaderVOsByCityId( cityId );

         // ��ʼ��Response��������
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         out.println( SocialBenefitHeaderRender.getSocialBenbfitHeaderVOsByCityId( request, socialBenefitHeaderVOs, headerId ) );
         out.flush();
         out.close();
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ��ʼ��Ĭ������
      ( ( SocialBenefitHeaderVO ) form ).setAttribute( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setStartRule( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setStartRuleRemark( "15" );
      ( ( SocialBenefitHeaderVO ) form ).setEndRule( "1" );
      ( ( SocialBenefitHeaderVO ) form ).setEndRuleRemark( "15" );
      ( ( SocialBenefitHeaderVO ) form ).setMakeup( "2" );
      ( ( SocialBenefitHeaderVO ) form ).setCompanyAccuracy( "3" );
      ( ( SocialBenefitHeaderVO ) form ).setPersonalAccuracy( "3" );
      ( ( SocialBenefitHeaderVO ) form ).setRound( "1" );

      // ����Sub Action
      ( ( SocialBenefitHeaderVO ) form ).setStatus( SocialBenefitHeaderVO.TRUE );
      ( ( SocialBenefitHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      
      request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );

      // ��ת���½�����  
      return mapping.findForward( "manageSocialBenefitHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��SocialBenefitDetailVO
         final SocialBenefitDetailVO socialBenefitDetailVO = new SocialBenefitDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ��õ�ǰFORM
            final SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;
            // checkbox�����籣�·ݡ���������
            socialBenefitHeaderVO.setTermMonth( KANUtil.toJasonArray( socialBenefitHeaderVO.getTermMonthArray(), "," ) );
            socialBenefitHeaderVO.setResidency( KANUtil.toJasonArray( socialBenefitHeaderVO.getResidencyArray(), "," ) );
            socialBenefitHeaderVO.setCreateBy( getUserId( request, response ) );
            socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
            socialBenefitHeaderVO.setAccountId( getAccountId( request, response ) );
            socialBenefitHeaderService.insertSocialBenefitHeader( socialBenefitHeaderVO );
            socialBenefitDetailVO.setHeaderId( socialBenefitHeaderVO.getHeaderId() );

            // ���¼��س����е�ϵͳ�籣
            constants.initSocialBenefit();

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
         }
         else
         {
            // �����ظ��ύ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // ���Form
            ( ( SocialBenefitHeaderVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return new SocialBenefitDetailAction().list_object( mapping, socialBenefitDetailVO, request, response );
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
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡSocialBenefitHeaderVO����
            final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            socialBenefitHeaderVO.update( ( SocialBenefitHeaderVO ) form );
            // checkbox�����籣�·ݡ���������
            socialBenefitHeaderVO.setTermMonth( KANUtil.toJasonArray( socialBenefitHeaderVO.getTermMonthArray(), "," ) );
            socialBenefitHeaderVO.setResidency( KANUtil.toJasonArray( socialBenefitHeaderVO.getResidencyArray(), "," ) );
            // ��ȡ��¼�û�
            socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            socialBenefitHeaderService.updateSocialBenefitHeader( socialBenefitHeaderVO );
            // ���¼��س����е�ϵͳ�籣
            constants.initSocialBenefit();
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         }
         // ���Form
         ( ( SocialBenefitHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new SocialBenefitDetailAction().list_object( mapping, new SocialBenefitDetailVO(), request, response );
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
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // ���Action Form
         SocialBenefitHeaderVO socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) form;
         // ����ѡ�е�ID
         if ( socialBenefitHeaderVO.getSelectedIds() != null && !socialBenefitHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : socialBenefitHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���Ҫɾ���Ķ���
               socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( selectedId );
               // ����ɾ���ӿ�
               socialBenefitHeaderVO.setHeaderId( selectedId );
               socialBenefitHeaderVO.setAccountId( getAccountId( request, response ) );
               socialBenefitHeaderVO.setModifyBy( getUserId( request, response ) );
               socialBenefitHeaderService.deleteSocialBenefitHeader( socialBenefitHeaderVO );
            }
         }

         // ���¼��س����е�ϵͳ�籣
         constants.initSocialBenefit();

         // ���Selected IDs����Action
         socialBenefitHeaderVO.setSelectedIds( "" );
         socialBenefitHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
