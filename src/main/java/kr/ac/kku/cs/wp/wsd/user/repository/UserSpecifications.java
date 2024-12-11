package kr.ac.kku.cs.wp.wsd.user.repository;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import kr.ac.kku.cs.wp.wsd.user.entity.User;
import kr.ac.kku.cs.wp.wsd.user.entity.UserRole;

/**
 * UserSpecification
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
public class UserSpecifications {

	private static final Logger logger = LogManager.getLogger(UserSpecifications.class);
	
	public static Specification<User> filterUsersByQueryString(String queryString) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join for userRoles
            Join<User, UserRole> userRolesJoin = root.join("userRoles", JoinType.LEFT);

            // Dynamic conditions
            if (queryString != null) {
            	
            	logger.debug("in spec ");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("id")), "%" + queryString.toLowerCase() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + queryString.toLowerCase() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + queryString.toLowerCase() + "%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + queryString.toLowerCase() + "%"));
                predicates.add(criteriaBuilder.like(userRolesJoin.get("roleName"), "%" + queryString + "%"));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
	
	public static Specification<User> filterUsers(User user) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<User, UserRole> userRolesJoin = root.join("userRoles", JoinType.LEFT);


            if (user != null) {
                if (user.getId() != null && !user.getId().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("id")), "%" + user.getId().toLowerCase() + "%"));
                }
                if (user.getName() != null && !user.getName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + user.getName().toLowerCase() + "%"));
                }
                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + user.getEmail().toLowerCase() + "%"));
                }
                if (user.getStatus() != null && !user.getStatus().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + user.getStatus().toLowerCase() + "%"));
                }
                if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
                    predicates.add(criteriaBuilder.like(userRolesJoin.get("roleName"), "%" + user.getUserRoles().get(0).getRoleName() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}