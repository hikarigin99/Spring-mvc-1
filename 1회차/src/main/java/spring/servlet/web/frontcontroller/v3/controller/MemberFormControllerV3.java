package spring.servlet.web.frontcontroller.v3.controller;

import spring.servlet.web.frontcontroller.ModelView;
import spring.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) {
        return new ModelView("new-form");   // 전체 Path을 넣는 것이 아니라 논리적인 이름만 넣기
    }
}
