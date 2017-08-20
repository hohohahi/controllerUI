package com.bottle.business.data.vo;

import java.util.ArrayList;
import java.util.List;

public class RealtimeStasticDataVO {
	long totalNum = 0;
	long totalValidNum = 0;
	long totalInvalidNum = 0;
	double totalMoney = 0.0d;
	private List<ProductionDataVO> dataList = new ArrayList<ProductionDataVO>();
	
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public long getTotalValidNum() {
		return totalValidNum;
	}
	public void setTotalValidNum(long totalValidNum) {
		this.totalValidNum = totalValidNum;
	}
	public long getTotalInvalidNum() {
		return totalInvalidNum;
	}
	public void setTotalInvalidNum(long totalInvalidNum) {
		this.totalInvalidNum = totalInvalidNum;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public List<ProductionDataVO> getDataList() {
		return dataList;
	}
	public void setDataList(List<ProductionDataVO> dataList) {
		this.dataList = dataList;
	}
	
	public void reset() {
		totalNum = 0;
		totalValidNum = 0;
		totalInvalidNum = 0;
		totalMoney = 0.0d;
	}
	@Override
	public String toString() {
		return "RealtimeStasticDataVO [totalNum=" + totalNum + ", totalValidNum=" + totalValidNum + ", totalInvalidNum="
				+ totalInvalidNum + ", totalMoney=" + totalMoney + "]";
	}
}
