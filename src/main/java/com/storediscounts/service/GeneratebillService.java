package com.storediscounts.service;

import com.storediscounts.dto.BillDto;

public interface GeneratebillService {

	BillDto generateBillWithDiscount(Long userId);

}
