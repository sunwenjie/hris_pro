package com.kan.base.domain.security;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/**
 * @author XIAODONG
 **/
public class PositionGradeVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1405715541321631051L;

   // 职级ID
   private String positionGradeId;

   // 职级中文名称
   private String gradeNameZH;

   // 职级英文名称
   private String gradeNameEN;

   // 权重
   private String weight;

   // 职级描述
   private String description;

   @Override
   public void reset() throws KANException
   {
      this.gradeNameZH = "";
      this.gradeNameEN = "";
      this.weight = "";
      this.description = "";
      super.setStatus( "0" );

   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( positionGradeId );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final PositionGradeVO positionGradeVO = ( PositionGradeVO ) object;
      this.gradeNameZH = positionGradeVO.gradeNameZH;
      this.gradeNameEN = positionGradeVO.gradeNameEN;
      this.weight = positionGradeVO.weight;
      this.description = positionGradeVO.description;
      super.setStatus( positionGradeVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getWeight()
   {
      return weight;
   }

   public void setWeight( String weight )
   {
      this.weight = weight;
   }

}
