package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANUtil;

/**
 * 封装Report对象 - 包含Header和Detail
 * 
 * @author Kevin
 */
public class ReportDTO implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -732022950611228412L;

	// 封装ReportHeaderVO对象
	private ReportHeaderVO reportHeaderVO = new ReportHeaderVO();

	// 封装ReportDetailVO对象 - 从属当前ReportHeaderVO对象
	private List<ReportDetailVO> reportDetailVOs = new ArrayList<ReportDetailVO>();

	// 封装ReportSearchDetailVO对象 - 从属当前ReportHeaderVO对象
	private List<ReportSearchDetailVO> reportSearchDetailVOs = new ArrayList<ReportSearchDetailVO>();

	// 当前Table子表及关联关系
	private List<TableRelationVO> tableRelationVOs = new ArrayList<TableRelationVO>();
	
	// 当前report子表关联关系
	private List<ReportRelationVO> reportRelationVOs = new ArrayList<ReportRelationVO>();

	// 当前列表使用到的搜索条件
	private SearchDTO searchDTO = new SearchDTO();

	public ReportHeaderVO getReportHeaderVO() {
		return reportHeaderVO;
	}

	public void setReportHeaderVO(ReportHeaderVO reportHeaderVO) {
		this.reportHeaderVO = reportHeaderVO;
	}

	public List<ReportDetailVO> getReportDetailVOs() {
		return reportDetailVOs;
	}

	public void setReportDetailVOs(List<ReportDetailVO> reportDetailVOs) {
		this.reportDetailVOs = reportDetailVOs;
	}

	public SearchDTO getSearchDTO() {
		return searchDTO;
	}

	public void setSearchDTO(SearchDTO searchDTO) {
		this.searchDTO = searchDTO;
	}

	public List<ReportSearchDetailVO> getReportSearchDetailVOs() {
		return reportSearchDetailVOs;
	}

	public void setReportSearchDetailVOs(
			List<ReportSearchDetailVO> reportSearchDetailVOs) {
		this.reportSearchDetailVOs = reportSearchDetailVOs;
	}

	// 获取ReportDetailVO
	public ReportDetailVO getReportDetailVO(final String columnId) {
		if (reportDetailVOs != null && reportDetailVOs.size() > 0) {
			for (ReportDetailVO reportDetailVO : reportDetailVOs) {
				if (reportDetailVO.getColumnId().equals(columnId)) {
					return reportDetailVO;
				}
			}
		}

		return null;
	}

	// 获取ReportSearchDetailVO
	public ReportSearchDetailVO getReportSearchDetailVO(final String columnId) {
		if (reportSearchDetailVOs != null && reportSearchDetailVOs.size() > 0) {
			for (ReportSearchDetailVO reportSearchDetailVO : reportSearchDetailVOs) {
				if (reportSearchDetailVO.getColumnId().equals(columnId)) {
					return reportSearchDetailVO;
				}
			}
		}

		return null;
	}

	// 获取报表名称
	public String getReportName(final HttpServletRequest request) {
		if (reportHeaderVO != null) {
			if (request.getLocale().getLanguage().equalsIgnoreCase("ZH")) {
				return reportHeaderVO.getNameZH();
			} else {
				return reportHeaderVO.getNameEN();
			}
		}

		return "";
	}

	// 获取当前列表是否需要分页
	public boolean isPaged() {
		if (reportHeaderVO != null && reportHeaderVO.getUsePagination() != null
				&& reportHeaderVO.getUsePagination().trim().equals("1")) {
			return true;
		}

		return false;
	}

	// 获取当前列表每页记录数
	public int getPageSize() {
		if (reportHeaderVO != null && reportHeaderVO.getUsePagination() != null
				&& reportHeaderVO.getUsePagination().trim().equals("1")) {
			if (reportHeaderVO.getPageSize() != null
					&& reportHeaderVO.getPageSize().matches("[0-9]*")) {
				return Integer.valueOf(reportHeaderVO.getPageSize());
			}
		}

		return 0;
	}

	// 获取当前列表预读页数
	public int getLoadPages() {
		if (reportHeaderVO != null && reportHeaderVO.getUsePagination() != null
				&& reportHeaderVO.getUsePagination().trim().equals("1")) {
			if (reportHeaderVO.getLoadPages() != null
					&& reportHeaderVO.getLoadPages().matches("[0-9]*")) {
				return Integer.valueOf(reportHeaderVO.getLoadPages());
			}
		}

		return 0;
	}

	// 获取当前报表是否搜索优先
	public boolean isSearchFirst() {
		if (reportHeaderVO != null && reportHeaderVO.getIsSearchFirst() != null
				&& reportHeaderVO.getIsSearchFirst().trim().equals("1")) {
			return true;
		}

		return false;
	}

	// 获取是否直接导出
	public boolean isExportFirst() {
		return (KANUtil.filterEmpty(reportHeaderVO.getExportExcelType()) != null && reportHeaderVO
				.getExportExcelType().trim().equals("2")) ? true : false;
	}

	// 获取是否直接导出&&非搜索优先
	public boolean isExportFirstAndSearchFirst() {
		return (!isSearchFirst() && isExportFirst());
	}

	public List<TableRelationVO> getTableRelationVOs() {
		return tableRelationVOs;
	}

	public void setTableRelationVOs(List<TableRelationVO> tableRelationVOs) {
		this.tableRelationVOs = tableRelationVOs;
	}

	public List<ReportRelationVO> getReportRelationVOs() {
		return reportRelationVOs;
	}

	public void setReportRelationVOs(List<ReportRelationVO> reportRelationVOs) {
		this.reportRelationVOs = reportRelationVOs;
	}
	
	

}
