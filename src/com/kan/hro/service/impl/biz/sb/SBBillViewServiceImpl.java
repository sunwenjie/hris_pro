package com.kan.hro.service.impl.biz.sb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.sb.SBBillViewDao;
import com.kan.hro.domain.biz.sb.SBBillDTO;
import com.kan.hro.domain.biz.sb.SBBillDetailView;
import com.kan.hro.domain.biz.sb.SBBillHeaderView;
import com.kan.hro.service.inf.biz.sb.SBBillViewService;
import com.kan.hro.web.actions.biz.sb.SBBillViewAction;

public class SBBillViewServiceImpl extends ContextService implements SBBillViewService
{

   /*
   @Override
   public PagedListHolder getSBBillViewsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBBillViewDao sbBillViewDao = ( SBBillViewDao ) getDao();
      if ( isPaged )
      {
         if ( ( pagedListHolder.getPage() * pagedListHolder.getPageSize() ) >= sbBillViewDao.getSBBillHeaderViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject() ).size() )
         {
            pagedListHolder.setSource( sbBillViewDao.getSBBillHeaderViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject(), new RowBounds( 1, pagedListHolder.getPageSize() ) ) );
            pagedListHolder.setPage( "0" );
         }
         else
         {
            pagedListHolder.setSource( sbBillViewDao.getSBBillHeaderViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
                  * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
         }
      }
      else
      {
         pagedListHolder.setSource( sbBillViewDao.getSBBillHeaderViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }*/

   @Override
   public PagedListHolder getSBBillDTOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      // 初始化Dao
      final SBBillViewDao sbBillViewDao = ( SBBillViewDao ) getDao();

      SBBillDetailView object = ( SBBillDetailView ) pagedListHolder.getObject();
      SBBillDetailView tempSBBillDetailView = null;

