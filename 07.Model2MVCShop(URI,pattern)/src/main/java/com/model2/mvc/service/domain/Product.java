package com.model2.mvc.service.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

//상품정보를 모델링한 Bean
public class Product {
	
	private int prodNo;
	private String prodName;
	private String prodDetail;
	private String manuDate;
	private int price;
	//private String fileName;
	//private MultipartFile file;
	private List<MultipartFile> files;
	private String fileName;
	private Date regDate;
	private String proTranCode;
	private String onSale;
	
	public Product(){
	}
	
	
	
	public List<MultipartFile> getFiles() {
		return files;
	}



	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}



	public String getProTranCode() {
		return proTranCode;
	}
	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}
	
	
	/*
	public MultipartFile getFile() {
		return file;
	}



	public void setFile(MultipartFile file) {
		this.file = file;
	}

*/

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getManuDate() {
		return manuDate;
	}
	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProdDetail() {
		return prodDetail;
	}
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	

	public String getOnSale() {
		return onSale;
	}

	public void setOnSale(String onSale) {
		this.onSale = onSale;
	}

	
	
	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodDetail=" + prodDetail + ", manuDate="
				+ manuDate + ", price=" + price + ", fileName=" + fileName + ", regDate=" + regDate + ", proTranCode="
				+ proTranCode + ", onSale=" + onSale + "]";
	}	
}