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
package com.apress.cems.tx;

import com.apress.cems.aop.service.DetectiveService;
import com.apress.cems.aop.service.PersonService;
import com.apress.cems.dao.Person;
import com.apress.cems.tx.config.AppConfig;
import com.apress.cems.tx.config.TestTransactionalDbConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestTransactionalDbConfig.class, AppConfig.class})
class DetectiveServiceTest {

    private Logger logger = LoggerFactory.getLogger(DetectiveServiceTest.class);

    @Autowired
    @Qualifier("detectiveServiceImpl")
    DetectiveService detectiveService;

    @Autowired
    PersonService personService;

    @Test
    void testFindById(){
        detectiveService.findById(1L).ifPresentOrElse(
                d -> assertEquals("LD112233", d.getBadgeNumber()),
                Assertions:: fail
        );
    }

    @Test
    void testDetectiveHtml(){
        detectiveService.findById(1L).ifPresent(
                d -> {
                    Person p = d.getPerson();
                    assertNotNull(p);
                    String html = personService.getPersonAsHtml(p.getUsername());
                    logger.info("Detective personal info: {}", html);
                }
        );
    }

}
