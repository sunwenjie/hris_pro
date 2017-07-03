package com.kan.hro.domain.biz.payment;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

/**  
 * 项目名称：HRO_V1  
 * 类名称：SalaryDetailVO  
 * 类描述：  
 * 创建人：Kevin  
 * 创建时间：2014-01-17  
 */
public class SalaryDetailVO extends BaseVO
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = 7409890123688725095L;

   // 工资从表Id
   private String salaryDetailId;

   //工资主表Id
   private String salaryHeaderId;

   //科目Id
   private String itemId;

   //科目编号
   private String itemNo;

   //科目名称（中文）
   private String nameZH;

   //科目名称（英文
   private String nameEN;

   //基数（公司）
   private String baseCompany;

   // 基数（个人）
   private String basePersonal;

   //比率（公司营收）
   private String billRateCompany;

   // 比率（个人收入）
   private String billRatePersonal;

   // 比率（公司成本）
   private String costRateCompany;

   //比率（个人支出）
   private String costRatePersonal;

   // 固定金（公司营收）
   private String billFixCompany;

   //固定金（个人收入）
   private String billFixPersonal;

   //固定金（公司成本）
   private String costFixCompany;

   //固定金（个人支出）
   private String costFixPersonal;

   // 合计（公司营收）
   private String billAmountCompany;

   //合计（个人收入）
   private String billAmountPersonal;

   //合计（公司成本）
   private String costAmountCompany;

   //  合计（个人支出）
   private String costAmountPersonal;

   private String description;

   // for application
   private String itemType;
   
   private String batchId;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.salaryHeaderId );
   }

   @Override
   public void reset() throws KANException
   {

      this.itemId = "0";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.baseCompany = "";
      this.basePersonal = "";
      this.billRateCompany = "";
      this.billRatePersonal = "";
      this.costRateCompany = "";
      this.costRatePersonal = "";
      this.billFixCompany = "";
      this.billFixPersonal = "";
      this.costFixCompany = "";
      this.costFixPersonal = "";
      this.billAmountCompany = "";
      this.billAmountPersonal = "";
      this.costAmountCompany = "";
      this.costAmountPersonal = "";
      this.description = "";
      super.setDeleted( "" );
      super.setStatus( "0" );
      super.setRemark1( "" );
      super.setRemark2( "" );
      super.setRemark3( "" );
      super.setRemark4( "" );
      super.setRemark5( "" );

   }

   @Override
   public void update( Object object ) throws KANException
   {
      final SalaryDetailVO salaryDetailVO = ( SalaryDetailVO ) object;
      this.itemId = salaryDetailVO.getItemId();
      this.itemNo = salaryDetailVO.getItemNo();
      this.nameZH = salaryDetailVO.getNameZH();
      this.nameEN = salaryDetailVO.getNameEN();
      this.baseCompany = salaryDetailVO.getBaseCompany();
      this.basePersonal = salaryDetailVO.getBasePersonal();
      this.billRateCompany = salaryDetailVO.getBillRateCompany();
      this.billRatePersonal = salaryDetailVO.getBillRatePersonal();
      this.costRateCompany = salaryDetailVO.getCostRateCompany();
      this.costRatePersonal = salaryDetailVO.getCostRatePersonal();
      this.billFixCompany = salaryDetailVO.getBillFixCompany();
      this.billFixPersonal = salaryDetailVO.getBillFixPersonal();
      this.costFixCompany = salaryDetailVO.getCostFixCompany();
      this.costFixPersonal = salaryDetailVO.getCostFixPersonal();
      this.billAmountCompany = salaryDetailVO.getBillAmountCompany();
      this.billAmountPersonal = salaryDetailVO.getBillAmountPersonal();
      this.costAmountCompany = salaryDetailVO.getCostAmountCompany();
      this.costAmountPersonal = salaryDetailVO.getCostAmountPersonal();
      this.description = salaryDetailVO.getDescription();
      super.setDeleted( salaryDetailVO.getDeleted() );
      super.setStatus( salaryDetailVO.getStatus() );
      super.setRemark1( salaryDetailVO.getRemark1() );
      super.setRemark2( salaryDetailVO.getRemark2() );
      super.setRemark3( salaryDetailVO.getRemark3() );
      super.setRemark4( salaryDetailVO.getRemark4() );
      super.setRemark5( salaryDetailVO.getRemark5() );
   }

   public String getSalaryDetailId()
   {
      return salaryDetailId;
   }

   public void setSalaryDetailId( String salaryDetailId )
   {
      this.salaryDetailId = salaryDetailId;
   }

   public String getSalaryHeaderId()
   {
      return salaryHeaderId;
   }

   public void setSalaryHeaderId( String salaryHeaderId )
   {
      this.salaryHeaderId = salaryHeaderId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
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

   public String getBaseCompany()
   {
      return formatNumber( baseCompany);
   }

   public void setBaseCompany( String baseCompany )
   {
      this.baseCompany = baseCompany;
   }

   public String getBasePersonal()
   {
      return formatNumber( basePersonal);
   }

   public void setBasePersonal( String basePersonal )
   {
      this.basePersonal = basePersonal;
   }

   public String getBillRateCompany()
   {
      return formatNumber( billRateCompany);
   }

   public void setBillRateCompany( String billRateCompany )
   {
      this.billRateCompany = billRateCompany;
   }

   public String getBillRatePersonal()
   {
      return formatNumber( billRatePersonal);
   }

   public void setBillRatePersonal( String billRatePersonal )
   {
      this.billRatePersonal = billRatePersonal;
   }

   public String getCostRateCompany()
   {
      return formatNumber( costRateCompany);
   }

   public void setCostRateCompany( String costRateCompany )
   {
      this.costRateCompany = costRateCompany;
   }

   public String getCostRatePersonal()
   {
      return formatNumber( costRatePersonal);
   }

   public void setCostRatePersonal( String costRatePersonal )
   {
      this.costRatePersonal = costRatePersonal;
   }

   public String getBillFixCompany()
   {
      return billFixCompany;
   }

   public void setBillFixCompany( String billFixCompany )
   {
      this.billFixCompany = billFixCompany;
   }

   public String getBillFixPersonal()
   {
      return billFixPersonal;
   }

   public void setBillFixPersonal( String billFixPersonal )
   {
      this.billFixPersonal = billFixPersonal;
   }

   public String getCostFixCompany()
   {
      return costFixCompany;
   }

   public void setCostFixCompany( String costFixCompany )
   {
      this.costFixCompany = costFixCompany;
   }

   public String getCostFixPersonal()
   {
      return costFixPersonal;
   }

   public void setCostFixPersonal( String costFixPersonal )
   {
      this.costFixPersonal = costFixPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany);
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal);
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany);
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber(costAmountPersonal);
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }
   

}
