package com.model2.mvc.web.product;

import java.io.File;
import java.io.IOException;
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
	public String addProduct(@ModelAttribute("product") Product product, Model model, HttpServletRequest request) throws Exception{
		
		System.out.println("/product/addProduct    :   POST");
		
		if(FileUpload.isMultipartContent(request)){
			String temDir = "C:\\workspace\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
		
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024*1024*10);
			fileUpload.setSizeThreshold(1024*100); //한번에 100k까지는 메모리에 저장

			if(request.getContentLength() < fileUpload.getSizeMax()) {
				
				StringTokenizer token = null;
				
				//parseRequest()는 FileItem을 포함하고 있는 List타입 리턴
				List fileItemList = fileUpload.parseRequest(request);
				int size = fileItemList.size();
				for(int i=0; i<size; i++) {
					FileItem fileItem = (FileItem)fileItemList.get(i);
						if(fileItem.isFormField()) {
							if(fileItem.getFieldName().equals("manuDate")) {
								
							token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							product.setManuDate(manuDate);
							}
							else if(fileItem.getFieldName().equals("prodName"))
								product.setProdName(fileItem.getString("euc-kr"));
							else if(fileItem.getFieldName().equals("prodDetail"))
								product.setProdDetail(fileItem.getString("euc-kr"));
							else if(fileItem.getFieldName().equals("price"))
								product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						
						}else {  //파일 형식ㅇㅣ면!
							
							if(fileItem.getSize() > 0) {
								int idx = fileItem.getName().lastIndexOf("\\");

								if(idx==-1) {
									idx = fileItem.getName().lastIndexOf("/");
								}
								String fileName = fileItem.getName().substring(idx+1);
								product.setFileName(fileName);
								try {
									File uploadedFile = new File(temDir, fileName);
									fileItem.write(uploadedFile);								
								}catch(IOException e) {
									System.out.println(e);
								}
							}else {
								product.setFileName("../../images/emptu.GIF");
							}	
						}//else
				}//for
				
				
				productService.addProduct(product);
				model.addAttribute("product",product);
		
				
				}else { //업로드하는 파일이 setSizeMax보다 큰 경우
					int overSize = (request.getContentLength()/1000000);
					System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은 " + overSize +"MB입니다');");
					System.out.println("history.back();</script>");
				}
			
		} else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		
		
		
		return "forward:/product/addProduct.jsp";
	} 
	
	
	
	//@RequestMapping("/getProduct")
	@RequestMapping(value="getProduct", method = RequestMethod.GET)
	public String getProduct(@ModelAttribute("product") Product product , Model model ) throws Exception {
		
		System.out.println("/product/getProduct   :   GET");
	
		product = productService.getProduct(product.getProdNo());
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
