package mvc1.servlet.web.frontcontroller.v1.controller;

import mvc1.servlet.domain.member.Member;
import mvc1.servlet.domain.member.MemberRepository;
import mvc1.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        // 컨트롤러가 비지니스 로직 호출
        Member member = new Member(username, age);
        memberRepository.save(member);

        // Model 에 데이터를 보관한다.
        request.setAttribute("member", member); // request 객체의 내부 저장소에 저장 (Map 과 비슷)

        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);  // 컨트롤러 서버 내부에서 jsp(view) 호출
    }
}
