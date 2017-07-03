package com.kan.wx.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class QuestionHeaderVO extends BaseVO
{

   /**
    * SerialVersionUID
    */
   private static final long serialVersionUID = 2565272476579085681L;

   // For DB
   private String headerId;
   private String titleZH;
   private String titleEN;
   private String isSingle;
   private String expirationDate;
   private String answer;
   private String luckyNumber;
   private String luckyType;
   private String tipsZH;
   private String tipsEN;
   private String description;

   // For app
   private List< MappingVO > isSingles = new ArrayList< MappingVO >();
   private List< MappingVO > luckyTypes = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      isSingles = KANUtil.getMappings( request.getLocale(), "wx.option.isSingle" );
      luckyTypes = KANUtil.getMappings( request.getLocale(), "wx.option.luckyType" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   @Override
   public void reset() throws KANException
   {
      this.titleZH = "";
      this.titleEN = "";
      this.isSingle = "0";
      this.expirationDate = null;
      this.answer = "";
      this.luckyNumber = "";
      this.luckyType = "";
      this.tipsZH = "";
      this.tipsEN = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final QuestionHeaderVO questionHeaderVO = ( QuestionHeaderVO ) object;
      this.titleZH = questionHeaderVO.getTitleZH();
      this.titleEN = questionHeaderVO.getTitleEN();
      this.isSingle = questionHeaderVO.getIsSingle();
      this.expirationDate = questionHeaderVO.getExpirationDate();
      this.answer = questionHeaderVO.getAnswer();
      this.luckyNumber = questionHeaderVO.getLuckyNumber();
      this.luckyType = questionHeaderVO.getLuckyType();
      this.tipsZH = questionHeaderVO.getTipsZH();
      this.tipsEN = questionHeaderVO.getTipsEN();
      this.description = questionHeaderVO.getDescription();
      super.setStatus( questionHeaderVO.getStatus() );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

   public String getIsSingle()
   {
      return isSingle;
   }

   public void setIsSingle( String isSingle )
   {
      this.isSingle = isSingle;
   }

   public String getExpirationDate()
   {
      return KANUtil.formatDate( expirationDate, "yyyy-MM-dd" );
   }

   public void setExpirationDate( String expirationDate )
   {
      this.expirationDate = expirationDate;
   }

   public String getAnswer()
   {
      return answer;
   }

   public void setAnswer( String answer )
   {
      this.answer = answer;
   }

   public String getLuckyNumber()
   {
      return luckyNumber;
   }

   public void setLuckyNumber( String luckyNumber )
   {
      this.luckyNumber = luckyNumber;
   }

   public String getLuckyType()
   {
      return luckyType;
   }

   public void setLuckyType( String luckyType )
   {
      this.luckyType = luckyType;
   }

   public String getTipsZH()
   {
      return tipsZH;
   }

   public void setTipsZH( String tipsZH )
   {
      this.tipsZH = tipsZH;
   }

   public String getTipsEN()
   {
      return tipsEN;
   }

   public void setTipsEN( String tipsEN )
   {
      this.tipsEN = tipsEN;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getIsSingles()
   {
      return isSingles;
   }

   public void setIsSingles( List< MappingVO > isSingles )
   {
      this.isSingles = isSingles;
   }

   public List< MappingVO > getLuckyTypes()
   {
      return luckyTypes;
   }

   public void setLuckyTypes( List< MappingVO > luckyTypes )
   {
      this.luckyTypes = luckyTypes;
   }

}
