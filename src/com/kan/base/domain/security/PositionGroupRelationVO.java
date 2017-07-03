package com.kan.base.domain.security;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class PositionGroupRelationVO extends BaseVO
{
   
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -5482726996660124638L;
   
   private String relationId;
   
   private String positionId;
   
   private String groupId;
   
   public void update( Object object )
   {
      final PositionGroupRelationVO positionGroupRelationVO = ( PositionGroupRelationVO ) object;
      this.setPositionId( positionGroupRelationVO.getPositionId() );
      this.setGroupId( positionGroupRelationVO.getGroupId() );
      super.setStatus( positionGroupRelationVO.getStatus() );
      super.setModifyBy( positionGroupRelationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }
   
   public void reset(){
      super.setStatus( "0" );
   }

   public String getRelationId()
   {
      return relationId;
   }

   public void setRelationId( String relationId )
   {
      this.relationId = relationId;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

}
