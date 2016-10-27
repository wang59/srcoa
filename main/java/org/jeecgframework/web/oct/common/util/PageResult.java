package org.jeecgframework.web.oct.common.util;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class PageResult<E> implements java.io.Serializable{
	private List<E> rows = new ArrayList<E>(); //查询结果
	private long total = 0L; //总记录数
	public List<E> getRows() {
		return rows;
	}
	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
}
