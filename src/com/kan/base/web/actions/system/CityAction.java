package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.CityVO;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CityService;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.service.inf.system.ProvinceService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class CityAction extends BaseAction
{

   /**  
    * List city options by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
   */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ȡʡ��Id�ͳ���Id
         final String provinceId = request.getParameter( "provinceId" );
         final String cityId = request.getParameter( "cityId" );

         // ʵ����CityMappingVOs
         List< MappingVO > cityMappingVOs = new ArrayList< MappingVO >();
         // ��ӡ���ѡ��ѡ��
         cityMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ���ProvinceId����
         if ( provinceId != null )
         {
            // �ӳ����л�ȡ��ǰʡ�ݶ�Ӧ��CityMappingVOs
            cityMappingVOs = KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( cityMappingVOs, "cityId", cityId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "" );
   }

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final CityVO cityVO = ( CityVO ) form;

         // �����Action��ɾ���б�
         if ( cityVO.getSubAction() != null && cityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ��ʼ��Service�ӿ�
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         final CityService cityService = ( CityService ) getService( "cityService" );

         // ��ò��� provinceId�����������Ҫ֪�����ĸ�ʡ�ݵ�
         final String provinceId = KANUtil.decodeStringFromAjax( request.getParameter( "provinceId" ) );

         // ���ҵ���ǰ�����б��Ӧ��ʡ�ݶ���
         final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );
         // �ҵ����ҵ��������֣�����ǰ��Navigation��ʾ
         final CountryVO countryVO = ( ( CountryService ) getService( "countryService" ) ).getCountryVOByCountryId( Integer.valueOf( provinceVO.getCountryId() ) );
         //������ҵ�����
         request.setAttribute( "countryNameZH", countryVO.getCountryNameZH() );
         // ��ʼ��ProvinceVO����  
         provinceVO.reset( null, request );
         request.setAttribute( "provinceForm", provinceVO );

         //�˴�Ϊ�����б�
         PagedListHolder cityHolder = new PagedListHolder();
         cityHolder.setPage( page );
         cityVO.setProvinceId( provinceId );
         cityHolder.setObject( cityVO );
         cityHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         cityService.getCityVOByCondition( cityHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( cityHolder, request );

         // Holder��д��Request����
         request.setAttribute( "cityHolder", cityHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listCityTable" );
         }

         // New CityVO��ʱ�򣬳�ʼ��StatusΪTrue
         ( ( CityVO ) form ).setStatus( CityVO.TRUE );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listCity" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final CityService cityService = ( CityService ) getService( "cityService" );
            // ���ActionForm
            final CityVO cityVO = ( CityVO ) form;
            // ��ȡʡ�ݵ�ID
            final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "GBK" ) );

            cityVO.setProvinceId( provinceId );
            cityVO.setCreateBy( getUserId( request, response ) );
            cityVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            cityService.insertCity( cityVO );

            // ���Form����
            ( ( CityVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final CityService cityService = ( CityService ) getService( "cityService" );
         // ��õ�ǰ����
         final String cityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "cityId" ), "GBK" ) );
         // ��õ�ǰ���ж�Ӧ��ʡ��
         final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "GBK" ) );

         //�ҵ��ó��е�ʡ��
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );
         //����request����
         request.setAttribute( "provinceVO", provinceVO );

         //�ҵ��ó��еĹ���
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( provinceVO.getCountryId() ) );
         //������ҵ�����
         request.setAttribute( "countryNameZH", countryVO.getCountryNameZH() );

         final CityVO cityVO = cityService.getCityVOByCityId( Integer.valueOf( cityId ) );
         cityVO.reset( null, request );
         request.setAttribute( "cityForm", cityVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageCity" );
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final CityService cityService = ( CityService ) getService( "cityService" );
            // ��õ�ǰ����
            final String cityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "cityId" ), "GBK" ) );
            // ���������Ӧ����
            final CityVO cityVO = cityService.getCityVOByCityId( Integer.valueOf( cityId ) );

            // װ�ؽ��洫ֵ
            cityVO.update( ( CityVO ) form );

            // ��ȡ��¼�û�
            cityVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            cityService.updateCity( cityVO );

            // ���Form����
            ( ( CityVO ) form ).reset();
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
         final CityService cityService = ( CityService ) getService( "cityService" );
         // ��ʼ��Action Form
         final CityVO cityVO = ( CityVO ) form;

         // ����ѡ�е�ID
         if ( cityVO.getSubAction() != null && cityVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // �ָ�
            for ( String selectedId : cityVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               cityVO.setCityId( selectedId );
               cityVO.setModifyBy( getUserId( request, response ) );
               cityService.deleteCity( cityVO );
            }
         }
         // ���Selected IDs����Action
         cityVO.setSelectedIds( "" );
         cityVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
