package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.domain.Order;
import project.model.dto.OrderDTO;
import project.model.exception.NoFreeCarWithSuchTypeException;
import project.model.exception.NoStreetWithSuchName;
import project.model.service.OrderService;
import project.model.util.OrderPriceGenerator;
import project.validator.OrderDTOValidator;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class OrderController {

    private OrderDTOValidator orderDTOValidator;
    private OrderService orderService;

    @Autowired
    public OrderController(OrderDTOValidator orderDTOValidator, OrderService orderService) {
        this.orderDTOValidator = orderDTOValidator;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());

        return "admin/all_orders";
    }

    @GetMapping("/make_order")
    public String getMakeOrderPage(Model model) {
        model.addAttribute("order", new OrderDTO());

        return "user/make_order_page";
    }

    @PostMapping("/make_order")
    public String makeOrder(@ModelAttribute("order") OrderDTO orderDTO, Model model, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("order") != null) {
            model.addAttribute("informationMessage", "You already have order, confirm or cancel it");
        } else {
            orderDTOValidator.validate(orderDTO, bindingResult);
            if (bindingResult.hasErrors()) {
                return "user/make_order_page";
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            try {
                Order order = orderService.makeOrder(auth.getName(), orderDTO.getDepartureStreet(), orderDTO.getDestinationStreet(), orderDTO.getType());
                session.setAttribute("order", order);
                model.addAttribute("informationMessage", "Approximate wait time is " + OrderPriceGenerator
                        .getOrderWaitingTime(orderDTO.getDepartureStreet(), orderDTO.getDestinationStreet()));
                //model.addAttribute("order", order);
            } catch (NoFreeCarWithSuchTypeException e) {
                model.addAttribute("informationMessage", "There is no free car with " + orderDTO.getType() + " type.");
            } catch (NoStreetWithSuchName e) {
                model.addAttribute("informationMessage", "There is no street with " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return "user/user_foundation";
    }

    @GetMapping("/my_orders")
    public String getAllUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("orders", orderService.getAllUserOrders(auth.getName()));

        return "user/my_orders";
    }

    @RequestMapping("/orders/remove/{id}")
    public String removeOrder(@PathVariable("id") Integer id) {
        orderService.removeOrderById(id);

        return "redirect:/orders";
    }

    @PostMapping("/confirm_order")
    public String confirmOrder(Model model, HttpSession session) {
        orderService.confirmOrder((Order) session.getAttribute("order"));
        session.removeAttribute("order");
        model.addAttribute("informationMessage", "Have a good trip");

        return "user/user_foundation";
    }

    @PostMapping("/cancel_order")
    public String cancelOrder(Model model, HttpSession session) {
        orderService.cancelOrder((Order) session.getAttribute("order"));
        session.removeAttribute("order");
        model.addAttribute("informationMessage", "We regret that something didn't suit you.");

        return "user/user_foundation";
    }
}