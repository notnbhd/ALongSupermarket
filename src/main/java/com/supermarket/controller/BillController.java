package com.supermarket.controller;

import com.supermarket.dao.BillDAO;
import com.supermarket.dao.CustomerDAO;
import com.supermarket.model.Bill;
import com.supermarket.model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/BillController")
public class BillController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        billDAO = new BillDAO();
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String customerIdStr = request.getParameter("customerId");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        if (customerIdStr == null || customerIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/views/ManagementStaff/CustomerStatView.jsp");
            return;
        }
        
        try {
            int customerId = Integer.parseInt(customerIdStr);
            
            Customer customer = customerDAO.findById(customerId);
            
            if (customer == null) {
                request.setAttribute("error", "Customer not found.");
                request.getRequestDispatcher("/views/ManagementStaff/CustomerDetailView.jsp")
                       .forward(request, response);
                return;
            }
  
            List<Bill> bills;
            
            // Check if date range is provided
            if (startDateStr != null && endDateStr != null && 
                !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
                
                Date startDate = Date.valueOf(startDateStr);
                Date endDate = Date.valueOf(endDateStr);
                
                bills = billDAO.getBill(customerId, startDate, endDate);
                
                request.setAttribute("startDate", startDateStr);
                request.setAttribute("endDate", endDateStr);
            } else {
                bills = billDAO.getBill(customerId);
            }
            
            request.setAttribute("customer", customer);
            request.setAttribute("bills", bills);
            request.setAttribute("billDAO", billDAO);

            request.getRequestDispatcher("/views/ManagementStaff/CustomerDetailView.jsp")
                   .forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid customer ID.");
            request.getRequestDispatcher("/views/ManagementStaff/CustomerDetailView.jsp")
                   .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while retrieving bill details.");
            request.getRequestDispatcher("/views/ManagementStaff/CustomerDetailView.jsp")
                   .forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
