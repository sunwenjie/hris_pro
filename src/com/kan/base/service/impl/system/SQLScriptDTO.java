package com.kan.base.service.impl.system;

import java.util.ArrayList;
import java.util.List;

public class SQLScriptDTO
{
   // ��ʼ��SQL Script
   private String sqlScript;

   // ��ʼ��Sub SQL��
   private List< SQLScriptDTO > subSQLScriptDTOs = new ArrayList< SQLScriptDTO >();

   public String getSqlScript()
   {
      return sqlScript;
   }

   public void setSqlScript( final String sqlScript )
   {
      this.sqlScript = sqlScript;
   }

   public List< SQLScriptDTO > getSubSQLScriptDTOs()
   {
      return subSQLScriptDTOs;
   }

   public void setSubSQLScriptDTOs( final List< SQLScriptDTO > subSQLScriptDTOs )
   {
      this.subSQLScriptDTOs = subSQLScriptDTOs;
   }

   public void addSubSQLScript( final String subSQLScript )
   {
      final SQLScriptDTO sqlScriptDTO = new SQLScriptDTO();
      sqlScriptDTO.setSqlScript( subSQLScript );
      
      this.getSubSQLScriptDTOs().add( sqlScriptDTO );
   }
   
}
