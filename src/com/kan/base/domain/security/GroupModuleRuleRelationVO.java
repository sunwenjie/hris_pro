package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**
 * һ��Ⱥ���ܷ��ʵ���Щģ�飬������ЩȺ����ʲô����Ȩ��
 */
public class GroupModuleRuleRelationVO extends BaseVO
{

   /**  
   * Serial Version UID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -629972390869187905L;
   // ��ϵID
   private String relationId;
   // ְλ��ID
   private String groupId;
   // ģ��Id
   private String moduleId;
   // ����Ȩ��ID
   private String ruleId;

   public GroupModuleRuleRelationVO()
   {

   }

   public GroupModuleRuleRelationVO( final String groupId, final String moduleId, final String ruleId )
   {
      this.groupId = groupId;
      this.moduleId = moduleId;
      this.ruleId = ruleId;
   }

   public void update( Object object )
   {
      final GroupModuleRuleRelationVO groupRelationVO = ( GroupModuleRuleRelationVO ) object;
      this.setGroupId( groupRelationVO.getGroupId() );
      this.setModuleId( groupRelationVO.getModuleId() );
      this.setRuleId( groupRelationVO.getModuleId() );
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

   public String getRuleId()
   {
      return ruleId;
   }

   public void setRuleId( String ruleId )
   {
      this.ruleId = ruleId;
   }

}
