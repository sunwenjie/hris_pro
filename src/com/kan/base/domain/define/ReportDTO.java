package com.kan.base.domain.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kan.base.util.KANUtil;

/**
 * ��װReport���� - ����Header��Detail
 * 
 * @author Kevin
 */
public class ReportDTO implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -732022950611228412L;

	// ��װReportHeaderVO����
	private ReportHeaderVO reportHeaderVO = new ReportHeaderVO();

	// ��װReportDetailVO���� - ������ǰReportHeaderVO����
	private List<ReportDetailVO> reportDetailVOs = new ArrayList<ReportDetailVO>();

	// ��װReportSearchDetailVO���� - ������ǰReportHeaderVO����
	private List<ReportSearchDetailVO> reportSearchDetailVOs = new ArrayList<ReportSearchDetailVO>();

	// ��ǰTable�ӱ�������ϵ
	private List<TableRelationVO> tableRelationVOs = new ArrayList<TableRelationVO>();
	
	// ��ǰreport�ӱ������ϵ
	private List<ReportRelationVO> reportRelationVOs = new ArrayList<ReportRelationVO>();

	// ��ǰ�б�ʹ�õ�����������
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

	// ��ȡReportDetailVO
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

	// ��ȡReportSearchDetailVO
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

	// ��ȡ��������
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

	// ��ȡ��ǰ�б��Ƿ���Ҫ��ҳ
	public boolean isPaged() {
		if (reportHeaderVO != null && reportHeaderVO.getUsePagination() != null
				&& reportHeaderVO.getUsePagination().trim().equals("1")) {
			return true;
		}

		return false;
	}

	// ��ȡ��ǰ�б�ÿҳ��¼��
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

	// ��ȡ��ǰ�б�Ԥ��ҳ��
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

	// ��ȡ��ǰ�����Ƿ���������
	public boolean isSearchFirst() {
		if (reportHeaderVO != null && reportHeaderVO.getIsSearchFirst() != null
				&& reportHeaderVO.getIsSearchFirst().trim().equals("1")) {
			return true;
		}

		return false;
	}

	// ��ȡ�Ƿ�ֱ�ӵ���
	public boolean isExportFirst() {
		return (KANUtil.filterEmpty(reportHeaderVO.getExportExcelType()) != null && reportHeaderVO
				.getExportExcelType().trim().equals("2")) ? true : false;
	}

	// ��ȡ�Ƿ�ֱ�ӵ���&&����������
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
