package com.demo.beans;

public class PageBean {

	private int min;
	private int max;
	private int prevPage;
	private int nextPage;
	private int pageCnt;
	private int currentPage;

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		this.currentPage = currentPage;

		// 전체 페이지 갯수
		pageCnt = contentCnt / contentPageCnt;
		if (contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}

		// 페이징 최솟값, 최댓값
		min = ((currentPage - 1) / contentPageCnt) * contentPageCnt + 1;
		max = min + paginationCnt - 1;

		// 최댓값이 최대 페이지보다 크면 최대 페이지로 설정
		if (max > pageCnt) {
			max = pageCnt;
		}

		// 이전 페이지값, 다음 페이지값
		prevPage = min - 1;
		nextPage = max + 1;

		// 다음 페이지가 최대 페이지보다 크면 최대 페이지로 설정
		if (nextPage > pageCnt) {
			nextPage = pageCnt;
		}
	}

}
