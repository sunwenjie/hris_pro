package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.BankVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BankService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class BankAction extends BaseAction
{

   public static final String accessAction = "HRO_MGT_BANK";

   @Override
   // Reviewed by Kevin Jin at 2013-12-29
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final BankService bankService = ( BankService ) getService( "bankService" );

         // ���Action Form
         final BankVO bankVO = ( BankVO ) form;

         // ��Ҫ���õ�ǰ�û�AccountId
         bankVO.setAccountId( getAccountId( request, response ) );

         // ����ɾ������
         if ( bankVO.getSubAction() != null && bankVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( bankVO );
         }

         // ���û��ָ��������Ĭ�ϰ�BankId����
         if ( KANUtil.filterEmpty( bankVO.getSortColumn() ) == null )
         {
            bankVO.setSortColumn( "bankId" );
            bankVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder bankHolder = new PagedListHolder();
         // ���뵱ǰҳ
         bankHolder.setPage( page );
         // ���뵱ǰֵ����
         bankHolder.setObject( bankVO );
         // ����ҳ���¼����
         bankHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         bankService.getBankVOsByCondition( bankHolder, true );

         if ( bankHolder != null && bankHolder.getHolderSize() > 0 )
         {
            for ( Object bankVOObject : bankHolder.getSource() )
            {
               final BankVO tempBankVO = ( BankVO ) bankVOObject;

               // ϵͳ��Ŀ
               if ( tempBankVO.getAccountId().equals( "1" ) )
               {
                  tempBankVO.reset( null, request );
                  tempBankVO.setAccountId( "1" );
               }
               else
               {
                  tempBankVO.reset( null, request );
               }
            }
         }

         // Holder��д��Request����
         request.setAttribute( "bankHolder", bankHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Bank JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listBankTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listBank" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( BankVO ) form ).setStatus( BankVO.TRUE );
      ( ( BankVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageBank" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰFORM
         final BankVO bankVO = ( BankVO ) form;

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final BankService bankService = ( BankService ) getService( "bankService" );
            bankVO.setCreateBy( getUserId( request, response ) );
            bankVO.setModifyBy( getUserId( request, response ) );
            bankVO.setAccountId( getAccountId( request, response ) );
            bankService.insertBank( bankVO );

            // ��ʼ�������־ö���
            constantsInit( "initBank", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, bankVO, Operate.ADD, bankVO.getBankId(), null );
         }

         // ���Form
         ( ( BankVO ) form ).reset();

         return to_objectModify( mapping, bankVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final BankService bankService = ( BankService ) getService( "bankService" );

         String bankId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( bankId ) != null )
         {
            bankId = Cryptogram.decodeString( URLDecoder.decode( bankId, "UTF-8" ) );
         }
         else
         {
            bankId = ( ( BankVO ) form ).getBankId();
         }
         // ���BankVO����
         final BankVO bankVO = bankService.getBankVOByBankId( bankId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         bankVO.reset( null, request );
         // ���City Id�������Province Id
         if ( bankVO.getCityId() != null && !bankVO.getCityId().trim().equals( "" ) && !bankVO.getCityId().trim().equals( "0" ) )
         {
            bankVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( bankVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            bankVO.setCityIdTemp( bankVO.getCityId() );
         }
         String accountId = "";
         // ϵͳ��Ŀ
         if ( bankVO.getAccountId().equals( "1" ) )
         {
            accountId = "1";
         }
         // ����Add��Update
         bankVO.setSubAction( VIEW_OBJECT );
         bankVO.reset( null, request );
         if ( !accountId.equals( "" ) )
         {
            bankVO.setAccountId( accountId );
         }
         // ��BankVO����request����
         request.setAttribute( "bankForm", bankVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageBank" );
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
            final BankService bankService = ( BankService ) getService( "bankService" );
            // ������ȡ�����
            final String bankId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡBankVO����
            final BankVO bankVO = bankService.getBankVOByBankId( bankId );
            // װ�ؽ��洫ֵ
            bankVO.update( ( BankVO ) form );
            // ��ȡ��¼�û�
            bankVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            bankService.updateBank( bankVO );
            // ��ʼ�������־ö���
            constantsInit( "initBank", getAccountId( request, response ) );
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, bankVO, Operate.MODIFY, bankVO.getBankId(), null );
         }
         // ���Form
         ( ( BankVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
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
         final BankService bankService = ( BankService ) getService( "bankService" );
         // ���Action Form
         final BankVO bankVO = ( BankVO ) form;
         // ����ѡ�е�ID
         if ( bankVO.getSelectedIds() != null && !bankVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : bankVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               bankVO.setBankId( selectedId );
               bankVO.setAccountId( getAccountId( request, response ) );
               bankVO.setModifyBy( getUserId( request, response ) );
               bankService.deleteBank( bankVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initBank", getAccountId( request, response ) );

            insertlog( request, bankVO, Operate.DELETE, null, bankVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         bankVO.setSelectedIds( "" );
         bankVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
