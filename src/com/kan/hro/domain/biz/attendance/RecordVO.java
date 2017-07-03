package com.kan.hro.domain.biz.attendance;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class RecordVO extends BaseVO
{

   // Serial Version UID
   private static final long serialVersionUID = 2272869867709316037L;

   // ���ڼ�¼ID
   private String recordId;

   // Զ���ն˿��ڼ�¼ID
   private String serialId;

   // Ա��ID
   private String employeeId;

   // Ա�����
   private String employeeNo;

   // Ա�����������ģ�
   private String employeeNameZH;

   // Ա��������Ӣ�ģ�
   private String employeeNameEN;

   // ������
   private String signDate;

   // ��ʱ��
   private String signTime;

   // ǩ����ʽ
   private String signType;

   // �豸���
   private String machineNo;

   // ��ע
   private String description;
   
   // for app
   // ��ѯ��ʼ������ʱ������
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
