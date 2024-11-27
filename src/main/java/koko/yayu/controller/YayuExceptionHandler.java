package koko.yayu.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import koko.yayu.exception.YayuException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class YayuExceptionHandler {

  @ExceptionHandler(YayuException.class)
  public ModelAndView yayuException(YayuException e) {
    ModelAndView mav = new ModelAndView();
    mav.addObject("message", e.getMessage());
    mav.setViewName("error-known");
    return mav;
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.addObject("trace", Arrays.stream(e.getStackTrace())
      .map(String::valueOf).collect(Collectors.joining("<br>")));
    mav.setViewName("error-unknown");
    return mav;
  }
}