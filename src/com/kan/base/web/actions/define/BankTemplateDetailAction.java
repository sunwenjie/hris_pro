package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateDetailService;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankTemplateDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_TEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );

         // ���Action Form
         final BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ����SubAction
         dealSubAction( bankTemplateDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = bankTemplateDetailVO.getTemplateHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( headerId );

         // ˢ�¹��ʻ�
         bankTemplateHeaderVO.reset( null, request );
         // ����SubAction
         bankTemplateHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "bankTemplateHeaderForm", bankTemplateHeaderVO );

         // ����templateHeaderId
         bankTemplateDetailVO.setTemplateHeaderId( headerId );

         // ���û��ָ��������Ĭ�ϰ� �б��ֶ�˳������
         if ( bankTemplateDetailVO.getSortColumn() == null || bankTemplateDetailVO.getSortColumn().isEmpty() )
         {
            bankTemplateDetailVO.setSortColumn( "columnIndex" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder bankTemplateDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         bankTemplateDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         bankTemplateDetailHolder.setObject( bankTemplateDetailVO );
         // ����ҳ���¼����
         bankTemplateDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         bankTemplateDetailService.getBankTemplateDetailVOsByCondition( bankTemplateDetailHolder, true );

         // ����BankTemplateDetailForm��ʼֵ
         bankTemplateDetailVO.setColumnIndex( "0" );
         bankTemplateDetailVO.setFontSize( "13" );
         bankTemplateDetailVO.setAlign( BankTemplateDetailVO.TRUE );
         bankTemplateDetailVO.setStatus( BankTemplateDetailVO.TRUE );

         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( bankTemplateDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "bankTemplateDetailHolder", bankTemplateDetailHolder );

         // Ajax Table���ã�ֱ�Ӵ���Detail JSP
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBankTemplateDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listBankTemplateDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service �ӿ�
            final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

            // ���templateHeaderId
            final String templateHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateHeaderId" ), "UTF-8" ) );

            // ��õ�ǰForm
            final BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;

            // ��ʼ��BankTemplateDetailVO����
            bankTemplateDetailVO.setTemplateHeaderId( templateHeaderId );
            bankTemplateDetailVO.setCreateBy( getUserId( request, response ) );
            bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            bankTemplateDetailVO.setAccountId( getAccountId( request, response ) );

            // ���BankTemplateDetailVO
            bankTemplateDetailService.insertBankTemplateDetail( bankTemplateDetailVO );

            // ���¼��س����е�BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, bankTemplateDetailVO, Operate.ADD, bankTemplateDetailVO.getTemplateDetailId(), null );
         }

         // ���form
         ( ( BankTemplateDetailVO ) form ).reset();
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
         // ��ȡtemplateDetailId
         final String templateDetailId = KANUtil.decodeString( request.getParameter( "id" ) );

         // ��ʼ��Service�ӿ�
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // ��ȡBankTemplateDetailVO
         final BankTemplateDetailVO bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( templateDetailId );

         // ��ȡBankTemplateHeaderVO
         final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( bankTemplateDetailVO.getTemplateHeaderId() );

         // ���ʻ���ֵ
         bankTemplateDetailVO.reset( null, request );
         // ����SubAction
         bankTemplateDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "bankTemplateHeaderForm", bankTemplateHeaderVO );
         request.setAttribute( "bankTemplateDetailForm", bankTemplateDetailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax Form���ã�ֱ�Ӵ���Form JSP
      return mapping.findForward( "manageBankTemplateDetailForm" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ������ȡ - �����
            final String templateDetailId = KANUtil.decodeString( request.getParameter( "templateDetailId" ) );

            // ��ʼ�� Service�ӿ�
            final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

            // ��ȡBankTemplateDetailVO
            final BankTemplateDetailVO bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( templateDetailId );

            // װ�ؽ��洫ֵ
            bankTemplateDetailVO.update( ( BankTemplateDetailVO ) form );

            // �޸�BankTemplateDetailVO
            bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
            bankTemplateDetailService.updateBankTemplateDetail( bankTemplateDetailVO );

            // ���¼��س����е�BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, bankTemplateDetailVO, Operate.MODIFY, bankTemplateDetailVO.getTemplateDetailId(), null );
         }

         // ���Form
         ( ( BankTemplateDetailVO ) form ).reset();
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
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final BankTemplateDetailService bankTemplateDetailService = ( BankTemplateDetailService ) getService( "bankTemplateDetailService" );

         // ���Action Form
         BankTemplateDetailVO bankTemplateDetailVO = ( BankTemplateDetailVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( bankTemplateDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, bankTemplateDetailVO, Operate.DELETE, null, bankTemplateDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : bankTemplateDetailVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               bankTemplateDetailVO = bankTemplateDetailService.getBankTemplateDetailVOByTemplateDetailId( selectedId );
               bankTemplateDetailVO.setModifyBy( getUserId( request, response ) );
               bankTemplateDetailVO.setModifyDate( new Date() );
               bankTemplateDetailService.deleteBankTemplateDetail( bankTemplateDetailVO );
            }
         }

         // ���¼��س����е�BankTemplateHeader
         constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
         ( ( BankTemplateDetailVO ) form ).setSelectedIds( "" );
         ( ( BankTemplateDetailVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
