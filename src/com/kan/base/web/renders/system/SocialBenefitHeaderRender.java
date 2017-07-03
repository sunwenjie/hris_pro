package com.kan.base.web.renders.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.system.SocialBenefitHeaderVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SocialBenefitHeaderRender
{
   public static String getSocialBenbfitHeaderVOsByCityId( final HttpServletRequest request, final List< Object > socialBenefitHeaderVOs, final String headerId )
         throws KANException
   {
      final StringBuffer rs = new StringBuffer();
      rs.append( "<label>" + KANUtil.getProperty( request.getLocale(), "management.sb.solution.header.system" ) + "</label> " );
      rs.append( "<select name=\"sysHeaderId\" class=\"manageSocialBenefitSolutionHeader_sysHeaderId\" id=\"sysHeaderId\" onchange=\"sysHeaderIdChange();\">" );
      rs.append( "<option value=\"0\">" + KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() + "</option>" );
      if ( socialBenefitHeaderVOs != null && socialBenefitHeaderVOs.size() > 0 )
      {
         SocialBenefitHeaderVO socialBenefitHeaderVO = null;
         for ( int i = 0; i < socialBenefitHeaderVOs.size(); i++ )
         {
            socialBenefitHeaderVO = ( SocialBenefitHeaderVO ) socialBenefitHeaderVOs.get( i );
            rs.append( "<option value=\"" + socialBenefitHeaderVO.getHeaderId() + "\"" );
            if ( headerId != null && socialBenefitHeaderVO.getHeaderId().equals( headerId ) )
            {
               rs.append( "selected=\"selected\"" );
            }
            rs.append( ">" );
            if ( request.getLocale().getLanguage().equals( "zh" ) )
            {
               rs.append( "" + socialBenefitHeaderVO.getNameZH() + "" );
            }
            else
            {
               rs.append( "" + socialBenefitHeaderVO.getNameEN() + "" );
            }
            rs.append( "</option>" );
         }
      }
      rs.append( "</select>" );

      return rs.toString();
   }
}
