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
import com.kan.hro.service.inf.biz.performance.BudgetSettingHeaderService;

public class BudgetSettingHeaderAction extends BaseAction
{

   public static final String ASSESS_ACTION = "HRO_PM_BUDGET_SETTING";
   
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final BudgetSettingHeaderVO budgetHeaderVO = ( BudgetSettingHeaderVO ) form;
         // ����subAction
         dealSubAction( budgetHeaderVO, mapping, form, request, response );
         // ��ʼ��Service�ӿ�
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder budgetSettingHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         budgetSettingHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         budgetSettingHeaderHolder.setObject( budgetHeaderVO );
         // ����ҳ���¼����
         budgetSettingHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         budgetSettingHeaderService.getBudgetSettingHeaderVOsByCondition( budgetSettingHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( budgetSettingHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "budgetSettingHeaderHolder", budgetSettingHeaderHolder );
         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBudgetSettingHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listBudgetSettingHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      // ����Sub Action
      ( ( BudgetSettingHeaderVO ) form ).setStatus( BudgetSettingHeaderVO.TRUE );
      ( ( BudgetSettingHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      return mapping.findForward( "manageBudgetSettingHeader" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��BudgetSettingDetailVO
         final BudgetSettingDetailVO budgetSettingDetailVO = new BudgetSettingDetailVO();
         budgetSettingDetailVO.reset( null, request );
         budgetSettingDetailVO.setStatus( "1" );
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
            // ��õ�ǰFORM
            final BudgetSettingHeaderVO budgetSettingHeaderVO = ( BudgetSettingHeaderVO ) form;

            budgetSettingHeaderVO.setCreateBy( getUserId( request, response ) );
            budgetSettingHeaderVO.setModifyBy( getUserId( request, response ) );
            budgetSettingHeaderVO.setAccountId( getAccountId( request, response ) );
            budgetSettingHeaderService.insertBudgetSettingHeader( budgetSettingHeaderVO );
            budgetSettingDetailVO.setHeaderId( budgetSettingHeaderVO.getHeaderId() );

            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, budgetSettingHeaderVO, Operate.ADD, budgetSettingHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���form
            ( ( BudgetSettingHeaderVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }

         return new BudgetSettingDetailAction().list_object( mapping, budgetSettingDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
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
            final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
            // ��ȡ���� - �����
            final String budgetSettingHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���BudgetSettingHeaderVO����
            final BudgetSettingHeaderVO budgetSettingHeaderVO = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( budgetSettingHeaderId );
            // װ�ؽ��洫ֵ
            budgetSettingHeaderVO.update( ( BudgetSettingHeaderVO ) form );
            // ��ȡ��¼�û�
            budgetSettingHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            budgetSettingHeaderService.updateBudgetSettingHeader( budgetSettingHeaderVO );
            // ���ر༭�ɹ��ı�� 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, budgetSettingHeaderVO, Operate.MODIFY, budgetSettingHeaderVO.getHeaderId(), null );
         }

         // ���Action Form
         ( ( BudgetSettingHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      BudgetSettingDetailVO forwardForm = new BudgetSettingDetailVO();
      forwardForm.reset( null, request );
      return new BudgetSettingDetailAction().list_object( mapping, forwardForm, request, response );
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
         final BudgetSettingHeaderService budgetSettingHeaderService = ( BudgetSettingHeaderService ) getService( "budgetSettingHeaderService" );
         // ���Action Form
         final BudgetSettingHeaderVO budgetSettingHeaderVO = ( BudgetSettingHeaderVO ) form;
         // ����ѡ�е�ID
         if ( budgetSettingHeaderVO.getSelectedIds() != null && !budgetSettingHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : budgetSettingHeaderVO.getSelectedIds().split( "," ) )
            {
               // ����ID��ȡ��Ӧ��budgetSettingHeaderVO
               final BudgetSettingHeaderVO budgetSettingHeaderVOForDel = budgetSettingHeaderService.getBudgetSettingHeaderVOByHeaderId( selectedId );
               budgetSettingHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               budgetSettingHeaderVOForDel.setModifyDate( new Date() );
               budgetSettingHeaderService.deleteBudgetSettingHeader( budgetSettingHeaderVOForDel );
            }

            insertlog( request, budgetSettingHeaderVO, Operate.DELETE, null, budgetSettingHeaderVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         budgetSettingHeaderVO.setSelectedIds( "" );
         budgetSettingHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
