package com.kan.base.domain.management;

import java.text.DecimalFormat;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class ExchangeRateVO extends BaseVO
{

   /**
    * 
    */
   private static final long serialVersionUID = -4939516239592471973L;

   private String exchangeRateId;

   private String currencyNameZH;

   private String currencyNameEN;

   private String currencyCode;

   private String fromUSD;

   private String toLocal;

   public String getExchangeRateId()
   {
      return exchangeRateId;
   }

   public void setExchangeRateId( String exchangeRateId )
   {
      this.exchangeRateId = exchangeRateId;
   }

   public String getCurrencyNameZH()
   {
      return currencyNameZH;
   }

   public void setCurrencyNameZH( String currencyNameZH )
   {
      this.currencyNameZH = currencyNameZH;
   }

   public String getCurrencyNameEN()
   {
      return currencyNameEN;
   }

   public void setCurrencyNameEN( String currencyNameEN )
   {
      this.currencyNameEN = currencyNameEN;
   }

   public String getCurrencyCode()
   {
      return currencyCode;
   }

   public void setCurrencyCode( String currencyCode )
   {
      this.currencyCode = currencyCode;
   }

   public String getFromUSD()
   {
      return fromUSD;
   }

   public void setFromUSD( String fromUSD )
   {
      this.fromUSD = fromUSD;
   }

   public String getToLocal()
   {
      return toLocal;
   }

   public void setToLocal( String toLocal )
   {
      this.toLocal = toLocal;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( exchangeRateId );
   }

   @Override
   public void reset() throws KANException
   {
      this.currencyNameZH = "";
      this.currencyNameEN = "";
      this.currencyCode = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ExchangeRateVO exchangeRateVO = ( ExchangeRateVO ) object;
      this.currencyNameZH = exchangeRateVO.getCurrencyNameZH();
      this.currencyNameEN = exchangeRateVO.getCurrencyNameEN();
      this.currencyCode = exchangeRateVO.getCurrencyCode();
      this.fromUSD = exchangeRateVO.getFromUSD();
      this.toLocal = exchangeRateVO.getToLocal();
      super.setStatus( exchangeRateVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getExchangeRate()
   {
      DecimalFormat df = new DecimalFormat( "0.0000" );
      return df.format( Double.valueOf( fromUSD ) / Double.valueOf( toLocal ) );
   }

}
