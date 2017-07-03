package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * 封装System固有及Account对应的Table对象
 * 
 * @author Kevin
 */
public class TableDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -6520497069711639943L;

   // 当前Table对象
   private TableVO tableVO = new TableVO();

   // 当前Table所包含的字段（系统固有和用户自定义） - 按照字段组打包
   private List< ColumnGroupDTO > columnGroupDTOs = new ArrayList< ColumnGroupDTO >();

   // 当前Table对应的列表
   private List< ListDTO > listDTOs = new ArrayList< ListDTO >();

   // 当前Table下的所有报表
   private List< ReportDTO > reportDTOs = new ArrayList< ReportDTO >();

   // 当前Table下的所有页面
   private List< ManagerDTO > managerDTOs = new ArrayList< ManagerDTO >();

   // 当前Table子表及关联关系
   private List< TableRelationVO > tableRelationVOs = new ArrayList< TableRelationVO >();

   // 当前Table子表及关联关系 简化对象减小页面开销
   private List< TableRelationSubVO > tableRelationSubVOs = new ArrayList< TableRelationSubVO >();

   public List< TableRelationSubVO > getTableRelationSubVOs()
   {
      tableRelationSubVOs = new ArrayList< TableRelationSubVO >();
      if ( tableRelationVOs != null && tableRelationVOs.size() > 0 )
      {
         for ( TableRelationVO tableRelationVO : tableRelationVOs )
            tableRelationSubVOs.add( tableRelationVO.getTableRelationSubVO() );
      }
      return tableRelationSubVOs;
   }

   public void setTableRelationSubVOs( List< TableRelationSubVO > tableRelationSubVOs )
   {
      this.tableRelationSubVOs = tableRelationSubVOs;
   }

   public TableDTO()
   {
      // Table初始化，添加默认的字段组
      columnGroupDTOs.add( new ColumnGroupDTO() );
   }

   public TableVO getTableVO()
   {
      return tableVO;
   }

   public void setTableVO( TableVO tableVO )
   {
      this.tableVO = tableVO;
   }

   public List< ColumnGroupDTO > getColumnGroupDTOs()
   {
      return columnGroupDTOs;
   }

   public void setColumnGroupDTOs( List< ColumnGroupDTO > columnGroupDTOs )
   {
      this.columnGroupDTOs = columnGroupDTOs;
   }

   public final List< ListDTO > getListDTOs()
   {
      return listDTOs;
   }

   public final void setListDTOs( List< ListDTO > listDTOs )
   {
      this.listDTOs = listDTOs;
   }

   public List< ReportDTO > getReportDTOs()
   {
      return reportDTOs;
   }

   public void setReportDTOs( List< ReportDTO > reportDTOs )
   {
      this.reportDTOs = reportDTOs;
   }

   public List< TableRelationVO > getTableRelationVOs()
   {
      return tableRelationVOs;
   }

   public void setTableRelationVOs( List< TableRelationVO > tableRelationVOs )
   {
      this.tableRelationVOs = tableRelationVOs;
   }

   public final List< ManagerDTO > getManagerDTOs()
   {
      return managerDTOs;
   }

   public final void setManagerDTOs( List< ManagerDTO > managerDTOs )
   {
      this.managerDTOs = managerDTOs;
   }

   // 获取默认的ColumnGroupDTO，未分组的字段都放在此默认组中
   public ColumnGroupDTO getDefaultColumnGroupDTO()
   {
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            if ( columnGroupDTO.getColumnGroupVO() == null )
            {
               return columnGroupDTO;
            }
         }
      }

      return null;
   }

   // 遍历寻找目标DTO，如果找不到则新建DTO对象
   public ColumnGroupDTO getColumnGroupDTO( final ColumnGroupVO columnGroupVO )
   {
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            if ( columnGroupVO != null && columnGroupDTO.getColumnGroupVO() != null && columnGroupDTO.getColumnGroupVO().getGroupId() != null
                  && columnGroupDTO.getColumnGroupVO().getGroupId().equals( columnGroupVO.getGroupId() ) )
            {
               return columnGroupDTO;
            }
         }
      }

      final ColumnGroupDTO columnGroupDTO = new ColumnGroupDTO();
      columnGroupDTO.setColumnGroupVO( columnGroupVO );
      columnGroupDTOs.add( columnGroupDTO );

      return columnGroupDTO;
   }

   // 获取Columns
   public List< MappingVO > getColumns( final String localeLanguage )
   {
      // 初始化返回值对象
      final List< MappingVO > columns = new ArrayList< MappingVO >();

      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  // 初始化MappingVO对象
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( columnVO.getColumnId() );

                  if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( columnVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( columnVO.getNameEN() );
                  }

                  columns.add( mappingVO );
               }
            }
         }
      }

      return columns;
   }

   // 获取Columns 是否是数据库定义表
   public List< MappingVO > getCanImportColumns( final String localeLanguage, final String corpId )
   {
      // 初始化返回值对象
      final List< MappingVO > columns = new ArrayList< MappingVO >();

      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                        && ( KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                              || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                              && KANUtil.filterEmpty( columnVO.getCorpId() ) != null && KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnVO.getCorpId() ) ) ) ) )
                  {
                     if ( KANUtil.filterEmpty( columnVO.getCanImport() ) != null && KANUtil.filterEmpty( columnVO.getCanImport() ).equals( "1" ) )
                     {
                        // 初始化MappingVO对象
                        final MappingVO mappingVO = new MappingVO();
                        mappingVO.setMappingId( columnVO.getColumnId() );

                        if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                        {
                           mappingVO.setMappingValue( columnVO.getNameZH() );
                        }
                        else
                        {
                           mappingVO.setMappingValue( columnVO.getNameEN() );
                        }

                        columns.add( mappingVO );
                     }
                  }
               }
            }
         }
      }

      return columns;
   }

   // 获取Columns 是否是数据库定义表
   public List< MappingVO > getColumns( final String localeLanguage, final String corpId, final String isDBColumn )
   {
      // 初始化返回值对象
      final List< MappingVO > columns = new ArrayList< MappingVO >();

      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null && columnVO.getIsDBColumn().equals( isDBColumn ) && ( KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID ) ) ) )
                  {
                     // 初始化MappingVO对象
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( columnVO.getColumnId() );

                     if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                     {
                        mappingVO.setMappingValue( columnVO.getNameZH() );
                     }
                     else
                     {
                        mappingVO.setMappingValue( columnVO.getNameEN() );
                     }

                     columns.add( mappingVO );
                  }
               }
            }
         }
      }

      return columns;
   }

   public List< MappingVO > getColumns( final String localeLanguage, final String corpId )
   {
      return getColumns( localeLanguage, corpId, false );
   }

   // 获取Columns
   public List< MappingVO > getColumns( final String localeLanguage, final String corpId, final boolean isList )
   {
      // 初始化返回值对象
      final List< MappingVO > columns = new ArrayList< MappingVO >();

      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( KANUtil.filterEmpty( columnVO.getAccountId() ) != null
                        && ( KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                              || ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( columnVO.getCorpId() ) == null ) || ( KANUtil.filterEmpty( corpId ) != null
                              && KANUtil.filterEmpty( columnVO.getCorpId() ) != null && KANUtil.filterEmpty( corpId ).equals( KANUtil.filterEmpty( columnVO.getCorpId() ) ) ) ) )
                  {
                     if ( !isList && KANUtil.filterEmpty( columnVO.getAccountId() ).equals( KANConstants.SUPER_ACCOUNT_ID )
                           && KANUtil.filterEmpty( columnVO.getIsDBColumn() ) != null && KANUtil.filterEmpty( columnVO.getIsDBColumn() ).equals( "2" ) )
                     {
                        continue;
                     }

                     // 初始
                     // 初始化MappingVO对象
                     final MappingVO mappingVO = new MappingVO();
                     mappingVO.setMappingId( columnVO.getColumnId() );

                     if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
                     {
                        mappingVO.setMappingTemp( columnVO.getNameZH() );
                        mappingVO.setMappingValue( KANUtil.filterEmpty( columnVO.getManagerNameZH() ) == null ? columnVO.getNameZH() : columnVO.getManagerNameZH() );
                     }
                     else
                     {
                        mappingVO.setMappingTemp( columnVO.getNameEN() );
                        mappingVO.setMappingValue( KANUtil.filterEmpty( columnVO.getManagerNameEN() ) == null ? columnVO.getNameEN() : columnVO.getManagerNameEN() );
                     }

                     columns.add( mappingVO );
                  }
               }
            }
         }
      }

      return columns;
   }

   // 获取DTO中的自定义ColumnVO
   public List< ColumnVO > getDefineColumnVOs()
   {
      // 初始化返回值对象
      final List< ColumnVO > columnVOs = new ArrayList< ColumnVO >();

      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( KANUtil.filterEmpty( columnVO.getCorpId() ) != null )
                  {
                     columnVOs.add( columnVO );
                  }
               }
            }
         }
      }

      return columnVOs;
   }

   // 获取ColumnGroupDTO
   public ColumnGroupDTO getColumnGroupDTOByGroupId( final String groupId )
   {
      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 && KANUtil.filterEmpty( groupId ) != null )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnGroupVO() != null && columnGroupDTO.getColumnGroupVO().getGroupId().equals( groupId ) )
            {
               return columnGroupDTO;
            }
         }
      }

      return null;
   }

   // 获取ColumnVO
   public ColumnVO getColumnVOByColumnId( final String columnId )
   {
      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 && columnId != null && !columnId.trim().equals( "" ) )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( columnVO.getColumnId() != null && columnVO.getColumnId().equals( columnId ) )
                  {
                     return columnVO;
                  }
               }
            }
         }
      }

      return null;
   }

   // 获取ReportDTO
   public ReportDTO getReportDTOByReportHeaderId( final String reportHeaderId )
   {
      if ( reportDTOs != null && reportDTOs.size() > 0 )
      {
         for ( ReportDTO reportDTO : reportDTOs )
         {
            if ( reportDTO.getReportHeaderVO().getReportHeaderId().equals( reportHeaderId ) )
            {
               return reportDTO;
            }
         }
      }

      return null;
   }

   // 获取ListDTO
   public ListDTO getListDTO( final String accountId, final String corpId ) throws KANException
   {
      if ( listDTOs != null && listDTOs.size() > 0 )
      {
         for ( ListDTO listDTO : listDTOs )
         {
            final ListHeaderVO listHeaderVO = listDTO.getListHeaderVO();

            if ( listHeaderVO != null
                  && ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( listHeaderVO.getCorpId() ) == null
                        && KANUtil.filterEmpty( listHeaderVO.getAccountId() ) != null && KANUtil.filterEmpty( listHeaderVO.getAccountId() ).equals( accountId ) ) || ( KANUtil.filterEmpty( corpId ) != null
                        && KANUtil.filterEmpty( listHeaderVO.getAccountId() ) != null
                        && KANUtil.filterEmpty( listHeaderVO.getAccountId() ).equals( accountId )
                        && KANUtil.filterEmpty( listHeaderVO.getCorpId() ) != null && KANUtil.filterEmpty( listHeaderVO.getCorpId() ).equals( corpId ) ) ) )
            {
               return listDTO;
            }
         }
      }

      return null;
   }

   // 获取ColumnVO
   public List< ColumnVO > getAllColumnVO()
   {
      // 遍历Column Group List
      List< ColumnVO > columnVOList = new ArrayList< ColumnVO >();
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  columnVOList.add( columnVO );
               }
            }
         }
      }

      return columnVOList;
   }

   // 获取ManagerDTO 
   public ManagerDTO getManagerDTO( final String accountId, final String corpId )
   {
      if ( managerDTOs != null && managerDTOs.size() > 0 )
      {
         for ( ManagerDTO managerDTO : managerDTOs )
         {
            final ManagerHeaderVO managerHeaderVO = managerDTO.getManagerHeaderVO();

            if ( managerHeaderVO != null
                  && ( ( KANUtil.filterEmpty( corpId ) == null && KANUtil.filterEmpty( managerHeaderVO.getCorpId() ) == null
                        && KANUtil.filterEmpty( managerHeaderVO.getAccountId() ) != null && KANUtil.filterEmpty( managerHeaderVO.getAccountId() ).equals( accountId ) ) || ( KANUtil.filterEmpty( corpId ) != null
                        && KANUtil.filterEmpty( managerHeaderVO.getAccountId() ) != null
                        && KANUtil.filterEmpty( managerHeaderVO.getAccountId() ).equals( accountId )
                        && KANUtil.filterEmpty( managerHeaderVO.getCorpId() ) != null && KANUtil.filterEmpty( managerHeaderVO.getCorpId() ).equals( corpId ) ) ) )
            {
               return managerDTO;
            }
         }
      }

      return null;
   }

   // 获取ColumnVO
   public ColumnVO getColumnVOByNameDB( final String nameDB )
   {
      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 && nameDB != null && !nameDB.trim().equals( "" ) )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( columnVO.getNameDB() != null && columnVO.getNameDB().equals( nameDB ) )
                  {
                     return columnVO;
                  }
               }
            }
         }
      }

      return null;
   }

   // 获取ColumnVO
   public ColumnVO getColumnVOByName( final String name )
   {
      // 遍历Column Group List
      if ( columnGroupDTOs != null && columnGroupDTOs.size() > 0 && name != null && !name.trim().equals( "" ) )
      {
         for ( ColumnGroupDTO columnGroupDTO : columnGroupDTOs )
         {
            // 遍历Column List
            if ( columnGroupDTO.getColumnVOs() != null && columnGroupDTO.getColumnVOs().size() > 0 )
            {
               for ( ColumnVO columnVO : columnGroupDTO.getColumnVOs() )
               {
                  if ( ( columnVO.getNameZH() != null && columnVO.getNameZH().equals( name ) ) || ( columnVO.getNameEN() != null && columnVO.getNameEN().equals( name ) )
                        || ( columnVO.getManagerNameZH() != null && columnVO.getManagerNameZH().equals( name ) )
                        || ( columnVO.getManagerNameEN() != null && columnVO.getManagerNameEN().equals( name ) ) )
                  {
                     return columnVO;
                  }
               }
            }
         }
      }

      return null;
   }

}
