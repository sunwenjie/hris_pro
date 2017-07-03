package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.SickLeaveSalaryDetailVO;
import com.kan.base.util.KANException;

public interface SickLeaveSalaryDetailDao
{
   public abstract int countSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryDetailVOsByCondition( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract SickLeaveSalaryDetailVO getSickLeaveSalaryDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO ) throws KANException;

   public abstract int updateSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO ) throws KANException;

   public abstract int deleteSickLeaveSalaryDetail( final SickLeaveSalaryDetailVO SickLeaveSalaryDetailVO ) throws KANException;

   public abstract List< Object > getSickLeaveSalaryDetailVOsByHeaderId( final String headerId ) throws KANException;
   
   public abstract List<Object> getSickLeaveSalaryDetailByHeaderId( String headerId ) throws KANException;
}
