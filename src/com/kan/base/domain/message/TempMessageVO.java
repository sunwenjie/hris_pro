package com.kan.base.domain.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;
import com.kan.hro.domain.biz.attendance.OTHeaderVO;

public class TempMessageVO
{
   public static List< TempMessageVO > tempMessageVOs = new ArrayList< TempMessageVO >();
   private LeaveHeaderVO leaveHeaderVO;

   private OTHeaderVO otHeaderVO;
   // 1 Çë¼Ù 2¼Ó°à
   private String identity;

   private boolean readed;

   private String id;

   private Date createDate;

   private String accountId;

   public TempMessageVO( LeaveHeaderVO leaveHeaderVO, OTHeaderVO otHeaderVO, String identity, String accountId )
   {
      super();
      this.leaveHeaderVO = leaveHeaderVO;
      this.otHeaderVO = otHeaderVO;
      this.identity = identity;
      this.readed = false;
      this.accountId = accountId;
      this.id = UUID.randomUUID().toString();
      this.createDate = new Date();
   }

   public LeaveHeaderVO getLeaveHeaderVO()
   {
      return leaveHeaderVO;
   }

   public void setLeaveHeaderVO( LeaveHeaderVO leaveHeaderVO )
   {
      this.leaveHeaderVO = leaveHeaderVO;
   }

   public OTHeaderVO getOtHeaderVO()
   {
      return otHeaderVO;
   }

   public void setOtHeaderVO( OTHeaderVO otHeaderVO )
   {
      this.otHeaderVO = otHeaderVO;
   }

   public String getIdentity()
   {
      return identity;
   }

   public void setIdentity( String identity )
   {
      this.identity = identity;
   }

   public boolean isReaded()
   {
      return readed;
   }

   public void setReaded( boolean readed )
   {
      this.readed = readed;
   }

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getDecodeItemId()
   {
      String result = "";
      if ( "2".equals( identity ) )
      {
         result = "¼Ó°à";
      }
      else
      {
         ItemVO itemVO;
         try
         {
            itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( leaveHeaderVO.getItemId() );
            result = itemVO != null ? itemVO.getNameZH() : "Çë¼Ù";
         }
         catch ( KANException e )
         {

         }
      }
      return result;
   }

   public String getDecodeCreateDate()
   {
      SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
      return sdf.format( this.createDate );
   }

   public void setCreateDate( Date createDate )
   {
      this.createDate = createDate;
   }

   public static List< TempMessageVO > getTempMessageVOs()
   {
      return tempMessageVOs;
   }

   public static void setTempMessageVOs( List< TempMessageVO > tempMessageVOs )
   {
      TempMessageVO.tempMessageVOs = tempMessageVOs;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public static TempMessageVO getTempMessageVOById( String id )
   {
      for ( TempMessageVO messageVO : TempMessageVO.tempMessageVOs )
      {
         if ( id.equalsIgnoreCase( messageVO.getId() ) )
         {
            return messageVO;
         }
      }
      return null;
   }

}
