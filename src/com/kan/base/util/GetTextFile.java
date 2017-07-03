/*
 * Created on 2005-10-9
 */
package com.kan.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author Kevin Jin
 */
public class GetTextFile
{
   private String filePath;

   private String id;

   public GetTextFile( final String filePath, final String id )
   {
      this.filePath = filePath;
      this.id = id;
   }

   public String get() throws KANException
   {
      KANUtil.createFolder( filePath );
      String text = "";

      if ( filePath != null && id != null )
      {
         File textFile = new File( this.filePath + "/" + this.id + ".txt" );
         if ( !textFile.exists() )
         {
            return text;
         }

         try
         {
            FileInputStream inStream = new FileInputStream( textFile );
            BufferedReader br = new BufferedReader( new InputStreamReader( inStream ) );
            String line;
            while ( ( line = br.readLine() ) != null )
            {
               text = text + line;
            }
            br.close();
            inStream.close();
         }
         catch ( Exception e )
         {
            throw new KANException( e );
         }
      }

      return text;
   }
}