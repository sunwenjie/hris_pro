package com.kan.hro.domain.biz.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractSBVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */
   // ��Ա�籣����Id������
   private String employeeSBId;

   // �Ͷ���ͬId
   private String contractId;

   // �籣����Id�������� - ҵ�� - �籣������
   private String sbSolutionId;

   // ��Ӧ��Id
   private String vendorId;

   // ��Ӧ�̷���Id
   private String vendorServiceId;

   // ��˾�е������籣
   private String personalSBBurden;

   // �ӱ�����
   private String startDate;

   // �˱�����
   private String endDate;

   // ��Ҫ����ҽ����
   private String needMedicalCard;

   // ��Ҫ�����籣��
   private String needSBCard;

   // ҽ�����ʺ�
   private String medicalNumber;

   // �籣���ʺ�
   private String sbNumber;

   // �������ʺ�
   private String fundNumber;

   // �籣ʵ�ʽ��ɱ�ʶ
   private String flag;

   // ����
   private String description;

   // �籣����
   private String sbBase;

   /**
    * For Application
    */
   // ��Ӧ��������
   private String vendorNameZH;

   // ��Ӧ��Ӣ����
   private String vendorNameEN;

   // �Ͷ���ͬ������Э�����ƣ����ģ�
   private String contractNameZH;

   // �Ͷ���ͬ������Э�����ƣ�Ӣ�ģ�
   private String contractNameEN;

   // �Ͷ���ͬ������Э�����ƣ���ʼʱ�䣩
   private String contractStartDate;

   // �Ͷ���ͬ������Э�����ƣ�����ʱ�䣩
   private String contractEndDate;

   // ��λ��hro_tempBranchIds��
   private String department;

   // ��λ��hrm_tempPositionIds��
   private String positionId;

   // ����ID
   private String orderId;

   // ��ԱID
   private String employeeId;

   // ��Ա������
   private String employeeNameZH;

   // ��ԱӢ����
   private String employeeNameEN;

   // ��Ա֤������
   private String certificateNumber;

   // �ͷ���Ա��������Э�������ˣ�
   private String owner;

   // �ͻ�����(����)
   private String clientNameZH;

   // �ͻ�����(Ӣ��)
   private String clientNameEN;

   // ����Э��״̬
   private String contractStatus;

   // ǩԼ�����������
   private String orderDescription;
   @JsonIgnore
   // �ͻ���Ч�籣����
   private List< MappingVO > solutions = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ͬ״̬
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   private String solutionDetailIdArray[] = new String[] {};

   private String baseCompanyArray[] = new String[] {};

   private String basePersonalArray[] = new String[] {};

   // �Ƿ��޸��̱���������
   private String applyToAllHeader;

   // �Ƿ��޸��̱���������
   private String applyToAllDetail;

   // �籣״̬
   private String sbType;

   // �籣״̬������ʽ
   private String[] statusArray = new String[] {};
   @JsonIgnore
   // ����
   private List< MappingVO > branchs = new ArrayList< MappingVO >();
   @JsonIgnore
   // ְλ
   private List< MappingVO > positions = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��Ӧ�̷�������
   private List< MappingVO > serviceContents = new ArrayList< MappingVO >();

   // ��ͬ��ע
   private String contractDescription;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeSBId );
   }

   public String getDecodeSbSolutionId() throws KANException
   {
      List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
      // �����In House��¼
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // �����Hr Service��¼
      else
      {
         mappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage() ) );
      }
      // ���super���籣
      mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale() == null ? "ZH" : this.getLocale().getLanguage() ) );

      return decodeField( this.sbSolutionId, mappingVOs );
   }

   public String getDecodeContractStatus()
   {
      return decodeField( this.contractStatus, this.getContractStatuses() );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.sbSolutionId = "";
      this.vendorId = "0";
      this.vendorServiceId = "0";
      this.startDate = "";
      this.endDate = "";
      this.needMedicalCard = "0";
      this.needSBCard = "0";
      this.medicalNumber = "";
      this.sbNumber = "";
      this.fundNumber = "";
      this.flag = "";
      this.description = "";
      this.contractNameZH = "";
      this.contractNameEN = "";
      // Add By Jack at 2013-12-29
      this.contractId = "";
      this.orderId = "";
      this.employeeId = "";
      this.employeeNameZH = "";
      this.employeeNameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.contractStatus = "";
      this.sbBase = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "business.employee.contract.sb.statuses" ) );
      this.contractStatuses = KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" );
      this.solutions = KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( request.getLocale().getLanguage(), super.getCorpId() );

      if ( this.solutions != null )
      {
         this.solutions.add( 0, super.getEmptyMappingVO() );
      }

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

      this.serviceContents = KANUtil.getMappings( request.getLocale(), "business.vendor.service.item" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) object;
      this.contractId = employeeContractSBVO.getContractId();
      this.sbSolutionId = employeeContractSBVO.getSbSolutionId();
      this.vendorId = employeeContractSBVO.getVendorId();
      this.vendorServiceId = employeeContractSBVO.getVendorServiceId();
      this.personalSBBurden = employeeContractSBVO.getPersonalSBBurden();
      this.startDate = employeeContractSBVO.getStartDate();
      this.endDate = employeeContractSBVO.getEndDate();
      this.needMedicalCard = employeeContractSBVO.getNeedMedicalCard();
      this.needSBCard = employeeContractSBVO.getNeedSBCard();
      this.medicalNumber = employeeContractSBVO.getMedicalNumber();
      this.sbNumber = employeeContractSBVO.getSbNumber();
      this.fundNumber = employeeContractSBVO.getFundNumber();
      this.flag = employeeContractSBVO.getFlag();
      this.description = employeeContractSBVO.getDescription();
      this.solutionDetailIdArray = employeeContractSBVO.getSolutionDetailIdArray();
      this.baseCompanyArray = employeeContractSBVO.getBaseCompanyArray();
      this.basePersonalArray = employeeContractSBVO.getBasePersonalArray();
      this.sbBase = employeeContractSBVO.getSbBase();
      super.setStatus( employeeContractSBVO.getStatus() );
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
   }

   public String getEncodedVendorId() throws KANException
   {
      return encodedField( vendorId );
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

   public String getSbSolutionId()
   {
      return sbSolutionId;
   }

   public void setSbSolutionId( String sbSolutionId )
   {
      this.sbSolutionId = sbSolutionId;
   }

   public String[] getSolutionDetailIdArray()
   {
      return solutionDetailIdArray;
   }

   public void setSolutionDetailIdArray( String[] solutionDetailIdArray )
   {
      this.solutionDetailIdArray = solutionDetailIdArray;
   }

   public String[] getBaseCompanyArray()
   {
      return baseCompanyArray;
   }

   public void setBaseCompanyArray( String[] baseCompanyArray )
   {
      this.baseCompanyArray = baseCompanyArray;
   }

   public String[] getBasePersonalArray()
   {
      return basePersonalArray;
   }

   public void setBasePersonalArray( String[] basePersonalArray )
   {
      this.basePersonalArray = basePersonalArray;
   }

   public String getVendorServiceId()
   {
      return vendorServiceId;
   }

   public void setVendorServiceId( String vendorServiceId )
   {
      this.vendorServiceId = vendorServiceId;
   }

   public String getNeedMedicalCard()
   {
      return KANUtil.filterEmpty( needMedicalCard );
   }

   public void setNeedMedicalCard( String needMedicalCard )
   {
      this.needMedicalCard = needMedicalCard;
   }

   public String getNeedSBCard()
   {
      return KANUtil.filterEmpty( needSBCard );
   }

   public void setNeedSBCard( String needSBCard )
   {
      this.needSBCard = needSBCard;
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

   public final String getFlag()
   {
      return flag;
   }

   public final void setFlag( String flag )
   {
      this.flag = flag;
   }

   public final String getContractNameZH()
   {
      return contractNameZH;
   }

   public final void setContractNameZH( String contractNameZH )
   {
      this.contractNameZH = contractNameZH;
   }

   public final String getContractNameEN()
   {
      return contractNameEN;
   }

   public final void setContractNameEN( String contractNameEN )
   {
      this.contractNameEN = contractNameEN;
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

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
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

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getEncodedEmployeeSBId() throws KANException
   {
      return encodedField( employeeSBId );
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public String getApplyToAllHeader()
   {
      return applyToAllHeader;
   }

   public void setApplyToAllHeader( String applyToAllHeader )
   {
      this.applyToAllHeader = applyToAllHeader;
   }

   public String getApplyToAllDetail()
   {
      return applyToAllDetail;
   }

   public void setApplyToAllDetail( String applyToAllDetail )
   {
      this.applyToAllDetail = applyToAllDetail;
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

   public final String getVendorName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getVendorNameZH();
         }
         else
         {
            return this.getVendorNameEN();
         }
      }
      else
      {
         return this.getVendorNameZH();
      }
   }

   public List< MappingVO > getSolutions()
   {
      return solutions;
   }

   public void setSolutions( List< MappingVO > solutions )
   {
      this.solutions = solutions;
   }

   public String getSbName()
   {
      if ( solutions != null && solutions.size() > 0 )
      {
         for ( MappingVO mappingVO : solutions )
         {
            if ( mappingVO.getMappingId().equals( getSbSolutionId() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return null;
   }

   public String getPersonalSBBurden()
   {
      return personalSBBurden;
   }

   public void setPersonalSBBurden( String personalSBBurden )
   {
      this.personalSBBurden = personalSBBurden;
   }

   public final String getSbType()
   {
      return sbType;
   }

   public void setSbType( String sbType )
   {
      this.sbType = sbType;
   }

   public String[] getStatusArray()
   {
      return statusArray;
   }

   public void setStatusArray( String[] statusArray )
   {
      this.statusArray = statusArray;
   }

   public String getOrderDescription()
   {
      return orderDescription;
   }

   public void setOrderDescription( String orderDescription )
   {
      this.orderDescription = orderDescription;
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

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
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

   public List< MappingVO > getServiceContents()
   {
      return serviceContents;
   }

   public void setServiceContents( List< MappingVO > serviceContents )
   {
      this.serviceContents = serviceContents;
   }

   // ���빫˾�е������籣
   public String getDecodePersonalSBBurden()
   {
      return decodeField( personalSBBurden, super.getFlags() );
   }

   // ������Ҫ��ҽ����
   public String getDecodeNeedMedicalCard()
   {
      return decodeField( needMedicalCard, super.getFlags() );
   }

   // ���빫˾�е������籣
   public String getDecodeNeedSBCard()
   {
      return decodeField( needSBCard, super.getFlags() );
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

   // ���빩Ӧ�̷�������
   public String getDecodeVendorServiceIds()
   {
      String returnString = "";
      if ( this.serviceContents != null && vendorServiceId != null )
      {
         final String serviceArray[] = KANUtil.jasonArrayToString( vendorServiceId ).split( "," );

         for ( String arrayItem : serviceArray )
         {
            for ( MappingVO mappingVO : this.serviceContents )
            {
               if ( mappingVO.getMappingId().equals( arrayItem ) )
               {
                  if ( returnString.equals( "" ) )
                  {
                     returnString = mappingVO.getMappingValue();
                  }
                  else
                  {
                     returnString = returnString + " + " + mappingVO.getMappingValue();
                  }
                  break;
               }
            }
         }
      }

      return returnString;
   }

   public String getSbBase()
   {
      return KANUtil.filterEmpty( sbBase ) == null ? "0" : sbBase;
   }

   public void setSbBase( String sbBase )
   {
      this.sbBase = sbBase;
   }

   public String getContractDescription()
   {
      return contractDescription;
   }

   public void setContractDescription( String contractDescription )
   {
      this.contractDescription = contractDescription;
   }

   public String getSubStrContractDescription()
   {
      String result = "";
      if ( KANUtil.filterEmpty( contractDescription ) != null )
      {
         if ( contractDescription.length() > 20 )
         {
            result = contractDescription.substring( 0, 20 ) + "...";

         }
         else
         {
            result = contractDescription;
         }
      }
      return result;
   }
}
