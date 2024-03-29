package lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.*;

/**
 * Servlet implementation class LectureController
 */
@WebServlet("/lecture/*")
public class LectureController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	LectureService lectureService;
	LectureVO lectureVO;
	
	CommentService commentService;
	CommentVO commentVO;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//super.init(config);
		lectureService = new LectureService();
		commentService = new CommentService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();	
		try {
			List<LectureVO> lecturesList = new ArrayList<LectureVO>();
			List<CommentVO> commentsList = new ArrayList<CommentVO>();
			
			if(action == null) {
				lecturesList = lectureService.listLectures();
				request.setAttribute("LecturesList", lecturesList);
				nextPage = "/index.jsp";
			}else if(action.equals("/listLectures.do")) {
				lecturesList = lectureService.listLectures();				
				request.setAttribute("lecturesList", lecturesList);
				nextPage = "/index.jsp";
				RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
				dispatch.forward(request, response);
			}else if(action.equals("/searchLecture.do"))
			{
				System.out.println("lecture controller");
				String searchlec = request.getParameter("findl");
				System.out.println(searchlec);
				lecturesList = lectureService.searchLectures(searchlec);
				request.setAttribute("lecturesList", lecturesList);
				RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
				dispatch.forward(request, response);
			}
			else if(action.equals("/eventprice.do"))
			{
				String sort = request.getParameter("do_sort");
				System.out.println(sort);
				lecturesList = lectureService.eventpriceLectures(sort);

				request.setAttribute("lecturesList", lecturesList);
				RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
				dispatch.forward(request, response);
			}
			else if(action.equals("/connectLecture")) {
				int lkey = Integer.parseInt(request.getParameter("lkey"));
				lectureVO = lectureService.infoLecture(lkey);
				lectureVO.setLinfo(lectureVO.getLinfo().replace("\n", "<br/>"));
				commentsList = commentService.listComments(lkey);
				request.setAttribute("commentsList", commentsList);
				request.setAttribute("infoLecture", lectureVO);
				RequestDispatcher dispatch = request.getRequestDispatcher("/lecture_info.jsp");
				dispatch.forward(request, response);
				System.out.println(lkey);
			}
			else if(action.equals("/categorySearch.do")){
				String[] selectedAcademy = request.getParameter("selectedAcademy").split(",");
				String radioValue1 = request.getParameter("radioValue1");
				String radioValue2 = request.getParameter("radioValue2");
				//String radioValue3 = request.getParameter("radioValue3");
				String radioValue4 = request.getParameter("radioValue4");
				
				lecturesList = lectureService.categorySearch(selectedAcademy, radioValue1, radioValue2, radioValue4);
				
				request.setAttribute("lecturesList", lecturesList);
				nextPage = "/index.jsp";
				RequestDispatcher dispatch = request.getRequestDispatcher("/index.jsp");
				dispatch.forward(request, response);
				
			}
			else {
				System.out.println(action);
			}
			
		} catch(Exception e) {
			System.out.println("error1");
			e.printStackTrace();
		}
	}
}
