package com.diliprathore.bo;

import com.diliprathore.bo.exception.BOException;
import com.diliprathore.dao.OrderDAO;
import com.diliprathore.dto.Order;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class OrderBOImplTest {
    @Mock
    OrderDAO dao;
    private OrderBOImpl bo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bo = new OrderBOImpl();
        bo.setDao(dao);
    }

    @Test
    public void placeOrder_shouldCreateOrder() throws SQLException, BOException {
        Order order = new Order();
        when(dao.create(order)).thenReturn(1); // setting expectation here

        boolean result = bo.placeOrder(order);
        assertTrue(result);

        verify(dao).create(order); // verify that dao.create method is actually being called inside the BO 'placeOrder' method
    }

    @Test
    public void placeOrder_shouldFailCreateOrder() throws SQLException, BOException {

        Order order = new Order();
        when(dao.create(order)).thenReturn(0);

        boolean result = bo.placeOrder(order);
        assertFalse(result);

        verify(dao).create(order);
    }

    @Test(expected = BOException.class)
    public void placeOrder_shouldThrow_BOException() throws SQLException, BOException {

        Order order = new Order();
        when(dao.create(order)).thenThrow(SQLException.class);

        boolean result = bo.placeOrder(order);
    }

    @Test
    public void cancelOrder_shouldCancelOrder() throws BOException, SQLException {
        int id = 10;
        Order order = new Order();
        when(dao.read(id)).thenReturn(order);
        when(dao.update(order)).thenReturn(1);

        boolean result = bo.cancelOrder(id);
        assertTrue(result);
        verify(dao).read(id);
        verify(dao).update(order);
    }

    @Test
    public void cancelOrder_shouldNotCancelOrder_OrderDoesNotExist() throws BOException, SQLException {
        int id = 10;
        Order order = new Order();
        when(dao.read(id)).thenReturn(order);
        when(dao.update(order)).thenReturn(0);

        boolean result = bo.cancelOrder(id);
        assertFalse(result);
        verify(dao).read(id);
        verify(dao).update(order);
    }

    @Test(expected = BOException.class)
    public void cancelOrder_readMethodThrowSQLException_BOExceptionShouldBeTriggered() throws BOException, SQLException {
        int id = 10;
        Order order = new Order();
        when(dao.read(id)).thenThrow(SQLException.class);
        bo.cancelOrder(id);

        verify(dao).read(id);
        verify(dao).update(order);
    }

    @Test(expected = BOException.class)
    public void cancelOrder_updateMethodThrowSQLException_BOExceptionShouldBeTriggered() throws BOException, SQLException {
        int id = 10;
        Order order = new Order();
        when(dao.read(id)).thenReturn(order);
        when(dao.update(order)).thenThrow(SQLException.class);
        bo.cancelOrder(id);

        verify(dao).read(id);
        verify(dao).update(order);
    }
}