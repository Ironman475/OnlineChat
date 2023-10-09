package academy.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.*;

/*

	0 - m / from=0
	1 - m
	2 - m
	....
	100 - m / from=101
	....


 */

public class GetListServlet extends HttpServlet {

	private MessageList msgList = MessageList.getInstance();
	private PrivateListMap prvMap = PrivateListMap.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		int from = 0;
		String prvStr = req.getParameter("prv");
		int prv = 0;
		String login = req.getParameter("login");

		if (fromStr != null) {
			try {
				from = Integer.parseInt(fromStr);
				if (from < 0) from = 0;

			} catch (Exception ex) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			resp.setContentType("application/json");

			String json = msgList.toJSON(from);
			if (json != null) {
				OutputStream os = resp.getOutputStream();
				byte[] buf = json.getBytes(StandardCharsets.UTF_8);
				os.write(buf);
			}
			//PrintWriter pw = resp.getWriter();
			//pw.print(json);
		}

		if(login != null) {
			try {
				prv = Integer.parseInt(prvStr);
				if (prv < 0) prv = 0;

			} catch (Exception ex) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			resp.setContentType("application/json");
			String json = prvMap.toJSON(prv, login);
			if (json != null) {
				OutputStream os = resp.getOutputStream();
				byte[] buf = json.getBytes(StandardCharsets.UTF_8);
				os.write(buf);
			}
		}
	}
}

