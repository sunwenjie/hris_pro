package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;

public class YERRRuleVO extends BaseVO
{

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 2455715855091739934L;

   /**
    * For DB
    */
   // ¹æÔòID
   private String ruleId;
   // ¼¨Ð§ÆÀ·Ö{1:2:2.5:3:3.5:4:4.5:5}
   private String rating;
   // ·ÖÅä±ÈÀý
   private double distribution;
   // Òµ¼¨ÕÇ·ù£¨ÈËÃñ±Ò£©
   private double meritRateRMB;
   // Òµ¼¨ÕÇ·ù£¨¸Û±Ò£©
   private double meritRateHKD;
   // Òµ¼¨ÕÇ·ù£¨ÐÂ¼ÓÆÂÔª£©
   private double meritRateSGD;
   // Òµ¼¨ÕÇ·ù£¨´ý¶¨£©
   private double meritRateRemark1;
   // Òµ¼¨ÕÇ·ù£¨´ý¶¨£©
   private double meritRateRemark2;
   // Òµ¼¨+½úÉýÕÇ·ù£¨ÈËÃñ±Ò£©
   private double meritAndPromotionRateRMB;
   // Òµ¼¨+½úÉýÕÇ·ù£¨¸Û±Ò£©
   private double meritAndPromotionRateHKD;
   // Òµ¼¨+½úÉýÕÇ·ù£¨ÐÂ¼ÓÆÂÔª£©
   private double meritAndPromotionRateSGD;
   // Òµ¼¨+½úÉýÕÇ·ù£¨´ý¶¨£©
   private double meritAndPromotionRateRemark1;
   // Òµ¼¨+½úÉýÕÇ·ù£¨´ý¶¨£©
   private double meritAndPromotionRateRemark2;
   // ½±½ðÕÇ·ù
   private double bounsRate;
   private String description;

