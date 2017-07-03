/*
 * Created on 2012-05-07
 */
package com.kan.base.domain.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jixiang Hu
 */

/**  
*   
* 项目名称：HRO_V1  
* 类名称：MessageMailVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2014-2-20 下午03:50:50  
* 修改人：Jixiang  
* 修改时间：2014-2-20 下午03:50:50  
* 修改备注：  
* @version   
*   
*/
public class MessageMailVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1552070218064078462L;

   /**
    * For DB
    */
   // 主键
   private String mailId;

   private String regularId;

   // 邮件主题 (NOT NULL)
   private String title;

   // 邮件内容
   private String content;

   // 邮件内容类型（Text或HTML）
   private String contentType;

   private String templateId;

   // 收件人，同一邮件发送给1位接收者
   private String reception;

   // 需要显示的别名
   private String showName;

   // 发送人
   private String sentAs;

   // SMTP服务器
   private String smtpHost;

   // SMTP端口
   private String smtpPort;

   // SMTP用户名
   private String username;

   // SMTP密码（需加密保存）
   private String password;

   // 邮件发送是否需要验证
   private String smtpAuthType;

   // 最后尝试发送时间
   private Date lastSendingTime;

   // 已尝试发送次数
   private int sendingTimes;

   // 最终发送时间
   private Date sentTime;

   // 发送类型  1：立即发送 2：定时发送  3：循环发送
   private String sendType;

   // 待发送时间
   private String toSendTime;

   // 状态 1:待发送，2:发送中，3:已发送，4:暂停
   //private String status;//继承了BaseVO的status

   /**
    * For Application
    */
   // 创建时间早于
   private String beforeCreateDate;

   // 创建时间晚于
   private String afterCreateDate;

   private List< MappingVO > contentTypes = new ArrayList< MappingVO >();

   private List< MappingVO > smtpAuthTypes = new ArrayList< MappingVO >();

   private List< MappingVO > templateIds = new ArrayList< MappingVO >();

   private List< MappingVO > sendTypes = new ArrayList< MappingVO >();

   // 定时时间
   private String startDateTime;

   // 失效时间
   private String endDateTime;

   // 重复模式（按日，按周，按月）
   private String repeatType;

   // 间隔
   private String period;

   // 附加间隔时间
   private String additionalPeriod;

   // 星期间隔
   private String weekPeriod;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.contentTypes = KANUtil.getMappings( request.getLocale(), "messageMail.contentType" );
      this.smtpAuthTypes = KANUtil.getMappings( request.getLocale(), "messageMail.smtpAuthType" );
      try
      {
         this.templateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "1" );
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

      setStatuses( KANUtil.getMappings( request.getLocale(), "messageMail.status" ) );

      this.sendTypes = KANUtil.getMappings( request.getLocale(), "messageMail.sendType" );
   }

   @Override
   public void update( final Object object )
   {
      final MessageMailVO messageMailVO = ( MessageMailVO ) object;
      this.title = messageMailVO.getTitle();
      this.templateId = messageMailVO.getTemplateId();
      this.content = messageMailVO.getContent();
      this.contentType = messageMailVO.getContentType();
      this.showName = messageMailVO.getShowName();
      this.sendType = messageMailVO.getSendType();
      super.setCreateDate( messageMailVO.getCreateDate() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.title = "";
      this.templateId = "0";
      this.content = "";
      this.contentType = "0";
      this.reception = "";
      this.showName = "";
      this.sentAs = "";
      this.smtpHost = "";
      this.smtpPort = "";
      this.username = "";
      this.password = "";
      this.smtpAuthType = "0";
      this.afterCreateDate = "";
      this.beforeCreateDate = "";
      this.sendType = "0";
      this.toSendTime = null;
      super.setStatus( "0" );
   }

   public List< MappingVO > getSmtpAuthTypes()
   {
      return smtpAuthTypes;
   }

   public void setSmtpAuthTypes( List< MappingVO > smtpAuthTypes )
   {
      this.smtpAuthTypes = smtpAuthTypes;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
   }

   public String getSmtpHost()
   {
      return smtpHost;
   }

   public void setSmtpHost( String smtpHost )
   {
      this.smtpHost = smtpHost;
   }

   public String getSmtpPort()
   {
      return smtpPort;
   }

   public void setSmtpPort( String smtpPort )
   {
      this.smtpPort = smtpPort;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword( String password )
   {
      this.password = password;
   }

   public String getSmtpAuthType()
   {
      return smtpAuthType;
   }

   public String getDecodeSmtpAuthType()
   {
      return decodeField( this.smtpAuthType, this.smtpAuthTypes );
   }

   public void setSmtpAuthType( String smtpAuthType )
   {
      this.smtpAuthType = smtpAuthType;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public List< MappingVO > getContentTypes()
   {
      return contentTypes;
   }

   public void setContentTypes( List< MappingVO > contentTypes )
   {
      this.contentTypes = contentTypes;
   }

   public String getMailId()
   {
      return mailId;
   }

   public void setMailId( String mailId )
   {
      this.mailId = mailId;
   }

   public String getContentType()
   {
      return contentType;
   }

   public String getDecodeContentType()
   {
      return decodeField( this.content, this.contentTypes );
   }

   public void setContentType( String contentType )
   {
      this.contentType = contentType;
   }

   public String getReception()
   {
      return reception;
   }

   public void setReception( String reception )
   {
      this.reception = reception;
   }

   public String getShowName()
   {
      return showName;
   }

   public void setShowName( String showName )
   {
      this.showName = showName;
   }

   public String getSentAs()
   {
      return sentAs;
   }

   public void setSentAs( String sentAs )
   {
      this.sentAs = sentAs;
   }

   public Date getLastSendingTime()
   {
      return lastSendingTime;
   }

   public void setLastSendingTime( Date lastSendingTime )
   {
      this.lastSendingTime = lastSendingTime;
   }

   public String getDecodeLastSendingTime()
   {
      return decodeDate( this.lastSendingTime );
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
      return encodedField( this.mailId );
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

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public List< MappingVO > getTemplateIds()
   {
      return templateIds;
   }

   public void setTemplateIds( List< MappingVO > templateIds )
   {
      this.templateIds = templateIds;
   }

   public List< MappingVO > getSendTypes()
   {
      return sendTypes;
   }

   public void setSendTypes( List< MappingVO > sendTypes )
   {
      this.sendTypes = sendTypes;
   }

   public String getSendType()
   {
      return sendType;
   }

   public void setSendType( String sendType )
   {
      this.sendType = sendType;
   }

   public String getRepeatType()
   {
      return repeatType;
   }

   public void setRepeatType( String repeatType )
   {
      this.repeatType = repeatType;
   }

   public String getPeriod()
   {
      return period;
   }

   public void setPeriod( String period )
   {
      this.period = period;
   }

   public String getAdditionalPeriod()
   {
      return additionalPeriod;
   }

   public void setAdditionalPeriod( String additionalPeriod )
   {
      this.additionalPeriod = additionalPeriod;
   }

   public String getWeekPeriod()
   {
      return weekPeriod;
   }

   public void setWeekPeriod( String weekPeriod )
   {
      this.weekPeriod = weekPeriod;
   }

   public String getRegularId()
   {
      return regularId;
   }

   public void setRegularId( String regularId )
   {
      this.regularId = regularId;
   }

   public String getToSendTime()
   {
      return KANUtil.filterEmpty( decodeDatetime( toSendTime ) );
   }

   public void setToSendTime( String toSendTime )
   {
      this.toSendTime = toSendTime;
   }

   public String getStartDateTime()
   {
      return KANUtil.filterEmpty( decodeDatetime( startDateTime ) );
   }

   public void setStartDateTime( String startDateTime )
   {
      this.startDateTime = startDateTime;
   }

   public String getEndDateTime()
   {
      return KANUtil.filterEmpty( decodeDatetime( endDateTime ) );
   }

   public void setEndDateTime( String endDateTime )
   {
      this.endDateTime = endDateTime;
   }

}