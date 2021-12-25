package com.diliprathore.bo;

import com.diliprathore.bo.exception.BOException;
import com.diliprathore.dto.Order;

public interface OrderBO {
    boolean placeOrder(Order order) throws BOException;
    boolean cancelOrder(int id) throws BOException;
    boolean deleteOrder(int id) throws BOException;
}
