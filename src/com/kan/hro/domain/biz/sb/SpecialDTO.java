package com.kan.hro.domain.biz.sb;

import java.util.List;
import java.util.Map;

public interface SpecialDTO< H, D >
{
   public abstract Object getHeaderVO();

   public abstract List< ? > getDetailVOs();

   public abstract Map< ?, ? > getFlags();
}
