package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SocialBenefitDTO implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -909272977533697890L;

   // SocialBenefitHeaderVO
   private SocialBenefitHeaderVO socialBenefitHeaderVO;

   // SocialBenefitDetailVOs
   private List< SocialBenefitDetailVO > socialBenefitDetailVOs = new ArrayList< SocialBenefitDetailVO >();

   public SocialBenefitHeaderVO getSocialBenefitHeaderVO()
   {
      return socialBenefitHeaderVO;
   }

   public void setSocialBenefitHeaderVO( SocialBenefitHeaderVO socialBenefitHeaderVO )
   {
      this.socialBenefitHeaderVO = socialBenefitHeaderVO;
   }

   public List< SocialBenefitDetailVO > getSocialBenefitDetailVOs()
   {
      return socialBenefitDetailVOs;
   }

   public void setSocialBenefitDetailVOs( List< SocialBenefitDetailVO > socialBenefitDetailVOs )
   {
      this.socialBenefitDetailVOs = socialBenefitDetailVOs;
   }

   public SocialBenefitDetailVO getSocialBenefitDetailVOByItemId( final String itemId )
   {
      if ( socialBenefitDetailVOs != null && socialBenefitDetailVOs.size() > 0 )
      {
         for ( SocialBenefitDetailVO socialBenefitDetailVO : this.socialBenefitDetailVOs )
         {
            if ( socialBenefitDetailVO.getItemId() != null && socialBenefitDetailVO.getItemId().trim().equals( itemId ) )
            {
               return socialBenefitDetailVO;
            }
         }
      }

      return null;
   }

}
