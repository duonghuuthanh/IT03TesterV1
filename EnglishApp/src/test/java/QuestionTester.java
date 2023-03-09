/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.JdbcUtils;
import com.dht.services.QuestionService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
        
/**
 *
 * @author admin
 */
public class QuestionTester {
    // Q1 --> content != null
    // Q2 --> co dung 4 lua chon
    // Q3 --> chi lua chon dung
    // Q4 --> bat buoc thuoc danh muc
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    @Test
    public void testAddSuccessful() throws SQLException {
        Question q = new Question("ABC", 1);
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("A", true, q.getId()));
        choices.add(new Choice("B", false, q.getId()));
        choices.add(new Choice("C", false, q.getId()));
        choices.add(new Choice("D", false, q.getId()));
        
        QuestionService s = new QuestionService();
        
        PreparedStatement stm = conn.prepareCall("SELECT * FROM question WHERE id=?");
        stm.setString(1, q.getId());
        ResultSet r = stm.executeQuery();
        
        try {
            s.addQuestion(q, choices);
            
            Assertions.assertNotNull(r.next());
            Assertions.assertEquals(r.getString("id"), q.getId());
            Assertions.assertEquals("ABC", r.getString("content"));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
