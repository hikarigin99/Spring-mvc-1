package mvc1.servlet.web.frontcontroller.v5;

import mvc1.servlet.web.frontcontroller.ModelView;
import mvc1.servlet.web.frontcontroller.MyView;
import mvc1.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import mvc1.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import mvc1.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import mvc1.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import mvc1.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import mvc1.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import mvc1.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import mvc1.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // private Map<String, ControllerV3> controllerMap = new HashMap<>();
    private final Map<String, Object> handlerMappingMap = new HashMap<>();      // 모든 컨트롤러가 들어갈 수 있기 때문에 Object type
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();   // 어뎁터가 여러개 담겨있고 그 중에 하나 사용하기 위함

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // v4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // handler 찾아오는 메서드
        Object handler = getHandler(request);                       //MemberFormControllerV4

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // handler Adapter 찾아오는 메서드
        MyHandlerAdapter adapter = getHandlerAdapter(handler);      //ControllerV4HandlerAdapter

        ModelView mv = adapter.handle(request, response, handler);  //MemberFormControllerV4

        String viewName = mv.getViewName(); // 논리이름 new-form
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {    //MemberFormControllerV4
        for (MyHandlerAdapter adapter : handlerAdapters) {          //ControllerV3HandlerAdapter, ControllerV4HandlerAdapter
            if(adapter.support(handler)) {
                return adapter;                                     // ControllerV4HandlerAdapter
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
