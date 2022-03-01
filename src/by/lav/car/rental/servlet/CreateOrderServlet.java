package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.CreateOrderDto;
import by.lav.car.rental.dto.UserDto;
import by.lav.car.rental.entity.OrderStatus;
import by.lav.car.rental.exception.ValidationException;
import by.lav.car.rental.service.CarService;
import by.lav.car.rental.service.CreateOrderService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.lav.car.rental.util.UrlPath.CARS;
import static by.lav.car.rental.util.UrlPath.ORDER;

@WebServlet(ORDER)
public class CreateOrderServlet extends HttpServlet {

    private final CarService carService = CarService.getInstance();
    private final CreateOrderService createOrderService = CreateOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cars", carService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("order"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var allCars = carService.findAll();
        var user = (UserDto) req.getSession().getAttribute("user");
        var orderDto = CreateOrderDto.builder()
                .userId(user.getId())
                .carId(carService.findByDescription(allCars, req.getParameter("car")))
                .beginTime(req.getParameter("beginTime"))
                .endTime(req.getParameter("endTime"))
                .orderStatus(OrderStatus.PROCESSING)
                .message("In process.")
                .build();

        try {
            createOrderService.create(orderDto);
            resp.sendRedirect(CARS);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
