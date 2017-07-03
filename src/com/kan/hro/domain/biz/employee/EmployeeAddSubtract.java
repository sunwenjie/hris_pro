package com.kan.hro.domain.biz.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * ����Ա����model Ա������ ���֤�� ��ǲЭ���� ��ͬ��ʼʱ�� ��ͬ����ʱ�� ���� ���� ������ʼʱ�� ��������ʱ�� ����
 * 
 * @author Jack Sun
 * 
 */
public class EmployeeAddSubtract extends BaseVO implements Serializable
{

   private static final long serialVersionUID = -4945675104482898612L;

   /*************** ������ʾ�� ***************/
   private String contractId;// ��ǲЭ����
   private String employeeId;
   private String employeeName;// ��Ա����
   private String salutation;// �Ա�
   private String sbNumber;// �Ͷ����Ͽ��ţ��籣����
   private String certificateNumber;
   private String residencyType;// ��������
   private String highestEducation;// ѧ��
   private String additionalPosition;// ְλ
   private String startDate;// ��ͬ��ʼʱ��
   private String endDate;// ��ͬ����ʱ��
   private String description;// ��ע

   private String cityNameZH;// ����
   private String residencyAddress;// ������ַ
   private String resignDate;// ��ְ����

   private String planStartDate;// �籣(�̱�)���ʱ��
   private String planEndDate;// �籣(�̱�)ͣ����ʱ��
   private String sbName;// ��������
   private String base;// �籣����

   private String number;// �������
   private String clientName;// �ͻ�����

   /*************** search condition ***************/
   /* �·ݡ����͡���Ա��š���Ա�������ͻ���š��ͻ����ơ��������� */

   // �·�
   private String month;

   // ����
   private String type;

   // �ͻ����
   private String clientId;

   // ��ѯ�������䣨ҳ�治��Ҫ��
   private String fromDate;
   private String toDate;

   // ���� 1 ��Ա 2 ��Ա
   private String opType;

   private List< MappingVO > last12Months = new ArrayList< MappingVO >();

   private List< MappingVO > opTypes = new ArrayList< MappingVO >();

   // ��ǲЭ��״̬
   private List< MappingVO > contractStatuses = new ArrayList< MappingVO >();

   // ��ǲЭ��״̬
   private List< MappingVO > sbStatuses = new ArrayList< MappingVO >();

   // ��ǲЭ��״̬
   private List< MappingVO > cbStatuses = new ArrayList< MappingVO >();

   // ����
   private List< MappingVO > types = new ArrayList< MappingVO >();
   //�Ա�
   private List< MappingVO > salutations = new ArrayList< MappingVO >();
   //ѧ��
   private List< MappingVO > highestEducations = new ArrayList< MappingVO >();

   // ��ǲЭ��״̬
   private String contractStatus;

   // �籣״̬
   private String sbStatus;

   // �̱�״̬
   private String cbStatus;

   private String ctop;//��ͬ����
   private String sbop;//�籣����
   private String cbop;//�̱�����

   public String getCtop()
   {
      if ( "5".equals( type ) )
      {
         ctop = type;
      }
      else
      {
         ctop = null;
      }
      return ctop;
   }

   public void setCtop( String ctop )
   {
      this.ctop = ctop;
   }

   public String getSbop()
   {
      if ( "1".equals( type ) || "2".equals( type ) || "3".equals( type ) || "4".equals( type ) )
      {
         sbop = type;
      }
      else
      {
         sbop = null;
      }
      return sbop;
   }

   public void setSbop( String sbop )
   {
      this.sbop = sbop;
   }

   public String getCbop()
   {
      if ( "6".equals( type ) )
      {
         cbop = type;
      }
      else
      {
         cbop = null;
      }
      return cbop;
   }

   public void setCbop( String cbop )
   {
      this.cbop = cbop;
   }

   public String getOpType()
   {
      return opType;
   }

   public void setOpType( String opType )
   {
      this.opType = opType;
   }

   public String getFromDate()
   {
      if ( StringUtils.isNotBlank( month ) )
      {
         fromDate = KANUtil.formatDate( KANUtil.getFirstDate( month ), "yyyy-MM-dd" );
      }
      return fromDate;
   }

   public void setFromDate( String fromDate )
   {
      this.fromDate = fromDate;
   }

