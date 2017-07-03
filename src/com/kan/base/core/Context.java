package com.kan.base.core;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;

import java.sql.Connection;

public class Context
{

   // Instance the SQL session template
   private SqlSessionTemplate sqlSessionTemplate;

   // Set SQL session template
   public void setSqlSessionTemplate( final SqlSessionTemplate sqlSessionTemplate )
   {
      this.sqlSessionTemplate = sqlSessionTemplate;
   }

   // Execute select
   public Object select( final String statement, final Object parameter )
   {
      return sqlSessionTemplate.selectOne( statement, parameter );
   }

   // Execute select list
   public List< Object > selectList( final String statement, final Object parameter )
   {
      return sqlSessionTemplate.selectList( statement, parameter );
   }

   // Execute select list by RowBounds
   public List< Object > selectList( final String statement, final Object parameter, final RowBounds rowBounds )
   {
      return sqlSessionTemplate.selectList( statement, parameter, rowBounds );
   }

   // Execute insert
   public int insert( final String statement, final Object parameter )
   {
      return sqlSessionTemplate.insert( statement, parameter );
   }

   // Execute update
   public int update( final String statement, final Object parameter )
   {
      return sqlSessionTemplate.update( statement, parameter );
   }

   // Execute delete
   public int delete( final String statement, final Object parameter )
   {
      return sqlSessionTemplate.delete( statement, parameter );
   }

   /**
   * 获取数据库连接对象
   * @return
   */
   public Connection getConnection()
   {
      Connection connection = SqlSessionUtils.getSqlSession( sqlSessionTemplate.getSqlSessionFactory(), sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator() ).getConnection();
      return connection;
   }

}
