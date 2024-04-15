package org.joksin.sociallogin.model;

public record AuthenticatedUser(
    String id, String email, String name, String firstName, String lastName, String provider) {}
