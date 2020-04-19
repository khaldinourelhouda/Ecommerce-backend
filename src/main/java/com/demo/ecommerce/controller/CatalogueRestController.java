package com.demo.ecommerce.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.ecommerce.entity.Product;
import com.demo.ecommerce.repository.ProductRepository;

@CrossOrigin("*")
@RestController
public class CatalogueRestController {
	
	private ProductRepository productRepository;
	
	public CatalogueRestController(ProductRepository productRepository) {
		this.productRepository=productRepository;
	}
	
	@GetMapping(path ="/photoProduct/{id}" , produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto(@PathVariable ("id")Long id ) throws Exception {
		
		Product product  =productRepository.findById(id).get();
		//return Files.readAllBytes(Paths.get("src/main/resources/templates/img/"+product.getPhotoName()));
		return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecom/products/"+product.getPhotoName()));
		
	}
	
	@PostMapping(path = "/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file , @PathVariable Long id)  throws Exception{
		
		Product product  =productRepository.findById(id).get();
		product.setPhotoName(file.getOriginalFilename());
		Files.write(Paths.get(System.getProperty("user.home")+"/ecom/products/"+product.getPhotoName()), file.getBytes());
		productRepository.save(product);
		
	}
	
	

}
