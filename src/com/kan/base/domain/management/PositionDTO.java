package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：PositionDTO  
* 类描述：生成职位的树形结构用  
* 创建人：Jack  
* 创建时间：2013-7-10 下午07:36:29  
* 修改人：Jack  
* 修改时间：2013-7-10 下午07:36:29  
* 修改备注：  
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

   // 当前Position对象
   private PositionVO positionVO = new PositionVO();

   private String employeeContractNumber;

   // 用于生成Position树
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
