package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.RuleDao;
import com.kan.base.domain.system.RuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RuleService;
import com.kan.base.util.KANException;

public class RuleServiceImpl extends ContextService implements RuleService
{

   @Override
   public PagedListHolder getRuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final RuleDao ruleDao = ( RuleDao ) getDao();
      pagedListHolder.setHolderSize( ruleDao.countRuleVOsByCondition( ( RuleVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( ruleDao.getRuleVOsByCondition( ( RuleVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( ruleDao.getRuleVOsByCondition( ( RuleVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public RuleVO getRuleVOByRuleId( final String ruleId ) throws KANException
   {
      return ( ( RuleDao ) getDao() ).getRuleVOByRuleId( ruleId );
   }

   @Override
   public int updateRule( final RuleVO ruleVO ) throws KANException
   {
      return ( ( RuleDao ) getDao() ).updateRule( ruleVO );
   }

   @Override
   public int insertRule( final RuleVO ruleVO ) throws KANException
   {
      return ( ( RuleDao ) getDao() ).insertRule( ruleVO );

   }

   @Override
   public void deleteRule( final RuleVO ruleVO ) throws KANException
   {
      ( ( RuleDao ) getDao() ).deleteRule( ruleVO );
   }

   @Override
   public List< Object > getRuleVOs() throws KANException
   {
      return ( ( RuleDao ) getDao() ).getRuleVOs();
   }

}
