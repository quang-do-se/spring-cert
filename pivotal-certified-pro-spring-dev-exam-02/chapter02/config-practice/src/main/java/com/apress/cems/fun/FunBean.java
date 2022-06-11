/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.fun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 * Bean that is initialized using all three techniques supported in Spring
 */
// TODO 12. Add initialization and destroy methods to implement all three techniques specified in the book
@Component
public class FunBean implements InitializingBean, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(FunBean.class);

    private DepBean depBean;

    private String description;

    @Autowired
    public FunBean(DepBean depBean) {
        logger.info("Stage 1: Calling the constructor");
        this.depBean = depBean;
    }

    @Autowired
    public void setDepBean(DepBean depBean) {
        logger.info("Stage 2: Calling the setter");
        this.depBean = depBean;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @PostConstruct
    private void postConstruct() {
        logger.info("Stage 3.1: call @PostConstruct method");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Stage 3.2: call InitializingBean method");
    }

    void beanInitMethod() {
        logger.info("Stage 3.3: call beanInitMethod()");
    }


    @PreDestroy
    private void preDestroy() {
        logger.info("Stage 4.1: call @PreDestroy method");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Stage 4.2: call DisposableBean method");
    }

    void beanDestroyMethod() {
        logger.info("Stage 4.3: call beanDestroyMethod()");
    }
}
