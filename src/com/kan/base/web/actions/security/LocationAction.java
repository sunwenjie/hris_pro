package com.kan.base.web.actions.security;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.LocationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.LocationService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class LocationAction extends BaseAction
{
   public static String accessAction = "HRO_SYS_LOCATION";

   /**
    * List location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // ���Action Form
         final LocationVO locationVO = ( LocationVO ) form;

         // �����Action��ɾ���û��б�
         if ( locationVO.getSubAction() != null && locationVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( locationVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder locationHolder = new PagedListHolder();

         // ���뵱ǰҳ
         locationHolder.setPage( page );
         // ���뵱ǰֵ����
         locationHolder.setObject( locationVO );
         // ����ҳ���¼����
         locationHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         locationService.getLocationVOsByCondition( locationHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( locationHolder, request );
         // Holder��д��Request����
         request.setAttribute( "locationHolder", locationHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax��������ת
            return mapping.findForward( "listLocationTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listLocation" );
   }

   /**
    * to_objectNew
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( LocationVO ) form ).setStatus( LocationVO.TRUE );
      ( ( LocationVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageLocation" );
   }

   /**
    * Add location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final LocationService locationService = ( LocationService ) getService( "secLocationService" );

            // ���ActionForm
            final LocationVO locationVO = ( LocationVO ) form;
            // ��ȡ��¼�û�

            locationVO.setAccountId( getAccountId( request, response ) );
            locationVO.setCreateBy( getUserId( request, response ) );
            locationVO.setModifyBy( getUserId( request, response ) );

            final String positionId = request.getParameter( "positionId" );
            // �½�����
            locationService.insertLocation( locationVO, positionId );

            // ��ʼ�������־ö���
            constantsInit( "initLocation", getAccountId( request, response ) );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, locationVO, Operate.ADD, locationVO.getLocationId(), null );
         }
         else
         {
            // ���Form
            ( ( LocationVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );
         // ��õ�ǰ����
         String locationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( locationId ) == null )
         {
            locationId = ( ( LocationVO ) form ).getLocationId();
         }

         // ���������Ӧ����
         final LocationVO locationVO = locationService.getLocationVOByLocationId( locationId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         locationVO.reset( null, request );

         // ���City Id�������Province Id
         if ( locationVO.getCityId() != null && !locationVO.getCityId().trim().equals( "" ) && !locationVO.getCityId().trim().equals( "0" ) )
         {
            locationVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( locationVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
         }

         locationVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "locationForm", locationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageLocation" );
   }

   /**
    * Modify location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final LocationService locationService = ( LocationService ) getService( "secLocationService" );
            // ��õ�ǰ����
            final String locationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���LocationVO
            final LocationVO locationVO = locationService.getLocationVOByLocationId( locationId );
            // ��ȡ��¼�û�
            locationVO.update( ( LocationVO ) form );
            locationVO.setModifyBy( getUserId( request, response ) );
            final String positionId = request.getParameter( "positionId" );
            // �޸Ķ���
            locationService.updateLocation( locationVO, positionId );

            // ��ʼ�������־ö���
            constantsInit( "initLocation", getAccountId( request, response ) );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, locationVO, Operate.MODIFY, locationVO.getLocationId(), null );
         }

         // ���Form����
         ( ( LocationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete location
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         final LocationVO locationVO = new LocationVO();
         // ��õ�ǰ����
         String locationId = request.getParameter( "locationId" );

         // ɾ��������Ӧ����
         locationVO.setLocationId( locationId );
         locationVO.setModifyBy( getUserId( request, response ) );
         // ɾ������
         locationService.deleteLocation( locationVO );

         insertlog( request, locationVO, Operate.DELETE, locationId, null );

         // ��ʼ�������־ö���
         constantsInit( "initLocation", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete location list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final LocationService locationService = ( LocationService ) getService( "secLocationService" );

         // ���Action Form
         LocationVO locationVO = ( LocationVO ) form;
         // ����ѡ�е�ID
         if ( locationVO.getSelectedIds() != null && !locationVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : locationVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               locationVO.setLocationId( selectedId );
               locationVO.setModifyBy( getUserId( request, response ) );

               locationService.deleteLocation( locationVO );
            }

            insertlog( request, locationVO, Operate.DELETE, null, locationVO.getSelectedIds() );
         }

         // ��ʼ�������־ö���
         constantsInit( "initLocation", getAccountId( request, response ) );

         // ���Selected IDs����Action
         locationVO.setSelectedIds( "" );
         locationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
