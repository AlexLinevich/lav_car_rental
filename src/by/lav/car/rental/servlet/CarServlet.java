package by.lav.car.rental.servlet;

import by.lav.car.rental.service.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/cars")
public class CarServlet extends HttpServlet {

    private final CarService carService = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write("<h1>СПИСОК ДОСТУПНЫХ АВТОМОБИЛЕЙ</h1>");
            writer.write("<ul>");
            carService.findAll().forEach(carDto -> writer.write(
                    String.format("<li><a href=\"car-category?carCategoryId=%d\">%s</li>",
                            carDto.getCarCategoryId(), carDto.getDescription())));
            writer.write("<ul>");
        }
    }
}
