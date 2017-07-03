package com.kan.base.domain.security;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ModuleRightRelationVO extends BaseVO
{

   /**  
   * serialVersionUID:����ģ������ݷ���Ȩ�޹�ϵ��  
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = 8207927112258536045L;
   // ��ϵID
   private String relationId;
   // �˻�ID
   private String accountId;
   // ģ��ID
   private String moduleId;
   // ����Ȩ��ID
   private String rightId;

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
      this.setRightId( "" );
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ModuleRightRelationVO moduleRightRelationVO = ( ModuleRightRelationVO ) object;
      this.setAccountId( moduleRightRelationVO.getAccountId() );
      this.setModuleId( moduleRightRelationVO.getModuleId() );
      this.setRightId( moduleRightRelationVO.getRightId() );
      super.setStatus( moduleRightRelationVO.getStatus() );
      super.setModifyBy( moduleRightRelationVO.getModifyBy() );
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

   public String getRightId()
   {
      return rightId;
   }

   public void setRightId( String rightId )
   {
      this.rightId = rightId;
   }

}
