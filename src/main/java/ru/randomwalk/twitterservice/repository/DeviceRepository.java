package ru.randomwalk.twitterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.randomwalk.twitterservice.model.entity.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUser_UserId(UUID userId);

    boolean existsByUser_UserIdAndDeviceToken(UUID userId, String token);

    Optional<Device> findByUser_UserIdAndDeviceToken(UUID userId, String token);
}
