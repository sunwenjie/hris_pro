package com.kan.hro.domain.biz.attendance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class AttendanceImportHeaderVO extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = -139088777515141139L;

   /**
    * For DB
    */
   // 考勤导入主表ID
   private String headerId;

   // 批次ID
   private String batchId;

   // 劳动合同ID
   private String contractId;

   // 考勤表月份
   private String monthly;

   // 工作小时数（考勤周期合计）
   private String totalWorkHours;

   // 工作天数（考勤周期合计）
   private String totalWorkDays;

   // 全勤小时数（考勤周期合计）
   private String totalFullHours;

   // 全勤天数（考勤周期合计）
   private String totalFullDays;

   // 附件
   private String attachment;

   // 描述
   private String description;

   /**
    *  For App
    */
   // 雇员名称
   private String employeeName;

   private String employeeId;

   private String employeeNameZH;

   private String employeeNameEN;

   @Override
   public void reset( ActionMapping mapping, HttpServletRequest request )
   {
      super.reset( mapping, request );
      setStatuses( KANUtil.getMappings( request.getLocale(), "def.common.batch.status" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.batchId = "";
      this.contractId = "";
      this.monthly = "";
      this.totalWorkHours = "";
      this.totalWorkDays = "";
      this.totalFullHours = "";
      this.totalFullDays = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final AttendanceImportHeaderVO attendanceImportHeaderVO = ( AttendanceImportHeaderVO ) object;
      this.contractId = attendanceImportHeaderVO.getContractId();
      this.monthly = attendanceImportHeaderVO.getMonthly();
      this.totalWorkHours = attendanceImportHeaderVO.getTotalWorkHours();
      this.totalWorkDays = attendanceImportHeaderVO.getTotalWorkDays();
      this.totalFullHours = attendanceImportHeaderVO.getTotalFullHours();
      this.totalFullDays = attendanceImportHeaderVO.getTotalFullDays();
      this.description = attendanceImportHeaderVO.getDescription();
      super.setStatus( attendanceImportHeaderVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( headerId );
   }

   public String getHeaderId()
   {
      return headerId;
   }

   public void setHeaderId( String headerId )
   {
      this.headerId = headerId;
   }

   public String getBatchId()
   {
      return batchId;
   }

   public void setBatchId( String batchId )
   {
      this.batchId = batchId;
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getMonthly()
   {
      return monthly;
   }

   public void setMonthly( String monthly )
   {
      this.monthly = monthly;
   }

   public String getTotalWorkHours()
   {
      return totalWorkHours;
   }

   public void setTotalWorkHours( String totalWorkHours )
   {
      this.totalWorkHours = totalWorkHours;
   }

   public String getTotalWorkDays()
   {
      return totalWorkDays;
   }

   public void setTotalWorkDays( String totalWorkDays )
   {
      this.totalWorkDays = totalWorkDays;
   }

   public String getTotalFullHours()
   {
      return totalFullHours;
   }

   public void setTotalFullHours( String totalFullHours )
   {
      this.totalFullHours = totalFullHours;
   }

   public String getTotalFullDays()
   {
      return totalFullDays;
   }

   public void setTotalFullDays( String totalFullDays )
   {
      this.totalFullDays = totalFullDays;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNameZH()
   {
      return employeeNameZH;
   }

   public void setEmployeeNameZH( String employeeNameZH )
   {
      this.employeeNameZH = employeeNameZH;
   }

   public String getEmployeeNameEN()
   {
      return employeeNameEN;
   }

   public void setEmployeeNameEN( String employeeNameEN )
   {
      this.employeeNameEN = employeeNameEN;
   }

   public String getEncodedBatchId() throws KANException
   {
      return encodedField( batchId );
   }

}
