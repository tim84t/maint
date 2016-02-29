/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.qt.maint.sys.permission.web.controller;

import com.qt.maint.common.Constants;
import com.qt.maint.common.entity.enums.AvailableEnum;
import com.qt.maint.common.web.controller.BaseCRUDController;
import com.qt.maint.sys.permission.entity.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/permission/permission")
public class PermissionController extends BaseCRUDController<Permission, Long> {

    public PermissionController() {
        setResourceIdentity("sys:permission");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("availableList", AvailableEnum.values());
    }

    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
            Permission permission = baseService.findOne(id);
            permission.setShow(newStatus);
            baseService.update(permission);
        }


        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


}
