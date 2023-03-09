/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.dht.pojo.Category;
import com.dht.services.CategoryService;
import com.dht.services.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author admin
 */
public class CategoryTester {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    @DisplayName("Kiem tra ten danh muc khong trung nhau")
    public void test01() throws SQLException {
        CategoryService s = new CategoryService();
        List<Category> cates = s.getCategories();
        
        List<String> names = cates.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
        Set<String> testNames = new HashSet<>(names);
        
        Assertions.assertEquals(names.size(), testNames.size());
    }
    
    @Test
    @DisplayName("Kiem tra ten danh muc khac null")
    public void test02() throws SQLException {
        CategoryService s = new CategoryService();
        List<Category> cates = s.getCategories();
        
        long t = cates.stream().filter(c -> c.getName() == null).count();
        
        Assertions.assertTrue(t == 0);
    }
}
