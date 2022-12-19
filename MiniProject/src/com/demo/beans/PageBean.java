package com.demo.beans;

public class PageBean {

	private int min; // 최소 페이지 번호
	private int max; // 최대 페이지 번호
	private int prevPage; // 이전 버튼의 페이지 번호
	private int nextPage; // 다음 버튼의 페이지 번호
	private int pageCnt; // 전체 페이지 수
	private int currentPage; // 현재 페이지 번호

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

	/* 전체 게시글 수, 현재 페이지 번호, 페이지당 게시글의 수, 페이지 버튼의 수 */
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		/* 현재 페이지 번호 */
		this.currentPage = currentPage;

		/* 전체 페이지 수 */
		pageCnt = contentCnt / contentPageCnt;
		if (contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}

		/* 페이지네이션 최솟값과 최댓값 */
		min = ((currentPage - 1) / paginationCnt) * paginationCnt + 1;
		max = min + paginationCnt - 1;

		/* 최댓값이 최대 페이지보다 크면 최대 페이지로 설정 */
		if (max > pageCnt) {
			max = pageCnt;
		}

		/* 이전 페이지값, 다음 페이지값 */
		prevPage = min - 1;
		nextPage = max + 1;

		/* 다음 페이지가 최대 페이지보다 크면 최대 페이지로 설정 */
		if (nextPage > pageCnt) {
			nextPage = pageCnt;
		}
	}

}
