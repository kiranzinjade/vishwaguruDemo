package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.LedgerAccounts_;
import com.techvg.vks.accounts.model.LedgerAccountSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LedgerAccountSpecification {

    public static Specification<LedgerAccounts> getLedgerAccounts(LedgerAccountSearchCriteria search) {
        return (la, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.equal(la.get("isDeleted"), false));

            if(search.getAccountType() !=null && search.getAccountType() !="null"){
                Join<Object, Object> accountType = (Join<Object, Object>) la.fetch(LedgerAccounts_.ACCOUNT_TYPE);
                predicates.add(
                        cb.equal(cb.lower(accountType.get("name")), search.getAccountType().toLowerCase()));
            }

            if (search.getAccHeadCode() != null && search.getAccHeadCode() != "null" ) {
                predicates.add(
                        cb.like(cb.lower(la.get("accHeadCode")), "%" + search.getAccHeadCode().toLowerCase()+ "%"));
            }

            Predicate[] predicatesArr = predicates.toArray(new Predicate[predicates.size()]);
            return cb.and(predicatesArr);
        };
    }
}
