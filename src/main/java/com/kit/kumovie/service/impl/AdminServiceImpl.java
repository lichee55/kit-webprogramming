package com.kit.kumovie.service.impl;

import com.kit.kumovie.domain.DiscountType;
import com.kit.kumovie.domain.Screening;
import com.kit.kumovie.domain.repository.ScreeningRepository;
import com.kit.kumovie.dto.ApplyDiscountDTO;
import com.kit.kumovie.dto.ScreeningListDTO;
import com.kit.kumovie.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ScreeningRepository screeningRepository;

    @Override
    public List<ScreeningListDTO> getScreeningList() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime after = now.plusDays(6).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Screening> allScreening = screeningRepository.findAllByStartTimeBetweenOrderByStartTime(now, after);
        return allScreening.stream().map(ScreeningListDTO::of).collect(Collectors.toList());
    }

    @Override
    public void applyDiscount(ApplyDiscountDTO applyDiscountDTO) {
        Screening screening = screeningRepository.findById(applyDiscountDTO.getScreeningId()).orElseThrow(() -> new RuntimeException("존재하지 않는 상영작입니다."));
        screening.setDiscountType(applyDiscountDTO.getDiscountType());
        if (applyDiscountDTO.getDiscountType() == DiscountType.NONE) {
            screening.setDiscountValue(BigDecimal.ZERO);
            screeningRepository.save(screening);
            return;
        }
        screening.setDiscountValue(applyDiscountDTO.getDiscountValue());
        screeningRepository.save(screening);
    }
}