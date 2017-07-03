package com.kan.hro.web.renders.biz.vendor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.util.KANException;

public class VendorRender
{

   public static String getSocialBenbfitSolutionHeaderVOsByCityId( final HttpServletRequest request, final List< Object > socialBenefitSolutionHeaderVOs, final String headerId )
         throws KANException
   {
      final StringBuffer rs = new StringBuffer();

      rs.append( "<select name=\"sbHeaderId\" class=\"manageVendorService_sbHeaderId\" id=\"manageVendorService_sbHeaderId\">" );
      rs.append( "<option value=\"0\">«Î—°‘Ò</option>" );
      if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
      {
         SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO = null;
         for ( int i = 0; i < socialBenefitSolutionHeaderVOs.size(); i++ )
         {
            socialBenefitSolutionHeaderVO = ( SocialBenefitSolutionHeaderVO ) socialBenefitSolutionHeaderVOs.get( i );
            rs.append( "<option value=\"" + socialBenefitSolutionHeaderVO.getHeaderId() + "\"" );
            if ( headerId != null && socialBenefitSolutionHeaderVO.getHeaderId().equals( headerId ) )
            {
               rs.append( "selected=\"selected\"" );
            }
            rs.append( ">" );
            if ( request.getLocale().getLanguage().equals( "zh" ) )
            {
               rs.append( "" + socialBenefitSolutionHeaderVO.getNameZH() + "" );
            }
            else
            {
               rs.append( "" + socialBenefitSolutionHeaderVO.getNameEN() + "" );
            }
            rs.append( "</option>" );
         }
      }
      rs.append( "</select>" );

      return rs.toString();
   }

}
