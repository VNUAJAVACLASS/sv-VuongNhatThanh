package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NewsDAO;
import model.News;

@WebServlet("/news") 
public class NewsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO(); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String idStr = req.getParameter("id");

        if (action == null)
            action = "list";

        switch (action) {
            case "create":
                req.getRequestDispatcher("form.jsp").forward(req, resp);
                break;

            case "edit":
                int idEdit = Integer.parseInt(idStr);
                News editNews = findById(idEdit);
                req.setAttribute("news", editNews);
                req.getRequestDispatcher("form.jsp").forward(req, resp);
                break;

            case "delete":
                int idDelete = Integer.parseInt(idStr);
                newsDAO.deleteNews(idDelete);
                resp.sendRedirect("news");
                break;

            case "detail":
                int idDetail = Integer.parseInt(idStr);
                News detailNews = findById(idDetail);
                req.setAttribute("news", detailNews);
                req.getRequestDispatcher("detail.jsp").forward(req, resp);
                break;

            default: // list
                List<News> newsList = newsDAO.getAllNews();
                req.setAttribute("newsList", newsList);
                req.getRequestDispatcher("list.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (idStr == null || idStr.isEmpty()) {
            newsDAO.addNews(title, content);
        } else {
            int id = Integer.parseInt(idStr);
            newsDAO.updateNews(id, title, content);
        }

        resp.sendRedirect("news");
    }

    private News findById(int id) {
        return newsDAO.getById(id);
    }

    @Override
    public void destroy() {
        newsDAO.close();
        super.destroy();
    }
}
