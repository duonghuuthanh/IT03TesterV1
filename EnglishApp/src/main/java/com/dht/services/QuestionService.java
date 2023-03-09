/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class QuestionService {
    public boolean addQuestion(Question q, List<Choice> choices) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            
            String sql = "INSERT INTO question(id, content, category_id) VALUES(?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, q.getId());
            stm.setString(2, q.getContent());
            stm.setInt(3, q.getCategoryId());
            int r = stm.executeUpdate();
           
            for (Choice c: choices) {
                sql = "INSERT INTO choice(id, content, is_correct, question_id) VALUES(?, ?, ?, ?)";
                PreparedStatement stm1 = conn.prepareCall(sql);
                stm1.setString(1, c.getId());
                stm1.setString(2, c.getContent());
                stm1.setBoolean(3, c.isCorrect());
                stm1.setString(4, q.getId());
                
                stm1.executeUpdate();
            }
            
            conn.commit();
            return r > 0;
        }
    }
}
