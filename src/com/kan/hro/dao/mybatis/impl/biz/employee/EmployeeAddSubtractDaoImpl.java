package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeAddSubtractDao;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;

public class EmployeeAddSubtractDaoImpl extends Context implements EmployeeAddSubtractDao
{

	@Override
	public int countEmployeeAddSubtractsByCondition(EmployeeAddSubtract employeeAddSubtract) throws KANException {
		//��Ա
		if("1".equals(employeeAddSubtract.getOpType())){
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				 return ( Integer ) select( "countEmployeeAddByConditionForCB", employeeAddSubtract );
			}else{
				//��ͬ���籣
				 return ( Integer ) select( "countEmployeeAddByCondition", employeeAddSubtract );
			}
		}
		//��Ա
		else if("2".equals(employeeAddSubtract.getOpType())){
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				return ( Integer ) select( "countEmployeeSubtractByConditionForCB", employeeAddSubtract );
			}else{
				//��ͬ���籣
				return ( Integer ) select( "countEmployeeSubtractByCondition", employeeAddSubtract );
			}
		}
		return 0;
	}

	@Override
	public List<Object> getEmployeeAddSubtractsByCondition(EmployeeAddSubtract employeeAddSubtract) throws KANException {
		//��Ա
		if("1".equals(employeeAddSubtract.getOpType())){
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				return selectList( "getEmployeeAddByConditionForCB", employeeAddSubtract );
			}else{
				//��ͬ���籣
				 return selectList( "getEmployeeAddByCondition", employeeAddSubtract );
			}
			
		}
		//��Ա
		else if("2".equals(employeeAddSubtract.getOpType())){
			
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				return selectList( "getEmployeeSubtractByConditionForCB", employeeAddSubtract );
			}else{
				//��ͬ���籣
				return selectList( "getEmployeeSubtractByCondition", employeeAddSubtract );
			}
			
		}
		return Collections.emptyList();
	}

	@Override
	public List<Object> getEmployeeAddSubtractsByCondition(EmployeeAddSubtract employeeAddSubtract, RowBounds rowBounds) throws KANException {
		
		//��Ա
		if("1".equals(employeeAddSubtract.getOpType())){
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				return selectList( "getEmployeeAddByConditionForCB", employeeAddSubtract,rowBounds );
			}else{
				//��ͬ���籣
				 return selectList( "getEmployeeAddByCondition", employeeAddSubtract,rowBounds );
			}
			
		}
		//��Ա
		else if("2".equals(employeeAddSubtract.getOpType())){
			
			if("6".equals(employeeAddSubtract.getType())){
				//�̱�
				return selectList( "getEmployeeSubtractByConditionForCB", employeeAddSubtract,rowBounds );
			}else{
				//��ͬ���籣
				return selectList( "getEmployeeSubtractByCondition", employeeAddSubtract,rowBounds );
			}
			
		}
		return Collections.emptyList();
	}

	

   

}
