package com.kan.hro.domain.biz.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.BranchVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class SystemInvoiceHeaderVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = 2375609244015295338L;
   
   // invoice����Id
   private String invoiceId;
   
   private String extendInvoiceId;
   
   private String parentInvoiceId;

   // ����ʵ��Id
   private String entityId;

   // ҵ������Id
   private String businessTypeId;

   // ����Id
   private String batchId;

   // �ͻ�Id
   private String clientId;

   // ����Id
   private String orderId;

   
   private String clientOrderId;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   
   private String taxAmount;

   // �������ţ�Branch Id��
   private String branch;

   // �����ˣ�Position Id��
   private String owner;

   // ����
   private String description;

   // �·�
   private String monthly;

   /**
    * For App
    */
   // �ͻ�������
   private String clientNameZH;

   // �ͻ�������
   private String clientNameEN;

   // ��������Э������
   private String countContractId;

   // ������Ŀ����
   private String countItemId;

   // ����ʵ��
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // �·�
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // ��ǰҳ�����ͱ��(Preview, Split, Merge)
   private String pageFlag;
   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast2Months( this.getLocale().getLanguage(), super.getCorpId() );
      this.entitys = KANConstants.getKANAccountConstants( getAccountId() ).getEntities( getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() );
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
      this.extendInvoiceId="";
      this.entityId = "";
      this.parentInvoiceId="";
      this.businessTypeId = "";
      this.clientId = "";
      this.batchId="";
      this.orderId = "";
      this.clientOrderId="";
      this.billAmountCompany = "";
      this.costAmountCompany = "";
      this.taxAmount = "";
      this.branch = "0";
      this.owner = "0";
      this.description = "";
      this.monthly = "";
      super.setStatus( "0" );
   }

   @Override
   public void update(final Object object ) throws KANException
   {
      final SystemInvoiceHeaderVO systemInvoiceHeaderVO = ( SystemInvoiceHeaderVO ) object;
      this.parentInvoiceId=systemInvoiceHeaderVO.getParentInvoiceId();
      this.extendInvoiceId=systemInvoiceHeaderVO.getExtendInvoiceId();
      this.entityId = systemInvoiceHeaderVO.getEntityId();
      this.businessTypeId = systemInvoiceHeaderVO.getBusinessTypeId();
      this.batchId = systemInvoiceHeaderVO.getBatchId();
      this.clientId = systemInvoiceHeaderVO.getClientId();
      this.orderId = systemInvoiceHeaderVO.getOrderId();
      this.taxAmount = systemInvoiceHeaderVO.getTaxAmount();
      this.billAmountCompany = systemInvoiceHeaderVO.getBillAmountCompany();
      this.costAmountCompany = systemInvoiceHeaderVO.getCostAmountCompany();
      this.branch = systemInvoiceHeaderVO.getBranch();
      this.owner = systemInvoiceHeaderVO.getOwner();
      this.description = systemInvoiceHeaderVO.getDescription();
      this.monthly = systemInvoiceHeaderVO.getMonthly();
      super.setStatus( systemInvoiceHeaderVO.getStatus() );
      super.setModifyBy( systemInvoiceHeaderVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( systemInvoiceHeaderVO.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( invoiceId );
   }

   // batchId����
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // clientId����
   public String getEncodedClientId() throws KANException
   {
      return encodedField( clientId );
   }

   // orderId����
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }
   
   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( entityId, entitys );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( businessTypeId, businessTypes );
   }
   
   public String getInvoiceId()
   {
      return invoiceId;
   }

   public void setInvoiceId( String invoiceId )
   {
      this.invoiceId = invoiceId;
   }

   public String getExtendInvoiceId()
   {
      return extendInvoiceId;
   }

   public void setExtendInvoiceId( String extendInvoiceId )
   {
      this.extendInvoiceId = extendInvoiceId;
   }

   public String getParentInvoiceId()
   {
      return parentInvoiceId;
   }

   public void setParentInvoiceId( String parentInvoiceId )
   {
      this.parentInvoiceId = parentInvoiceId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getClientOrderId()
   {
      return clientOrderId;
   }

   public void setClientOrderId( String clientOrderId )
   {
      this.clientOrderId = clientOrderId;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany);
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getTaxAmount()
   {
      return formatNumber( taxAmount );
   }

   public void setTaxAmount( String taxAmount )
   {
      this.taxAmount = taxAmount;
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
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

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
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

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
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
   
   
   // ���������������
   public String getDecodeBranch() throws KANException
   {
      // ��ó����е�BranchVO ����
      final List< BranchVO > branchVOs = KANConstants.getKANAccountConstants( getAccountId() ).BRANCH_VO;

      for ( BranchVO branchVO : branchVOs )
      {
         if ( this.branch.equals( branchVO.getBranchId() ) )
         {
            if ( getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return branchVO.getNameZH();
            }
            else
            {
               return branchVO.getNameEN();
            }

         }
      }
      return null;
   }

   // �������������
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   public String getPageFlag()
   {
      return pageFlag;
   }

   public void setPageFlag( String pageFlag )
   {
      this.pageFlag = pageFlag;
   }
}
