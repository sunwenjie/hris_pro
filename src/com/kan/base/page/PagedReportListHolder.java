/*
 * Created on 2005-6-19
 */
package com.kan.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Kevin Jin
 */
public class PagedReportListHolder implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7275019567710779875L;

   private int page;

   private int pageSize = 20;

   private int holderSize;

   private Object object;

   private Object additionalObject;

   private List< Map<String,Object> > sourceMap = new ArrayList<  Map<String,Object> >();

   /**
    * Constructor
    * 
    * @param source
    */
   public PagedReportListHolder()
   {
      super();
   }

   /**
    * Constructor
    * 
    * @param source
    */
   public PagedReportListHolder( final List< Map<String,Object> > sourceMap )
   {
      super();
      this.sourceMap = sourceMap;
   }

   /**
    * Constructor
    * 
    * @param source
    */
   public PagedReportListHolder( final int holderSize,  final List< Map<String,Object> > sourceMap  )
   {
      super();
      this.holderSize = holderSize;
      this.sourceMap = sourceMap;
   }

   /**
    * Get total size in the fetch
    * 
    * @return Returns the holderSize.
    */
   public int getHolderSize()
   {
      return holderSize;
   }

   /**
    * Set total size in the fetch
    * 
    * @param holderSize
    *            The holderSize to set.
    */
   public void setHolderSize( final int holderSize )
   {
      this.holderSize = holderSize;
   }

   /**
    * Get current page
    * 
    * @return Returns the page.
    */
   public int getPage()
   {
      return page;
   }

   /**
    * Get first page
    * 
    * @return Returns the page.
    */
   public int getFirstPage()
   {
      return 0;
   }

   /**
    * Get previous page
    * 
    * @return Returns the page.
    */
   public int getPreviousPage()
   {
      return page > 0 ? page - 1 : 0;
   }

   /**
    * Get next page
    * 
    * @return Returns the page.
    */
   public int getNextPage()
   {
      return page < getPageCount() - 1 ? page + 1 : getPageCount() - 1;
   }

   /**
    * Get last page
    * 
    * @return Returns the page.
    */
   public int getLastPage()
   {
      return getPageCount() - 1;
   }

   /**
    * Get real page
    * 
    * @return Returns the page.
    */
   public int getRealPage()
   {
      if ( this.sourceMap.size() == 0 )
      {
         return 0;
      }
      else
      {
         return page + 1;
      }
   }

   /**
    * Set current page
    * 
    * @param page
    *            The page to set.
    */
   public void setPage( final String page )
   {
      if ( page != null && page.trim().matches( "[0-9]+" ) )
      {
         this.page = Integer.parseInt( page );
      }
   }

   /**
    * Get total page counts in this fetch
    * 
    * @return Returns the pageCount.
    */
   public int getPageCount()
   {
      return ( int ) Math.ceil( ( double ) holderSize / ( double ) pageSize );
   }

   /**
    * Get how many objects in one page
    * 
    * @return Returns the pageSize.
    */
   public int getPageSize()
   {
      return pageSize;
   }

   /**
    * Set how many objects in one page
    * 
    * @param pageSize
    *            The pageSize to set.
    */
   public void setPageSize( final int pageSize )
   {
      this.pageSize = pageSize;
   }

   /**
    * Get source
    * 
    * @return
    */
   public List< Map<String, Object >> getSourceMap()
   {
      return sourceMap;
   }

   /**
    * Set source
    * 
    * @param source
    */
   public void setSourceMap( final List< Map<String, Object >> sourceMap )
   {
      this.sourceMap = sourceMap;
   }

   /**
    * Get index start
    * 
    * @return
    */
   public int getIndexStart()
   {
      if ( this.sourceMap.size() == 0 )
      {
         return 0;
      }
      else
      {
         return this.page * this.pageSize + 1;
      }
   }

   /**
    * Get index end
    * 
    * @return
    */
   public int getIndexEnd()
   {
      int returnInt = this.page * this.pageSize + this.pageSize;
      return returnInt > this.holderSize ? this.holderSize : returnInt;
   }

   public Object getObject()
   {
      return object;
   }

   public void setObject( final Object object )
   {
      this.object = object;
   }

   public Object getAdditionalObject()
   {
      return additionalObject;
   }

   public void setAdditionalObject( Object additionalObject )
   {
      this.additionalObject = additionalObject;
   }

   public String getCurrentSortClass( final String sortColumn )
   {
	   return "";
//      if ( this.object != null )
//      {
//         final BaseVO baseVO = ( BaseVO ) this.object;
//
//         if ( baseVO != null && baseVO.getSortColumn() != null && baseVO.getSortColumn().equals( sortColumn ) )
//         {
//            if ( baseVO.getSortOrder().equalsIgnoreCase( "asc" ) )
//            {
//               return "headerSortUp";
//            }
//            else
//            {
//               return "headerSortDown";
//            }
//         }
//         else
//         {
//            return "";
//         }
//      }
//      else
//      {
//         return "";
//      }
   }

   public String getNextSortOrder( final String sortColumn )
   {
      if ( StringUtils.isNotBlank(sortColumn) ){
            if ( sortColumn.equalsIgnoreCase( "asc" ) )
            {
               return "desc";
            }
            else
            {
               return "asc";
            }
         }
         return "null";
   }

   public String getSortColumn()
   {
//      if ( this.object != null )
//      {
//         final BaseVO baseVO = ( BaseVO ) this.object;
//
//         if ( baseVO.getSortColumn() != null && !baseVO.getSortColumn().trim().equalsIgnoreCase( "null" ) )
//         {
//            return baseVO.getSortColumn();
//         }
//      }
      return "";
   }

   public String getSortOrder()
   {
//      if ( this.object != null )
//      {
//         final BaseVO baseVO = ( BaseVO ) this.object;
//
//         if ( baseVO.getSortOrder() != null && !baseVO.getSortOrder().trim().equalsIgnoreCase( "null" ) )
//         {
//            return baseVO.getSortOrder();
//         }
//      }
      return "";
   }

   public String getSelectedIds()
   {
//      if ( this.object != null )
//      {
//         final BaseVO baseVO = ( BaseVO ) this.object;
//
//         if ( baseVO.getSelectedIds() != null && !baseVO.getSelectedIds().trim().equalsIgnoreCase( "null" ) )
//         {
//            return baseVO.getSelectedIds();
//         }
//      }
      return "";
   }
}