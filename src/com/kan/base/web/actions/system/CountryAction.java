package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class CountryAction extends BaseAction
{

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final CountryVO countryVO = ( CountryVO ) form;
         // ��ʼ��Service�ӿ�
         final CountryService countryService = ( CountryService ) getService( "countryService" );

         // �����Action��ɾ���б�
         if ( countryVO.getSubAction() != null && countryVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder countryHolder = new PagedListHolder();

         // ���뵱ǰҳ
         countryHolder.setPage( page );
         // ���뵱ǰֵ����
         countryHolder.setObject( countryVO );
         // ����ҳ���¼����
         countryHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         countryHolder = countryService.getCountryVOByCondition( countryHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( countryHolder, request );

         // Holder��д��Request����
         request.setAttribute( "countryHolder", countryHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listCountryTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listCountry" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( CountryVO ) form ).setStatus( AccountVO.TRUE );
      ( ( CountryVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageCountry" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final CountryService countryService = ( CountryService ) getService( "countryService" );
            // ���ActionForm
            final CountryVO countryVO = ( CountryVO ) form;

            countryVO.setCreateBy( getUserId( request, response ) );
            countryVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            countryService.insertCountry( countryVO );

            // ���Form����
            ( ( CountryVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         // ���Action Form
         final CountryVO countryVO = ( CountryVO ) form;

         // ����ѡ�е�ID
         if ( countryVO.getSelectedIds() != null && !countryVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : countryVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               countryVO.setCountryId( selectedId );
               countryVO.setModifyBy( getUserId( request, response ) );
               countryService.deleteCountry( countryVO );
            }
         }

         // ���Selected IDs����Action
         countryVO.setSelectedIds( "" );
         countryVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            final CountryService countryService = ( CountryService ) getService( "countryService" );
            final String countryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "countryId" ), "GBK" ) );
            final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( countryId ) );

            // װ�ؽ��洫ֵ
            countryVO.update( ( CountryVO ) form );

            // ��ȡ��¼�û�
            countryVO.setModifyBy( getUserId( request, response ) );
            countryService.updateCountry( countryVO );

            // ���Form����
            ( ( CountryVO ) form ).reset();
         }

         // �����ɹ�����Province List����
         return new ProvinceAction().list_object( mapping, new ProvinceVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

}
