package com.javaproject.leaderboardservice.repositories;

import com.javaproject.leaderboardservice.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //@Query("select u.userName from User u inner join u.area ar where ar.idArea = :idArea")

//    @Query(nativeQuery = true,value = "Select * from user JOIN role ON user.role_id = role.id WHERE role.id = :role_id")
//    Page<Role> getRoleById(
//                            @Param("role_id") Long role_id,
//                             Pageable pageable
//                            );
}
