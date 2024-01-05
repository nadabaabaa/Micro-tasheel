package tn.esprit.infini.Pidev.Services.user_management.exception;
    public class EmailAlreadyExistsException extends RuntimeException {

        public EmailAlreadyExistsException(String email) {
            super("Email " + email + "already exists");
        }

    }

