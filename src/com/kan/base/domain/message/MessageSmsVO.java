/*
 * Created on 2012-05-07
 */
package com.kan.base.domain.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jixiang Hu
 */
public class MessageSmsVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1552070218064078462L;

   /**
    * For DB
    */
   //AUTO_INCREMENT 主键
   private String smsId;

   // 模板ID
   private String templateId;

   //短信内容
   private String content;

   //短信收件人，同一短信发送给1位接收者
   private String reception;

   //最后尝试发送时间
   private Date lastSendingTime;

   //已尝试发送次数
   private int sendingTimes;

   //最终发送时间
   private Date sentTime;

   //状态 1:待发送，2:发送中，3:已发送，4:暂停
   //private String  status;//继承BaseVO的status
   /**
    * For Application
    */

   //创建时间早于
   private String beforeCreateDate;

   // 创建时间晚于
   private String afterCreateDate;

   // for app
   @JsonIgnore
   private List< MappingVO > templateIds;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      try
      {
         this.templateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "2" );
      }
      catch ( KANException e )
      {
         e.printStackTrace();
      }
      if ( templateIds == null )
      {
         templateIds = new ArrayList< MappingVO >();
         templateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      else
      {
         templateIds.add( 0, KANUtil.getEmptyMappingVO( getLocale() ) );
      }
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "messageSms.status" ) );
   }

   @Override
   public void update( final Object object )
   {
      final MessageSmsVO messageMailVO = ( MessageSmsVO ) object;
      this.templateId = messageMailVO.getTemplateId();
      this.content = messageMailVO.getContent();
      this.reception = messageMailVO.getReception();
      super.setModifyBy( messageMailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.content = "";
      this.templateId = "0";
      this.reception = "";
      this.beforeCreateDate = "";
      this.afterCreateDate = "";
      super.setStatus( "0" );
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getSmsId()
   {
      return smsId;
   }

   public void setSmsId( String smsId )
   {
      this.smsId = smsId;
   }

   public String getReception()
   {
      return reception;
   }

   public void setReception( String reception )
   {
      this.reception = reception;
   }

   public Date getLastSendingTime()
   {
      return lastSendingTime;
   }

   public String getDecodeLastSendingTime()
   {
      return decodeDate( lastSendingTime );
   }

   public void setLastSendingTime( Date lastSendingTime )
   {
      this.lastSendingTime = lastSendingTime;
   }

   public int getSendingTimes()
   {
      return sendingTimes;
   }

   public void setSendingTimes( int sendingTimes )
   {
      this.sendingTimes = sendingTimes;
   }

   public Date getSentTime()
   {
      return sentTime;
   }

   public String getDecodeSentTime()
   {
      return decodeDate( this.sentTime );
   }

   public void setSentTime( Date sentTime )
   {
      this.sentTime = sentTime;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.smsId );
   }

   public String getBeforeCreateDate()
   {
      return beforeCreateDate;
   }

   public void setBeforeCreateDate( String beforeCreateDate )
   {
      this.beforeCreateDate = beforeCreateDate;
   }

   public String getAfterCreateDate()
   {
      return afterCreateDate;
   }

   public void setAfterCreateDate( String afterCreateDate )
   {
      this.afterCreateDate = afterCreateDate;
   }

   public List< MappingVO > getTemplateIds()
   {
      return templateIds;
   }

   public void setTemplateIds( List< MappingVO > templateIds )
   {
      this.templateIds = templateIds;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }
}