package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TableRelationService
{

   public abstract PagedListHolder getTableRelationVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException;

   public abstract List< Object > getTableRelationVOsByCondition( TableRelationVO tableRelationVO ) throws KANException;

   public abstract TableRelationVO getTableRelationVOsByTableRelationId( String tableRelationId ) throws KANException;

   public abstract int insertTableRelationVO( TableRelationVO tableRelationVO ) throws KANException;

   public abstract int updateTableRelationVO( TableRelationVO tableRelationVO ) throws KANException;

   public abstract int deleteTableRelationVO( String tableRelationId ) throws KANException;

}
