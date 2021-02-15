package vo;

import java.math.BigDecimal;

public class BoardVO {
	private BigDecimal id;
	private String title;
	private String content;
	public int getId() {
		return id.intValue();
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
