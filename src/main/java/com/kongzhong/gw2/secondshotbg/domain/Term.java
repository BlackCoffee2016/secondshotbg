package com.kongzhong.gw2.secondshotbg.domain;

import java.util.Date;

public class Term {
	private Integer id;
	
	private Integer servings;//份数
	
	private Integer auctionServings;//目前秒杀的数量
	
	private Integer price;//商品价格
	
	private Integer itemId;//商品字典表ID
	
	private Integer number;//某一个商品对应的期数
	
	private Integer st;
	
	private Date createDateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public Integer getAuctionServings() {
		return auctionServings;
	}

	public void setAuctionServings(Integer auctionServings) {
		this.auctionServings = auctionServings;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSt() {
		return st;
	}

	public void setSt(Integer st) {
		this.st = st;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	
}
