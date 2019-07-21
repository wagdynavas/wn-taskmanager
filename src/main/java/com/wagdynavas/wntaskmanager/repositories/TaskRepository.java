package com.wagdynavas.wntaskmanager.repositories;

import com.wagdynavas.wntaskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(" select t from  Task t where t.done = false")
    List<Task> findAllOpenTasks();

    @Query("select t from Task t where t.done = true")
    List<Task> findAllClosedTasks();

    @Query("select t from Task t where t.user.email = :email")
    List<Task> findTasksByUsername(@Param("email") String email);

    @Query("select t from Task t where t.user.email = :email and t.done = false")
    List<Task> findAllOpenTasksByUsername(@Param(("email")) String username);
}
