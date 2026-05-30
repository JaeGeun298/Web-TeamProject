package com.example.TripCalendar.service;

import com.example.TripCalendar.dto.FundFormDTO;
import com.example.TripCalendar.entity.Fund;
import com.example.TripCalendar.entity.Trip;
import com.example.TripCalendar.entity.UserEntity;
import com.example.TripCalendar.repository.FundRepository;
import com.example.TripCalendar.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FundService {

    private final FundRepository fundRepository;
    private final TripRepository tripRepository;

    // 공금 납입 기록
    public void addFund(Long tripId, FundFormDTO form, UserEntity user) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("없는 여행입니다."));

        Fund fund = new Fund();
        fund.setTrip(trip);
        fund.setUser(user);
        fund.setAmount(form.getAmount());
        fund.setPaidDate(form.getPaidDate());
        fund.setNote(form.getNote());

        fundRepository.save(fund);
    }

    // 공금 목록 조회
    @Transactional(readOnly = true)
    public List<Fund> getFunds(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("없는 여행입니다."));
        return fundRepository.findByTrip(trip);
    }

    // 공금 납입 총액
    @Transactional(readOnly = true)
    public int getTotalFund(Long tripId) {
        return getFunds(tripId).stream()
                .mapToInt(Fund::getAmount)
                .sum();
    }

    // 공금 기록 삭제
    public void deleteFund(Long fundId) {
        fundRepository.deleteById(fundId);
    }
}