package com.kan.base.web.actions.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.SocialBenefitDetailVO;
import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SocialBenefitDetailService;
import com.kan.base.service.inf.system.SocialBenefitHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class SocialBenefitDetailAction extends BaseAction
{
	public static final String accessAction = "HRO_SB_SB_POLICY";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;

         // ����SubAction
         dealSubAction( socialBenefitDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = socialBenefitDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ���SocialBenefitHeaderVO
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( headerId );

         // ˢ�¹��ʻ�
         socialBenefitHeaderVO.reset( null, request );

         // ����SubAction
         socialBenefitHeaderVO.setSubAction( VIEW_OBJECT );

         // ���City Id�������Province Id
         if ( KANUtil.filterEmpty( socialBenefitHeaderVO.getCityId(), "0" ) != null )
         {
            socialBenefitHeaderVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( socialBenefitHeaderVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            socialBenefitHeaderVO.setCityIdTemp( socialBenefitHeaderVO.getCityId() );
         }

         // д��request����
         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );

         // ���û��ָ��������Ĭ�ϰ� LeaveId����
         if ( socialBenefitDetailVO.getSortColumn() == null || socialBenefitDetailVO.getSortColumn().isEmpty() )
         {
            socialBenefitDetailVO.setSortColumn( "itemId" );
            socialBenefitDetailVO.setSortOrder( "desc" );
         }

         // ��������ID
         socialBenefitDetailVO.setHeaderId( headerId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder socialBenefitDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         socialBenefitDetailHolder.setPage( page );
         // ��������ID
         socialBenefitDetailVO.setHeaderId( headerId );
         // ���뵱ǰֵ����
         socialBenefitDetailHolder.setObject( socialBenefitDetailVO );
         // ����ҳ���¼����
         socialBenefitDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         socialBenefitDetailService.getSocialBenefitDetailVOsByCondition( socialBenefitDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( socialBenefitDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "socialBenefitDetailHolder", socialBenefitDetailHolder );
         request.setAttribute( "PAGE_ACCOUNT_ID", getAccountId( request, response ) );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���SocialBenetifDetail JSP
            return mapping.findForward( "listSocialBenefitDetailTable" );
         }
         ( ( SocialBenefitDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSocialBenefitDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // ��õ�ǰFORM
            final SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;
            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            socialBenefitDetailVO.setHeaderId( headerId );
            socialBenefitDetailVO.setCreateBy( getUserId( request, response ) );
            socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            socialBenefitDetailVO.setAccountId( getAccountId( request, response ) );
            // checkbox�����籣�·ݡ���������
            socialBenefitDetailVO.setTermMonth( KANUtil.toJasonArray( socialBenefitDetailVO.getTermMonthArray(), "," ) );
            socialBenefitDetailService.insertSocialBenefitDetail( socialBenefitDetailVO );

            // ���¼��س����е�ϵͳ�籣
            constants.initSocialBenefit();

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( SocialBenefitDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��HeaderService�ӿ�
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
         final SocialBenefitHeaderService socialBenefitHeaderService = ( SocialBenefitHeaderService ) getService( "socialBenefitHeaderService" );
         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // ���SocialBenefitDetailVO����
         final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( detailId );
         // ���SocialBenefitHeaderVO����
         final SocialBenefitHeaderVO socialBenefitHeaderVO = socialBenefitHeaderService.getSocialBenefitHeaderVOByHeaderId( socialBenefitDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         socialBenefitDetailVO.reset( null, request );
         // �����޸����
         socialBenefitDetailVO.setSubAction( BaseAction.VIEW_OBJECT );
         socialBenefitDetailVO.setStatus( SocialBenefitHeaderVO.TRUE );
         // ����request����
         request.setAttribute( "socialBenefitHeaderForm", socialBenefitHeaderVO );
         request.setAttribute( "socialBenefitDetailForm", socialBenefitDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageSocialBenefitDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // �ӱ�ID
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡSocialBenefitDetailVO����
            final SocialBenefitDetailVO socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            socialBenefitDetailVO.update( ( SocialBenefitDetailVO ) form );
            // ��ȡ��¼�û�
            socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            // checkbox�����籣�·ݡ���������
            socialBenefitDetailVO.setTermMonth( KANUtil.toJasonArray( socialBenefitDetailVO.getTermMonthArray(), "," ) );
            // �����޸ķ���
            socialBenefitDetailService.updateSocialBenefitDetail( socialBenefitDetailVO );

            // ���¼��س����е�ϵͳ�籣
            constants.initSocialBenefit();

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }
         // ���Form
         ( ( SocialBenefitDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final SocialBenefitDetailService socialBenefitDetailService = ( SocialBenefitDetailService ) getService( "socialBenefitDetailService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // ���Action Form
         SocialBenefitDetailVO socialBenefitDetailVO = ( SocialBenefitDetailVO ) form;
         // ����ѡ�е�ID
         if ( socialBenefitDetailVO.getSelectedIds() != null && !socialBenefitDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : socialBenefitDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               socialBenefitDetailVO = socialBenefitDetailService.getSocialBenefitDetailVOByDetailId( selectedId );
               // ����ɾ���ӿ�
               socialBenefitDetailVO.setDetailId( selectedId );
               socialBenefitDetailVO.setAccountId( getAccountId( request, response ) );
               socialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
               socialBenefitDetailService.deleteSocialBenefitDetail( socialBenefitDetailVO );
            }
         }

         // ���¼��س����е�ϵͳ�籣
         constants.initSocialBenefit();

         // ���Selected IDs����Action
         socialBenefitDetailVO.setSelectedIds( "" );
         socialBenefitDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
