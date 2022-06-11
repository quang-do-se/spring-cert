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
package com.apress.cems.web.controllers;

import com.apress.cems.dao.Detective;
import com.apress.cems.dj.services.DetectiveService;
import com.apress.cems.dj.services.wrappers.DetectiveWrapper;
import com.apress.cems.web.problem.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static com.apress.cems.util.Functions.COMPARATOR_BY_ID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
// TODO 48. complete the configuration and implementation of this class so that the Detectives part of the application works too.
@Controller
@RequestMapping("/detectives")
public class DetectiveController {

    private Logger logger = LoggerFactory.getLogger(DetectiveController.class);

    private DetectiveService detectiveService;

    public DetectiveController(DetectiveService detectiveService) {
        this.detectiveService = detectiveService;
    }

    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
        List<Detective> detectives = detectiveService.findAll();
        detectives.sort(COMPARATOR_BY_ID);
        model.addAttribute("detectives", detectives);
        return "detectives/list";
    }

    @GetMapping(value = "/show")
    public String show(@RequestParam("id") Long id, Model model) throws NotFoundException {
        DetectiveWrapper detectiveWrapper = detectiveService.findById(id);
        if (!detectiveWrapper.isEmpty()) {
            model.addAttribute("detective", detectiveWrapper);
        } else {
            throw new NotFoundException(Detective.class, id);
        }
        return "detectives/show";
    }
}
