package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class PaymentAdjustmentImportDetailVO extends BaseVO
{

/**
    * 
    */
   private static final long serialVersionUID = 1L;

   // �����ӱ�Id
   private String adjustmentDetailId;

   // ��������Id
   private String adjustmentHeaderId;

   // ��ĿId
   private String itemId;

   // ��Ŀ���
   private String itemNo;

   // ��Ŀ���ƣ����ģ�
   private String nameZH;

   // ��Ŀ���ƣ�Ӣ�ģ�
   private String nameEN;

   // �ϼƣ��������룩
   private String billAmountPersonal;

   // �ϼƣ�����֧����
   private String costAmountPersonal;

   // �ϼƣ���˰��
   private String taxAmountPersonal;

   private String description;

   /**
    * for application
    */
   // ��Ŀ����
   private String itemType;
   
   // ��Ŀ
   private List< MappingVO > items = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.items = KANConstants.getKANAccountConstants( super.getAccountId() ).getSalaryItems( getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( adjustmentDetailId );
   }

   public String getEncodedAdjustmentHeaderId() throws KANException
   {
      return encodedField( adjustmentHeaderId );
   }

   @Override
   public void reset() throws KANException
   {
      this.adjustmentDetailId = "";
      this.adjustmentHeaderId = "";
      this.itemId = "0";
      this.itemNo = "";
      this.nameZH = "";
      this.nameEN = "";
      this.billAmountPersonal = "0";
      this.costAmountPersonal = "0";
      this.taxAmountPersonal = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) object;
      this.adjustmentHeaderId = paymentAdjustmentDetailVO.getAdjustmentHeaderId();
      this.itemId = paymentAdjustmentDetailVO.getItemId();
      this.itemNo = paymentAdjustmentDetailVO.getItemNo();
      this.nameZH = paymentAdjustmentDetailVO.getNameZH();
      this.nameEN = paymentAdjustmentDetailVO.getNameEN();
      this.billAmountPersonal = paymentAdjustmentDetailVO.getBillAmountPersonal();
      this.costAmountPersonal = paymentAdjustmentDetailVO.getCostAmountPersonal();
      this.taxAmountPersonal = paymentAdjustmentDetailVO.getTaxAmountPersonal();
      this.description = paymentAdjustmentDetailVO.getDescription();
      super.setStatus( paymentAdjustmentDetailVO.getStatus() );
      super.setModifyDate( new Date() );
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

   public String getTaxAmountPersonal()
   {
      return formatNumber( taxAmountPersonal );
   }

   public void setTaxAmountPersonal( String taxAmountPersonal )
   {
      this.taxAmountPersonal = taxAmountPersonal;
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

   public final String getBillAmountPersonal()
   {
      return formatNumber( billAmountPersonal );
   }

   public final void setBillAmountPersonal( String billAmountPersonal )
   {
      this.billAmountPersonal = billAmountPersonal;
   }

   public final String getCostAmountPersonal()
   {
      return formatNumber( costAmountPersonal );
   }

   public final void setCostAmountPersonal( String costAmountPersonal )
   {
      this.costAmountPersonal = costAmountPersonal;
   }

   public String getItemType()
   {
      return itemType;
   }

   public void setItemType( String itemType )
   {
      this.itemType = itemType;
   }
}
