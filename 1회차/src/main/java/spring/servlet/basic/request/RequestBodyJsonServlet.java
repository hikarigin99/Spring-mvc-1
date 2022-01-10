package spring.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;
import spring.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // Json -> 객체로 변환하기 위해 필요한 라이브러리 (스프링 부트는 Jackson('ObjectMapper' 라이브러리 사용)
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);  // json도 문자

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); // Json 문자를 HelloData 객체로 변환

        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("OK");
    }
}
