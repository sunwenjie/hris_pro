package com.kan.base.domain.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class IncomeTaxBaseVO extends BaseVO
{
   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 404525499723351353L;

   // 个税起征ID
   private String baseId;

   // 名称（中文）
   private String nameZH;

   // 名称（英文）
   private String nameEN;

   // 生效日期
   private String startDate;

   // 失效日期
   private String endDate;

   // 默认的
   private String isDefault;

   // 个税起征点
   private String base;

   // 个税起征点（外籍）
   private String baseForeigner;

   // 精度
   private String accuracy;

   // 截取
   private String round;

   // 描述
   private String description;

   /**
    * For Application
    */

   // 精度
   private List< MappingVO > accuracys = new ArrayList< MappingVO >();

   // 取值范围
   private List< MappingVO > rounds = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.accuracys = KANUtil.getMappings( request.getLocale(), "def.list.detail.accuracy" );
      this.rounds = KANUtil.getMappings( request.getLocale(), "def.list.detail.round" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( baseId );
   }

   public String getEncodedAccuracy() throws KANException
   {
      return decodeField( accuracy, accuracys, true );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.base = "";
      this.baseForeigner = "";
      this.isDefault = "";
      this.accuracy = "0";
      this.round = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) object;
      this.nameZH = incomeTaxBaseVO.getNameZH();
      this.nameEN = incomeTaxBaseVO.getNameEN();
      this.startDate = incomeTaxBaseVO.getStartDate();
      this.endDate = incomeTaxBaseVO.getEndDate();
      this.base = incomeTaxBaseVO.getBase();
      this.baseForeigner = incomeTaxBaseVO.getBaseForeigner();
      this.isDefault = incomeTaxBaseVO.getIsDefault();
      this.accuracy = incomeTaxBaseVO.getAccuracy();
      this.round = incomeTaxBaseVO.getRound();
      this.description = incomeTaxBaseVO.getDescription();
      super.setStatus( incomeTaxBaseVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public final String getBaseId()
   {
      return baseId;
   }

   public final void setBaseId( String baseId )
   {
      this.baseId = baseId;
   }

   public final String getNameZH()
   {
      return nameZH;
   }

   public final void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public final String getNameEN()
   {
      return nameEN;
   }

   public final void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public final String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public final void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public final String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( endDate ) );
   }

   public final void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public final String getBase()
   {
      return KANUtil.filterEmpty( formatNumber( base ) );
   }

   public final void setBase( String base )
   {
      this.base = base;
   }

   public String getBaseForeigner()
   {
      return KANUtil.filterEmpty( formatNumber( baseForeigner ) );
   }

   public void setBaseForeigner( String baseForeigner )
   {
      this.baseForeigner = baseForeigner;
   }

   public String getIsDefault()
   {
      return isDefault;
   }

   public void setIsDefault( String isDefault )
   {
      this.isDefault = isDefault;
   }

   public final String getDescription()
   {
      return description;
   }

   public final void setDescription( String description )
   {
      this.description = description;
   }

   public final String getAccuracy()
   {
      return accuracy;
   }

   public final void setAccuracy( String accuracy )
   {
      this.accuracy = accuracy;
   }

   public final String getRound()
   {
      return round;
   }

   public final void setRound( String round )
   {
      this.round = round;
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

}
