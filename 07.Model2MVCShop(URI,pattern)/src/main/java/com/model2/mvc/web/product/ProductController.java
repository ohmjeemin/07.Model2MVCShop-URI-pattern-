package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	
	
	//@RequestMapping("/addProductView")
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProductView() throws Exception {
	
		System.out.println("/product/addProductView   :   GET");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct")
	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product, Model model) throws Exception{
		//첨부파일은 Product에서 처리하지 않으므로 HttpServletRequest를 추가로 받음
		System.out.println("/product/addProduct    :   POST");
		String filesName ="";
		
		if(product.getManuDate()!=null) {
			product.setManuDate(product.getManuDate().replaceAll("-", ""));		
		}
		
		List<MultipartFile> fileList = product.getFiles();
		List<String> fn = new ArrayList();
		
					
		for (int i = 0; i < fileList.size(); i++) {
			
			filesName += fileList.get(i).getOriginalFilename()+",";
			fn.add(fileList.get(i).getOriginalFilename());
		}
		System.out.println(fileList);
		String filePath = "C:\\Users\\user\\git\\repository\\07.Model2MVCShop(URI,pattern)\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles";
		
		for (int i = 0; i < fileList.size(); i++) {
			String fileName = fn.get(i);
			fileList.get(i).transferTo(new File(filePath, fileName));
		}
		
		
		product.setFileName(filesName);

		productService.addProduct(product);
		model.addAttribute("product", product);
		
		
		//MultipartFile file = product.getFile();
		//String fileName = file.getOriginalFilename();
		//String filePath = "C:\\Users\\user\\git\\repository\\07.Model2MVCShop(URI,pattern)\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles";
		//file.transferTo(new File(filePath, fileName));
		
		
		//product.setFileName(fileName);
		//productService.addProduct(product);
		//model.addAttribute("product",product);
		
				
		
		return "forward:/product/addProduct.jsp";
	} 
	
	
	
	//@RequestMapping("/getProduct")
	@RequestMapping(value="getProduct", method = RequestMethod.GET)
	public String getProduct(@ModelAttribute("product") Product product , Model model ) throws Exception {
		
		System.out.println("/product/getProduct   :   GET");
	
		product = productService.getProduct(product.getProdNo());
		String[] fileNameArr = product.getFileName().split(",");
	
		for(String a : fileNameArr) {
			System.out.println(a);
		}
		
		model.addAttribute("fileNameArr",fileNameArr);
		model.addAttribute("product", product);
		
		return "forward:/product/readProduct.jsp";
	}
	
	
	
	//@RequestMapping("/updateProductView")
	@RequestMapping(value = "updateProduct", method =RequestMethod.GET)
	public String updateProductView(@ModelAttribute("product") Product product , Model model ) throws Exception {

		System.out.println("/product/updateProduc  :   GET");
		
		product = productService.getProduct(product.getProdNo());
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}
	
	//@RequestMapping("/updateProduct")
	@RequestMapping(value = "updateProduct", method =RequestMethod.POST)
	public String updateProduct( @ModelAttribute("product") Product product) throws Exception{

		System.out.println("/product/updateProduct    :    POST");
		
	   	productService.updateProduct(product);
	
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
	
	
	
	//@RequestMapping("/listProduct")
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("Search") Search search, @RequestParam("menu") String menu, Model model) throws Exception{

		System.out.println("/product/listProduct     :    POST /  GET");
		//Business Logic
	   	
	   	if(search.getCurrentPage()==0) {
	   		search.setCurrentPage(1);
	   	}
	   	search.setPageSize(pageSize);
	   	
	   	Map<String, Object> map = productService.getProductList(search);
	   	
	  	Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	   	
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu", menu);
		

		return "forward:/product/listProduct.jsp";
	}
	
	
}
