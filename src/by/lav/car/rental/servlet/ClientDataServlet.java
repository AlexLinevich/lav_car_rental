package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.CreateClientDataDto;
import by.lav.car.rental.dto.UserDto;
import by.lav.car.rental.exception.ValidationException;
import by.lav.car.rental.service.ClientDataService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.lav.car.rental.util.UrlPath.CARS;
import static by.lav.car.rental.util.UrlPath.CLIENT_DATA;

@WebServlet(CLIENT_DATA)
public class ClientDataServlet extends HttpServlet {

    private final ClientDataService clientDataService = ClientDataService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("client-data"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserDto) req.getSession().getAttribute("user");
        var clientDataDto = CreateClientDataDto.builder()
                .userId(String.valueOf(user.getId()))
                .driverLicenceNo(req.getParameter("driverLicenceNo"))
                .dlExpirationDay(req.getParameter("dlExpirationDay"))
                .creditAmount(req.getParameter("creditAmount"))
                .build();

        try {
            clientDataService.create(clientDataDto);
            resp.sendRedirect(CARS);
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
