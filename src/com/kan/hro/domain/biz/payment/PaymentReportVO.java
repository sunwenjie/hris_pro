package com.kan.hro.domain.biz.payment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class PaymentReportVO extends PaymentHeaderVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 5472434311469222227L;

   // 查询起始月
   private String monthlyBegin;

   // 查询截止月
   private String monthlyEnd;

   // 统计月数
   private String monthlyCount;

   // 平均工资
   private String avgAmount;
   
   /**
    * For Export
    */
   private String titleNameList;

   private String titleIdList;

   /**
    * Mapping List
    */
   private List< MappingVO > monthlys = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.monthlys = KANUtil.getMonthsByCondition( 24, 0 );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public void reset() throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void update( Object object ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   public String getMonthlyBegin()
   {
      return monthlyBegin;
   }

   public void setMonthlyBegin( String monthlyBegin )
   {
      this.monthlyBegin = monthlyBegin;
   }

   public String getMonthlyEnd()
   {
      return monthlyEnd;
   }

   public void setMonthlyEnd( String monthlyEnd )
   {
      this.monthlyEnd = monthlyEnd;
   }

   public String getMonthlyCount()
   {
      return monthlyCount;
   }

   public void setMonthlyCount( String monthlyCount )
   {
      this.monthlyCount = monthlyCount;
   }

   public String getAvgAmount()
   {
      return formatNumber( avgAmount );
   }

   public void setAvgAmount( String avgAmount )
   {
      this.avgAmount = avgAmount;
   }

   public List< MappingVO > getMonthlys()
   {
      return monthlys;
   }

   public void setMonthlys( List< MappingVO > monthlys )
   {
      this.monthlys = monthlys;
   }

   public String getTitleNameList()
   {
      return titleNameList;
   }

   public void setTitleNameList( String titleNameList )
   {
      this.titleNameList = titleNameList;
   }

   public String getTitleIdList()
   {
      return titleIdList;
   }

   public void setTitleIdList( String titleIdList )
   {
      this.titleIdList = titleIdList;
   }

}
