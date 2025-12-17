package features.stock;

import core.Connection;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Model for the stock feature
 * Holds the application state and business logic
 */
public class StockModel {
    private Datastore datastore;
    private ObservableList<Product> products;
    private static final double PKR_TO_GBP_RATE = 350.0; // Fixed exchange rate: 1 GBP = 350 PKR
    
    public StockModel() {
        // Get datastore from Connection singleton
        datastore = Connection.getInstance().getDatastore();
        
        // Register Product entity with Morphia
        Connection.getInstance().mapEntity(Product.class);
        
        // Initialize observable list for products
        products = FXCollections.observableArrayList();
        
        // Load initial products
        loadProducts();
    }
    
    /**
     * Load all products from MongoDB
     */
    public void loadProducts() {
        try {
            List<Product> productList = datastore.find(Product.class).iterator().toList();
            products.clear();
            products.addAll(productList);
        } catch (Exception e) {
            System.err.println("Error loading products: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Add a new product to the database
     * @param product The product to add
     * @return true if successful, false otherwise
     */
    public boolean addProduct(Product product) {
        try {
            // Check if SKU already exists
            Product existing = datastore.find(Product.class)
                    .filter("sku", product.getSku())
                    .first();
            
            if (existing != null) {
                return false; // SKU already exists
            }
            
            datastore.save(product);
            loadProducts(); // Reload to update observable list
            return true;
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update an existing product in the database
     * @param product The product to update
     * @return true if successful, false otherwise
     */
    public boolean updateProduct(Product product) {
        try {
            datastore.save(product);
            loadProducts(); // Reload to update observable list
            return true;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a product by SKU
     * @param sku The SKU of the product to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteProduct(String sku) {
        try {
            Query<Product> query = datastore.find(Product.class).filter("sku", sku);
            datastore.delete(query);
            loadProducts(); // Reload to update observable list
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Search products by SKU, name, or brand
     * @param query The search query string
     * @return List of matching products
     */
    public ObservableList<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return products;
        }
        
        String searchTerm = query.trim().toLowerCase();
        ObservableList<Product> results = FXCollections.observableArrayList();
        
        for (Product product : products) {
            if ((product.getSku() != null && product.getSku().toLowerCase().contains(searchTerm)) ||
                (product.getName() != null && product.getName().toLowerCase().contains(searchTerm)) ||
                (product.getBrand() != null && product.getBrand().toLowerCase().contains(searchTerm))) {
                results.add(product);
            }
        }
        
        return results;
    }
    
    /**
     * Get all products
     * @return ObservableList of all products
     */
    public ObservableList<Product> getProducts() {
        return products;
    }
    
    /**
     * Calculate total value in PKR
     * @return Total value in PKR
     */
    public double calculateTotalValuePkr() {
        return products.stream()
                .mapToDouble(Product::getBaseCostPkr)
                .sum();
    }
    
    /**
     * Calculate total value in GBP using fixed exchange rate
     * @return Total value in GBP
     */
    public double calculateTotalValueGbp() {
        return calculateTotalValuePkr() / PKR_TO_GBP_RATE;
    }
    
    /**
     * Calculate total value in PKR for a given list of products
     * @param productList The list of products to calculate for
     * @return Total value in PKR
     */
    public double calculateTotalValuePkr(ObservableList<Product> productList) {
        return productList.stream()
                .mapToDouble(Product::getBaseCostPkr)
                .sum();
    }
    
    /**
     * Calculate total value in GBP for a given list of products
     * @param productList The list of products to calculate for
     * @return Total value in GBP
     */
    public double calculateTotalValueGbp(ObservableList<Product> productList) {
        return calculateTotalValuePkr(productList) / PKR_TO_GBP_RATE;
    }
    
    /**
     * Reset the model state
     */
    public void reset() {
        products.clear();
        loadProducts();
    }
}

