package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.util.KANUtil;

public class SocialBenefitSolutionDTO implements Serializable
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = -1716765943201681969L;

   // 社保方案主表
   private SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO;

   // 社保方案从表列表
   private List< SocialBenefitSolutionDetailVO > socialBenefitSolutionDetailVOs = new ArrayList< SocialBenefitSolutionDetailVO >();

   public SocialBenefitSolutionHeaderVO getSocialBenefitSolutionHeaderVO()
   {
      return socialBenefitSolutionHeaderVO;
   }

   public void setSocialBenefitSolutionHeaderVO( SocialBenefitSolutionHeaderVO socialBenefitSolutionHeaderVO )
   {
      this.socialBenefitSolutionHeaderVO = socialBenefitSolutionHeaderVO;
   }

   public List< SocialBenefitSolutionDetailVO > getSocialBenefitSolutionDetailVOs()
   {
      return socialBenefitSolutionDetailVOs;
   }

   public void setSocialBenefitSolutionDetailVOs( List< SocialBenefitSolutionDetailVO > socialBenefitSolutionDetailVOs )
   {
      this.socialBenefitSolutionDetailVOs = socialBenefitSolutionDetailVOs;
   }

   public SocialBenefitSolutionDetailVO getSocialBenefitSolutionDetailVOByDetailId( final String detailId )
   {
      if ( KANUtil.filterEmpty( detailId ) != null && socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO.getDetailId().equals( detailId ) )
            {
               return socialBenefitSolutionDetailVO;
            }
         }
      }

      return null;
   }

   public String getSBStartRule()
   {
      if ( socialBenefitSolutionHeaderVO != null )
      {
         return socialBenefitSolutionHeaderVO.getStartRule();
      }

      return null;
   }

   public String getSBStartRuleRemark()
   {
      if ( socialBenefitSolutionHeaderVO != null )
      {
         return socialBenefitSolutionHeaderVO.getStartRuleRemark();
      }

      return null;
   }

   public String getSBEndRule()
   {
      if ( socialBenefitSolutionHeaderVO != null )
      {
         return socialBenefitSolutionHeaderVO.getEndRule();
      }

      return null;
   }

   public String getSBEndRuleRemark()
   {
      if ( socialBenefitSolutionHeaderVO != null )
      {
         return socialBenefitSolutionHeaderVO.getEndRuleRemark();
      }

      return null;
   }

   public String getSBAttribute()
   {
      if ( socialBenefitSolutionHeaderVO != null )
      {
         return socialBenefitSolutionHeaderVO.getAttribute();
      }

      return null;
   }

   public String getSBStartRuleByItemId( final String itemId )
   {
      if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO != null && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ) != null
                  && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ).equals( itemId ) )
            {
               return socialBenefitSolutionDetailVO.getStartRule();
            }
         }
      }

      return null;
   }

   public String getSBStartRuleRemarkByItemId( final String itemId )
   {
      if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO != null && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ) != null
                  && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ).equals( itemId ) )
            {
               return socialBenefitSolutionDetailVO.getStartRuleRemark();
            }
         }
      }

      return null;
   }

   public String getSBEndRuleByItemId( final String itemId )
   {
      if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO != null && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ) != null
                  && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ).equals( itemId ) )
            {
               return socialBenefitSolutionDetailVO.getEndRule();
            }
         }
      }

      return null;
   }

   public String getSBEndRuleRemarkByItemId( final String itemId )
   {
      if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO != null && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ) != null
                  && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ).equals( itemId ) )
            {
               return socialBenefitSolutionDetailVO.getEndRuleRemark();
            }
         }
      }

      return null;
   }

   public String getSBAttributeByItemId( final String itemId )
   {
      if ( socialBenefitSolutionDetailVOs != null && socialBenefitSolutionDetailVOs.size() > 0 )
      {
         for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDetailVOs )
         {
            if ( socialBenefitSolutionDetailVO != null && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ) != null
                  && KANUtil.filterEmpty( socialBenefitSolutionDetailVO.getItemId() ).equals( itemId ) )
            {
               return socialBenefitSolutionDetailVO.getAttribute();
            }
         }
      }

      return null;
   }
}
