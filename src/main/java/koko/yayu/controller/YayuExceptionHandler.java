package koko.yayu.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class YayuExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", e);
    mav.addObject("url", req.getRequestURL());
    mav.addObject("trace", Arrays.stream(e.getStackTrace())
      .map(String::valueOf).collect(Collectors.joining("<br>")));
    mav.setViewName("error");
    return mav;
  }
}