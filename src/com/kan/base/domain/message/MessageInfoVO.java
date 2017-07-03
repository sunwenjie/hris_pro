/*
 * Created on 2012-05-07
 */
package com.kan.base.domain.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
public class MessageInfoVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1552070218064078462L;

   /**
    * For DB
    */
   //AUTO_INCREMENT ����
   private String infoId;

   //վ����Ϣ����
   private String title;

   // ģ��ID
   private String templateId;

   //վ��������
   private String content;

   //վ�����ռ��ˣ�ͬһվ���ŷ��͸�1λ������
   private String reception;

   //��Ϣ����ʱ�䣨ͨ��90�죩
   private Date expiredTime;

   //�Ķ�ʱ��
   private Date readTime;

   //����״̬ # 2:δ����3:�Ѷ� [��Խ�����]
   private String receptionStatus;

   /**
    * For Application
    */
   @JsonIgnore
   private List< MappingVO > receptionStatuses;
   @JsonIgnore
   //����ʱ������
   private String beforeCreateDate;
   @JsonIgnore
   // ����ʱ������
   private String afterCreateDate;
   @JsonIgnore
   private List< MappingVO > templateIds;
   @JsonIgnore
   private String staffName;
   @JsonIgnore
   private String employeeName;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.receptionStatuses = KANUtil.getMappings( request.getLocale(), "messageInfo.receptionStatus" );
      try
      {
         this.templateIds = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getMessageTemplateByType( request.getLocale().getLanguage(), "3" );
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
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "messageInfo.status" ) );
   }

   @Override
   public void update( final Object object )
   {
      final MessageInfoVO messageMailVO = ( MessageInfoVO ) object;
      this.templateId = messageMailVO.getTemplateId();
      this.content = messageMailVO.getContent();
      this.reception = messageMailVO.getReception();
      this.title = messageMailVO.getTitle();
      super.setModifyBy( messageMailVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      super.setStatus( "" );
      this.templateId = "0";
      this.receptionStatus = "0";
      this.title = "";
      this.content = "";
      this.reception = "";
      this.afterCreateDate = "";
      this.beforeCreateDate = "";
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getInfoId()
   {
      return infoId;
   }

   public void setInfoId( String infoId )
   {
      this.infoId = infoId;
   }

   public String getReception()
   {
      return reception;
   }

   public void setReception( String reception )
   {
      this.reception = reception;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle( String title )
   {
      this.title = title;
   }

   public Date getExpiredTime()
   {
      return expiredTime;
   }

   //   public void setExpiredTime( Date expiredTime )
   //   {
   //      this.expiredTime = expiredTime;
   //   }

   public void setExpiredTime( String expiredTime )
   {
      try
      {
         this.expiredTime = new SimpleDateFormat( "yyyy-MM-dd" ).parse( expiredTime );
      }
      catch ( ParseException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   public Date getReadTime()
   {
      return readTime;
   }

   public String getDecodeReadTime()
   {
      return decodeDate( this.readTime );
   }

   public void setReadTime( Date readTime )
   {
      this.readTime = readTime;
   }

   public String getReceptionStatus()
   {
      return receptionStatus;
   }

   public String getDecodeReceptionStatus()
   {
      return decodeField( this.receptionStatus, this.receptionStatuses );
   }

   public void setReceptionStatus( String receptionStatus )
   {
      this.receptionStatus = receptionStatus;
   }

   public List< MappingVO > getReceptionStatuses()
   {
      return receptionStatuses;
   }

   public void setReceptionStatuses( List< MappingVO > receptionStatuses )
   {
      this.receptionStatuses = receptionStatuses;
   }

   public String getDecodeExpiredTime()
   {
      return decodeDate( this.expiredTime );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.infoId );
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

   //���staffΪ��ȥemployeeName ˵����ep ʹ��
   public String getStaffName()
   {
      if ( StringUtils.isNotBlank( staffName ) )
      {
         return staffName;
      }
      else
      {
         return this.employeeName;
      }
   }

   public void setStaffName( String staffName )
   {
      this.staffName = staffName;
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

   public String getEmployeeName()
   {
      return employeeName;
   }

   public void setEmployeeName( String employeeName )
   {
      this.employeeName = employeeName;
   }
}