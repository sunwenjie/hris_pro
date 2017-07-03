package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SickLeaveSalaryHeaderVO;
import com.kan.base.util.KANException;

public interface SickLeaveSalaryHeaderDao
{
   public abstract int countSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryHeaderVOsByCondition( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SickLeaveSalaryHeaderVO getSickLeaveSalaryHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO ) throws KANException;

   public abstract int updateSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO ) throws KANException;

   public abstract int deleteSickLeaveSalaryHeader( final SickLeaveSalaryHeaderVO SickLeaveSalaryHeaderVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryHeaderVOsByAccountId( final String accountId ) throws KANException;
}
