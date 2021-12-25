package com.diliprathore.bo;

import com.diliprathore.bo.exception.BOException;
import com.diliprathore.dao.OrderDAO;
import com.diliprathore.dto.Order;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {
    private OrderDAO dao;

    public void setDao(OrderDAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean placeOrder(Order order) throws BOException {
        try {
            int result = dao.create(order);
            if (result == 0)
                return false;
        } catch (SQLException e) {
            throw new BOException(e);
        }
        return true;
    }

    @Override
    public boolean cancelOrder(int id) throws BOException {
        try {
            Order order = dao.read(id); //Can throw SQLException
            order.setStatus("cancelled");
            int result = dao.update(order); //Can throw SQLException
            if (result == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new BOException(e);
        }
        return true;
    }

    @Override
    public boolean deleteOrder(int id) throws BOException {
        try {
            int result = dao.delete(id);
            if (result == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new BOException(e);
        }
        return true;
    }
}
