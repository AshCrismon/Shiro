package pers.ash.shiro.base;

import org.apache.shiro.authz.UnauthorizedException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pers.ash.shiro.exception.CustomizedException;

@ControllerAdvice
public class CustomExceptionHandler {

	@Autowired
	private JSONObject errorMsg;

	Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(CustomizedException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleBusinessException(CustomizedException ex) {
		return renderMessage(ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String handleUnauthenticatedException(UnauthorizedException ex) {
		return renderMessage(ex.getMessage());
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String handleBusinessException(RuntimeException ex) {
		return renderMessage(ex.getMessage());
	}
	
	public String renderMessage(String message){
		logger.error(message);
		errorMsg.put("success", "false");
		errorMsg.put("msg", message);
		return errorMsg.toString();
	}

}