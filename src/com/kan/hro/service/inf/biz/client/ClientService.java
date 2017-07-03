package com.kan.hro.service.inf.biz.client;

import java.util.List;

import com.kan.base.domain.management.ContractTemplateClientVo;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientDTO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.base.biz.client.BaseClientService;

public interface ClientService extends BaseClientService
{
   public abstract int updateClient( final ClientVO clientVO ) throws KANException;

   public abstract void insertClient( final ClientVO clientVO ) throws KANException;

   public abstract int deleteClient( final ClientVO clientVO ) throws KANException;
   
   public abstract int delClientAndGroupRelationByClientId( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientBaseViewsByCondition( final ClientBaseView clientBaseView ) throws KANException;

   public abstract ClientDTO getClientDTOByClientVO( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientFullViews( final String accountId ) throws KANException;

   public abstract ClientDTO getClientDTOByClientId( final String clientId ) throws KANException;

   public abstract int submitClient( final ClientVO clinentVO ) throws KANException;
   
   public abstract List< Object > getClientVOsByEmployeeId( final String employeeId ) throws KANException;
   
   public abstract List< Object > getClientByAccountId(String accountId)throws KANException;

   public abstract List<ContractTemplateClientVo> clientIdsForTab(String templateId,String clientIds)throws KANException;
   
   public abstract ClientVO getClientVOByTitle( final String title ) throws KANException;
   
   public abstract ClientVO getClientVOByName( final String name ) throws KANException;
}
