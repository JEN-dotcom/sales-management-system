package com.ingryd.sms.controllers;

import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.ProductDTO;
import com.ingryd.sms.service.InvoiceService;
import com.ingryd.sms.service.OrderService;
import com.ingryd.sms.service.ProductService;
import com.ingryd.sms.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    /********************************* PRODUCT *********************************/

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/product/id/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product successfully deleted");
    }

    /*********************************** USER ***********************************/

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/name/{firstName}")
    public ResponseEntity<User> getUserByFirstName(@PathVariable String firstName) {
        User user = userService.getUserByFirstName(firstName);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/user/id/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/id/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("user successfully deleted");
    }

    /********************************* ORDER *********************************/

    @GetMapping("/orders/{number}/{size}")
    public ResponseEntity<List<Order>> getAllOrdersPaginated(@PathVariable("number") int pageNumber,
            @PathVariable("size") int pageSize) {
        List<Order> orders = orderService.getAllOrdersPaginated(pageNumber, pageSize);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/order/id/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersByDatePaginated(
            @RequestParam(name = "date", required = false) String dateStringDDMMYYYY,
            @RequestParam(name = "startDate", required = false) String startDateStringDDMMYYYY,
            @RequestParam(name = "endDate", required = false) String endDateStringDDMMYYYY,
            @RequestParam(name = "pageNumber") int pageNumber,
            @RequestParam(name = "pageSize") int pageSize) throws ParseException {

        if (dateStringDDMMYYYY != null) {
            List<Order> orderList = orderService.getOrdersByDatePaginated(dateStringDDMMYYYY, pageNumber, pageSize);
            if (!orderList.isEmpty()) {
                return ResponseEntity.ok(orderList);
            }
        } else if (startDateStringDDMMYYYY != null && endDateStringDDMMYYYY != null) {
            List<Order> orderList = orderService.getOrdersByDatePaginated(startDateStringDDMMYYYY,
                    endDateStringDDMMYYYY, pageNumber, pageSize);
            if (!orderList.isEmpty()) {
                return ResponseEntity.ok(orderList);
            }
        }
        return ResponseEntity.noContent().build();
    }

    /********************************* INVOICE *********************************/

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getInvoicesByDateRange(
            @RequestParam(name = "date", required = false) String dateStringDDMMYYYY,
            @RequestParam(name = "startDate", required = false) String startDateStringDDMMYYYY,
            @RequestParam(name = "endDate", required = false) String endDateStringDDMMYYYY) throws ParseException {

        if (dateStringDDMMYYYY != null) {
            List<Invoice> invoiceList = invoiceService.getInvoicesByDate(dateStringDDMMYYYY);
            if (!invoiceList.isEmpty()) {
                return ResponseEntity.ok(invoiceList);
            }
        } else if (startDateStringDDMMYYYY != null && endDateStringDDMMYYYY != null) {
            List<Invoice> invoiceList = invoiceService.getInvoicesByDate(startDateStringDDMMYYYY,
                    endDateStringDDMMYYYY);
            if (!invoiceList.isEmpty()) {
                return ResponseEntity.ok(invoiceList);
            }
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoice/id/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice != null)
            return ResponseEntity.ok(invoice);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/invoice/grandTotal")
    public ResponseEntity<Double> getInvoiceGrandTotal(
            @RequestParam(name = "date", required = false) String dateStringDDMMYYYY,
            @RequestParam(name = "startDate", required = false) String startDateStringDDMMYYYY,
            @RequestParam(name = "endDate", required = false) String endDateStringDDMMYYYY) throws ParseException {

        ResponseEntity<List<Invoice>> responseEntity = getInvoicesByDateRange(dateStringDDMMYYYY,
                startDateStringDDMMYYYY, endDateStringDDMMYYYY);

        Double grandTotal = invoiceService.getGrandTotalOfInvoices(responseEntity.getBody());
        return ResponseEntity.ok(grandTotal);
    }

    @GetMapping("/invoice/grandTotalDetail")
    public ResponseEntity<String> getInvoiceGrandTotalString(
            @RequestParam(name = "date", required = false) String dateStringDDMMYYYY,
            @RequestParam(name = "startDate", required = false) String startDateStringDDMMYYYY,
            @RequestParam(name = "endDate", required = false) String endDateStringDDMMYYYY) throws ParseException {

        ResponseEntity<List<Invoice>> responseEntity = getInvoicesByDateRange(dateStringDDMMYYYY,
                startDateStringDDMMYYYY, endDateStringDDMMYYYY);

        ResponseEntity<Double> responseEntityDouble = getInvoiceGrandTotal(dateStringDDMMYYYY,
                startDateStringDDMMYYYY, endDateStringDDMMYYYY);

        String grandTotal = invoiceService.getGrandTotalOfInvoices(responseEntity.getBody(),
                responseEntityDouble.getBody());
        return ResponseEntity.ok(grandTotal);
    }

    @DeleteMapping("/invoice/date/{date}")
    public ResponseEntity<String> deleteInvoicesBeforeDate(@PathVariable String dateStringDDMMYYYY)
            throws ParseException {
        return invoiceService.deleteInvoicesBeforeDate(dateStringDDMMYYYY);
    }
}
