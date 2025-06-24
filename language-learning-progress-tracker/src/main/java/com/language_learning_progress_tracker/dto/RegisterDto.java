package com.language_learning_progress_tracker.dto;

import com.language_learning_progress_tracker.entity.UserRole;

public record RegisterDto(String username, String password, UserRole role) {
}
