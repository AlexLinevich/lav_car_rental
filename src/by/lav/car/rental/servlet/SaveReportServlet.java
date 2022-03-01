package by.lav.car.rental.servlet;

import by.lav.car.rental.service.ReportService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static by.lav.car.rental.util.UrlPath.CARS;
import static by.lav.car.rental.util.UrlPath.FILE_FULL_PATH;
import static by.lav.car.rental.util.UrlPath.SAVE;
import static java.nio.file.StandardOpenOption.CREATE;

@WebServlet(SAVE)
public class SaveReportServlet extends HttpServlet {

    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Files.writeString(FILE_FULL_PATH, reportService.createReport(), CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        resp.sendRedirect(CARS);
    }
}
