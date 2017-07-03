package com.kan.base.domain.management;

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

public class ShiftExceptionVO extends BaseVO
{

   /**  
   * Serial Version UID 
   */
   private static final long serialVersionUID = -9009101140269712698L;

   // �Ű�����Id
   private String exceptionId;

   // �Ű�����Id
   private String headerId;

   // �������ƣ����ģ�
   private String nameZH;

   // �������ƣ�Ӣ�ģ�
   private String nameEN;

   // ������������
   private String dayType;

   // �����Ŀ
   private String itemId;

   // �������ڣ��Զ���ʹ�ã���ȷ��ĳһ�죩
   private String exceptionDay;

   // ����ʱ���
   private String exceptionPeriod;

   // �������ͣ�0����ѡ��1����٣�2���Ӱࣩ
   private String exceptionType;

   // ����
   private String description;

   /**
    * For Application
    */
   // ��������
   private List< MappingVO > dayTypes = new ArrayList< MappingVO >();

   // ��������
   private List< MappingVO > exceptionTypes = new ArrayList< MappingVO >();

   // �Ű�ʱ������MappingVO
   private List< MappingVO > shiftPeriods = new ArrayList< MappingVO >();

   // ��ٿ�Ŀ
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();

   // �Ӱ��Ŀ
   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   // ʱ���
   private String[] shiftPeriodArray = new String[] {};

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.dayTypes = KANUtil.getMappings( request.getLocale(), "sys.calendar.header.day.type" );
      this.exceptionTypes = KANUtil.getMappings( request.getLocale(), "sys.shift.exception.exception.type" );
      this.shiftPeriods = KANUtil.getMappings( request.getLocale(), "sys.shift.detail.shift.period" );
      this.leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( super.getLocale().getLanguage(), super.getClientId() );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( super.getLocale().getLanguage(), super.getClientId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( exceptionId );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.dayType = "0";
      this.exceptionDay = "";
      this.exceptionPeriod = "";
      this.exceptionType = "0";
      this.itemId = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) object;
      this.nameZH = shiftExceptionVO.getNameZH();
      this.nameEN = shiftExceptionVO.getNameEN();
      this.dayType = shiftExceptionVO.getDayType();
      this.exceptionDay = shiftExceptionVO.getExceptionDay();
      this.exceptionPeriod = shiftExceptionVO.getExceptionPeriod();
      this.exceptionType = shiftExceptionVO.getExceptionType();
      this.itemId = shiftExceptionVO.getItemId();
      super.setStatus( shiftExceptionVO.getStatus() );
      super.setModifyDate( new Date() );
      this.shiftPeriodArray = shiftExceptionVO.getShiftPeriodArray();
   }

   // ������������
   public String getDecodeDayType()
   {
      return decodeField( dayType, dayTypes );
   }

   // ������������
   public String getDecodeExceptionType()
   {
      return decodeField( exceptionType, exceptionTypes );
   }

   // �����Ŀ
   public String getDecodeItemId()
   {
      String itemName = "";

      if ( KANUtil.filterEmpty( exceptionType ) != null )
      {
         if ( KANUtil.filterEmpty( exceptionType ).equals( "1" ) )
         {
            itemName = decodeField( itemId, leaveItems );
         }
         else
         {
            itemName = decodeField( itemId, otItems );
         }
      }

      if ( KANUtil.filterEmpty( itemName ) == null )
      {
         itemName = "������";
      }

      return itemName;
   }

   // ��������ʱ���
   public String getDecodeExceptionPeriod()
   {
      return getDecoded( KANUtil.filterEmpty( exceptionPeriod ) );
   }

   private String getDecoded( final String source )
   {
      if ( source != null )
      {
         final String[] periodArray = KANUtil.jasonArrayToStringArray( source );

         if ( periodArray.length == 1 )
         {
            int periodId = Integer.parseInt( periodArray[ 0 ] );
            return getStartTime( periodId ) + " ~ " + getEndTime( periodId );
         }
         else
         {
            // ��ʼ��StringBuilder
            final StringBuilder rb = new StringBuilder();

            int begin = Integer.parseInt( periodArray[ 0 ] );
            int end = Integer.parseInt( periodArray[ 0 ] );

            for ( int i = 1; i < periodArray.length; i++ )
            {
               int tempPeriodId = Integer.parseInt( periodArray[ i ] );
               if ( end + 1 == tempPeriodId )
               {
                  end = end + 1;
               }
               else
               {
                  rb.append( getStartTime( begin ) + " ~ " + getEndTime( end ) );
                  rb.append( "       " );
                  begin = tempPeriodId;
                  end = tempPeriodId;
               }
            }

            rb.append( getStartTime( begin ) + " ~ " + getEndTime( end ) );

            return rb.toString();
         }
      }

      return null;
   }

   private String getStartTime( int i )
   {
      int t = ( i - 1 ) / 2;
      int r = ( i - 1 ) % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

   private String getEndTime( int i )
   {
      int t = i / 2;
      int r = i % 2;
      return ( t < 10 ? "0" + t : t ) + ( r == 0 ? ":00" : ":30" );
   }

   public String getExceptionId()
   {
      return exceptionId;
   }

   public void setExceptionId( String exceptionId )
   {
      this.exceptionId = exceptionId;
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

   public String getDayType()
   {
      return dayType;
   }

   public void setDayType( String dayType )
   {
      this.dayType = dayType;
   }

   public String getExceptionDay()
   {
      return KANUtil.filterEmpty( exceptionDay ) == null ? null : KANUtil.formatDate( KANUtil.filterEmpty( exceptionDay ), "yyyy-MM-dd" );
   }

   public void setExceptionDay( String exceptionDay )
   {
      this.exceptionDay = exceptionDay;
   }

   public String getExceptionPeriod()
   {
      return exceptionPeriod;
   }

   public void setExceptionPeriod( String exceptionPeriod )
   {
      this.exceptionPeriod = exceptionPeriod;
   }

   public String getExceptionType()
   {
      return exceptionType;
   }

   public void setExceptionType( String exceptionType )
   {
      this.exceptionType = exceptionType;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getDayTypes()
   {
      return dayTypes;
   }

   public void setDayTypes( List< MappingVO > dayTypes )
   {
      this.dayTypes = dayTypes;
   }

   public List< MappingVO > getExceptionTypes()
   {
      return exceptionTypes;
   }

   public void setExceptionTypes( List< MappingVO > exceptionTypes )
   {
      this.exceptionTypes = exceptionTypes;
   }

   public List< MappingVO > getShiftPeriods()
   {
      return shiftPeriods;
   }

   public void setShiftPeriods( List< MappingVO > shiftPeriods )
   {
      this.shiftPeriods = shiftPeriods;
   }

   public List< MappingVO > getLeaveItems()
   {
      return leaveItems;
   }

   public void setLeaveItems( List< MappingVO > leaveItems )
   {
      this.leaveItems = leaveItems;
   }

   public List< MappingVO > getOtItems()
   {
      return otItems;
   }

   public void setOtItems( List< MappingVO > otItems )
   {
      this.otItems = otItems;
   }

   public String[] getShiftPeriodArray()
   {
      return shiftPeriodArray;
   }

   public void setShiftPeriodArray( String[] shiftPeriodArray )
   {
      this.shiftPeriodArray = shiftPeriodArray;
   }

}
