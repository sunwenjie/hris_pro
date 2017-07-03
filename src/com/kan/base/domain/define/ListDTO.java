package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * ��װList���� - ����Header��Detail
 * 
 * @author Kevin
 */
public class ListDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -2665664154337305224L;

   // ��װListHeaderVO����
   private ListHeaderVO listHeaderVO = new ListHeaderVO();

   // ��װListDetailVO���� - ������ǰListHeaderVO����
   private List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

   // ��װSub ListDTO���� - List
   private List< ListDTO > subListDTOs = new ArrayList< ListDTO >();

   // ��ǰ�б�ʹ�õ�����������
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

   // ��ȡ�б�����
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

   // ��ȡ��ǰ�б��Ƿ���Ҫ��ҳ
   public boolean isPaged()
   {
      if ( listHeaderVO != null && listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().trim().equals( "1" ) )
      {
         return true;
      }

      return false;
   }

   // ��ȡ��ǰ�б�ÿҳ��¼��
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

   // ��ȡ��ǰ�б�Ԥ��ҳ��
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

   // ��ȡ��ǰ�б��Ƿ���������
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
