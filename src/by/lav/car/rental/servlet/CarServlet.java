package by.lav.car.rental.servlet;

import by.lav.car.rental.service.CarService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {

    private final CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cars", carService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("cars"))
                .forward(req, resp);
    }
}
