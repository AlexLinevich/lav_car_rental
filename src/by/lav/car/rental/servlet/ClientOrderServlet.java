package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.UserDto;
import by.lav.car.rental.service.OrderService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.lav.car.rental.util.UrlPath.CLIENT_ORDERS;

@WebServlet(CLIENT_ORDERS)
public class ClientOrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserDto) req.getSession().getAttribute("user");
        var ordersByUser = orderService.ordersByUser(user.getId());
        req.setAttribute("orders", ordersByUser);
        req.getRequestDispatcher(JspHelper.getPath("client-orders"))
                .forward(req, resp);
    }

}
