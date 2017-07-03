package com.kan.hro.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.payment.PayslipDetailView;
import com.kan.hro.domain.biz.payment.PayslipHeaderView;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;

public class TableAliasConstant
{

   //����sql����Ա����ͬ��hro_biz_employee_contract�������ı�ı��������ص���VO����keyΪclassName��valueΪalias
   public static final Map< String, String > mainAlias = new HashMap< String, String >();

   //����sql��Ա����ͬ��hro_biz_employee_contract���ı������Ѿ� join�˱�Ա����ͬ��ģ���Ҫ���ñ���;����Ҫ����
   public static final Map< String, String > foreignAlias = new HashMap< String, String >();

   static
   {
      mainAlias.put( OTHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( LeaveHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( TimesheetHeaderVO.class.getCanonicalName(), "a" );
      foreignAlias.put( TimesheetHeaderVO.class.getCanonicalName(), "d" );

      mainAlias.put( EmployeeContractSBVO.class.getCanonicalName(), "a" );
      foreignAlias.put( EmployeeContractSBVO.class.getCanonicalName(), "b" );

      mainAlias.put( CBBatchVO.class.getCanonicalName(), "b" );

      mainAlias.put( SBBatchVO.class.getCanonicalName(), "b" );

      mainAlias.put( EmployeeContractCBVO.class.getCanonicalName(), "a" );
      foreignAlias.put( EmployeeContractCBVO.class.getCanonicalName(), "b" );

      mainAlias.put( SBAdjustmentHeaderVO.class.getCanonicalName(), "a" );
      foreignAlias.put( SBAdjustmentHeaderVO.class.getCanonicalName(), "c" );

      mainAlias.put( AdjustmentHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( OTImportHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( LeaveImportHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( EmployeeContractSalaryVO.class.getCanonicalName(), "a" );
      foreignAlias.put( EmployeeContractSalaryVO.class.getCanonicalName(), "b" );

      mainAlias.put( PaymentBatchVO.class.getCanonicalName(), "b" );

      mainAlias.put( PaymentHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( PaymentAdjustmentHeaderVO.class.getCanonicalName(), "a" );

      mainAlias.put( PayslipHeaderView.class.getCanonicalName(), "a" );

      mainAlias.put( PayslipDetailView.class.getCanonicalName(), "b" );
      
      foreignAlias.put( PayslipDetailView.class.getCanonicalName(), "f" );
   }
}
