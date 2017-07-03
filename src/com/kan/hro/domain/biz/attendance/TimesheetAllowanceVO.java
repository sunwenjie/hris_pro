package com.kan.hro.domain.biz.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class TimesheetAllowanceVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = -1338716753712258058L;

   /**
    * For DB
    */

   // 考勤津贴Id
   private String allowanceId;

   // 考勤主表Id
   private String headerId;

   // 科目Id
   private String itemId;

   // 基数
   private String base;

   // 基数来源，多个科目（即ItemGroup）
   private String baseFrom;

   // 比例（当使用基数来源时出现）
   private String percentage;

   // 固定金（当使用基数来源时出现）
   private String fix;

   // 数量
   private String quantity;

   // 折扣
   private String discount;

   // 倍率
   private String multiple;

   // 计算结果上限 
   private String resultCap;

   // 计算结果下限
   private String resultFloor;

   // 计算公式类型（默认 - 数量×倍率×折扣（基数 or 基数来源×比例+固定金），自定义）
   private String formularType;

   // 计算公式
   private String formular;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 科目编号
   private String itemNo;

   // 科目名称（中文）
   private String itemNameZH;

   // 科目名称（英文）
   private String itemNameEN;

   // 批次id用于查询
   private String batchId;

   // 工资性科目
   private List< MappingVO > salaryItems = new ArrayList< MappingVO >();

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      salaryItems.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemsByType( "1", request.getLocale().getLanguage(), super.getCorpId() ) );
      salaryItems.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemsByType( "2", request.getLocale().getLanguage(), super.getCorpId() ) );
      salaryItems.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemsByType( "3", request.getLocale().getLanguage(), super.getCorpId() ) );
      salaryItems.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getItemsByType( "5", request.getLocale().getLanguage(), super.getCorpId() ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.base = "";
      this.baseFrom = "";
      this.percentage = "";
      this.fix = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "";
      this.resultCap = "";
      this.resultFloor = "";
      this.formularType = "";
      this.formular = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final TimesheetAllowanceVO timeSheetAllowanceVO = ( TimesheetAllowanceVO ) object;
      this.itemId = timeSheetAllowanceVO.getItemId();
      this.base = timeSheetAllowanceVO.getBase();
      //      this.baseFrom = timeSheetAllowanceVO.getBaseFrom();
      //      this.percentage = timeSheetAllowanceVO.getPercentage();
      //      this.fix = timeSheetAllowanceVO.getFix();
      //      this.quantity = timeSheetAllowanceVO.getQuantity();
      //      this.discount = timeSheetAllowanceVO.getDiscount();
      //      this.multiple = timeSheetAllowanceVO.getMultiple();
      //      this.resultCap = timeSheetAllowanceVO.getResultCap();
      //      this.resultFloor = timeSheetAllowanceVO.getResultFloor();
      //      this.formularType = timeSheetAllowanceVO.getFormularType();
      //      this.formular = timeSheetAllowanceVO.getFormular();
      this.description = timeSheetAllowanceVO.getDescription();
      super.setStatus( timeSheetAllowanceVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getAllowanceId()
   {
      return allowanceId;
   }

   public void setAllowanceId( String allowanceId )
   {
      this.allowanceId = allowanceId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getBase()
   {
      return base;
   }

   public void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseFrom()
   {
      return baseFrom;
   }

   public void setBaseFrom( String baseFrom )
   {
      this.baseFrom = baseFrom;
   }

   public String getPercentage()
   {
      return percentage;
   }

   public void setPercentage( String percentage )
   {
      this.percentage = percentage;
   }

   public String getFix()
   {
      return fix;
   }

   public void setFix( String fix )
   {
      this.fix = fix;
   }

   public String getQuantity()
   {
      return quantity;
   }

   public void setQuantity( String quantity )
   {
      this.quantity = quantity;
   }

   public String getDiscount()
   {
      return discount;
   }

   public void setDiscount( String discount )
   {
      this.discount = discount;
   }

   public String getMultiple()
   {
      return multiple;
   }

   public void setMultiple( String multiple )
   {
      this.multiple = multiple;
   }

   public String getResultCap()
   {
      return resultCap;
   }

   public void setResultCap( String resultCap )
   {
      this.resultCap = resultCap;
   }

   public String getResultFloor()
   {
      return resultFloor;
   }

   public void setResultFloor( String resultFloor )
   {
      this.resultFloor = resultFloor;
   }

   public String getFormularType()
   {
      return formularType;
   }

   public void setFormularType( String formularType )
   {
      this.formularType = formularType;
   }

   public String getFormular()
   {
      return formular;
   }

   public void setFormular( String formular )
   {
      this.formular = formular;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( allowanceId );
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
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

   public List< MappingVO > getSalaryItems()
   {
      return salaryItems;
   }

   public void setSalaryItems( List< MappingVO > salaryItems )
   {
      this.salaryItems = salaryItems;
   }

}
