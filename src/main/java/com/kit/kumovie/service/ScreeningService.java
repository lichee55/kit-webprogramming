package com.kit.kumovie.service;

import com.kit.kumovie.dto.ScreeningDetailDTO;
import com.kit.kumovie.dto.ScreeningListDTO;

import java.util.List;

public interface ScreeningService {

    List<ScreeningListDTO> getScreeningList(String filmId);

    ScreeningDetailDTO getScreeningDetail(Long screeningId);
}
