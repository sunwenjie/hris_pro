package com.kan.base.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class ContextService
{
   // Ìí¼ÓLogger¹¦ÄÜ
   protected Log logger = LogFactory.getLog( getClass() );

   // DAO
   private Object dao;

   // Instance DataSource transaction manager
   private DataSourceTransactionManager transactionManager;

   // Default transaction definition
   private final DefaultTransactionDefinition definition = new DefaultTransactionDefinition( DefaultTransactionDefinition.PROPAGATION_NESTED );

   // Instance transaction status
   private TransactionStatus status = null;

   // Get DAO
   public Object getDao()
   {
      return this.dao;
   }

   // Set DAO
   public void setDao( final Object dao )
   {
      this.dao = dao;
   }

   // Set DataSource transaction manager
   public void setTransactionManager( final DataSourceTransactionManager transactionManager )
   {
      this.transactionManager = transactionManager;
   }

   // Start transaction
   public void startTransaction()
   {
      System.err.println( "start transaction" );
      status = transactionManager.getTransaction( definition );
   }

   // Commit transaction
   public void commitTransaction()
   {
      System.err.println( "commit transaction" );
      transactionManager.commit( status );
   }

   // Rollback transaction
   public void rollbackTransaction()
   {
      System.err.println( "rollback transaction" );
      transactionManager.rollback( status );
   }

}
