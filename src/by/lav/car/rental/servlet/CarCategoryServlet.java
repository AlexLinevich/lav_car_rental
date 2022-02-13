package by.lav.car.rental.servlet;

import by.lav.car.rental.service.CarCategoryService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/car-category")
public class CarCategoryServlet extends HttpServlet {

    private final CarCategoryService carCategoryService = CarCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer carCategoryId = Integer.valueOf(req.getParameter("carCategoryId"));
        req.setAttribute("carCategory", carCategoryService.findById(carCategoryId).get());
        req.getRequestDispatcher(JspHelper.getPath("car-category"))
                .forward(req, resp);
    }
}
