package com.kan.hro.domain.biz.attendance;

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

public class OTDetailVO extends BaseVO
{

   /**  
   * serialVersionUID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = 6687793808492096149L;

   /**
    * For DB
    */
   // 加班主表Id
   private String otDetailId;

   // 加班从表Id
   private String otHeaderId;

   // 表勤表Id
   private String timesheetId;

   // 科目ID
   private String itemId;

   // 预计加班开始时间
   private String estimateStartDate;

   // 预计加班结束时间
   private String estimateEndDate;

   // 实际加班开始时间
   private String actualStartDate;

   // 实际加班结束时间
   private String actualEndDate;

   // 预计加班小时数
   private String estimateHours;

   // 实际加班小时数
   private String actualHours;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 加班科目
   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage() );
      if ( otItems != null )
      {
         otItems.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.otHeaderId = "";
      this.timesheetId = "";
      this.itemId = "";
      this.estimateStartDate = "";
      this.estimateEndDate = "";
      this.actualStartDate = "";
      this.actualEndDate = "";
      this.estimateHours = "";
      this.actualHours = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final OTDetailVO otDetailVO = ( OTDetailVO ) object;
      this.otHeaderId = otDetailVO.getOtHeaderId();
      this.timesheetId = otDetailVO.getTimesheetId();
      this.itemId = otDetailVO.getItemId();
      this.estimateStartDate = otDetailVO.getEstimateStartDate();
      this.estimateEndDate = otDetailVO.getEstimateEndDate();
      this.actualStartDate = otDetailVO.getActualStartDate();
      this.actualEndDate = otDetailVO.getActualEndDate();
      this.estimateHours = otDetailVO.getEstimateHours();
      this.actualHours = otDetailVO.getActualHours();
      this.description = otDetailVO.getDescription();
      super.setStatus( otDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDecodeItemId()
   {
      return decodeField( itemId, otItems );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( otDetailId );
   }

   public String getOtDetailId()
   {
      return otDetailId;
   }

   public void setOtDetailId( String otDetailId )
   {
      this.otDetailId = otDetailId;
   }

   public String getOtHeaderId()
   {
      return otHeaderId;
   }

   public void setOtHeaderId( String otHeaderId )
   {
      this.otHeaderId = otHeaderId;
   }

   public String getTimesheetId()
   {
      return KANUtil.filterEmpty( timesheetId );
   }

   public void setTimesheetId( String timesheetId )
   {
      this.timesheetId = timesheetId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getEstimateStartDate() throws KANException
   {
      if ( KANUtil.filterEmpty( this.estimateStartDate ) != null )
      {
         return KANUtil.formatDate( this.estimateStartDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setEstimateStartDate( String estimateStartDate )
   {
      this.estimateStartDate = estimateStartDate;
   }

   public String getEstimateEndDate()
   {
      if ( KANUtil.filterEmpty( this.estimateEndDate ) != null )
      {
         return KANUtil.formatDate( this.estimateEndDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setEstimateEndDate( String estimateEndDate )
   {
      this.estimateEndDate = estimateEndDate;
   }

   public String getActualStartDate()
   {
      if ( KANUtil.filterEmpty( this.actualStartDate ) != null )
      {
         return KANUtil.formatDate( this.actualStartDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setActualStartDate( String actualStartDate )
   {
      this.actualStartDate = actualStartDate;
   }

   public String getActualEndDate()
   {
      if ( KANUtil.filterEmpty( this.actualEndDate ) != null )
      {
         return KANUtil.formatDate( this.actualEndDate, "yyyy-MM-dd HH:mm" );
      }
      else
      {
         return null;
      }
   }

   public void setActualEndDate( String actualEndDate )
   {
      this.actualEndDate = actualEndDate;
   }

   public String getEstimateHours()
   {
      return estimateHours;
   }

   public void setEstimateHours( String estimateHours )
   {
      this.estimateHours = estimateHours;
   }

   public String getActualHours()
   {
      return actualHours;
   }

   public void setActualHours( String actualHours )
   {
      this.actualHours = actualHours;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   // 按照当前状态获取加班日期
   public String getDay()
   {
      if ( this.getStatus() != null && this.getStatus().trim().equals( "3" ) )
      {
         if ( this.estimateStartDate != null )
         {
            return this.estimateStartDate;
         }
      }
      else if ( this.getStatus() != null && this.getStatus().trim().equals( "5" ) )
      {
         if ( this.actualStartDate != null )
         {
            return this.actualStartDate;
         }
      }

      return null;
   }

}
