package spring.servlet.web.frontcontroller.v3;

import spring.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap); // 우리가 만든 프레임워크에 종속적이고 서블릿에 종속적인 것이 아님
}
