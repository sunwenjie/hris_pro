package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ModuleRuleRelationVO extends BaseVO
{

   /**  
   * serialVersionUID:（关系ID，各模块的数据访问权限，账户全局设置）  
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -3024517127291390605L;
   // 关系ID
   private String relationId;
   // 账户ID
   private String accountId;
   // 模块ID
   private String moduleId;
   // 数据权限ID
   private String ruleId;

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
      this.setAccountId( "" );
      this.setModuleId( "" );
      this.setRuleId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ModuleRuleRelationVO moduleRuleRelationVO = ( ModuleRuleRelationVO ) object;
      this.setAccountId( moduleRuleRelationVO.getAccountId() );
      this.setModuleId( moduleRuleRelationVO.getModuleId() );
      this.setRuleId( moduleRuleRelationVO.getRuleId() );
      super.setStatus( moduleRuleRelationVO.getStatus() );
      super.setModifyBy( moduleRuleRelationVO.getModifyBy() );
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

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
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
