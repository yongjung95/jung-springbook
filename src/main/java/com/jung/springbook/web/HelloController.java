package com.jung.springbook.web;

import com.jung.springbook.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController 는
 * 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
 * 기존의 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해준다라고 생각하면 된다.
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name , @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
