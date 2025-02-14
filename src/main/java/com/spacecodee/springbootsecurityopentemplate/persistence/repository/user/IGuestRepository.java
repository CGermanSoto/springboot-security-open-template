package com.spacecodee.springbootsecurityopentemplate.persistence.repository.user;

import com.spacecodee.springbootsecurityopentemplate.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuestRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.username = :username AND u.roleEntity.id = :roleId")
    boolean existsByUsernameAndRoleId(@Param("username") String username, @Param("roleId") Integer roleId);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.email = :email AND u.roleEntity.id = :roleId")
    boolean existsByEmailAndRoleId(@Param("email") String email, @Param("roleId") Integer roleId);

    @Query("""
            SELECT u FROM UserEntity u
            WHERE (:username IS NULL OR u.username LIKE %:username%)
            AND (:firstName IS NULL OR u.firstName LIKE %:firstName%)
            AND (:lastName IS NULL OR u.lastName LIKE %:lastName%)
            AND u.roleEntity.id = :roleId
            AND (:status IS NULL OR u.status = :status)
            """)
    Page<UserEntity> searchGuests(
            @Param("username") String username,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("roleId") Integer roleId,
            @Param("status") Boolean status,
            Pageable pageable);
}
