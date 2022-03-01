package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.OrderDto;
import by.lav.car.rental.service.SeeOrderService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import static by.lav.car.rental.util.UrlPath.CHECK_ORDER;

@WebServlet(CHECK_ORDER)
public class CheckOrderServlet extends HttpServlet {

    private final SeeOrderService seeOrderService = SeeOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Integer orderId = Integer.valueOf(req.getParameter("orderId"));

        seeOrderService.findById(orderId).ifPresentOrElse(orderDto -> {
            forwardOrderDto(req, resp, orderDto);
        }, () -> {
            sendError(resp);
        });

    }

    @SneakyThrows
    private void sendError(HttpServletResponse resp) {
        resp.setStatus(400);
        resp.sendError(400, "NO ORDER");
    }

    @SneakyThrows
    private void forwardOrderDto(HttpServletRequest req, HttpServletResponse resp, OrderDto orderDto) {
        req.setAttribute("order", orderDto);
        req.getRequestDispatcher(JspHelper.getPath("check-order"))
                .forward(req, resp);
    }
}
