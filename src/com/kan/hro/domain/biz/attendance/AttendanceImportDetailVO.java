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

public class AttendanceImportDetailVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 1953378780963651884L;

   // 考勤导入从表ID
   private String detailId;

   // 科目名
   private String itemName;

   // 考勤导入主表ID
   private String headerId;

   // 科目ID
   private String itemId;

   // 考勤小数数
   private String hours;

   // 描述
   private String description;

   /**
    * For APP
    */
   private List< MappingVO > leaveItems = new ArrayList< MappingVO >();

   private List< MappingVO > otItems = new ArrayList< MappingVO >();

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( detailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.headerId = "";
      this.itemId = "";
      this.hours = "";
      this.itemName = "";
      super.setStatus( "0" );
   }

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      setStatuses( KANUtil.getMappings( request.getLocale(), "def.common.batch.status" ) );
      leaveItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getLeaveItems( request.getLocale().getLanguage(), super.getCorpId() );
      otItems = KANConstants.getKANAccountConstants( super.getAccountId() ).getOtItems( request.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      AttendanceImportDetailVO attendanceImportDetailVO = ( AttendanceImportDetailVO ) object;
      this.headerId = attendanceImportDetailVO.getHeaderId();
      this.itemId = attendanceImportDetailVO.getItemId();
      this.hours = attendanceImportDetailVO.getHours();
      this.itemName = attendanceImportDetailVO.getItemName();
      super.setStatus( attendanceImportDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getDetailId()
   {
      return detailId;
   }

   public void setDetailId( String detailId )
   {
      this.detailId = detailId;
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getItemId()
   {
      return itemId;
   }

   public void setItemId( String itemId )
   {
      this.itemId = itemId;
   }

   public String getHours()
   {
      return hours;
   }

   public void setHours( String hours )
   {
      this.hours = hours;
   }

   public String getItemName()
   {
      return itemName;
   }

   public void setItemName( String itemName )
   {
      this.itemName = itemName;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
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

}
