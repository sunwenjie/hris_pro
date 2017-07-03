package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.util.KANException;

public interface BusinessContractTemplateDao
{
   public abstract int countBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract List< Object > getBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract List< Object > getBusinessContractTemplateVOsByCondition( final BusinessContractTemplateVO businessContractTemplateVO, final RowBounds rowBounds )
         throws KANException;

   public abstract BusinessContractTemplateVO getBusinessContractTemplateVOByBusinessContractTemplateId( final String businessContractTemplateId ) throws KANException;

   public abstract int insertBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract int updateBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract int deleteBusinessContractTemplate( final BusinessContractTemplateVO businessContractTemplateVO ) throws KANException;

   public abstract List< Object > getBusinessContractTemplateVOsByAccountId( final String accountId ) throws KANException;

}
