package com.kan.base.web.renders.util;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

/**
 * Render for New or Modify Page
 * 
 * @author Kevin
 */
public class EmployeeUserManageRender
{
   @SuppressWarnings("unchecked")
   public static String generateDisplayBtnJS( final HttpServletRequest request, final String accessAction)
         throws KANException
   {
      
    // ��ʼ��StringBuffer
    final StringBuffer rs = new StringBuffer();
    String role =  EmployeeSecurityAction.getRole( request, null );
    //��Ա��¼
    if ( role!=null && role.equals( KANConstants.ROLE_EMPLOYEE ) )
    {
       //��Ա��Ϣ����ҳ��
       if (accessAction.equals( "HRO_BIZ_EMPLOYEE" )) {
          final EmployeeVO employeeVO = (EmployeeVO)request.getAttribute("employeeForm"); 
          //��ְ1 ����ְ3 �༭��ť����
          if(employeeVO!=null&&employeeVO.getStatus()!=null&&!"2".equals( employeeVO.getStatus() )&&!"4".equals( employeeVO.getStatus() )){
             rs.append( "$('#btnEdit').hide();" );
          }
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnList').hide();" );
       // ��Ա��ͬlist   
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnSubmit').hide();" );
          rs.append( "$('#btnDelete').hide();" );
          rs.append( "$('#exportExcel').remove();" );
          
       // ��Ա��ͬ����ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_MANAGE" )) {
          
//          final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractForm"); 
//          if(employeeContractVO!=null&&employeeContractVO.getStatus()!=null&&!"1".equals( employeeContractVO.getStatus() )&&!"4".equals( employeeContractVO.getStatus() )){
//             rs.append( "$('#btnEdit').hide();" );
//          }
          rs.append( "$('#btnEdit').hide();" );
          rs.append( "$('#copyInfo').hide();" );
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnTransfer').hide();" );
          //��Ա��ͬ н��ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
        //��Ա��ͬ �̱�ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_CB" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
        //��Ա��ͬ �籣ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_SB" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
          
        //��Ա��ͬ �Ӱ�ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_OT" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
        //��Ա��ͬ ����ҳ��  
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
        //��Ա��ͬ ����ҳ��
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
          
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_CONTRACT_LEAVE" )) {
          
          rs.append( "$('#btnAdd').hide();" );
          rs.append( "$('#btnEdit').hide();" );
          
       }
       
    }
    return rs.toString();
   }
   
   public static String generateDisabledJS( final HttpServletRequest request, final String accessAction)
         throws KANException
   {
      
    // ��ʼ��StringBuffer
    final StringBuffer rs = new StringBuffer();
    String role =  EmployeeSecurityAction.getRole( request, null );
    if ( role!=null && role.equals( KANConstants.ROLE_EMPLOYEE ) )
    {
       //Ա����Ϣ
       if (accessAction.equals( "HRO_BIZ_EMPLOYEE" )) {
             rs.append( "$('#branch').attr('disabled', 'disabled');");
             rs.append( "$('#owner').attr('disabled', 'disabled');");
             rs.append( "$('#status').attr('disabled', 'disabled');");
             rs.append( "$('#username').attr('disabled', 'disabled');");
//             rs.append( "disableLinkById('#addEmployeeContract');");
       //��ͬ��Ϣ
       }else if (accessAction.equals( "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_MANAGE" )) {
//          rs.append( "$('#employeeId').attr('class', 'employeeId');");
//          rs.append("$('#orderId').attr('class', 'orderId');");
//          rs.append("disableForm('manage_primary_form');");
//          rs.append( "$('#startDate').removeAttr('disabled');");
//          rs.append( "$('#endDate').removeAttr('disabled');");
//          rs.append( "$('#department ').removeAttr('disabled');");
//          rs.append( "$('#positionName').removeAttr('disabled');");
//          rs.append( "$('#additionalPosition').removeAttr('disabled');");
//          rs.append( "$('#lineManagerId').removeAttr('disabled');");
//          rs.append( "$('#resignDate').removeAttr('disabled');");
//          rs.append( "$('#description').removeAttr('disabled');");
          rs.append( "$('#orderId').parent().hide();");
          rs.append( "$('#settlementBranch').parent().remove();");
          
       //���
       }else if(accessAction.equals( "HRO_BIZ_ATTENDANCE_LEAVE_HEADER" )){
          rs.append( "$('#employeeId').attr('disabled', 'disabled');");
          rs.append( "$('#employeeId').attr('disabled', 'disabled');");
          rs.append( "$('#employeeId').next('a').remove();");
          rs.append( "getEmployee();");
          rs.append( "$('#employeeId').removeClass('important');");
       }
       
    }
    return rs.toString();
   }
   
   

   
}
