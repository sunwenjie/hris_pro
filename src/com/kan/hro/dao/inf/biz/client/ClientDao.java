package com.kan.hro.dao.inf.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientVO;

public interface ClientDao
{
   public abstract int countClientVOsByCondition( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientVOsByCondition( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientVOsByCondition( final ClientVO clientVO, RowBounds rowBounds ) throws KANException;

   public abstract ClientVO getClientVOByClientId( final String clientId ) throws KANException;
   
   public abstract ClientVO getClientVOByCorpId( final String clientId ) throws KANException;
   
   public abstract ClientVO getClientVOByTitle( final String title ) throws KANException;

   public abstract int updateClient( final ClientVO clientVO ) throws KANException;

   public abstract int insertClient( final ClientVO clientVO ) throws KANException;

   public abstract int deleteClient( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientBaseViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientBaseViewsByCondition( final ClientBaseView clientBaseView ) throws KANException;

   public abstract int delClientAndGroupRelationByGroupId( final ClientVO clientVO ) throws KANException;

   public abstract int delClientAndGroupRelationByClientId( final ClientVO clientVO ) throws KANException;

   public abstract List< Object > getClientFullViews( final String accountId ) throws KANException;

   public abstract List< Object > getClientVOsByEmployeeId( final String employeeId ) throws KANException;

   public abstract List< Object > getClientByAccountId( final String accountId ) throws KANException;

   public abstract ClientBaseView getClientNameById( final String clientId ) throws KANException;

   public abstract List< Object > getClientBaseViewsByAccountId4LogoFile( final String accountId ) throws KANException;
   
   public abstract ClientVO getClientVOByClientIdForPdf( final String clientId ) throws KANException;
   
   public abstract ClientVO getClientVOByName( final String name ) throws KANException;
}
