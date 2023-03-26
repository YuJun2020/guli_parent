package com.yj.msm.service;

import java.util.HashMap;

public interface MsmService {
    boolean send(String phone, HashMap<String,String> param);
}
