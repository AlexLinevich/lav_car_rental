package by.lav.car.rental.servlet;

import by.lav.car.rental.dto.UserDto;
import by.lav.car.rental.entity.UsersRole;
import by.lav.car.rental.service.ClientDataService;
import by.lav.car.rental.service.UserService;
import by.lav.car.rental.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.lav.car.rental.util.UrlPath.CARS;
import static by.lav.car.rental.util.UrlPath.CLIENT_DATA;
import static by.lav.car.rental.util.UrlPath.LOGIN;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final ClientDataService clientDataService = ClientDataService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(LOGIN + "?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);
        if (user.getRole().equals(UsersRole.CLIENT)) {
            if (clientDataService.findClientDataId(user.getId()).isPresent()) {
                resp.sendRedirect(CARS);
            } else {
                resp.sendRedirect(CLIENT_DATA);
            }
        } else {
            resp.sendRedirect(CARS);
        }
    }
}
