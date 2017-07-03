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
      // ��ʼ��Dao
      final SBBillViewDao sbBillViewDao = ( SBBillViewDao ) getDao();

      SBBillDetailView object = ( SBBillDetailView ) pagedListHolder.getObject();
      SBBillDetailView tempSBBillDetailView = null;

      // ������Ƿ����ѯ
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

      // ��ʼ��SBBillDetailView List
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

      // ��ȡSBBillDTO���б�����
      final ListDTO listDTO = KANConstants.getKANAccountConstants( object.getAccountId() ).getListDTOByJavaObjectName( SBBillViewAction.JAVA_OBJECT_NAME, object.getCorpId() );

      // ��ȡ��ͷ����
      ListDTO subListDTO = null;

      // ��ʼ��item��ʶ
      Map< String, Integer > flags = new HashMap< String, Integer >();

      if ( listDTO != null && listDTO.getSubListDTOs().size() > 0 )
      {
         subListDTO = listDTO.getSubListDTOs().get( 0 );
      }

      // �����б������
      if ( subListDTO != null && subListDTO.getListDetailVOs() != null && subListDTO.getListDetailVOs().size() > 0 )
      {
         flags = initFlags( subListDTO.getListDetailVOs() );
      }

      if ( sbBillDetailViews != null && sbBillDetailViews.size() > 0 && subListDTO != null && listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
      {
         for ( Object objectSBBillDetailView : sbBillDetailViews )
         {
            // ��ʼ��SBBillDTO
            final SBBillDTO sbBillDTO = new SBBillDTO();

            // ��ʼ��SBBillHeaderView
            final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
            sbBillHeaderView.update( objectSBBillDetailView );
            sbBillHeaderView.setAmountCompany( "0" );
            sbBillHeaderView.setAmountPersonal( "0" );

            sbBillDTO.setSbBillHeaderView( sbBillHeaderView );

            // ��ʼ��SBBillDetailView
            final SBBillDetailView sbBillDetailView = new SBBillDetailView();
            sbBillDetailView.setAccountId( object.getAccountId() );
            sbBillDetailView.setCorpId( object.getCorpId() );
            sbBillDetailView.setGroupColumn( object.getGroupColumn() );
            sbBillDetailView.setMonthly( object.getMonthly() );

            // ������Ƿ����ѯ
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

            // ����sbBillDetailViews
            final List< Object > sbBillDetailViewsObject = ( ( SBBillViewDao ) getDao() ).getSBBillDetailViewsByCondition( sbBillDetailView );

            // ����sbBillDetailViewsObject
            if ( sbBillDetailViewsObject != null && sbBillDetailViewsObject.size() > 0 )
            {
               for ( Object sbBillDetailViewObject : sbBillDetailViewsObject )
               {
                  // װ��SBBillDetailViews
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

   // װ��SBBillDTO��SBBillDetailViews
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
               // �����Ŀ��ʶ
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

   // ��ʼ����ʶ����
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

   // �����ʶ
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

      // ��ʼ������Դ
      final List< Object > sources = new ArrayList< Object >();

      // �������Դ��Ϊ�գ�Ĭ��ȡ֮
      if ( pagedListHolder.getSource() != null && !pagedListHolder.getSource().isEmpty() )
      {
         sources.addAll( pagedListHolder.getSource() );
      }
      // ��������ݿ��л�ȡ����
      else
      {
         final List< Object > sbBillViewDetailObjects = sbBillViewDao.getSBBillViewsByCondition( ( SBBillDetailView ) pagedListHolder.getObject() );

         if ( sbBillViewDetailObjects != null && sbBillViewDetailObjects.size() > 0 )
         {
            sources.addAll( getSBBillViewDTOs( sbBillViewDetailObjects, ( ( SBBillDetailView ) pagedListHolder.getObject() ).getAccountId(), ( ( SBBillDetailView ) pagedListHolder.getObject() ).getCorpId() ) );
         }
      }

      pagedListHolder.setHolderSize( sources.size() );

      // ��������Դ
      pagedListHolder.setSource( new ArrayList< Object >() );

      // �ֶ���ҳ
      if ( isPaged )
      {
         // װ������
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

   // ��ȡSBBillViewDTO ��ͼת��DTO
   private List< Object > getSBBillViewDTOs( final List< Object > sbBillViewDetailObjects, final String accountId, final String corpId ) throws KANException
   {
      // ��ʼ������ֵ����
      final List< Object > sbBillDTOs = new ArrayList< Object >();

      // ��ȡSBBillDTO���б�����
      final ListDTO listDTO = KANConstants.getKANAccountConstants( accountId ).getListDTOByJavaObjectName( SBBillViewAction.JAVA_OBJECT_NAME, corpId );

      // ��ʼ��item��ʶ
      Map< String, Integer > flags = new HashMap< String, Integer >();

      // ��ȡ��ͷ����
      ListDTO subListDTO = null;

      if ( listDTO != null && listDTO.getSubListDTOs().size() > 0 )
      {
         subListDTO = listDTO.getSubListDTOs().get( 0 );
      }

      // �����籣����ͼ���б�����
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

   // ��ʼ����ʶ����
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

   // �����ʶ
   private void dealFlags( final Map< String, Integer > flags, final String itemId )
   {
      if ( flags.containsKey( itemId ) && flags.get( itemId ) == 2 )
         flags.put( itemId, 1 );
   }

   // װ��SBBillDTO
   private void fetchSBBillDTO( final List< Object > sbBillDTOs, final SBBillDetailView sbBillDetailView, final List< ListDetailVO > listDetailVOs,
         final Map< String, Integer > flags ) throws KANException
   {
      // ��ʶ��
      boolean flag = false;

      // SBBillViewDTO��������
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
         // ʵ����SBBillDTO
         final SBBillDTO sbBillDTO = new SBBillDTO();

         // ��ʼ��SBBillHeaderView
         final SBBillHeaderView sbBillHeaderView = new SBBillHeaderView();
         sbBillHeaderView.update( sbBillDetailView );
         sbBillDTO.setSbBillHeaderView( sbBillHeaderView );

         // ��ʼ��SBBillDetailView�б�
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

   // װ��SBBillDetailView
   private void fetchSBBillDetailView( final SBBillDTO sbBillDTO, final SBBillDetailView sbBillDetailView ) throws KANException
   {
      // �ۼ��籣���ˡ���˾�ϼ�
      if ( sbBillDTO != null && sbBillDTO.getSbBillHeaderView() != null )
      {
         sbBillDTO.getSbBillHeaderView().setAmountCompany( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountCompany() )
               + Double.valueOf( sbBillDetailView.getAmountCompany() ) ) );
         sbBillDTO.getSbBillHeaderView().setAmountPersonal( String.valueOf( Double.valueOf( sbBillDTO.getSbBillHeaderView().getAmountPersonal() )
               + Double.valueOf( sbBillDetailView.getAmountPersonal() ) ) );
      }

      // ����SBBillDetailView�б�
      if ( sbBillDTO != null && sbBillDTO.getSbBillDetailViews() != null && sbBillDTO.getSbBillDetailViews().size() > 0 )
      {
         for ( SBBillDetailView temp : sbBillDTO.getSbBillDetailViews() )
         {
            // �ϲ��籣��������
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
