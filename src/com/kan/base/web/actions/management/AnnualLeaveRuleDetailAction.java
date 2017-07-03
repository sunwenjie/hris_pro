package com.kan.base.web.actions.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.AnnualLeaveRuleDetailVO;
import com.kan.base.domain.management.AnnualLeaveRuleHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.AnnualLeaveRuleDetailService;
import com.kan.base.service.inf.management.AnnualLeaveRuleHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class AnnualLeaveRuleDetailAction extends BaseAction
{
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
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;
         // ����SubAction
         dealSubAction( annualLeaveRuleDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = annualLeaveRuleDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( headerId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         annualLeaveRuleHeaderVO.reset( null, request );
         // �����޸ġ��鿴
         annualLeaveRuleHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "annualLeaveRuleHeaderForm", annualLeaveRuleHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder annualLeaveRuleDetailHolder = new PagedListHolder();
         annualLeaveRuleDetailVO.setHeaderId( headerId );
         // ���뵱ǰҳ
         annualLeaveRuleDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         annualLeaveRuleDetailHolder.setObject( annualLeaveRuleDetailVO );
         // ����ҳ���¼����
         annualLeaveRuleDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByCondition( annualLeaveRuleDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( annualLeaveRuleDetailHolder, request );

         annualLeaveRuleDetailVO.setStatus( BaseVO.TRUE );
         annualLeaveRuleDetailVO.reset( null, request );
         // Holder��д��Request����
         request.setAttribute( "annualLeaveRuleDetailHolder", annualLeaveRuleDetailHolder );
         request.setAttribute( "annualLeaveRuleDetailForm", annualLeaveRuleDetailVO );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���AnnualLeaveRuleDetail JSP
            return mapping.findForward( "listAnnualLeaveRuleDetailTable" );
         }

         // ���subAction
         ( ( AnnualLeaveRuleDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listAnnualLeaveRuleDetail" );
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
            final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

            // ��õ�ǰFORM
            final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;

            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );

            final List< Object > annualLeaveRuleDetailVOs = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByHeaderId( headerId );

            if ( checkAnnualLeaveRuleMonthSoction( annualLeaveRuleDetailVOs, annualLeaveRuleDetailVO, null ) )
            {
               annualLeaveRuleDetailVO.setHeaderId( headerId );
               annualLeaveRuleDetailVO.setCreateBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setAccountId( getAccountId( request, response ) );
               // ������ӷ���
               annualLeaveRuleDetailService.insertAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );

               // ���¼��ػ���
               constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

               // ���ر���ɹ��ı��
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

               insertlog( request, annualLeaveRuleDetailVO, Operate.ADD, annualLeaveRuleDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "���ʧ�ܣ��������Ѵ��ڣ�", MESSAGE_DETAIL );
            }
         }

         // ���Form
         ( ( AnnualLeaveRuleDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   private boolean checkAnnualLeaveRuleMonthSoction( final List< Object > annualLeaveRuleDetailVOs, final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO, final String detailId )
   {
      boolean flag = true;
      if ( annualLeaveRuleDetailVOs != null && annualLeaveRuleDetailVOs.size() > 0 )
      {
         String positionGradeId = annualLeaveRuleDetailVO.getPositionGradeId();
         String seniority = annualLeaveRuleDetailVO.getSeniority();

         for ( Object o : annualLeaveRuleDetailVOs )
         {
            final AnnualLeaveRuleDetailVO tempAannualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) o;

            if ( detailId != null && tempAannualLeaveRuleDetailVO.getDetailId().equals( detailId ) )
               continue;

            final String tempPositionGradeId = tempAannualLeaveRuleDetailVO.getPositionGradeId();
            final String tempSeniority = tempAannualLeaveRuleDetailVO.getSeniority();

            if ( positionGradeId.equals( tempPositionGradeId ) && seniority.equals( tempSeniority ) )
            {
               flag = false;
               break;
            }
         }
      }

      return flag;
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

         // ��ʼ��Service�ӿ�
         final AnnualLeaveRuleHeaderService annualLeaveRuleHeaderService = ( AnnualLeaveRuleHeaderService ) getService( "annualLeaveRuleHeaderService" );
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );
         // ��������ID
         final String detailId = request.getParameter( "detailId" );
         // ���AnnualLeaveRuleDetailVO����
         final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( detailId );
         // ���AnnualLeaveRuleHeaderVO����
         final AnnualLeaveRuleHeaderVO annualLeaveRuleHeaderVO = annualLeaveRuleHeaderService.getAnnualLeaveRuleHeaderVOByHeaderId( annualLeaveRuleDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         annualLeaveRuleDetailVO.reset( null, request );
         // �����޸����
         annualLeaveRuleDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "annualLeaveRuleHeaderForm", annualLeaveRuleHeaderVO );
         request.setAttribute( "annualLeaveRuleDetailForm", annualLeaveRuleDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageAnnualLeaveRuleDetailForm" );
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
            // ��ʼ��Service�ӿ�
            final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );
            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            // ��ȡ��������
            final AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( detailId );
            final List< Object > annualLeaveRuleDetailVOs = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOsByHeaderId( headerId );

            if ( checkAnnualLeaveRuleMonthSoction( annualLeaveRuleDetailVOs, ( AnnualLeaveRuleDetailVO ) form, detailId ) )
            {
               // װ�ؽ��洫ֵ
               annualLeaveRuleDetailVO.setHeaderId( headerId );
               annualLeaveRuleDetailVO.update( ( AnnualLeaveRuleDetailVO ) form );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               // �����޸Ľӿ�
               annualLeaveRuleDetailService.updateAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );

               // ���¼��ػ���
               constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );

               // ���ر༭�ɹ��ı��
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

               insertlog( request, annualLeaveRuleDetailVO, Operate.MODIFY, annualLeaveRuleDetailVO.getDetailId(), null );
            }
            else
            {
               error( request, null, "�༭ʧ�ܣ��������Ѵ��ڣ�", MESSAGE_DETAIL );
            }
         }

         // ���Form
         ( ( AnnualLeaveRuleDetailVO ) form ).reset();
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
         final AnnualLeaveRuleDetailService annualLeaveRuleDetailService = ( AnnualLeaveRuleDetailService ) getService( "annualLeaveRuleDetailService" );

         // ��õ�ǰform
         AnnualLeaveRuleDetailVO annualLeaveRuleDetailVO = ( AnnualLeaveRuleDetailVO ) form;

         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( annualLeaveRuleDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, annualLeaveRuleDetailVO, Operate.DELETE, null, annualLeaveRuleDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : annualLeaveRuleDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               annualLeaveRuleDetailVO = annualLeaveRuleDetailService.getAnnualLeaveRuleDetailVOByDetailId( selectedId );
               annualLeaveRuleDetailVO.setModifyBy( getUserId( request, response ) );
               annualLeaveRuleDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               annualLeaveRuleDetailService.deleteAnnualLeaveRuleDetail( annualLeaveRuleDetailVO );
            }

            // ���¼��ػ���
            constantsInit( "initAnnualLeaveRule", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         ( ( AnnualLeaveRuleDetailVO ) form ).setSelectedIds( "" );
         ( ( AnnualLeaveRuleDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
