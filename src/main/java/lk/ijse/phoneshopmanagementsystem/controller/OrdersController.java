package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.CustomerBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.PlaceOrderBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.*;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML private TextField txtSearchOrderId;
    @FXML private ComboBox<String> cmbSearchCustomer;
    @FXML private ComboBox<String> cmbSearchStatus;
    @FXML private Button btnSearch;
    @FXML private Button btnRefresh;

    @FXML private TableView<OrderTableDTO> tblOrders;
    @FXML private TableColumn<OrderTableDTO, String> colOrderId;
    @FXML private TableColumn<OrderTableDTO, String> colOrderDate;
    @FXML private TableColumn<OrderTableDTO, String> colCustomerId;
    @FXML private TableColumn<OrderTableDTO, String> colCustomerName;
    @FXML private TableColumn<OrderTableDTO, Double> colTotalAmount;
    @FXML private TableColumn<OrderTableDTO, String> colStatus;
    @FXML private Label lblTotalOrders;

    @FXML private VBox vboxOrderDetails;
    @FXML private VBox vboxNoSelection;
    @FXML private Label lblDetailOrderId;
    @FXML private Label lblDetailOrderDate;
    @FXML private Label lblDetailCustomer;
    @FXML private Label lblDetailTotalAmount;
    @FXML private ComboBox<String> cmbDetailStatus;
    @FXML private Button btnUpdateStatus;
    @FXML private Button btnCancelOrder;

    @FXML private TableView<OrderDetailDTO> tblOrderItems;
    @FXML private TableColumn<OrderDetailDTO, String> colItemId;
    @FXML private TableColumn<OrderDetailDTO, String> colItemDescription;
    @FXML private TableColumn<OrderDetailDTO, String> colItemBrand;
    @FXML private TableColumn<OrderDetailDTO, Integer> colItemQty;
    @FXML private TableColumn<OrderDetailDTO, Double> colItemUnitPrice;
    @FXML private TableColumn<OrderDetailDTO, Double> colItemSubtotal;

    private final ObservableList<OrderTableDTO> ordersList = FXCollections.observableArrayList();
    private final ObservableList<OrderDetailDTO> orderItemsList = FXCollections.observableArrayList();

    //private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    //private final OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();
    //private final OrdersDAOImpl ordersDAO = new OrdersDAOImpl();
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.PlaceOrder);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Customer);


    private String selectedOrderId = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadCustomers();
        loadOrderStatuses();
        loadAllOrders();
        setupTableSelectionListener();

    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblOrders.setItems(ordersList);

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItemSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tblOrderItems.setItems(orderItemsList);
    }

    private void loadCustomers() {
        try {
            ArrayList<CustomerDTO> customersDTO = customerBO.getAllCustomer();

            ObservableList<String> customerIds = FXCollections.observableArrayList();
            customerIds.add("All Customers");

            for (CustomerDTO customer : customersDTO) {
                customerIds.add(customer.getCustomerId());
            }

            cmbSearchCustomer.setItems(customerIds);
            cmbSearchCustomer.setValue("All Customers");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customers: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderStatuses() {
        ObservableList<String> statuses = FXCollections.observableArrayList(
                "All Status", "Pending", "Processing", "Shipped", "Delivered", "Cancelled"
        );
        cmbSearchStatus.setItems(statuses);
        cmbSearchStatus.setValue("All Status");

        ObservableList<String> detailStatuses = FXCollections.observableArrayList(
                "Pending", "Processing", "Shipped", "Delivered", "Cancelled"
        );
        cmbDetailStatus.setItems(detailStatuses);

    }

    private void loadAllOrders() {
        try {
            ArrayList<PlaceOrderDTO> orders = placeOrderBO.getAllOrders();
            ordersList.clear();

            for (PlaceOrderDTO order : orders) {
                String customerName = placeOrderBO.getCustomerName(order.getCustomerId());

                OrderTableDTO tableDTO = new OrderTableDTO(
                        order.getOrderId(),
                        formatDate(order.getOrderDate()),
                        order.getCustomerId(),
                        customerName,
                        order.getTotalAmount(),
                        order.getOrderStatus(),
                        order.getEmployeeId()
                );

                ordersList.add(tableDTO);
            }

            lblTotalOrders.setText("Total: " + ordersList.size() + " orders");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load orders: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void searchOrders(ActionEvent event) {
        String searchOrderId = txtSearchOrderId.getText().trim();
        String searchCustomer = cmbSearchCustomer.getValue();
        String searchStatus = cmbSearchStatus.getValue();

        try {
            ArrayList<PlaceOrderDTO> allOrders = placeOrderBO.getAllOrders();
            ordersList.clear();

            for (PlaceOrderDTO order : allOrders) {
                boolean matches = true;

                if (!searchOrderId.isEmpty()) {
                    if (!order.getOrderId().toLowerCase().contains(searchOrderId.toLowerCase())) {
                        matches = false;
                    }
                }

                if (!searchCustomer.equals("All Customers")) {
                    try {
                        String customerId = String.valueOf(Integer.parseInt(searchCustomer));
                        if (order.getCustomerId() != customerId) {
                            matches = false;
                        }
                    } catch (NumberFormatException e) {
                        matches = false;
                    }
                }

                if (!searchStatus.equals("All Status")) {
                    if (!order.getOrderStatus().equalsIgnoreCase(searchStatus)) {
                        matches = false;
                    }
                }

                if (matches) {
                    String customerName = placeOrderBO.getCustomerName(order.getCustomerId());

                    OrderTableDTO tableDTO = new OrderTableDTO(
                            order.getOrderId(),
                            formatDate(order.getOrderDate()),
                            order.getCustomerId(),
                            customerName,
                            order.getTotalAmount(),
                            order.getOrderStatus(),
                            order.getEmployeeId()
                    );

                    ordersList.add(tableDTO);
                }
            }

            lblTotalOrders.setText("Total: " + ordersList.size() + " orders");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Search failed: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void refreshOrders(ActionEvent event) {
        txtSearchOrderId.clear();
        cmbSearchCustomer.setValue("All Customers");
        cmbSearchStatus.setValue("All Status");
        loadAllOrders();
        selectedOrderId = null;

    }

    private void setupTableSelectionListener() {
        tblOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadOrderDetails(newSelection.getOrderId());
            }
        });
    }

    private void loadOrderDetails(String orderId) {
        try {
            selectedOrderId = orderId;
            PlaceOrderDTO order = placeOrderBO.searchOrders(orderId);

            if (order != null) {
                lblDetailOrderId.setText(order.getOrderId());
                lblDetailOrderDate.setText(formatDate(order.getOrderDate()));

                String customerName = placeOrderBO.getCustomerName(order.getCustomerId());
                lblDetailCustomer.setText(customerName + " (ID: " + order.getCustomerId() + ")");

                lblDetailTotalAmount.setText("Rs. " + String.format("%.2f", order.getTotalAmount()));
                cmbDetailStatus.setValue(order.getOrderStatus());

                orderItemsList.clear();
                ArrayList<OrderDetailDTO> items = placeOrderBO.getOrderDetails(orderId);
                orderItemsList.addAll(items);

                // Show details, hide placeholder
                vboxOrderDetails.setVisible(true);
                vboxNoSelection.setVisible(false);
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load order details: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateOrderStatus(ActionEvent event) {
        if (selectedOrderId == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an order first!");
            return;
        }

        String newStatus = cmbDetailStatus.getValue();
        if (newStatus == null || newStatus.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a status!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Update order " + selectedOrderId + " status to '" + newStatus + "'?",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean updated = placeOrderBO.updateOrders(selectedOrderId, newStatus);

                    if (updated) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Order status updated successfully!");
                        loadAllOrders();
                        loadOrderDetails(selectedOrderId);

                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to update order status!");
                    }

                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void cancelOrder(ActionEvent event) {
        if (selectedOrderId == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an order first!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to CANCEL this order?\n" +
                        "Order ID: " + selectedOrderId + "\n" +
                        "This will restore the stock quantities.",
                ButtonType.YES, ButtonType.NO);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean cancelled = placeOrderBO.cancelOrder(selectedOrderId);

                    if (cancelled) {
                        showAlert(Alert.AlertType.INFORMATION, "Success",
                                "Order cancelled successfully!\nStock quantities have been restored.");
                        loadAllOrders();
                        selectedOrderId = null;
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order!");
                    }

                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void backButtonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }

    private String formatDate(Date date) {
        if (date == null) return "-";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    }