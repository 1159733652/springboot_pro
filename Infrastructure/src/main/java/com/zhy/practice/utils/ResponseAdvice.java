package com.zhy.practice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author zhanghongyu
 * @Date 2022/8/29
 * @DESC    统一包装处理
 *  目的：
 *      ResponseBodyAdvice 是对 Controller 返回的内容在 HttpMessageConverter 进行类型转换之前拦截，
 *      进行相应的处理操作后，再将结果返回给客户端。那这样就可以把统一包装的工作放到这个类里面。
 *  方法解释：
 *      supports：判断是否要交给 beforeBodyWrite 方法执行，ture：需要；false：不需要
 *      beforeBodyWrite：对 response 进行具体的处理
 */
// 如果引入了swagger或knife4j的文档生成组件，这里需要仅扫描自己项目的包，否则文档无法正常生成
@RestControllerAdvice(basePackages = "com.zhy.practice")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果不需要进行封装的，可以添加一些校验手段，比如添加标记排除的注解
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        if (body instanceof Result) {
            return body;
        }

        return Result.success(body);
    }

    /**
     * 处理 cannot be cast to java.lang.String 问题
     *
     * 如果直接使用 ResponseBodyAdvice，对于一般的类型都没有问题，当处理字符串类型时，
     * 会抛出 xxx.包装类 cannot be cast to java.lang.String 的类型转换的异常
     *
     * 在 ResponseBodyAdvice 实现类中 debug 发现，
     * 只有 String 类型的 selectedConverterType 参数值是 org.springframework.http.converter.StringHttpMessageConverter，
     * 而其他数据类型的值是 org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     *
     * 现在问题已经较为清晰了，因为我们需要返回一个 Result 对象
     *     所以使用 MappingJackson2HttpMessageConverter 是可以正常转换的
     *     而使用 StringHttpMessageConverter 字符串转换器会导致类型转换失败
     *
     *
     *     现在处理这个问题有两种方式
     */

    //1、在 beforeBodyWrite 方法处进行判断，
    // 如果返回值是 String 类型就对 Result 对象手动进行转换成 JSON 字符串，
    // 另外方便前端使用，最好在 @RequestMapping 中指定 ContentType
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
//                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
//                                  ServerHttpRequest request, ServerHttpResponse response) {
//        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
//        if (body instanceof Result) {
//            return body;
//        }
//
//        // 如果返回值是String类型，那就手动把Result对象转换成JSON字符串
//        if (body instanceof String) {
//            try {
//                return this.objectMapper.writeValueAsString(Result.success(body));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return Result.success(body);
//    }
}
