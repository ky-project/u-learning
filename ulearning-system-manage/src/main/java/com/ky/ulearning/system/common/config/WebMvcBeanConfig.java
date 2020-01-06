package com.ky.ulearning.system.common.config;

import com.ky.ulearning.common.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * mvc的bean管理配置类
 *
 * @author luyuhao
 * @date 2020/1/6 18:41
 */
@Configuration
@AutoConfigureAfter(WebMvcConfig.class)
public class WebMvcBeanConfig {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 规定SpringContext在String和Date时的用的转化器
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
                .getWebBindingInitializer();
        if (initializer != null && initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer
                    .getConversionService();
            genericConversionService.addConverter(String.class, Date.class, new String2DateConverter());
        }
    }

    private class String2DateConverter implements Converter<String, Date> {
        @SuppressWarnings("NullableProblems")
        @Override
        public Date convert(String source) {
            return DateUtil.parseDate(source);
        }
    }
}
