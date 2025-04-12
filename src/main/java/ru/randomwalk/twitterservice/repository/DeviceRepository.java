package ru.randomwalk.twitterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.randomwalk.twitterservice.model.entity.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findAllByUserId(UUID userId);

    boolean existsByUserIdAndDeviceToken(UUID userId, String token);

    Optional<Device> findByUserIdAndDeviceToken(UUID userId, String token);
}
