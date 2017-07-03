package com.kan.base.domain.define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class TableVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 99576852565803056L;

   public static final String MANAGER = "1";

   public static final String LIST = "2";

   public static final String SEARCH = "3";

   public static final String REPORT = "4";

   public static final String IMPORT = "5";

   /**
    * For DB
    */
   // 
   private String tableId;

   private String nameZH;

   private String nameEN;

   private String tableType;

   private String tableIndex;

   private String accessAction;

   private String accessName;

   private String accessTempName;

   // 视图关联table自定义字段
   private String linkedTableIds;

   // 是否支持页面管理
   private String canManager;

   // 是否支持列表管理
   private String canList;

   // 是否支持搜索管理
   private String canSearch;

   // 是否支持报表管理
   private String canReport;

   // 是否支持导入管理
   private String canImport;

   private String className;

   private String description;

   private String moduleType;

   /**
    * For Application
    */
   // 页面备注（满足ManagerHeaderVO）
   private String comments;

   private List< MappingVO > tableTypes = new ArrayList< MappingVO >();

   private List< MappingVO > roles = new ArrayList< MappingVO >();

   private List< MappingVO > moduleTypes = new ArrayList< MappingVO >();
   

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.moduleTypes = KANUtil.getMappings( this.getLocale(), "sys.module.types" );
      this.tableTypes = KANUtil.getMappings( request.getLocale(), "def.table.type" );
      this.roles = KANUtil.getMappings( request.getLocale(), "def.table.role" );
   }

   @Override
   public void reset()
   {
      this.nameZH = "";
      this.nameEN = "";
      this.tableType = "0";
      this.tableIndex = "0";
      this.accessAction = "";
      this.accessName = "";
      this.accessTempName = "";
      this.linkedTableIds = "";
      this.canManager = "0";
      this.canList = "0";
      this.canSearch = "0";
      this.canReport = "0";
      this.canImport = "0";
      this.className = "";
      this.description = "";
      this.moduleType = "";
      super.setStatus( "0" );
      super.setCorpId( "" );
   }

   @Override
   public void update( final Object object )
   {
      final TableVO tableVO = ( TableVO ) object;
      this.nameZH = tableVO.getNameZH();
      this.nameEN = tableVO.getNameEN();
      this.moduleType = tableVO.getModuleType();
      this.tableType = tableVO.getTableType();
      this.tableIndex = tableVO.getTableIndex();
      this.accessAction = tableVO.getAccessAction();
      this.accessName = tableVO.getAccessName();
      this.accessTempName = tableVO.getAccessTempName();
      this.linkedTableIds = tableVO.getLinkedTableIds();
      this.canManager = tableVO.getCanManager();
      this.canList = tableVO.getCanList();
      this.canSearch = tableVO.getCanSearch();
      this.canReport = tableVO.getCanReport();
      this.canImport = tableVO.getCanImport();
      this.className = tableVO.getClassName();
      this.description = tableVO.getDescription();
      super.setRole( tableVO.getRole() );
      super.setStatus( tableVO.getStatus() );
      super.setModifyDate( new Date() );
      super.setCorpId( tableVO.getCorpId() );
   }

   // 解译数据词典类型
   public String getDecodeTableType()
   {
      return decodeField( tableType, tableTypes );
   }

   // 解译访问权限
   public String getDecodeRole()
   {
      if ( KANUtil.filterEmpty( super.getRole() ) != null && roles != null && roles.size() > 0 )
      {
         for ( MappingVO mappingVO : roles )
         {
            if ( mappingVO != null && mappingVO.getMappingId() != null && mappingVO.getMappingId().equals( super.getRole() ) )
            {
               return mappingVO.getMappingValue();
            }
         }
      }

      return "";
   }

   public String getTableId()
   {
      return tableId;
   }

   public void setTableId( String tableId )
   {
      this.tableId = tableId;
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

   public String getTableType()
   {
      return tableType;
   }

   public void setTableType( String tableType )
   {
      this.tableType = tableType;
   }

   public String getTableIndex()
   {
      return tableIndex;
   }

   public void setTableIndex( String tableIndex )
   {
      this.tableIndex = tableIndex;
   }

   public String getAccessAction()
   {
      return accessAction;
   }

   public void setAccessAction( String accessAction )
   {
      this.accessAction = accessAction;
   }

   public final String getAccessName()
   {
      return accessName;
   }

   public final void setAccessName( String accessName )
   {
      this.accessName = accessName;
   }

   public String getClassName()
   {
      return className;
   }

   public void setClassName( String className )
   {
      this.className = className;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public List< MappingVO > getTableTypes()
   {
      return tableTypes;
   }

   public void setTableTypes( List< MappingVO > tableTypes )
   {
      this.tableTypes = tableTypes;
   }

   public final List< MappingVO > getRoles()
   {
      return roles;
   }

   public final void setRoles( List< MappingVO > roles )
   {
      this.roles = roles;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( tableId );
   }

   public String getAccessTempName()
   {
      return accessTempName;
   }

   public void setAccessTempName( String accessTempName )
   {
      this.accessTempName = accessTempName;
   }

   public String getComments()
   {
      return comments;
   }

   public void setComments( String comments )
   {
      this.comments = comments;
   }

   public String getCanManager()
   {
      return canManager;
   }

   public void setCanManager( String canManager )
   {
      this.canManager = canManager;
   }

   public String getCanList()
   {
      return canList;
   }

   public void setCanList( String canList )
   {
      this.canList = canList;
   }

   public String getCanSearch()
   {
      return canSearch;
   }

   public void setCanSearch( String canSearch )
   {
      this.canSearch = canSearch;
   }

   public String getCanReport()
   {
      return canReport;
   }

   public void setCanReport( String canReport )
   {
      this.canReport = canReport;
   }

   public String getCanImport()
   {
      return canImport;
   }

   public void setCanImport( String canImport )
   {
      this.canImport = canImport;
   }

   public String getLinkedTableIds()
   {
      return linkedTableIds;
   }

   public void setLinkedTableIds( String linkedTableIds )
   {
      this.linkedTableIds = linkedTableIds;
   }

   public String getModuleType()
   {
      return moduleType;
   }

   public void setModuleType( String moduleType )
   {
      this.moduleType = moduleType;
   }

   public List< MappingVO > getModuleTypes()
   {
      return moduleTypes;
   }

   public void setModuleTypes( List< MappingVO > moduleTypes )
   {
      this.moduleTypes = moduleTypes;
   }

}
