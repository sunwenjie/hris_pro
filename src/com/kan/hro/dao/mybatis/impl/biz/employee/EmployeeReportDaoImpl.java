package com.kan.hro.dao.mybatis.impl.biz.employee;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.employee.EmployeeReportDao;
import com.kan.hro.domain.biz.employee.EmployeeReportVO;

public class EmployeeReportDaoImpl extends Context implements EmployeeReportDao
{

   @Override
   public int countEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeReportVOsByCondition", employeeReportVO );
   }

   @Override
   public List< Object > getEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeeReportVOsByCondition", employeeReportVO );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   @Override
   public List< Object > getEmployeeReportVOsByCondition( final EmployeeReportVO employeeReportVO, final RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeeReportVOsByCondition", employeeReportVO, rowBounds );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   @Override
   public int countEmployeeSalaryReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeeSalaryReportVOsByCondition", employeeReportVO );
   }

   @Override
   public List< Object > getEmployeeSalaryReportVOsByCondition( final EmployeeReportVO employeeReportVO ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeeSalaryReportVOsByCondition", employeeReportVO );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   @Override
   public List< Object > getEmployeeSalaryReportVOsByCondition( final EmployeeReportVO employeeReportVO, final RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeeSalaryReportVOsByCondition", employeeReportVO, rowBounds );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   @Override
   public int countEmployeePerformanceReportVOsByCondition( EmployeeReportVO employeeReportVO ) throws KANException
   {
      return ( Integer ) select( "countEmployeePerformanceReportVOsByCondition", employeeReportVO );
   }

   @Override
   public List< Object > getEmployeePerformanceReportVOsByCondition( EmployeeReportVO employeeReportVO ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeePerformanceReportVOsByCondition", employeeReportVO );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   @Override
   public List< Object > getEmployeePerformanceReportVOsByCondition( EmployeeReportVO employeeReportVO, RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getEmployeePerformanceReportVOsByCondition", employeeReportVO, rowBounds );
      for ( Object obj : objects )
      {
         decodeNumber( ( EmployeeReportVO ) obj );
      }
      return objects;
   }

   /**
    * 解密年薪
    * @param employeeContractVO
    */
   private void decodeNumber( final EmployeeReportVO employeeReportVO )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( ( employeeReportVO ).getContractRemark1() );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", Cryptogram.decodeNumber( Cryptogram.getPublicCode( employeeReportVO.getEmployeeId() ), objValue ) );
            employeeReportVO.setContractRemark1( jsonObject.toString() );
         }
      }
      catch ( Exception e )
      {
         System.err.println( "年薪解密失败" );
      }

   }

   @Override
   public int countContactsByCondition( EmployeeReportVO employeeReportVO ) throws KANException
   {
      return selectList( "countContactsByCondition", employeeReportVO ).size();
   }

   @Override
   public List< Object > getContactsByCondition( EmployeeReportVO employeeReportVO ) throws KANException
   {
      final List< Object > objects = selectList( "getContactsByCondition", employeeReportVO );
      setEntity( objects );
      return objects;
   }

   @Override
   public List< Object > getContactsByCondition( EmployeeReportVO employeeReportVO, RowBounds rowBounds ) throws KANException
   {
      final List< Object > objects = selectList( "getContactsByCondition", employeeReportVO, rowBounds );
      // 查下entityname,
      setEntity( objects );
      return objects;
   }

   /**
    * 设置entity for contacts
    * @param objects
    */
   private void setEntity( final List< Object > objects )
   {
      for ( int i = 0; i < objects.size(); i++ )
      {
         final EmployeeReportVO tmpVO = ( EmployeeReportVO ) objects.get( i );
         final String entityId = tmpVO.getEntityId();
         final EntityVO entityVO = KANConstants.getKANAccountConstants( tmpVO.getAccountId() ).getEntityVOByEntityId( entityId );
         if ( entityVO != null )
         {
            tmpVO.setEntityNameZH( entityVO.getNameZH() );
            tmpVO.setEntityNameEN( entityVO.getNameEN() );
         }
      }
   }

}
