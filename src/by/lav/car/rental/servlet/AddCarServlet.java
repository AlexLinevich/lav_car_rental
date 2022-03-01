package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.CreateCarDto;
import by.lav.car.rental.service.CarCategoryService;
import by.lav.car.rental.service.CarService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.lav.car.rental.util.UrlPath.ADD_CAR;
import static by.lav.car.rental.util.UrlPath.CARS;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(ADD_CAR)
public class AddCarServlet extends HttpServlet {

    private final CarCategoryService carCategoryService = CarCategoryService.getInstance();
    private final CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("carCategory", carCategoryService.findAllCategory());
        req.getRequestDispatcher(JspHelper.getPath("add-car"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var createCarDto = CreateCarDto.builder()
                .model(req.getParameter("model"))
                .carCategory(req.getParameter("carCategory"))
                .colour(req.getParameter("colour"))
                .seatsQuantity(req.getParameter("seatsQuantity"))
                .image(req.getPart("image"))
                .build();

        carService.create(createCarDto);
        resp.sendRedirect(CARS);
    }
}
