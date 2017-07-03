package com.kan.hro.dao.mybatis.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientDao;
import com.kan.hro.domain.biz.client.ClientBaseView;
import com.kan.hro.domain.biz.client.ClientVO;

public class ClientDaoImpl extends Context implements ClientDao
{

   @Override
   public int countClientVOsByCondition( final ClientVO clientVO ) throws KANException
   {
      return ( Integer ) select( "countClientVOsByCondition", clientVO );
   }

   @Override
   public List< Object > getClientVOsByCondition( final ClientVO clientVO ) throws KANException
   {
      return selectList( "getClientVOsByCondition", clientVO );
   }

   @Override
   public List< Object > getClientVOsByCondition( final ClientVO clientVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getClientVOsByCondition", clientVO, rowBounds );
   }

   @Override
   public ClientVO getClientVOByClientId( final String clientId ) throws KANException
   {
      return ( ClientVO ) select( "getClientVOByClientId", clientId );
   }

   @Override
   public ClientVO getClientVOByCorpId( final String clientId ) throws KANException
   {
      return ( ClientVO ) select( "getClientVOByCorpId", clientId );
   }

   @Override
   public int updateClient( final ClientVO clientVO ) throws KANException
   {
      return update( "updateClient", clientVO );
   }

   @Override
   public int insertClient( final ClientVO clientVO ) throws KANException
   {
      return insert( "insertClient", clientVO );
   }

   @Override
   public int deleteClient( final ClientVO clientVO ) throws KANException
   {
      return delete( "deleteClient", clientVO );
   }

   @Override
   public List< Object > getClientBaseViews( final String accountId ) throws KANException
   {
      return selectList( "getClientBaseViews", accountId );
   }

   @Override
   public List< Object > getClientBaseViewsByCondition( final ClientBaseView clientBaseView ) throws KANException
   {
      return selectList( "getClientBaseViewsByCondition", clientBaseView );
   }

   @Override
   public int delClientAndGroupRelationByGroupId( final ClientVO clientVO ) throws KANException
   {
      return update( "delClientAndGroupRelationByGroupId", clientVO );
   }

   @Override
   public int delClientAndGroupRelationByClientId( final ClientVO clientVO ) throws KANException
   {
      return update( "delClientAndGroupRelationByClientId", clientVO );
   }

   @Override
   public List< Object > getClientFullViews( final String accountId ) throws KANException
   {
      return selectList( "getClientFullViews", accountId );
   }

   @Override
   public List< Object > getClientVOsByEmployeeId( final String employeeId ) throws KANException
   {
      return selectList( "getClientVOsByEmployeeId", employeeId );
   }

   @Override
   public List< Object > getClientByAccountId( final String accountId ) throws KANException
   {

      return selectList( "getClientByAccountId", accountId );
   }
   
   @Override
   public ClientVO getClientVOByClientIdForPdf( String clientId ) throws KANException
   {
      return ( ClientVO ) select( "getClientVOByClientIdForPdf", clientId );
   }

   public ClientBaseView getClientNameById( final String clientId ) throws KANException
   {

      return ( ClientBaseView ) select( "getClientNameById", clientId );
   }

   @Override
   public List< Object > getClientBaseViewsByAccountId4LogoFile( final String accountId ) throws KANException
   {
      return selectList( "getClientBaseViewsByAccountId4LogoFile", accountId );
   }

   @Override
   public ClientVO getClientVOByTitle( String title ) throws KANException
   {
      return ( ClientVO ) select( "getClientVOByTitle", title );
   }
   
   
   @Override
   public ClientVO getClientVOByName(final  String name ) throws KANException
   {
      return ( ClientVO ) select( "getClientVOByName", name );
   }
}
