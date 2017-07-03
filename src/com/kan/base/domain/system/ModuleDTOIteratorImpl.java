package com.kan.base.domain.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class ModuleDTOIteratorImpl implements IModuleDTOIterator
{
   public Map< String, Object > doReturn( List< ModuleDTO > stackModules )
   {
      Map< String, Object > map = new HashMap< String, Object >();
      StringBuilder sb = new StringBuilder();

      for ( ModuleDTO moduleDTO : stackModules )
      {
         sb.append( moduleDTO.getModuleVO().getModuleName() ).append( " -> " );
      }
      sb.substring( 0, sb.lastIndexOf( "->" ) );
      map.put( "nameList", sb.toString() );
      ModuleDTO t = stackModules.get( stackModules.size() - 1 );
      map.put( "name", t.getModuleVO().getModuleName() );
      map.put( "moduleId", t.getModuleVO().getModuleId() );
      map.put( "staffId", t.getModuleVO().getStaffId() );

      JSONObject jsonObj = new JSONObject();
      jsonObj.accumulateAll( map );

      System.out.println( jsonObj.toString() );

      return map;
   }
}
