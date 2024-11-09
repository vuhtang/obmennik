package org.highload.controller;

import lombok.RequiredArgsConstructor;
import org.highload.service.AccountsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountsService service;

    @GetMapping("/")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }
}
