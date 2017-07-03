package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.define.ImportDetailDao;
import com.kan.base.dao.inf.define.ImportHeaderDao;
import com.kan.base.dao.inf.define.ListDetailDao;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

public class ItemServiceImpl extends ContextService implements ItemService
{
   private ListHeaderDao listHeaderDao;

   private ListDetailDao listDetailDao;

   private ImportDetailDao importDetailDao;

   private ImportHeaderDao importHeaderDao;

   @Override
   public PagedListHolder getItemVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ItemDao itemDao = ( ItemDao ) getDao();
      pagedListHolder.setHolderSize( itemDao.countItemVOsByCondition( ( ItemVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( itemDao.getItemVOsByCondition( ( ItemVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( itemDao.getItemVOsByCondition( ( ItemVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ItemVO getItemVOByItemId( final String itemId ) throws KANException
   {
      return ( ( ItemDao ) getDao() ).getItemVOByItemId( itemId );
   }

   public int cascadToListDetail( final ItemVO itemVO, final String itemName ) throws KANException
   {

      int returnVal = 0;
      //���ʵ��б� - ��ϸ header
      ListHeaderVO listHeaderVO = new ListHeaderVO();
      listHeaderVO.setStatus( ListHeaderVO.TRUE );
      listHeaderVO.setJavaObjectName( "com.kan.hro.domain.biz.payment.PayslipDTO" );
      listHeaderVO.setParentId( "0" );
      listHeaderVO.setAccountId( itemVO.getAccountId() );
      listHeaderVO.setCorpId( itemVO.getCorpId() );
      List< Object > listHeaderVOs = listHeaderDao.getListHeaderVOsByJavaObjcet( listHeaderVO );
      String listHeaderId = null;
      if ( listHeaderVOs != null && listHeaderVOs.size() > 0 )
      {
         listHeaderId = ( ( ListHeaderVO ) ( listHeaderVOs.get( 0 ) ) ).getListHeaderId();
      }
      else
      {
         return returnVal;
      }
      //�ж�detail �Ƿ��Ѿ�����
      ListDetailVO listDetailVOTemp = new ListDetailVO();
      listDetailVOTemp.setStatus( ListDetailVO.TRUE );
      listDetailVOTemp.setNameZH( itemName );
      listDetailVOTemp.setListHeaderId( listHeaderId );
      List< Object > listDetailVOs = listDetailDao.getListDetailVOsByCondition( listDetailVOTemp );
      //�Ѿ�����ͬ�����޸�����
      if ( listDetailVOs != null && listDetailVOs.size() > 0 )
      {
         for ( Object o : listDetailVOs )
         {
            ListDetailVO listDetailVO = ( ListDetailVO ) o;
            if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && KANUtil.filterEmpty( itemVO.getItemId() ) != null
                  && KANUtil.convertStringToList( listDetailVO.getPropertyName(), "_" ).contains( itemVO.getItemId() ) )
            {
               final ListDetailVO up = ( ListDetailVO ) o;
               up.setNameZH( itemVO.getNameZH() );
               up.setNameEN( itemVO.getNameEN() );
               return returnVal + listDetailDao.updateListDetail( up );
            }
         }
      }

      String itemtype = itemVO.getItemType();
      //1.���� 2����3����4�Ӱ� 7�籣
      if ( "7".equals( itemtype ) )
      {
         //���� ���ʵ��б� - ��ϸ ��ϸ�ж���
         ListDetailVO listDetailVO = new ListDetailVO();
         listDetailVO.setListHeaderId( listHeaderId );
         listDetailVO.setPropertyName( "item_" + itemVO.getItemId() + "_c" );
         listDetailVO.setNameZH( itemVO.getNameZH() + "-��˾" );
         listDetailVO.setNameEN( StringUtils.isNotBlank( itemVO.getNameEN() ) ? itemVO.getNameEN() + "-company" : "company" );
         listDetailVO.setColumnWidthType( "1" );
         listDetailVO.setColumnWidth( "150" );
         listDetailVO.setColumnIndex( "101" );
         listDetailVO.setFontSize( "13" );
         listDetailVO.setIsDecoded( ListDetailVO.FALSE );
         listDetailVO.setIsLinked( ListDetailVO.FALSE );
         listDetailVO.setAlign( "3" );
         listDetailVO.setSort( ListDetailVO.FALSE );
         listDetailVO.setDisplay( ListDetailVO.TRUE );
         listDetailVO.setRemark4( "�����Ŀ��������" );
         listDetailVO.setStatus( ListDetailVO.TRUE );
         listDetailVO.setModifyBy( itemVO.getModifyBy() );
         listDetailVO.setCreateBy( itemVO.getCreateBy() );
         listDetailVO.setSubAction( "" );
         //���빤�ʵ��б� - ��ϸ
         returnVal = listDetailDao.insertListDetail( listDetailVO );
         //���� ���ʵ��б� - ��ϸ ��ϸ�ж���
         listDetailVO = new ListDetailVO();
         listDetailVO.setListHeaderId( listHeaderId );
         listDetailVO.setPropertyName( "item_" + itemVO.getItemId() + "_p" );
         listDetailVO.setNameZH( itemVO.getNameZH() + "-����" );
         listDetailVO.setNameEN( StringUtils.isNotBlank( itemVO.getNameEN() ) ? itemVO.getNameEN() + "-person" : "person" );
         listDetailVO.setColumnWidthType( "1" );
         listDetailVO.setColumnWidth( "150" );
         listDetailVO.setColumnIndex( "101" );
         listDetailVO.setFontSize( "13" );
         listDetailVO.setIsDecoded( ListDetailVO.FALSE );
         listDetailVO.setIsLinked( ListDetailVO.FALSE );
         listDetailVO.setAlign( "3" );
         listDetailVO.setSort( ListDetailVO.FALSE );
         listDetailVO.setDisplay( ListDetailVO.TRUE );
         listDetailVO.setStatus( ListDetailVO.TRUE );
         listDetailVO.setRemark4( "�����Ŀ��������" );
         listDetailVO.setModifyBy( itemVO.getModifyBy() );
         listDetailVO.setCreateBy( itemVO.getCreateBy() );
         listDetailVO.setSubAction( "" );
         //���빤�ʵ��б� - ��ϸ
         returnVal = returnVal + listDetailDao.insertListDetail( listDetailVO );
      }
      else
      {
         //���� ���ʵ��б� - ��ϸ ��ϸ�ж���
         ListDetailVO listDetailVO = new ListDetailVO();
         listDetailVO.setListHeaderId( listHeaderId );
         listDetailVO.setPropertyName( "item_" + itemVO.getItemId() );
         listDetailVO.setNameZH( itemVO.getNameZH() );
         listDetailVO.setNameEN( itemVO.getNameEN() );
         listDetailVO.setColumnWidthType( "1" );
         listDetailVO.setColumnWidth( "100" );
         listDetailVO.setColumnIndex( "101" );
         listDetailVO.setFontSize( "13" );
         listDetailVO.setIsDecoded( ListDetailVO.FALSE );
         listDetailVO.setIsLinked( ListDetailVO.FALSE );
         listDetailVO.setAlign( "3" );
         listDetailVO.setSort( ListDetailVO.FALSE );
         listDetailVO.setStatus( ListDetailVO.TRUE );
         listDetailVO.setDisplay( ListDetailVO.TRUE );
         listDetailVO.setRemark4( "�����Ŀ��������" );
         listDetailVO.setModifyBy( itemVO.getModifyBy() );
         listDetailVO.setCreateBy( itemVO.getCreateBy() );
         listDetailVO.setSubAction( "" );
         //���빤�ʵ��б� - ��ϸ
         returnVal = returnVal + listDetailDao.insertListDetail( listDetailVO );
      }

      return returnVal;
   }

   /**
    * ��ӵ����ʵ�����ϸ
    * @param itemVO
    * @return
    * @throws KANException
    */
   public int cascadToImportList( final ItemVO itemVO, final String itemName ) throws KANException
   {

      int returnVal = 0;
      //���ʵ��б� - ��ϸ header
      ImportHeaderVO importHeaderVO = new ImportHeaderVO();
      importHeaderVO.setStatus( "2" );
      importHeaderVO.setHandlerBeanId( "salaryExcueHandler" );
      importHeaderVO.setParentId( "0" );
      importHeaderVO.setAccountId( itemVO.getAccountId() );
      importHeaderVO.setCorpId( itemVO.getCorpId() );
      List< Object > importHeaderVOs = importHeaderDao.getImportHeaderVOsByCondition( importHeaderVO );
      String headerId = null;
      if ( importHeaderVOs != null && importHeaderVOs.size() > 0 )
      {
         for ( Object object : importHeaderVOs )
         {
            if ( !"0".equals( ( ( ImportHeaderVO ) object ).getParentId() ) )
            {
               headerId = ( ( ImportHeaderVO ) object ).getImportHeaderId();
               break;
            }
         }
      }
      else
      {
         return returnVal;
      }
      //�ж�detail �Ƿ��Ѿ�����
      ImportDetailVO importDetailVOTemp = new ImportDetailVO();
      importDetailVOTemp.setStatus( ListDetailVO.TRUE );
      importDetailVOTemp.setNameZH( itemName );
      importDetailVOTemp.setImportHeaderId( headerId );
      List< Object > importDetailVOs = importDetailDao.getImportDetailVOsByCondition( importDetailVOTemp );
      //�Ѿ�����ͬ�����޸�����
      if ( importDetailVOs != null && importDetailVOs.size() > 0 )
      {
         final ImportDetailVO up = ( ImportDetailVO ) importDetailVOs.get( 0 );
         up.setNameZH( itemVO.getNameZH() );
         up.setNameEN( itemVO.getNameEN() );
         return returnVal + importDetailDao.updateImportDetail( up );
      }

      String itemtype = itemVO.getItemType();
      //1.���� 2����3����4�Ӱ� 7�籣
      if ( "7".equals( itemtype ) )
      {
         //���� ���ʵ����� - ��ϸ  ��˾
         ImportDetailVO importDetailVO = new ImportDetailVO();
         importDetailVO.setImportHeaderId( headerId );
         importDetailVO.setColumnId( "7519" );
         importDetailVO.setNameZH( itemVO.getNameZH() + "-��˾" );
         importDetailVO.setNameEN( StringUtils.isNotBlank( itemVO.getNameEN() ) ? itemVO.getNameEN() + "-(Company)" : "(Company)" );
         importDetailVO.setColumnWidth( "14" );
         importDetailVO.setColumnIndex( "101" );
         importDetailVO.setFontSize( "13" );
         importDetailVO.setIsDecoded( ListDetailVO.FALSE );
         importDetailVO.setAlign( "3" );
         importDetailVO.setAccuracy( "3" );
         importDetailVO.setTempValue( "0.00" );
         importDetailVO.setRemark4( "�����Ŀ��������" );
         importDetailVO.setStatus( ListDetailVO.TRUE );
         importDetailVO.setModifyBy( itemVO.getModifyBy() );
         importDetailVO.setCreateBy( itemVO.getCreateBy() );
         //���빤�ʵ��б� - ��ϸ
         returnVal = importDetailDao.insertImportDetail( importDetailVO );

         //���� ���ʵ����� - ��ϸ ����
         importDetailVO = new ImportDetailVO();
         importDetailVO.setImportHeaderId( headerId );
         importDetailVO.setColumnId( "7520" );
         importDetailVO.setNameZH( itemVO.getNameZH() + "-����" );
         importDetailVO.setNameEN( StringUtils.isNotBlank( itemVO.getNameEN() ) ? itemVO.getNameEN() + "-(Personal)" : "(Personal)" );
         importDetailVO.setColumnWidth( "14" );
         importDetailVO.setColumnIndex( "101" );
         importDetailVO.setFontSize( "13" );
         importDetailVO.setIsDecoded( ListDetailVO.FALSE );
         importDetailVO.setAlign( "3" );
         importDetailVO.setAccuracy( "0" );
         importDetailVO.setTempValue( "0.00" );
         importDetailVO.setRemark4( "�����Ŀ��������" );
         importDetailVO.setStatus( ListDetailVO.TRUE );
         importDetailVO.setModifyBy( itemVO.getModifyBy() );
         importDetailVO.setCreateBy( itemVO.getCreateBy() );

         returnVal = returnVal + importDetailDao.insertImportDetail( importDetailVO );
      }
      else
      {
         //���빤�ʵ��б� - ��ϸ
         ImportDetailVO importDetailVO = new ImportDetailVO();
         importDetailVO.setImportHeaderId( headerId );
         importDetailVO.setColumnId( "7519" );
         importDetailVO.setNameZH( itemVO.getNameZH() );
         importDetailVO.setNameEN( itemVO.getNameEN() );
         importDetailVO.setColumnWidth( "14" );
         importDetailVO.setColumnIndex( "101" );
         importDetailVO.setFontSize( "13" );
         importDetailVO.setIsDecoded( ListDetailVO.FALSE );
         importDetailVO.setAlign( "3" );
         importDetailVO.setAccuracy( "3" );
         importDetailVO.setTempValue( "0.00" );
         importDetailVO.setRemark4( "�����Ŀ��������" );
         importDetailVO.setStatus( ListDetailVO.TRUE );
         importDetailVO.setModifyBy( itemVO.getModifyBy() );
         importDetailVO.setCreateBy( itemVO.getCreateBy() );
         //���빤�ʵ��б� - ��ϸ
         returnVal = importDetailDao.insertImportDetail( importDetailVO );
      }

      return returnVal;
   }

   @Override
   public int insertItem( final ItemVO itemVO ) throws KANException
   {
      int returnVal = 0;
      try
      {
         startTransaction();

         returnVal = ( ( ItemDao ) getDao() ).insertItem( itemVO );

         // �������빤����ϸ��
         if ( KANUtil.filterEmpty( itemVO.getIsCascade() ) != null && "1".equals( itemVO.getIsCascade() ) )
         {
            returnVal = returnVal + cascadToListDetail( itemVO, itemVO.getNameZH() );
            returnVal = returnVal + cascadToImportList( itemVO, itemVO.getNameZH() );
         }

         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return returnVal;
   }

   @Override
   public int updateItem( final ItemVO itemVO ) throws KANException
   {
      int returnVal = 0;
      try
      {
         startTransaction();

         final String itemName = ( ( ItemDao ) getDao() ).getItemVOByItemId( itemVO.getItemId() ).getNameZH();
         returnVal = ( ( ItemDao ) getDao() ).updateItem( itemVO );

         // �������빤����ϸ��
         if ( KANUtil.filterEmpty( itemVO.getIsCascade() ) != null && "1".equals( itemVO.getIsCascade() ) )
         {
            returnVal = returnVal + cascadToListDetail( itemVO, itemName );
            returnVal = returnVal + cascadToImportList( itemVO, itemName );
         }

         this.commitTransaction();
      }
      catch ( Exception e )
      {
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return returnVal;
   }

   @Override
   public int deleteItem( final ItemVO itemVO ) throws KANException
   {
      // ���ɾ��
      final ItemVO modifyObject = ( ( ItemDao ) getDao() ).getItemVOByItemId( itemVO.getItemId() );
      modifyObject.setDeleted( ItemVO.FALSE );
      return ( ( ItemDao ) getDao() ).updateItem( modifyObject );
   }

   @Override
   public List< Object > getItemVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ItemDao ) getDao() ).getItemVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getItemBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return ( ( ItemDao ) getDao() ).getItemBaseViewsByAccountId( accountId );
   }

   public ListHeaderDao getListHeaderDao()
   {
      return listHeaderDao;
   }

   public void setListHeaderDao( ListHeaderDao listHeaderDao )
   {
      this.listHeaderDao = listHeaderDao;
   }

   public ListDetailDao getListDetailDao()
   {
      return listDetailDao;
   }

   public void setListDetailDao( ListDetailDao listDetailDao )
   {
      this.listDetailDao = listDetailDao;
   }

   public ImportDetailDao getImportDetailDao()
   {
      return importDetailDao;
   }

   public void setImportDetailDao( ImportDetailDao importDetailDao )
   {
      this.importDetailDao = importDetailDao;
   }

   public ImportHeaderDao getImportHeaderDao()
   {
      return importHeaderDao;
   }

   public void setImportHeaderDao( ImportHeaderDao importHeaderDao )
   {
      this.importHeaderDao = importHeaderDao;
   }

}
