package com.kan.base.domain.define;

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

public class ManagerDetailVO extends BaseVO
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 8774405285343305870L;

   /**
    * For DB
    */
   // 页面从表ID
   private String managerDetailId;

   // 页面主表ID
   private String managerHeaderId;

   // 字段ID
   private String columnId;

   // 字段名称（中文）
   private String nameZH;

   // 字段名称（英文）
   private String nameEN;

   // 字段分组
   private String groupId;

   // 是否必填
   private String isRequired;

   // 显示方式
   private String display;

   // 显示顺序   
   private String columnIndex;

   // 启用提示
   private String useTitle;

   // 标签提示（中文）
   private String titleZH;

   // 标签提示（英文）
   private String titleEN;

   // 字段对齐（靠左，靠右）
   private String align;

   // 描述
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // 字段 Action中进行初始化
   private List< MappingVO > columns = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字段分组
   private List< MappingVO > columnGroups = new ArrayList< MappingVO >();
   @JsonIgnore
   // 显示类型
   private List< MappingVO > displayTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 对齐方式
   private List< MappingVO > aligns = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );

      final List< MappingVO > tempMappingVOs = new ArrayList< MappingVO >();
      // 获得系统自定义的Column Group
      tempMappingVOs.addAll( KANConstants.getKANAccountConstants( KANConstants.SUPER_ACCOUNT_ID ).getColumnGroup( request.getLocale().getLanguage() ) );
      // 获得账户自定义的Column Group
      tempMappingVOs.addAll( KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnGroup( request.getLocale().getLanguage(), super.getCorpId() ) );
      this.columnGroups = tempMappingVOs;

      if ( this.columnGroups != null )
      {
         this.columnGroups.add( 0, super.getEmptyMappingVO() );
      }
      this.aligns = KANUtil.getMappings( request.getLocale(), "def.manager.detail.align" );
      this.displayTypes = KANUtil.getMappings( request.getLocale(), "def.column.display.type" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( managerDetailId );
   }

   @Override
   public void reset() throws KANException
   {
      this.managerHeaderId = "";
      this.columnId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.groupId = "";
      this.display = "";
      this.columnIndex = "";
      this.useTitle = "";
      this.titleZH = "";
      this.titleEN = "";
      this.align = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ManagerDetailVO managerDetailVO = ( ManagerDetailVO ) object;
      this.columnId = managerDetailVO.getColumnId();
      this.nameZH = managerDetailVO.getNameZH();
      this.nameEN = managerDetailVO.getNameEN();
      this.groupId = managerDetailVO.getGroupId();
      this.isRequired = managerDetailVO.getIsRequired();
      this.display = managerDetailVO.getDisplay();
      this.columnIndex = managerDetailVO.getColumnIndex();
      this.useTitle = managerDetailVO.getUseTitle();
      this.titleZH = managerDetailVO.getTitleZH();
      this.titleEN = managerDetailVO.getTitleEN();
      this.align = managerDetailVO.getAlign();
      this.description = managerDetailVO.getDescription();
      super.setStatus( managerDetailVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   // 解译字段
   public String getDecodeColumn()
   {
      return decodeField( this.columnId, this.columns, true );
   }

   // 解译字段分组
   public String getDecodeGroup()
   {
      return decodeField( this.groupId, this.columnGroups );
   }

   public String getManagerDetailId()
   {
      return managerDetailId;
   }

   public void setManagerDetailId( String managerDetailId )
   {
      this.managerDetailId = managerDetailId;
   }

   public String getManagerHeaderId()
   {
      return managerHeaderId;
   }

   public void setManagerHeaderId( String managerHeaderId )
   {
      this.managerHeaderId = managerHeaderId;
   }

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
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

   public String getGroupId()
   {
      return groupId;
   }

   public void setGroupId( String groupId )
   {
      this.groupId = groupId;
   }

   public String getIsRequired()
   {
      return isRequired;
   }

   public void setIsRequired( String isRequired )
   {
      this.isRequired = isRequired;
   }

   public String getDisplay()
   {
      return display;
   }

   public void setDisplay( String display )
   {
      this.display = display;
   }

   public String getColumnIndex()
   {
      return KANUtil.filterEmpty( columnIndex );
   }

   public void setColumnIndex( String columnIndex )
   {
      this.columnIndex = columnIndex;
   }

   public String getUseTitle()
   {
      return useTitle;
   }

   public void setUseTitle( String useTitle )
   {
      this.useTitle = useTitle;
   }

   public String getTitleZH()
   {
      return titleZH;
   }

   public void setTitleZH( String titleZH )
   {
      this.titleZH = titleZH;
   }

   public String getTitleEN()
   {
      return titleEN;
   }

   public void setTitleEN( String titleEN )
   {
      this.titleEN = titleEN;
   }

   public String getAlign()
   {
      return align;
   }

   public void setAlign( String align )
   {
      this.align = align;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getColumns()
   {
      return columns;
   }

   public void setColumns( List< MappingVO > columns )
   {
      this.columns = columns;
   }

   public List< MappingVO > getColumnGroups()
   {
      return columnGroups;
   }

   public void setColumnGroups( List< MappingVO > columnGroups )
   {
      this.columnGroups = columnGroups;
   }

   public List< MappingVO > getDisplayTypes()
   {
      return displayTypes;
   }

   public void setDisplayTypes( List< MappingVO > displayTypes )
   {
      this.displayTypes = displayTypes;
   }

   public List< MappingVO > getAligns()
   {
      return aligns;
   }

   public void setAligns( List< MappingVO > aligns )
   {
      this.aligns = aligns;
   }

}
