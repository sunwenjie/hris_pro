package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class PositionColumnRightRelationVO extends BaseVO
{
   
   /**  
   * serialVersionUID:（关系ID，一个职位能访问哪些字段，并对这些字段有什么功能权限）  
   *  
   * @since Ver 1.1  
   */  
   
   private static final long serialVersionUID = -8012901820200706412L;
   // 关系ID
   private String relationId;
   // 职位ID
   private String positionId;
   // 模块Id
   private String columnId;
   // 访问权限ID
   private String rightId;

   public void update( Object object )
   {
      final PositionColumnRightRelationVO positionGroupRelationVO = ( PositionColumnRightRelationVO ) object;
      this.setPositionId( positionGroupRelationVO.getPositionId() );
      this.setColumnId( positionGroupRelationVO.getColumnId() );
      this.setRightId( positionGroupRelationVO.getRightId() );
      super.setStatus( positionGroupRelationVO.getStatus() );
      super.setModifyBy( positionGroupRelationVO.getModifyBy() );
      super.setModifyDate( new Date() );
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

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   public void reset()
   {
      this.setPositionId( "" );
      this.setColumnId( "" );
      this.setRightId( "" );
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

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
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
