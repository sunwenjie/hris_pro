/*
 * Created on 2013-5-29
 */
package com.kan.base.domain.message;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Jixiang Hu
 */
public class MessageTemplateVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3328633436677177875L;

   /**
    * For DB
    */
   private String templateId;

   private String accountId;

   //消息模板中文名
   private String nameZH;

   //消息模板英文名
   private String nameEN;

   //消息模板类型，1:邮件，2:短信，3:站内信息
   private String templateType;

   //消息模板内容，传入参数类似${value}
   private String content;

   //消息模板内容类型，1:Text，2:HTML
   private String contentType;

   //消息模板描述
   private String description;
   @JsonIgnore
   private List< MappingVO > templateTypes;
   @JsonIgnore
   private List< MappingVO > contentTypes;

   public List< MappingVO > getTemplateTypes()
   {
      return templateTypes;
   }

   public String getDecodeTemplateType()
   {
      return decodeField( templateType, templateTypes );
   }

   public void setTemplateTypes( List< MappingVO > templateTypes )
   {
      this.templateTypes = templateTypes;
   }

   public List< MappingVO > getContentTypes()
   {
      return contentTypes;
   }

   public void setContentTypes( List< MappingVO > contentTypes )
   {
      this.contentTypes = contentTypes;
   }

   public String getDecodeContentType()
   {
      return decodeField( contentType, contentTypes );
   }

   @Override
   public void update( final Object object )
   {
      final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) object;
      this.nameZH = messageTemplateVO.getNameZH();
      this.nameEN = messageTemplateVO.getNameEN();
      this.templateType = messageTemplateVO.getTemplateType();
      this.description = messageTemplateVO.getDescription();
      this.content = messageTemplateVO.getContent();
      this.contentType = messageTemplateVO.getContentType();
      super.setStatus( messageTemplateVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.templateTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.templateType" );
      this.contentTypes = KANUtil.getMappings( this.getLocale(), "messageTemplate.contentType" );
   }

   @Override
   public void reset()
   {
      this.nameZH = "";
      this.nameEN = "";
      this.templateType = "";
      this.content = "";
      this.contentType = "";
      this.description = "";
      super.setStatus( "0" );
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getTemplateType()
   {
      return templateType;
   }

   public void setTemplateType( String templateType )
   {
      this.templateType = templateType;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getContentType()
   {
      return contentType;
   }

   public void setContentType( String contentType )
   {
      this.contentType = contentType;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( templateId );
   }

   @Override
   public String toString()
   {
      return "MessageTemplateVO [templateId=" + templateId + ", accountId=" + accountId + ", nameZH=" + nameZH + ", nameEN=" + nameEN + ", templateType=" + templateType
            + ", content=" + content + ", contentType=" + contentType + ", description=" + description + ", templateTypes=" + templateTypes + ", contentTypes=" + contentTypes
            + "]";
   }

}