package com.handloom.service;

import com.handloom.model.Campaign;
import com.handloom.model.User;
import com.handloom.repository.CampaignRepository;
import com.handloom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public List<Campaign> getMyCampaigns(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return campaignRepository.findByCreatedBy(user);
    }

    public Campaign createCampaign(Campaign campaign, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        campaign.setCreatedBy(user);
        return campaignRepository.save(campaign);
    }

    public Campaign updateCampaign(Long id, Campaign updated) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        campaign.setName(updated.getName());
        campaign.setPlatform(updated.getPlatform());
        campaign.setBudget(updated.getBudget());
        campaign.setStartDate(updated.getStartDate());
        campaign.setEndDate(updated.getEndDate());
        campaign.setAudience(updated.getAudience());
        campaign.setDescription(updated.getDescription());
        campaign.setStatus(updated.getStatus());
        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}