   public String getToDate()
   {
      if ( StringUtils.isNotBlank( month ) )
      {
         toDate = KANUtil.formatDate( KANUtil.getLastDate( month ), "yyyy-MM-dd" );
      }
      return toDate;
   }

   public void setToDate( String toDate )
   {
      this.toDate = toDate;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getSalutation()
   {
      return salutation;
   }

   public void setSalutation( String salutation )
   {
      this.salutation = salutation;
   }

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getCertificateNumber()
   {
      return certificateNumber;
   }

   public void setCertificateNumber( String certificateNumber )
   {
      this.certificateNumber = certificateNumber;
   }

   public String getResidencyType()
   {
      return KANUtil.filterEmpty( residencyType ) == null ? "0" : residencyType;
   }

   public void setResidencyType( String residencyType )
   {
      this.residencyType = residencyType;
   }

   public String getHighestEducation()
   {
      return highestEducation;
   }

   public void setHighestEducation( String highestEducation )
   {
      this.highestEducation = highestEducation;
   }

   public String getAdditionalPosition()
   {
      return additionalPosition;
   }

   public void setAdditionalPosition( String additionalPosition )
   {
      this.additionalPosition = additionalPosition;
   }

   public String getStartDate()
   {
	  if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate)){
			 return KANUtil.formatDate( startDate, "yyyy-MM-dd" );
	  }
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
	  if(org.apache.commons.lang3.StringUtils.isNotBlank(endDate)){
			 return KANUtil.formatDate( endDate, "yyyy-MM-dd" );
	  }
      return endDate;
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

   public String getCityNameZH()
   {
      return cityNameZH;
   }

   public void setCityNameZH( String cityNameZH )
   {
      this.cityNameZH = cityNameZH;
   }

   public String getResidencyAddress()
   {
      return residencyAddress;
   }

   public void setResidencyAddress( String residencyAddress )
   {
      this.residencyAddress = residencyAddress;
   }

   public String getResignDate()
   {
	  if(org.apache.commons.lang3.StringUtils.isNotBlank(resignDate)){
			  return KANUtil.formatDate( resignDate, "yyyy-MM-dd" );
	  }
      return resignDate;
   }

   public void setResignDate( String resignDate )
   {
      this.resignDate = resignDate;
   }

   public String getPlanStartDate()
   {
	  if(org.apache.commons.lang3.StringUtils.isNotBlank(planStartDate)){
		  return KANUtil.formatDate( planStartDate, "yyyy-MM-dd" );
	  }
	  return planStartDate;
   }

   public void setPlanStartDate( String planStartDate )
   {
      this.planStartDate = planStartDate;
   }

   public String getPlanEndDate()
   {
	  if(org.apache.commons.lang3.StringUtils.isNotBlank(planEndDate)){
			  return KANUtil.formatDate( planEndDate, "yyyy-MM-dd" );
	  }
      return planEndDate;
   }

   public void setPlanEndDate( String planEndDate )
   {
      this.planEndDate = planEndDate;
   }

   public String getSbName()
   {
      return sbName;
   }

   public void setSbName( String sbName )
   {
      this.sbName = sbName;
   }

