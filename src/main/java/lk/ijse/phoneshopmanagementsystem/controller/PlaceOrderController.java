package lk.ijse.phoneshopmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.phoneshopmanagementsystem.App;
import lk.ijse.phoneshopmanagementsystem.bo.BOFactory;
import lk.ijse.phoneshopmanagementsystem.bo.custom.CustomerBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.ItemBO;
import lk.ijse.phoneshopmanagementsystem.bo.custom.PlaceOrderBO;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.ItemDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.OrdersDAOImpl;
import lk.ijse.phoneshopmanagementsystem.dto.PlaceOrderDTO;
import lk.ijse.phoneshopmanagementsystem.dto.OrderDetailDTO;
import lk.ijse.phoneshopmanagementsystem.dto.CustomerDTO;
import lk.ijse.phoneshopmanagementsystem.dto.ItemDTO;
import lk.ijse.phoneshopmanagementsystem.entity.Customer;
import lk.ijse.phoneshopmanagementsystem.entity.Item;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {

    @FXML private ComboBox<String> cmbCustomerId;
    @FXML private TextField txtCustomerName;
    @FXML private Label lblOrderId;
    @FXML private Label lblOrderDate;
    @FXML private ComboBox<String> cmbOrderStatus;
    @FXML private ComboBox<String> cmbItemId;
    @FXML private TextField txtItemDescription;
    @FXML private TextField txtItemBrand;
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtQtyOnHand;
    @FXML private TextField txtOrderQty;
    @FXML private TableView<OrderDetailDTO> tblOrderDetails;
    @FXML private TableColumn<OrderDetailDTO, String> colItemId;
    @FXML private TableColumn<OrderDetailDTO, String> colDescription;
    @FXML private TableColumn<OrderDetailDTO, Integer> colQty;
    @FXML private TableColumn<OrderDetailDTO, Double> colUnitPrice;
    @FXML private TableColumn<OrderDetailDTO, Double> colSubtotal;
    @FXML private Label lblNetTotal;
    @FXML private Button btnAddToCart;
    @FXML private Button btnPlaceOrder;
    @FXML private Button btnRemoveItem;

    private final ObservableList<OrderDetailDTO> orderDetailsList = FXCollections.observableArrayList();

   ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Item);
   PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.PlaceOrder);
   CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BO_TYPE.Customer);

   OrdersDAOImpl ordersDAO = new OrdersDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        loadCustomerIds();
        loadItemIds();
        loadOrderStatuses();
        setCurrentDate();
        tblOrderDetails.setItems(orderDetailsList);
        setupTableContextMenu();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    }

    private void setCurrentDate() {
        lblOrderDate.setText(new Date().toString());
        lblOrderId.setText("Auto Generated");
    }

    private void loadCustomerIds() {
        try {
            ArrayList<CustomerDTO> customers = customerBO.getAllCustomer();
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (CustomerDTO c : customers) ids.add(c.getCustomerId());
            cmbCustomerId.setItems(ids);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customers: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemIds() {
        try {
            ArrayList<ItemDTO> items = itemBO.getAllItem();
            ObservableList<String> ids = FXCollections.observableArrayList();
            for (ItemDTO i : items) ids.add(i.getItemCode());
            cmbItemId.setItems(ids);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load items: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderStatuses() {
        cmbOrderStatus.setItems(FXCollections.observableArrayList("Pending", "Processing", "Shipped", "Delivered", "Cancelled"));
        cmbOrderStatus.setValue("Pending");
    }

    @FXML
    void customerSelected(ActionEvent event) {
        String id = cmbCustomerId.getValue();
        if (id != null) {
            try {
                CustomerDTO customerDTO = customerBO.searchCustomer(id);
                if (customerDTO != null) txtCustomerName.setText(customerDTO.getName());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void itemSelected(ActionEvent event) {
        String id = cmbItemId.getValue();
        if (id != null) {
            try {
                ItemDTO item = itemBO.searchItem(id);
                if (item != null) {
                    txtItemDescription.setText(item.getDescription());
                    txtItemBrand.setText(item.getBrand());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
                    txtOrderQty.clear();
                    txtOrderQty.requestFocus();
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void addToCart(ActionEvent event) {
        if (cmbItemId.getValue() == null || txtOrderQty.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select item and enter quantity!");
            return;
        }

        try {
            String itemId = cmbItemId.getValue();
            int qty = Integer.parseInt(txtOrderQty.getText());
            int stock = Integer.parseInt(txtQtyOnHand.getText());
            double price = Double.parseDouble(txtUnitPrice.getText());

            if (qty <= 0) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Quantity must be > 0");
                return;
            }
            if (qty > stock) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Not enough stock! Available: " + stock);
                return;
            }

            for (OrderDetailDTO d : orderDetailsList) {
                if (d.getItemId().equals(itemId)) {
                    showAlert(Alert.AlertType.WARNING, "Warning", "Item already in cart!");
                    return;
                }
            }

            OrderDetailDTO detail = new OrderDetailDTO();
            detail.setItemId(itemId);
            detail.setItemDescription(txtItemDescription.getText());
            detail.setItemBrand(txtItemBrand.getText());
            detail.setQuantity(qty);
            detail.setUnitPrice(price);
            detail.setSubtotal(qty * price);

            orderDetailsList.add(detail);
            calculateNetTotal();
            clearItemFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid quantity!");
        }
    }


    @FXML
    void removeItem(ActionEvent event) {
        OrderDetailDTO selected = tblOrderDetails.getSelectionModel().getSelectedItem();
        if (selected != null) {
            orderDetailsList.remove(selected);
            calculateNetTotal();
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select an item to remove!");
        }
    }

    @FXML
    void placeOrder(ActionEvent event) {
        if (cmbCustomerId.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Select a customer!");
            return;
        }
        if (orderDetailsList.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Add items to cart first!");
            return;
        }

        double netTotal = orderDetailsList.stream().mapToDouble(OrderDetailDTO::getSubtotal).sum();

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Place order for Rs. " + String.format("%.2f", netTotal) + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(res -> {

            if (res == ButtonType.YES) {
                try {
                    PlaceOrderDTO orderDTO = new PlaceOrderDTO();
                    orderDTO.setCustomerId(cmbCustomerId.getValue());
                    orderDTO.setOrderDate(new Date());
                    orderDTO.setTotalAmount(netTotal);
                    orderDTO.setOrderStatus(cmbOrderStatus.getValue());
                    orderDTO.setEmployeeId(null); // Change later if login system added
                    orderDTO.setOrderDetails(new ArrayList<>(orderDetailsList));

                    boolean success = placeOrderBO.saveOrders(orderDTO);
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Order placed successfully!");
                        clearAll();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Failed", "Failed to place order!");
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
    void clearOrder(ActionEvent event) {
        new Alert(Alert.AlertType.CONFIRMATION, "Clear cart?", ButtonType.YES, ButtonType.NO)
                .showAndWait()
                .ifPresent(res -> {
                    if (res == ButtonType.YES) clearAll();
                });
    }

    private void calculateNetTotal() {
        double total = orderDetailsList.stream().mapToDouble(OrderDetailDTO::getSubtotal).sum();
        lblNetTotal.setText(String.format("%.2f", total));
    }

    private void clearItemFields() {
        cmbItemId.setValue(null);
        txtItemDescription.clear();
        txtItemBrand.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtOrderQty.clear();
    }

    private void clearAll() {
        cmbCustomerId.setValue(null);
        txtCustomerName.clear();
        cmbOrderStatus.setValue("Pending");
        orderDetailsList.clear();
        calculateNetTotal();
        clearItemFields();
        setCurrentDate();
    }

    private void setupTableContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem remove = new MenuItem("Remove Item");
        remove.setOnAction(e -> removeItem(e));
        menu.getItems().add(remove);
        tblOrderDetails.setContextMenu(menu);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void backButtonAction() {
        App.setRoot(LoginController.getDashboardForRole());
    }

    @FXML
    private void handleOrdermPrint(ActionEvent event) {
        try{
            placeOrderBO.printOrdersReports();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}