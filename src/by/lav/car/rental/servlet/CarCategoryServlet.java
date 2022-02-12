package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.CarCategoryDto;
import by.lav.car.rental.service.CarCategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/car-category")
public class CarCategoryServlet extends HttpServlet {

    private final CarCategoryService carCategoryService = CarCategoryService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>КАТЕГОРИЯ АВТОМОБИЛЯ</h1>");
            writer.write("<ul>");
            Integer carCategoryId = Integer.valueOf(req.getParameter("carCategoryId"));
            CarCategoryDto carCategoryServiceById = carCategoryService.findById(carCategoryId).get();
            writer.write("КАТЕГОРИЯ АВТОМОБИЛЯ: " + carCategoryServiceById.getCategory() +
                    "   ЦЕНА АРЕНДЫ ЗА СУТКИ: " + carCategoryServiceById.getDayPrice());
            writer.write("<ul>");
        }

    }
}
