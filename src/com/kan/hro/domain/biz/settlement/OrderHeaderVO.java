package com.kan.hro.domain.biz.settlement;

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
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�HeaderVO  
 * ��������  
 * �����ˣ�Jack  
 * ����ʱ�䣺2013-9-11  
 */
public class OrderHeaderVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 6238025199774881228L;

   // Order����Id
   private String orderHeaderId;

   // �����Order����Id
   private String orderHeaderTempId;

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

   // ������ʼ����
   private String startDate;

   // ������������
   private String endDate;

   // ˰��Id
   private String taxId;

   // ˰�����ƣ����ģ�
   private String taxNameZH;

   // ˰�����ƣ�Ӣ�ģ�
   private String taxNameEN;

   // ˰�ʱ�ע
   private String taxRemark;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   // �����ܽ��
   private String orderAmount;

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

   // for ���㵥�����
   private String itemServiceFree;

   //���㵥��һ������ͳ�ƣ���ͬ��ͳ��
   private String employeeCount;

   private String contractCount;

   // ����ʵ��
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.entities = KANConstants.getKANAccountConstants( getAccountId() ).getEntities( getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( getAccountId() ).getBusinessTypes( getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( final Object object )
   {
      final OrderHeaderVO orderHeaderVO = ( OrderHeaderVO ) object;
      this.entityId = orderHeaderVO.getEntityId();
      this.businessTypeId = orderHeaderVO.getBusinessTypeId();
      this.batchId = orderHeaderVO.getBatchId();
      this.clientId = orderHeaderVO.getClientId();
      this.orderId = orderHeaderVO.getOrderId();
      this.taxId = orderHeaderVO.getTaxId();
      this.taxNameZH = orderHeaderVO.getTaxNameZH();
      this.taxNameEN = orderHeaderVO.getTaxNameEN();
      this.taxRemark = orderHeaderVO.getTaxRemark();
      this.billAmountPersonal = orderHeaderVO.getBillAmountPersonal();
      this.billAmountCompany = orderHeaderVO.getBillAmountCompany();
      this.costAmountPersonal = orderHeaderVO.getCostAmountPersonal();
      this.costAmountCompany = orderHeaderVO.getCostAmountCompany();
      this.orderAmount = orderHeaderVO.getOrderAmount();
      this.branch = orderHeaderVO.getBranch();
      this.owner = orderHeaderVO.getOwner();
      this.startDate = orderHeaderVO.getStartDate();
      this.endDate = orderHeaderVO.getEndDate();
      this.description = orderHeaderVO.getDescription();
      this.monthly = orderHeaderVO.getMonthly();
      super.setStatus( orderHeaderVO.getStatus() );
      super.setModifyBy( orderHeaderVO.getModifyBy() );
      super.setModifyDate( new Date() );
      super.setCorpId( orderHeaderVO.getCorpId() );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.clientId = "";
      this.orderId = "";
      this.taxId = "";
      this.taxNameZH = "";
      this.taxNameEN = "";
      this.taxRemark = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.orderAmount = "";
      this.branch = "";
      this.owner = "";
      this.startDate = "";
      this.endDate = "";
      this.description = "";
      this.monthly = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( orderHeaderId );
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

   // taxId����
   public String getEncodedTaxId() throws KANException
   {
      return encodedField( taxId );
   }

   // �·ݱ���
   public String getEncodedMonthly() throws KANException
   {
      return encodedField( this.monthly );
   }

   // ��÷���ʵ������
   public String getDecodeEntityId() throws KANException
   {
      return decodeField( entityId, entities );
   }

   // ���ҵ����������
   public String getDecodeBusinessTypeId() throws KANException
   {
      return decodeField( businessTypeId, businessTypes );
   }

   public String getOrderHeaderId()
   {
      return orderHeaderId;
   }

   public void setOrderHeaderId( String orderHeaderId )
   {
      this.orderHeaderId = orderHeaderId;
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

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getTaxId()
   {
      return taxId;
   }

   public void setTaxId( String taxId )
   {
      this.taxId = taxId;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public final String getOrderAmount()
   {
      return orderAmount;
   }

   public final void setOrderAmount( String orderAmount )
   {
      this.orderAmount = orderAmount;
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

   public String getTaxName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getTaxNameZH();
         }
         else
         {
            return this.getTaxNameEN();
         }
      }
      else
      {
         return this.getTaxNameZH();
      }
   }

   public String getTaxNameZH()
   {
      return taxNameZH;
   }

   public void setTaxNameZH( String taxNameZH )
   {
      this.taxNameZH = taxNameZH;
   }

   public String getTaxNameEN()
   {
      return taxNameEN;
   }

   public void setTaxNameEN( String taxNameEN )
   {
      this.taxNameEN = taxNameEN;
   }

   public String getTaxRemark()
   {
      return taxRemark;
   }

   public void setTaxRemark( String taxRemark )
   {
      this.taxRemark = taxRemark;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public void addBillAmountCompany( final String billAmountCompany )
   {
      if ( this.billAmountCompany == null || this.billAmountCompany.trim().equals( "" ) )
      {
         this.billAmountCompany = billAmountCompany;
      }
      else
      {
         this.billAmountCompany = String.valueOf( Double.valueOf( this.billAmountCompany ) + Double.valueOf( billAmountCompany ) );
      }
   }

   public void addBillAmountPersonal( String billAmountPersonal )
   {
      if ( this.billAmountPersonal == null || this.billAmountPersonal.trim().equals( "" ) )
      {
         this.billAmountPersonal = billAmountPersonal;
      }
      else
      {
         this.billAmountPersonal = String.valueOf( Double.valueOf( this.billAmountPersonal ) + Double.valueOf( billAmountPersonal ) );
      }
   }

   public void addCostAmountCompany( String costAmountCompany )
   {
      if ( this.costAmountCompany == null || this.costAmountCompany.trim().equals( "" ) )
      {
         this.costAmountCompany = costAmountCompany;
      }
      else
      {
         this.costAmountCompany = String.valueOf( Double.valueOf( this.costAmountCompany ) + Double.valueOf( costAmountCompany ) );
      }
   }

   public void addCostAmountPersonal( String costAmountPersonal )
   {
      if ( this.costAmountPersonal == null || this.costAmountPersonal.trim().equals( "" ) )
      {
         this.costAmountPersonal = costAmountPersonal;
      }
      else
      {
         this.costAmountPersonal = String.valueOf( Double.valueOf( this.costAmountPersonal ) + Double.valueOf( costAmountPersonal ) );
      }
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

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public final String getOrderHeaderTempId()
   {
      return orderHeaderTempId;
   }

   public final void setOrderHeaderTempId( String orderHeaderTempId )
   {
      this.orderHeaderTempId = orderHeaderTempId;
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

   public String getDecodeOrderDTOStatus() throws KANException
   {
      return "1".equals( getStatus() ) ? "�½�" : "�ѹ���";
   }

   public String getDecodeOrderDTOStatus4Page() throws KANException
   {
      return "&nbsp;<image src=\"images/appicons/excel_16.png\" onclick=\"exportMulit('" + getEncodedClientId() + "','" + getEncodedOrderId() + "','" + getStatus() + "','"
            + getEncodedMonthly() + "')\"  />";
   }

   public String getDesInfo() throws KANException
   {
      return "��Ա������" + this.employeeCount + "   Э��������" + this.contractCount;
   }

   public String getItemServiceFree()
   {
      return itemServiceFree;
   }

   public void setItemServiceFree( String itemServiceFree )
   {
      this.itemServiceFree = itemServiceFree;
   }

   public String getEmployeeCount()
   {
      return employeeCount;
   }

   public void setEmployeeCount( String employeeCount )
   {
      this.employeeCount = employeeCount;
   }

   public String getContractCount()
   {
      return contractCount;
   }

   public void setContractCount( String contractCount )
   {
      this.contractCount = contractCount;
   }

}
