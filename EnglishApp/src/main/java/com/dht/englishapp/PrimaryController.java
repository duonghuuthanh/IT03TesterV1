package com.dht.englishapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryService;
import com.dht.services.QuestionService;
import com.dht.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class PrimaryController implements Initializable {
    @FXML private TableView<Question> tbQuestions;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextField txtContent;
    @FXML private TextField txtA;
    @FXML private TextField txtB;
    @FXML private TextField txtC;
    @FXML private TextField txtD;
    @FXML private RadioButton rdoA;
    @FXML private RadioButton rdoB;
    @FXML private RadioButton rdoC;
    @FXML private RadioButton rdoD;
    @FXML private TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryService s = new CategoryService();
        try {
            List<Category> cates = s.getCategories();
            this.cbCategories.setItems(FXCollections.observableArrayList(cates));
            
            this.loadTableColumns();
            this.loadQuestions(null);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtSearch.textProperty().addListener(o -> {
            try {
                this.loadQuestions(this.txtSearch.getText());
            } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void addQuestionHandler(ActionEvent evt) {
        Question q = new Question(this.txtContent.getText(), 
                this.cbCategories.getSelectionModel().getSelectedItem().getId());
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(this.txtA.getText(), this.rdoA.isSelected(), q.getId()));
        choices.add(new Choice(this.txtB.getText(), this.rdoB.isSelected(), q.getId()));
        choices.add(new Choice(this.txtC.getText(), this.rdoC.isSelected(), q.getId()));
        choices.add(new Choice(this.txtD.getText(), this.rdoD.isSelected(), q.getId()));
        
        QuestionService s = new QuestionService();
        try {
            s.addQuestion(q, choices);
            this.loadQuestions(null);
            MessageBox.getBox("Question", "Add question successful!!!", 
                    Alert.AlertType.INFORMATION).show();
        } catch (SQLException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
            MessageBox.getBox("Question", "Add question failed!!!", 
                    Alert.AlertType.ERROR).show();
        }
    }
    
    private void loadTableColumns() {
        TableColumn colContent = new TableColumn("Question Content");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(300);
        
        TableColumn colCate = new TableColumn("Category");
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        
        TableColumn colDelete = new TableColumn();
        colDelete.setCellFactory(p -> {
            Button btn = new Button("Xóa");
            
            btn.setOnAction(evt -> {
                Alert confirm = MessageBox.getBox("Question", 
                        "Are you sure to delete this question", 
                        Alert.AlertType.CONFIRMATION);
                confirm.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.OK) {
                        Button b = (Button)evt.getSource();
                        TableCell cell = (TableCell) b.getParent();
                        Question ques = (Question) cell.getTableRow().getItem();
                        
                        QuestionService s = new QuestionService();
                        try {
                            if (s.deleteQuestion(ques.getId()) == true) {
                                MessageBox.getBox("Question", "Delete successful!", Alert.AlertType.INFORMATION).show();
                                this.loadQuestions(null);
                            } else
                                MessageBox.getBox("Question", "Delete failed", Alert.AlertType.INFORMATION).show();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            MessageBox.getBox("Question", "Delete failed", Alert.AlertType.INFORMATION).show();
                            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
            
            TableCell c = new TableCell();
            c.setGraphic(btn);
            return c;
        });
        
        this.tbQuestions.getColumns().addAll(colContent, colCate, colDelete);
    }
    
    private void loadQuestions(String kw) throws SQLException {
        QuestionService s = new QuestionService();
        List<Question> questions = s.getQuestions(kw);
        this.tbQuestions.getItems().clear();
        this.tbQuestions.setItems(FXCollections.observableList(questions));
    }
}
