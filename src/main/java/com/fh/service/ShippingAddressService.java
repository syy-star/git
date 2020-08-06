package com.fh.service;


import com.fh.commons.exception.CountException;

import java.util.Map;

public interface ShippingAddressService {
    Map addOrder(Integer addressId,Integer payType) throws CountException;

}
