package com.quarkdata.data.model.common;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author holy
 *
 */
public class PageBean<T> implements Serializable{
	private static final long serialVersionUID = -3357057038349867138L;
	private int pageNum;
	private int pageSize;
	private int totalRecord;
	private int startIndex;
	private int totalPage;
	private List<T> data;
	public PageBean(Integer pageNum,Integer pageSize,int totalRecord){
		//结果集的总记录数
		this.totalRecord = totalRecord;
		//如果没有提供pageSize则默认为10
		if(pageSize==null) {
			this.pageSize=10;
		}else {
			this.pageSize=pageSize;
		}
		//当查询结果总数为0的时候，也显示为1页
		if(totalRecord==0) {
			this.totalPage=1;
		}else {
			this.totalPage=(this.pageSize-1+this.totalRecord)/this.pageSize;
		}
		//如果没有提供pageNum或者pageNum不合法 则置为1
		if(pageNum==null || pageNum>this.totalPage || pageNum<=0) {
			this.pageNum=1;
		}else {
			this.pageNum=pageNum;
		}
		//结果集中的开始索引 
		this.startIndex=(this.pageNum-1)*this.pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
