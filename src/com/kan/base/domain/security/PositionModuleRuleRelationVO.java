package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class PositionModuleRuleRelationVO extends BaseVO
{

   /**  
   * serialVersionUID:（关系ID，一个职位能访问到哪些模块，并对这些模块有什么数据权限）  
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -7221421826317321864L;
   // 关系ID
   private String relationId;
   // 职位ID
   private String positionId;
   // 模块Id
   private String moduleId;
   // 数据权限ID
   private String ruleId;

   public PositionModuleRuleRelationVO()
   {

   }

   public PositionModuleRuleRelationVO( final String positionId, final String moduleId, final String ruleId )
   {
      this.positionId = positionId;
      this.moduleId = moduleId;
      this.ruleId = ruleId;
   }

   public void update( Object object )
   {
      final PositionModuleRuleRelationVO positionGroupRelationVO = ( PositionModuleRuleRelationVO ) object;
      this.setPositionId( positionGroupRelationVO.getPositionId() );
      this.setModuleId( positionGroupRelationVO.getModuleId() );
      this.setRuleId( positionGroupRelationVO.getModuleId() );
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
      this.setModuleId( "" );
      this.setRuleId( "" );
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

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

   public String getRuleId()
   {
      return ruleId;
   }

   public void setRuleId( String ruleId )
   {
      this.ruleId = ruleId;
   }

}
