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
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�PositionGradeCurrencyVO  
* ��������  ְλ�ȼ���Ӧн�ʻ�����Ϣ
* �����ˣ�Jack  
* ����ʱ�䣺2013-7-7 ����05:46:30  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-7-7 ����05:46:30  
* �޸ı�ע��  
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
   // н�ʻ��Ҷ�Ӧ����Id
   private String currencyId;
   
   // ְλ�ȼ�id
   private String positionGradeId;
   
   // ��������id
   private String currencyType;
   
   // ���н��
   private float minSalary;
   
   // ���н��
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
   // ���еĻ�������
   private List< MappingVO > currencyTypes;

   // ��û�������
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
