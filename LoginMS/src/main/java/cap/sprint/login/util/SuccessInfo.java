package cap.sprint.login.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class SuccessInfo 
{
		private int httpCode;
		private HttpStatus status;
		private String message;
		private int id;
		public SuccessInfo(int httpCode, HttpStatus status, String message, int id) {
			super();
			this.httpCode = httpCode;
			this.status = status;
			this.message = message;
			this.id = id;
		}
		public int getHttpCode() {
			return httpCode;
		}
		public void setHttpCode(int httpCode) {
			this.httpCode = httpCode;
		}
		public HttpStatus getStatus() {
			return status;
		}
		public void setStatus(HttpStatus status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
}

