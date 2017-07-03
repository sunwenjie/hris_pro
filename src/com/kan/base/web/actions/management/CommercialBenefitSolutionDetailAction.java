package com.kan.base.web.actions.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDetailVO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CommercialBenefitSolutionDetailService;
import com.kan.base.service.inf.management.CommercialBenefitSolutionHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class CommercialBenefitSolutionDetailAction extends BaseAction
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

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = ( CommercialBenefitSolutionDetailVO ) form;

         // ����SubAction
         dealSubAction( commercialBenefitSolutionDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = commercialBenefitSolutionDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( headerId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         commercialBenefitSolutionHeaderVO.reset( null, request );
         // �����޸ġ��鿴
         commercialBenefitSolutionHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "commercialBenefitSolutionHeaderForm", commercialBenefitSolutionHeaderVO );
         request.setAttribute( "listAttachmentCount", commercialBenefitSolutionHeaderVO.getAttachmentArray().length );

         // �����÷�ʽ����������С�� ������������ʽ������Ĭ��ֵ
         commercialBenefitSolutionDetailVO.setCalculateType( commercialBenefitSolutionHeaderVO.getCalculateType() );
         commercialBenefitSolutionDetailVO.setAccuracy( commercialBenefitSolutionHeaderVO.getAccuracy() );
         commercialBenefitSolutionDetailVO.setRound( commercialBenefitSolutionHeaderVO.getRound() );

         commercialBenefitSolutionDetailVO.setHeaderId( headerId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder commercialBenefitSolutionDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         commercialBenefitSolutionDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         commercialBenefitSolutionDetailHolder.setObject( commercialBenefitSolutionDetailVO );
         // ����ҳ���¼����
         commercialBenefitSolutionDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOsByCondition( commercialBenefitSolutionDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( commercialBenefitSolutionDetailHolder, request );

         commercialBenefitSolutionDetailVO.setStatus( BaseVO.TRUE );
         commercialBenefitSolutionDetailVO.reset( null, request );
         // Holder��д��Request����
         request.setAttribute( "commercialBenefitSolutionDetailHolder", commercialBenefitSolutionDetailHolder );
         request.setAttribute( "commercialBenefitSolutionDetailForm", commercialBenefitSolutionDetailVO );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            // Ajax Table���ã�ֱ�Ӵ���CommercialBenefitSolutionDetail JSP
            return mapping.findForward( "listCommercialBenefitSolutionDetailTable" );
         }
         // ���subAction
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listCommercialBenefitSolutionDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-09-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

            // ��õ�ǰFORM
            final CommercialBenefitSolutionDetailVO commercialBenefitDetailVO = ( CommercialBenefitSolutionDetailVO ) form;
            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            commercialBenefitDetailVO.setHeaderId( headerId );
            commercialBenefitDetailVO.setCreateBy( getUserId( request, response ) );
            commercialBenefitDetailVO.setModifyBy( getUserId( request, response ) );
            commercialBenefitDetailVO.setAccountId( getAccountId( request, response ) );

            // ������ӷ���
            commercialBenefitSolutionDetailService.insertCommercialBenefitSolutionDetail( commercialBenefitDetailVO );

            // ���¼��س����е�CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( CommercialBenefitSolutionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
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
         // ��ʼ��Service�ӿ�
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );
         final CommercialBenefitSolutionHeaderService commercialBenefitSolutionHeaderService = ( CommercialBenefitSolutionHeaderService ) getService( "commercialBenefitSolutionHeaderService" );
         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // ���CommercialBenefitSolutionDetailVO����
         final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( detailId );
         // ���CommercialBenefitSolutionHeaderVO����
         final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionHeaderService.getCommercialBenefitSolutionHeaderVOByHeaderId( commercialBenefitSolutionDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         commercialBenefitSolutionDetailVO.reset( null, request );
         // �����޸����
         commercialBenefitSolutionDetailVO.setSubAction( VIEW_OBJECT );
         // ����request����
         request.setAttribute( "commercialBenefitSolutionHeaderForm", commercialBenefitSolutionHeaderVO );
         request.setAttribute( "commercialBenefitSolutionDetailForm", commercialBenefitSolutionDetailVO );

         // ��дrole
         request.setAttribute( "role", getRole( request, null ) );
         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageCommercialBenefitSolutionDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            // ��ʼ��Service�ӿ�
            final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡ��������
            final CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            commercialBenefitSolutionDetailVO.update( ( CommercialBenefitSolutionDetailVO ) form );
            commercialBenefitSolutionDetailVO.setModifyBy( getUserId( request, response ) );

            // �����޸Ľӿ�
            commercialBenefitSolutionDetailService.updateCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );

            // ���¼��س����е�CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( CommercialBenefitSolutionDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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
         final CommercialBenefitSolutionDetailService commercialBenefitSolutionDetailService = ( CommercialBenefitSolutionDetailService ) getService( "commercialBenefitSolutionDetailService" );

         // ��õ�ǰform
         CommercialBenefitSolutionDetailVO commercialBenefitSolutionDetailVO = ( CommercialBenefitSolutionDetailVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( commercialBenefitSolutionDetailVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : commercialBenefitSolutionDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               commercialBenefitSolutionDetailVO = commercialBenefitSolutionDetailService.getCommercialBenefitSolutionDetailVOByDetailId( selectedId );
               commercialBenefitSolutionDetailVO.setModifyBy( getUserId( request, response ) );
               commercialBenefitSolutionDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               commercialBenefitSolutionDetailService.deleteCommercialBenefitSolutionDetail( commercialBenefitSolutionDetailVO );
            }

            // ���¼��س����е�CommercialBenefitSolution
            constantsInit( "initCommercialBenefitSolution", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSelectedIds( "" );
         ( ( CommercialBenefitSolutionDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
