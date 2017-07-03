package com.kan.hro.web.actions.biz.performance;

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
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.performance.BudgetSettingDetailVO;
import com.kan.hro.domain.biz.performance.BudgetSettingHeaderVO;
import com.kan.hro.service.inf.biz.performance.BudgetSettingDetailService;
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;

public class BudgetSettingDetailAction extends BaseAction
{

   public static final String ASSESS_ACTION = "HRO_PM_BUDGET_SETTING";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         //��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
         // ����ɾ������
         if ( budgetSettingDetailVO.getSubAction() != null && budgetSettingDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );

         // ��õ�������  
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = budgetSettingDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ��ȡBudgetSettingDetailVO
         final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( headerId );
         budgetSettingHeaderVO.setSubAction( VIEW_OBJECT );
         budgetSettingHeaderVO.reset( null, request );
         request.setAttribute( "budgetSettingHeaderForm", budgetSettingHeaderVO );
         budgetSettingDetailVO.setHeaderId( headerId );

         //�˴���ҳ����
         final PagedListHolder budgetSettingDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         budgetSettingDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         budgetSettingDetailHolder.setObject( budgetSettingDetailVO );
         // ����ҳ���¼����
         budgetSettingDetailHolder.setPageSize( listPageSize_large );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         budgetSettingDetailService.getBudgetSettingDetailVOsByCondition( budgetSettingDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( budgetSettingDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "budgetSettingDetailHolder", budgetSettingDetailHolder );

         // BudgetSettingDetailд��Request����
         request.setAttribute( "budgetSettingDetailForm", budgetSettingDetailVO );

         // �����AJAX���ã���ֱ�Ӵ�ֵ��table JSPҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBudgetSettingDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBudgetSettingDetail" );
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
            // ��ʼ��Service �ӿ�
            final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
            // ���headerId
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��õ�ǰform
            final BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
            // ��ʼ��BudgetSettingDetailVO����
            budgetSettingDetailVO.setHeaderId( headerId );
            budgetSettingDetailVO.setCreateBy( getUserId( request, response ) );
            budgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
            budgetSettingDetailVO.setAccountId( getAccountId( request, response ) );
            budgetSettingDetailService.insertBudgetSettingDetail( budgetSettingDetailVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, budgetSettingDetailVO, Operate.ADD, budgetSettingDetailVO.getDetailId(), null );
         }

         // ���Action Form
         ( ( BudgetSettingDetailVO ) form ).setSubAction( "" );
         ( ( BudgetSettingDetailVO ) form ).reset();
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

   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // ��ȡ���� - �����
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // ��ȡBudgetSettingDetailVO����
         final BudgetSettingDetailVO budgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( detailId );
         // ��ȡBudgetSettingHeaderVO����
         final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( budgetSettingDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         budgetSettingDetailVO.reset( null, request );
         // ����SubAction
         budgetSettingDetailVO.setSubAction( VIEW_OBJECT );
         // д��Request
         request.setAttribute( "budgetSettingHeaderForm", budgetSettingHeaderVO );
         request.setAttribute( "budgetSettingDetailForm", budgetSettingDetailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // AJAX������תFORMҳ��
      return mapping.findForward( "manageBudgetSettingDetail" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
            // ��ȡ���� - �����
            final String detailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "detailId" ), "UTF-8" ) );
            // ���BudgetSettingDetailVO����
            final BudgetSettingDetailVO budgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            budgetSettingDetailVO.update( ( BudgetSettingDetailVO ) form );
            // ��ȡ��¼�û�
            budgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            budgetSettingDetailService.updateBudgetSettingDetail( budgetSettingDetailVO );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, budgetSettingDetailVO, Operate.MODIFY, budgetSettingDetailVO.getDetailId(), null );
         }

         // ���Form
         ( ( BudgetSettingDetailVO ) form ).reset();
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
         final BudgetSettingDetailService budgetSettingDetailService = ( BudgetSettingDetailService ) getService( "budgetSettingDetailService" );
         // ��õ�ǰform
         BudgetSettingDetailVO budgetSettingDetailVO = ( BudgetSettingDetailVO ) form;
         // ����ѡ�е�ID
         if ( budgetSettingDetailVO.getSelectedIds() != null && !budgetSettingDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : budgetSettingDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               final BudgetSettingDetailVO tempBudgetSettingDetailVO = budgetSettingDetailService.getBudgetSettingDetailVOByDetailId( selectedId );
               tempBudgetSettingDetailVO.setModifyBy( getUserId( request, response ) );
               tempBudgetSettingDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               budgetSettingDetailService.deleteBudgetSettingDetail( tempBudgetSettingDetailVO );
            }

            insertlog( request, budgetSettingDetailVO, Operate.DELETE, null, budgetSettingDetailVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         budgetSettingDetailVO.setSelectedIds( "" );
         budgetSettingDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
