package com.zhy.practice.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC    @RequestBody 参数校验
 *
 *      Post、Put 请求的参数推荐使用 @RequestBody 请求体参数
 *
 *      对 @RequestBody 参数进行校验需要在 DTO 对象中加入校验条件后，再搭配 @Validated 即可完成自动校验
 *
 *      如果校验失败，会抛出 ConstraintViolationException 异常
 *
 *      校验原理
 *          声明约束的方式，注解加到了参数上面，
 *          可以比较容易猜测到是使用了 AOP 对方法进行增强
 *
 *          而实际上 Spring 也是通过 MethodValidationPostProcessor 动态注册 AOP 切面，
 *          然后使用 MethodValidationInterceptor 对切点方法进行织入增强
 */
@Data
public class TestDTO {
    @NotBlank
    private String userName;

    @NotBlank
    @Length(min = 6, max = 20)
    private String password;

    @NotNull
    @Email
    private String email;
}

