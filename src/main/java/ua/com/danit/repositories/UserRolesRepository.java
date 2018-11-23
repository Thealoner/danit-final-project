package ua.com.danit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.danit.models.UserRoles;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
