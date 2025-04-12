package ru.randomwalk.twitterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.randomwalk.twitterservice.model.entity.UserAccount;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

}
