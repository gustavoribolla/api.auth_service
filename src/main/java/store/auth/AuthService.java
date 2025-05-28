package store.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import store.account.AccountController;
import store.account.AccountIn;
import store.account.AccountOut;

@Service
public class AuthService {

    @Autowired
    private AccountController accountController;

    @Autowired
    private JwtService jwtService;

    public Register register(Register register) {
        AccountIn accountIn = AccountIn.builder()
            .email(register.email())
            .name(register.name())
            .password(register.password())
            .build();

        ResponseEntity<AccountOut> response = accountController.create(accountIn);
        AccountOut accountOut = response.getBody();

        return Register.builder()
            .id(accountOut.id())
            .email(accountOut.email())
            .name(accountOut.name())
            .password(register.password())
            .build();
    }

    public Register findByEmailAndPassword(String email, String password) {
        ResponseEntity<AccountOut> response = accountController.findByEmailAndPassword(
            AccountIn.builder()
                .email(email)
                .password(password)
                .build()
        );
        AccountOut accountOut = response.getBody();

        return Register.builder()
            .id(accountOut.id())
            .email(accountOut.email())
            .name(accountOut.name())
            .build();
    }

    public String generateToken(Register register) {
        Date notBefore = new Date();
        Date expiration = new Date(notBefore.getTime() + 1000L * 60 * 60 * 24);
        return jwtService.create(register.id(), notBefore, expiration);
    }

    public SolveOut solve(String token) {
        return SolveOut.builder()
            .idAccount(jwtService.getId(token))
            .build();
    }
}