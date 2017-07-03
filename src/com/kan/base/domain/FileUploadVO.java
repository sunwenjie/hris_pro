package com.kan.base.domain;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 上传状态类
 */
public class FileUploadVO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7963712895070084683L;

   private long readedBytes = 0L;

   private long totalBytes = 0L;

   private int currentItem = 0;

   private String statusMsg = "";

   private String errorMsg = "";

   private String warningMsg = "";

   private String extraMsg = "";

   // 1：错误 ，0：上传中，2：完成上传，3：导入中，4：导入成功
   private String info = "0";

   public long getReadedBytes()
   {
      return readedBytes;
   }

   public void setReadedBytes( long readedBytes )
   {
      this.readedBytes = readedBytes;
   }

   public long getTotalBytes()
   {
      return totalBytes;
   }

   public void setTotalBytes( long totalBytes )
   {
      this.totalBytes = totalBytes;
   }

   public int getCurrentItem()
   {
      return currentItem;
   }

   public void setCurrentItem( int currentItem )
   {
      this.currentItem = currentItem;
   }

   public String getInfo()
   {
      return info;
   }

   public void setInfo( String info )
   {
      this.info = info;
   }

   public final String getStatusMsg()
   {
      return statusMsg;
   }

   public final void setStatusMsg( String statusMsg )
   {
      this.statusMsg = statusMsg;
   }

   public final String getErrorMsg()
   {
      return errorMsg;
   }

   public final void setErrorMsg( String errorMsg )
   {
      this.errorMsg = errorMsg;
   }

   public String getWarningMsg()
   {
      return warningMsg;
   }

   public void setWarningMsg( String warningMsg )
   {
      this.warningMsg = warningMsg;
   }

   public String toJSon()
   {
      return JSONObject.fromObject( this ).toString();
   }

   public String getExtraMsg()
   {
      return extraMsg;
   }

   public void setExtraMsg( String extraMsg )
   {
      this.extraMsg = extraMsg;
   }

}
