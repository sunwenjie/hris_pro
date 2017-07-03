package com.kan.hro.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

import com.kan.base.util.KANConstants;
import com.kan.base.util.KANUtil;

/**
 *   
 * 项目名称：HRO_V1  
 * 类名称：DataAuthorityInterceptor  
 * 类描述： 数据权限拦截器
 * 创建人：Kevin  
 * 创建时间：2014-3-27  
 *
 */

@Intercepts(value = { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DataAuthorityInterceptor implements Interceptor
{

   @Override
   public Object intercept( Invocation invocation ) throws Throwable
   {

      /*  //第一个拦截不需要分离代理对象链  
        StatementHandler statementHandler = ( StatementHandler ) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject( statementHandler );

        // 初始化DefaultParameterHandler
        final DefaultParameterHandler parameterHandler = ( DefaultParameterHandler ) metaStatementHandler.getValue( "delegate.parameterHandler" );

        // Get OriginalSql
        String originalSql = ( String ) metaStatementHandler.getValue( "delegate.boundSql.sql" );

        // 用于对敏感数据进行解密
        if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.contains( "PRIVATE_CODE" ) )
        {
           originalSql = originalSql.replace( "PRIVATE_CODE", KANConstants.PRIVATE_CODE );
           // 回写SQL
           metaStatementHandler.setValue( "delegate.boundSql.sql", originalSql );
        }

        if ( parameterHandler == null || parameterHandler.getParameterObject() == null )
        {
           return invocation.proceed();
        }

        // 初始化Concat String
        String concatString = "";

        // EmployeeVO, EmployeeContractVO, ClientOrderHeaderVO, or ClientVO
        if ( parameterHandler.getParameterObject() instanceof EmployeeVO || parameterHandler.getParameterObject() instanceof EmployeeContractVO
              || parameterHandler.getParameterObject() instanceof ClientOrderHeaderVO || parameterHandler.getParameterObject() instanceof ClientVO
              || parameterHandler.getParameterObject() instanceof ClientContractVO || parameterHandler.getParameterObject() instanceof VendorVO
              || parameterHandler.getParameterObject() instanceof VendorContactVO )
        {
           final String hasIn = ( ( BaseVO ) parameterHandler.getParameterObject() ).getHasIn();
           final String notIn = ( ( BaseVO ) parameterHandler.getParameterObject() ).getNotIn();

           if ( StringUtils.isBlank( hasIn ) && StringUtils.isBlank( notIn ) )
           {
              return invocation.proceed();
           }

           //Employee的数据权限需同时考虑Employee和EmployeeContract
           boolean isEmployee = false;

           if ( parameterHandler.getParameterObject() instanceof EmployeeVO )
           {
              isEmployee = true;
           }

           boolean isClientOrder = false;

           if ( parameterHandler.getParameterObject() instanceof ClientOrderHeaderVO )
           {
              isClientOrder = true;
           }

           if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.toLowerCase().contains( "select" ) )
           {
              final int indexOfGroupBy = originalSql.toLowerCase().indexOf( "group by" );
              final int indexOfOrderBy = originalSql.toLowerCase().indexOf( "order by" );

              if ( StringUtils.isNotBlank( hasIn ) )
              {
                 if ( isEmployee )
                 {
                    concatString = concatString + " AND (a.owner in (" + hasIn + ") or b.owner in (" + hasIn + ")) ";
                 }
                 else if ( isClientOrder )
                 {
                    concatString = concatString + " AND d.owner in (" + hasIn + ") ";
                 }
                 else
                 {
                    concatString = concatString + " AND a.owner in (" + hasIn + ") ";
                 }
              }

              if ( StringUtils.isNotBlank( notIn ) )
              {
                 if ( isEmployee )
                 {
                    concatString = concatString + " AND (a.owner not in (" + notIn + ") AND b.owner not in (" + notIn + ")) ";
                 }
                 else if ( isClientOrder )
                 {
                    concatString = concatString + " AND d.owner not in (" + notIn + ") ";
                 }
                 else
                 {
                    concatString = concatString + " AND a.owner not in (" + notIn + ") ";
                 }
              }

              // 存在Group By语句
              if ( indexOfGroupBy > 0 )
              {
                 originalSql = originalSql.substring( 0, indexOfGroupBy - 1 ) + concatString + originalSql.substring( indexOfGroupBy );
              }
              // 存在Order By语句
              else if ( indexOfOrderBy > 0 )
              {
                 originalSql = originalSql.substring( 0, indexOfOrderBy - 1 ) + concatString + originalSql.substring( indexOfOrderBy );
              }
              else
              {
                 originalSql = originalSql + concatString;
              }
           }
        }
        //通用拦截
        else
        {
           final String className = parameterHandler.getParameterObject().getClass().getCanonicalName();

           if ( TableAliasConstant.mainAlias.get( className ) == null )
           {
              return invocation.proceed();
           }

           final String hasIn = ( ( BaseVO ) parameterHandler.getParameterObject() ).getHasIn();
           final String notIn = ( ( BaseVO ) parameterHandler.getParameterObject() ).getNotIn();

           if ( StringUtils.isBlank( hasIn ) && StringUtils.isBlank( notIn ) )
           {
              return invocation.proceed();
           }

           //表hro_biz_employee_contract的别名
           String foreignAlias = "hbizemp";

           if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.toLowerCase().contains( "select" ) )
           {
              // 初始化SQL中Where出现的位置
              final List< Integer > whereIndexs = new ArrayList< Integer >();
              // 初始化搜索位置
              int lastWhereIndex = 0;

              // 遍历到最后一个Where为止
              while ( originalSql.toLowerCase().indexOf( "where", lastWhereIndex ) > 0 )
              {
                 lastWhereIndex = originalSql.toLowerCase().indexOf( "where", lastWhereIndex );
                 whereIndexs.add( lastWhereIndex );
                 lastWhereIndex = lastWhereIndex + "where".length();
              }

              //已经Join Owner表的
              if ( originalSql.toLowerCase().contains( " join hro_biz_employee_contract " ) )
              {
                 if ( TableAliasConstant.foreignAlias.get( className ) != null )
                 {
                    foreignAlias = TableAliasConstant.foreignAlias.get( className );
                 }
                 else
                 {
                    throw new KANException( "系统出错。" );
                 }
              }
              else
              {
                 final String leftJoinEmployeeContract = " left join hro_biz_employee_contract " + foreignAlias + " on " + TableAliasConstant.mainAlias.get( className )
                       + ".contractId = " + foreignAlias + ".contractId ";

                 //把Left Join放在Where 前面
                 if ( whereIndexs != null && whereIndexs.size() > 0 )
                 {
                    for ( Integer whereIndex : whereIndexs )
                    {
                       originalSql = originalSql.substring( 0, whereIndex - 1 ) + leftJoinEmployeeContract + originalSql.substring( whereIndex );
                    }
                 }
              }

              if ( StringUtils.isNotBlank( hasIn ) )
              {
                 concatString = concatString + " AND " + foreignAlias + ".owner in (" + hasIn + ") ";
              }

              if ( StringUtils.isNotBlank( notIn ) )
              {
                 concatString = concatString + " AND " + foreignAlias + ".owner not in (" + notIn + ") ";
              }

              // 在Where的最后面放上条件
              if ( whereIndexs != null && whereIndexs.size() > 0 )
              {
                 for ( Integer whereIndex : whereIndexs )
                 {
                    // 插入条件的最小位置
                    int minConditionIndex = 0;
                    final int indexOfGroupBy = originalSql.toLowerCase().indexOf( "group by", whereIndex );
                    final int indexOfOrderBy = originalSql.toLowerCase().indexOf( "order by", whereIndex );
                    final int indexOfLimit = originalSql.toLowerCase().indexOf( "limit", whereIndex );
                    final int indexOfUnion = originalSql.toLowerCase().indexOf( "union", whereIndex );

                    // 存在Group By语句
                    if ( indexOfGroupBy > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfGroupBy ) ) )
                    {
                       minConditionIndex = indexOfGroupBy;
                    }

                    // 存在Order By语句
                    if ( indexOfOrderBy > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfOrderBy ) ) )
                    {
                       minConditionIndex = indexOfOrderBy;
                    }

                    // 存在Limit语句
                    if ( indexOfLimit > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfLimit ) ) )
                    {
                       minConditionIndex = indexOfLimit;
                    }

                    // 存在Union语句
                    if ( indexOfUnion > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfUnion ) ) )
                    {
                       minConditionIndex = indexOfUnion;
                    }

                    if ( minConditionIndex > 0 )
                    {
                       originalSql = originalSql.substring( 0, minConditionIndex - 1 ) + concatString + originalSql.substring( minConditionIndex );
                    }
                    else
                    {
                       originalSql = originalSql + concatString;
                    }
                 }
              }
           }
        }

        // 回写SQL
        metaStatementHandler.setValue( "delegate.boundSql.sql", originalSql );

        return invocation.proceed();*/
      //第一个拦截不需要分离代理对象链  
      StatementHandler statementHandler = ( StatementHandler ) invocation.getTarget();
      MetaObject metaStatementHandler = MetaObject.forObject( statementHandler );

      // Get OriginalSql
      String originalSql = ( String ) metaStatementHandler.getValue( "delegate.boundSql.sql" );
      if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.contains( "PRIVATE_CODE" ) )
      {
         originalSql = originalSql.replace( "PRIVATE_CODE", KANConstants.PRIVATE_CODE );
         // 回写SQL
         metaStatementHandler.setValue( "delegate.boundSql.sql", originalSql );
      }

      return invocation.proceed();

   }

   @Override
   public Object plugin( Object target )
   {
      return Plugin.wrap( target, this );
   }

   @Override
   public void setProperties( Properties properties )
   {
      // No USe
   }

}
