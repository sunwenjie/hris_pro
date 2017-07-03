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
import com.kan.base.web.action.BaseAction;

public class ColumnVO extends BaseVO
{

   /**
   * Serial Version UID
   */
   private static final long serialVersionUID = -1162153910612579305L;

   /**
    * For DB
    */

   private String columnId;

   private String tableId;

   private String nameDB;

   private String nameSys;

   private String nameZH;

   private String nameEN;

   private String groupId;

   private String isPrimaryKey;

   private String isForeignKey;

   private String isDBColumn;

   private String isRequired;

   private String displayType;

   private String columnIndex;

   private String inputType;

   private String valueType;

   private String optionType;

   private String optionValue;

   private String cssStyle;

   private String jsEvent;

   private String validateType;

   private String validateRequired;

   private String validateLength;

   private String validateRange;

   private String editable;

   private String useThinking;

   private String thinkingId;

   private String thinkingAction;

   private String useTitle;

   private String titleZH;

   private String titleEN;

   private String description;

   // 是否支持导入管理
   private String canImport;

   /**
    * For Application 
    */
   private String validateLengthMin;

   private String validateLengthMax;

   private String validateRangeMin;

   private String validateRangeMax;

   //主键是否自增，配合isPrimaryKey使用， 值为0非自增，其他值为自增
   private String autoIncrement;

   // 特殊字段
   private String specialField;

   // 字段对齐（靠左，靠右）
   private String align;

   // 临时存储数据库表名
   private String tableName;

   // 临时存储ImportId
   private String tempImportId;

   // 导入时是否忽略默认验证 ，让handler来处理
   private String isIgnoreDefaultValidate;

   // 来自自定义页面的字段名字（默认为“null”）
   private String managerNameZH;

