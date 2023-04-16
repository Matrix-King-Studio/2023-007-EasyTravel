package tour.controller.test;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"测试"})
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    @GetMapping("test1")
    public String test1(){
        String s = "2023-03-02";
        String ss = "2023-03-22";
        String ss1 = ss.substring(8);
        String s1 = s.substring(8);
        log.info(s1);
        log.info(ss1);
        int i1 = Integer.parseInt(ss1);
        int i = Integer.parseInt(s1);
        System.out.println(i);;
        return s1+i+ss1+i1;
    }

}
