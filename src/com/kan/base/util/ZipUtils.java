package com.kan.base.util;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipUtils
{

   /** 
    * 压缩 
    *  
    * @param sourceFile 
    *            压缩的源文件 如: c:/upload 
    * @param targetZip 
    *            生成的目标文件 
    */
   public static void zip( String sourceFile, File targetZip )
   {

      File dir = new File( sourceFile );

      if ( !dir.exists() )
      {
         System.out.println( sourceFile + "不存在!" );
         return;
      }

      Project prj = new Project();

      Zip zip = new Zip();

      zip.setProject( prj );

      zip.setDestFile( targetZip );//设置生成的目标zip文件File对象  

      FileSet fileSet = new FileSet();

      fileSet.setProject( prj );

      fileSet.setDir( new File( sourceFile ) );//设置将要进行压缩的源文件File对象  
      fileSet.setIncludes("**/*.doc");
      //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件  

      //fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件  

      zip.addFileset( fileSet );

      zip.execute();

   }
}
