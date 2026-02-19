package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.QueryBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.ReportBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.QueryDAO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.ReportDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ReportController implements Initializable {

    @FXML private ComboBox<Integer> cmbYear;
    @FXML private ComboBox<String> cmbMonth;
    @FXML private Button btnGenerateReport;

    @FXML private Label lblTotalOrders;
    @FXML private Label lblTotalRevenue;
    @FXML private Label lblAverageOrderValue;
    @FXML private Label lblUniqueCustomers;

    @FXML private TableView<Map<String, Object>> tblMonthlyOrders;
    @FXML private TableColumn<Map<String, Object>, String> colOrderId;
    @FXML private TableColumn<Map<String, Object>, Date> colOrderDate;
    @FXML private TableColumn<Map<String, Object>, String> colOrderCustomer;
    @FXML private TableColumn<Map<String, Object>, Double> colOrderTotal;

    @FXML private TableView<Map<String, Object>> tblTopCustomers;
    @FXML private TableColumn<Map<String, Object>, String> colCustomerId;
    @FXML private TableColumn<Map<String, Object>, String> colCustomerName;
    @FXML private TableColumn<Map<String, Object>, Integer> colCustomerOrders;
    @FXML private TableColumn<Map<String, Object>, Double> colCustomerSpent;

    @FXML private Label lblPendingOrders;
    @FXML private Label lblProcessingOrders;
    @FXML private Label lblDeliveredOrders;

    private final String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    //private final ReportDAOImpl reportDAO = new ReportDAOImpl();
    ReportBO reportBO = (ReportBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Report);
    QueryBO queryBO = (QueryBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Query);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupComboBoxes();
        setupTables();
        loadCurrentYearReport();
    }

    private void setupComboBoxes() {
        try {
            List<Integer> years = reportBO.getAvailableYears();
            if (years.isEmpty()) {
                years.add(LocalDate.now().getYear());
            }
            cmbYear.setItems(FXCollections.observableArrayList(years));
            cmbYear.setValue(years.get(0));

            ObservableList<String> months = FXCollections.observableArrayList(
                    "All Year", "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            );
            cmbMonth.setItems(months);
            cmbMonth.setValue("All Year");

        } catch (SQLException e) {
            e.printStackTrace();
            cmbYear.setItems(FXCollections.observableArrayList(LocalDate.now().getYear()));
            cmbYear.setValue(LocalDate.now().getYear());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupTables() {
        // Monthly Orders Table
        colOrderId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("orderId")));
        colOrderDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>((Date) data.getValue().get("orderDate")));
        colOrderCustomer.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("customerName")));
        colOrderTotal.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty((Double) data.getValue().get("totalAmount")).asObject());

        // Top Customers Table
        colCustomerId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("customerId")));
        colCustomerName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("name")));
        colCustomerOrders.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty((Integer) data.getValue().get("orderCount")).asObject());
        colCustomerSpent.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty((Double) data.getValue().get("totalSpent")).asObject());
    }

    @FXML
    private void btnGenerateReportOnAction() {
        Integer year = cmbYear.getValue();
        String monthStr = cmbMonth.getValue();

        if (year == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "කරුණාකර year එකක් select කරන්න!");
            return;
        }

        if (monthStr.equals("All Year")) {
            loadYearlyReport(year);
        } else {
            int month = Arrays.asList(monthNames).indexOf(monthStr) + 1;
            loadMonthlyReport(year, month);
        }
    }

    private void loadCurrentYearReport() {
        int currentYear = LocalDate.now().getYear();
        loadYearlyReport(currentYear);
    }

    private void loadYearlyReport(int year) {
        try {
            Map<String, Object> summary = reportBO.getYearlySummary(year);
            updateSummaryLabels(summary);

            List<Map<String, Object>> yearlyOrders = queryBO.getYearlyOrders(year);
            tblMonthlyOrders.setItems(FXCollections.observableArrayList(yearlyOrders));

            List<Map<String, Object>> topCustomers = queryBO.getTopCustomers(year, 10);
            tblTopCustomers.setItems(FXCollections.observableArrayList(topCustomers));

            loadOrderStatusBreakdown(year);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Report load කරන්න බැරි වුණා!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMonthlyReport(int year, int month) {
        try {
            Map<String, Object> summary = reportBO.getMonthlySummary(year, month);
            updateSummaryLabels(summary);

            List<Map<String, Object>> monthlyOrders = queryBO.getMonthlyOrders(year, month);
            tblMonthlyOrders.setItems(FXCollections.observableArrayList(monthlyOrders));

            List<Map<String, Object>> topCustomers = queryBO.getTopCustomers(year, 10);
            tblTopCustomers.setItems(FXCollections.observableArrayList(topCustomers));

            loadOrderStatusBreakdown(year);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Monthly report load කරන්න බැරි වුණා!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSummaryLabels(Map<String, Object> summary) {
        lblTotalOrders.setText(String.valueOf(summary.get("totalOrders")));
        lblTotalRevenue.setText(String.format("Rs. %.2f", summary.get("totalRevenue")));
        lblAverageOrderValue.setText(String.format("Rs. %.2f", summary.get("averageOrderValue")));
        lblUniqueCustomers.setText(String.valueOf(summary.get("uniqueCustomers")));
    }

    private void loadOrderStatusBreakdown(int year) throws SQLException, ClassNotFoundException {
        Map<String, Integer> statusBreakdown = reportBO.getOrderStatusBreakdown(year);

        if (lblPendingOrders != null)
            lblPendingOrders.setText(String.valueOf(statusBreakdown.getOrDefault("Pending", 0)));
        if (lblProcessingOrders != null)
            lblProcessingOrders.setText(String.valueOf(statusBreakdown.getOrDefault("Processing", 0)));
        if (lblDeliveredOrders != null)
            lblDeliveredOrders.setText(String.valueOf(statusBreakdown.getOrDefault("Delivered", 0)));
    }

    public void backButtonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}