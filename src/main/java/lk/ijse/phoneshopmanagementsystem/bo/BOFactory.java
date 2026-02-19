package lk.ijse.phoneshopmanagementsystem.bo;


import lk.ijse.phoneshopmanagementsystem.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory instance;
    private BOFactory(){}
    public static BOFactory getInstance() {
        return instance == null ? instance = new BOFactory() : instance;
    }

    public enum BO_TYPE{
        Customer,
        Item,
        PlaceOrder,
        DashBoard,
        Repair,
        Report,
        Supplier,
        User,
        Query
    }

    public SuperBO getBO(BO_TYPE bo_type){
        switch(bo_type){
            case Customer:
                return new CustomerBOImpl();
            case  Item:
                return new ItemBOImpl();
            case  Supplier:
               return new SupplierBOImpl();
            case DashBoard:
                return new DashBoardBOImpl();
            case User:
                return new UserBOImpl();
            case PlaceOrder:
                return new PlaceOrderBOImpl();
            case Report:
                return new ReportBOImpl();
            case  Repair:
                return new RepairBOImpl();
            case  Query:
                return new QueryBOImpl();
            default:
                return null;
        }
    }
}
