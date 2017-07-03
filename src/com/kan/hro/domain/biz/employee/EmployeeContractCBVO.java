package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractCBVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */
   // ��Ա�̱�����Id������
   private String employeeCBId;

   // �Ͷ���ͬid
   private String contractId;

   // �̱�����Id
   private String solutionId;

   // �ӱ�����
   private String startDate;

   // �˱�����
   private String endDate;

   // ��ȫ����ѣ���/��
   private String freeShortOfMonth;

   // ��ȫ�¼Ʒѣ���/��
   private String chargeFullMonth;

   // ����
   private String description;

   // �̱����ʺ�
   private String cbNumber;

   /**
    * For Application
    */
   // ����Э�����ƣ����ģ�
   private String contractNameZH;

   // ����Э�����ƣ�Ӣ�ģ�
   private String contractNameEN;

   // �Ͷ���ͬ������Э�����ƣ���ʼʱ�䣩
   private String contractStartDate;

   // �Ͷ���ͬ������Э�����ƣ�����ʱ�䣩
   private String contractEndDate;

   // ��λ��hro_tempBranchIds��
   private String department;

   // ��λ��hrm_tempPositionIds��
   private String positionId;

   // �ͷ���Ա��������Э�������ˣ�
   private String owner;

   // ����ID
   private String orderId;

   // ��ԱID
   private String employeeId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   // ֤������
   private String certificateNumber;

   // �ͻ�����(����)
   private String clientNameZH;

   // �ͻ�����(Ӣ��)
   private String clientNameEN;

   // ����Э��״̬
   private String contractStatus;

   // �Ƿ��޸��̱���������
   private String applyToAllHeader;
   @JsonIgnore
   // �ͻ���Ч�̱�����
   private List< MappingVO > solutions = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ͻ���Ч ��ǲ��Ϣ/�Ͷ���ͬ ����
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();
   @JsonIgnore
   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // ְλ
   private List< MappingVO > positions = new ArrayList< MappingVO >();

   //��ѯʹ�� �̱���������
   private String solutionNameZH;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeCBId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getDecodeSolutionId() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

      // �����In House��¼
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale() == null ? "ZH"
               : this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // �����Hr Service��¼
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale() == null ? "ZH"
               : this.getLocale().getLanguage() ) );
      }
      // ���super���籣
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage() ) );

      return decodeField( this.solutionId, mappingVOs );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.solutionId = "";
      this.startDate = "";
      this.endDate = "";
      this.freeShortOfMonth = "0";
      this.chargeFullMonth = "0";
      this.description = "";
      // Add By Jack at 2013-12-29
      this.orderId = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.contractStatus = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.employee.contract.cb.statuses" ) );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      solutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getCommercialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      // ���Super���籣
      solutions.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( this.getLocale().getLanguage() ) );

      this.branchs = KANConstants.getKANAccountConstants( getAccountId() ).getBranchs( request.getLocale().getLanguage(), super.getCorpId() );
      // �ⲿְλ
      if ( KANConstants.ROLE_HR_SERVICE.equals( this.getRole() ) )
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getEmployeePositions( request.getLocale().getLanguage() );
      }
      // �ڲ�ְλ
      else
      {
         this.positions = KANConstants.getKANAccountConstants( super.getAccountId() ).getPositions( request.getLocale().getLanguage(), super.getCorpId() );
      }

   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractCBVO employeeContractSBSolutionVO = ( EmployeeContractCBVO ) object;
      this.contractId = employeeContractSBSolutionVO.getContractId();
      this.solutionId = employeeContractSBSolutionVO.getSolutionId();
      this.startDate = employeeContractSBSolutionVO.getStartDate();
      this.endDate = employeeContractSBSolutionVO.getEndDate();
      this.freeShortOfMonth = employeeContractSBSolutionVO.getFreeShortOfMonth();
      this.chargeFullMonth = employeeContractSBSolutionVO.getChargeFullMonth();
      this.description = employeeContractSBSolutionVO.getDescription();
      this.cbNumber = employeeContractSBSolutionVO.getCbNumber();
      super.setStatus( employeeContractSBSolutionVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeCBId()
   {
      return employeeCBId;
   }

   public void setEmployeeCBId( String employeeCBId )
   {
      this.employeeCBId = employeeCBId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public String getStartDate()
   {
      return this.startDate = KANUtil.filterEmpty( decodeDate( this.startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return this.endDate = KANUtil.filterEmpty( decodeDate( this.endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getSolutionId()
   {
      return solutionId;
   }

   public void setSolutionId( String solutionId )
   {
      this.solutionId = solutionId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getFreeShortOfMonth()
   {
      return KANUtil.filterEmpty( freeShortOfMonth );
   }

   public void setFreeShortOfMonth( String freeShortOfMonth )
   {
      this.freeShortOfMonth = freeShortOfMonth;
   }

   public String getChargeFullMonth()
   {
      return KANUtil.filterEmpty( chargeFullMonth );
   }

   public void setChargeFullMonth( String chargeFullMonth )
   {
      this.chargeFullMonth = chargeFullMonth;
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

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
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

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
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
         return this.getClientNameEN();
      }
   }

   public String getContractName()
   {
      if ( this.getLocale() != null )
      {
         if ( this.getLocale().getLanguage() != null && this.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            return contractNameZH;
         }
         else
         {
            return contractNameEN;
         }
      }
      else
      {
         return contractNameZH;
      }
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public String getEncodedEmployeeId() throws KANException
   {
      return encodedField( employeeId );
   }

   public String getEncodedCorpId() throws KANException
   {
      return encodedField( super.getCorpId() );
   }

   public String getEncodedOrderId() throws KANException
   {
      return encodedField( orderId );
   }

   public String getEncodedEmployeeSBId() throws KANException
   {
      return encodedField( employeeCBId );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( this.contractStatus, this.getContractStatuses() );
   }

   public String getApplyToAllHeader()
   {
      return applyToAllHeader;
   }

   public void setApplyToAllHeader( String applyToAllHeader )
   {
      this.applyToAllHeader = applyToAllHeader;
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

   public List< MappingVO > getSolutions()
   {
      return solutions;
   }

   public void setSolutions( List< MappingVO > solutions )
   {
      this.solutions = solutions;
   }

   public List< MappingVO > getBranchs()
   {
      return branchs;
   }

   public void setBranchs( List< MappingVO > branchs )
   {
      this.branchs = branchs;
   }

   public List< MappingVO > getPositions()
   {
      return positions;
   }

   public void setPositions( List< MappingVO > positions )
   {
      this.positions = positions;
   }

   public String getContractStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractStartDate ) );
   }

   public void setContractStartDate( String contractStartDate )
   {
      this.contractStartDate = contractStartDate;
   }

   public String getContractEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( this.contractEndDate ) );
   }

   public void setContractEndDate( String contractEndDate )
   {
      this.contractEndDate = contractEndDate;
   }

   public String getDepartment()
   {
      return department;
   }

   public void setDepartment( String department )
   {
      this.department = department;
   }

   public String getPositionId()
   {
      return positionId;
   }

   public void setPositionId( String positionId )
   {
      this.positionId = positionId;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   // ��������Э��������
   public String getDecodeOwner() throws KANException
   {
      return KANConstants.getKANAccountConstants( getAccountId() ).getStaffNamesByPositionId( getLocale().getLanguage(), this.owner );
   }

   // �����λ��hrm_tempPositionIds��
   public String getDecodePositionId()
   {
      String returnString = "";
      if ( KANUtil.filterEmpty( positionId ) != null )
      {
         final String[] positionArray = positionId.split( "," );
         if ( positionArray != null && positionArray.length > 0 )
         {
            for ( String position : positionArray )
            {
               if ( KANUtil.filterEmpty( returnString ) == null )
               {
                  returnString = decodeField( position, positions );
               }
               else
               {
                  returnString = returnString + "��" + decodeField( position, positions );
               }
            }
         }
      }

      return returnString;
   }

   // ���뵥λ��hrm_tempBranchIds��
   public String getDecodeDepartment() throws KANException
   {
      if ( KANUtil.filterEmpty( super.getCorpId() ) == null )
      {
         return department;
      }
      else
      {
         String returnStr = "";
         if ( KANUtil.filterEmpty( department ) != null && branchs != null && branchs.size() > 0 )
         {
            for ( String branchId : department.split( "," ) )
            {
               if ( KANUtil.filterEmpty( returnStr ) == null )
               {
                  returnStr = decodeField( branchId, branchs, true );
               }
               else
               {
                  returnStr = returnStr + "��" + decodeField( branchId, branchs, true );
               }
            }
         }

         return returnStr;
      }
   }

   // ���벻ȫ����ѣ���/��
   public String getDecodeFreeShortOfMonth()
   {
      return decodeField( freeShortOfMonth, super.getFlags() );
   }

   // ���밴ȫ�¼Ʒѣ���/��
   public String getDecodeChargeFullMonth()
   {
      return decodeField( chargeFullMonth, super.getFlags() );
   }

   public String getCbNumber()
   {
      return cbNumber;
   }

   public void setCbNumber( String cbNumber )
   {
      this.cbNumber = cbNumber;
   }

   public String getSolutionNameZH()
   {
      return solutionNameZH;
   }

   public void setSolutionNameZH( String solutionNameZH )
   {
      this.solutionNameZH = solutionNameZH;
   }

   public String getDecodeRemark() throws KANException
   {
      if ( KANUtil.filterEmpty( this.solutionId ) != null )
      {
         String returnStr = getDecodeSolutionId();
         if ( KANUtil.filterEmpty( this.startDate ) != null )
         {
            returnStr = returnStr + " " + getStartDate();
         }

         if ( KANUtil.filterEmpty( this.endDate ) != null )
         {
            returnStr = returnStr + " ~ " + getEndDate();
         }

         if ( KANUtil.filterEmpty( super.getStatus(), "0" ) != null )
         {
            returnStr = returnStr + "��" + super.getDecodeStatus() + "��";
         }

         return returnStr;
      }

      return "";
   }

}
