package com.kan.base.service.inf.define;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.define.ManagerDTO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TableService
{
   public abstract PagedListHolder getTableVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract List< Object > getAvailableTableVOs() throws KANException;

   public abstract TableVO getTableVOByTableId( final String TableId ) throws KANException;

   public abstract int insertTable( final TableVO tableVO ) throws KANException;

   public abstract int updateTable( final TableVO tableVO ) throws KANException;

   public abstract int deleteTable( final TableVO tableVO ) throws KANException;

   public abstract List< TableDTO > getTableDTOsByAccountId( final String accountId ) throws KANException;

   /**获取报表的dto 可局部更新tableDTOs
   * @param accountId
   * @param tableId
   * @return
   * @throws KANException
   */
   public abstract TableDTO getTableDTOByTableId( final String accountId, final String tableId, final ManagerDTO managerDTO ) throws KANException;

   public abstract List getReportDTOByTableId( final String accountId, final String tableId ) throws KANException;
   
   public static final Map< String, String[] > SOME_SPECIAL_VIEW_ID_MAP = new HashMap< String, String[] >();
}
