/*
 * Created on 2005-7-18
 */
package com.kan.base.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin
 */
public class UploadedFile implements Serializable
{

   private static final long serialVersionUID = 2956367075917350924L;

   private String uploadId;

   private int fileCount;

   private String filePath;

   private List< Object > files = new ArrayList< Object >();

   /**
    * @return Returns the fileCount.
    */
   public int getFileCount()
   {
      return fileCount;
   }

   /**
    * @param fileCount
    *            The fileCount to set.
    */
   public void setFileCount( final int fileCount )
   {
      this.fileCount = fileCount;
   }

   /**
    * @return Returns the filePath.
    */
   public String getFilePath()
   {
      return filePath;
   }

   /**
    * @param filePath
    *            The filePath to set.
    */
   public void setFilePath( final String filePath )
   {
      this.filePath = filePath;
   }

   /**
    * @return Returns the files.
    */
   public List< Object > getFiles()
   {
      return files;
   }

   /**
    * @param files
    *            The files to set.
    */
   public void setFiles( final List< Object > files )
   {
      this.files = files;
   }

   /**
    * @return Returns the uploadId.
    */
   public String getUploadId()
   {
      return uploadId;
   }

   /**
    * @param uploadItemId
    *            The uploadId to set.
    */
   public void setUploadId( final String uploadId )
   {
      this.uploadId = uploadId;
   }
}