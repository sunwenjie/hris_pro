package com.kan.hro.domain.biz.attendance;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class RecordVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = 2272869867709316037L;

   // 考勤记录ID
   private String recordId;

   // 远程终端考勤记录ID
   private String serialId;

   // 员工ID
   private String employeeId;

   // 员工编号
   private String employeeNo;

   // 员工姓名（中文）
   private String employeeNameZH;

   // 员工姓名（英文）
   private String employeeNameEN;

   // 打卡日期
   private String signDate;

   // 打卡时间
   private String signTime;

   // 签到方式
   private String signType;

   // 设备编号
   private String machineNo;

   // 备注
   private String description;
   
   // for app
   // 查询开始，结束时间条件
   private String startDate;
   
   private String endDate;
   
   private String workId;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( recordId );
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

   public String getRecordId()
   {
      return recordId;
   }

   public void setRecordId( String recordId )
   {
      this.recordId = recordId;
   }

   public String getSerialId()
   {
      return serialId;
   }

   public void setSerialId( String serialId )
   {
      this.serialId = serialId;
   }

   public String getEmployeeId()
   {
      return employeeId;
   }

   public void setEmployeeId( String employeeId )
   {
      this.employeeId = employeeId;
   }

   public String getEmployeeNo()
   {
      return employeeNo;
   }

   public void setEmployeeNo( String employeeNo )
   {
      this.employeeNo = employeeNo;
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

   public String getSignDate()
   {
      return signDate;
   }

   public void setSignDate( String signDate )
   {
      this.signDate = signDate;
   }

   public String getSignTime()
   {
      return signTime;
   }

   public void setSignTime( String signTime )
   {
      this.signTime = signTime;
   }

   public String getSignType()
   {
      return signType;
   }

   public void setSignType( String signType )
   {
      this.signType = signType;
   }

   public String getMachineNo()
   {
      return machineNo;
   }

   public void setMachineNo( String machineNo )
   {
      this.machineNo = machineNo;
   }


   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getStartDate()
   {
      return startDate;
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return endDate;
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getWorkId()
   {
      return workId;
   }

   public void setWorkId( String workId )
   {
      this.workId = workId;
   }

}
