package com.kan.hro.domain.biz.employee;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractPropertyVO  
* ��������  ��Ա��ͬ����  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-8-19 ����03:07:55  
* @version   
*   
*/
public class EmployeeContractPropertyVO extends BaseVO
{

   // Serial Version UID  
   private static final long serialVersionUID = -1607472493140368127L;

   // ��Ա��ͬ����Id
   private String propertyId;

   // �����ͬId  
   private String contractId;

   // �ֶ�ֵ�����ͣ�ʹ���Զ����ֶ��е�ֵ���ͣ�
   private String valueType;

   // ��������  
   private String propertyName;

   // ����ֵ  
   private String propertyValue;

   // ����  
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
      this.valueType = "";
      this.propertyName = "";
      this.propertyValue = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      EmployeeContractPropertyVO clientContractPropertyVO = ( EmployeeContractPropertyVO ) object;
      this.contractId = clientContractPropertyVO.getContractId();
      this.valueType = clientContractPropertyVO.getValueType();
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

   public String getValueType()
   {
      return valueType;
   }

   public void setValueType( String valueType )
   {
      this.valueType = valueType;
   }

}
