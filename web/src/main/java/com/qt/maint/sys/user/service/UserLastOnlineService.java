/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.qt.maint.sys.user.service;

import com.qt.maint.common.service.BaseService;
import com.qt.maint.sys.user.entity.UserLastOnline;
import com.qt.maint.sys.user.repository.UserLastOnlineRepository;
import org.springframework.stereotype.Service;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:01
 * <p>Version: 1.0
 */
@Service
public class UserLastOnlineService extends BaseService<UserLastOnline, Long> {

    private UserLastOnlineRepository getUserLastOnlineRepository() {
        return (UserLastOnlineRepository) baseRepository;
    }


    public UserLastOnline findByUserId(Long userId) {
        return getUserLastOnlineRepository().findByUserId(userId);
    }

    public void lastOnline(UserLastOnline lastOnline) {
        UserLastOnline dbLastOnline = findByUserId(lastOnline.getUserId());

        if (dbLastOnline == null) {
            dbLastOnline = lastOnline;
        } else {
            UserLastOnline.merge(lastOnline, dbLastOnline);
        }
        dbLastOnline.incLoginCount();
        dbLastOnline.incTotalOnlineTime();
        //相对于save or update
        save(dbLastOnline);
    }
}
