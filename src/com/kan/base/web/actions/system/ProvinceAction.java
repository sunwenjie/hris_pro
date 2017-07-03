package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.service.inf.system.ProvinceService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ProvinceAction extends BaseAction
{

   /**  
    * List province options by Ajax
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
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ȡʡ��Id
         final String provinceId = request.getParameter( "provinceId" );
         
         // �ӳ����л�ȡ��ǰ���Ҷ�Ӧ��Province MappingVOs
         final List< MappingVO > provinceMappingVOs = KANConstants.LOCATION_DTO.getProvinces( request.getLocale().getLanguage() );
         // ��ӡ���ѡ��ѡ��
         provinceMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // Send to client
         out.println( KANUtil.getOptionHTML( provinceMappingVOs, "provinceId", provinceId ) );
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
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final ProvinceVO provinceVO = ( ProvinceVO ) form;

         // �����Action��ɾ���б�
         if ( provinceVO.getSubAction() != null && provinceVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // ��ʼ��Service�ӿ�
         final CountryService countryService = ( CountryService ) getService( "countryService" );
         // ��Ҫ���ProvinceVO�б����Ի����ʼ�� ProvinceService
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );

         // ��ò��� countryId
         final String countryId = KANUtil.decodeStringFromAjax( request.getParameter( "countryId" ) );
         // ����CountryService���CountryVO
         final CountryVO countryVO = countryService.getCountryVOByCountryId( Integer.valueOf( countryId ) );
         // ��ʼ��CountryVO����         
         countryVO.reset( null, request );
         request.setAttribute( "countryForm", countryVO );

         // ����ProvinceService���ProvinceVOList
         // �˴���ҳ����
         PagedListHolder provinceHolder = new PagedListHolder();
         provinceHolder.setPage( page );
         provinceVO.setCountryId( countryId );
         provinceHolder.setObject( provinceVO );
         provinceHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         provinceService.getProvinceVOByCondition( provinceHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( provinceHolder, request );

         // Holder��д��Request����
         request.setAttribute( "provinceHolder", provinceHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            return mapping.findForward( "listProvinceTable" );
         }

         // New ProvinceVO��ʱ�򣬳�ʼ��StatusΪTrue
         ( ( ProvinceVO ) form ).setStatus( ProvinceVO.TRUE );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listProvince" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
            // ��ȡ���ҵ�ID
            final String countryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "countryId" ), "UTF-8" ) );

            // ���ActionForm
            final ProvinceVO provinceVO = ( ProvinceVO ) form;
            provinceVO.setCountryId( countryId );
            provinceVO.setCreateBy( getUserId( request, response ) );
            provinceVO.setModifyBy( getUserId( request, response ) );

            // �½�����
            provinceService.insertProvince( provinceVO );

            // ���Form����
            ( ( ProvinceVO ) form ).reset();
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
            // ��õ�ǰ����
            final String provinceId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "provinceId" ), "UTF-8" ) );
            // ���������Ӧ����
            final ProvinceVO provinceVO = provinceService.getProvinceVOByProvinceId( Integer.valueOf( provinceId ) );

            // װ�ؽ��洫ֵ
            provinceVO.update( ( ProvinceVO ) form );

            // ��ȡ��¼�û�
            provinceVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            provinceService.updateProvince( provinceVO );

            // ���Form����
            ( ( ProvinceVO ) form ).reset();
         }

         // �����ɹ�����Province List����
         return new CityAction().list_object( mapping, new CityVO(), request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

      try
      {
         // ��ʼ��Service�ӿ�
         final ProvinceService provinceService = ( ProvinceService ) getService( "provinceService" );
         // ��ʼ��Action Form
         final ProvinceVO provinceVO = ( ProvinceVO ) form;
         // ����ѡ�е�ID
         if ( provinceVO.getSelectedIds() != null && !provinceVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : provinceVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               provinceVO.setProvinceId( selectedId );
               provinceVO.setModifyBy( getUserId( request, response ) );
               provinceService.deleteProvince( provinceVO );
            }
         }
         // ���Selected IDs����Action
         provinceVO.setSelectedIds( "" );
         provinceVO.setSubAction( "" );
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
