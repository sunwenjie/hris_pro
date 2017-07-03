package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�SBAdjustmentImportBatchVO  
 * ��������  
 * �����ˣ�steven  
 * ����ʱ�䣺2014-06-23  
 */
public class EmployeeInsuranceNoImportBatchVO extends BaseVO
{

   private static final long serialVersionUID = 6743204876767167744L;

   private String batchId;
   
   private String remark;
   
   private String importExcelName;

   private String cardnoId;
   
   // ��ԱId
   private String employeeId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   // ����Э��ID
   private String contractId;

   /**
    * For Application
    */
   // ������Ϣ�������ģ�
   private String contractNameZH;

   // ������Ϣ����Ӣ�ģ�
   private String contractNameEN;
   
   private String medicalNumber;

   private String sbNumber;

   private String fundNumber;

   private String solutionId;

   private String cbNumber;


   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.adjustment.import.status" ) );
   }

   @Override
   public void update( final Object object )
   {
      final EmployeeInsuranceNoImportHeaderVO employeeInsuranceNoImportHeaderVO = ( EmployeeInsuranceNoImportHeaderVO ) object;
      this.employeeId = employeeInsuranceNoImportHeaderVO.getEmployeeId();
      this.employeeNameZH = employeeInsuranceNoImportHeaderVO.getEmployeeNameZH();
      this.employeeNameEN = employeeInsuranceNoImportHeaderVO.getEmployeeNameEN();
      this.contractId = employeeInsuranceNoImportHeaderVO.getContractId();
      super.setStatus( employeeInsuranceNoImportHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.contractId = "";
      super.setStatus( "0" );
   }

   // ���ܹ�ԱID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }
   
   // ����BATCHID
   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
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

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
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

   public String getBatchId()
   {
      return batchId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( batchId );
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

   public String getCardnoId()
   {
      return cardnoId;
   }

   public void setCardnoId( String cardnoId )
   {
      this.cardnoId = cardnoId;
   }

   public String getMedicalNumber()
   {
      return medicalNumber;
   }

   public void setMedicalNumber( String medicalNumber )
   {
      this.medicalNumber = medicalNumber;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getFundNumber()
   {
      return fundNumber;
   }

   public void setFundNumber( String fundNumber )
   {
      this.fundNumber = fundNumber;
   }

   public String getSolutionId()
   {
      return solutionId;
   }

   public void setSolutionId( String solutionId )
   {
      this.solutionId = solutionId;
   }

   public String getCbNumber()
   {
      return cbNumber;
   }

   public void setCbNumber( String cbNumber )
   {
      this.cbNumber = cbNumber;
   }
}
