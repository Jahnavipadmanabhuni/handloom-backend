package com.handloom.controller;

import com.handloom.config.JwtUtil;
import com.handloom.model.Campaign;
import com.handloom.service.CampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    private final JwtUtil jwtUtil;

    private String extractEmail(String header) {
        return jwtUtil.extractEmail(header.replace("Bearer ", ""));
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @GetMapping("/my-campaigns")
    public ResponseEntity<List<Campaign>> getMyCampaigns(
            @RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(campaignService.getMyCampaigns(extractEmail(auth)));
    }

    @PostMapping
    public ResponseEntity<?> createCampaign(
            @RequestBody Campaign campaign,
            @RequestHeader("Authorization") String auth) {
        try {
            return ResponseEntity.ok(campaignService.createCampaign(campaign, extractEmail(auth)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampaign(
            @PathVariable Long id,
            @RequestBody Campaign campaign) {
        try {
            return ResponseEntity.ok(campaignService.updateCampaign(id, campaign));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}
