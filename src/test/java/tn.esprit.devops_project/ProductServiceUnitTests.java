package tn.esprit.devops_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceUnitTests {
    @Mock
    ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    ProductServiceImpl productService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testAddProduct() {
        Product productToAdd = new Product();
        Stock stock = new Stock();
        Long idStock = 1L;
        stock.setIdStock(idStock);
        when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));
        when(productRepository.save(productToAdd)).thenReturn(productToAdd);

        Product result = productService.addProduct(productToAdd, idStock);

        assertNotNull(result);
        assertEquals(stock, result.getStock());
    }

    @Test
    void testRetrieveProductNotFound() {
        Long productId = 1L;

        // Définition du comportement du mock pour retourner une valeur vide
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Appel de la méthode à tester, qui devrait lancer une exception
        assertThrows(NullPointerException.class, () -> productService.retrieveProduct(productId));
    }
    @Test
    void testRetrieveProductByCategory() {
        // Arrange
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> productsToReturn = new ArrayList<>();
        productsToReturn.add(new Product(1L, "Product 1", 10, 100, category, null));
        productsToReturn.add(new Product(2L, "Product 2", 20, 200, category, null));
        when(productRepository.findByCategory(category)).thenReturn(productsToReturn);

        // Act
        List<Product> result = productService.retrieveProductByCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals(productsToReturn.size(), result.size());
        assertEquals(productsToReturn, result);
    }
    @Test
    void testRetrieveProductStock() {
        // Arrange
        Long stockId = 1L;
        Stock stock = new Stock();
        stock.setIdStock(stockId);

        Product product1 = new Product();
        product1.setIdProduct(1L);
        product1.setTitle("Product 1");
        product1.setStock(stock);

        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setTitle("Product 2");
        product2.setStock(stock);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        // Mocking the behavior of productRepository.findByStockIdStock()
        when(productRepository.findByStockIdStock(stockId)).thenReturn(products);

        // Act
        List<Product> result = productService.retreiveProductStock(stockId);

        // Assert
        assertNotNull(result);
        assertEquals(products.size(), result.size());
        assertEquals(products, result);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

}
