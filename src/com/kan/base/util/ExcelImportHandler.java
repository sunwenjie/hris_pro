package com.kan.base.util;

import javax.servlet.http.HttpServletRequest;

public interface ExcelImportHandler< T >
{
   public void init( T importData );

   public boolean excuteBeforInsert( T importData, HttpServletRequest request ) throws KANException;

   public boolean excueEndInsert( T importData, String batchId ) throws KANException;

   public boolean excuteRegroupmentBeforInsert( T importData, HttpServletRequest request ) throws KANException;
}
