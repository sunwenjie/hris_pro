package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.util.KANException;

public interface TableRelationDao
{

   public  List<Object> getTableVOByTableId( final String tableId ) throws KANException;
   
   public abstract int countTableRelationVOsByCondition( final TableRelationVO tableRelation ) throws KANException;

   public abstract List< Object > getTableRelationVOsByCondition( final TableRelationVO tableRelation ) throws KANException;
   
   public abstract List< Object > getTableRelationVOsByCondition( final TableRelationVO tableRelation, final RowBounds rowBounds ) throws KANException;
   
   public abstract int insertTableRelationVO( final TableRelationVO tableRelationVO ) throws KANException;
   
   public abstract int updateTableRelationVO( final TableRelationVO tableRelationVO ) throws KANException;
   
   public abstract int deleteTableRelationVO( final String id ) throws KANException;
   
   public abstract TableRelationVO getTableRelationVOTableRelationId( String tableRelationId )throws KANException;
   
}
