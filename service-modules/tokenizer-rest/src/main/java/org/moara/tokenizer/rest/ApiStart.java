/*
 * Copyright (C) 2021 Wigo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.moara.tokenizer.rest;

import org.moara.ara.datamining.textmining.dictionary.word.WordDictionary;
import org.moara.yido.tokenizer.TokenizerManager;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * api 서버 시작
 * @author macle
 */
@SpringBootApplication(scanBasePackages = {"org.moara"})
public class ApiStart {


    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {

        return beanFactory -> {
            BeanDefinition bean = beanFactory.getBeanDefinition(
                    DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);

            bean.getPropertyValues().add("loadOnStartup", 1);
        };
    }

    public static void main(String[] args) {

        try {

            //사용할 메모리 정보 load
            //noinspection ResultOfMethodCallIgnored
            WordDictionary.getInstance();
            //noinspection ResultOfMethodCallIgnored
            TokenizerManager.getInstance();

            HashMap<String, Object> props = new HashMap<>();
            props.put("server.port", Integer.parseInt( new String(Files.readAllBytes(Paths.get("config/port_number"))).trim() )  );
            props.put("logging.config","config/logback.xml");


            String[] springbootArgs = new String[0];

            new SpringApplicationBuilder()
                    .sources(ApiStart.class)
                    .properties(props)
                    .run(springbootArgs);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
