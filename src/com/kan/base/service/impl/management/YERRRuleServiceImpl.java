package com.kan.base.service.impl.management;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.YERRRuleDao;
import com.kan.base.domain.management.YERRRuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.YERRRuleService;
import com.kan.base.util.KANException;

public class YERRRuleServiceImpl extends ContextService implements YERRRuleService
{

   @Override
   public PagedListHolder getYERRRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final YERRRuleDao languageDao = ( YERRRuleDao ) getDao();
      pagedListHolder.setHolderSize( languageDao.countYERRRuleVOsByCondition( ( YERRRuleVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( languageDao.getYERRRuleVOsByCondition( ( YERRRuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( languageDao.getYERRRuleVOsByCondition( ( YERRRuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public YERRRuleVO getYERRRuleVOByRuleId( final String ruleId ) throws KANException
   {
      return ( ( YERRRuleDao ) getDao() ).getYERRRuleVOByRuleId( ruleId );
   }

   @Override
   public int insertYERRRule( final YERRRuleVO yerrRule ) throws KANException
   {
      return ( ( YERRRuleDao ) getDao() ).insertYERRRule( yerrRule );
   }

   @Override
   public int updateYERRRule( final YERRRuleVO yerrRule ) throws KANException
   {
      return ( ( YERRRuleDao ) getDao() ).updateYERRRule( yerrRule );
   }

   @Override
   public int deleteYERRRule( final YERRRuleVO yerrRule ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final YERRRuleVO modifyObject = ( ( YERRRuleDao ) getDao() ).getYERRRuleVOByRuleId( yerrRule.getRuleId() );
      modifyObject.setDeleted( YERRRuleVO.FALSE );
      return ( ( YERRRuleDao ) getDao() ).updateYERRRule( modifyObject );
   }

   @Override
   public List< Object > getYERRRuleVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( YERRRuleDao ) getDao() ).getYERRRuleVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getYERRRuleVOsByMapParameter( Map< String, Object > parameterMap ) throws KANException
   {
      return ( ( YERRRuleDao ) getDao() ).getYERRRuleVOsByMapParameter( parameterMap );
   }

}
