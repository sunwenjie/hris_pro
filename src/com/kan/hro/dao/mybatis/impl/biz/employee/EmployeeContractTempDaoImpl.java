package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeContractTempDao;
import com.kan.hro.domain.biz.employee.EmployeeContractTempVO;

public class EmployeeContractTempDaoImpl extends Context implements EmployeeContractTempDao
{
   @Override
   public int countEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeContractTempVOsByCondition", employeeContractTempVO );
   }

   @Override
   public List< Object > getEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO ) throws KANException
   {
      return selectList( "getEmployeeContractTempVOsByCondition", employeeContractTempVO );
   }

   @Override
   public List< Object > getEmployeeContractTempVOsByCondition( final EmployeeContractTempVO employeeContractTempVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getEmployeeContractTempVOsByCondition", employeeContractTempVO, rowBounds );
   }

   @Override
   public List< Object > getEmployeeContractTempVOsByBatchId( final String batchId ) throws KANException
   {
      List< Object > list = selectList( "getEmployeeContractTempVOsByBatchId", batchId );
      if ( list != null && list.size() > 0 )
      {
         for ( Object o : list )
         {
            decodeNumber( ( EmployeeContractTempVO ) o );
         }
         return list;
      }
      else
      {
         return null;
      }
   }

   @Override
   public EmployeeContractTempVO getEmployeeContractTempVOByContractId( final String contractId ) throws KANException
   {
      List< Object > list = selectList( "getEmployeeContractTempVOByContractId", contractId );
      if ( list != null && list.size() > 0 )
      {
         EmployeeContractTempVO returnVO = ( EmployeeContractTempVO ) list.get( 0 );
         if ( returnVO != null )
         {
            decodeNumber( returnVO );
         }
         return returnVO;
      }
      else
      {
         return null;
      }
   }

   @Override
   public int updateEmployeeContractTemp( final EmployeeContractTempVO employeeContractTempVO ) throws KANException
   {
      encodeNumber( employeeContractTempVO );
      update( "updateEmployeeContractTemp", employeeContractTempVO );
      decodeNumber( employeeContractTempVO );
      return 1;
   }

   @Override
   public int insertEmployeeContractTemp( final EmployeeContractTempVO employeeContractTempVO ) throws KANException
   {
      encodeNumber( employeeContractTempVO );
      insert( "insertEmployeeContractTemp", employeeContractTempVO );
      decodeNumber( employeeContractTempVO );
      return 1;
   }

   @Override
   public int deleteEmployeeContractTemp( final String contractId ) throws KANException
   {
      return delete( "deleteEmployeeContractTemp", contractId );
   }

   @Override
   public int updateEmployeeContractClientIdFromOrderIdByBatchId( String batchId ) throws KANException
   {
      return update( "updateEmployeeContractClientIdFromOrderIdByBatchId", batchId );
   }

   /**
    * 
    * @param employeeContractVO
    */
   private void decodeNumber( final EmployeeContractTempVO employeeContractTempVO )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( ( employeeContractTempVO ).getRemark1() );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", Cryptogram.decodeNumber( Cryptogram.getPublicCode( employeeContractTempVO.getEmployeeId() ), objValue ) );
            employeeContractTempVO.setRemark1( jsonObject.toString() );
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
   private void encodeNumber( final EmployeeContractTempVO employeeContractTempVO )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( ( employeeContractTempVO ).getRemark1() );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", Cryptogram.encodeNumber( Cryptogram.getPublicCode( employeeContractTempVO.getEmployeeId() ), objValue ) );
            employeeContractTempVO.setRemark1( jsonObject.toString() );
         }
      }
      catch ( Exception e )
      {
         System.err.println( "ƒÍ–Ωº”√‹ ß∞‹" );
      }
   }

}
