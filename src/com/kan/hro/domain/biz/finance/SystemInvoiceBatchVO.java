package com.kan.hro.domain.biz.finance;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SystemInvoiceBatchVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -3355845589608305908L;
   
      // ����Id
   private String batchId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // �ͻ�Id
   private String clientId;

   // ����Id
   private String orderId;


   // �·ݣ�����2013/9��
   private String monthly;

   // ��ʼʱ�� - ָBatch���е�ʱ��
   private String startDate;

   // ����ʱ�� - ָBatch���е�ʱ��
   private String endDate;

   // ����
   private String description;

   /**
    * For App
    */

   // �ͻ�����
   private String clientNameZH;

   // �ͻ�Ӣ������
   private String clientNameEN;

   // �����ͻ�����
   private String countClientId;

   // ������������
   private String countOrderId;

   // ������Ŀ����
   private String countItemId;
   
   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;
   
   //
   private String taxAmount;

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // �·�
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // ��ǰҳ�����ͱ��(Preview, Split, Merge)
   private String pageFlag;
   
   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast36Months( this.getLocale().getLanguage() );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( this.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( this.getLocale().getLanguage(), super.getCorpId() );

      // ���"��ѡ��"ѡ��
      if ( this.monthlies != null )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }
      if ( this.entitys != null )
      {
         this.entitys.add( 0, getEmptyMappingVO() );
      }
      if ( this.businessTypes != null )
      {
         this.businessTypes.add( 0, getEmptyMappingVO() );
      }
   }
   
   
   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.clientId = "";
      this.orderId = "";
      this.monthly = "";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SystemInvoiceBatchVO systemInvoiceBatchVO = ( SystemInvoiceBatchVO ) object;
      this.entityId = systemInvoiceBatchVO.getEntityId();
      this.businessTypeId = systemInvoiceBatchVO.getBusinessTypeId();
      this.clientId = systemInvoiceBatchVO.getClientId();
      this.orderId = systemInvoiceBatchVO.getOrderId();
      this.monthly = systemInvoiceBatchVO.getMonthly();
      this.startDate = systemInvoiceBatchVO.getStartDate();
      this.endDate = systemInvoiceBatchVO.getEndDate();
      this.description = systemInvoiceBatchVO.getDescription();
      super.setStatus( systemInvoiceBatchVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setModifyBy( systemInvoiceBatchVO.getModifyBy() );
   }

   
   @Override
   public String getEncodedId() throws KANException
   {
      if ( batchId == null || batchId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( batchId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ����ʵ��ID����
   public String getEncodedEntityId() throws KANException
   {
      return encodedField( entityId );
   }

   // ҵ�����ͱ���
   public String getEncodedBusinessTypeId() throws KANException
   {
      return encodedField( businessTypeId );
   }

   // �ͻ�ID����
   public String getEncodedClientId() throws KANException
   {
      return encodedField( clientId );
   }

   // ����ID����
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      if ( this.getEntityId() == null || this.getEntityId().trim().equals( "0" ) )
      {
         return null;
      }
      return decodeField( this.getEntityId(), this.entitys );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      if ( this.getBusinessTypeId() == null || this.getBusinessTypeId().trim().equals( "0" ) )
      {
         return null;
      }
      return decodeField( this.getBusinessTypeId(), this.businessTypes );
   }
   
   
   
   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = KANUtil.filterEmpty( entityId );
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = KANUtil.filterEmpty( businessTypeId );
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = KANUtil.filterEmpty( clientId );
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = KANUtil.filterEmpty( orderId );
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDatetime( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public String getCountClientId()
   {
      return countClientId;
   }

   public void setCountClientId( String countClientId )
   {
      this.countClientId = countClientId;
   }

   public String getCountOrderId()
   {
      return countOrderId;
   }

   public void setCountOrderId( String countOrderId )
   {
      this.countOrderId = countOrderId;
   }

   public String getCountItemId()
   {
      return countItemId;
   }

   public void setCountItemId( String countItemId )
   {
      this.countItemId = countItemId;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }
   
   public String getBillAmountCompany()
   {
      return formatNumber(billAmountCompany);
   }


   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }


   public String getCostAmountCompany()
   {
      return formatNumber(costAmountCompany);
   }


   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   
   public String getPageFlag()
   {
      return pageFlag;
   }


   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }
   
   

   public String getTaxAmount()
   {
      return taxAmount;
   }


   public void setTaxAmount( String taxAmount )
   {
      this.taxAmount = taxAmount;
   }


   public String getClientName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getClientNameZH();
         }
         else
         {
            return this.getClientNameEN();
         }
      }
      else
      {
         return this.getClientNameZH();
      }
   }
}
