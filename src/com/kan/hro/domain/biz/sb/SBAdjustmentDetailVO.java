package com.kan.hro.domain.biz.sb;

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

/**  
 * 项目名称：HRO_V1  
 * 类名称：SBAdjustmentDetailVO  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2014-5-7  
 */
public class SBAdjustmentDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = 6745642266505878034L;

   /**
    * For DB
    */
   // 调整从表Id
   private String adjustmentDetailId;

   // 社保主表Id
   private String adjustmentHeaderId;

   // 科目Id
   private String itemId;

   // 科目中文名
   private String nameZH;

   // 科目英文名
   private String nameEN;

   // 合计（公司）
   private String amountCompany;

   // 合计（个人）
   private String amountPersonal;

   // 账单月份
   private String monthly;

   // 所属月份
   private String accountMonthly;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 订单 ID
   private String orderId;

   // 劳动合同 ID
   private String contractId;

   // 法务实体 ID
   private String entityId;

   // 业务类型 ID
   private String businessTypeId;

   // 客户编号
   private String clientNo;

   // 客户中文名
   private String clientNameZH;

   // 客户英文名
   private String clientNameEN;

   // 雇员ID
   private String employeeId;

   // 雇员中文名
   private String employeeNameZH;

   // 雇员英文名
   private String employeeNameEN;

   // 社保卡号
   private String sbNumber;

   // 社保方案
   private String sbSolutionId;
   @JsonIgnore
   // 社保方案集合
   private List< MappingVO > sbSolutions = new ArrayList< MappingVO >();
   @JsonIgnore
   // 科目
   private List< MappingVO > items = new ArrayList< MappingVO >();
   @JsonIgnore
   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 雇员社保方案ID
   private String employeeSBId;

   // 科目编号
   private String itemNo;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlies = KANConstants.getKANAccountConstants( super.getAccountId() ).getLast12Months( request.getLocale().getLanguage() );

      if ( this.monthlies != null && this.monthlies.size() > 0 )
      {
         this.monthlies.add( 0, getEmptyMappingVO() );
      }

      sbSolutions.add( 0, getEmptyMappingVO() );

      // 如果是In House登录
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         sbSolutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // 如果是Hr Service登录
      else
      {
         sbSolutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // 添加super的社保
      sbSolutions.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
   }

   @Override
   public void update( final Object object )
   {
      final SBAdjustmentDetailVO sbAdjustmentDetailVO = ( SBAdjustmentDetailVO ) object;
      this.adjustmentHeaderId = sbAdjustmentDetailVO.getAdjustmentHeaderId();
      this.itemId = sbAdjustmentDetailVO.getItemId();
      this.nameZH = sbAdjustmentDetailVO.getNameZH();
      this.nameEN = sbAdjustmentDetailVO.getNameEN();
      this.amountCompany = sbAdjustmentDetailVO.getAmountCompany();
      this.amountPersonal = sbAdjustmentDetailVO.getAmountPersonal();
      this.accountMonthly = sbAdjustmentDetailVO.getAccountMonthly();
      this.description = sbAdjustmentDetailVO.getDescription();
      super.setStatus( sbAdjustmentDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.adjustmentHeaderId = "";
      this.itemId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.amountCompany = "";
      this.amountPersonal = "";
      this.monthly = "";
      this.accountMonthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentDetailId );
   }

   public String getAdjustmentDetailId()
   {
      return adjustmentDetailId;
   }

   public void setAdjustmentDetailId( String adjustmentDetailId )
   {
      this.adjustmentDetailId = adjustmentDetailId;
   }

   public String getAdjustmentHeaderId()
   {
      return adjustmentHeaderId;
   }

   public void setAdjustmentHeaderId( String adjustmentHeaderId )
   {
      this.adjustmentHeaderId = adjustmentHeaderId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getAmountCompany()
   {
      return formatNumber( amountCompany );
   }

   public void setAmountCompany( String amountCompany )
   {
      this.amountCompany = amountCompany;
   }

   public String getAmountPersonal()
   {
      return formatNumber( amountPersonal );
   }

   public void setAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = amountPersonal;
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

   public List< MappingVO > getItems()
   {
      return items;
   }

   public void setItems( List< MappingVO > items )
   {
      this.items = items;
   }

   public List< MappingVO > getMonthlies()
   {
      return monthlies;
   }

   public void setMonthlies( List< MappingVO > monthlies )
   {
      this.monthlies = monthlies;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public final String getAccountMonthly()
   {
      return accountMonthly;
   }

   public final void setAccountMonthly( String accountMonthly )
   {
      this.accountMonthly = accountMonthly;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public void addAmountCompany( String amountCompany )
   {
      this.amountCompany = String.valueOf( Double.valueOf( this.amountCompany == null ? "0" : this.amountCompany ) + Double.valueOf( amountCompany == null ? "0" : amountCompany ) );
   }

   public void addAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = String.valueOf( Double.valueOf( this.amountPersonal == null ? "0" : this.amountPersonal )
            + Double.valueOf( amountPersonal == null ? "0" : amountPersonal ) );
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

   public String getEmployeeSBId()
   {
      return employeeSBId;
   }

   public void setEmployeeSBId( String employeeSBId )
   {
      this.employeeSBId = employeeSBId;
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

   public String getSbNumber()
   {
      return sbNumber;
   }

   public void setSbNumber( String sbNumber )
   {
      this.sbNumber = sbNumber;
   }

   public String getOrderId()
   {
      return orderId;
   }

   public void setOrderId( String orderId )
   {
      this.orderId = orderId;
   }

   public String getClientNo()
   {
      return clientNo;
   }

   public void setClientNo( String clientNo )
   {
      this.clientNo = clientNo;
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

}
