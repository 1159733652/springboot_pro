package com.zhy.practice.controller;

import com.zhy.practice.entity.TestDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC    字段注解
 *      在 SpringMVC 中，有一个类是 RequestResponseBodyMethodProcessor ，这个类有两个作用（实际上可以从名字上得到一点启发）
 *          用于解析 @RequestBody 标注的参数
 *          处理 @ResponseBody 标注方法的返回值
 *          解析 @RequestBoyd 标注参数的方法是 resolveArgument
 */
@RestController(value = "testController")
@RequestMapping("/test")
@Validated
public class BaseController {

    @GetMapping(value = "/returnString", produces = "application/json; charset=UTF-8")
    public String returnString() {
        return "success";
    }


    @GetMapping("/{num}")
    public Integer detail(@PathVariable("num") @Min(1) @Max(20) Integer num) {
        return num * num;
    }

    @GetMapping("/getByEmail")
    public TestDTO getByAccount(@RequestParam @NotBlank @Email String email) {
        TestDTO testDTO = new TestDTO();
        testDTO.setEmail(email);
        return testDTO;
    }

    @PostMapping("/test-validation")
    public void testValidation(@RequestBody @Validated TestDTO testDTO) {

    }

}
