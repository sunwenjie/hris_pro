package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.BankTemplateDetailDao;
import com.kan.base.domain.define.BankTemplateDetailVO;
import com.kan.base.util.KANException;

public class BankTemplateDetailDaoImpl extends Context implements BankTemplateDetailDao
{

   @Override
   public int countBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return ( Integer ) select( "countBankTemplateDetailVOsByCondition", bankTemplateDetailVO );
   }

   @Override
   public List< Object > getBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return selectList( "getBankTemplateDetailVOsByCondition", bankTemplateDetailVO );
   }

   @Override
   public List< Object > getBankTemplateDetailVOsByCondition( final BankTemplateDetailVO bankTemplateDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBankTemplateDetailVOsByCondition", bankTemplateDetailVO, rowBounds );
   }

   @Override
   public BankTemplateDetailVO getBankTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException
   {
      return ( BankTemplateDetailVO ) select( "getBankTemplateDetailVOByTemplateDetailId", templateDetailId );
   }

   @Override
   public int insertBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return insert( "insertBankTemplateDetail", bankTemplateDetailVO );
   }

   @Override
   public int updateBankTemplateDetail( final BankTemplateDetailVO bankTemplateDetailVO ) throws KANException
   {
      return update( "updateBankTemplateDetail", bankTemplateDetailVO );
   }

   @Override
   public int deleteBankTemplateDetail( final String templateDetailId ) throws KANException
   {
      return delete( "deleteBankTemplateDetail", templateDetailId );
   }

   @Override
   public List< Object > getBankTemplateDetailVOsByTemplateHeaderId( String templateHeaderId ) throws KANException
   {
      return selectList( "getBankTemplateDetailVOsByTemplateHeaderId", templateHeaderId );
   }

}
