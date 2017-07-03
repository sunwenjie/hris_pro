/*
 * Created on 2005-10-9
 */
package com.kan.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author Kevin Jin
 */
public class GeneratingTextFile
{
   private String filePath;

   private String text;

   private String id;

   public GeneratingTextFile( final String filePath, final String text, final String id )
   {
      this.filePath = filePath;
      this.text = text;
      this.id = id;
   }

   public void generating() throws KANException
   {
      KANUtil.createFolder( filePath );

      if ( filePath != null && text != null && id != null )
      {
         File textFile = new File( this.filePath + "/" + this.id + ".txt" );
         if ( textFile.exists() )
         {
            /** 如果文件已存在，先删除旧文件。（修改时将出现这种可能） */
            textFile.delete();
         }

         try
         {
            FileOutputStream outStream = new FileOutputStream( textFile );
            PrintWriter pw = new PrintWriter( outStream, true );
            pw.print( text );
            pw.close();
            outStream.close();
         }
         catch ( Exception e )
         {
            throw new KANException( e );
         }
      }
   }
}
