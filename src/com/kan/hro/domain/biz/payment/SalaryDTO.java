package com.kan.hro.domain.biz.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�SalaryDTO  
 * ��������  
 * �����ˣ�Kevin  
 * ����ʱ�䣺2014-01-17  
 */
public class SalaryDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1030287169254622050L;

   // SalaryHeaderVO
   private SalaryHeaderVO salaryHeaderVO;

   // SalaryDetailVO List
   private List< SalaryDetailVO > salaryDetailVOs = new ArrayList< SalaryDetailVO >();

   public final SalaryHeaderVO getSalaryHeaderVO()
   {
      return salaryHeaderVO;
   }

   public final void setSalaryHeaderVO( SalaryHeaderVO salaryHeaderVO )
   {
      this.salaryHeaderVO = salaryHeaderVO;
   }

   public final List< SalaryDetailVO > getSalaryDetailVOs()
   {
      return salaryDetailVOs;
   }

   public final void setSalaryDetailVOs( List< SalaryDetailVO > salaryDetailVOs )
   {
      this.salaryDetailVOs = salaryDetailVOs;
   }

}
