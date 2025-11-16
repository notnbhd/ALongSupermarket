package com.supermarket.controller;

import com.supermarket.dao.CustomerStatDAO;
import com.supermarket.model.Customer;
import com.supermarket.model.CustomerStat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;


@WebServlet("/CustomerStatController")
public class CustomerStatController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerStatDAO customerStatDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        customerStatDAO = new CustomerStatDAO();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        Map<Customer, CustomerStat> customerStatMap;
        
        try {
            if (startDateStr != null && endDateStr != null && 
                !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                
                Date startDate = Date.valueOf(startDateStr);
                Date endDate = Date.valueOf(endDateStr);

                customerStatMap = customerStatDAO.getCustomerStat(startDate, endDate);
                
            } else {
                customerStatMap = customerStatDAO.getAllCustomerStat();
            }
            
            request.setAttribute("customerStatMap", customerStatMap);
            request.setAttribute("startDate", startDateStr);
            request.setAttribute("endDate", endDateStr);
            
            request.getRequestDispatcher("/views/ManagementStaff/CustomerStatView.jsp")
                   .forward(request, response);
            
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
            request.getRequestDispatcher("/views/ManagementStaff/CustomerStatView.jsp")
                   .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while retrieving statistics.");
            request.getRequestDispatcher("/views/ManagementStaff/CustomerStatView.jsp")
                   .forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
