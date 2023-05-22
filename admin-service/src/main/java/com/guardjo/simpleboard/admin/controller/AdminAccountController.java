package com.guardjo.simpleboard.admin.controller;

import com.guardjo.simpleboard.admin.controller.constant.UrlConstant;
import com.guardjo.simpleboard.admin.model.response.AdminAccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class AdminAccountController {
    @GetMapping(UrlConstant.ADMIN_ACCOUNT_URL_PREFIX)
    public String getDefaultViewPage() {
        log.info("[Test] Request Default Admin Account Page");
        return "admin/account";
    }

    @GetMapping(UrlConstant.ADMIN_ACCOUNT_API_URL)
    @ResponseBody
    public List<AdminAccountResponse> getAdminAccountResponses() {
        log.info("Request Find AdminAccountResponses");
        return List.of();
    }

    @DeleteMapping(UrlConstant.ADMIN_ACCOUNT_API_URL + "/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdminAccount(@PathVariable String email) {
        log.info("Request Delete AdminAccountResponse, email = {}", email);
    }
}
