package com.satyam.SpringEcom.controller;

import com.satyam.SpringEcom.model.Product;
import com.satyam.SpringEcom.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProdcuts() {
        log.info("api/products successfully hitted");
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("/product/{id} successfully triggered");
        Product product = productService.getProductById(id);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Long productId){
           Product product= null;
           try {
               product= productService.getProductById(productId);
               return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
           }catch (NullPointerException e){
               log.info("ProductID {} Doesnt contains a image or is null",productId);
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile image) {
        Product savedProduct = null;
        try {
            savedProduct = productService.addProductOrUpdateProduct(product, image);
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestPart Product product,@RequestPart MultipartFile image){
        Product updateProduct=null;
        try{
            updateProduct =productService.addProductOrUpdateProduct(product,image);
            log.info("update product {}",updateProduct);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch(IOException e){
            log.info("error updating the product");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
            Product product =productService.getProductById(id);

            if(product!=null){
                log.info("deleting product with id {}",id);
               productService.deleteProduct(id);
               return new ResponseEntity<>("Delted",HttpStatus.OK);
            }else{
                log.info("No product found with id {}",id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping("product/search")
    public ResponseEntity<List<Product>> searchproduct(@RequestParam String key){
        List<Product> products =productService.searchProducts(key);
        System.out.println("Searching with keyword"+key);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
