package com.kan.hro.dao.mybatis.impl.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceHeaderDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;

public class SystemInvoiceHeaderDaoImpl extends Context implements SystemInvoiceHeaderDao
{

   @Override
   public int countSystemInvoiceHeaderVOsByCondition(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (Integer)select( "countSystemInvoiceVOsByCondition", systemInvoiceHeaderVO );
   }

   @Override
   public List< Object > getSystemInvoiceHeaderVOsByCondition(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return selectList( "getSystemInvoiceVOsByCondition", systemInvoiceHeaderVO );
   }

   @Override
   public List< Object > getSystemInvoiceHeaderVOsByCondition(final SystemInvoiceHeaderVO systemInvoiceHeaderVO,final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSystemInvoiceVOsByCondition", systemInvoiceHeaderVO ,rowBounds);
   }

   @Override
   public List< Object > getSystemInvoiceHeaderVOsByBatchId(final String batchId ) throws KANException
   {
      return selectList("getSystemInvoiceHeaderVOsByBatchId",batchId);
   }

   @Override
   public int insertSystemInvoiceHeader(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return insert( "insertSystemInvoiceHeader", systemInvoiceHeaderVO );
   }

   @Override
   public int updateSystemInvoiceHeader(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return update( "updateSystemInvoiceHeader",systemInvoiceHeaderVO );
   }

   @Override
   public int deleteSystemInvoiceHeader(final String invoiceId ) throws KANException
   {
      return delete( "deleteSystemInvoiceHeader", invoiceId );
   }

   
   
   @Override
   public int countSubSystemInvoiceHeaderByHeaderId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (Integer)select( "countSubSystemInvoiceHeaderByHeaderId", systemInvoiceHeaderVO );
   }

   @Override
   public List< Object > getSubSystemInvoiceHeaderByHeaderId( SystemInvoiceHeaderVO systemInvoiceHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSubSystemInvoiceHeaderByHeaderId", systemInvoiceHeaderVO, rowBounds);
   }
   
   @Override
   public List< Object > getSubSystemInvoiceHeaderByHeaderId( SystemInvoiceHeaderVO systemInvoiceHeaderVO) throws KANException
   {
      return selectList( "getSubSystemInvoiceHeaderByHeaderId", systemInvoiceHeaderVO);
   }

   @Override
   public List< Object > getComSystemInvoiceHeaderById( SystemInvoiceHeaderVO systemInvoiceHeaderVO, RowBounds rowBounds  ) throws KANException
   {
      return selectList( "getComSystemInvoiceHeaderById", systemInvoiceHeaderVO, rowBounds);
   }
   
   @Override
   public List< Object > getComSystemInvoiceHeaderById( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return selectList( "getComSystemInvoiceHeaderById", systemInvoiceHeaderVO);
   }

   @Override
   public int countComSystemInvoiceHeaderById( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (Integer)select( "countComSystemInvoiceHeaderById", systemInvoiceHeaderVO );
   }

   @Override
   public SystemInvoiceHeaderVO getSystemInvoiceHeaderById( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (SystemInvoiceHeaderVO)select( "getSystemInvoiceHeaderById", systemInvoiceHeaderVO );
   }
   @Override
   public int getMaxInvoiceId(){
      return (Integer)select( "getMaxInvoiceId",null);
   }

   @Override
   public SystemInvoiceHeaderVO getSystemInvoiceHeaderByInvoiceId( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
         return (SystemInvoiceHeaderVO)select( "getSystemInvoiceHeaderByInvoiceId", systemInvoiceHeaderVO );
   }
}
