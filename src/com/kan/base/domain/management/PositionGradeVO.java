package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author XIAODONG
 **/
public class PositionGradeVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4319794840718321615L;
   // 职级ID
   private String positionGradeId;
   // 职级中文名
   private String gradeNameZH;
   // 职级英文名
   private String gradeNameEN;
   // 职级描述
   private String description;

   // 权重
   private String weight;

   @Override
   public void reset() throws KANException
   {
      this.gradeNameZH = "";
      this.gradeNameEN = "";
      this.description = "";
      this.weight = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final PositionGradeVO positionGradeVO = ( PositionGradeVO ) object;
      this.gradeNameZH = positionGradeVO.getGradeNameZH();
      this.gradeNameEN = positionGradeVO.getGradeNameEN();
      this.description = positionGradeVO.getDescription();
      this.weight = positionGradeVO.getWeight();
      super.setStatus( positionGradeVO.getStatus() );
      super.setModifyBy( positionGradeVO.getModifyBy() );
      super.setModifyDate( new Date() );

   }

   public String getGradeNameZH()
   {
      return gradeNameZH;
   }

   public void setGradeNameZH( String gradeNameZH )
   {
      this.gradeNameZH = gradeNameZH;
   }

   public String getGradeNameEN()
   {
      return gradeNameEN;
   }

   public void setGradeNameEN( String gradeNameEN )
   {
      this.gradeNameEN = gradeNameEN;
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId.split( "_" )[ 0 ];
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getWeight()
   {
      return KANUtil.filterEmpty( weight );
   }

   public void setWeight( String weight )
   {
      this.weight = weight;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( positionGradeId == null || positionGradeId.trim().equals( "" ) )
      {
         return "";
      }
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( positionGradeId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
