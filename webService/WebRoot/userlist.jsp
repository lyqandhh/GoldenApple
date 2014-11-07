<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<h2>Welcome to my SpringMVC demo page</h2>  
<c:forEach items="${users}" var="user">  
    <c:out value="${user.username}"/><br/>  
    <c:out value="${user.password}"/><br/>  
    <c:out value="${user.address}"/><br/>  
    <c:out value="${user.age}"/><br/>  
</c:forEach>  
  
<!--   
以下是，处理对"/userlist.htm"的请求的整个工作步骤   
1、DispatcherServlet接受对"/userlist.htm"样式的URL请求   
2、DispatcherServlet询问BeanNameUrlHandlerMapping   
  询问Bean的名字是"/userlist.htm"的控制器，并且找到了UserController的Bean   
3、DispatcherServlet分发给UserController来处理这个请求   
4、UserController返回一个ModelAndView对象，它带有一个逻辑视图名"userlist"，以及保存在"users"属性中的值   
5、DispatcherServlet询问它的视图解析器(配置为InternalResourceViewResolver)   
  查找逻辑名为userlist的视图，InternalResourceViewResolver返回/WEB-INF/jsp/userlist.jsp路径   
6、DispatcherServlet将请求导向/WEB-INF/jsp/userlist.jsp页面   
-->