   // 来自自定义页面的字段名字（默认为“null”）
   private String managerNameEN;
   @JsonIgnore
   // 表 - 视图
   private List< MappingVO > tables = new ArrayList< MappingVO >();
   @JsonIgnore
   // 字段分组
   private List< MappingVO > columnGroups = new ArrayList< MappingVO >();
   @JsonIgnore
   // 输入框类型
   private List< MappingVO > inputTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 值类型
   private List< MappingVO > valueTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 选项来源
   private List< MappingVO > optionTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 选项来源 - 系统常量
   private List< MappingVO > systemDefines = new ArrayList< MappingVO >();
   @JsonIgnore
   // 选项来源 - 账户常量
   private List< MappingVO > accountDefines = new ArrayList< MappingVO >();
   @JsonIgnore
   // 选项来源 - 用户自定义
   private List< MappingVO > userDefines = new ArrayList< MappingVO >();
   @JsonIgnore
   // 验证类型
   private List< MappingVO > validateTypies = new ArrayList< MappingVO >();
   @JsonIgnore
   // 显示类型
   private List< MappingVO > displayTypes = new ArrayList< MappingVO >();
   @JsonIgnore
   // 具体的值
   private List< MappingVO > optionValues = new ArrayList< MappingVO >();

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      // 获得系统自定义的Table
      this.tables = KANConstants.getKANAccountConstants( getAccountId() ).getTables( request.getLocale().getLanguage(), getRole(), null );
      if ( this.tables != null )
      {
         this.tables.add( 0, super.getEmptyMappingVO() );
      }
      // 获得账户自定义的Column Group
      this.columnGroups = KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnGroup( request.getLocale().getLanguage(), super.getCorpId() );
      if ( this.columnGroups != null )
      {
         this.columnGroups.add( 0, super.getEmptyMappingVO() );
      }
      this.inputTypies = KANUtil.getMappings( request.getLocale(), "def.column.input.type" );
      this.valueTypies = KANUtil.getMappings( request.getLocale(), "def.column.value.type" );
      this.optionTypies = KANUtil.getMappings( request.getLocale(), "def.column.option.type" );
      // 获得系统常量选项列表
      this.systemDefines = KANUtil.getMappings( request.getLocale(), "def.column.option.type.system" );
      // 获得账户常量选项列表
      this.accountDefines = KANUtil.getMappings( request.getLocale(), "def.column.option.type.account" );
      // 获得用户自定义的选项列表
      this.userDefines = KANConstants.getKANAccountConstants( super.getAccountId() ).getColumnOptions( request.getLocale().getLanguage() );
      if ( this.userDefines != null )
      {
         this.userDefines.add( 0, super.getEmptyMappingVO() );
      }
      this.validateTypies = KANUtil.getMappings( request.getLocale(), "def.column.validate.type" );
      this.displayTypes = KANUtil.getMappings( request.getLocale(), "def.column.display.type" );
   }

   @Override
   public void reset()
   {
      this.tableId = "";
      this.nameDB = "";
      this.nameSys = "";
      this.nameZH = "";
      this.nameEN = "";
      this.groupId = "";
      this.isPrimaryKey = "";
      this.isForeignKey = "";
      this.isDBColumn = "";
      this.isRequired = "";
      this.displayType = "";
      this.columnIndex = "";
      this.inputType = "";
      this.valueType = "";
      this.optionType = "";
      this.optionValue = "";
      this.cssStyle = "";
      this.jsEvent = "";
      this.validateType = "";
      this.validateRequired = "";
      this.validateLength = "";
      this.validateRange = "";
      this.editable = "";
      this.useThinking = "";
      this.thinkingId = "";
      this.thinkingAction = "";
      this.useTitle = "";
      this.titleZH = "";
      this.titleEN = "";
      this.description = "";
      this.canImport = "0";
      super.setStatus( "" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object )
   {
      final ColumnVO columnVO = ( ColumnVO ) object;
      this.tableId = columnVO.getTableId();
      this.nameDB = columnVO.getNameDB();
      this.nameSys = columnVO.getNameSys();
      this.nameZH = columnVO.getNameZH();
      this.nameEN = columnVO.getNameEN();
      this.groupId = columnVO.getGroupId();
      this.isPrimaryKey = columnVO.getIsPrimaryKey();
      this.isForeignKey = columnVO.getIsForeignKey();
      this.isDBColumn = columnVO.getIsDBColumn();
      this.isRequired = columnVO.getIsRequired();
      this.displayType = columnVO.getDisplayType();
      this.columnIndex = columnVO.getColumnIndex();
      this.inputType = columnVO.getInputType();
      this.valueType = columnVO.getValueType();
      this.optionType = columnVO.getOptionType();
      this.optionValue = columnVO.getOptionValue();
      this.cssStyle = columnVO.getCssStyle();
      this.jsEvent = columnVO.getJsEvent();
      this.validateType = columnVO.getValidateType();
      this.validateRequired = columnVO.getValidateRequired();
      this.validateLength = columnVO.getValidateLength();
      this.validateRange = columnVO.getValidateRange();
      this.editable = columnVO.getEditable();
      this.useThinking = columnVO.getUseThinking();
      this.thinkingId = columnVO.getThinkingId();
      this.thinkingAction = columnVO.getThinkingAction();
      this.useTitle = columnVO.getUseTitle();
      this.titleZH = columnVO.getTitleZH();
      this.titleEN = columnVO.getTitleEN();
      this.description = columnVO.getDescription();
      this.canImport = columnVO.getCanImport();
      super.setStatus( columnVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( columnVO.getCorpId() );
   }

   public String getDecodeTable()
   {
      return decodeField( tableId, tables );
   }

   public String getDecodeGroup()
   {
      return decodeField( groupId, columnGroups );
   }

   public String getDecodeInputType()
   {
      return decodeField( inputType, inputTypies );
   }

   public String getDecodeValueType()
   {
      return decodeField( valueType, valueTypies );
   }

   public String getDecodeOptionType()
   {
      return decodeField( optionType, optionTypies );
   }

   public String getDecodeDisplayType()
   {
      return decodeField( displayType, displayTypes );
   }

   public String getDecodeValidateType()
   {
      return decodeField( validateType, validateTypies );
   }

   public String getDecodeIsRequired()
   {
      return decodeField( isRequired, super.getFlags() );
   }

   public String getColumnId()
   {
      return columnId;
   }

   public void setColumnId( String columnId )
   {
      this.columnId = columnId;
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
   }

   public String getNameDB()
   {
      return nameDB;
   }

   public void setNameDB( String nameDB )
   {
      this.nameDB = nameDB;
   }

   public String getNameSys()
   {
      return nameSys;
   }

   public void setNameSys( String nameSys )
   {
      this.nameSys = nameSys;
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

   public String getDisplayType()
   {
      return displayType;
   }

   public void setDisplayType( String displayType )
   {
      this.displayType = displayType;
   }

   public String getColumnIndex()
   {
      return columnIndex;
   }

   public void setColumnIndex( String columnIndex )
   {
      this.columnIndex = columnIndex;
   }

   public String getInputType()
   {
      return inputType;
   }

   public void setInputType( String inputType )
   {
      this.inputType = inputType;
   }

   public String getValueType()
   {
      return valueType;
   }

   public void setValueType( String valueType )
   {
      this.valueType = valueType;
   }

   public String getOptionType()
   {
      return optionType;
   }

   public void setOptionType( String optionType )
   {
      this.optionType = optionType;
   }

   public String getOptionValue()
   {
      return optionValue;
   }

   public void setOptionValue( String optionValue )
   {
      this.optionValue = optionValue;
   }

   public String getCssStyle()
   {
      return cssStyle;
   }

   public void setCssStyle( String cssStyle )
   {
      this.cssStyle = cssStyle;
   }

   public String getJsEvent()
   {
      return jsEvent;
   }

   public void setJsEvent( String jsEvent )
   {
      this.jsEvent = jsEvent;
   }

   public String getValidateType()
   {
      return validateType;
   }

   public void setValidateType( String validateType )
   {
      this.validateType = validateType;
   }

   public String getValidateRequired()
   {
      return validateRequired;
   }

   public void setValidateRequired( String validateRequired )
   {
      this.validateRequired = validateRequired;
   }

   public String getValidateLength()
   {
      if ( KANUtil.filterEmpty( validateLengthMin ) != null && KANUtil.filterEmpty( validateLengthMax ) != null )
      {
         return validateLengthMin + "_" + validateLengthMax;
      }

      return validateLength;
   }

   public void setValidateLength( String validateLength )
   {
      this.validateLength = validateLength;
   }

   public String getValidateRange()
   {
      if ( KANUtil.filterEmpty( validateRangeMin ) != null && KANUtil.filterEmpty( validateRangeMax ) != null )
      {
         return validateRangeMin + "_" + validateRangeMax;
      }

      return validateRange;
   }

   public void setValidateRange( String validateRange )
   {
      this.validateRange = validateRange;
   }

   public String getValidateLengthMin()
   {
      if ( KANUtil.filterEmpty( this.validateLength ) != null )
      {
         return validateLength.split( "_" ).length == 2 ? validateLength.split( "_" )[ 0 ] : null;
      }

      return validateLengthMin;
   }

   public void setValidateLengthMin( String validateLengthMin )
   {
      this.validateLengthMin = validateLengthMin;
   }

   public String getValidateLengthMax()
   {
      if ( KANUtil.filterEmpty( this.validateLength ) != null )
      {
         return validateLength.split( "_" ).length == 2 ? validateLength.split( "_" )[ 1 ] : null;
      }

      return validateLengthMax;
   }

   public void setValidateLengthMax( String validateLengthMax )
   {
      this.validateLengthMax = validateLengthMax;
   }

   public String getValidateRangeMin()
   {
      if ( KANUtil.filterEmpty( this.validateRange ) != null )
      {
         return validateRange.split( "_" ).length == 2 ? validateRange.split( "_" )[ 0 ] : null;
      }

      return validateRangeMin;
   }

   public void setValidateRangeMin( String validateRangeMin )
   {
      this.validateRangeMin = validateRangeMin;
   }

   public String getValidateRangeMax()
   {
      if ( KANUtil.filterEmpty( this.validateRange ) != null )
      {
         return validateRange.split( "_" ).length == 2 ? validateRange.split( "_" )[ 1 ] : null;
      }

      return validateRangeMax;
   }

   public void setValidateRangeMax( String validateRangeMax )
   {
      this.validateRangeMax = validateRangeMax;
   }

   public String getEditable()
   {
      return editable;
   }

   public void setEditable( String editable )
   {
      this.editable = editable;
   }

   public String getUseThinking()
   {
      return useThinking;
   }

   public void setUseThinking( String useThinking )
   {
      this.useThinking = useThinking;
   }

   public String getThinkingId()
   {
      return thinkingId;
   }

   public void setThinkingId( String thinkingId )
   {
      this.thinkingId = thinkingId;
   }

   public String getThinkingAction()
   {
      return thinkingAction;
   }

   public void setThinkingAction( String thinkingAction )
   {
      this.thinkingAction = thinkingAction;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getOptionValues()
   {
      return optionValues;
   }

   public void loadOptionValues( final HttpServletRequest request ) throws KANException
   {
      setOptionValues( KANUtil.getColumnOptionValues( request.getLocale(), this, BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) ) );
   }

   public void setOptionValues( List< MappingVO > optionValues )
   {
      this.optionValues = optionValues;
   }

   public String getOptionValueByOptionName( String optionName )
   {
      if ( this.optionValues != null && this.optionValues.size() > 0 )
      {
         for ( MappingVO optionMapping : this.optionValues )
         {
            if ( ( optionMapping.getMappingValue() != null && optionMapping.getMappingValue().equals( optionName ) )
                  || ( optionMapping.getMappingTemp() != null && optionMapping.getMappingTemp().equals( optionName ) ) )
            {
               return optionMapping.getMappingId();
            }
         }
      }

      return "0";
   }

   public List< MappingVO > getTables()
   {
      return tables;
   }

   public void setTables( List< MappingVO > tables )
   {
      this.tables = tables;
   }

   public List< MappingVO > getColumnGroups()
   {
      return columnGroups;
   }

   public void setColumnGroups( List< MappingVO > columnGroups )
   {
      this.columnGroups = columnGroups;
   }

   public List< MappingVO > getInputTypies()
   {
      return inputTypies;
   }

   public void setInputTypies( List< MappingVO > inputTypies )
   {
      this.inputTypies = inputTypies;
   }

   public List< MappingVO > getValueTypies()
   {
      return valueTypies;
   }

   public void setValueTypies( List< MappingVO > valueTypies )
   {
      this.valueTypies = valueTypies;
   }

   public List< MappingVO > getOptionTypies()
   {
      return optionTypies;
   }

   public void setOptionTypies( List< MappingVO > optionTypies )
   {
      this.optionTypies = optionTypies;
   }

   public List< MappingVO > getValidateTypies()
   {
      return validateTypies;
   }

   public void setValidateTypies( List< MappingVO > validateTypies )
   {
      this.validateTypies = validateTypies;
   }

   public List< MappingVO > getSystemDefines()
   {
      return systemDefines;
   }

   public void setSystemDefines( List< MappingVO > systemDefines )
   {
      this.systemDefines = systemDefines;
   }

   public List< MappingVO > getAccountDefines()
   {
      return accountDefines;
   }

   public void setAccountDefines( List< MappingVO > accountDefines )
   {
      this.accountDefines = accountDefines;
   }

   public List< MappingVO > getUserDefines()
   {
      return userDefines;
   }

   public void setUserDefines( List< MappingVO > userDefines )
   {
      this.userDefines = userDefines;
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

   public List< MappingVO > getDisplayTypes()
   {
      return displayTypes;
   }

   public void setDisplayTypes( List< MappingVO > displayTypes )
   {
      this.displayTypes = displayTypes;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( columnId );
   }

   public String getEncodedTableId() throws KANException
   {
      return encodedField( tableId );
   }

   public String getEncodedGroupId() throws KANException
   {
      return encodedField( groupId );
   }

   public String getSpecialField()
   {
      return specialField;
   }

   public void setSpecialField( String specialField )
   {
      this.specialField = specialField;
   }

   public final String getAlign()
   {
      return align;
   }

   public final void setAlign( String align )
   {
      this.align = align;
   }

   public final String getTableName()
   {
      return tableName;
   }

   public final void setTableName( String tableName )
   {
      this.tableName = tableName;
   }

   public final String getIsPrimaryKey()
   {
      return isPrimaryKey;
   }

   public final void setIsPrimaryKey( String isPrimaryKey )
   {
      this.isPrimaryKey = isPrimaryKey;
   }

   public final String getIsForeignKey()
   {
      return isForeignKey;
   }

   public final void setIsForeignKey( String isForeignKey )
   {
      this.isForeignKey = isForeignKey;
   }

   public String getTempImportId()
   {
      return tempImportId;
   }

   public void setTempImportId( String tempImportId )
   {
      this.tempImportId = tempImportId;
   }

   public String getIsDBColumn()
   {
      return isDBColumn;
   }

   public void setIsDBColumn( String isDBColumn )
   {
      this.isDBColumn = isDBColumn;
   }

   public String getAutoIncrement()
   {
      return autoIncrement;
   }

   public void setAutoIncrement( String autoIncrement )
   {
      this.autoIncrement = autoIncrement;
   }

   public String getIsIgnoreDefaultValidate()
   {
      return isIgnoreDefaultValidate;
   }

   public void setIsIgnoreDefaultValidate( String isIgnoreDefaultValidate )
   {
      this.isIgnoreDefaultValidate = isIgnoreDefaultValidate;
   }

   public String getManagerNameZH()
   {
      return managerNameZH;
   }

   public void setManagerNameZH( String managerNameZH )
   {
      this.managerNameZH = managerNameZH;
   }

   public String getManagerNameEN()
   {
      return managerNameEN;
   }

   public void setManagerNameEN( String managerNameEN )
   {
      this.managerNameEN = managerNameEN;
   }

   public String getCanImport()
   {
      return canImport;
   }

   public void setCanImport( String canImport )
   {
      this.canImport = canImport;
   }

}
