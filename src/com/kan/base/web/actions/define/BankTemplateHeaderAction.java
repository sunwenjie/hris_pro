package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankTemplateHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_SALARY_TEMPLATE";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ���Action Form
         final BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;

         // �����Action��ɾ��
         if ( bankTemplateHeaderVO.getSubAction() != null && bankTemplateHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( bankTemplateHeaderVO );
         }

         // ��ʼ��Service�ӿ�
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder bankTemplateHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         bankTemplateHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         bankTemplateHeaderHolder.setObject( bankTemplateHeaderVO );
         // ����ҳ���¼����
         bankTemplateHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         bankTemplateHeaderService.getBankTemplateHeaderVOsByCondition( bankTemplateHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( bankTemplateHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "bankTemplateHeaderHolder", bankTemplateHeaderHolder );

         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBankTemplateHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listBankTemplateHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ��ȡ��ǰaccountId - client��BankTemplate Mapping��ʽ
      final List< MappingVO > bankTemplateMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBankTemplate( getLocale( request ).getLanguage(), KANUtil.filterEmpty( getCorpId( request, response ) ) );

      // ���bankId�Ѿ����ڣ����Ƴ���
      if ( ( ( BankTemplateHeaderVO ) form ).getBanks() != null && ( ( BankTemplateHeaderVO ) form ).getBanks().size() > 0 && bankTemplateMappingVOs != null
            && bankTemplateMappingVOs.size() > 0 )
      {
         for ( int i = ( ( BankTemplateHeaderVO ) form ).getBanks().size() - 1; i > 0; i-- )
         {
            for ( MappingVO bankTemplateMappingVO : bankTemplateMappingVOs )
            {
               final BankTemplateDTO bankTemplateDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBankTemplateDTOByTemplateHeaderId( bankTemplateMappingVO.getMappingId() );

               if ( bankTemplateDTO != null && bankTemplateDTO.getBankTemplateHeaderVO() != null && bankTemplateDTO.getBankTemplateHeaderVO().getBankId() != null
                     && ( ( BankTemplateHeaderVO ) form ).getBanks().get( i ).getMappingId().equals( bankTemplateDTO.getBankTemplateHeaderVO().getBankId() ) )
               {
                  ( ( BankTemplateHeaderVO ) form ).getBanks().remove( i );
                  break;
               }
            }
         }
      }

      // ���ó�ʼ���ֶ�
      ( ( BankTemplateHeaderVO ) form ).setStatus( BankTemplateHeaderVO.TRUE );
      ( ( BankTemplateHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageBankTemplateHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��BankTemplateDetailVO
         final BankTemplateDetailVO bankTemplateDetailVO = new BankTemplateDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

            // ��õ�ǰFORM
            final BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;
            // ��ȡ��¼�û����˻�
            bankTemplateHeaderVO.setCreateBy( getUserId( request, response ) );
            bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            bankTemplateHeaderVO.setAccountId( getAccountId( request, response ) );

            // ���BankTemplateHeaderVO
            bankTemplateHeaderService.insertBankTemplateHeader( bankTemplateHeaderVO );

            bankTemplateDetailVO.setTemplateHeaderId( bankTemplateHeaderVO.getTemplateHeaderId() );

            // ���¼��س����е�BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, bankTemplateHeaderVO, Operate.ADD, bankTemplateHeaderVO.getTemplateHeaderId(), null );
         }
         else
         {
            // ���Form
            ( ( BankTemplateHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
         // ��תList BankTemplateDetail����
         return new BankTemplateDetailAction().list_object( mapping, bankTemplateDetailVO, request, response );
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
            final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

            // �������
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���BankTemplateHeaderVO����
            final BankTemplateHeaderVO bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( headerId );

            // װ�ؽ��洫ֵ
            bankTemplateHeaderVO.update( ( BankTemplateHeaderVO ) form );
            // ��ȡ��¼�û�
            bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            bankTemplateHeaderService.updateBankTemplateHeader( bankTemplateHeaderVO );

            // ���¼��س����е�BankTemplateHeader
            constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, bankTemplateHeaderVO, Operate.MODIFY, bankTemplateHeaderVO.getTemplateHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תList BankTemplateDetail����
      return new BankTemplateDetailAction().list_object( mapping, new BankTemplateDetailVO(), request, response );
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
         final BankTemplateHeaderService bankTemplateHeaderService = ( BankTemplateHeaderService ) getService( "bankTemplateHeaderService" );

         // ���Action Form
         BankTemplateHeaderVO bankTemplateHeaderVO = ( BankTemplateHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( bankTemplateHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, bankTemplateHeaderVO, Operate.DELETE, null, bankTemplateHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : bankTemplateHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               bankTemplateHeaderVO = bankTemplateHeaderService.getBankTemplateHeaderVOByTemplateHeaderId( selectedId );
               bankTemplateHeaderVO.setModifyBy( getUserId( request, response ) );
               bankTemplateHeaderVO.setModifyDate( new Date() );
               bankTemplateHeaderService.deleteBankTemplateHeader( bankTemplateHeaderVO );
            }
         }

         // ���¼��س����е�BankTemplateHeader
         constantsInit( "initBankTemplateHeader", getAccountId( request, response ) );

         // ���Selected IDs����Action
         ( ( BankTemplateHeaderVO ) form ).setSelectedIds( "" );
         ( ( BankTemplateHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}
