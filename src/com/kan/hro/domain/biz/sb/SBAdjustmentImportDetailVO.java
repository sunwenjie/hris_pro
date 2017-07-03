package com.kan.hro.domain.biz.sb;

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

public class SBAdjustmentImportDetailVO extends BaseVO
{
   // serialVersionUID
   private static final long serialVersionUID = 6745642266505878034L;

   /**
    * For DB
    */

   private String batchId;

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
   // 劳动合同 ID
   private String contractId;

   // 社保方案
   private String sbSolutionId;

   // 社保方案集合
   private List< MappingVO > sbSolutions = new ArrayList< MappingVO >();

   // 科目
   private List< MappingVO > items = new ArrayList< MappingVO >();

   // 月份
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // 科目编号
   private String itemNo;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.sb.adjustment.import.status" ) );
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
      final SBAdjustmentImportDetailVO sbAdjustmentDetailVO = ( SBAdjustmentImportDetailVO ) object;
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

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
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
      this.amountCompany = String.valueOf( Double.valueOf( this.amountCompany ) + Double.valueOf( amountCompany ) );
   }

   public void addAmountPersonal( String amountPersonal )
   {
      this.amountPersonal = String.valueOf( Double.valueOf( this.amountPersonal ) + Double.valueOf( amountPersonal ) );
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
}
