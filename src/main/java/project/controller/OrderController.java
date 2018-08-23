package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import project.model.service.OrderService;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());

        return "admin/all_orders";
    }

    @GetMapping("/orders/{login}")
    public String getAllUserOrders(@PathVariable("login") String login, Model model) {
        model.addAttribute("orders", orderService.getAllUserOrders(login));

        return "user/my_orders";
    }
}
