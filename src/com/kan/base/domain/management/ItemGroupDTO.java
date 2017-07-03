package com.kan.base.domain.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemGroupDTO implements Serializable
{

   /**  
    *  Serial Version UID
    */
   private static final long serialVersionUID = 1180132725406410901L;

   private ItemGroupVO itemGroupVO = new ItemGroupVO();

   private List< ItemVO > itemVOs = new ArrayList< ItemVO >();

   public ItemGroupVO getItemGroupVO()
   {
      return itemGroupVO;
   }

   public void setItemGroupVO( ItemGroupVO itemGroupVO )
   {
      this.itemGroupVO = itemGroupVO;
   }

   public List< ItemVO > getItemVOs()
   {
      return itemVOs;
   }

   public void setItemVOs( List< ItemVO > itemVOs )
   {
      this.itemVOs = itemVOs;
   }

}