      // 如果不是分组查询
      if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) == null )
      {
         tempSBBillDetailView = object;
      }
      else if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) != null && KANUtil.filterEmpty( object.getGroupColumn() ).equals( "1" ) )
      {
         tempSBBillDetailView = new SBBillDetailView();
         tempSBBillDetailView.setAccountId( object.getAccountId() );
         tempSBBillDetailView.setCorpId( object.getCorpId() );
         tempSBBillDetailView.setGroupColumn( object.getGroupColumn() );
         tempSBBillDetailView.setMonthly( object.getMonthly() );
         tempSBBillDetailView.setClientId( object.getClientId() );
      }
      else if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) != null && KANUtil.filterEmpty( object.getGroupColumn() ).equals( "2" ) )
      {
         tempSBBillDetailView = new SBBillDetailView();
         tempSBBillDetailView.setAccountId( object.getAccountId() );
         tempSBBillDetailView.setCorpId( object.getCorpId() );
         tempSBBillDetailView.setGroupColumn( object.getGroupColumn() );
         tempSBBillDetailView.setMonthly( object.getMonthly() );
         tempSBBillDetailView.setEmployeeSBId( object.getEmployeeSBId() );
      }

      /*  final List< Object > souceAll = sbBillViewDao.getSBBillHeaderViewsByCondition( tempSBBillDetailView );*/

      // 初始化SBBillDetailView List
      List< Object > sbBillDetailViews = new ArrayList< Object >();
      if ( isPaged )
      {
         pagedListHolder.setHolderSize( sbBillViewDao.countSBBillHeaderViewsByCondition( tempSBBillDetailView ) );
         sbBillDetailViews.addAll( sbBillViewDao.getSBBillHeaderViewsByCondition( tempSBBillDetailView, new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         final List< Object > souceAll = sbBillViewDao.getSBBillHeaderViewsByCondition( tempSBBillDetailView );
         pagedListHolder.setHolderSize( souceAll.size() );
         sbBillDetailViews.addAll( souceAll );
      }

      // 获取SBBillDTO的列表配置
      final ListDTO listDTO = KANConstants.getKANAccountConstants( object.getAccountId() ).getListDTOByJavaObjectName( SBBillViewAction.JAVA_OBJECT_NAME, object.getCorpId() );

      // 获取表头配置
      ListDTO subListDTO = null;

      // 初始化item标识
      Map< String, Integer > flags = new HashMap< String, Integer >();

      if ( listDTO != null && listDTO.getSubListDTOs().size() > 0 )
      {
         subListDTO = listDTO.getSubListDTOs().get( 0 );
      }

      // 存在列表表配置
      if ( subListDTO != null && subListDTO.getListDetailVOs() != null && subListDTO.getListDetailVOs().size() > 0 )
      {
         flags = initFlags( subListDTO.getListDetailVOs() );
      }

      if ( sbBillDetailViews != null && sbBillDetailViews.size() > 0 && subListDTO != null && listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
      {
         for ( Object objectSBBillDetailView : sbBillDetailViews )
         {
            // 初始化SBBillDTO
            final SBBillDTO sbBillDTO = new SBBillDTO();

            // 初始化SBBillHeaderView
            final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
            sbBillHeaderView.update( objectSBBillDetailView );
            sbBillHeaderView.setAmountCompany( "0" );
            sbBillHeaderView.setAmountPersonal( "0" );

            sbBillDTO.setSbBillHeaderView( sbBillHeaderView );

            // 初始化SBBillDetailView
            final SBBillDetailView sbBillDetailView = new SBBillDetailView();
            sbBillDetailView.setAccountId( object.getAccountId() );
            sbBillDetailView.setCorpId( object.getCorpId() );
            sbBillDetailView.setGroupColumn( object.getGroupColumn() );
            sbBillDetailView.setMonthly( object.getMonthly() );

            // 如果不是分组查询
            if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) == null )
            {
               sbBillDetailView.setEmployeeId( ( ( SBBillDetailView ) objectSBBillDetailView ).getEmployeeId() );
               sbBillDetailView.setContractId( ( ( SBBillDetailView ) objectSBBillDetailView ).getContractId() );
               sbBillDetailView.setClientId( ( ( SBBillDetailView ) objectSBBillDetailView ).getClientId() );
               sbBillDetailView.setSbSolutionId( ( ( SBBillDetailView ) objectSBBillDetailView ).getSbSolutionId() );
               sbBillDetailView.setSbStatus( ( ( SBBillDetailView ) objectSBBillDetailView ).getSbStatus() );
               sbBillDetailView.setAccountMonthly( ( ( SBBillDetailView ) objectSBBillDetailView ).getAccountMonthly() );
            }
            else if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) != null && KANUtil.filterEmpty( object.getGroupColumn() ).equals( "1" ) )
            {
               sbBillDetailView.setClientId( ( ( SBBillDetailView ) objectSBBillDetailView ).getClientId() );
               if ( KANUtil.filterEmpty( ( ( SBBillDetailView ) objectSBBillDetailView ).getHeaderId() ) != null )
               {
                  sbBillDetailView.setSbStatus( "1,2,3,4,5,6" );
               }
            }
            else if ( KANUtil.filterEmpty( object.getGroupColumn(), "0" ) != null && KANUtil.filterEmpty( object.getGroupColumn() ).equals( "2" ) )
            {
               if ( KANUtil.filterEmpty( ( ( SBBillDetailView ) objectSBBillDetailView ).getHeaderId() ) != null )
               {
                  sbBillDetailView.setSbStatus( "1,2,3,4,5,6" );
               }
               sbBillDetailView.setSbSolutionId( ( ( SBBillDetailView ) objectSBBillDetailView ).getSbSolutionId() );
            }

            // 搜索sbBillDetailViews
            final List< Object > sbBillDetailViewsObject = ( ( SBBillViewDao ) getDao() ).getSBBillDetailViewsByCondition( sbBillDetailView );

            // 存在sbBillDetailViewsObject
            if ( sbBillDetailViewsObject != null && sbBillDetailViewsObject.size() > 0 )
            {
               for ( Object sbBillDetailViewObject : sbBillDetailViewsObject )
               {
                  // 装载SBBillDetailViews
                  sbBillDTO.getSbBillHeaderView().setAmountCompany( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountCompany() )
                        + Double.valueOf( ( ( SBBillDetailView ) sbBillDetailViewObject ).getAmountCompany() ) ) );
                  sbBillDTO.getSbBillHeaderView().setAmountPersonal( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountPersonal() )
                        + Double.valueOf( ( ( SBBillDetailView ) sbBillDetailViewObject ).getAmountPersonal() ) ) );
                  fetchSBBillDetailView( sbBillDTO, ( SBBillDetailView ) sbBillDetailViewObject, subListDTO, flags );
               }
            }

            sbBillDTO.setFlagMap( flags );
            pagedListHolder.getSource().add( sbBillDTO );
         }
      }

      return pagedListHolder;
   }

   // 装载SBBillDTO的SBBillDetailViews
   private void fetchSBBillDetailView( final SBBillDTO sbBillDTO, final SBBillDetailView sbBillDetailView, final ListDTO listDTO, final Map< String, Integer > flags )
         throws KANException
   {
      if ( sbBillDTO.getDetailVOs() != null && sbBillDTO.getDetailVOs().size() == 0 )
      {
         for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
         {
            if ( listDetailVO.filterCompany() )
            {
               if ( listDetailVO.getItemId().equals( sbBillDetailView.getItemId() ) )
               {
                  dealFlags( flags, listDetailVO.getItemId() );
                  sbBillDTO.getSbBillDetailViews().add( sbBillDetailView );
               }
               else
               {
                  final SBBillDetailView tempSbBillDetailView = new SBBillDetailView();
                  tempSbBillDetailView.setAccountId( sbBillDetailView.getAccountId() );
                  tempSbBillDetailView.setItemId( listDetailVO.getItemId() );
                  tempSbBillDetailView.setItemType( "7" );
                  tempSbBillDetailView.setAmountCompany( "0.00" );
                  tempSbBillDetailView.setAmountPersonal( "0.00" );
                  tempSbBillDetailView.setBaseCompany( "0.00" );
                  tempSbBillDetailView.setBasePersonal( "0.00" );
                  tempSbBillDetailView.setRateCompany( "0.00" );
                  tempSbBillDetailView.setRatePersonal( "0.00" );

                  sbBillDTO.getSbBillDetailViews().add( tempSbBillDetailView );
               }
            }
         }
      }
      else if ( sbBillDTO.getDetailVOs() != null && sbBillDTO.getDetailVOs().size() > 0 )
      {
         for ( Object view : sbBillDTO.getDetailVOs() )
         {
            final SBBillDetailView tempSBBillDetailView = ( SBBillDetailView ) view;
            if ( tempSBBillDetailView.getItemId().equals( sbBillDetailView.getItemId() ) )
            {
               // 处理科目标识
               dealFlags( flags, sbBillDetailView.getItemId() );
               tempSBBillDetailView.setAmountCompany( sbBillDetailView.getAmountCompany() );
               tempSBBillDetailView.setAmountPersonal( sbBillDetailView.getAmountPersonal() );
               tempSBBillDetailView.setBaseCompany( sbBillDetailView.getBaseCompany() );
               tempSBBillDetailView.setBasePersonal( sbBillDetailView.getBasePersonal() );
               tempSBBillDetailView.setRateCompany( sbBillDetailView.getRateCompany() );
               tempSBBillDetailView.setRatePersonal( sbBillDetailView.getRatePersonal() );
            }
         }
      }

   }

   // 初始化标识集合
   private Map< String, Integer > initFlags( final List< ListDetailVO > listDetailVOs )
   {
      final Map< String, Integer > flags = new HashMap< String, Integer >();
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         if ( listDetailVO.filterCompany() )
            flags.put( listDetailVO.getItemId(), 2 );
      }
      return flags;
   }

   // 处理标识
   private void dealFlags( final Map< String, Integer > flags, final String itemId )
   {
      if ( flags.containsKey( itemId ) && flags.get( itemId ) == 2 )
         flags.put( itemId, 1 );
   }

   /*
   @Override
   public PagedListHolder getSBBillDTOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SBBillViewDao sbBillViewDao = ( SBBillViewDao ) getDao();

      // 初始化数据源
      final List< Object > sources = new ArrayList< Object >();

      // 如果数据源不为空，默认取之
      if ( pagedListHolder.getSource() != null && !pagedListHolder.getSource().isEmpty() )
      {
         sources.addAll( pagedListHolder.getSource() );
      }
      // 否则从数据库中获取数据
      else
      {
         final List< Object > sbBillViewDetailObjects = sbBillViewDao.getSBBillViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject() );

         if ( sbBillViewDetailObjects != null && sbBillViewDetailObjects.size() > 0 )
         {
            sources.addAll( getSBBillViewDTOs( sbBillViewDetailObjects, ( ( SBBillDetailView ) pagedListHolder.getObject() ).getAccountId(), ( ( SBBillDetailView ) pagedListHolder.getObject() ).getCorpId() ) );
         }
      }

      pagedListHolder.setHolderSize( sources.size() );

      // 重置数据源
      pagedListHolder.setSource( new ArrayList< Object >() );

      // 手动分页
      if ( isPaged )
      {
         // 装载数据
         if ( sources != null && sources.size() > 0 )
         {

            for ( int i = pagedListHolder.getPage() * pagedListHolder.getPageSize(); i < ( ( pagedListHolder.getPage() + 1 ) * pagedListHolder.getPageSize() )
                  && i < sources.size(); i++ )
            {
               pagedListHolder.getSource().add( sources.get( i ) );
            }

         }
      }
      else
      {
         pagedListHolder.setSource( sources );
      }

      return pagedListHolder;
   }

   // 获取SBBillViewDTO 视图转换DTO
   private List< Object > getSBBillViewDTOs( final List< Object > sbBillViewDetailObjects, final String accountId, final String corpId ) throws KANException
   {
      // 初始化返回值对象
      final List< Object > sbBillDTOs = new ArrayList< Object >();

      // 获取SBBillDTO的列表配置
      final ListDTO listDTO = KANConstants.getKANAccountConstants( accountId ).getListDTOByJavaObjectName( SBBillViewAction.JAVA_OBJECT_NAME, corpId );

      // 初始化item标识
      Map< String, Integer > flags = new HashMap< String, Integer >();

      // 获取表头配置
      ListDTO subListDTO = null;

      if ( listDTO != null && listDTO.getSubListDTOs().size() > 0 )
      {
         subListDTO = listDTO.getSubListDTOs().get( 0 );
      }

      // 存在社保单视图和列表配置
      if ( subListDTO != null && subListDTO.getListDetailVOs() != null && subListDTO.getListDetailVOs().size() > 0 && sbBillViewDetailObjects != null
            && sbBillViewDetailObjects.size() > 0 )
      {
         flags = initFlags( subListDTO.getListDetailVOs() );
         for ( Object sbBillDetailView : sbBillViewDetailObjects )
         {
            fetchSBBillDTO( sbBillDTOs, ( SBBillDetailView ) sbBillDetailView, subListDTO.getListDetailVOs(), flags );
         }
      }

      // TODO
      return sbBillDTOs;
   }

   // 初始化标识集合
   private Map< String, Integer > initFlags( final List< ListDetailVO > listDetailVOs )
   {
      final Map< String, Integer > flags = new HashMap< String, Integer >();
      for ( ListDetailVO listDetailVO : listDetailVOs )
      {
         if ( listDetailVO.filterCompany() )
            flags.put( listDetailVO.getItemId(), 2 );
      }
      return flags;
   }

   // 处理标识
   private void dealFlags( final Map< String, Integer > flags, final String itemId )
   {
      if ( flags.containsKey( itemId ) && flags.get( itemId ) == 2 )
         flags.put( itemId, 1 );
   }

   // 装载SBBillDTO
   private void fetchSBBillDTO( final List< Object > sbBillDTOs, final SBBillDetailView sbBillDetailView, final List< ListDetailVO > listDetailVOs,
         final Map< String, Integer > flags ) throws KANException
   {
      // 标识符
      boolean flag = false;

      // SBBillViewDTO存在数据
      if ( sbBillDTOs.size() > 0 )
      {
         for ( Object sbBillDTOObject : sbBillDTOs )
         {
            final SBBillDTO sbBillDTO = ( SBBillDTO ) sbBillDTOObject;
            if ( sbBillDTO.getSbBillHeaderView() != null && sbBillDTO.getSbBillHeaderView().getEmployeeId().equals( sbBillDetailView.getEmployeeId() )
                  && sbBillDTO.getSbBillHeaderView().getContractId().equals( sbBillDetailView.getContractId() )
                  && sbBillDTO.getSbBillHeaderView().getMonthly().equals( sbBillDetailView.getMonthly() )
                  && sbBillDTO.getSbBillHeaderView().getEmployeeSBId().equals( sbBillDetailView.getEmployeeSBId() )
                  && sbBillDTO.getSbBillHeaderView().getStatus().equals( sbBillDetailView.getPseudoStatus() ) )
            {
               flag = true;
               dealFlags( flags, sbBillDetailView.getItemId() );
               fetchSBBillDetailView( sbBillDTO, sbBillDetailView );
               sbBillDTO.setFlagMap( flags );
               break;
            }
         }
      }

      if ( !flag )
      {
         // 实例化SBBillDTO
         final SBBillDTO sbBillDTO = new SBBillDTO();

         // 初始化SBBillHeaderView
         final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
         sbBillHeaderView.update( sbBillDetailView );
         sbBillDTO.setSbBillHeaderView( sbBillHeaderView );

         // 初始化SBBillDetailView列表
         final List< SBBillDetailView > sbBillDetailViews = new ArrayList< SBBillDetailView >();

         for ( ListDetailVO listDetailVO : listDetailVOs )
         {
            if ( listDetailVO.filterCompany() )
            {
               if ( listDetailVO.getItemId().equals( sbBillDetailView.getItemId() ) )
               {
                  dealFlags( flags, listDetailVO.getItemId() );
                  sbBillDetailViews.add( sbBillDetailView );
               }
               else
               {
                  final SBBillDetailView tempSbBillDetailView = new SBBillDetailView();
                  tempSbBillDetailView.setAccountId( sbBillDetailView.getAccountId() );
                  tempSbBillDetailView.setItemId( listDetailVO.getItemId() );
                  tempSbBillDetailView.setItemType( "7" );
                  tempSbBillDetailView.setAmountCompany( "0.00" );
                  tempSbBillDetailView.setAmountPersonal( "0.00" );

                  sbBillDetailViews.add( tempSbBillDetailView );
               }
            }
         }

         sbBillDTO.getSbBillDetailViews().addAll( sbBillDetailViews );
         sbBillDTO.setFlagMap( flags );

         sbBillDTOs.add( sbBillDTO );
      }
   }

   // 装载SBBillDetailView
   private void fetchSBBillDetailView( final SBBillDTO sbBillDTO, final SBBillDetailView sbBillDetailView ) throws KANException
   {
      // 累加社保个人、公司合计
      if ( sbBillDTO != null && sbBillDTO.getSbBillHeaderView() != null )
      {
         sbBillDTO.getSbBillHeaderView().setAmountCompany( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountCompany() )
               + Double.valueOf( sbBillDetailView.getAmountCompany() ) ) );
         sbBillDTO.getSbBillHeaderView().setAmountPersonal( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountPersonal() )
               + Double.valueOf( sbBillDetailView.getAmountPersonal() ) ) );
      }

      // 存在SBBillDetailView列表
      if ( sbBillDTO != null && sbBillDTO.getSbBillDetailViews() != null && sbBillDTO.getSbBillDetailViews().size() > 0 )
      {
         for ( SBBillDetailView temp : sbBillDTO.getSbBillDetailViews() )
         {
            // 合并社保调整数据
            if ( temp.getItemId().equals( sbBillDetailView.getItemId() ) )
            {
               temp.update( sbBillDetailView );
               if ( KANUtil.filterEmpty( temp.getAmountCompany() ) == null )
                  temp.setAmountCompany( sbBillDetailView.getAmountCompany() );
               else
                  temp.setAmountCompany( String.valueOf( Double.valueOf( temp.getAmountCompany() ) + Double.valueOf( sbBillDetailView.getAmountCompany() ) ) );

               if ( KANUtil.filterEmpty( temp.getAmountPersonal() ) == null )
                  temp.setAmountPersonal( sbBillDetailView.getAmountPersonal() );
               else
                  temp.setAmountPersonal( String.valueOf( Double.valueOf( temp.getAmountPersonal() ) + Double.valueOf( sbBillDetailView.getAmountPersonal() ) ) );

               break;
            }
         }
      }
   }
   */

}
