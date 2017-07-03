package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
 * 项目名称：HRO_V1  
 * 类名称：SBAdjustmentHeaderTempVO  
 * 类描述：  
 * 创建人：steven  
 * 创建时间：2014-06-23  
 */
public class EmployeeInsuranceNoImportHeaderVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -5231593240468576123L;

   private String batchId;

   private String cardnoId;

   // 雇员Id
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 服务协议ID
   private String contractId;

   // 派送信息名（中文）
   private String contractNameZH;

   // 派送信息名（英文）
   private String contractNameEN;

   private String medicalNumber;

   private String sbNumber;

   private String fundNumber;

   private String solutionId;

   private String cbNumber;

   private String remark1;
   
   private String solutionIdB;

   private String cbNumberB;

   private String hsNumber;
   
   private String certificateNumber;
   
   // 客户有效商保方案
   private List< MappingVO > solutions = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.adjustment.import.status" ) );
      solutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      solutions.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );
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
      this.medicalNumber = "";
      this.sbNumber = "";
      this.fundNumber = "";
      this.solutionId = "";
      this.cbNumber = "";
      super.setStatus( "0" );
   }

   // 加密雇员ID
   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   // 加密BATCHID
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
      return encodedField( cardnoId );
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

   public String getCardnoId()
   {
      return cardnoId;
   }

   public void setCardnoId( String cardnoId )
   {
      this.cardnoId = cardnoId;
   }

   public String getRemark1()
   {
      return remark1;
   }

   public void setRemark1( String remark1 )
   {
      this.remark1 = remark1;
   }
   
   public String getSolutionIdB()
   {
      return solutionIdB;
   }

   public void setSolutionIdB( String solutionIdB )
   {
      this.solutionIdB = solutionIdB;
   }

   public String getCbNumberB()
   {
      return cbNumberB;
   }

   public void setCbNumberB( String cbNumberB )
   {
      this.cbNumberB = cbNumberB;
   }

   public String getHsNumber()
   {
      return hsNumber;
   }

   public void setHsNumber( String hsNumber )
   {
      this.hsNumber = hsNumber;
   }

   public String getContractName(){
      
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
   
   public String getEmployeeName(){
      
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getEmployeeNameZH();
         }
         else
         {
            return this.getEmployeeNameEN();
         }
      }
      else
      {
         return this.getEmployeeNameZH();
      }
   }
   
   public String getDecodeSolutionId() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.solutionId, mappingVOs );
   }
   
   public String getDecodeSolutionIdB() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );

      return decodeField( this.solutionIdB, mappingVOs );
   }
   
   public String getCbName()
   {
      if ( solutions != null && solutions.size() > 0 )
      {
         for ( MappingVO mappingVO : solutions )
         {
            if ( mappingVO.getMappingId().equals( getSolutionId() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return null;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }
   
   
}
