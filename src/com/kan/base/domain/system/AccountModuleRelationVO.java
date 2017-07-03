package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**
 * 账户能够访问哪些模块
 * 
 * @author Kevin
 */
public class AccountModuleRelationVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3104194874885552750L;

   // 关系ID
   private String relationId;

   // 模块ID
   private String moduleId;

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
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final AccountModuleRelationVO accountModuleRelationVO = ( AccountModuleRelationVO ) object;
      this.setAccountId( accountModuleRelationVO.getAccountId() );
      this.setModuleId( accountModuleRelationVO.getModuleId() );
      super.setStatus( accountModuleRelationVO.getStatus() );
      super.setModifyBy( accountModuleRelationVO.getModifyBy() );
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

   public String getModuleId()
   {
      return moduleId;
   }

   public void setModuleId( String moduleId )
   {
      this.moduleId = moduleId;
   }

}
