package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ExchangeRateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ExchangeRateService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ExchangeRateAction extends BaseAction
{

   public final static String accessAction = "HRO_MGT_EXCHANGE_RATE";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );
         // ���Action Form
         final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
         // ��Ҫ���õ�ǰ�û�AccountId
         exchangeRateVO.setAccountId( getAccountId( request, response ) );
         // ����ɾ������
         if ( exchangeRateVO.getSubAction() != null && exchangeRateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder exchangeRateHolder = new PagedListHolder();
         // ���뵱ǰҳ
         exchangeRateHolder.setPage( page );
         // ���뵱ǰֵ����
         exchangeRateHolder.setObject( exchangeRateVO );
         // ����ҳ���¼����
         exchangeRateHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         exchangeRateService.getExchangeRateVOsByCondition( exchangeRateHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( exchangeRateHolder, request );
         // Holder��д��Request����
         request.setAttribute( "exchangeRateHolder", exchangeRateHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listExchangeRateTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listExchangeRate" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );
      // ����Sub Action
      ( ( ExchangeRateVO ) form ).setStatus( ExchangeRateVO.TRUE );
      ( ( ExchangeRateVO ) form ).setSubAction( CREATE_OBJECT );
      // ��ת���½�����  
      return mapping.findForward( "manageExchangeRate" );
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
            final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

            // ��õ�ǰFORM
            final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
            exchangeRateVO.setCreateBy( getUserId( request, response ) );
            exchangeRateVO.setModifyBy( getUserId( request, response ) );
            exchangeRateVO.setAccountId( getAccountId( request, response ) );
            exchangeRateService.insertExchangeRate( exchangeRateVO );

            // ��ʼ�������־ö���
            constantsInit( "initExchangeRate", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
            // ���Action Form
            ( ( ExchangeRateVO ) form ).reset();
         }
         else
         {
            // ���FORM
            ( ( ExchangeRateVO ) form ).reset();
            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ�� Service�ӿ�
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );
         // ������ȡ�����
         String exchangeRateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( exchangeRateId ) == null )
         {
            exchangeRateId = ( ( ExchangeRateVO ) form ).getExchangeRateId();
         }
         // ���ExchangeRateVO����
         ExchangeRateVO exchangeRateVO = exchangeRateService.getExchangeRateVOByExchangeRateId( exchangeRateId );
         exchangeRateVO.reset( null, request );
         // ����Add��Update
         exchangeRateVO.setSubAction( VIEW_OBJECT );
         // ��ExchangeRateVO����request����
         request.setAttribute( "exchangeRateForm", exchangeRateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageExchangeRate" );
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
            final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

            // ������ȡ�����
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // ��ȡExchangeRateVO����
            final ExchangeRateVO exchangeRateVO = exchangeRateService.getExchangeRateVOByExchangeRateId( entityId );
            // װ�ؽ��洫ֵ
            exchangeRateVO.update( ( ExchangeRateVO ) form );
            // ��ȡ��¼�û�
            exchangeRateVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            exchangeRateService.updateExchangeRate( exchangeRateVO );

            // ��ʼ�������־ö���
            constantsInit( "initExchangeRate", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Action Form
         ( ( ExchangeRateVO ) form ).reset();
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
         final ExchangeRateService exchangeRateService = ( ExchangeRateService ) getService( "exchangeRateService" );

         // ���Action Form
         final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) form;
         // ����ѡ�е�ID
         if ( exchangeRateVO.getSelectedIds() != null && !exchangeRateVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : exchangeRateVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               exchangeRateVO.setExchangeRateId( selectedId );
               exchangeRateVO.setModifyBy( getUserId( request, response ) );
               exchangeRateVO.setAccountId( getAccountId( request, response ) );
               exchangeRateService.deleteExchangeRate( exchangeRateVO );
            }

            // ��ʼ�������־ö���
            constantsInit( "initExchangeRate", getAccountId( request, response ) );
         }

         // ���Selected IDs����Action
         exchangeRateVO.setSelectedIds( "" );
         exchangeRateVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
