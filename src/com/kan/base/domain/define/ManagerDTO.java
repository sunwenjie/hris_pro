package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManagerDTO implements Serializable
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = -2323275906723876813L;

   // ManagerHeaderVO
   private ManagerHeaderVO managerHeaderVO = new ManagerHeaderVO();

   // ManagerDetailVOÁÐ±í
   private List< ManagerDetailVO > managerDetailVOs = new ArrayList< ManagerDetailVO >();

   public ManagerHeaderVO getManagerHeaderVO()
   {
      return managerHeaderVO;
   }

   public void setManagerHeaderVO( ManagerHeaderVO managerHeaderVO )
   {
      this.managerHeaderVO = managerHeaderVO;
   }

   public List< ManagerDetailVO > getManagerDetailVOs()
   {
      return managerDetailVOs;
   }

   public void setManagerDetailVOs( List< ManagerDetailVO > managerDetailVOs )
   {
      this.managerDetailVOs = managerDetailVOs;
   }

}
