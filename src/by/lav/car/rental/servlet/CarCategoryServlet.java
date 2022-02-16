package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.CarCategoryDto;
import by.lav.car.rental.service.CarCategoryService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet("/car-category")
public class CarCategoryServlet extends HttpServlet {

    private final CarCategoryService carCategoryService = CarCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Integer carCategoryId = Integer.valueOf(req.getParameter("carCategoryId"));

        carCategoryService.findById(carCategoryId).ifPresentOrElse(carCategoryDto -> {
            forwardCarCategoryDto(req, resp, carCategoryDto);
        }, () -> {
            sendError(resp);
        });

    }

    @SneakyThrows
    private void sendError(HttpServletResponse resp) {
        resp.setStatus(400);
        resp.sendError(400, "Такой категории не существует");
    }

    @SneakyThrows
    private void forwardCarCategoryDto(HttpServletRequest req, HttpServletResponse resp, CarCategoryDto carCategoryDto) {
        req.setAttribute("carCategory", carCategoryDto);
        req.getRequestDispatcher(JspHelper.getPath("car-category"))
                .forward(req, resp);
    }
}
