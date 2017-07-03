package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.ConstantDao;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ConstantService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;

public class ConstantServiceImpl extends ContextService implements ConstantService
{

   @Override
   public PagedListHolder getConstantVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ConstantDao constantDao = ( ConstantDao ) getDao();
      pagedListHolder.setHolderSize( constantDao.countConstantVOsByCondition( ( ConstantVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( constantDao.getConstantVOsByCondition( ( ConstantVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( constantDao.getConstantVOsByCondition( ( ConstantVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ConstantVO getConstantVOByConstantId( final String constantId ) throws KANException
   {
      return ( ( ConstantDao ) getDao() ).getConstantVOByConstantId( constantId );
   }

   @Override
   public int insertConstant( final ConstantVO constantVO ) throws KANException
   {
      return ( ( ConstantDao ) getDao() ).insertConstant( constantVO );
   }

   @Override
   public int updateConstant( final ConstantVO constantVO ) throws KANException
   {
      return ( ( ConstantDao ) getDao() ).updateConstant( constantVO );
   }

   @Override
   public int deleteConstant( final ConstantVO constantVO ) throws KANException
   {
      // 标记删除
      constantVO.setDeleted( ConstantVO.FALSE );
      return updateConstant( constantVO );
   }

   @Override
   public List< Object > getConstantVOsByAccountId( final String accountId ) throws KANException
   {
      // 初始化参数列表
      final List< Object > constantVOs = new ArrayList< Object >();

      // 准备搜索条件
      ConstantVO constantVO = new ConstantVO();
      constantVO.setAccountId( KANConstants.SUPER_ACCOUNT_ID );
      constantVO.setStatus( ColumnVO.TRUE );
      // 装载系统定义的参数
      constantVOs.addAll( ( ( ConstantDao ) getDao() ).getConstantVOsByCondition( constantVO ) );

      if ( accountId != null && !accountId.trim().equals( KANConstants.SUPER_ACCOUNT_ID ) )
      {
         // 装载账户定义的参数
         constantVO.setAccountId( accountId );
         constantVOs.addAll( ( ( ConstantDao ) getDao() ).getConstantVOsByCondition( constantVO ) );
      }

      return constantVOs;
   }

}
