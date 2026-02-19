package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.DashboardBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.DashboardDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label userLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label dateLabel;
    @FXML private Label salesLabel;
    @FXML private Label ordersLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label repairsLabel;

    @FXML private Label timeLabel;

    @FXML private LineChart<String, Number> salesChart;

    @FXML private TableView<Map<String, Object>> topItemsTable;
    @FXML private TableColumn<Map<String, Object>, String> colItemModel;
    @FXML private TableColumn<Map<String, Object>, String> colItemBrand;
    @FXML private TableColumn<Map<String, Object>, Integer> colItemSold;

    @FXML private TableView<Map<String, Object>> recentOrdersTable;
    @FXML private TableColumn<Map<String, Object>, String> colOrderId;
    @FXML private TableColumn<Map<String, Object>, Date> colOrderDate;
    @FXML private TableColumn<Map<String, Object>, String> colCustomerName;
    @FXML private TableColumn<Map<String, Object>, Double> colOrderAmount;

    private Timeline refreshTimeline;
    private Timeline clockTimeline;
    //private final DashboardDAOImpl dashboardDAO = new DashboardDAOImpl();

    DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.DashBoard);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        dateLabel.setText(today.format(formatter));

        setupTables();

        loadDashboardData();

        startAutoRefresh();

        startLiveClock();
    }


    private void startLiveClock() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");

        updateClock(timeFormatter, dateFormatter);

        clockTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    updateClock(timeFormatter, dateFormatter);
                })
        );
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
    }

    private void updateClock(DateTimeFormatter timeFormatter, DateTimeFormatter dateFormatter) {
        LocalDateTime now = LocalDateTime.now();
        timeLabel.setText(now.format(timeFormatter));
    }


    private void startAutoRefresh() {
        refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(30), event -> {
                    System.out.println("Auto refreshing dashboard data...");
                    loadDashboardData();
                })
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }



    public void refreshDashboard() {
        loadDashboardData();
    }

    private void setupTables() {
        // Top Items Table
        colItemModel.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("model")));
        colItemBrand.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("brand")));
        colItemSold.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty((Integer) data.getValue().get("totalSold")).asObject());

        colOrderId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("orderId")));
        colOrderDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>((Date) data.getValue().get("orderDate")));
        colCustomerName.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty((String) data.getValue().get("customerName")));
        colOrderAmount.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty((Double) data.getValue().get("totalAmount")).asObject());
    }

    private void loadDashboardData() {
        try {
            double todaySales = dashboardBO.getTodaySales();
            salesLabel.setText(String.format("Rs. %.2f", todaySales));

            int totalOrders = dashboardBO.getTotalOrdersCount();
            ordersLabel.setText(String.valueOf(totalOrders));

            int lowStock = dashboardBO.getLowStockCount();
            lowStockLabel.setText(String.valueOf(lowStock));

            int pendingRepairs = dashboardBO.getPendingRepairsCount();
            repairsLabel.setText(String.valueOf(pendingRepairs));

            loadSalesChart();

            loadTopSellingItems();

            loadRecentOrders();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard data: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSalesChart() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> salesData = dashboardBO.getLast7DaysSales();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");

        for (Map<String, Object> data : salesData) {
            Date date = (Date) data.get("date");
            Double sales = (Double) data.get("sales");

            String formattedDate = dateFormat.format(date);
            series.getData().add(new XYChart.Data<>(formattedDate, sales));
        }

        salesChart.getData().clear();
        salesChart.getData().add(series);
        salesChart.setStyle("-fx-background-color: transparent;");
    }

    private void loadTopSellingItems() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> topItems = dashboardBO.getTopSellingItems();
        topItemsTable.setItems(FXCollections.observableArrayList(topItems));
    }

    private void loadRecentOrders() throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> recentOrders = dashboardBO.getRecentOrders();
        recentOrdersTable.setItems(FXCollections.observableArrayList(recentOrders));
    }

    @FXML
    private void itemsAction(ActionEvent event) {
        stopTimelines();
        App.setRoot("items");
    }

    @FXML
    private void placeOrdersAction() {
        stopTimelines();
        App.setRoot("placeOrders");
    }

    @FXML
    private void ordersAction() {
        stopTimelines();
        App.setRoot("orders");
    }

    @FXML
    private void customerAction() {
        stopTimelines();
        App.setRoot("customer");
    }

    @FXML
    private void suppliersAction() {
        stopTimelines();
        App.setRoot("suppliers");
    }

    @FXML
    private void repairsAction() {
        stopTimelines();
        App.setRoot("repairs");
    }

    @FXML
    private void userManageAction(ActionEvent event) {
        stopTimelines();
        App.setRoot("userManagement");
    }

    @FXML
    private void reportsAction() {
        stopTimelines();
        App.setRoot("reports");
    }

    @FXML
    private void logout(ActionEvent event) {
        stopTimelines();
        System.out.println("Logout clicked!");
        App.setRoot("login");
    }

    private void stopTimelines() {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        if (clockTimeline != null) {
            clockTimeline.stop();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}