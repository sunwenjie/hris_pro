package com.kan.hro.domain.biz.settlement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class SettlementAdjustmentImportBatchVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String batchId;

   private String remark;

   private String importExcelName;

   // header ����
   private String countHeaderId;

   private String countContractId;

   /**
    * For DB
    */
   // ��������ID�������Է���Э��Ϊ����
   private String adjustmentHeaderId;

   // ��Ӧ��ID
   private String vendorId;

   // ��Ӧ�����ƣ����ģ�
   private String vendorNameZH;

   // ��Ӧ�����ƣ�Ӣ�ģ�
   private String vendorNameEN;

   // �ͻ����
   private String clientNo;

   // �ͻ��������ģ�
   private String clientNameZH;

   // �ͻ�����Ӣ�ģ�
   private String clientNameEN;

   // ����ID
   private String orderId;

   // ����ʵ��ID
   private String entityId;

   // ҵ������ID
   private String businessTypeId;

   // ��ԱId
   private String employeeId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   // ��Ա�籣����ID
   private String employeeSBId;

   // ����Э��ID
   private String contractId;

   // �籣���˲��ֹ�˾�е�
   private String personalSBBurden;

   private String billAmountCompany;
   private String costAmountCompany;
   private String billAmountPersonal;
   private String costAmountPersonal;

   // �˵��·�
   private String monthly;

   // ����
   private String description;

   /**
    * For Application
    */

   // ������Ϣ�������ģ�
   private String contractNameZH;

   // ������Ϣ����Ӣ�ģ�
   private String contractNameEN;

   // �����������
   private String orderDescription;

   // �籣����
   private String sbSolutionId;

   // �籣��������
   private List< MappingVO > sbSolutions = new ArrayList< MappingVO >();

   // ����ʵ��
   private List< MappingVO > entities = new ArrayList< MappingVO >();

   // ҵ������
   private List< MappingVO > businessTypies = new ArrayList< MappingVO >();

   // ���12����(������)
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.settlement.adjustment.import.status" ) );
      this.entities = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypies = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );

      if ( this.entities != null )
      {
         entities.add( 0, getEmptyMappingVO() );
      }

      if ( this.businessTypies != null )
      {
         businessTypies.add( 0, getEmptyMappingVO() );
      }

      if ( this.monthlies != null )
      {
         monthlies.add( 0, getEmptyMappingVO() );
      }


   }

   @Override
   public void update( final Object object )
   {
   }

   @Override
   public void reset() throws KANException
   {
      this.vendorId = "";
      this.orderId = "";
      this.entityId = "0";
      this.businessTypeId = "0";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.employeeSBId = "";
      this.contractId = "";
      this.personalSBBurden = "0";
      this.billAmountCompany = "0";
      this.billAmountPersonal = "0";
      this.costAmountCompany = "0";
      this.costAmountPersonal = "0";
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   // ���ܹ�ԱID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   // ���ܶ���ID
   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   // ����BATCHID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

   // ���ܷ���Э��ID
   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   // �����籣����ID
   public String getEncodedEmployeeSBId() throws KANException
   {
      return encodedField( employeeSBId );
   }

   // ���뷨��ʵ��
   public String getDecodeLegalEntity()
   {
      return decodeField( entityId, entities );
   }

   // ����ҵ������
   public String getDecodeBusinessType()
   {
      return decodeField( businessTypeId, businessTypies );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentHeaderId );
   }

   public String getAdjustmentHeaderId()
   {
      return adjustmentHeaderId;
   }

   public void setAdjustmentHeaderId( String adjustmentHeaderId )
   {
      this.adjustmentHeaderId = adjustmentHeaderId;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public final String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public final void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getEntities()
   {
      return entities;
   }

   public void setEntities( List< MappingVO > entities )
   {
      this.entities = entities;
   }

   public List< MappingVO > getBusinessTypies()
   {
      return businessTypies;
   }

   public void setBusinessTypies( List< MappingVO > businessTypies )
   {
      this.businessTypies = businessTypies;
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

   public String getClientName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
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

   public String getContractName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getContractNameZH();
         }
         else
         {
            return this.getContractNameEN();
         }
      }
      else
      {
         return this.getContractNameZH();
      }
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getContractNameZH()
   {
      return contractNameZH;
   }

   public void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public String getContractNameEN()
   {
      return contractNameEN;
   }

   public void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
   }

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getOrderDescription()
   {
      return orderDescription;
   }

   public void setOrderDescription( String orderDescription )
   {
      this.orderDescription = orderDescription;
   }

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public List< MappingVO > getSbSolutions()
   {
      return sbSolutions;
   }

   public void setSbSolutions( List< MappingVO > sbSolutions )
   {
      this.sbSolutions = sbSolutions;
   }

   public String getDecodeSbSolutionId() throws KANException
   {
      return decodeField( this.sbSolutionId, sbSolutions );
   }

   public String getVendorNameZH()
   {
      return vendorNameZH;
   }

   public void setVendorNameZH( String vendorNameZH )
   {
      this.vendorNameZH = vendorNameZH;
   }

   public String getVendorNameEN()
   {
      return vendorNameEN;
   }

   public void setVendorNameEN( String vendorNameEN )
   {
      this.vendorNameEN = vendorNameEN;
   }

   public String getClientNo()
   {
      return clientNo;
   }

   public void setClientNo( String clientNo )
   {
      this.clientNo = clientNo;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getRemark()
   {
      return remark;
   }

   public void setRemark( String remark )
   {
      this.remark = remark;
   }

   public String getImportExcelName()
   {
      return importExcelName;
   }

   public void setImportExcelName( String importExcelName )
   {
      this.importExcelName = importExcelName;
   }

   public String getCountHeaderId()
   {
      return countHeaderId;
   }

   public void setCountHeaderId( String countHeaderId )
   {
      this.countHeaderId = countHeaderId;
   }

   public String getCountContractId()
   {
      return countContractId;
   }

   public void setCountContractId( String countContractId )
   {
      this.countContractId = countContractId;
   }

   public String getBillAmountCompany()
   {
      return billAmountCompany;
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountCompany()
   {
      return costAmountCompany;
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getBillAmountPersonal()
   {
      return billAmountPersonal;
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountPersonal()
   {
      return costAmountPersonal;
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

}
