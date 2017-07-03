package com.kan.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocUtils
{
   public final Log logger = LogFactory.getLog( getClass() );

   /** 
    * freemark模板配置 
    */
   private Configuration configuration = null;

   // 模板文件路径
   private static String templatePath = "/ftl/";

   public DocUtils()
   {
      configuration = new Configuration();
      configuration.setClassForTemplateLoading( getClass(), templatePath );
      configuration.setDefaultEncoding( "utf-8" );
   }

   public File createWord( Map< String, Object > dataMap, String defFileName, boolean lang_zh )
   {
      long start = System.currentTimeMillis();
      Template t = null;
      File outFile = new File( defFileName );
      try
      {
         //获取模板信息  
         t = configuration.getTemplate( lang_zh ? "adjSalaryTempalte_zh.ftl" : "adjSalaryTempalte_en.ftl" );
         Writer w = new OutputStreamWriter( new FileOutputStream( outFile ), "utf-8" );
         t.process( dataMap, w );
         w.close();
      }
      catch ( IOException | TemplateException e )
      {
         logger.error( "*** Create word document error: " + e );
         e.printStackTrace();
      }
      long end = System.currentTimeMillis();

      logger.info( "Successfully create word document, times: " + ( end - start ) + "ms, temp path: " + defFileName );
      return outFile;
   }

}
