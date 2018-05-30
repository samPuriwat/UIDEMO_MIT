package adminDashBoard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dbUtil.dbConnection;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dashBoardController implements Initializable {
    @FXML
    private Button btnLoad;
    @FXML
    private TableView<Employee> employeeTable;
    //add column from SB
    @FXML
    private TableColumn<Employee,String> idcolum;
    @FXML
    private TableColumn<Employee,String> namecolum;
    @FXML
    private TableColumn<Employee,String> positioncolum;
    @FXML
    private TableColumn<Employee,String> emailcolum;
    @FXML
    private TableColumn<Employee,String> salarycolum;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXTextField searchTxt;

    private dbConnection db;
    private ObservableList<Employee> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();

    }

    @FXML
    private void loadEmployeeData(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            String sql = "select * from employee";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.data.add(new Employee(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.idcolum.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        this.namecolum.setCellValueFactory(new PropertyValueFactory<Employee,String>("name"));
        this.positioncolum.setCellValueFactory(new PropertyValueFactory<Employee,String>("position"));
        this.emailcolum.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        this.salarycolum.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));

        this.employeeTable.setItems(null);
        this.employeeTable.setItems(this.data);

        //Filter Data in TableView
        FilteredList<Employee> filteredData =
                new FilteredList<>(data, e -> true);
        searchTxt.setOnKeyReleased(e -> {
            searchTxt.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        filteredData.setPredicate(Employee -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();
                            if (Employee.getId().contains(newValue)) {
                                return true;
                            } else if
                                    (Employee.getName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if
                                    (Employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            }
                            return false;
                        });
                    });
            SortedList<Employee> sortedData =
                    new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(
                    employeeTable.comparatorProperty());
            employeeTable.setItems(sortedData);

        });

    }

    @FXML
    public void addEmployee(ActionEvent event){
        try {
            Stage addEmployeeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass()
                    .getResource("/adminDashBoard/addEmployee.fxml").openStream());
            addEmployeeController db = loader.getController();

            Scene scene = new Scene(root);
            addEmployeeStage.setScene(scene);
            addEmployeeStage.setTitle("Add Employee");
            addEmployeeStage.initStyle(StageStyle.UNDECORATED);
            addEmployeeStage.initStyle(StageStyle.UTILITY);


            addEmployeeStage.setResizable(false);
            addEmployeeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}//class
