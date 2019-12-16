package com.xpjava.medicine.service.impl;

import com.xpjava.medicine.entity.User;
import com.xpjava.medicine.mapper.UserMapper;
import com.xpjava.medicine.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @since 2019-12-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