   public String getBase()
   {
	   if(org.apache.commons.lang3.StringUtils.isNotBlank(base)){
		   return formatNumber( base );
	   }
      return base ;
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getNumber()
   {
      return number;
   }

   public void setNumber( String number )
   {
      this.number = number;
   }

   public String getClientName()
   {
      return clientName;
   }

   public void setClientName( String clientName )
   {
      this.clientName = clientName;
   }

   public String getMonth()
   {
      return month;
   }

   public void setMonth( String month )
   {
      this.month = month;
   }

   public String getType()
   {
      return type;
   }

   public void setType( String type )
   {
      this.type = type;
   }

   public String getClientId()
   {
      return clientId;
   }

   public void setClientId( String clientId )
   {
      this.clientId = clientId;
   }

   public List< MappingVO > getLast12Months()
   {
      return last12Months;
   }

   public void setLast12Months( List< MappingVO > last12Months )
   {
      this.last12Months = last12Months;
   }

   public List< MappingVO > getOpTypes()
   {
      return opTypes;
   }

   public void setOpTypes( List< MappingVO > opTypes )
   {
      this.opTypes = opTypes;
   }

   public List< MappingVO > getSalutations()
   {
      return salutations;
   }

   public void setSalutations( List< MappingVO > salutations )
   {
      this.salutations = salutations;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return null;
   }

   @Override
   public void reset() throws KANException
   {

   }

   @Override
   public void update( Object object ) throws KANException
   {

   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.last12Months = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );
      this.contractStatuses.addAll( KANUtil.getMappings( this.getLocale(), "business.employee.contract.statuses" ) );
      this.sbStatuses.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.sb.statuses" ) );
      this.salutations = KANUtil.getMappings( this.getLocale(), "salutation" );
      this.highestEducations = KANConstants.getKANAccountConstants( super.getAccountId() ).getEducations( request.getLocale().getLanguage(), super.getCorpId() );

      Iterator< MappingVO > sbiterator = this.sbStatuses.iterator();
      while ( sbiterator.hasNext() )
      {
         MappingVO pappingVO = ( MappingVO ) sbiterator.next();
         if ( "0".equals( pappingVO.getMappingId() ) )
         {
            pappingVO.setMappingValue( "��ѡ��" );
            break;
         }
      }

      this.cbStatuses.addAll( KANUtil.getMappings( request.getLocale(), "business.employee.contract.cb.statuses" ) );

      Iterator< MappingVO > cbiterator = this.cbStatuses.iterator();
      while ( cbiterator.hasNext() )
      {
         MappingVO pappingVO = ( MappingVO ) cbiterator.next();
         if ( "0".equals( pappingVO.getMappingId() ) )
         {
            pappingVO.setMappingValue( "��ѡ��" );
            break;
         }
      }
      this.types.addAll( KANUtil.getMappings( this.getLocale(), "business.employee.addsubtract.type" ) );
      this.opTypes.addAll( KANUtil.getMappings( this.getLocale(), "business.employee.addsubtract.opTypes" ) );
   }

   public String getContractStatus()
   {
      return contractStatus;
   }

   public void setContractStatus( String contractStatus )
   {
      this.contractStatus = contractStatus;
   }

   public String getSbStatus()
   {
      return sbStatus;
   }

   public void setSbStatus( String sbStatus )
   {
      this.sbStatus = sbStatus;
   }

   public String getCbStatus()
   {
      return cbStatus;
   }

   public void setCbStatus( String cbStatus )
   {
      this.cbStatus = cbStatus;
   }

   public List< MappingVO > getContractStatuses()
   {
      return contractStatuses;
   }

   public void setContractStatuses( List< MappingVO > contractStatuses )
   {
      this.contractStatuses = contractStatuses;
   }

   public List< MappingVO > getSbStatuses()
   {
      return sbStatuses;
   }

   public void setSbStatuses( List< MappingVO > sbStatuses )
   {
      this.sbStatuses = sbStatuses;
   }

   public List< MappingVO > getCbStatuses()
   {
      return cbStatuses;
   }

   public void setCbStatuses( List< MappingVO > cbStatuses )
   {
      this.cbStatuses = cbStatuses;
   }

   public String getDecodeContractStatus()
   {
      return decodeField( contractStatus, contractStatuses );
   }

   public String getDecodeSBStatus()
   {
      return decodeField( sbStatus, sbStatuses );
   }

   public String getDecodeCBStatus()
   {
      return decodeField( sbStatus, cbStatuses );
   }

   public String getDecodeType()
   {
      return decodeField( type, types );
   }

   public String getDecodeOpType()
   {
      return decodeField( opType, opTypes );
   }

   public List< MappingVO > getTypes()
   {
      return types;
   }

   public void setTypes( List< MappingVO > types )
   {
      this.types = types;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public List< MappingVO > getHighestEducations()
   {
      return highestEducations;
   }

   public void setHighestEducations( List< MappingVO > highestEducations )
   {
      this.highestEducations = highestEducations;
   }

   public String getDecodeSalutation()
   {
      return decodeField( this.getSalutation(), this.getSalutations() );
   }

   public String getDecodeResidencyType()
   {
      return decodeField( this.getResidencyType(), KANUtil.getMappings( this.getLocale(), "sys.sb.residency" ) );
   }

   public String getDecodeHighestEducation()
   {
      return decodeField( highestEducation, highestEducations );
   }
}
