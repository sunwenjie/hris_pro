package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractDao;
import com.kan.hro.domain.biz.employee.EmployeeContractBaseView;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;

public class EmployeeContractDaoImpl extends Context implements EmployeeContractDao
{

   @Override
   public int countEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractVOsByCondition", employeeContractVO );
   }

   @Override
   public List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractVOsByCondition", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO, final RowBounds rowBounds ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractVOsByCondition", employeeContractVO, rowBounds );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public EmployeeContractVO getEmployeeContractVOByContractId( final String contractId ) throws KANException
   {
      final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) select( "getEmployeeContractVOByContractId", contractId );
      if ( employeeContractVO != null )
      {
         decodeNumber( employeeContractVO );
      }
      return employeeContractVO;
   }

   @Override
   public List< Object > getEmployeeContractVOsByContractIds( final List< String > selectedIdList ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractVOsByContractIds", selectedIdList );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public int updateEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      encodeNumber( employeeContractVO );
      update( "updateEmployeeContract", employeeContractVO );
      decodeNumber( employeeContractVO );
      return 1;
   }

   @Override
   public int insertEmployeeContract( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      encodeNumber( employeeContractVO );
      insert( "insertEmployeeContract", employeeContractVO );
      decodeNumber( employeeContractVO );
      return 1;
   }

   @Override
   public int deleteEmployeeContract( final String contractId ) throws KANException
   {
      return delete( "deleteEmployeeContract", contractId );
   }

   @Override
   public List< Object > getEmployeeContractVOsByEmployeeId( final String employeeId ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractVOsByEmployeeId", employeeId );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByClientId( final EmployeeContractBaseView employeeContractBaseView ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractBaseViewsByClientId", employeeContractBaseView );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByAccountId( final String accountId ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractBaseViewsByAccountId", accountId );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getSBEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getSBEmployeeContractVOsByCondition", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getCBEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getCBEmployeeContractVOsByCondition", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getSettlementEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getSettlementEmployeeContractVOsByCondition", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getTSEmployeeContractVOsByCondition( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getTSEmployeeContractVOsByCondition", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public EmployeeContractBaseView getEmployeeContractBaseViewByContractId( final String contractId ) throws KANException
   {
      return ( EmployeeContractBaseView ) select( "getEmployeeContractBaseViewByContractId", contractId );
   }

   @Override
   public List< Object > getEmployeeContractVOsByClientId( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractVOsByClientId", employeeContractVO );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getEmployeeContractsDuringService( final List< String > selectedIdList ) throws KANException
   {
      final List< Object > objs = selectList( "getEmployeeContractsDuringService", selectedIdList );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   @Override
   public List< Object > getEmployeeContractBaseViewsByEmployeeId( final EmployeeContractVO employeeContractVO ) throws KANException
   {
      return selectList( "getEmployeeContractBaseViewsByEmployeeId", employeeContractVO );
   }

   @Override
   public List< Object > getServiceEmployeeContractVOsByOrderId( final String orderId ) throws KANException
   {
      final List< Object > objs = selectList( "getServiceEmployeeContractVOsByOrderId", orderId );
      for ( Object obj : objs )
      {
         decodeNumber( ( EmployeeContractVO ) obj );
      }
      return objs;
   }

   /**
    * 
    * @param employeeContractVO
    */
   private void decodeNumber( final EmployeeContractVO employeeContractVO )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( ( employeeContractVO ).getRemark1() );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", Cryptogram.decodeNumber( Cryptogram.getPublicCode( employeeContractVO.getEmployeeId() ), objValue ) );
            employeeContractVO.setRemark1( jsonObject.toString() );
         }
      }
      catch ( Exception e )
      {
         System.err.println( "ƒÍ–ΩΩ‚√‹ ß∞‹" );
      }

   }

   /**
    * º”√‹
    * @param employeeContractVO
    */
   private void encodeNumber( final EmployeeContractVO employeeContractVO )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( ( employeeContractVO ).getRemark1() );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeContractVO.getEmployeeId() ), objValue ) );
            employeeContractVO.setRemark1( jsonObject.toString() );
         }
      }
      catch ( Exception e )
      {
         System.err.println( "ƒÍ–Ωº”√‹ ß∞‹" );
      }
   }

   @Override
   public int transferEmployeeContractHROwner( Map< String, Object > parameterMap ) throws KANException
   {
      return update( "transferEmployeeContractHROwner", parameterMap );
   }
   
}
