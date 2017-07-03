package com.kan.base.domain.system;

import java.util.List;
import java.util.Map;

public interface IModuleDTOIterator
{
   public Map< String, Object > doReturn( List< ModuleDTO > stackModules );
}
