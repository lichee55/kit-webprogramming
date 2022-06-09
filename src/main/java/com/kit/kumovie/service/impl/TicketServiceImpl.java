package com.kit.kumovie.service.impl;

import com.kit.kumovie.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
}
