package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�PositionDTO  
* ������������ְλ�����νṹ��  
* �����ˣ�Jack  
* ����ʱ�䣺2013-7-10 ����07:36:29  
* �޸��ˣ�Jack  
* �޸�ʱ�䣺2013-7-10 ����07:36:29  
* �޸ı�ע��  
* @version   
*   
*/
public class PositionDTO implements Serializable
{

   /**  
   * serialVersionUID
   *  
   * @since Ver 1.1  
   */

   private static final long serialVersionUID = -3849156748675320914L;

   // ��ǰPosition����
   private PositionVO positionVO = new PositionVO();

   private String employeeContractNumber;

   // ��������Position��
   private List< PositionDTO > positionDTOs = new ArrayList< PositionDTO >();

   public PositionVO getPositionVO()
   {
      return positionVO;
   }

   public void setPositionVO( PositionVO positionVO )
   {
      this.positionVO = positionVO;
   }

   public List< PositionDTO > getPositionDTOs()
   {
      return positionDTOs;
   }

   public void setPositionDTOs( List< PositionDTO > positionDTOs )
   {
      this.positionDTOs = positionDTOs;
   }

   public String getEmployeeContractNumber()
   {
      return employeeContractNumber;
   }

   public void setEmployeeContractNumber( String employeeContractNumber )
   {
      this.employeeContractNumber = employeeContractNumber;
   }

}
