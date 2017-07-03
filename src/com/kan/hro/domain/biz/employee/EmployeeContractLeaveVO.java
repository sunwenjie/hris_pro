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

   // 雇员休假方案Id
   private String employeeLeaveId;

   // 劳动合同Id
   private String contractId;

   // 科目Id（绑定设置 - 财务 - 科目）
   private String itemId;

   // 法定数量（小时）
   private String legalQuantity;

   // 福利数量（小时） 
   private String benefitQuantity;

   // 使用周期
   private String cycle;

   // 所属年份
   private String year;

   // 试用期是否可使用
   private String probationUsing;

   // 可以延迟使用
   private String delayUsing;

   // 法定假未使用完可延期月数（0表示不可延期）
   private String legalQuantityDelayMonth;

   // 福利假未使用完可延期月数（0表示不可延期）
   private String benefitQuantityDelayMonth;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 使用下一年的年假（仅当年年假为“0”时触发使用）
   private String useNextYearHours;

   // 数据来源(1：劳动合同；2：结算规则)
   private String dataFrom;

   // 之前法定数量剩余截止使用日期
   private String leftLastYearLegalQuantityEndDate;

   // 之前福利数量剩余截止使用日期
   private String leftLastYearBenefitQuantityEndDate;

   // 法定数量剩余（小时）
   private String leftLegalQuantity;

   // 福利数量剩余（小时）
   private String leftBenefitQuantity;

   // 劳动合同或派送协议名称（中文）
   private String contractNameZH;

   // 劳动合同或派送协议名称（英文）
   private String contractNameEN;

   // 科目名（中文） 
   private String itemNameZH;

   // 科目名（英文）
   private String itemNameEN;

   // 法定数量已用（小时）
   private String usedLegalQuantity;

   // 福利数量已用（小时）
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
   //   （<bean:write name="employeeContractLeaveVO" property="benefitQuantity" />小时，法定<bean:write name="employeeContractLeaveVO" property="legalQuantity" />小时）
   //   </logic:equal>
   //   <logic:notEqual name="employeeContractLeaveVO" property="itemId" value="41">
   //      <bean:write name="employeeContractLeaveVO" property="decodeItemId" />
   //      （<bean:write name="employeeContractLeaveVO" property="benefitQuantity" />小时）
   //   </logic:notEqual>

   public String getRemark()
   {
      String ret = "";
      if ( KANUtil.filterEmpty( itemId ) != null )
      {
         // 如果是年假
         if ( "41".equals( itemId ) )
         {
            if ( "zh".equalsIgnoreCase( super.getLocale().getLanguage() ) )
            {
               ret = year + getDecodeItemId() + "（ " + getBenefitQuantity() + " 小时，法定 " + getLegalQuantity() + " 小时 ）";
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
               ret = getDecodeItemId() + "（ " + getBenefitQuantity() + " 小时 ）";
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
