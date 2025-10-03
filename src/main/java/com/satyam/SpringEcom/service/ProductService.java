package com.satyam.SpringEcom.service;

import com.satyam.SpringEcom.model.Product;
import com.satyam.SpringEcom.repository.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        log.info("fetching products inside service layer");
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        log.info("fetching product by their id");
        return productRepo.findById(id).orElse(null);
    }

    public Product addProductOrUpdateProduct(Product product, MultipartFile image) throws IOException {
        log.info("adding  or updating a  product");
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        log.info("Inside Service layer->deleting the product with id {}",id);
        productRepo.deleteById(id);
    }

    public List<Product> searchProducts(String key) {
        return productRepo.searchProducts(key);
    }


//    public void getImageByProductId(Long productId) {
//
//    }

//    public Product updateProduct(Product product, MultipartFile image) throws IOException {
//        product.setImageName(image.getName());
//        product.setImageType(image.getContentType());
//        product.setImageData(image.getBytes());
//
//        return productRepo.save(product);
//    }
}
