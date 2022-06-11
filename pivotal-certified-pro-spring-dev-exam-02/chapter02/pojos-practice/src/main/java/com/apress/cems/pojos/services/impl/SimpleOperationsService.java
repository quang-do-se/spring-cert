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
package com.apress.cems.pojos.services.impl;

import com.apress.cems.dao.*;
import com.apress.cems.pojos.repos.CriminalCaseRepo;
import com.apress.cems.pojos.repos.DetectiveRepo;
import com.apress.cems.pojos.repos.EvidenceRepo;
import com.apress.cems.pojos.repos.StorageRepo;
import com.apress.cems.pojos.services.OperationsService;
import com.apress.cems.pojos.services.ServiceException;
import com.apress.cems.util.CaseType;
import com.apress.cems.util.Rank;
import org.apache.commons.lang3.NotImplementedException;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class SimpleOperationsService implements OperationsService {
    private CriminalCaseRepo criminalCaseRepo;
    private EvidenceRepo evidenceRepo;
    private DetectiveRepo detectiveRepo;
    private StorageRepo storageRepo;

    @Override
    public Detective createDetective(String firstName, String lastName, LocalDateTime hiringDate, Rank rank) {
        throw new NotImplementedException("Not needed for this section.");
    }

    @Override
    public CriminalCase createCriminalCase(CaseType caseType, String shortDescription, String badgeNo, Map<Evidence, String> evidenceMap) {
        // get detective
        // TODO 1. retrieve detective  (according to diagram 2.5)
        Optional<Detective> detectiveOptional = detectiveRepo.findByBadgeNumber(badgeNo);

        // create a criminal case instance
        CriminalCase criminalCase = new CriminalCase();
        // TODO 2. set fields; use ifPresent(..) to set(or not) the leadDetective field
        criminalCase.setShortDescription(shortDescription);
        criminalCase.setType(caseType);
        detectiveOptional.ifPresent(criminalCase::setLeadInvestigator);

        evidenceMap.forEach((evidence, storageName) -> {
            // TODO 3. retrieve storage, throw ServiceException if not found
            Optional<Storage> storageOptional = storageRepo.findByName(storageName);

            storageOptional.ifPresentOrElse(
                    storage -> {
                        // TODO 4. if storage is found, link it to the evidence and add evidence to the case
                        storage.addEvidence(evidence);
                        evidence.setStorage(storage);
                        criminalCase.addEvidence(evidence);
                        evidenceRepo.save(evidence);
                    },
                    () -> { throw new ServiceException("Storage " + storageName + " is not found."); }
            );
        });

        // TODO 5. save the criminal case instance
        criminalCaseRepo.save(criminalCase);

        return criminalCase;
    }

    @Override
    public Optional<CriminalCase> assignLeadInvestigator(String caseNumber, String leadDetectiveBadgeNo) {
        throw new NotImplementedException("Not needed for this section.");
    }

    @Override
    public Optional<CriminalCase> linkEvidence(String caseNumber, List<Evidence> evidenceList) {
        throw new NotImplementedException("Not needed for this section.");
    }

    @Override
    public boolean solveCase(String caseNumber, String reason) {
        throw new NotImplementedException("Not needed for this section.");
    }

    @Override
    public Set<Detective> getAssignedTeam(String caseNumber) {
        throw new NotImplementedException("Not needed for this section.");
    }

    //setters
    @Override
    public void setCriminalCaseRepo(CriminalCaseRepo criminalCaseRepo) {
        this.criminalCaseRepo = criminalCaseRepo;
    }

    @Override
    public void setEvidenceRepo(EvidenceRepo evidenceRepo) {
        this.evidenceRepo = evidenceRepo;
    }

    @Override
    public void setDetectiveRepo(DetectiveRepo detectiveRepo) {
        this.detectiveRepo = detectiveRepo;
    }

    @Override
    public void setStorageRepo(StorageRepo storageRepo) {
        this.storageRepo = storageRepo;
    }
}
