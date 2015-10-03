package pers.ash.shiro.base;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

	@Autowired
	private JSONObject errorMsg;
	
	Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleBusinessException(RuntimeException ex) {
		logger.error(ex.getMessage());
		errorMsg.put("success", "false");
		errorMsg.put("msg", ex.getMessage());
		return errorMsg.toString();
	}

}