   /**
    * For app
    */
   private List< MappingVO > ratings = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      ratings.add( getEmptyMappingVO() );
      ratings.add( new MappingVO( "5", "5", "5" ) );
      ratings.add( new MappingVO( "4.5", "4.5", "4.5" ) );
      ratings.add( new MappingVO( "4", "4", "4" ) );
      ratings.add( new MappingVO( "3.5", "3.5", "3.5" ) );
      ratings.add( new MappingVO( "3", "3", "3" ) );
      ratings.add( new MappingVO( "2.5", "2.5", "2.5" ) );
      ratings.add( new MappingVO( "2", "2", "2" ) );
      ratings.add( new MappingVO( "1", "1", "1" ) );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( ruleId );
   }

   @Override
   public void reset() throws KANException
   {
      this.distribution = 0;
      this.meritRateRMB = 0;
      this.meritRateSGD = 0;
      this.meritRateRemark1 = 0;
      this.meritRateRemark2 = 0;
      this.meritAndPromotionRateRMB = 0;
      this.meritAndPromotionRateHKD = 0;
      this.meritAndPromotionRateSGD = 0;
      this.meritAndPromotionRateRemark1 = 0;
      this.meritAndPromotionRateRemark2 = 0;
      this.bounsRate = 0;
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final YERRRuleVO yerrRuleVO = ( YERRRuleVO ) object;
      this.setRating( yerrRuleVO.getRating() );
      this.setDistribution( yerrRuleVO.getDistribution() );
      this.setMeritRateRMB( yerrRuleVO.getMeritRateRMB() );
      this.setMeritRateHKD( yerrRuleVO.getMeritRateHKD() );
      this.setMeritRateSGD( yerrRuleVO.getMeritRateSGD() );
      this.setMeritRateRemark1( yerrRuleVO.getMeritRateRemark1() );
      this.setMeritRateRemark2( yerrRuleVO.getMeritRateRemark2() );
      this.setMeritAndPromotionRateRMB( yerrRuleVO.getMeritAndPromotionRateRMB() );
      this.setMeritAndPromotionRateHKD( yerrRuleVO.getMeritAndPromotionRateHKD() );
      this.setMeritAndPromotionRateSGD( yerrRuleVO.getMeritAndPromotionRateSGD() );
      this.setMeritAndPromotionRateRemark1( yerrRuleVO.getMeritAndPromotionRateRemark1() );
      this.setMeritAndPromotionRateRemark2( yerrRuleVO.getMeritAndPromotionRateRemark2() );
      this.setBounsRate( yerrRuleVO.getBounsRate() );
      this.setDescription( yerrRuleVO.getDescription() );
      super.setStatus( yerrRuleVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getRuleId()
   {
      return ruleId;
   }

   public void setRuleId( String ruleId )
   {
      this.ruleId = ruleId;
   }

   public String getRating()
   {
      return rating;
   }

   public void setRating( String rating )
   {
      this.rating = rating;
   }

   public double getDistribution()
   {
      return distribution;
   }

   public void setDistribution( double distribution )
   {
      this.distribution = distribution;
   }

   public double getMeritRateRMB()
   {
      return meritRateRMB;
   }

   public void setMeritRateRMB( double meritRateRMB )
   {
      this.meritRateRMB = meritRateRMB;
   }

   public double getMeritRateHKD()
   {
      return meritRateHKD;
   }

   public void setMeritRateHKD( double meritRateHKD )
   {
      this.meritRateHKD = meritRateHKD;
   }

   public double getMeritRateSGD()
   {
      return meritRateSGD;
   }

   public void setMeritRateSGD( double meritRateSGD )
   {
      this.meritRateSGD = meritRateSGD;
   }

   public double getMeritRateRemark1()
   {
      return meritRateRemark1;
   }

   public void setMeritRateRemark1( double meritRateRemark1 )
   {
      this.meritRateRemark1 = meritRateRemark1;
   }

   public double getMeritRateRemark2()
   {
      return meritRateRemark2;
   }

   public void setMeritRateRemark2( double meritRateRemark2 )
   {
      this.meritRateRemark2 = meritRateRemark2;
   }

   public double getMeritAndPromotionRateRMB()
   {
      return meritAndPromotionRateRMB;
   }

   public void setMeritAndPromotionRateRMB( double meritAndPromotionRateRMB )
   {
      this.meritAndPromotionRateRMB = meritAndPromotionRateRMB;
   }

   public double getMeritAndPromotionRateHKD()
   {
      return meritAndPromotionRateHKD;
   }

   public void setMeritAndPromotionRateHKD( double meritAndPromotionRateHKD )
   {
      this.meritAndPromotionRateHKD = meritAndPromotionRateHKD;
   }

   public double getMeritAndPromotionRateSGD()
   {
      return meritAndPromotionRateSGD;
   }

   public void setMeritAndPromotionRateSGD( double meritAndPromotionRateSGD )
   {
      this.meritAndPromotionRateSGD = meritAndPromotionRateSGD;
   }

   public double getMeritAndPromotionRateRemark1()
   {
      return meritAndPromotionRateRemark1;
   }

   public void setMeritAndPromotionRateRemark1( double meritAndPromotionRateRemark1 )
   {
      this.meritAndPromotionRateRemark1 = meritAndPromotionRateRemark1;
   }

   public double getMeritAndPromotionRateRemark2()
   {
      return meritAndPromotionRateRemark2;
   }

   public void setMeritAndPromotionRateRemark2( double meritAndPromotionRateRemark2 )
   {
      this.meritAndPromotionRateRemark2 = meritAndPromotionRateRemark2;
   }

   public double getBounsRate()
   {
      return bounsRate;
   }

   public void setBounsRate( double bounsRate )
   {
      this.bounsRate = bounsRate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getRatings()
   {
      return ratings;
   }

   public void setRatings( List< MappingVO > ratings )
   {
      this.ratings = ratings;
   }

}
