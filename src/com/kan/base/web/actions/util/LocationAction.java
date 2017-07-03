package com.kan.base.web.actions.util;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.CityVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.LocationRender;

public class LocationAction extends BaseAction
{
   public ActionForward list_cities_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      final String provinceId = request.getParameter( "provinceId" );
      final String id_name = request.getParameter( "id_name" );

      if ( provinceId != null && !provinceId.trim().equals( "" ) )
      {
         try
         {
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "GBK" );
            final PrintWriter out = response.getWriter();
            if ( !provinceId.trim().equals( "0" ) )
            {
               final List< MappingVO > cities = KANConstants.LOCATION_DTO.getCities( provinceId, request.getLocale().getLanguage() );
               final CityVO cityVO = new CityVO();
               cityVO.reset( mapping, request );
               cities.add( 0, cityVO.getEmptyMappingVO() );
              
               // Renderµ÷ÓÃ 
               out.println( LocationRender.getCities( request.getLocale(), cities, id_name ) );
            }
            else
            {
               out.println( "" );
            }
            out.flush();
            out.close();
         }
         catch ( final Exception e )
         {
            throw new KANException( e );
         }
      }
      return mapping.findForward( "" );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
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
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }
}
