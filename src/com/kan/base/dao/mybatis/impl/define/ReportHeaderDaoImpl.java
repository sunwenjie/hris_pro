package com.kan.base.dao.mybatis.impl.define;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ReportHeaderDao;
import com.kan.base.domain.define.ReportHeaderVO;
import com.kan.base.util.KANException;

public class ReportHeaderDaoImpl extends Context implements ReportHeaderDao
{

   @Override
   public int countReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countReportHeaderVOsByCondition", reportHeaderVO );
   }

   @Override
   public List< Object > getReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      return selectList( "getReportHeaderVOsByCondition", reportHeaderVO );
   }

   @Override
   public List< Object > getReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getReportHeaderVOsByCondition", reportHeaderVO, rowBounds );
   }

   @Override
   public ReportHeaderVO getReportHeaderVOByReportHeaderId( final String reportHeaderId ) throws KANException
   {
      return ( ReportHeaderVO ) select( "getReportHeaderVOByReportHeaderId", reportHeaderId );
   }

   @Override
   public int insertReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      return insert( "insertReportHeader", reportHeaderVO );
   }

   @Override
   public int updateReportHeader( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      return update( "updateReportHeader", reportHeaderVO );
   }

   @Override
   public int deleteReportHeader( final String reportHeaderId ) throws KANException
   {
      return delete( "deleteReportHeader", reportHeaderId );
   }

   @Override
   public List< Map< String, Object > > executeReportHeader( String sql, final RowBounds rowBounds, final Object object ) throws KANException
   {
      ResultSet rs = null;
      Connection connection = null;
      Statement statement = null;
      try
      {
         connection = this.getConnection();
         // 执行静态sql语句
         if ( rowBounds != null )
         {
            int start = 0;
            int end = rowBounds.getLimit();
            if ( rowBounds.getOffset() == 0 )
            {
               start = 0;
            }
            else
            {
               start = rowBounds.getLimit() * rowBounds.getOffset();
            }
            statement = connection.createStatement();
            sql += " LIMIT " + start + " , " + end;
         }
         else
         {
            statement = connection.createStatement();
         }

         // 执行SQL查询语句，返回查询数据的结果集  
         rs = statement.executeQuery( sql );
         ResultSetMetaData ramd = rs.getMetaData();
         int columnSize = ramd.getColumnCount(); //有多少列
         List< Map< String, Object >> returnList = new ArrayList< Map< String, Object >>();
         Map< String, Object > map = null;
         while ( rs.next() )
         {
            map = new HashMap< String, Object >();
            for ( int i = 1; i <= columnSize; i++ )
            {
               String columnName = ramd.getColumnLabel( i );//列字段的名字
               map.put( columnName, rs.getObject( columnName ) );
            }
            returnList.add( map );
         }

         return returnList;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      finally
      {
         try
         {
            if ( rs != null )
            {
               rs.close();
            }
            if ( statement != null )
            {
               statement.close();
            }
            if ( connection != null )
            {
               connection.close();
            }
         }
         catch ( SQLException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

   }

   @Override
   public int countReportHeader( final String sql ) throws KANException
   {
      ResultSet rs = null;
      Connection connection = null;
      Statement statement = null;
      try
      {
         // 执行静态sql语句
         connection = this.getConnection();
         statement = connection.createStatement();
         String tempSQL = "SELECT COUNT(1) FROM " + "( " + sql + " ) temp";
         rs = statement.executeQuery( tempSQL );
         System.out.println( tempSQL );
         int count = 0;

         while ( rs.next() )
         {
            count = rs.getInt( 1 );
         }

         // 关闭连接
         return count;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      finally
      {
         try
         {
            if ( rs != null )
            {
               rs.close();
            }
            if ( statement != null )
            {
               statement.close();
            }
            if ( connection != null )
            {
               connection.close();
            }
         }
         catch ( SQLException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   @Override
   public List< Object > getAccountReportHeaderVOsByCondition( final ReportHeaderVO reportHeaderVO ) throws KANException
   {
      return selectList( "getAccountReportHeaderVOsByCondition", reportHeaderVO );
   }
}
