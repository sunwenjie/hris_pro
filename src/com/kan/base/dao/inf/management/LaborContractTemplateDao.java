package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.LaborContractTemplateVO;
import com.kan.base.util.KANException;

public interface LaborContractTemplateDao
{
   public abstract int countLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract List< Object > getLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract List< Object > getLaborContractTemplateVOsByCondition( final LaborContractTemplateVO laborContractTemplateVO, final RowBounds rowBounds ) throws KANException;

   public abstract LaborContractTemplateVO getLaborContractTemplateVOByLaborContractTemplateId( final String laborContractTemplateId ) throws KANException;

   public abstract int insertLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract int updateLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;

   public abstract int deleteLaborContractTemplate( final LaborContractTemplateVO laborContractTemplateVO ) throws KANException;
   
   public abstract List< Object > getLaborContractTemplateVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getLaborContractTemplateVOsByContractTypeId( final String contractTypeId ) throws KANException;
}

 