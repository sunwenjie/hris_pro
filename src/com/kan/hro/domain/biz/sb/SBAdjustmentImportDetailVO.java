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

   // �����ӱ�Id
   private String adjustmentDetailId;

   // �籣����Id
   private String adjustmentHeaderId;

   // ��ĿId
   private String itemId;

   // ��Ŀ������
   private String nameZH;

   // ��ĿӢ����
   private String nameEN;

   // �ϼƣ���˾��
   private String amountCompany;

   // �ϼƣ����ˣ�
   private String amountPersonal;

   // �˵��·�
   private String monthly;

   // �����·�
   private String accountMonthly;

   // ����
   private String description;

   /**
    * For Application
    */
   // �Ͷ���ͬ ID
   private String contractId;

   // �籣����
   private String sbSolutionId;

   // �籣��������
   private List< MappingVO > sbSolutions = new ArrayList< MappingVO >();

   // ��Ŀ
   private List< MappingVO > items = new ArrayList< MappingVO >();

   // �·�
   private List< MappingVO > monthlies = new ArrayList< MappingVO >();

   // ��Ŀ���
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

      // �����In House��¼
      if ( KANUtil.filterEmpty( getCorpId() ) != null )
      {
         sbSolutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage(), super.getCorpId() ) );
      }
      // �����Hr Service��¼
      else
      {
         sbSolutions.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getSocialBenefitSolutions( this.getLocale().getLanguage() ) );
      }
      // ���super���籣
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
