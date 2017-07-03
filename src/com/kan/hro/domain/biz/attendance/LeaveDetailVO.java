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

public class LeaveDetailVO extends BaseVO
{

   /**  
   * serialVersionUID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = 1132696506968350466L;

   /**
    * For DB
    */
   // 请假从表Id
   private String leaveDetailId;

   // 请假主表Id
   private String leaveHeaderId;

   // 表勤表Id
   private String timesheetId;

   // 科目ID
   private String itemId;

   // 预计请假开始时间
   private String estimateStartDate;

   // 预计请假结束时间
   private String estimateEndDate;

   // 实际请假开始时间
   private String actualStartDate;

   // 实际请假结束时间 
   private String actualEndDate;

   // 预计法定假小时数
   private String estimateLegalHours;

   // 预计福利假小时数
   private String estimateBenefitHours;

   // 实际法定假小时数 
   private String actualLegalHours;

   // 实际福利假小时数
   private String actualBenefitHours;

   // 附件
   private String attachment;

   // 销假状态
   private String retrieveStatus;

   // 描述
   private String description;

   /**
    * For Application
    */
   // 请假科目
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), getCorpId() );
      if ( this.leaveItems != null )
      {
         leaveItems.add( 0, getEmptyMappingVO() );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.leaveHeaderId = "";
      this.timesheetId = "";
      this.itemId = "";
      this.estimateStartDate = "";
      this.estimateEndDate = "";
      this.actualStartDate = "";
      this.actualEndDate = "";
      this.estimateLegalHours = "";
      this.estimateBenefitHours = "";
      this.actualLegalHours = "";
      this.actualBenefitHours = "";
      this.attachment = "";
      this.retrieveStatus = "0";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final LeaveDetailVO leaveDetailVO = ( LeaveDetailVO ) object;
      this.leaveHeaderId = leaveDetailVO.getLeaveHeaderId();
      this.timesheetId = leaveDetailVO.getTimesheetId();
      this.itemId = leaveDetailVO.getItemId();
      this.estimateStartDate = leaveDetailVO.getEstimateStartDate();
      this.estimateEndDate = leaveDetailVO.getEstimateEndDate();
      this.actualStartDate = leaveDetailVO.getActualStartDate();
      this.actualEndDate = leaveDetailVO.getActualEndDate();
      this.estimateLegalHours = leaveDetailVO.getEstimateLegalHours();
      this.estimateBenefitHours = leaveDetailVO.getEstimateBenefitHours();
      this.actualLegalHours = leaveDetailVO.getActualLegalHours();
      this.actualBenefitHours = leaveDetailVO.getActualBenefitHours();
      this.attachment = leaveDetailVO.getAttachment();
      this.retrieveStatus = leaveDetailVO.getRetrieveStatus();
      this.description = leaveDetailVO.getDescription();
      super.setStatus( leaveDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // 解密科目ID
   public String getDecodeItemId()
   {
      return decodeField( itemId, leaveItems );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( leaveDetailId );
   }

   public String getLeaveDetailId()
   {
      return leaveDetailId;
   }

   public void setLeaveDetailId( String leaveDetailId )
   {
      this.leaveDetailId = leaveDetailId;
   }

   public String getLeaveHeaderId()
   {
      return leaveHeaderId;
   }

   public void setLeaveHeaderId( String leaveHeaderId )
   {
      this.leaveHeaderId = leaveHeaderId;
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

   public String getEstimateStartDate()
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

   public String getEstimateLegalHours()
   {
      return KANUtil.filterEmpty( estimateLegalHours );
   }

   public void setEstimateLegalHours( String estimateLegalHours )
   {
      this.estimateLegalHours = estimateLegalHours;
   }

   public String getEstimateBenefitHours()
   {
      return KANUtil.filterEmpty( estimateBenefitHours );
   }

   public void setEstimateBenefitHours( String estimateBenefitHours )
   {
      this.estimateBenefitHours = estimateBenefitHours;
   }

   public String getActualLegalHours()
   {
      return KANUtil.filterEmpty( actualLegalHours );
   }

   public void setActualLegalHours( String actualLegalHours )
   {
      this.actualLegalHours = actualLegalHours;
   }

   public String getActualBenefitHours()
   {
      return KANUtil.filterEmpty( actualBenefitHours );
   }

   public void setActualBenefitHours( String actualBenefitHours )
   {
      this.actualBenefitHours = actualBenefitHours;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
   }

   public final String getRetrieveStatus()
   {
      return retrieveStatus;
   }

   public final void setRetrieveStatus( String retrieveStatus )
   {
      this.retrieveStatus = retrieveStatus;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   // 按照当前状态获取休假日期
   public String getDay()
   {
      if ( this.getStatus() != null && "3".equals( this.getStatus().trim() ) && this.getRetrieveStatus() != null && "1".equals( this.getRetrieveStatus().trim() ) )
      {
         if ( KANUtil.filterEmpty( this.estimateStartDate ) != null )
         {
            return KANUtil.formatDate( this.estimateStartDate );
         }
      }
      else if ( this.getStatus() != null && this.getStatus().trim().equals( "3" ) && this.getRetrieveStatus() != null && this.getRetrieveStatus().trim().equals( "3" ) )
      {
         if ( KANUtil.filterEmpty( this.actualStartDate ) != null )
         {
            return KANUtil.formatDate( this.actualStartDate );
         }
      }

      return null;
   }

}
