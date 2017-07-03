package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.SMSConfigDao;
import com.kan.base.domain.system.SMSConfigVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.SMSConfigService;
import com.kan.base.util.KANException;

public class SMSConfigServiceImpl extends ContextService implements SMSConfigService
{

   @Override
   public PagedListHolder getSMSConfigVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SMSConfigDao configDao = ( SMSConfigDao ) getDao();
      pagedListHolder.setHolderSize( configDao.countSMSConfigVOsByCondition( ( SMSConfigVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( configDao.getSMSConfigVOsByCondition( ( SMSConfigVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( configDao.getSMSConfigVOsByCondition( ( SMSConfigVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SMSConfigVO getSMSConfigVOByConfigId( final String configId ) throws KANException
   {
      return ( ( SMSConfigDao ) getDao() ).getSMSConfigVOByConfigId( configId );
   }

   @Override
   public int updateSMSConfig( final SMSConfigVO configVO ) throws KANException
   {
      return ( ( SMSConfigDao ) getDao() ).updateSMSConfig( configVO );
   }

   @Override
   public int insertSMSConfig( final SMSConfigVO configVO ) throws KANException
   {
      return ( ( SMSConfigDao ) getDao() ).insertSMSConfig( configVO );

   }

   @Override
   public void deleteSMSConfig( final SMSConfigVO configVO ) throws KANException
   {
      ( ( SMSConfigDao ) getDao() ).deleteSMSConfig( configVO );
   }

   @Override
   public List< Object > getSMSConfigVOs() throws KANException
   {
      return ( ( SMSConfigDao ) getDao() ).getSMSConfigVOs();
   }

}
