/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.qt.maint.personal.notification.web.controller;

import com.qt.maint.common.entity.search.SearchOperator;
import com.qt.maint.common.entity.search.Searchable;
import com.qt.maint.common.web.bind.annotation.PageableDefaults;
import com.qt.maint.common.web.controller.BaseController;
import com.qt.maint.maintain.notification.entity.NotificationData;
import com.qt.maint.maintain.notification.service.NotificationDataService;
import com.qt.maint.sys.user.entity.User;
import com.qt.maint.sys.user.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-9 下午2:01
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/admin/personal/notification")
public class NotificationController extends BaseController {

    @Autowired
    private NotificationDataService notificationDataService;

    @RequestMapping
    @PageableDefaults(value = 20, sort = "id=desc")
    public String list(@CurrentUser User user, Pageable pageable, Model model) {

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilter("userId", SearchOperator.eq, user.getId());

        Page<NotificationData> page = notificationDataService.findAll(pageable);

        model.addAttribute("page", page);
        if(pageable.getPageNumber() == 0) {
            notificationDataService.markReadAll(user.getId());
        }

        return viewName("list");
    }

    @RequestMapping("/markRead")
    @ResponseBody
    public String markRead(@RequestParam("id") Long id) {
        notificationDataService.markRead(id);
        return "";
    }

}
