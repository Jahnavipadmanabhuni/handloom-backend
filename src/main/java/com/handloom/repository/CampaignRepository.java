package com.handloom.repository;

import com.handloom.model.Campaign;
import com.handloom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByCreatedBy(User user);
    List<Campaign> findByStatus(String status);
}
