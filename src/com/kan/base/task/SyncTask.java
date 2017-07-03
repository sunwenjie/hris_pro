package com.kan.base.task;

import java.util.ArrayList;
import java.util.List;

import com.kan.base.web.action.BaseAction;

public class SyncTask extends Thread
{

   private List< MethodSyncVO > tasks = new ArrayList< MethodSyncVO >();

   @Override
   public void run()
   {
      super.run();
      for ( MethodSyncVO task : tasks )
      {
         try
         {
            BaseAction.constantsInit( task.getMethodName(), task.getParams() );
         }
         catch ( Exception e )
         {
            System.err.println( "SyncTask error:" + task.getMethodName() + "(" + task.getParams().toString() + ")" );
            e.printStackTrace();
         }
      }
   }

   public void addTask( String methodName, String param )
   {
      addTask( methodName, new String[] { param } );
   }

   public void addTask( String methodName, String[] params )
   {
      try
      {
         tasks.add( new MethodSyncVO( methodName, params ) );
      }
      catch ( Exception e )
      {
         System.err.println( "SyncTask error:" + methodName + "(" + params.toString() + ")" );
         e.printStackTrace();
      }

   }
}

class MethodSyncVO
{
   private String methodName;

   private String[] params;

   public MethodSyncVO( String methodName, String[] params )
   {
      this.methodName = methodName;
      this.params = params;
   }

   public String getMethodName()
   {
      return methodName;
   }

   public void setMethodName( String methodName )
   {
      this.methodName = methodName;
   }

   public String[] getParams()
   {
      return params == null ? new String[] {} : params;
   }

   public void setParams( String[] params )
   {
      this.params = params;
   }

}
