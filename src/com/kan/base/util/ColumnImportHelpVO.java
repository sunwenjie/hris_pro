package com.kan.base.util;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.management.ItemVO;

public class ColumnImportHelpVO
{

   private int index;
   private int nextIndex = -1;
   // 类型  1：公司，2：个人、
   private int type;
   private ColumnVO columnVO;
   private ItemVO itemVO;

   public int getIndex()
   {
      return index;
   }

   public void setIndex( int index )
   {
      this.index = index;
   }

   public int getNextIndex()
   {
      return nextIndex;
   }

   public void setNextIndex( int nextIndex )
   {
      this.nextIndex = nextIndex;
   }

   public ColumnVO getColumnVO()
   {
      return columnVO;
   }

   public void setColumnVO( ColumnVO columnVO )
   {
      this.columnVO = columnVO;
   }

   public ItemVO getItemVO()
   {
      return itemVO;
   }

   public void setItemVO( ItemVO itemVO )
   {
      this.itemVO = itemVO;
   }

   public int getType()
   {
      return type;
   }

   public void setType( int type )
   {
      this.type = type;
   }


}
