package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class PositionModuleRightRelationVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 2371332030814258249L;

   private String relationId;

   private String positionId;

   private String moduleId;

   private String rightId;

   public PositionModuleRightRelationVO()
   {

   }

   public PositionModuleRightRelationVO( final String positionId, final String moduleId, final String rightId )
   {
      this.positionId = positionId;
      this.moduleId = moduleId;
      this.rightId = rightId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( relationId == null || relationId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( relationId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Overwrite
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void reset() throws KANException
   {
      this.setPositionId( "" );
      this.setModuleId( "" );
      this.setRightId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final PositionModuleRightRelationVO positionModuleRightRelationVO = ( PositionModuleRightRelationVO ) object;
      this.setPositionId( positionModuleRightRelationVO.getPositionId() );
      this.setModuleId( positionModuleRightRelationVO.getModuleId() );
      this.setRightId( positionModuleRightRelationVO.getRightId() );
      super.setStatus( positionModuleRightRelationVO.getStatus() );
      super.setModifyBy( positionModuleRightRelationVO.getModifyBy() );
      super.setModifyDate( new Date() );

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

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

   public String getRightId()
   {
      return rightId;
   }

   public void setRightId( String rightId )
   {
      this.rightId = rightId;
   }

}
