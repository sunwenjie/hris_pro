package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ·â×°ColumnGroup¶ÔÏó
 * 
 * @author Kevin
 */
public class ColumnGroupDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 506883831764322045L;

   private ColumnGroupVO columnGroupVO;

   private List< ColumnVO > columnVOs = new ArrayList< ColumnVO >();

   public ColumnGroupVO getColumnGroupVO()
   {
      return columnGroupVO;
   }

   public void setColumnGroupVO( ColumnGroupVO columnGroupVO )
   {
      this.columnGroupVO = columnGroupVO;
   }

   public List< ColumnVO > getColumnVOs()
   {
      return columnVOs;
   }

   public void setColumnVOs( List< ColumnVO > columnVOs )
   {
      this.columnVOs = columnVOs;
   }

}
