package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * ��װImport���� - ����Header��Detail
 * 
 * @author Hu JiXiang
 */
public class ImportDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -732022950611228412L;

   // ��װ����ImportHeaderVO����
   private ImportHeaderVO importHeaderVO = new ImportHeaderVO();

   // ��װ����ImportDetailVO���� - ������ǰImportHeaderVO����
   private List< ImportDetailVO > importDetailVOs = new ArrayList< ImportDetailVO >();

   // ��װ�ӱ�
   private List< ImportDTO > subImportDTOs = new ArrayList< ImportDTO >();

   public ImportHeaderVO getImportHeaderVO()
   {
      return importHeaderVO;
   }

   public void setImportHeaderVO( ImportHeaderVO importHeaderVO )
   {
      this.importHeaderVO = importHeaderVO;
   }

   public List< ImportDetailVO > getImportDetailVOs()
   {
      return importDetailVOs;
   }

   public void setImportDetailVOs( List< ImportDetailVO > importDetailVOs )
   {
      this.importDetailVOs = importDetailVOs;
   }

   public List< ImportDTO > getSubImportDTOs()
   {
      return subImportDTOs;
   }

   public void setSubImportDTOs( List< ImportDTO > clildrenImportDTOs )
   {
      this.subImportDTOs = clildrenImportDTOs;
   }

   public ImportDetailVO getPrimaryKeyImportDetailVO()
   {
      if ( importDetailVOs != null )
      {
         for ( ImportDetailVO importDetailVO : importDetailVOs )
         {
            if ( "1".equals( importDetailVO.getIsPrimaryKey() ) )
            {
               return importDetailVO;
            }
         }
      }
      return null;
   }

   public String getImportFileName( HttpServletRequest request )
   {
      if ( this == null )
         return request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "����ģ��" : "Import Template";

      if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
      {
         return getImportHeaderVO().getNameZH() + "ģ��";
      }
      else
      {
         return getImportHeaderVO().getNameEN() + " Template";
      }
   }
}
