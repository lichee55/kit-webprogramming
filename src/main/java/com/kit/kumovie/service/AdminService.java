package com.kit.kumovie.service;

import com.kit.kumovie.dto.ApplyDiscountDTO;
import com.kit.kumovie.dto.ScreeningListDTO;

import java.util.List;

public interface AdminService {

    List<ScreeningListDTO> getScreeningList();

    void applyDiscount(ApplyDiscountDTO applyDiscountDTO);
}
