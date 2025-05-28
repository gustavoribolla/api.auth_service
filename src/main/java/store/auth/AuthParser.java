package store.auth;

public class AuthParser {

    public static Register to(RegisterIn in) {
        return in == null ? null :
            Register.builder()
                .name(in.name())
                .email(in.email())
                .password(in.password())
                .build();
    }

    public static TokenOut to(Register register, String token) {
        return TokenOut.builder()
            .id(register.id())
            .token(token)
            .build();
    }
}