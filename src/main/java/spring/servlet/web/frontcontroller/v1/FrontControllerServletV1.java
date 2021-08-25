package spring.servlet.web.frontcontroller.v1;

import spring.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import spring.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import spring.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");     // 실제로는 로그로 찍는 것이 더 좋음

        String requestURI = request.getRequestURI();

        ControllerV1 controller = controllerMap.get(requestURI);    // 다형성 부모 - 자식 (ControllerV1 .. = MemberListControllerV1)
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);   // 404 오류
            return;
        }
        controller.process(request, response);  // 다형성으로 인해 오버라이드 된 process 메서드 호출
    }
}
