package springapp.repository;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.transaction.jta.SpringJtaSynchronizationAdapter;

import org.springframework.transaction.jta.JtaTransactionManager;

import org.springframework.jdbc.datasource.DataSourceTransactionManager.*;


import springapp.domain.Product;




public class JdbcProductDaoTests /*extends AbstractTransactionalDataSourceSpringContextTests*/

{

    private ProductDao productDao;

    
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:test-context.xml"};
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.deleteFromTables(new String[] {"products"});
        super.executeSqlScript("file:db/load_data.sql", true);
    }

    public void testGetProductList() {
        
        List<Product> products = productDao.getProductList();
        
        assertEquals("wrong number of products?", 3, products.size());
        
    }
    
    public void testSaveProduct() {
        
        List<Product> products = productDao.getProductList();
        
        for (Product p : products) {
            p.setPrice(200.12);
            productDao.saveProduct(p);
        }
        
        List<Product> updatedProducts = productDao.getProductList();
        for (Product p : updatedProducts) {
            assertEquals("wrong price of product?", 200.12, p.getPrice());
        }

    }

}