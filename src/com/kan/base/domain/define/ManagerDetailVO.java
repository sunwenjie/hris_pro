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
   // ҳ��ӱ�ID
   private String managerDetailId;

   // ҳ������ID
   private String managerHeaderId;

   // �ֶ�ID
   private String columnId;

   // �ֶ����ƣ����ģ�
   private String nameZH;

   // �ֶ����ƣ�Ӣ�ģ�
   private String nameEN;

   // �ֶη���
   private String groupId;

   // �Ƿ����
   private String isRequired;

   // ��ʾ��ʽ
   private String display;

   // ��ʾ˳��   
   private String columnIndex;

   // ������ʾ
   private String useTitle;

   // ��ǩ��ʾ�����ģ�
   private String titleZH;

   // ��ǩ��ʾ��Ӣ�ģ�
   private String titleEN;

   // �ֶζ��루���󣬿��ң�
   private String align;

   // ����
   private String description;

   /**
    * For Application
    */
   @JsonIgnore
   // �ֶ� Action�н��г�ʼ��
   private List< MappingVO > columns = new ArrayList< MappingVO >();
   @JsonIgnore
   // �ֶη���
   private List< MappingVO > columnGroups = new ArrayList< MappingVO >();
   @JsonIgnore
   // ��ʾ����
   private List< MappingVO > displayTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // ���뷽ʽ
   private List< MappingVO > aligns = new ArrayList< MappingVO >();

   @Override
   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );

      final List< MappingVO > tempMappingVOs = new ArrayList< MappingVO >();
      // ���ϵͳ�Զ����Column Group
      tempMappingVOs.addAll( KANConstants.getKANAccountConstants( KANConstants.SUPER_ACCOUNT_ID ).getColumnGroup( request.getLocale().getLanguage() ) );
      // ����˻��Զ����Column Group
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

   // �����ֶ�
   public String getDecodeColumn()
   {
      return decodeField( this.columnId, this.columns, true );
   }

   // �����ֶη���
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
