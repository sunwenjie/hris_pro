package com.kan.base.service.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.TableDao;
import com.kan.base.dao.inf.define.TableRelationDao;
import com.kan.base.domain.define.TableRelationVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableRelationService;
import com.kan.base.util.KANException;

public class TableRelationServiceImpl extends ContextService implements TableRelationService
{

   private TableDao tableDao;

   public TableDao getTableDao()
   {
      return tableDao;
   }

   public void setTableDao( TableDao tableDao )
   {
      this.tableDao = tableDao;
   }

   @Override
   public PagedListHolder getTableRelationVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TableRelationDao tableRelationDao = ( TableRelationDao ) getDao();
      pagedListHolder.setHolderSize( tableRelationDao.countTableRelationVOsByCondition( ( TableRelationVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( tableRelationDao.getTableRelationVOsByCondition( ( TableRelationVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( tableRelationDao.getTableRelationVOsByCondition( ( TableRelationVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public List< Object > getTableRelationVOsByCondition( TableRelationVO tableRelationVO ) throws KANException
   {
      return ( ( TableRelationDao ) getDao() ).getTableRelationVOsByCondition( tableRelationVO );
   }

   @Override
   public TableRelationVO getTableRelationVOsByTableRelationId( String tableRelationId ) throws KANException
   {
      return ( ( TableRelationDao ) getDao() ).getTableRelationVOTableRelationId( tableRelationId );
   }

   @Override
   public int insertTableRelationVO( TableRelationVO tableRelationVO )throws KANException
   { 
       return ( ( TableRelationDao ) getDao() ).insertTableRelationVO( tableRelationVO );
   }
   
   @Override
   public int updateTableRelationVO( TableRelationVO tableRelationVO )throws KANException
   {
      return ( ( TableRelationDao ) getDao() ).updateTableRelationVO( tableRelationVO );
   }
   
   @Override
   public int deleteTableRelationVO( String  tableRelationId)throws KANException
   {
      return ( ( TableRelationDao ) getDao() ).deleteTableRelationVO( tableRelationId );
   }
   
   

}
