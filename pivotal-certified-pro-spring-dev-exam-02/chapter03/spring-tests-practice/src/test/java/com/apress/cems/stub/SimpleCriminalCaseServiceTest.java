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
package com.apress.cems.stub;

import com.apress.cems.dao.Detective;
import com.apress.cems.repos.NotFoundException;
import com.apress.cems.services.impl.SimpleCriminalCaseService;
import com.apress.cems.stub.repo.StubCriminalCaseRepo;
import com.apress.cems.util.Rank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.apress.cems.stub.util.TestObjectsBuilder.buildDetective;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleCriminalCaseServiceTest {
    static final Long CASE_ID = 1L;
    final Detective detective = buildDetective("Sherlock", "Holmes", Rank.INSPECTOR, "TS1234");

    StubCriminalCaseRepo repo = new StubCriminalCaseRepo();

    SimpleCriminalCaseService service = new SimpleCriminalCaseService();

    @BeforeEach
     void setUp(){
        repo.init();

        //create object to be tested
        service = new SimpleCriminalCaseService();
        service.setRepo(repo);
    }

    //positive test, we know that a Case with ID=1 exists
    @Test
     void findByIdPositive() {
        var criminalCase = service.findById(CASE_ID);
        assertNotNull(criminalCase);
    }

    //negative test, we know that a Case with ID=99 does not exist
    @Test
     void findByIdNegative() {
        assertThrows( NotFoundException.class, () ->
                service.findById(99L), "No such case exists");
    }

    @Test
     void deleteByIdPositive() {
        service.deleteById(CASE_ID);

        // we do a find to test the deletion succeeded
        assertThrows( NotFoundException.class, () ->
                service.findById(CASE_ID), "No such case exists");
    }

    @Test
     void deleteByIdNegative() {
        // TODO 15. Analyse the stub implementation and add a test for service.deleteById(99L)
        assertThrows( NotFoundException.class, () -> service.deleteById(99L));
    }

    //positive test, we know that cases for this detective exist and how many
    @Test
     void findByLeadPositive() {
        var result =  service.findByLeadInvestigator(detective);
        assertEquals(2, result.size());
    }

    //negative test, we know that cases for this detective do not exist
    @Test
    public void findByLeadNegative() {
        // TODO 16. Analyse the stub implementation and add a test for service.findByLeadInvestigator(detective);
        Detective newDetective = buildDetective("Quang", "Do", Rank.INSPECTOR, "TS5678");
        assertNull(service.findByLeadInvestigator(newDetective));
    }

    @AfterEach
     void tearDown(){
        repo.clear();
    }
}
