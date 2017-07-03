package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：PositionGradeCurrencyVO  
* 类描述：  职位等级对应薪资货币信息
* 创建人：Jack  
* 创建时间：2013-7-7 下午05:46:30  
* 修改人：Jack  
* 修改时间：2013-7-7 下午05:46:30  
* 修改备注：  
* @version   
*   
*/
public class PositionGradeCurrencyVO extends BaseVO
{
   /**
    * For Serial Version UID
    */
   private static final long serialVersionUID = -2099306204540722537L;

   /**
    * For DB
    */
   // 薪资货币对应主键Id
   private String currencyId;
   
   // 职位等级id
   private String positionGradeId;
   
   // 货币类型id
   private String currencyType;
   
   // 最低薪资
   private float minSalary;
   
   // 最高薪资
   private float maxSalary;

   @Override
   public void reset() throws KANException
   {
      this.minSalary = 0F;
      this.maxSalary = 0F;
      this.currencyType = "0";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final PositionGradeCurrencyVO positionGradeCurrencyVO = ( PositionGradeCurrencyVO ) object;
      this.minSalary = positionGradeCurrencyVO.getMinSalary();
      this.maxSalary = positionGradeCurrencyVO.getMaxSalary();
      super.setStatus( positionGradeCurrencyVO.getStatus() );
      super.setModifyBy( positionGradeCurrencyVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   /**
    * For Application
    */
   // 所有的货币类型
   private List< MappingVO > currencyTypes;

   // 获得货币类型
   public String getDecodeCurrencyType()
   {
      if ( this.currencyTypes != null )
      {
         for ( MappingVO mappingVO : this.currencyTypes )
         {
            if ( mappingVO.getMappingId().equals( currencyType ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.currencyTypes = KANUtil.getMappings( request.getLocale(), "management.positionGradeCurrency.currencyTypes" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( currencyId == null || currencyId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( currencyId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getCurrencyId()
   {
      return currencyId;
   }

   public void setCurrencyId( String currencyId )
   {
      this.currencyId = currencyId;
   }

   public String getPositionGradeId()
   {
      return positionGradeId;
   }

   public void setPositionGradeId( String positionGradeId )
   {
      this.positionGradeId = positionGradeId;
   }

   public float getMinSalary()
   {
      return minSalary;
   }

   public void setMinSalary( float minSalary )
   {
      this.minSalary = minSalary;
   }

   public float getMaxSalary()
   {
      return maxSalary;
   }

   public void setMaxSalary( float maxSalary )
   {
      this.maxSalary = maxSalary;
   }

   public List< MappingVO > getCurrencyTypes()
   {
      return currencyTypes;
   }

   public void setCurrencyTypes( List< MappingVO > currencyTypes )
   {
      this.currencyTypes = currencyTypes;
   }

   public String getCurrencyType()
   {
      return currencyType;
   }

   public void setCurrencyType( String currencyType )
   {
      this.currencyType = currencyType;
   }

}
