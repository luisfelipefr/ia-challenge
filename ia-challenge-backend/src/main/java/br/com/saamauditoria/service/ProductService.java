package br.com.saamauditoria.service;

import br.com.saamauditoria.repository.ProductRepository;
import org.springframework.stereotype.Service;
import br.com.saamauditoria.model.Product;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        product.setName(product.getName().trim());
        product.setDescription(product.getDescription().trim());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
