package com.kan.hro.domain.biz.client;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ClientContractVO extends BaseVO
{

   /**  
   * SerialVersionUID
   */
   private static final long serialVersionUID = -8001376454142554781L;

   // 主键ID
   private String contractId;

   // 法务实体Id
   private String entityId;

   // 业务类型Id
   private String businessTypeId;

   // 合同模板Id
   private String templateId;

   // 合同编号（归档使用）
   private String contractNo;

   // 版本类型（系统版，客户版）
   private String versionType;

   // 主合同号（归档编号）
   private String masterContractId;

   // 发票地址（关联Client Invoice Address） 
   private String invoiceAddressId;

   // 合同名称（中文）
   private String nameZH;

   // 合同名称（英文）
   private String nameEN;

   // 合同内容，参数${column}使用系统常量填充，参数${blank_input}，${blank_textarea}创建合同时填写
   private String content;

   // 合同开始时间
   private String startDate;

   // 合同结束时间
   private String endDate;

   // 合同时限（月数）
   private String period;

   // 附件
   private String attachment;

   // 所属部门（Branch Id）
   private String branch;

   // 所属人（Position Id）
   private String owner;

   // 描述
   private String description;

   /**
    * For App
    */
   // 附件列表
   private String[] attachmentArray;

   // 客户中文名称
   private String clientNameZH;

   // 客户英文名称
   private String clientNameEN;

   // 客户编号
   private String clientNumber;

   // 法务实体名称
   private String entityName;

   // 业务类型名称
   private String businessTypeName;

   // 合同模板名称（商务）
   private String templateName;

   // 法务实体
   private List< MappingVO > entitys = new ArrayList< MappingVO >();

   // 业务类型
   private List< MappingVO > businessTypes = new ArrayList< MappingVO >();

   // 合同模板
   private List< MappingVO > templates = new ArrayList< MappingVO >();

   // 版本类型
   private List< MappingVO > versionTypes = new ArrayList< MappingVO >();

   // 合同时限
   private List< MappingVO > periods = new ArrayList< MappingVO >();

   private List< ConstantVO > constantVOs;

   // 附加在状态后面
   private String isLink;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      super.setStatuses( KANUtil.getMappings( this.getLocale(), "business.client.contract.statuses" ) );
      this.versionTypes = KANUtil.getMappings( this.getLocale(), "business.client.contract.versionTypes" );
      this.periods = KANUtil.getMappings( this.getLocale(), "business.client.contract.periods" );
      this.entitys = KANConstants.getKANAccountConstants( super.getAccountId() ).getEntities( request.getLocale().getLanguage(), super.getCorpId() );
      this.businessTypes = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessTypes( request.getLocale().getLanguage(), super.getCorpId() );
      this.templates = KANConstants.getKANAccountConstants( super.getAccountId() ).getBusinessContractTemplates( request.getLocale().getLanguage(), super.getCorpId() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( contractId );
   }

   // 客户ID加密
   public String getEncodedClientId() throws KANException
   {
      return encodedField( super.getClientId() );
   }

   // 法务实体ID加密
   public String getEncodedEntityId() throws KANException
   {
      return encodedField( entityId );
   }

   // 合同模板ID加密
   public String getEncodedTemplateId() throws KANException
   {
      return encodedField( templateId );
   }

   // 获取版本类型
   public String getDecodeVersionType()
   {
      return decodeField( this.versionType, this.versionTypes );
   }

   // 获取商务合同模板
   public String getDecodeTemplateId()
   {
      return decodeField( this.templateId, this.templates );
   }

   // 获取法务实体
   public String getDecodeEntityId()
   {
      return decodeField( this.entityId, this.entitys );
   }

   // 获取业务类型
   public String getDecodeBusinessTypeId()
   {
      return decodeField( this.businessTypeId, this.businessTypes );
   }

   // 获取合同时限
   public String getDecodePeriod() throws ParseException
   {
      return decodeField( this.period, this.periods );
   }

   @Override
   public void reset() throws KANException
   {
      this.entityId = "";
      this.businessTypeId = "";
      this.templateId = "";
      this.contractNo = "";
      this.versionType = "";
      this.masterContractId = "";
      this.invoiceAddressId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.startDate = "";
      this.endDate = "";
      this.period = "";
      this.attachment = "";
      this.branch = "";
      this.owner = "";
      this.description = "";
      super.setStatus( "0" );
      super.setClientId( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      ClientContractVO clientContractVO = ( ClientContractVO ) object;
      this.entityId = clientContractVO.getEntityId();
      this.businessTypeId = clientContractVO.getBusinessTypeId();
      this.templateId = clientContractVO.getTemplateId();
      this.contractNo = clientContractVO.getContractNo();
      this.versionType = clientContractVO.getVersionType();
      this.masterContractId = clientContractVO.getMasterContractId();
      this.invoiceAddressId = clientContractVO.getInvoiceAddressId();
      this.nameZH = clientContractVO.getNameZH();
      this.nameEN = clientContractVO.getNameEN();
      this.startDate = clientContractVO.getStartDate();
      this.endDate = clientContractVO.getEndDate();
      this.period = clientContractVO.getPeriod();
      this.attachment = clientContractVO.getAttachment();
      this.attachmentArray = clientContractVO.getAttachmentArray();
      this.branch = clientContractVO.getBranch();
      this.owner = clientContractVO.getOwner();
      this.description = clientContractVO.getDescription();
      super.setModifyDate( new Date() );
      super.setModifyBy( clientContractVO.getModifyBy() );
      super.setClientId( clientContractVO.getClientId() );
      super.setCorpId( clientContractVO.getCorpId() );
   }

   public String getContractId()
   {
      return contractId;
   }

   public void setContractId( String contractId )
   {
      this.contractId = contractId;
   }

   public String getEntityId()
   {
      return entityId;
   }

   public void setEntityId( String entityId )
   {
      this.entityId = entityId;
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
   }

   public String getTemplateId()
   {
      return templateId;
   }

   public void setTemplateId( String templateId )
   {
      this.templateId = templateId;
   }

   public String getContractNo()
   {
      return contractNo;
   }

   public void setContractNo( String contractNo )
   {
      this.contractNo = contractNo;
   }

   public String getVersionType()
   {
      return versionType;
   }

   public void setVersionType( String versionType )
   {
      this.versionType = versionType;
   }

   public String getMasterContractId()
   {
      return masterContractId;
   }

   public void setMasterContractId( String masterContractId )
   {
      this.masterContractId = masterContractId;
   }

   public String getInvoiceAddressId()
   {
      return KANUtil.filterEmpty( invoiceAddressId );
   }

   public void setInvoiceAddressId( String invoiceAddressId )
   {
      this.invoiceAddressId = invoiceAddressId;
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

   public final String getName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNameZH();
         }
         else
         {
            return this.getNameEN();
         }
      }
      else
      {
         return this.getNameZH();
      }
   }

   public String getContent()
   {
      return content;
   }

   public void setContent( String content )
   {
      this.content = content;
   }

   public String getStartDate()
   {
      return KANUtil.filterEmpty( decodeDate( startDate ) );
   }

   public void setStartDate( String startDate )
   {
      this.startDate = startDate;
   }

   public String getEndDate()
   {
      return KANUtil.filterEmpty( decodeDate( endDate ) );
   }

   public void setEndDate( String endDate )
   {
      this.endDate = endDate;
   }

   public String getPeriod()
   {
      if ( this.period == null || this.period.equals( "0" ) )
      {
         if ( this.getEndDate() != null && !this.getEndDate().trim().equals( "" ) && this.getEndDate() != null && !this.getEndDate().trim().equals( "" ) )
         {
            try
            {
               return Integer.toString( getMonth( startDate, endDate ) );
            }
            catch ( ParseException e )
            {
               e.printStackTrace();
            }
         }
      }
      return period;
   }

   public int getMonth( final String startDate, final String endDate ) throws ParseException
   {
      SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      Calendar c = Calendar.getInstance();
      // 获取起始时间段
      c.setTime( sdf.parse( startDate ) );
      int startYeah = c.get( Calendar.YEAR );
      int startMonth = c.get( Calendar.MONTH );
      int startDay = c.get( Calendar.DATE );
      // 获取结束时间段
      c.setTime( sdf.parse( endDate ) );
      int endYeah = c.get( Calendar.YEAR );
      int endMonth = c.get( Calendar.MONTH );
      int endDay = c.get( Calendar.DATE );

      // 换算日期日期取整
      final BigDecimal monthBigDecimal = new BigDecimal( Integer.valueOf( ( endYeah - startYeah ) * 12 + ( endMonth - startMonth ) + ( endDay - startDay ) / 100 ) ).setScale( 0, BigDecimal.ROUND_HALF_UP );
      return monthBigDecimal.intValue();
   }

   public void setPeriod( String period )
   {
      this.period = period;
   }

   public String getAttachment()
   {
      return attachment;
   }

   public void setAttachment( String attachment )
   {
      this.attachment = attachment;
      this.attachmentArray = KANUtil.jasonArrayToStringArray( attachment );
   }

   public String getBranch()
   {
      return branch;
   }

   public void setBranch( String branch )
   {
      this.branch = branch;
   }

   public String getOwner()
   {
      return owner;
   }

   public void setOwner( String owner )
   {
      this.owner = owner;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   public String getBusinessTypeName()
   {
      return businessTypeName;
   }

   public void setBusinessTypeName( String businessTypeName )
   {
      this.businessTypeName = businessTypeName;
   }

   public String getTemplateName()
   {
      return templateName;
   }

   public void setTemplateName( String templateName )
   {
      this.templateName = templateName;
   }

   public List< MappingVO > getVersionTypes()
   {
      return versionTypes;
   }

   public void setVersionTypes( List< MappingVO > versionTypes )
   {
      this.versionTypes = versionTypes;
   }

   public List< MappingVO > getEntitys()
   {
      return entitys;
   }

   public void setEntitys( List< MappingVO > entitys )
   {
      this.entitys = entitys;
   }

   public List< MappingVO > getBusinessTypes()
   {
      return businessTypes;
   }

   public void setBusinessTypes( List< MappingVO > businessTypes )
   {
      this.businessTypes = businessTypes;
   }

   public List< MappingVO > getTemplates()
   {
      return templates;
   }

   public void setTemplates( List< MappingVO > templates )
   {
      this.templates = templates;
   }

   public List< MappingVO > getPeriods()
   {
      return periods;
   }

   public void setPeriods( List< MappingVO > periods )
   {
      this.periods = periods;
   }

   public String[] getAttachmentArray()
   {
      return attachmentArray;
   }

   public void setAttachmentArray( String[] attachmentArray )
   {
      this.attachmentArray = attachmentArray;
      this.attachment = KANUtil.toJasonArray( attachmentArray );
   }

   public String getClientNumber()
   {
      return clientNumber;
   }

   public void setClientNumber( String clientNumber )
   {
      this.clientNumber = clientNumber;
   }

   public String getClientNameZH()
   {
      return clientNameZH;
   }

   public void setClientNameZH( String clientNameZH )
   {
      this.clientNameZH = clientNameZH;
   }

   public String getClientNameEN()
   {
      return clientNameEN;
   }

   public void setClientNameEN( String clientNameEN )
   {
      this.clientNameEN = clientNameEN;
   }

   public final String getClientName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getClientNameZH();
         }
         else
         {
            return this.getClientNameEN();
         }
      }
      else
      {
         return this.getClientNameZH();
      }
   }

   public final String getContractName()
   {
      if ( super.getLocale() != null )
      {
         if ( super.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
         {
            return this.getNameZH();
         }
         else
         {
            return this.getNameEN();
         }
      }
      else
      {
         return this.getNameZH();
      }
   }

   public String getIsLink() throws KANException
   {
      if ( "1".equals( super.getStatus() ) || "4".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<a onclick=\"submit_object('" + getEncodedId() + "')\">提交</a>";
      }
      else if ( "2".equals( super.getStatus() ) )
      {
         isLink = "&nbsp;&nbsp;<img src='images/magnifer.png' onclick=popupWorkflow('" + this.getWorkflowId() + "'); />";
      }

      return isLink;
   }

   public void setIsLink( String isLink )
   {
      this.isLink = isLink;
   }

   public List< ConstantVO > getConstantVOs()
   {
      return constantVOs;
   }

   public void setConstantVOs( List< ConstantVO > constantVOs )
   {
      this.constantVOs = constantVOs;
   }

}
