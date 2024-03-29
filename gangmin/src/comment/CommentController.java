package comment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("/comment/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	CommentService commentService;
	CommentVO commentVO;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		commentService = new CommentService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		try {
			if(action == null) {
				
			}else if(action.equals("/addComment")) {
				String ccontent = request.getParameter("ccontent");
				int lkey = Integer.parseInt(request.getParameter("lkey"));
				int mkey = Integer.parseInt(request.getParameter("mkey"));
				float cscore = Float.parseFloat(request.getParameter("cscore"));
				
				commentVO = new CommentVO(ccontent, lkey, mkey, cscore / 2);
				commentService.addComment(commentVO);
				
				nextPage = request.getContextPath() + "/lecture/connectLecture?lkey=" + lkey;
				response.sendRedirect(nextPage);
			}else if(action.equals("/delComment")) {
				int ckey = Integer.parseInt(request.getParameter("ckey"));
				int lkey = Integer.parseInt(request.getParameter("lkey"));
				
				commentService.delComment(ckey, lkey);
				nextPage = request.getContextPath() + "/lecture/connectLecture?lkey=" + lkey;
				response.sendRedirect(nextPage);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
