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
 * ��Ŀ���ƣ�HRO_V1  
 * �����ƣ�DataAuthorityInterceptor  
 * �������� ����Ȩ��������
 * �����ˣ�Kevin  
 * ����ʱ�䣺2014-3-27  
 *
 */

@Intercepts(value = { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DataAuthorityInterceptor implements Interceptor
{

   @Override
   public Object intercept( Invocation invocation ) throws Throwable
   {

      /*  //��һ�����ز���Ҫ������������  
        StatementHandler statementHandler = ( StatementHandler ) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject( statementHandler );

        // ��ʼ��DefaultParameterHandler
        final DefaultParameterHandler parameterHandler = ( DefaultParameterHandler ) metaStatementHandler.getValue( "delegate.parameterHandler" );

        // Get OriginalSql
        String originalSql = ( String ) metaStatementHandler.getValue( "delegate.boundSql.sql" );

        // ���ڶ��������ݽ��н���
        if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.contains( "PRIVATE_CODE" ) )
        {
           originalSql = originalSql.replace( "PRIVATE_CODE", KANConstants.PRIVATE_CODE );
           // ��дSQL
           metaStatementHandler.setValue( "delegate.boundSql.sql", originalSql );
        }

        if ( parameterHandler == null || parameterHandler.getParameterObject() == null )
        {
           return invocation.proceed();
        }

        // ��ʼ��Concat String
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

           //Employee������Ȩ����ͬʱ����Employee��EmployeeContract
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

              // ����Group By���
              if ( indexOfGroupBy > 0 )
              {
                 originalSql = originalSql.substring( 0, indexOfGroupBy - 1 ) + concatString + originalSql.substring( indexOfGroupBy );
              }
              // ����Order By���
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
        //ͨ������
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

           //��hro_biz_employee_contract�ı���
           String foreignAlias = "hbizemp";

           if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.toLowerCase().contains( "select" ) )
           {
              // ��ʼ��SQL��Where���ֵ�λ��
              final List< Integer > whereIndexs = new ArrayList< Integer >();
              // ��ʼ������λ��
              int lastWhereIndex = 0;

              // ���������һ��WhereΪֹ
              while ( originalSql.toLowerCase().indexOf( "where", lastWhereIndex ) > 0 )
              {
                 lastWhereIndex = originalSql.toLowerCase().indexOf( "where", lastWhereIndex );
                 whereIndexs.add( lastWhereIndex );
                 lastWhereIndex = lastWhereIndex + "where".length();
              }

              //�Ѿ�Join Owner���
              if ( originalSql.toLowerCase().contains( " join hro_biz_employee_contract " ) )
              {
                 if ( TableAliasConstant.foreignAlias.get( className ) != null )
                 {
                    foreignAlias = TableAliasConstant.foreignAlias.get( className );
                 }
                 else
                 {
                    throw new KANException( "ϵͳ����" );
                 }
              }
              else
              {
                 final String leftJoinEmployeeContract = " left join hro_biz_employee_contract " + foreignAlias + " on " + TableAliasConstant.mainAlias.get( className )
                       + ".contractId = " + foreignAlias + ".contractId ";

                 //��Left Join����Where ǰ��
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

              // ��Where��������������
              if ( whereIndexs != null && whereIndexs.size() > 0 )
              {
                 for ( Integer whereIndex : whereIndexs )
                 {
                    // ������������Сλ��
                    int minConditionIndex = 0;
                    final int indexOfGroupBy = originalSql.toLowerCase().indexOf( "group by", whereIndex );
                    final int indexOfOrderBy = originalSql.toLowerCase().indexOf( "order by", whereIndex );
                    final int indexOfLimit = originalSql.toLowerCase().indexOf( "limit", whereIndex );
                    final int indexOfUnion = originalSql.toLowerCase().indexOf( "union", whereIndex );

                    // ����Group By���
                    if ( indexOfGroupBy > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfGroupBy ) ) )
                    {
                       minConditionIndex = indexOfGroupBy;
                    }

                    // ����Order By���
                    if ( indexOfOrderBy > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfOrderBy ) ) )
                    {
                       minConditionIndex = indexOfOrderBy;
                    }

                    // ����Limit���
                    if ( indexOfLimit > 0 && ( minConditionIndex == 0 || ( minConditionIndex > 0 && minConditionIndex > indexOfLimit ) ) )
                    {
                       minConditionIndex = indexOfLimit;
                    }

                    // ����Union���
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

        // ��дSQL
        metaStatementHandler.setValue( "delegate.boundSql.sql", originalSql );

        return invocation.proceed();*/
      //��һ�����ز���Ҫ������������  
      StatementHandler statementHandler = ( StatementHandler ) invocation.getTarget();
      MetaObject metaStatementHandler = MetaObject.forObject( statementHandler );

      // Get OriginalSql
      String originalSql = ( String ) metaStatementHandler.getValue( "delegate.boundSql.sql" );
      if ( KANUtil.filterEmpty( originalSql ) != null && originalSql.contains( "PRIVATE_CODE" ) )
      {
         originalSql = originalSql.replace( "PRIVATE_CODE", KANConstants.PRIVATE_CODE );
         // ��дSQL
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
