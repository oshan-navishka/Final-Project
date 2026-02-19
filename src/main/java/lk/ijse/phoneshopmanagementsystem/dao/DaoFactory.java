package lk.ijse.phoneshopmanagementsystem.dao;

import lk.ijse.phoneshopmanagementsystem.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory() {}
    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public enum DAOType{
        CUSTOMER,
        ITEM,
        ORDERS,
        ORDER_DETAIL,
        DASHBOARD,
        REPAIR,
        REPORT,
        SUPPLIER,
        USER,
        QUERY

    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case DASHBOARD:
                return new DashboardDAOImpl();
            case REPAIR:
                return new RepairDAOImpl();
            case REPORT:
                return new ReportDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }
}
