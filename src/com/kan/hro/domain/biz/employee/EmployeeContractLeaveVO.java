package com.kan.hro.domain.biz.employee;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanUtils;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class EmployeeContractLeaveVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3986770799631492651L;

   /**
    * For DB
    */

   // ��Ա�ݼٷ���Id
   private String employeeLeaveId;

   // �Ͷ���ͬId
   private String contractId;

   // ��ĿId�������� - ���� - ��Ŀ��
   private String itemId;

   // ����������Сʱ��
   private String legalQuantity;

   // ����������Сʱ�� 
   private String benefitQuantity;

   // ʹ������
   private String cycle;

   // �������
   private String year;

   // �������Ƿ��ʹ��
   private String probationUsing;

   // �����ӳ�ʹ��
   private String delayUsing;

   // ������δʹ���������������0��ʾ�������ڣ�
   private String legalQuantityDelayMonth;

   // ������δʹ���������������0��ʾ�������ڣ�
   private String benefitQuantityDelayMonth;

   // ����
   private String description;

   /**
    * For Application
    */
   // ʹ����һ�����٣����������Ϊ��0��ʱ����ʹ�ã�
   private String useNextYearHours;

   // ������Դ(1���Ͷ���ͬ��2���������)
   private String dataFrom;

   // ֮ǰ��������ʣ���ֹʹ������
   private String leftLastYearLegalQuantityEndDate;

   // ֮ǰ��������ʣ���ֹʹ������
   private String leftLastYearBenefitQuantityEndDate;

   // ��������ʣ�ࣨСʱ��
   private String leftLegalQuantity;

   // ��������ʣ�ࣨСʱ��
   private String leftBenefitQuantity;

   // �Ͷ���ͬ������Э�����ƣ����ģ�
   private String contractNameZH;

   // �Ͷ���ͬ������Э�����ƣ�Ӣ�ģ�
   private String contractNameEN;

   // ��Ŀ�������ģ� 
   private String itemNameZH;

   // ��Ŀ����Ӣ�ģ�
   private String itemNameEN;

   // �����������ã�Сʱ��
   private String usedLegalQuantity;

   // �����������ã�Сʱ��
   private String usedBenefitQuantity;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( employeeLeaveId );
   }

   public String getEncodedContractId() throws KANException
   {
      return encodedField( contractId );
   }

   public String getDecodeItemId()
   {
      return decodeField( this.itemId, KANConstants.getKANAccountConstants( super.getAccountId() ).getItems( this.getLocale().getLanguage(), super.getCorpId() ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.contractId = "";
      this.itemId = "0";
      this.legalQuantity = "";
      this.benefitQuantity = "";
      this.cycle = "0";
      this.probationUsing = "0";
      this.delayUsing = "0";
      this.legalQuantityDelayMonth = "0";
      this.benefitQuantityDelayMonth = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final String ignoreProperties[] = { "employeeLeaveId", "deleted", "createBy", "createDate" };
      BeanUtils.copyProperties( object, this, ignoreProperties );
      super.setModifyDate( new Date() );
   }

   public String getEmployeeLeaveId()
   {
      return employeeLeaveId;
   }

   public void setEmployeeLeaveId( String employeeLeaveId )
   {
      this.employeeLeaveId = employeeLeaveId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getLegalQuantity()
   {
      return KANUtil.filterEmpty( formatNumber( legalQuantity ) );
   }

   public void setLegalQuantity( String legalQuantity )
   {
      this.legalQuantity = legalQuantity;
   }

   public String getBenefitQuantity()
   {
      return KANUtil.filterEmpty( formatNumber( benefitQuantity ) );
   }

   public void setBenefitQuantity( String benefitQuantity )
   {
      this.benefitQuantity = benefitQuantity;
   }

   public String getCycle()
   {
      return KANUtil.filterEmpty( cycle );
   }

   public void setCycle( String cycle )
   {
      this.cycle = cycle;
   }

   public final String getProbationUsing()
   {
      return KANUtil.filterEmpty( probationUsing );
   }

   public final void setProbationUsing( String probationUsing )
   {
      this.probationUsing = probationUsing;
   }

   public String getDelayUsing()
   {
      return KANUtil.filterEmpty( delayUsing );
   }

   public void setDelayUsing( String delayUsing )
   {
      this.delayUsing = delayUsing;
   }

   public String getLegalQuantityDelayMonth()
   {
      return KANUtil.filterEmpty( legalQuantityDelayMonth );
   }

   public void setLegalQuantityDelayMonth( String legalQuantityDelayMonth )
   {
      this.legalQuantityDelayMonth = legalQuantityDelayMonth;
   }

   public String getBenefitQuantityDelayMonth()
   {
      return KANUtil.filterEmpty( benefitQuantityDelayMonth );
   }

   public void setBenefitQuantityDelayMonth( String benefitQuantityDelayMonth )
   {
      this.benefitQuantityDelayMonth = benefitQuantityDelayMonth;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getLeftLegalQuantity()
   {
      return KANUtil.filterEmpty( formatNumber( leftLegalQuantity ) );
   }

   public void setLeftLegalQuantity( String leftLegalQuantity )
   {
      this.leftLegalQuantity = leftLegalQuantity;
   }

   public String getLeftBenefitQuantity()
   {
      return KANUtil.filterEmpty( formatNumber( leftBenefitQuantity ) );
   }

   public void setLeftBenefitQuantity( String leftBenefitQuantity )
   {
      this.leftBenefitQuantity = leftBenefitQuantity;
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

   public String getItemNameZH()
   {
      return itemNameZH;
   }

   public void setItemNameZH( String itemNameZH )
   {
      this.itemNameZH = itemNameZH;
   }

   public String getItemNameEN()
   {
      return itemNameEN;
   }

   public void setItemNameEN( String itemNameEN )
   {
      this.itemNameEN = itemNameEN;
   }

   public String getUsedLegalQuantity()
   {
      return usedLegalQuantity;
   }

   public void setUsedLegalQuantity( String usedLegalQuantity )
   {
      this.usedLegalQuantity = usedLegalQuantity;
   }

   public String getUsedBenefitQuantity()
   {
      return usedBenefitQuantity;
   }

   public void setUsedBenefitQuantity( String usedBenefitQuantity )
   {
      this.usedBenefitQuantity = usedBenefitQuantity;
   }

   public String getDataFrom()
   {
      return dataFrom;
   }

   public void setDataFrom( String dataFrom )
   {
      this.dataFrom = dataFrom;
   }

   public String getYear()
   {
      return KANUtil.filterEmpty( year );
   }

   public void setYear( String year )
   {
      this.year = year;
   }

   public String getLeftLastYearLegalQuantityEndDate()
   {
      return KANUtil.formatDate( leftLastYearLegalQuantityEndDate, "yyyy-MM-dd", true );
   }

   public void setLeftLastYearLegalQuantityEndDate( String leftLastYearLegalQuantityEndDate )
   {
      this.leftLastYearLegalQuantityEndDate = leftLastYearLegalQuantityEndDate;
   }

   public String getLeftLastYearBenefitQuantityEndDate()
   {
      return KANUtil.formatDate( leftLastYearBenefitQuantityEndDate, "yyyy-MM-dd", true );
   }

   public void setLeftLastYearBenefitQuantityEndDate( String leftLastYearBenefitQuantityEndDate )
   {
      this.leftLastYearBenefitQuantityEndDate = leftLastYearBenefitQuantityEndDate;
   }

   //   <logic:equal name="employeeContractLeaveVO" property="itemId" value="41">
   //   <bean:write name="employeeContractLeaveVO" property="year" />
   //   <bean:write name="employeeContractLeaveVO" property="decodeItemId" /> 
   //   ��<bean:write name="employeeContractLeaveVO" property="benefitQuantity" />Сʱ������<bean:write name="employeeContractLeaveVO" property="legalQuantity" />Сʱ��
   //   </logic:equal>
   //   <logic:notEqual name="employeeContractLeaveVO" property="itemId" value="41">
   //      <bean:write name="employeeContractLeaveVO" property="decodeItemId" />
   //      ��<bean:write name="employeeContractLeaveVO" property="benefitQuantity" />Сʱ��
   //   </logic:notEqual>

   public String getRemark()
   {
      String ret = "";
      if ( KANUtil.filterEmpty( itemId ) != null )
      {
         // ��������
         if ( "41".equals( itemId ) )
         {
            if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
            {
               ret = year + getDecodeItemId() + "�� " + getBenefitQuantity() + " Сʱ������ " + getLegalQuantity() + " Сʱ ��";
            }
            else
            {
               ret = year + " " + getDecodeItemId() + " ( " + getBenefitQuantity() + " hours, Legal: " + getLegalQuantity() + " hours)";
            }
         }
         else
         {
            if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
            {
               ret = getDecodeItemId() + "�� " + getBenefitQuantity() + " Сʱ ��";
            }
            else
            {
               ret = getDecodeItemId() + " ( " + getBenefitQuantity() + " hours )";
            }
         }
      }

      return ret;
   }

   public String getUseNextYearHours()
   {
      return useNextYearHours;
   }

   public void setUseNextYearHours( String useNextYearHours )
   {
      this.useNextYearHours = useNextYearHours;
   }

}
