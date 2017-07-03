package com.kan.hro.web.actions.biz.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**
 *  employeeContract 的公共操作类
 *  为 社保 商报 请假 等 添加 劳动合同id和name
 * @author iori
 *
 */

public class EmployeeContractCommOperAction extends BaseAction
{

   public String addContractId_Name( HttpServletRequest request, String contractId ) throws KANException
   {
      // 初始化Service接口
      String contractName = "";
      try
      {
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

         if ( employeeContractVO == null )
         {
            throw new KANException( "ID为“" + contractId + "”劳动合同或服务协议已经被删除！" );
         }

         contractName = employeeContractVO.getNameZH() + " - " + employeeContractVO.getNameEN();
         // 判断是劳动合同还是服务协议
         request.setAttribute( "contractFlag", "1".equals( employeeContractVO.getFlag() ) ? "劳动合同" : "服务协议" );

         request.setAttribute( "employeeContractVO", employeeContractVO );

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return contractName;
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}
