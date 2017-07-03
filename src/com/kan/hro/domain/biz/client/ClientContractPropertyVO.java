package com.kan.hro.domain.biz.client;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientContractPropertyVO  
 * 类描述：客户商务合同属性  
 * 创建人：Jack  
 * 创建时间：2013-8-9  
 */
public class ClientContractPropertyVO extends BaseVO
{

   // Serial Version UID  
   private static final long serialVersionUID = -1607472493140368127L;

   // 商务合同参数Id
   private String propertyId;

   // 商务合同Id  
   private String contractId;

   // 参数Id
   private String constantId;

   // 参数名称  
   private String propertyName;

   // 参数值  
   private String propertyValue;

   // 描述  
   private String description;

   @Override
   public String getEncodedId() throws KANException
   {
      if ( propertyId == null || propertyId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( propertyId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.constantId = "";
      this.propertyName = "";
      this.propertyValue = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) object;
      this.contractId = clientContractPropertyVO.getContractId();
      this.constantId = clientContractPropertyVO.getConstantId();
      this.propertyName = clientContractPropertyVO.getPropertyName();
      this.propertyValue = clientContractPropertyVO.getPropertyValue();
      this.description = clientContractPropertyVO.getDescription();
      super.setStatus( clientContractPropertyVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getPropertyId()
   {
      return propertyId;
   }

   public void setPropertyId( String propertyId )
   {
      this.propertyId = propertyId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   public void setPropertyName( String propertyName )
   {
      this.propertyName = propertyName;
   }

   public String getPropertyValue()
   {
      return propertyValue;
   }

   public void setPropertyValue( String propertyValue )
   {
      this.propertyValue = propertyValue;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getConstantId()
   {
      return constantId;
   }

   public void setConstantId( String constantId )
   {
      this.constantId = constantId;
   }

}
