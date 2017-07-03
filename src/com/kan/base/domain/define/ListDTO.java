package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装List对象 - 包含Header和Detail
 * 
 * @author Kevin
 */
public class ListDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -2665664154337305224L;

   // 封装ListHeaderVO对象
   private ListHeaderVO listHeaderVO = new ListHeaderVO();

   // 封装ListDetailVO对象 - 从属当前ListHeaderVO对象
   private List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

   // 封装Sub ListDTO对象 - List
   private List< ListDTO > subListDTOs = new ArrayList< ListDTO >();

   // 当前列表使用到的搜索条件
   private SearchDTO searchDTO = new SearchDTO();

   public ListHeaderVO getListHeaderVO()
   {
      return listHeaderVO;
   }

   public void setListHeaderVO( ListHeaderVO listHeaderVO )
   {
      this.listHeaderVO = listHeaderVO;
   }

   public List< ListDetailVO > getListDetailVOs()
   {
      return listDetailVOs;
   }

   public void setListDetailVOs( List< ListDetailVO > listDetailVOs )
   {
      this.listDetailVOs = listDetailVOs;
   }

   public SearchDTO getSearchDTO()
   {
      return searchDTO;
   }

   public void setSearchDTO( SearchDTO searchDTO )
   {
      this.searchDTO = searchDTO;
   }

   // 获取列表名称
   public String getListName( final HttpServletRequest request )
   {
      if ( listHeaderVO != null )
      {
         if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return listHeaderVO.getNameZH();
         }
         else
         {
            return listHeaderVO.getNameEN();
         }
      }

      return "";
   }

   // 获取当前列表是否需要分页
   public boolean isPaged()
   {
      if ( listHeaderVO != null && listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
      {
         return true;
      }

      return false;
   }

   // 获取当前列表每页记录数
   public int getPageSize()
   {
      if ( listHeaderVO != null && listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
      {
         if ( listHeaderVO.getPageSize() != null && listHeaderVO.getPageSize().matches( "[0-9]*" ) )
         {
            return Integer.valueOf( listHeaderVO.getPageSize() );
         }
      }

      return 0;
   }

   // 获取当前列表预读页数
   public int getLoadPages()
   {
      if ( listHeaderVO != null && listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
      {
         if ( listHeaderVO.getLoadPages() != null && listHeaderVO.getLoadPages().matches( "[0-9]*" ) )
         {
            return Integer.valueOf( listHeaderVO.getLoadPages() );
         }
      }

      return 0;
   }

   // 获取当前列表是否搜索优先
   public boolean isSearchFirst()
   {
      if ( listHeaderVO != null && listHeaderVO.getIsSearchFirst() != null && listHeaderVO.getIsSearchFirst().trim().equals( "1" ) )
      {
         return true;
      }

      return false;
   }

   public List< ListDTO > getSubListDTOs()
   {
      return subListDTOs;
   }

   public void setSubListDTOs( List< ListDTO > subListDTOs )
   {
      this.subListDTOs = subListDTOs;
   }

}
