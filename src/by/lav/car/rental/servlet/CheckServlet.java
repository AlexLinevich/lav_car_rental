package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.OrderDto;
import by.lav.car.rental.service.OrderService;
import by.lav.car.rental.service.SeeOrderService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import static by.lav.car.rental.util.UrlPath.CHECK;

@WebServlet(CHECK)
public class CheckServlet extends HttpServlet {

    private final SeeOrderService seeOrderService = SeeOrderService.getInstance();
    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Integer orderId = Integer.valueOf(req.getParameter("orderId"));

        seeOrderService.findById(orderId).ifPresentOrElse(orderDto -> {
            forwardCheckedOrderDto(req, resp, orderDto);
        }, () -> {
            sendError(resp);
        });

    }

    @SneakyThrows
    private void sendError(HttpServletResponse resp) {
        resp.setStatus(400);
        resp.sendError(400, "NO ORDERS");
    }

    @SneakyThrows
    private void forwardCheckedOrderDto(HttpServletRequest req, HttpServletResponse resp, OrderDto orderDto) {
        orderService.check(orderDto);
        req.setAttribute("orders", orderService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("orders"))
                .forward(req, resp);
    }

}
