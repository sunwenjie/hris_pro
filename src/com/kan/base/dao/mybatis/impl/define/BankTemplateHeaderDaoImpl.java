package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.BankTemplateHeaderDao;
import com.kan.base.domain.define.BankTemplateHeaderVO;
import com.kan.base.util.KANException;

public class BankTemplateHeaderDaoImpl extends Context implements BankTemplateHeaderDao
{

   @Override
   public int countBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countBankTemplateHeaderVOsByCondition", bankTemplateHeaderVO );
   }

   @Override
   public List< Object > getBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return selectList( "getBankTemplateHeaderVOsByCondition", bankTemplateHeaderVO );
   }

   @Override
   public List< Object > getBankTemplateHeaderVOsByCondition( final BankTemplateHeaderVO bankTemplateHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getBankTemplateHeaderVOsByCondition", bankTemplateHeaderVO, rowBounds );
   }

   @Override
   public BankTemplateHeaderVO getBankTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException
   {
      return ( BankTemplateHeaderVO ) select( "getBankTemplateHeaderVOByTemplateHeaderId", templateHeaderId );
   }

   @Override
   public int insertBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return insert( "insertBankTemplateHeader", bankTemplateHeaderVO );
   }

   @Override
   public int updateBankTemplateHeader( final BankTemplateHeaderVO bankTemplateHeaderVO ) throws KANException
   {
      return update( "updateBankTemplateHeader", bankTemplateHeaderVO );
   }

   @Override
   public int deleteBankTemplateHeader( final String templateHeaderId ) throws KANException
   {
      return delete( "deleteBankTemplateHeader", templateHeaderId );
   }

   @Override
   public List< Object > getBankTemplateHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getBankTemplateHeaderVOsByAccountId", accountId );
   }

}
