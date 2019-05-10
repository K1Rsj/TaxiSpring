package project.controller;

import static project.constant.ExceptionMessages.YOU_CAN_ONLY_HAVE_ONE_ORDER_AT_TIME;
import static project.constant.GlobalConstants.ID;
import static project.constant.GlobalConstants.INFORMATION_MESSAGE;
import static project.constant.GlobalConstants.ORDER;
import static project.constant.GlobalConstants.ORDERS;
import static project.constant.GlobalConstants.SPACE;
import static project.constant.Messages.APPROXIMATE_TRAVEL_TIME;
import static project.constant.Messages.HAVE_A_GOOD_TRIP;
import static project.constant.Messages.MINUTES;
import static project.constant.Messages.WE_ARE_DISAPPOINTED;
import static project.constant.URL.CANCEL_ORDER;
import static project.constant.URL.CONFIRM_ORDER;
import static project.constant.URL.MAKE_ORDER;
import static project.constant.URL.MY_ORDERS;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
import project.model.dto.OrderDto;
import project.model.exception.NoFreeCarWithSuchTypeException;
import project.model.exception.NoStreetWithSuchName;
import project.model.service.OrderService;
import project.model.util.OrderPriceGenerator;
import project.model.util.Utils;
import project.validator.OrderValidator;

@Controller
public class OrderController {

    private OrderValidator orderValidator;
    private OrderService orderService;
    private ReloadableResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public OrderController(OrderValidator orderValidator, OrderService orderService,
                           ReloadableResourceBundleMessageSource bundleMessageSource) {
        this.orderValidator = orderValidator;
        this.orderService = orderService;
        this.bundleMessageSource = bundleMessageSource;
    }

    @GetMapping(ORDERS)
    public String getAllOrders(Model model) {
        model.addAttribute(ORDERS, orderService.getAllOrders());

        return "admin/all_orders";
    }

    @GetMapping(MAKE_ORDER)
    public String getMakeOrderPage(Model model) {
        model.addAttribute(ORDER, new OrderDto());

        return "user/make_order_page";
    }

    @PostMapping(MAKE_ORDER)
    public String makeOrder(@ModelAttribute(ORDER) OrderDto orderDTO, Model model,
                            BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute(ORDER) != null) {
            model.addAttribute(INFORMATION_MESSAGE, Utils.getMessageInCurrentLocale(bundleMessageSource,
                    YOU_CAN_ONLY_HAVE_ONE_ORDER_AT_TIME));
        } else {
            orderValidator.validate(orderDTO, bindingResult);
            if (bindingResult.hasErrors()) {
                return "user/make_order_page";
            }
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            try {
                Order order = orderService.makeOrder(auth.getName(), orderDTO.getDepartureStreet(),
                        orderDTO.getDestinationStreet(), orderDTO.getType());
                session.setAttribute(ORDER, order);
                model.addAttribute(INFORMATION_MESSAGE, Utils.getMessageInCurrentLocale(
                        bundleMessageSource, APPROXIMATE_TRAVEL_TIME) + SPACE + OrderPriceGenerator
                        .getOrderTravelTime(orderDTO.getDepartureStreet(),
                        orderDTO.getDestinationStreet()) + SPACE +  Utils.getMessageInCurrentLocale(bundleMessageSource,
                        MINUTES));
                //model.addAttribute("order", order);
            } catch (NoFreeCarWithSuchTypeException e) {
                model.addAttribute(INFORMATION_MESSAGE, Utils.getMessageInCurrentLocale(bundleMessageSource,
                        e.getMessage()));
            } catch (NoStreetWithSuchName e) {
                model.addAttribute(INFORMATION_MESSAGE, e.getMessage());
            }
        }

        return "user/user_foundation";
    }

    @GetMapping(MY_ORDERS)
    public String getAllUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute(ORDERS, orderService.getAllUserOrders(auth.getName()));

        return "user/my_orders";
    }

    @RequestMapping("/orders/remove/{id}")
    public String removeOrder(@PathVariable(ID) Integer id) {
        orderService.removeOrderById(id);

        return "redirect:/orders";
    }

    @PostMapping(CONFIRM_ORDER)
    public String confirmOrder(Model model, HttpSession session) {
        orderService.confirmOrder((Order) session.getAttribute(ORDER));
        session.removeAttribute(ORDER);
        model.addAttribute(INFORMATION_MESSAGE, Utils.getMessageInCurrentLocale(bundleMessageSource, HAVE_A_GOOD_TRIP));

        return "redirect:/user_home";
    }

    @PostMapping(CANCEL_ORDER)
    public String cancelOrder(Model model, HttpSession session) {
        orderService.cancelOrder((Order) session.getAttribute(ORDER));
        session.removeAttribute(ORDER);
        model.addAttribute(INFORMATION_MESSAGE, Utils.getMessageInCurrentLocale(bundleMessageSource,
                WE_ARE_DISAPPOINTED));

        return "redirect:/user_home";
    }
}