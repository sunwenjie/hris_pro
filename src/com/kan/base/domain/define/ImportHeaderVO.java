package com.kan.base.domain.define;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

/**  
*   
* 项目名称：HRO_V1  
* 类名称：ImportHeaderVO  
* 类描述：  
* 创建人：Jixiang  
* 创建时间：2014-3-12 下午04:29:45  
* 修改人：Jixiang  
* 修改时间：2014-3-12 下午04:29:45  
* 修改备注：  
* @version   
*   
*/
public class ImportHeaderVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -2948575542385388878L;

   /**
    * For DB
    */

   private String importHeaderId;

   private String parentId;

   private String tableId;

   private String nameZH;

   private String nameEN;

   private String description;
   // 开放职位
   private String positionIds;

   // 开放职级
   private String positionGradeIds;

   // 开放职位组
   private String positionGroupIds;

   private String needBatchId;
   // 导入特殊配置
   private String matchConfig;

   // IExcelImportHandler BeanId
   private String handlerBeanId;
   /**
    * For Application
    */
   @JsonIgnore
   private List< MappingVO > tables;
   @JsonIgnore
   private List< MappingVO > parentIds;
   @JsonIgnore
   private List< MappingVO > matchConfigs;
   @JsonIgnore
   private List< MappingVO > handlerBeanIds;
   @JsonIgnore
   // 职级数组
   private String[] positionIdArray = new String[] {};
   @JsonIgnore
   // 职位数组
   private String[] positionGradeIdArray = new String[] {};
   @JsonIgnore
   // 职位组数组
   private String[] groupIdArray = new String[] {};

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // 获得系统自定义的Table
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), TableVO.IMPORT );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }

      try
      {
         KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) );
         if ( accountConstants != null )
         {

            this.parentIds = accountConstants.getImportHeaders( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId() ) );
            if ( this.parentIds != null )
            {
               this.parentIds.add( 0, super.getEmptyMappingVO() );
            }
         }
      }
      catch ( KANException e )
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      this.matchConfigs = KANUtil.getMappings( request.getLocale(), "def.import.sub.header.config" );
      this.handlerBeanIds = KANUtil.getMappings( request.getLocale(), "def.import.excue.handler" );
      super.setStatuses( KANUtil.getMappings( request.getLocale(), "def.report.header.status" ) );
   }

   @Override
   public void reset() throws KANException
   {
      this.parentId = "";
      this.tableId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      this.positionIds = "";
      this.positionGradeIds = "";
      this.positionGroupIds = "";
      this.needBatchId = "";
      this.matchConfig = "";
      this.handlerBeanId = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ImportHeaderVO importHeaderVO = ( ImportHeaderVO ) object;
      this.nameZH = importHeaderVO.getNameZH();
      this.nameEN = importHeaderVO.getNameEN();
      this.parentId = importHeaderVO.getParentId();
      this.description = importHeaderVO.getDescription();
      this.needBatchId = importHeaderVO.getNeedBatchId();
      this.positionIds = KANUtil.toJasonArray( importHeaderVO.getPositionIdArray() );
      this.positionGradeIds = KANUtil.toJasonArray( importHeaderVO.getPositionGradeIds() );
      this.positionGroupIds = KANUtil.toJasonArray( importHeaderVO.getPositionGroupIds() );
      this.matchConfig = importHeaderVO.getMatchConfig();
      this.handlerBeanId = importHeaderVO.getHandlerBeanId();
      super.setStatus( importHeaderVO.getStatus() );
      super.setModifyBy( importHeaderVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.importHeaderId );
   }

   public String getDecodeTable()
   {
      if ( this.tables != null )
      {
         for ( MappingVO mappingVO : this.tables )
         {
            if ( mappingVO.getMappingId().equals( tableId ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }
      return "";
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

   public String getImportHeaderId()
   {
      return importHeaderId;
   }

   public void setImportHeaderId( String importHeaderId )
   {
      this.importHeaderId = importHeaderId;
   }

   public String getParentId()
   {
      return parentId;
   }

   public void setParentId( String parentId )
   {
      this.parentId = parentId;
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getTables()
   {
      return tables;
   }

   public void setTables( List< MappingVO > tables )
   {
      this.tables = tables;
   }

   public String getPositionIds()
   {
      return positionIds;
   }

   public void setPositionIds( String positionIds )
   {
      this.positionIds = positionIds;
   }

   public String getPositionGradeIds()
   {
      return positionGradeIds;
   }

   public void setPositionGradeIds( String positionGradeIds )
   {
      this.positionGradeIds = positionGradeIds;
   }

   public String getPositionGroupIds()
   {
      return positionGroupIds;
   }

   public void setPositionGroupIds( String positionGroupIds )
   {
      this.positionGroupIds = positionGroupIds;
   }

   public String[] getPositionIdArray()
   {
      return positionIdArray;
   }

   public void setPositionIdArray( String[] positionIdArray )
   {
      this.positionIdArray = positionIdArray;
   }

   public String[] getPositionGradeIdArray()
   {
      return positionGradeIdArray;
   }

   public void setPositionGradeIdArray( String[] positionGradeIdArray )
   {
      this.positionGradeIdArray = positionGradeIdArray;
   }

   public String[] getGroupIdArray()
   {
      return groupIdArray;
   }

   public void setGroupIdArray( String[] groupIdArray )
   {
      this.groupIdArray = groupIdArray;
   }

   public List< MappingVO > getParentIds()
   {
      return parentIds;
   }

   public void setParentIds( List< MappingVO > parentIds )
   {
      this.parentIds = parentIds;
   }

   public String getNeedBatchId()
   {
      return needBatchId;
   }

   public void setNeedBatchId( String needBatchId )
   {
      this.needBatchId = needBatchId;
   }

   public String getMatchConfig()
   {
      return matchConfig;
   }

   public void setMatchConfig( String matchConfig )
   {
      this.matchConfig = matchConfig;
   }

   public List< MappingVO > getMatchConfigs()
   {
      return matchConfigs;
   }

   public void setMatchConfigs( List< MappingVO > matchConfigs )
   {
      this.matchConfigs = matchConfigs;
   }

   public String getHandlerBeanId()
   {
      return handlerBeanId;
   }

   public void setHandlerBeanId( String handlerBeanId )
   {
      this.handlerBeanId = handlerBeanId;
   }

   public List< MappingVO > getHandlerBeanIds()
   {
      return handlerBeanIds;
   }

   public void setHandlerBeanIds( List< MappingVO > handlerBeanIds )
   {
      this.handlerBeanIds = handlerBeanIds;
   }
}
