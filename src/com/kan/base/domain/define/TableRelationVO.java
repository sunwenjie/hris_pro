package com.kan.base.domain.define;

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

public class TableRelationVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 5000548700094169869L;

   private String tableRelationId;

   //主表
   private String masterTableId;
   //从表
   private String slaveTableId;

   //从表名称
   private String slaveTableNameZH;

   private String slaveTableNameEN;
   //关联条件
   private String joinOn;

   private String role;

   private String description;

   //left join ,inner join ,right join 
   private String joinType;
   //子表join on 字段
   private String slaveColumn;
   //主表join on 字段
   private String masterColumn;

   /**
    * For Application
    */

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

   private String moduleType;

   private List< MappingVO > tableTypes = new ArrayList< MappingVO >();

   private List< MappingVO > roles = new ArrayList< MappingVO >();

   private List< MappingVO > moduleTypes = new ArrayList< MappingVO >();

   private List< MappingVO > tablelist = new ArrayList< MappingVO >();

   public String getModuleType()
   {
      return moduleType;
   }

   public void setModuleType( String moduleType )
   {
      this.moduleType = moduleType;
   }

   public String getJoinType()
   {
      return joinType;
   }

   public void setJoinType( String joinType )
   {
      this.joinType = joinType;
   }

   public String getSlaveColumn()
   {
      return slaveColumn;
   }

   public void setSlaveColumn( String slaveColumn )
   {
      this.slaveColumn = slaveColumn;
   }

   public String getMasterColumn()
   {
      return masterColumn;
   }

   public void setMasterColumn( String masterColumn )
   {
      this.masterColumn = masterColumn;
   }

   private TableRelationSubVO tableRelationSubVO;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( tableRelationId );
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

   public String getAccessName()
   {
      return accessName;
   }

   public void setAccessName( String accessName )
   {
      this.accessName = accessName;
   }

   public String getAccessTempName()
   {
      return accessTempName;
   }

   public void setAccessTempName( String accessTempName )
   {
      this.accessTempName = accessTempName;
   }

   public String getLinkedTableIds()
   {
      return linkedTableIds;
   }

   public void setLinkedTableIds( String linkedTableIds )
   {
      this.linkedTableIds = linkedTableIds;
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

   public String getClassName()
   {
      return className;
   }

   public void setClassName( String className )
   {
      this.className = className;
   }

   public List< MappingVO > getTableTypes()
   {
      return tableTypes;
   }

   public void setTableTypes( List< MappingVO > tableTypes )
   {
      this.tableTypes = tableTypes;
   }

   public List< MappingVO > getRoles()
   {
      return roles;
   }

   public void setRoles( List< MappingVO > roles )
   {
      this.roles = roles;
   }

   public List< MappingVO > getModuleTypes()
   {
      return moduleTypes;
   }

   public void setModuleTypes( List< MappingVO > moduleTypes )
   {
      this.moduleTypes = moduleTypes;
   }

   public List< MappingVO > getTablelist()
   {
      return tablelist;
   }

   public void setTablelist( List< MappingVO > tablelist )
   {
      this.tablelist = tablelist;
   }

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
      this.moduleTypes = KANUtil.getMappings( this.getLocale(), "sys.module.types" );
      this.tableTypes = KANUtil.getMappings( request.getLocale(), "def.table.type" );
      this.roles = KANUtil.getMappings( request.getLocale(), "def.table.role" );
      this.tablelist = KANConstants.getTables( request.getLocale().getLanguage() );
      super.reset( mapping, request );
   }

   @Override
   public void reset()
   {
      this.masterTableId = "";
      this.slaveTableId = "";
      this.role = "";
      this.joinType = "";
      this.slaveColumn = "";
      this.masterColumn = "";

      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object )
   {
      final TableRelationVO tableRelationVO = ( TableRelationVO ) object;
      this.masterTableId = tableRelationVO.getMasterTableId();
      this.slaveTableId = tableRelationVO.getSlaveTableId();
      this.description = tableRelationVO.getDescription();
      this.joinType = tableRelationVO.getJoinType();
      this.slaveColumn = tableRelationVO.getSlaveColumn();
      this.masterColumn = tableRelationVO.getMasterColumn();
      super.setRole( tableRelationVO.getRole() );
      super.setStatus( tableRelationVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getTableRelationId()
   {
      return tableRelationId;
   }

   public void setTableRelationId( String tableRelationId )
   {
      this.tableRelationId = tableRelationId;
   }

   public String getMasterTableId()
   {
      return masterTableId;
   }

   public void setMasterTableId( String masterTableId )
   {
      this.masterTableId = masterTableId;
   }

   public String getSlaveTableId()
   {
      return slaveTableId;
   }

   public void setSlaveTableId( String slaveTableId )
   {
      this.slaveTableId = slaveTableId;
   }

   public String getRole()
   {
      return role;
   }

   public void setRole( String role )
   {
      this.role = role;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getSlaveTableNameZH()
   {
      return slaveTableNameZH;
   }

   public void setSlaveTableNameZH( String slaveTableNameZH )
   {
      this.slaveTableNameZH = slaveTableNameZH;
   }

   public String getSlaveTableNameEN()
   {
      return slaveTableNameEN;
   }

   public void setSlaveTableNameEN( String slaveTableNameEN )
   {
      this.slaveTableNameEN = slaveTableNameEN;
   }

   public String deMasterTable()
   {
      return slaveTableNameEN;
   }

   public String getJoinOn()
   {
      return joinOn;
   }

   public void setJoinOn( String joinOn )
   {
      this.joinOn = joinOn;
   }

   public String getDecodeMasterTableName()
   {
      return decodeField( masterTableId, tablelist );
   }

   public String getDecodeSlaveTableName()
   {
      return decodeField( slaveTableId, tablelist );
   }

   /**
    * 简化对象减少页面开销
    */
   public TableRelationSubVO getTableRelationSubVO()
   {
      if ( tableRelationSubVO == null )
      {
         tableRelationSubVO = new TableRelationSubVO();
      }

      tableRelationSubVO.setSlaveColumn( slaveColumn );
      tableRelationSubVO.setMasterColumn( masterColumn );
      tableRelationSubVO.setJoinType( joinType );
      tableRelationSubVO.setSlaveTableId( slaveTableId );
      tableRelationSubVO.setSlaveTableNameEN( slaveTableNameEN );
      tableRelationSubVO.setSlaveTableNameZH( slaveTableNameZH );
      tableRelationSubVO.setTableRelationId( tableRelationId );
      return tableRelationSubVO;
   }

   public void setTableRelationSubVO( TableRelationSubVO tableRelationSubVO )
   {
      this.tableRelationSubVO = tableRelationSubVO;
   }

}
