package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**
 * 一个群组能访问哪些模块，并对这些模块有什么功能权限
 */
public class GroupModuleRightRelationVO extends BaseVO
{
   /**  
   * Serial Version UID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -3107619951605715481L;
   // 关系ID
   private String relationId;
   // 职位组ID
   private String groupId;
   // 模块ID
   private String moduleId;
   // 访问权限ID
   private String rightId;

   public GroupModuleRightRelationVO()
   {
      
   }

   public GroupModuleRightRelationVO( final String groupId, final String moduleId, final String rightId )
   {
      this.groupId = groupId;
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
      this.setGroupId( "" );
      this.setModuleId( "" );
      this.setRightId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final GroupModuleRightRelationVO positionModuleRightRelationVO = ( GroupModuleRightRelationVO ) object;
      this.setGroupId( positionModuleRightRelationVO.getGroupId() );
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

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
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
