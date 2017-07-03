package com.kan.base.util;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipUtils
{

   /** 
    * ѹ�� 
    *  
    * @param sourceFile 
    *            ѹ����Դ�ļ� ��: c:/upload 
    * @param targetZip 
    *            ���ɵ�Ŀ���ļ� 
    */
   public static void zip( String sourceFile, File targetZip )
   {

      File dir = new File( sourceFile );

      if ( !dir.exists() )
      {
         System.out.println( sourceFile + "������!" );
         return;
      }

      Project prj = new Project();

      Zip zip = new Zip();

      zip.setProject( prj );

      zip.setDestFile( targetZip );//�������ɵ�Ŀ��zip�ļ�File����  

      FileSet fileSet = new FileSet();

      fileSet.setProject( prj );

      fileSet.setDir( new File( sourceFile ) );//���ý�Ҫ����ѹ����Դ�ļ�File����  
      fileSet.setIncludes("**/*.doc");
      //fileSet.setIncludes("**/*.java"); //������Щ�ļ����ļ���,ֻѹ��Ŀ¼�е�����java�ļ�  

      //fileSet.setExcludes("**/*.java"); //�ų���Щ�ļ����ļ���,ѹ�����е��ļ����ų�java�ļ�  

      zip.addFileset( fileSet );

      zip.execute();

   }
}
