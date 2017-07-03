package com.kan.base.service.impl.define;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.BankTemplateDetailDao;
import com.kan.base.dao.inf.define.BankTemplateHeaderDao;
import com.kan.base.domain.define.BankTemplateDTO;
import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.BankTemplateHeaderService;
import com.kan.base.util.KANException;

public class BankTemplateHeaderServiceImpl extends ContextService implements BankTemplateHeaderService
{

   private BankTemplateDetailDao bankTemplateDetailDao;

   public BankTemplateDetailDao getBankTemplateDetailDao()
   {
      return bankTemplateDetailDao;
   }

   public void setBankTemplateDetailDao( BankTemplateDetailDao bankTemplateDetailDao )
   {
      this.bankTemplateDetailDao = bankTemplateDetailDao;
   }

   @Override
   public PagedListHolder getBankTemplateHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BankTemplateHeaderDao bankTemplateHeaderDao = ( BankTemplateHeaderDao ) getDao();
      pagedListHolder.setHolderSize( bankTemplateHeaderDao.countBankTemplateHeaderVOsByCondition( ( BankTemplateHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( bankTemplateHeaderDao.getBankTemplateHeaderVOsByCondition( ( BankTemplateHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( bankTemplateHeaderDao.getBankTemplateHeaderVOsByCondition( ( BankTemplateHeaderVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public BankTemplateHeaderVO getBankTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( ( BankTemplateHeaderDao ) getDao() ).getBankTemplateHeaderVOByTemplateHeaderId( templateHeaderId );
   }

   @Override
   public int insertBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return ( ( BankTemplateHeaderDao ) getDao() ).insertBankTemplateHeader( bankTemplateHeaderVO );
   }

   @Override
   public int updateBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return ( ( BankTemplateHeaderDao ) getDao() ).updateBankTemplateHeader( bankTemplateHeaderVO );
   }

   @Override
   public int deleteBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      try
      {
         // 开启事务
         startTransaction();

         // 获取BankTemplateDetailVO列表
         final List< Object > bankTemplateDetailVOs = this.getBankTemplateDetailDao().getBankTemplateDetailVOsByTemplateHeaderId( bankTemplateHeaderVO.getTemplateHeaderId() );

         // 存在BankTemplateDetailVO列表
         if ( bankTemplateDetailVOs != null && bankTemplateDetailVOs.size() > 0 )
         {
            for ( Object bankTemplateDetailVOObject : bankTemplateDetailVOs )
            {
               ( ( BankTemplateDetailVO ) bankTemplateDetailVOObject ).setDeleted( BankTemplateDetailVO.FALSE );
               ( ( BankTemplateDetailVO ) bankTemplateDetailVOObject ).setModifyBy( bankTemplateHeaderVO.getModifyBy() );
               ( ( BankTemplateDetailVO ) bankTemplateDetailVOObject ).setModifyDate( bankTemplateHeaderVO.getModifyDate() );
               this.bankTemplateDetailDao.updateBankTemplateDetail( ( ( BankTemplateDetailVO ) bankTemplateDetailVOObject ) );
            }
         }

         // 最后标记删除List Header
         bankTemplateHeaderVO.setDeleted( BankTemplateHeaderVO.FALSE );
         ( ( BankTemplateHeaderDao ) getDao() ).updateBankTemplateHeader( bankTemplateHeaderVO );

         // 提交事务 
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public List< BankTemplateDTO > getBankTemplateDTOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化BankTemplateDTO List
      final List< BankTemplateDTO > bankTemplateDTOs = new ArrayList< BankTemplateDTO >();

      // 获取AccountId下所有有效BankTemplateHeaderVO
      final List< Object > bankTemplateHeaderVOs = ( ( BankTemplateHeaderDao ) getDao() ).getBankTemplateHeaderVOsByAccountId( accountId );

      // 存在BankTemplateHeaderVO List 
      if ( bankTemplateHeaderVOs != null && bankTemplateHeaderVOs.size() > 0 )
      {
         for ( Object bankTemplateHeaderVOObject : bankTemplateHeaderVOs )
         {
            // 初始化BankTemplateDTO
            final BankTemplateDTO bankTemplateDTO = new BankTemplateDTO();

            bankTemplateDTO.setBankTemplateHeaderVO( ( BankTemplateHeaderVO ) bankTemplateHeaderVOObject );

            // 获取当前BankTemplateHeaderVO下的所有BankTemplateDetailVO List
            final List< Object > bankTemplateDetailVOs = this.getBankTemplateDetailDao().getBankTemplateDetailVOsByTemplateHeaderId( ( ( BankTemplateHeaderVO ) bankTemplateHeaderVOObject ).getTemplateHeaderId() );

            // 存在BankTemplateDetailVO List
            if ( bankTemplateDetailVOs != null && bankTemplateDetailVOs.size() > 0 )
            {
               for ( Object bankTemplateDetailVOObject : bankTemplateDetailVOs )
               {
                  bankTemplateDTO.getBankTemplateDetailVOs().add( ( BankTemplateDetailVO ) bankTemplateDetailVOObject );
               }
            }

            bankTemplateDTOs.add( bankTemplateDTO );
         }
      }

      return bankTemplateDTOs;
   }

}
