package com.kan.base.web.renders.util;

import java.util.List;
import java.util.Locale;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;

public class LocationRender
{
   public static String getCities( final Locale locale, final List< MappingVO > cities, final String id_name ) throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      String inputName = "cityId";

      if ( id_name != null && !id_name.trim().equals( "" ) )
      {
         inputName = id_name;
      }

      rs.append( "<select id=\"" + inputName + "\" name=\"" + inputName + "\" class=\"" + inputName + "\" onchange=\"" + inputName + "Change();\" >" );
      for ( MappingVO mappingVO : cities )
      {
         rs.append( "<option value=\"" + mappingVO.getMappingId() + "\"> " + mappingVO.getMappingValue() + " </option>" );
      }
      rs.append( "</select>" );
      return rs.toString();
   }
}
