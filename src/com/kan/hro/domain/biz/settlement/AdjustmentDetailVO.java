package com.kan.hro.domain.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class AdjustmentDetailVO extends BaseVO
{

   // serialVersionUID
   private static final long serialVersionUID = -57341527980474746L;

   /**
    * For DB
    */

   // �����ӱ�Id
   private String adjustmentDetailId;

   // ��������Id
   private String adjustmentHeaderId;

   // ��ĿId
   private String itemId;

   // ��Ŀ������
   private String nameZH;

   // ��ĿӢ����
   private String nameEN;

   // ֱ�������ֵ��Base From�Ľ��ֵ
   private String base;

   // ��������ʱ�����㣩
   private String quantity;

   // �ۿۣ���ʱ�����㣩
   private String discount;

   // ���ʣ���ʱ�����㣩
   private String multiple;

   // ���ʣ��������룩
   private String billRatePersonal;

   // ���ʣ���˾Ӫ�գ�
   private String billRateCompany;

   // ���ʣ�����֧����
   private String costRatePersonal;

   // ���ʣ���˾�ɱ���
   private String costRateCompany;

   // �̶��𣨸������룩
   private String billFixPersonal;

   // �̶��𣨹�˾Ӫ�գ�
   private String billFixCompany;

   // �̶��𣨸���֧����
   private String costFixPersonal;

   // �̶��𣨹�˾�ɱ���
   private String costFixCompany;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ���˾Ӫ�գ�
   private String billAmountCompany;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // �ϼƣ���˾�ɱ���
   private String costAmountCompany;

   // ˰�գ�ʵ�ɣ�
   private String taxAmountActual;

   // ˰�գ��ɱ���
   private String taxAmountCost;

   // ˰�գ����ۣ�
   private String taxAmountSales;

   // �·ݣ�����2013/9��
   private String monthly;

   // ����
   private String description;

   /**
    * For Application
    */
   private String itemNo;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) object;
      this.itemId = adjustmentDetailVO.getItemId();
      this.billAmountPersonal = adjustmentDetailVO.getBillAmountPersonal();
      this.billAmountCompany = adjustmentDetailVO.getBillAmountCompany();
      this.costAmountPersonal = adjustmentDetailVO.getCostAmountPersonal();
      this.costAmountCompany = adjustmentDetailVO.getCostAmountCompany();
      this.monthly = adjustmentDetailVO.getMonthly();
      this.description = adjustmentDetailVO.getDescription();
      super.setStatus( adjustmentDetailVO.getStatus() );
      super.setModifyBy( adjustmentDetailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.adjustmentHeaderId = "";
      this.itemId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.base = "";
      this.quantity = "";
      this.discount = "";
      this.multiple = "";
      this.billRatePersonal = "";
      this.billRateCompany = "";
      this.costRatePersonal = "";
      this.costRateCompany = "";
      this.billFixPersonal = "";
      this.billFixCompany = "";
      this.costFixPersonal = "";
      this.costFixCompany = "";
      this.billAmountPersonal = "";
      this.billAmountCompany = "";
      this.costAmountPersonal = "";
      this.costAmountCompany = "";
      this.taxAmountActual = "";
      this.taxAmountCost = "";
      this.taxAmountSales = "";
      this.monthly = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentDetailId );
   }

   public String getOrderDetailId()
   {
      return adjustmentDetailId;
   }

   public void setOrderDetailId( String adjustmentDetailId )
   {
      this.adjustmentDetailId = adjustmentDetailId;
   }

   public String getContractId()
   {
      return adjustmentHeaderId;
   }

   public void setContractId( String adjustmentHeaderId )
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

   public String getBase()
   {
      return base;
   }

   public void setBase( String base )
   {
      this.base = base;
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

   public String getBillRatePersonal()
   {
      return billRatePersonal;
   }

   public void setBillRatePersonal( String billRatePersonal )
   {
      this.billRatePersonal = billRatePersonal;
   }

   public String getBillRateCompany()
   {
      return billRateCompany;
   }

   public void setBillRateCompany( String billRateCompany )
   {
      this.billRateCompany = billRateCompany;
   }

   public String getCostRatePersonal()
   {
      return costRatePersonal;
   }

   public void setCostRatePersonal( String costRatePersonal )
   {
      this.costRatePersonal = costRatePersonal;
   }

   public String getCostRateCompany()
   {
      return costRateCompany;
   }

   public void setCostRateCompany( String costRateCompany )
   {
      this.costRateCompany = costRateCompany;
   }

   public String getBillFixPersonal()
   {
      return billFixPersonal;
   }

   public void setBillFixPersonal( String billFixPersonal )
   {
      this.billFixPersonal = billFixPersonal;
   }

   public String getBillFixCompany()
   {
      return billFixCompany;
   }

   public void setBillFixCompany( String billFixCompany )
   {
      this.billFixCompany = billFixCompany;
   }

   public String getCostFixPersonal()
   {
      return costFixPersonal;
   }

   public void setCostFixPersonal( String costFixPersonal )
   {
      this.costFixPersonal = costFixPersonal;
   }

   public String getCostFixCompany()
   {
      return costFixCompany;
   }

   public void setCostFixCompany( String costFixCompany )
   {
      this.costFixCompany = costFixCompany;
   }

   public String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public String getBillAmountCompany()
   {
      return formatNumber( billAmountCompany );
   }

   public void setBillAmountCompany( String billAmountCompany )
   {
      this.billAmountCompany = billAmountCompany;
   }

   public String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getCostAmountCompany()
   {
      return formatNumber( costAmountCompany );
   }

   public void setCostAmountCompany( String costAmountCompany )
   {
      this.costAmountCompany = costAmountCompany;
   }

   public String getTaxAmountActual()
   {
      return taxAmountActual;
   }

   public void setTaxAmountActual( String taxAmountActual )
   {
      this.taxAmountActual = taxAmountActual;
   }

   public String getTaxAmountCost()
   {
      return taxAmountCost;
   }

   public void setTaxAmountCost( String taxAmountCost )
   {
      this.taxAmountCost = taxAmountCost;
   }

   public String getTaxAmountSales()
   {
      return taxAmountSales;
   }

   public void setTaxAmountSales( String taxAmountSales )
   {
      this.taxAmountSales = taxAmountSales;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

   public String getItemNo()
   {
      return itemNo;
   }

   public void setItemNo( String itemNo )
   {
      this.itemNo = itemNo;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

}
