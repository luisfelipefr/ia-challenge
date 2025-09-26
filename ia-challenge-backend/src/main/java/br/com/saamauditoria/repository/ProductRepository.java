package br.com.saamauditoria.repository;

import br.com.saamauditoria.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
