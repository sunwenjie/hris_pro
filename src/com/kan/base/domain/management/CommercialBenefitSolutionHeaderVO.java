package com.kan.base.domain.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class CommercialBenefitSolutionHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 4844569195436341947L;

   /**
    * For DB
    */
   // 商保方案主表ID
   private String headerId;

   // 商保方案名称（中文）
   private String nameZH;

   // 商保方案名称（英文）  
   private String nameEN;

   // 有效开始时间
   private String validFrom;

   // 有效截止时间
   private String validEnd;

   // 商保方案附件
   private String attachment;

   // 付费方式
   private String calculateType;

   // Add Column 保留小数位
   private String accuracy;

   // Add Column 小数位保留方式
   private String round;

   // 不全月免费
   private String freeShortOfMonth;

   // 按全月计费
   private String chargeFullMonth;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 附件列表
   private String[] attachmentArray = new String[] {};
   @JsonIgnore
   // 费用方式
   private List< MappingVO > calculateTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 精度
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();
   @JsonIgnore
   // 取值范围
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.calculateTypies = KANUtil.getMappings( request.getLocale(), "cb.calculate.type" );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.validFrom = "";
      this.validEnd = "";
      this.attachment = "";
      this.calculateType = "0";
      this.accuracy = "0";
      this.round = "0";
      this.freeShortOfMonth = "0";
      this.chargeFullMonth = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = ( CommercialBenefitSolutionHeaderVO ) object;
      this.nameZH = commercialBenefitSolutionHeaderVO.getNameZH();
      this.nameEN = commercialBenefitSolutionHeaderVO.getNameEN();
      this.validFrom = commercialBenefitSolutionHeaderVO.getValidFrom();
      this.validEnd = commercialBenefitSolutionHeaderVO.getValidEnd();
      this.calculateType = commercialBenefitSolutionHeaderVO.getCalculateType();
      this.accuracy = commercialBenefitSolutionHeaderVO.getAccuracy();
      this.round = commercialBenefitSolutionHeaderVO.getRound();
      this.freeShortOfMonth = commercialBenefitSolutionHeaderVO.getFreeShortOfMonth();
      this.chargeFullMonth = commercialBenefitSolutionHeaderVO.getChargeFullMonth();
      this.description = commercialBenefitSolutionHeaderVO.getDescription();
      super.setStatus( commercialBenefitSolutionHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
      this.attachment = commercialBenefitSolutionHeaderVO.getAttachment();
      this.attachmentArray = new String[] {};
   }

   public String getDecodeAccuracy()
   {
      return decodeField( accuracy, accuracys );
   }

   public String getDecodeAccuracyTemp()
   {
      return decodeField( accuracy, accuracys, true );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
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

   public String getValidFrom()
   {
      return KANUtil.filterEmpty( decodeDate( validFrom ) );
   }

   public void setValidFrom( String validFrom )
   {
      this.validFrom = validFrom;
   }

   public String getValidEnd()
   {
      return KANUtil.filterEmpty( decodeDate( validEnd ) );
   }

   public void setValidEnd( String validEnd )
   {
      this.validEnd = validEnd;
   }

   public String getCalculateType()
   {
      return calculateType;
   }

   public void setCalculateType( String calculateType )
   {
      this.calculateType = calculateType;
   }

   public String getDecodeCalculateType()
   {
      return decodeField( this.calculateType, this.calculateTypies );
   }

   public String getAccuracy()
   {
      return accuracy;
   }

   public void setAccuracy( String accuracy )
   {
      this.accuracy = accuracy;
   }

   public String getRound()
   {
      return round;
   }

   public void setRound( String round )
   {
      this.round = round;
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
      return encodedField( headerId );
   }

   public List< MappingVO > getCalculateTypies()
   {
      return calculateTypies;
   }

   public void setCalculateTypies( List< MappingVO > calculateTypies )
   {
      this.calculateTypies = calculateTypies;
   }

   public List< MappingVO > getAccuracys()
   {
      return accuracys;
   }

   public void setAccuracys( List< MappingVO > accuracys )
   {
      this.accuracys = accuracys;
   }

   public List< MappingVO > getRounds()
   {
      return rounds;
   }

   public void setRounds( List< MappingVO > rounds )
   {
      this.rounds = rounds;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String getFreeShortOfMonth()
   {
      return freeShortOfMonth;
   }

   public void setFreeShortOfMonth( String freeShortOfMonth )
   {
      this.freeShortOfMonth = freeShortOfMonth;
   }

   public String getChargeFullMonth()
   {
      return chargeFullMonth;
   }

   public void setChargeFullMonth( String chargeFullMonth )
   {
      this.chargeFullMonth = chargeFullMonth;
   }

}
