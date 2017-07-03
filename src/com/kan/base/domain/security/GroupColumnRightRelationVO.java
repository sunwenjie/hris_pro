package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class GroupColumnRightRelationVO extends BaseVO
{
   
   /**  
   * serialVersionUID:TODO（关系ID，一个群组能访问哪些字段，并对这些群组有什么功能权限）  
   *  
   * @since Ver 1.1  
   */  
   
   private static final long serialVersionUID = -3457703097636994506L;
   // 关系ID
   private String relationId;
   // 职位组ID
   private String groupId;
   // 模块Id
   private String columnId;
   // 访问权限ID
   private String rightId;

   public void update( Object object )
   {
      final GroupColumnRightRelationVO groupRelationVO = ( GroupColumnRightRelationVO ) object;
      this.setGroupId( groupRelationVO.getGroupId() );
      this.setColumnId( groupRelationVO.getColumnId() );
      this.setRightId( groupRelationVO.getRightId() );
      super.setStatus( groupRelationVO.getStatus() );
      super.setModifyBy( groupRelationVO.getModifyBy() );
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
      this.setGroupId( "" );
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

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
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
